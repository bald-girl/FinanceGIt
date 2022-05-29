<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.example.familyfinance.entity.UsersEntity" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<html>
<head>
    <title>修改个人信息</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- style CSS 登录页样式基本与注册页相同-->
    <link rel="stylesheet" href="css/register/style.css" type="text/css" media="all">
    <link rel="stylesheet" href="css/register/font-awesome.min.css" type="text/css" media="all">
    <link href="//fonts.googleapis.com/css?family=Mukta:300,400,500" rel="stylesheet">
</head>

<body>
<%

    UsersEntity principal = (UsersEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String usersName = principal.getUsersName();
    //当前用户id
    Integer usersId = principal.getUsersId();
%>
<div id="wrapper1">
    <header>
        <div class="navbar">
            <div class="container">
                <div class="navbar-right">
<%--                    <a class="navbar-index" href="${pageContext.request.contextPath}/pre_list"  style="color: #eeeeee">返回上一级</a>--%>
<%--                    <a class="navbar-index" onclick="window.history.go(-1);"  style="color: #eeeeee">返回上一级</a>--%>
                    <a class="navbar-index" href="/pre_list"  style="color: #eeeeee">返回主页</a>
                </div>
            </div>
        </div>
    </header>

    <section class="main">

        <div class="w3lhny-register">
            <h3 style="color:#99B5B4 ">亲爱的用户<%=usersName%></h3>
            <h3 style="color:#99B5B4 ">请认真填写以下信息</h3>
            <form action="${pageContext.request.contextPath}/userUpdate" method="post" class="userUpdate-form" name="userUpdateForm" onsubmit="return beforeSubmit(this);">
                <security:csrfInput/>
                <input type="hidden" name="usersId" value="<%=usersId%>">
                <fieldset>
                    <div class="form">

                        <div class="form-row">
                            <input type="password" class="form-text" placeholder="旧密码" name="originalPwd" >
                        </div>
                        <div class="form-row">
                            <input type="password" class="form-text" placeholder="新密码" name="newUsersPwd" >
                        </div>
                        <div class="form-row">
                            <input type="password" class="form-text" placeholder="确认新密码" name="newUsersPwd2" >
                        </div>

                        <div class="form-row button-login">
                            <%-- 提交表单信息给controller层--%>
                            <button class="btn1 btn-login"  >提交</button>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
        <%--    备案以及其他信息--%>
        <div class="w3l-copyright">
            <p>Family income and expenditure management assistant. All rights reserved|Design by  Mrs Lee.</p>
        </div>
    </section>
</div>

<script type="text/javascript">
    // 当表单信息不完整时 不提交表单
    function beforeSubmit(form){
        if(form.originalPwd.value===''||form.newUsersPwd.value===''||form.newUsersPwd2.value===''){
            alert('请将信息填写完整! ');
            return false;
        }
        if(form.newUsersPwd.value.length<8 ||form.newUsersPwd.value.length>20) {
            alert('密码应为8~20位，请重新输入! ');
            return false;
        }
        if(form.newUsersPwd.value!==form.newUsersPwd2.value) {
            alert('两次输入的密码不一致，请重新输入! ')
            return false;
        }
        return true ;
    }
</script>

</body>

</html>