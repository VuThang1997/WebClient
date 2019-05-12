package com.webClient3.service;

import java.util.List;

import com.webClient3.model.TeacherClass;

public interface RollcallService {

	List<TeacherClass> findRecordByTeacherID(int teacherID);

}
