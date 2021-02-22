package ru.ragnok123.menuAPI.form;

import java.util.*;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import lombok.NonNull;
import ru.ragnok123.menuAPI.form.impl.CustomFormMenu;
import ru.ragnok123.menuAPI.form.impl.ModalFormMenu;
import ru.ragnok123.menuAPI.form.impl.SimpleFormMenu;
import ru.ragnok123.menuAPI.form.impl.elements.Button;
import ru.ragnok123.menuAPI.form.impl.elements.Element;
import ru.ragnok123.menuAPI.form.impl.response.DropdownResponse;
import ru.ragnok123.menuAPI.form.impl.response.FormGlobalResponse;
import ru.ragnok123.menuAPI.inventory.InventoryMenu;

public class FormMenuHandler implements Listener{
	
	public static HashMap<String,FormMenu> menus = new HashMap<String,FormMenu>();
	public static HashMap<UUID,FormMenu> pmenus = new HashMap<UUID,FormMenu>();
	
	public static FormMenu getMenuById(@NonNull String id) {
		if(menus.containsKey(id)) {
			return menus.get(id);
		}
		return null;
	}
	
	public static FormMenu getMenuByPlayer(@NonNull UUID uuid) {
		if(pmenus.containsKey(uuid)) {
			return pmenus.get(uuid);
		}
		return null;
	}
	
	public static void registerNewMenu(String id, FormMenu menu) {
		if(!menus.containsKey(id)) {
			menus.put(id,menu);
		}
	}
	
	public static void unregisterMenu(String id) {
		if(menus.containsKey(id)) {
			menus.remove(id);
		}
	}
	
	@EventHandler
	public void onFormResponse(PlayerFormRespondedEvent event) {
		Player player = event.getPlayer();
		int formId = event.getFormID();
		FormWindow window = event.getWindow();
		FormResponse response = window.getResponse();
		if(getMenuByPlayer(player.getUniqueId()) != null) {
			FormMenu menu = getMenuByPlayer(player.getUniqueId());
			if(menu.getId() == formId) {
				pmenus.remove(player.getUniqueId());
				if(window instanceof FormWindowSimple) {
					SimpleFormMenu simple = (SimpleFormMenu) menu;
					if(response == null) {
						if(simple.getEmptyResponse() != null) {
							simple.getEmptyResponse().handle(player);
						}
					} else {
						Button button = simple.getButton(((FormResponseSimple)response).getClickedButtonId());
						if(simple.hasResponse(button)) {
							simple.getResponse(button).onResponse(player,button);
						}
					}
				} else if(window instanceof FormWindowModal) {
					ModalFormMenu modal = (ModalFormMenu)menu;
						if(response == null) {
							if(modal.getEmptyResponse() != null) {
								modal.getEmptyResponse().handle(player);
							}
						} else {
							Button button = modal.getButton(((FormResponseModal)response).getClickedButtonId());
							if(modal.hasResponse(button)) {
								modal.getResponse(button).onResponse(player,button);
							}
						}
				} else if(window instanceof FormWindowCustom) {
					CustomFormMenu custom = (CustomFormMenu)menu;
					if(response == null) {
						if(custom.getEmptyResponse() != null) {
							custom.getEmptyResponse().handle(player);
						}
					} else {
						FormResponseCustom customResponse = (FormResponseCustom)response;
						for(int i : customResponse.getResponses().keySet()) {
							Element element = custom.getElement(i);
							if(custom.hasResponse(element)) {
								custom.getResponse(element).onHandle(player,element,customResponse.getResponse(i));
							}
						}
						custom.getCallbackResponse().accept(player);
					}
				}
			}
		}
	}
	
	
}
