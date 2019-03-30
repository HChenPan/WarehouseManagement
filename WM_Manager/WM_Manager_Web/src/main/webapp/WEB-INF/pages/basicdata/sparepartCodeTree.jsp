<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/3/30
  Time: 10:50 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>备件编码</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%--<%@ include file="../common/head.jsp" %>--%>
    <link href="${ctx}/css/main.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/statics/jqueryeasyui/themes/material-teal/easyui.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/statics/jqueryeasyui/themes/icon.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/statics/js/jquery-1.8.0.min.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/jquery.easyui.mobile.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/easyloader.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/locale/easyui-lang-zh_CN.js" type="text/javascript"
            charset="UTF-8"></script>
    <script src="${ctx}/JavaScript/sparepartCodeTree.js" type="text/javascript"></script>
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
<table class="easyui-datagrid" id="departlist"></table>

<div id="w" class="easyui-window" title="添加备件编码" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:550px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="departadd" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>备件所属</td>
                    <td>
                        <select class="easyui-combobox" style="width:240px;" data-options="required:true,editable:false,limitToList:true" id="description" name="description">
                            <option value=""></option>
                            <!-- <option value="备件类型">备件类型</option> -->
                            <option value="备件类别">备件类别</option>
                            <option value="备件属性">备件属性</option>
                            <option value="备件大类">备件大类</option>
                            <option value="备件中类">备件中类</option>
                            <option value="物资">物资</option>
                        </select>
                    </td>
                    <td>备件名称</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" id="name" name="name" data-options="required:true"/>
                        <input type="hidden" name="id"/>
                        <input type="hidden" name="_parentId" id="parentid"/>
                        <input type="hidden" name="parentcode" id="parentcode"/>
                    </td>
                </tr>
                <tr>
                    <td>备件编码</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" id="code" name="code"/>
                    </td>
                    <td>备件英文名称</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="ywname" id="ywname"/>
                    </td>
                </tr>
                <tr>
                    <td>所属设备编码</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" id="devicecode" name="devicecode" data-options="required:true"/>
                    </td>
                    <td>标件类型</td>
                    <td>
                        <input class="easyui-combobox" id="spareparttype" name="spareparttype" style="width: 240px;" panelHeight="auto" data-options="valueField:'name', textField:'name',url:'dictionaryschild/getsbzltypelistbydecode',required:true"/>
                        <input class="easyui-textbox" type="hidden" name="spareparttypecode" id="spareparttypecode"/>
                    </td>
                </tr>
                <tr>
                    <td>主机名称及型号</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" id="hostname" name="hostname" data-options="required:true"/>
                    </td>
                    <td>供货周期</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" id="supplycycle" name="supplycycle" data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <td>型号＋规格</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" id="modelSpecification" name="modelSpecification" data-options="required:true"/>
                    </td>
                    <td>采购时间</td>
                    <td>
                        <input  class="easyui-datebox purchasetime" data-options="required:true" name="purchasetime" style="width: 240px; height: 25px"/>
                    </td>
                </tr>
                <tr>
                    <td>计量单位</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" id="unit" name="unit" data-options="required:true"/>
                    </td>
                    <td>图号</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" id="tuhao" name="tuhao" data-options="required:true"/>
                    </td>
                </tr>
                <tr id="BZ" style="display:none;">
                    <td>资金单位</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" id="currencyunit" name="currencyunit"/>
                    </td>
                    <td>资金类型</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" id="currencytype" name="currencytype"/>
                    </td>
                </tr>
                <tr>
                    <td>计划价格</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" id="planprice" name="planprice" data-options="required:true" validtype="number"/>
                    </td>
                    <td>最低库存量</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" id="stockmin" name="stockmin" data-options="required:true" validtype="number"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <input style="width: 100%;height:70px;" class="easyui-textbox" type="text" id="remark" name="remark" data-options="required:true,multiline:true"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" id="linkbutton" data-options="iconCls:'icon-ok'" onclick="submitdepartForm()">保存数据</a>
        </div>
    </div>
</div>

<div id="d" class="easyui-window" title="查看详情" data-options="modal:true,closed:true,iconCls:'icon-search'" style="width:800px;height:550px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="detail" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>备件所属</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="description" data-options="readonly:true"/>
                    </td>
                    <td>备件名称</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="name" data-options="readonly:true"/>
                    </td>
                </tr>
                <tr>
                    <td>备件编码</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="code" data-options="readonly:true"/>
                    </td>
                    <td>备件英文名称</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="ywname" data-options="readonly:true"/>
                    </td>
                </tr>
                <tr>
                    <td>所属设备编码</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="devicecode" data-options="readonly:true"/>
                    </td>
                    <td>标件类型</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="spareparttype" data-options="readonly:true"/>
                    </td>
                </tr>
                <tr>
                    <td>主机名称及型号</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="hostname" data-options="readonly:true"/>
                    </td>
                    <td>供货周期</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="supplycycle" data-options="readonly:true"/>
                    </td>
                </tr>
                <tr>
                    <td>型号＋规格</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="modelSpecification" data-options="readonly:true"/>
                    </td>
                    <td>采购时间编码</td>
                    <td>
                        <input  class="easyui-datebox purchasetime" name="purchasetime" style="width: 240px; height: 25px" data-options="readonly:true"/>
                    </td>
                </tr>
                <tr>
                    <td>计量单位</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="unit" data-options="readonly:true"/>
                    </td>
                    <td>图号</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="tuhao" data-options="readonly:true"/>
                    </td>
                </tr>
                <td>资金单位</td>
                <td>
                    <input style="width:240px;" class="easyui-textbox" type="text" name="currencyunit" data-options="readonly:true"/>
                </td>
                <td>资金类型</td>
                <td>
                    <input style="width:240px;" class="easyui-textbox" type="text" name="currencytype" data-options="readonly:true"/>
                </td>
                </tr>
                <tr>
                    <td>计划价格</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="planprice" data-options="readonly:true"/>
                    </td>
                    <td>最低库存量</td>
                    <td>
                        <input style="width:240px;" class="easyui-textbox" type="text" name="stockmin" data-options="readonly:true"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <input style="width: 100%;height:70px;" class="easyui-textbox" type="text" name="remark" data-options="required:true,multiline:true"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>
