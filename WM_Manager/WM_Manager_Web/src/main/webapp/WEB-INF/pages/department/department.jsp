<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/15
  Time: 07:57 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>部门设置</title>
    <%@ include file="../common/head.jsp" %>

    <script type="text/javascript" src="${ctx}/statics/calendarTime/calendarTime.js"></script>
    <script src="${ctx}/JavaScript/department.js" type="text/javascript"></script>
</head>

<body>
<table class="easyui-treegrid" id="departlist">
</table>

<div id="w" class="easyui-window" title="添加组织机构" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:400px;height:250px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="departadd" method="post">
            <table cellpadding="5">
                <tr>
                    <td>组织机构名称</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="name" data-options="required:true" style="width:180px;"/>
                        <input type="hidden" name="id"/>
                        <input type="hidden" name="_parentId" id="parentid"/>
                    </td>
                </tr>
                <tr>
                    <td>组织机构编号</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="deptnumber" data-options="required:true" style="width:180px;"/>
                    </td>
                </tr>
                <tr>
                    <td>办公电话</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="tel" style="width:180px;"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitdepartForm()">保存数据</a>
        </div>
    </div>
</div>

</body>
</html>
