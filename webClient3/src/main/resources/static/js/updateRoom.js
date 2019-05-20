$(document)
  .ready(
    function() {
     $("#img_loader").hide();
     $("#message").hide();
     $(".show_info").hide();
     $("#update_room").hide();
    
     $('#update_room_form').validate({
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
        }
      },
      highlight : function(element) {
       $(element).parent().addClass('error')
      },
      unhighlight : function(element) {
       $(element).parent().removeClass('error')
      }
     });

     $("#room_select").on('change', function(e) {
    	 $(".show_info").hide();
    	 $("#img_loader").show();
    	 let roomID = $("option:selected", this).val();
    	 console.log("room id after change = " + roomID);
		  
		  $.ajax({ 
			   type: "GET",
			   dataType: "json",
			   url: protocol_server_core + "://"
	               + host_server_core + ":" + port_server_core
	               + "/rooms?roomID=" + roomID,
			   contentType: 'application/json',			   
			   success: function(data){
				   console.log(data);
				   $("#img_loader").hide();
				   $("#message").hide();
				   
				   $(".show_info").show();
				   $("#room_name").val(data["roomName"]);
				   $("#address").val(data["address"]);
				   $("#latitude").val(data["gpsLatitude"]);
				   $("#longitude").val(data["gpsLongitude"]);
				   $("#mac_addr").val(data["macAddress"]);
				   
				   $("#update_room").show();
			   },
			   error: function() {
				   $(".show_info").hide();
				   $("#img_loader").hide();
				   $("#message").text("Truy xuất thông tin phòng học thất bại!");
				   $("#message").show();

				   $("#update_room").hide();
				   
			   }
			});
	  });
     
     $("#update_room_form").submit(function(e){
		e.preventDefault();
		$("#message").hide();
		let isvalidate=$("#update_room_form").valid();
		console.log(isvalidate);
		if (!isvalidate) {
			e.preventDefault();
		}
		else {
 		e.preventDefault();
 		$.ajax({
 			type : "PUT",
 			dataType : "json",
 			url : protocol_server_core + "://"
 					+ host_server_core + ":" + port_server_core
 					+ "/rooms",
 			contentType : 'application/json',
 			data: JSON.stringify({
 				id: $('#room_select :selected').val(),
 				roomName:  $("#room_name").val(),
 				address: $("#address").val(),
 				gpsLatitude: $("#latitude").val(),
 				gpsLongitude: $("#longitude").val(),
 				macAddress: $("#mac_addr").val()
 			 }),
 			success : function(data) {
 				$("#message").text("Cập nhật phòng thành công!");
 				$("#message").show();
				$("#img_loader").hide();
				
				$("#room_select").find("option:selected").text($("#room_name").val());
 			},
 			error : function(data) {
 				$("#message").text(data.responseJSON.description);
 				$("#message").show();
				
				$("#img_loader").hide();
				$(".show_info").hide();
				$("#update_room").hide();
				$("#room_select").val(0);
 			}
 		});
		}
 	});
});