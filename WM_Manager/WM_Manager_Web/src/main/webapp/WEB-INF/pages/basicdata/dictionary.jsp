<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/3/26
  Time: 03:14 下午
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>数据字典</title>
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
    <script src="${ctx}/JavaScript/dictionary.js" type="text/javascript"></script>
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
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west',border:false,split:true" style="width:40%;">
            <div class="easyui-layout" data-options="fit:true">
                <div id="p1" class="easyui-panel" data-options="region:'north',split:false,border:false"
                     style="padding:2px;background: #fafafa;height:50px;">
                    <table cellpadding="5" cellspacing="1" border="0">
                        <tr>
                            <td>字典名称</td>
                            <td>
                                <input class="easyui-textbox" type="text" id="searchdedevicename"
                                       style="width: 150px;"/>
                            </td>
                            <td>
                                <a href="javascript:void(0)" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search'" onclick="dictionarysearch()">查询</a>&nbsp;&nbsp;
                                <a href="javascript:void(0)" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-reload'" onclick="dictionarysearchclean()">清空条件</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <div data-options="region:'center',border:false">
                    <table class="easyui-datagrid" id="dictionarylist" data-options="fit:true">
                    </table>
                </div>
            </div>
        </div>
        <div data-options="region:'east',border:false" style="width:60%;">
            <div class="easyui-layout" data-options="fit:true">
                <div id="p2" class="easyui-panel" data-options="region:'north',split:false,border:false"
                     style="padding:2px;background: #fafafa;height:50px;">
                    <table cellpadding="5" cellspacing="1" border="0">
                        <tr>
                            <td>字典子类名称</td>
                            <td>
                                <input class="easyui-textbox" type="text" id="searchdchild" style="width: 200px;"/>
                            </td>
                            <td>
                                <a href="javascript:void(0)" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search'" onclick="dchildsearch()">查询</a>&nbsp;&nbsp;
                                <a href="javascript:void(0)" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-reload'" onclick="dchildsearchclean()">清空条件</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <div data-options="region:'center',border:false">
                    <table class="easyui-datagrid" id="dictionaryschildlist" data-options="fit:true"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="w" class="easyui-window" title="新增数据字典大类" data-options="modal:true,closed:true,iconCls:'icon-save'"
     style="width:400px;height:220px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="dictionary" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>字典编码</td>
                    <td>
                        <input class="easyui-textbox" name="dcode" data-options="required:true" style="width:180px"/>
                        <input type="hidden" name="id"/>
                    </td>
                </tr>
                <tr>
                    <td>字典名称</td>
                    <td>
                        <input class="easyui-textbox" name="dname" data-options="required:true" style="width:180px"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
               onclick="submitdictionaryform()">保存数据</a>
        </div>
    </div>
</div>
<div id="c" class="easyui-window" title="新增数据字典子类"
     data-options="modal:true,closed:true,iconCls:'icon-save'"
     style="width:800px;height:250px;padding:10px;">
    <div style="padding:10px 20px 20px 20px">
        <form id="dictionaryschild" method="post">
            <table cellpadding="5" align="center">
                <tr>
                    <td>字典大类编码</td>
                    <td>
                        <input class="easyui-textbox" id="dcode" name="dcode" data-options="readonly:true"
                               style="width:180px"/>
                        <input type="hidden" class="easyui-textbox" name="dictionarys.id" id="dictionarysid"/>
                    </td>
                    <td>字典大类描述</td>
                    <td>
                        <input class="easyui-textbox" id="dname" name="dname" data-options="readonly:true"
                               style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>子类编码</td>
                    <td>
                        <input class="easyui-textbox" id="zname" name="code" data-options="required:true"
                               style="width:180px"/>
                        <input type="hidden" name="id"/>
                    </td>
                    <td>子类描述</td>
                    <td>
                        <input class="easyui-textbox" name="name" data-options="required:true" style="width:180px"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <input class="easyui-textbox" name="note" style="width:100%"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
               onclick="submitdictionarychildForm()">保存数据</a>
        </div>
    </div>
</div>
</body>
</html>

