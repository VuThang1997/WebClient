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
<script src="vendor/qrcode/qrcode.js" type="text/javascript"></script>
<script src="js/teacherRollCall.js" type="text/javascript"></script>

</head>

<body>
	<script>
		var id = ${sessionScope.id};
		var userRole = ${sessionScope.role};
		//var host = ${sessionScope.host};
		//alert("host = " + host);
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

						<div id="error_div" class="alert alert-warning" role="alert">${error}</div>
						<hr />
						
						<div class="row col-md-12">
							<h4>Giảng viên điểm danh</h4>
							<hr>
						</div>

						<div class="col-md-12 text-center">
							<div class="row">
								<label for="class_select" class="col-md-2 col-form-label">Lớp học: </label>
								<select id="class_select" class="col-md-4 browser-default custom-select">
									<option selected value=""></option>
									<c:forEach items="${allClasses}" var="teacherClass">
									    <option value="${teacherClass.classInstance.id}">${teacherClass.classInstance.className}</option>
									</c:forEach>
								</select>

								<label for="room_select" class="col-md-2 col-form-label">Phòng học: </label>
								<select id="room_select" class="col-md-4 browser-default custom-select">
								</select>
							</div>
							
							<br />
							<button id="roll_call_btn" type="submit" class="btn btn-primary">Điểm danh!</button>
							<hr />

							<div class="row text-center">
								<div id="qrcode" style="width:200px; height:200px; margin-top:15px; margin: 0 auto;"></div>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp" />
</body>

</html>
