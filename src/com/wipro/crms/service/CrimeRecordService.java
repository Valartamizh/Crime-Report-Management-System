package com.wipro.crms.service;

import java.util.ArrayList;
import com.wipro.crms.entity.*;
import com.wipro.crms.util.*;

public class CrimeRecordService {
	ArrayList<CrimeCase> cases;
	ArrayList<Suspect> suspects;
	ArrayList<InvestigationUpdate> updates;
	
	public void addInvestigationUpdate(InvestigationUpdate update) throws CaseNotFoundException, InvalidCrimeOperationException {
		int flag = 0;
		for(int i=0;i<updates.size();i++) {
			if(update.getCaseId() == updates.get(i).getCaseId() && update.getDescription().length() > updates.get(i).getDescription().length()) {
				flag=1;
				break;
			}
			
		}
		if (flag == 0){
			throw new CaseNotFoundException("Check!, There is no Existence of the Crime Case");
		}
	}

	public ArrayList<Suspect> getCaseSuspects(String caseId) throws CaseNotFoundException {
		ArrayList<Suspect> temp=new ArrayList<Suspect>();
		for(int i=0;i<suspects.size();i++) {
			if(caseId == cases.get(i).getCaseID()) {
				temp.add(suspects.get(i));
			}
		}
		if(temp.size()>0) return temp;
		throw new CaseNotFoundException("Check!, There is no Existence of the Crime Case");
	}

	public ArrayList<InvestigationUpdate> getCaseUpdates(String caseId) throws CaseNotFoundException {
		ArrayList<InvestigationUpdate> sus=new ArrayList<InvestigationUpdate>();
		for(int i=0;i<updates.size();i++) {
			if(caseId == updates.get(i).getCaseId()) {
				sus.add(updates.get(i));
			}
		}
		if(sus.size()>0) return sus;
		throw new CaseNotFoundException("Check!, There is no Existence of the Crime Case");

	}

	public String generateCaseSummary(String caseId) {
		String summery="";
		for(int i=0;i<updates.size();i++) {
			if(caseId == updates.get(i).getCaseId() && caseId == cases.get(i).getCaseID()) {
				summery += cases.get(i).toString() + " " + updates.get(i).toString() + " " + suspects.get(i).toString();
			}
		}
		return summery;

	}

}
