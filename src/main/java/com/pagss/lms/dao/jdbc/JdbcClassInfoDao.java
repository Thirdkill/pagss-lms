package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.constants.LmsClassEmployeeData;
import com.pagss.lms.constants.LmsClassInfoData;
import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.ClassInfoDao;
import com.pagss.lms.domains.ClassInfo;
import com.pagss.lms.domains.ClassSeriesSchedule;
import com.pagss.lms.domains.ClassBlockSchedule;
import com.pagss.lms.domains.ClassSetSchedule;

@Repository
public class JdbcClassInfoDao extends JdbcDao implements ClassInfoDao {
	
	@Override
	public int fetchLatestClassId() {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
		.append("SELECT (IFNULL(MAX(ci.classId),0)+1) AS classId ")
		.append("FROM classinfo ci  ")
		.toString(),
		new MapSqlParameterSource(), 
		Integer.class);
	}

	@Override
	public int checkClassCode(ClassInfo clInfo) {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
		.append("SELECT COUNT(*) FROM classinfo ci ")
		.append("WHERE ci.classCode = :classCode ").toString(),
		new MapSqlParameterSource()
		.addValue("classCode",clInfo.getClassCode()),
		Integer.class);
	}

	@Override
	public void addClassInfo(ClassInfo classinfo) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO classinfo ")
				.append("(classCode, courseId, locationId, className, isSelfRegister, ")
				.append("withCertificate, minAttendee, maxAttendee, withExam, scheduleType) ")
				.append("VALUES (:classCode, :courseId, :locationId, :className, :isSelfRegister, ")
				.append(":withCertificate, :minAttendee, :maxAttendee, :withExam, :scheduleType) ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classCode",classinfo.getClassCode())
				.addValue("courseId",classinfo.getCourseId())
				.addValue("locationId",classinfo.getLocationId())
				.addValue("className",classinfo.getClassName())
				.addValue("isSelfRegister",classinfo.getIsSelfRegister())
				.addValue("withCertificate",classinfo.getWithCertificate())
				.addValue("minAttendee",classinfo.getMinAttendee())
				.addValue("maxAttendee",classinfo.getMaxAttendee())
				.addValue("withExam",classinfo.getWithExam())
				.addValue("scheduleType",classinfo.getScheduleType()));
		
	}

	private class FetchClassInfoPagesMapper implements RowMapper<ClassInfo> {
		@Override
		public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassId(rs.getInt("classId"));
			classInfo.setClassCode(rs.getString("classCode"));
			classInfo.setCourseId(rs.getInt("courseId"));
			classInfo.setLocationId(rs.getInt("locationId"));
			classInfo.setClassName(rs.getString("className"));
			classInfo.setIsSelfRegister(rs.getInt("isSelfRegister"));
			classInfo.setWithCertificate(rs.getInt("withCertificate"));
			classInfo.setCertificateUrl(rs.getString("certificateUrl"));
			classInfo.setMinAttendee(rs.getInt("minAttendee"));
			classInfo.setMaxAttendee(rs.getInt("maxAttendee"));
			classInfo.setWithExam(rs.getInt("withExam"));
			classInfo.setScheduleType(rs.getInt("scheduleType"));
			classInfo.setIsEvaluationRequired(rs.getInt("isEvaluationRequired"));
			classInfo.setIsAnonymousSender(rs.getInt("isAnonymousSender"));
			classInfo.setCreatedAt(rs.getString("createdAt"));
			classInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			classInfo.setFullName(rs.getString("fullName"));
			classInfo.setCourseName(rs.getString("courseName"));
			classInfo.setCourseCode(rs.getString("courseCode"));
			classInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			classInfo.setStartDate(getDateValue(rs.getString("startDate"),"yyyy-MM-dd","MMMM dd, yyyy"));
			classInfo.setEndDate(getDateValue(rs.getString("endDate"),"yyyy-MM-dd","MMMM dd, yyyy"));
			classInfo.setTotalEnrolledTrainees(rs.getInt("totalEnrolledTrainees"));
			return classInfo;
		}
	}
	
	@Override
	public List<ClassInfo> fetchClassInfoPages(int pageNumber, int pageSize) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ci.classId, ci.classCode, ci.courseId, ")
				.append("ci.locationId, ci.className, ci.isSelfRegister, ")
				.append("ci.withCertificate, ci.certificateUrl, ci.minAttendee, ci.maxAttendee, ")
				.append("ci.withExam, ci.scheduleType, ci.isEvaluationRequired, ")
				.append("ci.isAnonymousSender, ci.createdAt, ci.lastModifiedAt, ")
				.append("ei.fullName,coi.courseName,coi.courseCode, coi.deliveryMethod, ")
				.append("IF(ci.scheduleType=2, ")
				.append("(SELECT MIN(css1.startDate) ")
				.append("FROM classsetschedule css1 ")
				.append("WHERE css1.classId = ci.classId), ")
				.append("(SELECT cbs.startDate ")
				.append("FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId)) AS startDate, ")
				.append("IF(ci.scheduleType=2, ")
				.append("(SELECT MAX(css2.endDate) ")
				.append("FROM classsetschedule css2 ")
				.append("WHERE css2.classId = ci.classId), ")
				.append("(SELECT cbs.endDate ")
				.append("FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId)) AS endDate, ")
				.append("(SELECT COUNT(ce2.classEmployeeId) ")
				.append("FROM classemployee ce2 ")
				.append("WHERE ce2.classId = ci.classId AND ")
				.append("ce2.approvalStatus = :approvalStatus) AS totalEnrolledTrainees ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN classemployee ce ON ce.classId = ci.classId ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId = ce.employeeId ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId = ci.courseId ")
				.append("WHERE ce.role=:role ")
				.append("GROUP BY ci.classId ")
				.append("ORDER BY ci.createdAt DESC ")
				.append("LIMIT :pageNumber,:pageSize ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("role", LmsClassEmployeeData.TRAINER)
				.addValue("pageSize", pageSize)
				.addValue("pageNumber", pageNumber)
				.addValue("approvalStatus", LmsClassEmployeeData.APPROVED),
			new FetchClassInfoPagesMapper());
	}

	@Override
	public int countClassInfos() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ci.classId) ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN classemployee ce ON ce.classId = ci.classId ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId = ce.employeeId ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId = ci.courseId ")
				.append("WHERE ce.role=:role ")
				.append("ORDER BY ci.createdAt DESC ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("role", LmsClassEmployeeData.TRAINER),
			Integer.class);
	}
	
	private class FetchClassInfosMapper implements RowMapper<ClassInfo> {
		@Override
		public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassId(rs.getInt("classId"));
			classInfo.setClassCode(rs.getString("classCode"));
			classInfo.setCourseId(rs.getInt("courseId"));
			classInfo.setLocationId(rs.getInt("locationId"));
			classInfo.setClassName(rs.getString("className"));
			classInfo.setIsSelfRegister(rs.getInt("isSelfRegister"));
			classInfo.setWithCertificate(rs.getInt("withCertificate"));
			classInfo.setCertificateUrl(rs.getString("certificateUrl"));
			classInfo.setMinAttendee(rs.getInt("minAttendee"));
			classInfo.setMaxAttendee(rs.getInt("maxAttendee"));
			classInfo.setWithExam(rs.getInt("withExam"));
			classInfo.setScheduleType(rs.getInt("scheduleType"));
			classInfo.setIsEvaluationRequired(rs.getInt("isEvaluationRequired"));
			classInfo.setIsAnonymousSender(rs.getInt("isAnonymousSender"));
			classInfo.setCreatedAt(rs.getString("createdAt"));
			classInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			classInfo.setFullName(rs.getString("fullName"));
			classInfo.setCourseName(rs.getString("courseName"));
			classInfo.setCourseCode(rs.getString("courseCode"));
			classInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			classInfo.setStartDate(getDateValue(rs.getString("startDate"),"yyyy-MM-dd","MMMM dd, yyyy"));
			classInfo.setEndDate(getDateValue(rs.getString("endDate"),"yyyy-MM-dd","MMMM dd, yyyy"));
			classInfo.setEmployeeId(rs.getInt("employeeId"));
			return classInfo;
		}
	}
	
	@Override
	public List<ClassInfo> fetchClassInfos() {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ci.classId, ci.classCode, ci.courseId, ")
				.append("ci.locationId, ci.className, ci.isSelfRegister, ")
				.append("ci.withCertificate, ci.certificateUrl, ci.minAttendee, ci.maxAttendee, ")
				.append("ci.withExam, ci.scheduleType, ci.isEvaluationRequired, ")
				.append("ci.isAnonymousSender, ci.createdAt, ci.lastModifiedAt, ")
				.append("ei.fullName,ei.employeeId, coi.courseName,coi.courseCode, ")
				.append("coi.deliveryMethod, ")
				.append("IF(ci.scheduleType=2, ")
				.append("(SELECT MIN(css1.startDate) ")
				.append("FROM classsetschedule css1 ")
				.append("WHERE css1.classId = ci.classId), ")
				.append("(SELECT cbs.startDate ")
				.append("FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId)) AS startDate, ")
				.append("IF(ci.scheduleType=2, ")
				.append("(SELECT MAX(css2.endDate) ")
				.append("FROM classsetschedule css2 ")
				.append("WHERE css2.classId = ci.classId), ")
				.append("(SELECT cbs.endDate ")
				.append("FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId)) AS endDate ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN classemployee ce ON ce.classId = ci.classId ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId = ce.employeeId ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId = ci.courseId ")
				.append("WHERE ce.role=:role ")
				.append("ORDER BY ei.fullName ASC;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("role", LmsClassEmployeeData.TRAINER),
			new FetchClassInfosMapper());
	}

	private class SearchClassInfosMapper implements RowMapper<ClassInfo> {
		@Override
		public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassId(rs.getInt("classId"));
			classInfo.setClassCode(rs.getString("classCode"));
			classInfo.setCourseId(rs.getInt("courseId"));
			classInfo.setLocationId(rs.getInt("locationId"));
			classInfo.setClassName(rs.getString("className"));
			classInfo.setIsSelfRegister(rs.getInt("isSelfRegister"));
			classInfo.setWithCertificate(rs.getInt("withCertificate"));
			classInfo.setCertificateUrl(rs.getString("certificateUrl"));
			classInfo.setMinAttendee(rs.getInt("minAttendee"));
			classInfo.setMaxAttendee(rs.getInt("maxAttendee"));
			classInfo.setWithExam(rs.getInt("withExam"));
			classInfo.setScheduleType(rs.getInt("scheduleType"));
			classInfo.setIsEvaluationRequired(rs.getInt("isEvaluationRequired"));
			classInfo.setIsAnonymousSender(rs.getInt("isAnonymousSender"));
			classInfo.setCreatedAt(rs.getString("createdAt"));
			classInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			classInfo.setFullName(rs.getString("fullName"));
			classInfo.setCourseName(rs.getString("courseName"));
			classInfo.setCourseCode(rs.getString("courseCode"));
			classInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			classInfo.setStartDate(getDateValue(rs.getString("startDate"),"yyyy-MM-dd","MMMM dd, yyyy"));
			classInfo.setEndDate(getDateValue(rs.getString("endDate"),"yyyy-MM-dd","MMMM dd, yyyy"));
			classInfo.setEmployeeId(rs.getInt("employeeId"));
			classInfo.setTotalEnrolledTrainees(rs.getInt("totalEnrolledTrainees"));
			return classInfo;
		}
	}
	
	@Override
	public List<ClassInfo> searchClassInfo(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
				new StringBuffer()
					.append("SELECT ci.classId, ci.classCode, ci.courseId, ")
					.append("ci.locationId, ci.className, ci.isSelfRegister, ")
					.append("ci.withCertificate, ci.certificateUrl, ci.minAttendee, ci.maxAttendee, ")
					.append("ci.withExam, ci.scheduleType, ci.isEvaluationRequired, ")
					.append("ci.isAnonymousSender, ci.createdAt, ci.lastModifiedAt, ")
					.append("ei.fullName,ei.employeeId,coi.courseName,coi.courseCode, coi.deliveryMethod, ")
					.append("IF(ci.scheduleType=2, ")
					.append("(SELECT MIN(css1.startDate) ")
					.append("FROM classsetschedule css1 ")
					.append("WHERE css1.classId = ci.classId), ")
					.append("(SELECT cbs.startDate ")
					.append("FROM classblockschedule cbs ")
					.append("WHERE cbs.classId = ci.classId)) AS startDate, ")
					.append("IF(ci.scheduleType=2, ")
					.append("(SELECT MAX(css2.endDate) ")
					.append("FROM classsetschedule css2 ")
					.append("WHERE css2.classId = ci.classId), ")
					.append("(SELECT cbs.endDate ")
					.append("FROM classblockschedule cbs ")
					.append("WHERE cbs.classId = ci.classId)) AS endDate, ")
					.append("(SELECT COUNT(ce2.classEmployeeId) ")
					.append("FROM classemployee ce2 ")
					.append("WHERE ce2.classId = ci.classId AND ")
					.append("ce2.approvalStatus = :approvalStatus) AS totalEnrolledTrainees ")
					.append("FROM classinfo ci ")
					.append("LEFT JOIN classemployee ce ON ce.classId = ci.classId ")
					.append("LEFT JOIN employeeinfo ei ON ei.employeeId = ce.employeeId ")
					.append("LEFT JOIN courseinfo coi ON coi.courseId = ci.courseId ")
					.append("WHERE ce.role=:role ")
					.append(tableCommand.getWhereClause())
					.append("LIMIT :pageNumber,:pageSize ")
					.toString(),
				new MapSqlParameterSource()
					.addValue("role", LmsClassEmployeeData.TRAINER)
					.addValue("pageNumber", tableCommand.getPageNumber())
					.addValue("pageSize", tableCommand.getPageSize())
					.addValue("deliveryMethod",tableCommand.getDeliveryMethod())
					.addValue("employeeId", tableCommand.getSearchByEmployeeId())
					.addValue("courseId", tableCommand.getCourseId())
					.addValue("startDate", tableCommand.getStartDate())
					.addValue("endDate", tableCommand.getEndDate())
					.addValue("keyword", "%"+tableCommand.getKeyword()+"%")
					.addValue("approvalStatus", LmsClassEmployeeData.APPROVED),
				new SearchClassInfosMapper());
	}

	@Override
	public int countSearchClassInfo(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ci.classId) ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN classemployee ce ON ce.classId = ci.classId ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId = ce.employeeId ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId = ci.courseId ")
				.append("WHERE ce.role=:role ")
				.append(tableCommand.getWhereClause())
				.toString(),
			new MapSqlParameterSource()
				.addValue("role", LmsClassEmployeeData.TRAINER)
				.addValue("deliveryMethod",tableCommand.getDeliveryMethod())
				.addValue("employeeId", tableCommand.getSearchByEmployeeId())
				.addValue("courseId", tableCommand.getCourseId())
				.addValue("startDate", tableCommand.getStartDate())
				.addValue("endDate", tableCommand.getEndDate())
				.addValue("keyword", "%"+tableCommand.getKeyword()+"%"),
			Integer.class);
	}

	@Override
	public int countClassInfoByUserId(int userId) {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
					.append("SELECT COUNT(ci.classId) ")
					.append("FROM classinfo ci ")
					.append("LEFT JOIN classemployee ce ON ce.classId = ci.classId ")
					.append("LEFT JOIN employeeinfo ei ON ei.employeeId = ce.employeeId ")
					.append("LEFT JOIN user u ON u.userId = ei.userId ")
					.append("LEFT JOIN courseinfo coi ON coi.courseId = ci.courseId ")
					.append("WHERE ce.role=:role AND u.userId = :userId;")
					.toString(),
				new MapSqlParameterSource()
					.addValue("role", LmsClassEmployeeData.TRAINER)
					.addValue("userId", userId),
				Integer.class);
	}

	@Override
	public int countInProgressClassInfos(TableCommand tblCommand) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ci.classId) ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN classemployee ce ON ce.classId = ci.classId ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId = ce.employeeId ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId = ci.courseId ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("WHERE ce.role=:role AND NOW() BETWEEN ")
				.append("(CASE WHEN ci.scheduleType = 1 THEN ")
				.append("(SELECT cbs.startDate FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId) ")
				.append("WHEN ci.scheduleType = 2 THEN ")
				.append("(SELECT MIN(css.startDate) FROM classsetschedule css ")
				.append("WHERE css.classId = ci.classId) ")
				.append("END) AND ")
				.append("(CASE WHEN ci.scheduleType = 1 THEN ")
				.append("(SELECT cbs.endDate FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId) ")
				.append("WHEN ci.scheduleType = 2 THEN ")
				.append("(SELECT MAX(css.endDate) FROM classsetschedule css ")
				.append("WHERE css.classId = ci.classId) ")
				.append("END)")
				.append(tblCommand.getWhereClause())
				.toString(),
			new MapSqlParameterSource()
				.addValue("role", LmsClassEmployeeData.TRAINER)
				.addValue("userId", tblCommand.getUserId()),
			Integer.class);
	}

	@Override
	public int countUpcomingClassInfos(TableCommand tblCommand) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ci.classId) ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN classemployee ce ON ce.classId = ci.classId ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId = ci.courseId ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId=ce.employeeId ")
				.append("LEFT JOIN user u ON u.userId=ei.userId ")
				.append("WHERE ce.role=:role ")
				.append("AND (CASE WHEN ci.scheduleType = 1 THEN ")
				.append("(SELECT cbs.startDate FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId) ")
				.append("WHEN ci.scheduleType = 2 THEN ")
				.append("(SELECT MIN(css.startDate) FROM classsetschedule css ")
				.append("WHERE css.classId = ci.classId) ")
				.append("END) > NOW() ")
				.append(tblCommand.getWhereClause())
				.toString(),
			new MapSqlParameterSource()
				.addValue("role", LmsClassEmployeeData.TRAINER)
				.addValue("userId", tblCommand.getUserId()),
			Integer.class);
	}
	
	@Override
	public int countCompletedClassInfos(TableCommand tblCommand) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ci.classId) ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN classemployee ce ON ce.classId = ci.classId ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId = ci.courseId ")
				.append("LEFT JOIN employeeinfo ei On ei.employeeId=ce.employeeId ")
				.append("LEFT JOIN user u ON u.userId=ei.userId ")
				.append("WHERE ce.role=:role ")
				.append("AND (CASE WHEN ci.scheduleType = 1 THEN ")
				.append("(SELECT cbs.endDate FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId) ")
				.append("WHEN ci.scheduleType = 2 THEN ")
				.append("(SELECT MAX(css.endDate) FROM classsetschedule css ")
				.append("WHERE css.classId = ci.classId) ")
				.append("END) < NOW() ")
				.append(tblCommand.getWhereClause())
				.toString(),
			new MapSqlParameterSource()
				.addValue("role", LmsClassEmployeeData.TRAINER)
				.addValue("userId", tblCommand.getUserId()),
			Integer.class);
	}
	
	@Override
	public int countCancelledClassInfos(TableCommand tblCommand) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ci.classId) ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN classemployee ce ON ce.classId = ci.classId ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId = ci.courseId ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId=ce.employeeId ")
				.append("LEFT JOIN user u ON u.userId=ei.userId ")
				.append("WHERE ce.role=:role AND ")
				.append("ci.completionStatus = :completionStatus ")
				.append(tblCommand.getWhereClause())
				.toString(),
			new MapSqlParameterSource()
				.addValue("role", LmsClassEmployeeData.TRAINER)
				.addValue("completionStatus", LmsClassInfoData.CANCELLED)
				.addValue("userId", tblCommand.getUserId()),
			Integer.class);
	}
	
	@Override
	public void deleteClassInfo(int classId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE ci.*,ce.* ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN classemployee ce ON ce.classId = ci.classId ")
				.append("WHERE ci.classId = :classId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId));
	}
	
	@Override
	public void addBlockClassSchedules(List<ClassBlockSchedule> classSchedules) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(classSchedules.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
			.append("INSERT INTO classblockschedule ")
			.append("(classId, startDate, endDate, startTime, endTime) ")
			.append("VALUES (:classId, :startDate, :endDate, :startTime, :endTime) ")
			.toString(), 
			batch);
	}

	@Override
	public void addSetClassSchedules(List<ClassSetSchedule> classSchedules) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(classSchedules.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
			.append("INSERT INTO classsetschedule ")
			.append("(classId, startDate, endDate, startTime, endTime) ")
			.append("VALUES (:classId, :startDate, :endDate, :startTime, :endTime) ")
			.toString(), 
			batch);
	}
	
	@Override
	public void addSeriesClassSchedules(List<ClassSeriesSchedule> classSchedules) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(classSchedules.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
			.append("INSERT INTO classseriesschedule ")
			.append("(classId, startDate, endDate, startTime, endTime) ")
			.append("VALUES (:classId, :startDate, :endDate, :startTime, :endTime) ")
			.toString(), 
			batch);
	}

	private static class FetchClassInfoMapper implements RowMapper<ClassInfo> {
		@Override
		public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassId(rs.getInt("classId"));
			classInfo.setClassCode(rs.getString("classCode"));
			classInfo.setCourseId(rs.getInt("courseId"));
			classInfo.setLocationId(rs.getInt("locationId"));
			classInfo.setClassName(rs.getString("className"));
			classInfo.setIsSelfRegister(rs.getInt("isSelfRegister"));
			classInfo.setSelfRegisterType(rs.getInt("selfRegisterType"));
			classInfo.setSelfRegisterStartDate(rs.getString("selfRegisterStartDate"));
			classInfo.setSelfRegisterEndDate(rs.getString("selfRegisterEndDate"));
			classInfo.setWithCertificate(rs.getInt("withCertificate"));
			classInfo.setCertificateTemplateType(rs.getInt("certificateTemplateType"));
			classInfo.setCertificateUrl(rs.getString("certificateUrl"));
			classInfo.setIsCertificateDownloadable(rs.getInt("isCertificateDownloadable"));
			classInfo.setMinAttendee(rs.getInt("minAttendee"));
			classInfo.setMaxAttendee(rs.getInt("maxAttendee"));
			classInfo.setWithExam(rs.getInt("withExam"));
			classInfo.setScheduleType(rs.getInt("scheduleType"));
			classInfo.setIsEvaluationRequired(rs.getInt("isEvaluationRequired"));
			classInfo.setIsAnonymousSender(rs.getInt("isAnonymousSender"));
			classInfo.setViewRestrictionType(rs.getInt("viewRestrictionType"));
			classInfo.setAccessRestrictionType(rs.getInt("accessRestrictionType"));
			classInfo.setAccessStartDate(rs.getString("accessStartDate"));
			classInfo.setAccessEndDate(rs.getString("accessEndDate"));
			classInfo.setCompletionStatus(rs.getInt("completionStatus"));
			classInfo.setCreatedAt(rs.getString("createdAt"));
			classInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			classInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			classInfo.setCourseName(rs.getString("courseName"));
			classInfo.setClassPhotoUrl(rs.getString("classPhotoUrl"));
			classInfo.setCourseCode(rs.getString("courseCode"));
			classInfo.setCategoryName(rs.getString("categoryName"));
			classInfo.setTrainerName(rs.getString("trainerName"));
			classInfo.setLocationName(rs.getString("locationName"));
			classInfo.setObjective(rs.getString("objective"));
			classInfo.setDescription(rs.getString("description"));
			classInfo.setClassDuration(rs.getInt("classDuration"));
			return classInfo;
		}
	}
	
	@Override
	public ClassInfo fetchClassInfo(int classId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT ci.classId, ci.classCode, ci.courseId, ")
				.append("ci.locationId, ci.className, ci.isSelfRegister, ci.selfRegisterType, ")
				.append("ci.selfRegisterStartDate, ci.selfRegisterEndDate, ci.withCertificate, ci.certificateUrl, ")
				.append("ci.certificateTemplatetype, ci.isCertificateDownloadable, ci.minAttendee, ci.maxAttendee, ci.withExam, ")
				.append("ci.scheduleType, ci.isEvaluationRequired, ci.classDuration, ci.viewRestrictionType, ci.accessRestrictionType, ")
				.append("ci.accessStartDate,ci.accessEndDate,ci.isAnonymousSender, ci.completionStatus, ci.createdAt, ci.lastModifiedAt, ")
				.append("coi.description, coi.objective,coi.courseName, coi.courseCode, tc.categoryName, ")
				.append("coi.deliveryMethod, IF(ci.classPhotoUrl IS NULL,cd.classPhotoUrl,ci.classPhotoUrl) AS classPhotoUrl, ")
				.append("(SELECT ei.fullName FROM classemployee ce ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId=ce.employeeId ")
				.append("WHERE ce.classId = ci.classId AND role=:role LIMIT 1) AS trainerName,l.locationName ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId = ci.courseId ")
				.append("LEFT JOIN classdefault cd ON cd.courseId = coi.courseId ")
				.append("LEFT JOIN trainingcategory tc ON tc.categoryId=coi.categoryId ")
				.append("LEFT JOIN location l ON l.locationId=ci.locationId ")
				.append("WHERE ci.classId = :classId;")
				.toString(), 
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("role", LmsClassEmployeeData.TRAINER),
			new FetchClassInfoMapper());
	}

	@Override
	public void updateClassPhotoUrl(ClassInfo classInfo) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE classinfo SET ")
				.append("classPhotoUrl = :classPhotoUrl ")
				.append("WHERE classId = :classId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classInfo.getClassId())
				.addValue("classPhotoUrl", classInfo.getClassPhotoUrl()));
	}
	
	

	private static class FetchInProgressClassInfosByUserIdMapper implements RowMapper<ClassInfo> {
		@Override
		public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassId(rs.getInt("classId"));
			classInfo.setClassCode(rs.getString("classCode"));
			classInfo.setCourseId(rs.getInt("courseId"));
			classInfo.setCourseName(rs.getString("courseName"));
			classInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			classInfo.setLocationId(rs.getInt("locationId"));
			classInfo.setClassName(rs.getString("className"));
			classInfo.setIsSelfRegister(rs.getInt("isSelfRegister"));
			classInfo.setWithCertificate(rs.getInt("withCertificate"));
			classInfo.setCertificateUrl(rs.getString("certificateUrl"));
			classInfo.setMinAttendee(rs.getInt("minAttendee"));
			classInfo.setMaxAttendee(rs.getInt("maxAttendee"));
			classInfo.setWithExam(rs.getInt("withExam"));
			classInfo.setScheduleType(rs.getInt("scheduleType"));
			classInfo.setIsEvaluationRequired(rs.getInt("isEvaluationRequired"));
			classInfo.setIsAnonymousSender(rs.getInt("isAnonymousSender"));
			classInfo.setCompletionStatus(rs.getInt("completionStatus"));
			classInfo.setCreatedAt(rs.getString("createdAt"));
			classInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			classInfo.setStartDate(getDateValue(rs.getString("startDate"),"yyyy-MM-dd","MMMM dd, yyyy"));
			classInfo.setStartDate(getDateValue(rs.getString("endDate"),"yyyy-MM-dd","MMMM dd, yyyy"));
			classInfo.setTrainingStatus(rs.getInt("trainingStatus"));
			classInfo.setTotalEnrolledTrainees(rs.getInt("totalEnrolledTrainees"));
			return classInfo;
		}
	}
	
	@Override
	public List<ClassInfo> fetchInProgressClassInfosByUserId(int userId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ci.classId, ci.classCode, ci.courseId, coi.courseName, ")
				.append("coi.deliveryMethod, ci.locationId, ci.className, ci.isSelfRegister, ")
				.append("ci.withCertificate, ci.certificateUrl, ci.minAttendee, ")
				.append("ci.maxAttendee, ci.withExam, ci.scheduleType, ")
				.append("ci.isEvaluationRequired, ci.isAnonymousSender, ")
				.append("ci.completionStatus, ci.createdAt, ci.lastModifiedAt, ce.trainingStatus, ")
				.append("(CASE WHEN ci.scheduleType = 1 THEN ")
				.append("(SELECT cbs.startDate FROM classblockschedule cbs ") 
				.append("WHERE cbs.classId = ci.classId) ")
				.append("WHEN ci.scheduleType = 2 THEN ")
				.append("(SELECT MIN(css.startDate) FROM classsetschedule css ")
				.append(" WHERE css.classId = ci.classId) ")
				.append("END) AS startDate, ")
				.append("(CASE WHEN ci.scheduleType = 1 THEN ")
				.append("(SELECT cbs.endDate FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId) ")
				.append("WHEN ci.scheduleType = 2 THEN ")
				.append("(SELECT MAX(css.endDate) FROM classsetschedule css ")
				.append("WHERE css.classId = ci.classId) ")
				.append("END) AS endDate, ")
				.append("(SELECT COUNT(ce2.classEmployeeId) ")
				.append("FROM classemployee ce2 ")
				.append("WHERE ce2.classId = ci.classId AND ")
				.append("ce2.approvalStatus = :approvalStatus) AS totalEnrolledTrainees ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId=ci.courseId ")
				.append("LEFT JOIN classemployee ce ON ce.classId=ci.classId ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId=ce.employeeId ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("WHERE (CASE WHEN ci.scheduleType = 1 THEN ")
				.append("(SELECT cbs.startDate FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId) ")
				.append("WHEN ci.scheduleType = 2 THEN ")
				.append("(SELECT MIN(css.startDate) FROM classsetschedule css ")
				.append("WHERE css.classId = ci.classId) ")
				.append("END) < NOW() AND ")
				.append("(CASE WHEN ci.scheduleType = 1 THEN ")
				.append("(SELECT cbs.endDate FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId) ")
				.append("WHEN ci.scheduleType = 2 THEN ")
				.append("(SELECT MAX(css.endDate) FROM classsetschedule css ")
				.append("WHERE css.classId = ci.classId) ")
				.append("END) > NOW() AND ")
				.append("u.userId = :userId AND ce.approvalStatus = :approvalStatus ")
				.append("AND ce.trainingStatus IN(1,5);")
				.toString(),
			new MapSqlParameterSource()
				.addValue("userId", userId)
				.addValue("approvalStatus", LmsClassEmployeeData.APPROVED),
			new FetchInProgressClassInfosByUserIdMapper());
	}

	private static class FetchAccessTrainingDurationClassInfosMapper implements RowMapper<ClassInfo> {
		@Override
		public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassId(rs.getInt("classId"));
			classInfo.setClassCode(rs.getString("classCode"));
			classInfo.setCourseId(rs.getInt("courseId"));
			classInfo.setCourseName(rs.getString("courseName"));
			classInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			classInfo.setLocationId(rs.getInt("locationId"));
			classInfo.setClassName(rs.getString("className"));
			classInfo.setIsSelfRegister(rs.getInt("isSelfRegister"));
			classInfo.setWithCertificate(rs.getInt("withCertificate"));
			classInfo.setCertificateUrl(rs.getString("certificateUrl"));
			classInfo.setMinAttendee(rs.getInt("minAttendee"));
			classInfo.setMaxAttendee(rs.getInt("maxAttendee"));
			classInfo.setWithExam(rs.getInt("withExam"));
			classInfo.setScheduleType(rs.getInt("scheduleType"));
			classInfo.setIsEvaluationRequired(rs.getInt("isEvaluationRequired"));
			classInfo.setIsAnonymousSender(rs.getInt("isAnonymousSender"));
			classInfo.setCompletionStatus(rs.getInt("completionStatus"));
			classInfo.setCreatedAt(rs.getString("createdAt"));
			classInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			classInfo.setTrainingStatus(rs.getInt("trainingStatus"));
			return classInfo;
		}
	}
	
	@Override
	public List<ClassInfo> fetchAccessTrainingDurationClassInfos(int userId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ci.classId, ci.classCode, ci.courseId, coi.courseName, ")
				.append("coi.deliveryMethod, ci.locationId, ci.className, ci.isSelfRegister, ")
				.append("ci.withCertificate, ci.certificateUrl, ci.minAttendee, ")
				.append("ci.maxAttendee, ci.withExam, ci.scheduleType, ")
				.append("ci.isEvaluationRequired, ci.isAnonymousSender, ")
				.append("ci.completionStatus, ci.createdAt, ci.lastModifiedAt, ce.trainingStatus ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId=ci.courseId ")
				.append("LEFT JOIN classemployee ce ON ce.classId=ci.classId ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId=ce.employeeId ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("WHERE (CASE WHEN ci.scheduleType = 1 THEN ")
				.append("(SELECT cbs.startDate FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId) ")
				.append("WHEN ci.scheduleType = 2 THEN ")
				.append("(SELECT MIN(css.startDate) FROM classsetschedule css ")
				.append("WHERE css.classId = ci.classId) ")
				.append("END) < NOW() AND ")
				.append("(CASE WHEN ci.scheduleType = 1 THEN ")
				.append("(SELECT cbs.endDate FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId) ")
				.append("WHEN ci.scheduleType = 2 THEN ")
				.append("(SELECT MAX(css.endDate) FROM classsetschedule css ")
				.append("WHERE css.classId = ci.classId) ")
				.append("END) > NOW() AND ")
				.append("u.userId = :userId AND ce.approvalStatus = :approvalStatus ")
				.append("AND ci.viewRestrictionType = :viewRestrictionType ")
				.append("AND ce.trainingStatus IN(2);")
				.toString(),
			new MapSqlParameterSource()
				.addValue("userId", userId)
				.addValue("approvalStatus", LmsClassEmployeeData.APPROVED)
				.addValue("viewRestrictionType", LmsClassInfoData.ACCESSIBLE_BY_TRAINING_DURATION),
			new FetchAccessTrainingDurationClassInfosMapper());
	}

	private static class FetchAccessIndefinitlyClassInfosMapper implements RowMapper<ClassInfo> {
		@Override
		public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassId(rs.getInt("classId"));
			classInfo.setClassCode(rs.getString("classCode"));
			classInfo.setCourseId(rs.getInt("courseId"));
			classInfo.setCourseName(rs.getString("courseName"));
			classInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			classInfo.setLocationId(rs.getInt("locationId"));
			classInfo.setClassName(rs.getString("className"));
			classInfo.setIsSelfRegister(rs.getInt("isSelfRegister"));
			classInfo.setWithCertificate(rs.getInt("withCertificate"));
			classInfo.setCertificateUrl(rs.getString("certificateUrl"));
			classInfo.setMinAttendee(rs.getInt("minAttendee"));
			classInfo.setMaxAttendee(rs.getInt("maxAttendee"));
			classInfo.setWithExam(rs.getInt("withExam"));
			classInfo.setScheduleType(rs.getInt("scheduleType"));
			classInfo.setIsEvaluationRequired(rs.getInt("isEvaluationRequired"));
			classInfo.setIsAnonymousSender(rs.getInt("isAnonymousSender"));
			classInfo.setCompletionStatus(rs.getInt("completionStatus"));
			classInfo.setCreatedAt(rs.getString("createdAt"));
			classInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			classInfo.setTrainingStatus(rs.getInt("trainingStatus"));
			return classInfo;
		}
	}
	
	@Override
	public List<ClassInfo> fetchAccessIndefinitlyClassInfos(int userId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ci.classId, ci.classCode, ci.courseId, coi.courseName, ")
				.append("coi.deliveryMethod, ci.locationId, ci.className, ci.isSelfRegister, ")
				.append("ci.withCertificate, ci.certificateUrl, ci.minAttendee, ")
				.append("ci.maxAttendee, ci.withExam, ci.scheduleType, ")
				.append("ci.isEvaluationRequired, ci.isAnonymousSender, ")
				.append("ci.completionStatus, ci.createdAt, ci.lastModifiedAt, ce.trainingStatus ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId=ci.courseId ")
				.append("LEFT JOIN classemployee ce ON ce.classId=ci.classId ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId=ce.employeeId ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("WHERE u.userId = :userId AND ce.approvalStatus = :approvalStatus ")
				.append("AND ci.viewRestrictionType = :viewRestrictionType ")
				.append("AND ce.trainingStatus IN(2);")
				.toString(),
			new MapSqlParameterSource()
				.addValue("userId", userId)
				.addValue("approvalStatus", LmsClassEmployeeData.APPROVED)
				.addValue("viewRestrictionType", LmsClassInfoData.ACCESSIBLE_INDEFINITELY),
			new FetchAccessIndefinitlyClassInfosMapper());
	}

	private static class FetchAccessSpecifiedClassInfosMapper implements RowMapper<ClassInfo> {
		@Override
		public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassId(rs.getInt("classId"));
			classInfo.setClassCode(rs.getString("classCode"));
			classInfo.setCourseId(rs.getInt("courseId"));
			classInfo.setCourseName(rs.getString("courseName"));
			classInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			classInfo.setLocationId(rs.getInt("locationId"));
			classInfo.setClassName(rs.getString("className"));
			classInfo.setIsSelfRegister(rs.getInt("isSelfRegister"));
			classInfo.setWithCertificate(rs.getInt("withCertificate"));
			classInfo.setCertificateUrl(rs.getString("certificateUrl"));
			classInfo.setMinAttendee(rs.getInt("minAttendee"));
			classInfo.setMaxAttendee(rs.getInt("maxAttendee"));
			classInfo.setWithExam(rs.getInt("withExam"));
			classInfo.setScheduleType(rs.getInt("scheduleType"));
			classInfo.setIsEvaluationRequired(rs.getInt("isEvaluationRequired"));
			classInfo.setIsAnonymousSender(rs.getInt("isAnonymousSender"));
			classInfo.setCompletionStatus(rs.getInt("completionStatus"));
			classInfo.setCreatedAt(rs.getString("createdAt"));
			classInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			classInfo.setTrainingStatus(rs.getInt("trainingStatus"));
			return classInfo;
		}
	}
	
	@Override
	public List<ClassInfo> fetchAccessSpecifiedClassInfos(int userId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ci.classId, ci.classCode, ci.courseId, coi.courseName, ")
				.append("coi.deliveryMethod, ci.locationId, ci.className, ci.isSelfRegister, ")
				.append("ci.withCertificate, ci.certificateUrl, ci.minAttendee, ")
				.append("ci.maxAttendee, ci.withExam, ci.scheduleType, ")
				.append("ci.isEvaluationRequired, ci.isAnonymousSender, ")
				.append("ci.completionStatus, ci.createdAt, ci.lastModifiedAt, ce.trainingStatus ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId=ci.courseId ")
				.append("LEFT JOIN classemployee ce ON ce.classId=ci.classId ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId=ce.employeeId ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("WHERE u.userId = :userId AND ce.approvalStatus = :approvalStatus ")
				.append("AND ci.viewRestrictionType = :viewRestrictionType ")
				.append("AND ce.trainingStatus IN(2) AND ")
				.append("ci.accessStartDate <= NOW() AND ci.accessEndDate >= NOW();") 
				.toString(),
			new MapSqlParameterSource()
				.addValue("userId", userId)
				.addValue("approvalStatus", LmsClassEmployeeData.APPROVED)
				.addValue("viewRestrictionType", LmsClassInfoData.ACCESSIBLE_BY_SPECIFIED_DATE),
			new FetchAccessSpecifiedClassInfosMapper());
	}

	private static class FetchCompletedClassInfosByUserIdMapper implements RowMapper<ClassInfo> {
		@Override
		public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassId(rs.getInt("classId"));
			classInfo.setClassCode(rs.getString("classCode"));
			classInfo.setCourseId(rs.getInt("courseId"));
			classInfo.setCourseName(rs.getString("courseName"));
			classInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			classInfo.setLocationId(rs.getInt("locationId"));
			classInfo.setClassName(rs.getString("className"));
			classInfo.setIsSelfRegister(rs.getInt("isSelfRegister"));
			classInfo.setWithCertificate(rs.getInt("withCertificate"));
			classInfo.setCertificateUrl(rs.getString("certificateUrl"));
			classInfo.setMinAttendee(rs.getInt("minAttendee"));
			classInfo.setMaxAttendee(rs.getInt("maxAttendee"));
			classInfo.setWithExam(rs.getInt("withExam"));
			classInfo.setScheduleType(rs.getInt("scheduleType"));
			classInfo.setIsEvaluationRequired(rs.getInt("isEvaluationRequired"));
			classInfo.setIsAnonymousSender(rs.getInt("isAnonymousSender"));
			classInfo.setCompletionStatus(rs.getInt("completionStatus"));
			classInfo.setCreatedAt(rs.getString("createdAt"));
			classInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			classInfo.setTrainingStatus(rs.getInt("trainingStatus"));
			return classInfo;
		}
	}
	
	@Override
	public List<ClassInfo> fetchCompletedClassInfosByUserId(int userId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ci.classId, ci.classCode, ci.courseId, coi.courseName, ")
				.append("coi.deliveryMethod, ci.locationId, ci.className, ci.isSelfRegister, ")
				.append("ci.withCertificate, ci.certificateUrl, ci.minAttendee, ")
				.append("ci.maxAttendee, ci.withExam, ci.scheduleType, ")
				.append("ci.isEvaluationRequired, ci.isAnonymousSender, ")
				.append("ci.completionStatus, ci.createdAt, ci.lastModifiedAt, ce.trainingStatus ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId=ci.courseId ")
				.append("LEFT JOIN classemployee ce ON ce.classId=ci.classId ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId=ce.employeeId ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("WHERE u.userId = :userId AND ce.approvalStatus = :approvalStatus ")
				.append("AND ce.trainingStatus =  :trainingStatus;") 
				.toString(),
			new MapSqlParameterSource()
				.addValue("userId", userId)
				.addValue("approvalStatus", LmsClassEmployeeData.APPROVED)
				.addValue("trainingStatus", LmsClassEmployeeData.TRAININGSTATUS_COMPLETED),
			new FetchCompletedClassInfosByUserIdMapper());
	}

	private static class FetchRecommendedClassInfosMapper implements RowMapper<ClassInfo> {
		@Override
		public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassId(rs.getInt("classId"));
			classInfo.setCourseId(rs.getInt("courseId"));
			classInfo.setCourseName(rs.getString("courseName"));
			classInfo.setClassName(rs.getString("className"));
			classInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			classInfo.setClassId(rs.getInt("classId"));
			return classInfo;
		}
	}
	
	@Override
	public List<ClassInfo> fetchRecommendedClassInfos(int userId, int jobRoleId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT coi.courseId,coi.courseName, ci.className, ")
				.append("coi.deliveryMethod, ci.classId ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId = ci.courseId ")
				.append("LEFT JOIN trainingflow tf ON tf.courseId = ci.courseId ")
				.append("WHERE (CASE WHEN ci.scheduleType = 1 THEN ")
				.append("(SELECT cbs.startDate FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId) ")
				.append("WHEN ci.scheduleType = 2 THEN ")
				.append("(SELECT MIN(css.startDate) FROM classsetschedule css ")
				.append("WHERE css.classId = ci.classId) ")
				.append("END) > NOW() ")
				.append("AND ci.isSelfRegister = 1 ")
				.append("OR tf.jobroleId = :jobRoleId ")
				.append("OR tf.userGroupId IN( ")
				.append("SELECT userGroupId FROM usergroupmember ugm ")
				.append("WHERE ugm.userId = :userId ) ")
				.append("GROUP BY ci.classId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("jobRoleId", jobRoleId)
				.addValue("userId", userId),
			new FetchRecommendedClassInfosMapper());
	}

	private static class FetchUserClassInfosMapper implements RowMapper<ClassInfo> {
		@Override
		public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassId(rs.getInt("classId"));
			classInfo.setClassCode(rs.getString("classCode"));
			classInfo.setCourseId(rs.getInt("courseId"));
			classInfo.setLocationId(rs.getInt("locationId"));
			classInfo.setClassName(rs.getString("className"));
			classInfo.setIsSelfRegister(rs.getInt("isSelfRegister"));
			classInfo.setWithCertificate(rs.getInt("withCertificate"));
			classInfo.setCertificateUrl(rs.getString("certificateUrl"));
			classInfo.setMinAttendee(rs.getInt("minAttendee"));
			classInfo.setMaxAttendee(rs.getInt("maxAttendee"));
			classInfo.setWithExam(rs.getInt("withExam"));
			classInfo.setScheduleType(rs.getInt("scheduleType"));
			classInfo.setIsEvaluationRequired(rs.getInt("isEvaluationRequired"));
			classInfo.setIsAnonymousSender(rs.getInt("isAnonymousSender"));
			classInfo.setViewRestrictionType(rs.getInt("viewRestrictionType"));
			classInfo.setAccessStartDate(rs.getString("accessStartDate"));
			classInfo.setAccessEndDate(rs.getString("accessEndDate"));
			classInfo.setCompletionStatus(rs.getInt("completionStatus"));
			classInfo.setCreatedAt(rs.getString("createdAt"));
			classInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return classInfo;
		}
	}
	
	@Override
	public List<ClassInfo> fetchUserClassInfos(int userId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ci.classId, ci.classCode, ci.courseId, ")
				.append("ci.locationId, ci.className, ci.isSelfRegister, ci.withCertificate, ")
				.append("ci.certificateUrl, ci.minAttendee, ci.maxAttendee, ci.withExam, ")
				.append("ci.scheduleType, ci.isEvaluationRequired, ci.isAnonymousSender, ")
				.append("ci.viewRestrictionType, ci.accessStartDate, ci.accessEndDate, ")
				.append("ci.completionStatus, ci.createdAt, ci.lastModifiedAt ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN classemployee ce ON ce.classId=ci.classId ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId=ce.employeeId ")
				.append("LEFT JOIN user u ON u.userId=ei.userId ")
				.append("WHERE u.userId = :userId AND ce.approvalStatus NOT IN (3);")
				.toString(),
			new MapSqlParameterSource()
				.addValue("userId", userId),
			new FetchUserClassInfosMapper());
	}

	private static class FetchClassInfoByIsSelfRegisterMapper implements RowMapper<ClassInfo> {
		@Override
		public ClassInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassId(rs.getInt("classId"));
			classInfo.setClassCode(rs.getString("classCode"));
			classInfo.setCourseId(rs.getInt("courseId"));
			classInfo.setLocationId(rs.getInt("locationId"));
			classInfo.setClassName(rs.getString("className"));
			classInfo.setIsSelfRegister(rs.getInt("isSelfRegister"));
			classInfo.setWithCertificate(rs.getInt("withCertificate"));
			classInfo.setCertificateUrl(rs.getString("certificateUrl"));
			classInfo.setMinAttendee(rs.getInt("minAttendee"));
			classInfo.setMaxAttendee(rs.getInt("maxAttendee"));
			classInfo.setWithExam(rs.getInt("withExam"));
			classInfo.setScheduleType(rs.getInt("scheduleType"));
			classInfo.setIsEvaluationRequired(rs.getInt("isEvaluationRequired"));
			classInfo.setIsAnonymousSender(rs.getInt("isAnonymousSender"));
			classInfo.setViewRestrictionType(rs.getInt("viewRestrictionType"));
			classInfo.setAccessStartDate(rs.getString("accessStartDate"));
			classInfo.setAccessEndDate(rs.getString("accessEndDate"));
			classInfo.setCompletionStatus(rs.getInt("completionStatus"));
			classInfo.setCreatedAt(rs.getString("createdAt"));
			classInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			classInfo.setCourseName(rs.getString("courseName"));
			classInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			return classInfo;
		}
	}
	
	@Override
	public List<ClassInfo> fetchClassInfoByIsSelfRegister(int isSelfRegister) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ci.classId, ci.classCode, ci.courseId, coi.courseName, coi.deliveryMethod, ")
				.append("ci.locationId, ci.className, ci.isSelfRegister, ci.withCertificate, ")
				.append("ci.certificateUrl, ci.minAttendee, ci.maxAttendee, ci.withExam, ")
				.append("ci.scheduleType, ci.isEvaluationRequired, ci.isAnonymousSender, ")
				.append("ci.viewRestrictionType, ci.accessStartDate, ci.accessEndDate, ")
				.append("ci.completionStatus, ci.createdAt, ci.lastModifiedAt ")
				.append("FROM classinfo ci ")
				.append("LEFT JOIN courseinfo coi ON coi.courseId=ci.courseId ")
				.append("WHERE (CASE WHEN ci.scheduleType = 1 THEN ")
				.append("(SELECT cbs.startDate FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = ci.classId) ")
				.append("WHEN ci.scheduleType = 2 THEN ")
				.append("(SELECT MIN(css.startDate) FROM classsetschedule css ")
				.append("WHERE css.classId = ci.classId) ")
				.append("END) > NOW() AND ci.isSelfRegister = :isSelfRegister;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("isSelfRegister", isSelfRegister),
			new FetchClassInfoByIsSelfRegisterMapper());
	}

	@Override
	public void updateClassEvaluationSettings(ClassInfo classInfo) {
		this.jdbcTemplate.update(
		new StringBuffer()
			.append("UPDATE classinfo SET ")
			.append("isEvaluationRequired = :isEvaluationRequired, ")
			.append("isAnonymousSender = :isAnonymousSender ")
			.append("WHERE classId = :classId ")
			.toString(),
		new MapSqlParameterSource()
			.addValue("classId", classInfo.getClassId())
			.addValue("isEvaluationRequired", classInfo.getIsEvaluationRequired())
			.addValue("isAnonymousSender", classInfo.getIsAnonymousSender()));
	}

	@Override
	public void updateClassInfoSettings(ClassInfo classInfo, TableCommand tableCommand) {
		this.jdbcTemplate.update(
		new StringBuffer()
			.append("UPDATE classinfo SET ")
			.append("classDuration = :classDuration, ")
			.append("isSelfRegister = :isSelfRegister, ")
			.append("selfRegisterType = :selfRegisterType, ")
			.append("withCertificate = :withCertificate, ")
			.append("certificateTemplateType = :certificateTemplateType, ")
			.append("isCertificateDownloadable = :isCertificateDownloadable, ")
			.append("viewRestrictionType = :viewRestrictionType, ")
			.append("accessRestrictionType = :accessRestrictionType")
			.append(tableCommand.getUpdateSetClause())
//			.append("certificateUrl = :certificateUrl, ")
//			.append("selfRegisterStartDate = :selfRegisterStartDate, ")
//			.append("selfRegisterEndDate = :selfRegisterEndDate, ")
//			.append("accessStartDate = :accessStartDate, ")
//			.append("accessEndDate = :accessEndDate ")
			.append("WHERE classId = :classId ")
			.toString(),
		new MapSqlParameterSource()
			.addValue("classId", classInfo.getClassId())
			.addValue("classDuration", classInfo.getClassDuration())
			.addValue("isSelfRegister", classInfo.getIsSelfRegister())
			.addValue("selfRegisterType", classInfo.getSelfRegisterType())
			.addValue("selfRegisterStartDate", classInfo.getSelfRegisterStartDate())
			.addValue("selfRegisterEndDate", classInfo.getSelfRegisterEndDate())
			.addValue("withCertificate", classInfo.getWithCertificate())
			.addValue("certificateTemplateType", classInfo.getCertificateTemplateType())
			.addValue("certificateUrl", classInfo.getCertificateUrl())
			.addValue("isCertificateDownloadable", classInfo.getIsCertificateDownloadable())
			.addValue("viewRestrictionType", classInfo.getViewRestrictionType())
			.addValue("accessRestrictionType", classInfo.getAccessRestrictionType())
			.addValue("accessStartDate", classInfo.getAccessStartDate())
			.addValue("accessEndDate", classInfo.getAccessEndDate()));
	}
}
