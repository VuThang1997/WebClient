$(document).ready(function() {
	
	$("#message").hide();
	$("#img_loader").hide();
	//$("#img_file_loader").hide();
	//let linkDownload = protocol_client + "://" + host_client + ":" + port_client + '/download/Import_Template_File/StudentClass.xlsx';
    //$("#link_report").attr("href",linkDownload);
	
	$.validator.addMethod("EMAIL", function(value, element) {
			return this.optional(element) || /^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\.[a-zA-Z.]{2,5}$/i
						.test(value);
	}, "Email không hợp lệ! ");

	$('#form_add_teacher_to_class').validate({
		errorClass : 'errors',
		rules : {
			email : {
			required : true,
			EMAIL : "required EMAIL",
			email : true
			}
		},
		messages : {
			email : {
				required : "Mời nhập email!"
			}
		},
		highlight : function(element) {
			$(element).parent().addClass('error')
		},
		unhighlight : function(element) {
			$(element).parent().removeClass('error')
		}
	});
	
	$("#course_select").on('change', function(e) {
		$("#img_loader").show();
		$("#message").hide();
		
		let courseID = $("option:selected", this).val();
		console.log("course id = " + courseID);
		$.ajax({
			type : "GET",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/listClass?courseID=" + courseID,
			contentType : 'application/json',
			success : function(data) {
				$("#message").hide();
				$("#img_loader").hide();
				
				$("#class_select option[value != '0']").remove();
				let arrayLength = data.length;
				let classInstance;
				let opt;
				for (let i = 0; i < arrayLength; i++) {
					$("#img_loader").hide();
					$("#message").hide();
					
					classInstance = data[i];
					opt = document.createElement('option');
					opt.value = classInstance["id"];
					opt.innerHTML = classInstance["className"];
					$("#class_select").append(opt);
				}
			},
			error : function() {
				$("#img_loader").hide();
				$("#message").text("Học phần này không có lớp học nào!");
				$("#message").show();
			}
		});
	});
	
	$("#add_teacher").click(function(e){
		e.preventDefault();
		$("#img_loader").show();
		$("#message").hide();

		console.log("teacher email = " + $("#email").val());
		console.log("class id = " + $('#class_select :selected').val());
		$.ajax({
			type : "POST",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/teacherClassByWeb",
			contentType : 'application/json',
			data: JSON.stringify({
				   teacherEmail: $("#email").val(),
				   classID: $('#class_select :selected').val()
				   
			 }),
			success : function(data) {
				$("#img_loader").hide();
				$("#message").text(data.description);
				$("#message").show();
			},
			error : function(data) {
				$("#img_loader").hide();
				$("#message").text(data.responseJSON.description);
				$("#message").show();
			}
		});
	});
	
	/*
	$("#class_select").on('change', function(e) {
		$("#classId").val($("#class_select").val());
		console.log("classId in hidden input = " + $("#classId").val());
	});
		
		window.onload = function() {
			classID = sessionStorage.getItem('classID');
			courseID = sessionStorage.getItem('courseID');
			console.log("classID = " + classID);
			console.log("couseID = " + courseID);
			
			if (courseID != null) {
				$("#course_select").val(courseID);
				
				$.ajax({
					type : "GET",
					dataType : "json",
					url : protocol_server_core + "://"
							+ host_server_core + ":" + port_server_core
							+ "/listClass?courseID=" + courseID,
					contentType : 'application/json',
					success : function(data) {
						//$("#error_div").hide();
						var arrayLength = data.length;
						for (var i = 0; i < arrayLength; i++) {
							var classInstance = data[i];
							var opt = document.createElement('option');
							opt.value = classInstance["id"];
							opt.innerHTML = classInstance["className"];
							$("#class_select").append(opt);
						}
						
						classID = sessionStorage.getItem('classID');
						if (classID != null) {
							$("#class_select").val(classID);
							$("#classId").val(classID);
							console.log("classID input: "+ $("#classId").val());
						}
					},
					error : function() {
						$("#error_div").text("Học phần này không có lớp học nào!");
						$("#error_div").show();
					}
				});
			}
		}
		
		window.onbeforeunload = function() {
			sessionStorage.setItem("classID", $("#class_select").val());
			sessionStorage.setItem("courseID", $("#course_select").val());
		}
		
		$("#create_btn").click(function(e){
			 e.preventDefault();
			 $("#img_file_loader").show();
			sessionStorage.removeItem("classID");
			sessionStorage.removeItem("courseID");
			$("#form_submit").submit();
		});
		*/
});