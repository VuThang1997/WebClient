$(document)
        .ready(
                function () {
                    $("#img_loader").hide();
                    $("#message").hide();
                    $(".show_info").hide();
                    $("#update_course").hide();

                    $('#update_course_form').validate({
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

                    $("#course_select").on('change', function (e) {
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
                            success: function (data) {
                                console.log(data);
                                $("#img_loader").hide();
                                $("#message").hide();

                                $(".show_info").show();
                                $("#course_name").val(data["courseName"]);
                                $("#description").val(data["description"]);

                                $("#update_course").show();
                            },
                            error: function () {
                                $(".show_info").hide();
                                $("#img_loader").hide();
                                $("#message").text("Truy xuất thông tin phòng học thất bại!");
                                $("#message").show();

                                $("#update_room").hide();

                            }
                        });
                    });

                    $("#update_course_form").submit(function (e) {
                        e.preventDefault();
                        $("#message").hide();
                        let isvalidate = $("#update_course_form").valid();
                        console.log(isvalidate);
                        if (!isvalidate) {
                            e.preventDefault();
                        } else {
                            e.preventDefault();
                            $("#img_loader").show();

                            $.ajax({
                                type: "PUT",
                                dataType: "json",
                                url: protocol_server_core + "://"
                                        + host_server_core + ":" + port_server_core
                                        + "/courses",
                                contentType: 'application/json',
                                data: JSON.stringify({
                                    courseID: $('#course_select :selected').val(),
                                    courseName: $("#course_name").val(),
                                    description: $("#description").val()
                                }),
                                success: function (data) {
                                    $("#img_loader").hide();
                                    $("#message").text("Cập nhật thành công!");
                                    $("#message").show();
                                    $("#course_select").find("option:selected").text($("#course_name").val());
                                },
                                error: function () {
                                    $("#message").text("Cập nhật không thành công!");
                                    $("#message").show();

                                    $("#img_loader").hide();
                                    $(".show_info").hide();
                                    $("#update_course").hide();
                                    $("#course_select").val(0);
                                }
                            });
                        }
                    });
                });