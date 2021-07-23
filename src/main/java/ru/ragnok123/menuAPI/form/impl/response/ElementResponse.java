package ru.ragnok123.menuAPI.form.impl.response;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.elements.Element;

public interface ElementResponse<T> {
	
	void onHandle(Player player, Element element, T object);
	
	T getResponse();
}