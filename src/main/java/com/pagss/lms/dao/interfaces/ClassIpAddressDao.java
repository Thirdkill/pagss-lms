package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.ClassIpAddress;

public interface ClassIpAddressDao {

	public List<ClassIpAddress> fetchClassIpAddressList(int classId, int calculatedPageNo, int pageSize);

	public int countTotalClassIpAddress(int classId);

	public void addClassIpAddresses(List<ClassIpAddress> classIpAddresses);

	public void deleteClassIpAddress(int classIpId);

}
