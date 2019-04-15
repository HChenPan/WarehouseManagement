<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/15
  Time: 09:55 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>物资编码权限</title>
    <%@ include file="../common/head.jsp" %>

    <script type="text/javascript" src="${ctx}/statics/calendarTime/calendarTime.js"></script>
    <script src="${ctx}/JavaScript/wzqx.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:42px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>物资编码前缀</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="wzqz1" style="width: 180px;"/>
                    </td>
                    <td>物资编码操作人</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="czrzw1" style="width: 180px;"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="declareunitsearch()">查询</a>&nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="declareunitsearchclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>

        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="wzqxlist"></table>
        </div>
    </div>
</div>

<div id="w" class="easyui-window" title="新增仓库数据" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:750px;height:250px;padding:10px;">
    <div style="padding:10px 20px 20px 60px">
        <form id="wzqx" method="post">
            <table cellpadding="5">
                <tr>
                    <td>物资编码前缀</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="wzqz" id="wzqz2" data-options="required:true" style="width:180px"/>
                        <input type="hidden" name="id" id="id"/>
                    </td>
                </tr>
                <tr>
                    <td>物资操作人</td>
                    <td colspan="3">
                        <input class="easyui-combobox" id="czr" name="czr" style="width:  500px;"data-options="required:true,valueField:'id', textField:'text',multiple:true,limitToList:true,url:'user/getdeptuserlist'"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:10px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitwarehouseform()">保存数据</a>
        </div>
    </div>
</div>


</body>
</html>

