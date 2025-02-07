package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.OtherFinance;

public interface ClassOtherFinancialDao {

	int countOtherFinancialByClassId(int classId);

	void deleteOtherFinancials(int classId);

	void addOtherFinancials(List<OtherFinance> otherFinancials);

	List<OtherFinance> fetchClassOtherFinancials(int classId);

}
