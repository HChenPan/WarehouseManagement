<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/15
  Time: 07:27 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>工作流程</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/JavaScript/workflow.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/statics/calendarTime/calendarTime.js"></script>
</head>

<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:42px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>流程名称</td>
                    <td>
                        <input class="easyui-combobox" id="sptypecode1" style="width: 180px;" data-options="valueField:'code', textField:'name',limitToList:true,multiple:false,editable:true,url:'dictionaryschild/getSptypelistbydecode'"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="declareunitsearch()">查询</a> &nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="declareunitsearchclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>

        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="workflowlist"></table>
        </div>
    </div>
</div>
<div id="w" class="easyui-window" title="新增审批流程" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:260px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="workflow" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>审批类型</td>
                    <td>
                        <input class="easyui-combobox" id="sptypecode" name="sptypecode" style="width: 180px;" data-options="required:true, valueField:'code', textField:'name',url:'dictionaryschild/getSptypelistbydecode'"/>
                         <input type="hidden" class="easyui-textbox" name="id"/>
                    </td>
                    <td>审批级别</td>
                    <td>
                        <input class="easyui-combobox" id="spnode" name="spnode" style="width: 180px;" data-options="required:true,valueField:'code', textField:'name',limitToList:true,multiple:false,editable:true,url:'dictionaryschild/getSpjblistbydecode'"/>
                    </td>
                </tr>
                <tr>
                    <td>审批金额下限</td>
                    <td>
                        <select class="easyui-combobox" style="width: 180px;" data-options="required:true,editable:false,limitToList:true" id="spmoneylowlimit" name="spmoneylowlimit">
                            <option value=""></option>
                            <option value="0">0</option>
                            <option value="100">100</option>
                            <option value="200">200</option>
                            <option value="99999999">99999999</option>
                        </select>
                    </td>
                    <td>审批金额上限</td>
                    <td>
                        <select class="easyui-combobox" style="width: 180px;" data-options="editable:false,limitToList:true" id="spmoneyuplimit" name="spmoneyuplimit">
                            <option value="0">0</option>
                            <option value="100">100</option>
                            <option value="200">200</option>
                            <option value="99999999">99999999</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>下一级审批级别</td>
                    <td>
                        <input class="easyui-combobox" id="nextnode" name="nextnode" style="width: 180px;" data-options="valueField:'code', textField:'name',limitToList:true,multiple:false,editable:true"/>
                        <input type="hidden" class="easyui-textbox" name="nextnodename" id="nextnodename"/>
                    </td>
                    <td>回退审批级别</td>
                    <td>
                        <input class="easyui-combobox" id="backnode" name="backnode" style="width: 180px;" data-options="valueField:'code', textField:'name',limitToList:true,multiple:false,editable:true"/>
                        <input type="hidden" class="easyui-textbox" name="backnodename" id="backnodename"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitworkflowform()">保存数据</a>
        </div>
    </div>
</div>


</body>
</html>

