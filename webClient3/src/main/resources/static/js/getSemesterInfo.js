$(document)
  .ready(
    function() {
     $("#img_loader").hide();
     $("#message").hide();
     $(".show_info").hide();

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
			   }
			});
	  });
});