package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.ClassOtherFinancialDao;
import com.pagss.lms.domains.OtherFinance;
import com.pagss.lms.manager.interfaces.ClassOtherFinancialManager;

@Component
public class LmsClassOtherFinancialManager implements ClassOtherFinancialManager {
	
	@Autowired
	private ClassOtherFinancialDao classOtherFinancialDao;

	@Override
	public void updateClassOtherFinancials(int classId, List<OtherFinance> otherFinancials) {
		int financialCount = this.classOtherFinancialDao.countOtherFinancialByClassId(classId);
		
		if(financialCount > 0) {
			this.classOtherFinancialDao.deleteOtherFinancials(classId);
			this.classOtherFinancialDao.addOtherFinancials(otherFinancials);
		} else if(financialCount == 0) {
			this.classOtherFinancialDao.addOtherFinancials(otherFinancials);
		}
	}

	@Override
	public List<OtherFinance> fetchClassOtherFinancials(int classId) {
		return this.classOtherFinancialDao.fetchClassOtherFinancials(classId);
	}

}
