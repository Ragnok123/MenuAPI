package ru.ragnok123.menuAPI.npc;

import cn.nukkit.form.element.ElementButtonImageData;
import ru.ragnok123.menuAPI.form.impl.elements.Button.PathMethod;

public class NPCButton {
	
	private String text = null;
	
	
	public NPCButton(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
}