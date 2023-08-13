package br.com.senai.core.domain;

public class Email {
	
	private String to;
	
	private String name;

	public Email(String to, String name) {
		this.to = to;
		this.name = name;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	
	

}
