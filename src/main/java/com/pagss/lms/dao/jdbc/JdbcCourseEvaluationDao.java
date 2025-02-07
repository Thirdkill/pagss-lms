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
import com.pagss.lms.dao.interfaces.CourseEvaluationDao;
import com.pagss.lms.domains.CourseEvaluation;
import com.pagss.lms.domains.EmployeeEvaluation;

@Repository
public class JdbcCourseEvaluationDao extends JdbcDao implements CourseEvaluationDao {

	private static class FetchCourseEvaluationTableMapper implements RowMapper<CourseEvaluation> {
		@Override
		public CourseEvaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseEvaluation courseevaluation = new CourseEvaluation();
			courseevaluation.setCourseEvaluationId(rs.getInt("courseEvaluationId"));
			courseevaluation.setCourseId(rs.getInt("courseId"));
			courseevaluation.setEvaluationId(rs.getInt("evaluationId"));
			courseevaluation.setTitle(rs.getString("title"));
			courseevaluation.setStatus(rs.getInt("status"));
			courseevaluation.setEmployeeEvaluationId(rs.getInt("employeeEvaluationId"));
			return courseevaluation;
		}
	}
	
	@Override
	public List<CourseEvaluation> fetchCourseEvaluations(int courseId, int employeeId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ce.courseEvaluationId, ce.courseId, ev.evaluationId, ev.title,ev.status, ")
				.append("    empv.employeeEvaluationId ")
				.append("FROM courseevaluation ce ")
				.append("LEFT JOIN evaluation ev ON ce.evaluationId = ev.evaluationId ")
				.append("LEFT JOIN employeeevaluation empv ON empv.employeeId = :employeeId ")
				.append("    AND empv.evaluationId IN (ce.evaluationId) ")
				.append("WHERE ce.courseId = :courseId AND ev.status > 0 ")
				.append("GROUP BY ce.courseEvaluationId")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", courseId)
				.addValue("employeeId", employeeId),
			new FetchCourseEvaluationTableMapper());
	}

	@Override
	public void deleteCourseEvaluation(int courseEvaluationId) {
		this.jdbcTemplate.update(
		new StringBuffer()
			.append("DELETE ce FROM courseevaluation ce ")
			.append("WHERE ce.courseEvaluationId = :courseEvaluationId")
			.toString(),
		new MapSqlParameterSource()
			.addValue("courseEvaluationId",courseEvaluationId));
	}

	@Override
	public void addCourseEvaluations(List<CourseEvaluation> courseEvaluations) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(courseEvaluations.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
			.append("INSERT INTO courseevaluation ")
			.append("(courseId, evaluationId) ")
			.append("VALUES (:courseId, :evaluationId) ")
			.toString(), 
			batch);
	}
	
	private static class FetchCourseEvaluationsTableMapper implements RowMapper<CourseEvaluation> {
		@Override
		public CourseEvaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseEvaluation courseevaluation = new CourseEvaluation();
			courseevaluation.setCourseEvaluationId(rs.getInt("courseEvaluationId"));
			courseevaluation.setCourseId(rs.getInt("courseId"));
			courseevaluation.setEvaluationId(rs.getInt("evaluationId"));
			courseevaluation.setTitle(rs.getString("title"));
			return courseevaluation;
		}
	}

	@Override
	public List<CourseEvaluation> fetchCourseEvaluationsTable(int courseId, int calculatedPageNo, int pageSize) {
		return this.jdbcTemplate.query(
			new StringBuffer().append("SELECT ce.courseEvaluationId, ce.courseId, ev.evaluationId, ev.title ")
			.append("FROM courseevaluation ce ")
			.append("LEFT JOIN evaluation ev ON ce.evaluationId = ev.evaluationId ")
			.append("WHERE ce.courseId = :courseId ")
			.append("ORDER BY ev.title ")
			.append("LIMIT :pageNo,:pageSize").toString(),
			new MapSqlParameterSource()
			.addValue("courseId", courseId)
			.addValue("pageSize", pageSize)
			.addValue("pageNo", calculatedPageNo),
			new FetchCourseEvaluationsTableMapper());
	}

	@Override
	public int countTotalCourseEvaluations(int courseId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
			.append("SELECT COUNT(*) FROM courseevaluation ce ")
			.append("WHERE ce.courseId = :courseId ")
			.toString(),
			new MapSqlParameterSource()
			.addValue("courseId", courseId), Integer.class);
	}

	
	private static class FetchCourseEvaluationPathTableMapper implements RowMapper<CourseEvaluation> {
		@Override
		public CourseEvaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseEvaluation courseEvaluation = new CourseEvaluation();
			courseEvaluation.setClassName(rs.getString("className"));
			courseEvaluation.setTitle(rs.getString("title"));
			courseEvaluation.setCourseName(rs.getString("courseName"));
			return courseEvaluation;
		}
	}
	
	@Override
	public CourseEvaluation fetchEmployeeCoursePath(int classId, int evaluationId) {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
					.append("SELECT course.courseName, class.className, ev.title ")
					.append("FROM classinfo class ")
					.append("LEFT JOIN courseinfo course ON class.courseId = course.courseId ")
					.append("LEFT JOIN courseevaluation ceval ON ceval.courseId = class.courseId ")
					.append("LEFT JOIN evaluation ev ON ev.evaluationId = ceval.evaluationid ")
					.append("WHERE class.classId = :classId AND ev.evaluationId = :evaluationId ")
					.toString(),
				new MapSqlParameterSource()
					.addValue("classId", classId)
					.addValue("evaluationId", evaluationId),
				new FetchCourseEvaluationPathTableMapper());
	}
	
	private static class fetchCourseEmployeeEvaluationTableMapper implements RowMapper<EmployeeEvaluation> {
		@Override
		public EmployeeEvaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeEvaluation employeeEvaluation = new EmployeeEvaluation();
			employeeEvaluation.setEmployeeEvaluationId(rs.getInt("employeeEvaluationId"));
			employeeEvaluation.setEvaluationQuestionId(rs.getInt("evaluationQuestionId"));
			employeeEvaluation.setClassId(rs.getInt("classId"));
			employeeEvaluation.setEvaluationId(rs.getInt("evaluationId"));
			employeeEvaluation.setEmployeeId(rs.getInt("employeeId"));
			employeeEvaluation.setEvaluationType(rs.getString("evaluationType"));
			employeeEvaluation.setAnswer(rs.getString("answer"));
			return employeeEvaluation;
		}
	}

	@Override
	public List<EmployeeEvaluation> fetchEmployeeEvaluation(int classId, int employeeId, int evaluationId,
			int courseId) {
		return this.jdbcTemplate.query(
				new StringBuffer()
					.append("SELECT empv.employeeEvaluationId, empv.employeeId, empv.classId, empv.evaluationId, ")
					.append("    empv.evaluationQuestionId, empv.answer, evq.evaluationType ")
					.append("FROM courseinfo coinf ")
					.append("LEFT JOIN courseevaluation ceval ON ceval.courseId =  coinf.courseId ")
					.append("LEFT JOIN classinfo clin ON clin.courseId = coinf.courseId ")
					.append("LEFT JOIN employeeevaluation empv ON  empv.evaluationId = ceval.evaluationId ")
					.append("LEFT JOIN evaluationquestion evq ON evq.evaluationQuestionId = empv.evaluationQuestionId ")
					.append("WHERE empv.evaluationId = :evaluationId AND clin.classId = :classId ")
					.append("    AND empv.employeeId = :employeeId AND coinf.courseId = :courseId").toString(),
				new MapSqlParameterSource()
					.addValue("evaluationId", evaluationId)
					.addValue("classId", classId)
					.addValue("employeeId", employeeId)
					.addValue("courseId", courseId),
				new fetchCourseEmployeeEvaluationTableMapper());
	}
}
