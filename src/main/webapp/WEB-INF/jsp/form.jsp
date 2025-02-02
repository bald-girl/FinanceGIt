﻿<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.example.familyfinance.entity.UsersEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<html>

<%--收入支出表单--%>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>添加收入/支出</title>
    <link href="assets/css/bootstrap.css" rel="stylesheet"/>
    <link href="assets/css/font-awesome.css" rel="stylesheet"/>
    <link href="assets/css/custom-styles.css" rel="stylesheet"/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
</head>
<body>
<%
    UsersEntity principal = (UsersEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String usersName = principal.getUsersName();
    //当前用户id
    Integer usersId = principal.getUsersId();

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
                            <a href="${pageContext.request.contextPath}/pre_type" class="active-menu">收入支出</a>
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
                        收支记录
                    </h1>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <%--收入表单--%>
                                    <form role="form" action="${pageContext.request.contextPath}/createIncome"
                                          method="post" class="income-form" name="incomeForm"
                                          onsubmit="return incomeChecked(this)">
                                        <security:csrfInput/>
                                        <div>
                                            <h3 style="margin-bottom: 10px">收入详情</h3>
                                        </div>

                                        <input type="hidden" name="usersId" value="<%=usersId%>">
                                        <div class="form-group">
                                            <label>收入日期</label>
                                            <input class="form-control" type="date" name="incomeDate">
                                        </div>
                                        <div class="form-group">
                                            <label>收入金额</label>
                                            <input class="form-control" name="incomeAmount">
                                        </div>
                                        <div class="form-group">
                                            <label>收入类型</label>
                                            <select class="form-control" name="incomeType">
                                                <option value="-1">请选择收入类型</option>
                                                <%-- 使用EL表达式获取UserEntityController中查询的收入类型--%>
                                                <c:if test="${requestScope.incomeType!=null}">
                                                    <c:forEach items="${requestScope.incomeType}" var="type">
                                                        <option value="${type.incometypeName}">${type.incometypeName}</option>
                                                    </c:forEach>
                                                </c:if>
                                            </select>
                                        </div>
                                        <button type="submit" class="btn btn-default">保存记录</button>
                                        <button type="reset" class="btn btn-default">重新填写</button>
                                    </form>
                                </div>
                                <div class="col-lg-6">
                                    <%--                                    支出表单--%>
                                    <form role="form" action="${pageContext.request.contextPath}/createOutlay"
                                          method="post" class="Outlay-form" name="OutlayForm"
                                          onsubmit="return OutlayChecked(this)">
                                        <security:csrfInput/>
                                        <div>
                                            <h3 style="margin-bottom: 10px">支出详情</h3>
                                        </div>
                                        <input type="hidden" name="usersId" value="<%=usersId%>">

                                        <div class="form-group">
                                            <label>支出日期</label>
                                            <input class="form-control" type="date" name="outlayDate">
                                        </div>
                                        <div class="form-group">
                                            <label>支出金额</label>
                                            <input class="form-control" name="outlayAmount">
                                        </div>
                                        <div class="form-group">
                                            <label>支出类型</label>
                                            <select class="form-control" name="outlayType">
                                                <option value="-1">请选择收入类型</option>
                                                <c:if test="${requestScope.outlayType!=null}">
                                                    <c:forEach items="${requestScope.outlayType}" var="type">
                                                        <option value="${type.outlaytypeName}">${type.outlaytypeName}</option>
                                                    </c:forEach>
                                                </c:if>
                                            </select>
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
            <footer>
                <p align="center">Family income and expenditure management assistant.</p>
                <p align="center"> All rights reserved|Design by  Mrs Lee.</p>
            </footer>
        </div>
    </div>
</div>

<script src="assets/js/jquery-1.10.2.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/jquery.metisMenu.js"></script>
<script src="assets/js/custom-scripts.js"></script>
<script type="text/javascript">
    // 当账号或者密码为空时 不提交表单
    function incomeChecked(form) {
        if (form.incomeAmount.value === '' || form.incomeDate.value === '' || form.incomeType.value === -1) {
            alert("请将表单填写完整！");
            return false;
        }
        if (form.outlayAmount.length > 50) {
            alert("金额长度超出范围！");
            return false;
        }
        return true;
    }


    /**
     * @return {boolean}
     */
    function OutlayChecked(form) {
        if (form.outlayAmount.value === '' || form.outlayDate.value === '' || form.outlayType === '') {
            alert("请将表单填写完整！");
            return false;
        }
        if (form.outlayAmount.length > 50) {
            alert("金额长度超出范围！");
            return false;
        }
        return true;
    }

</script>

</body>
</html>
