package com.pagss.lms.commands;

import lombok.Getter;
import lombok.Setter;

/**
 * Description: General Command for table related actionsm like sorting or searching
 * @author Gerald
 *
 */
public class TableCommand {
	
	@Getter @Setter private int pageSize;
	@Getter @Setter private int pageNumber;
	@Getter @Setter private String sortDirection;
	@Getter @Setter private String keyword;
	@Getter @Setter private String sortColumnName;	
	@Getter @Setter private String selectClause;
	@Getter @Setter private String whereClause;
	@Getter @Setter private String orderClause;
	@Getter @Setter private String updateSetClause;
	
	//Question Table Search Columns
	@Getter @Setter private int searchByQuestionType;
	@Getter @Setter private int searchByTopic;
	@Getter @Setter private int searchByDifficultyLevel;
	@Getter @Setter private int searchByStatus;
	
	//ExamInfo Table Search columns
	@Getter @Setter private int searchByExamStatus;
	@Getter @Setter private int searchByExamType;
	
	//Class info search columns
	@Getter @Setter private int searchByEmployeeId;
	@Getter @Setter private int searchInProgressClasses;
	@Getter @Setter private int searchUpcomingClasses;
	@Getter @Setter private int searchCompletedClasses;
	@Getter @Setter private int searchCancelledClasses;
	
	//Class Employee
	@Getter @Setter private int searchByApprovalStatus;
	@Getter @Setter private int searchByRole;
	
	@Getter @Setter private int courseId;
	@Getter @Setter private int deliveryMethod;
	@Getter @Setter private int jobRoleId;
	@Getter @Setter private int assignmentType;
	@Getter @Setter private int userGroupId;
	@Getter @Setter private int assignmentTypeId;
	@Getter @Setter private String startDate;
	@Getter @Setter private String endDate;
	@Getter @Setter private int classId;
	@Getter @Setter private int userId;
	
	//Class Settings Update Set Command
	
}
