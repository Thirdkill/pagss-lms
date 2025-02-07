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
@Table(name="classseriesparameter")
public class ClassSeriesParameter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int parameterId;
	@Getter @Setter private int classId;
	@Getter @Setter private int recurrencePattern;
	@Getter @Setter private int noOfOccurrence;
	@Getter @Setter private int day;
	@Getter @Setter private int week;
	@Getter @Setter private int monthOffset;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
