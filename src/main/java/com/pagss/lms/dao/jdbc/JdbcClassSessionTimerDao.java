package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.ClassSessionTimerDao;
import com.pagss.lms.domains.ClassSessionTimer;

@Repository
public class JdbcClassSessionTimerDao extends JdbcDao implements ClassSessionTimerDao {

	@Override
	public int countClassSessionTimerByEmployeeId(int classId, int employeeId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(cst.classSessionTimerId) ")
				.append("FROM classsessiontimer cst ")
				.append("WHERE cst.classId = :classId AND employeeId = :employeeId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("employeeId", employeeId),
			Integer.class);
	}

	@Override
	public void createClassSessionTimer(ClassSessionTimer classSessionTimer) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO classsessiontimer(")
				.append("classId, employeeId, timeStarted, timeEnded) ")
				.append("VALUES(:classId,:employeeId,:timeStarted,:timeEnded);")
				.toString(), 
			new MapSqlParameterSource()
				.addValue("classId", classSessionTimer.getClassId())
				.addValue("employeeId", classSessionTimer.getEmployeeId())
				.addValue("timeStarted", classSessionTimer.getTimeStarted())
				.addValue("timeEnded", classSessionTimer.getTimeEnded()));
	}

	@Override
	public void updateClassSessionTimer(ClassSessionTimer classSessionTimer) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE classsessiontimer SET ")
				.append("classId = :classId, ")
				.append("employeeId = :employeeId, ")
				.append("timeStarted = :timeStarted, ")
				.append("timeEnded = :timeEnded, ")
				.append("sessionStatus = :sessionStatus ")
				.append("WHERE classId = :classId AND employeeId = :employeeId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classSessionTimer.getClassId())
				.addValue("employeeId", classSessionTimer.getEmployeeId())
				.addValue("timeStarted", classSessionTimer.getTimeStarted())
				.addValue("timeEnded", classSessionTimer.getTimeEnded())
				.addValue("sessionStatus", classSessionTimer.getSessionStatus()));
	}

	private static class FetchClassSessionTimer implements RowMapper<ClassSessionTimer> {
		@Override
		public ClassSessionTimer mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassSessionTimer classSessionTimer = new ClassSessionTimer();
			classSessionTimer.setClassSessionTimerId(rs.getInt("classSessionTimerId"));
			classSessionTimer.setClassId(rs.getInt("classId"));
			classSessionTimer.setEmployeeId(rs.getInt("employeeId"));
			classSessionTimer.setTimeStarted(rs.getString("timeStarted"));
			classSessionTimer.setTimeEnded(rs.getString("timeEnded"));
			classSessionTimer.setTimeSpent(rs.getInt("timeSpent"));
			classSessionTimer.setSessionStatus(rs.getInt("sessionStatus"));
			return classSessionTimer;
		}
	}
	
	@Override
	public ClassSessionTimer fetchClassSessionTimer(int classId, int employeeId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT cst.classSessionTimerId, cst.classId, ")
				.append("cst.employeeId, cst.timeStarted, cst.timeEnded, cst.timeSpent, cst.sessionStatus ")
				.append("FROM classsessiontimer cst ")
				.append("WHERE cst.classId = :classId AND cst.employeeId = :employeeId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("employeeId", employeeId),
			new FetchClassSessionTimer());
	}

	@Override
	public void updateTimeSpent(ClassSessionTimer classSessionTimer) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE classsessiontimer cst1 SET ")
				.append("cst1.timeSpent = :timeSpent ")
				.append("WHERE cst1.classId = :classId AND cst1.employeeId = :employeeId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("timeSpent", classSessionTimer.getTimeSpent())
				.addValue("classId", classSessionTimer.getClassId())
				.addValue("employeeId", classSessionTimer.getEmployeeId()));
	}

}
