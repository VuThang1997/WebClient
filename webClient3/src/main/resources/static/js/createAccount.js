$(document).ready(function(){
	
	 $.validator.addMethod("EMAIL", function(value, element) {
                return this.optional(element) || /^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\.[a-zA-Z.]{2,5}$/i.test(value);
            }, "Email không đúng format! ");
	
	$('#form_create_account').validate({
		errorClass: 'errors',
        rules : {
            new_password : {
				required: true,
				minlength : 5,
				
        },
            retype_password : {
				required: true,
                minlength : 5,
                equalTo : "#new_password"
            },
			email: {
              required: true,
			  EMAIL: "required EMAIL",
              email: true
            },
            username: {
              required: true
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
	}
  },
   highlight: function (element) {
                $(element).parent().addClass('error')
            },
            unhighlight: function (element) {
                $(element).parent().removeClass('error')
            }
	});
	
	window.onload = function() {
		let accountType = sessionStorage.getItem('accountType');
		if (accountType !== null) {
			console.log("session storage : " + accountType);
			$("#account_type_select").val(accountType);
			console.log("account_type_select : " + $("#account_type_select").val());
			$("#account_type").val(accountType);
			console.log("account_type: " + $("#account_type").val());
        }
	}
	
	$("#account_type_select").on('change', function(e) {
		  let accountType = $("option:selected", this).val();
		  $("#account_type").val(accountType);
		  console.log("account type change to " + accountType);
	 });
	
	window.onbeforeunload = function() {
		sessionStorage.setItem("accountType", $("#account_type").val());
	 }
	
	$("#manual_type_select").on('change', function(e) {
		let type = $("option:selected", this).val();
		$("#role").val(type);
	});
});