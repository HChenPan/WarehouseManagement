<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/4/12
  Time: 01:19 下午  
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>合同基本信息</title>
    <%@ include file="../common/head.jsp" %>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-cellediting.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/plugins/datagrid-filter.js" type="text/javascript"></script>
    <script src="${ctx}/JavaScript/contractbasic.js" type="text/javascript"></script>
</head>

<body class="easyui-layout">
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div id="p" class="easyui-panel" data-options="region:'north',split:false,border:false"
             style="padding:2px;background: #fafafa;height:45px;">
            <table cellpadding="5" cellspacing="1" border="0">
                <tr>
                    <td width="90px;">合同流水号</td>
                    <td>
                        <input class="easyui-textbox" id="serialsnumber1" style="width: 180px"/>
                    </td>
                    <td>合同类型</td>
                    <td>
                        <input class="easyui-combobox" id="contracttype1" data-options="limitToList:true,valueField:'id',textField:'name',url:'dictionaryschild/getcontracttypelistbydecode'"/>
                    </td>
                    <td>创建时间</td>
                    <td>
                        <input id="datetime1" class="easyui-datebox" style="width: 150px; height: 25px"/>——&nbsp;
                        <input id="datetime2" class="easyui-datebox" style="width: 150px; height: 25px"/>
                    </td>
                    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="contractsearch()">查询记录</a>
                    </td>
                    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="contractsearchclean()">清空条件</a> &nbsp;&nbsp;
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table class="easyui-datagrid" id="contractbasiclist" data-options="fit:true"> </table>
        </div>
    </div>
</div>

<div id="w" class="easyui-window" title="合同基本信息录入" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:500px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="contractbasicadd" method="post">
            <table cellpadding="4" align="center">
                <tr>
                    <td>合同类型</td>
                    <td>
                        <input class="easyui-combobox" data-options="required:true,limitToList:true,valueField:'id',textField:'name',url:'dictionaryschild/getcontracttypelistbydecode'" id="contracttypeId" name="contracttype"/>
                        <input type="hidden" name="id" />
                        <input type="hidden" name="contracttypecode" id="contracttypecode"/>
                    </td>
                    <td>签订方式</td>
                    <td>
                        <select class="easyui-combobox" data-options="required:true" style="width: 150px;" name="contractmethod">
                            <option value="招标">招标</option>
                            <option value="议标">议标</option>
                            <option value="比价">比价</option>
                            <option value="零星采购">零星采购</option>
                        </select>

                    </td>
                </tr>
                <tr>
                    <td>付款方式</td>
                    <td>
                        <select class="easyui-combobox" name="paymentmethod" style="width: 150px" data-options="required:true,editable:false,limitToList:true">
                            <option value="现金支付">现金支付</option>
                            <option value="转账">转账</option>
                            <option value="汇票">汇票</option>
                            <option value="银行承兑">银行承兑</option>
                            <option value="银行汇款">银行汇款</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>签订地点</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width: 300px" name="contractarea"/>
                    </td>
                </tr>
                <tr>
                    <td>有效起始日期</td>
                    <td>
                        <input class="easyui-datebox" data-options="required:true,limitToList:true" style="width: 150px;" name="startdate"/>
                    </td>
                    <td>有效截止日期</td>
                    <td>
                        <input class="easyui-datebox" data-options="required:true,limitToList:true" style="width: 150px;" name="enddate"/>
                    </td>
                </tr>
                <tr>
                    <td>比价方一</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:false" style="width: 300px" name="bjf1"/>
                    </td>
                </tr>
                <tr>
                    <td>比价方二</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:false" style="width: 300px" name="bjf2"/>
                    </td>
                </tr>
                <tr>
                    <td>比价方三</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:false" style="width: 300px" name="bjf3"/>
                    </td>
                </tr>
                <tr>
                    <td>选择该供货商原因</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:false" style="width: 300px" name="supplierreasons"/>
                    </td>
                </tr>
                <tr>
                    <td>选择比价采购原因</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:false" style="width: 300px" name="bjreasons"/>
                    </td>
                </tr>
                <tr>
                    <td>运费承担</td>
                    <td>
                        <input class="easyui-textbox" data-options="required:true" style="width:150px" name="freight"/>
                    </td>
                    <td>合同税率</td>
                    <td>
                        <input class="easyui-numberbox" data-options="required:true,suffix:'%'" style="width:150px" name="contracttax"/>
                    </td>
                </tr>
                <tr>
                    <td>合同审批类型</td>
                    <td>
                        <select class="easyui-combobox" data-options="required:true,limitToList:true,valueField:'id',textField:'name',url:'dictionaryschild/getSplistbydecode'" style="width: 150px" name="contractauditingtype" id="contractauditingtype"></select>
                        <input type="hidden" id="contractauditingtypecode" name="contractauditingtypcode"/>
                        <input type="hidden" id="contractauditingtypename" name="contractauditingtypename"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <fieldset style="border-color: #BF3EFF ;border-style: solid  !important;">
                            <legend>出卖人信息</legend>
                            <table cellpadding="5" cellspacing="1" border="0">
                                <tr>
                                    <td>名称</td>
                                    <td>
                                        <input class="easyui-combogrid" data-options="required:true, panelWidth:450, idField:'id', textField:'suppliername', url:'supplier/getall',
           											  columns:[[ {field:'suppliercode',title:'代码',width:120}, {field:'suppliername',title:'名称',width:120}, {field:'address',title:'住所',width:150}, {field:'phone',title:'电话',width:150}, ]]
										" style="width:220px" name="venditorid" id="venditorid"/>
                                        <input type="hidden" id="venditorname" name="venditorname"/>
                                    </td>
                                    <td>代码</td>
                                    <td>
                                        <input class="easyui-textbox" id="venditorcode" disabled="disabled"/>
                                    </td>
                                    <td>住所</td>
                                    <td>
                                        <input class="easyui-textbox" style="width:220px" id="venditoraddress" disabled="disabled"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>法人</td>
                                    <td>
                                        <input class="easyui-textbox" style="width:220px" disabled="disabled" id="venditorfr"/>
                                    </td>
                                    <td>委托人</td>
                                    <td>
                                        <input class="easyui-textbox" data-options="required:false" name="venditorwt"/>
                                    </td>
                                    <td>电话</td>
                                    <td>
                                        <input class="easyui-textbox" data-options="required:false" disabled="disabled" id="venditorphone"/>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <fieldset style="border-color: #BF3EFF ;border-style: solid  !important;">
                            <legend>买受人信息</legend>
                            <table cellpadding="5" cellspacing="1" border="0">
                                <tr>
                                    <td>名称</td>
                                    <td>
                                        <input class="easyui-combogrid" data-options="required:true, panelWidth:450, idField:'id', textField:'buyername', url:'buyer/getall',
           											 columns:[[ {field:'buyercode',title:'代码',width:120}, {field:'buyername',title:'名称',width:120}, {field:'address',title:'住所',width:150}, {field:'phone',title:'电话',width:150}, ]]
										" style="width:220px" name="buyerid" id="buyerid"/>
                                        <input type="hidden" id="buyername" name="buyername"/>
                                    </td>
                                    <td>代码</td>
                                    <td>
                                        <input class="easyui-textbox" data-options="required:true" disabled="disabled" id="buyercode"/>
                                    </td>
                                    <td>住所</td>
                                    <td>
                                        <input class="easyui-textbox" style="width:220px" data-options="required:true" disabled="disabled" id="buyeraddress"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>法人</td>
                                    <td>
                                        <input class="easyui-textbox" style="width:220px" data-options="required:true" disabled="disabled" id="buyerfr"/>
                                    </td>
                                    <td>委托人</td>
                                    <td>
                                        <input class="easyui-textbox" data-options="required:false" name="buyerwt"/>
                                    </td>
                                    <td>电话</td>
                                    <td>
                                        <input class="easyui-textbox" data-options="required:false" disabled="disabled" id="buyerphone"/>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <input class="easyui-textbox" data-options="multiline:true" style="width: 100%;" name="note"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitcontractbasicForm()">保存数据</a>
        </div>
    </div>
</div>

<div id="c" class="easyui-window" title="批量新增合同条款" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:300px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="termsform" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>合同流水号</td>
                    <td>
                        <input class="easyui-textbox" id="serialsnumber2" name="serialsnumber" data-options="readonly:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>模板名称</td>
                    <td>
                        <input class="easyui-combobox" id="contracttemp1" data-options="required:false,limitToList:true,valueField:'id',textField:'contractempname',url:'contracttempname/getall'" style="width: 180px"
                        />
                    </td>
                </tr>
                <tr>
                    <td>条款模板</td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table id="addtempterms" class="easyui-datagrid" style="width: 1000px"></table>
                    </td>
                </tr>


            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" id="daoru" data-options="iconCls:'icon-ok'" onclick="submittermsForm()">导入模板</a>
        </div>

        <table cellpadding="5" align="center">
            <tr>
                <td>合同条款内容</td>
            </tr>
            <tr>
                <td colspan="4">
                    <table id="addterms" class="easyui-datagrid" style="width: 1000px"></table>
                </td>
            </tr>
        </table>
    </div>
</div>


<div id="d" class="easyui-window" title="新增合同条款" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:300px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="contentform" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>合同流水号</td>
                    <td>
                        <input class="easyui-textbox" id="serialsnumber3" name="contractbasicid" data-options="readonly:true" style="width:180px"/>
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
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitcontentForm()">保存数据</a>
        </div>
    </div>
</div>

<div id="p2" class="easyui-window" title="合同审批记录" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:100%;height:100%;padding:10px;">
    <table class="easyui-datagrid" id="approvallist" align="center" style="height:auto;"></table>
</div>

<div id="f" class="easyui-window" title="编写合同编号" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:700px;height:300px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="contentidform" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>合同流水号</td>
                    <td>
                        <input class="easyui-textbox" id="serialsnumber4" name="serialsnumber" data-options="readonly:true" style="width:180px"/>
                        <input type="hidden" name="id" id="id"/>
                    </td>
                </tr>
                <tr>
                    <td>合同号</td>
                    <td>
                        <input class="easyui-textbox" name="contractid" data-options="required:true" style="width:400px;height:60px"/>
                    </td>

                </tr>

            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitcontentidForm()">保存数据</a>
        </div>
    </div>
</div>
</body>
</html>

