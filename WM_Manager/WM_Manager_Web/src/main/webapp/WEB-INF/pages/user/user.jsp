<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/3/19
  Time: 05:15 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>用户管理</title>
    <%@ include file="../common/head.jsp" %>
    <link href="${ctx}/css/main.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js"></script>
    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js"></script>
    <script src="${ctx}/JavaScript/user.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
    $(function () {
        if ("<%=session.getAttribute("user")%>" === "null") {
            alert("对不起，您尚未登录或者登录超时。");
            window.location = "${ctx}/login.do";
        }
    });
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p" class="easyui-panel" data-options="region:'north',split:false,border:false"
             style="padding:2px;background: #fafafa;height:42px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>用户名</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="searchusername" style="width: 80px;"/></td>
                    <td>真实姓名</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="searchrealname" style="width: 60px;"/></td>
                    <td>所属部门</td>
                    <td>
                        <input class="easyui-combobox" id="password1" style="width: 250px;" panelHeight="auto" data-options="valueField:'id', textField:'name',url:'department/getall.do'"/>
                    </td>
                    <td>拥有角色</td>
                    <td>
                        <input class="easyui-combobox" id="password2" style="width: 180px;" panelHeight="auto" data-options="valueField:'rid', textField:'name',url:'role/getall.do'"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="usersearch()">查询用户</a>
                        &nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="usersearchclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="userlist" data-options="fit:true">
            </table>
        </div>
    </div>
</div>

<div id="w" class="easyui-window" title="添加用户" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:780px;height:450px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="useradd" method="post">
            <table cellpadding="5">
                <tr>
                    <td>用户名</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="username" data-options="required:true" style="width:120px"/>
                    </td>
                    <td>真实姓名</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="realname" data-options="required:true" style="width:120px"/>
                    </td>
                </tr>
                <tr>
                    <td>所属部门</td>
                    <td colspan="3">
                        <input class="easyui-combotree" name="department.id" style="width: 300px;" panelHeight="auto" data-options="required:true, valueField:'id', textField:'name',url:'department/gettreedepart.do'"/>
                    </td>
                </tr>
                <tr>
                    <td>联系电话</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="tel" style="width:120px"/>
                    </td>
                    <td>是否超级管理员</td>
                    <td>
                        <select id="cc" class="easyui-combobox" name="issuper" panelHeight="auto" style="width:80px">
                            <option value="TRUE">是</option>
                            <option value="FALSE">否</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table id="rdg"></table>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submituserForm()">保存数据</a>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="clearuserForm()">重置</a>
        </div>
    </div>
</div>

<div id="u" class="easyui-window" title="修改用户" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:780px;height:450px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="userupdate" method="post">
            <table cellpadding="5">
                <tr>
                    <td>用户名</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="username" data-options="required:true" style="width:120px" readonly="readonly"/>
                        <input type="hidden" name="id"/>
                    </td>
                    <td>真实姓名</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="realname" data-options="required:true" style="width:120px"/>
                    </td>
                </tr>
                <tr>
                    <td>所属部门</td>
                    <td colspan="3">
                        <input class="easyui-combotree" name="department.id" id="deptid" style="width: 300px;" panelHeight="auto" data-options="required:true, valueField:'id', textField:'name',url:'department/gettreedepart.do'"/>
                    </td>
                </tr>
                <tr>
                    <td>联系电话</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="tel" style="width:120px"/>
                    </td>
                    <td>是否超级管理员</td>
                    <td>
                        <select id="isusuper" class="easyui-combobox" name="issuper" panelHeight="auto" style="width: 80px;">
                            <option value="TRUE">是</option>
                            <option value="FALSE">否</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table id="rdgedit"></table>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="updateuserForm()">修改数据</a>
        </div>
    </div>
</div>
</body>
</html>
