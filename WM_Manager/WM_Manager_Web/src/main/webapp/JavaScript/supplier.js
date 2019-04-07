$(function () {
    $('#supplierlist').datagrid({
        url: 'supplier/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', hidden: true},
            {field: 'suppliercode', title: '供应商代码', width: 150, resizable: true},
            {field: 'suppliername', title: '供应商名称', width: 200, sortable: true, resizable: true},
            {field: 'legalrepresentative', title: '法定代表人', sortable: true, width: 100, resizable: true},
            {field: 'phone', title: '电话', width: 80, resizable: true},
            {field: 'fax', title: '传真', width: 80, resizable: true},
            {field: 'address', title: '住所', width: 100, resizable: true},
            {field: 'bank', title: '开户行', width: 100, resizable: true},
            {field: 'account', title: '银行账户', width: 200, resizable: true},
            {field: 'postcode', title: '邮编', width: 80, resizable: true},
            {field: 'taxid', title: '税务证号', width: 100, resizable: true},
            {field: 'supplyscope', title: '供货范围', width: 100, resizable: true},
            {field: 'registeredcapital', title: '注册资金', width: 100, resizable: true},
            {field: 'remark', title: '备注', width: 200, resizable: true}
        ]]
    });
});

var url;
/*定义数据表格的按钮及事件*/
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        $('#supplieradd').form('clear');
        url = 'supplier/create';
        $('#w').window('setTitle', '新增供应商').window('open');

    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        //清空表单
        $('#supplieradd').form('clear');
        getSelectsupplier()
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelectsupplier()
    }
}, '-', {
    text: '查看详情',
    iconCls: 'icon-search',
    handler: function () {
        viewSelected();
    }
}];


/* 删除选中的供货商信息*/
function deleteSelectsupplier() {
    var row = $('#supplierlist').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'supplier/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        if (data === "success") {
                            $('#supplierlist').datagrid('reload');
                            $.messager.alert('提示信息', '成功删除所选的供应商信息', 'info');
                        } else {
                            alert("登录超时删除失败，请重新登录");
                        }
                    }
                });
                $('#supplierlist').datagrid('reload');
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}

/*新增及修改供应商信息表单提交*/
function submitsupplierForm() {
    $('#supplieradd').form('submit', {
        url: url,
        onSubmit: function () {
            return $('#supplieradd').form('validate');
        },
        success: function (data) {
            if (data === "success") {
                if (url.indexOf('update') > 0) {
                    $('#supplieradd').form('clear');
                    $('#w').window('close');
                    $('#supplierlist').datagrid('reload');
                    $.messager.alert('提示信息', '供应商已成功修改', 'info');
                } else {
                    $('#supplieradd').form('clear');
                    $('#w').window('close');
                    $('#supplierlist').datagrid('reload');
                    $.messager.alert('提示信息', '供应商已成功录入', 'info');
                }
            } else if (data === "供应商编码已存在") {
                $.messager.alert('提示信息', '供应商编码已存在请重新输入', 'info');
            } else if (data === "供应商名称已存在") {
                $.messager.alert('提示信息', '供应商名称已存在请重新输入', 'info');
            } else {
                alert("登录超时保存失败，请重新登录");
            }
        }
    });
}

/* 修改选中的供应商信息 */
function getSelectsupplier() {
    var row = $('#supplierlist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'supplier/getbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 定义提交方法 */
                url = 'supplier/update';
                /* 加载数据 */
                $('#supplieradd').form('clear');
                $('#supplieradd').form('load', data);
                $('#w').window('setTitle', '供应商基本信息修改').window('open');

            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}


/* 查询数据 */
function suppliersearch() {
    var suppliercode = $('#suppliercode1').textbox('getValue');
    var suppliername = $('#suppliername1').textbox('getValue');
    var legalrepresentative = $('#legalrepresentative1').textbox('getValue');
    var url = 'supplier/search?&suppliercode=' + suppliercode + '&suppliername=' + suppliername + '&legalrepresentative=' + legalrepresentative;
    $('#supplierlist').datagrid('options').url = url;
    $("#supplierlist").datagrid('reload');
}

/* 清空查询数据 */
function suppliersearchclean() {
    $('#suppliername1').textbox('reset');
    $('#legalrepresentative1').textbox('reset');
    $('#suppliercode1').textbox('reset');
}


/*查看详情==================================================================================*/
function viewSelected() {
    var row = $('#supplierlist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'supplier/getbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /* 加载数据 */
                $('#supplierdetail').form('clear');
                $('#supplierdetail').form('load', data);

                setTimeout(function () {
                    /*延时打开窗口*/
                    $('#v').window('setTitle', '供应商基本信息').window('open');
                }, 300);
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}