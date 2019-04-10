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
            {field: 'price', title: '单价', width: 110}
        ]]
    });
});


/*定义数据表格的按钮及事件*/
var toolbar = [{
    text: '库存结转',
    iconCls: 'icon-search',
    handler: function () {
        $('#kcjz').form('clear');
        $('#w').window('setTitle', '库存结转').window('open');
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

//采用jquery easyui loading css效果 
function ajaxLoading() {
    $("<div class=\"datagrid-mask\"></div>").css({
        display: "block",
        width: "100%",
        height: $(window).height()
    }).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在结转，请稍候。。。").appendTo("body").css({
        "z-index": "9999",
        display: "block",
        left: ($(document.body).outerWidth(true) - 190) / 2,
        top: ($(window).height() - 45) / 2
    });
}

function ajaxLoadEnd() {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}

function submitform() {
    //$('#w2').window('setTitle','库存结转').window('maximize').window('open');
    //document.getElementById('loading').style.display = "block";
    //获取更新更改的行的集合
    var stockyearmon = $("#stockyearmon1").val();
    var stocklastyearmon = getPreMonth(stockyearmon);
    //DataGrid的更该行为不为0
    $.ajax(
        {
            type: 'POST',
            url: "stock/jz",
            beforeSend: ajaxLoading,//发送请求前打开进度条
            data: {stockyearmon: stockyearmon, stocklastyearmon: stocklastyearmon},
//                data : 'id=' + row.id,
            success: function (data) {
                //setTimeout(function(){
                ajaxLoadEnd();//任务执行成功，关闭进度条
                if (data === "success") {
                    $('#w').window('close');
                    $('#kclist').datagrid('reload');
                    $.messager.alert('提示信息', '当月库存已成功进行结转', 'info');
                } else if (data === "上月未结转") {
                    $('#w').window('close');
                    $.messager.alert('提示信息', '上月尚未结转,本次结转无法进行', 'info');
                } else if (data === "该月已结转") {
                    $('#w').window('close');
                    $.messager.alert('提示信息', '该月已结转,请重新选择结转时间', 'info');
                } else if (data === "选取时间") {
                    $('#w').window('close');
                    $.messager.alert('提示信息', '该时间无法结转，请重新选择结转时间', 'info');
                } else {
                    alert("登录超时保存失败，请重新登录");
                }
                //},3000);
            }
        });
}


function getPreMonth(date) {
    var arr = date.split('-');
    var year = arr[0]; //获取当前日期的年份
    var month = arr[1]; //获取当前日期的月份
    var year2 = year;
    var month2 = parseInt(month) - 1;
    if (month2 === 0) {//如果是1月份，则取上一年的12月份
        year2 = parseInt(year2) - 1;
        month2 = 12;
    }
    if (month2 < 10) {
        month2 = '0' + month2;//月份填补成2位。
    }
    return year2 + '-' + month2;
}
