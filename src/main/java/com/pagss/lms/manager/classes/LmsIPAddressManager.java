package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.IPAddressDao;
import com.pagss.lms.domains.IpAddress;
import com.pagss.lms.manager.interfaces.IPAddressManager;

@Component
public class LmsIPAddressManager implements IPAddressManager{
	
	private IPAddressDao ipAddressDao;

	/*******************************************************************************************************
	 * Start: Autowired Setters	
	 ******************************************************************************************************/
	@Autowired
	public void setIPAddressManager(IPAddressDao ipAddressDao) {
		this.ipAddressDao = ipAddressDao;
	}
	/*******************************************************************************************************
	 * End: Autowired Setters	
	 ******************************************************************************************************/
	@Override
	public List<IpAddress> fetchIpAddress(int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.ipAddressDao.fetchIpAddress(pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalIpAddresses() {
		return this.ipAddressDao.countTotalIpAddresses();
	}
	
	@Override
	public List<IpAddress> searchIpAddress(String keyword, int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.ipAddressDao.searchIpAddress(keyword, pageSize, calculatedPageNo);
	}

	@Override
	public int countSearchedIpAddresses(String keyword) {
		return this.ipAddressDao.countSearchedIpAddresses(keyword);
	}
	@Override
	public int countIpAddress(IpAddress ipaddress) {
		return this.ipAddressDao.countIpAddress(ipaddress);
	}
	@Override
	public List<IpAddress> fetchIpAddressOnClass(int classId,int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.ipAddressDao.fetchIpAddressOnClass(classId,pageSize, calculatedPageNo);
	}
	@Override
	public int countTotalIpAddressOnClass(int classId) {
		return this.ipAddressDao.countTotalIpAddressOnClass(classId);
	}
}
