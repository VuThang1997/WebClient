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

<script src="js/config.js" type="text/javascript"></script>
<script src="js/adminCheckRollcall.js" type="text/javascript"></script>

</head>

<body>
	<c:if test="${empty sessionScope.id}">
		<c:redirect url="/"/>
	</c:if>
	
	<script>
	 	var id = ${sessionScope.id};
	 	//var host = ${sessionScope.host};
	 	//var port = ${sessionScope.port};
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
		            	 <div id="error_div" class="row col-4 alert alert-warning ">
                          </div>
		                <div class="col-md-12">
		                    <h4>BÁO CÁO TỔNG HỢP CHO LỚP</h4>
		                    <hr>
		                </div>
		            </div>
		            <div class="row">
		                <div class="col-md-12">
		                    <form>
                              <div class="form-group row">
                                <label for="course_select" class="col-2 col-form-label">Chọn học phần: </label> 
								<div class="col-4">
								   <select id="course_select" class="browser-default custom-select">
								   		<option value="0" selected>Chọn học phần</option>
									    <c:forEach items="${allCourses}" var="course">
										    <option value="${course.courseID}">${course.courseName}</option>
										</c:forEach>
									</select>
								</div>
								
								<label for="class_select" class="col-2 col-form-label">Chọn lớp học: </label> 
								<div class="col-4">
								   <select id="class_select" class="browser-default custom-select">
									   
									</select>
								</div>
                              </div>
                              
							  <div class="form-group row">
                                <label for="begin_at" class="col-2 col-form-label">Ngày bắt đầu: </label> 
                                <div class="col-4">
								 <input id="begin_at" width="276"/>
									<script>
										$('#begin_at').datepicker({
											uiLibrary: 'bootstrap4',
											format: 'yyyy-mm-dd'
										});
									</script>                     
								</div>
								
								<label for="finish_at" class="col-2 col-form-label">Ngày kết thúc: </label> 
                                <div class="col-4">
								 <input id="finish_at" width="276"/>
									<script>
										$('#finish_at').datepicker({
											uiLibrary: 'bootstrap4',
											format: 'yyyy-mm-dd',
											maxDate: $.now()
										});
									</script>                     
								</div>
                              </div>
							</form>
							
                              <div class="col-md-12 row">
                              <div class="col-md-6 reportPdf">
                                  <button id="generate_report_pdf" type="submit" class="btn btn-primary buttonReport">Xuất pdf</button>
                              </div>
							  <div class="col-md-6 reportXlsx">
                                  <button id="generate_report_xlsx" type="submit" class="btn btn-primary buttonReport">Xuất excel</button>
                              </div>
							  </div>
							  <div id="div_link" class="col-md-12" style="text-align:center">
								 <a id="link_report" href="#"><i>Click để tải báo cáo</i></a>
							  </div>
                            
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
