<%@ page import="com.hchenpan.pojo.User" %><%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/10
  Time: 11:38 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>需求申请审批</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js" type="text/javascript"></script>
    <script src="${ctx}/JavaScript/planapproval.js" type="text/javascript"></script>
</head>

<body  class="easyui-layout">
<div data-options="region:'center',border:false" >
    <table class="easyui-datagrid" id="planlist" style="height:50%;"></table>
    <table class="easyui-datagrid" id="planchildlist" style="height:50%;"></table>
</div>
<div id="cw" class="easyui-window" title="新增数据字典子类" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:450px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="planlistschild" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>计划号</td>
                    <td >
                        <input class="easyui-textbox" name="plancode" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" class="easyui-textbox" name="plancodeid"  />
                    </td>
                    <td>计划大类</td>
                    <td >
                        <input class="easyui-textbox" id="plantype2" name="plantype" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>计划名称</td>
                    <td >
                        <input class="easyui-textbox" id="planname2" name="planname" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" name="id"  />
                    </td>
                    <td>物资编码</td>
                    <td>
                        <input class="easyui-combogrid" data-options="readonly:true,panelWidth:450,idField:'code',textField:'code',url:'sparepartcode/getallwz',columns:[[{field:'code',title:'物资编码',width:120},{field:'name',title:'物资名称',width:120},]]" style="width:180px"id="wzcode2" name="wzcode"/>
                        <input class="easyui-textbox" type="hidden" name="wzid" id="wzid2" />
                    </td>
                </tr>
                <tr>
                    <td>物资名称</td>
                    <td>
                        <input class="easyui-textbox" id="wzname2" name="wzname" data-options="readonly:true" style="width:180px"/>
                    </td>
                    <td>单位</td>
                    <td>
                        <input class="easyui-textbox" id="unit2" name="unit" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>计划审批单价</td>
                    <td>
                        <input class="easyui-textbox" id="spprice2" name="spprice" data-options="required:true" style="width:180px" validtype="number"/>
                    </td>

                    <td>计划审批数量</td>
                    <td>
                        <input class="easyui-textbox" name="spnum" data-options="required:true" style="width:180px" validtype="number"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td  colspan="3">
                        <input class="easyui-textbox" name="note" style="width:100%"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitdictionarychildForm()">保存数据</a>
        </div>
    </div>
</div>
<div id="sp" class="easyui-window" title="固定资产采购审批" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:430px;height:250px;pading:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="planapproval" method="post">
            <table cellpadding="8" align="center">
                <tr>
                    <td>审批人</td>
                    <td width="280px">
                        <%
                            User user = (User) request.getSession().getAttribute("user");
                        %>
                        <input  class="easyui-textbox"  id="spuser" name="spuser"  value="<%=user.getRealname()%>" style="width:100%;"/>
                        <input type="hidden" class="easyui-textbox" name="spuserid" id="spuserid"  value="<%=user.getId()%>"/>
                        <input type="hidden" name="id"/>
                    </td>
                </tr>
                <tr style="padding-top:15px;">
                    <td>审批意见</td>
                    <td width="280px">
                        <input class="easyui-textbox" data-options="required:true,multiline:true" name="spadvice" style="width: 100%;">
                        </input>
                        <input type="hidden" class="easyui-textbox"   id="spresult"  name="spresult" />
                        <input type="hidden" class="easyui-textbox"   id="spcode"  name="spcode" />
                        <input type="hidden" class="easyui-textbox"   id="planmoney"  name="planmoney" />
                        <input type="hidden" class="easyui-textbox"   id="plantype"  name="plantype" />
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

<div id="p1" class="easyui-window" title="固定资产采购审批记录" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:100%;height:100%;padding:10px;">
    <table  class="easyui-datagrid" id="approvallist"  align="center" style="height:auto;"></table>
</div>

</body>
</html>
