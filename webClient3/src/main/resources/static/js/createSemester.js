$(document)
  .ready(
    function() {
     $("#img_loader").hide();
     $("#message").hide();
    
     $('#create_semester_form').validate({
      errorClass : 'errors',
      rules : {
    	  semester_name : {
	        required : true
	       },
	       begin_date : {
		     required : true
		  },
		  end_date : {
			required : true
		  },
      },
      messages : {
    	  semester_name : {
    		required : "Mời nhập tên học kì!"
        },
        
        begin_date : {
    		required : "Mời chọn thời gian bắt đầu của học kì!"
        },
        
        end_date : {
    		required : "Mời chọn thời gian kết thúc của học kì!"
        },
        
      },
      highlight : function(element) {
       $(element).parent().addClass('error')
      },
      unhighlight : function(element) {
       $(element).parent().removeClass('error')
      }
     });
	 
	 

     $("#create_semester_form").submit(function(e){
		  
		  e.preventDefault();
		  
		  let isvalidate=$("#create_semester_form").valid();
		  console.log(isvalidate);
		  if (!isvalidate) {
			e.preventDefault();
		  }
		  else {
		  $("#img_loader").show();
		  console.log(JSON.stringify({
				   semesterName: $("#semester_name").val(),
				   beginDate: $("#begin_date").val(),
				   endDate: $("#end_date").val()
			   }));
		  
		  $.ajax({ 
			   type: "POST",
			   dataType: "json",
			   url: protocol_server_core + "://"
	               + host_server_core + ":" + port_server_core
	               + "/semesters",
			   contentType: 'application/json',
			   data: JSON.stringify({
				   semesterName: $("#semester_name").val(),
				   beginDate: $("#begin_date").val(),
				   endDate: $("#end_date").val()
			   }),
			   success: function(data){
				   $("#img_loader").hide();
				   
				   $("#message").text("Thêm học kì thành công!");
				   $("#message").show();
			   },
			   error: function(data) {
				   console.log(data);
				   $("#img_loader").hide();
				   $("#message").text(data.responseJSON.description);
				   $("#message").show();
			   }
			});
		  }
	  });
});