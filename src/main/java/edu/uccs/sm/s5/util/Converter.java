package edu.uccs.sm.s5.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 
 * Class to convert input data in text format to excel
 * @author Deepa Krishna
 *
 */

public class Converter {
	
	public void convertStudentData(String pathToTextFile, String pathToExcelFile) throws IOException {
		
		/*Initialize reader to read the text file*/
		BufferedReader br = new BufferedReader(new FileReader(new File(pathToTextFile)));
		String line = br.readLine();
		
		/*Initialize writer to write to excel file*/
		FileInputStream inputStream = new FileInputStream(new File(pathToExcelFile));
        Workbook workbook = WorkbookFactory.create(inputStream);
        
        /*If the student sheet already exits - delete the sheet and create new sheet*/
        Sheet student = workbook.getSheet(Constants.STUDENT_SHEET_NAME);
		if(student!=null) {
			workbook.removeSheetAt(workbook.getSheetIndex(student));
		}
		student = workbook.createSheet(Constants.STUDENT_SHEET_NAME);
		int rowindex = 0;
		
		/*Loop through each line in the text file and add it to excel*/
		while(line!=null) {
			Row row = student.createRow(rowindex);
			
			String studentName = line.substring(0, 10);
			String ssan = line.substring(10, 19);
			line = line.substring(31);
			
			String[] preferences = new String[35];
			for(int i=0,j=0; i<line.length() && j<preferences.length; i+=2,j++) {
				preferences[j] = line.substring(i, i+2);
			}
			
			row.createCell(0).setCellValue(studentName);
			row.createCell(1).setCellValue(ssan);
			
			for(int i =0; i<preferences.length; i++) {
				if(preferences[i]!=null && !preferences[i].trim().equals("")) {
					row.createCell(i+2).setCellValue(Integer.parseInt(preferences[i].trim()));
				}
			}			
			
			rowindex++;
			line = br.readLine();
		}
		
		inputStream.close();
		
		FileOutputStream outputStream =new FileOutputStream(new File(pathToExcelFile));
		workbook.write(outputStream);
		outputStream.close();
		
		workbook.close();
		br.close();
	}
	
	public void convertWorkshopData(String pathToTextFile, String pathToExcelFile) throws IOException {
		
		/*Initialize reader to read the text file*/
		BufferedReader br = new BufferedReader(new FileReader(new File(pathToTextFile)));
		String line = br.readLine();
		
		/*Initialize writer to write to excel file*/
		FileInputStream inputStream = new FileInputStream(new File(pathToExcelFile));
        Workbook workbook = WorkbookFactory.create(inputStream);
        
        /*If the student sheet already exits - delete the sheet and create new sheet*/
        Sheet workshop = workbook.getSheet(Constants.WORKSHOP_SHEET_NAME);
		if(workshop!=null) {
			workbook.removeSheetAt(workbook.getSheetIndex(workshop));
		}
		workshop = workbook.createSheet(Constants.WORKSHOP_SHEET_NAME);
		int rowindex = 0;
		
		/*Loop through each line in the text file and add it to excel*/
		while(line!=null) {
			Row row = workshop.createRow(rowindex);
			
			String oprName = line.substring(0, 5);
			String location = line.substring(7, 17);
			String periodLength = Character.toString(line.charAt(17));
			line = line.substring(18);
			
			String[] enrollment = new String[16];
			for(int i=0,j=0; i<line.length() && j<enrollment.length; i+=2,j++) {
				enrollment[j] = line.substring(i, i+2);
			}
			
			row.createCell(0).setCellValue(oprName);
			row.createCell(1).setCellValue(location);
			row.createCell(2).setCellValue(Integer.parseInt(periodLength));
			
			for(int i =0; i<enrollment.length; i++) {
				if(enrollment[i]==null || enrollment[i].equals("  ")) {
					row.createCell(i+3).setCellValue(0);
				}else {
					row.createCell(i+3).setCellValue(Integer.parseInt(enrollment[i].trim()));
				}
			}			
			
			rowindex++;
			line = br.readLine();
		}
		
		inputStream.close();
		
		FileOutputStream outputStream =new FileOutputStream(new File(pathToExcelFile));
		workbook.write(outputStream);
		outputStream.close();
		
		workbook.close();
		br.close();
		
	}

}
