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

<script type="text/javascript"
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>

<link href="css/createAccount.css" rel="stylesheet">

<script src="js/config.js" type="text/javascript"></script>
<script src="js/createClassRoom.js" type="text/javascript"></script>
</head>

<body>
	<c:if test="${empty sessionScope.id}">
		<c:redirect url="/" />
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
			    <div class = "col-md-3"></div>
				<div class="card col-md-6">
					<div class="card-body">
						<div>
							<div class="col-md-12 text-center">
								<h4>THÊM TIẾT HỌC THEO FILE</h4>
								<hr>
							</div>
						</div>

						<div class="form-group row">
							<label for="room_select" class="col-5 col-form-label">
								Chọn phòng học:
							</label>
							<div class="col-7">
								<select id="room_select"
									class="browser-default custom-select">
									<option value="0" selected>Chọn phòng học</option>
									<c:forEach items="${allRooms}" var="room">
										<option value="${room.id}">${room.roomName}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<br />
						<div class="col-md-12">
							<form:form method="POST" action="/uploadClassRoomFile"
								enctype="multipart/form-data" modelAttribute="myFile">
									 Select a file *: <form:input id="input_file"
									path="multipartFile" type="file" name="myFile" />
								<br>
								<br>
								<input type="submit">
								<a id="link_report" href="#"><i>Tải template mẫu</i></a>
							</form:form>
						</div>
						<br />

						<div class="col-md-12 text-center">
							<form:form id="classroom_by_file" method="POST"
								action="/readClassRoomFile" modelAttribute="roomIdHolder">
								<form:input path="description" type="hidden"
									value='<%=request.getAttribute("fileName")%>' />
								<form:input id="roomId" path="errorCode" type="hidden" />
								
								<c:if test="${not empty fileName}">
									<button id="create_btn" type="submit" class="btn btn-primary">
										Thêm tiết học
									</button>
								</c:if>
							</form:form>
						</div>
						<div class="loader col-md-12">
							<img id="img_file_loader" src="images/loader.gif" style=""
								alt="Loading..." />
						</div>
						<c:if test="${not empty message}">
							<div class="col-md-12 text-center alert alert-warning ">
								${message}
							</div>
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
