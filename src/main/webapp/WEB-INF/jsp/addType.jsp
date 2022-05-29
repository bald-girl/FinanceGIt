<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>添加收入/支出类型</title>
    <link href="assets/css/bootstrap.css" rel="stylesheet"/>
    <link href="assets/css/font-awesome.css" rel="stylesheet"/>
    <link href="assets/css/custom-styles.css" rel="stylesheet"/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
</head>
<body>
<div style="background-color: #e7e7e9;">

    <div id="page-inner" style=" margin: 0px auto;width: 90%;">
        <div class="col-lg-6">
            <%--                                    收入类型表单--%>
            <form role="form" action="${pageContext.request.contextPath}/createIncomeType"
                  method="post" class="Outlay-form" name="OutlayForm"
                  onsubmit="return IncomeTypeChecked(this)">
                <div>
                    <h3 style="margin-bottom: 10px">添加收入类型</h3>
                </div>
                <div class="form-group">
                    <label>收入类型名称</label>
                    <input class="form-control" name="incomeTypeName">
                </div>
                <button type="submit" class="btn btn-default">保存记录</button>
                <button type="reset" class="btn btn-default">重新填写</button>
            </form>
        </div>
        <div class="col-lg-6">
            <%--                                    支出类型表单--%>
            <form role="form" action="${pageContext.request.contextPath}/createOutlayType"
                  method="post" class="Outlay-form" name="OutlayForm"
                  onsubmit="return OutlayTypeChecked(this)">
                <div>
                    <h3 style="margin-bottom: 10px">添加支出类型</h3>
                </div>
                <div class="form-group">
                    <label>支出类型名称</label>
                    <input class="form-control" name="outlayTypeName">
                </div>
                <button type="submit" class="btn btn-default">保存记录</button>
                <button type="reset" class="btn btn-default">重新填写</button>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    // 当账号或者密码为空时 不提交表单
    /**
     * @return {boolean}
     */
    function IncomeTypeChecked(form) {
        if (form.incomeTypeName.value === '') {
            alert("请将表单填写完整！");
            return false;
        }

        return true;
    }

    /**
     * @return {boolean}
     */
    function OutlayTypeChecked(form) {
        if (form.outlayTypeName.value === '') {
            alert("请将表单填写完整！");
            return false;
        }
        return true;
    }
</script>
</body>
</html>
