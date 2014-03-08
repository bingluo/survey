package cn.edu.seu.cse.survey.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import cn.edu.seu.cse.survey.entity.CatalogPojo;

import com.ibatis.sqlmap.client.SqlMapClient;

@Component
public class CatalogDAOImpl extends SqlMapClientDaoSupport implements
		CatalogDAO {

	@Autowired(required = true)
	public void setSqlMapClientTemp(SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}

	@Override
	public List<CatalogPojo> getAllCatalogs() {
		return getSqlMapClientTemplate().queryForList(
				"CATALOG.selectAllCatalogs");
	}
}
