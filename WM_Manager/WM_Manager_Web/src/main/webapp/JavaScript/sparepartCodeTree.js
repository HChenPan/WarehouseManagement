$(function () {
    $('#departlist').treegrid({
        url: 'sparepartcode/getrootlist',
        idField: 'id',
        treeField: 'name',
        lines: true,
        rownumbers: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        columns: [[
            {field: 'id', width: 100, hidden: true},
            {field: 'name', title: '备件名称', width: 300, sortable: true},
            {field: 'code', title: '备件编码', width: 300, sortable: true},
            {field: 'description', title: '备件所属', width: 150, sortable: true},
            {field: 'parentcode', width: 100, hidden: true},
            {field: '_parentId', width: 100, hidden: true}
        ]],
        loadFilter: myLoadFilter
    });
    comboevent();
});

function myLoadFilter(data, parentId) {
    function setData() {
        var todo = [];
        for (var i = 0; i < data.length; i++) {
            todo.push(data[i]);
        }
        while (todo.length) {
            var node = todo.shift();
            if (node.children) {
                node.state = 'closed';
                node.children1 = node.children;
                node.children = undefined;
                todo = todo.concat(node.children1);
            }
        }
    }

    setData(data);
    var tg = $(this);
    var opts = tg.treegrid('options');
    opts.onBeforeExpand = function (row) {
        if (row.children1) {
            tg.treegrid('append', {
                parent: row[opts.idField],
                data: row.children1
            });
            row.children1 = undefined;
            tg.treegrid('expand', row[opts.idField]);
        }
        return row.children1 === undefined;
    };
    return data;
}

var url;

/* 定义数据表格的按钮及事件 */
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        getselectnode();
        url = 'sparepartcode/create';
    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        getSelecteddepart();
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelecteddepart();
    }
}, '-', {
    text: '查看详情',
    iconCls: 'icon-search',
    handler: function () {
        getSelecteddetail();
        url = 'sparepartcode/update';
    }
}];

/* 为新增操作取得选中节点 */
function getselectnode() {
    var row = $('#departlist').treegrid('getSelected');
    if (row) {
        $('#departadd').form('clear');
        $("#description").combobox({disabled: false});
        $('#parentid').val(row.id);
        if (row.parentcode === undefined) {
            row.parentcode = "";
        }
        $('#parentcode').val(row.parentcode + row.code);
        $('#w').window('open');
        comboEvent();
    } else {
        $.messager.alert('提示信息', '请首先选择欲添加编码的上级');
    }
}

/* 数据表单提交 */
function submitdepartForm() {
    //移除disable属性,后台取值
    $("[disabled]").each(function () {
        if (parseInt($(this).val()) !== -1) {
            $(this).attr("disabled", false);
        }
    });
    $('#departadd').form(
        'submit',
        {
            url: url,
            onSubmit: function () {
                return $('#departadd').form('validate');
            },
            success: function (data) {
                if (data !== "error" && data !== "备件已经存在") {
                    data = eval('(' + data + ')');
                    if (url.indexOf('update') > 0) {
                        $.messager.alert('提示信息', '成功修改备件编码');
                        $('#departadd').form('clear');
                        $('#departlist').treegrid('reload');
                        $('#w').window('close');
                        setTimeout(function () {
                            $('#departlist').treegrid('expandTo', data.id).treegrid('select', data._parentId);
                        }, 500);
                    } else {
                        $.messager.alert('提示信息', '成功添加备件编码 ');
                        $('#departadd').form('clear');
                        $('#departlist').treegrid('reload');
                        $('#w').window('close');
                        setTimeout(function () {
                            $('#parentid').val(data._parentId);
                            $('#departlist').treegrid('expandTo', data.id).treegrid('select', data._parentId);
                        }, 500);
                    }
                } else if (data === "备件已经存在") {
                    $.messager.alert('提示信息', '该备件已经存在请重新输入', 'info');
                } else {
                    $.messager.alert('提示信息', '登录超时保存失败,请重新登录', 'info');
                }
            }
        });
}

/* 删除所选数据 */
function deleteSelecteddepart() {
    var row = $('#departlist').treegrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'sparepartcode/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data !== "error") {
                            $.messager.alert('提示信息', '成功删除备件编码');
                            $('#departlist').treegrid('reload');
                            setTimeout(function () {
                                $('#departlist').treegrid('expandTo', data);
                            }, 500);
                        } else {
                            $.messager.alert('提示信息', '登录超时保存失败,请重新登录', 'info');
                        }
                    }
                });
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}

/* 取得选中行数据并打开修改表单 */
function getSelecteddepart() {
    var row = $('#departlist').treegrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'sparepartcode/getallbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url = 'sparepartcode/update';
//				comboEvent();
                /* 加载数据 */
                $("#description").combobox({disabled: true});
                $('#departadd').form('clear');
                $('#departadd').form('load', data);
                $('#parentcode').val(row.parentcode);
                $('#w').window('setTitle', '修改物资').window('open');

                if ($("#description").combobox('getValue') !== "物资") {
                    $("#code").textbox({disabled: true});
                }

            }
        });


    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}

/* 取得选中行数据并打开表单明细 */
function getSelecteddetail() {
    var row = $('#departlist').treegrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'sparepartcode/getallbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 加载数据 */
                $('#detail').form('clear');
                $('#detail').form('load', data);
                $('#d').window('setTitle', '查看详情').window('open');
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}

/*新增、修改时下拉框控制*/
function comboEvent() {
    $("#description").combobox(
        {
            onChange: function (rec) {
                descriptionEvent();
            }
        });
}

function descriptionEvent() {
    if ($("#description").combobox('getValue') == "物资") {
        $("#code").textbox({disabled: true, readonly: true});
        $("#modelSpecification").textbox({disabled: false});
        $("#devicecode").textbox({disabled: false});
        $(".purchasetime").textbox({disabled: false});
        $("#planprice").textbox({disabled: false});
        $("#planprice").textbox({required: true});
        $("#remark").textbox({disabled: false});
        $("#unit").textbox({disabled: false});
        $("#ywname").textbox({disabled: false});
        $("#tuhao").textbox({disabled: false});
        $("#hostname").textbox({disabled: false});
        $("#supplycycle").textbox({disabled: false});
        $("#spareparttype").combobox({disabled: false});
        $("#stockmin").textbox({disabled: false});
        $("#currencyunit").textbox({disabled: false});
        $("#currencytype").textbox({disabled: false});
    } else {
        $("#code").textbox({disabled: false});
        $("#code").textbox({required: true});
        $("#modelSpecification").textbox({disabled: true});
        $("#devicecode").textbox({disabled: true});
        $(".purchasetime").textbox({disabled: true});
        $("#planprice").textbox({disabled: true});
        $("#remark").textbox({disabled: true});
        $("#unit").textbox({disabled: true});
        $("#ywname").textbox({disabled: true});
        $("#tuhao").textbox({disabled: true});
        $("#hostname").textbox({disabled: true});
        $("#supplycycle").textbox({disabled: true});
        $("#spareparttype").combobox({disabled: true});
        $("#stockmin").textbox({disabled: true});
        $("#currencyunit").textbox({disabled: true});
        $("#currencytype").textbox({disabled: true});
    }
}

$.extend($.fn.validatebox.defaults.rules, { //添加验证规则
    number: { //value值为文本框中的值
        validator: function (value) {
            var reg = /^\d+(\.\d+)?$/;
            return reg.test(value);
        },
        message: '提示信息:只能输入数字和小数点'
    }
});


function comboevent() {
    $("#spareparttype").combobox(
        {
            onChange: function (rec) {
                var spareparttype = $('#spareparttype').combobox('getValue');
                var url = 'dictionaryschild/getDictionarysChildByName?name=' + encodeURIComponent(spareparttype);
                $.ajax({
                    url: url,
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        if (data != null) {
                            $("#spareparttypecode").textbox('setValue', data.id);
                        } else {
                            $("#spareparttypecode").textbox('setValue', '');
                        }

                    }
                });
            }
        });

}