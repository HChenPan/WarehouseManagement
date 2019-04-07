<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/7
  Time: 04:03 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>购买方管理</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/JavaScript/buyer.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p" class="easyui-panel" data-options="region:'north',split:false,border:false"
             style="padding:2px;background: #fafafa;height:47px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td>购买商代码</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="buyercode1" style="width: 200px;"/>
                    </td>
                    <td>购买商名称</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="buyername1" style="width: 200px;"/>
                    </td>
                    <td>法定代表人</td>
                    <td>
                        <input class="easyui-textbox" type="text" id="legalrepresentative1" style="width: 200px;"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="buyersearch()">查询</a>&nbsp;&nbsp;
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="buyersearchclean()">清空条件</a>
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="buyerlist" data-options="fit:true"></table>
        </div>
    </div>
</div>

<div id="w" class="easyui-window" title="购买商基本信息录入" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:500px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="buyeradd" method="post">
            <table cellpadding="4" align="center">
                <tr>
                    <td>购买商代码</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="buyercode" id="buyercode"/>
                        <input type="hidden" name="id" id="id"/>
                    </td>
                    <td>购买商名称</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="buyername" id="buyername"/>
                    </td>
                </tr>
                <tr>
                    <td>法定代表人</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="legalrepresentative" id="legalrepresentative"/>
                    </td>
                    <td>住所</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="address" id="address"/>
                    </td>
                </tr>
                <tr>
                    <td>电话</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="phone" id="phone"/>
                    </td>
                    <td>传真</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="fax" id="fax"/>
                    </td>
                </tr>
                <tr>
                    <td>开户行</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="bank" id="bank"/>
                    </td>
                    <td>银行账号</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="account" id="account"/>
                    </td>
                </tr>
                <tr>
                    <td>邮政编码</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="postcode" id="postcode"/>
                    </td>
                    <td>税务证号</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="taxid" id="taxid"/>
                    </td>
                </tr>
                <tr>
                    <td>供货范围</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="supplyscope" id="supplyscope"/>
                    </td>
                    <td>注册资金</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="registeredcapital" id="registeredcapital"/>
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
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitbuyerForm()">保存数据</a>
        </div>
    </div>
</div>
<div id="v" class="easyui-window" title="购买商基本信息详情" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:500px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="buyerdetail" method="post">
            <table cellpadding="4" align="center">
                <tr>
                    <td>购买商代码</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="buyercode"/>
                    </td>
                    <td>购买商名称</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="buyername"/>
                    </td>
                </tr>
                <tr>
                    <td>法定代表人</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="legalrepresentative"/>
                    </td>
                    <td>住所</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="address"/>
                    </td>
                </tr>
                <tr>
                    <td>电话</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="phone"/>
                    </td>
                    <td>传真</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="fax"/>
                    </td>
                </tr>
                <tr>
                    <td>开户行</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="bank"/>
                    </td>
                    <td>银行账号</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="account"/>
                    </td>
                </tr>
                <tr>
                    <td>邮政编码</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="postcode"/>
                    </td>
                    <td>税务证号</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="taxid"/>
                    </td>
                </tr>
                <tr>
                    <td>供货范围</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="supplyscope"/>
                    </td>
                    <td>注册资金</td>
                    <td>
                        <input class="easyui-textbox" data-options="readonly:true" style="width: 300px" name="registeredcapital"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <input class="easyui-textbox" data-options="multiline:true,readonly:true" style="width: 100%;" name="remark"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>

