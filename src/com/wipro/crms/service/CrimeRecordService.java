package com.wipro.crms.service;

import com.wipro.crms.entity.*;
import com.wipro.crms.util.*;
import java.util.*;

public class CrimeRecordService {

	ArrayList<CrimeCase> cases;
	ArrayList<Suspect> suspects;
	ArrayList<InvestigationUpdate> updates;
	ArrayList<CaseSuspectsDetails> caseSuspects;

	String[] checkStatus = { "OPEN", "UNDER INVESTIGATION", "CLOSED" };

	public CrimeRecordService(ArrayList<CrimeCase> cases, ArrayList<Suspect> suspects,
			ArrayList<InvestigationUpdate> updates) {
		super();
		this.cases = cases;
		this.suspects = suspects;
		this.updates = updates;
	}

	public void addCrimeCase(CrimeCase crime) throws InvalidCrimeOperationException {
		if (crime.getDescription() == null)
			throw new InvalidCrimeOperationException("Enter Decsription to Add Crime Case");
		int n = cases.size();
		for (int i = 0; i < n; i++) {
			if (cases.get(i).getCaseID() == crime.getCaseID())
				throw new InvalidCrimeOperationException("Case Id Already Exist, Enter a New Unique Case Id");
		}
		n = checkStatus.length;
		int flag = 0;
		for (int i = 0; i < n; i++) {
			if (crime.getCaseID().equalsIgnoreCase(checkStatus[i])) {
				flag = 1;
				break;
			}
		}
		if (flag == 0)
			throw new InvalidCrimeOperationException("Invalid Status");
		cases.add(crime);
	}

	public CrimeCase findCrimeCase(String caseId) throws CaseNotFoundException {
		int n = cases.size();
		for (int i = 0; i < n; i++) {
			if (cases.get(i).getCaseID() == caseId) {
				return cases.get(i);
			}
		}
		throw new CaseNotFoundException("Case Not Found, Enter a Valid Case Id");
	}

	public void addSuspectToCase(String caseId, Suspect suspect)
			throws CaseNotFoundException, InvalidCrimeOperationException {
		int n = cases.size();
		int flag = 0;
		String suspect1 = suspect.getSuspectId();

		for (int i = 0; i < n; i++) {
			if (cases.get(i).getCaseID() == caseId) {
				flag = 1;
				CaseSuspectsDetails csd = new CaseSuspectsDetails(caseId, suspect1);
				caseSuspects.add(csd);
				break;
			}

		}
		if (flag == 0)
			throw new CaseNotFoundException("Case Not Found, Enter a Valid Case Id");

		n = suspects.size();
		flag = 0;
		for (int i = 0; i < n; i++) {
			if (suspects.get(i).getSuspectId() == suspect1) {
				flag = 1;
				break;
			}
		}
		if(flag == 0) {
			suspects.add(suspect);
		}
	}

	public void updateCaseStatus(String caseId, String newStatus)
			throws CaseNotFoundException, InvalidCrimeOperationException {
		int n = cases.size();
		int flag = 0;
		for (int i = 0; i < n; i++) {
			if (cases.get(i).getCaseID() == caseId) {
				flag = 1;
				switch (newStatus.toUpperCase()) {
				case "OPEN":
					cases.get(i).setStatus("OPEN");
					break;
				case "UNDER INVESTIGATION":
					cases.get(i).setStatus("UNDER INVESTIGATION");
					break;
				case "CLOSED":
					cases.get(i).setStatus("CLOSED");
					break;
				default:
					throw new InvalidCrimeOperationException(
							"Invalid Status, Enter a Valid Status -> \"OPEN\", \"UNDER INVESTIGATION\",\"CLOSED\"");
				}
				break;
			}
		}
		if (flag == 0)
			throw new CaseNotFoundException("Case Not Found, Enter a Valid Case Id");

	}
}
