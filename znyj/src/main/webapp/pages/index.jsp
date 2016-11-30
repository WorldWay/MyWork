<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>

<head>

<title>Hello World!!</title>

</head>
<body class="hold-transition skin-blue sidebar-mini">
	<jsp:include page="header.jsp"></jsp:include>
	
	<header class="main-header">

		<a href="index.jsp" class="logo"> <span class="logo-mini"><b>S</b>Q</span>
			<span class="logo-lg"><b>晟群科技 </b></span>
		</a>

		<nav class="navbar navbar-static-top">
			<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
				role="button"> <span class="sr-only">导航栏开关</span>
			</a>

		</nav>
	</header>

	<aside class="main-sidebar">
		<section class="sidebar">
			<div class="user-panel">
			</div>
			<!-- addNav Begin -->
			<ul class="sidebar-menu">
				<li class="header">功能菜单</li>
				<li class="active treeview">
					<a href="#"> 
						<i class="fa fa-dashboard"></i> 
						<span>功能</span> 
						<span class="pull-right-container"> 
							<i class="fa fa-angle-left pull-right"></i>	
						</span>
					</a>
					<ul class="treeview-menu">
						<li id="QY" type="btn" class="active"><a href="#"><i class="fa fa-circle-o"></i> 取药</a></li>
						<li id="check" type="btn" ><a href="#"><i class="fa fa-circle-o"></i> 盘点</a></li>
						<li id="device" type="btn" ><a href="#"><i class="fa fa-circle-o"></i> 设备管理</a></li>
						<li id="test" type="btn" ><a href="#"><i class="fa fa-circle-o"></i> 测试</a></li>
					</ul>
				</li>
			</ul>
			<!-- addNav End -->
		</section>
	</aside>

	<div id="main" class="content-wrapper">
		<!-- jsAddHtml Begin -->
		
		<iframe id="main_iframe"  scroling="no"  width="100%" height="100%" name="float" frameborder="0" >
		</iframe>
		
		<script type="text/javascript">
		
			$(function(){				
				$("li[type=btn]").click(function(){
					$("li[type=btn]").attr("class","");
					$("#main_iframe").empty();
					var name = this.id;
					$(this).attr("class","active");
					$("#main_iframe").attr("src",name+".jsp");
				});
				
			})
		</script>
		
		<!-- jsAddHtml End -->
	</div>

	<footer class="main-footer">
		<div class="pull-right hidden-xs">
			<b>Version</b> 1.0.0
		</div>
		<strong>Copyright &copy; 2016-2017 <a
			href="http://www.sunqun.net/" target="_blank">晟群科技</a>.
		</strong> All rights reserved.
	</footer>

	<script type="text/javascript">
		
	</script>
</body>
</html>