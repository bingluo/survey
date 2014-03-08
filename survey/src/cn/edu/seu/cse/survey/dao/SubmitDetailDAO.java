package cn.edu.seu.cse.survey.dao;

import cn.edu.seu.cse.survey.entity.SubmitDetail;

public interface SubmitDetailDAO {
	SubmitDetail getSubmitDetailByQuestionnaireIdAndUserId(int questionnaireId,
			int userId);
}
