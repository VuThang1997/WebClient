$(document).ready(function(){
  $("#error_div").hide();
  var classID = -1;
  var roomID = -1;
  
  $("#class_select").on('change', function(e) {
	  classID = $("option:selected", this).val();
	  
	  $.ajax({ 
		   type: "GET",
		   dataType: "json",
		   url: "http://localhost:8080/listRooms?classID=" + classID,
		   contentType: 'application/json',
		   success: function(data){
			   $("#error_div").hide();
			   var arrayLength = data.length;
			   for (var i = 0; i < arrayLength; i++) {
				   var room = data[i];
				   var opt = document.createElement('option');
				   opt.value = room["id"];
				   opt.innerHTML = room["roomName"];
				   $("#room_select").append(opt);
			   }
			   
			},
			error: function() {
				$("#error_div").text("Error happened!");
				$("#error_div").show();
			}
		});

  });
  
  $("#roll_call_btn").click(function(e){
	  e.preventDefault();
	  classID = $("#class_select :selected").val();
	  roomID = $("#room_select :selected").val();
	  console.log(classID + " + " + roomID);
	 if (classID == -1 || roomID == -1 ) {
		 $("#error_div").text("Phải chọn lớp học và phòng học trước!");
		$("#error_div").show();
	 }
	  console.log("body" + JSON.stringify({
			   teacherID: id,
			   classID: classID,
			   roomID: roomID
		   }));
	  $.ajax({ 
		   type: "POST",
		   dataType: "json",
		   url: "http://localhost:8080/teacherRollCall",
		   contentType: 'application/json',
		   data: JSON.stringify({
			   teacherID: id,
			   classID: classID,
			   roomID: roomID
		   }),
		   success: function(data){
			   var identifyString = data["description"];
			   console.log("identi = " + identifyString);
			   
			   var qrcode = new QRCode(document.getElementById("qrcode"), {
					width : 200,
					height : 200
				});
				function makeCode () {		
					qrcode.makeCode(identifyString);
				}
				makeCode();
		   },
		error: function() {
			console.log("error");
		}
		});
  });
});