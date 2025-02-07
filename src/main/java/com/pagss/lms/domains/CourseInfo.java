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
@Table(name="courseinfo")
public class CourseInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int courseId;
	@Getter @Setter private int categoryId;
	@Getter @Setter private String courseCode;
	@Getter @Setter private String courseName;
	@Getter @Setter private String description;
	@Getter @Setter private String objective;
	@Getter @Setter private int deliveryMethod;
	@Getter @Setter private int passingGrade;
	@Getter @Setter private int status;
	@Getter @Setter private String modifiedBy;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
	
	@Transient @Getter @Setter private String categoryName;
	@Transient @Getter @Setter private int isSelected; //Used In Search Table with multiple selection
	@Transient @Getter @Setter private int assignmentType;
	@Transient @Getter @Setter private int jobRoleId;
	@Transient @Getter @Setter private int userGroupId;
	@Transient @Getter @Setter private int employeeId;
	@Transient @Getter @Setter private int locationId;
	@Transient @Getter @Setter private int isSelfRegister;
	@Transient @Getter @Setter private int withCertificate;
	@Transient @Getter @Setter private int minAttendee;
	@Transient @Getter @Setter private int maxAttendee;
	@Transient @Getter @Setter private String scheduleType;
	@Transient @Getter @Setter private int withExam;
	@Transient @Getter @Setter private String classPhotoUrl;
	@Transient @Getter @Setter private String photoFileName;
	@Transient @Getter @Setter private int classDefaultId;
	
	@Transient @Getter @Setter List<CourseMaterial> courseMaterials;
	@Transient @Getter @Setter List<CourseExam> courseExams;
	@Transient @Getter @Setter List<LearningPathSection> learningPathSections;
	@Transient @Getter @Setter List<LearningPath> learningPaths;
}
