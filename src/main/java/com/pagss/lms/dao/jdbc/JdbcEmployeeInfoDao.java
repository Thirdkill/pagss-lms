package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.constants.LmsUserData;
import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.EmployeeInfoDao;
import com.pagss.lms.domains.EmployeeInfo;

@Repository
public class JdbcEmployeeInfoDao extends JdbcDao implements EmployeeInfoDao {
	
	private static class FetchEmployeeInfoMapper implements RowMapper<EmployeeInfo> {
		@Override
		public EmployeeInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeInfo empInfo = new EmployeeInfo();
			empInfo.setEmployeeId(rs.getInt("employeeId"));
			empInfo.setEmployeeCode(getStringValue(rs.getString("employeeCode")));
			empInfo.setUserId(rs.getInt("userId"));
			empInfo.setJobRoleId(rs.getInt("jobRoleId"));
			empInfo.setLastName(getStringValue(rs.getString("lastName")));
			empInfo.setFirstName(getStringValue(rs.getString("firstName")));
			empInfo.setMiddleInitial(getStringValue(rs.getString("middleInitial")));
			empInfo.setFullName(getStringValue(rs.getString("fullName")));
			empInfo.setDateHired(getStringValue(rs.getString("dateHired")));
			empInfo.setMobileNo(getStringValue(rs.getString("mobileNo")));
			empInfo.setEmail(getStringValue(rs.getString("email")));
			return empInfo;
		}
	}

	@Override
	public EmployeeInfo fetchEmployeeInfo(EmployeeInfo empinfo) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT ei.employeeId, ei.employeeCode, ei.userId, ei.jobRoleId, ")
				.append("ei.lastName, ei.firstName, ei.middleInitial, ei.fullName, ")
				.append("ei.dateHired, ei.mobileNo, ei.email ")
				.append("FROM employeeinfo ei ")
				.append("LEFT JOIN user us ON ei.userId = us.userId ")
				.append("where ei.userId = :userId")
				.toString(),
			new MapSqlParameterSource()
				.addValue("userId", empinfo.getUserId()),
			new FetchEmployeeInfoMapper());
	}

	@Override
	public int countEmployeeCode(EmployeeInfo empinfo) {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
		.append("SELECT COUNT(*) from employeeinfo ei ")
		.append("WHERE ei.employeeCode = :employeeNo ").toString(),
		new MapSqlParameterSource()
		.addValue("employeeNo",empinfo.getEmployeeCode()),
		Integer.class);
	}

	private static class fetchTrainersMapper implements RowMapper<EmployeeInfo> {
		@Override
		public EmployeeInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeInfo empInfo = new EmployeeInfo();
			empInfo.setEmployeeId(rs.getInt("employeeId"));
			empInfo.setEmployeeCode(getStringValue(rs.getString("employeeCode")));
			empInfo.setUserId(rs.getInt("userId"));
			empInfo.setJobRoleId(rs.getInt("jobRoleId"));
			empInfo.setLastName(getStringValue(rs.getString("lastName")));
			empInfo.setFirstName(getStringValue(rs.getString("firstName")));
			empInfo.setMiddleInitial(getStringValue(rs.getString("middleInitial")));
			empInfo.setFullName(getStringValue(rs.getString("fullName")));
			empInfo.setDateHired(getStringValue(rs.getString("dateHired")));
			empInfo.setMobileNo(getStringValue(rs.getString("mobileNo")));
			empInfo.setEmail(getStringValue(rs.getString("email")));
			return empInfo;
		}
	}
	
	@Override
	public List<EmployeeInfo> fetchTrainers() {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ei.employeeId, ei.employeeCode, ei.userId, ei.jobRoleId, ")
				.append("ei.lastName, ei.firstName, ei.middleInitial, ei.fullName, ")
				.append("ei.dateHired, ei.mobileNo, ei.email ")
				.append("FROM employeeinfo ei ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("WHERE u.userTypeId IN(1,2) ")
				.append("AND ei.employeeId <> 1 ")
				.append("ORDER BY ei.lastName ASC;")
				.toString(),
			new MapSqlParameterSource(),
			new fetchTrainersMapper());
	}

	private static class fetchEmployeeInfoByUserIdMapper implements RowMapper<EmployeeInfo> {
		@Override
		public EmployeeInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeInfo empInfo = new EmployeeInfo();
			empInfo.setEmployeeId(rs.getInt("employeeId"));
			empInfo.setEmployeeCode(getStringValue(rs.getString("employeeCode")));
			empInfo.setUserId(rs.getInt("userId"));
			empInfo.setJobRoleId(rs.getInt("jobRoleId"));
			empInfo.setLastName(getStringValue(rs.getString("lastName")));
			empInfo.setFirstName(getStringValue(rs.getString("firstName")));
			empInfo.setMiddleInitial(getStringValue(rs.getString("middleInitial")));
			empInfo.setFullName(getStringValue(rs.getString("fullName")));
			empInfo.setDateHired(getStringValue(rs.getString("dateHired")));
			empInfo.setMobileNo(getStringValue(rs.getString("mobileNo")));
			empInfo.setEmail(getStringValue(rs.getString("email")));
			return empInfo;
		}
	}
	
	@Override
	public EmployeeInfo fetchEmployeeInfoByUserId(int userId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT ei.employeeId, ei.employeeCode, ei.userId, ei.jobRoleId, ")
				.append("ei.lastName, ei.firstName, ei.middleInitial, ei.fullName, ")
				.append("ei.dateHired, ei.mobileNo, ei.email ")
				.append("FROM employeeinfo ei ")
				.append("LEFT JOIN user us ON ei.userId = us.userId ")
				.append("where ei.userId = :userId ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("userId",userId),
			new fetchEmployeeInfoByUserIdMapper());
	}
	
	private static class fetchTrainersByStatusMapper implements RowMapper<EmployeeInfo> {
		@Override
		public EmployeeInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeInfo empInfo = new EmployeeInfo();
			empInfo.setEmployeeId(rs.getInt("employeeId"));
			empInfo.setEmployeeCode(getStringValue(rs.getString("employeeCode")));
			empInfo.setUserId(rs.getInt("userId"));
			empInfo.setJobRoleId(rs.getInt("jobRoleId"));
			empInfo.setLastName(getStringValue(rs.getString("lastName")));
			empInfo.setFirstName(getStringValue(rs.getString("firstName")));
			empInfo.setMiddleInitial(getStringValue(rs.getString("middleInitial")));
			empInfo.setFullName(getStringValue(rs.getString("fullName")));
			empInfo.setDateHired(getStringValue(rs.getString("dateHired")));
			empInfo.setMobileNo(getStringValue(rs.getString("mobileNo")));
			empInfo.setEmail(getStringValue(rs.getString("email")));
			return empInfo;
		}
	}
	
	@Override
	public List<EmployeeInfo> fetchTrainersByStatus(int status) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ei.employeeId, ei.employeeCode, ei.userId, ei.jobRoleId, ")
				.append("ei.lastName, ei.firstName, ei.middleInitial, ei.fullName, ")
				.append("ei.dateHired, ei.mobileNo, ei.email ")
				.append("FROM employeeinfo ei ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("WHERE u.userTypeId IN(1,2) ")
				.append("AND ei.employeeId <> 1 ")
				.append("AND u.status = :status ")
				.append("ORDER BY ei.lastName ASC;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("status", status),
			new fetchTrainersByStatusMapper());
	}

	private static class FetchEmployeeInfoWithoutClassMapper implements RowMapper<EmployeeInfo> {
		@Override
		public EmployeeInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeInfo empInfo = new EmployeeInfo();
			empInfo.setEmployeeId(rs.getInt("employeeId"));
			empInfo.setEmployeeCode(getStringValue(rs.getString("employeeCode")));
			empInfo.setUserId(rs.getInt("userId"));
			empInfo.setJobRoleId(rs.getInt("jobRoleId"));
			empInfo.setLastName(getStringValue(rs.getString("lastName")));
			empInfo.setFirstName(getStringValue(rs.getString("firstName")));
			empInfo.setMiddleInitial(getStringValue(rs.getString("middleInitial")));
			empInfo.setFullName(getStringValue(rs.getString("fullName")));
			empInfo.setDateHired(getStringValue(rs.getString("dateHired")));
			empInfo.setMobileNo(getStringValue(rs.getString("mobileNo")));
			empInfo.setEmail(getStringValue(rs.getString("email")));
			empInfo.setJobName(rs.getString("jobName"));
			return empInfo;
		}
	}
	
	@Override
	public List<EmployeeInfo> fetchEmployeeInfoWithoutClass(int classId,int pageNumber,int pageSize) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ei.employeeId, ei.employeeCode, ei.userId, ei.jobRoleId, ")
				.append("jr.jobName,ei.lastName, ei.firstName, ei.middleInitial, ei.fullName, ")
				.append("ei.dateHired, ei.mobileNo, ei.email ")
				.append("FROM employeeinfo ei ")
				.append("LEFT JOIN user u ON u.userId=ei.userId ")
				.append("LEFT JOIN jobrole jr ON jr.jobRoleId = ei.jobRoleId ")
				.append("WHERE ei.employeeId NOT IN(")
				.append("SELECT ce.employeeId ")
				.append("FROM classemployee ce ")
				.append("WHERE ce.classId = :classId AND ")
				.append("ce.approvalStatus IN(1,3) AND ")
				.append("ce.trainingStatus NOT IN(3,4)) AND u.status= :status ")
				.append("ORDER BY ei.fullName ASC ")
				.append("LIMIT :pageNumber,:pageSize")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("pageNumber", pageNumber)
				.addValue("pageSize", pageSize)
				.addValue("status", LmsUserData.ACTIVE),
			new FetchEmployeeInfoWithoutClassMapper());
	}

	@Override
	public int countEmployeeInfoWithoutClass(int classId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ei.employeeId) ")
				.append("FROM employeeinfo ei ")
				.append("LEFT JOIN jobrole jr ON jr.jobRoleId = ei.jobRoleId ")
				.append("WHERE ei.employeeId NOT IN(")
				.append("SELECT ce.employeeId ")
				.append("FROM classemployee ce ")
				.append("WHERE ce.classId = :classId AND ")
				.append("ce.approvalStatus IN(1,3) AND ")
				.append("ce.trainingStatus NOT IN(3,4));")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId),
			Integer.class);
	}

	private static class SearchEmployeeInfoByJobRoleIdMapper implements RowMapper<EmployeeInfo> {
		@Override
		public EmployeeInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeInfo empInfo = new EmployeeInfo();
			empInfo.setEmployeeId(rs.getInt("employeeId"));
			empInfo.setEmployeeCode(getStringValue(rs.getString("employeeCode")));
			empInfo.setUserId(rs.getInt("userId"));
			empInfo.setJobRoleId(rs.getInt("jobRoleId"));
			empInfo.setLastName(getStringValue(rs.getString("lastName")));
			empInfo.setFirstName(getStringValue(rs.getString("firstName")));
			empInfo.setMiddleInitial(getStringValue(rs.getString("middleInitial")));
			empInfo.setFullName(getStringValue(rs.getString("fullName")));
			empInfo.setDateHired(getStringValue(rs.getString("dateHired")));
			empInfo.setMobileNo(getStringValue(rs.getString("mobileNo")));
			empInfo.setEmail(getStringValue(rs.getString("email")));
			empInfo.setJobName(rs.getString("jobName"));
			return empInfo;
		}
	}
	
	@Override
	public List<EmployeeInfo> searchEmployeeInfoByJobRoleId(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ei.employeeId, ei.employeeCode, ei.userId, ei.jobRoleId, ")
				.append("jr.jobName,ei.lastName, ei.firstName, ei.middleInitial, ei.fullName, ")
				.append("ei.dateHired, ei.mobileNo, ei.email ")
				.append("FROM employeeinfo ei ")
				.append("LEFT JOIN jobrole jr ON jr.jobRoleId = ei.jobRoleId ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("WHERE ei.employeeId NOT IN(")
				.append("SELECT ce.employeeId ")
				.append("FROM classemployee ce ")
				.append("WHERE ce.classId = :classId AND ")
				.append("ce.approvalStatus IN(1,3) AND ")
				.append("ce.trainingStatus NOT IN(3,4)) ")
				.append("AND ei.jobRoleId = :jobRoleId ")
				.append(tableCommand.getWhereClause())
				.append("GROUP BY ei.employeeId ")
				.append("ORDER BY ei.fullName ASC ")
				.append("LIMIT :pageNumber,:pageSize")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", tableCommand.getClassId())
				.addValue("pageNumber", tableCommand.getPageNumber())
				.addValue("pageSize", tableCommand.getPageSize())
				.addValue("jobRoleId", tableCommand.getJobRoleId())
				.addValue("keyword","%"+tableCommand.getKeyword()+"%")
				.addValue("status", LmsUserData.ACTIVE),
			new SearchEmployeeInfoByJobRoleIdMapper());
	}

	@Override
	public int countSearchEmployeeInfoByJobRoleId(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ei.employeeId) ")
				.append("FROM employeeinfo ei ")
				.append("LEFT JOIN jobrole jr ON jr.jobRoleId = ei.jobRoleId ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("WHERE ei.employeeId NOT IN(")
				.append("SELECT ce.employeeId ")
				.append("FROM classemployee ce ")
				.append("WHERE ce.classId = :classId AND ")
				.append("ce.approvalStatus IN(1,3) AND ")
				.append("ce.trainingStatus NOT IN(3,4)) ")
				.append("AND ei.jobRoleId = :jobRoleId ")
				.append(tableCommand.getWhereClause())
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", tableCommand.getClassId())
				.addValue("jobRoleId", tableCommand.getJobRoleId())
				.addValue("keyword","%"+tableCommand.getKeyword()+"%")
				.addValue("status", LmsUserData.ACTIVE),
			Integer.class);
	}
	
	private static class SearchEmployeeInfoByUserGroupIdMapper implements RowMapper<EmployeeInfo> {
		@Override
		public EmployeeInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeInfo empInfo = new EmployeeInfo();
			empInfo.setEmployeeId(rs.getInt("employeeId"));
			empInfo.setEmployeeCode(getStringValue(rs.getString("employeeCode")));
			empInfo.setUserId(rs.getInt("userId"));
			empInfo.setJobRoleId(rs.getInt("jobRoleId"));
			empInfo.setLastName(getStringValue(rs.getString("lastName")));
			empInfo.setFirstName(getStringValue(rs.getString("firstName")));
			empInfo.setMiddleInitial(getStringValue(rs.getString("middleInitial")));
			empInfo.setFullName(getStringValue(rs.getString("fullName")));
			empInfo.setDateHired(getStringValue(rs.getString("dateHired")));
			empInfo.setMobileNo(getStringValue(rs.getString("mobileNo")));
			empInfo.setEmail(getStringValue(rs.getString("email")));
			empInfo.setUserGroupName(rs.getString("userGroupName"));
			return empInfo;
		}
	}
	
	@Override
	public List<EmployeeInfo> searchEmployeeInfoByUserGroupId(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ei.employeeId, ei.employeeCode, ei.userId, ei.jobRoleId, ")
				.append("ei.lastName, ei.firstName, ei.middleInitial, ei.fullName, ")
				.append("ei.dateHired, ei.mobileNo, ei.email,ug.userGroupName ")
				.append("FROM employeeinfo ei ")
				.append("LEFT JOIN jobrole jr ON jr.jobRoleId = ei.jobRoleId ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("LEFT JOIN usergroupmember ugm ON ugm.userId = ei.userId ")
				.append("LEFT JOIN usergroup ug ON ug.userGroupId = ugm.userGroupId ")
				.append("WHERE ei.employeeId NOT IN(")
				.append("SELECT ce.employeeId ")
				.append("FROM classemployee ce ")
				.append("WHERE ce.classId = :classId AND ")
				.append("ce.approvalStatus IN(1,3) AND ")
				.append("ce.trainingStatus NOT IN(3,4)) ")
				.append("AND ugm.userGroupId = :userGroupId ")
				.append(tableCommand.getWhereClause())
				.append("ORDER BY ei.fullName ASC ")
				.append("LIMIT :pageNumber,:pageSize")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", tableCommand.getClassId())
				.addValue("pageNumber", tableCommand.getPageNumber())
				.addValue("pageSize", tableCommand.getPageSize())
				.addValue("userGroupId", tableCommand.getUserGroupId())
				.addValue("keyword","%"+tableCommand.getKeyword()+"%")
				.addValue("status", LmsUserData.ACTIVE),
			new SearchEmployeeInfoByUserGroupIdMapper());
	}
	
	@Override
	public int countSearchEmployeeInfoByUserGroupId(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ei.employeeId) ")
				.append("FROM employeeinfo ei ")
				.append("LEFT JOIN jobrole jr ON jr.jobRoleId = ei.jobRoleId ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("WHERE ei.employeeId NOT IN(")
				.append("SELECT ce.employeeId ")
				.append("FROM classemployee ce ")
				.append("WHERE ce.classId = :classId AND ")
				.append("ce.approvalStatus IN(1,3) AND ")
				.append("ce.trainingStatus NOT IN(3,4)) ")
				.append("AND ei.userId IN (")
				.append("SELECT ugm.userId FROM usergroupmember ugm ")
				.append("WHERE ugm.userGroupId = :userGroupId) ")
				.append(tableCommand.getWhereClause())
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", tableCommand.getClassId())
				.addValue("userGroupId", tableCommand.getUserGroupId())
				.addValue("keyword","%"+tableCommand.getKeyword()+"%")
				.addValue("status", LmsUserData.ACTIVE),
			Integer.class);
	}
	
	private static class SearchEmployeeInfoMapper implements RowMapper<EmployeeInfo> {
		@Override
		public EmployeeInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeInfo empInfo = new EmployeeInfo();
			empInfo.setEmployeeId(rs.getInt("employeeId"));
			empInfo.setEmployeeCode(getStringValue(rs.getString("employeeCode")));
			empInfo.setUserId(rs.getInt("userId"));
			empInfo.setJobRoleId(rs.getInt("jobRoleId"));
			empInfo.setLastName(getStringValue(rs.getString("lastName")));
			empInfo.setFirstName(getStringValue(rs.getString("firstName")));
			empInfo.setMiddleInitial(getStringValue(rs.getString("middleInitial")));
			empInfo.setFullName(getStringValue(rs.getString("fullName")));
			empInfo.setDateHired(getStringValue(rs.getString("dateHired")));
			empInfo.setMobileNo(getStringValue(rs.getString("mobileNo")));
			empInfo.setEmail(getStringValue(rs.getString("email")));
			return empInfo;
		}
	}
	
	@Override
	public List<EmployeeInfo> searchEmployeeInfo(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ei.employeeId, ei.employeeCode, ei.userId, ei.jobRoleId, ")
				.append("ei.lastName, ei.firstName, ei.middleInitial, ei.fullName, ")
				.append("ei.dateHired, ei.mobileNo, ei.email ")
				.append("FROM employeeinfo ei ")
				.append("LEFT JOIN jobrole jr ON jr.jobRoleId = ei.jobRoleId ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("WHERE ei.employeeId NOT IN(")
				.append("SELECT ce.employeeId ")
				.append("FROM classemployee ce ")
				.append("WHERE ce.classId = :classId AND ")
				.append("ce.approvalStatus IN(1,3) AND ")
				.append("ce.trainingStatus NOT IN(3,4)) ")
				.append(tableCommand.getWhereClause())
				.append("ORDER BY ei.fullName ASC ")
				.append("LIMIT :pageNumber,:pageSize")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", tableCommand.getClassId())
				.addValue("pageNumber", tableCommand.getPageNumber())
				.addValue("pageSize", tableCommand.getPageSize())
				.addValue("keyword","%"+tableCommand.getKeyword()+"%")
				.addValue("status", LmsUserData.ACTIVE),
			new SearchEmployeeInfoMapper());
	}

	@Override
	public int countSearchEmployeeInfo(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ei.employeeId) ")
				.append("FROM employeeinfo ei ")
				.append("LEFT JOIN jobrole jr ON jr.jobRoleId = ei.jobRoleId ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("WHERE ei.employeeId NOT IN(")
				.append("SELECT ce.employeeId ")
				.append("FROM classemployee ce ")
				.append("WHERE ce.classId = :classId AND ")
				.append("ce.approvalStatus IN(1,3) AND ")
				.append("ce.trainingStatus NOT IN(3,4)) ")
				.append(tableCommand.getWhereClause())
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", tableCommand.getClassId())
				.addValue("pageNumber", tableCommand.getPageNumber())
				.addValue("pageSize", tableCommand.getPageSize())
				.addValue("keyword","%"+tableCommand.getKeyword()+"%")
				.addValue("status", LmsUserData.ACTIVE),
			Integer.class);
	}
}
