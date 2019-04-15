$(function () {
    $('#permissionlist').datagrid({
        url: 'permission/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'pid', title: 'pid', hidden: true},
            {field: 'name', title: '权限名称', width: 220},
            {field: 'nameValue', title: '权限键值', width: 200},
            {field: 'modular', title: '所属模块', width: 100, sortable: true},
            {field: 'description', title: '操作描述', width: 280}
        ]]
    });
});


/*定义数据表格的按钮及事件*/
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#permissionadd').form('clear');
        $('#w').window('open');
    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        getSelectedpermission();
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedpermission();
    }
}];

/*新增数据表单提交*/
function submitpermissionForm() {
    $('#permissionadd').form('submit', {
        url: 'permission/create.action',
        onSubmit: function () {
            return $('#permissionadd').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                $.messager.alert('提示信息', '成功创建权限,可继续添加');
                $('#permissionadd').form('clear');
                $('#permissionlist').datagrid('reload');
            } else {
                alert("登录超时请重新登录");
            }
        }
    });
}

/*修改数据表单提交*/
function updatepermissionForm() {
    $('#permissionupdate').form('submit', {
        url: 'permission/update.action',
        onSubmit: function () {
            return $('#permissionupdate').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                $.messager.alert('提示信息', '成功修改权限');
                $('#permissionlist').datagrid('reload');
                $('#u').window('close');
            } else {
                alert("登录超时请重新登录");
            }
        }
    });
}

/*删除所选数据*/
function deleteSelectedpermission() {
    var row = $('#permissionlist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'permission/delete.action',
                    data: 'pid=' + row.pid,
                    success: function (msg) {
                        if (msg === "success") {
                            $.messager.alert('提示信息', '成功删除权限');
                            $('#permissionlist').datagrid('reload');
                        } else {
                            alert("登录超时请重新登录");
                        }
                    }
                });
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}

/*重置数据表单*/
function clearpermissionForm() {
    $('#permissionadd').form('clear');
}

/*取得选中行数据并打开修改表单*/
function getSelectedpermission() {
    var row = $('#permissionlist').datagrid('getSelected');
    if (row) {
        $('#permissionupdate').form('clear');
        $('#permissionupdate').form('load', row);
        $('#u').window('open');
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}
