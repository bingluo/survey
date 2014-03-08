package cn.edu.seu.cse.survey.entity;

import java.util.Date;

public class SubmitDetail {
	private int userId;
	private int questionnaireId;
	private int catalogId;
	private String content;
	private Date submitTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public int getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
}
