package ru.ragnok123.menuAPI.form.impl.response;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.elements.Element;
import ru.ragnok123.menuAPI.form.impl.elements.Toggle;

public abstract class ToggleResponse implements ElementResponse<Boolean> {

	private Boolean response;

	@Override
	public void onHandle(Player player, Element element, Boolean object) {
		if(object instanceof Boolean && element instanceof Toggle) {
			this.response = object;
			onResponse(player,(Toggle)element,object);
		}
	}
	
	public Boolean getResponse() {
		return this.response;
	}
	
	public abstract void onResponse(Player player, Toggle element, Boolean response);
	
}
