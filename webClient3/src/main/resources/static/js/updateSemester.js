$(document)
  .ready(
    function() {
     $("#img_loader").hide();
     $("#message").hide();
     $(".show_info").hide();
     $("#update_semester").hide();
    
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

     $("#semester_select").on('change', function(e) {
    	 $(".show_info").hide();
    	 $("#img_loader").show();
    	 let semesterID = $("option:selected", this).val();
    	 console.log("semester id after change = " + semesterID);
		  
		  $.ajax({ 
			   type: "GET",
			   dataType: "json",
			   url: protocol_server_core + "://"
	               + host_server_core + ":" + port_server_core
	               + "/semesters/id?semesterID=" + semesterID,
			   contentType: 'application/json',			   
			   success: function(data){
				   console.log(data);
				   $("#img_loader").hide();
				   $("#message").hide();
				   $("#update_semester").show();
				   
				   $(".show_info").show();
				   $("#semester_name").val(data["semesterName"]);
				   $("#begin_date").val(data["beginDate"]);
				   $("#end_date").val(data["endDate"]);
				 
			   },
			   error: function() {
				   $(".show_info").hide();
				   $("#img_loader").hide();
				   $("#message").text("Truy xuất thông tin kì học thất bại!");
				   $("#message").show();
				   $("#update_semester").hide();
			   }
			});
	  });
     
     $("#update_semester").click(function(e){
 		e.preventDefault();
 		console.log("semesterID= " + $('#semester_select :selected').val());
 		
 		$.ajax({
 			type : "PUT",
 			dataType : "json",
 			url : protocol_server_core + "://"
 					+ host_server_core + ":" + port_server_core
 					+ "/semesters",
 			contentType : 'application/json',
 			data: JSON.stringify({
				   id: $('#semester_select :selected').val(),
				   semesterName: $("#semester_name").val(),
				   beginDate: $("#begin_date").val(),
				   endDate:  $("#end_date").val()
 			 }),
 			success : function(data) {
 				$("#message").text("Cập nhật thành công!");
 				$("#message").show();
 			},
 			error : function(data) {
				console.log(data);
 				$("#message").text(data.responseJSON.description);
 				$("#message").show();
				
				
				$("#img_loader").hide();
				$(".show_info").hide();
				$("#update_semester").hide();
				$("#semester_select").val(0);
 			}
 		});
 	});
});