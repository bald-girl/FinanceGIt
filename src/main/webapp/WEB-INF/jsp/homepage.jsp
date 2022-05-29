<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.example.familyfinance.entity.UsersEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<%--统计图页面--%>
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>统计图</title>
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
                    <i class="fa fa-user fa-fw"></i>
                    <i class="fa fa-caret-down"></i>
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
        <div id="sideNav" href=""><i class="fa fa-caret-right"></i></div>
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
                    <a class="active-menu"  href="/jump?url=homepage"><i class="fa fa-dashboard"></i>统计图</a>
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
                        资产概况
                    </h1>
                </div>
            </div>

            <div class="row">
                <div class="col-md-3 col-sm-12 col-xs-12">
                    <div class="panel panel-primary text-center no-boder bg-color-green green">
                        <div class="panel-left pull-left green">
                            <i class="fa fa-bar-chart-o fa-5x"></i>
                        </div>
                        <div class="panel-right pull-right">
                            <h4 id="yearIncome"></h4>
                            <strong>今年总收入</strong>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 col-sm-12 col-xs-12">
                    <div class="panel panel-primary text-center no-boder bg-color-blue blue">
                        <div class="panel-left pull-left blue">
                            <i class="fa fa-shopping-cart fa-5x"></i>
                        </div>
                        <div class="panel-right pull-right">
                            <h4 id="yearOutlay"></h4>
                            <strong>今年总支出</strong>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 col-sm-12 col-xs-12">
                    <div class="panel panel-primary text-center no-boder bg-color-red red">
                        <div class="panel-left pull-left red">
                            <i class="fa fa fa-comments fa-5x"></i>
                        </div>
                        <div class="panel-right pull-right">
                            <h4 id="yearBorrow"></h4>
                            <strong>今年总借入</strong>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 col-sm-12 col-xs-12">
                    <div class="panel panel-primary text-center no-boder bg-color-brown brown">
                        <div class="panel-left pull-left brown">
                            <i class="fa fa-users fa-5x"></i>
                        </div>
                        <div class="panel-right pull-right">
                            <h4 id="yearLend"></h4>
                            <strong>今年总借出</strong>

                        </div>
                    </div>
                </div>
            </div>

            <div class="row" >
                <div class="col-md-3 col-sm-12 col-xs-12" style="width: 100%; height: 100%;">

                    <div class="panel panel-default" style="width: 100%; height: 90%">
                        <div class="panel-heading">
                            <h1>本年度资金流动图</h1>
                        </div>
                        <div class="panel-body">
                            <div id="column" style="width:1000px;height: 400px"></div>
                        </div>
                    </div>
                </div>

            </div>

            <div class="row" >
                <div class="col-md-3 col-sm-12 col-xs-12" style="width: 100%; height: 85%">
                    <div class="panel panel-default" style="width:49%; height: 90%; float:left; " >
                        <div class="panel-heading">
                            <h2>本月支出统计图（按支出类别分）</h2>
                        </div>
                        <div class="panel-body" >
                            <div id="outlayPie" style="width:400px;height: 400px"></div>
                        </div>
                    </div>

                    <div class="panel panel-default" style="width: 49%;  height: 90%;float: right; ">
                        <div class="panel-heading">
                            <h2>本月收入统计图（按收入类别分）</h2>
                        </div>
                        <div class="panel-body" >
                            <div id="incomePie" style="width:400px;height: 400px"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-3 col-sm-12 col-xs-12"  style="width: 100%; height: 80%">

                    <div class="panel panel-default" style="width:49%; height: 90%; float:left; " >
                        <div class="panel-heading">
                            <h2>本月支出统计图（按家庭成员分）</h2>
                        </div>
                        <div class="panel-body" >
                            <div id="outlayMemberPie" style="width:400px;height: 400px"></div>
                        </div>
                    </div>

                    <div class="panel panel-default" style="width: 49%; height: 90%; float: right; ">
                        <div class="panel-heading">
                            <h2>本月收入统计图（按家庭成员分）</h2>
                        </div>
                        <div class="panel-body" >
                            <div id="incomeMemberPie" style="width:400px;height: 400px"></div>
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
<script src="assets/js/morris/raphael-2.1.0.min.js"></script>
<script src="assets/js/morris/morris.js"></script>
<script src="assets/js/easypiechart.js"></script>
<script src="assets/js/easypiechart-data.js"></script>
<script src="assets/js/custom-scripts.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/echarts/5.1.0/echarts.min.js"></script>
<script>

    var myOutlayChart = echarts.init(document.getElementById('outlayPie'));
    var myIncomeChart = echarts.init(document.getElementById('incomePie'));
    var myMemberIncomeChart = echarts.init(document.getElementById('incomeMemberPie'));
    var myMemberOutlayChart = echarts.init(document.getElementById('outlayMemberPie'));
    // 指定图表的配置项和数据
    var outlayOption = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            top: '5%',
            left: 'center'
        },
        series: [
            {
                name: '支出类型、金额',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '40',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: []
            }
        ]
    };
    var incomeOption = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            top: '5%',
            left: 'center'
        },
        series: [
            {
                name: '收入类型、金额',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '40',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: []
            }
        ]
    };
    var memberIncomeOption = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        series: [
            {
                name: '收入成员编号、金额',
                type: 'pie',
                radius: '50%',
                data: [],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    var memberOutlayOption =    {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        series: [
            {
                name: '支出成员编号、金额',
                type: 'pie',
                radius: '50%',
                data: [],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };


    // 使用刚指定的配置项和数据显示图表。
    myOutlayChart.setOption(outlayOption);
    myIncomeChart.setOption(incomeOption);
    myMemberIncomeChart.setOption(memberIncomeOption);
    myMemberOutlayChart.setOption(memberOutlayOption);

    //当页面有多个图时 让图形随着窗口变化而变化
    //此处无效----暂未找到原因
    window.addEventListener("resize",function () {
        myOutlayChart.resize();
        myIncomeChart.resize();
        myMemberIncomeChart.resize();
        myMemberOutlayChart.resize();
    });

    //不同种类收入
    var myIncomeTypeData = [];
    var myOutlayTypeData = [];
    var myOutlayMemberData = [];
    var myIncomeMemberData = [];


    $.ajax({
        type : "get",
        url : "/pre_homepage",
        cache : false,
        data : {

        },
        dataType : 'json',
        success : function(data) {
            //获取总收入、支出、借入、借出
            var yearIncome = data[8];
            var yearOutlay = data[9];
            var yearBorrow = data[10];
            var yearLend = data[11];
            $("#yearIncome").text(yearIncome[0].memberYearIncome+'元');
            $("#yearOutlay").text(yearOutlay[0].memberYearOutlay+'元');
            $("#yearBorrow").text(yearBorrow[0].memberYearBorrow+'元');
            $("#yearLend").text(yearLend[0].memberYearLend+'元');

            //获取 不同收入类型对应的金额
            var TypeIncome = data[0];
            for(var i=0;i<TypeIncome.length;i++){
                myIncomeTypeData[i] = {value: TypeIncome[i].incomeTypeTotal, name:TypeIncome[i].incomeKind}
            }
            //获取 不同支出类型对应的金额
            var TypeOutlay = data[1];
            for(var i=0;i<TypeOutlay.length;i++){
                myOutlayTypeData[i] = {value: TypeOutlay[i].outlayTypeTotal, name:TypeOutlay[i].outlayKind}
            }

            var MemberOutlay = data[7];
            for(var i=0;i<MemberOutlay.length;i++){
                myOutlayMemberData[i] = {value: MemberOutlay[i].memberMonthOutlay, name:MemberOutlay[i].outlayMemberid}
            }

            var MemberIncome = data[6];
            for(var i=0;i<MemberIncome.length;i++){
                myIncomeMemberData[i] = {value: MemberIncome[i].memberMonthIncome, name:MemberIncome[i].incomeMemberid}
            }

            //将数据设置到图中
            myIncomeChart.setOption({
                series:[{
                    data: myIncomeTypeData
                }]
            });
            myOutlayChart.setOption({
                series:[{
                    data: myOutlayTypeData
                }]
            });
            myMemberIncomeChart.setOption({
                series:[{
                    data: myIncomeMemberData
                }]
            });
            myMemberOutlayChart.setOption({
                series:[{
                    data: myOutlayMemberData
                }]
            });
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {//请求失败的返回的方法
            alert("数据获取失败！请重试！");
        }
    })


</script>
<script>
    var app = {};

    var chartDom = document.getElementById('column');
    var myYearChart = echarts.init(chartDom);
    var yearOption;

    const posList = [
        'left',
        'right',
        'top',
        'bottom',
        'inside',
        'insideTop',
        'insideLeft',
        'insideRight',
        'insideBottom',
        'insideTopLeft',
        'insideTopRight',
        'insideBottomLeft',
        'insideBottomRight'
    ];
    app.configParameters = {
        rotate: {
            min: -90,
            max: 90
        },
        align: {
            options: {
                left: 'left',
                center: 'center',
                right: 'right'
            }
        },
        verticalAlign: {
            options: {
                top: 'top',
                middle: 'middle',
                bottom: 'bottom'
            }
        },
        position: {
            options: posList.reduce(function (map, pos) {
                map[pos] = pos;
                return map;
            }, {})
        },
        distance: {
            min: 0,
            max: 100
        }
    };
    app.config = {
        rotate: 90,
        align: 'left',
        verticalAlign: 'middle',
        position: 'insideBottom',
        distance: 15,
        onChange: function () {
            const labelOption = {
                rotate: app.config.rotate,
                align: app.config.align,
                verticalAlign: app.config.verticalAlign,
                position: app.config.position,
                distance: app.config.distance
            };
            myYearChart.setOption({
                series: [
                    {
                        label: labelOption
                    },
                    {
                        label: labelOption
                    },
                    {
                        label: labelOption
                    },
                    {
                        label: labelOption
                    }
                ]
            });
        }
    };
    const labelOption = {
        show: true,
        position: app.config.position,
        distance: app.config.distance,
        align: app.config.align,
        verticalAlign: app.config.verticalAlign,
        rotate: app.config.rotate,
        formatter: '{c}  {name|{a}}',
        fontSize: 16,
        rich: {
            name: {}
        }
    };
    yearOption = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['收入', '支出', '借入', '借出']
        },
        xAxis: [
            {
                type: 'category',
                axisTick: { show: false },
                data: ['1月', '2月', '3月', '4月', '5月','6月','7月','8月','9月','10月','11月','12月']
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '收入',
                type: 'bar',
                barGap: 0,
                label: labelOption,
                emphasis: {
                    focus: 'series'
                },
                data: []
            },
            {
                name: '支出',
                type: 'bar',
                label: labelOption,
                emphasis: {
                    focus: 'series'
                },
                data: []
            },
            {
                name: '借入',
                type: 'bar',
                label: labelOption,
                emphasis: {
                    focus: 'series'
                },
                data: []
            },
            {
                name: '借出',
                type: 'bar',
                label: labelOption,
                emphasis: {
                    focus: 'series'
                },
                data: []
            }
        ]
    };

   myYearChart.setOption(yearOption);

   var myIncomeData = [];
   var myOutlayData = [];
   var myBorrowData = [];
   var myLendData = [];

    $.ajax({
        type : "get",
        url : "/pre_homepage",
        cache : false,
        data : {

        },
        dataType : 'json',
        success : function(data) {
            //获取本年度本家庭每个月的收支、支出、债权、债务
            var everyMonthIncome = data[2];
            var everyMonthOutlay = data[3];
            var everyMonthBorrow = data[4];
            var everyMonthLend = data[5];

            for(var i=0;i< everyMonthIncome.length;i++ ){
                myIncomeData[i] =  everyMonthIncome[i].incomeMonthAmount;
            }
            for(var i=0;i< everyMonthOutlay.length;i++ ){
                myOutlayData[i] =  everyMonthOutlay[i].outlayMonthAmount;
            }
            for(var i=0;i< everyMonthBorrow.length;i++ ){
                myBorrowData[i] =  everyMonthBorrow[i].borrowMonthAmount;
            }
            for(var i=0;i< everyMonthLend.length;i++ ){
                myLendData[i] =  everyMonthLend[i].lendMonthAmount;
            }

            myYearChart.setOption({
                series:[
                    {
                        data: myIncomeData
                    },
                    {
                        data: myOutlayData
                    },
                    {
                        data: myBorrowData
                    },
                    {
                        data: myLendData
                    }

                ]
            });
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {//请求失败的返回的方法
            alert("false");
        }
    })
</script>
</body>

</html>