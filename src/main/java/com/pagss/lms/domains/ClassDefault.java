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
@Table(name="classdefault")
public class ClassDefault {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int classDefaultId;
	@Getter @Setter private int courseId;
	@Getter @Setter private int employeeId;
	@Getter @Setter private int locationId;
	@Getter @Setter private int isSelfRegister;
	@Getter @Setter private int withCertificate;
	@Getter @Setter private int minAttendee;
	@Getter @Setter private int maxAttendee;
	@Getter @Setter private int withExam;
	@Getter @Setter private int scheduleType;
	@Getter @Setter private String classPhotoUrl;
	@Getter @Setter private String photoFileName;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
