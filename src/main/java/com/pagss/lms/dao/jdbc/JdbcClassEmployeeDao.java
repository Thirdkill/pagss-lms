package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.constants.LmsClassEmployeeData;
import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.ClassEmployeeDao;
import com.pagss.lms.domains.ClassEmployee;

@Repository
public class JdbcClassEmployeeDao extends JdbcDao implements ClassEmployeeDao {

	@Override
	public void createClassEmployees(List<ClassEmployee> classEmployees) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(classEmployees.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("INSERT INTO classemployee(")
				.append("classId, employeeId, role, approvalStatus) ")
				.append("VALUES(:classId,:employeeId,:role,1)")
				.toString(),
			batch);
	}

	private static class FetchClassEmployeesByApprovalStatusMapper implements RowMapper<ClassEmployee> {
		@Override
		public ClassEmployee mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassEmployee classEmployee = new ClassEmployee();
			classEmployee.setClassEmployeeId(rs.getInt("classEmployeeId"));
			classEmployee.setClassId(rs.getInt("classId"));
			classEmployee.setEmployeeId(rs.getInt("employeeId"));
			classEmployee.setRole(rs.getInt("role"));
			classEmployee.setCreatedAt(rs.getString("createdAt"));
			classEmployee.setLastModifiedAt(rs.getString("lastModifiedAt"));
			classEmployee.setFullName(rs.getString("fullName"));
			classEmployee.setJobRoleId(rs.getInt("jobRoleId"));
			classEmployee.setJobName(rs.getString("jobName"));
			classEmployee.setTrainingStatus(rs.getInt("trainingStatus"));
			classEmployee.setApprovalStatus(rs.getInt("approvalStatus"));
			classEmployee.setRequestDate(getDateValue(rs.getString("requestDate"),"yyyy-MM-dd","MMM dd,yyyy"));
			classEmployee.setEnrolledDate(getDateValue(rs.getString("enrolledDate"),"yyyy-MM-dd","MMM dd,yyyy"));
			classEmployee.setApprovedDate(getDateValue(rs.getString("approvedDate"),"yyyy-MM-dd","MMM dd,yyyy"));
			classEmployee.setCompletedDate(getDateValue(rs.getString("completedDate"),"yyyy-MM-dd","MMM dd,yyyy"));
			classEmployee.setComment(rs.getString("comment"));
			classEmployee.setUserGroupName(rs.getString("userGroupName"));
			return classEmployee;
		}
	}
	
	@Override
	public List<ClassEmployee> fetchClassEmployeesByApprovalStatus(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ce.classEmployeeId, ce.classId, ce.employeeId, ")
				.append("ce.role, ce.createdAt, ce.lastModifiedAt, ei.fullName, ")
				.append("jr.jobRoleId, jr.jobName, ce.trainingStatus, ")
				.append("ce.approvalStatus, ce.requestDate, ce.enrolledDate, ")
				.append("ce.approvedDate, ce.completedDate,ce.comment,ug.userGroupName ")
				.append("FROM classemployee ce ")
				.append("LEFT JOIN employeeinfo ei ON ei.employeeId = ce.employeeId ")
				.append("LEFT JOIN jobrole jr ON jr.jobRoleId = ei.jobRoleId ")
				.append("LEFT JOIN user u ON u.userId = ei.userId ")
				.append("LEFT JOIN usergroupmember ugm ON ugm.userId = u.userId ")
				.append("LEFT JOIN usergroup ug ON ug.userGroupId = ugm.userGroupId ")
				.append("WHERE ce.classId = :classId ")
				.append(tableCommand.getWhereClause())
				.append("GROUP BY ce.employeeId ")
				.append(tableCommand.getOrderClause())
				.append("LIMIT :pageNumber,:pageSize;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", tableCommand.getClassId())
				.addValue("approvalStatus", tableCommand.getSearchByApprovalStatus())
				.addValue("role", tableCommand.getSearchByRole())
				.addValue("pageNumber", tableCommand.getPageNumber())
				.addValue("pageSize", tableCommand.getPageSize()),
			new FetchClassEmployeesByApprovalStatusMapper());
	}

	@Override
	public int countClassEmployeesByApprovalStatus(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
					.append("SELECT COUNT(ce.classEmployeeId) ")
					.append("FROM classemployee ce ")
					.append("LEFT JOIN employeeinfo ei ON ei.employeeId = ce.employeeId ")
					.append("LEFT JOIN jobrole jr ON jr.jobRoleId = ei.jobRoleId ")
					.append("WHERE ce.classId = :classId ")
					.append(tableCommand.getWhereClause())
					.toString(),
				new MapSqlParameterSource()
					.addValue("classId", tableCommand.getClassId())
					.addValue("approvalStatus", tableCommand.getSearchByApprovalStatus())
					.addValue("role", LmsClassEmployeeData.TRAINEE),
				Integer.class);
	}

	@Override
	public void updateClassEmployee(ClassEmployee classEmployee) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE classemployee SET ")
				.append("role = :role, ")
				.append("trainingStatus = :trainingStatus, ")
				.append("approvalStatus = :approvalStatus, ")
				.append("comment = :comment, ")
				.append("approvedDate = NOW() ")
				.append("WHERE classEmployeeId = :classEmployeeId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("role", classEmployee.getRole())
				.addValue("trainingStatus", classEmployee.getTrainingStatus())
				.addValue("approvalStatus", classEmployee.getApprovalStatus())
				.addValue("comment", classEmployee.getComment())
				.addValue("classEmployeeId", classEmployee.getClassEmployeeId()));
	}

	@Override
	public void updateClassInfoTrainingStatus(List<ClassEmployee> classEmployees) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(classEmployees.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("UPDATE classemployee SET ")
				.append("trainingStatus = :trainingStatus ")
				.append("WHERE classEmployeeId = :classEmployeeId;")
				.toString(),
			batch);
	}

	@Override
	public void updateApprovalStatus(ClassEmployee classEmployee) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE classemployee SET ")
				.append("approvalStatus = :approvalStatus ")
				.append("WHERE classEmployeeId = :classEmployeeId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classEmployeeId", classEmployee.getClassEmployeeId())
				.addValue("approvalStatus", classEmployee.getApprovalStatus()));
	}

	@Override
	public void createClassEmployee(ClassEmployee classEmployee) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO classemployee(")
				.append("classId, employeeId, role, trainingStatus, ")
				.append("approvalStatus, requestDate) VALUES(")
				.append(":classId, :employeeId, :role, :trainingStatus, ")
				.append(":approvalStatus,CURDATE());")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classEmployee.getClassId())
				.addValue("employeeId", classEmployee.getEmployeeId())
				.addValue("role", LmsClassEmployeeData.TRAINEE)
				.addValue("trainingStatus", LmsClassEmployeeData.TRAININGSTATUS_NOT_YET_STARTED)
				.addValue("approvalStatus", LmsClassEmployeeData.ONGOING));
	}

	@Override
	public int countClassEmployeeByEmployeeId(int classId,int employeeId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ce.employeeId) ")
				.append("FROM classemployee ce ")
				.append("WHERE ce.classId=:classId AND ce.employeeId=:employeeId ")
				.append("AND ce.approvalStatus = :approvalStatus;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("employeeId", employeeId)
				.addValue("approvalStatus", LmsClassEmployeeData.ONGOING),
			Integer.class);
	}

	@Override
	public int countClassEmployeeWithOngoingClasses(int classId, int employeeId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ce.employeeId) ")
				.append("FROM classemployee ce ")
				.append("WHERE ce.classId=:classId AND ce.employeeId=:employeeId ")
				.append("AND ce.approvalStatus IN(1,3);")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("employeeId", employeeId),
			Integer.class);
	}

	@Override
	public void updateTrainingStatus(int classId, int employeeId,int trainingStatus) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE classemployee SET ")
				.append("trainingStatus = :trainingStatus ")
				.append("WHERE classId=:classId AND employeeId=:employeeId ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("employeeId", employeeId)
				.addValue("trainingStatus", trainingStatus));
	}
}
