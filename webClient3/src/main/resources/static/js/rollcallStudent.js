$(document).ready(function() {
	//var courseID = -1;
	//var classID = -1;
	$("#message").hide();
	$("#message_manual").hide();
	$("#img_file_loader").hide();
	$("#img_loader").hide();
	let linkDownload = protocol_client + "://" + host_client + ":" + port_client + '/download/Import_Template_File/StudentClass.xlsx';
    $("#link_report").attr("href",linkDownload);
	$.validator
                            .addMethod(
                                    "EMAIL",
                                    function (value, element) {
                                        return this.optional(element)
                                                || /^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\.[a-zA-Z.]{2,5}$/i
                                                .test(value);
                                    }, "Email không hợp lệ! ");
	$('#rollcall_for_student').validate({
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
	window.onload = function() {
		if (sessionStorage.getItem('courseID') != null) {
			let classID = sessionStorage.getItem('classID');
			let courseID = sessionStorage.getItem('courseID');
			let roomID = sessionStorage.getItem('roomID');
			$("#course_select").val(courseID);
			console.log("classID = " + classID);
			console.log("couseID = " + courseID);
			console.log("roomID = " + roomID);
			
			$.ajax({
				type : "GET",
				dataType : "json",
				url : protocol_server_core + "://"
						+ host_server_core + ":" + port_server_core
						+ "/listClass?courseID=" + courseID,
				contentType : 'application/json',
				success : function(data) {
					$("#error_div").hide();
					let arrayLength = data.length;
					let classInstance, opt;
					for (let i = 0; i < arrayLength; i++) {
						classInstance = data[i];
						opt = document.createElement('option');
						opt.value = classInstance["id"];
						opt.innerHTML = classInstance["className"];
						$("#class_select").append(opt);
					}
					
					classID = sessionStorage.getItem('classID');
					if (classID != null) {
						$("#class_select").val(classID);
						$("#classId").val(classID);
						console.log("classID hidden input: "+ $("#classId").val());
						$.ajax({
			type : "GET",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/listRooms?classID=" + classID,
			contentType : 'application/json',
			success : function(data) {
				$("#room_select option[value != '0']").remove();
				let arrayLength = data.length;
				console.log(arrayLength);
				let room, opt;
				
				for (let i = 0; i < arrayLength; i++) {
					$("#message").hide();
					room = data[i];
					console.log(room);
					opt = document.createElement('option');
					opt.value = room["id"];
					opt.innerHTML = room["roomName"];
					$("#room_select").append(opt);
				}
				roomID = sessionStorage.getItem('roomID');
				if (roomID != null) {
						$("#room_select").val(roomID);
						$("#roomId").val(roomID);
						console.log("roomID hidden input: "+ $("#roomID").val());
					}
			},
			error : function() {
				$("#message").text("Lớp này hiện tại không có phòng học nào hoặc xảy ra lỗi đường truyền!");
				$("#message").show();
			}
		});
					}
					
				},
				error : function() {
					$("#message").text("Học phần này không có lớp học nào!");
					$("#message").show();
				}
			});
		}
	}

	$("#course_select").on('change', function(e) {
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
				$("#error_div").hide();
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
				$("#message").text("Học phần này không có lớp học nào!");
				$("#message").show();
			}
		});
	});
		
	$("#class_select").on('change', function(e) {
		let classID = $("option:selected", this).val();
		console.log("class id after changed = " + classID);
		
		$("#classId").val(classID);
		console.log("classId in hidden input = " + $("#classId").val());
		
		$.ajax({
			type : "GET",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/listRooms?classID=" + classID,
			contentType : 'application/json',
			success : function(data) {
				$("#room_select option[value != '0']").remove();
				let arrayLength = data.length;
				console.log(arrayLength);
				let room, opt;
				
				for (let i = 0; i < arrayLength; i++) {
					$("#message").hide();
					room = data[i];
					console.log(room);
					opt = document.createElement('option');
					opt.value = room["id"];
					opt.innerHTML = room["roomName"];
					$("#room_select").append(opt);
				}
			},
			error : function() {
				$("#message").text("Lớp này hiện tại không có phòng học nào hoặc xảy ra lỗi đường truyền!");
				$("#message").show();
			}
		});
	});
		
	 $("#room-select").on('change', function(e) {
	      let roomID = $("option:selected", this).val();
	      console.log("room id after changed = " + roomID);
	      
	      $("#roomId").val(roomID);
	      console.log("room ID input change to " +  $("#roomId").val());
	 });
		
	window.onbeforeunload = function() {
		sessionStorage.setItem("classID", $("#class_select").val());
		
		sessionStorage.setItem("courseID", $("#course_select").val());
		console.log($("#room_select").val());
		sessionStorage.setItem('roomID',  $("#room_select").val());
		console.log(sessionStorage.getItem('roomID'));
	}
		
	$("#rollcall_btn").click(function(e){
		e.preventDefault();
		$("#img_file_loader").show();
		sessionStorage.removeItem("classID");
		sessionStorage.removeItem("courseID");
		sessionStorage.removeItem("roomID");
		$("#form_submit").submit();
	});
	
	
	//======================= MANUAL ===============================================
	
	//load list class when course is changed
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
					console.log("classIns" + classInstance);
					opt = document.createElement('option');
					opt.value = classInstance["id"];
					console.log("classInsId" + classInstance["id"]);
					opt.innerHTML = classInstance["className"];
					$("#class_select_manual").append(opt);
				}
			},
			error : function() {
				$("#message_manual").text("Học phần này không có lớp học nào!");
				$("#message_manual").show();
			}
		});
	});
	
	//load list room when class is changed
	$("#class_select_manual").on('change', function(e) {
		let classIDManual = $("option:selected", this).val();
		console.log("class id manual after changed = " + classIDManual);
		$.ajax({
			type : "GET",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/listRooms?classID=" + classIDManual,
			contentType : 'application/json',
			success : function(data) {
				$("#room_select_manual option[value != '0']").remove();
				let arrayLength = data.length;
				console.log(arrayLength);
				let room, opt;
				
				for (let i = 0; i < arrayLength; i++) {
					$("#message_manual").hide();
					room = data[i];
					console.log(room);
					opt = document.createElement('option');
					opt.value = room["id"];
					opt.innerHTML = room["roomName"];
					$("#room_select_manual").append(opt);
				}
			},
			error : function() {
				$("#message_manual").text("Lớp này hiện tại không có phòng học nào hoặc xảy ra lỗi đường truyền!");
				$("#message_manual").show();
			}
		});
	});
	
	
	$("#rollcall_student_manual").click(function(e){
		e.preventDefault();
		$("#img_loader").show();
		console.log("student email = " + $("#email").val());
		console.log("reason = " + $('#reason_select :selected').val());
		console.log("class id manual = " + $('#class_select_manual :selected').val());
		console.log("teacher id = " + id);		//this field is retrieved from config.js
		console.log("room id = " + $('#room_select_manual :selected').val());
		$.ajax({
			type : "POST",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/rollCallStudentWithPermission",
			contentType : 'application/json',
			data: JSON.stringify({
				   studentEmail: $("#email").val(),
				   teacherID: id,
				   classID: $('#class_select_manual :selected').val(),
				   reason: $('#reason_select :selected').val(),
				   roomID: $('#room_select_manual :selected').val()
				   
			 }),
			success : function(data) {
				$("#img_loader").hide();
				$("#message_manual").text("Thêm sinh viên thành công!");
				$("#message_manual").show();
			},
			error : function(data) {
				$("#img_loader").hide();
				$("#message_manual").text(data.responseJSON.description);
				$("#message_manual").show();
			}
		});
	});
});