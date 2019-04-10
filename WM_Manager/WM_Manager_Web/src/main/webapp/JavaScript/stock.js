$(function () {
    $('#kclist').datagrid({
        url: 'stock/search',
        rownumbers: true,
        pagination: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        pageSize: 10,
        pageList: [10, 20],
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'stockcode', title: '仓库编号', width: 110},
            {field: 'stockname', title: '仓库名称', width: 110},
            {field: 'wzcode', title: '物资编码', width: 150},
            {field: 'wzname', title: '物资名称', width: 150},
            {field: 'modelspcification', title: '型号规格', width: 150, align: 'center'},
            {field: 'unit', title: '计量单位', width: 110},
            {field: 'bqstart', title: '本期期初值', width: 110},
            {field: 'bqin', title: '本期入库量总和', width: 110},
            {field: 'bqout', title: '本期出库量总和', width: 110},
            {field: 'bqend', title: '本期期末量', width: 110},
            {field: 'zjcode', title: '资金编码', width: 110},
            {field: 'zjname', title: '资金单位', width: 110},
            {field: 'bqstartmoney', title: '本期期初金额', width: 110},
            {field: 'bqinmoney', title: '本期入库金额', width: 110},
            {field: 'bqoutmoney', title: '本期出库金额', width: 110},
            {field: 'bqendmoney', title: '本期期末金额', width: 110},
            {field: 'price', title: '单价', width: 110},


        ]]
    });
});


/*定义数据表格的按钮及事件*/
var toolbar = [{
    text: '查看详情',
    iconCls: 'icon-search',
    handler: function () {
        views();
    }
}];

function stocksearch() {
    var wzname = $('#wzname1').textbox('getValue');
    var wzcode = $('#wzcode1').textbox('getValue');
    var stockname = $('#stockname1').textbox('getValue');
    var stockcode = $('#stockcode1').textbox('getValue');
    var url = 'stock/search?&wzname=' + wzname + '&wzcode=' + wzcode + '&stockname=' + stockname + '&stockcode=' + stockcode;
    $('#kclist').datagrid('options').url = url;
    $("#kclist").datagrid('reload');
}

/* 清空查询数据 */
function stocksearchclean() {
    $('#wzname1').textbox('reset');
    $('#wzcode1').textbox('reset');
    $('#stockname1').textbox('reset');
    $('#stockcode1').textbox('reset');
}

/* 查看所选项目详情 */
function views() {
    var row = $('#kclist').datagrid('getSelected');
    if (row) {
        $.ajax({
            async: false,
            dataType: 'json',
            url: 'stock/getbyid',
            data: 'id=' + row.id,
            success: function (data) {
                /*加载获取数据*/
                $('#kcdetail').form('clear');
                $('#kcdetail').form('load', data);
                /*延时加载相关数据,打开窗口*/
                setTimeout(function () {
                    $('#w').window('setTitle', '库存物资详情').window('open');
                }, 300);
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行', 'info');
    }
}