package com.pagss.lms.domains;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="question")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int questionId;
	@Getter @Setter private int questionTypeId;
	@Getter @Setter private int topicId;
	@Getter @Setter private int difficultyId;
	@Getter @Setter private String label;
	@Getter @Setter private String content;
	@Getter @Setter private String answer;
	@Getter @Setter private int matchCase;
	@Getter @Setter private int ignoreOrder;
	@Getter @Setter private String mediaUrl;
	@Getter @Setter private String mediaFileName;
	@Getter @Setter private int status;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	
	@Transient @Getter @Setter private String topicDesc;
	@Transient @Getter @Setter private String difficultyName;
	@Transient @Getter @Setter private String questionTypeDesc;
	@Transient @Getter @Setter private List<Choice> choices;
	@Transient @Getter @Setter private int noOfQuestions;
	@Transient @Getter @Setter private List<EnumerationAnswer> enumerationAnswers;
	@Transient @Getter @Setter private List<AnalogyAnswer> analogyAnswers;
	@Transient @Getter @Setter private List<FillInTheBlanksAnswer> fillInTheBlanksAnswers;
	@Transient @Getter @Setter private List<OrderingAnswer> orderingAnswers;
}
