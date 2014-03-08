package cn.edu.seu.cse.survey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.seu.cse.survey.dao.SubmitDetailDAO;
import cn.edu.seu.cse.survey.entity.SubmitDetail;

@Service
public class SubmitDetailService {

	@Autowired
	SubmitDetailDAO submitDetailDAOImpl;

	public SubmitDetail getSubmitDetail(int questionnaireId, int userId) {
		return submitDetailDAOImpl.getSubmitDetailByQuestionnaireIdAndUserId(
				questionnaireId, userId);
	}
}
