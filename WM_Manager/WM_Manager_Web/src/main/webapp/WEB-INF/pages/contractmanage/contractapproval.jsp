<%@ page import="com.hchenpan.pojo.User" %><%--
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
    <title>合同审批</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js" type="text/javascript"></script>
    <script src="${ctx}/JavaScript/contractapproval.js" type="text/javascript"></script>
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
<div id="sp" class="easyui-window" title="固定资产采购审批" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:430px;height:250px;pading:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="contractapproval" method="post">
            <table cellpadding="8" align="center">
                <tr>
                    <td>审批人</td>
                    <td width="280px">
                        <%
                            User user = (User) request.getSession().getAttribute("user");
                        %>
                        <input  class="easyui-textbox"  id="spuser" name="spuser"  value="<%=user.getRealname()%>" style="width:100%;"/>
                        <input type="hidden" class="easyui-textbox" name="backuserid" id="spuserid"  value="<%=user.getId()%>"/>
                        <input type="hidden" name="id" id="id"/>
                    </td>
                </tr>
                <tr style="padding-top:15px;">
                    <td>审批意见</td>
                    <td width="280px">
                        <input class="easyui-textbox" data-options="multiline:true" name="backreason" style="width: 100%;"/>
                        <input type="hidden" class="easyui-textbox" id="spresult" name="spresult"/>
                        <input type="hidden" class="easyui-textbox" id="spcode" name="spcode"/>
                        <input type="hidden" class="easyui-textbox" id="summoney" name="summoney"/>
                        <input type="hidden" class="easyui-textbox" id="contractauditingtypcode" name="contractauditingtypcode"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px;margin-top:20px;">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitagreeform()">同意</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:20px;" data-options="iconCls:'icon-no'" onclick="submitbackform()">退回</a>
        </div>
    </div>
</div>

<div id="p2" class="easyui-window" title="合同审批记录" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:100%;height:100%;padding:10px;">
    <table class="easyui-datagrid" id="approvallist" align="center" style="height:auto;"></table>
</div>

</body>
</html>

