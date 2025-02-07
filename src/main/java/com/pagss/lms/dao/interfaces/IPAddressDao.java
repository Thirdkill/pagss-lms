package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.domains.IpAddress;

public interface IPAddressDao {

	public List<IpAddress> fetchIpAddress(int pageSize, int pageNo);

	public int countTotalIpAddresses();
	
	public int countSearchedIpAddresses(String keyword);

	public List<IpAddress> searchIpAddress(String keyword, int pageSize, int pageNo);

	public int countIpAddress(IpAddress ipaddress);

	public List<IpAddress> fetchIpAddressOnClass(int classId, int pageSize, int calculatedPageNo);

	public int countTotalIpAddressOnClass(int classId);
}
