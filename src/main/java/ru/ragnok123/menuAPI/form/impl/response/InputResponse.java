package ru.ragnok123.menuAPI.form.impl.response;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.elements.Element;
import ru.ragnok123.menuAPI.form.impl.elements.Input;

public abstract class InputResponse implements ElementResponse {

	@Override
	public void onHandle(Player player,Element element, Object object) {
		if(object instanceof String && element instanceof Input) {
			onResponse(player,((Input)element),(String)object);
		}
	}
	
	public abstract void onResponse(Player player, Input element, String response);

}
