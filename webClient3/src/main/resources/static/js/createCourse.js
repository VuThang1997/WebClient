$(document)
        .ready(
                function () {
                    $("#img_loader").hide();
                    $("#message").hide();

                    $('#create_course_form').validate({
                        errorClass: 'errors',
                        rules: {
                            course_name: {
                                required: true
                            },
                            description: {
                                required: true
                            }
                        },
                        messages: {
                            course_name: {
                                required: "Mời nhập tên phòng học!"
                            },

                            description: {
                                required: "Mời nhập mô tả của học phần!"
                            }
                        },
                        highlight: function (element) {
                            $(element).parent().addClass('error')
                        },
                        unhighlight: function (element) {
                            $(element).parent().removeClass('error')
                        }
                    });

                    $("#create_course_form").submit(function (e) {
                        e.preventDefault();
                        let isvalidate = $("#create_course_form").valid();
                        console.log(isvalidate);
                        if (!isvalidate) {
                            e.preventDefault();
                        } else {
                            $("#img_loader").show();

                            $.ajax({
                                type: "POST",
                                dataType: "json",
                                url: protocol_server_core + "://"
                                        + host_server_core + ":" + port_server_core
                                        + "/courses",
                                contentType: 'application/json',
                                data: JSON.stringify({
                                    courseName: $("#course_name").val(),
                                    description: $("#description").val()
                                }),
                                success: function (data) {
                                    $("#img_loader").hide();
                                    $("#message").text("Thêm học phần thành công!");
                                    $("#message").show();
                                },
                                error: function (data) {
                                    $("#img_loader").hide();
                                    $("#message").text(data.responseJSON.description);
                                    $("#message").show();
                                }
                            });
                        }
                    });
                });