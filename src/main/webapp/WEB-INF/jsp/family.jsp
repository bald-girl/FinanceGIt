<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.example.familyfinance.entity.UsersEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>添加家庭成员</title>
    <link href="assets/css/bootstrap.css" rel="stylesheet"/>
    <link href="assets/css/font-awesome.css" rel="stylesheet"/>
    <link href="assets/js/morris/morris-0.4.3.min.css" rel="stylesheet"/>
    <link href="assets/css/custom-styles.css" rel="stylesheet"/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
</head>
<body>
<%
    UsersEntity principal = (UsersEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String usersName = principal.getUsersName();
    //当前用户id
    Integer usersId = principal.getUsersId();
    Integer homeownerId = (Integer) session.getAttribute("homeownerId");
%>
<div id="wrapper">
    <nav class="navbar navbar-default top-navbar" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/jump?url=homepage"><i class="fa fa-comments"></i><strong>Finance</strong></a>

        </div>

        <ul class="nav navbar-top-links navbar-right">
            <span>您好，<%=usersName%>! 您的账号为<%=usersId%>，登录时请使用账号登录！</span>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                    <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li style="margin-left: 10px">
                        <%--                        <a href="${pageContext.request.contextPath}/pre_update"><i class="fa fa-user fa-fw"></i>修改密码</a>--%>
                        <form action="${pageContext.request.contextPath}/pre_update" method="post" >
                            <security:csrfInput/>
                            <i class="fa fa-user fa-fw"></i>
                            <input type="submit" value="修改密码" style="background-color:transparent;border-style:none;">
                        </form>
                    </li>

                    <li class="divider"></li>

                    <li style="margin-left: 10px">
                        <%--                         <a href="/jump?url=index"><i class="fa fa-sign-out fa-fw"></i>退出登录</a>--%>
                        <%--action对应securityConfig中的url 而不是controller中的url--%>
                        <form action="/logout" method="post" >
                            <security:csrfInput/>
                            <i class="fa fa-sign-out fa-fw"></i>
                            <input type="submit" value="退出登录" style="background-color:transparent;border-style:none;">
                        </form>
                    </li>
                </ul>
            </li>
        </ul>
    </nav>
    <nav class="navbar-default navbar-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="main-menu">

                <li>
                    <a href="#"><i class="fa fa-sitemap"></i>报表<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="${pageContext.request.contextPath}/pre_list">收入表</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/pre_list?type=outlay">支出表</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/pre_list?type=borrow">债务表</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/pre_list?type=lend">债权表</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/pre_list?type=family">家庭成员表</a>
                        </li>
                    </ul>
                </li>

                <li>
                    <a href="/jump?url=homepage"><i class="fa fa-dashboard"></i>统计图</a>
                </li>

                <li>
                    <a href="#"><i class="fa fa-sitemap"></i>记一笔<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="${pageContext.request.contextPath}/pre_type">收入支出</a>
                        </li>
                        <li>
                            <a href="/jump?url=form2">债权债务</a>
                        </li>
                        <li>
                            <a href="/jump?url=family">家庭成员</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>

    <div id="page-wrapper">
        <div id="page-inner">

            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">
                        家庭成员登记
                    </h1>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12" style="margin: 0 auto">
                    <div class="panel panel-default" style="width: 700px">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6" style="width: 100%; margin: 0 auto">
                                    <%--表单--%>
                                    <form role="form" action="${pageContext.request.contextPath}/createMember"
                                          method="post" class="member-form" name="memberForm"
                                          onsubmit="return memberChecked(this)">
                                        <security:csrfInput/>
                                        <div>
                                            <h3 style="margin-bottom: 10px">成员信息</h3>
                                        </div>

                                        <input type="hidden" name="homeownerId" value="<%=homeownerId%>">
                                        <input type="hidden" name="usersId" value="<%=usersId%>">
                                        <input type="hidden" name="usersName" value="<%=usersName%>">

                                        <div class="form-group">
                                            <label>成员编号</label>
                                            <input class="form-control" name="membersId">
                                        </div>
                                        <div class="form-group">
                                            <label>成员名称</label>
                                            <input class="form-control" name="membersName">
                                        </div>
                                        <div class="form-group">
                                            <label>成员与家主关系</label>
                                            <input class="form-control" name="membersRelation">
                                        </div>
                                        <button type="submit" class="btn btn-default">保存记录</button>
                                        <button type="reset" class="btn btn-default">重新填写</button>
                                    </form>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <footer>
            <p align="center">Family income and expenditure management assistant.</p>
            <p align="center"> All rights reserved|Design by  Mrs Lee.</p>
        </footer>
    </div>
</div>

<script src="assets/js/jquery-1.10.2.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/jquery.metisMenu.js"></script>
<script src="assets/js/morris/raphael-2.1.0.min.js"></script>
<script src="assets/js/morris/morris.js"></script>
<script src="assets/js/custom-scripts.js"></script>
<script type="text/javascript">
    /**
     * @return {boolean}
     */
    function memberChecked(form) {
        if(form.homeownerId.value !== form.usersId.value){
            alert("只有家主可以添加家庭成员！");
            //清空表单数据
            form.membersId.value = "";
            form.membersName.value = "";
            form.membersRelation.value = "";

            return false;
        }
        if (form.membersId.value === '' || form.membersName.value === '' || form.membersRelation.value === '') {
            alert("请将表单填写完整！");
            return false;
        }
        if (form.membersRelation.length > 50) {
            alert("成员关系字数超出范围（不大于50字）！");
            return false;
        }
        return true;
    }

</script>

</body>
</html>
