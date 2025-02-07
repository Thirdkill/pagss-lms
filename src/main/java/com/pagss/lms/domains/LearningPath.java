package com.pagss.lms.domains;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="learningpath")
public class LearningPath {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int learningPathId;
	@Getter @Setter private int courseMaterialId;
	@Getter @Setter private int courseExamId;
	@Getter @Setter private int learningPathSectionId;
	@Getter @Setter private String itemType;
	@Getter @Setter private int subOrderNo;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	
	@Transient @Getter @Setter private int courseId;
	@Transient @Getter @Setter private String fileLabel;
	@Transient @Getter @Setter private int contentType;
	@Transient @Getter @Setter private String title;
	@Transient @Getter @Setter private int examType;
	@Transient @Getter @Setter private int maxOrderNo;
	@Transient @Getter @Setter private String contentUrl;
	@Transient @Getter @Setter private String lastAccessed;
	@Transient @Getter @Setter private String timeStarted;
	@Transient @Getter @Setter private String timeEnded;
	@Transient @Getter @Setter private int timeSpent;
	@Transient @Getter @Setter private int sessionStatus;
	@Transient @Getter @Setter private BigDecimal passingScore;
	@Transient @Getter @Setter private int duration;
	@Transient @Getter @Setter private int totalItems;
	@Transient @Getter @Setter private int noOfRetake;
}
