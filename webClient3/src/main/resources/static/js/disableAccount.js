
$(document).ready(function() {
	$("#message").hide();
	$("#img_loader").hide();
	
	$.validator.addMethod("EMAIL", function(value, element) {
			return this.optional(element) || /^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\.[a-zA-Z.]{2,5}$/i
						.test(value);
	}, "Email không hợp lệ! ");

	$('#disable_account_form').validate({
		errorClass : 'errors',
		rules : {
			email : {
			required : true,
			EMAIL : "required EMAIL",
			email : true
			}
		},
		messages : {
			email : {
				required : "Mời nhập email!"
			}
		},
		highlight : function(element) {
			$(element).parent().addClass('error')
		},
		unhighlight : function(element) {
			$(element).parent().removeClass('error')
		}
	});
	
	$("#disable_account_form").submit(function(e){
		e.preventDefault();
		$("#img_loader").show();
		console.log("email = " + $("#email").val());
		$.ajax({
			type : "PUT",
			dataType : "json",
			url : protocol_server_core + "://"
					+ host_server_core + ":" + port_server_core
					+ "/deactivateAccount",
			contentType : 'application/json',
			data: JSON.stringify({
				  email: $("#email").val()
			 }),
			success : function(data) {
				$("#img_loader").hide();
				$("#message").text("Vô hiệu hóa tài khoản thành công!");
				$("#message").show();
			},
			error : function(data) {
				$("#img_loader").hide();
				$("#message").text(data.responseJSON.description);
				$("#message").show();
			}
		});
	});
	
});