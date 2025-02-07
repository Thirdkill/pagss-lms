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
import com.pagss.lms.dao.interfaces.ClassMaterialFinancialDao;
import com.pagss.lms.domains.MaterialFinance;

@Repository
public class JdbcClassMaterialFinancialDao extends JdbcDao implements ClassMaterialFinancialDao {

	@Override
	public int countMaterialFinancialByClassId(int classId) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT COUNT(*) as financialCount FROM materialfinance ")
			.append("WHERE classId = :classId ")
			.toString(),
		new MapSqlParameterSource()
			.addValue("classId",classId), 
		Integer.class);
	}

	@Override
	public void deleteMaterialFinancials(int classId) {
		this.jdbcTemplate.update(
		new StringBuffer()
		.append("DELETE mf FROM materialfinance mf ")
		.append("WHERE classId = :classId ")
		.toString(),
		new MapSqlParameterSource()
		.addValue("classId",classId));
	}

	@Override
	public void addMaterialFinancials(List<MaterialFinance> materialFinancials) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(materialFinancials.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("INSERT INTO materialfinance (classId, name, price, quantity, totalCost) ")
				.append("VALUES (:classId, :name, :price, :quantity, :totalCost) ")
				.toString(), 
			batch);
	}
	
	private static class FetchClassMaterialFinancialMapper implements RowMapper<MaterialFinance>{
		@Override
		public MaterialFinance mapRow(ResultSet rs, int rowNum) throws SQLException {
			MaterialFinance materialFinance = new MaterialFinance();
			materialFinance.setMaterialFinanceId(rs.getInt("materialFinanceId"));
			materialFinance.setClassId(rs.getInt("classId"));
			materialFinance.setName(getStringValue(rs.getString("name")));
			materialFinance.setPrice(rs.getBigDecimal("price"));
			materialFinance.setQuantity(rs.getInt("quantity"));
			materialFinance.setTotalCost(rs.getBigDecimal("totalCost"));
			return materialFinance;
		}
	}

	@Override
	public List<MaterialFinance> fetchClassMaterialFinancials(int classId) {
		return this.jdbcTemplate.query(
			new StringBuffer().append("SELECT mf.materialFinanceId, mf.classId, mf.name, ")
				.append("mf.price, mf.quantity, mf.totalCost ")
				.append("from materialfinance mf ")
				.append("WHERE mf.classId = :classId ").toString(),
			new MapSqlParameterSource()
			.addValue("classId", classId),
			new FetchClassMaterialFinancialMapper());
	}

}
