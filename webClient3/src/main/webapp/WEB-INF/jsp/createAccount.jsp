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

</head>

<body>
	<script>
	 	var id = ${sessionScope.id};
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
		            	<c:if test = "${not empty error}">
		            	 <div id="error_div" class="row col-4 alert alert-warning ">
		            	 	${error}
                          </div>
                        </c:if>
		                <div class="col-md-12">
		                    <h4>THÊM TÀI KHOẢN THEO FILE</h4>
		                    <hr>
		                </div>
		            </div>
		            <div class="row">
		                <div class="col-md-12">
							<form:form method="POST" action="/uploadFile" enctype="multipart/form-data" modelAttribute="myFile">
								 Select a file: <form:input id="input_file" path="multipartFile"  type="file" name="myFile" /><br><br>
  								 <input type="submit">
							</form:form>
		                </div>
		                
		                <c:if test="${not empty fileName}">
						    <div class="col-md-12 text-center">
			                	<form:form method="POST" action="/readFile" modelAttribute="report">
			                		<form:input path="description" type="hidden" value='<%=request.getAttribute("fileName") %>'  />
			                		<button id="create_btn" type="submit" class="btn btn-primary">Tạo tài khoản</button>
			                	</form:form>
		                	</div>
						</c:if>
		            </div>
		            <div class="row">
		            	<c:if test="${not empty message}">
							<div class="errorAccount"><h3>${message}</h3></div>
						</c:if>
		            </div>
		        </div>
				
		    </div>
		</div>
		</div>
	</div>

	<jsp:include page="footer.jsp" />

</body>

</html>
