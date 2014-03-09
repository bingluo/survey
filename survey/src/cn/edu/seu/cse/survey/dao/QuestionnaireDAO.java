package cn.edu.seu.cse.survey.dao;

import java.util.List;

import cn.edu.seu.cse.survey.entity.QuestionnairePojo;

public interface QuestionnaireDAO {
	QuestionnairePojo getQuestionnaireById(int id);

	List<QuestionnairePojo> getQuestionnairesByCatalogId(int catalogId);

	List<QuestionnairePojo> getAllQuestionnaires();
}