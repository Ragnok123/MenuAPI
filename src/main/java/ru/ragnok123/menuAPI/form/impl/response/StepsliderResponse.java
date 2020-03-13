package ru.ragnok123.menuAPI.form.impl.response;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.elements.Stepslider;
import ru.ragnok123.menuAPI.form.impl.elements.Element;

public abstract class StepsliderResponse implements ElementResponse {

	@Override
	public void onHandle(Player player, Element element, Object object) {
		if(object instanceof String && element instanceof Stepslider) {
			onResponse(player,((Stepslider)element),(String)object);
		}
	}
	
	public abstract void onResponse(Player player, Stepslider element, String response);
}
