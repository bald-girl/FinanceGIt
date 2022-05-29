<%@ page language="java" pageEncoding="UTF-8" %>
<%--跨域请求设置--%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<html>
<head>

    <title>用户登录</title>


    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <!-- style CSS 登录页样式基本与注册页相同-->
    <link rel="stylesheet" href="css/register/style.css" type="text/css" media="all">
    <link rel="stylesheet" href="css/register/font-awesome.min.css" type="text/css" media="all">
    <link href="//fonts.googleapis.com/css?family=Mukta:300,400,500" rel="stylesheet">


</head>
<!-- //Head -->

<!-- Body -->

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
                <h1><a>用户登录</a></h1>
            </div>
        </div>
        <!-- register -->
        <div class="w3lhny-register">

            <div class="iconhny">
                <%--            加了个登录注册的小头像--%>
            </div>

            <form action="${pageContext.request.contextPath}/login" method="post" class="register-form" name="loginForm"
                  onsubmit="return checked(this);">
                <security:csrfInput/>
                <fieldset>
                    <div class="form">
                        <div class="form-row">
                            <input type="text" class="form-text" placeholder="请输入账号" name="username">
                        </div>
                        <div class="form-row">
                            <input type="password" class="form-text" placeholder="请输入密码" name="password">
                        </div>
                        <div class="form-row button-login">
                            <%--                        提交表单信息给controller层--%>
                            <button class="btn1 btn-login">登录</button>
                        </div>
                    </div>
                </fieldset>
            </form>

            <%-- 跳转到注册页面 --%>
            <p class="already"><a href="${pageContext.request.contextPath}/admin_pre_login"
                                  name="adminLogin.jsp">我是管理员</a>&nbsp;&nbsp;&nbsp;&nbsp;还没有账号？<a
                    href="${pageContext.request.contextPath}/pre_register" name="register.jsp">立即注册</a></p>
        </div>
        <%--    备案以及其他信息--%>
        <div class="w3l-copyright">
            <p>Family income and expenditure management assistant. All rights reserved|Design by Mrs Lee.</p>
        </div>
    </section>
</div>

<script type="text/javascript">
    // 当账号或者密码为空时 不提交表单
    function checked(form) {
        if (form.usersId.value === '') {
            alert("账号不能为空！");
            form.usersId.focus();
            return false;
        }
        if (form.usersPwd.value === '') {
            alert("密码不能为空！");
            form.usersPwd.focus();
            return false;
        }
        return true;
    }



</script>
</body>


</html>