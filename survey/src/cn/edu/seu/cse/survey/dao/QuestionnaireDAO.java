package cn.edu.seu.cse.survey.dao;

import java.util.List;

import cn.edu.seu.cse.survey.entity.Questionnaire;
import cn.edu.seu.cse.survey.entity.QuestionnairePojo;

public interface QuestionnaireDAO {
	Questionnaire getQuestionnaireById(int id);

	List<QuestionnairePojo> getQuestionnairesByCatalogIdAndUserId(
			int catalogId, int userId);

	List<QuestionnairePojo> getAllQuestionnairesByUserId(int userId);
}
