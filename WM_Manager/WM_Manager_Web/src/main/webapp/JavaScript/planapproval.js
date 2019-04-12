$(function () {
    var spuserid = $("#spuserid").val();
    var spuser = $("#spuser").val();
    $('#planlist').datagrid({
        url: 'plan/searchsplist',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar1,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'plancode', title: '计划号', width: 150, sortable: true},
            {field: 'plantype', title: '计划大类', width: 100, sortable: true},
            {field: 'zjcode', title: '资金类型', width: 100, sortable: true},
            {field: 'zjname', title: '资金单位', width: 100, sortable: true},
            {field: 'planspmoney', title: '计划审批金额', width: 100, sortable: true},
            {field: 'planname', title: '计划名称', width: 150, sortable: true},
            {field: 'sbdate', title: '计划申报日期', width: 100, sortable: true},
            {field: 'projectcode', title: '工程号', width: 100, sortable: true},
            {field: 'sqrname', title: '申请人', width: 100, sortable: true},
            {field: 'sbunit', title: '申报部门', width: 100, sortable: true},
            {field: 'note', title: '用途说明', width: 100, sortable: true},
            {field: 'spcode', title: 'spcode', hidden: true},
            {
                field: 'spstatus', title: '审批状态', width: 100,
                styler: function (value, row, index) {
                    if (value.indexOf('退回') > 0) {
                        return 'background-color:#FD1C3A;color:white;';
                    }
                    if (value === '审批结束') {
                        return 'background-color:#78C06E;color:white;';
                    }
                    if (value.indexOf('审批') > 0) {
                        return 'background-color:#3399D4;color:white;';
                    }

                }
            }
        ]],
        onClickRow: function (index, row) {
            var plancode = row.plancode;
            var plancodeid = row.id;
            var plantype = row.plantype;
            var planname = row.planname;

            getdictionaryschild(plancodeid);

        }
    });

});

var url1;
var url2;
/*定义数据表格的按钮及事件*/
var toolbar1 = [{
    text: '审批',
    iconCls: 'icon-edit',
    handler: function () {
        var spuser = $("#spuser").val();
        var spuserid = $("#spuserid").val();
        getSelected(spuser, spuserid);

    }
}, '-', {
    text: '审批记录',
    iconCls: 'icon-search',
    handler: function () {
        views();
    }
}];
/*定义数据表格的按钮及事件*/
var toolbar2 = [{
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        //url2 = 'dictionaryschild/update';
        getSelectedDictionaryChild();
    }
}];


//根据大类编码获取子类列表
function getdictionaryschild(plancodeid) {
    $('#planchildlist').datagrid({
        url: 'planlist/search?plancodeid=' + plancodeid,
        rownumbers: true,
        pagination: true,
        //toolbar:toolbar2,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'plancode', title: '计划号', width: 150, sortable: true},
            {field: 'plantype', title: '计划大类', width: 120, sortable: true},
            {field: 'planname', title: '计划名称', width: 120, sortable: true},
            {field: 'wzcode', title: '物资编码', width: 150, sortable: true},
            {field: 'wzname', title: '物资名称', width: 120, sortable: true},
            {field: 'unit', title: '单位', width: 120, sortable: true},
            {field: 'zjcode', title: '资金类型', width: 100, sortable: true},
            {field: 'zjname', title: '资金单位', width: 100, sortable: true},
            {field: 'spprice', title: '计划审批单价', width: 120, sortable: true},
            {field: 'spnum', title: '计划审批数量', width: 120, sortable: true},
            {field: 'spmoney', title: '计划审批金额', width: 120, sortable: true},
            {field: 'note', title: '备注', width: 120, sortable: true}
        ]]
    });
}

/*新增及修改数据字典子类表单提交*/
function submitdictionarychildForm() {
    $('#planlistschild').form('submit', {
        url: url2,
        onSubmit: function () {
            return $('#planlistschild').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url2.indexOf('update') > 0) {

                    $('#planlistschild').form('clear');

                    $('#cw').window('close');
                    $('#planchildlist').datagrid('reload');
                    $("#planlist").datagrid('reload');
                    $.messager.alert('提示信息', '物资明细已成功修改', 'info');
                }
            } else {
                $.messager.alert('提示信息', '登录超时保存失败,请重新登录', 'info');
            }
        }
    });
}


/* 获取选中的数据字典子类 */
function getSelectedDictionaryChild() {
    var row = $('#planchildlist').datagrid('getSelected');

    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'planlist/getallbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url2 = 'planlist/updateforsp';
                //清空表单前获取大类相关值

                $('#planlistschild').form('clear');
                $('#planlistschild').form('load', data);
                $("#planlist").datagrid('reload');
                $('#cw').window('setTitle', '物资明细修改').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

$.extend($.fn.validatebox.defaults.rules, {//添加验证规则
    number: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^\d+(\.\d+)?$/;
            return reg.test(value);
        },
        message: '提示信息:只能输入数字和小数点'
    }
});

/* 审批选中的数据 */
function getSelected(spuser, spuserid) {
    var row = $('#planlist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'plan/getallbyid',
            data: 'id=' + row.id,
            success: function (data) {
                //加载数据 
                $('#planapproval').form('clear');
                $('#planapproval').form('load', data);
                $('#spuser').textbox('setValue', spuser);
                $('#spuserid').textbox('setValue', spuserid);
                $('#planmoney').textbox('setValue', data.planmoney);
                $('#plantype').textbox('setValue', data.plantype);
                $('#sp').window('setTitle', '需求申请审批').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/*同意审批提交*/
function submitagreeform() {
    $('#spresult').textbox('setValue', "同意");
    $('#planapproval').form('submit', {
        url: 'plan/approve',
        onSubmit: function () {

            return $('#planapproval').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                var spuser = $("#spuser").val();
                var spuserid = $("#spuserid").val();
                $('#planapproval').form('clear');
                getSelected(spuser, spuserid);
                $('#sp').window('close');
                $('#planlist').datagrid('reload');
                //$('#planchildlist').datagrid('reload');
                $('#planchildlist').datagrid('loadData', {total: 0, rows: []});
                $.messager.alert('提示信息', '同意审批操成功', 'info');
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/*退回审批提交*/
function submitbackform() {
    $('#spresult').textbox('setValue', "退回");
    $('#planapproval').form('submit', {
        url: 'plan/approve',
        onSubmit: function () {

            return $('#planapproval').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                var spuser = $("#spuser").val();
                var spuserid = $("#spuserid").val();
                $('#planapproval').form('clear');
                getSelected(spuser, spuserid);
                $('#sp').window('close');
                $('#planlist').datagrid('reload');
                //$('#planchildlist').datagrid('reload');
                $('#planchildlist').datagrid('loadData', {total: 0, rows: []});
                $.messager.alert('提示信息', '退回审批操成功', 'info');
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 查看审批记录 */
function views() {
    var row = $('#planlist').datagrid('getSelected');
    if (row) {
        $('#approvallist').datagrid({
            url: 'approvalrecord/getbyspid?spid=' + row.id,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            striped: true,
            pageSize: 10,
            pageList: [10, 20],
            columns: [[
                {field: 'id', title: 'id', hidden: true},
                {field: 'sptime', title: '审批时间', width: 160},
                {field: 'spuser', title: '审批人', width: 150},
                {field: 'spadvice', title: '审批意见', width: 200, sortable: true}
            ]]
        });
        $('#p1').window('setTitle', '需求申请审批记录').window('open');
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 查看详情 */
function viewsall() {
    var row = $('#planchildlist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'planlist/getallbyid',
            data: 'id=' + row.id,
            success: function (data) {
                $('#planlistschild').form('clear');
                $('#planlistschild').form('load', data);
                $("#planlist").datagrid('reload');
                $('#view').window('setTitle', '物资明细查看详情').window('open');
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}