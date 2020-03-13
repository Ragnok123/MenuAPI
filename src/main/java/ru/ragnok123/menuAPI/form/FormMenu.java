package ru.ragnok123.menuAPI.form;

import cn.nukkit.Player;
import lombok.NonNull;
import ru.ragnok123.menuAPI.form.impl.response.FormGlobalResponse;

public interface FormMenu {
	
	void show(@NonNull Player player);
	
	String getTitle();
	
	void setEmptyResponse(FormGlobalResponse response);
	FormGlobalResponse getEmptyResponse();
}
