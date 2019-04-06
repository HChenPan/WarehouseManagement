$(function () {
    $('#sptypesplevellist').datagrid({
        url: 'sptypesplevel/search',
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
            {field: 'splevelcode', title: '审批级别编码', width: 120, sortable: true},
            {field: 'splevelname', title: '审批级别描述', width: 120, sortable: true},
            {field: 'spuserszw', title: '审批人', width: 200, sortable: true},
            {field: 'spnote', title: '审批级别备注', width: 200, sortable: true}
        ]]
    });
});

var url;
/*定义数据表格的按钮及事件*/
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#sptypesplevel').form('clear');
        setrequiredoptions();
        url = 'sptypesplevel/create';
        $('#w').window('setTitle', '新增审批类型级别配置').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        //清空表单
        $('#sptypesplevel').form('clear');
        setrequiredoptions();
        getSelectedSptypeSplevel();
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedDictionary();
    }
}];


/*新增及修改数据字典大类表单提交*/
function submitsptypesplevelform() {
    $('#sptypesplevel').form('submit', {
        url: url,
        onSubmit: function () {
            return $('#sptypesplevel').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url.indexOf('update') > 0) {
                    $('#sptypesplevel').form('clear');
                    $('#w').window('close');
                    $('#sptypesplevellist').datagrid('reload');
                    $.messager.alert('提示信息', '审批类型级别配置已成功修改', 'info');
                } else {
                    $('#sptypesplevel').form('clear');
                    $('#w').window('close');
                    $('#sptypesplevellist').datagrid('reload');
                    $.messager.alert('提示信息', '审批类型级别配置已成功录入', 'info');
                }
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 编辑选中的数据字典 */
function getSelectedSptypeSplevel() {
    var row = $('#sptypesplevellist').datagrid('getSelected');

    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'sptypesplevel/getsptypesplevellistbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url = 'sptypesplevel/update';
                /* 加载数据 */
                $('#sptypesplevel').form('clear');
                $('#sptypesplevel').form('load', data);
                if (data.user !== undefined)
                    $('#userid').combobox('select', data.user.id);
                $('#w').window('setTitle', '字典大类修改').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的保产记录 */
function deleteSelectedDictionary() {
    var row = $('#sptypesplevellist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'sptypesplevel/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#sptypesplevellist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的审批配置', 'info');
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

function setrequiredoptions() {
    $("#splevelcode").combobox({
        onSelect: function (rec) {
            if (rec.code !== "00" && rec.code !== "99" && rec.code !== "TH") {
                $('#spusersId').textbox({required: true});
            } else {
                $('#spusersId').textbox({required: false});
            }
        }
    });
}


function declareunitsearch() {
    var code = $("#sptypecode1").combobox('getValue');

    var url = 'sptypesplevel/search?&sptypecode=' + code;
    $('#sptypesplevellist').datagrid('options').url = url;
    $("#sptypesplevellist").datagrid('reload');
}

function declareunitsearchclean() {
    $("#sptypecode1").combobox('reset');

}