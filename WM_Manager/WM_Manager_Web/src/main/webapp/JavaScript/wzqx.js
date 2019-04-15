$(function () {
    $('#wzqxlist').datagrid({
        url: 'wzqx/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'wzqz', title: '物资编码前缀', width: 120, sortable: true},
            {field: 'czrzw', title: '物资操作人', width: 600, sortable: true},
        ]]
    });
});

/*定义数据表格的按钮及事件*/
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        url = 'wzqx/create';
        $('#w').window('setTitle', '新增物资权限').window('open');
    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {

        getSelectWarehousenum();
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectWarehousenum();
    }
}];

/*清空搜索条件*/
function declareunitsearchclean() {
    $("#wzqz1").textbox('reset');
    $("#czrzw1").textbox('reset');
}

/*搜索方法*/
function declareunitsearch() {
    var stockcode = $("#wzqz1").textbox('getValue');
    var stockname = $("#czrzw1").textbox('getValue');
    var url = 'wzqx/search?&wzqz=' + stockcode + '&czrzw=' + stockname;
    $('#wzqxlist').datagrid('options').url = url;
    $("#wzqxlist").datagrid('reload');
}

var url;

/*新增及修改数据表单提交*/
function submitwarehouseform() {
    //移除disable属性,后台取值

    $('#wzqx').form('submit', {
        url: url,
        onSubmit: function () {
            return $('#wzqx').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url.indexOf('update') > 0) {
                    $('#wzqx').form('clear');

                    $('#w').window('close');
                    $('#wzqxlist').datagrid('reload');
                    $.messager.alert('提示信息', '物资权限信息已成功修改', 'info');
                } else {
                    $('#wzqx').form('clear');
                    $('#w').window('close');
                    $('#wzqxlist').datagrid('reload');
                    $.messager.alert('提示信息', '物资权限信息已成功录入', 'info');
                }
            } else if (data === "cz") {
                $.messager.alert('提示信息', '物资编码前缀已存在请重新输入', 'info');
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 修改选中的仓库数据---------------------------------------------------- */
function getSelectWarehousenum() {
    var row = $('#wzqxlist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'wzqx/getallbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url = 'wzqx/update';
                /* 加载数据 */
                $('#wzqx').form('clear');
                $('#wzqx').form('load', data);
                $('#w').window('setTitle', '物资权限修改').window('open');

            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的仓库----------------------------------------------------- */
function deleteSelectWarehousenum() {
    var row = $('#wzqxlist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'wzqx/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#wzqxlist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的物资权限', 'info');
                        } else {
                            alert("登录超时删除失败，请重新登录");
                        }
                    }
                });
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}


