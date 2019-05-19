package com.webClient3.service;

import java.io.File;
import java.util.List;

import com.webClient3.model.ClassRoom;
import com.webClient3.model.ReportError;

public interface FileService {

	public File getFolderUpload();
	
	public List<String> readFileExcelToGetListEmail(String linkFile);
	
	public List<ReportError> readFileExcelToGetListStudentRollcall(String linkFile);

	public List<ClassRoom> readFileExcelForListClassRoom(String linkFile);
}
