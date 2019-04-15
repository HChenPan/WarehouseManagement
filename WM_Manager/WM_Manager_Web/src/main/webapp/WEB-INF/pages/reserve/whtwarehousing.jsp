<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/13
  Time: 12:39 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>无合同入库</title>
    <%@ include file="../common/head.jsp" %>

    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js"></script>
    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js"></script>
    <script src="${ctx}/JavaScript/whtwarehousing.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<input type="hidden" class="easyui-textbox" id="fhrid" value=${user.id }/>
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p1" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:50px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>入库单号</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="notecode1" style="width: 150px;"/>
                    </td>
                    <td>入库申请时间</td>
                    <td>
                        <input id="datetime1" class="easyui-datebox" style="width: 150px; height: 25px"/>——&nbsp;
                        <input id="datetime2" class="easyui-datebox" style="width: 150px; height: 25px"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="rksearch()">查询</a> &nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="rkclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="rklist" style="height:50%;"></table>
            <table class="easyui-datagrid" id="rkchildlist" style="height:50%;"></table>
        </div>
    </div>
</div>
<div id="w" class="easyui-window" title="新增数据字典大类"data-options="modal:true,closed:true,iconCls:'icon-save'"style="width:800px;height:370px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="rk" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>仓库名称</td>
                    <td>
                        <input class="easyui-combobox" id="storehousename" name="storehousename" style="width: 180px;" panelHeight="auto" data-options="required:true"/>
                        <input type="hidden" name="id" id="id"/>
                    </td>
                    <td>仓库编码</td>
                    <td>
                        <input class="easyui-textbox" id="storehousecode" name="storehousecode" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" name="storehouseid" id="storehouseid"/>
                    </td>
                </tr>
                <tr>
                    <td>申请日期</td>
                    <td>
                        <input id="entrydate" class="easyui-datebox" name="entrydate" data-options="required:true" style="width: 180px; height: 25px"/>
                    </td>
                    <td>保管员</td>
                    <td>
                        <input class="easyui-textbox" id="storeman" name="storeman" data-options="required:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>入库类型</td>
                    <td>
                        <select class="easyui-combobox" style="width: 180px;" data-options="editable:false,limitToList:true,required:true" id="entryinfotype" name="entryinfotype">
                            <option value="无合同入库">无合同入库</option>
                        </select>
                    </td>
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
<div id="c" class="easyui-window" title="新增数据字典子类" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:1000px;height:450px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="rklistschild" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>入库流水号</td>
                    <td>
                        <input class="easyui-textbox" id="rkcode2" name="rkcode" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" name="id" id="id"/>
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


</body>
</html>
