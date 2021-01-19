package ru.ragnok123.menuAPI.form.impl.elements;

import cn.nukkit.form.element.ElementButtonImageData;

public class Button {
	
	public enum PathMethod {
		URL(ElementButtonImageData.IMAGE_DATA_TYPE_URL),
		PATH(ElementButtonImageData.IMAGE_DATA_TYPE_PATH);
		
		private String method;
		
		PathMethod(String method){
			this.method = method;
		}
		
		public String getMethod() {
			return this.method;
		}
	}
	
	private String text = null;
	private PathMethod method = null;
	private String url = null;
	
	
	public Button(String text) {
		this.text = text;
	}
	
	public Button(String text, PathMethod method, String url) {
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
	
	public PathMethod getMethod() {
		return this.method;
	}
	public String getUrl() {
		return this.url;
	}

}
