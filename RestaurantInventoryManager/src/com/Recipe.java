package com;

public class Recipe {
	String ingredient, menuItem, quantity, type;
	int position;
	
	public void setPosition(int position){
		this.position = position;
	}
	
	public int getPosition(){
		return position;
	}
		
	public void setIngredient(String ingredient){
		this.ingredient = ingredient;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setMenuItem(String menuItem){
		this.menuItem = menuItem;
	}
	
	public void setQuantity(String quantity){
		this.quantity = quantity;
	}
	
	public String getIngredient(){
		return ingredient;
	}
	
	public String getType(){
		return type;
	}
	
	public String getQuantity(){
		return quantity;
	}
	
	public String getMenuItem(){
		return menuItem;
	}
}
