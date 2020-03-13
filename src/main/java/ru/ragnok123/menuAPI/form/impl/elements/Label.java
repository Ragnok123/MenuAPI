package ru.ragnok123.menuAPI.form.impl.elements;

public class Label implements Element {
	
	public Label(String label) {
		this.label = label;
	}
	
	public String getText() {
		return this.label;
	}
	
	private String label = "";

}
