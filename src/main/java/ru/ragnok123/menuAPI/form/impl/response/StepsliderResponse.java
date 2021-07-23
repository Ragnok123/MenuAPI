package ru.ragnok123.menuAPI.form.impl.response;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.elements.Stepslider;
import ru.ragnok123.menuAPI.form.impl.elements.Element;

public abstract class StepsliderResponse implements ElementResponse<String> {

	private String response;

	@Override
	public void onHandle(Player player, Element element, String object) {
		if(object instanceof String && element instanceof Stepslider) {
			this.response = object;
			onResponse(player,((Stepslider)element),object);
		}
	}
	
	public String getResponse() {
		return this.response;
	}
	
	public abstract void onResponse(Player player, Stepslider element, String response);
}
