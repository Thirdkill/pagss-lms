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
import com.pagss.lms.dao.interfaces.LearningPathSectionDao;
import com.pagss.lms.domains.LearningPathSection;

@Repository
public class JdbcLearningPathSectionDao extends JdbcDao implements LearningPathSectionDao {

	@Override
	public void createLearningPathSection(LearningPathSection learningPathSection) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO learningpathsection(")
				.append("courseId,sectionTitle, sectionOrderNo, description) ")
				.append("VALUES(:courseId,:sectionTitle,")
				.append("(SELECT IFNULL((MAX(lps.sectionOrderNo)+1),1) ")
				.append("FROM learningpathsection lps ")
				.append("WHERE lps.courseId = :courseId),:description) ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", learningPathSection.getCourseId())
				.addValue("sectionTitle", learningPathSection.getSectionTitle())
				.addValue("sectionOrderNo", learningPathSection.getSectionOrderNo())
				.addValue("description", learningPathSection.getDescription()));
	}

	private static class FetchLearningPathSectionsMapper implements RowMapper<LearningPathSection> {
		@Override
		public LearningPathSection mapRow(ResultSet rs, int rowNum) throws SQLException {
			LearningPathSection learningPathSection=new LearningPathSection();
			learningPathSection.setLearningPathSectionId(rs.getInt("learningPathSectionId"));
			learningPathSection.setCourseId(rs.getInt("courseId"));
			learningPathSection.setSectionTitle(rs.getString("sectionTitle"));
			learningPathSection.setSectionOrderNo(rs.getInt("sectionOrderNo"));
			learningPathSection.setDescription(rs.getString("description"));
			learningPathSection.setCreatedAt(rs.getString("createdAt"));
			learningPathSection.setLastModifiedAt(rs.getString("lastModifiedAt"));
			learningPathSection.setMaxSectionOrderNo(rs.getInt("maxSectionOrderNo"));
			return learningPathSection;
		}
	}
	
	@Override
	public List<LearningPathSection> fetchLearningPathSections(int courseId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT lps.learningPathSectionId, lps.courseId, ")
				.append("lps.sectionTitle, lps.sectionOrderNo, lps.description, ")
				.append("lps.createdAt, lps.lastModifiedAt, ")
				.append("(SELECT MAX(lps2.sectionOrderNo) FROM learningpathsection lps2 ")
				.append("WHERE lps.courseId = :courseId ) AS maxSectionOrderNo ")
				.append("FROM learningpathsection lps ")
				.append("WHERE lps.courseId = :courseId ")
				.append("ORDER BY lps.sectionOrderNo ASC;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", courseId),
			new FetchLearningPathSectionsMapper());
	}

	@Override
	public void updateSectionOrderNo(List<LearningPathSection> LearningPathSections) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(LearningPathSections.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("UPDATE learningpathsection SET ")
				.append("sectionOrderNo=:sectionOrderNo ")
				.append("WHERE learningPathSectionId=:learningPathSectionId;")
				.toString(),
			batch);
	}

	@Override
	public void updateLearningPathSection(LearningPathSection learningPathSection) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE learningpathsection SET ")
				.append("sectionTitle = :sectionTitle, ")
				.append("description = :description ")
				.append("WHERE learningPathSectionId = :learningPathSectionId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("sectionTitle", learningPathSection.getSectionTitle())
				.addValue("description", learningPathSection.getDescription())
				.addValue("learningPathSectionId", learningPathSection.getLearningPathSectionId()));
	}

	@Override
	public void deleteLearningPathSection(int learningPathSectionId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE lps.* ")
				.append("FROM learningpathsection lps ")
				.append("WHERE lps.learningPathSectionId = :learningPathSectionId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("learningPathSectionId", learningPathSectionId));
	}
}
