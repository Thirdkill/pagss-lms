package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.UserDao;
import com.pagss.lms.domains.User;

@Repository
public class JdbcUserDao extends JdbcDao implements UserDao {

	@Override
	public int countUser(User user) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer().append("Select COUNT(*) from user ")
		.append("where username = :username AND password = :password").toString(),
		new MapSqlParameterSource()
		.addValue("username", user.getUsername())
		.addValue("password", user.getPassword()),
		Integer.class);
	}

	private static class FetchUserMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUserId(rs.getInt("userId"));
			user.setUsername(getStringValue(rs.getString("username")));
			user.setPassword(getStringValue(rs.getString("password")));
			user.setIsPasswordReset(rs.getInt("isPasswordReset"));
			user.setStatus(rs.getInt("status"));
			user.setUserTypeId(rs.getInt("userTypeId"));
			user.setCreatedAt(getStringValue(rs.getString("createdAt")));
			user.setLastModifiedAt(getStringValue("lastModifiedAt"));
			user.setEmployeeId(rs.getInt("employeeId"));
			user.setJobroleId(rs.getInt("jobRoleId"));
			return user;
		}
	}

	@Override
	public User fetchUser(User user) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT u.userId,u.username, u.password, u.isPasswordReset, ")
				.append("u.status, u.userTypeId , u.createdAt, u.lastModifiedAt, ")
				.append("ei.employeeId,ei.jobRoleId ")
				.append("FROM user u ")
				.append("LEFT JOIN employeeinfo ei ON ei.userId=u.userId ")
				.append("where username = :username AND password = :password").toString(),
			new MapSqlParameterSource()
				.addValue("username", user.getUsername())
				.addValue("password",user.getPassword()),
			new FetchUserMapper());
	}

	private static class FetchUserTableMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUserId(rs.getInt("userId"));
			user.setLastName(getStringValue(rs.getString("lastName")));
			user.setFirstName(getStringValue(rs.getString("firstName")));
			user.setEmployeeCode(getStringValue(rs.getString("employeeCode")));
			user.setJobName(getStringValue(rs.getString("jobName")));
			user.setUsername(getStringValue(rs.getString("username")));
			user.setStatus(rs.getInt("status"));
			user.setUserTypeDesc(getStringValue(rs.getString("userTypeDesc")));
			return user;
		}
	}
	
	private static class FetchUserListMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUserId(rs.getInt("userId"));
			user.setFullName(getStringValue(rs.getString("fullName")));
			user.setEmployeeCode(getStringValue(rs.getString("employeeCode")));
			user.setJobName(getStringValue(rs.getString("jobName")));
			user.setUsername(getStringValue(rs.getString("username")));
			user.setStatus(rs.getInt("status"));
			user.setUserTypeDesc(getStringValue(rs.getString("userTypeDesc")));
			return user;
		}
	}

	@Override
	public List<User> fetchUsers(int pageSize, int pageNo) {
		return this.jdbcTemplate.query(
		new StringBuffer().append("SELECT ei.userId, ei.lastName, ei.firstName, ei.employeeCode, ")
		.append("jr.jobName, us.username, ut.userTypeId, ut.userTypeDesc, us.status ")
		.append("from employeeinfo ei ")
		.append("left join jobrole jr on jr.jobRoleId = ei.jobRoleId ")
		.append("left join user us on us.userId = ei.userId ")
		.append("left join usertype ut on ut.userTypeId = us.userTypeId ")
		.append("WHERE ei.employeeId <> 1 ")
		.append("ORDER BY ei.lastName ").append("LIMIT :pageNo,:pageSize").toString(),
		new MapSqlParameterSource()
		.addValue("pageSize", pageSize)
		.addValue("pageNo", pageNo),
		new FetchUserTableMapper());
	}
	
	@Override
	public List<User> fetchUserlist() {
		return this.jdbcTemplate.query(
		new StringBuffer().append("SELECT ei.userId, ei.fullName, ei.employeeCode, ")
		.append("jr.jobName, us.username, ut.userTypeId, ut.userTypeDesc, us.status ")
		.append("from employeeinfo ei ")
		.append("left join jobrole jr on jr.jobRoleId = ei.jobRoleId ")
		.append("left join user us on us.userId = ei.userId ")
		.append("left join usertype ut on ut.userTypeId = us.userTypeId ")
		.append("WHERE ei.employeeId <> 1 AND us.status = 1 ")
		.append("ORDER BY ei.lastName ").toString(),
		new MapSqlParameterSource(),
		new FetchUserListMapper());
	}

	@Override
	public int countTotalUsers() {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
		.append("SELECT COUNT(*) from employeeinfo ei ")
		.append("left join jobrole jr on jr.jobRoleId = ei.jobRoleId ")
		.append("left join user us on us.userId = ei.userId ")
		.append("left join usertype ut on ut.userTypeId = us.userTypeId ")
		.append("WHERE ei.employeeId <> 1 ").toString(),
		new MapSqlParameterSource(), Integer.class);
	}

	@Override
	public List<User> searchUsers(User user, int pageSize, int pageNo) {
		return this.jdbcTemplate.query(new StringBuffer()
		.append("SELECT ei.userId, ei.lastName, ei.firstName, ei.employeeCode, ")
		.append("jr.jobName, us.username, ut.userTypeId, ut.userTypeDesc, us.status ")
		.append("from employeeinfo ei ")
		.append("left join jobrole jr on jr.jobRoleId = ei.jobRoleId ")
		.append("left join user us on us.userId = ei.userId ")
		.append("left join usertype ut on ut.userTypeId = us.userTypeId ")
		.append("WHERE ((ei.firstName like :firstName AND ei.lastName like :lastName) AND ei.employeeCode like :employeeNo) ")
		.append("AND ei.employeeId <> 1 ")
		.append(user.getWhereClause())
		.append("ORDER BY ei.lastName ")
		.append("LIMIT :pageNo,:pageSize")
		.toString(),
		new MapSqlParameterSource().addValue("firstName", "%" + user.getFirstName() + "%")
		.addValue("lastName", "%" + user.getLastName() + "%")
		.addValue("employeeNo", "%" + user.getEmployeeCode() + "%")
		.addValue("jobRoleId", user.getJobroleId())
		.addValue("userTypeId", user.getUserTypeId())
		.addValue("status", user.getStatus())
		.addValue("pageSize", pageSize)
		.addValue("pageNo", pageNo),
		new FetchUserTableMapper());
	}

	@Override
	public int countTotalSearchedUsers(User user) {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
		.append("SELECT COUNT(*) from employeeinfo ei ")
		.append("left join jobrole jr on jr.jobRoleId = ei.jobRoleId ")
		.append("left join user us on us.userId = ei.userId ")
		.append("left join usertype ut on ut.userTypeId = us.userTypeId ")
		.append("WHERE ((ei.firstName like :firstName AND ei.lastName like :lastName) AND ei.employeeCode like :employeeNo) ")
		.append("AND ei.employeeId <> 1 ")
		.append(user.getWhereClause()).toString(),
		new MapSqlParameterSource()
		.addValue("firstName", "%" + user.getFirstName() + "%")
		.addValue("lastName", "%" + user.getLastName() + "%")
		.addValue("employeeNo", "%" + user.getEmployeeCode() + "%")
		.addValue("jobRoleId", user.getJobroleId() == 0 ? null : user.getJobroleId())
		.addValue("userTypeId", user.getUserTypeId() == 0 ? null : user.getUserTypeId())
		.addValue("status", user.getStatus() == 0 ? null : user.getStatus()),
		Integer.class);
	}

	private static class FetchUserInfoMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setEmployeeId(rs.getInt("employeeId"));
			user.setEmployeeCode(getStringValue(rs.getString("employeeCode")));
			user.setLastName(getStringValue(rs.getString("lastName")));
			user.setFirstName(getStringValue(rs.getString("firstName")));
			user.setMiddleInitial(getStringValue(rs.getString("middleInitial")));
			user.setFullName(getStringValue(rs.getString("fullName")));
			user.setUsername(getStringValue(rs.getString("username")));
			user.setJobroleId(rs.getInt("jobRoleId"));
			user.setJobName(getStringValue(rs.getString("jobName")));
			user.setUserTypeId(rs.getInt("userTypeId"));
			user.setUserGroupName(getStringValue(rs.getString("userGroupName")));
			user.setUserTypeDesc(getStringValue(rs.getString("userTypeDesc")));
			user.setMobileNo(getStringValue(rs.getString("mobileNo")));
			user.setDateHired(getStringValue(rs.getString("dateHired")));
			user.setMobileNo(getStringValue(rs.getString("mobileNo")));
			user.setEmail(getStringValue(rs.getString("email")));
			user.setStatus(rs.getInt("status"));
			user.setCreatedAt(getStringValue(rs.getString("createdAt")));
			user.setLastModifiedAt(getStringValue(rs.getString("lastModifiedAt")));
			user.setModifiedBy(getStringValue(rs.getString("ModifiedBy")));
			return user;
		}
	}

	@Override
	public User fetchUserInfo(User user) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
		.append("SELECT  ei.employeeId, ei.employeeCode,  ei.lastName, ei.firstName, ")
		.append("ei.middleInitial, ei.fullName, ut.userTypeId, ut.userTypeDesc, us.username, ")
		.append("ei.jobRoleId, jr.jobName, ei.dateHired, ei.mobileNo, ")
		.append("ei.email, ei.modifiedBy, us.status, ei.createdAt, ei.lastModifiedAt, ")
		.append("COALESCE(ug.userGroupName, '') AS userGroupName ")
		.append("FROM user us ")
		.append("LEFT join employeeinfo ei on ei.userId = us.userId ")
		.append("LEFT join jobrole jr on ei.jobRoleId = jr.jobroleId ")
		.append("LEFT join usertype ut on us.userTypeId = ut.userTypeId ")
		.append("LEFT join usergroupmember ugm on ugm.userId = us.userId ")
		.append("LEFT join usergroup ug ON ug.userGroupId = ugm.userGroupId ")
		.append("Where us.userId = :userId ").toString(),
		new MapSqlParameterSource().addValue("userId", user.getUserId()), 
		new FetchUserInfoMapper());
	}
	
	@Override
	public int fetchLatestUserId() {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
		.append("SELECT (IFNULL(MAX(us.userId),0)) AS userId ")
		.append("FROM user us  ")
		.toString(),
		new MapSqlParameterSource(), 
		Integer.class);
	}
	
	@Override
	public int fetchLatestEmployeeId() {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
		.append("SELECT (IFNULL(MAX(ei.employeeId),0)) as employeeId ")
		.append("FROM employeeinfo ei ")
		.toString(),
		new MapSqlParameterSource(), 
		Integer.class);
	}

	@Override
	public int updateUserInfo(User user) {
		return this.jdbcTemplate.update(
		new StringBuffer()
		.append("UPDATE user us SET ")
		.append(user.getWhereClause())
		.append(" WHERE us.userId = :userId")
		.toString(),
		new MapSqlParameterSource()
		.addValue("userTypeId", user.getUserTypeId())
		.addValue("status",user.getStatus())
		.addValue("userId",user.getUserId()));
	}
	
	@Override
	public int addUserInfo(User user) {
		return this.jdbcTemplate.update(
		new StringBuffer()
		.append("INSERT INTO user (username,password,userTypeId,status) ")
		.append("VALUES (:username, :password, :userTypeId, :status )")
		.toString(),
		new MapSqlParameterSource()
		.addValue("username", user.getUsername())
		.addValue("password", user.getPassword())
		.addValue("userTypeId",user.getUserTypeId())
		.addValue("status",user.getStatus()));
	}
	
	@Override
	public int updateUserPassword(User user) {
		return this.jdbcTemplate.update(
		new StringBuffer()
		.append("UPDATE user us SET us.password = :password ")
		.append("WHERE us.userId = :userId")
		.toString(),
		new MapSqlParameterSource()
		.addValue("password", user.getPassword())
		.addValue("userId",user.getUserId()));
	}

	@Override
	public void saveNewPassword(User user) {
		this.jdbcTemplate.update(
		new StringBuffer()
		.append("UPDATE user us SET us.password = :password, us.isPasswordReset = 0 ")
		.append("WHERE us.userId = :userId")
		.toString(),
		new MapSqlParameterSource()
		.addValue("password", user.getPassword())
		.addValue("userId",user.getUserId()));
	}
	
	private static class FetchUserListToUserGroupMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUserId(rs.getInt("userId"));
			user.setLastName(getStringValue(rs.getString("lastName")));
			user.setFirstName(getStringValue(rs.getString("firstName")));
			user.setEmployeeCode(getStringValue(rs.getString("employeeCode")));
			user.setJobName(getStringValue(rs.getString("jobName")));
			user.setStatus(rs.getInt("status"));
			return user;
		}
	}

	@Override
	public List<User> fetchSearchedUserListOnUserGroup(User user, int pageSize, int pageNo) { 
		return this.jdbcTemplate.query(
		new StringBuffer().append("SELECT ei.userId, ei.lastName, ei.firstName, ")
		.append("ei.employeeCode, jr.jobName, us.status ")
		.append("from employeeinfo ei ")
		.append("LEFT join jobrole jr on jr.jobRoleId = ei.jobRoleId ")
		.append("LEFT join user us on us.userId = ei.userId ")
		.append("WHERE (ei.firstName like :firstName OR ei.lastName like :lastName OR ei.employeeCode like :employeeNo) ")
		.append(user.getWhereClause())
		.append("AND ei.employeeId <> 1 AND us.status = 1 ")
		.append("ORDER BY ei.lastName ")
		.append("LIMIT :pageNo,:pageSize").toString(),
		new MapSqlParameterSource()
		.addValue("firstName", "%" + user.getFirstName() + "%")
		.addValue("lastName", "%" + user.getLastName() + "%")
		.addValue("employeeNo", "%" + user.getEmployeeCode() + "%")
		.addValue("jobRoleId", user.getJobroleId())
		.addValue("pageSize", pageSize)
		.addValue("pageNo", pageNo),
		new FetchUserListToUserGroupMapper());
	}
	
	@Override
	public int countTotalSearchedUserListOnUserGroup(User user) {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
		.append("SELECT COUNT(*) from employeeinfo ei ")
		.append("LEFT join jobrole jr on jr.jobRoleId = ei.jobRoleId ")
		.append("LEFT join user us on us.userId = ei.userId ")
		.append("LEFT join usertype ut on ut.userTypeId = us.userTypeId ")
		.append("WHERE (ei.firstName like :firstName OR ei.lastName like :lastName OR ei.employeeCode like :employeeNo) ")
		.append(user.getWhereClause())
		.append("AND ei.employeeId <> 1 AND us.status = 1 ").toString(),
		new MapSqlParameterSource()
		.addValue("firstName", "%" + user.getFirstName() + "%")
		.addValue("lastName", "%" + user.getLastName() + "%")
		.addValue("employeeNo", "%" + user.getEmployeeCode() + "%")
		.addValue("jobRoleId", user.getJobroleId()),
		Integer.class);
	}

	@Override
	public List<User> fetchUserListOnUserGroup(int pageSize, int calculatedPageNo) {
		return this.jdbcTemplate.query(
		new StringBuffer().append("SELECT ei.userId, ei.lastName, ei.firstName, ")
		.append("ei.employeeCode, jr.jobName, us.status ")
		.append("from employeeinfo ei ")
		.append("LEFT join jobrole jr on jr.jobRoleId = ei.jobRoleId ")
		.append("LEFT join user us on us.userId = ei.userId ")
		.append("WHERE ei.employeeId <> 1 AND us.status = 1 ")
		.append("ORDER BY ei.lastName ")
		.append("LIMIT :pageNo,:pageSize").toString(),
		new MapSqlParameterSource()
		.addValue("pageSize", pageSize)
		.addValue("pageNo", calculatedPageNo),
		new FetchUserListToUserGroupMapper());
	}

	@Override
	public int countTotalUserListOnUserGroup() {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
		.append("SELECT COUNT(*) from employeeinfo ei ")
		.append("LEFT join jobrole jr on jr.jobRoleId = ei.jobRoleId ")
		.append("LEFT join user us on us.userId = ei.userId ")
		.append("LEFT join usertype ut on ut.userTypeId = us.userTypeId ")
		.append("WHERE ei.employeeId <> 1 AND us.status = 1 ").toString(),
		new MapSqlParameterSource(),
		Integer.class);
	}

	@Override
	public int countUsername(String genUsername) {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
		.append("SELECT COUNT(*) from user us ")
		.append("WHERE us.username like :username ").toString(),
		new MapSqlParameterSource()
		.addValue("username", genUsername),
		Integer.class);
	}

	@Override
	public int countTotalRecords() {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
		.append("SELECT COUNT(*) from user us WHERE us.userId <> 1 ").toString(),
		new MapSqlParameterSource(),
		Integer.class);
	}

	@Override
	public int countTotalActiveRecords() {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
		.append("SELECT COUNT(*) from user us ")
		.append("WHERE us.status = 1 AND us.userId <> 1").toString(),
		new MapSqlParameterSource(),
		Integer.class);
	}

	@Override
	public int countTotalInactiveRecords() {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
		.append("SELECT COUNT(*) from user us ")
		.append("WHERE us.status = 0 AND us.userId <> 1").toString(),
		new MapSqlParameterSource(),
		Integer.class);
	}
	
	@Override
	public int getUserTypeId(String userTypeDesc) {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
				.append("SELECT ut.userTypeId ")
				.append("FROM usertype ut ")
				.append("WHERE ut.userTypeDesc = :userTypeDesc").toString(),
				new MapSqlParameterSource()
				.addValue("userTypeDesc", userTypeDesc), Integer.class);
	}
}
