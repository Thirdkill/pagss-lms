package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.MaterialFinance;

import lombok.Getter;
import lombok.Setter;

public class ClassMaterialFinancialResponse {
	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private MaterialFinance materialFinance;
	@Getter @Setter private List<MaterialFinance> materialFinances;
	
	public ClassMaterialFinancialResponse (int status) {
		setStatus(status);
	}

	public ClassMaterialFinancialResponse (int status, MaterialFinance materialFinance) {
		setStatus(status);
		setMaterialFinance(materialFinance);
	}
	
	public ClassMaterialFinancialResponse (int status, List<MaterialFinance> materialFinances) {
		setStatus(status);
		setMaterialFinances(materialFinances);
	}
	
	public ClassMaterialFinancialResponse (int status, int totalRecords, List<MaterialFinance> trainerFinances) {
		setStatus(status);
		setTotalRecords(totalRecords);
		setMaterialFinances(materialFinances);
	}
}
