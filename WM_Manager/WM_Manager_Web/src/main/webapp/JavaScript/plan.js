$(function () {
    var plancode;
    var plancodeid;
    var plantype;
    var planname;
    var a;
    var b;
    var url33;

    $('#planlist').datagrid({
        url: 'plan/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar1,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', title: 'id', hidden: true, width: 100},
            {field: 'plancode', title: '计划号', width: 150, sortable: true},
            {field: 'plantype', title: '计划大类', width: 100, sortable: true},
            {field: 'zjcode', title: '资金类型', width: 100, sortable: true},
            {field: 'zjname', title: '资金单位', width: 100, sortable: true},
            {field: 'planmoney', title: '计划金额', width: 100, sortable: true},
            {field: 'planspmoney', title: '计划审批金额', width: 100, sortable: true},
            {field: 'planname', title: '计划名称', width: 150, sortable: true},
            {field: 'sbdate', title: '计划申报日期', width: 100, sortable: true},
            {field: 'projectcode', title: '工程号', width: 100, sortable: true},
            {field: 'sqrname', title: '申请人', width: 100, sortable: true},
            {field: 'sbunit', title: '申报部门', width: 100, sortable: true},
            {field: 'note', title: '用途说明', width: 100, sortable: true},
            {field: 'spcode', title: 'spcode', hidden: true, width: 100},
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
            plancode = row.plancode;
            plancodeid = row.id;
            plantype = row.plantype;
            planname = row.planname;
            $('#plancode2').textbox('setValue', plancode);
            $('#plancodeid1').textbox('setValue', plancodeid);
            $('#plantype1').textbox('setValue', plantype);
            $('#planname1').textbox('setValue', planname);
            getdictionaryschild(plancodeid);

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
            {field: 'price', title: '单价', width: 200}
        ]]
    });


    $("#standardQueryBtn").click(function () {
        a = $("#wzcode").val();
        b = $("#wzname").val();
        if (a === "" && b === "") {
            $.messager.alert('提示信息', '尚未输入搜索条件,无法查询', 'info');
        } else {
            url33 = 'planlist/getallwz?&wzcode=' + a + '&wzname=' + b;
            $("#wzlist").datagrid('options').url = url33;
            $('#wzlist').datagrid('reload');
        }
    });
    //comboevent();
    comgrid();
});

var url1;
var url2;
/*定义数据表格的按钮及事件*/
var toolbar1 = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#plan').form('clear');
        comboevent();
        var curr_time = new Date();
        var str = curr_time.getFullYear() + "-";
        str += curr_time.getMonth() + 1 + "-";
        str += curr_time.getDate();
        $('#sbdate').datebox('setValue', str);
        var departmentname = $("#departmentname").val();
        $('#sbunit').combobox('setValue', departmentname);
        url1 = 'plan/create';
        $('#w').window('setTitle', '新增需求申请').window('open');
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
        plancode = $("#plancode2").textbox('getValue');
        plancodeid = $("#plancodeid1").textbox('getValue');
        plantype = $("#plantype1").textbox('getValue');
        planname = $("#planname1").textbox('getValue');
        $('#planlistschild').form('clear');
        $('#plancode2').textbox('setValue', plancode);
        $('#plancodeid1').textbox('setValue', plancodeid);
        $('#plantype1').textbox('setValue', plantype);
        $('#planname1').textbox('setValue', planname);
        url2 = 'planlist/create';
        $('#c').window('setTitle', '新增物资明细').window('maximize').window('open');
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
}, '-', {
    text: '查看详情',
    iconCls: 'icon-search',
    handler: function () {
        viewsall();
    }
}];

/*新增及修改数据字典大类表单提交*/
function submitform() {
    //移除disable属性,后台取值
    $('#plan').form('submit', {
        url: url1,
        onSubmit: function () {
            return $('#plan').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url1.indexOf('update') > 0) {
                    $('#plan').form('clear');
                    //清空表单后给大类赋值
                    $('#w').window('close');
                    $('#planlist').datagrid('reload');
                    $("#planchildlist").datagrid('reload');
                    $.messager.alert('提示信息', '需求申请已成功修改', 'info');
                } else {
                    $('#plan').form('clear');
                    //('#w').window('close');
                    $('#planlist').datagrid('reload');
                    $("#planchildlist").datagrid('reload');
                    $.messager.alert('提示信息', '需求申请已成功录入', 'info');
                }
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 编辑选中的数据字典 */
function getSelectedDictionary() {
    var row = $('#planlist').datagrid('getSelected');
    if (row) {
        if (row.spcode === "00") {
            $.ajax({
                async: false,
                dataType: 'json',
                url: 'plan/getallbyid',
                data: 'id=' + row.id,
                success: function (data) {
                    /* 定义提交方法 */
                    url1 = 'plan/update';
                    /* 加载数据 */
                    $('#plan').form('clear');
                    $('#plan').form('load', data);
                    $('#w').window('setTitle', '需求申请修改').window('open');
                }
            });
        } else {
            $.messager.alert('提示信息', '该需求申请已上报，无法修改！', 'info');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的字典大类 */
function deleteSelectedDictionary() {
    var row = $('#planlist').datagrid('getSelected');
    if (row) {
        if (row.spcode === "00") {
            $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'plan/delete',
                        data: 'id=' + row.id,
                        success: function (data) {
                            if (data === "success") {
                                $('#planlist').datagrid('reload');
                                $('#planchildlist').datagrid('reload');

                                $.messager.alert('提示信息', '成功删除所选的需求申请', 'info');
                            } else {
                                alert("登录超时删除失败，请重新登录");
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '该需求申请已上报，无法删除！', 'info');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

//根据大类编码获取子类列表
function getdictionaryschild(plancodeid) {
    $('#planchildlist').datagrid({
        url: 'planlist/search?plancodeid=' + plancodeid,
        rownumbers: true,
        pagination: true,
        toolbar: toolbar2,
        singleSelect: true,
        striped: true,
        onClickCell: onClickCell,
        onEndEdit: onEndEdit,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'plancode', title: '计划号', width: 150, sortable: true},
            {field: 'plantype', title: '计划大类', width: 120, sortable: true},
            {field: 'planname', title: '计划名称', width: 120, sortable: true},
            {field: 'wzcode', title: '物资编码', width: 120, sortable: true},
            {field: 'wzname', title: '物资名称', width: 120, sortable: true},
            {field: 'zjcode', title: '资金类型', width: 100, sortable: true},
            {field: 'zjname', title: '资金单位', width: 100, sortable: true},
            {field: 'unit', title: '单位', width: 120, sortable: true},
            {
                field: 'price',
                title: '单价',
                width: 120,
                sortable: true,
                editor: {type: 'numberbox', options: {required: true, precision: 2}}
            },
            {
                field: 'plannum',
                title: '计划数量',
                width: 120,
                sortable: true,
                editor: {type: 'numberbox', options: {required: true, precision: 2}}
            },
            {field: 'planmoney', title: '计划金额', width: 120, sortable: true},
            {field: 'spprice', title: '审批单价', width: 120, sortable: true},
            {field: 'spnum', title: '计划审批数量', width: 120, sortable: true},
            {field: 'spmoney', title: '计划审批金额', width: 120, sortable: true},
            {field: 'note', title: '备注', width: 120, sortable: true}
        ]]
    });
}

/*新增及修改数据字典子类表单提交*/
function submitdictionarychildForm() {
    //获取更新更改的行的集合
    var row = $("#wzlist").datagrid('getSelections');
    //DataGrid的更该行为不为0
    if (row.length) {
        $.ajax(
            {
                type: 'POST',
                url: "planlist/create",
                data: {
                    arrayList: JSON.stringify(row),
                    plancode: plancode,
                    plancodeid: plancodeid,
                    plantype: plantype,
                    planname: planname
                },
//                 data : 'id=' + row.id,
                success: function (data) {
                    if (data === "success") {
                        $.messager.alert('提示', '物资新增成功！', 'info');
                        //$('#c').window('close');
                        $('#wzname').textbox('reset');
                        $('#wzcode').textbox('reset');
                        $('#wzlist').datagrid('loadData', {total: 0, rows: []});
                        $('#planchildlist').datagrid('reload');
                    } else if (data === "审批中") {
                        $.messager.alert('提示', '此需求已经进入审批状态，无法新增物资！', 'error');
                        $('#wzname').textbox('reset');
                        $('#wzcode').textbox('reset');
                        $('#wzlist').datagrid('loadData', {total: 0, rows: []});
                        $('#planchlidlist').datagrid('reload');
                    } else if (data.indexOf('已存在') > 0) {
                        $.messager.alert('提示', data, '').window({width: 750, height: 300});
                        $('#wzname').textbox('reset');
                        $('#wzcode').textbox('reset');
                        $('#wzlist').datagrid('loadData', {total: 0, rows: []});
                    }
                }
            });
    } else {
        $.messager.alert('提示信息', '请至少选择一行物资信息，请联系管理员！', 'warning');
        $('#wzlist').datagrid('destroyFilter'); // 销毁过滤
    }
}


/* 获取选中的数据字典子类 */
function getSelectedDictionaryChild() {
    var row = $('#planchildlist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'planlist/getallbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url2 = 'planlist/update';
                //清空表单前获取大类相关值
                var plancode = $("#plancode3").textbox('getValue');
                var plancodeid = $("#plancodeid2").textbox('getValue');
                var plantype = $("#plantype2").textbox('getValue');
                var planname = $("#planname2").textbox('getValue');
                $('#planlistschild1').form('clear');
                //清空表单后给大类赋值
                //清空表单后给大类赋值
                $('#plancode3').textbox('setValue', plancode);
                $('#plancodeid2').textbox('setValue', plancodeid);
                $('#plantype2').textbox('setValue', plantype);
                $('#planname2').textbox('setValue', planname);
                /* 加载数据 */
                $('#planlistschild1').form('load', data);
                $("#planlist").datagrid('reload');
                $('#cw').window('setTitle', '物资明细修改').window('open');
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的字典子类 */
function deleteSelectedDictionaryChild() {
    var row = $('#planchildlist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'planlist/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#planchildlist').datagrid('reload');
                            $('#planlist').datagrid('reload');
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
function plansearch() {
    var plancode = $('#plancode1').textbox('getValue');
    var datetime1 = $('#datetime1').datebox('getText');
    var datetime2 = $('#datetime2').datebox('getText');
    var url = 'plan/search?&plancode=' + plancode + '&datetime1=' + datetime1 + '&datetime2=' + datetime2;
    $('#planlist').datagrid('options').url = url;
    $("#planlist").datagrid('reload');
}

/* 清空查询数据 */
function planclean() {
    $('#plancode1').textbox('reset');
    $('#datetime1').combo('setText', '');
    $('#datetime2').combo('setText', '');
}

function comboevent() {
    $("#sbunit").combobox({
        onSelect: function (rec) {
            $("#sbunitid").val(rec.id);
        }
    });
    $('#projectcode').combogrid({
        required: true,
        multiple: false,
        panelWidth: 300,
        idField: 'projectno',
        textField: 'projectname',
        url: 'projectnomanage/getall',
        columns: [[
            {field: 'projectno', title: '工程号', width: 150},
            {field: 'projectname', title: '工程名称', width: 250}

        ]]
    });
    $('#plantype').combogrid({
        required: true,
        multiple: false,
        panelWidth: 320,
        idField: 'code',
        textField: 'code',
        url: 'dictionaryschild/getplantypelistbydecode',
        columns: [[
            {field: 'code', title: '计划大类', width: 70},
            {field: 'name', title: '计划名称', width: 250}

        ]]
    });


}

/* 上报所选数据 */
function sendSelected() {
    var row = $('#planlist').datagrid('getSelected');
    if (row) {
        if (row.spstatus === "草稿" && row.planspmoney !== "0") {
            $.messager.confirm('提示信息', '确定上报此条需求申请信息?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'plan/send.action',
                        data: 'id=' + row.id,
                        success: function () {
                            $.messager.alert('提示信息', '成功上报需求申请信息', 'info');
                            $('#planlist').datagrid('reload');
                            $('#planchildlist').datagrid('loadData', {total: 0, rows: []});
                        }
                    });
                }
            });
        } else if (row.spstatus === "一级审批退回") {
            $.messager.confirm('提示信息', '确定再次上报此条需求申请信息?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'plan/send.action',
                        data: 'id=' + row.id,
                        success: function () {
                            $.messager.alert('提示信息', '成功上报需求申请信息', 'info');
                            $('#planlist').datagrid('reload');
                            $('#planchildlist').datagrid('loadData', {total: 0, rows: []});
                        }
                    });
                }
            });
        } else if (row.planspmoney === "0") {
            $.messager.alert('提示信息', '审批金额为0,无法上报！', 'info');

        } else {
            $.messager.alert('提示信息', '该需求申请已上报，请勿重复操作！');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}


function comgrid() {
    $("#wzcode1").combogrid({
        onSelect: function (rowIndex, rowData) {
            $('#wzid1').val(rowData.id);
            $('#wzname1').textbox("setValue", rowData.name);

            $('#unit1').textbox("setValue", rowData.unit);
            $('#price1').textbox("setValue", rowData.planprice);
        }
    });
    $("#projectcode").combogrid(
        {
            onSelect: function (rowIndex, rowData) {
                $('#projectid').val(rowData.id);

            }
        });

    $("#plantype").combogrid(
        {
            onSelect: function (rowIndex, rowData) {
                $('#planid').val(rowData.id);
                $('#planname').textbox("setValue", rowData.name);
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

/* 查看审批记录 */
function views() {
    var row = $('#planlist').datagrid('getSelected');
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
        $('#p2').window('setTitle', '需求申请审批记录').window('open');
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
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
    if ($('#planchildlist').datagrid('validateRow', editIndex)) {
        $('#planchildlist').datagrid('endEdit', editIndex); //结束编辑
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

function onClickCell(index, field) {
    if (editIndex !== index) {
        if (endEditing()) {
            $('#planchildlist').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#planchildlist').datagrid('getEditor', {index: index, field: field});
            if (ed) {
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex = index;
        } else {
            setTimeout(function () {
                $('#planchildlist').datagrid('selectRow', editIndex);
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
        var row = $("#planchildlist").datagrid('getChanges');
        //DataGrid的更该行为不为0
        if (row.length) {
            $.ajax(
                {
                    type: 'POST',
                    url: "planlist/update",
                    data: {arrayList: JSON.stringify(row),},
//                  data : 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $.messager.alert('提示', '修改成功！', 'info');
                            $('#planlist').datagrid('reload');
                            $('#planchildlist').datagrid('reload');    // 重新载入当前页面数据  
                        } else if (data === "审批中") {
                            $.messager.alert('提示', '该需求制作审批中，无法修改！', 'info');
                        } else {
                            $.messager.alert('提示信息', '保存失败，请联系管理员！', 'warning');
                            $('#planchildlist').datagrid('reload');
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
    var rows = $('#planchildlist').datagrid('getChanges');
    alert(rows.length + '行被修改!');
}

