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
import com.pagss.lms.dao.interfaces.ChoiceDao;
import com.pagss.lms.domains.Choice;

@Repository
public class JdbcChoiceDao extends JdbcDao implements ChoiceDao {

	@Override
	public void createChoices(List<Choice> choices) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(choices.toArray());
		this.jdbcTemplate.batchUpdate(
				new StringBuffer()
					.append("INSERT INTO choice(")
					.append("questionId, choiceDesc, choiceType)")
					.append("VALUES(:questionId, :choiceDesc, :choiceType)")
					.toString(), 
				batch);
	}
	
	private static class FetchChoicesMapper implements RowMapper<Choice> {
		@Override
		public Choice mapRow(ResultSet rs, int rowNum) throws SQLException {
			Choice choice = new Choice();
			choice.setChoiceId(rs.getInt("choiceId"));
			choice.setQuestionId(rs.getInt("questionId"));
			choice.setChoiceType(rs.getInt("choiceType"));
			choice.setChoiceDesc(rs.getString("choiceDesc"));
			choice.setCreatedAt(rs.getString("createdAt"));
			choice.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return choice;
		}
	}
	
	@Override
	public List<Choice> fetchChoices(int questionId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT c.choiceId, c.questionId, ")
				.append("c.choiceDesc, c.choiceType, ")
				.append("c.createdAt, c.lastModifiedAt ")
				.append("FROM choice c ")
				.append("WHERE c.questionId = :questionId")
				.toString(),
			new MapSqlParameterSource()
				.addValue("questionId", questionId),
			new FetchChoicesMapper());
	}
	
	@Override
	public void deleteChoices(int questionId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE FROM choice ")
				.append("WHERE questionId = :questionId ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("questionId", questionId));
	}
}
