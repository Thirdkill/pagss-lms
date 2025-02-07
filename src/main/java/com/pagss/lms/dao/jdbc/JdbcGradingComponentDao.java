package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.GradingComponentDao;
import com.pagss.lms.domains.GradingComponent;

@Repository
public class JdbcGradingComponentDao extends JdbcDao implements GradingComponentDao {
	
	@Override
	public void createGradingComponent(GradingComponent gradingComponent) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("INSERT INTO gradingcomponent( ")
				.append("courseId, componentDesc, percentage) ")
				.append("VALUES(:courseId,:componentDesc,:percentage);")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", gradingComponent.getCourseId())
				.addValue("componentDesc", gradingComponent.getComponentDesc())
				.addValue("percentage", gradingComponent.getPercentage()));
	}

	private static class FetchGradingComponentPagesMapper implements RowMapper<GradingComponent> {
		@Override
		public GradingComponent mapRow(ResultSet rs, int rowNum) throws SQLException {
			GradingComponent gradingComponent = new GradingComponent();
			gradingComponent.setGradingComponentId(rs.getInt("gradingComponentId"));
			gradingComponent.setCourseId(rs.getInt("courseId"));
			gradingComponent.setComponentDesc(rs.getString("componentDesc"));
			gradingComponent.setPercentage(rs.getBigDecimal("percentage"));
			gradingComponent.setCreatedAt(rs.getString("createdAt"));
			gradingComponent.setLastModifiedAt(rs.getString("lastModifiedAt"));
			gradingComponent.setTotalMarks(rs.getInt("totalMarks"));
			gradingComponent.setPassingGrade(rs.getInt("passingGrade"));
			return gradingComponent;
		}
	}
	
	@Override
	public List<GradingComponent> fetchGradingComponentPages(int pageSize, int pageNumber,int courseId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT gc.gradingComponentId, gc.courseId, ")
				.append("gc.componentDesc, gc.percentage, ")
				.append("gc.createdAt, gc.lastModifiedAt, ci.passingGrade, ")
				.append("(SELECT SUM(gc2.percentage) FROM gradingcomponent gc2 ")
				.append("WHERE courseId = :courseId) AS totalMarks ")
				.append("FROM gradingcomponent gc ")
				.append("LEFT JOIN courseinfo ci ON ci.courseId = gc.courseId ")
				.append("WHERE gc.courseId = :courseId ")
				.append("ORDER BY gc.createdAt DESC ")
				.append("LIMIT :pageNumber,:pageSize;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("pageSize", pageSize)
				.addValue("pageNumber", pageNumber)
				.addValue("courseId", courseId),
			new FetchGradingComponentPagesMapper());
	}

	@Override
	public int countTotalGradingComponents(int courseId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(gc.gradingComponentId) ")
				.append("FROM gradingcomponent gc ")
				.append("WHERE gc.courseId = :courseId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", courseId),
			Integer.class);
	}

	@Override
	public void updateGradingComponent(GradingComponent gradingComponent) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("UPDATE gradingcomponent SET ")
				.append("componentDesc = :componentDesc, ")
				.append("percentage = :percentage ")
				.append("WHERE gradingComponentId=:gradingComponentId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("gradingComponentId", gradingComponent.getGradingComponentId())
				.addValue("componentDesc", gradingComponent.getComponentDesc())
				.addValue("percentage", gradingComponent.getPercentage()));
	}

	@Override
	public int checkIfExistComponentExist(GradingComponent gradingComponent) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT EXISTS(")
				.append("SELECT * FROM gradingcomponent ")
				.append("WHERE componentDesc = :componentDesc AND ")
				.append("courseId = :courseId ")
				.append("LIMIT 1);")
				.toString(),
			new MapSqlParameterSource()
				.addValue("componentDesc", gradingComponent.getComponentDesc())
				.addValue("courseId", gradingComponent.getCourseId()),
			Integer.class);
	}

	@Override
	public void deleteGradingComponent(int gradingComponentId) {
		this.jdbcTemplate.update(
			new StringBuffer()
				.append("DELETE FROM gradingcomponent ")
				.append("WHERE gradingComponentId = :gradingComponentId;")
				.toString(),
			new MapSqlParameterSource()
				.addValue("gradingComponentId", gradingComponentId));
	}

	private static class FetchGradingComponentMapper implements RowMapper<GradingComponent> {
		@Override
		public GradingComponent mapRow(ResultSet rs, int rowNum) throws SQLException {
			GradingComponent gradingComponent = new GradingComponent();
			gradingComponent.setGradingComponentId(rs.getInt("gradingComponentId"));
			gradingComponent.setCourseId(rs.getInt("courseId"));
			gradingComponent.setComponentDesc(rs.getString("componentDesc"));
			gradingComponent.setPercentage(rs.getBigDecimal("percentage"));
			gradingComponent.setCreatedAt(rs.getString("createdAt"));
			gradingComponent.setLastModifiedAt(rs.getString("lastModifiedAt"));
			return gradingComponent;
		}
	}
	
	@Override
	public GradingComponent fetchGradingComponent(int gradingComponentId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT gc.gradingComponentId, gc.courseId, ")
				.append("gc.componentDesc, gc.percentage, ")
				.append("gc.createdAt, gc.lastModifiedAt ")
				.append("FROM gradingcomponent gc ")
				.append("WHERE gc.gradingComponentId = :gradingComponentId ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("gradingComponentId", gradingComponentId),
			new FetchGradingComponentMapper());
	}

	private static class FetchGradingComponentsMapper implements RowMapper<GradingComponent> {
		@Override
		public GradingComponent mapRow(ResultSet rs, int rowNum) throws SQLException {
			GradingComponent gradingComponent = new GradingComponent();
			gradingComponent.setGradingComponentId(rs.getInt("gradingComponentId"));
			gradingComponent.setCourseId(rs.getInt("courseId"));
			gradingComponent.setComponentDesc(rs.getString("componentDesc"));
			gradingComponent.setPercentage(rs.getBigDecimal("percentage"));
			gradingComponent.setCreatedAt(rs.getString("createdAt"));
			gradingComponent.setLastModifiedAt(rs.getString("lastModifiedAt"));
			gradingComponent.setTotalMarks(rs.getInt("totalMarks"));
			gradingComponent.setPassingGrade(rs.getInt("passingGrade"));
			return gradingComponent;
		}
	}
	
	@Override
	public List<GradingComponent> fetchGradingComponents(int courseId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT gc.gradingComponentId, gc.courseId, ")
				.append("gc.componentDesc, gc.percentage, ")
				.append("gc.createdAt, gc.lastModifiedAt, ci.passingGrade, ")
				.append("(SELECT SUM(gc2.percentage) FROM gradingcomponent gc2 ")
				.append("WHERE courseId = :courseId) AS totalMarks ")
				.append("FROM gradingcomponent gc ")
				.append("LEFT JOIN courseinfo ci ON ci.courseId = gc.courseId ")
				.append("WHERE gc.courseId = :courseId ")
				.append("ORDER BY gc.createdAt DESC ")
			.toString(),
			new MapSqlParameterSource()
				.addValue("courseId", courseId),
			new FetchGradingComponentsMapper());
	}
}
