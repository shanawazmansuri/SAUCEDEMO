package com.utilities;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.InputStream;

public class ExcelUtils {

	static HSSFWorkbook workbook;
	static HSSFSheet sheet;

	public ExcelUtils(String excelPath, String sheetName) {

		try {
			//File src = new File(excelPath);
			InputStream file = new FileInputStream(excelPath);
			workbook = new HSSFWorkbook(new POIFSFileSystem(file));
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/*
	 * public static void main(String[] args) { getRowCount(); getCellDataNumeric(1,
	 * 1); getCellDataString(0, 0);
	 * 
	 * }
	 */

	public static int getRowCount() {

		int rowCount = 0;
		try {
			rowCount = sheet.getPhysicalNumberOfRows();
			// System.out.println("Row Count is " + rowCount);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
		}
		return rowCount;
	}

	public static int getColCount() {
		int colCount = 0;
		try {
			colCount = sheet.getRow(0).getPhysicalNumberOfCells();
			// System.out.println("Columns Count is " + colCount);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
		}
		return colCount;
	}

	public static String getCellDataString(int rowNum, int colNum) {
		String cellData = null;
		try {

			cellData = sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
			// System.out.println("No of Columns are " + cellData);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();

		}
		return cellData;
	}

	/*
	 * public static void getCellDataNumeric(int rowNum, int colNum) { try {
	 * 
	 * double cellData = sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
	 * System.out.println("No of Columns are " + cellData); } catch (Exception e) {
	 * System.out.println(e.getMessage()); System.out.println(e.getCause());
	 * e.printStackTrace();
	 * 
	 * } }
	 * 
	 */
	public static Object[][] excelData(String excelPath, String sheetName) {
		ExcelUtils util = new ExcelUtils(excelPath, sheetName);
		int rowCount = util.getRowCount();
		int colCount = util.getColCount();
		Object data[][] = new Object[rowCount - 1][colCount];

		for (int i = 1; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				String cellData = util.getCellDataString(i, j);
				// System.out.print(cellData + " | ");
				data[i - 1][j] = cellData;

			}

		}
		return data;
	}

}
