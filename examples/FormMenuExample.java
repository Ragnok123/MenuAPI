import java.awt.Button;

import ru.ragnok123.menuAPI.form.impl.CustomFormMenu;
import ru.ragnok123.menuAPI.form.impl.SimpleFormMenu;
import ru.ragnok123.menuAPI.form.impl.response.InputResponse;

public class FormMenuExample {
	
	public void sendFormMenu(Player player) {
		SimpleFormMenu menu = new SimpleFormMenu("TEST MENU", "It is working");
		menu.addButton("First button", new ButtonResponse() {
			public void onClick(Player player, Button button) {
				player.sendMessage("Clicked button " + button.getText());
			}
		});
		menu.show(player);
	}
	
	public void sendCustomMenu(Player player) {
		CustomFormMenu menu = new CustomFormMenu("CUSTOM MENU");
		menu.addToggle("Remember me", new ToggleResponse() {
			public void onResponse(Player player, Toggle toggle, Boolean response) {
				if(response) {
					player.sendMessage("Now I'm remembering you");
				} else {
					player.sendMessage("Disabled auto remembering");
				}
			}
		});
		menu.addInput("Password", new PasswordResponse());
		menu.addInput("Confirm password", new PasswordResponse());
		menu.setCallbackResponse(p -> {
			InputResponse response = (InputResponse) menu.getResponse(menu.getElement(0));
			InputResponse secondResponse = (InputResponse) menu.getResponse(menu.getElement(1));
			if(response.getResponse().equals(secondResponse.getResponse())) {
				player.sendMessage("Passwords matches");
			} else {
				player.sendMessage("Password doesn't match");
			}
		});
		menu.show(player);
	}
	
}
	

