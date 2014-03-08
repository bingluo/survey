package cn.edu.seu.cse.survey.entity;

import java.util.List;

public class CatalogPojo {
	private int id;
	private String title;
	private int priority;
	private List<QuestionnairePojo> questionnaires;

	public List<QuestionnairePojo> getQuestionnaires() {
		return questionnaires;
	}

	public void setQuestionnaires(List<QuestionnairePojo> questionnaires) {
		this.questionnaires = questionnaires;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
