package edu.uccs.sm.s5.model;

import java.util.ArrayList;
import java.util.List;

public class Period {
	
	private int id;
	private int maxEnroll;
	private int minEnroll;
	private int currEnroll;
	private List<Student> scheduledStudents;
	
	public Period() {
		scheduledStudents = new ArrayList<Student>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMaxEnroll() {
		return maxEnroll;
	}
	public void setMaxEnroll(int maxEnroll) {
		this.maxEnroll = maxEnroll;
	}
	public int getMinEnroll() {
		return minEnroll;
	}
	public void setMinEnroll(int minEnroll) {
		this.minEnroll = minEnroll;
	}
	public int getCurrEnroll() {
		return currEnroll;
	}
	public void setCurrEnroll(int currEnroll) {
		this.currEnroll = currEnroll;
	}
	public List<Student> getScheduledStudents() {
		return scheduledStudents;
	}
	public void setScheduledStudents(List<Student> scheduledStudents) {
		this.scheduledStudents = scheduledStudents;
	}
	

}
