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
@Table(name="classinfo")
public class ClassInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int classId;
	@Getter @Setter private String classCode;
	@Getter @Setter private int courseId;
	@Getter @Setter private int locationId;
	@Getter @Setter private String className;
	@Getter @Setter private int isSelfRegister;
	@Getter @Setter private int selfRegisterType;
	@Getter @Setter private String selfRegisterStartDate;
	@Getter @Setter private String selfRegisterEndDate;
	@Getter @Setter private int withCertificate;
	@Getter @Setter private int certificateTemplateType;
	@Getter @Setter private String certificateUrl;
	@Getter @Setter private int isCertificateDownloadable;
	@Getter @Setter private int minAttendee;
	@Getter @Setter private int maxAttendee;
	@Getter @Setter private int withExam;
	@Getter @Setter private int scheduleType;
	@Getter @Setter private int isEvaluationRequired;
	@Getter @Setter private int isAnonymousSender;
	@Getter @Setter private int completionStatus;
	@Getter @Setter private int viewRestrictionType;
	@Getter @Setter private int accessRestrictionType;
	@Getter @Setter private String accessStartDate;
	@Getter @Setter private String accessEndDate;
	@Getter @Setter private int classDuration;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	
	@Transient @Getter @Setter private String fullName;
	@Transient @Getter @Setter private String courseName;
	@Transient @Getter @Setter private String courseCode;
	@Transient @Getter @Setter private int deliveryMethod;
	@Transient @Getter @Setter private String startDate;
	@Transient @Getter @Setter private String endDate;
	@Transient @Getter @Setter private int employeeId;
	@Transient @Getter @Setter private String classPhotoUrl;
	@Transient @Getter @Setter private int trainingStatus;
	@Transient @Getter @Setter private int totalEnrolledTrainees;
	@Transient @Getter @Setter private String categoryName;
	@Transient @Getter @Setter private String trainerName;
	@Transient @Getter @Setter private String locationName;
	@Transient @Getter @Setter private String objective;
	@Transient @Getter @Setter private String description;
	@Transient @Getter @Setter private ClassBlockSchedule classBlockSchedule;
	@Transient @Getter @Setter private List<ClassSetSchedule> classSetSchedules;
	@Transient @Getter @Setter private List<ClassSeriesSchedule> classSeriesSchedules;
	@Transient @Getter @Setter private List<ClassEmployee> classEmployees;
	@Transient @Getter @Setter private ClassEmployee classEmployee;
}