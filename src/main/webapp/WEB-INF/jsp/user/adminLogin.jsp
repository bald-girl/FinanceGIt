<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<%--管理员登录页面--%>

<head>

    <title>管理员登录</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <!-- style CSS 登录页样式基本与注册页相同-->
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
                <h1><a>管理员登录</a></h1>
            </div>
        </div>
        <!-- register -->
        <div class="w3lhny-register">

            <div class="iconhny">
                <%--            加了个登录注册的小头像--%>
            </div>

            <form action="${pageContext.request.contextPath}/adminLogin" method="post" class="register-form" name="loginForm" onsubmit="return checked(this);">
                <fieldset>
                    <div class="form">
                        <div class="form-row">
                            <input type="text" class="form-text" placeholder="请输入账号" name="adminId" >
                        </div>
                        <div class="form-row">
                            <input type="password" class="form-text" placeholder="请输入密码" name="adminPwd" >
                        </div>
                        <div class="form-row button-login">
                            <%--                        提交表单信息给controller层--%>
                            <button class="btn1 btn-login"  >登录</button>
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
    // 当账号或者密码为空时 不提交表单
    function checked(form){
        if(form.adminId.value===''){
            alert("账号不能为空！");
            form.adminId.focus();
            return false;
        }
        if(form.adminPwd.value===''){
            alert("密码不能为空！");
            form.adminPwd.focus();
            return false;
        }
        return true;
    }


</script>
</body>

</html>
