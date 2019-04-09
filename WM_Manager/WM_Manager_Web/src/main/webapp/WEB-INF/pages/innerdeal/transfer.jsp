<%@ page import="com.hchenpan.pojo.User" %><%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/7
  Time: 09:23 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>发货发料</title>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js"></script>
    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js"></script>
    <script src="${ctx}/JavaScript/transfer.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<%
    User user = (User) request.getSession().getAttribute("user");
%>
<input type="hidden" class="easyui-textbox" id="fhrid" value="<%=user.getId()%>"/>
<input type="hidden" id="departmentname" value="<%=user.getDepartment()%>"  style="width:100%;"/>

<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p1" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:50px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>申请编号</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="applytransfercode1" style="width: 150px;" />
                    </td>
                    <td>申请时间</td>
                    <td>
                        <input id="datetime1" class="easyui-datebox" style="width: 150px; height: 25px"> </input>——&nbsp;
                        <input id="datetime2" class="easyui-datebox" style="width: 150px; height: 25px"> </input>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="applytransfersearch()">查询</a> &nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="applytransferclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="applytransferlist"style="height:50%;"></table>
            <table class="easyui-datagrid" id="applytransferchildlist"style="height:50%;"></table>
        </div>
    </div>
</div>

<div id="view" class="easyui-window" title="查看内部交易调拨单明细" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:360px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="applytransferlistschild2" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>调拨申请单号</td>
                    <td>
                        <input class="easyui-textbox" name="applytransfercode" data-options="readonly:true" style="width:180px"/>
                        <input class="easyui-textbox" type="hidden" name="applytransfercodeid" />
                        <input class="easyui-textbox" type="hidden" name="sbunitid" />
                        <input class="easyui-textbox" type="hidden" name="sbunit" />
                        <input class="easyui-textbox" type="hidden" name="id" /></td>
                    <td>物资编码</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true," style="width:180px" name="wzcode"/>
                        <input class="easyui-textbox" type="hidden" name="wzid" />
                    </td>
                </tr>
                <tr>
                    <td>调出仓库</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 180px" name="dcck"/>
                    </td>
                    <td>调出仓库编号</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 180px" name="dcckcode"/>
                        <input class="easyui-textbox" type="hidden" name="dcckid" />
                    </td>
                </tr>
                <tr>
                    <td>物资名称</td>
                    <td>
                        <input class="easyui-textbox" name="wzname" data-options="readonly:true" style="width:180px"/>
                    </td>
                    <td>型号规格</td>
                    <td>
                        <input class="easyui-textbox" name="modelspcification" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>申请数量</td>
                    <td>
                        <input class="easyui-textbox" name="sqnum" data-options="readonly:true" style="width:180px"/>
                        <input class="easyui-textbox" type="hidden" name="kucunmax" />
                    </td>
                    <td>发货数量</td>
                    <td>
                        <input class="easyui-textbox" name="realnum" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>单位</td>
                    <td>
                        <input class="easyui-textbox" name="unit" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>

