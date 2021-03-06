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

        <script src="js/generalReportForTeacher.js" type="text/javascript"></script>
        <script src="js/config.js" type="text/javascript"></script>
    </head>

    <body>
        <c:if test="${empty sessionScope.id}">
            <c:redirect url="/"/>
        </c:if>

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
                               
                                </div>
                                <div class="col-md-12">
                                    <h4>BÁO CÁO TỔNG HỢP CHO GIẢNG VIÊN</h4>
                                    <hr>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <form>
                                        <div class="form-group row">
                                            <label for="email" class="col-2 col-form-label">Email giảng viên</label> 
                                            <div class="col-4">
                                                <input id="email" name="email" placeholder="Hãy nhập email ...." class="form-control here" required="required" type="text">
                                            </div>

                                            <label for="semester_select" class="col-2 col-form-label">Chọn học kì: </label> 
                                            <div class="col-4">
                                                <select id="semester_select" class="browser-default custom-select">
                                                    <c:forEach items="${allSemester}" var="semester">
                                                        <option value="${semester.semesterID}">${semester.semesterName}</option>
                                                    </c:forEach>
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
                                    <div class="loader col-md-12">
                                        <img id = "img_loader" src= "images/loader.gif" style= "" alt="Loading..." />
                                    </div>
                                    <div id="div_link" class="col-md-12" style="text-align:center">
                                        <a id="link_report" href="#"><i>Click this link to download report</i></a>
                                    </div>
									<br />
									<div class="alert alert-warning text-center" role="alert">
										<p id="message"></p>
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
