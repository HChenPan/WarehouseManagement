//加载 调拨单 
$(function () {
    var fhrid = $("#fhrid").val();
    $('#applytransferlist').datagrid({
        url: 'applytransfer/searchsq?sqrid=' + fhrid,
        rownumbers: true,
        pagination: true,
        toolbar: toolbar1,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'applytransfercode', title: '申请编号', width: 150, sortable: true},
            {field: 'sqrname', title: '申请人', width: 100, sortable: true},
            {field: 'sqrid', title: '申请人id', hidden: true},
            {field: 'sbunit', title: '申报部门', width: 100, sortable: true},
            {field: 'sbunitid', title: '申报部门id', hidden: true},
            {field: 'drck', title: '调入仓库', width: 100, sortable: true},
            {field: 'drckcode', title: '调入仓库编号', width: 150, sortable: true},
            {field: 'drckid', title: '调入仓库ID', hidden: true},
            {field: 'dcck', title: '调出仓库', width: 100, sortable: true},
            {field: 'dcckcode', title: '调出仓库编号', width: 150, sortable: true},
            {field: 'dcckid', title: '调出仓库ID', hidden: true},
            {field: 'sbmoney', title: '计划总金额', width: 100, sortable: true, hidden: true},
            {field: 'realmoney', title: '实际总金额', width: 100, sortable: true, hidden: true},
            {field: 'zjcode', title: '资金类型', width: 100, sortable: true},
            {field: 'zjname', title: '资金单位', width: 100, sortable: true},
            {field: 'note', title: '备注', width: 100, sortable: true},
            {field: 'sbdate', title: '申请时间', width: 100, sortable: true},
            {
                field: 'sbstatus', title: '申请状态', width: 100,
                styler: function (value, row, index) {
                    if (value === '已发货') {
                        return 'background-color:#78C06E;color:white;';
                    }
                    if (value === '已申请') {
                        return 'background-color:#9F79EE;color:white;';
                    }
                    if (value === '正在发货') {
                        return 'background-color:#9ACD32;color:white;';
                    }
                }
            }
        ]],
        onClickRow: function (index, row) {
            var id = row.id;
            var applytransfercode = row.applytransfercode;
            var sbunitid = row.sbunitid;
            var sbunit = row.sbunit;
            $('#applytransfercode2').textbox('setValue', applytransfercode);
            $('#applytransfercodeid1').textbox('setValue', id);
            $('#sbunitid1').textbox('setValue', sbunitid);
            $('#sbunit1').textbox('setValue', sbunit);
            getapplytransferchild(id);
        },
    });
});

/*根据 调拨单 编码获取 调拨详情 列表*/
function getapplytransferchild(id) {
    $('#applytransferchildlist').datagrid({
        url: 'transferlist/search?applytransfercodeid=' + id,
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
            {field: 'id', title: 'id', hidden: true},
            {field: 'applytransfercodeid', title: 'applytransfercodeid', hidden: true},
            {field: 'applytransfercode', title: '调拨申请单号', width: 150},
            {field: 'dcck', title: '调出仓库', width: 120, hidden: true},
            {field: 'dcckcode', title: '调出仓库编号', width: 120, hidden: true},
            {field: 'dcckid', title: '调出仓库id', width: 120, hidden: true},
            {field: 'wzcode', title: '物资编码', width: 120},
            {field: 'wzname', title: '物资名称', width: 120},
            {field: 'wzid', title: '物资id', width: 120, hidden: true},
            {field: 'modelspcification', title: '型号规格', width: 120},
            {field: 'unit', title: '单位', width: 120},
            {field: 'price', title: '单价', width: 120, hidden: true},
            {field: 'sqnum', title: '申请数量', width: 120},
            {field: 'realprice', title: '实际单价', width: 120, hidden: true},
            {field: 'ljnum', title: '累计发货数量', width: 120},
            {
                field: 'realnum',
                title: '本次发货数量',
                width: 120,
                editor: {type: 'numberbox', options: {required: true, precision: 2}}
            },
            {field: 'note', title: '备注', width: 120, hidden: true},
        ]]
    });
}

/*以下是批量保存代码------------------------------------------*/
var editIndex = undefined;

//结束编辑
function endEditing() {
    if (editIndex === undefined) {
        return true
    }
    //校验指定的行，如果有效返回true
    if ($('#applytransferchildlist').datagrid('validateRow', editIndex)) {
        $('#applytransferchildlist').datagrid('endEdit', editIndex); //结束编辑
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

function onClickCell(index, field) {
    if (editIndex !== index) {
        if (endEditing()) {
            $('#applytransferchildlist').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#applytransferchildlist').datagrid('getEditor', {index: index, field: field});
            if (ed) {
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex = index;
        } else {
            setTimeout(function () {
                $('#applytransferchildlist').datagrid('selectRow', editIndex);
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
        var row = $("#applytransferchildlist").datagrid('getChanges');
        //DataGrid的更该行为不为0
        if (row.length) {
            $.ajax({
                type: 'POST',
                url: "transferlist/updatereal",
                data: {arrayList: JSON.stringify(row),},
                success: function (data) {
                    if (data === "success") {
                        $.messager.alert('提示', '保存成功！', 'info');
                        $('#applytransferchildlist').datagrid('reload');    // 重新载入当前页面数据  
                    } else if (data === "已发货") {
                        $.messager.alert('提示信息', '该需求已处于发货阶段,请勿操作', 'info');
                    } else if (data === "error") {
                        $.messager.alert('提示信息', '登录超时保存失败,请重新登录', 'info');
                    } else if (data !== "error") {
                        $.messager.alert('提示信息', data, 'error').window({width: 750});
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
    var rows = $('#applytransferchildlist').datagrid('getChanges');
    alert(rows.length + '行被修改!');
}

/*定义 调拨单 表格的按钮及事件*/
var toolbar1 = [{
    text: '退回申请',
    iconCls: 'icon-undo',
    handler: function () {
        sendbackSelected();

    }
}, '-', {
    text: '发货',
    iconCls: 'icon-redo',
    handler: function () {
        sendSelected();

    }
}, '-', {
    text: '结束发货',
    iconCls: 'icon-ok',
    handler: function () {
        EndSelected();

    }
}];

/*定义 调拨单 明细 数据表格的按钮及事件*/
var toolbar2 = [{
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        save();
    }
}, '-', {
    text: '查看详情',
    iconCls: 'icon-search',
    handler: function () {
        $('#applytransferlistschild1').form('clear');
        getSelectedtransferlistChildview();
    }
}];


/* 退回 所选数据 */
function sendbackSelected() {
    var row = $('#applytransferlist').datagrid('getSelected');
    if (row) {
        if (row.sbstatus === "已申请") {
            $.messager.confirm('提示信息', '确定退回此条需求申请信息?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'applytransfer/sendback.action',
                        data: 'id=' + row.id,
                        success: function () {
                            $('#applytransferlist').datagrid('reload');
                            $.messager.alert('提示信息', '成功退回需求申请信息', 'info');
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '该内部交易申请已同意发货，请勿重复操作！');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}


/* 查询 */
function applytransfersearch() {
    var fhrid = $("#fhrid").val();
    var applytransfercode = $('#applytransfercode1').textbox('getValue');
    var datetime1 = $('#datetime1').datebox('getText');
    var datetime2 = $('#datetime2').datebox('getText');
    var url = 'applytransfer/searchsq?&applytransfercode=' + applytransfercode + '&datetime1=' + datetime1 + '&datetime2=' + datetime2 + '&sqrid=' + fhrid;
    $('#applytransferlist').datagrid('options').url = url;
    $("#applytransferlist").datagrid('reload');
}

/* 清空查询数据 */
function applytransferclean() {
    $('#applytransfercode1').textbox('reset');
    $('#datetime1').combo('setText', '');
    $('#datetime2').combo('setText', '');
}

/* 获取选中的调拨物资明细  */
function getSelectedtransferlistChildview() {
    var row = $('#applytransferchildlist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'transferlist/getallbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 加载数据 */
                $('#applytransferlistschild2').form('clear');
                $('#applytransferlistschild2').form('load', data);
                $('#view').window('setTitle', '查看调拨物资明细').window('open');
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

/* 上报所选数据 */
function sendSelected() {
    var row = $('#applytransferlist').datagrid('getSelected');
    if (row) {
        if (row.sbstatus !== "已发货") {
            $.messager.confirm('提示信息', '确定同意此条发货信息?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'applytransfer/agreesend.action',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $.messager.alert('提示信息', '发货完成', 'info');
                                $('#applytransferlist').datagrid('reload');
                                $('#applytransferchildlist').datagrid('reload');
                            } else if (data === "发货数量超出库存") {
                                $.messager.alert('提示信息', '发货数量超出库存,请重新修改', 'info');
                            } else if (data === "error") {
                                $.messager.alert('提示信息', '登录超时保存失败,请重新登录', 'info');
                            } else if (data !== "error") {
                                $.messager.alert('提示信息', data, 'error');
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '该内部交易申请已完成发货，请勿重复操作！');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}

/* 结束所选数据的发货 */
function EndSelected() {
    var row = $('#applytransferlist').datagrid('getSelected');
    if (row) {
        if (row.sbstatus !== "已发货") {
            $.messager.confirm('提示信息', '结束此条申请信息的发货?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'applytransfer/sendfh.action',
                        data: 'id=' + row.id,
                        success: function () {
                            $.messager.alert('提示信息', '发货成功', 'info');
                            $('#applytransferlist').datagrid('reload');
                            $('#applytransferchildlist').datagrid('reload');
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '该内部交易申请已完成发货，请勿重复操作！');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}
