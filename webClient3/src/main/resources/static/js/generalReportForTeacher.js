$(document).ready(function () {
    $("#message").hide();
	$(".alert").hide();
    $("#link_report").hide();
    $("#img_loader").hide();
	
    $("#generate_report_pdf").click(function (e) {
        e.preventDefault();
		$("#message").hide();
		$(".alert").hide();
        $("#img_loader").show();
        $.ajax({
            type: "POST",
            dataType: "json",
            url: protocol_server_core + "://"
                    + host_server_core + ":" + port_server_core
                    + "/teacherGeneralReport?adminID=" + id,
            contentType: 'application/json',
            data: JSON.stringify({
                email: $("#email").val(),
                semesterID: $('#semester_select :selected').val(),
                beginAt: $("#begin_at").val(),
                finishAt: $("#finish_at").val(),
                fileType: "pdf"
            }),
            success: function (data) {
				$("#img_loader").hide();
                let linkDown = data["description"];
                let linkDownload = protocol_client + "://" + host_client + ":" + port_client + '/download/out/' + linkDown + "/" + linkDown
                        + ".pdf";
                $("#link_report").attr("href", linkDownload);
                $("#link_report").show();
            },
            error: function (data) {
				let message = data.responseJSON.description;
                $("#img_loader").hide();
				$("#message").show();
				$("#message").text(message);
				$(".alert").show();
                console.log($("#email").val() + $("option:selected").val() + $("#begin_at").val() + $("#finish_at").val());
            }
        });
    });

    $("#generate_report_xlsx").click(function (e) {
        e.preventDefault();
		$("#message").hide();
		$(".alert").hide();
        $("#img_loader").show();
        $.ajax({
            type: "POST",
            dataType: "json",
            url: protocol_server_core + "://"
                    + host_server_core + ":" + port_server_core
                    + "/teacherGeneralReport?adminID=" + id,
            contentType: 'application/json',
            data: JSON.stringify({
                email: $("#email").val(),
                semesterID: $('#semester_select :selected').val(),
                beginAt: $("#begin_at").val(),
                finishAt: $("#finish_at").val(),
                fileType: "xls"
            }),
            success: function (data) {
                $("#img_loader").hide();
                let linkDown = data["description"];
                let linkDownload = protocol_client + "://" + host_client + ":" + port_client + '/download/out/' + linkDown + "/" + linkDown
                        + ".xls";
                $("#link_report").attr("href", linkDownload);
                $("#link_report").show();
            },
            error: function () {
				let message = data.responseJSON.description;
                $("#img_loader").hide();
				$("#message").show();
				$("#message").text(message);
				$(".alert").show();
                console.log($("#email").val() + $("option:selected").val() + $("#begin_at").val() + $("#finish_at").val());
            }
        });
    });
});