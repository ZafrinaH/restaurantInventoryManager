package com;

public class DoneInventory {
	String ingredient, menuItem, quantity, min, max, typeQty, typeMin, typeMax;
	
	public void setIngredient(String ingredient){
		this.ingredient = ingredient;
	}
	
	public void setMenuItem(String menuItem){
		this.menuItem = menuItem;
	}
	
	public void setQuantity(String quantity){
		this.quantity = quantity;
	}
	
	public void setTypeQty(String typeQty){
		this.typeQty = typeQty;
	}
	
	public void setMin(String min){
		this.min = min;
	}
	
	public void setTypeMin(String typeMin){
		this.typeMin = typeMin;
	}
	
	public void setMax(String max){
		this.max = max;
	}
	
	public void setTypeMax(String typeMax){
		this.typeMax = typeMax;
	}
	
	public String getIngredient(){
		return ingredient;
	}
	
	public String getMenuItem(){
		return menuItem;
	}
	
	public String getQuantity(){
		return quantity;
	}
	
	public String getTypeQty(){
		return typeQty;
	}
	
	
	public String getMin(){
		return min;
	}
	
	public String getTypeMin(){
		return typeMin;
	}
	
	
	public String getMax(){
		return max;
	}
	
	public String getTypeMax(){
		return typeMax;
	}
	
}
