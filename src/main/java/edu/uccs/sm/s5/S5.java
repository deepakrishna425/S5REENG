package edu.uccs.sm.s5;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import edu.uccs.sm.s5.io.InitializeData;
import edu.uccs.sm.s5.model.Period;
import edu.uccs.sm.s5.model.Student;
import edu.uccs.sm.s5.model.Workshop;
import edu.uccs.sm.s5.util.Constants;
import edu.uccs.sm.s5.util.Converter;
import edu.uccs.sm.s5.util.ExcelUtil;

public class S5 {
	
	public List<Student> studentList;
	public List<Workshop> workshopList;
	
	/*
	 * 
	 * Name : isStudentAvailable
     * Authors : Deepa Krishna
     * Description : This procedure checks for a given student , and a starting period and 
     * if he/she can attend workshop of "lengthOfPeriod"  number of periods.
     * returns true if yes false if not.
     *
	 */
	
	public boolean isStudentAvailable(Student student, int period,int lengthOfPeriod) {
		int count = 0;
		for(int i =0; i<lengthOfPeriod;i++){
			if(period+i >= Constants.MAX_PERIODS)
				break;
			if(student.getScheduledWorkshops().get(period+i).isEmpty()) {
				count++;
			}
		}
		
		if(count == lengthOfPeriod) return true;
		
		return false;
	}
	
	/*
	 * 
	 * Name : checkAvailablity
     * Authors : Deepa Krishna
     * Description : This procedure checks for a given student ,
     *  and period - "nextPreferedWorkshop" is within   the max limit for that workshop and
     *  also calls  isStudentAvailable method to check student availability 
     * returns true if yes false if not.
     *
	 */
	
	public boolean checkAvailablity(Student student, int period, int nextPreferedWorkshop) {
		int maxEnrollForPreferedWorkshop = workshopList.get(nextPreferedWorkshop).getPeriods().get(period).getMaxEnroll();
		int currEnrollForPreferedWorkshop = workshopList.get(nextPreferedWorkshop).getPeriods().get(period).getCurrEnroll();
		if(maxEnrollForPreferedWorkshop!=0 &&
				currEnrollForPreferedWorkshop < maxEnrollForPreferedWorkshop && isStudentAvailable(student, period, workshopList.get(nextPreferedWorkshop).getLengthOfWorkshop())) {
			return true;
		}else {
			return false;
		}
	}
	
	public void scheduleStudent(Student student, int period, int workshopindex,int lengthOfPeriod, int currentChoice) {
		for(int i=0;i<lengthOfPeriod;i++) {
			student.getScheduledWorkshops().put(new Integer(period+i), workshopList.get(workshopindex).getOpr());
		}
		student.setLastWorkshopChoiceScheduled(currentChoice);
		workshopList.get(workshopindex).getPeriods().get(period).getScheduledStudents().add(student);
		workshopList.get(workshopindex).getPeriods().get(period).setCurrEnroll(
				workshopList.get(workshopindex).getPeriods().get(period).getCurrEnroll() + 1);
	}
	/*
	 * 
	 * Name : Schedule
     * Authors : Deepa Krishna
     * Description : This procedure assigns each student to their ordered 
     * preferences until all student schedules are full.  In the case of 
     * overlapping or full workshops, a student may end up with lower 
     * preferences.  
     * Algorithm : 
     * Initialize current enrollments to 0
     *  Loop through num periods
     *     Loop through num students
     *        Set preference to student's choice
     *        (Walk the workshop periods looking for one with room
     *         that doesn't conflict with student's schedule)
     *        Loop through num periods
     *           Check if student can be assigned to workshop this period
     *           If they can be assigned 
     *             Fill in workshop for student for the appropriate span 
     *                 of time
     *              Set student's last scheduled choice to current choice
     *              Check for full schedule for this student
     *              Exit from the loop through the periods
     *    Increment current choice
     *    Check to see if all students' schedules are full
     *
	 */
	
	public void schedule(List<Student> studentList, List<Workshop> workshopList) {
		int currentChoice = 1;
		for(Student student : studentList) {
			while(currentChoice <= workshopList.size()) {
				int nextPreferedWorkshop = student.getWorkshopPreference().indexOf(currentChoice);
				if(nextPreferedWorkshop == 36 || nextPreferedWorkshop == -1) {
					currentChoice++;
					continue;
				}
				for(int p =0; p<Constants.MAX_PERIODS; p++) {
					boolean canBeAssigned = checkAvailablity(student, p, nextPreferedWorkshop);
					if(canBeAssigned) {
						scheduleStudent(student, p, nextPreferedWorkshop, workshopList.get(nextPreferedWorkshop).getLengthOfWorkshop(), currentChoice);
						break;
					}
				}
				currentChoice++;
			}
			currentChoice = 1;
		}
	}
	/*
	 * 
	 * Name : needtoRescheduleStudents
     * Authors : Deepa Krishna
     * Description : This procedure checks for each workshop in every period if there is 
     * a any current enrollment which exceeds wordkshop minimum workshop enrollment
     * if yes then it gets all the affected students and  and sets current enrollment for 
     * that period and that workshop as zero. 
     * 
     *
	 */
	
	public boolean needtoRescheduleStudents() {
		boolean needto = false;
		for(Workshop workshop : workshopList) {
			for(Period period : workshop.getPeriods()) {
				if(period.getCurrEnroll()!=0 && period.getCurrEnroll()<period.getMinEnroll()) {
					System.out.println(workshop.getOpr() + "  \t\t\t  " + period.getMinEnroll() +  "   \t\t\t  " + period.getCurrEnroll());
					needto = true;
					for(Student student : period.getScheduledStudents()) {
						for(int i =0; i<workshop.getLengthOfWorkshop();i++) {
							student.getScheduledWorkshops().put(period.getId()+i, "");
						}
					}
					period.getScheduledStudents().clear();
					period.setCurrEnroll(0);
				}
			}
		}
		
		/*if(!needto) {
			for(Student student : studentList) {
				if(student.getNoofWorkshopsInterestedIn()!=0) {
					int count = 0;
					for(Entry<Integer, String> wks : student.getScheduledWorkshops().entrySet()) {
						if(!wks.getValue().equals("")) {
							count++;
						}
					}
					if(count < student.getNoofWorkshopsInterestedIn()) {
						System.out.println(student.getName() + "     "+ student.getLastWorkshopChoiceScheduled());
						needto = true;
						break;
					}
				}
			}
		}*/
		
		return needto;
	}
	  /*
	   * Procedure : Reschedule_Workshops
	   * Author : Deepa Krishna
	   * Description : Checks each workshop/period to see if the enrollment
	   *    for that period meets minimum enrollment guidelines.  If not,
	   *    all cadets scheduled for that workshop/period are descheduled
	   *    from that workshop/period.  After doing this for all the 
	   *    workshops, the procedure reschedules the students.
	   * NOTE :  This is a fairly complicated procedure.  The main idea is
	   *    to remove students from workshop periods with low enrollments
	   *    and reschedule those students into their later choices.  Because
	   *    this process can cause new periods with low enrollments, we
	   *    need to keep doing this until all students are fully scheduled
	   *    and there are no low enrollment workshops (or students can no
	   *    longer be scheduled given their choices).
	   * IMPLEMENTATION COMMENT : In its current form, a student may end up
	   *    with a lower priority choice than necessary because we schedule
	   *    from their last scheduled choice rather than starting at their
	   *    first choice every time.  We do this to "close" a workshop/period
	   *    that the student has been removed from (closed to that student,
	   *    anyway), but we may miss an opportunity to schedule that student
	   *    in one of their higher priority choices that may have opened up
	   *    from other students being descheduled from it.  We do this to 
	   *    ensure our loop terminates (otherwise we could keep filling up
	   *    a low enrollment period with the same students, then removing 
	   *    them, then scheduling them for that period again, etc.).  
	   *    There's probably a more elegant way to do this, though.
	   * Algorithm :
	   *    Initialize Descheduled to true
	   *    While descheduled (we had at least one low enrollment period)
	   *       Check minimum enrollments
	   *       If descheduled (found at least one more low enrollment)
	   *          Reschedule students into workshops
	 */
	
	public void reschedule() {
		for(Student student : studentList) {
			int currentChoice = student.getLastWorkshopChoiceScheduled()+1;
			while(currentChoice <= workshopList.size()) {
				int nextPreferedWorkshop = student.getWorkshopPreference().indexOf(currentChoice);
				if(nextPreferedWorkshop == 36 || nextPreferedWorkshop == -1) {
					currentChoice++;
					continue;
				}
				for(int p =0; p<Constants.MAX_PERIODS; p++) {
					boolean canBeAssigned = checkAvailablity(student, p, nextPreferedWorkshop);
					if(canBeAssigned) {
						scheduleStudent(student, p, nextPreferedWorkshop, workshopList.get(nextPreferedWorkshop).getLengthOfWorkshop(), currentChoice);
						break;
					}
				}
				currentChoice++;
			}
		}
	}
	

	public static void main(String[] args){
		
		try {
			
			S5 s5 = new S5();
			
			Converter converter = new Converter();
			String pathToExcelFile = "/Users/deepakrishna/git/S5REENG/src/main/resources/S5.xlsx";
			String pathToStudentTextFile = "/Users/deepakrishna/git/S5REENG/src/main/resources/studentsA.txt";
			String pathToWorkshopTextFile = "/Users/deepakrishna/git/S5REENG/src/main/resources/workshopsA.txt";
			
			//convert txt student data to readable excel file
			converter.convertStudentData(pathToStudentTextFile, pathToExcelFile);
			//convert txt workshop data to readable excel file
			converter.convertWorkshopData(pathToWorkshopTextFile, pathToExcelFile);
			
			InitializeData iniData = new InitializeData(pathToExcelFile);
			
			System.out.println("************************************** Initializing Student Data *********************************************");
			s5.studentList = iniData.initializeStudents();
			
			/*for(Student student : studentList) {
				System.out.println("");
				for(int x : student.getWorkshopPreference()) {
					System.out.println(student.getName()+"\t"+x);
				}
			}*/
			
			System.out.println("************************************** Initializing Workshop Data *********************************************");
			s5.workshopList = iniData.initailizeWorkshops();
			
			System.out.println(s5.workshopList.size());
			
			/*for(Workshop workshop : s5.workshopList) {
				System.out.println("");
				for(Period period : workshop.getPeriods()) {
					System.out.println(workshop.getOpr() + "\t"+period.getId() +"\t"+ period.getMaxEnroll() +"\t"+ period.getMinEnroll());
				}
			}*/
			
			System.out.println("************************************** Random Student Order *********************************************");
			
			Collections.shuffle(s5.studentList);
			
			ExcelUtil myExcel = new ExcelUtil(pathToExcelFile);
			myExcel.writeToOrderSheet(s5.studentList);
			
			s5.schedule(s5.studentList, s5.workshopList);
			
			for(Workshop workshop : s5.workshopList) {
				System.out.println("\nWorkshop Name" + "\t"+ "Period" +"\t"+ "Max Enroll" +"\t"+ "Min Enroll" +"\t"+ "Curr Enroll");
				for(Period period : workshop.getPeriods()) {
					System.out.println(workshop.getOpr() + "\t\t"+period.getId() +"\t\t"+ period.getMaxEnroll() +"\t\t"+ period.getMinEnroll() +"\t\t"+ period.getCurrEnroll());
				}
			}
			
			System.out.println("*********************** Check for Reschedule Students *******************************************");
			System.out.println("\nWorkshop Name" + "\t"+ "\t"+ "Min Enroll" +"\t"+ "Curr Enroll");
			while(s5.needtoRescheduleStudents()) {
				s5.reschedule();
			}
			
			for(Workshop workshop : s5.workshopList) {
				System.out.println("\nWorkshop Name" + "\t"+ "Period" +"\t"+ "Max Enroll" +"\t"+ "Min Enroll" +"\t"+ "Curr Enroll");
				for(Period period : workshop.getPeriods()) {
					System.out.println(workshop.getOpr() + "\t\t"+period.getId() +"\t\t"+ period.getMaxEnroll() +"\t\t"+ period.getMinEnroll() +"\t\t"+ period.getCurrEnroll());
				}
			}
			
			for(Student student : s5.studentList) {
				System.out.println("\nStudent Name" + "\t"+ "Period" + " \t" + "Workshop Name");
				for(Entry<Integer,String> entrySet : student.getScheduledWorkshops().entrySet()) {
					System.out.println(student.getName()+"  \t  "+ entrySet.getKey() +  "  \t  " + entrySet.getValue());
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
