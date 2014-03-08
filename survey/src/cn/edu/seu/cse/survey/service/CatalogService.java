package cn.edu.seu.cse.survey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.seu.cse.survey.dao.CatalogDAO;
import cn.edu.seu.cse.survey.dao.QuestionnaireDAO;
import cn.edu.seu.cse.survey.entity.CatalogPojo;

@Service
public class CatalogService {

	@Autowired
	CatalogDAO catalogDAOImpl;
	@Autowired
	QuestionnaireDAO questionnaireDAOImpl;

	List<CatalogPojo> getAllCatalogsWithAnswerStep(int userId) {
		List<CatalogPojo> catalogs = catalogDAOImpl.getAllCatalogs();
		for (CatalogPojo catalog : catalogs) {
			catalog.setQuestionnaires(questionnaireDAOImpl
					.getQuestionnairesByCatalogIdAndUserId(catalog.getId(),
							userId));
		}
		return catalogs;
	}
}
