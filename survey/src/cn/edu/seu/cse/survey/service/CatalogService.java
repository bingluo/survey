package cn.edu.seu.cse.survey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.seu.cse.survey.dao.CatalogDAO;
import cn.edu.seu.cse.survey.dao.QuestionnaireDAO;
import cn.edu.seu.cse.survey.dao.SubmitDetailDAO;
import cn.edu.seu.cse.survey.entity.CatalogPojo;
import cn.edu.seu.cse.survey.entity.QuestionnairePojo;
import cn.edu.seu.cse.survey.entity.SubmitDetail;

@Service
public class CatalogService {

	@Autowired
	CatalogDAO catalogDAOImpl;
	@Autowired
	QuestionnaireDAO questionnaireDAOImpl;
	@Autowired
	SubmitDetailDAO submitDetailDAOImpl;

	private List<CatalogPojo> getAllCatalogsWithAnswerStep(int userId) {
		List<CatalogPojo> catalogs = catalogDAOImpl.getAllCatalogs();
		for (CatalogPojo catalog : catalogs) {
			List<QuestionnairePojo> questionnairePojos = questionnaireDAOImpl
					.getQuestionnairesByCatalogId(catalog.getId());
			for (QuestionnairePojo questionnairePojo : questionnairePojos) {
				questionnairePojo.setSubmitDetail(submitDetailDAOImpl
						.getSubmitDetailByQuestionnaireIdAndUserId(
								questionnairePojo.getId(), userId));
			}
			catalog.setQuestionnaires(questionnairePojos);
		}
		return catalogs;
	}

	public String getMenuString(int userId) {
		List<CatalogPojo> catalogs = getAllCatalogsWithAnswerStep(userId);
		StringBuilder sb = new StringBuilder();
		sb.append("<ul id='survey-menu-list'>");
		for (int i = 0; i < catalogs.size(); i++) {
			CatalogPojo catalog = catalogs.get(i);
			sb.append("<li>");
			sb.append("<p>").append(catalog.getTitle()).append("</p>");
			sb.append("<ul class='survey-menu-inner-list'>");
			for (QuestionnairePojo questionnaire : catalog.getQuestionnaires()) {
				String didBefore = "";
				SubmitDetail submitDetail = questionnaire.getSubmitDetail();
				if (submitDetail != null && submitDetail.getContent() != null
						&& !submitDetail.getContent().equals("")) {
					didBefore = " class='done'";
				}
				sb.append("<li").append(didBefore)
						.append("><a href='?questionnaireId=")
						.append(questionnaire.getId()).append("'>")
						.append(questionnaire.getTitle()).append("</a></li>");
			}
			sb.append("</ul>");
			sb.append("</li>");
		}

		sb.append("</ul>");
		return sb.toString();
	}
}