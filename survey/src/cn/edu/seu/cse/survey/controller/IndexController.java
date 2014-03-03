package cn.edu.seu.cse.survey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/{rootCat}.html")
	public String indexPage(Model model, @PathVariable("rootCat") String rootCat) {
		model.addAttribute("a", rootCat);
		return "index";
	}
}
