package cn.edu.seu.cse.survey.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import cn.edu.seu.cse.survey.entity.Questionnaire;
import cn.edu.seu.cse.survey.entity.QuestionnairePojo;

import com.ibatis.sqlmap.client.SqlMapClient;

@Component
@SuppressWarnings("unchecked")
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
	public List<QuestionnairePojo> getQuestionnairesByCatalogId(int catalogId) {
		return getSqlMapClientTemplate().queryForList(
				"QUESTIONNAIRE.selectQuestionnairesByCatalogId", catalogId);
	}

	@Override
	public List<QuestionnairePojo> getAllQuestionnaires() {
		return getSqlMapClientTemplate().queryForList(
				"QUESTIONNAIRE.selectAllQuestionnaires");
	}
}