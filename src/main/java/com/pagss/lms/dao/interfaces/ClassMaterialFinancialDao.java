package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.MaterialFinance;

public interface ClassMaterialFinancialDao {

	public int countMaterialFinancialByClassId(int classId);

	public void deleteMaterialFinancials(int classId);

	public void addMaterialFinancials(List<MaterialFinance> materialFinancial);

	public List<MaterialFinance> fetchClassMaterialFinancials(int classId);

}
