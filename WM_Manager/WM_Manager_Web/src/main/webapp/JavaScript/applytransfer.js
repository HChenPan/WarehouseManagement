var drckcode;
var stockcode;
//加载 调拨单 
$(function () {
    var fhrid = $("#fhrid").val();
    $('#applytransferlist').datagrid({
        url: 'applytransfer/search',
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
                    if (value.indexOf('退回') > 0) {
                        return 'background-color:#FD1C3A;color:white;';
                    }
                    if (value === '已发货') {
                        return 'background-color:#9F79EE;color:white;';
                    }
                    if (value === '已申请') {
                        return 'background-color:#8B7355;color:white;';
                    }
                    if (value === '已退回') {
                        return 'background-color:#DC143C;color:white;';
                    }
                }
            }
        ]],
        onClickRow: function (index, row) {
            var id = row.id;
            var applytransfercode = row.applytransfercode;
            var sbunitid = row.sbunitid;
            var sbunit = row.sbunit;
            drckcode = row.drckcode;
            stockcode = row.dcckcode;
            $('#applytransfercode2').textbox('setValue', applytransfercode);
            $('#applytransfercodeid1').textbox('setValue', id);
            $('#sbunitid1').textbox('setValue', sbunitid);
            $('#sbunit1').textbox('setValue', sbunit);
            getapplytransferchild(id);
        },
    });
    var url3 = "warehousenum/getckbyfhrid?fhrid=" + fhrid;
    $('#drck').combobox({
        url: url3,
        valueField: 'stockname',
        textField: 'stockname',
    });
    comboevent();
});

//根据 调拨单 编码获取 调拨详情 列表
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
            {field: 'applytransfercode', title: '调拨申请单号', width: 150, sortable: true},
            {field: 'dcck', title: '调出仓库', width: 120, sortable: true, hidden: true},
            {field: 'dcckcode', title: '调出仓库编号', width: 120, sortable: true, hidden: true},
            {field: 'dcckid', title: '调出仓库id', width: 120, sortable: true, hidden: true},
            {field: 'wzcode', title: '物资编码', width: 120, sortable: true},
            {field: 'wzname', title: '物资名称', width: 120, sortable: true},
            {field: 'wzid', title: '物资id', width: 120, sortable: true, hidden: true},
            {field: 'modelspcification', title: '型号规格', width: 120, sortable: true},
            {field: 'unit', title: '单位', width: 120, sortable: true},
            {field: 'price', title: '单价', width: 120, sortable: true, hidden: true},
            {
                field: 'sqnum',
                title: '申请数量',
                width: 120,
                sortable: true,
                editor: {type: 'numberbox', options: {required: true, precision: 2}}
            },
            {field: 'realprice', title: '实际单价', width: 120, sortable: true, hidden: true},
            {field: 'realnum', title: '发货数量', width: 120, sortable: true},
        ]]
    });
}

/*定义全局URL*/
var url1;
var url3;

/*定义 调拨单 表格的按钮及事件*/
var toolbar1 = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#applytransfer').form('clear');
        url1 = 'applytransfer/create';
        var curr_time = new Date();
        var str = curr_time.getFullYear() + "-";
        str += curr_time.getMonth() + 1 + "-";
        str += curr_time.getDate();
        $('#sbdate').datebox('setValue', str);	// 设置日期输入框的值
        var departmentname = $("#departmentname").val();
        $('#sbunit').combobox('setValue', departmentname);

        $('#w').window('setTitle', '新增内部交易调拨单').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        $('#applytransfer').form('clear');
        getSelectedApplytransfer();
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedApplytransfer();
        getapplytransferchild(null);
    }
}, '-', {
    text: '申请',
    iconCls: 'icon-redo',
    handler: function () {
        sendSelected();
    }
}];

/*定义 调拨单 明细 数据表格的按钮及事件*/
var toolbar2 = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
        $('#addgoodslist').datagrid({
            rownumbers: true,
//		    pagination:true,
            url: 'stock/getgoods?stockcode=' + stockcode,
            singleSelect: false, //允许选择多行
            selectOnCheck: true,//true勾选会选择行，false勾选不选择行
            checkOnSelect: true, //
            striped: true,
            pageSize: 10,
            pageList: [10, 20],
            columns: [[
                {field: 'id', width: 30, checkbox: true},
                {field: 'wzcode', title: '物资编码', width: 150},
                {field: 'wzname', title: '物资名称', width: 150},
                {field: 'modelspcification', title: '型号规格', width: 300},
                {field: 'unit', title: '单位', width: 100},
                {field: 'price', title: '单价', width: 100},
                {field: 'stockname', title: '所属仓库', width: 100},
                {field: 'bqend', title: '库存数量', width: 100},

            ]]
        });
        $('#addgoodslist').datagrid('enableFilter'); // 启用过滤
        $('#c').window('setTitle', '新增物资明细').window('maximize').window('open');

    }
}, '-', {
    text: '保存',
    iconCls: 'icon-save',
    handler: function () {
        save();
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedtransferlistChild();
    }
}, '-', {
    text: '查看详情',
    iconCls: 'icon-search',
    handler: function () {
        $('#applytransferlistschild1').form('clear');
        url3 = 'transferlist/update';
        getSelectedtransferlistChildview();
    }
}];

/* 删除选中的 内部交易调拨单 */
function deleteSelectedApplytransfer() {
    var row = $('#applytransferlist').datagrid('getSelected');
    if (row) {
        if (row.sbstatus === "草稿" || row.sbstatus === "已退回") {
            $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'applytransfer/delete',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $('#applytransferlist').datagrid('reload');
                                $('#applytransferchildlist').datagrid('reload');
                                $.messager.alert('提示信息', '成功删除所选的调拨单', 'info');
                            } else {
                                alert("登录超时删除失败，请重新登录");
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '该需求申请已提交，无法删除！', 'info');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 修改 选中的调拨申请单 */
function getSelectedApplytransfer() {
    var row = $('#applytransferlist').datagrid('getSelected');
    if (row) {
        if (row.sbstatus === "草稿" || row.sbstatus === "已退回") {
            $.ajax({
                async: false,
                dataType: 'json',
                url: 'applytransfer/getallbyid',
                data: 'id=' + row.id,
                success: function (data) {
                    /* 定义提交方法 */
                    url1 = 'applytransfer/update';
                    /* 加载数据 */
                    $('#applytransfer').form('clear');
                    $('#applytransfer').form('load', data);
                    $('#w').window('setTitle', '内部交易调拨单修改').window('open');
                }
            });
        } else {
            $.messager.alert('提示信息', '该内部调拨单已提交，无法修改！', 'info');
        }

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}


/*新增及修改 调拨申请单 提交*/
function submitform() {
    var drck = $('#drck').textbox('getValue');
    var dcck = $('#dcck').textbox('getValue');
    if (dcck.toString() === drck.toString()) {
        $.messager.alert('提示信息', "调出仓库和调入仓库相同，请修改", 'error');
    } else {
        $('#applytransfer').form('submit', {
            url: url1,
            onSubmit: function () {
                return $('#applytransfer').form('validate');
            },
            success: function (data) {
                if (data === "success") {
                    if (url1.indexOf('update') > 0) {
                        $('#applytransfer').form('clear');
                        //清空表单后给大类赋值
                        $('#w').window('close');
                        $('#applytransferlist').datagrid('reload');
                        $("#applytransferchildlist").datagrid('reload');
                        $.messager.alert('提示信息', '内部交易调拨单已成功修改', 'info');
                    } else {
                        $('#applytransfer').form('clear');
                        $('#w').window('close');
                        $('#applytransferlist').datagrid('reload');
                        $("#applytransferchildlist").datagrid('reload');
                        $.messager.alert('提示信息', '内部交易调拨单已成功录入', 'info');
                    }
                } else if (data === "error") {
                    alert("登录超时保存失败，请重新登录");
                } else if (data !== "error") {
                    $.messager.alert('提示信息', data, 'error');
                }
            }
        });
    }
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

/* 删除选中的 调拨明细  */
function deleteSelectedtransferlistChild() {
    var row = $('#applytransferchildlist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'transferlist/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#applytransferchildlist').datagrid('reload');
                            $('#applytransferlist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的物资明细', 'info');
                        } else if (data === "已申请") {
                            $.messager.alert('提示信息', '该需求已提交,请勿操作', 'info');
                        } else if (data === "error") {
                            alert("登录超时保存失败，请重新登录");
                        } else if (data !== "error") {
                            $.messager.alert('提示信息', data, 'error');
                        }
                    }
                });
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/*新增 调拨明细 表单提交*/
function applytransferlistschildForm() {
    var applytransfercodeid = $('#applytransfercodeid1').textbox('getValue');

    //获取更新更改的行的集合
    var row = $("#addgoodslist").datagrid('getSelections');
    //DataGrid的更该行为不为0
    if (row.length) {
        $.ajax({
            type: 'POST',
            url: 'transferlist/create',
            data: {arrayList: JSON.stringify(row), applytransfercodeid: applytransfercodeid},
            success: function (data) {
                if (data === "success") {
                    $.messager.alert('提示', '物资新增成功！', 'info');
                    $('#c').window('close');
                    $('#applytransferlist').datagrid('reload');
                    $('#applytransferchildlist').datagrid('reload');
                    $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
                } else if (data === "该库存物资明细已存在，请修改") {
                    $.messager.alert('提示信息', '该库存物资明细已存在，请修改', 'info');
                    $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
                } else if (data === "已申请") {
                    $.messager.alert('提示信息', '该需求已处于申请阶段,请勿操作', 'info');
                    $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
                } else if (data === "error") {
                    alert("登录超时保存失败，请重新登录");
                    $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
                } else if (data !== "error") {
                    $.messager.alert('提示信息', data, 'error').window({width: 750});
                    $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
                }
            }
        });
    } else {
        $.messager.alert('提示信息', '请至少选择一行入库物资信息，请联系管理员！', 'warning');
        $('#addgoodslist').datagrid('destroyFilter'); // 销毁过滤
    }
}

/* 上报所选数据 */
function sendSelected() {
    var row = $('#applytransferlist').datagrid('getSelected');
    if (row) {
        if (row.sbstatus === "草稿" || row.sbstatus === "已退回") {
            $.messager.confirm('提示信息', '确定提交此条需求申请信息?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'applytransfer/send.action',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $.messager.alert('提示信息', '成功提交需求申请信息', 'info');
                                $('#applytransferlist').datagrid('reload');
                            } else if (data === "error") {
                                alert("登录超时保存失败，请重新登录");
                            } else if (data !== "error") {
                                $.messager.alert('提示信息', data, 'error').window({width: 750});
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '该需求申请已提交，请勿重复操作！');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
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
                url: "transferlist/update",
                data: {arrayList: JSON.stringify(row),},
                success: function (data) {
                    if (data === "success") {
                        $.messager.alert('提示', '保存成功！', 'info');
                        $('#applytransferchildlist').datagrid('reload');    // 重新载入当前页面数据  
                    } else if (data === "申请数量超出库存") {
                        $.messager.alert('提示信息', '申请数量超出库存,请重新输入', 'info');
                    } else if (data === "已申请") {
                        $.messager.alert('提示信息', '该需求已处于申请阶段,请勿操作', 'info');
                    } else if (data === "error") {
                        alert("登录超时保存失败，请重新登录");
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

$.extend($.fn.validatebox.defaults.rules, {//添加验证规则
    number: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^\d+(\.\d+)?$/;
            return reg.test(value);
        },
        message: '提示信息:只能输入数字和小数点'
    }
});

/* 查询 */
function applytransfersearch() {
    var applytransfercode = $('#applytransfercode1').textbox('getValue');
    var datetime1 = $('#datetime1').datebox('getText');
    var datetime2 = $('#datetime2').datebox('getText');
    var url = 'applytransfer/search?&applytransfercode=' + applytransfercode + '&datetime1=' + datetime1 + '&datetime2=' + datetime2;
    $('#applytransferlist').datagrid('options').url = url;
    $("#applytransferlist").datagrid('reload');
}

/* 清空查询数据 */
function applytransferclean() {
    $('#applytransfercode1').textbox('reset');
    $('#datetime1').combo('setText', '');
    $('#datetime2').combo('setText', '');
}

/*选择调入仓库自动填充仓库ID*/
function comboevent() {
    $("#drck").combobox({
        onChange: function (rec) {
            var drck = $('#drck').combobox('getValue');
            var url = 'warehousenum/getIdByName?stockname=' + encodeURIComponent(drck);
            $.ajax({
                url: url,
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data !== null) {
                        $("#drckcode").textbox('setValue', data.stockcode);
                        $("#drckid").textbox('setValue', data.id);
                    } else {
                        $("#drckcode").textbox('setValue', '');
                        $("#drckid").textbox('setValue', '');
                    }
                }
            });
        }
    });
    $("#dcck").combobox({
        onChange: function (rec) {
            var drck = $('#dcck').combobox('getValue');
            var url = 'warehousenum/getIdByName?stockname=' + encodeURIComponent(drck);
            $.ajax({
                url: url,
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data !== null) {
                        $("#dcckcode").textbox('setValue', data.stockcode);
                        $("#dcckid").textbox('setValue', data.id);
                    } else {
                        $("#dcckcode").textbox('setValue', '');
                        $("#dcckid").textbox('setValue', '');
                    }
                }
            });
        }
    });

    $("#sbunit").combobox({
        onChange: function (rec) {
            var sbunit = $('#sbunit').combobox('getValue');
            var url = 'department/getDepart?name=' + encodeURIComponent(sbunit);
            $.ajax({
                url: url,
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data !== null) {
                        $("#sbunitid").textbox('setValue', data.id);
                    } else {
                        $("#sbunitid").textbox('setValue', '');
                    }
                }
            });
        }
    });
}
