<%@ page language="java" pageEncoding="UTF-8" %>

<%--未登录时显示的首页 即宣传页--%>
<html>
    <head>
        <meta charset="utf-8">
        <title>家庭收支管理助手主页</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="description" content="" />
        <meta name="author" content="http://webthemez.com" />

        <!-- css -->
        <link href="css/index/bootstrap.min.css" rel="stylesheet" />
        <link href="css/index/flexslider.css" rel="stylesheet" />
        <link href="css/index/style.css" rel="stylesheet" />

    </head>
<body>
<div id="wrapper">
    <header>
        <div class="navbar navbar-default navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/jump?url=index" name="index.jsp"><img style="border-radius: 5px;height: 50px;width: 50px" src="img/logo.jpg" alt="logo"/></a>
                </div>
                <div class="navbar-collapse collapse ">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="/jump?url=index" name="index.jsp" >主页</a></li>
                        <li><a href="/pre_login" name="login.jsp" >登录</a></li>
                        <%-- 将请求转到控制层--%>
                        <li><a href="${pageContext.request.contextPath}/pre_register" name="register.jsp" >注册</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </header>
    <!-- end header -->
    <section id="featured">
        <!-- Slider -->
        <div id="main-slider" class="flexslider">
            <ul class="slides">
                <li>
                    <img src="img/slides/1.jpg" alt=""/>
                    <div class="flex-caption">
                        <div class="item_introtext">
                            <span>家庭收支管理</span>
                            <strong>就选我们</strong>
                            <p>帮您随时随地管理您的资产!</p> </div>
                    </div>
                </li>
                <li>
                    <img src="img/slides/2.jpg" alt=""/>
                    <div class="flex-caption">
                        <div class="item_introtext">
                            <span>我们是</span>
                            <strong>全球首个</strong>
                            <p>随叫随到的家庭理财专家！</p> </div>
                    </div>
                </li>
                <li>
                    <img src="img/slides/3.jpg" alt=""/>
                    <div class="flex-caption">
                        <div class="item_introtext">
                            <span>我们的</span>
                            <strong>理念</strong>
                            <p>100%的质量+100%的诚信=顾客满意的笑脸!</p> </div>
                    </div>
                </li>
            </ul>
        </div>
    </section>

    <section class="callaction">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="aligncenter"><h1 class="aligncenter">家庭收支管理助手</h1>
                        <span
                            class="clear spacer_responsive_hide_mobile " style="height:13px;display:block;"></span>
                        本系统具有各类信息的查看、增加、删除、修改、报表等功能，
                        主要包括家庭成员管理、成员收入管理、成员支出管理、债权债务管理、统计报表管理、系统管理共六大模块。
                        系统操作简单、界面简洁，用户可以从各个维度查看账目状况，轻松读懂账单报表。
                        家庭收支管理助手还有预算提醒功能，使得家庭实实在在将钱用在刀刃上，有效地帮助家庭合理支配财产。
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section id="content">
        <div class="container">
            <div class="row">
                <div class="skill-home">
                    <div class="skill-home-solid clearfix">
                        <div class="col-md-3 text-center">
                            <span class="icons c1"><i class="fa fa-trophy"></i></span>
                            <div class="box-area">
                                <h3>发财了怎么办？</h3>
                                <p>做一个精明而时尚的人！免费在线记帐本－－“家庭收支管理助手”－－顶你！</p></div>
                        </div>
                        <div class="col-md-3 text-center">
                            <span class="icons c2"><i class="fa fa-picture-o"></i></span>
                            <div class="box-area">
                                <h3>懂记账</h3>
                                <p>家庭收支管理助手，最简洁的记账网站，百万用户的共同选择，您的财务管家！</p></div>
                        </div>
                        <div class="col-md-3 text-center">
                            <span class="icons c3"><i class="fa fa-desktop"></i></span>
                            <div class="box-area">
                                <h3>家人共享记账</h3>
                                <p>发个信息吧，一键邀请家人共同记账，账单、视图实时同步，共睹每笔账单，告别糊涂账！</p></div>
                        </div>
                        <div class="col-md-3 text-center">
                            <span class="icons c4"><i class="fa fa-globe"></i></span>
                            <div class="box-area">
                                <h3>3秒快速记账</h3>
                                <p>一键记账，记账就是这么简单，随时随地记账；多种账目分类，让您的账单清清楚楚！</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <footer>
        <div class="container">
            <div class="row">
                <div class="col-lg-3">
                    <div class="widget">
                        <h5 class="widgetheading">品质保证</h5>
                        <address>
                            <strong>数百家服务机构</strong>
                            <br>
                            国内获IDG投资互联网财税企业
                        </address>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="widget">
                        <h5 class="widgetheading">专业实力</h5>
                        <address>
                            <strong>资深财税团队</strong>
                            <br>
                            专业会计团队
                        </address>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="widget">
                        <h5 class="widgetheading">安全无忧</h5>
                        <address>
                            <strong>2048位安全证书</strong>
                            <br>
                            银行级别的系统安全
                        </address>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="widget">
                        <h5 class="widgetheading">多元服务</h5>
                        <address>
                            <strong>工商注册公司托管、税务代办</strong>
                            <br>
                            财务规划和咨询等增值服务
                        </address>
                    </div>
                </div>
            </div>
        </div>
    </footer>
</div>
<a href="#" class="scrollup"><i class="fa fa-angle-up active"></i></a>

<script src="js/index/jquery.js"></script>
<script src="js/index/bootstrap.min.js"></script>
<script src="js/index/jquery.fancybox.pack.js"></script>
<script src="js/index/jquery.flexslider.js"></script>
<script src="js/index/custom.js"></script>

</body>
</html>