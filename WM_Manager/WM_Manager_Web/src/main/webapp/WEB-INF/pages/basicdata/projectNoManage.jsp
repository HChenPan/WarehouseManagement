<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/6
  Time: 12:42 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>工程号维护</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/JavaScript/projectNoManage.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<!-- 	整体居中 -->
<div data-options="region:'center',border:false">
    <!-- 		搜索模块和数据显示表格的布局 -->
    <div class="easyui-layout" data-options="fit:true">
        <!-- 		搜索模块 -->
        <div id="p" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:50px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>工程号</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="projectno1" style="width: 200px;" />
                    </td>
                    <td>工程名称</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="projectname1" style="width: 200px;" />
                    </td>
                    <td>创建人</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="createperson1" style="width: 200px;" />
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="projectNoManagesearch()">查询</a>&nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="projectNoManagesearchclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>
        <!-- 			数据显示模块			 -->
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="projectnomanagelist" data-options="fit:true">
            </table>
        </div>
    </div>
</div>

<!-- 新增、修改窗体 -->
<div id="w" class="easyui-window" title="工程号基本信息录入" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:300px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="projectnomanageadd" method="post">
            <table cellpadding="4" align="center">
                <tr>
                    <td>工程号编码</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="projectno" id="projectno"/>
                        <input type="hidden" name="id" id="id"/>
                    </td>
                    <td>工程号名称</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="projectname" id="projectname"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <input class="easyui-textbox" data-options="multiline:true" style="width: 100%;" name="remark" id="remark"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitprojectnomanageForm()">保存数据</a>
        </div>
    </div>
</div>

<!-- 查看详情窗体 -->
<div id="v" class="easyui-window" title="工程号基本信息详情" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:350px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="projectnomanagedetail" method="post">
            <table cellpadding="4" align="center">
                <tr>
                    <td>工程号编码</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="projectno"/>
                    </td>
                    <td>工程号名称</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="projectname"/>
                    </td>
                </tr>
                <tr>
                    <td>创建时间</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="createtime"/>
                    </td>
                    <td>创建人</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="createperson"/>
                    </td>
                </tr>
                <tr>
                    <td>更新时间</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="updatetime"/>
                    </td>
                    <td>更新人</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="updateperson"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <input class="easyui-textbox" data-options="multiline:true,readonly:true"  style="width: 100%;" name="remark"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>

