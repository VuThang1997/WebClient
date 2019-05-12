$(document).ready(function() {
	var courseID = -1;
	var classID = -1;
	
	$.validator.addMethod("EMAIL", function(value, element) {
			return this.optional(element) || /^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\.[a-zA-Z.]{2,5}$/i
						.test(value);
	}, "Email không hợp lệ! ");

	$('#form_create_account').validate({
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
		$.ajax({
			type : "GET",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/listClass?courseID=" + courseID,
			contentType : 'application/json',
			success : function(data) {
				$("#error_div").hide();
				var arrayLength = data.length;
				for (var i = 0; i < arrayLength; i++) {
					var classInstance = data[i];
					var opt = document.createElement('option');
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
		
		$("#class_select").on('change', function(e) {
			$("#classId").val($("#class_select").val());
			console.log("classId input = " + $("#classId").val());
		}
		
		window.onload = function() {
			classID = sessionStorage.getItem('classID');
			courseID = sessionStorage.getItem('courseID');
			console.log("classID = " + classID);
			console.log("couseID = " + courseID);
			
			if (courseID !== "undefined") {
				$("#course_select").val(courseID);
			}
			
			if (classID !== "undefined") {
				$("#class_select").val(classID);
				console.log("class_select : "+ $("#class_select").val());
				
				//save classID to an hidden input -> for controller
				$("#classId").val(classID);
				console.log("classID input: "+ $("#classId").val());
			}
		}
		
		window.onbeforeunload = function() {
			sessionStorage.setItem("classID", $("#class_select").val());
			sessionStorage.setItem("courseID", $("#course_select").val());
		}
		
		
});
