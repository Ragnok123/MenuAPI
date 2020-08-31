package ru.ragnok123.menuAPI.form.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.window.FormWindowCustom;
import ru.ragnok123.menuAPI.form.FormMenu;
import ru.ragnok123.menuAPI.form.FormMenuHandler;
import ru.ragnok123.menuAPI.form.impl.elements.*;
import ru.ragnok123.menuAPI.form.impl.response.*;

public class CustomFormMenu implements FormMenu {
	
	private String title = "";
	private ArrayList<Element> elements = new ArrayList<Element>();
	private HashMap<Element, ElementResponse> responses = new HashMap<Element, ElementResponse>();
	
	public CustomFormMenu(String title) {
		this.title = title;
	}
	
	public void addDropdown(String text, List<String> options, DropdownResponse response) {
		Dropdown dropdown = new Dropdown(text,options);
		dropdown.setMenu(this);
		this.elements.add(dropdown);
		if(response != null) {
			this.responses.put(dropdown,response);
		}
	}
	
	public void addInput(String text, InputResponse response) {
		addInput(text,null,response);
	}
	
	public void addInput(String text, String def, InputResponse response) {
		Input input = new Input(text,def);
		input.setMenu(this);
		this.elements.add(input);
		if(response != null) {
			this.responses.put(input,response);
		}
	}
	
	public void addLabel(String text) {
		this.elements.add(new Label(text));
	}
	
	public void addSlider(String text, int min, int max, SliderResponse response) {
		Slider slider = new Slider(text,min,max);
		slider.setMenu(this);
		this.elements.add(slider);
		if(response != null) {
			this.responses.put(slider,response);
		}
	}
	
	public void addStepslider(String text, List<String> options, StepsliderResponse response) {
		Stepslider step = new Stepslider(text,options);
		step.setMenu(this);
		this.elements.add(step);
		if(response != null) {
			this.responses.put(step,response);
		}
	}
	
	public void addToggle(String text, ToggleResponse response) {
		Toggle toggle = new Toggle(text);
		toggle.setMenu(this);
		this.elements.add(toggle);
		if(response != null) {
			this.responses.put(toggle,response);
		}
	}
	
	public Element getElement(int id) {
		return this.elements.get(id);
	}
	
	public boolean hasResponse(Element element) {
		return this.responses.containsKey(element);
	}
	
	public ElementResponse getResponse(Element element) {
		return this.responses.get(element);
	}
	
	
	private int num = 0;
	public Integer getId() {
		return this.num;
	}
	
	@Override
	public void show(Player player) {
		FormMenuHandler.pmenus.put(player.getUniqueId(),this);
		FormWindowCustom custom = new FormWindowCustom(getTitle());
		for(Element element : this.elements) {
			custom.addElement(transformToElement(element));
		}
		Random rand = new Random();
		num = rand.nextInt(1000000000);
		player.showFormWindow(custom,num);
	}
	
	public cn.nukkit.form.element.Element transformToElement(Element element){
		if(element instanceof Label) {
			return new ElementLabel(((Label)element).getText());
		} else if(element instanceof Dropdown) {
			Dropdown dropdown = (Dropdown)element;
			return new ElementDropdown(dropdown.getText(), dropdown.getOptions());
		} else if(element instanceof Input) {
			Input input = (Input)element;
			if(input.hasDefault()) {
				return new ElementInput(input.getText(),input.getDefaultText());
			} else {
				return new ElementInput(input.getText());
			}
		} else if(element instanceof Slider) {
			Slider slider = (Slider)element;
			return new ElementSlider(slider.getText(),slider.getMin(),slider.getMax());
		} else if(element instanceof Stepslider) {
			Stepslider step = (Stepslider)element;
			return new ElementStepSlider(step.getText(),step.getSteps());
		} else if(element instanceof Toggle) {
			Toggle toggle = (Toggle)element;
			return new ElementToggle(toggle.getText());
		}
		return null;
	}

	@Override
	public String getTitle() {
		return this.title;
	}


	private FormGlobalResponse empty = null;
	@Override
	public void setEmptyResponse(FormGlobalResponse response) {
		empty = response;
		
	}
	@Override
	public FormGlobalResponse getEmptyResponse() {
		return empty;
	}

}
