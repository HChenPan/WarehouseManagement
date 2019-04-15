<%@ page import="com.hchenpan.pojo.User" %><%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/13
  Time: 12:40 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>领料单审批</title>
    <%@ include file="../common/head.jsp" %>

    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js"></script>
    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js"></script>
    <script src="${ctx}/JavaScript/callslipapproval.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<%
    User user = (User) request.getSession().getAttribute("user");
%>
<input type="hidden" id="departmentid" value="<%=user.getDepartmentid()%>"   style="width:100%;"/>
<input type="hidden" id="departmentname" value="<%=user.getDepartment()%>"  style="width:100%;"/>
<input type="hidden" id="userid" value="<%=user.getId()%>"   style="width:100%;"/>
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p" class="easyui-panel" data-options="region:'north',split:false,border:false"
             style="padding:2px;background: #fafafa;height:45px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>领料单号</td>
                    <td>
                        <input class="easyui-textbox" id="callslipcode1" style="width: 180px"/>
                    </td>

                    <td>领料大类</td>
                    <td>
                        <input class="easyui-combobox" id="callsliptype1" data-options="limitToList:true,valueField:'id',textField:'name',url:'dictionaryschild/getbillbydecode'"/>
                    </td>
                    <td>申请时间</td>
                    <td>
                        <input id="datetime1" class="easyui-datebox" style="width: 150px; height: 25px"/>——&nbsp;
                        <input id="datetime2" class="easyui-datebox" style="width: 150px; height: 25px"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="callslipsearch()">查询记录</a>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="searchclean()">清空条件</a>
                        &nbsp;&nbsp;
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="callsliplist"></table>
            <table class="easyui-datagrid" id="callslipgoodslist"></table>
        </div>
    </div>
</div>

<div id="sp" class="easyui-window" title="固定资产采购审批" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:430px;height:250px;pading:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="callslipapproval" method="post">
            <table cellpadding="8" align="center">
                <tr>
                    <td>审批人</td>
                    <td width="280px">
                        <input class="easyui-textbox" id="spuser" name="spuser" value=${user.realname }  style="width:100%;"/>
                        <input type="hidden" class="easyui-textbox" name="spuserid" id="spuserid" value=${user.id }/>
                        <input type="hidden" name="id" id="id"/>
                    </td>
                </tr>
                <tr style="padding-top:15px;">
                    <td>审批意见</td>
                    <td width="280px">
                        <input class="easyui-textbox" data-options="multiline:true" name="spadvice" style="width: 100%;"/>
                        <input type="hidden" class="easyui-textbox" id="spresult" name="spresult"/>
                        <input type="hidden" class="easyui-textbox" id="spcode" name="spcode"/>
                        <input type="hidden" class="easyui-textbox" id="money" name="money"/>
                        <input type="hidden" class="easyui-textbox" id="llcode" name="llcode"/>
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

<div id="p2" class="easyui-window" title="合同审批记录" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:100%;height:100%;padding:10px;">
    <table class="easyui-datagrid" id="approvallist" align="center" style="height:auto;"></table>
</div>


</body>
</html>

