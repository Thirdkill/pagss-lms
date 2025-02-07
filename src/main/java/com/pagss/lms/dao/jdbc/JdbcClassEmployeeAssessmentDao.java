package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.ClassEmployeeAssessmentDao;
import com.pagss.lms.domains.ClassEmployeeAssessment;

@Repository
public class JdbcClassEmployeeAssessmentDao extends JdbcDao implements ClassEmployeeAssessmentDao {

	@Override
	public int countEmployeeGradingComponent(ClassEmployeeAssessment classEmployeeAssessment) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) ")
				.append("FROM classemployeeassessment cea ")
				.append("WHERE cea.classId = :classId AND ")
				.append("cea.employeeId = :employeeId AND ")
				.append("cea.gradingComponentId = :gradingComponentId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId",classEmployeeAssessment.getClassId())
				.addValue("employeeId", classEmployeeAssessment.getEmployeeId())
				.addValue("gradingComponentId", classEmployeeAssessment.getGradingComponentId()),
			Integer.class);
	}

	@Override
	public void createClassEmployeeAssessment(ClassEmployeeAssessment classEmployeeAssessment) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO classemployeeassessment(")
				.append("classId, employeeId, gradingComponentId, ")
				.append("score, dateFinished, status) VALUES(")
				.append(":classId, :employeeId, :gradingComponentId, ")
				.append(":score, NOW(),1);")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classEmployeeAssessment.getClassId())
				.addValue("employeeId", classEmployeeAssessment.getEmployeeId())
				.addValue("gradingComponentId", classEmployeeAssessment.getGradingComponentId())
				.addValue("score", classEmployeeAssessment.getScore()));
	}

	@Override
	public void updateClassEmployeeAssessment(ClassEmployeeAssessment classEmployeeAssessment) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE classemployeeassessment SET ")
				.append("score = :score, ")
				.append("dateFinished = NOW(), ")
				.append("status = :status ")
				.append("WHERE classId = :classId AND ")
				.append("employeeId = :employeeId AND ")
				.append("gradingComponentId = :gradingComponentId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("score", classEmployeeAssessment.getScore())
				.addValue("status", classEmployeeAssessment.getStatus())
				.addValue("classId", classEmployeeAssessment.getClassId())
				.addValue("employeeId", classEmployeeAssessment.getEmployeeId())
				.addValue("gradingComponentId", classEmployeeAssessment.getGradingComponentId()));
	}

	private static class FetchClassEmployeeAssessmentMapper implements RowMapper<ClassEmployeeAssessment> {
		@Override
		public ClassEmployeeAssessment mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassEmployeeAssessment classEmployeeAssessment = new ClassEmployeeAssessment();
			classEmployeeAssessment.setClassEmploymentAssessmentId(rs.getInt("classEmployeeAssessmentId"));
			classEmployeeAssessment.setClassId(rs.getInt("classId"));
			classEmployeeAssessment.setEmployeeId(rs.getInt("employeeId"));
			classEmployeeAssessment.setGradingComponentId(rs.getInt("gradingComponentId"));
			classEmployeeAssessment.setScore(rs.getBigDecimal("score"));
			classEmployeeAssessment.setDateFinished(rs.getString("dateFinished"));
			classEmployeeAssessment.setStatus(rs.getInt("status"));
			classEmployeeAssessment.setCreatedAt(rs.getString("createdAt"));
			classEmployeeAssessment.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return classEmployeeAssessment;
		}
	}
	
	@Override
	public List<ClassEmployeeAssessment> fetchClassEmployeeAssessment(int classId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT cea.classEmployeeAssessmentId, ")
				.append("cea.classId, cea.employeeId, ")
				.append("cea.gradingComponentId, ")
				.append("cea.score, cea.dateFinished, ")
				.append("cea.status, cea.createdAt, ")
				.append("cea.lastModifiedAt ")
				.append("FROM classemployeeassessment cea ")
				.append("WHERE cea.classId = :classId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId),
			new FetchClassEmployeeAssessmentMapper());
	}
}
