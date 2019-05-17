$(document)
  .ready(
    function() {
     $("#img_loader").hide();
     $("#message").hide();
     $(".show_info").hide();
    
     $('#retrieve_course_form').validate({
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
    		required : "Mời nhập tên học phần!"
        },
        
        description : {
    		required : "Mời nhập mô tả học phần!"
        }
        
      },
      highlight : function(element) {
       $(element).parent().addClass('error')
      },
      unhighlight : function(element) {
       $(element).parent().removeClass('error')
      }
     });

     $("#course_select").on('change', function(e) {
    	 $(".show_info").hide();
    	 $("#img_loader").show();
    	 let courseID = $("option:selected", this).val();
    	 console.log("course id after change = " + courseID);
		  
		  $.ajax({ 
			   type: "GET",
			   dataType: "json",
			   url: protocol_server_core + "://"
	               + host_server_core + ":" + port_server_core
	               + "/courses?courseID=" + courseID,
			   contentType: 'application/json',			   
			   success: function(data){
				   console.log(data);
				   $("#img_loader").hide();
				   $("#message").hide();
				   
				   $(".show_info").show();
				   $("#course_name").val(data["courseName"]);
				   $("#description").val(data["description"]);
			   },
			   error: function() {
				   $(".show_info").hide();
				   $("#img_loader").hide();
				   $("#message").text("Truy xuất thông tin học phần thất bại!");
				   $("#message").show();
			   }
			});
	  });
});