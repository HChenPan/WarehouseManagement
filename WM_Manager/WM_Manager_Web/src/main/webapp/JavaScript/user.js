$(function () {
    $('#userlist').datagrid({
        url: 'user/search.do',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', title: 'id', hidden: true, width: 100,},
            {field: 'password', title: 'password', hidden: true},
            {field: 'username', title: '用户名', width: 120, sortable: true},
            {field: 'realname', title: '真实姓名', width: 100, sortable: true},
            {
                field: 'issuper', title: '是否超管', width: 100, sortable: true, formatter: function (value, row, index) {
                    if (row.issuper) {
                        return '是';
                    } else {
                        return '否';
                    }
                }
            },
            {
                field: 'department', title: '所属部门', width: 120, formatter: function (value, row, index) {
                    if (row.department) {
                        return row.department;
                    } else {
                        return value;
                    }
                }
            },
            {field: 'tel', title: '联系电话', width: 120},
        ]]
    });
});

/*定义数据表格的按钮及事件*/
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#useradd').form('clear');
        $('#w').window('open');
        getrdg();
    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        getrdgedit();
        setTimeout(function () {
            getSelecteduser();
        }, 500);
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelecteduser();
    }
}];

/*加载角色表格数据*/
function getrdg() {
    $('#rdg').datagrid({
        title: '对应角色',
        width: '700',
        height: '205',
        url: 'role/getall',
        rownumbers: true,
        singleSelect: false,
        striped: true,
        checkOnSelect: true,
        selectOnCheck: true,
        columns: [[
            {field: 'rid', checkbox: true},
            {field: 'name', title: '角色名称', width: 150},
            {field: 'description', title: '操作描述', width: 350},
            {field: 'type', title: '角色类别', width: 150}
        ]]
    });
}

function getrdgedit() {
    $('#rdgedit').datagrid({
        title: '对应角色',
        width: '700',
        height: '205',
        url: 'role/getall',
        rownumbers: true,
        singleSelect: false,
        striped: true,
        checkOnSelect: true,
        selectOnCheck: true,
        columns: [[
            {field: 'rid', checkbox: true},
            {field: 'name', title: '角色名称', width: 150},
            {field: 'description', title: '操作描述', width: 350},
            {field: 'type', title: '角色类别', width: 150}
        ]]
    });
}

/*搜索方法*/
function usersearch() {
    var username = $("#searchusername").textbox('getValue');
    var realname = $("#searchrealname").textbox('getValue');
    var password1 = $("#password1").combotree('getValue');
    var password2 = $("#password2").combobox('getValue');
    var url = '/user/search?username=' + username + '&realname=' + encodeURIComponent(realname) + '&password1=' + password1 + '&password2=' + password2;
    $('#userlist').datagrid('options').url = url;
    $("#userlist").datagrid('reload');
}

/*清空搜索条件*/
function usersearchclean() {
    $("#searchusername").textbox('reset');
    $("#searchrealname").textbox('reset');
    $("#password1").combotree('reset');
    $("#password2").combobox('reset');
}

/*新增数据表单提交*/
function submituserForm() {
    $('#useradd').form('submit', {
        url: '/user/create',
        onSubmit: function () {
            return $('#useradd').form('validate')
        },
        success: function (data) {
            if (data == "success") {
                $.messager.alert('提示信息', '成功创建用户,可继续添加');
                $('#useradd').form('clear');
                $('#userlist').datagrid('reload');
            } else {
                alert("登录超时请重新登录");
            }
        }
    });
}

/*修改数据表单提交*/
function updateuserForm() {
    $('#userupdate').form('submit', {
        url: '/user/update',
        onSubmit: function () {
            return $('#userupdate').form('validate')
        },
        success: function (data) {
            if (data === "success") {
                $.messager.alert('提示信息', '成功修改用户');
                $('#userlist').datagrid('reload');
                $('#u').window('close');
            } else {
                alert("登录超时请重新登录");
            }
        }
    });
}

/*删除所选数据*/
function deleteSelecteduser() {
    var row = $('#userlist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: '/user/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $.messager.alert('提示信息', '成功删除用户');
                            $('#userlist').datagrid('reload');
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
function clearuserForm() {
    $('#useradd').form('clear')
}

/*取得选中行数据并打开修改表单*/
function getSelecteduser() {
    var row = $('#userlist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: '/user/getridlistbyid',
            data: 'id=' + row.id,
            success: function (data) {
                row = data;
                $('#userupdate').form('clear');
                $('#userupdate').form('load', row);
                var t;
                if (row.issuper) {
                    t = 'TRUE';
                } else {
                    t = 'FALSE';
                }
                $('#isusuper').combobox('select', t);
                //$('#deptid').combobox('select',row.department.id);
                $('#deptid').combotree('setValue', row.department.id);
                $('#u').window('open');
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}
