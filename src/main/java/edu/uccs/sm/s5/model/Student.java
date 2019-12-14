package edu.uccs.sm.s5.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uccs.sm.s5.util.Constants;

public class Student {
	
	private String name;
	private String ssan;
	private int noofWorkshopsInterestedIn;
	private List<Integer> workshopPreference;
	private Map<Integer, String> scheduledWorkshops;
	private int lastWorkshopChoiceScheduled;
	
	public Student() {
		scheduledWorkshops = new HashMap<Integer, String>();
		for(int i =0; i<Constants.MAX_PERIODS;i++) {
			scheduledWorkshops.put(i, "");
		}
		noofWorkshopsInterestedIn = 0;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSsan() {
		return ssan;
	}
	public void setSsan(String ssan) {
		this.ssan = ssan;
	}
	public List<Integer> getWorkshopPreference() {
		return workshopPreference;
	}
	public void setWorkshopPreference(List<Integer> workshopPreference) {
		this.workshopPreference = workshopPreference;
	}
	public Map<Integer, String> getScheduledWorkshops() {
		return scheduledWorkshops;
	}
	public void setScheduledWorkshops(Map<Integer, String> scheduledWorkshops) {
		this.scheduledWorkshops = scheduledWorkshops;
	}

	public int getLastWorkshopChoiceScheduled() {
		return lastWorkshopChoiceScheduled;
	}

	public void setLastWorkshopChoiceScheduled(int lastWorkshopChoiceScheduled) {
		this.lastWorkshopChoiceScheduled = lastWorkshopChoiceScheduled;
	}

	public int getNoofWorkshopsInterestedIn() {
		return noofWorkshopsInterestedIn;
	}

	public void setNoofWorkshopsInterestedIn(int noofWorkshopsInterestedIn) {
		this.noofWorkshopsInterestedIn = noofWorkshopsInterestedIn;
	}


}
