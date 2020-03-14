package ru.ragnok123.menuAPI.form.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import lombok.NonNull;
import ru.ragnok123.menuAPI.form.FormMenu;
import ru.ragnok123.menuAPI.form.FormMenuHandler;
import ru.ragnok123.menuAPI.form.impl.elements.Button;
import ru.ragnok123.menuAPI.form.impl.response.ButtonResponse;
import ru.ragnok123.menuAPI.form.impl.response.FormGlobalResponse;

public class SimpleFormMenu implements FormMenu{
	
	private String title = "";
	private String content = "";
	
	public ArrayList<Button> buttons = new ArrayList<Button>();
	public HashMap<Button,ButtonResponse> responses = new HashMap<Button,ButtonResponse>();
	
	public SimpleFormMenu(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public void addButton(String text, ButtonResponse response) {
		addButton(text,null,null,response);
	}
	
	public void addButton(String text, String method, String url, ButtonResponse response) {
		Button b;
		if(method != null) {
			b = new Button(text,method,url);
		} else {
			b = new Button(text);
		}
		buttons.add(b);
		if(response != null) {
			responses.put(b,response);
		}
	}
	
	public Button getButton(int id) {
		return this.buttons.get(id);
	}
	
	public Button getButton(String text) {
		for(Button button : buttons) {
			if(button.getText().equals(text)) {
				return button;
			}
		}
		return null;
	}
	
	public boolean hasResponse(Button button) {
		return this.responses.containsKey(button);
	}
	
	public ButtonResponse getResponse(Button button) {
		return this.responses.get(button);
	}
	
	private int num = 0;
	public Integer getId() {
		return this.num;
	}
	
	public void show(@NonNull Player player) {
		FormMenuHandler.pmenus.put(player.getUniqueId(),this);
		FormWindowSimple simple = new FormWindowSimple(this.getTitle(),this.getContent());
		for(Button button : buttons) {
			if(button.hasImage()) {
				simple.addButton(new ElementButton(button.getText(), new ElementButtonImageData(button.getMethod(),button.getUrl())));
			} else {
				simple.addButton(new ElementButton(button.getText()));
			}	
		}
		Random rand = new Random();
		num = rand.nextInt(1000000000);
		player.showFormWindow(simple,num);
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}
	
	public String getContent() {
		return this.content;
	}

	private FormGlobalResponse empty = null;
	@Override
	public void setEmptyResponse(FormGlobalResponse response) {
		empty = response;
		
	}
	@Override
	public FormGlobalResponse getEmptyResponse() {
		return empty;
	}
	
}
