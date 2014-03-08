package cn.edu.seu.cse.survey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.seu.cse.survey.dao.QuestionnaireDAO;
import cn.edu.seu.cse.survey.entity.Questionnaire;
import cn.edu.seu.cse.survey.entity.QuestionnairePojo;

@Service
public class QuestionnaireService {

	@Autowired
	QuestionnaireDAO questionnaireDAOImpl;

	public Questionnaire getNextQuestionnaire(int userId) {
		List<QuestionnairePojo> questionnaires = questionnaireDAOImpl
				.getAllQuestionnairesByUserId(userId);
		for (int i = 0; i < questionnaires.size(); i++) {
			if (questionnaires.get(i).getSubmitContent() == null
					|| questionnaires.get(i).getSubmitContent().equals("")) {
				Questionnaire questionnaire = questionnaireDAOImpl
						.getQuestionnaireById(questionnaires.get(i).getId());
				return questionnaire;
			}
		}
		return null;
	}
}
