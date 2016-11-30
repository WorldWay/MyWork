<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<style type="text/css">
body {
	font-size: 30
}

button {
	font-size: 30
}

input {
	font-size: 30
}

select {
	font-size: 30
}

option {
	font-size: 30
}
</style>



</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<button id="refresh" style="width: 150px; height: 60px">刷新ip</button>
	<br>
	<select id="ipSelect" style="width: 400px;">
		<option id="1" value="1">1</option>
	</select>

	<div id="aaa" hidden="true">
		<br>
		<textarea id="order" style="min-height: 300px; min-width: 600px"
			rows="" cols="">
		</textarea>
		<br>
		<button id="send" style="height: 100px; width: 300px;">发送</button>
	</div>
	<div>
		<hr>
		<label>地址</label> <input id="addr" /> <label>命令类型</label> <input
			id="orderType" />
		<hr>
		<label>药品名称</label> <input id="medname" /> <label>效期年</label> <input
			id="medy" /> <label>效期月</label> <input id="medm" /> <br> <label>剂型</label>
		<input id="medstan" /> <label>库位（货架）</label> <input id="medpos1" />
		<label>库位（位置）</label> <input id="medpos2" />
		<hr>
		<label>处方序列号</label> <input id="medcf" /> <label>药师姓名</label> <input
			id="docname" /> <br> <label>取药数量</label> <input id="mednum" />
		<label>药品总数</label> <input id="medtotal" />
		<hr>
		<label>ID号</label> <input id="idNum" /> <label>处方停留时间</label> <input
			id="medTime" />
		<hr>
		<label>按钮延时</label> <input id="delaytime" />
		<hr>
		<button id="sendInfo" style="height: 60px; width: 200px">发送命令</button>
	</div>

	<script type="text/javascript">
	$(function(){
			$("#send").click(
					function() {
						var text = $("#order").val().replace(/\s{2,}/g, " ")
								.trim().replace(/[/\t\n\r/]/g, "");
						var ip = $("#ipSelect").val().replace(/\s{2,}/g, " ")
								.trim().replace(/[/\t\n\r/]/g, "");
						$.ajax({
							url : "sendOrder.json",
							datatype : 'json',
							type : 'post',
							data : {
								text : text,
								ip : ip
							},
							success : function(data) {
								alert("success");
							}
						})
					})

			$("#sendInfo").click(
					function() {
						var json = new Object();
						json.addr = $("#addr").val().replace(/\s{2,}/g, " ")
								.trim().replace(/[/\t\n\r/]/g, "");
						json.orderType = $("#orderType").val().replace(
								/\s{2,}/g, " ").trim().replace(/[/\t\n\r/]/g,
								"");
						json.medname = $("#medname").val().replace(/\s{2,}/g,
								" ").trim().replace(/[/\t\n\r/]/g, "");
						json.medy = $("#medy").val().replace(/\s{2,}/g, " ")
								.trim().replace(/[/\t\n\r/]/g, "");
						json.medm = $("#medm").val().replace(/\s{2,}/g, " ")
								.trim().replace(/[/\t\n\r/]/g, "");
						json.medstan = $("#medstan").val().replace(/\s{2,}/g,
								" ").trim().replace(/[/\t\n\r/]/g, "");
						json.medpos1 = $("#medpos1").val().replace(/\s{2,}/g,
								" ").trim().replace(/[/\t\n\r/]/g, "");
						json.medpos2 = $("#medpos2").val().replace(/\s{2,}/g,
								" ").trim().replace(/[/\t\n\r/]/g, "");
						json.medcf = $("#medcf").val().replace(/\s{2,}/g, " ")
								.trim().replace(/[/\t\n\r/]/g, "");
						json.docname = $("#docname").val().replace(/\s{2,}/g,
								" ").trim().replace(/[/\t\n\r/]/g, "");
						json.mednum = $("#mednum").val()
								.replace(/\s{2,}/g, " ").trim().replace(
										/[/\t\n\r/]/g, "");
						json.medtotal = $("#medtotal").val().replace(/\s{2,}/g,
								" ").trim().replace(/[/\t\n\r/]/g, "");
						json.idNum = $("#idNum").val().replace(/\s{2,}/g, " ")
								.trim().replace(/[/\t\n\r/]/g, "");
						json.medTime = $("#medTime").val().replace(/\s{2,}/g,
								" ").trim().replace(/[/\t\n\r/]/g, "");
						json.ip = $("#ipSelect").val().replace(/\s{2,}/g, " ")
								.trim().replace(/[/\t\n\r/]/g, "");
						json.delaytime = $("#delaytime").val().replace(
								/\s{2,}/g, " ").trim().replace(/[/\t\n\r/]/g,
								"");
						$.ajax({
							url : "sendMedInfo.json",
							datatype : 'json',
							type : 'post',
							data : {
								json : JSON.stringify(json)
							},
							success : function(data) {
								alert("success");
							}
						})
					})

			$("#refresh").click(
					function() {
						$
								.ajax({
									url : "getIps.json",
									datatype : 'json',
									type : 'get',
									success : function(data) {
										$("#ipSelect").empty();
										for (var i = 0; i < data.length; i++) {
											$("#ipSelect").append(
													"<option value='"+data[i].ip+"'>"
															+ data[i].ip
															+ "</option>");
										}
									}
								})
					})
		})
	</script>
</body>
</html>