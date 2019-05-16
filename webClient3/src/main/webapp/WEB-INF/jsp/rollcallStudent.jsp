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

<script src="js/config.js" type="text/javascript"></script>
<script src="js/rollcallStudent.js" type="text/javascript"></script>
<script src="js/config.js"></script>
</head>

<body>
	<c:if test="${empty sessionScope.id}">
		<c:redirect url="/"/>
	</c:if>
	<jsp:include page="header.jsp" />

	<script>
	 	id = ${sessionScope.id};
	 	console.log("user id = 	" + id);
	</script>
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
								<h4>ĐIỂM DANH SINH VIÊN</h4>
								<hr>
							</div>
						</div>
						<div class="card-body col-md-12">
							<form>
								
								<div class="form-group row">
									<label for="course_select_manual" class="col-md-5 col-form-label">
										Chọn học phần *: 
									</label>
									<div class="col-md-7">
										 <select id="course_select_manual" class="browser-default custom-select">
										   		<option value="0" selected>Chọn học phần</option>
											    <c:forEach items="${allCourses}" var="courseManul">
												    <option value="${courseManul.courseID}">${courseManul.courseName}</option>
												</c:forEach>
											</select>
									</div>
								</div>
								
								<div class="form-group row">
									<label for="class_select_manual" class="col-md-5 col-form-label">
										Chọn lớp học *: 
									</label>
									<div class="col-md-7">
										 <select id="class_select_manual" class="browser-default custom-select">
										 	<option value="0" selected>Chọn lớp học</option>
											<!-- dùng js để tạo lựa chọn -->
										</select>
									</div>
								</div>
								
								<div class="form-group row">
									<label for="room_select_manual" class="col-md-5 col-form-label">
										Chọn phòng học *: 
									</label>
									<div class="col-md-7">
										 <select id="room_select_manual" class="browser-default custom-select">
										 	<option value="0" selected>Chọn phòng học</option>
											<!-- dùng js để tạo lựa chọn -->
										</select>
									</div>
								</div>
								
								<div class="form-group row">
									<label for="email" class="col-4 col-form-label">
										Email sinh viên *</label>
									<div class="col-8">
										<input id="email" name="email" 
										placeholder="Student email" class="form-control here" type="text" />
									</div>
								</div>
								
								<div class="form-group row">
									<label for="reason_select" class="col-4 col-form-label">
										Lí do *</label>
									<div class="col-md-7">
										 <select id="reason_select" class="browser-default custom-select">
										 	<option value="1" selected>Bị ốm</option>
											<option value="2">Lí do khác</option>
										</select>
									</div>
								</div>
								

								<div class="form-group row">
									<div class="offset-4 col-8">
										<button id="rollcall_student_manual" name="rollcall_student_manual"
											type="submit" class="btn btn-primary">Điểm danh sinh viên</button>
									</div>
								</div>

								<div id="message_manual" class="col-md-12 text-center alert alert-warning "></div>
							</form>
						</div>
					
					</div>
				</div>
				
				<div class="card col-md-6">
					<div class="card-body">
						<div>
							<div class="col-md-12 text-center">
								<h4>ĐIỂM DANH SINH VIÊN THEO FILE</h4>
								<hr>
							</div>
						</div>

						<div class="row col-md-12">
							<label for="course_select" class="col-md-5 col-form-label">
								Chọn học phần *: 
							</label>
							<div class="col-md-7">
								 <select id="course_select" class="browser-default custom-select">
								   		<option value="0" selected>Chọn học phần</option>
									    <c:forEach items="${allCourses}" var="course">
										    <option value="${course.courseID}">${course.courseName}</option>
										</c:forEach>
									</select>
							</div>
						</div>
						
						<br />
						<div class="row col-md-12">
							<label for="class_select" class="col-md-5 col-form-label">
								Chọn lớp học *: 
							</label>
							<div class="col-md-7">
								 <select id="class_select" class="browser-default custom-select">
								 	<option value="0" selected>Chọn lớp học</option>
									<!-- dùng js để tạo lựa chọn -->
								</select>
							</div>
						</div>
						<br />
						<div class="form-group row">
							<label for="room_select" class="col-md-5 col-form-label">
								Chọn phòng học *: 
							</label>
							<div class="col-md-7">
								<select id="room_select" class="browser-default custom-select">
										<option value="0" selected>Chọn phòng học</option>
										<!-- dùng js để tạo lựa chọn -->
								</select>
							</div>
						</div>
						
						<div class="col-md-12">
							<form:form method="POST" action="/uploadFileStudentRollcall"
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
							<form:form id="form_submit" method="POST" action="/readFileStudentRollcall" 
										modelAttribute="classModel">
								<form:input path="className" type="hidden"
									value='<%=request.getAttribute("fileName")%>' />
								<form:input id="classId" path="maxStudent" type="hidden" />
								<form:input id="roomId" path="currentLesson" type="hidden" />
								
								<c:if test="${not empty fileName}">
									<button id="rollcall_btn" type="submit" class="btn btn-primary">
										Điểm danh
									</button>
								</c:if>
							</form:form>
						</div>

					
						<div id="message" class="col-md-12 text-center alert alert-warning ">
							<c:if test="${not empty message}">${message}</c:if>
						</div>
						
						<br />
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp" />

</body>

</html>
