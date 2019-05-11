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
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_2" style="padding-left: 78px;">SubSubPos2</a>
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_2" style="padding-left: 78px;">SubSubPos3</a>
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_2" style="padding-left: 78px;">SubSubPos4</a>
				</div>
				
				<a href="#" class="list-group-item list-group-item-success list-group-item-action">Quản lí thông tin người dùng</a>
				
				<a class="list-group-item list-group-item-danger list-group-item-action" data-remote="true" href="#sub_categories_1" id="categories_1" data-toggle="collapse" data-parent="#sub_categories_1">
					<span>Quản lí lớp học</span>
					<span class="menu-ico-collapse"><i class="fa fa-chevron-down"></i></span>
				</a>
			
				
				<div class="collapse list-group-submenu" id="sub_categories_1">
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_1" style="padding-left: 78px;">SubSubPos1</a>
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_1" style="padding-left: 78px;">SubSubPos2</a>
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_1" style="padding-left: 78px;">SubSubPos3</a>
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_1" style="padding-left: 78px;">SubSubPos4</a>
				</div>
			</c:if>
			
			<c:if test="${sessionScope.role eq 2}">
				<a class="list-group-item list-group-item-danger list-group-item-action" data-remote="true" href="#sub_categories_1" id="categories_1" data-toggle="collapse" data-parent="#sub_categories_1">
					<span>Điểm danh</span>
					<span class="menu-ico-collapse"><i class="fa fa-chevron-down"></i></span>
				</a>
			
				
				<div class="collapse list-group-submenu" id="sub_categories_1">
					<a href="/renderTeacherRollCallView" class="list-group-item sub-item" data-parent="#sub_categories_1" style="padding-left: 78px;">Giảng viên điểm danh</a>
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_1" style="padding-left: 78px;">Điểm danh cho sinh viên</a>
				</div>
			</c:if>
			
				<a class="list-group-item list-group-item-warning list-group-item-action" data-remote="true" href="#sub_categories_2" id="categories_2" data-toggle="collapse" data-parent="#sub_categories_2">
					<span>Báo cáo</span>
					<span class="menu-ico-collapse"><i class="fa fa-chevron-down"></i></span>
				</a>
				
				<div class="collapse list-group-submenu" id="sub_categories_2">
					<a href="/renderGeneralReportView1" class="list-group-item sub-item" data-parent="#sub_categories_2" style="padding-left: 78px;">Báo cáo tổng hợp cho sinh viên</a>
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_2" style="padding-left: 78px;">SubSubPos2</a>
					<a href="/renderGeneralReportForClass" class="list-group-item sub-item" data-parent="#sub_categories_2" style="padding-left: 78px;">Báo cáo tổng hợp cho lớp học</a>
					<a href="#" class="list-group-item sub-item" data-parent="#sub_categories_2" style="padding-left: 78px;">SubSubPos4</a>
				</div>
			</div> 