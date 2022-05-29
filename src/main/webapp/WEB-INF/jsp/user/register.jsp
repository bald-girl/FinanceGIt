<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<html>

<!-- Head -->

<head>

    <title>用户注册</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="css/register/style.css" type="text/css" media="all">
    <link rel="stylesheet" href="css/register/font-awesome.min.css" type="text/css" media="all">
    <link href="//fonts.googleapis.com/css?family=Mukta:300,400,500" rel="stylesheet">

</head>

<body>

<div id="wrapper1">
    <header>
        <div class="navbar">
            <div class="container">
                <div class="navbar-left">
                    <a class="navbar-brand" href="/jump?url=index" name="index.jsp"><img src="img/logo.png" alt="logo"/></a>
                </div>
                <div class="navbar-right">
                    <a class="navbar-index" href="/jump?url=index" name="index.jsp" style="color: #eeeeee">首页</a>
                </div>
            </div>
        </div>
    </header>


    <section class="main">
        <div class="bottom-grid">
            <div class="logo">
                <h1><a>新用户注册</a></h1>
            </div>
        </div>
        <!-- register -->
        <div class="w3lhny-register">
            <div class="iconhny">
                <%--            加了个登录注册的小头像--%>
            </div>


            <form action="${pageContext.request.contextPath}/register" method="post" class="register-form" onsubmit="return beforeSubmit(this)">
                <security:csrfInput/>
                <fieldset>
                    <div class="form">
                        <div class="form-row">
                            <input type="text" class="form-text" placeholder="请输入昵称" name="username">
                        </div>
                        <div class="form-row">
                            <input type="password" class="form-text" placeholder="请输入密码" name="password">
                        </div>
                        <div class="form-row">
                            <input type="password" class="form-text" placeholder="请确认密码" name="usersPwd2">
                        </div>
                        <div class="form-row button-login">
                            <button class="btn1 btn-login" type="submit">注册</button>
                        </div>
                    </div>
                </fieldset>
            </form>


            <%--        跳转到登录页面--%>
            <p class="already">已有账号？&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/pre_login" name="login.jsp">立即登录</a></p>
            <p class="tip">注册成功后将自动登录</p>
        </div>
        <%--    备案以及其他信息--%>
        <div class="w3l-copyright">
            <p>Family income and expenditure management assistant. All rights reserved|Design by  Mrs Lee.</p>
        </div>
    </section>
</div>


<script type="text/javascript">
    function beforeSubmit(form){
        if(form.username.value==='') {
            alert('用户名不能为空! ');
            form.username.focus();
            return false;
        }
        if(form.password.value===''){
            alert('密码不能为空! ');
            form.password.focus( );
            return false;
        }
        if(form.usersPwd2.value===''){
            alert('确认密码不能为空! ');
            form.usersPwd2.focus( );
            return false;
        }
        if(form.password.value.length<8 ||form.password.value.length>20) {
            alert('密码应为8~20位，请重新输入! ');
            form.password.focus();
            return false;
        }
        if(form.password.value!==form.usersPwd2.value) {
            alert('两次输入的密码不一致，请重新输入! ');
            form.usersPwd2.focus() ;
            return false;
        }
        return true ;
    }
</script>


</body>

</html>