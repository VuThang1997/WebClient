$(document)
  .ready(
    function() {
     $("#img_loader").hide();
     $("#message").hide();
    
     $("#create_room_form").validate({
      rules : {
    	  room_name : "required",
	      address : "required",
		  latitude : "required",
		  longitude : "required"
      },
      messages : {
    	room_name : "Mời nhập tên phòng!",
        
        address : "Mời nhập địa chỉ phòng!",
        
        latitude : "Mời nhập vĩ độ!",
        
        longitude : "Mời nhập kinh độ!"
      }
     });

     $("#create_room_form").submit(function(e){
		  e.preventDefault();
		  $("#img_loader").show();
		  
		  $.ajax({ 
			   type: "POST",
			   dataType: "json",
			   url: protocol_server_core + "://"
	               + host_server_core + ":" + port_server_core
	               + "/rooms",
			   contentType: 'application/json',
			   data: JSON.stringify({
				   address: $("#address").val(),
				   roomName: $("#room_name").val(),
				   gpsLa: $("#latitude").val(),
				   gpsLong: $("#longitude").val(),
				   macAddress: $("#mac_addr").val()
			   }),
			   success: function(data){
				   $("#img_loader").hide();
				   $("#message").text("Thêm phòng học thành công!");
				   $("#message").show();
			   },
			   error: function() {
				   $("#img_loader").hide();
				   $("#message").text("Thêm phòng học thất bại!");
				   $("#message").show();
			   }
			});
	  });
});