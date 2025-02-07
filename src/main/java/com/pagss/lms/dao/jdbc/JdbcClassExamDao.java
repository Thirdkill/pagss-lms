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
import com.pagss.lms.dao.interfaces.ClassExamDao;
import com.pagss.lms.domains.ClassExam;

@Repository
public class JdbcClassExamDao extends JdbcDao implements ClassExamDao {

	private class FetchClassExamListMapper implements RowMapper<ClassExam> {
		@Override
		public ClassExam mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassExam classExam = new ClassExam();
			classExam.setClassExamId(rs.getInt("classExamId"));
			classExam.setClassId(rs.getInt("classId"));
			classExam.setExamId(rs.getInt("examId"));
			classExam.setTitle(rs.getString("title"));
			classExam.setExamType(rs.getInt("examType"));
			classExam.setDescription(rs.getString("description"));
			classExam.setDuration(rs.getInt("duration"));
			classExam.setPassingScore(rs.getBigDecimal("passingScore"));
			classExam.setExamRetake(rs.getInt("examRetake"));
			classExam.setNoOfRetake(rs.getInt("noOfRetake"));
			classExam.setIsSafeBrowser(rs.getInt("isSafeBrowser"));
			classExam.setIsShowCorrectAnswer(rs.getInt("isShowCorrectAnswer"));
			classExam.setIsShowScore(rs.getInt("isShowScore"));
			classExam.setIsShowBreakDown(rs.getInt("isShowBreakDown"));
			classExam.setCreatedAt(rs.getString("createdAt"));
			return classExam;
		}
	}
	
	@Override
	public List<ClassExam> fetchClassExamList(int classId, int calculatedPageNo, int pageSize) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ce.classExamId, ce.classId, ce.examId, ")
				.append("ei.title, ei.examType, ei.description, ce.duration, ")
				.append("ce.passingScore, ce.examRetake, ce.noOfRetake, ce.isSafeBrowser, ")
				.append("ce.isShowCorrectAnswer, ce.isShowScore, ce.isShowBreakdown, ce.createdAt ")
				.append("FROM classexam ce ")
				.append("LEFT JOIN examinfo ei ON ce.examId = ei.examId ")
				.append("WHERE ce.classId=:classId ")
				.append("ORDER BY ce.createdAt DESC ")
				.append("LIMIT :pageNumber,:pageSize ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("pageSize", pageSize)
				.addValue("pageNumber", calculatedPageNo),
			new FetchClassExamListMapper());
	}

	@Override
	public int countClassExamList(int classId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ce.classId) ")
				.append("FROM classexam ce ")
				.append("LEFT JOIN examinfo ei ON ce.examId = ei.examId ")
				.append("WHERE ce.classId=:classId ")
				.append("ORDER BY ce.createdAt DESC ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId),
			Integer.class);
	}

	@Override
	public void createClassExam(List<ClassExam> classExams) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(classExams.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("INSERT INTO classexam (classId, examId, duration, passingScore, examRetake, ")
				.append("noOfRetake, isSafeBrowser, isShowCorrectAnswer, isShowScore, isShowBreakdown) ")
				.append("VALUES (:classId, :examId, 0, 0, 2, 0, 2, 2, 2, 2) ")
				.toString(), 
			batch);
		
	}

	@Override
	public void updateClassExam(ClassExam classExam) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE classexam ce SET ce.duration = :duration, ce.passingScore = :passingScore, ce.examRetake = :examRetake, ")
				.append("ce.noOfRetake = :noOfRetake, ce.isSafeBrowser = :isSafeBrowser, ce.isShowCorrectAnswer = :isShowCorrectAnswer, ")
				.append("ce.isShowScore = :isShowScore, ce.isShowBreakDown = :isShowBreakDown ")
				.append("WHERE ce.classExamId = :classExamId")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classExamId", classExam.getClassExamId())
				.addValue("duration", classExam.getDuration())
				.addValue("passingScore",classExam.getPassingScore())
				.addValue("examRetake",classExam.getExamRetake())
				.addValue("noOfRetake",classExam.getNoOfRetake())
				.addValue("isSafeBrowser",classExam.getIsSafeBrowser())
				.addValue("isShowCorrectAnswer",classExam.getIsShowCorrectAnswer())
				.addValue("isShowScore",classExam.getIsShowScore())
				.addValue("isShowBreakDown",classExam.getIsShowBreakDown()));
	}

	@Override
	public void addClassExam(ClassExam classExam) {
		this.jdbcTemplate.update(
			new StringBuffer()
			.append("INSERT INTO classexam (classId, examId, duration, passingScore, examRetake, ")
			.append("noOfRetake, isSafeBrowser, isShowCorrectAnswer, isShowScore, isShowBreakdown) ")
			.append("VALUES (:classId, :examId, 0, 0, 2, 0, 2, 2, 2, 2) ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classExam.getClassId())
				.addValue("examId", classExam.getExamId()));
	}

	@Override
	public void deleteClassExam(int classExamId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE ce FROM classexam ce ")
				.append("WHERE ce.classExamId = :classExamId")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classExamId", classExamId));
	}

	private static class FetchClassExamsMapper implements RowMapper<ClassExam> {
		@Override
		public ClassExam mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassExam classExam = new ClassExam();
			classExam.setClassExamId(rs.getInt("classExamId"));
			classExam.setClassId(rs.getInt("classId"));
			classExam.setExamId(rs.getInt("examId"));
			classExam.setDuration(rs.getInt("duration"));
			classExam.setPassingScore(rs.getBigDecimal("passingScore"));
			classExam.setExamRetake(rs.getInt("examRetake"));
			classExam.setNoOfRetake(rs.getInt("noOfRetake"));
			classExam.setIsSafeBrowser(rs.getInt("isSafeBrowser"));
			classExam.setIsShowCorrectAnswer(rs.getInt("isShowCorrectAnswer"));
			classExam.setIsShowScore(rs.getInt("isShowScore"));
			classExam.setIsShowBreakDown(rs.getInt("isShowBreakdown"));
			classExam.setCreatedAt(rs.getString("createdAt"));
			classExam.setLastModifiedAt(rs.getString("lastModifiedAt"));
			classExam.setExamType(rs.getInt("examType"));
			classExam.setExamCode(rs.getString("examCode"));
			classExam.setTitle(rs.getString("title"));
			classExam.setRemarks(rs.getString("remarks"));
			classExam.setNoOfTimesTaken(rs.getInt("noOfTimesTaken"));
			classExam.setScore(rs.getBigDecimal("score"));
			return classExam;
		}
	}
	
	@Override
	public List<ClassExam> fetchClassExams(int classId,int employeeId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ce.classExamId, ce.classId, ce.examId, ")
				.append("ce.duration, ce.passingScore, ce.examRetake, ")
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
				.append("FROM classexam ce ")
				.append("LEFT JOIN examinfo ei ON ei.examId=ce.examId ")
				.append("WHERE ce.classId = :classId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId",classId)
				.addValue("employeeId", employeeId),
			new FetchClassExamsMapper());
	}

	private static class FetchClassExamMapper implements RowMapper<ClassExam> {
		@Override
		public ClassExam mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassExam classExam = new ClassExam();
			classExam.setClassExamId(rs.getInt("classExamId"));
			classExam.setClassId(rs.getInt("classId"));
			classExam.setExamId(rs.getInt("examId"));
			classExam.setDuration(rs.getInt("duration"));
			classExam.setPassingScore(rs.getBigDecimal("passingScore"));
			classExam.setExamRetake(rs.getInt("examRetake"));
			classExam.setNoOfRetake(rs.getInt("noOfRetake"));
			classExam.setIsSafeBrowser(rs.getInt("isSafeBrowser"));
			classExam.setIsShowCorrectAnswer(rs.getInt("isShowCorrectAnswer"));
			classExam.setIsShowBreakDown(rs.getInt("isShowBreakdown"));
			classExam.setIsShowScore(rs.getInt("isShowScore"));
			classExam.setCreatedAt(rs.getString("createdAt"));
			classExam.setLastModifiedAt(rs.getString("lastModifiedAt"));
			classExam.setTitle(rs.getString("title"));
			classExam.setExamCode(rs.getString("examCode"));
			classExam.setExamType(rs.getInt("examType"));
			classExam.setDescription(rs.getString("description"));
			classExam.setIsAllowAttachment(rs.getInt("allowAttachment"));
			return classExam;
		}
	}
	
	@Override
	public ClassExam fetchClassExam(int classId, int examId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT ce.classExamId, ce.classId, ")
				.append("ce.examId, ce.duration, ce.passingScore, ")
				.append("ce.examRetake, ce.noOfRetake, ce.isSafeBrowser, ")
				.append("ce.isShowCorrectAnswer, ce.isShowScore, ")
				.append("ce.isShowBreakdown, ce.createdAt, ce.lastModifiedAt, ")
				.append("ei.title,ei.examCode, ei.examType, ei.description, ")
				.append("ei.allowAttachment ")
				.append("FROM classexam ce ")
				.append("LEFT JOIN examinfo ei ON ei.examId=ce.examId ")
				.append("WHERE ce.classId=:classId AND ce.examId=:examId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("examId", examId),
			new FetchClassExamMapper());
	}
}
