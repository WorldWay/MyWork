$(function() {
	//盘点药品Begin
	$('#table_PD').DataTable({
		"paging" : true,
		"lengthChange" : true,
		"processing" : true,
		"searching" : true,
		"info" : true,
		"serverSide" : true,
		"ajax" : {
			url : "getChecks.json",
			type : "post"
		},
		"columns" : [ {
			"data" : "YPBH"
		}, {
			"data" : "YPMC"
		}, {
			"data" : "XQ"
		}, {
			"data" : "YPSL"
		}, {
			"data" : "isRight"
		}],
		"language" : {
			"lengthMenu" : "每页显示 _MENU_ 条数据"
		}
	});	
	
	$("#PD_start").click(function(){
		$.ajax({
			url:"PDstart.json",
			dataType:"json",
			type:"post",
			success:function(data){
				if (data.data)
					alert("盘点开始");
				else 
					alert(data.msg);
			}
		})
	});
	
	$("#PD_end").click(function(){
		$.ajax({
			url:"PDend.json",
			dataType:"json",
			type:"post",
			success:function(data){
				alert("盘点结束");
				$("#table_PD").DataTable().ajax.reload();
			}
		})
	});
	
	//盘点药品End
});