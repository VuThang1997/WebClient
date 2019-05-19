$(document).ready(function() {

	$("#message").hide();
	$("#img_loader").hide();
	$(".show_info").hide();
	$("#update_class").hide();
	//$("#img_file_loader").hide();
	
	$.validator.addMethod('minStrict', function (value, el, param) {
        return value > param;
    });
	

	$('#form_class_info').validate({
		errorClass : 'errors',
		rules : {
			class_name : {
				required : true,
			},
			max_student: {
			    required: true,
			    minStrict: 19,
			    number: true
			},
			number_of_lesson: {
			    required: true,
			    minStrict: 4,
			    number: true
			}
		},
		
		messages : {
			class_name : {
				required : "Mời nhập tên lớp",
			},
			max_student: {
				required : "Mời nhập số lượng học sinh tối đa!",
				minStrict: "Lớp học không thể có ít hơn 20 học sinh"
			},
			number_of_lesson: {
				required : "Mời nhập số lượng buổi học!",
				minStrict: "Lớp học không thể có ít hơn 5 buổi học"
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
		$(".show_info").hide();
		$("#message").hide();
		$("#img_loader").show();
		$("#update_class").hide();
		
		let courseID = $("option:selected", this).val();
		console.log("course ID after course_select is changed = " + courseID);
		$.ajax({
			type : "GET",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/listClass?courseID=" + courseID,
			contentType : 'application/json',
			success : function(data) {
				$("#img_loader").hide();
				$("#class_select option[value != '0']").remove();
				let arrayLength = data.length;
				let classInstance, opt;
				for (let i = 0; i < arrayLength; i++) {
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
		
	$("#class_select").on('change', function(e) {
		$(".show_info").hide();
		$("#message").hide();
		$("#img_loader").show();
		$("#update_class").hide();
		
		let classID = $("option:selected", this).val();
		console.log("class ID after course_select is changed = " + classID);
		$.ajax({
			type : "GET",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/classes?classID=" + classID,
			contentType : 'application/json',
			success : function(data) {
				$("#update_class").show();
				$(".show_info").show();
				$("#img_loader").hide();
				$("#message").hide();
				
				console.log(data);
				$("#class_name").val(data["className"]);
				$("#max_student").val(data["maxStudent"]);
				$("#number_of_lesson").val(data["numberOfLessons"]);
					
			},
			error : function(data) {
				$("#update_class").hide();
				$(".show_info").hide();
				$("#img_loader").hide();
				$("#message").text("Truy xuất thông tin lớp học thất bại!");
				$("#message").show();
			}
		});
	});
		
	$("#update_class").click(function(e){
		$("#img_loader").show();
		$("#message").hide();
		
 		e.preventDefault();
 		console.log("classID= " + $('#class_select :selected').val());
 		
 		$.ajax({
 			type : "PUT",
 			dataType : "json",
 			url : protocol_server_core + "://"
 					+ host_server_core + ":" + port_server_core
 					+ "/classes",
 			contentType : 'application/json',
 			data: JSON.stringify({
				   id: $('#class_select :selected').val(),
				   className:  $("#class_name").val(),
				   numberOfLessons: $("#number_of_lesson").val(),
				   maxStudent: $("#max_student").val(),
				   courseID: $('#course_select :selected').val(),
				   semesterID: $('#semester_select :selected').val()
 			 }),
 			success : function(data) {
				
 				$("#img_loader").hide();
 				$("#message").text("Cập nhật thành công!");
 				$("#message").show();
				
				$("#class_select").find("option:selected").text($("#class_name").val());
 			},
 			error : function(data) {
				console.log(data);
 				$("#message").text(data.responseJSON.description);
 				$("#message").show();
				
				
				$("#img_loader").hide();
				$(".show_info").hide();
				$("#update_semester").hide();
				$("#semester_select").val(0);
				$("#course_select").val(0);
 			}
 		});
 	});
});