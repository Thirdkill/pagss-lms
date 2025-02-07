package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.EnumerationAnswer;

import lombok.Getter;
import lombok.Setter;

public class EnumerationAnswerResponse {

	@Getter @Setter private int status;
	@Getter @Setter private EnumerationAnswer enumerationAnswer;
	@Getter @Setter private List<EnumerationAnswer> enumerationAnswers;
	
	public EnumerationAnswerResponse(int status) {
		setStatus(status);
	}
	
	public EnumerationAnswerResponse(int status,EnumerationAnswer enumerationAnswer) {
		setStatus(status);
		setEnumerationAnswer(enumerationAnswer);
	}
	
	public EnumerationAnswerResponse(int status,List<EnumerationAnswer> enumerationAnswers) {
		setStatus(status);
		setEnumerationAnswers(enumerationAnswers);
	}
}
