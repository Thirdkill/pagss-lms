package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.EmployeeExamSummaryDao;
import com.pagss.lms.domains.EmployeeExamSummary;

@Repository
public class JdbcEmployeeExamSummaryDao extends JdbcDao implements EmployeeExamSummaryDao {
	
	private class FetchExamScoresMapper implements RowMapper<EmployeeExamSummary> {
		@Override
		public EmployeeExamSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeExamSummary employeeExamSummary = new EmployeeExamSummary();
			employeeExamSummary.setExamSummaryId(rs.getInt("examSummaryId"));
			employeeExamSummary.setExamId(rs.getInt("examId"));
			employeeExamSummary.setTitle(rs.getString("title"));
			employeeExamSummary.setFullName(rs.getString("fullName"));
			employeeExamSummary.setEmployeeCode(rs.getString("employeeCode"));
			employeeExamSummary.setJobName(rs.getString("jobName"));
			employeeExamSummary.setCompletedDate(rs.getString("completedDate"));
			employeeExamSummary.setScore(rs.getBigDecimal("score"));
			employeeExamSummary.setTrainingStatus(rs.getInt("trainingStatus"));
			return employeeExamSummary;
		}
	}

	@Override
	public List<EmployeeExamSummary> fetchExamScores(int examId, int calculatedPageNo, int pageSize) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ee.examSummaryId, ee.examId, ex.title, ei.fullName, ei.employeeCode, ")
				.append("jr.jobName, cle.completedDate, ee.score, cle.trainingStatus ")
				.append("FROM employeeexamsummary ee ")
				.append("LEFT JOIN classemployee cle ON ee.employeeId = cle.employeeId ")
				.append("LEFT JOIN employeeinfo ei ON cle.employeeId = ei.employeeId ")
				.append("LEFT JOIN jobrole jr ON cle.role = jr.jobroleId ")
				.append("LEFT JOIN examinfo ex ON ee.examId = ex.examId ")
				.append("WHERE ee.examId = :examId ")
				.append("LIMIT :pageNumber,:pageSize ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("examId", examId)
				.addValue("pageSize", pageSize)
				.addValue("pageNumber", calculatedPageNo),
			new FetchExamScoresMapper());
	}

	@Override
	public int countExamScores(int examId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ee.examSummaryId) ")
				.append("FROM employeeexamsummary ee ")
				.append("LEFT JOIN classemployee cle ON ee.employeeId = cle.employeeId ")
				.append("LEFT JOIN employeeinfo ei ON cle.employeeId = ei.employeeId ")
				.append("LEFT JOIN jobrole jr ON cle.role = jr.jobroleId ")
				.append("LEFT JOIN examinfo ex ON ee.examId = ex.examId ")
				.append("WHERE ee.examId = :examId ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("examId", examId),
			Integer.class);
	}

}
