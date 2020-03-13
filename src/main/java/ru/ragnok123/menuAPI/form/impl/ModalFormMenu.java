package ru.ragnok123.menuAPI.form.impl;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindowModal;
import lombok.NonNull;
import ru.ragnok123.menuAPI.form.FormMenu;
import ru.ragnok123.menuAPI.form.FormMenuHandler;
import ru.ragnok123.menuAPI.form.impl.elements.Button;
import ru.ragnok123.menuAPI.form.impl.response.ButtonResponse;
import ru.ragnok123.menuAPI.form.impl.response.FormGlobalResponse;

public class ModalFormMenu implements FormMenu {
	
	private String title = "";
	private String content = "";
	
	private Button but1 = null;
	private Button but2 = null;
	
	private ButtonResponse response1 = null;
	private ButtonResponse response2 = null;
	
	public ModalFormMenu(String title, String content) {
		this.title =title;
		this.content = content;
	}
	
	public void setButton1(@NonNull String text, ButtonResponse response) {
		but1 = new Button(text);
		if(response != null) {
			response1 = response;
		}
	}
	
	public void setButton2(@NonNull String text, ButtonResponse response) {
		but2 = new Button(text);
		if(response != null) {
			response2 = response;
		}
	}
	
	@Override
	public void show(Player player) {
		FormMenuHandler.pmenus.put(player.getUniqueId(),this);
		FormWindowModal modal = new FormWindowModal(this.getTitle(),this.getContent(),but1.getText(),but2.getText());
		player.showFormWindow(modal);
	}
	
	public Button getButton(int id) {
		if(id == 0) {
			return but1;
		} else if(id == 1) {
			return but2;
		}
		return null;
	}
	
	public boolean hasResponse(Button button) {
		if(button == but1) {
			return response1 != null ? true : false;
		} else if(button == but2) {
			return response2 != null ? true: false;
		}
		return false;
	}
	
	public ButtonResponse getResponse(Button but) {
		if(but == but1) {
			return response1;
		} else if(but == but2) {
			return response2;
		}
		return null;
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
