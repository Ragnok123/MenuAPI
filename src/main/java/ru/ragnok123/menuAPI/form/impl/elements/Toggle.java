package ru.ragnok123.menuAPI.form.impl.elements;

public class Toggle implements Element {
	
	private String text = "";
	
	public Toggle(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}

}
