package cn.edu.seu.cse.survey.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import cn.edu.seu.cse.survey.entity.SubmitDetail;

import com.ibatis.sqlmap.client.SqlMapClient;

@Component
public class SubmitDetailDAOImpl extends SqlMapClientDaoSupport implements
		SubmitDetailDAO {

	@Autowired(required = true)
	public void setSqlMapClientTemp(SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}

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

	@Override
	public void insertSubmitDetail(SubmitDetail submitDetail) {
		getSqlMapClientTemplate().insert("SUBMIT_DETAIL.insertSubmitDetail",
				submitDetail);
	}

	@Override
	public void updateSubmitDetail(SubmitDetail submitDetail) {
		getSqlMapClientTemplate().update("SUBMIT_DETAIL.updateSubmitDetail",
				submitDetail);
	}
}
