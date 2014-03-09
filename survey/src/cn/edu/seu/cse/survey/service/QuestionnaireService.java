package cn.edu.seu.cse.survey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.seu.cse.survey.dao.QuestionnaireDAO;
import cn.edu.seu.cse.survey.dao.SubmitDetailDAO;
import cn.edu.seu.cse.survey.entity.Questionnaire;
import cn.edu.seu.cse.survey.entity.QuestionnairePojo;

@Service
public class QuestionnaireService {

	@Autowired
	QuestionnaireDAO questionnaireDAOImpl;
	@Autowired
	SubmitDetailDAO submitDetailDAOImpl;

	public Questionnaire getNextQuestionnaire(int userId) {
		List<QuestionnairePojo> questionnaires = questionnaireDAOImpl
				.getAllQuestionnaires();
		for (int i = 0; i < questionnaires.size(); i++) {
			QuestionnairePojo questionnaire = questionnaires.get(i);
			questionnaire.setSubmitDetail(submitDetailDAOImpl
					.getSubmitDetailByQuestionnaireIdAndUserId(
							questionnaire.getId(), userId));
			if (questionnaire.getSubmitDetail() == null
					|| questionnaire.getSubmitDetail().getContent() == null
					|| questionnaire.getSubmitDetail().getContent().equals("")) {
				return questionnaireDAOImpl.getQuestionnaireById(questionnaires
						.get(i).getId());
			}
		}
		return null;
	}
}