<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>系统管理</title>
    <link href="assets/css/bootstrap.css" rel="stylesheet"/>
    <link href="assets/css/font-awesome.css" rel="stylesheet"/>
    <link href="assets/css/custom-styles.css" rel="stylesheet"/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
    <link href="assets/js/dataTables/dataTables.bootstrap.css" rel="stylesheet"/>

</head>
<body>





<div style="background-color: #e7e7e9;">

        <div id="page-inner" style=" margin: 0px auto;width: 90%;">

            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">
                    系统管理
                    </h1>
                </div>
            </div>
            <%-- 收入类型--%>
             <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading" style="height: 50px">
                            <h2 style="float:left">收入类型</h2>
                            <h3 style="float:right"><a href="/jump?url=addType">添加收入类型</a></h3>
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example5">
                                    <thead>
                                    <tr>
                                        <td>收入类型编号</td>
                                        <td>收入类型名称</td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${requestScope.incomeTypeList!=null}">
                                        <c:forEach items="${requestScope.incomeTypeList}" var="incomeType">
                                            <tr>
                                                <td>${incomeType.incometypeId}</td>
                                                <td>${incomeType.incometypeName}</td>
                                                <td><a href="${pageContext.request.contextPath}/delIncomeType?incometypeId=${incomeType.incometypeId}" onclick="return del()">删除</a></td>
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
            <%-- 支出类型--%>
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading" style="height: 50px">
                            <h2 style="float:left">支出类型</h2>
                            <h3 style="float:right"><a href="/jump?url=addType">添加支出类型</a></h3>
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example6">
                                    <thead>
                                    <tr>
                                        <td>支出类型编号</td>
                                        <td>支出类型名称</td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${requestScope.outlayTypeList!=null}">
                                        <c:forEach items="${requestScope.outlayTypeList}" var="outlayType">
                                            <tr>
                                                <td>${outlayType.outlaytypeId}</td>
                                                <td>${outlayType.outlaytypeName}</td>
                                                <td><a href="${pageContext.request.contextPath}/delOutlayType?outlaytypeId=${outlayType.outlaytypeId}" onclick="return del()">删除</a></td>

                                            </tr>
                                        </c:forEach>
                                    </c:if>

                                    <form id="Form" method="post" >
                                        <input type="hidden" name="condition.Id" value="">
                                    </form>

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

<script src="assets/js/jquery-1.10.2.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/jquery.metisMenu.js"></script>
<script src="assets/js/dataTables/jquery.dataTables.js"></script>
<script src="assets/js/dataTables/dataTables.bootstrap.js"></script>
<script>
    $(document).ready(function () {
        $('#dataTables-example5').dataTable();
        $('#dataTables-example6').dataTable();
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
