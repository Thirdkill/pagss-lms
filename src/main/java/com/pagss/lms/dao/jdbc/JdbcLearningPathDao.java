package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.pagss.lms.constants.LmsLearningPathData;
import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.LearningPathDao;
import com.pagss.lms.domains.LearningPath;

@Repository
public class JdbcLearningPathDao extends JdbcDao implements LearningPathDao {

	@Override
	public void createLearningPath(LearningPath learningPath) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO learningpath(")
				.append("courseMaterialId, courseExamId, learningPathSectionId, ")
				.append("itemType,subOrderNo) VALUES(")
				.append(":courseMaterialId, :courseExamId, :learningPathSectionId, ")
				.append(":itemType, (SELECT IFNULL(MAX(lp.subOrderNo)+1,1) ")
				.append("FROM learningpath lp ")
				.append("LEFT JOIN learningpathsection lps ON lps.learningPathSectionId=lp.learningPathSectionId ")
				.append("WHERE lps.courseId = :courseId AND lps.learningPathSectionId=:learningPathSectionId));")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseMaterialId", learningPath.getCourseMaterialId())
				.addValue("courseExamId", learningPath.getCourseExamId())
				.addValue("learningPathSectionId", learningPath.getLearningPathSectionId())
				.addValue("itemType", learningPath.getItemType())
				.addValue("courseId", learningPath.getCourseId()));
	}

	private static class FetchLearningPathsMapper implements RowMapper<LearningPath> {
		@Override
		public LearningPath mapRow(ResultSet rs, int rowNum) throws SQLException {
			LearningPath learningPath = new LearningPath();
			learningPath.setLearningPathId(rs.getInt("learningPathId"));
			learningPath.setCourseMaterialId(rs.getInt("courseMaterialId"));
			learningPath.setCourseExamId(rs.getInt("courseExamId"));
			learningPath.setLearningPathSectionId(rs.getInt("learningPathSectionId"));
			learningPath.setItemType(rs.getString("itemType"));
			learningPath.setSubOrderNo(rs.getInt("subOrderNo"));
			learningPath.setCreatedAt(rs.getString("createdAt"));
			learningPath.setLastModifiedAt(rs.getString("lastModifiedAt"));
			learningPath.setFileLabel(rs.getString("fileLabel"));
			learningPath.setContentType(rs.getInt("contentType"));
			learningPath.setTitle(rs.getString("title"));
			learningPath.setExamType(rs.getInt("examType"));
			learningPath.setMaxOrderNo(rs.getInt("maxOrderNo"));
			learningPath.setContentUrl(rs.getString("contentUrl"));
			learningPath.setDuration(rs.getInt("duration"));
			learningPath.setPassingScore(rs.getBigDecimal("passingScore"));
			learningPath.setTotalItems(rs.getInt("totalItems"));
			learningPath.setNoOfRetake(rs.getInt("noOfRetake"));
			return learningPath;
		}
	}
	
	@Override
	public List<LearningPath> fetchLearningPaths(int courseId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT lp.learningPathId, lp.courseMaterialId, ")
				.append("lp.courseExamId, lp.learningPathSectionId, ")
				.append("lp.itemType, lp.subOrderNo, ")
				.append("lp.createdAt, lp.lastModifiedAt, ")
				.append("cm.fileLabel, cm.contentType,ce.passingScore, ce.duration, ")
				.append("ei.title, ei.examType, cm.contentUrl, ce.noOfRetake, ")
				.append("(SELECT MAX(lp2.subOrderNo) ")
				.append("FROM learningpath lp2 ")
				.append("LEFT JOIN learningpathsection lps2 ON lps2.learningPathSectionId=lp2.learningPathSectionId ")
				.append("WHERE lps2.courseId = :courseId AND ")
				.append("lp2.learningPathSectionId =lp.learningPathSectionId) AS maxOrderNo, ")
				.append("(SELECT IF(eq.noOfQuestions=0,SUM(eq.weight),SUM(eq.weight*eq.noOfQuestions)) FROM examquestion eq ")
				.append("WHERE eq.examId = ei.examId) AS totalItems ")
				.append("FROM learningpathsection lps ")
				.append("LEFT JOIN learningpath lp ON lp.learningPathSectionId=lps.learningPathSectionId ")
				.append("LEFT JOIN coursematerial cm ON cm.courseMaterialId=lp.courseMaterialId ")
				.append("LEFT JOIN courseexam ce ON ce.courseExamId=lp.courseExamId ")
				.append("LEFT JOIN examinfo ei ON ei.examId=ce.examId ")
				.append("WHERE lps.courseId = :courseId ")
				.append("ORDER BY lp.subOrderNo ASC;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", courseId),
			new FetchLearningPathsMapper());
	}
	
	@Override
	public int checkIfCourseExamExist(LearningPath learningPath) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(lp.courseExamId) ")
				.append("FROM learningpath lp ")
				.append("LEFT JOIN learningpathsection lps ON lps.learningPathSectionId=lp.learningPathSectionId ")
				.append("WHERE lps.courseId=:courseId AND lp.courseExamId=:courseExamId ")
				.append("AND lp.itemType=:itemType;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", learningPath.getCourseId())
				.addValue("courseExamId", learningPath.getCourseExamId())
				.addValue("itemType", LmsLearningPathData.EXAM),
			Integer.class);
	}

	@Override
	public void updateSubOrderNo(List<LearningPath> learningPaths) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(learningPaths.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("UPDATE learningpath SET ")
				.append("subOrderNo = :subOrderNo ")
				.append("WHERE learningPathId=:learningPathId;")
				.toString(),
			batch);
	}
	
	@Override
	public void deleteLearningPath(int learningPathId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE FROM learningpath ")
				.append("WHERE learningPathId=:learningPathId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("learningPathId", learningPathId));
	}

	@Override
	public void deleteLearningPathByLearningPathSectionId(int learningPathSectionId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE lps.*,lp.* ")
				.append("FROM learningpath lp ")
				.append("LEFT JOIN learningpathsection lps ON lps.learningPathSectionId=lp.learningPathSectionId ")
				.append("WHERE lps.learningPathSectionId = :learningPathSectionId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("learningPathSectionId", learningPathSectionId));
	}

	private static class FetchLearningPathMapper implements RowMapper<LearningPath> {
		@Override
		public LearningPath mapRow(ResultSet rs, int rowNum) throws SQLException {
			LearningPath learningPath = new LearningPath();
			learningPath.setLearningPathId(rs.getInt("learningPathId"));
			learningPath.setCourseMaterialId(rs.getInt("courseMaterialId"));
			learningPath.setLearningPathSectionId(rs.getInt("learningPathSectionId"));
			learningPath.setItemType(rs.getString("itemType"));
			learningPath.setSubOrderNo(rs.getInt("subOrderNo"));
			learningPath.setCreatedAt(rs.getString("createdAt"));
			learningPath.setLastModifiedAt(rs.getString("lastModifiedAt"));
			learningPath.setFileLabel(rs.getString("fileLabel"));
			learningPath.setContentType(rs.getInt("contentType"));
			learningPath.setContentUrl(rs.getString("contentUrl"));
			return learningPath;
		}
	}
	
	@Override
	public LearningPath fetchLearningPath(int learningPathId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT lp.learningPathId, lp.courseMaterialId, ")
				.append("lp.learningPathSectionId, ")
				.append("lp.itemType, lp.subOrderNo, ")
				.append("lp.createdAt, lp.lastModifiedAt, ")
				.append("cm.fileLabel, cm.contentType, cm.contentUrl ")
				.append("FROM learningpathsection lps ")
				.append("LEFT JOIN learningpath lp ON lp.learningPathSectionId=lps.learningPathSectionId ")
				.append("LEFT JOIN coursematerial cm ON cm.courseMaterialId=lp.courseMaterialId ")
				.append("WHERE lp.learningPathId = :learningPathId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("learningPathId", learningPathId),
			new FetchLearningPathMapper());
	}
}
