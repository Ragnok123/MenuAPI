package ru.ragnok123.menuAPI.form.impl.response;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.elements.Element;
import ru.ragnok123.menuAPI.form.impl.elements.Toggle;

public abstract class ToggleResponse implements ElementResponse {

	@Override
	public void onHandle(Player player, Element element, Object object) {
		if(object instanceof Boolean && element instanceof Toggle) {
			onResponse(player,(Toggle)element,(Boolean)object);
		}
	}
	
	public abstract void onResponse(Player player, Toggle element, Boolean response);
	
}
