<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/12
  Time: 01:20 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>合同物资信息</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js" type="text/javascript"></script>
    <script src="${ctx}/JavaScript/contractgoods.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west',border:false,split:true" style="width:30%;height:100px;">
            <div class="easyui-layout" data-options="fit:true">
                <div id="p1" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:50px;">
                    <table cellpadding="5" cellspacing="1" border="0">
                        <tr>
                            <td>流水号</td>
                            <td>
                                <input class="easyui-textbox" type="text" id="serialsnumber1" style="width: 150px;"/>
                            </td>
                            <td>
                                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="contractsearch()">查询</a> &nbsp;&nbsp;
                                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="contractsearchclean()">清空条件</a>
                            </td>
                        </tr>
                    </table>
                </div>

                <div data-options="region:'center',border:false">
                    <table class="easyui-datagrid" id="contractbasiclist" data-options="fit:true"></table>
                </div>
            </div>
        </div>

        <div data-options="region:'east',border:false" style="width:70%;height:50%">
            <div class="easyui-layout" data-options="fit:true">
                <div data-options="region:'center',border:false">
                    <table class="easyui-datagrid" id="contractgoodslist" data-options="fit:true"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="c" class="easyui-window" title="新增物资信息" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:300px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="goodsform" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>合同流水号</td>
                    <td>
                        <input class="easyui-textbox" id="serialsnumber" name="contractbasicid" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>采购计划号</td>
                    <td>
                        <select class="easyui-combogrid" id="buylistid" name="buylistid" data-options=" panelWidth:620, idField:'buycode', textField:'buycode', multiple:true, url:'buy/getbuylist',columns:[[ {field:'buycode',title:'采购计划号',width:140}, {field:'buyname',title:'采购大类名称',width:130},{field:'buydate',title:'采购日期',width:100},{field:'buysummoney',title:'采购总金额',width:100},{field:'buyunit',title:'采购部门',width:150},]]" style="width:180px"></select>
                    </td>
                </tr>
                <tr>
                    <td>物资信息明细</td>
                </tr>
                <tr>
                    <td colspan="8">
                        <table id="searchgoodslist" class="easyui-datagrid" style="width: 1000px"></table>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitgoodsForm()">保存数据</a>
        </div>
    </div>
</div>


<div id="c1" class="easyui-window" title="协议制合同新增物资" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:450px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="xygoods" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>合同流水号</td>
                    <td>
                        <input class="easyui-textbox" id="serialsnumber2" name="contractbasicid" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>

                <tr>
                    <td>物资编码</td>
                    <td>
                        <input class="easyui-textbox" name="wzcode" id="wzcode1" style="width:180px"/> 
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
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitgoodsForm1()">保存数据</a>
        </div>
    </div>
</div>

</body>
</html>

