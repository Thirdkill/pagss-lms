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
import com.pagss.lms.dao.interfaces.FillInTheBlanksAnswerDao;
import com.pagss.lms.domains.FillInTheBlanksAnswer;

@Repository
public class JdbcFillInTheBlanksAnswerDao extends JdbcDao implements FillInTheBlanksAnswerDao {
	
	@Override
	public void createFillInTheBlanksAnswers(List<FillInTheBlanksAnswer> fillInTheBlanksAnswers) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(fillInTheBlanksAnswers.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("INSERT INTO fillintheblanksanswer(questionId, choiceType, ")
				.append("componentCode, answer, isMatchCase) VALUES( ")
				.append(":questionId, :choiceType, :componentCode, ")
				.append(":answer, :isMatchCase);")
				.toString(),
			batch);
	}

	private static class FetchFillInTheBlanksAnswersMapper implements RowMapper<FillInTheBlanksAnswer> {
		@Override
		public FillInTheBlanksAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
			FillInTheBlanksAnswer fillInTheBlanksAnswer = new FillInTheBlanksAnswer();
			fillInTheBlanksAnswer.setFillInTheBlanksAnswerId(rs.getInt("fillInTheBlanksAnswerId"));
			fillInTheBlanksAnswer.setQuestionId(rs.getInt("questionId"));
			fillInTheBlanksAnswer.setChoiceType(rs.getInt("choiceType"));
			fillInTheBlanksAnswer.setComponentCode(rs.getString("componentCode"));
			fillInTheBlanksAnswer.setAnswer(rs.getString("answer"));
			fillInTheBlanksAnswer.setIsMatchCase(rs.getInt("isMatchCase"));
			fillInTheBlanksAnswer.setCreatedAt(rs.getString("createdAt"));
			fillInTheBlanksAnswer.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return fillInTheBlanksAnswer;
		}
	}
	
	@Override
	public List<FillInTheBlanksAnswer> fetchFillInTheBlanksAnswers(int questionId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT fitb.fillInTheBlanksAnswerId, fitb.questionId, ")
				.append("fitb.choiceType, fitb.componentCode, fitb.answer, ")
				.append("fitb.isMatchCase, fitb.createdAt, fitb.lastModifiedAt ")
				.append("FROM fillintheblanksanswer fitb ")
				.append("WHERE fitb.questionId=:questionId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("questionId", questionId),
			new FetchFillInTheBlanksAnswersMapper());
	}

	@Override
	public void deleteFillInTheBlanksAnswer(int questionId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE FROM fillintheblanksanswer ")
				.append("WHERE questionId=:questionId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("questionId", questionId));
	}
}
