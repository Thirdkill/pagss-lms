package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.ClassIpAddressDao;
import com.pagss.lms.domains.ClassIpAddress;
import com.pagss.lms.manager.interfaces.ClassIpAddressManager;

@Component
public class LmsClassIpAddressManager implements ClassIpAddressManager {
	
	@Autowired
	private ClassIpAddressDao classIpAddressDao;

	@Override
	public List<ClassIpAddress> fetchClassIpAddressList(int classId, int pageNumber, int pageSize) {
		int calculatedPageNo = pageSize * (pageNumber - 1);
		return this.classIpAddressDao.fetchClassIpAddressList(classId,calculatedPageNo,pageSize);
	}

	@Override
	public int countTotalClassIpAddress(int classId) {
		return this.classIpAddressDao.countTotalClassIpAddress(classId);
	}

	@Override
	public void addClassIpAddresses(List<ClassIpAddress> classIpAddresses) {
		this.classIpAddressDao.addClassIpAddresses(classIpAddresses);
	}

	@Override
	public void deleteClassIpAddress(int classIpId) {
		this.classIpAddressDao.deleteClassIpAddress(classIpId);
	}
	
	
}
