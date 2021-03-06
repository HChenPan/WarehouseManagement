<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/4
  Time: 01:42 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>审批类型级别配置</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/JavaScript/sptypeSplevel.js" type="text/javascript"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:42px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>审批类型</td>
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
            <table class="easyui-datagrid" id="sptypesplevellist"></table>
        </div>
    </div>
</div>
<div id="w" class="easyui-window" title="新增审批配置" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:260px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="sptypesplevel" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>审批类型</td>
                    <td>
                        <input class="easyui-combobox" id="sptypecode" name="sptypecode" style="width: 180px;" data-options="required:true, valueField:'code', textField:'name',limitToList:true,multiple:false,editable:true,url:'dictionaryschild/getSptypelistbydecode'"/>
                        <input type="hidden" name="id"/>
                    </td>
                    <td>审批级别</td>
                    <td>
                        <input class="easyui-combobox" id="splevelcode" name="splevelcode" style="width: 180px;" data-options="required:true, valueField:'code', textField:'name',limitToList:true,multiple:false,editable:true,url:'dictionaryschild/getSpjblistbydecode'"/>
                    </td>
                </tr>
                <tr>
                    <td>审批人</td>
                    <td colspan="3">
                        <input class="easyui-combobox" id="spusersId" name="spusersId" style="width:  100%;" data-options="required:true,valueField:'id', textField:'text',multiple:true,limitToList:true,url:'user/getdeptuserlist'"/>
                    </td>
                </tr>
                <tr>
                    <td>审批备注</td>
                    <td colspan="3">
                        <input class="easyui-textbox" id="spnote" name="spnote" style="width: 100%;height:45px;"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitsptypesplevelform()">保存数据</a>
        </div>
    </div>
</div>
</body>
</html>
