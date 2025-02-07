package com.pagss.lms.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.core.JdbcDao;
import com.pagss.lms.dao.interfaces.EvaluationDao;
import com.pagss.lms.domains.EmployeeEvaluation;
import com.pagss.lms.domains.Evaluation;
import com.pagss.lms.domains.EvaluationQuestion;

@Repository
public class JdbcEvaluationDao extends JdbcDao implements EvaluationDao {
	
	private static class FetchEvaluationTableMapper implements RowMapper<Evaluation> {
		@Override
		public Evaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Evaluation evaluation = new Evaluation();
			evaluation.setEvaluationId(rs.getInt("evaluationId"));
			evaluation.setTitle(getStringValue(rs.getString("title")));
			evaluation.setStatus(rs.getInt("status"));
			evaluation.setLastModifiedAt(getStringValue(rs.getString("lastModifiedAt")));
			evaluation.setModifiedBy(getStringValue(rs.getString("modifiedBy")));
			return evaluation;
		}
	}

	@Override
	public List<Evaluation> fetchEvaluationTable(int pageSize, int calculatedPageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer().append("SELECT ev.evaluationId, ev.title, ev.status, ")
			.append("ev.lastModifiedAt, ev.modifiedBy ")
			.append("FROM evaluation ev ")
			.append("ORDER BY ev.title ")
			.append("LIMIT :pageNo,:pageSize").toString(),
			new MapSqlParameterSource()
			.addValue("pageSize", pageSize)
			.addValue("pageNo", calculatedPageNo),
			new FetchEvaluationTableMapper());
	}

	@Override
	public int countTotalEvaluations() {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
			.append("SELECT COUNT(*) FROM evaluation ev ").toString(),
			new MapSqlParameterSource(), Integer.class);
	}
	
	private static class FetchSearchedEvaluationTableMapper implements RowMapper<Evaluation> {
		@Override
		public Evaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Evaluation evaluation = new Evaluation();
			evaluation.setEvaluationId(rs.getInt("evaluationId"));
			evaluation.setTitle(getStringValue(rs.getString("title")));
			evaluation.setStatus(rs.getInt("status"));
			evaluation.setLastModifiedAt(getStringValue(rs.getString("lastModifiedAt")));
			evaluation.setModifiedBy(getStringValue(rs.getString("modifiedBy")));
			return evaluation;
		}
	}

	@Override
	public List<Evaluation> fetchSearchedEvaluationTable(Evaluation evaluation, int pageSize, int calculatedPageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer().append("SELECT ev.evaluationId, ev.title, ev.status, ")
			.append("ev.lastModifiedAt, ev.modifiedBy ")
			.append("FROM evaluation ev ")
			.append("WHERE ev.title like :title OR ev.modifiedBy like :modifiedBy ")
			.append("ORDER BY ev.title ")
			.append("LIMIT :pageNo,:pageSize").toString(),
			new MapSqlParameterSource()
			.addValue("title", "%"+evaluation.getTitle()+"%")
			.addValue("modifiedBy", "%"+evaluation.getModifiedBy()+"%")
			.addValue("pageSize", pageSize)
			.addValue("pageNo", calculatedPageNo),
			new FetchSearchedEvaluationTableMapper());
	}

	@Override
	public int countTotalSearchedEvaluations(Evaluation evaluation) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
			.append("SELECT COUNT(*) FROM evaluation ev ")
			.append("WHERE ev.title like :title OR ev.modifiedBy like :modifiedBy ")
			.toString(),
			new MapSqlParameterSource()
			.addValue("title", "%"+evaluation.getTitle()+"%")
			.addValue("modifiedBy", "%"+evaluation.getModifiedBy()+"%"), 
			Integer.class);
	}

	@Override
	public int fetchLatestEvaluationId() {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
		.append("SELECT (IFNULL(MAX(ev.evaluationId),0)+1) as evaluationId ")
		.append("FROM evaluation ev ")
		.toString(),
		new MapSqlParameterSource(), 
		Integer.class);
	}

	@Override
	public int countEvaluationCode(Evaluation eval) {
		return this.jdbcTemplate.queryForObject(new StringBuffer()
		.append("SELECT COUNT(*) FROM evaluation ev ")
		.append("WHERE ev.evaluationCode = :evaluationCode ").toString(),
		new MapSqlParameterSource()
		.addValue("evaluationCode",eval.getEvaluationCode()),
		Integer.class);
	}

	@Override
	public int countEvaluationQuestionsById(int evaluationId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(*) FROM evaluationquestion eq ")
				.append("WHERE eq.evaluationId = :evaluationId ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("evaluationId",evaluationId),
		Integer.class);
	}

	@Override
	public void deleteEvaluationQuestionById(int evaluationId) {
		this.jdbcTemplate.update(
		new StringBuffer()
			.append("DELETE eq FROM evaluationquestion eq ")
			.append("WHERE eq.evaluationId = :evaluationId")
			.toString(),
		new MapSqlParameterSource()
			.addValue("evaluationId",evaluationId));
	}

	@Override
	public void addEvaluationQuestion(List<EvaluationQuestion> evaluationquestion) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(evaluationquestion.toArray());
		this.jdbcTemplate.batchUpdate(
			new StringBuffer()
			.append("INSERT INTO evaluationquestion ")
			.append("(evaluationId, evaluationType, questionDesc, sectionOrder, sectionName, ")
			.append("instruction, scaleMin, scaleMax) ")
			.append("VALUES (:evaluationId, :evaluationType, :questionDesc, :sectionOrder, ")
			.append(":sectionName, :instruction, :scaleMin, :scaleMax) ")
			.toString(), 
			batch);
	}

	@Override
	public void updateEvaluationInfo(Evaluation evaluation) {
		this.jdbcTemplate.update(
			new StringBuffer()
			.append("UPDATE evaluation ev ")
			.append("SET evaluationCode = :evaluationCode, title = :title, status = :status ")
			.append("WHERE ev.evaluationId = :evaluationId")
			.toString(),
			new MapSqlParameterSource()
			.addValue("evaluationCode",evaluation.getEvaluationCode())
			.addValue("title",evaluation.getTitle())
			.addValue("status",evaluation.getStatus())
			.addValue("evaluationId",evaluation.getEvaluationId()));
	}
	
	private static class FetchEvaluationInfoMapper implements RowMapper<Evaluation> {
		@Override
		public Evaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Evaluation evaluation = new Evaluation();
			evaluation.setEvaluationId(rs.getInt("evaluationId"));
			evaluation.setEvaluationCode(getStringValue(rs.getString("evaluationCode")));
			evaluation.setTitle(getStringValue(rs.getString("title")));
			evaluation.setStatus(rs.getInt("status"));
			return evaluation;
		}
	}

	@Override
	public Evaluation fetchEvaluationInfo(int evaluationId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
			.append("SELECT ev.evaluationId, ev.evaluationCode, ev.title, ev.status ")
			.append("FROM evaluation ev ")
			.append("WHERE ev.evaluationId = :evaluationId ").toString(),
			new MapSqlParameterSource()
			.addValue("evaluationId", evaluationId),
			new FetchEvaluationInfoMapper());
	}
	
	private static class FetchEvaluationQuestionsMapper implements RowMapper<EvaluationQuestion> {
		@Override
		public EvaluationQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
			EvaluationQuestion evaluationquestion = new EvaluationQuestion();
			evaluationquestion.setEvaluationId(rs.getInt("evaluationId"));
			evaluationquestion.setEvaluationType(getStringValue(rs.getString("evaluationType")));
			evaluationquestion.setQuestionDesc(getStringValue(rs.getString("questionDesc")));
			evaluationquestion.setEvaluationQuestionId(rs.getInt("evaluationQuestionId"));
			evaluationquestion.setSectionOrder(rs.getInt("sectionOrder"));
			evaluationquestion.setSectionName(getStringValue(rs.getString("SectionName")));
			evaluationquestion.setInstruction(getStringValue(rs.getString("instruction")));
			evaluationquestion.setScaleMin(rs.getInt("scaleMin"));
			evaluationquestion.setScaleMax(rs.getInt("scaleMax"));
			return evaluationquestion;
		}
	}

	@Override
	public List<EvaluationQuestion> fetchEvaluationQuestions(int evaluationId) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT eq.evaluationId, eq.evaluationType, eq.questionDesc, eq.evaluationQuestionId, ")
				.append("eq.sectionOrder, eq.sectionName, eq.instruction, eq.scaleMin, eq.scaleMax ")
				.append("FROM evaluationquestion eq ")
				.append("WHERE eq.evaluationId = :evaluationId ").toString(),
			new MapSqlParameterSource()
				.addValue("evaluationId", evaluationId),
			new FetchEvaluationQuestionsMapper());
	}
	
	private static class FetchEvaluationOnCourseTableMapper implements RowMapper<Evaluation> {
		@Override
		public Evaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Evaluation evaluation = new Evaluation();
			evaluation.setEvaluationId(rs.getInt("evaluationId"));
			evaluation.setTitle(getStringValue(rs.getString("title")));
			evaluation.setStatus(rs.getInt("status"));
			evaluation.setLastModifiedAt(getStringValue(rs.getString("lastModifiedAt")));
			evaluation.setModifiedBy(getStringValue(rs.getString("modifiedBy")));
			return evaluation;
		}
	}

	@Override
	public List<Evaluation> fetchEvaluationOnCourseTable(int courseId,int pageSize, int pageNo) {
		return this.jdbcTemplate.query(
		new StringBuffer().append("SELECT ev.evaluationId, ev.title, ev.status, ")
		.append("ev.lastModifiedAt, ev.modifiedBy ")
		.append("FROM evaluation ev ")
		.append("WHERE ev.status = 1 ")
		.append("AND ev.evaluationId NOT IN ")
		.append("(SELECT evaluationId FROM courseevaluation WHERE courseId = :courseId) ")
		.append("ORDER BY ev.title ")
		.append("LIMIT :pageNo,:pageSize").toString(),
		new MapSqlParameterSource()
		.addValue("courseId", courseId)
		.addValue("pageSize", pageSize)
		.addValue("pageNo", pageNo),
		new FetchEvaluationOnCourseTableMapper());
	}
	
	@Override
	public int countTotalEvaluationsOnCourse(int courseId) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
		.append("SELECT COUNT(*) FROM evaluation ev ")
		.append("WHERE ev.status = 1 ")
		.append("AND ev.evaluationId NOT IN ")
		.append("(SELECT evaluationId FROM courseevaluation WHERE courseId = :courseId) ")
		.toString(),
		new MapSqlParameterSource()
		.addValue("courseId", courseId), Integer.class);
	}
	
	private static class FetchSearchedEvaluationOnCourseTableMapper implements RowMapper<Evaluation> {
		@Override
		public Evaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Evaluation evaluation = new Evaluation();
			evaluation.setEvaluationId(rs.getInt("evaluationId"));
			evaluation.setTitle(getStringValue(rs.getString("title")));
			evaluation.setStatus(rs.getInt("status"));
			evaluation.setLastModifiedAt(getStringValue(rs.getString("lastModifiedAt")));
			evaluation.setModifiedBy(getStringValue(rs.getString("modifiedBy")));
			return evaluation;
		}
	}

	@Override
	public List<Evaluation> fetchSearchedEvaluationOnCourseTable(int courseId, Evaluation evaluation, int pageSize, int pageNo) {
		return this.jdbcTemplate.query(
		new StringBuffer().append("SELECT ev.evaluationId, ev.title, ev.status, ")
		.append("ev.lastModifiedAt, ev.modifiedBy ")
		.append("FROM evaluation ev ")
		.append("WHERE ev.title like :title ")
		.append("AND ev.status = 1 ")
		.append("AND ev.evaluationId NOT IN ")
		.append("(SELECT evaluationId FROM courseevaluation WHERE courseId = :courseId) ")
		.append("ORDER BY ev.title ")
		.append("LIMIT :pageNo,:pageSize").toString(),
		new MapSqlParameterSource()
		.addValue("title", "%"+evaluation.getTitle()+"%")
		.addValue("courseId", courseId)
		.addValue("pageSize", pageSize)
		.addValue("pageNo", pageNo),
		new FetchSearchedEvaluationOnCourseTableMapper());
	}

	@Override
	public int countTotalSearchedEvaluationsOnCourse(int courseId, Evaluation evaluation) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
		.append("SELECT COUNT(*) FROM evaluation ev ")
		.append("WHERE ev.title like :title ")
		.append("AND ev.status = 1 ")
		.append("AND ev.evaluationId NOT IN ")
		.append("(SELECT evaluationId FROM courseevaluation WHERE courseId = :courseId) ")
		.toString(),
		new MapSqlParameterSource()
		.addValue("title", "%"+evaluation.getTitle()+"%")
		.addValue("modifiedBy", "%"+evaluation.getModifiedBy()+"%")
		.addValue("courseId", courseId), 
		Integer.class);
	}
	
	private static class FetchEvaluationOnClassTableMapper implements RowMapper<Evaluation> {
		@Override
		public Evaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Evaluation evaluation = new Evaluation();
			evaluation.setEvaluationId(rs.getInt("evaluationId"));
			evaluation.setTitle(getStringValue(rs.getString("title")));
			evaluation.setStatus(rs.getInt("status"));
			evaluation.setLastModifiedAt(getStringValue(rs.getString("lastModifiedAt")));
			evaluation.setModifiedBy(getStringValue(rs.getString("modifiedBy")));
			return evaluation;
		}
	}
	
	@Override
	public List<Evaluation> fetchEvaluationsOnClass(TableCommand tableCommand, int pageSize, int calculatedPageNo) {
		return this.jdbcTemplate.query(
		new StringBuffer().append("SELECT ev.evaluationId, ev.title, ev.status, ")
			.append("ev.lastModifiedAt, ev.modifiedBy ")
			.append("FROM evaluation ev ")
			.append("WHERE ev.evaluationId NOT IN ")
			.append("(SELECT ev.evaluationId FROM evaluation ev ")
			.append("LEFT JOIN classevaluation cle ON ev.evaluationId = cle.evaluationId ")
			.append("LEFT JOIN courseevaluation coe ON ev.evaluationId = coe.evaluationId ")
			.append("WHERE cle.classId = :classId OR coe.courseId = :courseId) ")
			.append("AND ev.status = 1 ")
			.append("ORDER BY ev.title ")
			.append("LIMIT :pageNo,:pageSize").toString(),
		new MapSqlParameterSource()
			.addValue("courseId", tableCommand.getCourseId())
			.addValue("classId", tableCommand.getClassId())
			.addValue("pageSize", pageSize)
			.addValue("pageNo", calculatedPageNo),
		new FetchEvaluationOnClassTableMapper());
	}
	
	@Override
	public int countTotalEvaluationsOnClass(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT COUNT(*) FROM evaluation ev ")
			.append("WHERE ev.evaluationId NOT IN ")
			.append("(SELECT ev.evaluationId FROM evaluation ev ")
			.append("LEFT JOIN classevaluation cle ON ev.evaluationId = cle.evaluationId ")
			.append("LEFT JOIN courseevaluation coe ON ev.evaluationId = coe.evaluationId ")
			.append("WHERE cle.classId = :classId OR coe.courseId = :courseId) ")
			.append("AND ev.status = 1 ")
			.toString(),
		new MapSqlParameterSource()
			.addValue("courseId", tableCommand.getCourseId())
			.addValue("classId", tableCommand.getClassId()), Integer.class);
	}
	
	private static class FetchSeachedEvaluationOnClassTableMapper implements RowMapper<Evaluation> {
		@Override
		public Evaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Evaluation evaluation = new Evaluation();
			evaluation.setEvaluationId(rs.getInt("evaluationId"));
			evaluation.setTitle(getStringValue(rs.getString("title")));
			evaluation.setStatus(rs.getInt("status"));
			evaluation.setLastModifiedAt(getStringValue(rs.getString("lastModifiedAt")));
			evaluation.setModifiedBy(getStringValue(rs.getString("modifiedBy")));
			return evaluation;
		}
	}

	@Override
	public List<Evaluation> searchEvaluationsOnClass(TableCommand tableCommand, int pageSize, int calculatedPageNo) {
		return this.jdbcTemplate.query(
		new StringBuffer().append("SELECT ev.evaluationId, ev.title, ev.status, ")
			.append("ev.lastModifiedAt, ev.modifiedBy ")
			.append("FROM evaluation ev ")
			.append("WHERE ev.title like :keyword ")
			.append("AND ev.evaluationId NOT IN ")
			.append("(SELECT ev.evaluationId FROM evaluation ev ")
			.append("LEFT JOIN classevaluation cle ON ev.evaluationId = cle.evaluationId ")
			.append("LEFT JOIN courseevaluation coe ON ev.evaluationId = coe.evaluationId ")
			.append("WHERE cle.classId = :classId OR coe.courseId = :courseId) ")
			.append("AND ev.status = 1 ")
			.append("ORDER BY ev.title ")
			.append("LIMIT :pageNo,:pageSize").toString(),
		new MapSqlParameterSource()
			.addValue("courseId", tableCommand.getCourseId())
			.addValue("keyword", "%"+tableCommand.getKeyword()+"%")
			.addValue("classId", tableCommand.getClassId())
			.addValue("pageSize", pageSize)
			.addValue("pageNo", calculatedPageNo),
		new FetchSeachedEvaluationOnClassTableMapper());
	}

	@Override
	public int countTotalSearchEvaluationsOnClass(TableCommand tableCommand) {
		return this.jdbcTemplate.queryForObject(
		new StringBuffer()
			.append("SELECT COUNT(*) FROM evaluation ev ")
			.append("WHERE ev.title like :keyword ")
			.append("AND ev.evaluationId NOT IN ")
			.append("(SELECT ev.evaluationId FROM evaluation ev ")
			.append("LEFT JOIN classevaluation cle ON ev.evaluationId = cle.evaluationId ")
			.append("LEFT JOIN courseevaluation coe ON ev.evaluationId = coe.evaluationId ")
			.append("WHERE cle.classId = :classId OR coe.courseId = :courseId) ")
			.append("AND ev.status = 1 ")
			.toString(),
		new MapSqlParameterSource()
			.addValue("courseId", tableCommand.getCourseId())
			.addValue("keyword", "%"+tableCommand.getKeyword()+"%")
			.addValue("classId", tableCommand.getClassId()), Integer.class);
	}
	
	private class FetchEmployeeEvaluationMapper implements RowMapper<EmployeeEvaluation> {
		@Override
		public EmployeeEvaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeEvaluation employeeEvaluation = new EmployeeEvaluation();
			employeeEvaluation.setEmployeeEvaluationId(rs.getInt("employeeEvaluationId"));
			employeeEvaluation.setEmployeeId(rs.getInt("employeeId"));
			employeeEvaluation.setEvaluationId(rs.getInt("evaluationId"));
			employeeEvaluation.setFullName(rs.getString("fullName"));
			employeeEvaluation.setEmployeeCode(rs.getString("employeeCode"));
			employeeEvaluation.setJobName(rs.getString("jobName"));
			employeeEvaluation.setEvaluationQuestionId(rs.getInt("evaluationQuestionId"));
			employeeEvaluation.setAnswer(rs.getString("answer"));
			employeeEvaluation.setDateCompleted(rs.getString("dateCompleted"));
			return employeeEvaluation;
		}
	}

	@Override
	public List<EmployeeEvaluation> fetchEmployeeEvaluations(int classEvaluationId, int pageSize, int calculatedPageNo) {
		return this.jdbcTemplate.query(
			new StringBuffer()
				.append("SELECT ee.employeeEvaluationId, ee.employeeId, ee.classEvaluationId, ei.fullName, ei.employeeCode, ")
				.append("jr.jobName, ee.evaluationQuestionId, ee.answer, ee.dateCompleted ")
				.append("FROM employeeevaluation ee ")
				.append("LEFT JOIN classemployee cle ON ee.employeeId = cle.employeeId ")
				.append("LEFT JOIN employeeinfo ei ON cle.employeeId = ei.employeeId ")
				.append("LEFT JOIN jobrole jr ON cle.role = jr.jobroleId ")
				.append("WHERE ee.classEvaluationId = :classEvaluationId ")
				.append("GROUP BY ee.employeeId ")
				.append("LIMIT :pageNumber,:pageSize ")
				.toString(),
			new MapSqlParameterSource()
				.addValue("classEvaluationId", classEvaluationId)
				.addValue("pageSize", pageSize)
				.addValue("pageNumber", calculatedPageNo),
			new FetchEmployeeEvaluationMapper());
	}

	@Override
	public int countTotalEmployeeEvaluations(int classEvaluationId) {
		return this.jdbcTemplate.queryForObject(
			new StringBuffer()
				.append("SELECT COUNT(ee.employeeEvaluationId) ")
				.append("FROM employeeevaluation ee ")
				.append("LEFT JOIN classemployee cle ON ee.employeeId = cle.employeeId ")
				.append("LEFT JOIN employeeinfo ei ON cle.employeeId = ei.employeeId ")
				.append("LEFT JOIN jobrole jr ON cle.role = jr.jobroleId ")
				.append("WHERE ee.classEvaluationId = :classEvaluationId ")
				.toString(),
			new MapSqlParameterSource()
			.addValue("classEvaluationId", classEvaluationId),
			Integer.class);
	}
}
