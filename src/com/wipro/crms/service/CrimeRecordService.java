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
		this.caseSuspects = new ArrayList<>();
	}

	public void addCrimeCase(CrimeCase crime) throws InvalidCrimeOperationException {

		if (crime.getDescription() == null)
			throw new InvalidCrimeOperationException("Enter Description to Add Crime Case");
		for (CrimeCase c : cases) {
			if (c.getCaseId().equals(crime.getCaseId()))
				throw new InvalidCrimeOperationException("Case Id Already Exist, Enter a New Unique Case Id");
		}
		int flag = 0;
		for (String s : checkStatus) {
			if (crime.getStatus().equalsIgnoreCase(s)) {
				flag = 1;
				break;
			}
		}
		if (flag == 0)
			throw new InvalidCrimeOperationException("Invalid Status");

		cases.add(crime);
	}

	public CrimeCase findCrimeCase(String caseId) throws CaseNotFoundException {
		for (CrimeCase c : cases) {
			if (c.getCaseId().equals(caseId)) {
				return c;
			}
		}
		throw new CaseNotFoundException("Case Not Found, Enter a Valid Case Id");
	}

	public void addSuspectToCase(String caseId, Suspect suspect)
			throws CaseNotFoundException, InvalidCrimeOperationException {

		int flag = 0;

		for (CrimeCase c : cases) {
			if (c.getCaseId().equals(caseId)) {
				flag = 1;
				CaseSuspectsDetails csd = new CaseSuspectsDetails(caseId, suspect.getSuspectId());
				caseSuspects.add(csd);
				break;
			}
		}

		if (flag == 0)
			throw new CaseNotFoundException("Case Not Found, Enter a Valid Case Id");

		flag = 0;
		for (Suspect s : suspects) {
			if (s.getSuspectId().equals(suspect.getSuspectId())) {
				flag = 1;
				break;
			}
		}
		if (flag == 0)
			suspects.add(suspect);
	}

	public void updateCaseStatus(String caseId, String newStatus)
			throws CaseNotFoundException, InvalidCrimeOperationException {

		int flag = 0;

		for (CrimeCase c : cases) {
			if (c.getCaseId().equals(caseId)) {
				flag = 1;

				switch (newStatus.toUpperCase()) {
				case "OPEN":
				case "UNDER INVESTIGATION":
				case "CLOSED":
					c.setStatus(newStatus.toUpperCase());
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

	public void addInvestigationUpdate(InvestigationUpdate update)
	        throws CaseNotFoundException, InvalidCrimeOperationException {

	    int flag = 0;
	    for (CrimeCase c : cases) {
	        if (c.getCaseId().equals(update.getCaseId())) {
	            flag = 1;
	            break;
	        }
	    }

	    if (flag == 0)
	        throw new CaseNotFoundException("Case Not Found!");

	    flag = 0;
	    for (int i = 0; i < updates.size(); i++) {
	        if (updates.get(i).getCaseId().equals(update.getCaseId())) {

	            if (update.getDescription().length() > updates.get(i).getDescription().length()) {
	                updates.set(i, update);
	            }
	            return; 
	        }
	    }
	    updates.add(update);
	}

	public ArrayList<Suspect> getCaseSuspects(String caseId) throws CaseNotFoundException {

		ArrayList<Suspect> temp = new ArrayList<>();

		for (CaseSuspectsDetails cs : caseSuspects) {
			if (cs.getCaseId().equals(caseId)) {
				for (Suspect s : suspects) {
					if (s.getSuspectId().equals(cs.getSuspectId()))
						temp.add(s);
				}
			}
		}

		if (temp.size() > 0)
			return temp;

		throw new CaseNotFoundException("Check!, There is no Existence of the Crime Case");
	}

	public ArrayList<InvestigationUpdate> getCaseUpdates(String caseId) throws CaseNotFoundException {

		ArrayList<InvestigationUpdate> temp = new ArrayList<>();

		for (InvestigationUpdate u : updates) {
			if (u.getCaseId().equals(caseId)) {
				temp.add(u);
			}
		}

		if (temp.size() > 0)
			return temp;

		throw new CaseNotFoundException("Check!, There is no Existence of the Crime Case");
	}

	public String generateCaseSummary(String caseId) {

		String summary = "";

		for (CrimeCase c : cases) {
			if (c.getCaseId().equals(caseId)) {

				summary += c.toString() + "\n";

				for (InvestigationUpdate u : updates) {
					if (u.getCaseId().equals(caseId)) {
						summary += u.toString() + "\n";
					}
				}

				for (CaseSuspectsDetails cs : caseSuspects) {
					if (cs.getCaseId().equals(caseId)) {
						for (Suspect s : suspects) {
							if (s.getSuspectId().equals(cs.getSuspectId())) {
								summary += s.toString() + "\n";
							}
						}
					}
				}
			}
		}

		return summary;
	}

}
