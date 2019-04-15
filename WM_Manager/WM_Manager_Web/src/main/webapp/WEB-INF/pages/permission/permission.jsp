<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/15
  Time: 06:50 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>权限管理</title>
    <%@ include file="../common/head.jsp" %>

    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js"></script>
    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js"></script>
    <script src="${ctx}/JavaScript/permission.js" type="text/javascript"></script>
</head>

<body>
<table class="easyui-datagrid" id="permissionlist" data-options="fit:true"></table>

<div id="w" class="easyui-window" title="添加权限" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:320px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="permissionadd" method="post">
            <table cellpadding="5">
                <tr>
                    <td>权限名称</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="name" data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <td>权限键值</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="nameValue" data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        所属模块
                    </td>
                    <td>
                        <select class="easyui-combobox" name="modular" panelHeight="auto" data-options="required:true" style="width:130px">
                            <option value="系统配置">系统配置</option>
                            <option value="基础数据">基础数据</option>
                            <option value="采购管理">采购管理</option>
                            <option value="合同管理">合同管理</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>操作描述</td>
                    <td>
                        <input class="easyui-textbox" name="description" data-options="multiline:true" style="height:60px;width:300px"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitpermissionForm()">保存数据</a> &nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="clearpermissionForm()">重置</a>
        </div>
    </div>
</div>

<div id="u" class="easyui-window" title="修改权限" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:320px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="permissionupdate" method="post">
            <table cellpadding="5">
                <tr>
                    <td>权限名称</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="name" data-options="required:true"/>
                        <input type="hidden" name="pid"/>
                    </td>
                </tr>
                <tr>
                    <td>权限键值</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="nameValue" data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        所属模块
                    </td>
                    <td>
                        <select class="easyui-combobox" name="modular" panelHeight="auto" data-options="required:true">
                            <option value="系统配置">系统配置</option>
                            <option value="数据字典管理">数据字典管理</option>
                            <option value="设备管理">设备管理</option>
                            <option value="运维记录">运维记录</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>操作描述</td>
                    <td>
                        <input class="easyui-textbox" name="description" data-options="multiline:true" style="height:60px;width:300px"/></td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="updatepermissionForm()">修改数据</a>
        </div>
    </div>
</div>

</body>
</html>

