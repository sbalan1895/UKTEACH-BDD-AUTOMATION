package com.baseJobApply;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelResource {

	String filePath = "C:\\Users\\sivabalan\\eclipse-workspace\\UKTEACH_JobApply\\src\\test\\resources\\ResourceFiles\\UKTeach_SchoolCredentials.xlsx";

	public static Map<String, String> getRowData(String filePath, String sheetName, String keys ) throws EncryptedDocumentException, IOException {

		DataFormatter fmt = new DataFormatter();

		try(FileInputStream fIs = new FileInputStream(filePath); Workbook wsB = WorkbookFactory.create(fIs);) {
			
			Sheet sheet = wsB.getSheet(sheetName);
			Row header = sheet.getRow(0);

			for(int r= 1; r <= sheet.getLastRowNum(); r++) {

				Row row = sheet.getRow(r);
				if(row == null) continue;

				if(fmt.formatCellValue(row.getCell(0)).trim().equalsIgnoreCase(keys)) {

					Map<String, String> data = new HashMap<>();

					for (int c = 0; c < header.getLastCellNum(); c++) {

						data.put(fmt.formatCellValue(header.getCell(c)).trim(),
								fmt.formatCellValue(row.getCell(c)).trim());
					}
					return data;

				}

			}

		} catch (RuntimeException e) {
			throw e;
		}
		catch(Exception ex) {
			throw new RuntimeException("Cannot read Excel file: " + filePath, ex);

		}

		throw new RuntimeException("No row found for key: " + keys);
	}

}
