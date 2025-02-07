package com.pagss.lms.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="fillintheblanksanswer")
public class FillInTheBlanksAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int fillInTheBlanksAnswerId;
	@Getter @Setter private int questionId;
	@Getter @Setter private int choiceType;
	@Getter @Setter private String componentCode;
	@Getter @Setter private String answer;
	@Getter @Setter private int isMatchCase;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}