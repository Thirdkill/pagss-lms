package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.LocationDao;
import com.pagss.lms.domains.Location;

@Repository
public class JdbcLocationDao extends JdbcDao implements LocationDao {

	private static class FetchLocationPagesMapper implements RowMapper<Location> {
		@Override
		public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
			Location location = new Location();
			location.setLocationId(rs.getInt("locationId"));
			location.setLocationCode(getStringValue(rs.getString("locationCode")));
			location.setLocationName(getStringValue(rs.getString("locationName")));
			location.setDescription(getStringValue(rs.getString("description")));
			location.setStatus(rs.getInt("status"));
			location.setCreatedAt(rs.getString("createdAt"));
			location.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return location;
		}
	}
	
	@Override
	public List<Location> fetchLocationPages(int pageSize, int pageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT l.locationId, l.locationCode, l.locationName, ")
				.append("l.description,l.status, l.createdAt, l.lastModifiedAt ")
				.append("FROM location l ")
				.append("ORDER BY l.locationCode ASC ")
				.append("LIMIT :pageNo,:pageSize")
				.toString(),
			new MapSqlParameterSource()
				.addValue("pageSize", pageSize)
				.addValue("pageNo", pageNo),
			new FetchLocationPagesMapper());
	}
	
	@Override
	public int countTotalLocations() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) FROM location l ")
				.toString(),
			new MapSqlParameterSource(),
		Integer.class);
	}

	private static class SearchLocationsMapper implements RowMapper<Location> {
		@Override
		public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
			Location location = new Location();
			location.setLocationId(rs.getInt("locationId"));
			location.setLocationCode(getStringValue(rs.getString("locationCode")));
			location.setLocationName(getStringValue(rs.getString("locationName")));
			location.setDescription(getStringValue(rs.getString("description")));
			location.setStatus(rs.getInt("status"));
			location.setCreatedAt(rs.getString("createdAt"));
			location.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return location;
		}
	}
	
	@Override
	public List<Location> searchLocations(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
				new StringBuffer()
					.append("SELECT l.locationId, l.locationCode, l.locationName, ")
					.append("l.description,l.status, l.createdAt, l.lastModifiedAt ")
					.append("FROM location l ")
					.append(tableCommand.getWhereClause())
					.append(tableCommand.getOrderClause())
					.append("LIMIT :pageNo,:pageSize")
					.toString(),
				new MapSqlParameterSource()
					.addValue("pageSize", tableCommand.getPageSize())
					.addValue("pageNo", tableCommand.getPageNumber())
					.addValue("keyword", "%"+tableCommand.getKeyword()+"%"),
				new SearchLocationsMapper());
	}

	@Override
	public int countTotalSearchedLocations(String keyword) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) FROM location l ")
				.append("WHERE l.locationCode like :keyword OR ")
				.append("locationName like :keyword OR description like :keyword ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("keyword", "%"+keyword+"%"),
		Integer.class);
	}
	
	@Override
	public int fetchLastInsertId() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT (IFNULL(MAX(l.locationId),0)+1) ")
				.append("FROM location l")
				.toString(),
			new MapSqlParameterSource(),
			Integer.class);
	}
	
	@Override
	public int countLocationCode(String locationCode) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(l.locationId) ")
				.append("FROM location l ")
				.append("WHERE l.locationCode = :locationCode")
				.toString(),
			new MapSqlParameterSource()
				.addValue("locationCode", locationCode),
			Integer.class);
	}

	private static class FetchLocationsMapper implements RowMapper<Location> {
		@Override
		public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
			Location location = new Location();
			location.setLocationId(rs.getInt("locationId"));
			location.setLocationCode(getStringValue(rs.getString("locationCode")));
			location.setLocationName(getStringValue(rs.getString("locationName")));
			location.setDescription(getStringValue(rs.getString("description")));
			location.setStatus(rs.getInt("status"));
			location.setCreatedAt(rs.getString("createdAt"));
			location.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return location;
		}
	}
	
	@Override
	public List<Location> fetchLocations() {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT l.locationId, l.locationCode, l.locationName, ")
				.append("l.description,l.status, l.createdAt, l.lastModifiedAt ")
				.append("FROM location l ")
				.append("ORDER BY l.locationCode ASC ")
				.toString(),
			new FetchLocationsMapper());
	}
	
	private static class FetchLocationsByStatusMapper implements RowMapper<Location> {
		@Override
		public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
			Location location = new Location();
			location.setLocationId(rs.getInt("locationId"));
			location.setLocationCode(getStringValue(rs.getString("locationCode")));
			location.setLocationName(getStringValue(rs.getString("locationName")));
			location.setDescription(getStringValue(rs.getString("description")));
			location.setStatus(rs.getInt("status"));
			location.setCreatedAt(rs.getString("createdAt"));
			location.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return location;
		}
	}
	
	@Override
	public List<Location> fetchLocationsByStatus(int status) {
		return this.jdbcTemplate.query(
				new StringBuffer()
					.append("SELECT l.locationId, l.locationCode, l.locationName, ")
					.append("l.description,l.status, l.createdAt, l.lastModifiedAt ")
					.append("FROM location l ")
					.append("WHERE l.status = :status ")
					.append("ORDER BY l.locationCode ASC ")
					.toString(),
				new MapSqlParameterSource()
					.addValue("status", status),
				new FetchLocationsByStatusMapper());
	}
}
