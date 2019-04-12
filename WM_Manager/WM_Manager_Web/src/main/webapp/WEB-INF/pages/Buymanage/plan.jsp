<%@ page import="com.hchenpan.pojo.User" %><%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/10
  Time: 11:17 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>需求申请</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js" type="text/javascript"></script>
    <script src="${ctx}/JavaScript/plan.js" type="text/javascript"></script>
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
                    <td>计划号</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="plancode1" style="width: 150px;"/>
                    </td>
                    <td>申请时间</td>
                    <td>
                        <input id="datetime1" class="easyui-datebox" style="width: 150px; height: 25px"/>——&nbsp;
                        <input id="datetime2" class="easyui-datebox" style="width: 150px; height: 25px"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="plansearch()">查询</a>&nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="planclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="planlist" style="height:50%;"></table>
            <table class="easyui-datagrid" id="planchildlist" style="height:50%;"></table>
        </div>
    </div>
</div>
<div id="w" class="easyui-window" title="新增数据字典大类" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:370px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="plan" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>计划大类</td>
                    <td>
                        <input class="easyui-combogrid" id="plantype" name="plantype" style="width: 180px;" panelHeight="auto" data-options="required:true"/>
                        <input type="hidden" name="id"/>
                    </td>
                    <td>计划名称</td>
                    <td>
                        <input class="easyui-textbox" id="planname" name="planname" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" name="planid"/>
                    </td>
                </tr>
                <tr>
                    <td>工程号</td>
                    <td>
                        <input class="easyui-combogrid" data-options="required:true" style="width:180px" id="projectcode" name="projectcode"/>
                        <input type="hidden" name="projectid" id="projectid"/>
                    </td>
                    <td>计划申报日期</td>
                    <td>
                        <input type="text" class="easyui-datebox" id="sbdate" name="sbdate" data-options="required:true" style="width: 180px; height: 25px"/>
                    </td>
                </tr>
                <tr>
                    <td>需求部门</td>
                    <td>
                        <input class="easyui-combobox" id="sbunit" name="sbunit" style="width: 180px;" panelHeight="auto" data-options="valueField:'name', textField:'name',url:'department/getall',required:true"/>
                        <input type="hidden" name="sbunitid" />
                    </td>
                </tr>
                <tr>
                    <td>用途说明</td>
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
        <form id="planlistschild" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>计划号</td>
                    <td>
                        <input class="easyui-textbox" id="plancode2" name="plancode" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" class="easyui-textbox" name="plancodeid" id="plancodeid1"/>
                    </td>
                    <td>计划大类</td>
                    <td>
                        <input class="easyui-textbox" id="plantype1" name="plantype" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>计划名称</td>
                    <td>
                        <input class="easyui-textbox" id="planname1" name="planname" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" name="id" />
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <input class="easyui-textbox" name="note" style="width:100%"/>
                    </td>
                </tr>
                <tr>
                    <td>物资编码</td>
                    <td>
                        <input class="easyui-textbox" name="wzcode" id="wzcode" style="width:180px"/> 
                    </td>
                    <td>物资名称</td>
                    <td>
                        <input class="easyui-textbox" name="wzname" id="wzname" style="width:180px"/> 
                        <a id="standardQueryBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
                    </td>
                </tr>
                <tr>
                    <td>物资明细表</td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table id="wzlist" class="easyui-datagrid" style="width: 1000px"></table>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitdictionarychildForm()">保存数据</a>
        </div>
    </div>
</div>

<div id="cw" class="easyui-window" title="新增数据字典子类" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:450px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="planlistschild1" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>计划号</td>
                    <td>
                        <input class="easyui-textbox" id="plancode3" name="plancode" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" class="easyui-textbox" name="plancodeid" id="plancodeid2"/>
                    </td>
                    <td>计划大类</td>
                    <td>
                        <input class="easyui-textbox" id="plantype2" name="plantype" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>计划名称</td>
                    <td>
                        <input class="easyui-textbox" id="planname2" name="planname" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" name="id" />
                    </td>
                    <td>物资编码</td>
                    <td>
                        <input class="easyui-combogrid" data-options="readonly:true, panelWidth:450,idField:'code',textField:'code',url:'sparepartcode/getallwz', columns:[[
                        {field:'code',title:'物资编码',width:120},{field:'name',title:'物资名称',width:120},]] " style="width:180px" id="wzcode2" name="wzcode"/>
                        <input class="easyui-textbox" type="hidden" name="wzid" id="wzid2"/>
                    </td>
                </tr>
                <tr>
                    <td>物资名称</td>
                    <td>
                        <input class="easyui-textbox" id="wzname2" name="wzname" data-options="readonly:true" style="width:180px"/>
                    </td>
                    <td>单位</td>
                    <td>
                        <input class="easyui-textbox" id="unit2" name="unit" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>单价</td>
                    <td>
                        <input class="easyui-textbox" id="price2" name="price" data-options="required:true" style="width:180px" validtype="number"/>
                    </td>
                    <td>计划数量</td>
                    <td>
                        <input class="easyui-textbox" id="plannum2" name="plannum" data-options="required:true" style="width:180px" validtype="number"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <input class="easyui-textbox" name="note" style="width:100%"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitdictionarychildForm1()">保存数据</a>
        </div>
    </div>
</div>
<div id="p2" class="easyui-window" title="固定资产采购审批记录" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:100%;height:100%;padding:10px;">
    <table class="easyui-datagrid" id="approvallist" align="center" style="height:auto;"></table>
</div>

<div id="view" class="easyui-window" title="新增数据字典子类" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:550px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="planlistschild2" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>计划号</td>
                    <td>
                        <input class="easyui-textbox" id="plancode4" name="plancode" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" class="easyui-textbox" name="plancodeid" id="plancodeid3"/>
                    </td>
                    <td>计划大类</td>
                    <td>
                        <input class="easyui-textbox" id="plantype3" name="plantype" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>计划名称</td>
                    <td>
                        <input class="easyui-textbox" id="planname3" name="planname" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" name="id" />
                    </td>
                    <td>物资编码</td>
                    <td>
                        <input class="easyui-combogrid" data-options="readonly:true, panelWidth:450, idField:'code',textField:'code',url:'sparepartcode/getallwz',columns:[[{field:'code',title:'物资编码',width:120},{field:'name',title:'物资名称',width:120},]]" style="width:180px"id="wzcode3" name="wzcode"/>
                        <input class="easyui-textbox" type="hidden" name="wzid" id="wzid3"/>
                    </td>
                </tr>
                <tr>
                    <td>物资名称</td>
                    <td>
                        <input class="easyui-textbox" id="wzname3" name="wzname" data-options="readonly:true" style="width:180px"/>
                    </td>
                    <td>单位</td>
                    <td>
                        <input class="easyui-textbox" id="unit3" name="unit" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>资金类型</td>
                    <td>
                        <input class="easyui-textbox" name="zjcode" data-options="readonly:true" style="width:180px"/>
                    </td>
                    <td>资金单位</td>
                    <td>
                        <input class="easyui-textbox" name="zjname" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>单价</td>
                    <td>
                        <input class="easyui-textbox" id="price3" name="price" data-options="readonly:true" style="width:180px" validtype="number"/>
                    </td>
                    <td>计划数量</td>
                    <td>
                        <input class="easyui-textbox" id="plannum3" name="plannum" data-options="readonly:true" style="width:180px" validtype="number"/>
                    </td>
                </tr>
                <tr>
                    <td>计划金额</td>
                    <td>
                        <input class="easyui-textbox" name="planmoney" data-options="readonly:true" style="width:180px" validtype="number"/>
                    </td>
                    <td>计划审批数量</td>
                    <td>
                        <input class="easyui-textbox" name="spnum" data-options="readonly:true" style="width:180px" validtype="number"/>
                    </td>
                </tr>
                <tr>
                    <td>计划审批金额</td>
                    <td>
                        <input class="easyui-textbox" name="spmoney" data-options="readonly:true" style="width:180px" validtype="number"/>
                    </td>
                    <td>剩余量</td>
                    <td>
                        <input class="easyui-textbox" name="synum" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <input class="easyui-textbox" name="note" data-options="readonly:true" style="width:100%"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>

