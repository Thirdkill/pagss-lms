package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.OtherFinance;

import lombok.Getter;
import lombok.Setter;

public class ClassOtherFinancialResponse {
	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private OtherFinance otherFinance;
	@Getter @Setter private List<OtherFinance> otherFinances;
	
	public ClassOtherFinancialResponse (int status) {
		setStatus(status);
	}

	public ClassOtherFinancialResponse (int status, OtherFinance otherFinance) {
		setStatus(status);
		setOtherFinance(otherFinance);
	}
	
	public ClassOtherFinancialResponse (int status, List<OtherFinance> otherFinances) {
		setStatus(status);
		setOtherFinances(otherFinances);
	}
	
	public ClassOtherFinancialResponse (int status, int totalRecords, List<OtherFinance> otherFinances) {
		setStatus(status);
		setTotalRecords(totalRecords);
		setOtherFinances(otherFinances);
	}
}
