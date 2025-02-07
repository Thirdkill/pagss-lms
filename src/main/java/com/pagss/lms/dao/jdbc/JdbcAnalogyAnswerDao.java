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
import com.pagss.lms.dao.interfaces.AnalogyAnswerDao;
import com.pagss.lms.domains.AnalogyAnswer;

@Repository
public class JdbcAnalogyAnswerDao extends JdbcDao implements AnalogyAnswerDao {

	@Override
	public void createAnalogyAnswers(List<AnalogyAnswer> analogyAnswers) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(analogyAnswers.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("INSERT INTO analogyanswer(")
				.append("questionId, givenA, givenB) VALUES(")
				.append(":questionId, :givenA, :givenB);")
				.toString(),
			batch);
	}

	private static class FetchAnalogyAnswersMapper implements RowMapper<AnalogyAnswer> {
		@Override
		public AnalogyAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
			AnalogyAnswer analogyAnswer = new AnalogyAnswer();
			analogyAnswer.setAnalogyId(rs.getInt("analogyId"));
			analogyAnswer.setQuestionId(rs.getInt("questionId"));
			analogyAnswer.setGivenA(rs.getString("givenA"));
			analogyAnswer.setGivenB(rs.getString("givenB"));
			analogyAnswer.setCreatedAt(rs.getString("createdAt"));
			analogyAnswer.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return analogyAnswer;
		}
	}
	
	@Override
	public List<AnalogyAnswer> fetchAnalogyAnswers(int questionId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT aa.analogyId, aa.questionId, ")
				.append("aa.givenA,aa.givenB, aa.createdAt,aa.lastModifiedAt ")
				.append("FROM analogyanswer aa ")
				.append("WHERE aa.questionId = :questionId")
				.toString(),
			new MapSqlParameterSource()
				.addValue("questionId", questionId),
			new FetchAnalogyAnswersMapper());
	}

	@Override
	public void deleteAnalogyAnswers(int questionId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE FROM analogyanswer ")
				.append("WHERE questionId = :questionId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("questionId", questionId));
	}
}
