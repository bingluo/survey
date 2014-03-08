package cn.edu.seu.cse.survey.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.seu.cse.survey.entity.User;
import cn.edu.seu.cse.survey.service.UserService;

@Controller
public class UserController extends AbstractController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/sign-in", method = RequestMethod.POST)
	public void signIn(HttpServletResponse response, HttpSession session,
			@RequestParam("email") String email,
			@RequestParam("password") String password) {
		JSONObject object = new JSONObject();

		User user = userService.signIn(email, password);
		int status;
		if (user != null) {
			session.setAttribute("userId", user.getId());
			status = 0;
			object.put("email", user.getEmail());
			object.put("nickname", user.getNickname());
		} else {
			status = 1;
		}
		object.put("status", status);
		ajaxResponse(response, object.toJSONString());
	}

	@RequestMapping(value = "/sign-in", method = RequestMethod.GET)
	public void signInget(HttpServletResponse response, HttpSession session,
			@RequestParam("email") String email,
			@RequestParam("password") String password) {
		JSONObject object = new JSONObject();

		User user = userService.signIn(email, password);
		int status;
		if (user != null) {
			session.setAttribute("userId", user.getId());
			status = 0;
			object.put("email", user.getEmail());
			object.put("nickname", user.getNickname());
		} else {
			status = 1;
		}
		object.put("status", status);
		ajaxResponse(response, object.toJSONString());
	}

	@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
	public void signUp(HttpServletResponse response, HttpSession session,
			@RequestParam("nickname") String nickname,
			@RequestParam("email") String email,
			@RequestParam("password") String password) {

		JSONObject object = new JSONObject();
		int status = userService.signUp(nickname, email, password);
		User user = userService.signIn(email, password);
		if (user != null) {
			session.setAttribute("userId", user.getId());
			object.put("email", user.getEmail());
			object.put("nickname", user.getNickname());
		}

		object.put("status", status);
		ajaxResponse(response, object.toJSONString());
	}

	@RequestMapping(value = "/log-out", method = RequestMethod.GET)
	public void logOut(HttpServletResponse response, HttpSession session) {
		session.setAttribute("userId", null);

		JSONObject object = new JSONObject();
		object.put("status", 0);
		ajaxResponse(response, object.toJSONString());
	}

	@RequestMapping(value = "/current-user", method = RequestMethod.GET)
	public void currentUser(HttpServletResponse response, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		JSONObject object = new JSONObject();
		int status;
		if (userId != null) {
			User user = userService.getUserById(userId);
			if (user != null) {
				status = 0;
				object.put("email", user.getEmail());
				object.put("nickname", user.getNickname());
			} else {
				status = 1;
			}
		} else {
			status = 1;
		}
		object.put("status", status);

		ajaxResponse(response, object.toJSONString());
	}
}
