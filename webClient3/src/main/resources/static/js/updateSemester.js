$(document)
        .ready(
                function () {
                    $("#img_loader").hide();
                    $("#message").hide();
                    $(".show_info").hide();
                    $("#update_semester").hide();

                    $('#update_semester_info').validate({
                        errorClass: 'errors',
                        rules: {
                            semester_name: {
                                required: true
                            },
                            begin_date: {
                                required: true
                            },
                            end_date: {
                                required: true
                            }
                        },
                        messages: {
                            semester_name: {
                                required: "Mời nhập tên học kì!"
                            },

                            begin_date: {
                                required: "Mời nhập thời gian bắt đầu!"
                            },

                            end_date: {
                                required: "Thiếu thời gian kết thúc!"
                            }
                        },
                        highlight: function (element) {
                            $(element).parent().addClass('error')
                        },
                        unhighlight: function (element) {
                            $(element).parent().removeClass('error')
                        }
                    });

                    $("#semester_select").on('change', function (e) {
                        $(".show_info").hide();
                        let semesterID = $("option:selected", this).val();
                        console.log("semester id after change = " + semesterID);

                        $.ajax({
                            type: "GET",
                            dataType: "json",
                            url: protocol_server_core + "://"
                                    + host_server_core + ":" + port_server_core
                                    + "/semesters/id?semesterID=" + semesterID,
                            contentType: 'application/json',
                            success: function (data) {
                                console.log(data);
                                $("#message").hide();
                                $("#update_semester").show();

                                $(".show_info").show();
                                $("#semester_name").val(data["semesterName"]);
                                $("#begin_date").val(data["beginDate"]);
                                $("#end_date").val(data["endDate"]);

                            },
                            error: function (data) {
                                $(".show_info").hide();
                                $("#message").text(data.responseJSON.description);
                                $("#message").show();
                                $("#update_semester").hide();
                            }
                        });
                    });

                    $("#update_semester_info").submit(function (e) {
                        e.preventDefault();

                        let isvalidate = $("#update_semester_info").valid();
                        console.log(isvalidate);
                        if (!isvalidate) {
                            e.preventDefault();
                        } else {
                            $("#img_loader").show();
                            console.log("semesterID= " + $('#semester_select :selected').val());

                            $.ajax({
                                type: "PUT",
                                dataType: "json",
                                url: protocol_server_core + "://"
                                        + host_server_core + ":" + port_server_core
                                        + "/semesters",
                                contentType: 'application/json',
                                data: JSON.stringify({
                                    id: $('#semester_select :selected').val(),
                                    semesterName: $("#semester_name").val(),
                                    beginDate: $("#begin_date").val(),
                                    endDate: $("#end_date").val()
                                }),
                                success: function (data) {
                                    $("#img_loader").hide();
                                    $("#message").text("Cập nhật thành công!");
                                    $("#message").show();
                                    $("#semester_select").find("option:selected").text($("#semester_name").val());

                                },
                                error: function (data) {
                                    console.log(data);
                                    $("#message").text(data.responseJSON.description);
                                    $("#message").show();
                                    $("#img_loader").hide();
                                    $(".show_info").hide();
                                    $("#update_semester").hide();
                                    $("#semester_select").val(0);
                                }
                            });
                        }
                    });
                });