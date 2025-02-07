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
import com.pagss.lms.dao.interfaces.ClassIpAddressDao;
import com.pagss.lms.domains.ClassIpAddress;

@Repository
public class JdbcClassIpAddressDao extends JdbcDao implements ClassIpAddressDao {
	
	private class FetchClassIpAddressPagesMapper implements RowMapper<ClassIpAddress> {
		@Override
		public ClassIpAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassIpAddress classIpAddress = new ClassIpAddress();
			classIpAddress.setClassIpId(rs.getInt("classIpId"));
			classIpAddress.setClassId(rs.getInt("classId"));
			classIpAddress.setIpAddressId(rs.getInt("ipAddressId"));
			classIpAddress.setIpAddress(rs.getString("ipAddress"));
			classIpAddress.setCreatedAt(rs.getString("createdAt"));
			classIpAddress.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return classIpAddress;
		}
	}

	@Override
	public List<ClassIpAddress> fetchClassIpAddressList(int classId, int calculatedPageNo, int pageSize) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT cip.classIpId, cip.classId, cip.ipAddressId, ")
				.append("ip.ipAddress, cip.createdAt, cip.lastModifiedAt ")
				.append("FROM classipaddress cip ")
				.append("LEFT JOIN ipaddress ip on cip.ipAddressId = ip.ipAddressId ")
				.append("WHERE cip.classId = :classId ")
				.append("LIMIT :pageNumber,:pageSize ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("pageNumber", calculatedPageNo)
				.addValue("pageSize", pageSize),
			new FetchClassIpAddressPagesMapper());
	}

	@Override
	public int countTotalClassIpAddress(int classId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(cip.classIpId) ")
				.append("FROM classipaddress cip ")
				.append("LEFT JOIN ipaddress ip on cip.ipAddressId = ip.ipAddressId ")
				.append("WHERE cip.classId = :classId ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId),
			Integer.class);
	}

	@Override
	public void addClassIpAddresses(List<ClassIpAddress> classIpAddresses) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(classIpAddresses.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
			.append("INSERT INTO classipaddress ")
			.append("(classId, ipAddressId) ")
			.append("VALUES (:classId, :ipAddressId) ")
			.toString(), 
			batch);
	}

	@Override
	public void deleteClassIpAddress(int classIpId) {
		this.jdbcTemplate.update(
		new StringBuffer()
			.append("DELETE cip FROM classipaddress cip ")
			.append("WHERE cip.classIpId = :classIpId")
			.toString(),
		new MapSqlParameterSource()
			.addValue("classIpId",classIpId));
	}

}
