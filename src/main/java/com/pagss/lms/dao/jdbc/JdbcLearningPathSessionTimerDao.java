package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.LearningPathSessionTimerDao;
import com.pagss.lms.domains.LearningPathSessionTimer;

@Repository
public class JdbcLearningPathSessionTimerDao extends JdbcDao implements LearningPathSessionTimerDao {

	private static class FetchLearningPathSessionTimer implements RowMapper<LearningPathSessionTimer> {
		@Override
		public LearningPathSessionTimer mapRow(ResultSet rs, int rowNum) throws SQLException {
			LearningPathSessionTimer learningPathSessionTimer = new LearningPathSessionTimer();
			learningPathSessionTimer.setLearningPathSessionTimerId(rs.getInt("learningPathSessionTimerId"));
			learningPathSessionTimer.setClassId(rs.getInt("classId"));
			learningPathSessionTimer.setEmployeeId(rs.getInt("employeeId"));
			learningPathSessionTimer.setLearningPathId(rs.getInt("learningPathId"));
			learningPathSessionTimer.setTimeStarted(rs.getString("timeStarted"));
			learningPathSessionTimer.setTimeEnded(rs.getString("timeEnded"));
			learningPathSessionTimer.setTimeSpent(rs.getInt("timeSpent"));
			learningPathSessionTimer.setSessionStatus(rs.getInt("sessionStatus"));
			learningPathSessionTimer.setLastAccessed(getDateValue(rs.getString("lastAccessed"),"yyyy-MM-dd","MMMM dd, yyyy"));
			learningPathSessionTimer.setCreatedAt(rs.getString("createdAt"));
			learningPathSessionTimer.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return learningPathSessionTimer;
		}
	}
	
	@Override
	public List<LearningPathSessionTimer> fetchLearningPathSessionTimers(int classId, int employeeId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT lpst.learningPathSessionTimerId, ")
				.append("lpst.classId, lpst.employeeId, lpst.learningPathId, ")
				.append("lpst.timeStarted, lpst.timeEnded, lpst.timeSpent, ")
				.append("lpst.sessionStatus, lpst.lastAccessed, lpst.createdAt, lpst.lastModifiedAt ")
				.append("FROM learningpathsessiontimer lpst ")
				.append("WHERE lpst.classId = :classId AND lpst.employeeId=:employeeId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("employeeId", employeeId),
			new FetchLearningPathSessionTimer());
	}

	@Override
	public int countLearningPathSessionTimer(int classId, int employeeId, int learningPathId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(lpst.learningPathSessionTimerId) ")
				.append("FROM learningPathSessionTimer lpst ")
				.append("WHERE lpst.classId=:classId AND lpst.employeeId=:employeeId ")
				.append("AND lpst.learningPathId=:learningPathId;")
				.toString(), 
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("employeeId", employeeId)
				.addValue("learningPathId", learningPathId),
			Integer.class);
	}

	@Override
	public void createLearningPathSessionTimer(LearningPathSessionTimer learningPathSessionTimer) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO learningpathsessiontimer(")
				.append("classId, employeeId, learningPathId, ")
				.append("timeStarted, timeEnded, sessionStatus, ")
				.append("lastAccessed) VALUES(")
				.append(":classId, :employeeId, :learningPathId, ")
				.append(":timeStarted, :timeEnded, :sessionStatus, ")
				.append(":lastAccessed)")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", learningPathSessionTimer.getClassId())
				.addValue("employeeId", learningPathSessionTimer.getEmployeeId())
				.addValue("learningPathId", learningPathSessionTimer.getLearningPathId())
				.addValue("timeStarted", learningPathSessionTimer.getTimeStarted())
				.addValue("timeEnded", learningPathSessionTimer.getTimeEnded())
				.addValue("sessionStatus", learningPathSessionTimer.getSessionStatus())
				.addValue("lastAccessed", learningPathSessionTimer.getLastAccessed()));
	}

	@Override
	public void updateLearningPathSessionTimer(LearningPathSessionTimer learningPathSessionTimer) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE learningpathsessiontimer SET ")
				.append("timeStarted = :timeStarted, ")
				.append("sessionStatus = :sessionStatus, ")
				.append("lastAccessed = :lastAccessed ")
				.append("WHERE classId = :classId AND ")
				.append("employeeId = :employeeId AND ")
				.append("learningPathId = :learningPathId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("timeStarted", learningPathSessionTimer.getTimeStarted())
				.addValue("sessionStatus", learningPathSessionTimer.getSessionStatus())
				.addValue("lastAccessed", learningPathSessionTimer.getLastAccessed())
				.addValue("classId", learningPathSessionTimer.getClassId())
				.addValue("employeeId", learningPathSessionTimer.getEmployeeId())
				.addValue("learningPathId", learningPathSessionTimer.getLearningPathId()));
	}

	private static class FetchLearningPathSessionTimerMapper implements RowMapper<LearningPathSessionTimer> {
		@Override
		public LearningPathSessionTimer mapRow(ResultSet rs, int rowNum) throws SQLException {
			LearningPathSessionTimer learningPathSessionTimer = new LearningPathSessionTimer();
			learningPathSessionTimer.setLearningPathSessionTimerId(rs.getInt("learningPathSessionTimerId"));
			learningPathSessionTimer.setClassId(rs.getInt("classId"));
			learningPathSessionTimer.setEmployeeId(rs.getInt("employeeId"));
			learningPathSessionTimer.setLearningPathId(rs.getInt("learningPathId"));
			learningPathSessionTimer.setTimeStarted(rs.getString("timeStarted"));
			learningPathSessionTimer.setTimeEnded(rs.getString("timeEnded"));
			learningPathSessionTimer.setTimeSpent(rs.getInt("timeSpent"));
			learningPathSessionTimer.setSessionStatus(rs.getInt("sessionStatus"));
			learningPathSessionTimer.setLastAccessed(rs.getString("lastAccessed"));
			learningPathSessionTimer.setCreatedAt(rs.getString("createdAt"));
			learningPathSessionTimer.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return learningPathSessionTimer;
		}
	}
	
	@Override
	public LearningPathSessionTimer fetchLearningPathSessionTimer(int classId, int employeeId, int learningPathId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT lpst.learningPathSessionTimerId, ")
				.append("lpst.classId, lpst.employeeId, ")
				.append("lpst.learningPathId, lpst.timeStarted, ")
				.append("lpst.timeEnded, lpst.timeSpent, lpst.sessionStatus, ")
				.append("lpst.lastAccessed, lpst.createdAt, lpst.lastModifiedAt ")
				.append("FROM learningpathsessiontimer lpst ")
				.append("WHERE lpst.classId=:classId AND lpst.employeeId=:employeeId ")
				.append("AND lpst.learningPathId = :learningPathId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("employeeId", employeeId)
				.addValue("learningPathId", learningPathId),
			new FetchLearningPathSessionTimerMapper());
	}

	@Override
	public void updateTimeSpent(LearningPathSessionTimer learningPathSessionTimer) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE learningpathsessiontimer SET ")
				.append("timeSpent = :timeSpent ")
				.append("WHERE classId = :classId AND employeeId = :employeeId AND ")
				.append("learningPathId = :learningPathId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("timeSpent", learningPathSessionTimer.getTimeSpent())
				.addValue("classId", learningPathSessionTimer.getClassId())
				.addValue("employeeId", learningPathSessionTimer.getEmployeeId())
				.addValue("learningPathId", learningPathSessionTimer.getLearningPathId()));
	}

	@Override
	public int calculateTotalTimeSpent(int classId, int employeeId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT IFNULL(SUM(lpst.timeSpent),0) ")
				.append("FROM learningpathsessiontimer lpst ")
				.append("WHERE lpst.classId = :classId AND lpst.employeeId = :employeeId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("employeeId", employeeId),
			Integer.class);
	}
}
