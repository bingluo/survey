package cn.edu.seu.cse.survey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.seu.cse.survey.dao.UserDAO;
import cn.edu.seu.cse.survey.entity.User;

@Service
public class UserService {
	@Autowired
	UserDAO userDaoImpl;

	public User getUserById(int id) {
		return userDaoImpl.getUserById(id);
	}

	public User signIn(String email, String pswd) {
		User user = userDaoImpl.getUserByEmailAndPswd(email, pswd);
		return user;
	}

	public int signUp(String nickname, String email, String pswd) {
		if (userDaoImpl.getUserByEmail(email) != null) {
			return 1;
		}
		if (userDaoImpl.getUserByNickname(nickname) != null) {
			return 2;
		}
		User user = new User();
		user.setNickname(nickname);
		user.setEmail(email);
		user.setPassword(pswd);
		userDaoImpl.insertUser(user);

		return 0;
	}
}
