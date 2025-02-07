package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.TrainingTopicDao;
import com.pagss.lms.domains.TrainingTopic;

@Repository
public class JdbcTrainingTopicDao extends JdbcDao implements TrainingTopicDao {

	private static class FetchTrainingTopicsMapper implements RowMapper<TrainingTopic> {
		@Override
		public TrainingTopic mapRow(ResultSet rs, int rowNum) throws SQLException {
			TrainingTopic trainingTopic = new TrainingTopic();
			trainingTopic.setTopicId(rs.getInt("topicId"));
			trainingTopic.setTopicDesc(rs.getString("topicDesc"));
			trainingTopic.setStatus(rs.getInt("status"));
			trainingTopic.setCreatedAt(rs.getString("createdAt"));
			trainingTopic.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return trainingTopic;
		}
	}
	
	@Override
	public List<TrainingTopic> fetchTrainingTopics() {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT tt.topicId, tt.topicDesc, tt.status, ")
				.append("tt.createdAt, tt.lastModifiedAt ")
				.append("FROM trainingtopic tt ")
				.append("WHERE tt.status = 1 ")
				.append("ORDER BY tt.topicDesc ASC")
				.toString(),
			new FetchTrainingTopicsMapper());
	}
	
	private static class FetchTrainingTopicsTableMapper implements RowMapper<TrainingTopic> {
		@Override
		public TrainingTopic mapRow(ResultSet rs, int rowNum) throws SQLException {
			TrainingTopic trainingTopic = new TrainingTopic();
			trainingTopic.setTopicId(rs.getInt("topicId"));
			trainingTopic.setTopicDesc(rs.getString("topicDesc"));
			trainingTopic.setStatus(rs.getInt("status"));
			return trainingTopic;
		}
	}

	@Override
	public List<TrainingTopic> fetchTopicsTable(int pageSize, int pageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT tt.topicId, tt.topicDesc, tt.status ")
				.append("FROM trainingtopic tt ")
				.append("ORDER BY tt.topicDesc ASC ")
				.append("LIMIT :pageNo,:pageSize")
				.toString(),
				new MapSqlParameterSource()
				.addValue("pageSize", pageSize)
				.addValue("pageNo", pageNo),
			new FetchTrainingTopicsTableMapper());
	}

	@Override
	public int countTotalTrainingTopics() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) FROM trainingtopic tt ")
				.toString(),
			new MapSqlParameterSource(),
			Integer.class);
	}
	
	@Override
	public List<TrainingTopic> fetchSearchedTopicsTable(String keyword, int pageSize, int pageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT tt.topicId, tt.topicDesc, tt.status ")
				.append("FROM trainingtopic tt ")
				.append("WHERE tt.topicDesc like :topicDesc ")
				.append("ORDER BY tt.topicDesc ASC ")
				.append("LIMIT :pageNo,:pageSize")
				.toString(),
				new MapSqlParameterSource()
				.addValue("topicDesc", "%" + keyword + "%")
				.addValue("pageSize", pageSize)
				.addValue("pageNo", pageNo),
			new FetchTrainingTopicsTableMapper());
	}

	@Override
	public int countTotalSearchedTrainingTopics( String keyword) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) FROM trainingtopic tt ")
				.append("WHERE tt.topicDesc like :topicDesc ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("topicDesc", "%" + keyword + "%"),
			Integer.class);
	}

	@Override
	public void addTrainingTopic(TrainingTopic trainingtopic) {
		this.jdbcTemplate.update(
		new StringBuffer()
		.append("INSERT INTO trainingtopic (topicDesc,status) ")
		.append("VALUES (:topicDesc, :status)")
		.toString(),
		new MapSqlParameterSource()
		.addValue("topicDesc",trainingtopic.getTopicDesc())
		.addValue("status",trainingtopic.getStatus()));
	}

	@Override
	public void updateTrainingTopic(TrainingTopic trainingtopic) {
		this.jdbcTemplate.update(
		new StringBuffer()
		.append("UPDATE trainingtopic tt SET tt.topicDesc = :topicDesc, tt.status = :status ")
		.append("WHERE tt.topicId = :topicId")
		.toString(),
		new MapSqlParameterSource()
		.addValue("topicId",trainingtopic.getTopicId())
		.addValue("topicDesc",trainingtopic.getTopicDesc())
		.addValue("status",trainingtopic.getStatus()));
	}

	@Override
	public int countTopicDesc(TrainingTopic trainingtopic) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
		.append("SELECT COUNT(*) from trainingtopic tt ")
		.append("WHERE tt.topicDesc = :topicDesc ").toString(),
		new MapSqlParameterSource()
		.addValue("topicDesc",trainingtopic.getTopicDesc()),
		Integer.class);
	}

	

}
