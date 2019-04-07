<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/6
  Time: 09:20 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>仓库编码</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/JavaScript/warehousenum.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:42px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>仓库编码</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="stockcode1" style="width: 180px;"/>
                    </td>
                    <td>仓库名称</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="stockname1" style="width: 180px;"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="declareunitsearch()">查询仓库</a> &nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="declareunitsearchclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>

        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="warehousenumlist"> </table>
        </div>
    </div>
</div>

<div id="w" class="easyui-window" title="新增仓库数据" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:750px;height:250px;padding:10px;">
    <div style="padding:10px 20px 20px 60px">
        <form id="warehousenum" method="post">
            <table cellpadding="5">
                <tr>
                    <td>仓库编码</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="stockcode" id="stockcode2" data-options="required:true" style="width:180px"/>
                        <input type="hidden" name="id"/>
                    </td>
                    <td>仓库名称</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="stockname" data-options="required:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>所属部门</td>
                    <td>
                        <input class="easyui-combobox" id="ssunitid" name="ssunitid" style="width: 180px;" panelHeight="auto" data-options="valueField:'id', textField:'name',url:'department/getall',required:true"/>
                    </td>
                    <td>仓库类型</td>
                    <td>
                        <input class="easyui-combobox" id="stocktype" name="stocktype" style="width: 180px;" panelHeight="auto" data-options="valueField:'id', textField:'name',url:'dictionaryschild/getcklistbydecode',required:true"/>
                    </td>
                </tr>
                <tr>
                    <td>仓库发货人</td>
                    <td colspan="3">
                        <input class="easyui-combobox" id="fhr" name="fhr" style="width:  100%;" data-options="required:true,valueField:'id', textField:'text',multiple:true,limitToList:true,url:'user/getdeptuserlist'"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:10px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitwarehouseform()">保存数据</a>
        </div>
    </div>
</div>


<div id="c" class="easyui-window" title="仓库详情" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:600px;height:250px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="warehousechildlist" method="post" enctype="multipart/form-data">
            <table cellpadding="5">
                <tr>
                    <td style="width:60px">仓库编码</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="stockcode" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" name="id"/>
                    </td>
                    <td>仓库名称</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="stockname" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>所属部门</td>
                    <td>
                        <input class="easyui-combobox" name="ssunitid" style="width: 180px;" panelHeight="auto" data-options="valueField:'id', textField:'name',url:'department/getall',readonly:true"/>
                    </td>
                    <td>仓库类型</td>
                    <td>
                        <input class="easyui-combobox"  name="stocktype" style="width: 180px;" panelHeight="auto" data-options="valueField:'id', textField:'name',url:'dictionaryschild/getcklistbydecode',readonly:true"/>
                    </td>
                </tr>
                <tr>
                    <td>仓库发货人</td>
                    <td colspan="3">
                        <input class="easyui-combobox" name="fhr" style="width:  100%;" data-options="readonly:true,valueField:'id', textField:'text',multiple:true,limitToList:true,url:'user/getdeptuserlist'"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>

