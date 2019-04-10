<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/10
  Time: 12:55 下午
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>库存管理</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/JavaScript/stock.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/statics/calendarTime/calendarTime.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <!-- 搜索框		 -->
        <div id="p" class="easyui-panel" data-options="region:'north',split:false,border:false" style="padding:2px;background: #fafafa;height:50px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>物资名称</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="wzname1" style="width: 180px;"/>
                    </td>
                    <td>物资编码</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="wzcode1" style="width: 180px;"/>
                    </td>
                    <td>仓库名称</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="stockname1" style="width: 180px;"/>
                    </td>
                    <td>仓库编码</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="stockcode1" style="width: 180px;"/>
                    </td>
                    <td colspan="6">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="stocksearch()">查询信息</a> &nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="stocksearchclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>
        <!-- 库存物资列表 -->
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="kclist" data-options="fit:true"></table>
        </div>
    </div>
</div>
<!-- 查看详情 -->
<div id="w" class="easyui-window" title="库存物资详情" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:500px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="kcdetail" method="post">
            <table cellpadding="4" align="center">
                <tr>
                    <td>仓库编码</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="stockcode"/>
                    </td>
                    <td>仓库名称</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="stockname"/>
                    </td>
                </tr>
                <tr>
                    <td>物资编码</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="wzcode"/>
                    </td>
                    <td>物资名称</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="wzname"/>
                    </td>
                </tr>
                <tr>
                    <td>规格型号</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="modelspcification"/>
                    </td>
                    <td>计量单位</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="unit"/>
                    </td>
                </tr>
                <tr>
                    <td>本月期初值</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="bqstart"/>
                    </td>
                    <td>本期入库量总和</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="bqin"/>
                    </td>
                </tr>
                <tr>
                    <td>本期出库量总和</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="bqout"/>
                    </td>
                    <td>本期期末量</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="bqend"/>
                    </td>
                </tr>
                <tr>
                    <td>资金编码</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="zjcode"/>
                    </td>
                    <td>资金单位</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="zjname"/>
                    </td>
                </tr>
                <tr>
                    <td>本期期初金额</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="bqstartmoney"/>
                    </td>
                    <td>本期入库金额</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="bqinmoney"/>
                    </td>
                </tr>

                <tr>
                    <td>本期出库金额</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="bqoutmoney"/>
                    </td>
                    <td>本期期末金额</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="bqendmoney"/>
                    </td>
                </tr>
                <tr>
                    <td>单价</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="price"/>
                    </td>

                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
