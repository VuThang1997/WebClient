$(document).ready(function() {
	var courseID = -1;
	var classID = -1;
	
	$("#message_manual").hide();
	$("#img_loader").hide();
	$("#img_file_loader").hide();

	$.validator.addMethod("EMAIL", function(value, element) {
			return this.optional(element) || /^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\.[a-zA-Z.]{2,5}$/i
						.test(value);
	}, "Email không hợp lệ! ");

	$('#form_add_student_to_class').validate({
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
		courseID = $("option:selected", this).val();
		console.log("course ID after course_select is changed = " + courseID);
		$.ajax({
			type : "GET",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/listClass?courseID=" + courseID,
			contentType : 'application/json',
			success : function(data) {
				$("#class_select option[value != '0']").remove();
				//$("#error_div").hide();
				let arrayLength = data.length;
				let classInstance;
				let opt;
				for (let i = 0; i < arrayLength; i++) {
					classInstance = data[i];
					opt = document.createElement('option');
					opt.value = classInstance["id"];
					opt.innerHTML = classInstance["className"];
					$("#class_select").append(opt);
				}
			},
			error : function() {
				$("#error_div").text("Học phần này không có lớp học nào!");
				$("#error_div").show();
			}
		});
	});
	
	$("#course_select_manual").on('change', function(e) {
		let courseIDManual = $("option:selected", this).val();
		console.log("course id manual = " + courseIDManual);
		$.ajax({
			type : "GET",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/listClass?courseID=" + courseIDManual,
			contentType : 'application/json',
			success : function(data) {
				$("#class_select_manual option[value != '0']").remove();
				let arrayLength = data.length;
				let classInstance;
				let opt;
				for (let i = 0; i < arrayLength; i++) {
					$("#message_manual").hide();
					classInstance = data[i];
					opt = document.createElement('option');
					opt.value = classInstance["id"];
					opt.innerHTML = classInstance["className"];
					$("#class_select_manual").append(opt);
				}
			},
			error : function() {
				$("#error_manual").text("Học phần này không có lớp học nào!");
				$("#error_manual").show();
			}
		});
	});
	
	$("#add_student_manual").click(function(e){
		e.preventDefault();
		$("#img_loader").show();

		console.log("student email = " + $("#email").val());
		console.log("class id = " + $('#class_select_manual :selected').val());
		$.ajax({
			type : "POST",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/studentClasses",
			contentType : 'application/json',
			data: JSON.stringify({
				   studentEmail: $("#email").val(),
				   classID: $('#class_select_manual :selected').val()
				   
			 }),
			success : function(data) {
				$("#img_loader").hide();
				$("#message_manual").text(data.description);
				$("#message_manual").show();
			},
			error : function(data) {
				$("#img_loader").hide();
				$("#message_manual").text(data.responseJSON.description);
				$("#message_manual").show();
			}
		});
	});
		
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
});