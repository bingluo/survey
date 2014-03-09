package cn.edu.seu.cse.survey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.seu.cse.survey.dao.QuestionnaireDAO;
import cn.edu.seu.cse.survey.dao.SubmitDetailDAO;
import cn.edu.seu.cse.survey.entity.SubmitDetail;

@Service
public class SubmitDetailService {

	@Autowired
	SubmitDetailDAO submitDetailDAOImpl;
	@Autowired
	QuestionnaireDAO questionnaireDAOImpl;

	public SubmitDetail getSubmitDetail(int questionnaireId, int userId) {
		return submitDetailDAOImpl.getSubmitDetailByQuestionnaireIdAndUserId(
				questionnaireId, userId);
	}

	public void submit(int questionnaireId, int userId, String content) {
		SubmitDetail submitDetail = submitDetailDAOImpl
				.getSubmitDetailByQuestionnaireIdAndUserId(questionnaireId,
						userId);
		if (submitDetail == null) {
			int catalogId = questionnaireDAOImpl.getQuestionnaireById(
					questionnaireId).getCatalogId();
			submitDetail = new SubmitDetail();
			submitDetail.setCatalogId(catalogId);
			submitDetail.setContent(content);
			submitDetail.setQuestionnaireId(questionnaireId);
			submitDetail.setUserId(userId);
			submitDetailDAOImpl.insertSubmitDetail(submitDetail);
		} else {
			submitDetail.setContent(content);
			submitDetailDAOImpl.updateSubmitDetail(submitDetail);
		}
	}
}
