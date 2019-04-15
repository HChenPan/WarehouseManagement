$(function () {
    $('#cancellingstockslist').datagrid({
        url: 'cancellingstockssq/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', width: 120, title: 'id', hidden: true},
            {field: 'TKcode', title: '退库单号', width: 120, sortable: true, align: 'center'},
            {field: 'TKtype', title: '退库类型', width: 120, sortable: true},
            {field: 'sqdate', title: '申请时间', width: 100, sortable: true},
            {field: 'sqr', title: '申请人', width: 80, sortable: true, align: 'center'},
            {field: 'realname', title: '退库人', width: 80, sortable: true, align: 'center'},
            {field: 'userid', title: '退库人id', width: 80, sortable: true, align: 'center', hidden: true},
            {field: 'createtime', title: '创建时间', width: 100, sortable: true, hidden: true},
            {
                field: 'tkstatus', title: '退库状态', width: 100, sortable: true,
                styler: function (value, row, index) {
                    if (value === '已退库') {
                        return 'background-color:#78C06E;color:white;';
                    }
                    if (value === '草稿') {
                        return 'background-color:#3399D4;color:white;';
                    }
                }
            },
            {field: 'note', title: '退库原因', width: 460, sortable: true},
        ]],
        onClickRow: function (index, row) {
            $('#TKcode1').textbox('setValue', row.TKcode);
            $('#TKid1').textbox('setValue', row.id);
            getCancellingstocksWZ(row.id);
        },
    });
    $('#tkmxlist').datagrid({
        rownumbers: true,
        singleSelect: false, //允许选择多行
        selectOnCheck: true,//true勾选会选择行，false勾选不选择行
        checkOnSelect: true, //
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', width: 30, checkbox: true},
            {field: 'callslipcode', title: '领料单号', width: 150, sortable: true, align: 'center'},
            {field: 'wzcode', title: '物资编码', width: 150, sortable: true, align: 'center'},
            {field: 'wzname', title: '物资名称', width: 150, sortable: true, align: 'center'},
            {field: 'modelspcification', title: '型号规格', width: 150, sortable: true, align: 'center'},
            {field: 'unit', title: '单位', width: 100, sortable: true, align: 'center'},
            {field: 'sum', title: '领料总数', width: 100, sortable: true, align: 'center'},
        ]]
    });
    comboevent();
    comgrid();
});

//根据退库申请单获取物资列表
function getCancellingstocksWZ(TKid) {
    $('#cancellingstockschildlist').datagrid({
        url: 'cancellingstockswz/search?TKid=' + TKid,
        rownumbers: true,
        pagination: true,
        toolbar: toolbar2,
        singleSelect: true,
        striped: true,
        onClickCell: onClickCell,
        onEndEdit: onEndEdit,
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'TKcode', title: '退库单号', width: 150, sortable: true, align: 'center'},
            {field: 'TKid', title: '退库ID', width: 120, sortable: true, hidden: true},
            {field: 'callslipcode', title: '领料单号', width: 150, sortable: true, align: 'center'},
            {field: 'wzcode', title: '物资编码', width: 150, sortable: true, align: 'center'},
            {field: 'wzname', title: '物资名称', width: 150, sortable: true, align: 'center'},
            {field: 'unit', title: '单位', width: 120, sortable: true, hidden: true},
            {field: 'modelSpecification', title: '型号', width: 120, sortable: true, hidden: true},
            {field: 'stockname', title: '领料仓库', width: 120, sortable: true, align: 'center'},
            {field: 'stockcode', title: '领料仓库编码', width: 120, sortable: true},
            {field: 'zjcode', title: '资金类型', width: 120, sortable: true, align: 'center'},
            {field: 'zjname', title: '资金单位', width: 120, sortable: true, align: 'center'},
            {field: 'price', title: '出库价格', width: 120, sortable: true, align: 'center'},
            {field: 'sum', title: '领料总数', width: 100, sortable: true, align: 'center'},
            {field: 'sysum', title: '剩余可退库总数', width: 120, sortable: true, align: 'center'},
            {
                field: 'tkprice',
                title: '退库价格',
                width: 120,
                sortable: true,
                align: 'center',
                editor: {type: 'numberbox', options: {required: true, precision: 2}}
            },
            {
                field: 'tksum',
                title: '退库总数',
                width: 100,
                sortable: true,
                align: 'center',
                editor: {type: 'numberbox', options: {required: true, precision: 2}}
            },
            {field: 'updatetime', title: '更新时间', width: 120, sortable: true, hidden: true},
            {field: 'flag', title: 'FLAG', width: 120, sortable: true, hidden: true},
        ]]
    });
}

/*单击选中*/
function onClickCell(index, field) {
    if (editIndex !== index) {
        if (endEditing()) {
            $('#cancellingstockschildlist').datagrid('selectRow', index).datagrid('beginEdit', index);
            var ed = $('#cancellingstockschildlist').datagrid('getEditor', {index: index, field: field});
            if (ed) {
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex = index;
        } else {
            setTimeout(function () {
                $('#cancellingstockschildlist').datagrid('selectRow', editIndex);
            }, 0);
        }
    }
}

/*结束编辑*/
function onEndEdit(index, row) {
    var ed = $(this).datagrid('getEditor', {index: index, field: 'id'});
}

/*以下是批量保存代码------------------------------------------*/
var editIndex = undefined;

//结束编辑
function endEditing() {
    if (editIndex === undefined) {
        return true;
    }
    //校验指定的行，如果有效返回true
    if ($('#cancellingstockschildlist').datagrid('validateRow', editIndex)) {
        $('#cancellingstockschildlist').datagrid('endEdit', editIndex); //结束编辑
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}


//保存按钮,多条数据一起提交
function save() {
    if (endEditing()) {
        //获取更新更改的行的集合
        var row = $("#cancellingstockschildlist").datagrid('getChanges');
        //DataGrid的更该行为不为0
        if (row.length) {
            $.ajax({
                type: 'POST',
                url: "cancellingstockswz/update",
                data: {arrayList: JSON.stringify(row),},
                success: function (data) {
                    if (data === "success") {
                        $.messager.alert('提示', '保存成功！', 'info');
                        $('#cancellingstockslist').datagrid('reload');
                        $('#cancellingstockschildlist').datagrid('reload');    // 重新载入当前页面数据  
                    } else if (data === "已退库") {
                        $.messager.alert('提示', '该退库信息已完成操作,暂不支持修改！', 'info');
                        $('#cancellingstockslist').datagrid('reload');
                        $('#cancellingstockschildlist').datagrid('reload');
                    } else if (data.indexOf('大于') > 0) {
                        $.messager.alert('提示', data, '').window({width: 750, height: 300});
                        $('#cancellingstockslist').datagrid('reload');
                        $('#cancellingstockschildlist').datagrid('reload');
                    }
                },
            });
        } else {
            $.messager.alert('提示信息', '您还没有修改信息！', 'warning');
        }
    }
    editIndex = undefined;
}


var url;
var url2;
/*定义数据表格的按钮及事件*/
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#cancellingstocks').form('clear');
        url = 'cancellingstockssq/create';
        var curr_time = new Date();
        var str = curr_time.getFullYear() + "-";
        str += curr_time.getMonth() + 1 + "-";
        str += curr_time.getDate();
        $('#sqdate').datebox('setValue', str);	// 设置日期输入框的值
        $('#w').window('setTitle', '新增退库申请单').window('open');
    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        url = 'cancellingstockssq/update';
        getSelectedCancellingstocksSQ();

    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {

        deleteSelectedCancellingstocksSQ();

    }
}, '-', {
    text: '查看详情',
    iconCls: 'icon-search',
    handler: function () {
        viewsall();
    }
}, '-', {
    text: '退库',
    iconCls: 'icon-redo',
    handler: function () {
        sendSelected();

    }
}];

/*定义物资表格的按钮及事件*/
var toolbar2 = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        comgrid();
        var TKcode = $("#TKcode1").textbox('getValue');
        $('#cancellingstockswz').form('clear');
        $('#tkmxlist').datagrid('loadData', {total: 0, rows: []});
        $('#TKcode1').textbox('setValue', TKcode);
        $('#c').window('setTitle', '新增退库物资单').window('open');
    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        save();
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        url = 'cancellingstockswz/delete';
        deleteSelectedCancellingstocksWZ();
    }
}];

/*新增或修改 退库申请单 提交*/
function submitform() {
    $('#cancellingstocks').form('submit', {
        url: url,
        onSubmit: function () {
            return $('#cancellingstocks').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url.indexOf('update') > 0) {
                    $('#cancellingstocks').form('clear');
                    $('#w').window('close');
                    $('#cancellingstockslist').datagrid('reload');
                    $('#cancellingstockschildlist').datagrid('reload');
                    $('#cancellingstockschildlist').datagrid('reload');
                    $.messager.alert('提示信息', '退库申请单已成功修改', 'info');
                } else {
                    $('#cancellingstocks').form('clear');
                    $('#w').window('close');
                    $('#cancellingstockslist').datagrid('reload');
                    $('#cancellingstockschildlist').datagrid('reload');
                    $('#cancellingstockschildlist').datagrid('reload');
                    $.messager.alert('提示信息', '退库申请单已成功录入', 'info');
                }
            } else if (data === "数据错误") {
                $.messager.alert('提示信息', '数据错误，请刷新页面重试', 'error');
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 编辑选中的退库申请单 */
function getSelectedCancellingstocksSQ() {
    var row = $('#cancellingstockslist').datagrid('getSelected');

    if (row) {
        if (row.tkstatus === "草稿") {
            $.ajax({
                async: false,
                dataType: 'json',
                url: 'cancellingstockssq/getallbyid',
                data: 'id=' + row.id,
                success: function (data) {
                    /* 定义提交方法 */
                    url1 = 'cancellingstockssq/update';
                    /* 加载数据 */
                    $('#cancellingstocks').form('clear');
                    $('#cancellingstocks').form('load', data);
                    $('#w').window('setTitle', '退库申请单').window('open');
                }
            });
        } else {
            $.messager.alert('提示信息', '该退库申请单已完成退库，无法修改！', 'info');
        }

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的退库申请单 */
function deleteSelectedCancellingstocksSQ() {
    var row = $('#cancellingstockslist').datagrid('getSelected');
    if (row) {
        if (row.tkstatus === "草稿") {
            $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'cancellingstockssq/delete',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $('#cancellingstockslist').datagrid('reload');
                                $('#cancellingstockschildlist').datagrid('reload');
                                $.messager.alert('提示信息', '成功删除所选的退库申请单', 'info');
                            } else {
                                alert("登录超时删除失败，请重新登录");
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '该退库申请单已完成退库，无法删除！', 'info');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的退库申请单 */
function deleteSelectedCancellingstocksWZ() {
    var row = $('#cancellingstockschildlist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'cancellingstockswz/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#cancellingstockslist').datagrid('reload');
                            $('#cancellingstockschildlist').datagrid('reload');

                            $.messager.alert('提示信息', '成功删除所选的退库申请单', 'info');
                        } else if (data === "已退库") {
                            $.messager.alert('提示信息', '所选记录已退库成功，请勿操作！', 'info');
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

/* 查看详情 */
function viewsall() {
    var row = $('#cancellingstockslist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'cancellingstockssq/getallbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 加载数据 */
                $('#cancellingstocksview').form('clear');
                $('#cancellingstocksview').form('load', data);
                $('#view').window('setTitle', '退库申请单详情').window('open');
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 上报所选数据 */
function sendSelected() {
    var row = $('#cancellingstockslist').datagrid('getSelected');
    if (row) {
        if (row.tkstatus === "草稿") {
            $.messager.confirm('提示信息', '确定提交此条退库申请信息?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'cancellingstockssq/send.action',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $.messager.alert('提示信息', '退库成功', 'info');
                                $('#cancellingstockslist').datagrid('reload');
                                $('#cancellingstockschildlist').datagrid('reload');

                            } else if (data === "为空") {
                                $.messager.alert('提示信息', '所选记录包含空物资，请删除之后再退库！', 'info');
                            } else if (data.indexOf('大于') > 0) {
                                $.messager.alert('提示信息', data, 'error').window({width: 750});
                            } else {
                                alert("登录超时删除失败，请重新登录");
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '该退库申请已完成，请勿重复操作！');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}


/*新增 退库申请物资单 提交*/
function cancellingstockswzForm() {
    //获取更新更改的行的集合
    var row = $("#tkmxlist").datagrid('getSelections');
    var TKcode = $("#TKcode1").textbox('getValue');
    //DataGrid的更该行为不为0
    if (row.length) {
        $.ajax({
            type: 'POST',
            url: "cancellingstockswz/create",
            data: {arrayList: JSON.stringify(row), TKcode: TKcode},
            success: function (data) {
                if (data === "success") {
                    $.messager.alert('提示', '新增成功！', 'info');
                    $('#c').window('close');
                    $('#cancellingstockslist').datagrid('reload');
                    $('#cancellingstockschildlist').datagrid('reload');    // 重新载入当前页面数据
                } else if (data === "已退库") {
                    $.messager.alert('提示信息', '该入库信息已完成退库，无法新增物资明细信息', 'info');
                } else if (data.indexOf('核实') > 0) {
                    $.messager.alert('提示信息', data, 'error').window({width: 750});
                } else {
                    alert("登录超时删除失败，请重新登录");
                }
            }
        });
    } else {
        $.messager.alert('提示信息', '请至少选择一行入库物资信息！', 'warning');
    }
}

function comgrid() {
    var temp = "";
    $("#callslipcode").combogrid({
        onSelect: function (rowIndex, rowData) {
            $('#tkmxlist').datagrid('clearChecked');
            temp = temp + rowData.callslipcode + ",";
            var url = 'callslipgoods/searchformx?callslipcode=' + temp.substring(0, temp.length - 1);
            $('#tkmxlist').datagrid('options').url = url;
            $("#tkmxlist").datagrid('reload');
        },
        onUnselect: function () {
            var arr = $("#callslipcode").combogrid("getValues");
            if (arr.length > 0) {
                temp = arr.join(',') + ",";
            } else {
                temp = "";
            }
            var url = 'callslipgoods/searchformx?callslipcode=' + arr;
            $('#tkmxlist').datagrid('options').url = url;
            $("#tkmxlist").datagrid('reload');
        }
    });
}

function comboevent() {
    var tkrid = $("#tkrid").val();
    $('#callslipcode').combogrid({
        required: true,
        multiple: true,
        panelWidth: 785,
        idField: 'callslipcode',
        textField: 'callslipcode',
        url: 'callslip/getcallsliplist?tkrid=' + tkrid,
        columns: [[
            {field: 'callslipcode', title: '领料单号', width: 150},
            {field: 'storehousename', title: '领料仓库', width: 150},
            {field: 'projectno', title: '工程号', width: 150},
            {field: 'projectname', title: '工程名称', width: 320},
        ]]
    });
}

/* 清空查询数据 */
function cancellingstocksclean() {
    $('#TKcodes').textbox('reset');
    $('#datetime1').combo('setText', '');
    $('#datetime2').combo('setText', '');
}

/* 查询数据 */
function cancellingstockssearch() {
    var TKcode = $('#TKcodes').textbox('getValue');
    var datetime1 = $('#datetime1').datebox('getText');
    var datetime2 = $('#datetime2').datebox('getText');
    var url = 'cancellingstockssq/search?&TKcode=' + TKcode + '&datetime1=' + datetime1 + '&datetime2=' + datetime2;
    $('#cancellingstockslist').datagrid('options').url = url;
    getCancellingstocksWZ(null);
    $('#cancellingstockslist').datagrid('reload');
    $('#cancellingstockschildlist').datagrid('reload');
    $('#cancellingstockschildlist').datagrid('reload');

}