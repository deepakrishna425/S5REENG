package edu.uccs.sm.s5.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import edu.uccs.sm.s5.model.Student;

public class ExcelUtil {
	
	String pathToExcelFile;
	
	public ExcelUtil(String pathToExcelFile){
		this.pathToExcelFile = pathToExcelFile;
	}
	
	public void writeToOrderSheet(List<Student> studentList) throws IOException {
		
		FileInputStream inputStream = new FileInputStream(new File(this.pathToExcelFile));
        Workbook workbook = WorkbookFactory.create(inputStream);
		
		Sheet order = workbook.getSheet(Constants.STUDENT_ORDER_SHEET_NAME);
		if(order!=null) {
			workbook.removeSheetAt(workbook.getSheetIndex(order));
		}
		order = workbook.createSheet(Constants.STUDENT_ORDER_SHEET_NAME);
		
		int rowindex = 0;
		for(Student student : studentList) {
			Row row = order.createRow(rowindex);
			row.createCell(0).setCellValue(student.getName());
			rowindex++;
		}
		
		inputStream.close();
		
		FileOutputStream outputStream =new FileOutputStream(new File(pathToExcelFile));
		workbook.write(outputStream);
		outputStream.close();
		workbook.close();
		
	}

}
