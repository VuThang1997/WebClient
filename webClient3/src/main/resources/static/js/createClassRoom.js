$(document)
        .ready(
                function () {
                    $("#img_file_loader").hide();
                    
                    //$("#img_loader").hide();
					//let linkDownload = protocol_client + "://" + host_client + ":" + port_client + '/download/Import_Template_File/StudentClass.xlsx';
                    //$("#link_report").attr("href",linkDownload);
                     
                    window.onload = function () {
                        if (sessionStorage.getItem('roomID') !== null) {
                        	let roomID = sessionStorage.getItem('roomID');
                            console.log("session storage : " + accountType);
                            
                            $("#room_select").val(roomID);
                            console.log("room_select in onload = "+ $("#room_select").val());
                            $("#roomId").val(roomID);
                            console.log("roomId holder =: " + $("#roomId").val());
                        }
                    }

                    $("#room_select").on('change', function (e) {
                        let roomID = $("option:selected", this).val();
                        $("#roomId").val(roomID);
                        console.log("room id change to " + $("#roomId").val());
                    });

                    window.onbeforeunload = function () {
                        sessionStorage.setItem("roomID", $("#roomId").val());
                    }

                    //thangnh - them spinner ở 2 button thêm mới 
//                    $("#submit_account").click(function (e) {
//                        e.preventDefault();
//                        $("#img_loader").show();
//                        $("#form_create_account").submit();
//                    });
                    
                    $("#create_btn").click(function (e) {
                        e.preventDefault();
                        $("#img_file_loader").show();
                        $("#classroom_by_file").submit();
                    });
                });