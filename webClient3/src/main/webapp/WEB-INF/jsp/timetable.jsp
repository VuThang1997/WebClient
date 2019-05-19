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

<script src="js/config.js" type="text/javascript"></script>
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

			<div class="row col-md-9" style = "background-color :white" >
				
				<table border="1px solid" style="margin: 10px">
					<tbody>
						<tr>
							<td align = "center" style = "padding: 10px">Thứ 2</td>
							<c:forEach items="${listClassRoom2}" var="classRoom">
								<td align = "center" style = "padding: 10px">
								${classRoom.classInstance.course.courseName} <br /> ${classRoom.classInstance.className} <br/> ${classRoom.room.roomName} <br/> ${classRoom.beginAt} : ${classRoom.finishAt}
								</td>
							</c:forEach>
						</tr>
						<tr>
							<td align = "center" style = "padding: 10px">Thứ 3</td>
							<c:forEach items="${listClassRoom3}" var="classRoom3">
								<td align = "center" style = "padding: 10px">
								${classRoom3.classInstance.course.courseName} <br /> ${classRoom3.classInstance.className} <br/> ${classRoom3.room.roomName} <br/> ${classRoom3.beginAt} : ${classRoom3.finishAt}
								</td>
							</c:forEach>
						</tr>
						<tr>
							<td align = "center" style = "padding: 10px">Thứ 4</td>
							<c:forEach items="${listClassRoom4}" var="classRoom4">
								<td align = "center" style = "padding: 10px">
								${classRoom4.classInstance.course.courseName} <br /> ${classRoom4.classInstance.className} <br/> ${classRoom4.room.roomName} <br/> ${classRoom4.beginAt} : ${classRoom4.finishAt}
								</td>
							</c:forEach>
						</tr>
						<tr>
							<td align = "center" style = "padding: 10px">Thứ 5</td>
							<c:forEach items="${listClassRoom5}" var="classRoom5">
								<td align = "center" style = "padding: 10px">
								${classRoom5.classInstance.course.courseName} <br /> ${classRoom5.classInstance.className} <br/> ${classRoom5.room.roomName} <br/> ${classRoom5.beginAt} : ${classRoom5.finishAt}
								</td>
							</c:forEach>
						</tr>
						<tr>
							<td align = "center" style = "padding: 10px">Thứ 6</td>
							<c:forEach items="${listClassRoom6}" var="classRoom6">
								<td align = "center" style = "padding: 10px">
								${classRoom6.classInstance.course.courseName} <br /> ${classRoom6.classInstance.className} <br/> ${classRoom6.room.roomName} <br/> ${classRoom6.beginAt} : ${classRoom6.finishAt}
								</td>
							</c:forEach>
						</tr>
						<tr>
							<td align = "center" style = "padding: 10px">Thứ 7</td>
							<c:forEach items="${listClassRoom7}" var="classRoom7">
								<td align = "center" style = "padding: 10px">
								${classRoom7.classInstance.course.courseName} <br /> ${classRoom7.classInstance.className} <br/> ${classRoom7.room.roomName} <br/> ${classRoom7.beginAt} : ${classRoom7.finishAt}
								</td>
							</c:forEach>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp" />
</body>

</html>
