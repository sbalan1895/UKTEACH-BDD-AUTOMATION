package com.pomjobsearch;

public class JobInfo {

	public final String JobTitle, JobSchoolName, Joblocation;
	
	public JobInfo(String JobTitle, String JobSchoolName, String Joblocation) {
		
		this.JobTitle = JobTitle;
		this.JobSchoolName = JobSchoolName;
		this.Joblocation = Joblocation;
	}
	
	@Override
	public String toString() {
		return "Title: " + JobTitle + " | School: " + JobSchoolName + " | Location: " + Joblocation;
	}

}
