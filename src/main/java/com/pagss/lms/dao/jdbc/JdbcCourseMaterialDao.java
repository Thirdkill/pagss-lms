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
import com.pagss.lms.dao.interfaces.CourseMaterialDao;
import com.pagss.lms.domains.CourseMaterial;

@Repository
public class JdbcCourseMaterialDao extends JdbcDao implements CourseMaterialDao {

	@Override
	public int createCourseMaterial(CourseMaterial courseMaterial) {
		KeyHolder courseMaterialId = new GeneratedKeyHolder();
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO coursematerial(")
				.append("courseId, contentType, contentUrl, ")
				.append("fileLabel,viewStatus)")
				.append("VALUES(")
				.append(":courseId,:contentType, :contentUrl, ")
				.append(":fileLabel, :viewStatus);")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", courseMaterial.getCourseId())
				.addValue("contentType", courseMaterial.getContentType())
				.addValue("contentUrl", courseMaterial.getContentUrl())
				.addValue("fileLabel", courseMaterial.getFileLabel())
				.addValue("viewStatus", courseMaterial.getViewStatus()),
			courseMaterialId);
		return courseMaterialId.getKey().intValue();
	}

	private static class FetchCourseMaterialMapper implements RowMapper<CourseMaterial> {
		@Override
		public CourseMaterial mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseMaterial courseMaterial = new CourseMaterial();
			courseMaterial.setCourseMaterialId(rs.getInt("courseMaterialId"));
			courseMaterial.setCourseId(rs.getInt("courseId"));
			courseMaterial.setContentType(rs.getInt("contentType"));
			courseMaterial.setContentUrl(rs.getString("contentUrl"));
			courseMaterial.setViewStatus(rs.getInt("viewStatus"));
			courseMaterial.setFileName(rs.getString("fileName"));
			courseMaterial.setFileLabel(rs.getString("fileLabel"));
			courseMaterial.setCreatedAt(rs.getString("createdAt"));
			courseMaterial.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return courseMaterial;
		}
	}
	
	@Override
	public CourseMaterial fetchCourseMaterial(int courseMaterialId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT cm.courseMaterialId, cm.courseId, ")
				.append("cm.contentType, cm.contentUrl, cm.viewStatus, ")
				.append("cm.fileName, cm.fileLabel, ")
				.append("cm.createdAt, cm.lastModifiedAt ")
				.append("FROM coursematerial cm ")
				.append("WHERE cm.courseMaterialId = :courseMaterialId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseMaterialId", courseMaterialId),
			new FetchCourseMaterialMapper());
	}

	@Override
	public void updateContentUrl(CourseMaterial courseMaterial) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE coursematerial SET ")
				.append("contentUrl = :contentUrl, ")
				.append("fileName = :fileName ")
				.append("WHERE courseMaterialId = :courseMaterialId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseMaterialId", courseMaterial.getCourseMaterialId())
				.addValue("contentUrl", courseMaterial.getContentUrl())
				.addValue("fileName", courseMaterial.getFileName()));
	}

	private static class FetchCourseMaterialsPagesMapper implements RowMapper<CourseMaterial> {
		@Override
		public CourseMaterial mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseMaterial courseMaterial = new CourseMaterial();
			courseMaterial.setCourseMaterialId(rs.getInt("courseMaterialId"));
			courseMaterial.setCourseId(rs.getInt("courseId"));
			courseMaterial.setContentType(rs.getInt("contentType"));
			courseMaterial.setContentUrl(rs.getString("contentUrl"));
			courseMaterial.setViewStatus(rs.getInt("viewStatus"));
			courseMaterial.setFileName(rs.getString("fileName"));
			courseMaterial.setFileLabel(rs.getString("fileLabel"));
			courseMaterial.setCreatedAt(rs.getString("createdAt"));
			courseMaterial.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return courseMaterial;
		}
	}
	
	@Override
	public List<CourseMaterial> fetchCourseMaterialsPages(int courseId,int pageSize, int pageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT cm.courseMaterialId, cm.courseId, ")
				.append("cm.contentType, cm.contentUrl, cm.viewStatus, ")
				.append("cm.fileName, cm.fileLabel, ")
				.append("cm.createdAt, cm.lastModifiedAt ")
				.append("FROM coursematerial cm ")
				.append("WHERE cm.courseId = :courseId ")
				.append("AND cm.contentUrl != '' ")
				.append("ORDER BY cm.createdAt DESC ")
				.append("LIMIT :pageNumber,:pageSize;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", courseId)
				.addValue("pageNumber", pageNo)
				.addValue("pageSize", pageSize),
			new FetchCourseMaterialsPagesMapper());
	}
	
	@Override
	public int countTotalCourseMaterials(int courseId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) ")
				.append("FROM coursematerial ")
				.append("WHERE courseId = :courseId ")
				.append("AND contentUrl != '' ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", courseId),
		Integer.class);
	}
	
	@Override
	public void deleteCourseMaterials(List<CourseMaterial> courseMaterials) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(courseMaterials.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("DELETE FROM coursematerial ")
				.append("WHERE courseMaterialId = :courseMaterialId;")
				.toString(),
			batch);
	}
	
	@Override
	public void deleteCourseMaterial(int courseMaterialId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE FROM coursematerial ")
				.append("WHERE courseMaterialId = :courseMaterialId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseMaterialId", courseMaterialId));
	}

	@Override
	public void updateViewStatus(CourseMaterial courseMaterial) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE coursematerial SET ")
				.append("viewStatus = :viewStatus ")
				.append("WHERE courseMaterialId = :courseMaterialId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseMaterialId", courseMaterial.getCourseMaterialId())
				.addValue("viewStatus", courseMaterial.getViewStatus()));
	}

	private static class FetchCourseMaterialsWithViewStatusMapper implements RowMapper<CourseMaterial> {
		@Override
		public CourseMaterial mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseMaterial courseMaterial = new CourseMaterial();
			courseMaterial.setCourseMaterialId(rs.getInt("courseMaterialId"));
			courseMaterial.setCourseId(rs.getInt("courseId"));
			courseMaterial.setContentType(rs.getInt("contentType"));
			courseMaterial.setContentUrl(rs.getString("contentUrl"));
			courseMaterial.setViewStatus(rs.getInt("viewStatus"));
			courseMaterial.setFileName(rs.getString("fileName"));
			courseMaterial.setFileLabel(rs.getString("fileLabel"));
			courseMaterial.setCreatedAt(rs.getString("createdAt"));
			courseMaterial.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return courseMaterial;
		}
	}
	
	@Override
	public List<CourseMaterial> fetchCourseMaterialsWithViewStatus(int courseId,int viewStatus) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT cm.courseMaterialId, cm.courseId, ")
				.append("cm.contentType, cm.contentUrl, cm.viewStatus, ")
				.append("cm.fileName, cm.fileLabel, ")
				.append("cm.createdAt, cm.lastModifiedAt ")
				.append("FROM coursematerial cm ")
				.append("WHERE cm.courseId = :courseId ")
				.append("AND cm.contentUrl != '' AND cm.viewStatus = :viewStatus ")
				.append("ORDER BY cm.fileLabel ASC;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", courseId)
				.addValue("viewStatus", viewStatus),
			new FetchCourseMaterialsWithViewStatusMapper());
	}

	@Override
	public int insertCourseMaterial(CourseMaterial courseMaterial) {
		KeyHolder courseMaterialId = new GeneratedKeyHolder();
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO coursematerial(")
				.append("courseId,contentUrl,fileName) VALUES(")
				.append(":courseId,:contentUrl,:fileName);")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", courseMaterial.getCourseId())
				.addValue("contentUrl", courseMaterial.getContentUrl())
				.addValue("fileName", courseMaterial.getFileName()),
			courseMaterialId);
		return courseMaterialId.getKey().intValue();
	}

	@Override
	public int fetchLastInsertedCourseMaterialId() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT MAX(cm.courseMaterialId) ")
				.append("FROM coursematerial cm ")
				.append("LIMIT 1;")
				.toString(),
			new MapSqlParameterSource(),
			Integer.class);
	}

	@Override
	public void updateCourseMaterial(CourseMaterial courseMaterial) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE coursematerial SET ")
				.append("contentType = :contentType, ")
				.append("fileLabel = :fileLabel, ")
				.append("viewStatus = :viewStatus ")
				.append("WHERE courseMaterialId = :courseMaterialId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseMaterialId", courseMaterial.getCourseMaterialId())
				.addValue("contentType", courseMaterial.getContentType())
				.addValue("fileLabel", courseMaterial.getFileLabel())
				.addValue("viewStatus", courseMaterial.getViewStatus()));
	}
}
