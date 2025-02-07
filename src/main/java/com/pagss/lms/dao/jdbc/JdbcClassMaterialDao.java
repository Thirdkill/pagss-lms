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

import com.pagss.lms.constants.LmsClassMaterialData;
import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.ClassMaterialDao;
import com.pagss.lms.domains.ClassMaterial;

@Repository
public class JdbcClassMaterialDao extends JdbcDao implements ClassMaterialDao {

	private static class FetchClassMaterialsMapper implements RowMapper<ClassMaterial> {
		@Override
		public ClassMaterial mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassMaterial classMaterial = new ClassMaterial();
			classMaterial.setClassMaterialId(rs.getInt("classMaterialId"));
			classMaterial.setClassId(rs.getInt("classId"));
			classMaterial.setContentType(rs.getInt("contentType"));
			classMaterial.setContentUrl(rs.getString("contentUrl"));
			classMaterial.setFileName(rs.getString("fileName"));
			classMaterial.setFileLabel(rs.getString("fileLabel"));
			classMaterial.setViewStatus(rs.getInt("viewStatus"));
			classMaterial.setCreatedAt(rs.getString("createdAt"));
			classMaterial.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return classMaterial;
		}
	}
	
	@Override
	public List<ClassMaterial> fetchClassMaterials(int classId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT cm.classMaterialId, cm.classId, ")
				.append("cm.contentType, cm.contentUrl, cm.fileName, ")
				.append("cm.fileLabel, cm.viewStatus, cm.createdAt, cm.lastModifiedAt ")
				.append("FROM classmaterial cm ")
				.append("WHERE cm.classId = :classId AND cm.viewStatus = :viewStatus;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("viewStatus", LmsClassMaterialData.ACTIVE),
			new FetchClassMaterialsMapper());
	}
	
	private static class FetchClassMaterialTableMapper implements RowMapper<ClassMaterial> {
		@Override
		public ClassMaterial mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassMaterial classMaterial = new ClassMaterial();
			classMaterial.setClassMaterialId(rs.getInt("classMaterialId"));
			classMaterial.setClassId(rs.getInt("classId"));
			classMaterial.setContentType(rs.getInt("contentType"));
			classMaterial.setContentUrl(rs.getString("contentUrl"));
			classMaterial.setFileName(rs.getString("fileName"));
			classMaterial.setFileLabel(rs.getString("fileLabel"));
			classMaterial.setViewStatus(rs.getInt("viewStatus"));
			return classMaterial;
		}
	}

	@Override
	public List<ClassMaterial> fetchClassMaterialPages(int classId, int pageNumber, int pageSize) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT cm.classMaterialId, cm.classId, cm.contentType, cm.contentUrl, ")
				.append("cm.fileName, cm.fileLabel, cm.viewStatus ")
				.append("FROM classmaterial cm ")
				.append("WHERE cm.classId = :classId ")
				.append("LIMIT :pageNumber,:pageSize ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classId)
				.addValue("pageNumber", pageNumber)
				.addValue("pageSize", pageSize),
			new FetchClassMaterialTableMapper());
	}

	@Override
	public int countClassMaterials(int classId) {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
			.append("SELECT COUNT(*) FROM classmaterial cm ")
			.append("WHERE cm.classId = :classId ").toString(),
		new MapSqlParameterSource()
			.addValue("classId",classId),
			Integer.class);
	}

	@Override
	public void updateClassMaterialViewStatus(ClassMaterial classMaterial) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE classmaterial cm ")
				.append("SET cm.viewStatus = :viewStatus ")
				.append("WHERE cm.classMaterialId = :classMaterialId")
				.toString(),
			new MapSqlParameterSource()
				.addValue("viewStatus", classMaterial.getViewStatus())
				.addValue("classMaterialId", classMaterial.getClassMaterialId()));
		
	}

	@Override
	public void deleteClassMaterials(List<ClassMaterial> classMaterials) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(classMaterials.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
			.append("DELETE cm FROM classmaterial cm ")
			.append("WHERE cm.classMaterialId = :classMaterialId ")
			.toString(), 
			batch);
	}

	@Override
	public int createClassMaterial(ClassMaterial classMaterial) {
		KeyHolder classMaterialId = new GeneratedKeyHolder();
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO classmaterial ")
				.append("(classId, contentType, contentUrl, fileName, fileLabel, viewStatus) ")
				.append("VALUES (:classId, :contentType, :contentUrl, :fileName, :fileLabel, :viewStatus)")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classId", classMaterial.getClassId())
				.addValue("contentType", classMaterial.getContentType())
				.addValue("contentUrl", classMaterial.getContentUrl())
				.addValue("fileName", classMaterial.getFileName())
				.addValue("fileLabel", classMaterial.getFileLabel())
				.addValue("viewStatus", classMaterial.getViewStatus()),
				classMaterialId);
		return classMaterialId.getKey().intValue();
	}

	@Override
	public void updateContentUrl(ClassMaterial classMaterial) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE classmaterial cm SET ")
				.append("cm.contentUrl = :contentUrl, ")
				.append("cm.fileName = :fileName ")
				.append("WHERE cm.classMaterialId = :classMaterialId")
				.toString(),
			new MapSqlParameterSource()
				.addValue("contentUrl", classMaterial.getContentUrl())
				.addValue("classMaterialId", classMaterial.getClassMaterialId())
				.addValue("fileName", classMaterial.getFileName()));
	}

	@Override
	public void deleteClassMaterial(int classMaterialId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE cm FROM classmaterial cm ")
				.append("WHERE cm.classMaterialId = :classMaterialId")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classMaterialId", classMaterialId));
	}
	
	private static class FetchClassMaterialMapper implements RowMapper<ClassMaterial> {
		@Override
		public ClassMaterial mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassMaterial classMaterial = new ClassMaterial();
			classMaterial.setClassMaterialId(rs.getInt("classMaterialId"));
			classMaterial.setClassId(rs.getInt("classId"));
			classMaterial.setContentType(rs.getInt("contentType"));
			classMaterial.setContentUrl(rs.getString("contentUrl"));
			classMaterial.setFileName(rs.getString("fileName"));
			classMaterial.setFileLabel(rs.getString("fileLabel"));
			classMaterial.setViewStatus(rs.getInt("viewStatus"));
			classMaterial.setCreatedAt(rs.getString("createdAt"));
			classMaterial.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return classMaterial;
		}
	}

	@Override
	public ClassMaterial fetchClassMaterial(int classMaterialId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT cm.classMaterialId, cm.classId, cm.contentType, ")
				.append("cm.fileName, cm.fileLabel, cm.viewStatus, cm.contentUrl, ")
				.append("cm.createdAt, cm.lastModifiedAt ")
				.append("FROM classmaterial cm ")
				.append("WHERE cm.classMaterialId = :classMaterialId;")
				.toString(), 
			new MapSqlParameterSource()
				.addValue("classMaterialId", classMaterialId),
			new FetchClassMaterialMapper());
	}

	@Override
	public int fetchLatestClassMaterialId() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT (IFNULL(MAX(cm.classMaterialId),0)+1) AS classMaterialId ")
				.append("FROM classmaterial cm  ")
				.toString(),
			new MapSqlParameterSource(), 
			Integer.class);
	}

}
