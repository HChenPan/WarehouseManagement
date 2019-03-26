<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/3/19
  Time: 05:16 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<head>
    <link href="${ctx}/css/main.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/statics/jqueryeasyui/themes/material-teal/easyui.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/statics/jqueryeasyui/themes/icon.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/statics/js/jquery-1.8.0.min.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/jquery.easyui.mobile.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/easyloader.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
    $(function () {
        if ("<%=session.getAttribute("user")%>" === "null") {
            alert("对不起，您尚未登录或者登录超时。");
            window.location = "${ctx}/login.do";
        }
    });
</script>

