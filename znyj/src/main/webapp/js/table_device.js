$(function() {
	//中继器管理Begin
	$('#table_ZJQ').DataTable({
		"paging" : true,
		"lengthChange" : true,
		"processing" : true,
		"searching" : true,
		"info" : true,
		"serverSide" : true,
		"ajax" : {
			url : "getZJQs.json",
			type : "post"
		},
		"columns" : [ {
			"data" : "id"
		}, {
			"data" : "ZJQIP"
		}, {
			"data" : "ZJQMC"
		}, {
			"data" : "btns"
		}, {
			"data" : "isOn"
		} ],
		"language" : {
			"lengthMenu" : "每页显示 _MENU_ 条数据"
		}
	});

	$("#table_ZJQ").on('click', 'tbody tr td div a', function() {
		var id = $(this).attr("data_id");
		var ZJQIP = $(this).attr("data_ZJQIP");
		var ZJQMC = $(this).attr("data_ZJQMC");
		var type = $(this).attr("href");
		switch (type) {

		case "#edit_ZJQ":
			$("#edit_ZJQ_id").val(id);
			$("#edit_ZJQ_ZJQIP").val(ZJQIP);
			$("#edit_ZJQ_ZJQMC").val(ZJQMC);
			break;

		case "#remove_ZJQ":
			$("#remove_ZJQ_id").val(id);
			$("#remove_ZJQ_ZJQIP").val(ZJQIP);
			break;

		default:
			break;
		}
	})

	$("#edit_btn_ZJQ").click(function() {
		var id = $("#edit_ZJQ_id").val();
		var ZJQIP = $("#edit_ZJQ_ZJQIP").val();
		var ZJQMC = $("#edit_ZJQ_ZJQMC").val();
		var data = JSON.stringify({
			id : id,
			ZJQIP : ZJQIP,
			ZJQMC : ZJQMC
		});
		$.ajax({
			url : "upZJQ.json",
			dataType : "json",
			type : "post",
			data : {
				json : data
			},
			success : function(data) {
				alert("保存成功");
				$("#edit_ZJQ").modal('hide');
				$('#table_ZJQ').DataTable().ajax.reload();
			}
		});
	});

	$("#remove_btn_ZJQ").click(function() {
		var id = $("#remove_ZJQ_id").val();
		var ZJQIP = $("#remove_ZJQ_ZJQIP").val();
		var data = JSON.stringify({
			id : id,
			ZJQIP : ZJQIP
		});
		$.ajax({
			url : "delZJQ.json",
			dataType : "json",
			type : "post",
			data : {
				json : data
			},
			success : function(data) {
				if (data.data)
					alert("删除成功");
				else
					alert("请先断开中继器连接");
				$("#remove_ZJQ").modal('hide');
				$('#table_ZJQ').DataTable().ajax.reload();
			}
		});
	});
	//中继器管理End
	
	//屏幕管理Begin
	$('#table_PM').DataTable({
		"paging" : true,
		"lengthChange" : true,
		"processing" : true,
		"searching" : true,
		"info" : true,
		"serverSide" : true,
		"ajax" : {
			url : "getPMs.json",
			type : "post"
		},
		"columns" : [ { 
			"data" : "ZJQIP"
		}, {
			"data" : "PMID"
		}, {
			"data" : "PMDZ"
		},{
			"data" : "YPBH"
		},{
			"data" : "KW1"
		},{
			"data" : "KW2"
		},{
			"data" : "btns"
		} ],
		"language" : {
			"lengthMenu" : "每页显示 _MENU_ 条数据"
		}
	});

	$("#table_PM").on('click', 'tbody tr td div a', function() {
		var PMID = $(this).attr("data_PMID");
		var ZJQIP = $(this).attr("data_ZJQIP");
		var PMDZ = $(this).attr("data_PMDZ");
		var YPBH = $(this).attr("data_YPBH");
		var KW1 = $(this).attr("data_KW1");
		var KW2 = $(this).attr("data_KW2");
		var type = $(this).attr("href");
		switch (type) {

		case "#edit_PM":
			$("#edit_PM_PMID").val(PMID);
			$("#edit_PM_ZJQIP").val(ZJQIP);
			$("#edit_PM_PMDZ").val(PMDZ);
			$("#edit_PM_YPBH").val(YPBH);
			$("#edit_PM_KW1").val(KW1);
			$("#edit_PM_KW2").val(KW2);
			break;

		case "#remove_PM":
			$("#remove_PM_PMID").val(PMID);
			break;

		default:
			break;
		}
	})

	$("#edit_btn_PM").click(function() {
		var re = /^\d+$/;
		
		var PMID = $("#edit_PM_PMID").val();
		var ZJQIP = $("#edit_PM_ZJQIP").val();
		var PMDZ = $("#edit_PM_PMDZ").val();
		var YPBH = $("#edit_PM_YPBH").val();			
		var KW1 = $("#edit_PM_KW1").val();
		var KW2 = $("#edit_PM_KW2").val();		
		if (!re.test(KW1) || !re.test(KW2))
		{
			alert("库位请输入两位整数");
			return;
		}		
		if (!(KW1>=0 & KW1<100 & KW2>=0 & KW2<100)) 
		{
			alert("库位请输入两位整数");
			return;
		}
		
		var data = JSON.stringify({
			PMID : PMID,
			ZJQIP : ZJQIP,
			PMDZ : PMDZ,
			YPBH : YPBH,
			KW1 : KW1,
			KW2 : KW2
		});
		$.ajax({
			url : "upPM.json",
			dataType : "json",
			type : "post",
			data : {
				json : data
			},
			success : function(data) {
				alert("保存成功");
				$("#edit_PM").modal('hide');
				$('#table_PM').DataTable().ajax.reload();
			}
		});
	});

	$("#remove_btn_PM").click(function() {
		var PMID = $("#remove_PM_PMID").val();
		var data = JSON.stringify({
			PMID : PMID
		});
		$.ajax({
			url : "delPM.json",
			dataType : "json",
			type : "post",
			data : {
				json : data
			},
			success : function(data) {
				if (data.data)
					alert("删除成功");
				$("#remove_PM").modal('hide');
				$('#table_PM').DataTable().ajax.reload();
			}
		});
	});
	
	$("#PM_start").click(function(){
		$.ajax({
			url : "PMstart.json",
			dataType : "json",
			type : "post",
			data : {},
			success : function(data) {
					alert("分配地址开始");
			}
		});
	})
	
	$("#PM_end").click(function(){
		$.ajax({
			url : "PMend.json",
			dataType : "json",
			type : "post",
			data : {},
			success : function(data) {
					alert("分配地址结束");
					$('#table_PM').DataTable().ajax.reload();
			}
		});
	})
	//屏幕管理End
	
});