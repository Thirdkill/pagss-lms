package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.IpAddress;

import lombok.Getter;
import lombok.Setter;

public class IpAddressResponse {
	@Getter @Setter private int status;
	@Getter @Setter private IpAddress ipaddress;
	@Getter @Setter private List<IpAddress> ipaddresses;
	@Getter @Setter private int totalRecords;
	
	public IpAddressResponse(int status) {
		setStatus(status);
	}
	
	public IpAddressResponse(int status, int totalRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
	}
	
	public IpAddressResponse(int status,IpAddress ipaddress) {
		setStatus(status);
		setIpaddress(ipaddress);
	}

	public IpAddressResponse(int status,List<IpAddress> ipaddresses, int totalRecords) {
		setStatus(status);
		setIpaddresses(ipaddresses);
		setTotalRecords(totalRecords);
	}
}
