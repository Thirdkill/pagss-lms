package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.TrainingCategoryDao;
import com.pagss.lms.domains.TrainingCategory;

@Repository
public class JdbcTrainingCategoryDao extends JdbcDao implements TrainingCategoryDao {

	private static class FetchTrainingCategoriesPagesMapper implements RowMapper<TrainingCategory> {
		@Override
		public TrainingCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
			TrainingCategory trainingCategory = new TrainingCategory();
			trainingCategory.setCategoryId(rs.getInt("categoryId"));
			trainingCategory.setCategoryName(getStringValue(rs.getString("categoryName")));
			trainingCategory.setCategoryCode(getStringValue(rs.getString("categoryCode")));
			trainingCategory.setDescription(getStringValue(rs.getString("description")));
			trainingCategory.setStatus(rs.getInt("status"));
			trainingCategory.setCreatedAt(rs.getString("createdAt"));
			trainingCategory.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return trainingCategory;
		}
	}
	
	@Override
	public List<TrainingCategory> fetchTrainingCategoriesPages(int pageSize, int pageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT tc.categoryId, tc.categoryCode, tc.categoryName, ")
				.append("tc.description, tc.status, tc.createdAt, tc.lastModifiedAt ")
				.append("FROM trainingcategory tc ")
				.append("ORDER BY tc.categoryCode ASC ")
				.append("LIMIT :pageNo,:pageSize ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("pageSize", pageSize)
				.addValue("pageNo", pageNo),
			new FetchTrainingCategoriesPagesMapper());
	}

	@Override
	public int countTotalCategories() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) ")
				.append("FROM trainingcategory ")
				.toString(),
			new MapSqlParameterSource(),
			Integer.class);
	}
	
	private static class SearchTrainingCategoriesMapper implements RowMapper<TrainingCategory> {
		@Override
		public TrainingCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
			TrainingCategory trainingCategory = new TrainingCategory();
			trainingCategory.setCategoryId(rs.getInt("categoryId"));
			trainingCategory.setCategoryName(getStringValue(rs.getString("categoryName")));
			trainingCategory.setCategoryCode(getStringValue(rs.getString("categoryCode")));
			trainingCategory.setDescription(getStringValue(rs.getString("description")));
			trainingCategory.setStatus(rs.getInt("status"));
			trainingCategory.setCreatedAt(rs.getString("createdAt"));
			trainingCategory.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return trainingCategory;
		}
	}
	
	@Override
	public List<TrainingCategory> searchTrainingCategories(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
				new StringBuffer()
					.append("SELECT tc.categoryId, tc.categoryCode, tc.categoryName, ")
					.append("tc.description, tc.status, tc.createdAt, tc.lastModifiedAt ")
					.append("FROM trainingcategory tc ")
					.append(tableCommand.getWhereClause())
					.append(tableCommand.getOrderClause())
					.append("LIMIT :pageNo,:pageSize ")
					.toString(),
				new MapSqlParameterSource()
					.addValue("pageSize", tableCommand.getPageSize())
					.addValue("pageNo", tableCommand.getPageNumber())
					.addValue("keyword", "%" + tableCommand.getKeyword() + "%"),
				new SearchTrainingCategoriesMapper());
	}

	@Override
	public int countSearchedTrainingCategories(String keyword) {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
					.append("SELECT COUNT(*) ")
					.append("FROM trainingcategory tc ")
					.append("WHERE tc.categoryCode like :keyword OR tc.categoryName like :keyword ")
					.append("OR tc.description like :keyword ")
					.toString(),
				new MapSqlParameterSource()
					.addValue("keyword", "%" + keyword + "%"),
				Integer.class);
	}
	
	@Override
	public int fetchLatestCategoryId() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT (IFNULL(MAX(tc.categoryId),0)+1) ")
				.append("FROM trainingcategory tc ")
				.toString(),
			new MapSqlParameterSource(),
			Integer.class);
	}
	
	@Override
	public int countTrainingCategoryCode(String categoryCode) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(tc.categoryId) ")
				.append("FROM trainingcategory tc ")
				.append("WHERE tc.categoryCode = :categoryCode")
				.toString(),
			new MapSqlParameterSource()
				.addValue("categoryCode", categoryCode),
			Integer.class);
	}

	private static class FetchTrainingCategoriesMapper implements RowMapper<TrainingCategory> {
		@Override
		public TrainingCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
			TrainingCategory trainingCategory = new TrainingCategory();
			trainingCategory.setCategoryId(rs.getInt("categoryId"));
			trainingCategory.setCategoryName(getStringValue(rs.getString("categoryName")));
			trainingCategory.setCategoryCode(getStringValue(rs.getString("categoryCode")));
			trainingCategory.setDescription(getStringValue(rs.getString("description")));
			trainingCategory.setStatus(rs.getInt("status"));
			trainingCategory.setCreatedAt(rs.getString("createdAt"));
			trainingCategory.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return trainingCategory;
		}
	}
	
	@Override
	public List<TrainingCategory> fetchTrainingCategories() {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT tc.categoryId, tc.categoryCode, tc.categoryName, ")
				.append("tc.description, tc.status, tc.createdAt, tc.lastModifiedAt ")
				.append("FROM trainingcategory tc ")
				.append("ORDER BY tc.categoryCode ASC ")
				.toString(),
			new FetchTrainingCategoriesMapper());
	}

	private static class FetchTrainingCategoriesByStatusMapper implements RowMapper<TrainingCategory> {
		@Override
		public TrainingCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
			TrainingCategory trainingCategory = new TrainingCategory();
			trainingCategory.setCategoryId(rs.getInt("categoryId"));
			trainingCategory.setCategoryName(getStringValue(rs.getString("categoryName")));
			trainingCategory.setCategoryCode(getStringValue(rs.getString("categoryCode")));
			trainingCategory.setDescription(getStringValue(rs.getString("description")));
			trainingCategory.setStatus(rs.getInt("status"));
			trainingCategory.setCreatedAt(rs.getString("createdAt"));
			trainingCategory.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return trainingCategory;
		}
	}
	
	@Override
	public List<TrainingCategory> fetchTrainingCategoriesByStatus(int status) {
		return this.jdbcTemplate.query(
				new StringBuffer()
					.append("SELECT tc.categoryId, tc.categoryCode, tc.categoryName, ")
					.append("tc.description, tc.status, tc.createdAt, tc.lastModifiedAt ")
					.append("FROM trainingcategory tc ")
					.append("WHERE tc.status = :status ")
					.append("ORDER BY tc.categoryCode ASC ")
					.toString(),
				new MapSqlParameterSource()
					.addValue("status", status),
				new FetchTrainingCategoriesByStatusMapper());
	}
}
