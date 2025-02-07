package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.ClassIpAddress;

import lombok.Getter;
import lombok.Setter;

public class ClassIpAddressResponse {
	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private ClassIpAddress classIpAddress;
	@Getter @Setter private List<ClassIpAddress> classIpAddresses;
	
	public ClassIpAddressResponse (int status) {
		setStatus(status);
	}
	
	public ClassIpAddressResponse (int status, int totalRecords) {
		setStatus(status);
		setTotalRecords(totalRecords);
	}
	
	public ClassIpAddressResponse (int status, ClassIpAddress classIpAddresse) {
		setStatus(status);
		setClassIpAddress(classIpAddresse);
	}

	public ClassIpAddressResponse (int status, List<ClassIpAddress> classIpAddresses) {
		setStatus(status);
		setClassIpAddresses(classIpAddresses);
	}
	
	public ClassIpAddressResponse (int status,int totalRecords, List<ClassIpAddress> classIpAddresses) {
		setStatus(status);
		setClassIpAddresses(classIpAddresses);
		setTotalRecords(totalRecords);
	}
}
