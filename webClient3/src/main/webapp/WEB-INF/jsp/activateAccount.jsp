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
              
         <script type="text/javascript"
			src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>

        <!-- Custom styles for this template -->
        <link href="css/modern-business.css" rel="stylesheet">

        <script src="js/config.js" type="text/javascript"></script>
        <script src="js/activateAccount.js" type="text/javascript"></script>
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

				<div class="col-md-9">
                    <div class="card">
                        <div class="card-body">
                            <div class="row">

                            </div>
                            <div class="col-md-12">
                                <h4>KÍCH HOẠT TÀI KHOẢN</h4>
                                <hr>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <form id="activate_account_form">
                                        <div class="form-group row">
                                            <div class = "col-2 text-center">
                                                <label for="email" class="col-form-label text-center">Email </label> 
                                            </div>
                                            <div class="col-4">
                                                <input id="email" name="email" placeholder="Hãy nhập email ...." class="form-control here" required="required" type="text">
                                            </div>
                                            <div class="col-4">
                                                <button id="activate_btn" type="submit" class="btn btn-primary col-12 buttonReport">Kích hoạt</button>
                                            </div>
                                            <div class="loader_disable_account col-1">
                                                <img id = "img_loader" src= "images/loader.gif" style= "text-align: left" alt="Loading..." />
                                            </div>
                                        </div>
                                    </form>

                                    <br />
                                    <div id="message" class="alert alert-warning text-center" role="alert">
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
