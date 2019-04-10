<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/10
  Time: 03:26 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>库存结转</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/JavaScript/stockjz.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/statics/calendarTime/calendarTime.js"></script>
</head>

<body class="easyui-layout">

<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <!-- 搜索框		 -->
        <!-- 库存物资列表 -->
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="kclist" data-options="fit:true"></table>
        </div>
    </div>
</div>

<!-- 查看详情 -->
<div id="w" class="easyui-window" title="库存物资详情" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:180px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="kcjz" method="post">
            <table cellpadding="4" align="center">
                <tr>
                    <td>库存结转月份</td>
                    <td>
                        <input class="easyui-datebox" id="stockyearmon1" name="stockyearmon" data-options="required:true,formatter:myformatter,parser:myparser" style="width: 180px;"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitform()">库存结转</a>
        </div>
    </div>
</div>
<script type="text/javascript">
    function myformatter(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        return y + '-' + (m < 10 ? ('0' + m) : m);
    }

    function myparser(s) {
        if (!s) return new Date();
        var ss = (s.split('-'));
        var y = parseInt(ss[0], 10);
        var m = parseInt(ss[1], 10);
        if (!isNaN(y) && !isNaN(m)) {
            return new Date(y, m - 1);
        } else {
            return new Date();
        }
    }
</script>


</body>
</html>
