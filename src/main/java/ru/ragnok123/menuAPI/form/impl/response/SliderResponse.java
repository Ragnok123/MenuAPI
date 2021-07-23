package ru.ragnok123.menuAPI.form.impl.response;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.elements.Element;
import ru.ragnok123.menuAPI.form.impl.elements.Slider;

public abstract class SliderResponse implements ElementResponse<Float> {

	private Float response;

	@Override
	public void onHandle(Player player, Element element, Float object) {
		if(object instanceof Float && element instanceof Slider) {
			this.response = object;
			onResponse(player, (Slider)element,object);
		}
	}
	
	public Float getResponse() {
		return this.response;
	}
	
	public abstract void onResponse(Player player, Slider element, Float response);

}
