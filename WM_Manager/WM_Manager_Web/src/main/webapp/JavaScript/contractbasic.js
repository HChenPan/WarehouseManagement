var contractbasicid;
var spcode;

$(function () {
    $('#contractbasiclist').datagrid({
        url: 'contractbasic/list',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'serialsnumber', title: '合同流水号', width: 120},
            {field: 'contractid', title: '合同号', width: 120},
            {field: 'contracttype', title: '合同类型', width: 120},
            {field: 'contractmethod', title: '签订方式', width: 100},
            {field: 'paymentmethod', title: '付款方式', width: 100},
            {field: 'contractarea', title: '签订地点', width: 140},
            {field: 'summoney', title: '合同总价', width: 100},
            {field: 'startdate', title: '有效起始日期', width: 120, sortable: true},
            {field: 'enddate', title: '有效截止日期', width: 120, sortable: true},
            {field: 'freight', title: '运费承担', width: 120},
            {field: 'contracttax', title: '合同税率', width: 100},
            {field: 'venditorname', title: '出卖人名称', width: 150},
            {field: 'buyername', title: '买受人名称', width: 150},
            {field: 'contractstatus', title: '合同状态', width: 100},
            {field: 'auditingstatus', title: '审批状态', width: 100},
            {field: 'creatusername', title: '创建人', width: 80},
            {field: 'creattime', title: '合同创建时间', width: 150},

        ]],

        rowStyler: function (index, row) {
            if (row.auditingstatus === '一级审批退回' || row.auditingstatus === '二级审批退回' || row.auditingstatus === '三级审批退回') {
                return 'background-color:#FD1C3A;color:white;';
            } else if (row.auditingstatus === '一级审批' || row.auditingstatus === '二级审批' || row.auditingstatus === '三级审批') {
                return 'background-color:#3399D4;color:white;';
            } else if (row.auditingstatus === '审批结束') {
                return 'background-color:#78C06E;color:white;';
            }
        }
    });

    $("#contracttypeId").combobox(
        {
            onSelect: function (rec) {
                $('#contracttypecode').val(rec.code);
            }
        });
    $("#contractauditingtype").combobox(
        {
            onSelect: function (rec) {
                $('#contractauditingtypecode').val(rec.code);
                $('#contractauditingtypename').val(rec.name);
            }
        });
    $("#venditorid").combogrid(
        {
            onBeforeSelect: function (rowIndex, rowData) {
                $('#venditorname').val(rowData.suppliername);
                $('#venditorcode').textbox("setValue", rowData.suppliercode);
                $('#venditoraddress').textbox("setValue", rowData.address);
                $('#venditorfr').textbox("setValue", rowData.legalrepresentative);
                $('#venditorphone').textbox("setValue", rowData.phone);
            }
        });

    $("#buyerid").combogrid(
        {
            onBeforeSelect: function (rowIndex, rowData) {
                $('#buyername').val(rowData.buyername);
                $('#buyercode').textbox("setValue", rowData.buyercode);
                $('#buyeraddress').textbox("setValue", rowData.address);
                $('#buyerfr').textbox("setValue", rowData.legalrepresentative);
                $('#buyerphone').textbox("setValue", rowData.phone);
            }
        });

    $('#addtempterms').datagrid({
        rownumbers: true,
        pagination: true,
//	    url:'stock/getgoodslist?stockcode='+stockcode,
        singleSelect: false, //允许选择多行
        selectOnCheck: true,//true勾选会选择行，false勾选不选择行
        checkOnSelect: true, //
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', width: 30, checkbox: true},
            {field: 'sn', title: '序号', width: 50},
            {field: 'content', title: '条款内容', width: 1500},
        ]]
    });

});

var url;
/*定义数据表格的按钮及事件*/
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#contractbasicadd').form('clear');
        /*定义提交方法*/
        url = 'contractbasic/create';
        $('#w').window('setTitle', '合同基本信息录入').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        setTimeout(function () {
            getSelectcontract()
        }, 500);
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectcontract()
    }
}, '-', {
    text: '合同条款',
    iconCls: 'icon-filter',
    handler: function () {
        var row = $('#contractbasiclist').datagrid('getSelected');

        $('#termsform').form('clear');

        if (row) {
            $('#serialsnumber2').textbox("setValue", row.serialsnumber);
            contractbasicid = row.serialsnumber;
            spcode = row.spcode;

            if (row.spcode !== '00') {
                $("#daoru").linkbutton({disabled: true});
                $("#contracttemp1").combobox({disabled: true});
            } else {

                $("#daoru").linkbutton({disabled: false});
                $("#contracttemp1").combobox({disabled: false});
            }

            content();
            $('#addtempterms').datagrid('loadData', {total: 0, rows: []});
            $('#c').window('setTitle', '合同条款录入').window('maximize').window('open');


        } else {
            $.messager.alert('提示信息', '请选择一行！', 'warning');
        }
    }
}, '-', {
    text: '上报',
    iconCls: 'icon-redo',
    handler: function () {
        sendSelected();
    }
}, '-', {
    text: '审批记录',
    iconCls: 'icon-search',
    handler: function () {
        views();
    }
}, '-', {
    text: '编写合同号',
    iconCls: 'icon-add',
    handler: function () {
        getcontractid();
    }
}];


/*清空搜索条件-------------------------------------------------*/
function contractsearchclean() {
    $("#serialsnumber1").textbox('reset');
    $("#contracttype1").combobox('reset');
    $('#datetime1').combo('setText', '');
    $('#datetime2').combo('setText', '');
}


/* 搜索方法----------------------------------------------------- */
function contractsearch() {
    //var contractDate = $("#contractDate1").val();
    var serialsnumber = $("#serialsnumber1").textbox('getText');
    var contracttype = $("#contracttype1").combobox('getValue');
    var datetime1 = $('#datetime1').datebox('getText');
    var datetime2 = $('#datetime2').datebox('getText');

    var url = 'contractbasic/list?&serialsnumber='
        + serialsnumber + '&contracttype='
        + contracttype + '&datetime1=' + datetime1 + '&datetime2=' + datetime2;

    $('#contractbasiclist').datagrid('options').url = url;
    $("#contractbasiclist").datagrid('reload');
}


/*新增及修改合同表单提交----------------------------------------------------*/
function submitcontractbasicForm() {
    $('#contractbasicadd').form('submit', {
        url: url,
        onSubmit: function () {
//			setrequiredoptions();
            return $('#contractbasicadd').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url.indexOf('update') > 0) {
                    $('#contractbasicadd').form('clear');
                    $('#w').window('close');
                    $('#contractbasiclist').datagrid('reload');
                    $.messager.alert('提示信息', '合同已成功修改', 'info');
                } else {
                    $('#contractbasicadd').form('clear');
                    $('#w').window('close');
                    $('#contractbasiclist').datagrid('reload');
                    $.messager.alert('提示信息', '合同已成功录入', 'info');
                }
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 修改选中的合同数据---------------------------------------------------- */
function getSelectcontract() {
    var row = $('#contractbasiclist').datagrid('getSelected');
    if (row) {
        if (row.spcode === "00") {
            $.ajax({
                async: false,
                dataType: 'json',
                url: 'contractbasic/getbyid',
                data: 'id=' + row.id,
                success: function (data) {
                    /* 定义提交方法 */
                    url = 'contractbasic/update';
                    /* 加载数据 */
                    $('#contractbasicadd').form('clear');
                    $('#contractbasicadd').form('load', data);
                    $('#w').window('setTitle', '合同基本信息修改').window('open');

                }
            });
        } else {
            $.messager.alert('提示信息', '合同已经进入审批阶段，无法修改！', 'error');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
}

/* 删除选中的合同----------------------------------------------------- */
function deleteSelectcontract() {
    var row = $('#contractbasiclist').datagrid('getSelected');
    if (row) {
        if (row.spcode === "00") {
            $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'contractbasic/delete',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $('#contractbasiclist').datagrid('reload');
                                $.messager.alert('提示信息', '成功删除所选的合同', 'info');
                            } else if (data === "有物资") {
                                $.messager.alert('提示信息', '该合同下面有物资信息，无法删除！', 'error');
                            } else {
                                alert("登录超时删除失败，请重新登录");
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '合同已经进入审批阶段，无法删除！', 'error');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
}


/*查看详情==================================================================================*/
function viewSelected() {
    var row = $('#contractlist').datagrid('getSelected');
    $.ajax({
        async: false,
        url: 'contractmanage/getbyid',
        data: 'id=' + row.id,
        dataType: 'json',
        success: function (data) {
            /*加载获取数据*/
            $('#detailform').form('clear');
            $('#detailform').form('load', data);

            /*文件表*/
            $('#filelistview').datagrid({
                rownumbers: true,
                singleSelect: true,
                toolbar: fileviewtoolbar,
                striped: true,
                columns: [[
                    {field: 'id', hidden: true},
                    {field: 'mainid', hidden: true},
                    {field: 'filepath', hidden: true},
                    {field: 'filename', title: '文件名', width: 400},
                    {field: 'createtime', title: '上传时间', width: 180}
                ]]
            });
            $('#filelistview').datagrid('options').url = 'document/list?mainid=' + row.id;
            $("#filelistview").datagrid('reload');

            setTimeout(function () {
                /*延时打开窗口*/
                $('#detail').window('setTitle', '查看详情').window('open');
            }, 300);

        }
    });
}

/* 上报所选数据 */
function sendSelected() {
    var row = $('#contractbasiclist').datagrid('getSelected');
    if (row) {
        if (row.spcode === "00" && row.summoney !== "0.00") {
            $.messager.confirm('提示信息', '确定上报此合同？上报后合同信息无法修改！', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'contractbasic/send',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $.messager.alert('提示信息', '成功上报合同审批', 'info');
                            } else if (data === "未添加合同条款") {
                                $.messager.alert('提示信息', '未添加合同条款，请添加后再上报审批！', 'error');
                            }
                            $('#contractbasiclist').datagrid('reload');
                        }
                    });
                }
            });
        } else if (row.auditingstatus.indexOf('退回') > 0) {
            $.messager.confirm('提示信息', '确定再次上报此合同?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'contractbasic/send',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $.messager.alert('提示信息', '成功上报合同审批', 'info');
                            } else if (data === "未添加合同条款") {
                                $.messager.alert('提示信息', '未添加合同条款，请添加后再上报审批！', 'error');
                            }
                            $('#contractbasiclist').datagrid('reload');
                        }
                    });
                }
            });
        } else if (row.summoney = "0.00") {
            $.messager.alert('提示信息', '金额为0的合同无法上报审批！', 'error');
        } else {
            $.messager.alert('提示信息', '该合同已上报，请勿重复操作！', 'error');
        }
    } else {

        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
}

function content() {
    $("#contracttemp1").combobox(
        {
            onSelect: function (row) {

                var url = 'contracttempcontent/search?id=' + row.id;
                $('#addtempterms').datagrid('options').url = url;
                $("#addtempterms").datagrid('reload');
            },
        });


    $('#addterms').datagrid({
        rownumbers: true,
        pagination: true,
        toolbar: toolbar2,
        url: 'contractterms/search?contractbasicid=' + contractbasicid,
        striped: true,
        singleSelect: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', width: 30, hidden: true},
            {field: 'sn', title: '序号', width: 50},
            {field: 'content', title: '条款内容', width: 1500},
        ]]
    });
}

var url2;
/*定义数据表格的按钮及事件*/
var toolbar2 = [{
    text: '单条新增',
    iconCls: 'icon-add',
    handler: function () {
//		$('#contractbasicadd').form('clear');
        /*定义提交方法*/
        if (spcode === "00") {
            url2 = 'contractterms/create';
            $('#serialsnumber3').textbox("setValue", contractbasicid);
            $('#d').window('setTitle', '合同条款录入').window('open');
        } else {
            $.messager.alert('提示信息', '只有草稿状态才可以新增合同条款！', 'error');
        }
    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        setTimeout(function () {
            getSelectedcontractcontent()
        }, 500);
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedcontent();
    }

}];

/*批量导入模板*/
function submittermsForm() {

    //获取更新更改的行的集合
    var row = $("#addtempterms").datagrid('getSelections');

    //DataGrid的更该行为不为0
    if (row.length) {
        $.ajax(
            {
                type: 'POST',
                url: "contractterms/creates",
                data: {arrayList: JSON.stringify(row), contractbasicid: contractbasicid},
//                 data : 'id=' + row.id,
                success: function (data) {
                    if (data === "success") {
                        $.messager.alert('提示', '合同条款导入成功！', 'info');
//                         $('#c').window('close');
                        $('#addtempterms').datagrid('reload');
                        $('#addterms').datagrid('reload');    // 重新载入当前页面数据  
                    }
                }
            });
    } else {

        $.messager.alert('提示信息', '请至少选择一行入库物资信息，请联系管理员！', 'warning');
        $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
    }

}

/*新增及修改合同条款表单提交*/
function submitcontentForm() {
    $('#contentform').form('submit', {
        url: url2,
        onSubmit: function () {
            return $('#contentform').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url2.indexOf('update') > 0) {
                    $('#d').window('close');
                    $('#addterms').datagrid('reload');
                    $('#contentform').form('clear');
                    $.messager.alert('提示信息', '合同条款已成功修改', 'info');
                } else {
                    $('#d').window('close');
                    $('#addterms').datagrid('reload');
                    $('#contentform').form('clear');
                    $.messager.alert('提示信息', '合同条款已成功录入', 'info');
                }
            } else {
                $.messager.alert('提示信息', '登录超时保存失败,请重新登录', 'error');
            }
        }

    });
}

/* 获取选中的合同条款 */
function getSelectedcontractcontent() {
    var row = $('#addterms').datagrid('getSelected');
    if (row) {
        if (spcode === "00") {
            $.ajax({
                async: false,
                dataType: 'json',
                url: 'contractterms/getcontentlistbyid',
                data: 'id=' + row.id,
                success: function (data) {
                    /* 定义提交方法 */
                    url2 = 'contractterms/update';
                    $('#contentform').form('clear');
                    /* 加载数据 */
                    $('#contentform').form('load', data);
                    $('#d').window('setTitle', '合同条款修改').window('open');
                }
            });
        } else {
            $.messager.alert('提示信息', '只有草稿状态才可以修改合同条款！', 'error');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
}

/* 删除选中的合同条款 */
function deleteSelectedcontent() {
    var row = $('#addterms').datagrid('getSelected');

    if (row) {
        if (spcode === "00") {
            $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'contractterms/delete',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $('#addterms').datagrid('reload');
                                $.messager.alert('提示信息', '成功删除所选的合同条款', 'info');
                            } else {

                                alert("登录超时删除失败，请重新登录");
                            }
                        }

                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '只有草稿状态才可以删除合同条款！', 'error');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
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
                {field: 'spresult', title: '审批结果', width: 150},
                {field: 'spadvice', title: '审批意见', width: 200, sortable: true},
            ]]
        });
        $('#p2').window('setTitle', '合同审批记录').window('open');
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 获取选中的合同信息 编写合同号---------------------------------------------------- */
function getcontractid() {
    var row = $('#contractbasiclist').datagrid('getSelected');
    if (row) {
        if (row.spcode === "99") {
            $.ajax({
                async: false,
                dataType: 'json',
                url: 'contractbasic/getbyid',
                data: 'id=' + row.id,
                success: function (data) {
                    /* 定义提交方法 */
                    url = 'contractbasic/createcontractid';
                    /* 加载数据 */
                    $('#contentidform').form('clear');
                    $('#contentidform').form('load', data);
                    $('#f').window('setTitle', '编写合同号').window('open');

                }
            });
        } else {
            $.messager.alert('提示信息', '合同审核通过后才可以编写合同号！', 'error');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
}


/*合同号编写提交----------------------------------------------------*/
function submitcontentidForm() {
    $('#contentidform').form('submit', {
        url: url,
        onSubmit: function () {
//			setrequiredoptions();
            return $('#contentidform').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                $('#contentidform').form('clear');
                $('#f').window('close');
                $('#contractbasiclist').datagrid('reload');
                $.messager.alert('提示信息', '合同号编写成功！', 'info');

            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}
