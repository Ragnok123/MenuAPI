package ru.ragnok123.menuAPI.form.impl.response;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.elements.Button;

public abstract class ButtonResponse {
	
	public abstract void onResponse(Player player, Button button);

}
