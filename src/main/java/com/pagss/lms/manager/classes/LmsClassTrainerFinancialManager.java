package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.ClassTrainerFinancialDao;
import com.pagss.lms.domains.TrainerFinance;
import com.pagss.lms.manager.interfaces.ClassTrainerFinancialManager;

@Component
public class LmsClassTrainerFinancialManager implements ClassTrainerFinancialManager {

	@Autowired
	private ClassTrainerFinancialDao classTrainerFinancialDao;

	@Override
	public void updateClassTrainerFinancials(int classId, List<TrainerFinance> trainerFinancials) {
		int financialCount = this.classTrainerFinancialDao.countTrainerFinancialByClassId(classId);
		
		if(financialCount > 0) {
			this.classTrainerFinancialDao.deleteTrainerFinancials(classId);
			this.classTrainerFinancialDao.addTrainerFinancials(trainerFinancials);
		} else if(financialCount == 0) {
			this.classTrainerFinancialDao.addTrainerFinancials(trainerFinancials);
		}
	}

	@Override
	public List<TrainerFinance> fetchClassTrainerFinancials(int classId) {
		return this.classTrainerFinancialDao.fetchClassTrainerFinancials(classId);
	}
	
}
