$(document).ready(function() {
	
	$("#message").hide();
	$("#img_loader").hide();
	
	//let linkDownload = protocol_client + "://" + host_client + ":" + port_client + '/download/Import_Template_File/StudentClass.xlsx';
    //$("#link_report").attr("href",linkDownload);

	$("#course_select").on('change', function(e) {
		$("#message").hide();
		$("#class_select option[value != '0']").remove();
		$("#lesson_select option[value != '0']").remove();
		
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
		$("#message").hide();
		$("#lesson_select option[value != '0']").remove();
		
		let classID = $("option:selected", this).val();
		console.log("classID after class_select is changed = " + classID);
		$.ajax({
			type : "GET",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/classroomsForClass?classID=" + classID,
			contentType : 'application/json',
			success : function(data) {
				
				let arrayLength = data.length;
				let tmpClassRoom, opt, tmpContent;
				for (let i = 0; i < arrayLength; i++) {
					tmpClassRoom = data[i];
					opt = document.createElement('option');
					opt.value = tmpClassRoom["id"];
					tmpContent = tmpClassRoom.classInstance.className + " - " + tmpClassRoom.room.roomName
							+ " - Thứ " + tmpClassRoom.weekday + " - " + tmpClassRoom.beginAt + " - " + tmpClassRoom.finishAt;
					opt.innerHTML = tmpContent;
					$("#lesson_select").append(opt);
				}
			},
			error : function(data) {
				$("#message").text(data.responseJSON.description);
				$("#message").show();
			}
		});
	});
	
	$("#update_classroom").click(function(e){
		e.preventDefault();
		$("#img_loader").show();
		$("#message").hide();
		
		console.log("lesson's id = " + $('#lesson_select :selected').val());
		console.log(JSON.stringify({
				id: $('#lesson_select :selected').val(),
				beginAt: $('#new_begin_at :selected').val(),
				finishAt: $('#new_finish_at :selected').val(),
				weekday: $('#weekday :selected').val(),
				classID: $('#class_select :selected').val(),
				roomID:  $('#room_select :selected').val()
			 }));
		$.ajax({
			type : "PUT",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/classrooms",
			contentType : 'application/json',
			data: JSON.stringify({
				id: $('#lesson_select :selected').val(),
				beginAt: $('#new_begin_at :selected').val(),
				finishAt: $('#new_finish_at :selected').val(),
				weekday: $('#weekday :selected').val(),
				classID: $('#class_select :selected').val(),
				roomID:  $('#room_select :selected').val()
			 }),
			success : function(data) {
				$("#img_loader").hide();
				$("#message").text("Cập nhật thông tin tiết học thành công!");
				$("#message").show();
			},
			error : function(data) {
				$("#img_loader").hide();
				$("#message").text(data.responseJSON.description);
				$("#message").show();
			}
		});
	});
});