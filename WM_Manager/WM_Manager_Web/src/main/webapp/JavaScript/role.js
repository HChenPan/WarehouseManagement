$(function () {
    $('#rolelist').datagrid({
        url: 'role/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'rid', title: 'rid', hidden: true},
            {field: 'name', title: '角色名称', width: 150, sortable: true},
            {field: 'description', title: '功能描述', width: 500},
            {field: 'type', title: '角色类别', width: 150, sortable: true}
        ]]
    });
});


/*定义数据表格的按钮及事件*/
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#roleadd').form('clear');
        $('#w').window('open');
        getpdg();
    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        getpdgedit();
        setTimeout(function () {
            getSelectedrole();
        }, 500);
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedrole();
    }
}];

/*加载权限表格数据*/
function getpdg() {
    $('#pdg').datagrid({
        title: '对应权限',
        width: '700',
        height: '250',
        url: 'permission/getall',
        rownumbers: true,
        singleSelect: false,
        striped: true,
        checkOnSelect: true,
        selectOnCheck: true,
        columns: [[
            {field: 'pid', checkbox: true},
            {field: 'name', title: '权限名称', width: 215},
            {field: 'modular', title: '所属模块', width: 100},
            {field: 'description', title: '操作描述', width: 310}
        ]]
    });
}

/*修改页面加载权限表格数据*/
function getpdgedit() {
    $('#pdgedit').datagrid({
        title: '对应权限',
        width: '700',
        height: '250',
        url: 'permission/getall',
        rownumbers: true,
        singleSelect: false,
        striped: true,
        checkOnSelect: true,
        selectOnCheck: true,
        columns: [[
            {field: 'pid', checkbox: true},
            {field: 'name', title: '权限名称', width: 215},
            {field: 'modular', title: '所属模块', width: 100},
            {field: 'description', title: '操作描述', width: 310}
        ]]
    });
}


/*新增数据表单提交*/
function submitroleForm() {
    $('#roleadd').form('submit', {
        url: 'role/create.action',
        onSubmit: function () {
            return $('#roleadd').form('validate')
        },
        success: function (data) {
            if (data === "success") {
                $.messager.alert('提示信息', '成功创建角色,可继续添加');
                $('#roleadd').form('clear');
                $('#rolelist').datagrid('reload');
            } else {
                alert("登录超时请重新登录");
            }
        }
    });
}

/*修改数据表单提交*/
function updateroleForm() {
    $('#roleupdate').form('submit', {
        url: 'role/update.action',
        onSubmit: function () {
            return $('#roleupdate').form('validate')
        },
        success: function (data) {
            if (data === "success") {
                $.messager.alert('提示信息', '成功修改角色');
                $('#rolelist').datagrid('reload');
                $('#u').window('close');
            } else {
                alert("登录超时请重新登录");
            }
        }
    });
}

/*删除所选数据*/
function deleteSelectedrole() {
    var row = $('#rolelist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'role/delete.action',
                    data: 'rid=' + row.rid,
                    success: function (data) {
                        if (data === "success") {
                            $.messager.alert('提示信息', '成功删除角色');
                            $('#rolelist').datagrid('reload');
                        } else {
                            alert("登录超时请重新登录");
                        }
                    }
                })
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}

/*重置数据表单*/
function clearroleForm() {
    $('#roleadd').form('clear')
}

/*取得选中行数据并打开修改表单*/
function getSelectedrole() {
    var row = $('#rolelist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'role/getpidlistbyid.action',
            data: 'rid=' + row.rid,
            success: function (data) {
                row = data;
            }
        })
        $('#roleupdate').form('clear');
        $('#roleupdate').form('load', row);
        $('#u').window('open')
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}
