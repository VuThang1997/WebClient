$(document).ready(function() {
	$("#message").hide();
	$("#img_loader").hide();
	$("#img_file_loader").hide();
	
	let linkDownload = protocol_client + "://" + host_client + ":" + port_client + '/download/Import_Template_File/StudentClass.xlsx';
    $("#link_report").attr("href",linkDownload);
    
    $.validator.addMethod('minStrict', function (value, el, param) {
        return value > param;
    });
	

	$('#form_create_class').validate({
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

	$("#form_create_class").submit(function(e){
		e.preventDefault();
		$("#message").hide();
		let isvalidate=$("#form_create_class").valid();
		console.log(isvalidate);
		if (!isvalidate) {
			e.preventDefault();
		}
		else {
		e.preventDefault();
		$("#img_loader").show();
		console.log("semester id = " + $('#semester_select :selected').val());
		console.log("course id = " + $('#course_select :selected').val());
		
		console.log(JSON.stringify({
				className: $("#class_name").val(),
				maxStudent: $("#max_student").val(),
				numberOfLessons: $("#number_of_lesson").val(),
				courseID: $('#course_select :selected').val(),
				semesterID: $('#semester_select :selected').val()  
			 }));
		
		$.ajax({
			type : "POST",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/classes",
			contentType : 'application/json',
			data: JSON.stringify({
				className: $("#class_name").val(),
				maxStudent: $("#max_student").val(),
				numberOfLessons: $("#number_of_lesson").val(),
				courseID: $('#course_select :selected').val(),
				semesterID: $('#semester_select :selected').val()  
			 }),
			success : function(data) {
				$("#img_loader").hide();
				$("#message").text("Thêm lớp học thành công!");
				$("#message").show();
			},
			error : function(data) {
				$("#img_loader").hide();
				$("#message").text(data.responseJSON.description);
				$("#message").show();
			}
		});
		}
		
	});
		
	//=========================FILE========================
	/*	
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
*/
});