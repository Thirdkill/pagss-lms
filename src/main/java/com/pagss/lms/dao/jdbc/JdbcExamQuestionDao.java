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
import com.pagss.lms.dao.interfaces.ExamQuestionDao;
import com.pagss.lms.domains.ExamQuestion;

@Repository
public class JdbcExamQuestionDao extends JdbcDao implements ExamQuestionDao {

	@Override
	public void createExamQuestions(List<ExamQuestion> examQuestions) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(examQuestions.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("INSERT INTO examquestion(")
				.append("examId, sectionName, sectionOrder,")
				.append("instruction, isRandomized, isShuffle, ")
				.append("randomizedTopicId, randomizedDifficultyId, ")
				.append("noOfQuestions,weight,questionId,randomizedQuestionTypeId) VALUES(")
				.append(":examId,:sectionName,:sectionOrder, ")
				.append(":instruction,:isRandomized,:isShuffle, ")
				.append(":randomizedTopicId,:randomizedDifficultyId, ")
				.append(":noOfQuestions,:weight,:questionId,:randomizedQuestionTypeId)")
				.toString(),
			batch);
	}
	
	private static class FetchExamQuestionsMapper implements RowMapper<ExamQuestion> {
		@Override
		public ExamQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExamQuestion examQuestion = new ExamQuestion();
			examQuestion.setExamQuestionId(rs.getInt("examQuestionId"));
			examQuestion.setExamId(rs.getInt("examId"));
			examQuestion.setQuestionId(rs.getInt("questionId"));
			examQuestion.setSectionName(rs.getString("sectionName"));
			examQuestion.setSectionOrder(rs.getInt("sectionOrder"));
			examQuestion.setInstruction(rs.getString("instruction"));
			examQuestion.setIsRandomized(rs.getInt("IsRandomized"));
			examQuestion.setIsShuffle(rs.getInt("isShuffle"));
			examQuestion.setRandomizedQuestionTypeId(rs.getInt("randomizedQuestionTypeId"));
			examQuestion.setRandomizedTopicId(rs.getInt("randomizedTopicId"));
			examQuestion.setRandomizedDifficultyId(rs.getInt("randomizedDifficultyId"));
			examQuestion.setNoOfQuestions(rs.getInt("noOfQuestions"));
			examQuestion.setCreatedAt(rs.getString("createdAt"));
			examQuestion.setLastModifiedAt(rs.getString("lastModifiedAt"));
			examQuestion.setQuestionTypeDesc(rs.getString("questionTypeDesc"));
			examQuestion.setLabel(rs.getString("label"));
			examQuestion.setWeight(rs.getInt("weight"));
			examQuestion.setRandomizeQuestionTypeDesc(rs.getString("randomizeQuestionDesc"));
			examQuestion.setRandomizedTopicDesc(rs.getString("topicDesc"));
			examQuestion.setRandomizedDifficultyDesc(rs.getString("difficultyName"));
			return examQuestion;
		}
	}
	
	@Override
	public List<ExamQuestion> fetchExamQuestions(int examId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT eq.examQuestionId, eq.examId, eq.questionId, ")
				.append("eq.sectionName, eq.sectionOrder, eq.instruction, ")
				.append("eq.isRandomized, eq.isShuffle, eq.randomizedQuestionTypeId, ")
				.append("eq.randomizedTopicId, eq.randomizedDifficultyId,eq.noOfQuestions, ")
				.append("eq.createdAt, eq.lastModifiedAt, ")
				.append("qt.questionTypeDesc AS randomizeQuestionDesc,q.label,eq.weight, ")
				.append("(SELECT qt2.questionTypeDesc FROM question q2 ")
				.append("LEFT JOIN questiontype qt2 ON qt2.questionTypeId = q2.questionTypeId ")
				.append("WHERE q2.questionId = q.questionId) AS questionTypeDesc, ")
				.append("tc.topicDesc, dl.difficultyName ")
				.append("FROM examquestion eq ")
				.append("LEFT JOIN questiontype qt ON qt.questionTypeId=eq.randomizedQuestionTypeId ")
				.append("LEFT JOIN question q ON q.questionId=eq.questionId ")
				.append("LEFT JOIN trainingtopic tc ON tc.topicId=eq.randomizedTopicId ")
				.append("LEFT JOIN difficultylevel dl ON dl.difficultyId=eq.randomizedDifficultyId ")
				.append("WHERE eq.examId = :examId ")
				.append("ORDER BY eq.sectionOrder ASC;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("examId", examId),
			new FetchExamQuestionsMapper());
	}

	@Override
	public void deleteExamQuestions(int examId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE FROM examquestion ")
				.append("WHERE examId = :examId")
				.toString(),
			new MapSqlParameterSource()
				.addValue("examId", examId));
	}
}
