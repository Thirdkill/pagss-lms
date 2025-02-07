package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.ClassDefault;

import lombok.Getter;
import lombok.Setter;

public class ClassDefaultResponse {
	@Getter @Setter private int status;
	@Getter @Setter private ClassDefault classdefault;
	@Getter @Setter private List<ClassDefault> classdefaults;
	
	public ClassDefaultResponse (int status) {
		setStatus(status);
	}
	public ClassDefaultResponse (int status, ClassDefault classdefault) {
		setStatus(status);
		setClassdefault(classdefault);
	}
	public ClassDefaultResponse (int status, List<ClassDefault> classdefaults) {
		setStatus(status);
		setClassdefaults(classdefaults);
	}
}
