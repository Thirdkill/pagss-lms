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
import com.pagss.lms.dao.interfaces.OrderingAnswerDao;
import com.pagss.lms.domains.OrderingAnswer;

@Repository
public class JdbcOrderingAnswerDao extends JdbcDao implements OrderingAnswerDao {

	@Override
	public void createOrderingAnswer(List<OrderingAnswer> orderingAnswers) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(orderingAnswers.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
				.append("INSERT INTO orderinganswer(")
				.append("questionId,orderNo,answer) VALUES(")
				.append(":questionId,:orderNo,:answer);")
				.toString(),
			batch);
	}

	private class FetchOrderingAnswersMapper implements RowMapper<OrderingAnswer> {
		@Override
		public OrderingAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
			OrderingAnswer orderingAnswer = new OrderingAnswer();
			orderingAnswer.setOrderingId(rs.getInt("orderingId"));
			orderingAnswer.setQuestionId(rs.getInt("questionId"));
			orderingAnswer.setOrderNo(rs.getInt("orderNo"));
			orderingAnswer.setAnswer(rs.getString("answer"));
			orderingAnswer.setCreatedAt(rs.getString("createdAt"));
			orderingAnswer.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return orderingAnswer;
		}
	}
	
	@Override
	public List<OrderingAnswer> fetchOrderingAnswers(int questionId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT oa.orderingId, oa.questionId,oa.orderNo, ")
				.append("oa.answer, oa.createdAt, oa.lastModifiedAt ")
				.append("FROM orderinganswer oa ")
				.append("WHERE oa.questionId=:questionId ")
				.append("ORDER BY oa.orderNo ASC;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("questionId", questionId),
			new FetchOrderingAnswersMapper());
	}

	@Override
	public void deleteOrderingAnswers(int questionId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE FROM orderinganswer ")
				.append("WHERE questionId = :questionId")
				.toString(),
			new MapSqlParameterSource()
				.addValue("questionId", questionId));
	}

}
