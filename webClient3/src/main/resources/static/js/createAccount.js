$(document)
        .ready(
                function () {
                    var accountType = -1;
                    $("#img_loader").hide();
                    $("#img_file_loader").hide();
					let linkDownload = protocol_client + "://" + host_client + ":" + port_client + '/download/Import_Template_File/StudentClass.xlsx';
                    $("#link_report").attr("href",linkDownload);
                    $.validator
                            .addMethod(
                                    "EMAIL",
                                    function (value, element) {
                                        return this.optional(element)
                                                || /^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\.[a-zA-Z.]{2,5}$/i
                                                .test(value);
                                    }, "Email không hợp lệ! ");

                    $.validator
                            .addMethod(
                                    "PHONE",
                                    function (value, element) {
                                        return this.optional(element)
                                                || /((09|03|07|08|05)+([0-9]{8})\b)/g
                                                .test(value);
                                    }, "Số điện thoại không hợp lệ! ");

                    $('#form_create_account').validate({
                        errorClass: 'errors',
                        rules: {
                            new_password: {
                                required: true,
                                minlength: 5,

                            },
                            retype_password: {
                                required: true,
                                minlength: 5,
                                equalTo: "#new_password"
                            },
                            email: {
                                required: true,
                                EMAIL: "required EMAIL",
                                email: true
                            },
                            username: {
                                required: true
                            },
                            fullName: {
                                required: true,
                                minlength: 10
                            },
                            phone: {
                                PHONE: "required PHONE"
                            }
                        },
                        messages: {
                            email: {
                                required: "Mời nhập email!"
                            },
                            username: {
                                required: "Mời nhập tên tài khoản!"
                            },
                            new_password: {
                                required: "Mời nhập mật khẩu!",
                                minlength: "Tối thiểu 5 kí tự"
                            },
                            retype_password: {
                                required: "Mời nhập lại mật khẩu!",
                                minlength: "Tối thiểu 5 kí tự",
                                equalTo: "Mật khẩu nhập lại không khớp!"
                            },
                            fullName: {
                                required: "Mời nhập đầy đủ họ tên!",
                                minlength: "Tối thiểu 10 kí tự"
                            }
                        },
                        highlight: function (element) {
                            $(element).parent().addClass('error')
                        },
                        unhighlight: function (element) {
                            $(element).parent().removeClass('error')
                        }
                    });

                    window.onload = function () {

                        accountType = sessionStorage.getItem('accountType');
                        console.log(accountType);
                        if (accountType !== "undefined") {
                            console.log("session storage : " + accountType);
                            $("#account_type_select").val(accountType);
                            console.log("account_type_select : "
                                    + $("#account_type_select").val());
                            $("#account_type").val(accountType);
                            console.log("account_type: "
                                    + $("#account_type").val());
                        }
                    }

                    $("#account_type_select").on('change', function (e) {
                        accountType = $("option:selected", this).val();
                        $("#account_type").val(accountType);
                        console.log("account type change to " + $("#account_type").val());
                    });

                    window.onbeforeunload = function () {
                        sessionStorage.setItem("accountType",
                                $("#account_type").val());
                        console.log(sessionStorage.getItem("accountType"));
                    }

                    $("#manual_type_select").on('change', function (e) {
                        var type = $("option:selected", this).val();
                        $("#role").val(type);
                    });
                    //thangnh - them spinner ở 2 button thêm mới 
                    $("#submit_account").click(function (e) {
                        e.preventDefault();
                        $("#img_loader").show();
                        $("#form_create_account").submit();
                    });
                    $("#create_btn").click(function (e) {
                        e.preventDefault();
                        $("#img_file_loader").show();
                        $("#submit_account_by_file").submit();
                    });
                });