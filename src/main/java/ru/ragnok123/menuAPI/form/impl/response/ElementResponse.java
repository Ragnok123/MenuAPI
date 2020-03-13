package ru.ragnok123.menuAPI.form.impl.response;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.elements.Element;

public interface ElementResponse {

	void onHandle(Player player, Element element, Object object);
	
}
