$(function () {
    $('#employeelist').datagrid({
        url: 'employee/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'employeenum', title: '员工编号', width: 180, sortable: true},
            {field: 'employeename', title: '员工姓名', width: 120, sortable: true},
            {field: 'phonenum', title: '联系电话', width: 180, sortable: true},
            {field: 'departname', title: '所属部门', width: 120, sortable: true},
        ]]
    });
});

/*定义数据表格按钮及时事件*/
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        url = 'employee/create';
        $('#w').window('setTitle', '新增员工').window('open');
    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        getSelectedemployee();
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedemployee();
    }
}];

var url;

/*搜索方法*/
function employeesearch() {
    var employeenum = $("#searchemployeenum").textbox('getValue');
    var employeename = $("#searchemployeename").textbox('getValue');
    var phonenum = $("#searchphonenum").textbox('getValue');
    var url = 'employee/search?employeenum=' + employeenum + '&employeename=' + employeename + '&phonenum=' + phonenum;
    $('#employeelist').datagrid('options').url = url;
    $("#employeelist").datagrid('reload');
}

/*清空搜索条件*/
function employeesearchclean() {
    $("#searchemployeenum").textbox('reset');
    $("#searchemployeename").textbox('reset');
    $("#searchphonenum").textbox('reset');
}

/*新增数据表单提交*/
function submitemployeeForm() {
    $('#employeeadd').form('submit', {
        url: url,
        onSubmit: function () {
            return $('#employeeadd').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url.indexOf('update') > 0) {
                    $('#employeeadd').form('clear');

                    $('#w').window('close');
                    $('#employeelist').datagrid('reload');
                    $.messager.alert('提示信息', '员工信息已成功修改', 'info');
                } else {
                    $('#employeeadd').form('clear');
                    $('#w').window('close');
                    $('#employeelist').datagrid('reload');
                    $.messager.alert('提示信息', '员工信息已成功录入', 'info');
                }
            } else if (data === "员工编码已存在") {
                $.messager.alert('提示信息', '员工编码已存在请重新输入', 'info');
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });

}

/* 修改选中的仓库数据---------------------------------------------------- */
function getSelectedemployee() {
    var row = $('#employeelist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'employee/getallbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url = 'employee/update';
                /* 加载数据 */
                $('#employeeadd').form('clear');
                $('#employeeadd').form('load', data);
                $('#w').window('setTitle', '员工修改').window('open');

            }

        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的员工----------------------------------------------------- */
function deleteSelectedemployee() {
    var row = $('#employeelist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'employee/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#employeelist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的员工', 'info');
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