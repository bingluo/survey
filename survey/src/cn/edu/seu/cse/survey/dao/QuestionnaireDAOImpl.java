package cn.edu.seu.cse.survey.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import cn.edu.seu.cse.survey.entity.Questionnaire;
import cn.edu.seu.cse.survey.entity.QuestionnairePojo;

import com.ibatis.sqlmap.client.SqlMapClient;

@Component
public class QuestionnaireDAOImpl extends SqlMapClientDaoSupport implements
		QuestionnaireDAO {

	@Autowired(required = true)
	public void setSqlMapClientTemp(SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}

	@Override
	public Questionnaire getQuestionnaireById(int id) {
		return (Questionnaire) getSqlMapClientTemplate().queryForObject(
				"QUESTIONNAIRE.selectQuestionnaireById", id);
	}

	@Override
	public List<QuestionnairePojo> getQuestionnairesByCatalogIdAndUserId(
			int catalogId, int userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("catalogId", catalogId);
		map.put("userId", userId);
		return getSqlMapClientTemplate().queryForList(
				"QUESTIONNAIRE.selectAllQuestionnairesByCatalogIdAndUserId",
				map);
	}

	@Override
	public List<QuestionnairePojo> getAllQuestionnairesByUserId(int userId) {
		return getSqlMapClientTemplate().queryForList(
				"QUESTIONNAIRE.selectAllQuestionnairesByUserId", userId);
	}
}