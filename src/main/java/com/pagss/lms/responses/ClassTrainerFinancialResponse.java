package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.TrainerFinance;

import lombok.Getter;
import lombok.Setter;

public class ClassTrainerFinancialResponse {
	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private TrainerFinance trainerFinance;
	@Getter @Setter private List<TrainerFinance> trainerFinances;
	
	public ClassTrainerFinancialResponse (int status) {
		setStatus(status);
	}

	public ClassTrainerFinancialResponse (int status, TrainerFinance trainerFinance) {
		setStatus(status);
		setTrainerFinance(trainerFinance);
	}
	
	public ClassTrainerFinancialResponse (int status, List<TrainerFinance> trainerFinances) {
		setStatus(status);
		setTrainerFinances(trainerFinances);
	}
	
	public ClassTrainerFinancialResponse (int status, int totalRecords, List<TrainerFinance> trainerFinances) {
		setStatus(status);
		setTotalRecords(totalRecords);
		setTrainerFinances(trainerFinances);
	}
}
