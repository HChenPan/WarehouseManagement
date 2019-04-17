<%@ page import="com.hchenpan.pojo.User" %><%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/3/5
  Time: 03:48 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>仓库管理系统</title>
    <%@ include file="common/head.jsp" %>
    <link rel="icon" type="image/x-icon" href="${ctx}/statics/img/biaoshi.ico"/>
    <link rel="shortcut icon" href="${ctx}/statics/img/biaoshi16.png">
    <link href="${ctx}/css/main.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/JavaScript/nav.js" type="text/javascript"></script>
</head>
<body class="easyui-layout">

<!--头部标识层-->
<div data-options="region:'north'" class="north">
    <table width="100%">
        <tr>
            <td width="400"><img src="${ctx}/statics/img/title.png" alt="仓库管理系统"/></td>
            <td align="right" class="right">
                <%
                    User user = (User) request.getSession().getAttribute("user");
                    out.print(user.getRealname());
                %>
                ,仓库管理系统
            </td>
            <td width="80" align="right" valign="bottom" class="logoutB">
                <a href="${ctx}/logout.do"> <img src="${ctx}/statics/img/logout.png" alt=" 退出"/> </a>
            </td>
        </tr>
    </table>
</div>
<shiro:authenticated>
    <!--左侧导航层 -->
    <div data-options="region:'west',split:true,title:'导航'" class="west">
        <div class="easyui-accordion" data-options="fit:true,border:false">
            <div title="系统配置" style="overflow:auto" data-options="iconCls:'icon-main-xtsz'">
                <ul class="navlist">
                    <shiro:hasRole name="admin">
                    <li><div><a href="#" onclick="addPanel('${ctx}/user.do','用户管理')">用户管理*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/permission.do','权限管理')">权限管理*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/role.do','角色管理')">角色管理*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/department.do','部门设置')">部门设置*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/employee.do','员工维护')">员工维护*</a></div></li>
                    </shiro:hasRole>
                    <shiro:hasAnyRoles name="admin,cangku">
                    <li><div><a href="#" onclick="addPanel('${ctx}/supplier.do','供应商管理')">供应商管理*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/buyer.do','购买方管理')">购买方管理*</a></div></li>
                    </shiro:hasAnyRoles>
                </ul>
            </div>
            <div title="基础数据" style="overflow:auto" data-options="iconCls:'icon-main-jichushuju'">
                <ul class="navlist">
                    <li><div><a href="#" onclick="addPanel('${ctx}/dictionary.do','数据字典')">数据字典*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/sparepartcode.do','备件编码管理树')">备件编码管理树*</a></div> </li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/sptypeSplevel.do','审批类型级别配置')">审批类型级别配置*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/projectNoManage.do','工程号维护')">工程号维护*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/workflow.do','工作流程')">工作流程*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/warehousenum.do','仓库编码')">仓库编码*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/wzqx.do','物资编码权限')">物资编码权限</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/contracttemp.do','合同条款模板')">合同条款模板*</a></div></li>
                </ul>
            </div>
            <div title="采购管理" style="overflow:auto" data-options="iconCls:'icon-main-shebeiguanli'">
                <ul class="navlist">
                    <li><div><a href="#" onclick="addPanel('${ctx}/plan.do','需求申请')">需求申请*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/planapproval.do','需求申请审批')">需求申请审批*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/buy.do','采购计划申请')">采购计划申请*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/buyapproval.do','采购计划申请审批')">采购计划申请审批*</a></div></li>
                </ul>
            </div>
            <div title="合同管理" style="overflow:auto" data-options="iconCls:'icon-main-contract'">
                <ul class="navlist">
                    <li><div><a href="#" onclick="addPanel('${ctx}/contractbasic.do','合同基本信息')">合同基本信息*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/contractgoods.do','合同物资信息')">合同物资信息*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/contractapproval.do','合同审批')">合同审批*</a></div></li>
                </ul>
            </div>
            <div title="内部交易" style="overflow:auto" data-options="iconCls:'icon-main-nenghao'">
                <ul class="navlist">
                    <li><div><a href="#" onclick="addPanel('${ctx}/applytransfer.do','申请调拨')">申请调拨*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/transfer.do','发货发料')">发货发料*</a></div></li>
                </ul>
            </div>
            <div title="库存管理" style="overflow:auto" data-options="iconCls:'icon-main-beijian'">
                <ul class="navlist">
                    <li><div><a href="#" onclick="addPanel('${ctx}/warehousing.do','采购入库')">采购入库*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/whtwarehousing.do','无合同入库')">无合同入库*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/callslip.do','领料单申请')">领料单申请*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/callslipout.do','领料出库')">领料出库*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/callslipapproval.do','领料单审批')">领料单审批*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/returntreasury.do','采购退货')">采购退货*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/cancellingstocks.do','领料退库')">领料退库*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/stock.do','库存管理')">库存管理*</a></div></li>
                    <li><div><a href="#" onclick="addPanel('${ctx}/stockjz.do','库存结转')">库存结转*</a></div></li>
                </ul>
            </div>
            <div title="用户设置" style="overflow:auto" data-options="iconCls:'icon-main-user'">
                <ul class="navlist">
                    <li><div><a href="#" onclick="addPanel('${ctx}/setpassword.do','个人密码修改')">密码修改</a></div></li>
                </ul>
            </div>
        </div>
    </div>
</shiro:authenticated>
<!--底部标识 -->
<div data-options="region:'south',border:false" class="south">
    <div style="float: left;padding-left: 40%">仓库管理系统&nbsp;&nbsp;All Rights Reserved.</div>
</div>

<!--中间窗口 -->
<div data-options="region:'center'">
    <div class="easyui-tabs" id="tt" fit="true" border="false" scrolling="auto" frameborder="0"></div>
</div>
</body>

</html>
