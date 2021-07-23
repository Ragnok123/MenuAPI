package ru.ragnok123.menuAPI.form.impl.response;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.elements.Dropdown;
import ru.ragnok123.menuAPI.form.impl.elements.Element;

public abstract class DropdownResponse implements ElementResponse<String> {

	private String response;
	
	@Override
	public void onHandle(Player player, Element element, String object) {
		if(object instanceof String && element instanceof Dropdown) {
			this.response = object;
			onResponse(player,((Dropdown)element),object);
		}
	}
	
	public String getResponse() {
		return this.response;
	}
	
	public abstract void onResponse(Player player, Dropdown element, String response);

}
