$(document).ready(
  function() {
   $("#error_div").hide();
   $("#link_report").hide();

   var courseID = -1;
   var classID = -1;

   $("#course_select").on(
     'change',
     function(e) {
      courseID = $("option:selected", this).val();

      $.ajax({
       type : "GET",
       dataType : "json",
       url : protocol_server_core + "://"
         + host_server_core + ":" + port_server_core
         + "/listClass?courseID=" + courseID,
       contentType : 'application/json',
       success : function(data) {
        $("#error_div").hide();
        var arrayLength = data.length;
        for (var i = 0; i < arrayLength; i++) {
         var classInstance = data[i];
         var opt = document.createElement('option');
         opt.value = classInstance["id"];
         opt.innerHTML = classInstance["className"];
         $("#class_select").append(opt);
        }

       },
       error : function() {
        $("#error_div").text(
          "Học phần này không có lớp học nào!");
        $("#error_div").show();
       }
      });

     });

   $("#generate_report_pdf").click(
     function(e) {
      e.preventDefault();

      $.ajax({
       type : "POST",
       dataType : "json",
       url : protocol_server_core + "://"
         + host_server_core + ":" + port_server_core
         + "/classDetailReport?adminID=" + id,
       contentType : 'application/json',
       data : JSON.stringify({
        classID : $('#class_select :selected').val(),
        beginAt : $("#begin_at").val(),
        finishAt : $("#finish_at").val(),
        fileType : "pdf"
       }),
       success : function(data) {
        let linkReport = data["description"];
        let linkDownload = protocol_client + "://" + host_client + ":" + port_client + '/download/' + linkReport + "/" + linkReport
        + ".pdf";
        $("#link_report").attr(
          "href",
          linkDownload
                                                                                );
        $("#link_report").show();
       },
       error : function() {
        console.log($("#email").val()
          + $("option:selected").val()
          + $("#begin_at").val()
          + $("#finish_at").val());
       }
      });
     });

   $("#generate_report_xlsx").click(
     function(e) {
      e.preventDefault();

      $.ajax({
       type : "POST",
       dataType : "json",
       url : protocol_server_core + "://"
         + host_server_core + ":" + port_server_core
         + "/classDetailReport?adminID=" + id,
       contentType : 'application/json',
       data : JSON.stringify({
        classID : $('#class_select :selected').val(),
        beginAt : $("#begin_at").val(),
        finishAt : $("#finish_at").val(),
        fileType : "xls"
       }),
       success : function(data) {
        let linkDown = data["description"];
        let linkDownload = protocol_client + "://" + host_client + ":" + port_client + '/download/' + linkDown + "/" + linkDown
        + ".xls";
        $("#link_report").attr(
          "href",
          linkDownload
          );
        $("#link_report").show();
       },
       error : function() {
        console.log($("#email").val()
          + $("option:selected").val()
          + $("#begin_at").val()
          + $("#finish_at").val());
       }
      });
     });
  });