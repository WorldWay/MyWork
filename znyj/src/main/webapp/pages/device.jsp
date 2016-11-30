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
			<div class="tabbable" id="tabs-000001">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#panel-001" class="btn btn-info" data-toggle="tab">中继器管理</a>
					</li>
					<li><a href="#panel-002" class="btn btn-info" data-toggle="tab">屏幕管理</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-001">

						<!-- 中继器管理 Begin -->
						<div id="div_ZJQ" class="box">
							<div class="box-header">
								<h3 class="box-title">中继器管理</h3>
							</div>
							<div class="box-body">
								<table id="table_ZJQ" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>ID</th>
											<th>中继器IP（port）</th>
											<th>货架名称</th>
											<th>编辑</th>
											<th>在线状态</th>
										</tr>
									</thead>
								</table>
							</div>
							<!--  中继器管理 End -->

							<!-- 编辑遮罩层 Begin -->
							<div class="modal fade" id="edit_ZJQ" role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">×</button>
											<h4 class="modal-title" id="myModalLabel">编辑</h4>
										</div>
										<div class="modal-body">
											 <label for="edit_ZJQ_id">ID</label>
											 <input disabled="disabled"  class="form-control" id="edit_ZJQ_id" />
											 <label for="edit_ZJQ_ZJQIP">中继器IP</label>
											 <input disabled="disabled" class="form-control" id="edit_ZJQ_ZJQIP" />
											 <label for="edit_ZJQ_ZJQMC">货架名称</label>
											 <input class="form-control" id="edit_ZJQ_ZJQMC" />
										</div>
										<div class="modal-footer">										
											<button id="edit_btn_ZJQ" type="button" class="btn btn-save bg-blue">保存</button>
											<button type="button" class="btn btn-default"
												data-dismiss="modal">关闭</button>
										</div>
									</div>
								</div>
							</div>
							<!-- 编辑遮罩层 End -->
							
							<!-- 删除遮罩层 Begin -->
							<div class="modal fade"  id="remove_ZJQ"  role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">×</button>
											<h4 class="modal-title" id="myModalLabel">警告</h4>
										</div>
										<div class="modal-body">
											 <label for="remove_ZJQ_id">ID</label>
											 <input disabled="disabled"  class="form-control" id="remove_ZJQ_id" />
											 <label for="remove_ZJQ_ZJQIP">中继器IP</label>
											 <input disabled="disabled" class="form-control" id="remove_ZJQ_ZJQIP" />
										</div>
										<div class="modal-footer">										
											<button id="remove_btn_ZJQ" type="button" class="btn btn-remove bg-red">删除</button>
											<button type="button" class="btn btn-default"
												data-dismiss="modal">关闭</button>
										</div>
									</div>
								</div>
							</div>
							<!-- 删除遮罩层 End -->
						</div>
						
					</div>
					<div class="tab-pane" id="panel-002">

						<!-- 屏幕管理 Begin -->
						<div id="div_PM" class="box">
							<div class="box-header">
								<h3 class="box-title">屏幕管理</h3>
								<hr>
								<a id="PM_start" class="btn btn-app bg-blue"><i class="fa fa-play"></i>分配地址</a>
								<a id="PM_end" class="btn btn-app bg-blue"><i class="fa fa-stop"></i>分配结束</a>
								<hr>
							</div>
							<div class="box-body">
								<table id="table_PM" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>中继器IP（port）</th>
											<th>屏幕ID号</th>
											<th>屏幕地址</th>
											<th>药品编号</th>
											<th>库位1</th>
											<th>库位2</th>
											<th>编辑</th>
										</tr>
									</thead>
								</table>
							</div>
							<!--  屏幕管理 End -->

							<!-- 编辑遮罩层 Begin -->
							<div class="modal fade" id="edit_PM" role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">×</button>
											<h4 class="modal-title" id="myModalLabel">编辑</h4>
										</div>
										<div class="modal-body">
											 <label for="edit_PM_ZJQIP">中继器IP</label>
											 <input disabled="disabled" class="form-control" id="edit_PM_ZJQIP" />
											 <label for="edit_PM_PMID">屏幕ID号</label>
											 <input class="form-control" id="edit_PM_PMID" />
											 <label for="edit_PM_PMDZ">屏幕地址</label>
											 <input disabled="disabled" class="form-control" id="edit_PM_PMDZ" />
											 <label for="edit_PM_YPBH">药品编号</label>
											 <input class="form-control" id="edit_PM_YPBH" />
											 <label for="edit_PM_KW1">库位1</label>
											 <input class="form-control" id="edit_PM_KW1" />
											 <label for="edit_PM_KW2">库位2</label>
											 <input class="form-control" id="edit_PM_KW2" />
										</div>
										<div class="modal-footer">										
											<button id="edit_btn_PM" type="button" class="btn btn-save bg-blue">保存</button>
											<button type="button" class="btn btn-default"
												data-dismiss="modal">关闭</button>
										</div>
									</div>
								</div>
							</div>
							<!-- 编辑遮罩层 End -->
							
							<!-- 删除遮罩层 Begin -->
							<div class="modal fade"  id="remove_PM"  role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">×</button>
											<h4 class="modal-title" id="myModalLabel">警告</h4>
										</div>
										<div class="modal-body">
											 <label for="remove_PM_PMID">屏幕ID号</label>
											 <input disabled="disabled" class="form-control" id="remove_PM_PMID" />
										</div>
										<div class="modal-footer">										
											<button id="remove_btn_PM" type="button" class="btn btn-remove bg-red">删除</button>
											<button type="button" class="btn btn-default"
												data-dismiss="modal">关闭</button>
										</div>
									</div>
								</div>
							</div>
							<!-- 删除遮罩层 End -->
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="../js/table_device.js">			
	</script>
</body>
</html>