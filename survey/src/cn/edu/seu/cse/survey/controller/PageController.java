package cn.edu.seu.cse.survey.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.seu.cse.survey.entity.Questionnaire;
import cn.edu.seu.cse.survey.entity.User;
import cn.edu.seu.cse.survey.service.QuestionnaireService;
import cn.edu.seu.cse.survey.service.UserService;

@Controller
public class PageController extends AbstractController {

	@Autowired
	QuestionnaireService questionnaireService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/next-page", method = RequestMethod.GET)
	public void nextPage(HttpServletResponse response, HttpSession session) {
		Questionnaire questionnaire = null;
		Integer userId = (Integer) session.getAttribute("userId");
		int status;
		JSONObject object = new JSONObject();
		if (userId == null) {
			User user = userService.getUserById(userId);
			if (user != null) {
				questionnaire = questionnaireService
						.getNextQuestionnaire(userId);
				if (questionnaire != null) {
					status = 0;
					object.put("id", questionnaire.getId());
					object.put("catalogId", questionnaire.getCatalogId());
					object.put("title", questionnaire.getTitle());
					object.put("pageName", questionnaire.getPageName());
				} else {
					status = 1;// 题目已答完
				}
			} else {
				status = 3;// 用户不存在
			}
		} else {
			status = 2;// 用户未登录
		}

		object.put("status", status);
		ajaxResponse(response, object.toJSONString());
	}
}
