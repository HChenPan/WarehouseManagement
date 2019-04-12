$(function () {
    var spuserid = $("#spuserid").val();
    var spuser = $("#spuser").val();
    $('#buylist').datagrid({
        url: 'buy/searchsplist',
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
    comboevent();
    comgrid();
});

var url1;
var url2;
/*定义数据表格的按钮及事件*/
var toolbar1 = [{
    text: '审批',
    iconCls: 'icon-edit',
    handler: function () {
        var spuser = $("#spuser").val();
        var spuserid = $("#spuserid").val();
        getSelected(spuser, spuserid);

    }
}, '-', {
    text: '审批记录',
    iconCls: 'icon-search',
    handler: function () {
        views();
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
        // toolbar:toolbar2,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'buycode', title: '采购计划号', width: 150, sortable: true},
            {field: 'buytype', title: '采购大类', width: 120, sortable: true},
            {field: 'buyname', title: '采购名称', width: 120, sortable: true},
            {field: 'plancode', title: '需求计划号', width: 150, sortable: true},
            {field: 'wzcode', title: '物资编码', width: 150, sortable: true},
            {field: 'wzname', title: '物资名称', width: 120, sortable: true},
            {field: 'modelspcification', title: '型号规格', width: 120, sortable: true},
            {field: 'unit', title: '单位', width: 120, sortable: true},
            {field: 'zjcode', title: '资金类型', width: 100, sortable: true},
            {field: 'zjname', title: '资金单位', width: 100, sortable: true},
            {field: 'buyprice', title: '采购单价', width: 120, sortable: true},
            {field: 'buynum', title: '采购数量', width: 120, sortable: true},
            {field: 'buymoney', title: '采购金额', width: 120, sortable: true},

        ]]
    });
}

/*新增及修改数据字典子类表单提交*/
function submitdictionarychildForm() {
    $('#buylistschild').form('submit', {
        url: url2,
        onSubmit: function () {
            return $('#buylistschild').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url2.indexOf('update') > 0) {
                    //清空表单前获取大类相关值

                    var buycode = $("#buycode2").textbox('getValue')
                    var buytype = $("#buytype1").textbox('getValue');
                    var buyname = $("#buyname1").textbox('getValue');
                    $('#buylistschild').form('clear');
                    //清空表单后给大类赋值
                    $('#buycode2').textbox('setValue', buycode);
                    $('#buytype1').textbox('setValue', buytype);
                    $('#buyname1').textbox('setValue', buyname);
                    $('#c').window('close');
                    $('#buychildlist').datagrid('reload');
                    $("#buylist").datagrid('reload');
                    $.messager.alert('提示信息', '物资明细已成功修改', 'info');
                } else {
                    //清空表单前获取大类相关值
                    var buycode = $("#buycode2").textbox('getValue');
                    var buytype = $("#buytype1").textbox('getValue');
                    var buyname = $("#buyname1").textbox('getValue');
                    $('#buylistschild').form('clear');
                    //清空表单后给大类赋值
                    $('#buycode2').textbox('setValue', buycode);
                    $('#buytype1').textbox('setValue', buytype);
                    $('#buyname1').textbox('setValue', buyname);
                    $('#c').window('close');
                    $('#buychildlist').datagrid('reload');
                    $("#buylist").datagrid('reload');
                    $.messager.alert('提示信息', '物资明细已成功录入', 'info');
                }
            } else if (data === "该物资明细已存在") {
                $.messager.alert('提示信息', '该物资明细已存在,请勿重复新增', 'info');
            } else if (data === "大于") {
                $.messager.alert('提示信息', '采购数量大于物资需求剩余量,请重新设置', 'info');
            } else if (data === "审批中") {
                $.messager.alert('提示信息', '该需求已处于审批阶段,请勿操作', 'info');
            } else {
                $.messager.alert('提示信息', '登录超时保存失败,请重新登录', 'info');
            }
        }
    });
}

/*新增及修改数据字典子类表单提交
function submitdictionarychildForm1() {
	$('#planlistschild1').form('submit', {
		url : url2,
		onSubmit : function() {
			return $('#planlistschild1').form('validate');
		},
		success : function(data) {
			if(data==="success"){
				if (url2.indexOf('update') > 0) {
					//清空表单前获取大类相关值
					var plancode=$("#plancode3").textbox('getValue');
		        	var plancodeid=$("#plancodeid2").textbox('getValue');
		        	var plantype=$("#plantype2").textbox('getValue');
		        	var planname=$("#planname2").textbox('getValue');
					$('#planlistschild1').form('clear');
					//清空表单后给大类赋值
					$('#plancode3').textbox('setValue',plancode);
		        	$('#plancodeid2').textbox('setValue',plancodeid);
		        	$('#plantype2').textbox('setValue',plantype);
		        	$('#planname2').textbox('setValue',planname);
					$('#cw').window('close');
					$('#planchildlist').datagrid('reload');
					$("#planlist").datagrid('reload');
					$.messager.alert('提示信息', '物资明细已成功修改', 'info');
				} else {
					//清空表单前获取大类相关值
					var plancode=$("#plancode3").textbox('getValue');
		        	var plancodeid=$("#plancodeid2").textbox('getValue');
		        	var plantype=$("#plantype2").textbox('getValue');
		        	var planname=$("#planname2").textbox('getValue');
					$('#planlistschild1').form('clear');
					//清空表单后给大类赋值
					$('#plancode3').textbox('setValue',plancode);
		        	$('#plancodeid2').textbox('setValue',plancodeid);
		        	$('#plantype2').textbox('setValue',plantype);
		        	$('#planname2').textbox('setValue',planname);
					$('#cw').window('close');
					$('#planchildlist').datagrid('reload');
					$("#planlist").datagrid('reload');
					$.messager.alert('提示信息', '物资明细已成功录入', 'info');
				}
			}else if(data==="该物资明细已存在"){
				$.messager.alert('提示信息', '该物资明细已存在,请勿重复新增', 'info');
			}else if(data==="审批中")
				{
				$.messager.alert('提示信息', '该需求已处于审批阶段,请勿操作', 'info');
				}
			else{
				$.messager.alert('提示信息', '登录超时保存失败,请重新登录', 'info');
			}
		}
	});
}*/

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
    $("#buytype").combobox(
        {
            onChange: function (rec) {


                var buytype = $('#buytype').combobox('getValue');
                var url = 'dictionaryschild/getDictionarysChild?code=' + buytype;
                $.ajax({
                    url: url,
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        if (data !== null) {
                            $("#buyname").textbox('setValue', data.name);


                        } else {
                            $("#buyname").textbox('setValue', '');

                        }

                    }
                });
            }
        });


    $("#buyunit").combobox(
        {
            onChange: function (rec) {


                var buyunit = $('#buyunit').combobox('getValue');
                var url = 'department/getDepart?name=' + buyunit;
                $.ajax({
                    url: url,
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        if (data !== null) {

                            $("#buyunitid").textbox('setValue', data.id);


                        } else {

                            $("#buyunitid").textbox('setValue', '');
                        }

                    }
                });
            }
        });

}

/* 上报所选数据 */
function sendSelected() {
    var row = $('#buylist').datagrid('getSelected');
    if (row) {
        if (row.spstatus === "草稿") {
            $.messager.confirm('提示信息', '确定上报此条需求申请信息?', function (r) {
                if (r) {
                    $.ajax({
                        async: false,
                        url: 'buy/send.action',
                        data: 'id=' + row.id,
                        success: function () {
                            $.messager.alert('提示信息', '成功上报采购计划申请信息', 'info');
                            $('#buylist').datagrid('reload');
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
                        }
                    });
                }
            });
        } else {
            $.messager.alert('提示信息', '该采购计划申请已上报，请勿重复操作！');
        }
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}


function comgrid() {
    $("#plancode1").combogrid(
        {
            onSelect: function (rowIndex, rowData) {
                $('#wzcode1').textbox("setValue", rowData.wzcode);
                $('#wzname1').textbox("setValue", rowData.wzname);
                $('#buynum1').textbox("setValue", rowData.synum);
                $('#buyprice1').textbox("setValue", rowData.spprice);
                $('#unit1').textbox("setValue", rowData.unit);

            }
        });
}


/* 审批选中的数据 */
function getSelected(spuser, spuserid) {
    var row = $('#buylist').datagrid('getSelected');

    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'buy/getallbyid',
            data: 'id=' + row.id,
            success: function (data) {
                //加载数据 
                $('#buyapproval').form('clear');
                $('#buyapproval').form('load', data);
                $('#spuser').textbox('setValue', spuser);
                $('#spuserid').textbox('setValue', spuserid);
                $('#buysummoney').textbox('setValue', data.buysummoney);
                $('#buytype').textbox('setValue', data.buytype);
                $('#sp').window('setTitle', '需求申请审批').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/*同意审批提交*/
function submitagreeform() {
    $('#spresult').textbox('setValue', "同意");
    $('#buyapproval').form('submit', {
        url: 'buy/approve',
        onSubmit: function () {

            return $('#buyapproval').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                var spuser = $("#spuser").val();
                var spuserid = $("#spuserid").val();
                $('#buyapproval').form('clear');
                getSelected(spuser, spuserid);
                $('#sp').window('close');
                $('#buylist').datagrid('reload');
                $('#buychildlist').datagrid('loadData', {total: 0, rows: []});
                $.messager.alert('提示信息', '同意审批操成功', 'info');
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/*退回审批提交*/
function submitbackform() {
    $('#spresult').textbox('setValue', "退回");
    $('#buyapproval').form('submit', {
        url: 'buy/approve',
        onSubmit: function () {

            return $('#buyapproval').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                var spuser = $("#spuser").val();
                var spuserid = $("#spuserid").val();
                $('#buyapproval').form('clear');
                getSelected(spuser, spuserid);
                $('#sp').window('close');
                $('#buylist').datagrid('reload');
                $('#buychildlist').datagrid('loadData', {total: 0, rows: []});
                $.messager.alert('提示信息', '退回审批操成功', 'info');
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 查看审批记录 */
function views() {
    var row = $('#buylist').datagrid('getSelected');
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
                {field: 'spadvice', title: '审批意见', width: 200, sortable: true},
            ]]
        });
        $('#p1').window('setTitle', '需求申请审批记录').window('open');
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}