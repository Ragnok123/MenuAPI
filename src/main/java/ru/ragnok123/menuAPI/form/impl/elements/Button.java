package ru.ragnok123.menuAPI.form.impl.elements;

import cn.nukkit.form.element.ElementButtonImageData;

public class Button {
	
	public static String METHOD_URL = ElementButtonImageData.IMAGE_DATA_TYPE_URL;
	public static String METHOD_PATH = ElementButtonImageData.IMAGE_DATA_TYPE_PATH;
	
	private String text = null;
	private String method = null;
	private String url = null;
	
	
	public Button(String text) {
		this.text = text;
	}
	
	public Button(String text, String method, String url) {
		this.text = text;
		this.method = method;
		this.url = url;
	}
	
	public String getText() {
		return this.text;
	}
	
	public boolean hasImage() {
		return this.method != null;
	}
	
	public String getMethod() {
		return this.method;
	}
	public String getUrl() {
		return this.url;
	}

}
