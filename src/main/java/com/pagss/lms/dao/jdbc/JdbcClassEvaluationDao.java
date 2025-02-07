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
import com.pagss.lms.dao.interfaces.ClassEvaluationDao;
import com.pagss.lms.domains.ClassEvaluation;
import com.pagss.lms.domains.EmployeeEvaluation;

@Repository
public class JdbcClassEvaluationDao extends JdbcDao implements ClassEvaluationDao {
	
	private static class FetchClassEvaluationTableMapper implements RowMapper<ClassEvaluation> {
		@Override
		public ClassEvaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassEvaluation classevaluation = new ClassEvaluation();
			classevaluation.setClassEvaluationId(rs.getInt("classEvaluationId"));
			classevaluation.setClassId(rs.getInt("classId"));
			classevaluation.setEvaluationId(rs.getInt("evaluationId"));
			classevaluation.setTitle(rs.getString("title"));
			classevaluation.setEmployeeEvaluationId(rs.getInt("employeeEvaluationId"));
			return classevaluation;
		}
	}
	
	@Override
	public List<ClassEvaluation> fetchClassEvaluationList(int classId, int calculatedPageNo, int pageSize) {
		return this.jdbcTemplate.query(
		new StringBuffer()
			.append("SELECT ce.classEvaluationId, ce.classId, ev.evaluationId, ev.title ")
			.append("FROM classevaluation ce ")
			.append("LEFT JOIN evaluation ev ON ce.evaluationId = ev.evaluationId ")
			.append("WHERE ce.classId = :classId ")
			.append("ORDER BY ev.title ")
			.append("LIMIT :pageNo,:pageSize").toString(),
		new MapSqlParameterSource()
			.addValue("classId", classId)
			.addValue("pageSize", pageSize)
			.addValue("pageNo", calculatedPageNo),
		new FetchClassEvaluationTableMapper());
	}
	
	
	
	@Override
	public List<ClassEvaluation> fetchEmployeeClassEvaluationList(int classId, int employeeId) {
		return this.jdbcTemplate.query(
		new StringBuffer()
			.append("SELECT ce.classEvaluationId, ce.classId, ev.evaluationId, ev.title, ")
			.append("empv.employeeEvaluationId ")
			.append("FROM classevaluation ce ")
			.append("LEFT JOIN evaluation ev ON ce.evaluationId = ev.evaluationId ")
			.append("LEFT JOIN employeeevaluation empv ON empv.employeeId = :employeeId ")
			.append("    AND empv.evaluationId = ce.evaluationId ")
			.append("WHERE ce.classId = :classId ")
			.append("GROUP BY ce.classEvaluationId ").toString(),
		new MapSqlParameterSource()
			.addValue("classId", classId)
			.addValue("employeeId", employeeId),
		new FetchClassEvaluationTableMapper());
	}
	
	@Override
	public int countTotalClassEvaluation(int classId) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT COUNT(*) FROM classevaluation ce ")
			.append("WHERE ce.classId = :classId ")
			.toString(),
		new MapSqlParameterSource()
			.addValue("classId", classId), Integer.class);
	}
	
	private static class FetchClassEvaluationPathTableMapper implements RowMapper<ClassEvaluation> {
		@Override
		public ClassEvaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassEvaluation classevaluation = new ClassEvaluation();
			classevaluation.setCourseName(rs.getString("courseName"));
			classevaluation.setClassName(rs.getString("className"));
			classevaluation.setTitle(rs.getString("title"));
			return classevaluation;
		}
	}
	
	public ClassEvaluation fetchEmployeeClassPath(int classId, int evaluationId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT course.courseName, class.className, ev.title, class.classId ")
				.append("FROM classinfo class ")
				.append("LEFT JOIN courseinfo course ON class.courseId = course.courseId ")
				.append("LEFT JOIN classevaluation classe ON classe.classId = class.classId ")
				.append("LEFT JOIN evaluation ev ON ev.evaluationId = classe.evaluationid ")
				.append("WHERE class.classId = :classId AND ev.evaluationId = :evaluationId ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("evaluationId", evaluationId),
			new FetchClassEvaluationPathTableMapper());
	}

	@Override
	public void addClassEvaluation(List<ClassEvaluation> classEvaluation) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(classEvaluation.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
			.append("INSERT INTO classevaluation ")
			.append("(classId, evaluationId) ")
			.append("VALUES (:classId, :evaluationId) ")
			.toString(), 
			batch);
	}

	@Override
	public void deleteClassEvaluation(int classEvaluationId) {
		this.jdbcTemplate.update(
		new StringBuffer()
			.append("DELETE ce FROM classevaluation ce ")
			.append("WHERE ce.classEvaluationId = :classEvaluationId")
			.toString(),
		new MapSqlParameterSource()
			.addValue("classEvaluationId",classEvaluationId));
	}

	private static class fetchEmployeeEvaluationTableMapper implements RowMapper<EmployeeEvaluation> {
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
	public List<EmployeeEvaluation> fetchEmployeeAnswers(int classId, int employeeId, int evaluationId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT empv.employeeEvaluationId, empv.employeeId, empv.classId, empv.evaluationId, ")
				.append("    empv.evaluationQuestionId, empv.answer, evq.evaluationType ")
				.append("FROM classinfo cinfo ")
				.append("LEFT JOIN classevaluation ceval ON ceval.classId =  cinfo.classId ")
				.append("LEFT JOIN employeeevaluation empv ON  empv.evaluationId = ceval.evaluationId ")
				.append("LEFT JOIN evaluationquestion evq ON evq.evaluationQuestionId = empv.evaluationQuestionId ")
				.append("WHERE empv.evaluationId = :evaluationId AND ceval.classId = :classId ")
				.append("    AND empv.employeeId = :employeeId ").toString(),
			new MapSqlParameterSource()
				.addValue("evaluationId", evaluationId)
				.addValue("classId", classId)
				.addValue("employeeId", employeeId),
			new fetchEmployeeEvaluationTableMapper());
	}
}
