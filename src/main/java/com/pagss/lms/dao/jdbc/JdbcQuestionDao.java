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
import com.pagss.lms.dao.interfaces.QuestionDao;
import com.pagss.lms.domains.Question;

@Repository
public class JdbcQuestionDao extends JdbcDao implements QuestionDao {

	@Override
	public int createQuestion(Question question) {
		KeyHolder questionIdHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO question(")
				.append("questionTypeId, topicId, difficultyId, ")
				.append("label,content,status,answer,matchCase, ")
				.append("ignoreOrder) VALUES(")
				.append(":questionTypeId, :topicId,:difficultyId,")
				.append(":label,:content,:status,:answer,:matchCase, ")
				.append(":ignoreOrder)")
				.toString(),
			new MapSqlParameterSource()
				.addValue("questionTypeId", question.getQuestionTypeId())
				.addValue("topicId", question.getTopicId())
				.addValue("difficultyId", question.getDifficultyId())
				.addValue("label", question.getLabel())
				.addValue("content", question.getContent())
				.addValue("status", question.getStatus())
				.addValue("answer", question.getAnswer())
				.addValue("matchCase", question.getMatchCase())
				.addValue("ignoreOrder", question.getIgnoreOrder()),
			questionIdHolder);
		 return questionIdHolder.getKey().intValue();
	}

	@Override
	public void updateMediaUrl(Question question) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE question q SET ")
				.append("q.mediaURL = :mediaURL, q.mediaFileName = :mediaFileName ")
				.append("WHERE q.questionId = :questionId")
				.toString(),
			new MapSqlParameterSource()
				.addValue("mediaURL", question.getMediaUrl())
				.addValue("mediaFileName", question.getMediaFileName())
				.addValue("questionId", question.getQuestionId()));
	}
	
	private static class FetchQuestionsMapper implements RowMapper<Question> {
		@Override
		public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
			Question question = new Question();
			question.setQuestionId(rs.getInt("questionId"));
			question.setQuestionTypeId(rs.getInt("questionTypeId"));
			question.setTopicId(rs.getInt("topicId"));
			question.setDifficultyId(rs.getInt("difficultyId"));
			question.setLabel(rs.getString("label"));
			question.setContent(rs.getString("content"));
			question.setMediaUrl(rs.getString("mediaURL"));
			question.setStatus(rs.getInt("status"));
			question.setCreatedAt(rs.getString("createdAt"));
			question.setLastModifiedAt(rs.getString("lastModifiedAt"));
			question.setTopicDesc(rs.getString("topicDesc"));
			question.setDifficultyName(rs.getString("difficultyName"));
			question.setQuestionTypeDesc(rs.getString("questionTypeDesc"));
			return question;
		}
	}
	
	@Override
	public List<Question> fetchQuestions(TableCommand tableCommand) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT q.questionId, q.questionTypeId, q.topicId, ")
				.append("q.difficultyId, q.label, q.content, q.mediaURL, q.status, ")
				.append("q.createdAt, q.lastModifiedAt, tt.topicDesc, ")
				.append("dl.difficultyName, qt.questionTypeDesc ")
				.append("FROM question q ")
				.append("LEFT JOIN trainingtopic tt ON tt.topicId = q.topicId ")
				.append("LEFT JOIN difficultylevel dl ON dl.difficultyId = q.difficultyId ")
				.append("LEFT JOIN questiontype qt ON qt.questionTypeId = q.questionTypeId ")
				.append(tableCommand.getWhereClause())
				.append(tableCommand.getOrderClause())
				.append("LIMIT :pageNo,:pageSize")
				.toString(),
			new MapSqlParameterSource()
				.addValue("pageNo", tableCommand.getPageNumber())
				.addValue("pageSize", tableCommand.getPageSize())
				.addValue("questionTypeId", tableCommand.getSearchByQuestionType())
				.addValue("topicId", tableCommand.getSearchByTopic())
				.addValue("difficultyId", tableCommand.getSearchByDifficultyLevel())
				.addValue("status", tableCommand.getSearchByStatus()),
			new FetchQuestionsMapper());
	}
	
	@Override
	public int countFetchQuestions(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
					.append("SELECT COUNT(*) ")
					.append("FROM question q ")
					.append("LEFT JOIN trainingtopic tt ON tt.topicId = q.topicId ")
					.append("LEFT JOIN difficultylevel dl ON dl.difficultyId = q.difficultyId ")
					.append("LEFT JOIN questiontype qt ON qt.questionTypeId = q.questionTypeId ")
					.append(tableCommand.getWhereClause())
					.toString(),
				new MapSqlParameterSource()
					.addValue("pageNo", tableCommand.getPageNumber())
					.addValue("pageSize", tableCommand.getPageSize())
					.addValue("questionTypeId", tableCommand.getSearchByQuestionType())
					.addValue("topicId", tableCommand.getSearchByTopic())
					.addValue("difficultyId", tableCommand.getSearchByDifficultyLevel())
					.addValue("status", tableCommand.getSearchByStatus()),
				Integer.class);
	}
	
	private static class FetchQuestionMapper implements RowMapper<Question> {
		@Override
		public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
			Question question = new Question();
			question.setQuestionId(rs.getInt("questionId"));
			question.setQuestionTypeId(rs.getInt("questionTypeId"));
			question.setTopicId(rs.getInt("topicId"));
			question.setDifficultyId(rs.getInt("difficultyId"));
			question.setLabel(rs.getString("label"));
			question.setContent(rs.getString("content"));
			question.setAnswer(rs.getString("answer"));
			question.setMatchCase(rs.getInt("matchCase"));
			question.setIgnoreOrder(rs.getInt("ignoreOrder"));
			question.setMediaUrl(rs.getString("mediaURL"));
			question.setMediaFileName(rs.getString("mediaFileName"));
			question.setStatus(rs.getInt("status"));
			question.setCreatedAt(rs.getString("createdAt"));
			question.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return question;
		}
	}
	
	@Override
	public Question fetchQuestion(int questionId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT q.questionId, q.questionTypeId, ")
				.append("q.topicId, q.difficultyId, q.label, ")
				.append("q.answer, q.matchCase, q.ignoreOrder, ")
				.append("q.content, q.mediaURL, q.mediaFileName,q.status, ")
				.append("q.createdAt, q.lastModifiedAt ")
				.append("FROM question q ")
				.append("WHERE q.questionId = :questionId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("questionId", questionId),
			new FetchQuestionMapper());
	}

	@Override
	public void updateQuestion(Question question) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE question q SET ")
				.append("q.questionTypeId = :questionTypeId, q.topicId = :topicId, ")
				.append("q.difficultyId = :difficultyId, q.label = :label," )
				.append("q.content = :content,q.status = :status, ")
				.append("q.answer = :answer, q.matchCase = :matchCase, ")
				.append("ignoreOrder=:ignoreOrder ")
				.append("WHERE q.questionId = :questionId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("questionTypeId", question.getQuestionTypeId())
				.addValue("questionId", question.getQuestionId())
				.addValue("topicId", question.getTopicId())
				.addValue("difficultyId", question.getDifficultyId())
				.addValue("label", question.getLabel())
				.addValue("content", question.getContent())
				.addValue("answer", question.getAnswer())
				.addValue("matchCase", question.getMatchCase())
				.addValue("ignoreOrder", question.getIgnoreOrder())
				.addValue("status", question.getStatus()));
	}
	
	private static class FetchRandomQuestionsMapper implements RowMapper<Question> {
		@Override
		public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
			Question question = new Question();
			question.setQuestionId(rs.getInt("questionId"));
			question.setQuestionTypeId(rs.getInt("questionTypeId"));
			question.setTopicId(rs.getInt("topicId"));
			question.setDifficultyId(rs.getInt("difficultyId"));
			question.setLabel(rs.getString("label"));
			question.setContent(rs.getString("content"));
			question.setAnswer(rs.getString("answer"));
			question.setMatchCase(rs.getInt("matchCase"));
			question.setMediaUrl(rs.getString("mediaURL"));
			question.setMediaFileName(rs.getString("mediaFileName"));
			question.setStatus(rs.getInt("status"));
			question.setCreatedAt(rs.getString("createdAt"));
			question.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return question;
		}
	}
	
	@Override
	public List<Question> fetchRandomQuestions(Question question) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT q.questionId, q.questionTypeId, ")
				.append("q.topicId, q.difficultyId, q.label,")
				.append("q.answer, q.matchCase, ")
				.append("q.content, q.mediaURL, q.mediaFileName,q.status,")
				.append("q.createdAt, q.lastModifiedAt ")
				.append("FROM question q ")
				.append("WHERE q.topicId = :topicId AND q.difficultyId = :difficultyId ")
				.append("AND q.questionTypeId = :questionTypeId ")
				.append("ORDER BY RAND() ")
				.append("LIMIT :noOfQuestions;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("topicId", question.getTopicId())
				.addValue("difficultyId", question.getDifficultyId())
				.addValue("noOfQuestions", question.getNoOfQuestions())
				.addValue("questionTypeId", question.getQuestionTypeId()),
			new FetchRandomQuestionsMapper());
	}
	
	@Override
	public int countTotalQuestions(Question question) {
		return this.jdbcTemplate.queryForObject(
				new StringBuffer()
					.append("SELECT COUNT(q.questionId) ")
					.append("FROM question q ")
					.append("WHERE q.topicId = :topicId AND q.difficultyId = :difficultyId ")
					.append("AND q.questionTypeId = :questionTypeId ")
					.append("ORDER BY RAND() ")
					.append("LIMIT :noOfQuestions;")
					.toString(),
				new MapSqlParameterSource()
					.addValue("topicId", question.getTopicId())
					.addValue("difficultyId", question.getDifficultyId())
					.addValue("noOfQuestions", question.getNoOfQuestions())
					.addValue("questionTypeId", question.getQuestionTypeId()),
				Integer.class);
	}
}
