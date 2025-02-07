package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.CourseExamDao;
import com.pagss.lms.domains.CourseExam;

@Repository
public class JdbcCourseExamDao extends JdbcDao implements CourseExamDao {

	private static class FetchCourseExamPagesMapper implements RowMapper<CourseExam> {
		@Override
		public CourseExam mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseExam courseExam = new CourseExam();
			courseExam.setCourseExamId(rs.getInt("courseExamId"));
			courseExam.setCourseId(rs.getInt("courseId"));
			courseExam.setExamId(rs.getInt("examId"));
			courseExam.setDuration(rs.getInt("duration"));
			courseExam.setPassingScore(rs.getBigDecimal("passingScore"));
			courseExam.setExamRetake(rs.getInt("examRetake"));
			courseExam.setNoOfRetake(rs.getInt("noOfRetake"));
			courseExam.setIsSafeBrowser(rs.getInt("isSafeBrowser"));
			courseExam.setIsShowCorrectAnswer(rs.getInt("isShowCorrectAnswer"));
			courseExam.setIsShowScore(rs.getInt("isShowScore"));
			courseExam.setIsShowBreakDown(rs.getInt("isSHowBreakdown"));
			courseExam.setCreatedAt(rs.getString("createdAt"));
			courseExam.setLastModifiedAt(rs.getString("lastModifiedAt"));
			courseExam.setExamType(rs.getInt("examType"));
			courseExam.setDescription(rs.getString("description"));
			courseExam.setTitle(rs.getString("title"));
			return courseExam;
		}
	}
	
	@Override
	public List<CourseExam> fetchCourseExamPages(int pageSize, int pageNumber, int courseId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ce.courseExamId, ce.examId, ")
				.append("ce.courseId, ce.duration, ce.passingScore, ")
				.append("ce.examRetake, ce.noOfRetake,ce.isSafeBrowser, ")
				.append("ce.isShowCorrectAnswer, ce.isShowScore, ")
				.append("ce.isShowBreakdown, ce.createdAt, ce.lastModifiedAt, ")
				.append("ei.examType, ei.description, ei.title ")
				.append("FROM courseexam ce ")
				.append("LEFT JOIN examinfo ei ON ei.examId=ce.examId ")
				.append("WHERE ce.courseId = :courseId ")
				.append("ORDER BY ce.createdAt DESC ")
				.append("LIMIT :pageNumber,:pageSize;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", courseId)
				.addValue("pageSize", pageSize)
				.addValue("pageNumber", pageNumber),
			new FetchCourseExamPagesMapper());
	}

	@Override
	public int countTotalCourseExams(int courseId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) ")
				.append("FROM courseexam ce ")
				.append("LEFT JOIN examinfo ei ON ei.examId=ce.examId ")
				.append("WHERE ce.courseId = :courseId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", courseId), 
			Integer.class);
	}

	@Override
	public int createCourseExam(CourseExam courseExam) {
		KeyHolder courseExamId = new GeneratedKeyHolder();
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO courseexam(")
				.append("examId, courseId, duration, ")
				.append("passingScore, examRetake, noOfRetake, ")
				.append("isSafeBrowser, isShowCorrectAnswer, ")
				.append("isShowScore, isShowBreakdown) VALUES(")
				.append(":examId, :courseId, :duration, ")
				.append(":passingScore, :examRetake, :noOfRetake, ")
				.append(":isSafeBrowser, :isShowCorrectAnswer, ")
				.append(":isShowScore, :isShowBreakdown)")
				.toString(),
			new MapSqlParameterSource()
				.addValue("examId", courseExam.getExamId())
				.addValue("courseId", courseExam.getCourseId())
				.addValue("duration", courseExam.getDuration())
				.addValue("passingScore", courseExam.getPassingScore())
				.addValue("examRetake", courseExam.getExamRetake())
				.addValue("noOfRetake", courseExam.getNoOfRetake())
				.addValue("isSafeBrowser", courseExam.getIsSafeBrowser())
				.addValue("isShowCorrectAnswer", courseExam.getIsShowCorrectAnswer())
				.addValue("isShowScore", courseExam.getIsShowScore())
				.addValue("isShowBreakdown", courseExam.getIsShowBreakDown()),
				courseExamId);	
		return courseExamId.getKey().intValue();
	}

	@Override
	public void createCourseExams(List<CourseExam> courseExams) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(courseExams.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("INSERT INTO courseexam(")
				.append("courseId, examId) VALUES(")
				.append(":courseId, :examId);")
				.toString(),
			batch);
	}

	@Override
	public void deleteCourseExam(int courseExamId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE FROM courseexam ")
				.append("WHERE courseExamId = :courseExamId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseExamId", courseExamId));	
	}

	private static class FetchCourseExamsWithExamTypeMapper implements RowMapper<CourseExam> {
		@Override
		public CourseExam mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseExam courseExam = new CourseExam();
			courseExam.setCourseExamId(rs.getInt("courseExamId"));
			courseExam.setCourseId(rs.getInt("courseId"));
			courseExam.setExamId(rs.getInt("examId"));
			courseExam.setDuration(rs.getInt("duration"));
			courseExam.setPassingScore(rs.getBigDecimal("passingScore"));
			courseExam.setExamRetake(rs.getInt("examRetake"));
			courseExam.setNoOfRetake(rs.getInt("noOfRetake"));
			courseExam.setIsSafeBrowser(rs.getInt("isSafeBrowser"));
			courseExam.setIsShowCorrectAnswer(rs.getInt("isShowCorrectAnswer"));
			courseExam.setIsShowScore(rs.getInt("isShowScore"));
			courseExam.setIsShowBreakDown(rs.getInt("isSHowBreakdown"));
			courseExam.setCreatedAt(rs.getString("createdAt"));
			courseExam.setLastModifiedAt(rs.getString("lastModifiedAt"));
			courseExam.setExamType(rs.getInt("examType"));
			courseExam.setDescription(rs.getString("description"));
			courseExam.setTitle(rs.getString("title"));
			return courseExam;
		}
	}
	
	@Override
	public List<CourseExam> fetchCourseExamsWithExamType(int courseId,int examType) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ce.courseExamId, ce.examId, ")
				.append("ce.courseId, ce.duration, ce.passingScore, ")
				.append("ce.examRetake, ce.noOfRetake,ce.isSafeBrowser, ")
				.append("ce.isShowCorrectAnswer, ce.isShowScore, ")
				.append("ce.isShowBreakdown, ce.createdAt, ce.lastModifiedAt, ")
				.append("ei.examType, ei.description, ei.title ")
				.append("FROM courseexam ce ")
				.append("LEFT JOIN examinfo ei ON ei.examId=ce.examId ")
				.append("WHERE ce.courseId = :courseId AND ")
				.append("ei.examType = :examType ")
				.append("ORDER BY ce.createdAt DESC ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", courseId)
				.addValue("examType", examType),
			new FetchCourseExamsWithExamTypeMapper());
	}

	@Override
	public void updateCourseExam(CourseExam courseExam) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE courseexam SET ")
				.append("duration=:duration, ")
				.append("passingScore=:passingScore, ")
				.append("examRetake=:examRetake, ")
				.append("noOfRetake=:noOfRetake, ")
				.append("isSafeBrowser=:isSafeBrowser, ")
				.append("isShowCorrectAnswer=:isShowCorrectAnswer, ")
				.append("isShowScore=:isShowScore, ")
				.append("isShowBreakdown=:isShowBreakdown ")
				.append("WHERE courseExamId=:courseExamId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("passingScore", courseExam.getPassingScore())
				.addValue("duration", courseExam.getDuration())
				.addValue("examRetake", courseExam.getExamRetake())
				.addValue("noOfRetake", courseExam.getNoOfRetake())
				.addValue("isSafeBrowser", courseExam.getIsSafeBrowser())
				.addValue("isShowCorrectAnswer", courseExam.getIsShowCorrectAnswer())
				.addValue("isShowScore", courseExam.getIsShowScore())
				.addValue("isShowBreakdown", courseExam.getIsShowBreakDown())
				.addValue("courseExamId", courseExam.getCourseExamId()));
	}

	private static class FetchCourseExamsByClassIdMapper implements RowMapper<CourseExam> {
		@Override
		public CourseExam mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseExam courseExam = new CourseExam();
			courseExam.setCourseExamId(rs.getInt("courseExamId"));
			courseExam.setCourseId(rs.getInt("courseId"));
			courseExam.setExamId(rs.getInt("examId"));
			courseExam.setDuration(rs.getInt("duration"));
			courseExam.setPassingScore(rs.getBigDecimal("passingScore"));
			courseExam.setExamRetake(rs.getInt("examRetake"));
			courseExam.setNoOfRetake(rs.getInt("noOfRetake"));
			courseExam.setIsSafeBrowser(rs.getInt("isSafeBrowser"));
			courseExam.setIsShowCorrectAnswer(rs.getInt("isShowCorrectAnswer"));
			courseExam.setIsShowScore(rs.getInt("isShowScore"));
			courseExam.setIsShowBreakDown(rs.getInt("isShowBreakdown"));
			courseExam.setCreatedAt(rs.getString("createdAt"));
			courseExam.setLastModifiedAt(rs.getString("lastModifiedAt"));
			courseExam.setExamCode(rs.getString("examCode"));
			courseExam.setExamType(rs.getInt("examType"));
			courseExam.setTitle(rs.getString("title"));
			courseExam.setRemarks(rs.getString("remarks"));
			courseExam.setNoOfTimesTaken(rs.getInt("noOfTimesTaken"));
			courseExam.setScore(rs.getBigDecimal("score"));
			return courseExam;
		}
	}
	
	@Override
	public List<CourseExam> fetchCourseExams(int courseId,int classId,int employeeId) {
		return this.jdbcTemplate.query(
				new StringBuffer()
					.append("SELECT ce.courseExamId, ce.courseId, ce.examId, ")
					.append("ce.duration, ce.passingScore, ")
					.append("ce.examRetake, ce.noOfRetake,ce.isSafeBrowser, ce.isShowCorrectAnswer, ")
					.append("ce.isShowScore, ce.isShowBreakdown, ") 
					.append("ce.createdAt, ce.lastModifiedAt, ei.examCode, ei.examType, ei.title, ")
					.append("IFNULL((SELECT ees.remarks FROM employeeexamsummary ees ")
					.append("WHERE ees.classId = :classId AND ees.employeeId = :employeeId ")
					.append("AND ees.examId = ce.examId),'NOT YET STARTED') AS remarks, ")
					.append("IFNULL((SELECT ees2.noOfTimesTaken FROM employeeexamsummary ees2 ")
					.append("WHERE ees2.classId = :classId AND ees2.employeeId = :employeeId ")
					.append("AND ees2.examId = ce.examId),0) AS noOfTimesTaken, ")
					.append("IFNULL((SELECT ees3.score FROM employeeexamsummary ees3 ")
					.append("WHERE ees3.classId = :classId AND ees3.employeeId = :employeeId ")
					.append("AND ees3.examId = ce.examId),0.00) AS score ")
					.append("FROM courseexam ce ")
					.append("LEFT JOIN classinfo ci ON ci.courseId=ce.courseId ")
					.append("LEFT JOIN examinfo ei ON ei.examId=ce.examId ")
					.append("WHERE ce.courseId = :courseId;")
					.toString(),
				new MapSqlParameterSource()
					.addValue("courseId",courseId)
					.addValue("classId",classId)
					.addValue("employeeId", employeeId),
				new FetchCourseExamsByClassIdMapper());
	}
}
