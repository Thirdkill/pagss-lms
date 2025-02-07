package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.TrainingFlowDao;
import com.pagss.lms.domains.CourseInfo;
import com.pagss.lms.domains.TrainingFlow;

@Repository
public class JdbcTrainingFlowDao extends JdbcDao implements TrainingFlowDao {
	
	private static class FetchTrainingFlowsPageMapper implements RowMapper<TrainingFlow> {
		@Override
		public TrainingFlow mapRow(ResultSet rs, int rowNum) throws SQLException {
			TrainingFlow trainingFlow = new TrainingFlow();
			trainingFlow.setTrainingFlowId(rs.getInt("trainingFlowId"));
			trainingFlow.setOrderNo(rs.getInt("orderNo"));
			trainingFlow.setCourseId(rs.getInt("courseId"));
			trainingFlow.setCategoryId(rs.getInt("categoryId"));
			trainingFlow.setPreCourseId(rs.getInt("preCourseId"));
			trainingFlow.setPreCourseName(rs.getString("preCourseName"));
			trainingFlow.setAssignmentType(rs.getInt("assignmentType"));
			trainingFlow.setJobRoleId(rs.getInt("jobroleId"));
			trainingFlow.setUserGroupId(rs.getInt("userGroupId"));
			trainingFlow.setIsRequired(rs.getInt("isRequired"));
			trainingFlow.setIsRecurring(rs.getInt("isRecurring"));
			trainingFlow.setInterval(rs.getInt("interval"));
			trainingFlow.setCourseCode(rs.getString("courseCode"));
			trainingFlow.setCourseName(rs.getString("courseName"));
			trainingFlow.setCategoryName(rs.getString("categoryName"));
			trainingFlow.setCreatedAt(rs.getString("createdAt"));
			trainingFlow.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return trainingFlow;
		}
	}
	
	@Override
	public List<TrainingFlow> fetchTrainingFlowsPage(int pageSize, int pageNo,TrainingFlow trainingFlow) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT tf.trainingFlowId, tf.orderNo, tf.courseId, ")
				.append("(select ci2.courseName from courseinfo ci2 WHERE ci2.courseId = tf.preCourseId) as preCourseName,")
				.append("tf.categoryId, tf.preCourseId, tf.assignmentType, ")
				.append("tf.jobroleId, tf.userGroupId, tf.isRequired, ")
				.append("tf.isRecurring, tf.interval, tf.createdAt,tf.lastModifiedAt, ")
				.append("ci.courseCode, ci.courseName, tc.categoryName ")
				.append("FROM trainingflow tf ")
				.append("LEFT JOIN courseinfo ci ON tf.courseId = ci.courseId ")
				.append("LEFT JOIN trainingcategory tc ON tf.categoryId = tc.categoryId ")
				.append("WHERE tf.assignmentType = :assignmentType ")
				.append(trainingFlow.getWhereClause())
				.append("ORDER BY tf.orderNo ASC ")
				.append("LIMIT :pageNo,:pageSize;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("pageSize", pageSize)
				.addValue("pageNo", pageNo)
				.addValue("assignmentType", trainingFlow.getAssignmentType())
				.addValue("jobRoleId", trainingFlow.getAssignmentTypeId())
				.addValue("userGroupId", trainingFlow.getAssignmentTypeId()),
			new FetchTrainingFlowsPageMapper());
	}

	@Override
	public int countFetchTrainingFlows(int assignmentType) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) ")
				.append("FROM trainingflow tf ")
				.append("LEFT JOIN courseinfo ci ON tf.courseId = ci.courseId ")
				.append("LEFT JOIN trainingcategory tc ON tf.categoryId = tc.categoryId ")
				.append("WHERE tf.assignmentType = :assignmentType ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("assignmentType", assignmentType),
			Integer.class);
	}
	
	private static class FetchTrainingFlowsMapper implements RowMapper<TrainingFlow> {
		@Override
		public TrainingFlow mapRow(ResultSet rs, int rowNum) throws SQLException {
			TrainingFlow trainingFlow = new TrainingFlow();
			trainingFlow.setTrainingFlowId(rs.getInt("trainingFlowId"));
			trainingFlow.setOrderNo(rs.getInt("orderNo"));
			trainingFlow.setCourseId(rs.getInt("courseId"));
			trainingFlow.setCategoryId(rs.getInt("categoryId"));
			trainingFlow.setPreCourseId(rs.getInt("preCourseId"));
			trainingFlow.setPreCourseName(rs.getString("preCourseName"));
			trainingFlow.setAssignmentType(rs.getInt("assignmentType"));
			trainingFlow.setJobRoleId(rs.getInt("jobroleId"));
			trainingFlow.setUserGroupId(rs.getInt("userGroupId"));
			trainingFlow.setIsRequired(rs.getInt("isRequired"));
			trainingFlow.setIsRecurring(rs.getInt("isRecurring"));
			trainingFlow.setInterval(rs.getInt("interval"));
			trainingFlow.setCourseCode(rs.getString("courseCode"));
			trainingFlow.setCourseName(rs.getString("courseName"));
			trainingFlow.setCategoryName(rs.getString("categoryName"));
			trainingFlow.setCreatedAt(rs.getString("createdAt"));
			trainingFlow.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return trainingFlow;
		}
	}
	
	@Override
	public List<TrainingFlow> fetchTrainingFlows(TrainingFlow trainingFlow) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT tf.trainingFlowId, tf.orderNo, tf.courseId, ")
				.append("tf.categoryId, tf.preCourseId, tf.assignmentType, ")
				.append("tf.jobroleId, tf.userGroupId, tf.isRequired, ")
				.append("(select ci2.courseName from courseinfo ci2 WHERE ci2.courseId = tf.preCourseId)as preCourseName, ")
				.append("tf.isRecurring, tf.interval, tf.createdAt,tf.lastModifiedAt, ")
				.append("ci.courseCode, ci.courseName, tc.categoryName ")
				.append("FROM trainingflow tf ")
				.append("LEFT JOIN courseinfo ci ON tf.courseId = ci.courseId ")
				.append("LEFT JOIN trainingcategory tc ON tf.categoryId = tc.categoryId ")
				.append("WHERE tf.assignmentType = :assignmentType ")
				.append(trainingFlow.getWhereClause())
				.append("ORDER BY tf.orderNo ASC;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("assignmentType", trainingFlow.getAssignmentType())
				.addValue("jobRoleId", trainingFlow.getAssignmentTypeId())
				.addValue("userGroupId", trainingFlow.getAssignmentTypeId()),
			new FetchTrainingFlowsMapper());
	}
	
	@Override
	public void updateTrainingFlow(TrainingFlow trainingFlow) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE trainingflow tf SET ")
				.append("tf.preCourseId = :preCourseId, ")
				.append("tf.isRequired = :isRequired, ")
				.append("tf.isRecurring = :isRecurring, ")
				.append("tf.interval = :interval, ")
				.append("tf.orderNo = :orderNo ")
				.append("WHERE trainingFlowId = :trainingFlowId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("preCourseId", trainingFlow.getPreCourseId())
				.addValue("isRequired", trainingFlow.getIsRequired())
				.addValue("isRecurring", trainingFlow.getIsRecurring())
				.addValue("interval", trainingFlow.getInterval())
				.addValue("orderNo", trainingFlow.getOrderNo())
				.addValue("trainingFlowId", trainingFlow.getTrainingFlowId()));
	}
	
	@Override
	public void updatePreCourseIds(int trainingFlowId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE trainingflow tf ")
				.append("INNER JOIN( ")
				.append("SELECT trainingflowId,courseId FROM trainingflow ")
				.append("WHERE trainingFlowId = :trainingFlowId) tf2 ON tf2.trainingFlowId = tf.trainingFlowId ")
				.append("SET tf.preCourseId = 0 ")
				.append("WHERE tf.preCourseId =  tf2.courseId ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("trainingFlowId", trainingFlowId));
	}
	

	@Override
	public void createTrainingFlows(List<CourseInfo> courseInfos) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(courseInfos.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("INSERT INTO trainingflow(")
				.append("orderNo,courseId,categoryId, ")
				.append("assignmentType,jobroleId, userGroupId,preCourseId) ")
				.append("VALUES((SELECT (IFNULL(MAX(orderNo),0)+1) FROM trainingflow tf ")
				.append("WHERE tf.assignmentType = :assignmentType AND ")
				.append("IF(:assignmentType != 1,tf.usergroupId=:userGroupId, tf.jobroleId = :jobRoleId)),")
				.append(":courseId,:categoryId,:assignmentType,:jobRoleId,:userGroupId,0)")
				.toString(), 
			batch);
	}

	private static class FetchTrainingFlowsListMapper implements RowMapper<TrainingFlow> {
		@Override
		public TrainingFlow mapRow(ResultSet rs, int rowNum) throws SQLException {
			TrainingFlow trainingFlow = new TrainingFlow();
			trainingFlow.setTrainingFlowId(rs.getInt("trainingFlowId"));
			trainingFlow.setOrderNo(rs.getInt("orderNo"));
			trainingFlow.setCourseId(rs.getInt("courseId"));
			trainingFlow.setCategoryId(rs.getInt("categoryId"));
			trainingFlow.setPreCourseId(rs.getInt("preCourseId"));
			trainingFlow.setPreCourseName(rs.getString("preCourseName"));
			trainingFlow.setAssignmentType(rs.getInt("assignmentType"));
			trainingFlow.setJobRoleId(rs.getInt("jobroleId"));
			trainingFlow.setUserGroupId(rs.getInt("userGroupId"));
			trainingFlow.setIsRequired(rs.getInt("isRequired"));
			trainingFlow.setIsRecurring(rs.getInt("isRecurring"));
			trainingFlow.setInterval(rs.getInt("interval"));
			trainingFlow.setCourseCode(rs.getString("courseCode"));
			trainingFlow.setCourseName(rs.getString("courseName"));
			trainingFlow.setCategoryName(rs.getString("categoryName"));
			trainingFlow.setCreatedAt(rs.getString("createdAt"));
			trainingFlow.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return trainingFlow;
		}
	}
	
	@Override
	public List<TrainingFlow> fetchTrainingFlowList(TrainingFlow trainingFlow) {
		return this.jdbcTemplate.query(
		new StringBuffer()
			.append("SELECT tf.trainingFlowId, tf.orderNo, ")
			.append("tf.courseId, tf.preCourseId, tf.categoryId, ")
			.append("(select ci2.courseName from courseinfo ci2 WHERE ci2.courseId = tf.preCourseId)as preCourseName, ")
			.append("tf.assignmentType, tf.jobroleId, tf.userGroupId, tf.isRequired, ")
			.append("tf.isRecurring, tf.interval, tf.createdAt,tf.lastModifiedAt, ")
			.append("ci.courseCode, ci.courseName, tc.categoryName ")
			.append("FROM trainingflow tf ")
			.append("LEFT JOIN courseinfo ci ON tf.courseId = ci.courseId ")
			.append("LEFT JOIN trainingcategory tc ON tf.categoryId = tc.categoryId ")
			.append("WHERE tf.jobroleId = :jobRoleId ")
			.append("ORDER BY tf.orderNo ASC ")
			.toString(),
		new MapSqlParameterSource()
			.addValue("jobRoleId", trainingFlow.getJobRoleId()),
		new FetchTrainingFlowsListMapper());
	}

	@Override
	public List<TrainingFlow> fetchTrainingFlowUserGroupList(TrainingFlow trainingFlow) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT tf.trainingFlowId, tf.orderNo, ")
				.append("tf.courseId, tf.preCourseId, tf.categoryId, ")
				.append("(select ci2.courseName from courseinfo ci2 WHERE ci2.courseId = tf.preCourseId)as preCourseName, ")
				.append("tf.assignmentType, tf.jobroleId, tf.userGroupId, tf.isRequired, ")
				.append("tf.isRecurring, tf.interval, tf.createdAt,tf.lastModifiedAt, ")
				.append("ci.courseCode, ci.courseName, tc.categoryName ")
				.append("FROM trainingflow tf ")
				.append("LEFT JOIN courseinfo ci ON tf.courseId = ci.courseId ")
				.append("LEFT JOIN trainingcategory tc ON tf.categoryId = tc.categoryId ")
				.append("WHERE tf.userGroupId = :userGroupId ")
				.append("ORDER BY tf.orderNo ASC ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("userGroupId", trainingFlow.getUserGroupId()),
			new FetchTrainingFlowsListMapper());
	}

	@Override
	public void updateTrainingFLowOrderNos(List<TrainingFlow> trainingFlows) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(trainingFlows.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("UPDATE trainingflow SET ")
				.append("orderNo = :orderNo ")
				.append("WHERE trainingFlowId=:trainingFlowId;")
				.toString(),
			batch);
	}

	@Override
	public void updateTrainingFlowPreCourseIds(List<TrainingFlow> trainingFlows) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(trainingFlows.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("UPDATE trainingflow SET ")
				.append("preCourseId = :preCourseId ")
				.append("WHERE orderNo>=:orderNo;")
				.toString(),
			batch);
	}
}