$(function () {
    $('#projectnomanagelist').datagrid({
        url: 'projectnomanage/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', hidden: true},
            {field: 'projectno', title: '工程号编码', width: 200, sortable: true},
            {field: 'projectname', title: '工程号名称', width: 420, sortable: true},
            {field: 'createperson', title: '创建人', width: 220, sortable: true},
            {field: 'createtime', title: '创建时间', width: 220, sortable: true},
            {field: 'remark', title: '备注', width: 220, sortable: true}
            ]]
    });
});

var url;
/*定义数据表格的按钮及事件*/
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#projectnomanageadd').form('clear');
        url = 'projectnomanage/create';
        $('#w').window('setTitle', '新增工程号').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        //清空表单
        $('#projectnomanageadd').form('clear');
        getSelectedProjectnomanage();
    }
}, '-', {
    text: '关闭',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedProjectnomanage();
    }
}, '-', {
    text: '查看详情',
    iconCls: 'icon-search',
    handler: function () {
        viewSelected();
    }
}];

/*新增及修改工程号信息表单提交*/
function submitprojectnomanageForm() {
    $('#projectnomanageadd').form('submit', {
        url: url,
        onSubmit: function () {
            return $('#projectnomanageadd').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url.indexOf('update') > 0) {
                    $('#projectnomanageadd').form('clear');
                    $('#w').window('close');
                    $('#projectnomanagelist').datagrid('reload');
                    $.messager.alert('提示信息', '工程号已成功修改', 'info');
                } else {
                    $('#projectnomanageadd').form('clear');
                    $('#w').window('close');
                    $('#projectnomanagelist').datagrid('reload');
                    $.messager.alert('提示信息', '工程号已成功录入', 'info');
                }
            } else if (data == "工程号编码已存在") {
                $.messager.alert('提示信息', '工程号编码已存在请重新输入', 'info');
            } else if (data == "工程号名称已存在") {
                $.messager.alert('提示信息', '工程号名称已存在请重新输入', 'info');
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 修改选中的工程号信息 */
function getSelectedProjectnomanage() {
    var row = $('#projectnomanagelist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'projectnomanage/getbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url = 'projectnomanage/update';
                /* 加载数据 */
                $('#projectnomanageadd').form('clear');
                $('#projectnomanageadd').form('load', data);
                $('#w').window('setTitle', '工程号基本信息修改').window('open');

            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 关闭选中的工程号信息*/
function deleteSelectedProjectnomanage() {
    var row = $('#projectnomanagelist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定关闭此工程号?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'projectnomanage/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#projectnomanagelist').datagrid('reload');
                            $.messager.alert('提示信息', '成功关闭工程号', 'info');
                        } else {
                            alert("登录超时删除失败，请重新登录");
                        }
                    }
                });
                $('#projectnomanagelist').datagrid('reload');
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
    $('#projectnomanagelist').datagrid('reload');
}

/*查看详情*/
function viewSelected() {
    var row = $('#projectnomanagelist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'projectnomanage/getbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 加载数据 */
                $('#projectnomanagedetail').form('clear');
                $('#projectnomanagedetail').form('load', data);

                setTimeout(function () {
                    /*延时打开窗口*/
                    $('#v').window('setTitle', '工程号基本信息').window('open');
                }, 300);
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 清空查询数据 */
function projectNoManagesearchclean() {
    $('#projectno1').textbox('reset');
    $('#projectname1').textbox('reset');
    $('#createperson1').textbox('reset');
}


/* 查询数据 */
function projectNoManagesearch() {
    var projectno1 = $('#projectno1').textbox('getValue');
    var projectname1 = $('#projectname1').textbox('getValue');
    var createperson1 = $('#createperson1').textbox('getValue');
    var url = 'projectnomanage/search?&projectno=' + projectno1 + '&projectname=' + encodeURIComponent(projectname1) + '&createperson=' + encodeURIComponent(createperson1);
    $('#projectnomanagelist').datagrid('options').url = url;
    $("#projectnomanagelist").datagrid('reload');
}