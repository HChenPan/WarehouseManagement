<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/10
  Time: 04:05 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>合同模板</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/JavaScript/contracttemp.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/statics/calendarTime/calendarTime.js"></script>
</head>

<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west',border:false,split:true" style="width:40%;">
            <div class="easyui-layout" data-options="fit:true">
                <div id="p1" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:50px;">
                    <table cellpadding="5" cellspacing="1" border="0">
                        <tr>
                            <td>模板名称</td>
                            <td>
                                <input class="easyui-textbox" type="text" id="searchtempname" style="width: 150px;"/>
                            </td>
                            <td>
                                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="tempnamesearch()">查询</a> &nbsp;&nbsp;
                                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="tempnamesearchclean()">清空条件</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <div data-options="region:'center',border:false">
                    <table class="easyui-datagrid" id="contracttempnamelist" data-options="fit:true"></table>
                </div>
            </div>
        </div>
        <div data-options="region:'east',border:false" style="width:70%;">
            <div class="easyui-layout" data-options="fit:true">
                <!-- 以下为右侧datagrid -->
                <div data-options="region:'center',border:false">
                    <table class="easyui-datagrid" id="contractcontent" data-options="fit:true"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="w" class="easyui-window" title="新增数据字典大类" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:400px;height:220px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="contracttempnameform" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>模板名称</td>
                    <td>
                        <input class="easyui-textbox" name="contractempname" data-options="required:true" style="width:180px"/>
                        <input type="hidden" name="id" />
                    </td>
                </tr>
                <tr>
                    <td>描述</td>
                    <td>
                        <input class="easyui-textbox" name="introduce" data-options="required:true" style="width:180px"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submittempnameform()">保存数据</a>
        </div>
    </div>
</div>
<div id="c" class="easyui-window" title="新增数据字典子类" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:300px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="tempcontentform" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>模板名称</td>
                    <td>
                        <input class="easyui-textbox" id="contracttempname" name="contracttempname" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" class="easyui-textbox" name="tempnameId" id="contracttempnameid"/>
                    </td>
                </tr>
                <tr>
                    <td>条款序号</td>
                    <td>
                        <input class="easyui-textbox" name="sn" data-options="required:true" style="width:180px"/>
                        <input type="hidden" name="id" />
                    </td>
                </tr>
                <tr>
                    <td>条款内容</td>
                    <td>
                        <input class="easyui-textbox" name="content" data-options="required:true,multiline:true" style="width:400px;height:60px"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submittempcontentForm()">保存数据</a>
        </div>
    </div>
</div>
</body>
</html>
