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
@Table(name="learningpathsection")
public class LearningPathSection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int learningPathSectionId;
	@Getter @Setter private int courseId;
	@Getter @Setter private String sectionTitle;
	@Getter @Setter private int sectionOrderNo;
	@Getter @Setter private String description;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	
	@Transient @Getter @Setter private int maxSectionOrderNo;
}
