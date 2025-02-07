package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.IPAddressDao;
import com.pagss.lms.domains.IpAddress;

@Repository
public class JdbcIPAddressDao extends JdbcDao implements IPAddressDao {

	private static class FetchIPAddressMapper implements RowMapper<IpAddress> {
		@Override
		public IpAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
			IpAddress ipaddress = new IpAddress();
			ipaddress.setIpAddressId((rs.getInt("ipAddressId")));
			ipaddress.setIpAddress(getStringValue(rs.getString("ipAddress")));
			ipaddress.setModifiedBy(getStringValue(rs.getString("modifiedBy")));
			ipaddress.setCreatedAt(getStringValue(rs.getString("createdAt")));
			return ipaddress;
		}
	}
	
	@Override
	public List<IpAddress> fetchIpAddress(int pageSize, int pageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ip.ipAddressId, ip.ipaddress, ip.createdAt, ip.modifiedBy ")
				.append("FROM ipaddress ip ")
				.append("ORDER BY ip.createdAt ")
				.append("LIMIT :pageNo,:pageSize")
				.toString(),
				new MapSqlParameterSource()
				.addValue("pageSize", pageSize)
				.addValue("pageNo", pageNo),
			new FetchIPAddressMapper()); 
	}

	@Override
	public int countTotalIpAddresses() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) FROM ipaddress ip ")
				.toString(),
			new MapSqlParameterSource(),
		Integer.class);
	}
	
	private static class SearchIpAddressMapper implements RowMapper<IpAddress> {
		@Override
		public IpAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
			IpAddress ipaddress = new IpAddress();
			ipaddress.setIpAddress(getStringValue(rs.getString("ipAddress")));
			ipaddress.setModifiedBy(getStringValue(rs.getString("modifiedBy")));
			ipaddress.setCreatedAt(getStringValue(rs.getString("createdAt")));
			return ipaddress;
		}
	}
	
	@Override
	public List<IpAddress> searchIpAddress(String keyword, int pageSize, int pageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ip.ipAddressId, ip.ipaddress, ip.createdAt, ip.modifiedBy ")
				.append("FROM ipaddress ip ")
				.append("WHERE ip.ipaddress like :keyword OR ip.modifiedBy like :keyword ")
				.append("ORDER BY ip.createdAt ")
				.append("LIMIT :pageNo,:pageSize ")
				.toString(),
				new MapSqlParameterSource()
				.addValue("pageSize", pageSize)
				.addValue("pageNo", pageNo)
				.addValue("keyword", "%" + keyword + "%"),
			new SearchIpAddressMapper()); 
	}
	
	@Override
	public int countSearchedIpAddresses(String keyword) {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
					.append("SELECT COUNT(*) ")
					.append("FROM ipaddress ip ")
					.append("WHERE ip.ipaddress like :keyword ")
					.append("OR ip.modifiedBy like :keyword ")
					.toString(),
				new MapSqlParameterSource()
					.addValue("keyword", "%" + keyword + "%"),
				Integer.class);
	}
 
	@Override
	public int countIpAddress(IpAddress ipaddress) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
		.append("SELECT COUNT(*) from ipaddress ip ")
		.append("WHERE ip.ipaddress = :ipaddress ").toString(),
		new MapSqlParameterSource()
		.addValue("ipaddress",ipaddress.getIpAddress()),
		Integer.class);
	}
	
	private static class FetchIPAddressOnClassMapper implements RowMapper<IpAddress> {
		@Override
		public IpAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
			IpAddress ipaddress = new IpAddress();
			ipaddress.setIpAddressId((rs.getInt("ipAddressId")));
			ipaddress.setIpAddress(getStringValue(rs.getString("ipAddress")));
			ipaddress.setModifiedBy(getStringValue(rs.getString("modifiedBy")));
			ipaddress.setCreatedAt(getStringValue(rs.getString("createdAt")));
			return ipaddress;
		}
	}

	@Override
	public List<IpAddress> fetchIpAddressOnClass(int classId, int pageSize, int calculatedPageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ip.ipAddressId, ip.ipaddress, ip.createdAt, ip.modifiedBy ")
				.append("FROM ipaddress ip ")
				.append("WHERE ipaddressId NOT IN ( ")
				.append("SELECT cip.ipAddressId FROM classipaddress cip ")
				.append("WHERE cip.classId = :classId) ")
				.append("ORDER BY ip.createdAt ")
				.append("LIMIT :pageNo,:pageSize")
				.toString(),
				new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("pageSize", pageSize)
				.addValue("pageNo", calculatedPageNo),
			new FetchIPAddressOnClassMapper()); 
	}

	@Override
	public int countTotalIpAddressOnClass(int classId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ip.ipAddressId) ")
				.append("FROM ipaddress ip ")
				.append("WHERE ipaddressId NOT IN ( ")
				.append("SELECT cip.ipAddressId FROM classipaddress cip ")
				.append("WHERE cip.classId = :classId) ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId),
		Integer.class);
	}
}