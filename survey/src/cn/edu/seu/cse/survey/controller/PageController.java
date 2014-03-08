package cn.edu.seu.cse.survey.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.seu.cse.survey.entity.Questionnaire;
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
	@Autowired
	@RequestMapping(value = "/next-page", method = RequestMethod.GET)
	public void nextPage(HttpServletResponse response, HttpSession session) {
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
					status = 0;
					object.put("id", questionnaire.getId());
					object.put("catalogId", questionnaire.getCatalogId());
					object.put("title", questionnaire.getTitle());
					object.put("pageName", questionnaire.getPageName());
				} else {
					status = 1;// ��Ŀ�Ѵ���
				}
			} else {
				status = 3;// �û�������
			}
		} else {
			status = 2;// �û�δ��¼
		}

		object.put("status", status);
		ajaxResponse(response, object.toJSONString());
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/get-answer", method = RequestMethod.GET)
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
				if (submitDetail != null) {
					object.put("questionnaireId",
							submitDetail.getQuestionnaireId());
					object.put("content", submitDetail.getContent());
					object.put("submitTime", submitDetail.getSubmitTime());
					status = 0;
				} else {
					status = 1;// δ������
				}
			} else {
				status = 3;// �û�������
			}
		} else {
			status = 2;// �û�δ��¼
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

				status = 0;
			} else {
				status = 3;// �û�������
			}
		} else {
			status = 2;// �û�δ��¼
		}
		object.put("status", status);
		ajaxResponse(response, object.toJSONString());
		// <ul id="survey-menu-list">
		// <li>
		// <p>�������</p>
		// <ul class="survey-menu-inner-list">
		// <li></li>
		// </ul>
		// </li>
		// <li>
		// <p>��һ����</p>
		// </li>
		// <li>
		// <p>�ڶ�����</p>
		// </li>
		// <li>
		// <p>��������</p>
		// </li>
		// <li>
		// <p>���Ĳ���</p>
		// </li>
		// <li>
		// <p>���岿��</p>
		// </li>
		// <li>
		// <p>��������</p>
		// </li>
		// <li>
		// <p>���߲���</p>
		// </li>
		// </ul>
	}
}
