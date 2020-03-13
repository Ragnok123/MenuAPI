package ru.ragnok123.menuAPI.form.impl.elements;

public class Slider implements Element {
	
	private String text = "";
	private float min = 0;
	private float max = 100;
	
	public Slider(String text, float min, float max) {
		this.text = text;
		this.min = min;
		this.max = max;
	}
	
	public String getText() {
		return this.text;
	}
	
	public float getMin() {
		return this.min;
	}
	
	public float getMax() {
		return this.max;
	}

}
