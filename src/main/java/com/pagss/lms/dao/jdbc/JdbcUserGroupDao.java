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
import com.pagss.lms.dao.interfaces.UserGroupDao;
import com.pagss.lms.domains.UserGroup;
import com.pagss.lms.domains.UserGroupMember;

@Repository
public class JdbcUserGroupDao extends JdbcDao implements UserGroupDao{
	
	private static class FetchUserGroupMapper implements RowMapper<UserGroup> {
		@Override
		public UserGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserGroup usergroup = new UserGroup();
			usergroup.setUserGroupId(rs.getInt("userGroupId"));
			usergroup.setUserGroupCode(getStringValue(rs.getString("userGroupCode")));
			usergroup.setUserGroupName(getStringValue(rs.getString("userGroupName")));
			usergroup.setDescription(getStringValue(rs.getString("description")));
			usergroup.setSupervisorId(rs.getInt("supervisorId"));
			usergroup.setStatus(rs.getInt("status"));
			usergroup.setLastModifiedAt(getStringValue(rs.getString("lastModifiedAt")));
			usergroup.setModifiedBy(getStringValue(rs.getString("modifiedBy")));
			usergroup.setFullName(getStringValue(rs.getString("fullName")));
			return usergroup;
		}
	}
	
	@Override
	public List<UserGroup> fetchUserGroup(int pageSize, int pageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ug.userGroupId, ug.userGroupCode, ug.userGroupName, ug.Description, ")
				.append("ug.supervisorId, ug.status, ug.lastModifiedAt, ")
				.append("ug.modifiedBy, ei.fullName ")
				.append("FROM usergroup ug ")
				.append("LEFT JOIN user us ON ug.supervisorId = us.userId ")
				.append("LEFT JOIN employeeinfo ei ON us.userId = ei.userId ")
				.append("ORDER BY ug.userGroupCode ")
				.append("LIMIT :pageNo,:pageSize")
				.toString(),
				new MapSqlParameterSource()
				.addValue("pageSize", pageSize)
				.addValue("pageNo", pageNo),
			new FetchUserGroupMapper());
	}
	
	@Override
	public int countTotalUserGroups() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) FROM usergroup ug ")
				.toString(),
			new MapSqlParameterSource(),
		Integer.class);
	}
	
	private static class SearchUserGroupMapper implements RowMapper<UserGroup> {
		@Override
		public UserGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserGroup usergroup = new UserGroup();
			usergroup.setUserGroupId(rs.getInt("userGroupId"));
			usergroup.setUserGroupCode(getStringValue(rs.getString("userGroupCode")));
			usergroup.setUserGroupName(getStringValue(rs.getString("userGroupName")));
			usergroup.setDescription(getStringValue(rs.getString("description")));
			usergroup.setSupervisorId(rs.getInt("supervisorId"));
			usergroup.setStatus(rs.getInt("status"));
			usergroup.setLastModifiedAt(getStringValue(rs.getString("lastModifiedAt")));
			usergroup.setModifiedBy(getStringValue(rs.getString("modifiedBy")));
			usergroup.setFullName(getStringValue(rs.getString("fullName")));
			return usergroup;
		}
	}
	
	@Override
	public List<UserGroup> searchUserGroup(String keyword, int pageSize, int pageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ug.userGroupId, ug.userGroupCode, ug.userGroupName, ug.Description, ")
				.append("ug.supervisorId, ug.status, ug.lastModifiedAt, ")
				.append("ug.modifiedBy, ei.fullName ")
				.append("FROM usergroup ug ")
				.append("LEFT JOIN user us ON ug.supervisorId = us.userId ")
				.append("LEFT JOIN employeeinfo ei ON us.userId = ei.userId ")
				.append("WHERE ug.userGroupCode like :keyword ")
				.append("OR ug.userGroupName like :keyword ")
				.append("OR ug.Description like :keyword ")
				.append("OR ei.fullName like :keyword ")
				.append("OR ug.modifiedBy like :keyword ")
				.append("ORDER BY ug.userGroupCode ")
				.append("LIMIT :pageNo,:pageSize")
				.toString(),
				new MapSqlParameterSource()
				.addValue("pageSize", pageSize)
				.addValue("pageNo", pageNo)
				.addValue("keyword", "%" + keyword + "%"),
			new SearchUserGroupMapper());
	}
	
	@Override
	public int countSearchedUserGroups(String keyword) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) FROM usergroup ug ")
				.append("LEFT JOIN user us ON ug.supervisorId = us.userId ")
				.append("LEFT JOIN employeeinfo ei ON us.userId = ei.userId ")
				.append("WHERE ug.userGroupCode like :keyword ")
				.append("OR ug.userGroupName like :keyword ")
				.append("OR ug.Description like :keyword ")
				.append("OR ei.fullName like :keyword ")
				.append("OR ug.modifiedBy like :keyword ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("keyword", "%" + keyword + "%"),
		Integer.class);
	}

	@Override
	public int countUserGroupMember(int userId) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
		.append("SELECT COUNT(*) as userCount FROM usergroupmember ")
		.append("WHERE userId = :userId ")
		.toString(),
		new MapSqlParameterSource()
		.addValue("userId",userId), 
		Integer.class);
	}

	@Override
	public void deleteMember(int userId) {
		this.jdbcTemplate.update(
		new StringBuffer()
		.append("DELETE us FROM usergroupmember us ")
		.append("WHERE us.userId = :userId")
		.toString(),
		new MapSqlParameterSource()
		.addValue("userId",userId));
	}

	@Override
	public void addMember(List<UserGroupMember> usergroupmember) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(usergroupmember.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
			.append("INSERT INTO usergroupmember (userGroupId,userId) ")
			.append("VALUES (:userGroupId, :userId)")
			.toString(), 
			batch);
	}
	
	private static class FetchUserGroupAssignmentMapper implements RowMapper<UserGroup> {
		@Override
		public UserGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserGroup usergroup = new UserGroup();
			usergroup.setUserGroupId(rs.getInt("userGroupId"));
			usergroup.setUserGroupName(getStringValue(rs.getString("userGroupName")));
			return usergroup;
		}
	}

	@Override
	public List<UserGroup> fetchUserGroupAssignment(int userId) {
		return this.jdbcTemplate.query(
		new StringBuffer()
			.append("SELECT ugm.userGroupId, ug.userGroupName ")
			.append("FROM usergroupmember ugm ")
			.append("INNER JOIN usergroup ug ON ugm.userGroupId = ug.userGroupId ")
			.append("WHERE ugm.userId = :userId;")
			.toString(),
			new MapSqlParameterSource()
			.addValue("userId", userId),
		new FetchUserGroupAssignmentMapper());
	}

	@Override
	public int fetchLatestUserGroupId() {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
				.append("SELECT (IFNULL(MAX(ug.userGroupId),0)+1) as userGroupId ")
				.append("FROM usergroup ug ")
				.toString(),
				new MapSqlParameterSource(), 
				Integer.class);
	}

	@Override
	public void addUserGroup(UserGroup usergroup) {
		this.jdbcTemplate.update(
		new StringBuffer()
		.append("INSERT INTO usergroup ")
		.append("(userGroupCode,userGroupName,description,supervisorId,status,isSubGroup,mainUserGroupCode,modifiedBy) ")
		.append("VALUES (:userGroupCode, :userGroupName, :description, :supervisorId, :status, :isSubGroup, :mainUserGroupCode, :modifiedBy)")
		.toString(),
		new MapSqlParameterSource()
		.addValue("userGroupCode",usergroup.getUserGroupCode())
		.addValue("userGroupName",usergroup.getUserGroupName())
		.addValue("description",usergroup.getDescription())
		.addValue("supervisorId",usergroup.getSupervisorId())
		.addValue("status",usergroup.getStatus())
		.addValue("isSubGroup",usergroup.getIsSubGroup())
		.addValue("mainUserGroupCode",usergroup.getMainUserGroupCode())
		.addValue("modifiedBy",usergroup.getModifiedBy()));
	}
	
	private static class FetchUserGroupListMapper implements RowMapper<UserGroup> {
		@Override
		public UserGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserGroup usergroup = new UserGroup();
			usergroup.setUserGroupId(rs.getInt("userGroupId"));
			usergroup.setUserGroupCode(getStringValue(rs.getString("userGroupCode")));
			usergroup.setUserGroupName(getStringValue(rs.getString("userGroupName")));
			usergroup.setStatus(rs.getInt("status"));
			return usergroup;
		}
	}

	@Override
	public List<UserGroup> fetchUserGroupList() {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ug.userGroupId, ug.userGroupCode, ug.userGroupName, ug.status ")
				.append("FROM usergroup ug ")
				.append("WHERE ug.status = 1 ")
				.toString(),
				new MapSqlParameterSource(),
			new FetchUserGroupListMapper());
	}

	@Override
	public int countUserGroupMemberById(int userGroupId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) FROM usergroupmember ug ")
				.append("WHERE ug.userGroupId = :userGroupId ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("userGroupId",userGroupId),
		Integer.class);
	}

	@Override
	public void deleteMemberByUserGroupId(int userGroupId) {
		this.jdbcTemplate.update(
		new StringBuffer()
			.append("DELETE us FROM usergroupmember us ")
			.append("WHERE us.userGroupId = :userGroupId")
			.toString(),
		new MapSqlParameterSource()
			.addValue("userGroupId",userGroupId));
	}
	
	private static class UserGroupInfoMapper implements RowMapper<UserGroup> {
		@Override
		public UserGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserGroup usergroup = new UserGroup();
			usergroup.setUserGroupId(rs.getInt("userGroupId"));
			usergroup.setUserGroupCode(getStringValue(rs.getString("userGroupCode")));
			usergroup.setUserGroupName(getStringValue(rs.getString("userGroupName")));
			usergroup.setDescription(getStringValue(rs.getString("description")));
			usergroup.setSupervisorId(rs.getInt("supervisorId"));
			usergroup.setStatus(rs.getInt("status"));
			usergroup.setIsSubGroup(rs.getInt("isSubGroup"));
			usergroup.setMainUserGroupCode(getStringValue(rs.getString("mainUserGroupCode")));
			return usergroup;
		}
	}

	@Override
	public UserGroup fetchUserGroupInfo(UserGroup usergroup) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT ug.userGroupId, ug.userGroupCode, ug.userGroupName, ")
				.append("ug.description, ug.supervisorId, ug.status, ")
				.append("ug.isSubGroup, ug.mainUserGroupCode ")
				.append("FROM usergroup ug ")
				.append("WHERE ug.userGroupId = :userGroupId ")
				.toString(),
				new MapSqlParameterSource()
				.addValue("userGroupId",usergroup.getUserGroupId()),
			new UserGroupInfoMapper());
		
	}
	
	private static class FetchUserGroupMemberTableMapper implements RowMapper<UserGroupMember> {
		@Override
		public UserGroupMember mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserGroupMember usergroupmember = new UserGroupMember();
			usergroupmember.setUserGroupId(rs.getInt("userGroupId"));
			usergroupmember.setUserId(rs.getInt("userId"));
			usergroupmember.setLastName(getStringValue(rs.getString("lastName")));
			usergroupmember.setFirstName(getStringValue(rs.getString("firstName")));
			usergroupmember.setEmployeeCode(getStringValue(rs.getString("employeeCode")));
			usergroupmember.setJobName(getStringValue(rs.getString("jobName")));
			return usergroupmember;
		}
	}

	@Override
	public List<UserGroupMember> fetchUserGroupMembers(int userGroupId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ug.userGroupId, ug.userId, ")
				.append("ei.lastName, ei.firstName, ei.employeeCode, jr.jobName ")
				.append("FROM lms.usergroupmember ug ")
				.append("LEFT JOIN employeeinfo ei ON ug.userId = ei.userId ")
				.append("LEFT JOIN jobrole jr ON jr.jobroleId = ei.jobRoleId ")
				.append("WHERE ug.userGroupId = :userGroupId ")
				.toString(),
				new MapSqlParameterSource()
				.addValue("userGroupId",userGroupId),
			new FetchUserGroupMemberTableMapper());
	}

	@Override
	public void updateUserGroup(UserGroup usergroup) {
		this.jdbcTemplate.update(
			new StringBuffer()
			.append("UPDATE usergroup us ")
			.append("SET userGroupName = :userGroupName, description = :description, ")
			.append("supervisorId = :supervisorId, status = :status, isSubGroup = :isSubGroup, ")
			.append("mainUserGroupCode = :mainUserGroupCode, modifiedBy = :modifiedBy ")
			.append("WHERE us.userGroupId = :userGroupId")
			.toString(),
			new MapSqlParameterSource()
			.addValue("userGroupName",usergroup.getUserGroupName())
			.addValue("description",usergroup.getDescription())
			.addValue("supervisorId",usergroup.getSupervisorId())
			.addValue("status",usergroup.getStatus())
			.addValue("isSubGroup",usergroup.getIsSubGroup())
			.addValue("mainUserGroupCode",usergroup.getMainUserGroupCode())
			.addValue("modifiedBy",usergroup.getModifiedBy())
			.addValue("userGroupId",usergroup.getUserGroupId()));
		
	}

	@Override
	public int countUserGroupCode(UserGroup usergroups) {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
		.append("SELECT COUNT(*) from usergroup ug ")
		.append("WHERE ug.userGroupCode = :userGroupCode ").toString(),
		new MapSqlParameterSource()
		.addValue("userGroupCode",usergroups.getUserGroupCode()),
		Integer.class);
	}
	
	@Override
	public int getUserGroupId(String userGroupCode) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT ug.userGroupId ")
				.append("FROM usergroup ug ")
				.append("WHERE ug.userGroupCode = :userGroupCode")
				.toString(),
			new MapSqlParameterSource()
				.addValue("userGroupCode", userGroupCode), 
			Integer.class);
	}
}
