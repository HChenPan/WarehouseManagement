$(function () {
    var fhrid = $("#fhrid").val();
    var a;
    var b;
    var url33;
    $('#rklist').datagrid({
        url: 'whtwarehousing/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar1,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'notecode', title: '入库流水号', width: 150, sortable: true},
            {field: 'entryinfotype', title: '入库类型', width: 100, sortable: true},
            {field: 'storehousecode', title: '仓库编码', width: 100, sortable: true},
            {field: 'storehousename', title: '仓库名称', width: 100, sortable: true},
            {field: 'entrydate', title: '申请时间', width: 100, sortable: true},
            {field: 'consignee', title: '收料员', width: 150, sortable: true},
            {field: 'storeman', title: '保管员', width: 100, sortable: true},
            {field: 'note', title: '备注', width: 100, sortable: true},
            {
                field: 'rkstatus', title: '入库状态', width: 100,
                styler: function (value, row, index) {

                    if (value === '已入库') {
                        return 'background-color:#78C06E;color:white;';
                    }
                    if (value.indexOf('未入库') > 0) {
                        return 'background-color:#3399D4;color:white;';
                    }

                }
            }
        ]],
        onClickRow: function (index, row) {
            var rkcode = row.notecode;

            $('#rkcode2').textbox('setValue', rkcode);
            getdictionaryschild(rkcode);

        },
    });
    $('#wzlist').datagrid({
        rownumbers: true,
//	    pagination:true,
        //url:'whtwarehousinglist/getallwz',
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
            {field: 'unit', title: '单位', width: 200}

        ]]
    });
    $("#standardQueryBtn").click(function () {
        var a = $("#wzcode").val();
        var b = $("#wzname").val();
        if (a === "" & b === "") {
            $.messager.alert('提示信息', '尚未输入搜索条件,无法查询', 'info');
        } else {
            url33 = 'whtwarehousinglist/getallwz?&wzcode=' + a + '&wzname=' + b;

            $("#wzlist").datagrid('options').url = url33;
            $('#wzlist').datagrid('reload');
        }


    });
    var url3 = "warehousenum/getckbyfhrid?fhrid=" + fhrid;
    $('#storehousename').combobox({
        url: url3,
        valueField: 'stockname',
        textField: 'stockname',
    });
    comboevent();
    //comgrid();
});

var url1;
var url2;
/*定义数据表格的按钮及事件*/
var toolbar1 = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#rk').form('clear');
        var curr_time = new Date();
        var str = curr_time.getFullYear() + "-";
        str += curr_time.getMonth() + 1 + "-";
        str += curr_time.getDate();
        $('#entrydate').datebox('setValue', str);
        url1 = 'whtwarehousing/create';
        $('#w').window('setTitle', '新增无合同入库申请').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        getSelectedDictionary();


    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedDictionary();
    }
}, '-', {
    text: '入库',
    iconCls: 'icon-redo',
    handler: function () {
        sendSelected();

    }
}];
/*定义数据表格的按钮及事件*/
var toolbar2 = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        var rkcode = $("#rkcode2").textbox('getValue');
        $('#rklistschild').form('clear');
        $('#rkcode2').textbox('setValue', rkcode);

//		url2 = 'buylist/create';


        $('#c').window('setTitle', '新增入库物资明细').window('maximize').window('open');
        $('#wzlist').datagrid('loadData', {total: 0, rows: []});

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        //url2 = 'dictionaryschild/update';
        save();
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedDictionaryChild();
    }
}];


/*新增及修改数据字典大类表单提交*/
function submitform() {
    //移除disable属性,后台取值

    $('#rk').form('submit', {
        url: url1,
        onSubmit: function () {
            return $('#rk').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url1.indexOf('update') > 0) {
                    $('#rk').form('clear');
                    //清空表单后给大类赋值
                    $('#w').window('close');
                    $('#rklist').datagrid('reload');
                    $("#rkchildlist").datagrid('reload');
                    $.messager.alert('提示信息', '采购入库申请已成功修改', 'info');
                } else {
                    $('#rk').form('clear');
                    $('#w').window('close');
                    $('#rklist').datagrid('reload');
                    $.messager.alert('提示信息', '采购入库申请已成功录入', 'info');
                }
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 编辑选中的数据字典 */
function getSelectedDictionary() {
    var row = $('#rklist').datagrid('getSelected');

    if (row) {
        if (row.rkstatus === "未入库") {
            $.ajax({
                async: false,
                dataType: 'json',
                url: 'whtwarehousing/getallbyid',
                data: 'id=' + row.id,
                success: function (data) {
                    /* 定义提交方法 */
                    url1 = 'whtwarehousing/update';
                    /* 加载数据 */
                    $('#rk').form('clear');
                    $('#rk').form('load', data);
                    $('#w').window('setTitle', '无合同入库申请修改').window('open');
                }
            });
        } else {
            $.messager.alert('提示信息', '该无合同入库申请已入库，无法修改！', 'info');
        }

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的字典大类 */
function deleteSelectedDictionary() {
    var row = $('#rklist').datagrid('getSelected');
    if (row) {
        if (row.rkstatus === "未入库") {
            $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'whtwarehousing/delete',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $('#rklist').datagrid('reload');
                                $('#rkchildlist').datagrid('reload');

                                $.messager.alert('提示信息', '成功删除所选的无合同入库申请', 'info');
                            } else {
                                alert("登录超时删除失败，请重新登录");
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '该无合同入库申请已入库，无法删除！', 'info');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}


//根据大类编码获取子类列表
function getdictionaryschild(rkcode) {
    $('#rkchildlist').datagrid({
        url: 'whtwarehousinglist/search?rkcode=' + rkcode,
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
        columns: [[
            {field: 'wzcode', title: '物资编码', width: 150, sortable: true},
            {field: 'wzname', title: '物资名称', width: 150, sortable: true},
            {field: 'zjcode', title: '资金类型', width: 100, sortable: true},
            {field: 'zjname', title: '资金单位', width: 100, sortable: true},
            {field: 'unit', title: '单位', width: 120, sortable: true},
            {
                field: 'planprice',
                title: '单价',
                width: 120,
                sortable: true,
                editor: {type: 'numberbox', options: {required: true, precision: 2}}
            },
            {
                field: 'sjnum',
                title: '实际收货量',
                width: 120,
                editor: {type: 'numberbox', options: {required: true, precision: 2}}
            },
            {field: 'sjmoney', title: '实际入库总金额', width: 120, sortable: true},

        ]]
    });
}


function submitdictionarychildForm() {
    //获取更新更改的行的集合
    var row = $("#wzlist").datagrid('getSelections');
    var rkcode = $("#rkcode2").textbox('getValue');
    //DataGrid的更该行为不为0
    if (row.length) {
        $.ajax(
            {
                type: 'POST',
                url: "whtwarehousinglist/create",
                data: {arrayList: JSON.stringify(row), rkcode: rkcode},
//                 data : 'id=' + row.id,
                success: function (data) {
                    if (data === "success") {
                        $.messager.alert('提示', '物资新增成功！', 'info');
                        $('#wzname').textbox('reset');
                        $('#wzcode').textbox('reset');
                        $('#wzlist').datagrid('loadData', {total: 0, rows: []});
                        $('#rkchildlist').datagrid('reload');


                    } else if (data.indexOf('已存在') > 0) {
                        $.messager.alert('提示', data, '').window({width: 750, height: 300});
                        $('#wzname').textbox('reset');
                        $('#wzcode').textbox('reset');
                        $('#wzlist').datagrid('loadData', {total: 0, rows: []});

                    } else if (data === "已入库") {
                        $.messager.alert('提示信息', '该入库信息已完成入库，无法新增物资明细信息', 'info');
                        $('#wzname').textbox('reset');
                        $('#wzcode').textbox('reset');
                        $('#wzlist').datagrid('loadData', {total: 0, rows: []});
                    }
                }
            });
    } else {

        $.messager.alert('提示信息', '请至少选择一行入库信息，请联系管理员！', 'warning');
        $('#wzlist').datagrid('destroyFilter'); // 销毁过滤
    }
}

/* 获取选中的数据字典子类 */
function getSelectedDictionaryChild() {
    var row = $('#rkchildlist').datagrid('getSelected');

    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'whtwarehousinglist/getallbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url2 = 'whtwarehousinglist/update';
                //清空表单前获取大类相关值
                var rkcode = $("#rkcode2").textbox('getValue');

                $('#rklistschild').form('clear');
                //清空表单后给大类赋值
                //清空表单后给大类赋值
                $('#rkcode2').textbox('setValue', rkcode);

                /* 加载数据 */
                $('#rklistschild').form('load', data);
                $("#rklist").datagrid('reload');
                $('#c').window('setTitle', '物资明细修改').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的字典子类 */
function deleteSelectedDictionaryChild() {
    var row = $('#rkchildlist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'whtwarehousinglist/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#rkchildlist').datagrid('reload');
                            $('#rklist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的物资明细', 'info');
                        } else if (data === "已入库") {
                            $.messager.alert('提示信息', '该入库信息已完成入库，无法删除物资明细信息', 'info');
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

/* 查询数据 */
function rksearch() {
    var notecode = $('#notecode1').textbox('getValue');
    var datetime1 = $('#datetime1').datebox('getText');
    var datetime2 = $('#datetime2').datebox('getText');
    var url = 'whtwarehousing/search?&notecode=' + notecode + '&datetime1=' + datetime1 + '&datetime2=' + datetime2;
    $('#rklist').datagrid('options').url = url;
    $("#rklist").datagrid('reload');
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

/* 清空查询数据 */
function rkclean() {
    $('#notecode1').textbox('reset');
    $('#datetime1').combo('setText', '');
    $('#datetime2').combo('setText', '');
}

function comboevent() {


    $("#storehousename").combobox(
        {
            onChange: function (rec) {


                var stockname = $('#storehousename').combobox('getValue');
                var url = 'warehousenum/getIdByName?stockname=' + stockname;
                $.ajax({
                    url: url,
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        if (data !== null) {

                            $("#storehousecode").textbox('setValue', data.stockcode);
                            $("#storehouseid").val(data.id);

                        } else {

                            $("#storehousecode").textbox('setValue', '');
                            $("#storehouseid").val('');
                        }

                    }
                });
            }
        });


}

/* 上报所选数据 */
function sendSelected() {
    var row = $('#rklist').datagrid('getSelected');
    if (row) {
        if (row.rkstatus === "未入库") {
            $.messager.confirm('提示信息', '确定对此条入库信息进行入库处理?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'whtwarehousing/rk.action',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $.messager.alert('提示信息', '成功完成入库操作', 'info');
                                $('#rklist').datagrid('reload');
                                $('#rkchildlist').datagrid('loadData', {total: 0, rows: []});
                            } else if (data === "存在数量为空") {
                                $.messager.alert('提示信息', '该入库申请存在物资实际收货量为0或没有添加收货物资，请重新添加', 'info');
                                $('#rklist').datagrid('reload');
                                $('#rkchildlist').datagrid('loadData', {total: 0, rows: []});
                            }
                        }
                    });
                }
            });
        } else if (row.rkstatus === "已入库") {
            $.messager.alert('提示信息', '该需求申请已入库，请勿重复操作！');
        }


    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}


function onClickCell(index, field) {
    if (editIndex !== index) {
        if (endEditing()) {
            $('#rkchildlist').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#rkchildlist').datagrid('getEditor', {index: index, field: field});
            if (ed) {
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex = index;
        } else {
            setTimeout(function () {
                $('#rkchildlist').datagrid('selectRow', editIndex);
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

/*以下是批量保存代码------------------------------------------*/
var editIndex = undefined;

//结束编辑
function endEditing() {
    if (editIndex === undefined) {
        return true;
    }
    //校验指定的行，如果有效返回true
    if ($('#rkchildlist').datagrid('validateRow', editIndex)) {
        $('#rkchildlist').datagrid('endEdit', editIndex); //结束编辑
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
        var row = $("#rkchildlist").datagrid('getChanges');

        //DataGrid的更该行为不为0
        if (row.length) {
            $.ajax(
                {
                    type: 'POST',
                    url: "whtwarehousinglist/update",
                    data: {arrayList: JSON.stringify(row),},
//                  data : 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $.messager.alert('提示', '保存成功！', 'info');
                            $('#rklist').datagrid('reload');
                            $('#rkchildlist').datagrid('reload');    // 重新载入当前页面数据  
                        } else if (data === "已入库") {
                            $.messager.alert('提示', '该入库信息已完成操作,暂不支持修改！', 'info');
                            $('#rklist').datagrid('reload');
                            $('#rkchildlist').datagrid('reload');
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


/* 上报所选数据 */
function sendSelected2() {
    var row = $('#rklist').datagrid('getSelected');
    if (row) {
        if (row.rkstatus === "未入库" && row.entryinfotype === "补录合同入库") {
            $.messager.confirm('提示信息', '确定对此条入库信息进行补录合同入库处理?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'warehousing/rk2.action',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $.messager.alert('提示信息', '成功完成入库操作', 'info');
                                $('#rklist').datagrid('reload');
                                $('#rkchildlist').datagrid('reload');
                            } else if (data === "存在数量为空") {
                                $.messager.alert('提示信息', '该入库申请存在物资实际收货量为0或没有添加收货物资，请重新添加', 'info');
                                $('#rklist').datagrid('reload');
                                $('#rkchildlist').datagrid('loadData', {total: 0, rows: []});
                            }
                        }
                    });
                }
            });
        } else if (row.rkstatus === "已入库") {
            $.messager.alert('提示信息', '该需求申请已入库，请勿重复操作！');
        } else if (row.entryinfotype === "采购入库") {
            $.messager.alert('提示信息', '该需求申请不属于补录合同入库，无法进行此类入库！');
        }

    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}

