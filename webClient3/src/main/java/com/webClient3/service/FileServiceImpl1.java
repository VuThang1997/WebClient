package com.webClient3.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.webClient3.utils.GeneralValue;

@Service
@Qualifier("FileServiceImpl1")
public class FileServiceImpl1 implements FileService{

	@Override
	public File getFolderUpload() {
		File folderUpload = new File(GeneralValue.FOLDER_IMPORT_FILE);
		if (!folderUpload.exists()) {
			folderUpload.mkdirs();
		}
		return folderUpload;
	}

}
