<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>

			<br />
		    <div class="list-group ">
				<a href="#" class="list-group-item list-group-item-primary list-group-item-action active">Thông tin người dùng</a>
			
			<c:if test="${sessionScope.role eq 1}">
				<a class="list-group-item list-group-item-warning list-group-item-action" data-remote="true" href="#sub_categories_3" id="categories_3" data-toggle="collapse" data-parent="#sub_categories_3">
					<span>Quản lí tài khoản người dùng khác</span>
					<span class="menu-ico-collapse"><i class="fa fa-chevron-down"></i></span>
				</a>
				
				<div class="collapse list-group-submenu" id="sub_categories_3">
					<a href="/renderCreateAccount" class="list-group-item sub-item" data-parent="#sub_categories_3" style="padding-left: 78px;">Tạo tài khoản</a>
					<a href="/renderDisableAccount" class="list-group-item sub-item" data-parent="#sub_categories_2" style="padding-left: 78px;">Vô hiệu hóa tài khoản</a>
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_2" style="padding-left: 78px;">SubSubPos3</a>
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_2" style="padding-left: 78px;">SubSubPos4</a>
				</div>
				
				<!-- <a href="adminCheckRollcall.jsp" class="list-group-item list-group-item-success list-group-item-action">Điểm danh cuối ngày</a> -->
				
				<a class="list-group-item list-group-item-danger list-group-item-action" data-remote="true" href="#sub_categories_1" id="categories_1" data-toggle="collapse" data-parent="#sub_categories_1">
					<span>Quản lí lớp học</span>
					<span class="menu-ico-collapse"><i class="fa fa-chevron-down"></i></span>
				</a>
			
				<div class="collapse list-group-submenu" id="sub_categories_1">
					<a href="/renderCreateClass" class="list-group-item sub-item" data-parent="#sub_categories_1" style="padding-left: 78px;">Thêm lớp học</a>
					<a href="/renderCreateStudentClass" class="list-group-item sub-item" data-parent="#sub_categories_1" style="padding-left: 78px;">Thêm sinh viên</a>
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_1" style="padding-left: 78px;">SubSubPos3</a>
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_1" style="padding-left: 78px;">SubSubPos4</a>
				</div>
				
				<a class="list-group-item list-group-item-warning list-group-item-action" data-remote="true" href="#sub_categories_4" id="categories_4" data-toggle="collapse" data-parent="#sub_categories_4">
					<span>Quản lí phòng học</span>
					<span class="menu-ico-collapse"><i class="fa fa-chevron-down"></i></span>
				</a>
				<div class="collapse list-group-submenu" id="sub_categories_4">
					<a href="/renderCreateRoom" class="list-group-item sub-item" data-parent="#sub_categories_4" style="padding-left: 78px;">Thêm phòng học</a>
					<a href="/renderGetRoomInfo" class="list-group-item sub-item" data-parent="#sub_categories_4" style="padding-left: 78px;">Xem thông tin phòng học</a>
					<a href="/renderUpdateRoomInfo" class="list-group-item sub-item" data-parent="#sub_categories_4" style="padding-left: 78px;">Cập nhật thông tin phòng học</a>
					<a href="/renderDeleteRoom" class="list-group-item sub-item" data-parent="#sub_categories_4" style="padding-left: 78px;">Xóa phòng học</a>
				</div>
				
				<a class="list-group-item list-group-item-warning list-group-item-action" data-remote="true" href="#sub_categories_5" id="categories_5" data-toggle="collapse" data-parent="#sub_categories_5">
					<span>Quản lí học kì</span>
					<span class="menu-ico-collapse"><i class="fa fa-chevron-down"></i></span>
				</a>
				<div class="collapse list-group-submenu" id="sub_categories_5">
					<a href="/renderCreateSemester" class="list-group-item sub-item" data-parent="#sub_categories_5" style="padding-left: 78px;">Thêm học kì</a>
					<a href="/renderGetSemesterInfo" class="list-group-item sub-item" data-parent="#sub_categories_5" style="padding-left: 78px;">Xem thông tin học kì</a>
					<a href="/renderUpdateSemesterInfo" class="list-group-item sub-item" data-parent="#sub_categories_5" style="padding-left: 78px;">Cập nhật thông tin học kì</a>
				</div>
				
				<a class="list-group-item list-group-item-warning list-group-item-action" data-remote="true" href="#sub_categories_6" id="categories_6" data-toggle="collapse" data-parent="#sub_categories_6">
					<span>Quản lí học phần</span>
					<span class="menu-ico-collapse"><i class="fa fa-chevron-down"></i></span>
				</a>
				<div class="collapse list-group-submenu" id="sub_categories_6">
					<a href="/renderCreateCourse" class="list-group-item sub-item" data-parent="#sub_categories_6" style="padding-left: 78px;">Thêm học phần</a>
					<a href="/renderGetCourseInfo" class="list-group-item sub-item" data-parent="#sub_categories_6" style="padding-left: 78px;">Xem thông tin học phần</a>
					<a href="/renderUpdateCourseInfo" class="list-group-item sub-item" data-parent="#sub_categories_6" style="padding-left: 78px;">Cập nhật thông tin học phần</a>
				</div>
			</c:if>
			
			<c:if test="${sessionScope.role eq 2}">
				<a class="list-group-item list-group-item-danger list-group-item-action" data-remote="true" href="#sub_categories_1" id="categories_1" data-toggle="collapse" data-parent="#sub_categories_1">
					<span>Điểm danh</span>
					<span class="menu-ico-collapse"><i class="fa fa-chevron-down"></i></span>
				</a>
			
				<div class="collapse list-group-submenu" id="sub_categories_1">
					<a href="/renderTeacherRollCallView" class="list-group-item sub-item" data-parent="#sub_categories_1" style="padding-left: 78px;">Giảng viên điểm danh</a>
					<a href="/renderRollcallStudentView" class="list-group-item sub-item" data-parent="#sub_categories_1" style="padding-left: 78px;">Điểm danh cho sinh viên</a>
				</div>
			</c:if>
			
				<a class="list-group-item list-group-item-warning list-group-item-action" data-remote="true" href="#sub_categories_2" id="categories_2" data-toggle="collapse" data-parent="#sub_categories_2">
					<span>Báo cáo</span>
					<span class="menu-ico-collapse"><i class="fa fa-chevron-down"></i></span>
				</a>
				
				<div class="collapse list-group-submenu" id="sub_categories_2">
					<a href="/renderGeneralReportView1" class="list-group-item sub-item" data-parent="#sub_categories_2" style="padding-left: 78px;">Báo cáo điểm danh theo sinh viên</a>
					<a href="/renderGeneralReportForClass" class="list-group-item sub-item" data-parent="#sub_categories_2" style="padding-left: 78px;">Báo cáo tổng hợp điểm danh theo lớp</a>
				</div>
			</div> 