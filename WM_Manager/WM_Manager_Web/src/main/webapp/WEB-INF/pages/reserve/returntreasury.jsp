<%--
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
    <title>采购退货</title>
    <%@ include file="../common/head.jsp" %>

    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js"></script>
    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js"></script>
    <script src="${ctx}/JavaScript/returntreasury.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p1" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:50px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>采购退货单号</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="tkcode1" style="width: 150px;"/>
                    </td>
                    <td>采购退货申请时间</td>
                    <td>
                        <input id="datetime1" class="easyui-datebox" style="width: 150px; height: 25px"/>——&nbsp;
                        <input id="datetime2" class="easyui-datebox" style="width: 150px; height: 25px"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="tksearch()">查询</a> &nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="tkclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>

        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="tklist" style="height:50%;"></table>
            <table class="easyui-datagrid" id="tkchildlist" style="height:50%;"></table>
        </div>
    </div>
</div>
<div id="w" class="easyui-window" title="新增数据字典大类" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:280px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="tk" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>申请日期</td>
                    <td>
                        <input id="sqdate" class="easyui-datebox" name="sqdate" data-options="required:true" style="width: 180px; height: 25px"/>
                        <input type="hidden" name="id" id="id"/>
                    </td>
                    <td>申请人</td>
                    <td>
                        <input id="sqr" class="easyui-textbox" name="sqr" data-options="required:true" style="width: 180px; height: 25px"/>
                    </td>
                </tr>
                <tr>
                    <td>退货原因</td>
                    <td colspan="3">
                        <input class="easyui-textbox" name="tkreason" data-options="multiline:true" style="width: 100%;height:70px;"/>
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
        <form id="tklistschild" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>退库流水号</td>
                    <td>
                        <input class="easyui-textbox" id="tkcode2" name="tkcode" data-options="readonly:true" style="width:650px"/>
                        <input type="hidden" name="id" />
                    </td>
                </tr>
                <tr>
                    <td>入库流水号</td>
                    <td>
                        <select id="notecode" name="notecode" class="easyui-combogrid" style="width:100%" data-options="">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>退库物资明细</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <table id="tkmxlist" class="easyui-datagrid"></table>
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
