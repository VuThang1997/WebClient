$(document)
  .ready(
    function() {
     $("#img_loader").hide();
     $("#message").hide();
     $(".show_info").hide();
     $("#delete_room").hide();
    
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

     $("#room_select").on('change', function(e) {
    	 $(".show_info").hide();
    	 $("#delete_room").hide();
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
				   $("#delete_room").show();
				   
				   $(".show_info").show();
				   $("#room_name").val(data["roomName"]);
				   $("#address").val(data["address"]);
				   $("#latitude").val(data["gpsLatitude"]);
				   $("#longitude").val(data["gpsLongitude"]);
				   $("#mac_addr").val(data["macAddress"]);
			   },
			   error: function() {
				   $(".show_info").hide();
				   $("#delete_room").hide();
				   $("#img_loader").hide();
				   $("#message").text("Truy xuất thông tin phòng học thất bại!");
				   $("#message").show();
			   }
			});
	  });
     
     $("#delete_room").click(function(e){
  		e.preventDefault();
  		$("#img_loader").show();
  		console.log("roomID= " + $('#room_select :selected').val());
  		
  		$.ajax({
  			type : "DELETE",
  			dataType : "json",
  			url : protocol_server_core + "://"
  					+ host_server_core + ":" + port_server_core
  					+ "/rooms?roomID=" + $('#room_select :selected').val(),
  			contentType : 'application/json',
  			success : function(data) {
  				$("#message").text("Xóa thành công!");
  				$("#message").show();
  				
  				$("#img_loader").hide();
  				$("#room_select").val(0);
  				$(".show_info").hide();
  			    $("#delete_room").hide();
  			},
  			error : function() {
  				$("#message").text("Phòng học này đang được sử dụng!");
  				$("#message").show();
 				
 				$("#img_loader").hide();
 				$("#delete_room").hide();
  			}
  		});
  	});
});