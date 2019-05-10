$(document).ready(function(){
	$("#error_div").hide();
	$("#link_report").hide();
  
  $("#generate_report_pdf").click(function(e){
	  e.preventDefault();
	  
	  $.ajax({ 
		   type: "POST",
		   dataType: "json",
		   url: "http://localhost:8080/studentGeneralReport?adminID=" + id,
		   contentType: 'application/json',
		   data: JSON.stringify({
			   email: $("#email").val(),
			   semesterID: $('#semester_select :selected').val(),
			   beginAt: $("#begin_at").val(),
			   finishAt: $("#finish_at").val(),
			   fileType: "pdf"
		   }),
		   success: function(data){
			   	 var linkDown = data["description"];
			     $("#link_report").attr("href", "http://localhost:8085/download/" + linkDown + "/" + linkDown + ".pdf");
			     $("#link_report").show();
			   },
			   error: function() {
				   console.log($("#email").val()+ $("option:selected").val()+$("#begin_at").val()+$("#finish_at").val());
			   }
		});
  });
  
  $("#generate_report_xlsx").click(function(e){
	  e.preventDefault();
	  
	  $.ajax({ 
		   type: "POST",
		   dataType: "json",
		   url: "http://localhost:8080/studentGeneralReport?adminID=" + id,
		   contentType: 'application/json',
		   data: JSON.stringify({
			   email: $("#email").val(),
			   semesterID: $('#semester_select :selected').val(),
			   beginAt: $("#begin_at").val(),
			   finishAt: $("#finish_at").val(),
			   fileType: "xls"
		   }),
		   success: function(data){
			   	 var linkDown = data["description"];
			     $("#link_report").attr("href", "http://localhost:8085/download/" + linkDown + "/" + linkDown + ".xls");
			     $("#link_report").show();
			   },
			   error: function() {
				   console.log($("#email").val()+ $("option:selected").val()+$("#begin_at").val()+$("#finish_at").val());
			   }
		});
  });
});