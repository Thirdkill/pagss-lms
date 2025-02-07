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
import com.pagss.lms.dao.interfaces.ClassOtherFinancialDao;
import com.pagss.lms.domains.OtherFinance;

@Repository
public class JdbcClassOtherFinancialDao extends JdbcDao implements ClassOtherFinancialDao {

	@Override
	public int countOtherFinancialByClassId(int classId) {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
					.append("SELECT COUNT(*) as financialCount FROM otherfinance ofi ")
					.append("WHERE ofi.classId = :classId ")
					.toString(),
				new MapSqlParameterSource()
					.addValue("classId",classId), 
				Integer.class);
	}

	@Override
	public void deleteOtherFinancials(int classId) {
		this.jdbcTemplate.update(
		new StringBuffer()
		.append("DELETE ofi FROM otherfinance ofi ")
		.append("WHERE ofi.classId = :classId ")
		.toString(),
		new MapSqlParameterSource()
		.addValue("classId",classId));
	}

	@Override
	public void addOtherFinancials(List<OtherFinance> otherFinancials) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(otherFinancials.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("INSERT INTO otherfinance (classId, name, price, quantity, totalCost) ")
				.append("VALUES (:classId, :name, :price, :quantity, :totalCost) ")
				.toString(), 
			batch);
	}
	
	private static class FetchClassOtherFinancialMapper implements RowMapper<OtherFinance>{
		@Override
		public OtherFinance mapRow(ResultSet rs, int rowNum) throws SQLException {
			OtherFinance otherFinance = new OtherFinance();
			otherFinance.setOtherFinanceId(rs.getInt("otherFinanceId"));
			otherFinance.setClassId(rs.getInt("classId"));
			otherFinance.setName(getStringValue(rs.getString("name")));
			otherFinance.setPrice(rs.getBigDecimal("price"));
			otherFinance.setQuantity(rs.getInt("quantity"));
			otherFinance.setTotalCost(rs.getBigDecimal("totalCost"));
			return otherFinance;
		}
	}

	@Override
	public List<OtherFinance> fetchClassOtherFinancials(int classId) {
		return this.jdbcTemplate.query(
				new StringBuffer().append("SELECT ofi.otherFinanceId, ofi.classId, ofi.name, ")
					.append("ofi.price, ofi.quantity, ofi.totalCost ")
					.append("FROM otherfinance ofi ")
					.append("WHERE ofi.classId = :classId ").toString(),
				new MapSqlParameterSource()
					.addValue("classId", classId),
				new FetchClassOtherFinancialMapper());
	}

}
