package ru.ragnok123.menuAPI.form.impl.elements;

public class Input implements Element {
	
	private String text = "";
	private String defaultText;
	
	public Input(String text) {
		this(text,null);
	}
	
	public Input(String text, String def) {
		this.text = text;
		if(def != null) {
			this.defaultText = def;
		}
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getDefaultText() {
		return this.defaultText;
	}
	
	public boolean hasDefault() {
		return this.defaultText != null;
	}
	
}
