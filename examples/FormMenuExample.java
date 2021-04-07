import java.awt.Button;

import ru.ragnok123.menuAPI.form.impl.CustomFormMenu;
import ru.ragnok123.menuAPI.form.impl.SimpleFormMenu;

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
			PasswordResponse response = (PasswordResponse) menu.getElement(1);
			PasswordResponse secondResponse = (PasswordResponse) menu.getElement(2);
			if(response.getPassword().equals(secondResponse.getPassword())) {
				player.sendMessage("Passwords matches");
			} else {
				player.sendMessage("Password doesn't match");
			}
		});
		menu.show(player);
	}
	
}
	
class PasswordResponse extends InputResponse() {
	
	private String password;
	
	public void onResponse(Player player, Input input, String pwd) {
		this.password = pwd;
	}
	
	public String getPassword() {
		return this.password;
	}
}
