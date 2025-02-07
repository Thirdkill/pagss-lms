package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.ExamInfoDao;
import com.pagss.lms.domains.ExamInfo;

@Repository
public class JdbcExamInfoDao extends JdbcDao implements ExamInfoDao {

	private static class FetchExamInfoPagesMapper implements RowMapper<ExamInfo> {
		@Override
		public ExamInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExamInfo examInfo = new ExamInfo();
			examInfo.setExamId(rs.getInt("examId"));
			examInfo.setExamCode(rs.getString("examCode"));
			examInfo.setExamType(rs.getInt("examType"));
			examInfo.setTitle(rs.getString("title"));
			examInfo.setDescription(rs.getString("description"));
			examInfo.setStatus(rs.getInt("status"));
			examInfo.setAllowAttachment(rs.getInt("allowAttachment"));
			examInfo.setModifiedBy(rs.getString("modifiedBy"));
			examInfo.setCreatedAt(rs.getString("createdAt"));
			examInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return examInfo;
		}
	}
	
	@Override
	public List<ExamInfo> fetchExamInfoPages(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ei.examId, ei.examCode, ei.examType, ")
				.append("ei.title, ei.description, ei.status, ")
				.append("ei.allowAttachment, ei.modifiedBy, ")
				.append("ei.createdAt,ei.lastModifiedAt ")
				.append("FROM examinfo ei ")
				.append("ORDER BY ei.title ASC ")
				.append("LIMIT :pageNo,:pageSize")
				.toString(), 
			new MapSqlParameterSource()
				.addValue("pageNo", tableCommand.getPageNumber())
				.addValue("pageSize", tableCommand.getPageSize()),
			new FetchExamInfoPagesMapper());
	}
	
	@Override
	public int countExamInfos(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
				.append("SELECT COUNT(ei.examId) ")
				.append("FROM examinfo ei ")
				.toString(), 
			new MapSqlParameterSource(),
			Integer.class);
	}

	private static class SearchExamInfosMapper implements RowMapper<ExamInfo> {
		@Override
		public ExamInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExamInfo examInfo = new ExamInfo();
			examInfo.setExamId(rs.getInt("examId"));
			examInfo.setExamCode(rs.getString("examCode"));
			examInfo.setExamType(rs.getInt("examType"));
			examInfo.setTitle(rs.getString("title"));
			examInfo.setDescription(rs.getString("description"));
			examInfo.setStatus(rs.getInt("status"));
			examInfo.setAllowAttachment(rs.getInt("allowAttachment"));
			examInfo.setModifiedBy(rs.getString("modifiedBy"));
			examInfo.setCreatedAt(rs.getString("createdAt"));
			examInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return examInfo;
		}
	}
	
	@Override
	public List<ExamInfo> searchExamInfo(int pageSize, int pageNo, String keyword) {
		return this.jdbcTemplate.query(
				new StringBuffer()
					.append("SELECT ei.examId, ei.examCode, ei.examType, ")
					.append("ei.title, ei.description, ei.status, ")
					.append("ei.allowAttachment, ei.modifiedBy, ")
					.append("ei.createdAt,ei.lastModifiedAt ")
					.append("FROM examinfo ei ")
					.append("WHERE ei.title like :keyword ")
					.append("ORDER BY ei.createdAt DESC ")
					.append("LIMIT :pageNo,:pageSize")
					.toString(), 
				new MapSqlParameterSource()
					.addValue("pageNo", pageNo)
					.addValue("pageSize", pageSize)
					.addValue("keyword", "%"+keyword+"%"),
				new SearchExamInfosMapper());
	}

	@Override
	public int countSearchExamInfo(String keyword) {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
				.append("SELECT COUNT(ei.examId) ")
				.append("FROM examinfo ei ")
				.append("WHERE ei.title like :keyword ")
				.toString(), 
			new MapSqlParameterSource()
				.addValue("keyword", "%"+keyword+"%"),
			Integer.class);
	}
	
	public int createExamInfo(ExamInfo examInfo) {
		KeyHolder examIdHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO examinfo(")
				.append("examCode, examType, title,")
				.append("description, status, allowAttachment, modifiedBy) ")
				.append("VALUES(:examCode, :examType,:title, ")
				.append(":description,:status,:allowAttachment,:modifiedBy)")
				.toString(),
			new MapSqlParameterSource()
				.addValue("examCode", examInfo.getExamCode())
				.addValue("examType", examInfo.getExamType())
				.addValue("title", examInfo.getTitle())
				.addValue("description", examInfo.getDescription())
				.addValue("status", examInfo.getStatus())
				.addValue("allowAttachment", examInfo.getAllowAttachment())
				.addValue("modifiedBy", examInfo.getModifiedBy()),
				examIdHolder);
		return examIdHolder.getKey().intValue();
	}
	
	public int countExamCode(String examCode) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ei.examCode) ")
				.append("FROM examinfo ei ")
				.append("WHERE ei.examCode = :examCode")
				.toString(),
			new MapSqlParameterSource()
				.addValue("examCode", examCode),
			Integer.class);
	}

	@Override
	public int fetchLatestExamId() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT (IFNULL(MAX(ei.examId),0)+1) AS examId ")
				.append("FROM examinfo ei;")
				.toString(),
			new MapSqlParameterSource(),
			Integer.class);
	}
	
	private static class FetchExamInfoMapper implements RowMapper<ExamInfo> {
		@Override
		public ExamInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExamInfo examInfo = new ExamInfo();
			examInfo.setExamId(rs.getInt("examId"));
			examInfo.setExamCode(rs.getString("examCode"));
			examInfo.setExamType(rs.getInt("examType"));
			examInfo.setTitle(rs.getString("title"));
			examInfo.setDescription(rs.getString("description"));
			examInfo.setStatus(rs.getInt("status"));
			examInfo.setAllowAttachment(rs.getInt("allowAttachment"));
			examInfo.setModifiedBy(rs.getString("modifiedBy"));
			examInfo.setCreatedAt(rs.getString("createdAt"));
			examInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return examInfo;
		}
	}
	
	@Override
	public ExamInfo fetchExamInfo(int examId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT ei.examId, ei.examCode, ei.examType, ")
				.append("ei.title, ei.description, ei.status, ")
				.append("ei.allowAttachment, ei.modifiedBy, ")
				.append("ei.createdAt,ei.lastModifiedAt ")
				.append("FROM examinfo ei ")
				.append("WHERE ei.examId = :examId")
				.toString(),
			new MapSqlParameterSource()
				.addValue("examId", examId),
			new FetchExamInfoMapper());
	}
	
	@Override
	public void updateExamInfo(ExamInfo examInfo) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE examinfo ei SET ")
				.append("ei.examType = :examType, ")
				.append("ei.title = :title, ")
				.append("ei.description = :description, ")
				.append("ei.status = :status, ")
				.append("ei.allowAttachment = :alllowAttachment, ")
				.append("ei.modifiedBy = :modifiedBy ")
				.append("WHERE ei.examId = :examId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("examType", examInfo.getExamType())
				.addValue("title", examInfo.getTitle())
				.addValue("description", examInfo.getDescription())
				.addValue("status", examInfo.getStatus())
				.addValue("alllowAttachment", examInfo.getAllowAttachment())
				.addValue("modifiedBy", examInfo.getModifiedBy())
				.addValue("examId", examInfo.getExamId()));
	}
	
	private static class fetchExamInfoPagesWithStatusMapper implements RowMapper<ExamInfo> {
		@Override
		public ExamInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExamInfo examInfo = new ExamInfo();
			examInfo.setExamId(rs.getInt("examId"));
			examInfo.setExamCode(rs.getString("examCode"));
			examInfo.setExamType(rs.getInt("examType"));
			examInfo.setTitle(rs.getString("title"));
			examInfo.setDescription(rs.getString("description"));
			examInfo.setStatus(rs.getInt("status"));
			examInfo.setAllowAttachment(rs.getInt("allowAttachment"));
			examInfo.setModifiedBy(rs.getString("modifiedBy"));
			examInfo.setCreatedAt(rs.getString("createdAt"));
			examInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return examInfo;
		}
	}
	
	@Override
	public List<ExamInfo> fetchExamInfoPagesWithStatus(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
				new StringBuffer()
					.append("SELECT ei.examId, ei.examCode, ei.examType, ")
					.append("ei.title, ei.description, ei.status, ")
					.append("ei.allowAttachment, ei.modifiedBy, ")
					.append("ei.createdAt,ei.lastModifiedAt ")
					.append("FROM examinfo ei ")
					.append("WHERE ei.status = :status AND ")
					.append("examId NOT IN(SELECT ce.examId ")
					.append("FROM courseexam ce ")
					.append("WHERE courseId = :courseId) ")
					.append("ORDER BY ei.title ASC ")
					.append("LIMIT :pageNo,:pageSize")
					.toString(), 
				new MapSqlParameterSource()
					.addValue("pageNo", tableCommand.getPageNumber())
					.addValue("pageSize", tableCommand.getPageSize())
					.addValue("status", tableCommand.getSearchByExamStatus())
					.addValue("courseId", tableCommand.getCourseId()),
				new fetchExamInfoPagesWithStatusMapper());
	}

	@Override
	public int countExamPagesWithStatus(int courseId, int status) {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
					.append("SELECT COUNT(*) ")
					.append("FROM examinfo ei ")
					.append("WHERE ei.status = :status AND ")
					.append("examId NOT IN(SELECT ce.examId ")
					.append("FROM courseexam ce ")
					.append("WHERE courseId = :courseId) ")
					.toString(), 
				new MapSqlParameterSource()
					.addValue("status", status)
					.addValue("courseId", courseId),
				Integer.class);
	}

	private static class searchExamInfoPagesWithStatusMapper implements RowMapper<ExamInfo> {
		@Override
		public ExamInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExamInfo examInfo = new ExamInfo();
			examInfo.setExamId(rs.getInt("examId"));
			examInfo.setExamCode(rs.getString("examCode"));
			examInfo.setExamType(rs.getInt("examType"));
			examInfo.setTitle(rs.getString("title"));
			examInfo.setDescription(rs.getString("description"));
			examInfo.setStatus(rs.getInt("status"));
			examInfo.setAllowAttachment(rs.getInt("allowAttachment"));
			examInfo.setModifiedBy(rs.getString("modifiedBy"));
			examInfo.setCreatedAt(rs.getString("createdAt"));
			examInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return examInfo;
		}
	}
	
	@Override
	public List<ExamInfo> searchExamInfoPagesWithStatus(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
				new StringBuffer()
					.append("SELECT ei.examId, ei.examCode, ei.examType, ")
					.append("ei.title, ei.description, ei.status, ")
					.append("ei.allowAttachment, ei.modifiedBy, ")
					.append("ei.createdAt,ei.lastModifiedAt ")
					.append("FROM examinfo ei ")
					.append("WHERE ei.examId NOT IN(SELECT ce.examId ")
					.append("FROM courseexam ce ")
					.append("WHERE courseId = :courseId) AND ")
					.append("ei.status = :status AND ")
					.append("ei.title like :keyword OR ")
					.append("ei.examType like :examType OR ")
					.append("ei.description like :keyword ")
					.append("ORDER BY ei.title ASC ")
					.append("LIMIT :pageNo,:pageSize;")
					.toString(), 
				new MapSqlParameterSource()
					.addValue("pageNo", tableCommand.getPageNumber())
					.addValue("pageSize", tableCommand.getPageSize())
					.addValue("status", tableCommand.getSearchByExamStatus())
					.addValue("keyword","%"+tableCommand.getKeyword()+"%")
					.addValue("examType", tableCommand.getSearchByExamType())
					.addValue("courseId", tableCommand.getCourseId()),
				new searchExamInfoPagesWithStatusMapper());
	}
	
	@Override
	public int countSearchExamInfoPagesWithStatus(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
					.append("SELECT COUNT(ei.examId) ")
					.append("FROM examinfo ei ")
					.append("WHERE ei.examId NOT IN(SELECT ce.examId ")
					.append("FROM courseexam ce ")
					.append("WHERE courseId = :courseId) AND ")
					.append("ei.status = :status AND ")
					.append("ei.title like :keyword OR ")
					.append("ei.examType like :examType OR ")
					.append("ei.description like :keyword ")
					.toString(), 
				new MapSqlParameterSource()
					.addValue("status", tableCommand.getSearchByExamStatus())
					.addValue("keyword","%"+tableCommand.getKeyword()+"%")
					.addValue("examType", tableCommand.getSearchByExamType())
					.addValue("courseId", tableCommand.getCourseId()),
				Integer.class);
	}

	private static class fetchExamInfosWithStatusMapper implements RowMapper<ExamInfo> {
		@Override
		public ExamInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExamInfo examInfo = new ExamInfo();
			examInfo.setExamId(rs.getInt("examId"));
			examInfo.setExamCode(rs.getString("examCode"));
			examInfo.setExamType(rs.getInt("examType"));
			examInfo.setTitle(rs.getString("title"));
			examInfo.setDescription(rs.getString("description"));
			examInfo.setStatus(rs.getInt("status"));
			examInfo.setAllowAttachment(rs.getInt("allowAttachment"));
			examInfo.setModifiedBy(rs.getString("modifiedBy"));
			examInfo.setCreatedAt(rs.getString("createdAt"));
			examInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return examInfo;
		}
	}
	
	@Override
	public List<ExamInfo> fetchExamInfosWithStatus(ExamInfo examInfo) {
		return this.jdbcTemplate.query(
				new StringBuffer()
					.append("SELECT ei.examId, ei.examCode, ei.examType, ")
					.append("ei.title, ei.description, ei.status, ")
					.append("ei.allowAttachment, ei.modifiedBy, ")
					.append("ei.createdAt,ei.lastModifiedAt ")
					.append("FROM examinfo ei ")
					.append("WHERE ei.status = :status AND ")
					.append("ei.examType = :examType AND ")
					.append("examId NOT IN(SELECT ce.examId ")
					.append("FROM courseexam ce ")
					.append("WHERE courseId = :courseId) ")
					.append("ORDER BY ei.title ASC ")
					.toString(), 
				new MapSqlParameterSource()
					.addValue("status", examInfo.getStatus())
					.addValue("courseId",examInfo.getCourseId())
					.addValue("examType",examInfo.getExamType()),
				new fetchExamInfosWithStatusMapper());
	}
	
	private static class searchExamInfoPagesOnClassWithStatusMapper implements RowMapper<ExamInfo> {
		@Override
		public ExamInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExamInfo examInfo = new ExamInfo();
			examInfo.setExamId(rs.getInt("examId"));
			examInfo.setExamCode(rs.getString("examCode"));
			examInfo.setExamType(rs.getInt("examType"));
			examInfo.setTitle(rs.getString("title"));
			examInfo.setDescription(rs.getString("description"));
			examInfo.setStatus(rs.getInt("status"));
			examInfo.setAllowAttachment(rs.getInt("allowAttachment"));
			examInfo.setModifiedBy(rs.getString("modifiedBy"));
			examInfo.setCreatedAt(rs.getString("createdAt"));
			examInfo.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return examInfo;
		}
	}

	@Override
	public List<ExamInfo> searchExamInfosPageOnClassWithStatus(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ei.examId, ei.examCode, ei.examType, ")
				.append("ei.title, ei.description, ei.status, ")
				.append("ei.allowAttachment, ei.modifiedBy, ")
				.append("ei.createdAt,ei.lastModifiedAt ")
				.append("FROM examinfo ei ")
				.append("WHERE ei.examId NOT IN(SELECT ce.examId ")
				.append("FROM classexam ce ")
				.append("WHERE classId = :classId) AND ")
				.append("ei.status = :status AND ")
				.append("ei.title like :keyword OR ")
				.append("ei.examType like :examType OR ")
				.append("ei.description like :keyword ")
				.append("ORDER BY ei.title ASC ")
				.append("LIMIT :pageNo,:pageSize;")
				.toString(), 
			new MapSqlParameterSource()
				.addValue("pageNo", tableCommand.getPageNumber())
				.addValue("pageSize", tableCommand.getPageSize())
				.addValue("status", tableCommand.getSearchByExamStatus())
				.addValue("keyword","%"+tableCommand.getKeyword()+"%")
				.addValue("examType", tableCommand.getSearchByExamType())
				.addValue("classId", tableCommand.getClassId()),
			new searchExamInfoPagesOnClassWithStatusMapper());
	}

	@Override
	public int countSearchExamInfosPageOnClassWithStatus(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ei.examId) ")
				.append("FROM examinfo ei ")
				.append("WHERE ei.examId NOT IN(SELECT ce.examId ")
				.append("FROM classexam ce ")
				.append("WHERE classId = :classId) AND ")
				.append("ei.status = :status AND ")
				.append("ei.title like :keyword OR ")
				.append("ei.examType like :examType OR ")
				.append("ei.description like :keyword ")
				.toString(), 
			new MapSqlParameterSource()
				.addValue("status", tableCommand.getSearchByExamStatus())
				.addValue("keyword","%"+tableCommand.getKeyword()+"%")
				.addValue("examType", tableCommand.getSearchByExamType())
				.addValue("classId", tableCommand.getClassId()),
			Integer.class);
	}

	@Override
	public List<ExamInfo> fetchExamInfosPageOnClassWithStatus(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ei.examId, ei.examCode, ei.examType, ")
				.append("ei.title, ei.description, ei.status, ")
				.append("ei.allowAttachment, ei.modifiedBy, ")
				.append("ei.createdAt,ei.lastModifiedAt ")
				.append("FROM examinfo ei ")
				.append("WHERE ei.status = :status AND ")
				.append("examId NOT IN(SELECT ce.examId ")
				.append("FROM classexam ce ")
				.append("WHERE classId = :classId) ")
				.append("ORDER BY ei.title ASC ")
				.append("LIMIT :pageNo,:pageSize")
				.toString(), 
			new MapSqlParameterSource()
				.addValue("pageNo", tableCommand.getPageNumber())
				.addValue("pageSize", tableCommand.getPageSize())
				.addValue("status", tableCommand.getSearchByExamStatus())
				.addValue("classId", tableCommand.getClassId()),
			new fetchExamInfoPagesWithStatusMapper());
	}

	@Override
	public int countxamInfosPageOnClassWithStatus(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) ")
				.append("FROM examinfo ei ")
				.append("WHERE ei.status = :status AND ")
				.append("examId NOT IN(SELECT ce.examId ")
				.append("FROM classexam ce ")
				.append("WHERE classId = :classId) ")
				.toString(), 
			new MapSqlParameterSource()
				.addValue("status", tableCommand.getSearchByExamStatus())
				.addValue("classId", tableCommand.getClassId()),
			Integer.class);
	}
}
