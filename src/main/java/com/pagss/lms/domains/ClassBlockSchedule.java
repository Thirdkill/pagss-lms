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
@Table(name="classblockschedule")
public class ClassBlockSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int classBlockId;
	@Getter @Setter private int classId;
	@Getter @Setter private String startDate;
	@Getter @Setter private String endDate;
	@Getter @Setter private String startTime;
	@Getter @Setter private String endTime;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	
	@Transient @Getter @Setter private int employeeId;
	
}
