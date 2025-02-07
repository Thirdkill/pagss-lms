package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.JobRoleDao;
import com.pagss.lms.domains.JobRole;

@Repository
public class JdbcJobRoleDao extends JdbcDao implements JobRoleDao {

	private static class FetchJobRolesMapper implements RowMapper<JobRole> {
		@Override
		public JobRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			JobRole jobRole = new JobRole();
			jobRole.setJobRoleId(rs.getInt("jobRoleId"));
			jobRole.setJobCode(getStringValue(rs.getString("jobCode")));
			jobRole.setJobName(getStringValue(rs.getString("jobName")));
			jobRole.setJobDesc(getStringValue(rs.getString("jobDesc")));
			jobRole.setStatus(rs.getInt("status"));
			jobRole.setCreatedAt(rs.getString("createdAt"));
			jobRole.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return jobRole;
		}
	}
	
	@Override
	public List<JobRole> fetchJobRoles(int pageSize, int pageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT jr.jobRoleId, jr.jobCode, jr.jobName, jr.jobDesc, ")
				.append("jr.status, jr.createdAt, jr.lastModifiedAt ")
				.append("FROM jobrole jr ")
				.append("ORDER BY jr.jobCode ASC ")
				.append("LIMIT :pageNo,:pageSize")
				.toString(),
			new MapSqlParameterSource()
				.addValue("pageSize", pageSize)
				.addValue("pageNo", pageNo),
			new FetchJobRolesMapper());
	}

	@Override
	public int countFetchJobRoles() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) ")
				.append("FROM jobrole jr ")
				.toString(),
			new MapSqlParameterSource(),
			Integer.class);
	}

	private static class SearchJobRolesMapper implements RowMapper<JobRole> {
		@Override
		public JobRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			JobRole jobRole = new JobRole();
			jobRole.setJobRoleId(rs.getInt("jobRoleId"));
			jobRole.setJobCode(getStringValue(rs.getString("jobCode")));
			jobRole.setJobName(getStringValue(rs.getString("jobName")));
			jobRole.setJobDesc(getStringValue(rs.getString("jobDesc")));
			jobRole.setStatus(rs.getInt("status"));
			jobRole.setCreatedAt(rs.getString("createdAt"));
			jobRole.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return jobRole;
		}
	}
	
	@Override
	public List<JobRole> searchJobRole(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT jr.jobRoleId, jr.jobCode, jr.jobName, jr.jobDesc, ")
				.append("jr.status, jr.createdAt, jr.lastModifiedAt ")
				.append("FROM jobrole jr ")
				.append(tableCommand.getWhereClause())
				.append(tableCommand.getOrderClause())
				.append("LIMIT :pageNo,:pageSize")
				.toString(),
			new MapSqlParameterSource()
				.addValue("pageSize", tableCommand.getPageSize())
				.addValue("pageNo", tableCommand.getPageNumber())
				.addValue("keyword", "%"+tableCommand.getKeyword()+"%")
				.addValue("sortColumnName", "jr." + tableCommand.getSortColumnName())
				.addValue("sortDirection", tableCommand.getSortDirection()),
			new SearchJobRolesMapper());
	}

	@Override
	public int countSearchJobRole(String keyword) {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
					.append("SELECT COUNT(*) ")
					.append("FROM jobrole jr ")
					.append("WHERE jr.jobCode like :keyword OR jr.jobName like :keyword ")
					.append("OR jr.jobDesc like :keyword ")
					.toString(),
				new MapSqlParameterSource()
					.addValue("keyword", "%"+keyword+"%"),
				Integer.class);
	}
	
	@Override
	public int fetchLatestJobRoleId() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT (IFNULL(MAX(jr.jobRoleId),0)+1) AS jobRoleId ")
				.append("FROM jobrole jr ")
				.toString(),
			new MapSqlParameterSource(),
			Integer.class);
	}

	@Override
	public int countJobCode(String jobCode) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(jr.jobRoleId) ")
				.append("FROM jobrole jr ")
				.append("WHERE jr.jobCode = :jobCode;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("jobCode", jobCode),
			Integer.class);
	}
	
	@Override
	public int getRoleId(String jobCode) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT jr.jobRoleId ")
				.append("FROM jobrole jr ")
				.append("WHERE jr.jobCode = :jobCode").toString(),
			new MapSqlParameterSource()
				.addValue("jobCode", jobCode),
			Integer.class);
	}
}
