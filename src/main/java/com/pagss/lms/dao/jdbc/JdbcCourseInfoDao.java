package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.CourseInfoDao;
import com.pagss.lms.domains.CourseInfo;

@Repository
public class JdbcCourseInfoDao extends JdbcDao implements CourseInfoDao {

	private static class FetchCourseInfoPagesMapper implements RowMapper<CourseInfo> {
		@Override
		public CourseInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseInfo courseInfo = new CourseInfo();
			courseInfo.setCourseId(rs.getInt("courseId"));
			courseInfo.setCategoryId(rs.getInt("categoryId"));
			courseInfo.setCategoryName(rs.getString("categoryName"));
			courseInfo.setCourseCode(rs.getString("courseCode"));
			courseInfo.setCourseName(rs.getString("courseName"));
			courseInfo.setDescription(rs.getString("description"));
			courseInfo.setObjective(rs.getString("objective"));
			courseInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			courseInfo.setStatus(rs.getInt("status"));
			courseInfo.setModifiedBy(rs.getString("modifiedBy"));
			courseInfo.setCreatedAt(getDateValue(rs.getString("createdAt"), "yyyy-MM-dd", "MMM dd,yyyy"));
			courseInfo.setLastModifiedAt(getDateValue(rs.getString("lastModifiedAt"), "yyyy-MM-dd", "MMM dd,yyyy"));
			return courseInfo;
		}
	}
	
	@Override
	public List<CourseInfo> fetchCourseInfoPages(int pageNumber, int pageSize) {
		return this.jdbcTemplate.query(
		new StringBuffer()
			.append("SELECT ci.courseId, ci.categoryId, ci.courseCode, ")
			.append("ci.courseName, ci.description, ci.objective, ci.deliveryMethod, ")
			.append("ci.status, ci.modifiedBy, ci.createdAt, ci.lastModifiedAt, tc.categoryName ")
			.append("FROM courseinfo ci ")
			.append("LEFT JOIN trainingcategory tc ON tc.categoryId = ci.categoryId ")
			.append("ORDER BY ci.courseName ASC ")
			.append("LIMIT :pageNumber,:pageSize ")
			.toString(),
		new MapSqlParameterSource()
			.addValue("pageNumber", pageNumber)
			.addValue("pageSize", pageSize),
		new FetchCourseInfoPagesMapper());
	}

	@Override
	public int countFetchCourseInfos() {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT COUNT(*) ")
			.append("FROM courseinfo ci ")
			.append("LEFT JOIN trainingcategory tc ON tc.categoryId = ci.categoryId ")
			.toString(),
		new MapSqlParameterSource(),
		Integer.class);
	}
	
	private static class SearchCourseInfosMapper implements RowMapper<CourseInfo> {
		@Override
		public CourseInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseInfo courseInfo = new CourseInfo();
			courseInfo.setCourseId(rs.getInt("courseId"));
			courseInfo.setCategoryId(rs.getInt("categoryId"));
			courseInfo.setCategoryName(rs.getString("categoryName"));
			courseInfo.setCourseCode(rs.getString("courseCode"));
			courseInfo.setCourseName(rs.getString("courseName"));
			courseInfo.setDescription(rs.getString("description"));
			courseInfo.setObjective(rs.getString("objective"));
			courseInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			courseInfo.setStatus(rs.getInt("status"));
			courseInfo.setModifiedBy(rs.getString("modifiedBy"));
			courseInfo.setCreatedAt(rs.getString("createdAt"));
			courseInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return courseInfo;
		}
	}
	
	@Override
	public List<CourseInfo> searchCourseInfos(int pageNumber, int pageSize,String keyword,int deliveryMethod) {
		return this.jdbcTemplate.query(
		new StringBuffer()
			.append("SELECT ci.courseId, ci.categoryId, ci.courseCode, ")
			.append("ci.courseName, ci.description, ci.objective, ci.deliveryMethod, ")
			.append("ci.status, ci.modifiedBy, ci.createdAt, ci.lastModifiedAt, tc.categoryName ")
			.append("FROM courseinfo ci ")
			.append("LEFT JOIN trainingcategory tc ON tc.categoryId = ci.categoryId ")
			.append("WHERE ci.courseCode like :keyword OR ci.courseName like :keyword OR ")
			.append("tc.categoryName like :keyword OR ci.deliveryMethod like :deliveryMethod ")
			.append("OR ci.description like :keyword ")
			.append("ORDER BY ci.courseCode ASC ")
			.append("LIMIT :pageNumber,:pageSize ")
			.toString(),
		new MapSqlParameterSource()
			.addValue("pageNumber", pageNumber)
			.addValue("pageSize", pageSize)
			.addValue("keyword", "%"+keyword+"%")
			.addValue("deliveryMethod", deliveryMethod),
		new SearchCourseInfosMapper());
	}

	@Override
	public int countSearchCourseInfos(String keyword) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT COUNT(*) ")
			.append("FROM courseinfo ci ")
			.append("LEFT JOIN trainingcategory tc ON tc.categoryId = ci.categoryId ")
			.append("WHERE ci.courseCode like :keyword OR ci.courseName like :keyword OR ")
			.append("tc.categoryName like :keyword ")
			.toString(),
		new MapSqlParameterSource()
			.addValue("keyword", "%"+keyword+"%"),
		Integer.class);
	}

	@Override
	public int createCourseInfo(CourseInfo courseInfo) {
		KeyHolder questionIdHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(
		new StringBuffer()
			.append("INSERT INTO courseinfo( ")
			.append("categoryId, courseCode, courseName, ")
			.append("description, objective, deliveryMethod, ")
			.append("status, modifiedBy) VALUES(")
			.append(":categoryId, :courseCode, :courseName, ")
			.append(":description,:objective,:deliveryMethod, ")
			.append(":status,:modifiedBy)")
			.toString(),
		new MapSqlParameterSource()
			.addValue("categoryId", courseInfo.getCategoryId())
			.addValue("courseCode", courseInfo.getCourseCode())
			.addValue("courseName", courseInfo.getCourseName())
			.addValue("description", courseInfo.getDescription())
			.addValue("objective", courseInfo.getObjective())
			.addValue("deliveryMethod", courseInfo.getDeliveryMethod())
			.addValue("status", courseInfo.getStatus())
			.addValue("modifiedBy", courseInfo.getModifiedBy()),
		questionIdHolder);
		return questionIdHolder.getKey().intValue();
	}

	@Override
	public int fetchLatestId() {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT (IFNULL(MAX(ci.courseId),0)+1) AS courseId ")
			.append("FROM courseinfo ci ")
			.toString(),
		new MapSqlParameterSource(),
		Integer.class);
	}

	@Override
	public int countCourseCode(String courseCode) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT COUNT(ci.courseCode) ")
			.append("FROM courseinfo ci ")
			.append("WHERE ci.courseCode = :courseCode;")
			.toString(),
		new MapSqlParameterSource()
			.addValue("courseCode", courseCode),
		Integer.class);
	}

	private static class FetchCourseInfoMapper implements RowMapper<CourseInfo> {
		@Override
		public CourseInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseInfo courseInfo = new CourseInfo();
			courseInfo.setCourseId(rs.getInt("courseId"));
			courseInfo.setCourseCode(rs.getString("courseCode"));
			courseInfo.setCategoryId(rs.getInt("categoryId"));
			courseInfo.setCourseName(rs.getString("courseName"));
			courseInfo.setDescription(rs.getString("description"));
			courseInfo.setObjective(rs.getString("objective"));
			courseInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			courseInfo.setStatus(rs.getInt("status"));
			courseInfo.setModifiedBy(rs.getString("modifiedBy"));
			courseInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			courseInfo.setCreatedAt(rs.getString("createdAt"));
			courseInfo.setClassDefaultId(rs.getInt("classDefaultId"));
			courseInfo.setEmployeeId(rs.getInt("employeeId"));
			courseInfo.setLocationId(rs.getInt("locationId"));
			courseInfo.setIsSelfRegister(rs.getInt("isSelfRegister"));
			courseInfo.setWithCertificate(rs.getInt("withCertificate"));
			courseInfo.setMinAttendee(rs.getInt("minAttendee"));
			courseInfo.setMaxAttendee(rs.getInt("maxAttendee"));
			courseInfo.setWithExam(rs.getInt("withExam"));
			courseInfo.setScheduleType(rs.getString("scheduleType"));
			courseInfo.setClassPhotoUrl(rs.getString("classPhotoUrl"));
			courseInfo.setPhotoFileName(rs.getString("photoFileName"));
			courseInfo.setPassingGrade(rs.getInt("passingGrade"));
			return courseInfo;
		}
	}
	
	@Override
	public CourseInfo fetchCourseInfo(int courseId) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT ci.courseId, ci.courseCode, ")
			.append("ci.categoryId, ci.courseName, ci.description, ")
			.append("ci.objective, ci.deliveryMethod, ")
			.append("ci.status,ci.modifiedBy,ci.createdAt, ")
			.append("ci.lastModifiedAt, cd.classDefaultId, ")
			.append("cd.employeeId, cd.locationId, cd.isSelfRegister, ")
			.append("cd.withCertificate, cd.minAttendee, cd.maxAttendee, ")
			.append("cd.withExam, cd.scheduleType, cd.classPhotoUrl, ")
			.append("cd.photoFileName,ci.passingGrade ")
			.append("FROM courseinfo ci ")
			.append("LEFT JOIN classdefault cd ON cd.courseId=ci.courseId ")
			.append("WHERE ci.courseId = :courseId;")
			.toString(),
		new MapSqlParameterSource()
			.addValue("courseId", courseId),
		new FetchCourseInfoMapper());
	}

	@Override
	public void updateCourseInfo(CourseInfo courseInfo) {
		this.jdbcTemplate.update(
		new StringBuffer()
			.append("UPDATE courseinfo SET ")
			.append("categoryId=:categoryId, ")
			.append("courseName=:courseName, description=:description, ")
			.append("objective=:objective, deliveryMethod=:deliveryMethod, ")
			.append("status=:status, modifiedBy=:modifiedBy, passingGrade=:passingGrade ")
			.append("WHERE courseId=:courseId")
			.toString(),
		new MapSqlParameterSource()
			.addValue("categoryId", courseInfo.getCategoryId())
			.addValue("courseName", courseInfo.getCourseName())
			.addValue("description", courseInfo.getDescription())
			.addValue("objective", courseInfo.getObjective())
			.addValue("deliveryMethod", courseInfo.getDeliveryMethod())
			.addValue("status", courseInfo.getStatus())
			.addValue("modifiedBy", courseInfo.getModifiedBy())
			.addValue("courseId", courseInfo.getCourseId())
			.addValue("passingGrade", courseInfo.getPassingGrade()));
	}
	
	private static class FetchCourseInfoPagesWithStatusMapper implements RowMapper<CourseInfo> {
		@Override
		public CourseInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseInfo courseInfo = new CourseInfo();
			courseInfo.setCourseId(rs.getInt("courseId"));
			courseInfo.setCategoryId(rs.getInt("categoryId"));
			courseInfo.setCategoryName(rs.getString("categoryName"));
			courseInfo.setCourseCode(rs.getString("courseCode"));
			courseInfo.setCourseName(rs.getString("courseName"));
			courseInfo.setDescription(rs.getString("description"));
			courseInfo.setObjective(rs.getString("objective"));
			courseInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			courseInfo.setStatus(rs.getInt("status"));
			courseInfo.setModifiedBy(rs.getString("modifiedBy"));
			courseInfo.setCreatedAt(getDateValue(rs.getString("createdAt"), "yyyy-MM-dd", "MMM dd,yyyy"));
			courseInfo.setLastModifiedAt(getDateValue(rs.getString("lastModifiedAt"), "yyyy-MM-dd", "MMM dd,yyyy"));
			return courseInfo;
		}
	}
	
	@Override
	public List<CourseInfo> fetchCourseInfoPagesWithStatus(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
		new StringBuffer()
			.append("SELECT ci.courseId, ci.categoryId, ci.courseCode, ")
			.append("ci.courseName, ci.description, ci.objective, ci.deliveryMethod, ")
			.append("ci.status, ci.modifiedBy, ci.createdAt, ci.lastModifiedAt, tc.categoryName ")
			.append("FROM courseinfo ci ")
			.append("LEFT JOIN trainingcategory tc ON tc.categoryId = ci.categoryId ")
			.append("WHERE ci.status = :status ")
			.append("AND ci.courseId NOT IN(")
			.append("SELECT tf.courseId FROM trainingflow tf ")
			.append("WHERE tf.courseId=ci.courseId ")
			.append(tableCommand.getWhereClause())
			.append("ORDER BY ci.courseName ASC ")
			.append("LIMIT :pageNumber,:pageSize ")
			.toString(),
		new MapSqlParameterSource()
			.addValue("pageNumber", tableCommand.getPageNumber())
			.addValue("pageSize", tableCommand.getPageSize())
			.addValue("status", tableCommand.getSearchByStatus())
			.addValue("jobRoleId", tableCommand.getAssignmentTypeId())
			.addValue("userGroupId", tableCommand.getAssignmentTypeId()),
		new FetchCourseInfoPagesWithStatusMapper());
	}
	
	@Override
	public int countFetchCourseInfosWithStatus(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT COUNT(*) ")
			.append("FROM courseinfo ci ")
			.append("LEFT JOIN trainingcategory tc ON tc.categoryId = ci.categoryId ")
			.append("WHERE ci.status = :status ")
			.append("AND ci.courseId NOT IN(")
			.append("SELECT tf.courseId FROM trainingflow tf ")
			.append("WHERE tf.courseId=ci.courseId ")
			.append(tableCommand.getWhereClause())
			.toString(),
		new MapSqlParameterSource()
			.addValue("status", tableCommand.getSearchByStatus())
			.addValue("jobRoleId", tableCommand.getAssignmentTypeId())
			.addValue("userGroupId", tableCommand.getAssignmentTypeId()),
		Integer.class);
	}
	
	private static class FetchCourseInfoListMapper implements RowMapper<CourseInfo> {
		@Override
		public CourseInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseInfo courseInfo = new CourseInfo();
			courseInfo.setCourseId(rs.getInt("courseId"));
			courseInfo.setCourseName(rs.getString("courseName"));
			return courseInfo;
		}
	}

	@Override
	public List<CourseInfo> fetchActiveCourseInfoList() {
		return this.jdbcTemplate.query(
		new StringBuffer()
			.append("SELECT ci.courseId, ci.courseName ")
			.append("FROM courseinfo ci ")
			.append("WHERE ci.status = 1 ")
			.toString(),
		new MapSqlParameterSource(),
		new FetchCourseInfoListMapper());
	}
	
	private static class SearchTrainingFlowCourseInfosMapper implements RowMapper<CourseInfo> {
		@Override
		public CourseInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseInfo courseInfo = new CourseInfo();
			courseInfo.setCourseId(rs.getInt("courseId"));
			courseInfo.setCategoryId(rs.getInt("categoryId"));
			courseInfo.setCategoryName(rs.getString("categoryName"));
			courseInfo.setCourseCode(rs.getString("courseCode"));
			courseInfo.setCourseName(rs.getString("courseName"));
			courseInfo.setDescription(rs.getString("description"));
			courseInfo.setObjective(rs.getString("objective"));
			courseInfo.setDeliveryMethod(rs.getInt("deliveryMethod"));
			courseInfo.setStatus(rs.getInt("status"));
			courseInfo.setModifiedBy(rs.getString("modifiedBy"));
			courseInfo.setCreatedAt(rs.getString("createdAt"));
			courseInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return courseInfo;
		}
	}
	
	@Override
	public List<CourseInfo> searchTrainingFlowCourseInfos(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
		new StringBuffer()
			.append("SELECT ci.courseId, ci.categoryId, ci.courseCode, ")
			.append("ci.courseName, ci.description, ci.objective, ci.deliveryMethod, ")
			.append("ci.status, ci.modifiedBy, ci.createdAt, ci.lastModifiedAt, tc.categoryName ")
			.append("FROM courseinfo ci ")
			.append("LEFT JOIN trainingcategory tc ON tc.categoryId = ci.categoryId ")
			.append("WHERE ci.status = :status ")
			.append("AND ci.courseId IN(SELECT ci2.courseId ")
			.append("FROM courseinfo ci2 ")
			.append("LEFT JOIN trainingcategory tc2 ON tc2.categoryId = ci2.categoryId ")
			.append("WHERE ci2.courseCode like :keyword ")
			.append("OR ci2.courseName like :keyword OR ")
			.append("tc2.categoryName like :keyword OR ")
			.append("ci2.deliveryMethod like :deliveryMethod ")
			.append("OR ci2.description like :keyword) ")
			.append("AND ci.courseId NOT IN(")
			.append("SELECT tf.courseId FROM trainingflow tf ")
			.append("WHERE tf.courseId=ci.courseId ")
			.append(tableCommand.getWhereClause())
			.append(") ORDER BY ci.courseCode ASC ")
			.append("LIMIT :pageNumber,:pageSize ")
			.toString(),
		new MapSqlParameterSource()
			.addValue("pageNumber", tableCommand.getPageNumber())
			.addValue("pageSize", tableCommand.getPageSize())
			.addValue("keyword", "%"+tableCommand.getKeyword()+"%")
			.addValue("deliveryMethod", tableCommand.getDeliveryMethod())
			.addValue("status", tableCommand.getSearchByStatus())
			.addValue("jobRoleId", tableCommand.getAssignmentTypeId())
			.addValue("userGroupId", tableCommand.getAssignmentTypeId()),
		new SearchTrainingFlowCourseInfosMapper());
	}

	@Override
	public int countTrainingFlowCourseInfos(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT COUNT(*) ")
			.append("FROM courseinfo ci ")
			.append("LEFT JOIN trainingcategory tc ON tc.categoryId = ci.categoryId ")
			.append("WHERE ci.status = :status AND ci.courseId IN(SELECT ci2.courseId ")
			.append("FROM courseinfo ci2 ")
			.append("LEFT JOIN trainingcategory tc2 ON tc2.categoryId = ci2.categoryId ")
			.append("WHERE ci2.courseCode = :keyword ")
			.append("OR ci2.courseName like :keyword OR ")
			.append("tc2.categoryName like :keyword OR ")
			.append("ci2.deliveryMethod like :deliveryMethod ")
			.append("OR ci2.description like :keyword) ")
			.append("AND ci.courseId NOT IN(")
			.append("SELECT tf.courseId FROM trainingflow tf ")
			.append("WHERE tf.courseId=ci.courseId ")
			.append(tableCommand.getWhereClause())
			.toString(),
		new MapSqlParameterSource()
			.addValue("keyword", "%"+tableCommand.getKeyword()+"%")
			.addValue("deliveryMethod", tableCommand.getDeliveryMethod())
			.addValue("status", tableCommand.getSearchByStatus())
			.addValue("jobRoleId", tableCommand.getAssignmentTypeId())
			.addValue("userGroupId", tableCommand.getAssignmentTypeId()),
		Integer.class);
	}
	
	@Override
	public int countFetchCourseInfosWithStatus(int status) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT COUNT(*) ")
			.append("FROM courseinfo ci ")
			.append("LEFT JOIN trainingcategory tc ON tc.categoryId = ci.categoryId ")
			.append("WHERE ci.status = :status;")
			.toString(),
		new MapSqlParameterSource()
			.addValue("status", status),
		Integer.class);
	}
}
