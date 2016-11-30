<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<div class="row clearfix">
			<div id="div_QY" class="box">
				<div class="box-header">
					<h3 class="box-title">取药</h3>
					<hr>
					<!-- 
					<a id="QY_start"  class="btn btn-app bg-blue"><i class="fa fa-play"></i>取药开始</a>
					-->
					<a id="QY_end" class="btn btn-app bg-blue"><i class="fa fa-stop"></i>取药结束</a>
					<hr>
				</div>		
				<script type="text/javascript">
					$(function(){
						$("#QY_start").click(function(){
							$.ajax({
								url:'QYstart.json',
								dataType:'json',
								type:'post',
								success:function(data){
									
								}
							})
						})
						
						$("#QY_end").click(function(){
							$.ajax({
								url:'QYend.json',
								dataType:'json',
								type:'post',
								success:function(data){
									
								}
							})
						})
					
					})
					
				
				</script>
			</div>
		</div>
	</div>
</body>
</html>