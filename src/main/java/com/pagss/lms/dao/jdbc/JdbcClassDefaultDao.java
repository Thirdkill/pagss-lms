package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.ClassDefaultDao;
import com.pagss.lms.domains.ClassDefault;
import com.pagss.lms.domains.CourseInfo;

@Repository
public class JdbcClassDefaultDao extends JdbcDao implements ClassDefaultDao {
	
	@Override
	public void createClassDefault(CourseInfo courseInfo) {
		this.jdbcTemplate.update(new StringBuffer()
			.append("INSERT INTO classdefault( ")
			.append("courseId, employeeId, locationId, ")
			.append("isSelfRegister, withCertificate, ")
			.append("minAttendee, maxAttendee, withExam, ")
			.append("scheduleType) VALUES( ")
			.append(":courseId, :employeeId, :locationId, ")
			.append(":isSelfRegister, :withCertificate, ")
			.append(":minAttendee, :maxAttendee, :withExam,")
			.append(":scheduleType)")
			.toString(),
		new MapSqlParameterSource()
			.addValue("courseId", courseInfo.getCourseId())
			.addValue("employeeId", courseInfo.getEmployeeId())
			.addValue("locationId", courseInfo.getLocationId())
			.addValue("isSelfRegister", courseInfo.getIsSelfRegister())
			.addValue("withCertificate", courseInfo.getWithCertificate())
			.addValue("minAttendee", courseInfo.getMinAttendee())
			.addValue("maxAttendee", courseInfo.getMaxAttendee())
			.addValue("withExam", courseInfo.getWithExam())
			.addValue("scheduleType", courseInfo.getScheduleType()));
	}

	@Override
	public void updatePhotoUrl(ClassDefault classDefault) {
		this.jdbcTemplate.update(new StringBuffer()
			.append("UPDATE classdefault SET ")
			.append("classPhotoUrl = :classPhotoUrl, ")
			.append("photoFileName = :photoFileName ")
			.append("WHERE courseId = :courseId;")
			.toString(),
		new MapSqlParameterSource()
			.addValue("classPhotoUrl", classDefault.getClassPhotoUrl())
			.addValue("photoFileName", classDefault.getPhotoFileName())
			.addValue("courseId", classDefault.getCourseId()));
		
	}
	
	@Override
	public void updateClassDefault(CourseInfo courseInfo) {
		this.jdbcTemplate.update(new StringBuffer()
			.append("UPDATE classdefault SET ")
			.append("employeeId = :employeeId, ")
			.append("locationId = :locationId, ")
			.append("isSelfRegister = :isSelfRegister, ")
			.append("withCertificate = :withCertificate, ")
			.append("minAttendee = :minAttendee, ")
			.append("maxAttendee = :maxAttendee, ")
			.append("withExam = :withExam, ")
			.append("scheduleType = :scheduleType ")
			.append("WHERE courseId = :courseId;")
			.toString(),
		new MapSqlParameterSource()
			.addValue("courseId", courseInfo.getCourseId())
			.addValue("employeeId", courseInfo.getEmployeeId())
			.addValue("locationId", courseInfo.getLocationId())
			.addValue("isSelfRegister", courseInfo.getIsSelfRegister())
			.addValue("withCertificate", courseInfo.getWithCertificate())
			.addValue("minAttendee", courseInfo.getMinAttendee())
			.addValue("maxAttendee", courseInfo.getMaxAttendee())
			.addValue("withExam", courseInfo.getWithExam())
			.addValue("scheduleType", courseInfo.getScheduleType()));
	}
	
	private static class FetchClassDefaultMapper implements RowMapper<ClassDefault> {
		@Override
		public ClassDefault mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassDefault classdefault = new ClassDefault();
			classdefault.setCourseId(rs.getInt("courseId"));
			classdefault.setEmployeeId(rs.getInt("employeeId"));
			classdefault.setLocationId(rs.getInt("locationId"));
			classdefault.setIsSelfRegister(rs.getInt("isSelfRegister"));
			classdefault.setWithCertificate(rs.getInt("withCertificate"));
			classdefault.setMinAttendee(rs.getInt("minAttendee"));
			classdefault.setMaxAttendee(rs.getInt("maxAttendee"));
			classdefault.setWithExam(rs.getInt("withExam"));
			classdefault.setScheduleType(rs.getInt("scheduleType"));
			return classdefault;
		}
	}

	@Override
	public ClassDefault fetchClassDefault(int courseId) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer().append("SELECT cd.courseId, cd.employeeId, cd.locationId, ")
			.append("cd.isSelfRegister, cd.withCertificate, cd.minAttendee, cd.maxAttendee, ")
			.append("cd.withExam, cd.scheduleType ")
			.append("FROM classdefault cd ")
			.append("WHERE cd.courseId = :courseId ").toString(),
		new MapSqlParameterSource()
			.addValue("courseId", courseId),
		new FetchClassDefaultMapper());
	}

	@Override
	public int checkClassDefault(int courseId) {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
			.append("SELECT COUNT(*) from classdefault cd ")
			.append("WHERE cd.courseId = :courseId ").toString(),
		new MapSqlParameterSource()
			.addValue("courseId", courseId),
		Integer.class);
	}
}
