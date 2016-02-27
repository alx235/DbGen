$(document).ready(
		function() {

			$('#clear').click(function() {
				$('#crt_hld').text("");
				$('#slt_hld').text("");
				$('#upt_hld').text("");
			})

			$('#crt_btn').click(
					function() {
						var sch_val = $("#sch").val();
						var tbl_val = $("#tbl").val();
						if (sch_val == "" || tbl_val == "")
							return;
						$.ajax({
							type : "POST",
							url : "Gen/Create",
							data : {
								sch_name : sch_val,
								tbl_name : tbl_val
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								alert("Request: " + XMLHttpRequest.toString()
										+ "\n\nStatus: " + textStatus
										+ "\n\nError: " + errorThrown);
							},
							success : function(data) {
								$('#crt_hld').text(data);
							}
						});
					})

			$('#slt_btn').click(
					function() {
						var sch_val = $("#sch").val();
						var tbl_val = $("#tbl").val();
						if (sch_val == "" || tbl_val == "")
							return;
						$.ajax({
							type : "POST",
							url : "Gen/Select",
							data : {
								sch_name : sch_val,
								tbl_name : tbl_val
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								alert("Request: " + XMLHttpRequest.toString()
										+ "\n\nStatus: " + textStatus
										+ "\n\nError: " + errorThrown);
							},
							success : function(data) {
								$('#slt_hld').text(data);
							}
						});
					})

			$('#upt_btn').click(
					function() {
						var sch_val = $("#sch").val();
						var tbl_val = $("#tbl").val();
						if (sch_val == "" || tbl_val == "")
							return;
						$.ajax({
							type : "POST",
							url : "Gen/Update",
							data : {
								sch_name : sch_val,
								tbl_name : tbl_val
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								alert("Request: " + XMLHttpRequest.toString()
										+ "\n\nStatus: " + textStatus
										+ "\n\nError: " + errorThrown);
							},
							success : function(data) {
								$('#upt_hld').text(data);
							}
						});
					})
		});