package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.DifficultyLevelDao;
import com.pagss.lms.domains.DifficultyLevel;

@Repository
public class JdbcDifficultyLevelDao extends JdbcDao implements DifficultyLevelDao {
	
	private static class FetchDifficultyLevelsMapper implements RowMapper<DifficultyLevel> {
		@Override
		public DifficultyLevel mapRow(ResultSet rs, int rowNum) throws SQLException {
			DifficultyLevel difficultyLevel = new DifficultyLevel();
			difficultyLevel.setDifficultyId(rs.getInt("difficultyId"));
			difficultyLevel.setDifficultyName(rs.getString("difficultyName"));
			difficultyLevel.setStatus(rs.getInt("status"));
			difficultyLevel.setCreatedAt(rs.getString("createdAt"));
			difficultyLevel.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return difficultyLevel;
		}
	}
	
	@Override
	public List<DifficultyLevel> fetchDifficultyLevels() {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT df.difficultyId, df.difficultyName, ")
				.append("df.status, df.createdAt, df.lastModifiedAt ")
				.append("FROM difficultylevel df ")
				.append("WHERE df.status = 1 ")
				.append("ORDER BY df.difficultyName ASC")
				.toString(), 
			new FetchDifficultyLevelsMapper());
	}
	
	private static class FetchDifficultyLevelsTableMapper implements RowMapper<DifficultyLevel> {
		@Override
		public DifficultyLevel mapRow(ResultSet rs, int rowNum) throws SQLException {
			DifficultyLevel difficultyLevel = new DifficultyLevel();
			difficultyLevel.setDifficultyId(rs.getInt("difficultyId"));
			difficultyLevel.setDifficultyName(rs.getString("difficultyName"));
			difficultyLevel.setStatus(rs.getInt("status"));
			return difficultyLevel;
		}
	}

	@Override
	public List<DifficultyLevel> fetchDifficultyLevelsTable(int pageSize, int PageNo) {
		return this.jdbcTemplate.query(
		new StringBuffer()
			.append("SELECT df.difficultyId, df.difficultyName,df.status ")
			.append("FROM difficultylevel df ")
			.append("ORDER BY df.difficultyName ASC ")
			.append("LIMIT :pageNo,:pageSize")
			.toString(),
		new MapSqlParameterSource()
			.addValue("pageSize", pageSize)
			.addValue("pageNo", PageNo),
		new FetchDifficultyLevelsTableMapper());
	}

	@Override
	public int countTotalDificultyLevels() {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT COUNT(*) FROM difficultylevel df ")
			.toString(),
		new MapSqlParameterSource(),
		Integer.class);
	}

	@Override
	public List<DifficultyLevel> fetchSearchedDifficultyLevelsTable(String keyword, int pageSize, int PageNo) {
		return this.jdbcTemplate.query(
		new StringBuffer()
			.append("SELECT df.difficultyId, df.difficultyName,df.status ")
			.append("FROM difficultylevel df ")
			.append("WHERE df.difficultyName like :difficultyName ")
			.append("ORDER BY df.difficultyName ASC ")
			.append("LIMIT :pageNo,:pageSize")
			.toString(),
		new MapSqlParameterSource()
			.addValue("difficultyName", "%" + keyword + "%")
			.addValue("pageSize", pageSize)
			.addValue("pageNo", PageNo),
		new FetchDifficultyLevelsTableMapper());
	}

	@Override
	public int countTotalSearchedDificultyLevels(String keyword) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) FROM difficultylevel df ")
				.append("WHERE df.difficultyName like :difficultyName ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("difficultyName", "%" + keyword + "%"),
			Integer.class);
	}

	@Override
	public void addDifficultyLevel(DifficultyLevel difficultylevel) {
		this.jdbcTemplate.update(
		new StringBuffer()
		.append("INSERT INTO difficultylevel (difficultyName,status) ")
		.append("VALUES (:difficultyName, :status)")
		.toString(),
		new MapSqlParameterSource()
		.addValue("difficultyName",difficultylevel.getDifficultyName())
		.addValue("status",difficultylevel.getStatus()));
	}

	@Override
	public void updateDifficultyLevel(DifficultyLevel difficultylevel) {
		this.jdbcTemplate.update(
		new StringBuffer()
		.append("UPDATE difficultylevel dl SET dl.difficultyName = :difficultyName, dl.status = :status ")
		.append("WHERE dl.difficultyId = :difficultyId")
		.toString(),
		new MapSqlParameterSource()
		.addValue("difficultyId",difficultylevel.getDifficultyId())
		.addValue("difficultyName",difficultylevel.getDifficultyName())
		.addValue("status",difficultylevel.getStatus()));
	}

	@Override
	public int countDifficultyName(DifficultyLevel difficultylevel) {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
		.append("SELECT COUNT(*) from difficultylevel dl ")
		.append("WHERE dl.difficultyName = :difficultyName ").toString(),
		new MapSqlParameterSource()
		.addValue("difficultyName",difficultylevel.getDifficultyName()),
		Integer.class);
	}
}
