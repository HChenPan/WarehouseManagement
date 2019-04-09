<%@ page import="com.hchenpan.pojo.User" %><%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/7
  Time: 09:23 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>申请调拨</title>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js"></script>
    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js"></script>
    <script src="${ctx}/JavaScript/applytransfer.js" type="text/javascript"></script>
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
                        <input class="easyui-textbox" type="text" id="applytransfercode1" style="width: 150px;"/>
                    </td>
                    <td>申请时间</td>
                    <td>
                        <input id="datetime1" class="easyui-datebox" style="width: 150px; height: 25px"/>——&nbsp;
                        <input id="datetime2" class="easyui-datebox" style="width: 150px; height: 25px"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="applytransfersearch()">查询</a> &nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="applytransferclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="applytransferlist" style="height:50%;"> </table>
            <table class="easyui-datagrid" id="applytransferchildlist" style="height:50%;"> </table>
        </div>
    </div>
</div>

<div id="w" class="easyui-window" title="新增内部交易调拨单草稿" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:370px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="applytransfer" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>计划上报日期</td>
                    <td>
                        <input id="sbdate" class="easyui-datebox" name="sbdate" data-options="required:true" style="width: 180px; height: 25px"/>
                        <input type="hidden" name="id" id="id"/>
                    </td>
                    <td>需求部门</td>
                    <td>
                        <input class="easyui-combobox" id="sbunit" name="sbunit" style="width: 180px;" panelHeight="auto" data-options="valueField:'name', textField:'name',url:'department/getall',required:true"/>
                        <input class="easyui-textbox" type="hidden" name="sbunitid" id="sbunitid"/>
                    </td>
                </tr>
                <tr>
                    <td>调入仓库</td>
                    <td>
                        <input class="easyui-combobox" id="drck" name="drck" style="width: 180px;" panelHeight="auto" data-options="required:true"/>
                    </td>
                    <td>调入仓库编号</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true,readonly:true" style="width: 300px" name="drckcode" id="drckcode"/>
                        <input class="easyui-textbox" type="hidden" name="drckid" id="drckid"/>
                    </td>
                </tr>
                <tr>
                    <td>调出仓库</td>
                    <td>
                        <input class="easyui-combobox" id="dcck" name="dcck" style="width: 180px;" panelHeight="auto" data-options="valueField:'stockname', textField:'stockname',url:'warehousenum/getall',required:true"/>
                    </td>
                    <td>调出仓库编号</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true,readonly:true" style="width: 300px" name="dcckcode" id="dcckcode"/>
                        <input class="easyui-textbox" type="hidden" name="dcckid" id="dcckid"/></td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <input class="easyui-textbox" name="note" data-options="multiline:true" style="width: 100%;height:70px;"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitform()">保存数据</a>
        </div>
    </div>
</div>

<div id="c" class="easyui-window" title="新增内部交易调拨单明细" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:300px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="applytransferlistschild" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>调拨申请单号</td>
                    <td>
                        <input class="easyui-textbox" id="applytransfercode2" name="applytransfercode" data-options="readonly:true" style="width:180px"/>
                        <input class="easyui-textbox" type="hidden" name="applytransfercodeid" id="applytransfercodeid1"/>
                        <input class="easyui-textbox" type="hidden" name="sbunitid" id="sbunitid1"/>
                        <input class="easyui-textbox" type="hidden" name="sbunit" id="sbunit1"/>
                    </td>
                </tr>
                <tr>
                    <td>物资明细表</td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table id="addgoodslist" class="easyui-datagrid" style="width: 1100px"></table>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="applytransferlistschildForm()">保存数据</a>
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
                        <input class="easyui-textbox" type="hidden" name="applytransfercodeid"/>
                        <input class="easyui-textbox" type="hidden" name="sbunitid"/>
                        <input class="easyui-textbox" type="hidden" name="sbunit"/>
                        <input class="easyui-textbox" type="hidden" name="id"/>
                    </td>
                    <td>物资编码</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width:180px" name="wzcode"/>
                        <input class="easyui-textbox" type="hidden" name="wzid"/>
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
                    <td>单位</td>
                    <td>
                        <input class="easyui-textbox" name="unit" data-options="readonly:true" style="width:180px"/>
                    </td>
                    <td>申请数量</td>
                    <td>
                        <input class="easyui-textbox" name="sqnum" data-options="readonly:true" style="width:180px"/>
                        <input class="easyui-textbox" type="hidden" name="kucunmax"/>
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
                        <input class="easyui-textbox" type="hidden" name="dcckid"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
