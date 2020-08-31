package ru.ragnok123.menuAPI.form.impl.elements;

import java.util.ArrayList;
import java.util.List;

public class Stepslider extends Element {
	
	private String text = "";
	private List<String> steps = new ArrayList<String>();
	
	public Stepslider(String text, List<String> steps) {
		this.text = text;
		this.steps = steps;
	}
	
	public String getText() {
		return this.text;
	}
	
	public List<String> getSteps(){
		return this.steps;
	}
	
}
