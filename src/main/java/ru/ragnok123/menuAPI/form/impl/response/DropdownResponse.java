package ru.ragnok123.menuAPI.form.impl.response;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.elements.Dropdown;
import ru.ragnok123.menuAPI.form.impl.elements.Element;

public abstract class DropdownResponse implements ElementResponse {

	@Override
	public void onHandle(Player player, Element element, Object object) {
		if(object instanceof String && element instanceof Dropdown) {
			onResponse(player,((Dropdown)element),(String)object);
		}
	}
	
	public abstract void onResponse(Player player, Dropdown element, String response);

}
