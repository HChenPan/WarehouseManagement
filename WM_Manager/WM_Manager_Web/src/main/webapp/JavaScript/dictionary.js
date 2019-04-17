var decode;
$(function () {
    $('#dictionarylist').datagrid({
        url: 'dictionarys/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar1,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', hidden: true},
            {field: 'dcode', title: '字典编码', width: 150, sortable: true},
            {field: 'dname', title: '字典描述', width: 250, sortable: true}
        ]],
        onClickRow: function (index, row) {
            dcode = row.dcode;
            dname = row.dname;
            dictionarysid = row.id;
            decode = row.dcode;
            $('#dcode').textbox('setValue', dcode);
            $('#dname').textbox('setValue', dname);
            $('#dictionarysid').textbox('setValue', dictionarysid);
            getdictionaryschild(dcode);

        },
    });

});

var url1;
var url2;
/*定义数据表格的按钮及事件*/
var toolbar1 = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        url1 = 'dictionarys/create';
        $('#w').window('setTitle', '新增数据字典节点').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        getSelectedDictionary();
        /*setTimeout(function() {
            $('#u').window('setTitle','修改数据字典节点').window('open');
        }, 500);*/

    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedDictionary();
    }
}];
/*定义数据表格的按钮及事件*/
var toolbar2 = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        url2 = 'dictionaryschild/create';
        $("#zname").textbox('textbox').attr('readonly', false);
        $('#c').window('setTitle', '新增数据字典内容').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        //url2 = 'dictionaryschild/update';
        getSelectedDictionaryChild();
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectedDictionaryChild();
    }
}];


/*新增及修改数据字典大类表单提交*/
function submitdictionaryform() {
    //移除disable属性,后台取值
    $("[disabled]").each(function () {
        if (parseInt($(this).val()) !== -1) {
            $(this).attr("disabled", false);
        }
    });
    $('#dictionary').form('submit', {
        url: url1,
        onSubmit: function () {
            return $('#dictionary').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url1.indexOf('update') > 0) {
                    $('#dictionary').form('clear');
                    //清空表单后给大类赋值
                    $('#w').window('close');
                    $('#dictionarylist').datagrid('reload');
                    $.messager.alert('提示信息', '数据字典节点已成功修改', 'info');
                } else {
                    $('#dictionary').form('clear');
                    $('#w').window('close');
                    $('#dictionarylist').datagrid('reload');
                    $.messager.alert('提示信息', '数据字典节点已成功录入', 'info');
                }
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 编辑选中的数据字典 */
function getSelectedDictionary() {
    var row = $('#dictionarylist').datagrid('getSelected');

    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'dictionarys/getdictionarylistbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url1 = 'dictionarys/update';
                /* 加载数据 */
                $('#dictionary').form('clear');
                $('#dictionary').form('load', data);
                $("#dcode").textbox({disabled: true});
                $('#w').window('setTitle', '字典大类修改').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的字典大类 */
function deleteSelectedDictionary() {
    var row = $('#dictionarylist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'dictionarys/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#dictionarylist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的字典大类', 'info');
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


//根据大类编码获取子类列表
function getdictionaryschild(dcode) {
    $('#dictionaryschildlist').datagrid({
        url: 'dictionaryschild/search?dcode=' + dcode,
        rownumbers: true,
        pagination: true,
        toolbar: toolbar2,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'dname', title: '字典大类', width: 120, sortable: true},
            {field: 'code', title: '子类编码', width: 150, sortable: true},
            {field: 'name', title: '子类描述', width: 220, sortable: true},
            {field: 'note', title: '备注', width: 120, sortable: true},
        ]]
    });
}

/*新增及修改数据字典子类表单提交*/
function submitdictionarychildForm() {
    $('#dictionaryschild').form('submit', {
        url: url2,
        onSubmit: function () {
            return $('#dictionaryschild').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url2.indexOf('update') > 0) {
                    //清空表单前获取大类相关值
                    var dcode = $("#dcode").textbox('getValue');
                    var dname = $("#dname").textbox('getValue');
                    var dictionarysid = $("#dictionarysid").textbox('getValue');
                    $('#dictionaryschild').form('clear');
                    //清空表单后给大类赋值
                    $('#dcode').textbox('setValue', dcode);
                    $('#dname').textbox('setValue', dname);
                    $('#dictionarysid').textbox('setValue', dictionarysid);
                    $('#c').window('close');
                    $('#dictionaryschildlist').datagrid('reload');
                    $.messager.alert('提示信息', '数据字典子类已成功修改', 'info');
                } else {
                    //清空表单前获取大类相关值
                    var dcode = $("#dcode").textbox('getValue');
                    var dname = $("#dname").textbox('getValue');
                    var dictionarysid = $("#dictionarysid").textbox('getValue');
                    $('#dictionaryschild').form('clear');
                    //清空表单后给大类赋值
                    $('#dcode').textbox('setValue', dcode);
                    $('#dname').textbox('setValue', dname);
                    $('#dictionarysid').textbox('setValue', dictionarysid);
                    $('#c').window('close');
                    $('#dictionaryschildlist').datagrid('reload');
                    $.messager.alert('提示信息', '数据字典子类已成功录入', 'info');
                }
            } else if (data === "子类编码已经存在") {
                $.messager.alert('提示信息', '该子类编码已经存在请重新输入', 'info');
            } else if (data === "子类描述已经存在") {
                $.messager.alert('提示信息', '该子类描述已经存在请重新输入', 'info');
            } else if (data === "唯一币种已经存在无法添加") {
                $.messager.alert('提示信息', '唯一币种已经存在无法添加', 'info');
            } else {
                $.messager.alert('提示信息', '登录超时保存失败,请重新登录', 'info');
            }
        }
    });
}

/* 获取选中的数据字典子类 */
function getSelectedDictionaryChild() {
    $("#zname").textbox('textbox').attr('readonly', true);
    var row = $('#dictionaryschildlist').datagrid('getSelected');

    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'dictionaryschild/getdictionarychildlistbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url2 = 'dictionaryschild/update';
                //清空表单前获取大类相关值
                var dcode = $("#dcode").textbox('getValue');
                var dname = $("#dname").textbox('getValue');
                var dictionarysid = $("#dictionarysid").textbox('getValue');
                $('#dictionaryschild').form('clear');
                //清空表单后给大类赋值
                $('#dcode').textbox('setValue', dcode);
                $('#dname').textbox('setValue', dname);
                $('#dictionarysid').textbox('setValue', dictionarysid);
                /* 加载数据 */
                $('#dictionaryschild').form('load', data);
                $('#c').window('setTitle', '字典大类修改').window('open');
            }
        });

    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/* 删除选中的字典子类 */
function deleteSelectedDictionaryChild() {
    var row = $('#dictionaryschildlist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'dictionaryschild/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#dictionaryschildlist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的字典子类', 'info');
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

function dictionarysearch() {
    var searchdedevicename = $('#searchdedevicename').textbox('getValue');

    var url = 'dictionarys/search?&dname=' + searchdedevicename;
    $('#dictionarylist').datagrid('options').url = url;
    $("#dictionarylist").datagrid('reload');
}

function dictionarysearchclean() {
    $('#searchdedevicename').textbox('reset');

}

function dchildsearch() {
    var searchdchild = $('#searchdchild').textbox('getValue');

    var url = 'dictionaryschild/search?&name=' + searchdchild + "&dcode=" + decode;
    $('#dictionaryschildlist').datagrid('options').url = url;
    $("#dictionaryschildlist").datagrid('reload');
}

function dchildsearchclean() {
    $('#searchdchild').textbox('reset');

}