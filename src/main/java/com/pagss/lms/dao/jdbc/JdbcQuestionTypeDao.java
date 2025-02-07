package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.QuestionTypeDao;
import com.pagss.lms.domains.QuestionType;

@Repository
public class JdbcQuestionTypeDao extends JdbcDao implements QuestionTypeDao {

	private static class FetchQuestionTypesMapper implements RowMapper<QuestionType> {
		@Override
		public QuestionType mapRow(ResultSet rs, int rowNum) throws SQLException {
			QuestionType questionType = new QuestionType();
			questionType.setQuestionTypeId(rs.getInt("questionTypeId"));
			questionType.setQuestionTypeDesc(rs.getString("questionTypeDesc"));
			questionType.setCreatedAt(rs.getString("createdAt"));
			questionType.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return questionType;
		}
	}
	
	@Override
	public List<QuestionType> fetchQuestionTypes() {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT qt.questionTypeId, qt.questionTypeDesc, ")
				.append("qt.createdAt, qt.lastModifiedAt ")
				.append("FROM questiontype qt ")
				.append("ORDER BY qt.questionTypeDesc ASC ")
				.toString(),
			new MapSqlParameterSource(),
			new FetchQuestionTypesMapper());
	}

}
