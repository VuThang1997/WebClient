package com.webClient3.service;

import java.io.File;
import java.util.List;

public interface FileService {

	public File getFolderUpload();
	
	public List<String> readFileExcelToGetListEmail(String linkFile);
}
