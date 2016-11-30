<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<div class="row clearfix">

			<!-- 屏幕管理 Begin -->
			<div id="div_PD" class="box">
				<div class="box-header">
					<h3 class="box-title">药品盘点</h3>
					<hr>
					<a id="PD_start" class="btn btn-app bg-blue"><i class="fa fa-play"></i>盘点开始</a>
					<a id="PD_end" class="btn btn-app bg-blue"><i class="fa fa-stop"></i>盘点结束</a>
					<hr>
				</div>
				<div class="box-body">
					<table id="table_PD" class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>药品编号</th>
								<th>药品名称</th>
								<th>药品效期</th>
								<th>药品数量</th>
								<th style="width:100px">是否正确</th>
							</tr>
						</thead>
					</table>
				</div>
				<!--  屏幕管理 End -->

			</div>
		</div>
	</div>
	<script type="text/javascript" src="../js/table_check.js"></script>
</body>
</html>