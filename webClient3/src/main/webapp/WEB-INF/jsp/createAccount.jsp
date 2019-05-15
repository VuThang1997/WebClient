<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>Home</title>

<!-- Bootstrap core CSS -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- Custom styles for this template -->
<link href="css/modern-business.css" rel="stylesheet">

<script src="js/createAccount.js" type="text/javascript"></script>
<script type="text/javascript"
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>

<link href="css/createAccount.css" rel="stylesheet">

<script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
<link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css" />

<script src="js/config.js" type="text/javascript"></script>
</head>

<body>
	<c:if test="${empty sessionScope.id}">
		<c:redirect url="/"/>
	</c:if>
	<jsp:include page="header.jsp" />

	<!-- Page Content -->
	<div class="container-fluid bg-info">
		<div class="row">
			<div class="col-md-3 ">
				<jsp:include page="listGroup.jsp" />
			</div>
			<!--end of col-md-3 -->

			<div class="row col-md-9">
				<div class="card col-md-6">
					<div class="card-body">
						<div>
							<div class="col-md-12 text-center">
								<h4>TẠO TÀI KHOẢN</h4>
								<hr>
							</div>
						</div>
						<div class="card-body col-md-12">
							<form:form id="form_create_account" method="POST"
								action="/createNewAccountManually" modelAttribute="newAccount">
								<div class="form-group row">
									<label for="manual_type_select" class="col-4 col-form-label">
										Tài khoản *: </label>
									<div class="col-md-8">
										<select id="manual_type_select"
											class="browser-default custom-select">
											<option value="0" selected>Loại tài khoản</option>
											<option value="3">Sinh viên</option>
											<option value="2">Giảng viên</option>
											<option value="1">Admin</option>
										</select>
									</div>
								</div>
								
								<div class="form-group row">
									<label for="username" class="col-4 col-form-label">Tên
										tài khoản *</label>
									<div class="col-8">
										<form:input path="username" id="username" name="username"
											placeholder="Username" class="form-control here" type="text" />
									</div>
								</div>

								<div class="form-group row">
									<label for="new_password" class="col-4 col-form-label">Mật
										khẩu *</label>
									<div class="col-8">
										<form:input path="password" id="new_password"
											name="new_password" placeholder="New Password"
											class="form-control here" type="password" />
									</div>
								</div>

								<div class="form-group row">
									<label for="retype_password" class="col-4 col-form-label">Nhập
										lại *</label>
									<div class="col-8">
										<input id="retype_password" name="retype_password"
											placeholder="Retype passwrod" class="form-control here"
											type="password" />
									</div>
								</div>

								<div class="form-group row">
									<label for="new_email" class="col-4 col-form-label">Email
										*</label>
									<div class="col-8">
										<form:input path="email" id="new_email" name="new_email"
											placeholder="New Email" class="form-control here" type="text" />
									</div>
								</div>

								<div class="form-group row">
									<label for="fullName" class="col-4 col-form-label">Họ tên *</label>
									<div class="col-8">
										<form:input path="fullName" id="fullName" name="fullName"
											placeholder="Full Name" class="form-control here" type="text" />
									</div>
								</div>

								<div class="form-group row">
									<label for="address" class="col-4 col-form-label">Địa
										chỉ </label>
									<div class="col-8">
										<form:input path="address" id="address" name="address"
											placeholder="Address" class="form-control here" type="text" />
									</div>
								</div>
								
								<div class="form-group row">
									<label for="phone" class="col-4 col-form-label">Số ĐT:</label>
									<div class="col-8">
										<form:input path="phone" id="phone" name="phone"
											placeholder="Phone" class="form-control here" type="text" />
									</div>
								</div>

								<div class="form-group row">
									<label for="birthday" class="col-4 col-form-label">Ngày
										sinh
									</label>
									<div class="col-8">
										<form:input path="birthday" id="birthday" width="276" />
										<script>
											$('#birthday').datepicker({
												uiLibrary : 'bootstrap4',
												format : 'yyyy-mm-dd',
												value: '1995-01-01'
											});
										</script>
									</div>
								</div>

								<form:input path="role" id="role" name="role_input"
									class="form-control here" type="hidden" />

								<div class="form-group row">
									<div class="offset-4 col-8">
										<button id="submit_account" name="submit_account"
											type="submit" class="btn btn-primary">Tạo tài khoản</button>
									</div>
								</div>

								<c:if test="${not empty error2}">
									<div id="error_div"
										class="col-md-12 text-center alert alert-warning ">
										${error2}</div>
								</c:if>
							</form:form>
						</div>
					</div>
				</div>
				<div class="card col-md-6">
					<div class="card-body">
						<div>
							<div class="col-md-12 text-center">
								<h4>TẠO TÀI KHOẢN THEO FILE</h4>
								<hr>
							</div>
						</div>

						<div class="row col-md-12">
							<label for="account_type_select" class="col-md-5 col-form-label">Loại
								tài khoản *: </label>
							<div class="col-md-7">
								<select id="account_type_select"
									class="browser-default custom-select">
									<option value="0" selected>Loại tài khoản</option>
									<option value="3">Sinh viên</option>
									<option value="2">Giảng viên</option>
									<option value="1">Admin</option>
								</select>
							</div>
						</div>
						<div class="col-md-12">
							<form:form method="POST" action="/uploadFile"
								enctype="multipart/form-data" modelAttribute="myFile">
									 Select a file *: <form:input id="input_file"
									path="multipartFile" type="file" name="myFile" />
								<br>
								<br>
								<input type="submit">
							</form:form>
						</div>
						<br />

						<div class="col-md-12 text-center">
							<form:form method="POST" action="/readFile"
								modelAttribute="report">
								<form:input path="description" type="hidden"
									value='<%=request.getAttribute("fileName")%>' />
								<form:input id="account_type" path="errorCode" type="hidden" />
								<c:if test="${not empty fileName}">
									<button id="create_btn" type="submit" class="btn btn-primary">Tạo
										tài khoản</button>
								</c:if>
							</form:form>
						</div>

						<c:if test="${not empty error}">
							<div id="error_div"
								class="col-md-12 text-center alert alert-warning ">
								${error}</div>
						</c:if>
						<br />
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp" />

</body>

</html>
