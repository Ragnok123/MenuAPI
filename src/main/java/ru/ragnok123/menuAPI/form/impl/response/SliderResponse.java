package ru.ragnok123.menuAPI.form.impl.response;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.elements.Element;
import ru.ragnok123.menuAPI.form.impl.elements.Slider;

public abstract class SliderResponse implements ElementResponse {

	@Override
	public void onHandle(Player player, Element element, Object object) {
		if(object instanceof Float && element instanceof Slider) {
			onResponse(player, (Slider)element,(Float)object);
		}
	}
	
	public abstract void onResponse(Player player, Slider element, Float response);

}
