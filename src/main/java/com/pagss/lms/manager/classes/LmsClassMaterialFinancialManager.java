package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.ClassMaterialFinancialDao;
import com.pagss.lms.domains.MaterialFinance;
import com.pagss.lms.manager.interfaces.ClassMaterialFinancialManager;

@Component
public class LmsClassMaterialFinancialManager implements ClassMaterialFinancialManager {
	
	@Autowired
	private ClassMaterialFinancialDao classMaterialFinancialDao;

	@Override
	public void updateClassMaterialFinancials(int classId, List<MaterialFinance> materialFinancial) {
		int financialCount = this.classMaterialFinancialDao.countMaterialFinancialByClassId(classId);
		
		if(financialCount > 0) {
			this.classMaterialFinancialDao.deleteMaterialFinancials(classId);
			this.classMaterialFinancialDao.addMaterialFinancials(materialFinancial);
		} else if(financialCount == 0) {
			this.classMaterialFinancialDao.addMaterialFinancials(materialFinancial);
		}
	}

	@Override
	public List<MaterialFinance> fetchClassMaterialFinancials(int classId) {
		return this.classMaterialFinancialDao.fetchClassMaterialFinancials(classId);
	}

}
