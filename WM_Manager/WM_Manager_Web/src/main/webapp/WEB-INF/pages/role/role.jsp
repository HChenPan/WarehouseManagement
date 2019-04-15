<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/15
  Time: 03:22 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>角色管理</title>
    <%@ include file="../common/head.jsp" %>

    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js"></script>
    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js"></script>
    <script src="${ctx}/JavaScript/role.js" type="text/javascript"></script>
</head>

<body>
<table class="easyui-datagrid" id="rolelist" data-options="fit:true">
</table>

<div id="w" class="easyui-window" title="添加角色" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:460px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="roleadd" method="post">
            <table cellpadding="5">
                <tr>
                    <td>角色名称</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="name" data-options="required:true" style="width:200px;"/>
                    </td>
                    <td>角色类别</td>
                    <td>
                        <select class="easyui-combobox" name="type" data-options="required:true" panelHeight="auto" style="width:100px">
                            <option value="系统角色">系统角色</option>
                            <option value="业务角色">业务角色</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>功能描述</td>
                    <td colspan="3">
                        <input class="easyui-textbox" type="text" name="description" data-options="required:true,width:400"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table id="pdg"></table>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitroleForm()">保存数据</a> &nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="clearroleForm()">重置</a>
        </div>
    </div>
</div>

<div id="u" class="easyui-window" title="修改角色" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:460px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="roleupdate" method="post">
            <table cellpadding="5">
                <tr>
                    <td>角色名称</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="name" data-options="required:true" style="width:200px;"/>
                        <input type="hidden" name="rid"/>
                    </td>
                    <td>角色类别</td>
                    <td>
                        <select id="cc" class="easyui-combobox" name="type" data-options="required:true" panelHeight="auto" style="width:100px">
                            <option value="系统角色">系统角色</option>
                            <option value="业务角色">业务角色</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>功能描述</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="description" data-options="required:true,width:400"/></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table id="pdgedit"></table>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="updateroleForm()">修改数据</a>
        </div>
    </div>
</div>
</body>
</html>

