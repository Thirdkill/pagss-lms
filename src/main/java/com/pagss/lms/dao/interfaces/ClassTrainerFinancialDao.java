package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.TrainerFinance;

public interface ClassTrainerFinancialDao {

	public int countTrainerFinancialByClassId(int classId);

	public void deleteTrainerFinancials(int classId);

	public void addTrainerFinancials(List<TrainerFinance> trainerFinancials);

	public List<TrainerFinance> fetchClassTrainerFinancials(int classId);

}
