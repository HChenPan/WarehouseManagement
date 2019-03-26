<%--
  Created by Huangcp
  User: Huangcp
  Date: 2019/3/4
  Time: 03:04 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>仓库管理系统</title>
    <script src="${ctx}/statics/js/jquery-1.8.0.min.js" type="text/javascript"></script>
    <script src="${ctx}/statics/js/jquery.md5.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryvalidation/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryvalidation/messages_zh.min.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${ctx}/statics/jqueryeasyui/easyloader.js" type="text/javascript"></script>
    <script src="${ctx}/JavaScript/login-tips.js" type="text/javascript"></script>
    <link href="${ctx}/css/login.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/form.css" rel="stylesheet" type="text/css"/>
    <link rel="icon" type="image/x-icon" href="${ctx}/statics/img/biaoshi.ico"/>
</head>

<body>
<div id="login" class="login">
    <div class="header">
    </div>
    <!--header end-->
    <div class="main">
        <div class="main_content">
            <div class="main_content">
                <div class="loginBox">
                    <div class="loginBox_title">
                        <img src="${ctx}/statics/img/title.png" class="title" alt="仓库管理系统"/>
                    </div>
                    <form action="${ctx}/login.do" Class="border_radius" name="login_form" id="focus"
                          method="post">
                        <label id="fuserid" class="label_input">
                            <span>用户名</span>
                            <input type="text" class="input_txt border_radius form-control" name="username"
                                   id="username"
                                   value="${model.username}"
                                   onkeydown="KeyDown()">
                            <span class="error">${errors.username}</span>
                        </label>
                        <label id="fpassword" class="label_input">
                            <span>密码</span>
                            <input type="password" class="input_txt border_radius form-control" id="password"
                                   name="password"
                                   onkeydown="KeyDown()" style="margin-top: 0px;">
                            <span class="error">${errors.password}</span>
                        </label>

                        <div class="btn">
                            <div class="left">
                                <input type="submit" value="登录" Class="btn1"
                                <%--鼠标压下时应用的css样式--%>
                                       onmousedown="this.className='btn1 btn1_down'"
                                <%--鼠标离开后应用的css样式--%>
                                       onmouseout="this.className='btn1'"
                                <%--鼠标移上去时应用的css样式--%>
                                       onmouseover="this.className='btn1 btn1_hover'"
                                       name="ibtn_Login"/>
                            </div>
                            <div class="right">
                                <input type="reset" value="重置" Class="btn1 btn2"
                                       onmousedown="this.className='btn1 btn2_down'"
                                       onmouseout="this.className='btn1 btn2'"
                                       onmouseover="this.className='btn1 btn2_hover'"
                                       onmouseup="f_input_reset()"
                                       name="ibtn_Reset"/>
                            </div>
                            <div class="checkBox">

                            </div>
                            <div class="loginInfo">
                                <label>新增用户口令默认为123456,请登录后自行修改</label>
                                <br>
                                <label>${msg}</label>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div><!--main end-->
        <div class="footer" style="line-height:150px;">
            <div class="ft_content">
                <b style=" color:red;">Copyright 2018 &nbsp; &nbsp; AAA</b>
            </div>

        </div><!--footer end-->
    </div><!--login end-->
</div>
</body>
</html>
