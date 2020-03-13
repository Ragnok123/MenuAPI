package ru.ragnok123.menuAPI.form.impl.elements;

import java.util.ArrayList;
import java.util.List;

public class Dropdown implements Element {
	
	private String text = "";
	private List<String> options = new ArrayList<String>();
	
	public Dropdown(String text, List<String> options) {
		this.text = text;
		this.options = options;
	}
	
	public String getText() {
		return this.text;
	}
	
	public List<String> getOptions(){
		return this.options;
	}
	
}
