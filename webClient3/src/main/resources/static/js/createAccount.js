$(document).ready(function(){
	var inputFile;
	var fileName;
	
	/*
	console.log(JSON.stringify({
				   file: inputFile;
			   }));
	$("#input_file").on("input", function() {
		inputFile = this.val();
		 $.ajax({ 
			   type: "POST",
			   dataType: "json",
			   url: "http://localhost:8085/manipulateFile",
			   contentType: 'application/json',
			   data: JSON.stringify({
				   file: inputFile;
			   }),
			   success: function(data){
				   fileName = data;
				   console.log("file name = " + fileName);
			   },
			   error: function() {
				  console.log("error ===============");
			   }
			});
	});
	 
	 $("#submit_profile").click(function(e){
		  e.preventDefault();
		  
		  $.ajax({ 
			   type: "PUT",
			   dataType: "json",
			   url: "http://localhost:8080/users",
			   contentType: 'application/json',
			   data: JSON.stringify({
				   id: id,
				   birthday: $("#new_birthday").val(),
				   phone: $("#phone").val(),
				   address: $("#address").val(),
				   fullName: $("#full_name").val(),
			   }),
			   success: function(data){
				   $("#report").text("Success!");
				   $("#alert").show();
				   $("#current_birthday").val($("#new_birthday").val());
			   },
			   error: function() {
				   $("#report").text("Failed!");
				   $("#alert").show();
				   alert($("#email").val()+ $("option:selected").val()+$("#begin_at").val()+$("#finish_at").val());
			   }
			});
	  });*/
});