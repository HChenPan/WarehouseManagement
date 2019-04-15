<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/13
  Time: 12:40 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>领料出库</title>
    <%@ include file="../common/head.jsp" %>

    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js"></script>
    <script type="text/javascript" src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js"></script>
    <script src="${ctx}/JavaScript/callslipout.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:45px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>领料单号</td>
                    <td>
                        <input class="easyui-textbox" id="callslipcode1" style="width: 180px"/>
                    </td>

                    <td>领料大类</td>
                    <td>
                        <input class="easyui-combobox" id="callsliptype1" data-options="limitToList:true,valueField:'id',textField:'name',url:'dictionaryschild/getbillbydecode'"/>
                    </td>

                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="callslipsearch()">查询记录</a>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="searchclean()">清空条件</a>
                        &nbsp;&nbsp;
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="callsliplist"></table>
            <table class="easyui-datagrid" id="callslipgoodslist"></table>
        </div>
    </div>
</div>

<div id="w" class="easyui-window" title="领料单信息录入" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:400px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="callslipadd" method="post">
            <table cellpadding="4" align="center">
                <tr>
                    <td>领料大类</td>
                    <td>
                        <input class="easyui-combobox" data-options="required:true,limitToList:true,valueField:'id',textField:'name',url:'dictionaryschild/getbillbydecode'" id="callsliptype" name="callsliptype"/>
                        <input type="hidden" id="id" name="id"/>
                        <input type="hidden" id="llcode" name="llcode"/>
                    </td>
                    <td>领料仓库</td>
                    <td>
                        <select class="easyui-combobox" data-options="required:true,limitToList:true,valueField:'stockcode',textField:'stockname',url:'warehousenum/getall'" style="width: 150px;" name="storehouse">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>部门</td>
                    <td>
                        <select class="easyui-combotree" name="department" style="width: 150px" data-options="required:true,editable:false,limitToList:true,valueField:'id',textField:'name',url:'department/gettreedepart'">
                        </select>
                    </td>
                    <td>申请时间</td>
                    <td>
                        <input class="easyui-datebox" data-options="required:true" style="width: 150px" name="applydate"/>
                    </td>
                </tr>
                <tr>
                    <td>用途</td>
                    <td>
                        <input class="easyui-combobox" data-options="required:true,limitToList:true,valueField:'id',textField:'name',url:'dictionaryschild/getytbydecode'" style="width: 150px;" name="application"/>
                    </td>
                </tr>
                <tr>
                    <td>工程号</td>
                    <td>
                        <input class="easyui-combogrid" data-options="required:true,limitToList:true, panelWidth:460,idField:'projectno',textField:'projectno',url:'projectnomanage/getall', columns:[[{field:'projectno',title:'工程号',width:140},{field:'projectname',title:'工程名称',width:320},]]" style="width: 140px;" name="projectno" id="projectno"/>
                    </td>
                    <td>工程名称</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 320px;" name="projectname" id="projectname"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <input class="easyui-textbox" data-options="multiline:true" style="width: 100%;" name="note"/>
                    </td>
                </tr>
                <tr>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitcallslipForm()">保存数据</a>
        </div>
    </div>
</div>

<div id="c" class="easyui-window" title="新增物资信息" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:300px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="goodsform" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>领料单号</td>
                    <td>
                        <input class="easyui-textbox" id="callslipcode2" name="callslipcode" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" id="stockcode2"/>
                    </td>
                </tr>
                <tr>
                    <td>物资明细表</td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table id="addgoodslist" class="easyui-datagrid" style="width: 1000px"></table>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitgoodsForm()">保存数据</a>
        </div>
    </div>
</div>

<div id="d" class="easyui-window" title="新增物资信息" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:300px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="goodsform" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>领料单号</td>
                    <td>
                        <input class="easyui-textbox" id="callslipcode3" name="callslipcode" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" id="stockcode3"/>
                    </td>
                </tr>
                <tr>
                    <td>入库流水号</td>
                    <td>
                        <input class="easyui-combogrid" id="notecode" name="notecode" data-options="required:true" style="width:400px"/>
                    </td>
                </tr>
                <tr>
                    <td>物资明细表</td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table id="addgoodslist2" class="easyui-datagrid" style="width: 1000px"></table>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitgoodsForm()">保存数据</a>
        </div>
    </div>
</div>

</body>
</html>
