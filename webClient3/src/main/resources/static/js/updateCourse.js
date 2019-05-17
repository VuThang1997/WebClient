$(document)
  .ready(
    function() {
     $("#img_loader").hide();
     $("#message").hide();
     $(".show_info").hide();
     $("#update_course").hide();
    
     $('#create_room_form').validate({
    	 errorClass : 'errors',
         rules : {
       	  course_name : {
   	        required : true
   	       },
   	       description : {
   		     required : true
   		  }
         },
         messages : {
       	  course_name : {
       		required : "Mời nhập tên phòng học!"
           },
           
           description : {
       		required : "Mời nhập mô tả của học phần!"
           }
         },
         highlight : function(element) {
          $(element).parent().addClass('error')
         },
         unhighlight : function(element) {
          $(element).parent().removeClass('error')
         }
     });

     $("#update_course_form").on('change', function(e) {
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
				   $("#course_name").val(data["courseName"]);
				   $("#description").val(data["description"]);
				   
				   $("#update_course").show();
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
     
     $("#update_course").click(function(e){
 		e.preventDefault();
 		console.log("courseID = " + $('#course_select :selected').val());
 		
 		$.ajax({
 			type : "PUT",
 			dataType : "json",
 			url : protocol_server_core + "://"
 					+ host_server_core + ":" + port_server_core
 					+ "/courses",
 			contentType : 'application/json',
 			data: JSON.stringify({
 				id: $('#course_select :selected').val(),
 				courseName: $("#course_name").val(),
 				description: $("#description").val()
 			 }),
 			success : function(data) {
 				$("#message").text("Cập nhật thành công!");
 				$("#message").show();
 			},
 			error : function() {
 				$("#message").text("Cập nhật không thành công!");
 				$("#message").show();
				
				$("#img_loader").hide();
				$(".show_info").hide();
				$("#update_course").hide();
				$("#course_select").val(0);
 			}
 		});
 	});
});