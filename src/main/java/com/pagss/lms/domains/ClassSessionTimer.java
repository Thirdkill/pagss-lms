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
@Table(name="classsessiontimer")
public class ClassSessionTimer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int classSessionTimerId;
	@Getter @Setter private int classId;
	@Getter @Setter private int employeeId;
	@Getter @Setter private String timeStarted;
	@Getter @Setter private String timeEnded;
	@Getter @Setter private int timeSpent;
	@Getter @Setter private int sessionStatus;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
