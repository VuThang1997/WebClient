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

<script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
<link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css" />

</head>

<body>
	<script>
	 	id = ${sessionScope.id};
	 	userRole = ${sessionScope.role};
	</script>
	<jsp:include page="header.jsp" />

	<!-- Page Content -->
	<div class="container-fluid bg-info">
		<div class="row">
			<div class="col-md-3 ">
				<jsp:include page="listGroup.jsp" />
			</div>
			<!--end of col-md-3 -->

			<div class="col-md-9">
				<div class="card">
					<div class="card-body">
						<div class="row">
							<div class="col-md-12">
								<h4>Về thông tin tài khoản</h4>
								<c:if test="${not empty message}">
									<div class="message">${message}</div>
								</c:if>
								<hr>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<form:form method="POST" action="/updateAcountInfo" modelAttribute="accountExtent">
									<div class="form-group row">
										<label for="old_username" class="col-4 col-form-label">Tên tài khoản</label>
										<div class="col-8">
											<input id="old_username" name="old_username" placeholder="Username"
												class="form-control here" type="text" value='<%=request.getAttribute("username") %>'
												readonly
											/>
										</div>
									</div>
									<div class="form-group row">
										<label for="username" class="col-4 col-form-label">Tên tài khoản mới <i>(tùy chọn)</i></label>
										<div class="col-8">
											<form:input path="username" id="username" name="username" placeholder="Username"
												class="form-control here" type="text"
											/>
										</div>
									</div>
									<div class="form-group row">
										<label for="current_password" class="col-4 col-form-label">Mật khẩu hiện tại</label>
										<div class="col-8">
											<input id="current_password" name="current_password"
												placeholder="Current Password" class="form-control here" type="text" readonly
												value='<%=request.getParameter("password")%>'
											/>
										</div>
									</div>
									<div class="form-group row">
										<label for="new_password" class="col-4 col-form-label">Mật khẩu mới <i>(tùy chọn)</i>
										</label>
										<div class="col-8">
											<form:input path="password" id="new_password" name="new_password"
												placeholder="New Password" class="form-control here"
												type="password"/>
										</div>
									</div>
									<div class="form-group row">
										<label for="current_email" class="col-4 col-form-label">Email hiện tại</label>
										<div class="col-8">
											<input id="current_email" name="current_email"
												placeholder="Current Email" class="form-control here"
												type="text" readonly value='<%=request.getParameter("email")%>'
												readonly
												/>
										</div>
									</div>
									<div class="form-group row">
										<label for="new_email" class="col-4 col-form-label">Email mới<i>(tùy chọn)</i>
										</label>
										<div class="col-8">
											<form:input path="email" id="new_email" name="new_email"
												placeholder="New Email" class="form-control here"
												type="text" />
										</div>
									</div>

									<div class="form-group row">
										<div class="offset-4 col-8">
											<button id="submit_account" name="submit_account" type="submit"
												class="btn btn-primary">Cập nhật thông tin tài khoản</button>
										</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>

					<hr />
					<div class="card-body">
						<div class="row">
							<div class="col-md-12">
								<h4>Về thông tin cá nhân</h4>
								<div id="alert" class="alert alert-dark" role="alert">
								  	<p id="report"><p>
								</div>
								<hr>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<form>
									<div class="form-group row">
										<label for="full_name" class="col-4 col-form-label">Họ tên</label>
										<div class="col-8">
											<input id="full_name" name="full_name"
												placeholder="Full Name" class="form-control here"
												type="text" value="<%=request.getAttribute("fullName") %>"
												/>
										</div>
									</div>
									<div class="form-group row">
										<label for="current_birthday" class="col-4 col-form-label">Ngày sinh hiện tại:</label>
										<div class="col-8">
											<input id="current_birthday" name="current_birthday"
												placeholder="Current Birthday" class="form-control here"
												type="text" readonly value='<%=request.getAttribute("birthday") %>'/>
										</div>
									</div>
									<div class="form-group row">
										<label for="new_birthday" class="col-4 col-form-label">Ngày sinh *
										</label>
										<div class="col-8">
											<input id="new_birthday" width="276" />
											<script>
												$('#new_birthday').datepicker(
												{
													uiLibrary : 'bootstrap4',
													format: 'yyyy-mm-dd',
													value: "1995-01-01"
												});
											</script>
										</div>
									</div>
									<div class="form-group row">
										<label for="address" class="col-4 col-form-label">Địa chỉ thường trú</label>
										<div class="col-8">
											<input id="address" name="address" placeholder="Address"
												class="form-control here" type="text" value='<%=request.getAttribute("address")%>' />
										</div>
									</div>
									<div class="form-group row">
										<label for="phone" class="col-4 col-form-label">Số điện thoại</label>
										<div class="col-8">
											<input id="phone" name="phone" placeholder="Phone"
												class="form-control here" type="text" value='<%=request.getAttribute("phone")%>'/>
										</div>
									</div>

									<div class="form-group row">
										<div class="offset-4 col-8">
											<button id="submit_profile" name="submit_profile" type="submit" class="btn btn-primary">Cập nhật thông tin cá nhân</button>
										</div>
									</div>
								</form>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp" />
	<script src="js/updateUserInfo.js"></script>

</body>

</html>
