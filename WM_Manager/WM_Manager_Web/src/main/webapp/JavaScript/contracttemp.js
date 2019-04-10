$(function () {
    $('#contracttempnamelist').datagrid({
        url: 'contracttempname/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar1,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', hidden: true},
            {field: 'contractempname', title: '模板名称', width: 150},
            {field: 'introduce', title: '描述', width: 250},
        ]],
        onClickRow: function (index, row) {
            contracttempname = row.contractempname;
            contracttempnameid = row.id;
            $('#contracttempname').textbox('setValue', contracttempname);
            $('#contracttempnameid').textbox('setValue', contracttempnameid);
            getcontractcontent(contracttempnameid);

        },

    });

});

var url1;
var url2;
/*定义数据表格的按钮及事件*/
var toolbar1 = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        url1 = 'contracttempname/create';
        $('#w').window('setTitle', '新增合同模板').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        getSelectedcontracttempname();
        /*setTimeout(function() {
            $('#u').window('setTitle','修改数据字典节点').window('open');
        }, 500);*/

    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedcontracttempname();
    }
}];
/*定义数据表格的按钮及事件*/
var toolbar2 = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        url2 = 'contracttempcontent/create';
        $('#c').window('setTitle', '新增合同条款').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        //url2 = 'dictionaryschild/update';
        getSelectedcontracttempcontent();
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedcontracttempcontent();
    }
}];

/* 合同模板名称搜索方法----------------------------------------------------- */
function tempnamesearch() {
    //var contractDate = $("#contractDate1").val();
    var contracttempname = $("#searchtempname").textbox('getText');

    var url = 'contracttempname/search?&contractempname='
        + contracttempname;

    $('#contracttempnamelist').datagrid('options').url = url;
    $("#contracttempnamelist").datagrid('reload');
}

/*清空搜索条件——合同模板名称-------------------------------------------------*/
function tempnamesearchclean() {
    $("#searchtempname").textbox('reset');
}

/*新增及修改合同模板表单提交*/
function submittempnameform() {
    $('#contracttempnameform').form('submit', {
        url: url1,
        onSubmit: function () {
            return $('#contracttempnameform').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url1.indexOf('update') > 0) {
                    $('#contracttempnameform').form('clear');
                    //清空表单后给大类赋值
                    $('#w').window('close');
                    $('#contracttempnamelist').datagrid('reload');
                    $.messager.alert('提示信息', '合同模板已成功修改', 'info');
                } else {
                    $('#contracttempnameform').form('clear');
                    $('#w').window('close');
                    $('#contracttempnamelist').datagrid('reload');
                    $.messager.alert('提示信息', '合同模板已成功录入', 'info');
                }
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 编辑选中的合同模板信息 */
function getSelectedcontracttempname() {
    var row = $('#contracttempnamelist').datagrid('getSelected');

    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'contracttempname/getdictionarylistbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url1 = 'contracttempname/update';
                /* 加载数据 */
                $('#contracttempnameform').form('clear');
                $('#contracttempnameform').form('load', data);
                $('#w').window('setTitle', '合同模板信息修改').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
}

/* 删除选中的合同模板 */
function deleteSelectedcontracttempname() {
    var row = $('#contracttempnamelist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'contracttempname/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#contracttempnamelist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的合同模板', 'info');
                        } else {
                            alert("登录超时删除失败，请重新登录");
                        }
                    }
                });
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
}


//根据模板名称获取合同条款
function getcontractcontent(id) {
    $('#contractcontent').datagrid({
        url: 'contracttempcontent/search?id=' + id,
        rownumbers: true,
        pagination: true,
        toolbar: toolbar2,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'realtempname', title: '模板名称', width: 120, sortable: true},
            {field: 'sn', title: '条款序号', width: 80, sortable: true},
            {field: 'content', title: '条款内容', width: 720, formatter: formatCellTooltip}
        ]]
    });
}

/*新增及修改合同条款表单提交*/
function submittempcontentForm() {
    $('#tempcontentform').form('submit', {
        url: url2,
        onSubmit: function () {
            return $('#tempcontentform').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url2.indexOf('update') > 0) {
                    //清空表单前获取合同模板相关值
                    var contracttempname = $('#contracttempname').textbox('getValue');
                    var contracttempnameid = $('#contracttempnameid').textbox('getValue');
                    $('#tempcontentform').form('clear');
                    //清空表单后给模板信息赋值
                    $('#contracttempname').textbox('setValue', contracttempname);
                    $('#contracttempnameid').textbox('setValue', contracttempnameid);
                    $('#c').window('close');
                    $('#contractcontent').datagrid('reload');
                    $.messager.alert('提示信息', '合同条款已成功修改', 'info');
                } else {
                    //清空表单前获取模板相关值
                    var contracttempname = $('#contracttempname').textbox('getValue');
                    var contracttempnameid = $('#contracttempnameid').textbox('getValue');
                    $('#tempcontentform').form('clear');
                    //清空表单后给模板赋值
                    $('#contracttempname').textbox('setValue', contracttempname);
                    $('#contracttempnameid').textbox('setValue', contracttempnameid);
                    $('#c').window('close');
                    $('#contractcontent').datagrid('reload');
                    $.messager.alert('提示信息', '合同条款已成功录入', 'info');
                }
            } else {
                $.messager.alert('提示信息', '登录超时保存失败,请重新登录', 'error');
            }
        }

    });
}

/* 获取选中的合同条款 */
function getSelectedcontracttempcontent() {
    var row = $('#contractcontent').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'contracttempcontent/getcontentlistbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url2 = 'contracttempcontent/update';
                //清空表单前获取大类相关值
                var contracttempname = $('#contracttempname').textbox('getValue');
                var contracttempnameid = $('#contracttempnameid').textbox('getValue');
                $('#dictionaryschild').form('clear');
                //清空表单后给大类赋值
                $('#contracttempname').textbox('setValue', contracttempname);
                $('#contracttempnameid').textbox('setValue', contracttempnameid);
                /* 加载数据 */
                $('#tempcontentform').form('load', data);
                $('#c').window('setTitle', '合同条款修改').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
}

/* 删除选中的合同条款 */
function deleteSelectedcontracttempcontent() {
    var row = $('#contractcontent').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'contracttempcontent/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#contractcontent').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选合同条款', 'info');
                        } else {
                            alert("登录超时删除失败，请重新登录");
                        }
                    }
                });
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
}

//格式化单元格提示信息  
function formatCellTooltip(value) {
    return "<span title='" + value + "'>" + value + "</span>";
}  


