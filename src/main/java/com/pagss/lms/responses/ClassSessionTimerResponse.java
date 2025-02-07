package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.ClassSessionTimer;

import lombok.Getter;
import lombok.Setter;

public class ClassSessionTimerResponse {

	@Getter @Setter private int status;
	@Getter @Setter private int sessionStatus;
	@Getter @Setter private boolean result;
	@Getter @Setter private List<ClassSessionTimer> classSessionTimers;
	@Getter @Setter private ClassSessionTimer classSessionTimer;
	
	public ClassSessionTimerResponse(int status) {
		setStatus(status);
	}
	
	public ClassSessionTimerResponse(int status,boolean result) {
		setStatus(status);
		setResult(result);
	}
	
	public ClassSessionTimerResponse(int status,int sessionStatus,boolean result) {
		setStatus(status);
		setResult(result);
		setSessionStatus(sessionStatus);
	}
}
