package cn.edu.seu.cse.survey.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import cn.edu.seu.cse.survey.entity.User;

import com.ibatis.sqlmap.client.SqlMapClient;

@Component
public class UserDAOImpl extends SqlMapClientDaoSupport implements UserDAO {

	@Autowired(required = true)
	public void setSqlMapClientTemp(SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}

	@Override
	public User getUserById(int id) {
		return (User) getSqlMapClientTemplate().queryForObject(
				"USER.selectUserById", id);
	}

	@Override
	public User getUserByEmailAndPswd(String email, String pswd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("password", pswd);
		return (User) getSqlMapClientTemplate().queryForObject(
				"USER.selectUserByEmailAndPswd", map);
	}

	@Override
	public void insertUser(User user) {
		getSqlMapClientTemplate().insert("USER.insertUser", user);
	}

	@Override
	public User getUserByEmail(String email) {
		return (User) getSqlMapClientTemplate().queryForObject(
				"USER.selectUserByEmail", email);
	}

	@Override
	public User getUserByNickname(String nickname) {
		return (User) getSqlMapClientTemplate().queryForObject(
				"USER.selectUserByNickname", nickname);
	}

	@Override
	public void updateUser(User user) {
		getSqlMapClientTemplate().update("USER.updateUser", user);
	}
}
