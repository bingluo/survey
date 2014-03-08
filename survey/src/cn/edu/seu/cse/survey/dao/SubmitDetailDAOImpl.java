package cn.edu.seu.cse.survey.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cn.edu.seu.cse.survey.entity.SubmitDetail;

public class SubmitDetailDAOImpl extends SqlMapClientDaoSupport implements
		SubmitDetailDAO {

	@Override
	public SubmitDetail getSubmitDetailByQuestionnaireIdAndUserId(
			int questionnaireId, int userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("questionnaireId", questionnaireId);
		map.put("userId", userId);
		return (SubmitDetail) getSqlMapClientTemplate().queryForObject(
				"SUBMIT_DETAIL.selectSubmitDetailByQuestionnaireIdAndUserId",
				map);
	}
}
