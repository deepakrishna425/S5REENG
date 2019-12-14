package edu.uccs.sm.s5.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.uccs.sm.s5.model.Period;
import edu.uccs.sm.s5.model.Student;
import edu.uccs.sm.s5.model.Workshop;
import edu.uccs.sm.s5.util.Constants;

public class InitializeData {
	
	String pathToExcelFile;
	
	public InitializeData(String pathToExcelFile) {
		this.pathToExcelFile = pathToExcelFile;
	}
	
	public List<Student> initializeStudents() throws IOException {
		
		Workbook workbook = new XSSFWorkbook(this.pathToExcelFile);
		Sheet studentSheet = workbook.getSheet(Constants.STUDENT_SHEET_NAME);
		
		List<Student> studentList = new ArrayList<Student>();
		
		int numRows = studentSheet.getLastRowNum()+1;
		
		for (int ri = 0; ri<numRows ; ri++) {
            Row nextRow = studentSheet.getRow(ri);
            
            Student tempStudent = new Student();
            List<Integer> workshopPreference = new ArrayList<Integer>();

            int numCols = nextRow.getLastCellNum()+1; 
            
            for (int ci = 0; ci<numCols; ci++){
                Cell cell = nextRow.getCell(ci);
                if(ci == 0) {
                	tempStudent.setName(cell.getStringCellValue());
                }else if(ci == 1) {
                	tempStudent.setSsan(cell.getStringCellValue());
                }else {
                	if(cell == null) {
                		workshopPreference.add(36);
                	}else {
                		tempStudent.setNoofWorkshopsInterestedIn(tempStudent.getNoofWorkshopsInterestedIn()+1);
                		workshopPreference.add(new Integer((int)cell.getNumericCellValue()));
                	}
                }
            }
            
            tempStudent.setWorkshopPreference(workshopPreference);
            studentList.add(tempStudent);
        }
        
        workbook.close();
        return studentList;
        
	}	
	
	
	public List<Workshop> initailizeWorkshops() throws IOException {
		
		Workbook workbook = new XSSFWorkbook(this.pathToExcelFile);
		Sheet workshopSheet = workbook.getSheet(Constants.WORKSHOP_SHEET_NAME);
		
		List<Workshop> workshopList = new ArrayList<Workshop>();
		
		int numRows = workshopSheet.getLastRowNum()+1;
		
        for (int ri = 0; ri<numRows ; ri++) {
            Row nextRow = workshopSheet.getRow(ri);
            
            Workshop tempWorkshop = new Workshop();
            List<Period> periods = new ArrayList<Period>();
            int periodindex = 0;
            
            int numCols = nextRow.getLastCellNum(); 
            
            for (int ci = 0; ci<numCols; ci++){
                Cell cell = nextRow.getCell(ci);
                if(ci == 0) {
                	tempWorkshop.setOpr(cell.getStringCellValue());
                }else if(ci == 1) {
                	tempWorkshop.setLocation(cell.getStringCellValue());
                }else if(ci == 2) {
                	tempWorkshop.setLengthOfWorkshop((int)cell.getNumericCellValue());
                }else if(ci >2 && ci <= (2+Constants.MAX_PERIODS)){
                	Period period = new Period();
                	period.setId(periodindex);
                	if(cell == null) {
                		period.setMaxEnroll(0);
                	}else {
	                	period.setMaxEnroll(new Integer((int)cell.getNumericCellValue()));
                	}
                	periods.add(period);
                	periodindex++;
                }else{
                	Period period = periods.get(periodindex);
                	if(cell == null) {
                		period.setMinEnroll(0);
                	}else {
                		period.setMinEnroll(new Integer((int)cell.getNumericCellValue()));
                	}
                	periodindex++;
                }
                
                if(periodindex == 8) {
                	periodindex = 0;
                }
            }
            tempWorkshop.setPeriods(periods);
            workshopList.add(tempWorkshop);
        }
        
        workbook.close();
        return workshopList;
		
	}
	
}
