package cn.edu.seu.cse.survey.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.edu.seu.cse.survey.view.util.ViewUtil;

public class InitSystemListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ViewUtil.setContextPath(event.getServletContext().getContextPath());
	}
}
