<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.example.familyfinance.entity.UsersEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>支出表</title>
    <link href="assets/css/bootstrap.css" rel="stylesheet"/>
    <link href="assets/css/font-awesome.css" rel="stylesheet"/>
    <link href="assets/css/custom-styles.css" rel="stylesheet"/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
    <link href="assets/js/dataTables/dataTables.bootstrap.css" rel="stylesheet"/>
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
                            <a class="active-menu" href="${pageContext.request.contextPath}/pre_list?type=outlay">支出表</a>
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
            <%--            支出--%>
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h2>支出表</h2>
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                    <tr>
                                        <th>支出时间</th>
                                        <th>支出成员编号</th>
                                        <th>支出金额</th>
                                        <th>支出类型</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${requestScope.outlayEntityList!=null}">
                                        <c:forEach items="${requestScope.outlayEntityList}" var="outlayEntity">
                                            <tr class="gradeA">
                                                <td><fmt:formatDate value="${outlayEntity.outlayTime}"
                                                                    pattern="yyyy-MM-dd"/></td>
                                                <td>${outlayEntity.outlayMemberid}</td>
                                                <td>${outlayEntity.outlayAmount}</td>
                                                <td>${outlayEntity.outlayKind}</td>
                                                <td>
                                                    <ul class="actions">
                                                        <li><a href="${pageContext.request.contextPath}/delOutlay?outlayId=${outlayEntity.outlayId}&usersId=<%=usersId%>" onclick="return del()"><i class="table-delete" title="删除"></i></a></li>
                                                    </ul>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    </tbody>
                                </table>

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
<script src="assets/js/dataTables/jquery.dataTables.js"></script>
<script src="assets/js/dataTables/dataTables.bootstrap.js"></script>
<script>
    $(document).ready(function () {
        $('#dataTables-example').dataTable();
    });

    function del() {
        if (confirm("您确认要删除吗？")) {
            return true;
        } else {
            return false;
        }
    }




</script>
<script src="assets/js/custom-scripts.js"></script>

</body>
</html>
