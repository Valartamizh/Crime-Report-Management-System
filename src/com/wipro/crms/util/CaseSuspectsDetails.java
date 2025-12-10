package com.wipro.crms.util;

public class CaseSuspectsDetails{
	private String caseId;
	private String suspectId;
	
	public CaseSuspectsDetails(String caseId, String suspectId) {
		super();
		this.caseId = caseId;
		this.suspectId = suspectId;
	}
	public String getCaseId() {
		return caseId;
	}
	public String getSuspectId() {
		return suspectId;
	}
	public void setSuspectId(String suspectId) {
		this.suspectId = suspectId;
	}
	
}
