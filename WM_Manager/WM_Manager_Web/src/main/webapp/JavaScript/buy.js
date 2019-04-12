$(function () {
    $('#buylist').datagrid({
        url: 'buy/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar1,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'buycode', title: '采购计划号', width: 150, sortable: true},
            {field: 'buytype', title: '采购大类', width: 100, sortable: true},
            {field: 'buyname', title: '采购大类名称', width: 100, sortable: true},
            {field: 'buydate', title: '采购日期', width: 100, sortable: true},
            {field: 'zjcode', title: '资金类型', width: 100, sortable: true},
            {field: 'zjname', title: '资金单位', width: 100, sortable: true},
            {field: 'buysummoney', title: '采购总金额', width: 100, sortable: true},
            {field: 'buysqr', title: '采购人', width: 100, sortable: true},
            {field: 'buyunit', title: '采购部门', width: 150, sortable: true},
            {field: 'note', title: '备注', width: 100, sortable: true},
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
            var buycode = row.buycode;
            var buytype = row.buytype;
            var buyname = row.buyname;
            $('#buycode2').textbox('setValue', buycode);
            $('#buytype1').textbox('setValue', buytype);
            $('#buyname1').textbox('setValue', buyname);
            getdictionaryschild(buycode);

        },
    });

    $('#cgmxlist').datagrid({
        rownumbers: true,
        pagination: true,
        singleSelect: false, //允许选择多行
        selectOnCheck: true,//true勾选会选择行，false勾选不选择行
        checkOnSelect: true, //
        striped: true,
        idField: 'id',
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', width: 30, checkbox: true},
            {field: 'plancode', title: '需求计划号', width: 150, sortable: true},
            {field: 'wzcode', title: '物资编码', width: 150, sortable: true},
            {field: 'wzname', title: '物资名称', width: 120, sortable: true},
            {field: 'modelspcification', title: '型号规格', width: 120, sortable: true},
            {field: 'unit', title: '单位', width: 120, sortable: true},
            {field: 'zjcode', title: '资金类型', width: 100, sortable: true},
            {field: 'zjname', title: '资金单位', width: 100, sortable: true},
            {field: 'buyprice', title: '采购单价', width: 120, sortable: true},
            {field: 'buynum', title: '采购数量', width: 120, sortable: true}


        ]]
    });

    comboevent();
    comgrid1();
    //comgrid();
});

var url1;
var url2;
/*定义数据表格的按钮及事件*/
var toolbar1 = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#buy').form('clear');
        $('#buytype').combobox({readonly: false});
        $("#buytype").removeAttr("readonly");
        var curr_time = new Date();
        var str = curr_time.getFullYear() + "-";
        str += curr_time.getMonth() + 1 + "-";
        str += curr_time.getDate();
        $('#buydate').datebox('setValue', str);
        var departmentname = $("#departmentname").val();
        $('#buyunit').combobox('setValue', departmentname);
        url1 = 'buy/create';
        $('#w').window('setTitle', '新增采购计划申请').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        $("#buytype").combobox("readonly", true);
        getSelectedDictionary();


    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedDictionary();
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
}];
/*定义数据表格的按钮及事件*/
var toolbar2 = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        comgrid();
        var buycode = $("#buycode2").textbox('getValue');
        var buytype = $("#buytype1").textbox('getValue');
        var buyname = $("#buyname1").textbox('getValue');
        $('#buylistschild').form('clear');
        $('#cgmxlist').datagrid('loadData', {total: 0, rows: []});
        $('#buycode2').textbox('setValue', buycode);
        $('#buytype1').textbox('setValue', buytype);
        $('#buyname1').textbox('setValue', buyname);

        $('#plancode1').combogrid({readonly: false});
        $("#plancode1").removeAttr("readonly");

        url2 = 'buylist/create';
        $('#c').window('setTitle', '新增物资明细').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        //url2 = 'dictionaryschild/update';


        /*	$("#plancode1").combogrid("readonly",true);
            
            getSelectedDictionaryChild();*/
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

    $('#buy').form('submit', {
        url: url1,
        onSubmit: function () {
            return $('#buy').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url1.indexOf('update') > 0) {
                    $('#buy').form('clear');
                    //清空表单后给大类赋值
                    $('#w').window('close');
                    $('#buylist').datagrid('reload');
                    $("#buychildlist").datagrid('reload');
                    $.messager.alert('提示信息', '采购计划申请已成功修改', 'info');
                } else {
                    $('#buy').form('clear');
                    $('#w').window('close');
                    $('#buylist').datagrid('reload');
                    $.messager.alert('提示信息', '采购计划申请申请已成功录入', 'info');
                }
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 编辑选中的数据字典 */
function getSelectedDictionary() {
    var row = $('#buylist').datagrid('getSelected');

    if (row) {
        if (row.spcode === "00") {
            $.ajax({
                async: false,
                dataType: 'json',
                url: 'buy/getallbyid',
                data: 'id=' + row.id,
                success: function (data) {
                    /* 定义提交方法 */
                    url1 = 'buy/update';
                    /* 加载数据 */
                    $('#buy').form('clear');
                    $('#buy').form('load', data);
                    $('#w').window('setTitle', '采购计划申请修改').window('open');
                }
            });
        } else {
            $.messager.alert('提示信息', '该采购计划申请已上报，无法修改！', 'info');
        }

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的字典大类 */
function deleteSelectedDictionary() {
    var row = $('#buylist').datagrid('getSelected');
    if (row) {
        if (row.spcode === "00") {
            $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'buy/delete',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $('#buylist').datagrid('reload');
                                $('#buychildlist').datagrid('reload');
                                $("#buychildlist").datagrid('reload');
                                $.messager.alert('提示信息', '成功删除所选的采购计划申请', 'info');
                            } else {
                                alert("登录超时删除失败，请重新登录");
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '该采购计划申请已上报，无法删除！', 'info');
        }
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

//根据大类编码获取子类列表
function getdictionaryschild(buycode) {
    $('#buychildlist').datagrid({
        url: 'buylist/search?buycode=' + buycode,
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
            {field: 'buycode', title: '采购计划号', width: 150, sortable: true},
            {field: 'buytype', title: '采购大类', width: 120, sortable: true},
            {field: 'buyname', title: '采购名称', width: 120, sortable: true},
            {field: 'plancode', title: '需求计划号', width: 150, sortable: true},
            {field: 'wzcode', title: '物资编码', width: 150, sortable: true},
            {field: 'wzname', title: '物资名称', width: 120, sortable: true},
            {field: 'unit', title: '单位', width: 120, sortable: true},
            {field: 'zjcode', title: '资金类型', width: 100, sortable: true},
            {field: 'zjname', title: '资金单位', width: 100, sortable: true},
            {
                field: 'buyprice',
                title: '采购单价',
                width: 120,
                sortable: true,
                editor: {type: 'numberbox', options: {required: true, precision: 2}}
            },
            {
                field: 'buynum',
                title: '采购数量',
                width: 120,
                sortable: true,
                editor: {type: 'numberbox', options: {required: true, precision: 2}}
            },
            {field: 'buymoney', title: '采购金额', width: 120, sortable: true},

        ]]
    });
}


/*新增及修改数据字典子类表单提交*/
function submitdictionarychildForm() {

    //获取更新更改的行的集合
    var row = $("#cgmxlist").datagrid('getSelections');
    var buycode = $("#buycode2").textbox('getValue')
    var buytype = $("#buytype1").textbox('getValue');
    var buyname = $("#buyname1").textbox('getValue');
    //DataGrid的更该行为不为0
    if (row.length) {
        $.ajax(
            {
                type: 'POST',
                url: "buylist/create",
                data: {arrayList: JSON.stringify(row), buycode: buycode, buytype: buytype, buyname: buyname},
//                 data : 'id=' + row.id,
                success: function (data) {
                    if (data === "success") {
                        $.messager.alert('提示', '新增成功！', 'info');
                        $('#c').window('close');
                        $('#buylist').datagrid('reload');
                        $('#buychildlist').datagrid('reload');    // 重新载入当前页面数据  
                    } else if (data.indexOf('已存在') > 0) {
                        $.messager.alert('提示', data, '').window({width: 750, height: 300});

                    } else if (data === "审批中") {
                        $.messager.alert('提示信息', '该需求已处于审批阶段,请勿操作', 'info');

                    }

                }
            });
    } else {

        $.messager.alert('提示信息', '请至少选择一行采购物资信息，请联系管理员！', 'warning');

    }

}
/* 获取选中的数据字典子类 */
function getSelectedDictionaryChild() {
    var row = $('#buychildlist').datagrid('getSelected');

    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'buylist/getallbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url2 = 'buylist/update';
                //清空表单前获取大类相关值
                var buycode = $("#buycode2").textbox('getValue');
                var buytype = $("#buytype1").textbox('getValue');
                var buyname = $("#buyname1").textbox('getValue');
                $('#buylistschild').form('clear');
                //清空表单后给大类赋值
                //清空表单后给大类赋值
                $('#buycode2').textbox('setValue', buycode);
                $('#buytype1').textbox('setValue', buytype);
                $('#buyname1').textbox('setValue', buyname);
                /* 加载数据 */
                $('#buylistschild').form('load', data);
                $("#buylist").datagrid('reload');
                $('#c').window('setTitle', '物资明细修改').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的字典子类 */
function deleteSelectedDictionaryChild() {
    var row = $('#buychildlist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'buylist/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#buychildlist').datagrid('reload');
                            $('#buylist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的物资明细', 'info');
                        } else if (data === "审批中") {
                            $.messager.alert('提示信息', '该需求已处于审批阶段,请勿操作', 'info');
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
function buysearch() {
    var buycode = $('#buycode1').textbox('getValue');
    var datetime1 = $('#datetime1').datebox('getText');
    var datetime2 = $('#datetime2').datebox('getText');
    var url = 'buy/search?&buycode=' + buycode + '&datetime1=' + datetime1 + '&datetime2=' + datetime2;
    $('#buylist').datagrid('options').url = url;
    $("#buylist").datagrid('reload');
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
function buyclean() {
    $('#buycode1').textbox('reset');
    $('#datetime1').combo('setText', '');
    $('#datetime2').combo('setText', '');
}

function comboevent() {


    $("#buyunit").combobox(
        {
            onSelect: function (rec) {

                $("#buyunitid").val(rec.id);


            }
        });
    $('#buytype').combogrid({
        required: true,
        multiple: false,
        panelWidth: 400,
        idField: 'code',
        textField: 'code',
        url: 'dictionaryschild/getbuytypelistbydecode',
        columns: [[
            {field: 'code', title: '采购大类', width: 70},
            {field: 'name', title: '采购名称', width: 150},

        ]]
    });
    $('#plancode1').combogrid({
        required: true,
        multiple: true,
        panelWidth: 750,
        idField: 'plancode',
        textField: 'plancode',
        url: 'plan/getplanlist',
        columns: [[
            {field: 'plancode', title: '需求计划号', width: 150},
            {field: 'plantype', title: '计划大类', width: 150},
            {field: 'planname', title: '计划名称', width: 150},
            {field: 'sbdate', title: '计划申报日期', width: 150},
            {field: 'sbunit', title: '申报部门', width: 150}
        ]]
    });

}

/* 上报所选数据 */
function sendSelected() {
    var row = $('#buylist').datagrid('getSelected');
    if (row) {
        if (row.spstatus === "草稿" && row.buysummoney !== "0") {
            $.messager.confirm('提示信息', '确定上报此条需求申请信息?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'buy/send.action',
                        data: 'id=' + row.id,
                        success: function () {
                            $.messager.alert('提示信息', '成功上报采购计划申请信息', 'info');
                            $('#buylist').datagrid('reload');
                            $('#buychildlist').datagrid('loadData', {total: 0, rows: []});
                        }
                    });
                }
            });
        } else if (row.spstatus === "一级审批退回") {
            $.messager.confirm('提示信息', '确定再次上报此条采购计划申请信息?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'buy/send.action',
                        data: 'id=' + row.id,
                        success: function () {
                            $.messager.alert('提示信息', '成功上报采购计划申请信息', 'info');
                            $('#buylist').datagrid('reload');
                            $('#buychildlist').datagrid('loadData', {total: 0, rows: []});
                        }
                    });
                }
            });
        } else if (row.buysummoney === "0") {
            $.messager.alert('提示信息', '审批金额为0,无法上报！', 'info');
            $('#buylist').datagrid('reload');
        } else {
            $.messager.alert('提示信息', '该采购计划申请已上报，请勿重复操作！');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}


function comgrid() {
    var temp = "";
    $("#plancode1").combogrid(
        {
            onSelect: function (rowIndex, rowData) {
                $('#cgmxlist').datagrid('clearChecked');
                temp = temp + rowData.plancode + ",";
                var url = 'buylist/searchformx?plancode=' + temp.substring(0, temp.length - 1);
                $('#cgmxlist').datagrid('options').url = url;
                $("#cgmxlist").datagrid('reload');
            },
            onUnselect: function () {
                var arr = $("plancode1").combogrid("getValues");
                if (arr.length > 0) {
                    temp = arr.join(',') + ",";
                } else {
                    temp = "";
                }
                var url = 'buylist/searchformx?plancode=' + arr;
                $('#cgmxlist').datagrid('options').url = url;
                $("#cgmxlist").datagrid('reload');
            }
        });
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
                /*加载获取数据*/
                //清空表单前获取大类相关值
                var plancode = $("#plancode4").textbox('getValue');
                var plancodeid = $("#plancodeid3").textbox('getValue');
                var plantype = $("#plantype3").textbox('getValue');
                var planname = $("#planname3").textbox('getValue');
                $('#planlistschild2').form('clear');
                //清空表单后给大类赋值
                //清空表单后给大类赋值
                $('#plancode4').textbox('setValue', plancode);
                $('#plancodeid3').textbox('setValue', plancodeid);
                $('#plantype3').textbox('setValue', plantype);
                $('#planname3').textbox('setValue', planname);
                /* 加载数据 */
                $('#planlistschild2').form('load', data);
                $("#planlist").datagrid('reload');
                $('#view').window('setTitle', '物资明细查看详情').window('open');


            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

function onClickCell(index, field) {
    if (editIndex !== index) {
        if (endEditing()) {
            $('#buychildlist').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#buychildlist').datagrid('getEditor', {index: index, field: field});
            if (ed) {
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex = index;
        } else {
            setTimeout(function () {
                $('#buychildlist').datagrid('selectRow', editIndex);
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
    if ($('#buychildlist').datagrid('validateRow', editIndex)) {
        $('#buychildlist').datagrid('endEdit', editIndex); //结束编辑
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
        var row = $("#buychildlist").datagrid('getChanges');

        //DataGrid的更该行为不为0
        if (row.length) {
            $.ajax(
                {
                    type: 'POST',
                    url: "buylist/update",
                    data: {arrayList: JSON.stringify(row),},
//                  data : 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $.messager.alert('提示', '保存成功！', 'info');
                            $('#buylist').datagrid('reload');
                            $('#buychildlist').datagrid('reload');    // 重新载入当前页面数据  
                        } else if (data === "审批中") {
                            $.messager.alert('提示', '该采购计划信息正在审批中,暂不支持修改！', 'info');
                            $('#buylist').datagrid('reload');
                            $('#buychildlist').datagrid('reload');
                        } else if (data.indexOf('大于') > 0) {
                            $.messager.alert('提示', data, '').window({width: 500, height: 300});
                            $('#buylist').datagrid('reload');
                            $('#buychildlist').datagrid('reload');
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

/* 查看审批记录 */
function views() {
    var row = $('#buylist').datagrid('getSelected');
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
        $('#p2').window('setTitle', '采购计划申请审批记录').window('open');
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}


function comgrid1() {


    $("#buytype").combogrid(
        {
            onSelect: function (rowIndex, rowData) {
                $('#buyname').textbox("setValue", rowData.name);
            }
        });
}