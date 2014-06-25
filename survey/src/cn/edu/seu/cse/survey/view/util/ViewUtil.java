package cn.edu.seu.cse.survey.view.util;

public class ViewUtil {
	public static String CONTEXT_PATH = "";

	public static String getContextPath() {
		return CONTEXT_PATH;
	}

	public static void setContextPath(String contextPath) {

		CONTEXT_PATH = contextPath != null ? contextPath : "";
	}
}
