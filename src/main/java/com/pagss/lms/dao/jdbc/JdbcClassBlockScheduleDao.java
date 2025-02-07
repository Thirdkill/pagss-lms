package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.ClassBlockScheduleDao;
import com.pagss.lms.domains.ClassBlockSchedule;

@Repository
public class JdbcClassBlockScheduleDao extends JdbcDao implements ClassBlockScheduleDao {

	private static class FetchClassBlockScheduleByClassIdMapper implements RowMapper<ClassBlockSchedule> {
		@Override
		public ClassBlockSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassBlockSchedule classBlockSchedule = new ClassBlockSchedule();
			classBlockSchedule.setClassBlockId(rs.getInt("classBlockId"));
			classBlockSchedule.setClassId(rs.getInt("classId"));
			classBlockSchedule.setStartDate(getDateValue(rs.getString("startDate"),"yyyy-MM-dd","MMMM dd, yyyy"));
			classBlockSchedule.setEndDate(getDateValue(rs.getString("endDate"),"yyyy-MM-dd","MMMM dd, yyyy"));
			classBlockSchedule.setStartTime(getDateValue(rs.getString("startTime"),"hh:mm:ss","hh:mm"));
			classBlockSchedule.setEndTime(getDateValue(rs.getString("endTime"),"hh:mm:ss","hh:mm"));
			classBlockSchedule.setCreatedAt(rs.getString("createdAt"));
			classBlockSchedule.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return classBlockSchedule;
		}
	}
	
	@Override
	public ClassBlockSchedule fetchClassBlockScheduleByClassId(int classId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT cbs.classBlockId, cbs.classId, cbs.startDate, ")
				.append("cbs.endDate, cbs.startTime, cbs.endTime, cbs.createdAt, cbs.lastModifiedAt ")
				.append("FROM classblockschedule cbs ")
				.append("WHERE cbs.classId = :classId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId),
			new FetchClassBlockScheduleByClassIdMapper());
	}

	@Override
	public int countClassBlockSchedule(ClassBlockSchedule classBlockSchedule) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(cbs.classBlockId) ")
				.append("FROM classblockschedule cbs ")
				.append("LEFT JOIN classemployee ce ON ce.classId=cbs.classId ")
				.append("WHERE ce.employeeId= :employeeId AND cbs.classId IN( ")
				.append("SELECT cbs2.classId FROM classblockschedule cbs2 ")
				.append("WHERE cbs2.startDate BETWEEN :startDate AND :endDate OR ")
				.append("cbs2.endDate BETWEEN :startDate AND :endDate) AND ")
				.append("ce.trainingStatus NOT IN(4) AND ")
				.append("ce.approvalStatus NOT IN(2);")
				.toString(),
			new MapSqlParameterSource()
				.addValue("employeeId", classBlockSchedule.getEmployeeId())
				.addValue("startDate", getDateValue(classBlockSchedule.getStartDate(),"MMMM dd, yyyy","yyyy-MM-dd"))
				.addValue("endDate",getDateValue(classBlockSchedule.getEndDate(),"MMMM dd, yyyy","yyyy-MM-dd")),
			Integer.class);
	}

}
