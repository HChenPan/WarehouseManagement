<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/15
  Time: 07:07 下午  
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
    <script src="${ctx}/JavaScript/employee.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:42px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>员工编号</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="searchemployeenum" style="width: 160px;"/>
                    </td>
                    <td>员工姓名</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="searchemployeename" style="width: 120px;"/>
                    </td>
                    <td>联系电话</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="searchphonenum" style="width: 160px;"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="employeesearch()">查询员工</a> &nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="employeesearchclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="employeelist" data-options="fit:true"></table>
        </div>
    </div>
</div>

<div id="w" class="easyui-window" title="添加员工" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:750px;height:250px;padding:10px;">
    <div style="padding:10px 20px 20px 60px">
        <form id="employeeadd" method="post">
            <table cellpadding="5">
                <tr>
                    <td>员工编码</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="employeenum" data-options="required:true" style="width:180px"/>
                    </td>
                    <input type="hidden" name="id" id="id"/>
                    <td>员工姓名</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="employeename" data-options="required:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>联系电话</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="phonenum" data-options="required:true" style="width:180px"/>
                    </td>
                    <td>所属部门</td>
                    <td>
                        <input class="easyui-combobox" id="departid" name="departid" style="width: 180px;" panelHeight="auto" data-options="valueField:'id', textField:'name',url:'department/getall',required:true"/>
                    </td>
                </tr>

            </table>
        </form>
        <div style="text-align:center;padding:10px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitemployeeForm()">保存数据</a>
        </div>
    </div>
</div>


</body>
</html>

