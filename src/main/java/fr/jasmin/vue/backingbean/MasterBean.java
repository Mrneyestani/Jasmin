package fr.jasmin.vue.backingbean;

public class MasterBean{

	private String promptStatus = null ;
		
	public MasterBean() {
	}

	public String getPromptStatus() {
		return promptStatus;
	}

	public void setPromptStatus(String promptStatus) {
		this.promptStatus = promptStatus;
	}
	
	public void cleanPromptStatus() {
		this.setPromptStatus(null);
	}
	
	public void clean() {
		this.setPromptStatus(null);
	}
	
}
