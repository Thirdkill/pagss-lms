package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.ClassSetScheduleDao;
import com.pagss.lms.domains.ClassSetSchedule;

@Repository
public class JdbcClassSetScheduleDao extends JdbcDao implements ClassSetScheduleDao {

	private static class FetchClassSetScheduleByClassIdMapper implements RowMapper<ClassSetSchedule> {
		@Override
		public ClassSetSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassSetSchedule classSetSchedule = new ClassSetSchedule();
			classSetSchedule.setClassSetId(rs.getInt("classSetId"));
			classSetSchedule.setClassId(rs.getInt("classId"));
			classSetSchedule.setStartDate(getDateValue(rs.getString("startDate"),"yyyy-MM-dd","MMMM dd, yyyy"));
			classSetSchedule.setEndDate(getDateValue(rs.getString("endDate"),"yyyy-MM-dd","MMMM dd, yyyy"));
			classSetSchedule.setStartTime(getDateValue(rs.getString("startTime"),"hh:mm:ss","hh:mm"));
			classSetSchedule.setEndTime(getDateValue(rs.getString("endTime"),"hh:mm:ss","hh:mm"));
			classSetSchedule.setCreatedAt(rs.getString("createdAt"));
			classSetSchedule.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return classSetSchedule;
		}
	}
	
	@Override
	public List<ClassSetSchedule> fetchClassSetScheduleByClassId(int classId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT css.classSetId, css.classId, ")
				.append("css.startDate, css.endDate, css.startTime, css.endTime, ")
				.append("css.createdAt, css.lastModifiedAt ")
				.append("FROM classsetschedule css ")
				.append("WHERE css.classId = :classId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId),
			new FetchClassSetScheduleByClassIdMapper());
	}

	@Override
	public int countClassSetSchedule(ClassSetSchedule classSetSchedule) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(css.classId) ")
				.append("FROM classsetschedule css ")
				.append("LEFT JOIN classemployee ce ON ce.classId=css.classId ")
				.append("WHERE ce.employeeId = :employeeId AND css.classId IN (")
				.append("SELECT css2.classId FROM classsetschedule css2 ")
				.append("WHERE css.startDate BETWEEN :startDate AND :endDate OR ")
				.append("css.endDate BETWEEN :startDate AND :endDate) AND ")
				.append("ce.trainingStatus NOT IN(4) AND ")
				.append("ce.approvalStatus NOT IN(2);")
				.toString(),
			new MapSqlParameterSource()
				.addValue("startDate", getDateValue(classSetSchedule.getStartDate(),"MMMM dd, yyyy","yyyy-MM-dd"))
				.addValue("endDate",getDateValue(classSetSchedule.getEndDate(),"MMMM dd, yyyy","yyyy-MM-dd"))
				.addValue("employeeId", classSetSchedule.getEmployeeId()),
			Integer.class);
	}

}
