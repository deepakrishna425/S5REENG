package edu.uccs.sm.s5.model;

import java.util.List;

public class Workshop {
	
	private String opr;
	private String location;
	private int lengthOfWorkshop;
	private List<Period> periods;
	
	public String getOpr() {
		return opr;
	}
	public void setOpr(String opr) {
		this.opr = opr;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getLengthOfWorkshop() {
		return lengthOfWorkshop;
	}
	public void setLengthOfWorkshop(int lengthOfWorkshop) {
		this.lengthOfWorkshop = lengthOfWorkshop;
	}
	public List<Period> getPeriods() {
		return periods;
	}
	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}
	

}
