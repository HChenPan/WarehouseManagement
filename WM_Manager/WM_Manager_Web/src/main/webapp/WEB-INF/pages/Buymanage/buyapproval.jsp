<%@ page import="com.hchenpan.pojo.User" %><%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/10
  Time: 11:58 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>采购计划申请审批</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js" type="text/javascript"></script>
    <script src="${ctx}/JavaScript/buyapproval.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <table class="easyui-datagrid" id="buylist" style="height:50%;"></table>
    <table class="easyui-datagrid" id="buychildlist" style="height:50%;"></table>
</div>

<div id="sp" class="easyui-window" title="固定资产采购审批" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:430px;height:250px;pading:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="buyapproval" method="post">
            <table cellpadding="8" align="center">
                <tr>
                    <td>审批人</td>
                    <td width="280px">
                        <%
                            User user = (User) request.getSession().getAttribute("user");
                        %>
                        <input  class="easyui-textbox"  id="spuser" name="spuser"  value="<%=user.getRealname()%>" style="width:100%;"/>
                        <input type="hidden" class="easyui-textbox" name="spuserid" id="spuserid"  value="<%=user.getId()%>"/>
                        <input type="hidden" name="id" id="id"/>
                    </td>
                </tr>
                <tr style="padding-top:15px;">
                    <td>审批意见</td>
                    <td width="280px">
                        <input class="easyui-textbox" data-options="required:true,multiline:true" name="spadvice" style="width: 100%;"/>
                        <input type="hidden" class="easyui-textbox" id="spresult" name="spresult"/>
                        <input type="hidden" class="easyui-textbox" id="spcode" name="spcode"/>
                        <input type="hidden" class="easyui-textbox" id="buysummoney" name="buysummoney"/>
                        <input type="hidden" class="easyui-textbox" id="buytype" name="buytype"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px;margin-top:20px;">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitagreeform()">同意</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:20px;" data-options="iconCls:'icon-no'" onclick="submitbackform()">退回</a>
        </div>
    </div>
</div>

<div id="p1" class="easyui-window" title="固定资产采购审批记录" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:100%;height:100%;padding:10px;">
    <table class="easyui-datagrid" id="approvallist" align="center" style="height:auto;"></table>
</div>
</body>
</html>

