package com.webClient3.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.webClient3.enumData.AfternoonTimeFrame;
import com.webClient3.enumData.MorningTimeFrame;
import com.webClient3.model.Class;
import com.webClient3.model.ClassRoom;
import com.webClient3.model.ReportError;
import com.webClient3.utils.GeneralValue;

@Service
@Qualifier("FileServiceImpl1")
public class FileServiceImpl1 implements FileService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl1.class);

	@Override
	public File getFolderUpload() {
		File folderUpload = new File(GeneralValue.FOLDER_IMPORT_FILE);
		if (!folderUpload.exists()) {
			folderUpload.mkdirs();
		}
		return folderUpload;
	}

	@Override
	public List<String> readFileExcelToGetListEmail(String linkFile) {
		int fieldNumber = 0;
		List<String> listEmail = null;
		String studentEmail = null;
		LOGGER.info("========================= link file  = " + linkFile);

		try {

			// Creating a Workbook from an Excel file (.xls or .xlsx)
			Workbook workbook = WorkbookFactory.create(new File(linkFile));

			// Create a DataFormatter to format and get each cell's value as String
			DataFormatter dataFormatter = new DataFormatter();

			Iterator<Sheet> sheetIterator = workbook.sheetIterator();
			Iterator<Cell> cellIterator = null;
			Sheet sheet = null;
			Cell cell = null;
			Row row = null;
			String cellValue = null;
			listEmail = new ArrayList<>();

			while (sheetIterator.hasNext()) {
				sheet = sheetIterator.next();

				LOGGER.info("\n\nIterating over Rows and Columns using Iterator\n");
				Iterator<Row> rowIterator = sheet.rowIterator();
				while (rowIterator.hasNext()) {
					row = rowIterator.next();
					
					//exclude the first row = header of table
					if (row.getRowNum() == 0) {
						continue;
					}

					// Now let's iterate over the columns of the current row
					cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) {
						fieldNumber++;
						cell = cellIterator.next();
						cellValue = dataFormatter.formatCellValue(cell);

						switch (fieldNumber) {
						case 1:
							studentEmail = cellValue;
							listEmail.add(studentEmail);
							LOGGER.info("====================== Student email = " + studentEmail);
							break;
						case 2:
							fieldNumber = 0;
							break;
						}
					}
				}

			}
		} catch(EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
			LOGGER.info("=========== Error happened when reading excel file!");
		}
		
		return listEmail;
	}

	@Override
	public List<ReportError> readFileExcelToGetListStudentRollcall(String linkFile) {
		int fieldNumber = 0;
		List<ReportError> listStudentRollcall = null;
		ReportError reportError = null;
		LOGGER.info("========================= link file  = " + linkFile);

		try {

			// Creating a Workbook from an Excel file (.xls or .xlsx)
			Workbook workbook = WorkbookFactory.create(new File(linkFile));

			// Create a DataFormatter to format and get each cell's value as String
			DataFormatter dataFormatter = new DataFormatter();

			Iterator<Sheet> sheetIterator = workbook.sheetIterator();
			Iterator<Cell> cellIterator = null;
			Sheet sheet = null;
			Cell cell = null;
			Row row = null;
			String cellValue = null;
			listStudentRollcall = new ArrayList<>();

			while (sheetIterator.hasNext()) {
				sheet = sheetIterator.next();

				LOGGER.info("\n\nIterating over Rows and Columns using Iterator\n");
				Iterator<Row> rowIterator = sheet.rowIterator();
				while (rowIterator.hasNext()) {
					row = rowIterator.next();
					
					//exclude the first row = header of table
					if (row.getRowNum() == 0) {
						continue;
					}

					// Now let's iterate over the columns of the current row
					cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) {
						fieldNumber++;
						cell = cellIterator.next();
						cellValue = dataFormatter.formatCellValue(cell);

						switch (fieldNumber) {
						case 1:
							reportError = new ReportError();
							reportError.setDescription(cellValue);
							LOGGER.info("====================== Student email = " + reportError.getDescription());
							break;
						case 2:
							
							break;
						case 3:
							LOGGER.info("===================== cell value = " + cellValue);
							int reason = Integer.parseInt(cellValue);
							reportError.setErrorCode(reason);
							listStudentRollcall.add(reportError);
							LOGGER.info("====================== reason = " + reportError.getErrorCode());
							reportError = null;
							fieldNumber = 0;
							break;
						}
					}
				}

			}
		} catch(EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
			LOGGER.info("=========== Error happened when reading excel file!");
		}
		
		return listStudentRollcall;
	}

	@Override
	public List<ClassRoom> readFileExcelForListClassRoom(String linkFile) {
		int fieldNumber = 0;
		List<ClassRoom> listClassRoom = null;
		ClassRoom tmpClassRoom = null;
		LOGGER.info("========================= link file  = " + linkFile);

		try {

			// Creating a Workbook from an Excel file (.xls or .xlsx)
			Workbook workbook = WorkbookFactory.create(new File(linkFile));

			// Create a DataFormatter to format and get each cell's value as String
			DataFormatter dataFormatter = new DataFormatter();

			Iterator<Sheet> sheetIterator = workbook.sheetIterator();
			Iterator<Cell> cellIterator = null;
			Sheet sheet = null;
			Cell cell = null;
			Row row = null;
			String cellValue = null;
			String enumName = "FRAME";
			int weekday = -1;
			LocalTime tmpBegin = null;
			LocalTime tmpFinish = null;
			listClassRoom = new ArrayList<>();

			while (sheetIterator.hasNext()) {
				sheet = sheetIterator.next();

				LOGGER.info("\n\nIterating over Rows and Columns using Iterator\n");
				Iterator<Row> rowIterator = sheet.rowIterator();
				while (rowIterator.hasNext()) {
					row = rowIterator.next();
					
					//exclude the first row = header of table
					if (row.getRowNum() == 0) {
						continue;
					}

					// Now let's iterate over the columns of the current row
					cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) {
						fieldNumber++;
						cell = cellIterator.next();
						cellValue = dataFormatter.formatCellValue(cell);

						switch (fieldNumber) {
						case 1:
							tmpClassRoom = new ClassRoom();
							tmpClassRoom.setClassInstance(new Class());
							tmpClassRoom.getClassInstance().setClassName(cellValue);
							LOGGER.info("====================== class name  = " + cellValue);
							break;
						case 2:
							weekday = Integer.parseInt(cellValue);
							tmpClassRoom.setWeekday(weekday);
							break;
						case 3:
							tmpBegin = null;
							enumName = "FRAME";
							enumName += cellValue;
							LOGGER.info("====================== enum name  = " + enumName);
							
							for (MorningTimeFrame frame: MorningTimeFrame.values()) {
								LOGGER.info("====================== frame name  = " + frame.name());
								if (enumName.equalsIgnoreCase(frame.name())) {
									tmpBegin = frame.getValue();
									break;
								}
							}
							
							if (tmpBegin == null) {
								for (AfternoonTimeFrame frame: AfternoonTimeFrame.values()) {
									LOGGER.info("====================== frame name  = " + frame.name());
									if (enumName.equalsIgnoreCase(frame.name())) {
										tmpBegin = frame.getValue();
										break;
									}
								}
							}
							
							tmpClassRoom.setBeginAt(tmpBegin);
							break;
						case 4:
							tmpFinish = null;
							enumName = "FRAME";
							enumName += cellValue;
							LOGGER.info("====================== enum name  = " + enumName);
							
							for (MorningTimeFrame frame: MorningTimeFrame.values()) {
								LOGGER.info("====================== frame name  = " + frame.name());
								if (enumName.equalsIgnoreCase(frame.name())) {
									tmpFinish = frame.getValue();
									break;
								}
							}
							
							if (tmpFinish == null) {
								for (AfternoonTimeFrame frame: AfternoonTimeFrame.values()) {
									LOGGER.info("====================== frame name  = " + frame.name());
									if (enumName.equalsIgnoreCase(frame.name())) {
										tmpFinish = frame.getValue();
										break;
									}
								}
							}
							
							tmpClassRoom.setFinishAt(tmpFinish);
							listClassRoom.add(tmpClassRoom);
							tmpClassRoom = null;
							fieldNumber = 0;
							break;
						}	
					}
				}

			}
		} catch(EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
			LOGGER.info("=========== Error happened when reading excel file!");
		}
		
		return listClassRoom;
	}
}
