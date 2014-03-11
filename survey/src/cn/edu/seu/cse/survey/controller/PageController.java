package cn.edu.seu.cse.survey.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.seu.cse.survey.entity.Questionnaire;
import cn.edu.seu.cse.survey.entity.QuestionnairePojo;
import cn.edu.seu.cse.survey.entity.SubmitDetail;
import cn.edu.seu.cse.survey.entity.User;
import cn.edu.seu.cse.survey.service.CatalogService;
import cn.edu.seu.cse.survey.service.QuestionnaireService;
import cn.edu.seu.cse.survey.service.SubmitDetailService;
import cn.edu.seu.cse.survey.service.UserService;

@Controller
public class PageController extends AbstractController {

	@Autowired
	CatalogService catalogService;
	@Autowired
	QuestionnaireService questionnaireService;
	@Autowired
	UserService userService;
	@Autowired
	SubmitDetailService submitDetailService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/next-page", method = RequestMethod.GET)
	public String nextPage(Model model, HttpServletResponse response,
			HttpSession session) throws IOException {
		Questionnaire questionnaire = null;
		Integer userId = (Integer) session.getAttribute("userId");
		int status;
		JSONObject object = new JSONObject();
		if (userId != null) {
			User user = userService.getUserById(userId);
			if (user != null) {
				questionnaire = questionnaireService
						.getNextQuestionnaire(userId);
				if (questionnaire != null) {
					model.addAttribute("questionnaireId", questionnaire.getId());
					return questionnaire.getPageName().split(".")[0];
				} else {
					status = 1;// 题目已答完
				}
			} else {
				status = 3;// 用户不存在
			}
		} else {
			status = 2;// 用户未登录
		}
		response.getWriter().write(status);
		return null;
	}

	@RequestMapping(value = "/get-page", method = RequestMethod.GET)
	public String getPage(Model model, HttpServletResponse response,
			HttpSession session,
			@RequestParam("questionnaireId") int questionnaireId)
			throws IOException {
		QuestionnairePojo questionnaire;
		Integer userId = (Integer) session.getAttribute("userId");
		int status;
		JSONObject object = new JSONObject();
		if (userId != null) {
			User user = userService.getUserById(userId);
			if (user != null) {
				questionnaire = questionnaireService
						.getQuestionnaireById(questionnaireId);
				if (questionnaire != null) {
					model.addAttribute("questionnaireId", questionnaire.getId());
					return questionnaire.getPageName().split(".")[0];
				} else {
					status = 1;// 问卷不存在
				}
			} else {
				status = 3;// 用户不存在
			}
		} else {
			status = 2;// 用户未登录
		}
		response.getWriter().write(status);
		ajaxResponse(response, object.toJSONString());
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/get-answer", method = RequestMethod.POST)
	public void getAnswer(HttpServletResponse response, HttpSession session,
			@RequestParam("questionnaireId") int questionnaireId) {

		SubmitDetail submitDetail;
		int status;
		Integer userId = (Integer) session.getAttribute("userId");
		JSONObject object = new JSONObject();

		if (userId != null) {
			User user = userService.getUserById(userId);
			if (user != null) {
				submitDetail = submitDetailService.getSubmitDetail(
						questionnaireId, userId);
				if (submitDetail != null && submitDetail.getContent() != null
						&& !submitDetail.getContent().equals("")) {
					object.put("questionnaireId",
							submitDetail.getQuestionnaireId());
					object.put("content", submitDetail.getContent());
					object.put("submitTime", submitDetail.getSubmitTime());
					status = 0;
				} else {
					status = 1;// 未曾答题
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/get-menu", method = RequestMethod.GET)
	public void getMenu(HttpServletResponse response, HttpSession session) {
		int status;
		Integer userId = (Integer) session.getAttribute("userId");
		JSONObject object = new JSONObject();

		if (userId != null) {
			User user = userService.getUserById(userId);
			if (user != null) {
				String menuString = catalogService.getMenuString(userId);
				object.put("menu", menuString);
				status = 0;
			} else {
				status = 2;// 用户不存在
			}
		} else {
			status = 1;// 用户未登录
		}
		object.put("status", status);
		ajaxResponse(response, object.toJSONString());
	}

	@RequestMapping(value = "/submit-answer", method = RequestMethod.POST)
	public void submitAnswer(HttpServletResponse response, HttpSession session,
			@RequestParam("questionnaireId") int questionnaireId,
			@RequestParam("answer") String answer) {
		int status;
		Integer userId = (Integer) session.getAttribute("userId");
		JSONObject object = new JSONObject();

		QuestionnairePojo questionnaire = questionnaireService
				.getQuestionnaireById(questionnaireId);
		if (questionnaire == null) {
			status = 3;// 问卷不存在

		} else if (userId != null) {
			User user = userService.getUserById(userId);
			if (user != null) {
				submitDetailService.submit(questionnaireId, userId, answer);
				status = 0;
			} else {
				status = 2;// 用户不存在
			}
		} else {
			status = 1;// 用户未登录
		}
		object.put("status", status);
		ajaxResponse(response, object.toJSONString());
	}
}