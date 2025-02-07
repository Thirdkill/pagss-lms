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
import com.pagss.lms.dao.interfaces.ClassTrainerFinancialDao;
import com.pagss.lms.domains.TrainerFinance;


@Repository
public class JdbcClassTrainerFinancialDao extends JdbcDao implements ClassTrainerFinancialDao {

	@Override
	public int countTrainerFinancialByClassId(int classId) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT COUNT(*) as financialCount FROM trainerfinance ")
			.append("WHERE classId = :classId ")
			.toString(),
		new MapSqlParameterSource()
			.addValue("classId",classId), 
		Integer.class);
	}

	@Override
	public void deleteTrainerFinancials(int classId) {
		this.jdbcTemplate.update(
		new StringBuffer()
		.append("DELETE tf FROM trainerfinance tf ")
		.append("WHERE classId = :classId ")
		.toString(),
		new MapSqlParameterSource()
		.addValue("classId",classId));
	}

	@Override
	public void addTrainerFinancials(List<TrainerFinance> trainerFinancials) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(trainerFinancials.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("INSERT INTO trainerfinance (classId, employeeId, noOfDays, trainerRate, totalCost) ")
				.append("VALUES (:classId, :employeeId, :noOfDays, :trainerRate, :totalCost) ")
				.toString(), 
			batch);
	}
	
	private static class FetchClassTrainerFinancialMapper implements RowMapper<TrainerFinance>{
		@Override
		public TrainerFinance mapRow(ResultSet rs, int rowNum) throws SQLException {
			TrainerFinance trainerFinance = new TrainerFinance();
			trainerFinance.setTrainerFinanceId(rs.getInt("trainerFinanceId"));
			trainerFinance.setClassId(rs.getInt("classId"));
			trainerFinance.setEmployeeId(rs.getInt("employeeId"));
			trainerFinance.setNoOfDays(rs.getInt("noOfDays"));
			trainerFinance.setTrainerRate(rs.getBigDecimal("trainerRate"));
			trainerFinance.setTotalCost(rs.getBigDecimal("totalCost"));
			return trainerFinance;
		}
	}

	@Override
	public List<TrainerFinance> fetchClassTrainerFinancials(int classId) {
		return this.jdbcTemplate.query(
			new StringBuffer().append("SELECT tf.trainerFinanceId, tf.classId, tf.employeeId, ")
				.append("tf.noOfDays, tf.trainerRate, tf.totalCost ")
				.append("from trainerfinance tf ")
				.append("WHERE tf.classId = :classId ").toString(),
			new MapSqlParameterSource()
			.addValue("classId", classId),
			new FetchClassTrainerFinancialMapper());
	}

}
