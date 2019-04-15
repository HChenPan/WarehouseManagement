var stockcode;
var callslipcode;
var userid = $("#userid").val();
$(function () {
    $('#callsliplist').datagrid({
        url: 'callslip/searchsplist',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar1,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'callslipcode', title: '领料单号', width: 150},
            {field: 'callsliptype', title: '领料大类', width: 150},
            {field: 'storehousename', title: '领料仓库', width: 120},
            {field: 'department', title: '部门', width: 120},
            {field: 'applydate', title: '申请时间', width: 120},
            {field: 'application', title: '用途', width: 120},
            {field: 'projectno', title: '工程号', width: 140},
            {field: 'projectname', title: '工程名称', width: 320},
            {field: 'status', title: '状态', width: 100},
            {field: 'realname', title: '申请人', width: 120},
//            {field:'outusername',title:'出库人',width:120},
//            {field:'outtime',title:'出库时间',width:120},
            {field: 'note', title: '备注', width: 120},
        ]],

        onClickRow: function (index, row) {
            callslipcode = row.callslipcode;
            stockcode = row.storehouse;
            $('#callslipcode2').textbox('setValue', callslipcode);
            $('#callslipcode3').textbox('setValue', callslipcode);
            $('#stockcode2').val(stockcode);
            $('#stockcode3').val(stockcode);
            getdictionaryschild(callslipcode);

        },
    });

});


var url;
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

//根据大类编码获取子类列表
function getdictionaryschild(callslipcode) {
//	$('#stockcode2').val(stockcode);
    $('#callslipgoodslist').datagrid({
        url: 'callslipgoods/list?callslipcode=' + callslipcode,
        rownumbers: true,
        pagination: true,
//      toolbar:toolbar2,
        singleSelect: true,
//      onClickCell: onClickCell,
//		onEndEdit: onEndEdit,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'wzcode', title: '物资编码', width: 150, sortable: true},
            {field: 'wzname', title: '物资名称', width: 150, sortable: true},
            {field: 'modelspcification', title: '型号规格', width: 300, sortable: true},
            {field: 'unit', title: '单位', width: 120, sortable: true},
//          {field:'zjcode',title:'资金类型',width:120,sortable:true},
//          {field:'zjname',title:'资金单位',width:120,sortable:true},
            {field: 'sum', title: '领料总数', width: 120, sortable: true},
            {field: 'comefrom', title: '物资来源', width: 120, sortable: true},

        ]]
    });
}


/*清空搜索条件-------------------------------------------------*/
function searchclean() {
    $("#callslipcode1").textbox('reset');
    $("#callsliptype1").combobox('clear');
    $('#datetime1').combo('setText', '');
    $('#datetime2').combo('setText', '');
}


/* 搜索方法----------------------------------------------------- */
function callslipsearch() {
    //var contractDate = $("#contractDate1").val();
    var callslipcode = $("#callslipcode1").textbox('getText');
    var callsliptype = $("#callsliptype1").combobox('getValue');
    var datetime1 = $('#datetime1').datebox('getText');
    var datetime2 = $('#datetime2').datebox('getText');

    var url = 'callslip/list?&callslipcode='
        + callslipcode + '&callsliptype='
        + callsliptype + '&datetime1=' + datetime1 + '&datetime2=' + datetime2;

    $('#callsliplist').datagrid('options').url = url;
    $("#callsliplist").datagrid('reload');
//	$("#callslipgoodslist").datagrid('reload');
    $('#callslipgoodslist').datagrid('loadData', {total: 0, rows: []});
}

/* 审批选中的数据 */
function getSelected(spuser, spuserid) {
    var row = $('#callsliplist').datagrid('getSelected');

    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'callslip/getbyid',
            data: 'id=' + row.id,
            success: function (data) {
                //加载数据 
                $('#callslipapproval').form('clear');
                $('#callslipapproval').form('load', data);
                $('#spuser').textbox('setValue', spuser);
                $('#spuserid').textbox('setValue', spuserid);
//				$('#planmoney').textbox('setValue',data.planmoney);
//				$('#plantype').textbox('setValue',data.plantype);
                $('#sp').window('setTitle', '领料单审批').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/*同意审批提交*/
function submitagreeform() {
    $('#spresult').textbox('setValue', "同意");
    $('#callslipapproval').form('submit', {
        url: 'callslip/approve',
        onSubmit: function () {

            return $('#callslipapproval').form('validate');
        },
        success: function (data) {
            if (data === "success") {
//					$('#callslipapproval').form('clear');
                $("#callslipcode1").textbox('reset');
                $('#sp').window('close');
                $('#callsliplist').datagrid('reload');
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
    $('#callslipapproval').form('submit', {
        url: 'callslip/approve',
        onSubmit: function () {

            return $('#callslipapproval').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                $('#callslipapproval').form('clear');
                $('#sp').window('close');
                $('#callsliplist').datagrid('reload');
                $('#callsliplist').datagrid('reload');
                $.messager.alert('提示信息', '退回审批操成功', 'info');
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}


/* 查看审批记录 */
function views() {
    var row = $('#callsliplist').datagrid('getSelected');
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




