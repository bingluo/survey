package cn.edu.seu.cse.survey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.seu.cse.survey.dao.CatalogDAO;
import cn.edu.seu.cse.survey.dao.QuestionnaireDAO;
import cn.edu.seu.cse.survey.dao.SubmitDetailDAO;
import cn.edu.seu.cse.survey.entity.CatalogPojo;
import cn.edu.seu.cse.survey.entity.QuestionnairePojo;

@Service
public class CatalogService {

	@Autowired
	CatalogDAO catalogDAOImpl;
	@Autowired
	QuestionnaireDAO questionnaireDAOImpl;
	@Autowired
	SubmitDetailDAO submitDetailDAOImpl;

	List<CatalogPojo> getAllCatalogsWithAnswerStep(int userId) {
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
}