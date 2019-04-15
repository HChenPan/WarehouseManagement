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
    <title>领料退库</title>
    <%@ include file="../common/head.jsp" %>

    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js"></script>
    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js"></script>
    <script src="${ctx}/JavaScript/cancellingstocks.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<%
    User user = (User) request.getSession().getAttribute("user");
%>
<input type="hidden" class="easyui-textbox" id="tkrid" value="<%=user.getId()%>"/>
<input type="hidden" id="departmentname" value="<%=user.getDepartment()%>"  style="width:100%;"/>


<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="search" class="easyui-panel"
             data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:50px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>退库单号</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="TKcodes" style="width: 150px;"/>
                    </td>
                    <td>退库日期</td>
                    <td>
                        <input id="datetime1" class="easyui-datebox" style="width: 150px; height: 25px"/>——&nbsp;
                        <input id="datetime2" class="easyui-datebox" style="width: 150px; height: 25px"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'"onclick="cancellingstocksclean()">清空条件</a> &nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="cancellingstockssearch()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="cancellingstockslist" style="height:50%;"></table>
            <table class="easyui-datagrid" id="cancellingstockschildlist" style="height:50%;"></table>
        </div>
    </div>
</div>
<div id="w" class="easyui-window" title="新增退库申请单" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:290px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="cancellingstocks" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>申请日期</td>
                    <td>
                        <input id="sqdate" class="easyui-datebox" name="sqdate" data-options="required:true" style="width: 180px; height: 25px"/>
                    </td>
                    <td>申请人</td>
                    <td>
                        <input id="sqr" class="easyui-textbox" name="sqr" data-options="required:true" style="width: 180px; height: 25px"/>
                    </td>
                </tr>
                <tr>
                    <td>退库原因</td>
                    <td colspan="3">
                        <input class="easyui-textbox" name="note" id="note" data-options="required:true,multiline:true" style="width: 100%;height:70px;"/>
                        <input type="hidden" name="id" id="id"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:20px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitform()">保存数据</a>
        </div>
    </div>
</div>
<div id="view" class="easyui-window" title="退库单详情" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:290px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="cancellingstocksview" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>申请日期</td>
                    <td>
                        <input class="easyui-datebox" name="sqdate" data-options="readonly:true" style="width: 180px; height: 25px"/>
                    </td>
                    <td>申请人</td>
                    <td>
                        <input class="easyui-textbox" name="sqr" data-options="readonly:true" style="width: 180px; height: 25px"/>
                    </td>
                </tr>
                <tr>
                    <td>退库原因</td>
                    <td colspan="3">
                        <input class="easyui-textbox" name="note" data-options="readonly:true,multiline:true" style="width: 100%;height:70px;"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div id="c" class="easyui-window" title="新增退库单单物资表" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:1000px;height:480px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="cancellingstockswz" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>退库单号</td>
                    <td>
                        <input class="easyui-textbox" id="TKcode1" name="TKcode" data-options="readonly:true" style="width:750px"/>
                        <input type="hidden" name="id" />
                    </td>
                </tr>
                <tr>
                    <td>领料单号</td>
                    <td>
                        <select id="callslipcode" name="callslipcode" class="easyui-combogrid" style="width:100%" data-options=""></select>
                    </td>
                </tr>
                <tr>
                    <td>退库物资明细</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <table id="tkmxlist" class="easyui-datagrid" style="height:400px"></table>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="cancellingstockswzForm()">保存数据</a>
        </div>
    </div>
</div>

<div id="view1" class="easyui-window" title="委外申请单物资表详情" data-options="modal:true,closed:true,iconCls:'icon-search'" style="width:800px;height:400px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="cancellingstockswzview" method="post">
            <table cellpadding="5" align="center"> </table>
        </form>
    </div>
</div>

</body>
</html>
