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
import com.pagss.lms.dao.interfaces.EnumerationAnswerDao;
import com.pagss.lms.domains.EnumerationAnswer;

@Repository
public class JdbcEnumerationAnswerDao extends JdbcDao implements EnumerationAnswerDao {

	@Override
	public void createEnumerationAnswers(List<EnumerationAnswer> enumerationAnswers) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(enumerationAnswers.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("INSERT INTO enumerationanswer(")
				.append("questionId,orderNo, answer) ")
				.append("VALUES(:questionId,:orderNo,:answer);")
				.toString(),
			batch);
	}

	@Override
	public void deleteEnumerationAnswers(int questionId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE FROM enumerationanswer ")
				.append("WHERE questionId = :questionId")
				.toString(), 
			new MapSqlParameterSource()
				.addValue("questionId", questionId));
	}

	private static class FetchEnumerationAnswersMapper implements RowMapper<EnumerationAnswer> {
		@Override
		public EnumerationAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
			EnumerationAnswer enumerationAnswer=new EnumerationAnswer();
			enumerationAnswer.setEnumerationId(rs.getInt("enumerationId"));
			enumerationAnswer.setQuestionId(rs.getInt("questionId"));
			enumerationAnswer.setOrderNo(rs.getInt("orderNo"));
			enumerationAnswer.setAnswer(rs.getString("answer"));
			enumerationAnswer.setCreatedAt(rs.getString("createdAt"));
			enumerationAnswer.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return enumerationAnswer;
		}	
	}
	
	@Override
	public List<EnumerationAnswer> fetchEnumerationAnswers(int questionId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ea.enumerationId, ea.questionId, ")
				.append("ea.orderNo, ea.answer, ea.createdAt, ")
				.append("ea.lastModifiedAt ")
				.append("FROM enumerationanswer ea ")
				.append("WHERE ea.questionId = :questionId ")
				.append("ORDER BY ea.createdAt ASC ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("questionId", questionId),
			new FetchEnumerationAnswersMapper());
	}
}
