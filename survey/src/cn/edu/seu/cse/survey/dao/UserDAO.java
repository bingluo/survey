package cn.edu.seu.cse.survey.dao;

import cn.edu.seu.cse.survey.entity.User;

public interface UserDAO {
	User getUserById(int id);

	User getUserByEmailAndPswd(String email, String pswd);

	User getUserByEmail(String email);

	User getUserByNickname(String nickname);

	void insertUser(User user);

	void updateUser(User user);
}
