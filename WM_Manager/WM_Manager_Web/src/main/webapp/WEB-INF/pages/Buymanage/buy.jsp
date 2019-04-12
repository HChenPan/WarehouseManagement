<%@ page import="com.hchenpan.pojo.User" %><%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/10
  Time: 11:47 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>采购计划申请</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js" type="text/javascript"></script>
    <script src="${ctx}/JavaScript/buy.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<%
    User user = (User) request.getSession().getAttribute("user");
%>
<input type="hidden" id="departmentname" value="<%=user.getDepartment()%>"/>
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p1" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:50px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>采购计划号</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="buycode1" style="width: 150px;"/>
                    </td>
                    <td>申请时间</td>
                    <td>
                        <input id="datetime1" class="easyui-datebox" style="width: 150px; height: 25px"/>——&nbsp;
                        <input id="datetime2" class="easyui-datebox" style="width: 150px; height: 25px"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="buysearch()">查询</a>&nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="buyclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="buylist" style="height:50%;"></table>
            <table class="easyui-datagrid" id="buychildlist" style="height:50%;"></table>
        </div>
    </div>
</div>
<div id="w" class="easyui-window" title="新增数据字典大类" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:370px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="buy" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>采购大类</td>
                    <td>
                        <input class="easyui-combogrid" id="buytype" name="buytype" style="width: 180px;" panelHeight="auto" data-options="required:true"/>
                        <input type="hidden" name="id" />
                    </td>
                    <td>采购大类名称</td>
                    <td>
                        <input class="easyui-textbox" id="buyname" name="buyname" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>采购日期</td>
                    <td>
                        <input id="buydate" class="easyui-datebox" name="buydate" data-options="required:true" style="width: 180px; height: 25px"/>
                    </td>
                    <td>采购部门</td>
                    <td>
                        <input class="easyui-combobox" id="buyunit" name="buyunit" style="width: 180px;" panelHeight="auto" data-options="valueField:'name', textField:'name',url:'department/getall',required:true"/>
                        <input type="hidden" name="buyunitid" />
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <input class="easyui-textbox" name="note" data-options="required:true,multiline:true" style="width: 100%;height:70px;"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitform()">保存数据</a>
        </div>
    </div>
</div>
<div id="c" class="easyui-window" title="新增数据字典子类" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:450px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="buylistschild" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>采购计划号</td>
                    <td>
                        <input class="easyui-textbox" id="buycode2" name="buycode" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" name="id"/>

                    </td>
                    <td>采购大类</td>
                    <td>
                        <input class="easyui-textbox"  name="buytype" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>采购计划号</td>
                    <td>
                        <input class="easyui-textbox" id="buyname1" name="buyname" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>需求计划号</td>
                    <td>
                        <select  name="plancode" class="easyui-combogrid" style="width:100%" data-options=""></select>
                    </td>
                </tr>
                <tr>
                    <td>采购计划物资明细</td>
                </tr>
                <tr>
                    <td colspan="8">
                        <table id="cgmxlist" class="easyui-datagrid"></table>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitdictionarychildForm()">保存数据</a>
        </div>
    </div>
</div>
<div id="p2" class="easyui-window" title="固定资产采购审批记录" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:100%;height:100%;padding:10px;">
    <table class="easyui-datagrid" id="approvallist" align="center" style="height:auto;"></table>
</div>
<div id="view" class="easyui-window" title="新增数据字典子类" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:550px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="buylistschild1" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>采购计划号</td>
                    <td>
                        <input class="easyui-textbox"  name="buycode" data-options="readonly:true" style="width:180px"/>
                    </td>
                    <td>需求计划号</td>
                    <td>
                        <input class="easyui-textbox" id="plancode1" name="plancode" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>物资编码</td>
                    <td>
                        <input class="easyui-combogrid" data-options="required:true, panelWidth:450,idField:'code',textField:'code',url:'sparepartcode/getallwz',columns:[[{field:'code',title:'物资编码',width:120},{field:'name',title:'物资名称',width:120},{field:'modelSpecification',title:'规格型号',width:120},]]" style="width:180px"id="wzcode1" name="wzcode"/>
                    </td>
                    <td>物资名称</td>
                    <td>
                        <input class="easyui-textbox" id="wzname1" name="wzname" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>采购数量</td>
                    <td>
                        <input class="easyui-textbox" id="buynum1" name="buynum" data-options="required:true" style="width:180px"/>
                    </td>
                    <td>采购单价</td>
                    <td>
                        <input class="easyui-textbox" id="buyprice1" name="buyprice" data-options="required:true" style="width:180px"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>

