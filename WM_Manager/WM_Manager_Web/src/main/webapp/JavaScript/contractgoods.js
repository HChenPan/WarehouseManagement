var serialsnumber;
var a;
var b;
var url3;

$(function () {
    $('#contractbasiclist').datagrid({
        url: 'contractbasic/list',
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
            {field: 'summoney', title: '合同总价', width: 100},
            {field: 'venditorname', title: '出卖人名称', width: 150},
            {field: 'startdate', title: '有效起始日期', width: 120, sortable: true},
            {field: 'enddate', title: '有效截止日期', width: 120, sortable: true},
            {field: 'auditingstatus', title: '审批状态', width: 100}
        ]],
        onClickRow: function (index, row) {
            serialsnumber = row.serialsnumber;
            $('#serialsnumber').textbox('setValue', serialsnumber);
            $('#serialsnumber2').textbox('setValue', serialsnumber);
            var contracttype = row.contracttype;
            getcontractgoods(serialsnumber, contracttype);
        },
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

    $('#wzlist').datagrid({
        rownumbers: true,
        singleSelect: false, //允许选择多行
        selectOnCheck: true,//true勾选会选择行，false勾选不选择行
        checkOnSelect: true, //
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'wzid', width: 30, checkbox: true},
            {field: 'wzcode', title: '物资编码', width: 250},
            {field: 'wzname', title: '物资名称', width: 250},
            {field: 'modelspcification', title: '型号规格', width: 250},
            {field: 'unit', title: '单位', width: 200},
            {field: 'price', title: '单价', width: 200},

        ]]
    });

    $("#standardQueryBtn").click(function () {
        var a = $("#wzcode1").val();
        var b = $("#wzname").val();
        if (a === "" & b === "") {
            $.messager.alert('提示信息', '尚未输入搜索条件,无法查询', 'info');
        } else {
            url33 = 'planlist/getallwz?&wzcode=' + a + '&wzname=' + b;

            $("#wzlist").datagrid('options').url = url33;
            $('#wzlist').datagrid('reload');
        }


    });


    $('#searchgoodslist').datagrid({
        rownumbers: true,
        pagination: true,
        singleSelect: false, //允许选择多行
        selectOnCheck: true,//true勾选会选择行，false勾选不选择行
        checkOnSelect: true, //
        striped: true,
        pageSize: 10,
        idField: 'id',
        pageList: [10, 20],
        columns: [[
            {field: 'id', width: 30, checkbox: true},
            {field: 'buycode', title: '采购计划号', width: 150, sortable: true},
            {field: 'wzcode', title: '物资编码', width: 150, sortable: true},
            {field: 'wzname', title: '物资名称', width: 120, sortable: true},
            {field: 'modelspcification', title: '规格型号', width: 150, sortable: true},
            {field: 'unit', title: '单位', width: 120, sortable: true},
            {field: 'buyprice', title: '采购单价', width: 120, sortable: true},
            {field: 'buynum', title: '采购数量', width: 120, sortable: true},
            {field: 'synum', title: '剩余采购数量', width: 120, sortable: true},


        ]]
    });

    var url3;


    $("#wzcode").combogrid(
        {
            onSelect: function (rowIndex, rowData) {
                $('#buynum').textbox("setValue", rowData.buynum);
                $('#buymoney').textbox("setValue", rowData.buymoney);
                $('#buyprice').textbox("setValue", rowData.buyprice);
                $('#wzname').textbox("setValue", rowData.wzname);
                $('#unit').textbox("setValue", rowData.unit);
                $('#plancode').textbox("setValue", rowData.plancode);
            }
        });

});

var url1;
var url2;


/* 合同搜索方法----------------------------------------------------- */
function contractsearch() {
    //var contractDate = $("#contractDate1").val();
    var serialsnumber = $("#serialsnumber1").textbox('getText');

    var url = 'contractbasic/list?&serialsnumber='
        + serialsnumber;

    $('#contractbasiclist').datagrid('options').url = url;
    $("#contractbasiclist").datagrid('reload');
    $('#contractgoodslist').datagrid('loadData', {total: 0, rows: []});
}

/*清空搜索条件——合同-------------------------------------------------*/
function contractsearchclean() {
    $("#serialsnumber1").textbox('clear');
}


/*定义数据表格的按钮及事件*/
var toolbar2 = [{
    id: 'add',
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#searchgoodslist').datagrid('clearChecked');
        comgrid();
        url2 = 'contractgoods/create';
//		$('#buylistid').combogrid('clear');
//		$('#wzcode').combogrid('clear');
        $('#c').window('setTitle', '新增物资信息').window('maximize').window('open');

    }
}, {
    id: 'addxy',
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        //url2 = 'dictionaryschild/update';
//		url2 = 'contractgoods/create';
        $('#c1').window('setTitle', '新增物资明细').window('maximize').window('open');
        $('#wzlist').datagrid('loadData', {total: 0, rows: []});
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

/*以下是批量保存代码------------------------------------------*/
var editIndex = undefined;

//结束编辑
function endEditing() {
    if (editIndex === undefined) {
        return true
    }
    //校验指定的行，如果有效返回true
    if ($('#contractgoodslist').datagrid('validateRow', editIndex)) {
        $('#contractgoodslist').datagrid('endEdit', editIndex); //结束编辑
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}


function onClickCell(index, field) {
    if (editIndex !== index) {
        if (endEditing()) {
            $('#contractgoodslist').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#contractgoodslist').datagrid('getEditor', {index: index, field: field});
            if (ed) {
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex = index;
        } else {
            setTimeout(function () {
                $('#contractgoodslist').datagrid('selectRow', editIndex);
            }, 0);
        }
    }
}

function onEndEdit(index, row) {
    var ed = $(this).datagrid('getEditor', {
        index: index,
        field: 'id'
    });
}

//保存按钮,多条数据一起提交
function save() {
    if (endEditing()) {

        //获取更新更改的行的集合
        var row = $("#contractgoodslist").datagrid('getChanges');

        //DataGrid的更该行为不为0
        if (row.length) {
            $.ajax(
                {
                    type: 'POST',
                    url: "contractgoods/update",
                    data: {arrayList: JSON.stringify(row),},
//                  data : 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $.messager.alert('提示', '保存成功！', 'info');
                            $('#contractbasiclist').datagrid('reload');    // 重新载入当前页面数据  
                            $('#contractgoodslist').datagrid('reload');    // 重新载入当前页面数据 

                        } else {
                            if (data === "审批中") {
                                $.messager.alert('提示信息', '此合同正在审批，不允许修改物资信息！', 'error');
                                $('#contractgoodslist').datagrid('reload');
                            } else {
                                if (data === "大于") {
                                    $.messager.alert('提示信息', '物资实际数量不得超过计划剩余数量！', 'error');
                                    $('#contractgoodslist').datagrid('reload');
                                } else {
                                    $.messager.alert('提示信息', '保存失败，请联系管理员！', 'warning');
                                    $('#contractgoodslist').datagrid('reload');
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
    var rows = $('#contractgoodslist').datagrid('getChanges');
    alert(rows.length + '行被修改!');
}

//根据合同获取物资列表
function getcontractgoods(serialsnumber, contracttype) {
    $('#contractgoodslist').datagrid({
        url: 'contractgoods/list?contractbasicid=' + serialsnumber,
        rownumbers: true,
        pagination: true,
        toolbar: toolbar2,
        singleSelect: true,
        striped: true,
        //clickToEdit:false,
        //dblclickToEdit:true,
        onClickCell: onClickCell,
        onEndEdit: onEndEdit,
        pageSize: 10,
        pageList: [10, 20],
//        协议制合同隐藏不同按钮
        onBeforeLoad: function () {
            if (contracttype !== '协议制合同') {
                $("div.datagrid-toolbar [id ='addxy']").eq(0).hide();
            } else {
                $("div.datagrid-toolbar [id ='add']").eq(0).hide();
            }
        },
        frozenColumns: [[
            {field: 'buycode', title: '采购计划号', width: 140},
            {field: 'wzname', title: '物资名称', width: 150},
        ]],
        columns: [[

            {field: 'wzcode', title: '物资编码', width: 180},
            {field: 'unit', title: '单位', width: 80},
            {field: 'modelspcification', title: '规格型号', width: 150, sortable: true},
            {field: 'synum', title: '剩余采购数量', width: 110},
            {
                field: 'planbum',
                title: '实际数量',
                width: 110,
                editor: {type: 'numberbox', options: {required: true, precision: 2}}
            },
            {
                field: 'planprice',
                title: '实际单价',
                width: 110,
                editor: {type: 'numberbox', options: {required: true, precision: 2}}
            },
            {field: 'summoney', title: '实际总金额', width: 110},
            {field: 'buyprice', title: '计划采购单价', width: 110},
            {field: 'buymoney', title: '计划采购金额', width: 110},
            {field: 'buynum', title: '计划采购数量', width: 110},

        ]]
    });


}




/* 删除选中的物资信息 */
function deleteSelectedgoods() {
    var row = $('#contractgoodslist').datagrid('getSelected');

    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'contractgoods/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#contractgoodslist').datagrid('reload');
                            $('#contractbasiclist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的物资', 'info');
                        } else {
                            if (data === "审批中") {
                                $.messager.alert('提示信息', '此合同正在审批，不允许删除物资！', 'error');
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
    $("#buylistid").combogrid(
        {
            onSelect: function (rowIndex, rowData) {
                temp = temp + rowData.buycode + ",";
                var url = 'buylist/searchforbuycode?buycode=' + temp.substring(0, temp.length - 1);
                $('#searchgoodslist').datagrid('options').url = url;
                $("#searchgoodslist").datagrid('reload');
            },
            onUnselect: function () {
                var arr = $("#buylistid").combogrid("getValues");
                if (arr.length > 0) {
                    temp = arr.join(',') + ",";
                } else {
                    temp = "";
                }
                var url = 'buylist/searchforbuycode?buycode=' + arr;
                $('#searchgoodslist').datagrid('options').url = url;
                $("#searchgoodslist").datagrid('reload');
            }
        });
}


/*新增选中物资表单提交*/
function submitgoodsForm() {

    //获取更新更改的行的集合
    var row = $("#searchgoodslist").datagrid('getSelections');

    //DataGrid的更该行为不为0
    if (row.length) {
        $.ajax(
            {
                type: 'POST',
                url: "contractgoods/create",
                data: {arrayList: JSON.stringify(row), serialsnumber: serialsnumber},
//                 data : 'id=' + row.id,
                success: function (data) {
                    if (data === "success") {
                        $.messager.alert('提示', '物资新增成功！', 'info');
                        $('#c').window('close');
                        $('#contractgoodslist').datagrid('reload');
                        $('#searchgoodslist').datagrid('reload');    // 重新载入当前页面数据  

                    } else if (data === "审核中") {
                        $.messager.alert('提示', '当前状态无法新增物资！', 'error');

                    }
                }
            });
    } else {

        $.messager.alert('提示信息', '请至少选择一行入库物资信息，请联系管理员！', 'warning');
        $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
    }

}


/*新增选中物资表单提交（协议制）*/
function submitgoodsForm1() {

    //获取更新更改的行的集合
    var row = $("#wzlist").datagrid('getSelections');

    //DataGrid的更该行为不为0
    if (row.length) {
        $.ajax(
            {
                type: 'POST',
                url: "contractgoods/createxy",
                data: {arrayList: JSON.stringify(row), serialsnumber: serialsnumber},
//                 data : 'id=' + row.id,
                success: function (data) {
                    if (data === "success") {
                        $.messager.alert('提示', '物资新增成功！', 'info');
                        $('#c').window('close');
                        $('#contractgoodslist').datagrid('reload');
                        $('#wzlist').datagrid('reload');    // 重新载入当前页面数据  

                    } else if (data === "审核中") {
                        $.messager.alert('提示', '当前状态无法新增物资！', 'error');

                    }
                }
            });
    } else {

        $.messager.alert('提示信息', '请至少选择一行入库物资信息，请联系管理员！', 'warning');

    }

}
