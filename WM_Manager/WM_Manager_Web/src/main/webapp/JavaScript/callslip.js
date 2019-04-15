var stockcode;
var callslipcode;
var userid = $("#userid").val();
$(function () {
    $('#callsliplist').datagrid({
        url: 'callslip/list',
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
            {
                field: 'status', title: '状态', width: 100,
                styler: function (value, row, index) {

                    if (value === '已出库') {
                        return 'background-color:#78C06E;color:white;';
                    }
                    if (value.indexOf('正在出库') >= 0) {
                        return 'background-color:#3399D4;color:white;';
                    }
                }

            },
//            {field:'realname',title:'申请人',width:120},
            {field: 'outusername', title: '出库人', width: 120},
            {field: 'outtime', title: '出库时间', width: 120},
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


    $("#callsliptype").combobox(
        {
            onSelect: function (rec) {
                $('#llcode').val(rec.code);
            }
        });

    $("#projectno").combogrid(
        {
            onSelect: function (rowIndex, rowData) {
                $("#projectname").textbox("setValue", rowData.projectname);
            }
        });

//	根据需求单选择物资
    $('#addgoodslist2').datagrid({
        rownumbers: true,
//	    pagination:true,
        singleSelect: false, //允许选择多行
        selectOnCheck: true,//true勾选会选择行，false勾选不选择行
        checkOnSelect: true, //
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', width: 30, checkbox: true},
            {field: 'contractbasicid', title: '合同号', width: 130},
            {field: 'buycode', title: '采购计划号', width: 130},
            {field: 'plancode', title: '需求计划号', width: 130},
            {field: 'wzcode', title: '物资编码', width: 150},
            {field: 'wzname', title: '物资名称', width: 150},
            {field: 'unit', title: '单位', width: 80},
            {field: 'sycknum', title: '剩余出库数量', width: 120},
        ]]
    });


});


var url;
//定义数据表格的按钮及事件
var toolbar1 = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#callslipadd').form('clear');
//		定义提交方法
        url = 'callslip/create';
        $('#applydate').datebox('setValue', 'today');
        var departmentid = $("#departmentid").val();
        $('#department').combotree('setValue', departmentid);
        $('#w').window('setTitle', '领料单信息录入').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        setTimeout(function () {
            getSelectcallslip()
        }, 500);
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectcallslip()
    }
}, '-', {
    text: '提交',
    iconCls: 'icon-redo',
    handler: function () {
        sendSelected();
    }
}];

//根据大类编码获取子类列表
function getdictionaryschild(callslipcode) {
//	$('#stockcode2').val(stockcode);
    $('#callslipgoodslist').datagrid({
        url: 'callslipgoods/list?callslipcode=' + callslipcode,
        rownumbers: true,
        pagination: true,
        toolbar: toolbar2,
        singleSelect: true,
        onClickCell: onClickCell,
        onEndEdit: onEndEdit,
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
            {
                field: 'sum',
                title: '领料总数',
                width: 120,
                sortable: true,
                editor: {type: 'numberbox', options: {required: true, precision: 2}}
            },
            {field: 'comefrom', title: '物资来源', width: 120, sortable: true},

        ]]
    });
}

/*定义数据表格的按钮及事件*/
var toolbar2 = [{
    text: '从库存表新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#addgoodslist').datagrid('clearChecked');
        $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
        $('#addgoodslist').datagrid('removeFilterRule'); // 移除过滤规则
        $('#addgoodslist').datagrid({
            rownumbers: true,
//		    pagination:true,
            url: 'stock/getgoodslist?stockcode=' + stockcode,
            singleSelect: false, //允许选择多行
            selectOnCheck: true,//true勾选会选择行，false勾选不选择行
            checkOnSelect: true, //
            striped: true,
            pageSize: 10,
            idField: 'id',
            pageList: [10, 20],
            columns: [[
                {field: 'id', width: 30, checkbox: true},
                {field: 'wzcode', title: '物资编码', width: 150},
                {field: 'wzname', title: '物资名称', width: 150},
                {field: 'modelspcification', title: '型号规格', width: 300},
                {field: 'unit', title: '单位', width: 100},
                {field: 'price', title: '单价', width: 100},
                {field: 'bqend', title: '库存数量', width: 100},

            ]]
        });
        $('#addgoodslist').datagrid('enableFilter'); // 启用过滤
//		comboevent();
        //comgrid();
        $('#c').window('setTitle', '新增物资明细').window('maximize').window('open');

    }

//需求单新增
}, '-', {
    text: '从需求单新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#addgoodslist2').datagrid('loadData', {total: 0, rows: []});
        $('#addgoodslist2').datagrid('clearChecked');
//		$('#addgoodslist2').datagrid('destroyFilter'); // 销毁过滤
        $('#addgoodslist2').datagrid('removeFilterRule'); // 移除过滤规则
        //url2 = 'dictionaryschild/update';
//		加载需求计划号
        $('#plancode').combogrid({
            required: true,
            multiple: true,
            panelWidth: 570,
            idField: 'plancode',
            textField: 'plancode',
            url: 'plan/getplanbyuserid',
            columns: [[
                {field: 'plancode', title: '需求计划号', width: 140},
                {field: 'planname', title: '计划大类', width: 120},
                {field: 'planmoney', title: '计划总金额', width: 100},
                {field: 'sbunit', title: '上报部门', width: 120},
                {field: 'note', title: '备注', width: 150},

            ]]
        });
        comgrid();
        $('#addgoodslist2').datagrid('enableFilter'); // 启用过滤
        $('#d').window('setTitle', '新增物资明细').window('maximize').window('open');
        $('#addgoodslist2').datagrid('loadData', {total: 0, rows: []});
    }
}, '-', {
    text: '保存',
    iconCls: 'icon-save',
    handler: function () {
        //url2 = 'dictionaryschild/update';
        save();

    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedgoods();
    }
}];


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


/*新增及修改领料单表单提交----------------------------------------------------*/
function submitcallslipForm() {
    $('#callslipadd').form('submit', {
        url: url,
        onSubmit: function () {
//			setrequiredoptions();
            return $('#callslipadd').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url.indexOf('update') > 0) {
                    $('#callslipadd').form('clear');
                    $('#w').window('close');
                    $('#callsliplist').datagrid('reload');
                    $('#addgoodslist').datagrid('reload');
                    $.messager.alert('提示信息', '领料单信息已成功修改', 'info');
                } else {
                    $('#callslipadd').form('clear');
                    $('#w').window('close');
                    $('#callsliplist').datagrid('reload');

                    $.messager.alert('提示信息', '领料单已成功录入', 'info');
                }
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 修改选中的领料单数据---------------------------------------------------- */
function getSelectcallslip() {
    var row = $('#callsliplist').datagrid('getSelected');
    if (row) {
        if (row.spcode === "00") {
            $.ajax({
                async: false,
                dataType: 'json',
                url: 'callslip/getbyid',
                data: 'id=' + row.id,
                success: function (data) {
//					 定义提交方法 
                    url = 'callslip/update';
//					 加载数据 
                    $('#callslipadd').form('clear');
                    $('#callslipadd').form('load', data);
                    $('#w').window('setTitle', '领料单基本信息修改').window('open');

                }
            });
        } else {
            $.messager.alert('提示信息', '领料单已经提交，无法修改！', 'error');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
}

/* 删除选中的领料单----------------------------------------------------- */
function deleteSelectcallslip() {
    var row = $('#callsliplist').datagrid('getSelected');
    if (row) {
        if (row.spcode === "00") {
            $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'callslip/delete',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $('#callsliplist').datagrid('reload');
                                $.messager.alert('提示信息', '成功删除所选领料单！', 'info');
                            } else {
                                alert("登录超时删除失败，请重新登录");
                            }
                        }
                    });
                }
            });

        } else {
            $.messager.alert('提示信息', '领料单已经提交，无法删除！', 'error');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
}

/*新增选中物资表单提交(库存)*/
function submitgoodsForm() {

    //获取更新更改的行的集合
    var row = $("#addgoodslist").datagrid('getSelections');

    //DataGrid的更该行为不为0
    if (row.length) {
        $.ajax(
            {
                type: 'POST',
                url: "callslipgoods/create",
                data: {arrayList: JSON.stringify(row), callslipcode: callslipcode},
//                 data : 'id=' + row.id,
                success: function (data) {
                    if (data === "success") {
                        $.messager.alert('提示', '物资新增成功！', 'info');
                        $('#d').window('close');
                        $('#callsliplist').datagrid('reload');
                        $('#callslipgoodslist').datagrid('reload');    // 重新载入当前页面数据  
                        $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
                    } else if (data === "状态") {
                        $.messager.alert('提示', '此领料单已经进入出库状态，无法新增物资！', 'error');
                        $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
                    }
                }
            });
    } else {

        $.messager.alert('提示信息', '请至少选择一行入库物资信息，请联系管理员！', 'warning');
        $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
    }

}


/*新增选中物资表单提交(需求)*/
function submitgoodsForm1() {
    //获取更新更改的行的集合
    var row = $("#addgoodslist2").datagrid('getSelections');

    //DataGrid的更该行为不为0
    if (row.length) {
        $.ajax(
            {
                type: 'POST',
                url: "callslipgoods/createxq",
                data: {arrayList: JSON.stringify(row), callslipcode: callslipcode, stockcode: stockcode},
//                 data : 'id=' + row.id,
                success: function (data) {
                    if (data === "success") {
                        $.messager.alert('提示', '物资新增成功！', 'info');
                        $('#c').window('close');
                        $('#callsliplist').datagrid('reload');
                        $('#callslipgoodslist').datagrid('reload');    // 重新载入当前页面数据  
                        $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
                    } else if (data === "状态") {
                        $.messager.alert('提示', '此领料单已经进入出库状态，无法新增物资！', 'error');
                        $('#addgoodslist2').datagrid('destroyFilter'); // 销毁过滤
                    }
                }
            });
    } else {

        $.messager.alert('提示信息', '请至少选择一行入库物资信息，请联系管理员！', 'warning');
        $('#addgoodslist2').datagrid('destroyFilter'); // 销毁过滤
    }

}

/* 上报所选数据 */
function sendSelected() {
    var row = $('#callsliplist').datagrid('getSelected');
    if (row) {
        if (row.spcode === "00") {
            $.messager.confirm('提示信息', '确定上报领料单？上报后领料单信息无法修改！', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'callslip/send',
                        data: 'id=' + row.id,
                        success: function () {
                            $.messager.alert('提示信息', '提交成功！', 'info');
                            $('#callsliplist').datagrid('reload');
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '该领料单已上报，请勿重复操作！', 'error');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
}


/*以下是批量保存代码------------------------------------------*/
var editIndex = undefined;

//结束编辑
function endEditing() {

    if (editIndex === undefined) {
        return true;
    }
    //校验指定的行，如果有效返回true
    if ($('#callslipgoodslist').datagrid('validateRow', editIndex)) {
        $('#callslipgoodslist').datagrid('endEdit', editIndex); //结束编辑
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

//单击事件
/*function onClickRow(index) {
  if (editIndex !== index) {
      if (endEditing()) {
          $('#contractgoodslist').datagrid('selectRow', index)
                  .datagrid('beginEdit', index); //开始启用编辑
          editIndex = index; //将正在编辑的行号赋值给变量
      } else {
          $('#contractgoodslist').datagrid('selectRow', editIndex);
      }
      //JSON.stringify(inserted);
  }
}*/
function onClickCell(index, field) {

    if (editIndex !== index) {
        if (endEditing()) {
            $('#callslipgoodslist').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#callslipgoodslist').datagrid('getEditor', {index: index, field: field});
            if (ed) {
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex = index;
        } else {
            setTimeout(function () {
                $('#callslipgoodslist').datagrid('selectRow', editIndex);
            }, 0);
        }
    } else if (editIndex === 0) {
        if (endEditing()) {
            $('#callslipgoodslist').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#callslipgoodslist').datagrid('getEditor', {index: index, field: field});
            if (ed) {
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex = index;
        } else {
            setTimeout(function () {
                $('#callslipgoodslist').datagrid('selectRow', editIndex);
            }, 0);
        }
    }
}

function onEndEdit(index, row) {
    var ed = $(this).datagrid('getEditor', {
        index: index,
        field: 'id'
    });
    editIndex = undefined;
}

//保存按钮,多条数据一起提交
function save() {
    if (endEditing()) {

        //获取更新更改的行的集合
        var row = $("#callslipgoodslist").datagrid('getChanges');

        //DataGrid的更该行为不为0
        if (row.length) {
            $.ajax(
                {
                    type: 'POST',
                    url: "callslipgoods/update",
                    data: {arrayList: JSON.stringify(row),},
//                  data : 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $.messager.alert('提示', '保存成功！', 'info');

                            $('#callslipgoodslist').datagrid('reload');    // 重新载入当前页面数据  
                        } else {
                            if (data === "状态") {
                                $.messager.alert('提示信息', '此领料单已经进入出库状态，无法保存！', 'error');
                                $('#callslipgoodslist').datagrid('reload');
                            } else {
                                if (data === "大于") {
                                    $.messager.alert('提示信息', '物资实际数量不得超过计划剩余数量！', 'error');
                                    $('#callslipgoodslist').datagrid('reload');
                                } else {
                                    $.messager.alert('提示信息', '保存失败，请联系管理员！', 'warning');
                                    $('#callslipgoodslist').datagrid('reload');
                                }
                            }

                        }

                    }
                });
        } else  //如果没有修改数据，则提醒用户
        {
            $.messager.alert('提示信息', '您还没有修改信息！', 'warning');
        }
    }
    editIndex = undefined;
}

//获取修改行数
function getChanges() {
    var rows = $('#callslipgoodslist').datagrid('getChanges');
    alert(rows.length + '行被修改!');
}


/* 删除选中的物资信息 */
function deleteSelectedgoods() {
    var row = $('#callslipgoodslist').datagrid('getSelected');

    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'callslipgoods/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#callslipgoodslist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的物资', 'info');
                        } else {
                            if (data === "状态") {
                                $.messager.alert('提示信息', '此领料单已经进入出库状态，无法删除物资！', 'error');
                            } else {
                                alert("登录超时删除失败，请重新登录");
                            }
                        }
                    }
                });
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'warning');
    }
}

function comgrid() {
    var temp = "";
    $("#plancode").combogrid(
        {
            onSelect: function (rowIndex, rowData) {
                temp = temp + rowData.plancode + ",";
                var url = 'warehousinglist/getlistgoods?plancode=' +
                    temp.substring(0, temp.length - 1) + '&storehousecode=' + stockcode;
                $('#addgoodslist2').datagrid('options').url = url;
                $("#addgoodslist2").datagrid('reload');
            },
            onUnselect: function () {
                var arr = $("#plancode").combogrid("getValues");
                if (arr.length > 0) {
                    temp = arr.join(',') + ",";
                } else {
                    temp = "";
                }

                var url = 'warehousinglist/getlistgoods?plancode=' + arr + '&storehousecode=' + stockcode;
                $('#addgoodslist2').datagrid('options').url = url;
                $("#addgoodslist2").datagrid('reload');
            }
        });
}

