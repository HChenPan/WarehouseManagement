$(function () {
    $('#contractbasiclist').datagrid({
        url: 'contractbasic/searchsplist',
        rownumbers: true,
        pagination: true,
//        toolbar:toolbar1,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', hidden: true},
            {field: 'serialsnumber', title: '合同流水号', width: 120},
            {field: 'contracttype', title: '合同类型', width: 120},
            {field: 'summoney', title: '合同金额', width: 120},
            {field: 'startdate', title: '有效起始日期', width: 120, sortable: true},
            {field: 'enddate', title: '有效截止日期', width: 120, sortable: true}

        ]],
        onClickRow: function (index, row) {
            serialsnumber = row.serialsnumber;
            $('#serialsnumber').textbox('setValue', serialsnumber);
            getcontractgoods(serialsnumber);

        },
    });

});

var url1;
var url2;


/* 合同搜索方法----------------------------------------------------- */
function contractsearch() {
    //var contractDate = $("#contractDate1").val();
    var serialsnumber = $("#serialsnumber1").textbox('getText');

    $('#contractbasiclist').datagrid('options').url = 'contractbasic/list?&serialsnumber=' + serialsnumber;
    $("#contractbasiclist").datagrid('reload');
}

/*清空搜索条件——合同-------------------------------------------------*/
function contractsearchclean() {
    $("#serialsnumber1").textbox('reset');
}


/*定义数据表格的按钮及事件*/
var toolbar2 = [{
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


//根据合同获取物资列表
function getcontractgoods(serialsnumber) {
    $('#contractgoodslist').datagrid({
        url: 'contractgoods/list?contractbasicid=' + serialsnumber,
        rownumbers: true,
        pagination: true,
        toolbar: toolbar2,
        singleSelect: true,
        striped: true,
        //clickToEdit:false,
        //dblclickToEdit:true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'buycode', title: '采购计划号', width: 140},
            {field: 'wzname', title: '物资名称', width: 150},
            {field: 'wzcode', title: '物资编码', width: 180},
            {field: 'unit', title: '单位', width: 80},
            {field: 'buyprice', title: '计划采购单价', width: 110},
            {field: 'buymoney', title: '计划采购金额', width: 110},
            {field: 'buynum', title: '计划采购数量', width: 110},
            {field: 'planbum', title: '实际数量', width: 110},
            {field: 'planprice', title: '实际单价', width: 110},

        ]]
    });/*.datagrid('enableCellEditing').datagrid('gotoCell', {
    	index: 0,
    	field: 'buycode'
    });*/


}


/* 审批选中的数据 */
function getSelected(spuser, spuserid) {
    var row = $('#contractbasiclist').datagrid('getSelected');

    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'contractbasic/getbyid',
            data: 'id=' + row.id,
            success: function (data) {
                //加载数据
                $('#contractapproval').form('clear');
                $('#contractapproval').form('load', data);
                $('#spuser').textbox('setValue', spuser);
                $('#spuserid').textbox('setValue', spuserid);
//				$('#planmoney').textbox('setValue',data.planmoney);
//				$('#plantype').textbox('setValue',data.plantype);
                $('#sp').window('setTitle', '合同审批').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/*同意审批提交*/
function submitagreeform() {
    $('#spresult').textbox('setValue', "同意");
    $('#contractapproval').form('submit', {
        url: 'contractbasic/approve',
        onSubmit: function () {

            return $('#contractapproval').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                $('#contractapproval').form('clear');
                $('#sp').window('close');
                $('#contractbasiclist').datagrid('reload');
                $('#contractgoodslist').datagrid('reload');
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
    $('#contractapproval').form('submit', {
        url: 'contractbasic/approve',
        onSubmit: function () {

            return $('#contractapproval').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                $('#contractapproval').form('clear');
                $('#sp').window('close');
                $('#contractbasiclist').datagrid('reload');
                $('#contractgoodslist').datagrid('reload');
                $.messager.alert('提示信息', '退回审批操成功', 'info');
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}


/* 查看审批记录 */
function views() {
    var row = $('#contractbasiclist').datagrid('getSelected');
    if (row) {
        $('#approvallist').datagrid({
            url: 'approvalrecord/getbyspid?&spid=' + row.id,
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
                {field: 'spadvice', title: '审批意见', width: 200, sortable: true},
            ]]
        });
        $('#p2').window('setTitle', '合同审批记录').window('open');
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}