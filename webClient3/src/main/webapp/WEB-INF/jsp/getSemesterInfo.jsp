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

        <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>

        <script src="js/config.js" type="text/javascript"></script>
        <script src="js/getCourseInfo.js" type="text/javascript"></script>
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
                    <div class = "col-md-3"> </div>

                    <div class="card col-md-6">
                        <div class="card-body">
                            <div>
                                <div class="col-md-12 text-center">
                                    <h4>XEM THÔNG TIN HỌC PHẦN</h4>
                                    <hr>
                                </div>
                            </div>
                            <div class="card-body col-md-12">
                                <form id="retrieve_course_form">
                                    <div class="form-group row">
                                        <label for="course_select" class="col-md-4 col-form-label">
                                            Chọn học phần: 
                                        </label>
                                        <div class="col-md-8">
                                            <select id="course_select" class="browser-default custom-select">
                                                <option value="0" selected>Chọn phòng học</option>
                                                <c:forEach items="${allCourses}" var="course">
                                                    <option value="${course.courseID}">${course.courseName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group row show_info">
                                        <label for="room_name" class="col-4 col-form-label">
                                            Tên học phần *
                                        </label>
                                        <div class="col-8">
                                            <input id="course_name" name="course_name" readonly
                                                   placeholder="Course Name" class="form-control here" type="text" />
                                        </div>
                                    </div>

                                    <div class="form-group row show_info">
                                        <label for="description" class="col-4 col-form-label">
                                            Mô tả học phần *
                                        </label>
                                        <div class="col-8">
                                            <input id="description" name="description" placeholder="Description"
                                                   class="form-control here" type="text" readonly/>
                                        </div>
                                    </div>

                                    <div class="loader col-md-12">
                                        <img id = "img_loader" src= "images/loader.gif" style= "" alt="Loading..." />
                                    </div>

                                    <div id="message" class="col-md-12 text-center alert alert-warning ">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <%-- 				<div class="card col-md-6">
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
                                </div> --%>
                </div>
            </div>
        </div>

        <jsp:include page="footer.jsp" />

    </body>

</html>
