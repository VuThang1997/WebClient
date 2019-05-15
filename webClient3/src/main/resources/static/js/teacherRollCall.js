$(document).ready(function () {
    $("#message").hide();
	$(".alert").hide();
    var classID = -1;
    var roomID = -1;
    $("#img_loader").hide();

    $("#class_select").on('change', function (e) {
        classID = $("option:selected", this).val();
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "http://localhost:8080/listRooms?classID=" + classID,
            contentType: 'application/json',
            success: function (data) {
                $("#message").hide();
				$(".alert").hide();
                var arrayLength = data.length;
                for (var i = 0; i < arrayLength; i++) {
                    var room = data[i];
                    var opt = document.createElement('option');
                    opt.value = room["id"];
                    opt.innerHTML = room["roomName"];
                    $("#room_select").append(opt);
                }

            },
            error: function () {
                $("#message").show();
                $("#message").text("Có lỗi xảy ra với đường truyền!");
            }
        });

    });

    $("#roll_call_btn").click(function (e) {
        e.preventDefault();
        $("#img_loader").show();
        classID = $("#class_select :selected").val();
        roomID = $("#room_select :selected").val();
        console.log(classID + " + " + roomID);
        if (classID == -1 || roomID == -1) {
            $("#message").text("Phải chọn lớp học và phòng học trước!");
            $("#message").show();
        } else {
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "http://localhost:8080/teacherRollCall",
                contentType: 'application/json',
                data: JSON.stringify({
                    teacherID: id,
                    classID: classID,
                    roomID: roomID
                }),
                success: function (data) {
                    $("#img_loader").hide();
                    var identifyString = data["description"];
                    console.log("identi = " + identifyString);

                    var qrcode = new QRCode(document.getElementById("qrcode"), {
                        width: 200,
                        height: 200
                    });
                    function makeCode() {
                        qrcode.makeCode(identifyString);
                    }
                    makeCode();
                },
                error: function (data) {
					let message = data.responseJSON.description;
                	$("#message").show();
                	$("#message").text(message);
					$(".alert").show();
                    $("#img_loader").hide();
                    console.log("error happen");
                }
            });
        }
        
        console.log("body" + JSON.stringify({
            teacherID: id,
            classID: classID,
            roomID: roomID
        }));
    });
    
});