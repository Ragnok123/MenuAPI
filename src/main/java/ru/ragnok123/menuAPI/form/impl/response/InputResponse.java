package ru.ragnok123.menuAPI.form.impl.response;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.elements.Element;
import ru.ragnok123.menuAPI.form.impl.elements.Input;

public abstract class InputResponse implements ElementResponse<String> {

	private String response;

	@Override
	public void onHandle(Player player,Element element, String object) {
		if(object instanceof String && element instanceof Input) {
			this.response = object;
			onResponse(player,((Input)element),object);
		}
	}
	
	public String getResponse() {
		return this.response;
	}
	
	public abstract void onResponse(Player player, Input element, String response);

}
