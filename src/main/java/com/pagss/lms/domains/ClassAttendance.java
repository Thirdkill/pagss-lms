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
@Table(name="classattendance")
public class ClassAttendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int attendanceId;
	@Getter @Setter private int classId;
	@Getter @Setter private String dateAccessed;
	@Getter @Setter private int timeSpent;
	@Getter @Setter private String remarks;
	@Getter @Setter private int attendanceStatus;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
