$(function() {
    $('#tklist').datagrid({
        url:'returntreasury/search',
        rownumbers:true,
        pagination:true,
        toolbar:toolbar1,
        singleSelect:true,
        striped:true,
        pageSize:10,
        pageList : [ 10, 20 ],
        columns:[[
            {field:'id',title:'id',hidden:true},
            {field:'tkcode',title:'退货流水号',width:150,sortable:true},
            {field:'tktype',title:'退货类型',width:100,sortable:true},
            {field:'sqdate',title:'申请时间',width:100,sortable:true},
            {field:'sqr',title:'申请人',width:150,sortable:true},
            {field:'tkczr',title:'退货操作人',width:150,sortable:true},
            {field:'tkreason',title:'退货原因',width:100,sortable:true},
            {field:'tkstatus',title:'退货状态',width:100,
                styler: function(value,row,index){

                    if (value==='已退货') {
                        return 'background-color:#78C06E;color:white;';
                    }
                    if (value.indexOf('未退货') > 0) {
                        return 'background-color:#3399D4;color:white;';
                    }

                }
            }
        ]],
        onClickRow : function(index,row) {
            var tkcode=row.tkcode;

            $('#tkcode2').textbox('setValue',tkcode);
            getdictionaryschild(tkcode);

        },
    });


    $('#tkmxlist').datagrid({
        rownumbers:true,
        pagination:true,
        singleSelect: false, //允许选择多行
        selectOnCheck: true,//true勾选会选择行，false勾选不选择行
        checkOnSelect: true, //
        striped:true,
        pageSize:10,
        idField:'id',
        pageList : [ 10, 20 ],
        columns:[[
            {field:'id',width:30,checkbox: true},
            {field:'rkcode',title:'入库流水号',width:150,sortable:true},
            {field:'contractbasicid',title:'合同号',width:150,sortable:true},
            {field:'buycode',title:'采购计划号',width:150,sortable:true},
            {field:'plancode',title:'需求计划号',width:150,sortable:true},
            {field:'wzcode',title:'物资编码',width:150,sortable:true},
            {field:'wzname',title:'物资名称',width:150,sortable:true},
            {field:'zjcode',title:'资金类型',width:100,sortable:true},
            {field:'zjname',title:'资金单位',width:100,sortable:true},
            {field:'planprice',title:'单价',width:120,sortable:true},
            {field:'unit',title:'单位',width:120,sortable:true},
            {field:'planbum',title:'合同采购量',width:120,sortable:true},
            {field:'sjnum',title:'实际收货量',width:120,sortable:true},
            {field:'sjmoney',title:'实际入库总金额',width:120,sortable:true},
        ]]
    });
    comboevent();
    //comgrid();
});

var url1;
var url2;
/*定义数据表格的按钮及事件*/
var toolbar1 = [ {
    text : '新增',
    iconCls : 'icon-add',
    handler : function() {
        $('#tk').form('clear');
        var curr_time = new Date();
        var str = curr_time.getFullYear()+"-";
        str += curr_time.getMonth()+1+"-";
        str += curr_time.getDate();
        $('#sqdate').datebox('setValue',str);
        url1 = 'returntreasury/create';
        $('#w').window('setTitle','新增采购退货申请').window('open');

    }
}, '-', {
    text : '修改',
    iconCls : 'icon-edit',
    handler : function() {
        getSelectedDictionary();


    }
}, '-', {
    text : '删除',
    iconCls : 'icon-cut',
    handler : function() {
        deleteSelectedDictionary();
    }
}, '-', {
    text : '退货',
    iconCls : 'icon-redo',
    handler : function() {
        sendSelected();

    }
}];
/*定义数据表格的按钮及事件*/
var toolbar2 = [ {
    text : '新增',
    iconCls : 'icon-add',
    handler : function() {
        comgrid();
        var tkcode=$("#tkcode2").textbox('getValue');
        $('#tklistschild').form('clear');
        $('#tkmxlist').datagrid('loadData', { total: 0, rows: [] });
        $('#tkcode2').textbox('setValue',tkcode);


        $('#c').window('setTitle','新增采购退货物资明细').window('open');

    }
}, '-', {
    text : '修改',
    iconCls : 'icon-edit',
    handler : function() {
        //url2 = 'dictionaryschild/update';
        save();
    }
}, '-', {
    text : '删除',
    iconCls : 'icon-cut',
    handler : function() {
        deleteSelectedDictionaryChild();
    }
}];



/*新增及修改数据字典大类表单提交*/
function submitform() {
    //移除disable属性,后台取值

    $('#tk').form('submit', {
        url : url1,
        onSubmit : function() {
            return $('#tk').form('validate');
        },
        success : function(data) {
            if(data==="success"){
                if (url1.indexOf('update') > 0) {
                    $('#tk').form('clear');
                    //清空表单后给大类赋值
                    $('#w').window('close');
                    $('#tklist').datagrid('reload');
                    $("#tkchildlist").datagrid('reload');
                    $.messager.alert('提示信息', '采购退货申请已成功修改', 'info');
                } else {
                    $('#tk').form('clear');
                    $('#w').window('close');
                    $('#tklist').datagrid('reload');
                    $.messager.alert('提示信息', '采购退货申请已成功录入', 'info');
                }
            }

            else{
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}
/* 编辑选中的数据字典 */
function getSelectedDictionary() {
    var row = $('#tklist').datagrid('getSelected');

    if (row) {
        if(row.tkstatus==="未退货")
        {
            $.ajax({
                async : false,
                dataType : 'json',
                url : 'returntreasury/getallbyid',
                data : 'id=' + row.id,
                success : function(data) {
                    /* 定义提交方法 */
                    url1 = 'returntreasury/update';
                    /* 加载数据 */
                    $('#tk').form('clear');
                    $('#tk').form('load', data);
                    $('#w').window('setTitle', '采购退货申请修改').window('open');
                }
            });
        }
        else{
            $.messager.alert('提示信息', '该采购入库申请已退货，无法修改！', 'info');
        }

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的字典大类 */
function deleteSelectedDictionary() {
    var row = $('#tklist').datagrid('getSelected');
    if (row) {
        if(row.tkstatus==="未退货"){
            $.messager.confirm('提示信息', '确定删除此数据?', function(r) {
                if (r) {
                    $.ajax({
                        async : false,
                        url : 'returntreasury/delete',
                        data : 'id=' + row.id,
                        success : function(data) {
                            if(data==="success"){
                                $('#tklist').datagrid('reload');
                                $('#tkchildlist').datagrid('reload');

                                $.messager.alert('提示信息', '成功删除所选的采购退货申请', 'info');
                            }else{
                                alert("登录超时删除失败，请重新登录");
                            }
                        }
                    });
                }
            });
        }
        else
        {
            $.messager.alert('提示信息', '该采购入库申请已退货，无法删除！', 'info');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}


//根据大类编码获取子类列表
function getdictionaryschild(tkcode){
    $('#tkchildlist').datagrid({
        url:'returntreasurylist/search?tkcode='+tkcode,
        rownumbers:true,
        pagination:true,
        toolbar:toolbar2,
        singleSelect:true,
        striped:true,
        //clickToEdit:false,
        //dblclickToEdit:true,
        onClickCell: onClickCell,
        onEndEdit: onEndEdit,
        pageSize:10,
        pageList : [ 10, 20 ],
        columns:[[
            {field:'rkcode',title:'入库流水号',width:150,sortable:true},
            {field:'contractbasicid',title:'合同号',width:150,sortable:true},
            {field:'buycode',title:'采购计划号',width:150,sortable:true},
            {field:'plancode',title:'需求计划号',width:150,sortable:true},
            {field:'wzcode',title:'物资编码',width:150,sortable:true},
            {field:'wzname',title:'物资名称',width:150,sortable:true},
            {field:'storehousecode',title:'仓库编码',width:150,sortable:true},
            {field:'storehousename',title:'仓库名称',width:150,sortable:true},
            {field:'zjcode',title:'资金类型',width:100,sortable:true},
            {field:'zjname',title:'资金单位',width:100,sortable:true},
            {field:'planprice',title:'单价',width:120,sortable:true},
            {field:'unit',title:'单位',width:120,sortable:true},
            {field:'planbum',title:'合同采购量',width:120,sortable:true},
            {field:'sjnum',title:'实际收货量',width:120,sortable:true},
            {field:'sjmoney',title:'实际入库总金额',width:120,sortable:true},
            {field:'sjthsl',title:'实际退货数量',width:120,editor:{type:'numberbox',options:{required: true,precision:2}}},
            {field:'sjthmoney',title:'实际退货总金额',width:120,sortable:true},

        ]]
    });
}

/*新增及修改数据字典子类表单提交*/
function submitdictionarychildForm() {

    //获取更新更改的行的集合
    var row = $("#tkmxlist").datagrid('getSelections');
    var tkcode=$("#tkcode2").textbox('getValue');
    //DataGrid的更该行为不为0
    if (row.length) {
        $.ajax(
            {
                type: 'POST',
                url: "returntreasurylist/create",
                data: { arrayList: JSON.stringify(row), tkcode:tkcode},
//                 data : 'id=' + row.id,
                success: function (data) {
                    if (data === "success") {
                        $.messager.alert('提示', '新增成功！','info');
                        $('#c').window('close');
                        $('#tklist').datagrid('reload');
                        $('#tkchildlist').datagrid('reload');    // 重新载入当前页面数据  
                    }
                    else if(data.indexOf('已存在') >0){
                        $.messager.alert('提示', data,'').window({ width: 750, height: 300 });

                    }
                    else if(data==="已退货"){
                        $.messager.alert('提示信息', '该退货信息已完成退货，无法新增物资明细信息', 'info');
                    }
                }
            });
    }

    else {

        $.messager.alert('提示信息', '请至少选择一行退货物资信息，请联系管理员！', 'warning');

    }

}


/* 获取选中的数据字典子类 */
function getSelectedDictionaryChild() {
    var row = $('#tkchildlist').datagrid('getSelected');

    if (row) {
        $.ajax({
            async : false,
            dataType : 'json',
            url : 'returntreasury/getallbyid',
            data : 'id=' + row.id,
            success : function(data) {
                /* 定义提交方法 */
                url2 = 'returntreasury/update';
                //清空表单前获取大类相关值
                var rkcode=$("#tkcode2").textbox('getValue');

                $('#tklistschild').form('clear');
                //清空表单后给大类赋值
                //清空表单后给大类赋值
                $('#tkcode2').textbox('setValue',tkcode);

                /* 加载数据 */
                $('#tklistschild').form('load', data);
                $("#tklist").datagrid('reload');
                $('#c').window('setTitle', '物资明细修改').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}
/* 删除选中的字典子类 */
function deleteSelectedDictionaryChild() {
    var row = $('#tkchildlist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function(r) {
            if (r) {
                $.ajax({
                    async : false,
                    url : 'returntreasurylist/delete',
                    data : 'id=' + row.id,
                    success : function(data) {
                        if(data==="success"){
                            $('#tkchildlist').datagrid('reload');
                            $('#tklist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的物资明细', 'info');
                        }
                        else if(data==="已退货")
                        {
                            $.messager.alert('提示信息', '该退货信息已完成退货，无法删除物资明细信息', 'info');
                        }

                        else{
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
function tksearch(){
    var tkcode=$('#tkcode1').textbox('getValue');
    var datetime1=$('#datetime1').datebox('getText');
    var datetime2=$('#datetime2').datebox('getText');
    var url = 'returntreasury/search?&tkcode='+tkcode+'&datetime1='+datetime1+'&datetime2='+datetime2;
    $('#tklist').datagrid('options').url = url;
    $("#tklist").datagrid('reload');
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
function tkclean(){
    $('#tkcode1').textbox('reset');
    $('#datetime1').combo('setText','');
    $('#datetime2').combo('setText','');
}

function comboevent(){



    $('#notecode').combogrid({
        required:true,
        multiple:true,
        panelWidth:450,
        idField:'notecode',
        textField:'notecode',
        url:'warehousing/getwarehousinglist',
        columns:[[
            {field:'notecode',title:'入库流水号',width:150,sortable:true},
            {field:'entryinfotype',title:'入库类型',width:100,sortable:true},
            {field:'storehousecode',title:'仓库编码',width:100,sortable:true},
            {field:'storehousename',title:'仓库名称',width:100,sortable:true},
            {field:'entrydate',title:'申请时间',width:100,sortable:true},
            {field:'consignee',title:'收料员',width:150,sortable:true},
            {field:'storeman',title:'保管员',width:100,sortable:true},
            {field:'note',title:'备注',width:100,sortable:true}
        ]]
    });

}
/* 上报所选数据 */
function sendSelected() {
    var row = $('#tklist').datagrid('getSelected');
    if (row) {
        if(row.tkstatus==="未退货"){
            $.messager.confirm('提示信息', '确定对此条退货信息进行退货处理?', function(r) {
                if (r) {
                    $.ajax( {
                        async: false,
                        url : 'returntreasury/tk.action',
                        data : 'id=' + row.id,
                        success : function(data) {
                            if(data==="success"){
                                $.messager.alert('提示信息', '成功完成退货操作','info');
                                $('#tklist').datagrid('reload');
                                $('#tkchildlist').datagrid('loadData', { total: 0, rows: [] });
                            }
                            else if(data.indexOf('大于') >0){
                                $.messager.alert('提示', data,'').window({ width: 750, height: 300 });

                            }
                            else if(data="存在数量为空"){
                                $.messager.alert('提示信息', '退货物资明细存在退货数量为0或者为添加退货明细,请删除或者重新添加','info');

                            }
                        }
                    });
                }
            });
        }
        else if(row.tkstatus==="已退货"){
            $.messager.alert('提示信息', '该退货申请已退货，请勿重复操作！');
        }


    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}


function comgrid(){
    var temp="";
    $("#notecode").combogrid(
        {
            onSelect : function(rowIndex, rowData) {
                $('#tkmxlist').datagrid('clearChecked');
                temp = temp  + rowData.notecode +  ",";
                var url = 'returntreasurylist/searchformx?notecode=' + temp.substring(0, temp.length-1);
                $('#tkmxlist').datagrid('options').url = url;
                $("#tkmxlist").datagrid('reload');
            },
            onUnselect:function() {
                var  arr = $("#notecode").combogrid("getValues");
                if(arr.length>0){
                    temp = arr.join(',')+",";
                }else{
                    temp = "";
                }
                var url = 'returntreasurylist/searchformx?notecode=' + arr;
                $('#tkmxlist').datagrid('options').url = url;
                $("#tkmxlist").datagrid('reload');
            }
        });
}

function onClickCell(index, field){
    if (editIndex !== index){
        if (endEditing()){
            $('#tkchildlist').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#tkchildlist').datagrid('getEditor', {index:index,field:field});
            if (ed){
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex = index;
        } else {
            setTimeout(function(){
                $('#tkchildlist').datagrid('selectRow', editIndex);
            },0);
        }
    }
}
function onEndEdit(index, row){
    var ed = $(this).datagrid('getEditor', {
        index: index,
        field: 'id'
    });
}

/*以下是批量保存代码------------------------------------------*/
var editIndex = undefined;

//结束编辑
function endEditing() {
    if (editIndex === undefined) { return true; }
    //校验指定的行，如果有效返回true
    if ($('#tkchildlist').datagrid('validateRow', editIndex)) {
        $('#tkchildlist').datagrid('endEdit', editIndex); //结束编辑
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
        var row = $("#tkchildlist").datagrid('getChanges');

        //DataGrid的更该行为不为0
        if (row.length) {
            $.ajax(
                {
                    type: 'POST',
                    url: "returntreasurylist/update",
                    data: { arrayList: JSON.stringify(row), },
//                  data : 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $.messager.alert('提示', '修改成功！','info');
                            $('#tklist').datagrid('reload');
                            $('#tkchildlist').datagrid('reload');    // 重新载入当前页面数据  
                        }
                        else if(data==="已退货")
                        {
                            $.messager.alert('提示', '该采购退货库信息已完成操作,暂不支持修改！','info');
                            $('#tklist').datagrid('reload');
                            $('#tkchildlist').datagrid('reload');
                        }else if(data.indexOf('大于') >0){
                            $.messager.alert('提示', data,'').window({ width: 750, height: 300 });
                            $('#tklist').datagrid('reload');
                            $('#tkchildlist').datagrid('reload');
                        }

                    }
                });
        }
        else  //如果没有修改数据，则提醒用户
        {
            $.messager.alert('提示信息', '您还没有修改信息！', 'warning');
        }
    }
    editIndex = undefined;
}
