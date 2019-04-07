$(function () {
    $('#warehousenumlist').datagrid({
        url: 'warehousenum/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'ID', title: 'id', hidden: true},
            {field: 'STOCKCODE', title: '仓库编码', width: 120, sortable: true},
            {field: 'STOCKNAME', title: '仓库名称', width: 120, sortable: true},
            {field: 'SSUNITNAME', title: '所属部门', width: 120, sortable: true},
            {field: 'STOCKTYPENAME', title: '仓库类型', width: 100, sortable: true},
            {field: 'FHRZW', title: '仓库操作人', width: 600, sortable: true},
        ]]
    });
});

/*定义数据表格的按钮及事件*/
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        url = 'warehousenum/create';
        $('#w').window('setTitle', '新增仓库').window('open');
    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        $("#stockcode2").textbox({readonly: true, disable: true});
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
    $("#stockcode1").textbox('reset');
    $("#stockname1").textbox('reset');
}

/*搜索方法*/
function declareunitsearch() {
    var stockcode = $("#stockcode1").textbox('getValue');
    var stockname = $("#stockname1").textbox('getValue');
    var url = 'warehousenum/search?&stockcode=' + stockcode + '&stockname=' + stockname;
    $('#warehousenumlist').datagrid('options').url = url;
    $("#warehousenumlist").datagrid('reload');
}

var url;

/*新增及修改数据表单提交*/
function submitwarehouseform() {
    //移除disable属性,后台取值

    $('#warehousenum').form('submit', {
        url: url,
        onSubmit: function () {
            return $('#warehousenum').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url.indexOf('update') > 0) {
                    $('#warehousenum').form('clear');

                    $('#w').window('close');
                    $('#warehousenumlist').datagrid('reload');
                    $.messager.alert('提示信息', '仓库信息已成功修改', 'info');
                } else {
                    $('#warehousenum').form('clear');
                    $('#w').window('close');
                    $('#warehousenumlist').datagrid('reload');
                    $.messager.alert('提示信息', '仓库信息已成功录入', 'info');
                }
            } else if (data === "仓库编码已存在") {
                $.messager.alert('提示信息', '仓库编码已存在请重新输入', 'info');
            } else if (data === "仓库名称已存在") {
                $.messager.alert('提示信息', '仓库名称已存在请重新输入', 'info');
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 修改选中的仓库数据---------------------------------------------------- */
function getSelectWarehousenum() {
    var row = $('#warehousenumlist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'warehousenum/getallbyid',
            data: 'id=' + row.ID,
            success: function (data) {
                /* 定义提交方法 */
                url = 'warehousenum/update';
                /* 加载数据 */
                $('#warehousenum').form('clear');
                $('#warehousenum').form('load', data);
                $('#w').window('setTitle', '仓库修改').window('open');

            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的仓库----------------------------------------------------- */
function deleteSelectWarehousenum() {
    var row = $('#warehousenumlist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'warehousenum/delete',
                    data: 'id=' + row.ID,
                    success: function (data) {
                        if (data === "success") {
                            $('#warehousenumlist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的仓库', 'info');
                        } else {
                            alert("登录超时删除失败，请重新登录");
                        }
                    }
                });
                $('#warehousenumlist').datagrid('reload');
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}


/*查看仓库详情==================================================================================*/
function getSelectdetail() {
    var row = $('#warehousenumlist').datagrid('getSelected');

    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'warehousenum/getallbyid',
            data: 'id=' + row.ID,
            success: function (data) {
                /*加载获取数据*/
                $('#warehousechildlist').form('clear');
                $('#warehousechildlist').form('load', data);

                /*延时加载相关数据,打开窗口*/
                setTimeout(function () {
                    $('#c').window('setTitle', '查看详情').window('open');
                }, 300);
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}