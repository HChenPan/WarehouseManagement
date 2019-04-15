$(function () {
    $('#workflowlist').datagrid({
        url: 'workflow/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', hidden: true},
            {field: 'sptypecode', title: '审批类型编码', width: 150, sortable: true},
            {field: 'sptypename', title: '审批类型描述', width: 220, sortable: true},
            {field: 'spnode', title: '审批级别编码', width: 100, sortable: true},
            {field: 'spnodename', title: '审批级别描述', width: 120, sortable: true},
            {field: 'spmoneylowlimit', title: '审批金额下限', width: 120, sortable: true},
            {field: 'spmoneyuplimit', title: '审批金额上限', width: 120, sortable: true},
            {field: 'nextnode', title: '下一级审批级别编码', width: 150, sortable: true},
            {field: 'backnode', title: '回退审批级别编码', width: 120, sortable: true},
        ]],
    });
    comboEvent();
});

var url;
/*定义数据表格的按钮及事件*/
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#workflow').form('clear');
        url1 = 'workflow/create';
        $('#w').window('setTitle', '新增数据审批流程').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        geteditSelectedWorkFlow();
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedWorkFlow();
    }
}];


/*新增及修改数据字典大类表单提交*/
function submitworkflowform() {
    $('#workflow').form('submit', {
        url: url1,
        onSubmit: function () {
            return $('#workflow').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url1.indexOf('update') > 0) {
                    $('#workflow').form('clear');
                    $('#w').window('close');
                    $('#workflowlist').datagrid('reload');
                    $.messager.alert('提示信息', '数据字典节点已成功修改', 'info');
                } else {
                    $('#workflow').form('clear');
                    $('#w').window('close');
                    $('#workflowlist').datagrid('reload');
                    $.messager.alert('提示信息', '数据字典节点已成功录入', 'info');
                }
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 编辑选中的数据字典 */
function geteditSelectedWorkFlow() {
    var row = $('#workflowlist').datagrid('getSelected');

    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'workflow/getworkflowlistbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url1 = 'workflow/update';
                /* 加载数据 */
                $('#workflow').form('clear');
                $('#workflow').form('load', data);
                $('#w').window('setTitle', '字典大类修改').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的工作流程 */
function deleteSelectedWorkFlow() {
    var row = $('#workflowlist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'workflow/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#workflowlist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的工作流程', 'info');
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


//根据大类编码获取子类列表
function getdictionaryschild(dcode) {
    $('#dictionaryschildlist').datagrid({
        url: 'dictionaryschild/search?dcode=' + dcode,
        rownumbers: true,
        pagination: true,
        toolbar: toolbar2,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'dname', title: '字典大类', width: 150, sortable: true},
            {field: 'code', title: '子类编码', width: 150, sortable: true},
            {field: 'name', title: '子类描述', width: 120, sortable: true},
            {field: 'note', title: '备注', width: 120, sortable: true},
            {field: 'updatetime', title: '更新时间', width: 200},
        ]]
    });
}

/*新增及修改数据字典子类表单提交*/
function submitdictionarychildForm() {
    $('#dictionaryschild').form('submit', {
        url: url2,
        onSubmit: function () {
            return $('#dictionaryschild').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url2.indexOf('update') > 0) {
                    $('#dictionaryschild').form('clear');
                    $('#c').window('close');
                    $('#dictionaryschildlist').datagrid('reload');
                    $.messager.alert('提示信息', '数据字典子类已成功修改', 'info');
                } else {
                    $('#dictionaryschild').form('clear');
                    $('#c').window('close');
                    $('#dictionaryschildlist').datagrid('reload');
                    $.messager.alert('提示信息', '数据字典子类已成功录入', 'info');
                }
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/*新增、修改时联动控制*/
function comboEvent() {
    $("#sptypecode").combobox(
        {
            onSelect: function (rec) {
                var url = 'sptypesplevel/getspjblistbysptypecode?sptypecode=' + rec.code;
                $('#spnode').combobox('clear');
                $('#spnode').combobox('reload', url);
                $('#nextnode').combobox('clear');
                $('#nextnode').combobox('reload', url);
                $('#backnode').combobox('clear');
                $('#backnode').combobox('reload', url);
            }
        });

}

function declareunitsearch() {
    var code = $("#sptypecode1").combobox('getValue');

    var url = 'workflow/search?&sptypecode=' + code;
    $('#workflowlist').datagrid('options').url = url;
    $("#workflowlist").datagrid('reload');
}

function declareunitsearchclean() {
    $("#sptypecode1").combobox('reset');

}