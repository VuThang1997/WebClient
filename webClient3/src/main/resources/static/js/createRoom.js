$(document)
  .ready(
    function() {
     $("#img_loader").hide();
     $("#message").hide();
    
     $('#create_room_form').validate({
      errorClass : 'errors',
      rules : {
    	  room_name : {
	        required : true
	       },
	      address : {
		     required : true
		  },
		  latitude : {
			required : true
		  },
		  longitude : {
			required : true
		  },
		  mac_addr: {
			  required : true
		  }
      },
      messages : {
    	room_name : {
    		required : "Mời nhập tên phòng!"
        },
        
        address : {
    		required : "Mời nhập địa chỉ phòng!"
        },
        
        latitude : {
    		required : "Mời nhập vĩ độ!"
        },
        
        longitude : {
    		required : "Mời nhập kinh độ!"
        },
        
        mac_addr : {
    		required : "Mời nhập địa chỉ MAC của WAP!"
        }
      },
      highlight : function(element) {
       $(element).parent().addClass('error')
      },
      unhighlight : function(element) {
       $(element).parent().removeClass('error')
      }
     });

     $("#submit_room").click(function(e){
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
				   $("#img_loader").show();
				   $("#message").text("Thêm phòng học thất bại!");
				   $("#message").show();
			   }
			});
	  });
});