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
      },
		highlight : function(element) {
			$(element).parent().addClass('error')
		},
		unhighlight : function(element) {
			$(element).parent().removeClass('error')
		}
     });

     $("#create_room_form").submit(function(e){
		 e.preventDefault();
		$("#message").hide();
		let isvalidate=$("#create_room_form").valid();
		console.log(isvalidate);
		if (!isvalidate) {
			e.preventDefault();
		}
		else {
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
				   gpsLatitude: $("#latitude").val(),
				   gpsLongitude: $("#longitude").val(),
				   macAddress: $("#mac_addr").val()
			   }),
			   success: function(data){
				   $("#img_loader").hide();
				   $("#message").text("Thêm phòng học thành công!");
				   $("#message").show();
			   },
			   error: function(data) {
				   $("#img_loader").hide();
				   $("#message").text(data.responseJSON.description);
				   $("#message").show();
			   }
			});
		}
	  });
});