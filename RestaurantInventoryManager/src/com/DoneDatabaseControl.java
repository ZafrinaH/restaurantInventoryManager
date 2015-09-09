package com;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

public class DoneDatabaseControl {
	private DoneDbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	public DoneDatabaseControl(Context c) {
		ourContext = c;
	}
	
	public DoneDatabaseControl open() throws SQLException{
		System.out.println("Inside DatabaseControl");
		ourHelper = DoneDbHelper.getInstance(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
				
		return this;
	}
	
	public void close() {
		ourHelper.close();
	}
	
	public long setRestaurantName() {
		ContentValues cv = new ContentValues();
		
		cv.put(DoneDbHelper.KEY_RESTAURANTNAME, "");
		
		return ourDatabase.insert(DoneDbHelper.DATABASE_RESTAURANTTABLE, null, cv);
	}
	
	public long setMenuItem(String name) {
		ContentValues cv = new ContentValues();
		cv.put(DoneDbHelper.KEY_MENUITEMNAME, name);
		return ourDatabase.insert(DoneDbHelper.DATABASE_MENUITEMTABLE, null, cv);
	}
	
	public long setIngredient(String ingredient, String quantity, String min, String max, 
			String qtyType, String minType, String maxType) {
		ContentValues cv = new ContentValues();
		cv.put(DoneDbHelper.KEY_INGREDIENTNAME, ingredient);
		cv.put(DoneDbHelper.KEY_INGREDIENTQUANTITY, quantity);
		cv.put(DoneDbHelper.KEY_INGREDIENTMINVALUE, min);
		cv.put(DoneDbHelper.KEY_INGREDIENTMAXVALUE, max);
		cv.put(DoneDbHelper.KEY_INGREDIENT_QUANTITY_TYPE, qtyType);
		cv.put(DoneDbHelper.KEY_INGREDIENT_MIN_TYPE, minType);
		cv.put(DoneDbHelper.KEY_INGREDIENT_MAX_TYPE, maxType);
		
		return ourDatabase.insert(DoneDbHelper.DATABASE_INGREDIENTTABLE, null, cv);
	}
	
	public long setRecipe(String ingredient, String menuItem, String quantity, String type) {
		System.out.print("Here inside ");
		boolean wasItThere = false;
		int position=0;
		double qty=0.0;
		ArrayList<Recipe> getrecipe = getRecipe();
		
		for(int i=0; i<getrecipe.size(); i++){
			if(getrecipe.get(i).getMenuItem().equals(menuItem)){
				if(getrecipe.get(i).getIngredient().equals(ingredient)){
					qty += Double.parseDouble(getrecipe.get(i).getQuantity());
					qty += Double.parseDouble(quantity);
					wasItThere = true;
					position = getrecipe.get(i).getPosition();
					break;
				}
			}
		}
		
		if(wasItThere){
			System.out.println("qty = " + qty);
			updateRecipe(position, menuItem, ingredient, 
					String.valueOf(qty), type);
		}else{
			System.out.println("set up recipe");
			ContentValues cv = new ContentValues();
			cv.put(DoneDbHelper.KEY_MENUITEMNAME, menuItem);
			cv.put(DoneDbHelper.KEY_INGREDIENTNAME, ingredient);
			cv.put(DoneDbHelper.KEY_INGREDIENTQUANTITY, quantity);
			cv.put(DoneDbHelper.KEY_INGREDIENT_TYPE, type);
		
			return ourDatabase.insert(DoneDbHelper.DATABASE_MENUITEMINGREDIENTTABLE, null, cv);
		}
		return position;
	}
	
	public String getRestaurantName() {
		String[] restaurantName = new String[]{DoneDbHelper.KEY_RESTAURANTNAME};
		Cursor c = ourDatabase.query(DoneDbHelper.DATABASE_RESTAURANTTABLE, restaurantName, null, null, null, null, null);
		String result = "";
		
		int iName = c.getColumnIndex(DoneDbHelper.KEY_RESTAURANTNAME);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iName);
		}
		System.out.println("inside getRestaurantName " + result);
		return result;
	}
	
	public String[] getAllMenuItems() {
		int i=0;
		
		String[] menuItems2 = new String[]{DoneDbHelper.KEY_MENUITEMNAME};
		Cursor c2 = ourDatabase.query(DoneDbHelper.DATABASE_MENUITEMTABLE, menuItems2, null, null, null, null, null);
		
		String[] menuItems = new String[c2.getCount()];
		
		int iName = c2.getColumnIndex(DoneDbHelper.KEY_MENUITEMNAME);
		
		for(c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()) {
			menuItems[i] = c2.getString(iName);
			i++;
		}
		
		return menuItems;
	}
	
	public int getMenuItemID(String menuItemName){
		int KEY_MENUITEMID = 0;
		String[] columns = new String[]{ DoneDbHelper.KEY_MENUITEMID, 
				DoneDbHelper.KEY_MENUITEMNAME};
		Cursor c = ourDatabase.query(DoneDbHelper.DATABASE_MENUITEMTABLE, 
				columns, DoneDbHelper.KEY_MENUITEMNAME + "='" + menuItemName + "'", null, null, null, null);
		
		if(c != null){
			c.moveToFirst();
			
			KEY_MENUITEMID = c.getInt(1);
			return KEY_MENUITEMID;
		}
		System.out.print("Here outside ");
		

		return KEY_MENUITEMID;
	}
	
	public int getIngredientID(String ingredientName){
		int KEY_INGREDIENTID = 0;
		String[] columns = new String[]{ DoneDbHelper.KEY_INGREDIENTID, DoneDbHelper.KEY_INGREDIENTNAME, 
				DoneDbHelper.KEY_INGREDIENTMAXVALUE, DoneDbHelper.KEY_INGREDIENTMINVALUE, 
				DoneDbHelper.KEY_INGREDIENTQUANTITY};
		
		Cursor c = ourDatabase.query(DoneDbHelper.DATABASE_INGREDIENTTABLE, 
				columns, DoneDbHelper.KEY_INGREDIENTNAME + "='" + ingredientName + "'", null, null, null, null);
		
		if(c != null){
			c.moveToFirst();
			
			KEY_INGREDIENTID = c.getInt(1);
			return KEY_INGREDIENTID;
		}
		return KEY_INGREDIENTID;
	}
	
	public ArrayList<Recipe> getRecipe(){
		String[] columns = new String[]{
				DoneDbHelper.KEY_MENUITEM_INGREDIENT_ID,
				DoneDbHelper.KEY_MENUITEMNAME, 
				DoneDbHelper.KEY_INGREDIENTNAME,
				DoneDbHelper.KEY_INGREDIENTQUANTITY,
				DoneDbHelper.KEY_INGREDIENT_TYPE};
		
		Cursor c = ourDatabase.query(DoneDbHelper.DATABASE_MENUITEMINGREDIENTTABLE, columns, null, null, null, null, null);
				
		ArrayList<Recipe> orderDetailList = new ArrayList<Recipe>();
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Recipe recipe = new Recipe();
			recipe.setPosition(c.getInt(c.getColumnIndexOrThrow(DoneDbHelper.KEY_MENUITEM_INGREDIENT_ID)));
			recipe.setIngredient(c.getString(c.getColumnIndexOrThrow(DoneDbHelper.KEY_INGREDIENTNAME)));
			recipe.setMenuItem(c.getString(c.getColumnIndexOrThrow(DoneDbHelper.KEY_MENUITEMNAME)));
			recipe.setType(c.getString(c.getColumnIndexOrThrow(DoneDbHelper.KEY_INGREDIENT_TYPE)));
			recipe.setQuantity(c.getString(c.getColumnIndexOrThrow(DoneDbHelper.KEY_INGREDIENTQUANTITY)));
			orderDetailList.add(recipe);
		}
				
		return orderDetailList;
	}
	
	public String[] getIngredientNames() {
		int i=0;
		
		String[] ingredientsArray = new String[]{DoneDbHelper.KEY_INGREDIENTNAME};
		Cursor c2 = ourDatabase.query(DoneDbHelper.DATABASE_INGREDIENTTABLE, ingredientsArray, null, null, null, null, null);
		
		String[] ingredients = new String[c2.getCount()];
		
		int iName = c2.getColumnIndex(DoneDbHelper.KEY_INGREDIENTNAME);
		
		for(c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()) {
			ingredients[i] = c2.getString(iName);
			i++;
		}
		
		return ingredients;
	}
	
	public ArrayList<DoneInventory> getAllIngredientsDetails() {
		String[] allIngredientsDetails = new String[]{
				DoneDbHelper.KEY_INGREDIENTID, 
				DoneDbHelper.KEY_INGREDIENTNAME, 
				DoneDbHelper.KEY_INGREDIENTQUANTITY, 
				DoneDbHelper.KEY_INGREDIENTMINVALUE, 
				DoneDbHelper.KEY_INGREDIENTMAXVALUE,
				DoneDbHelper.KEY_INGREDIENT_QUANTITY_TYPE,
				DoneDbHelper.KEY_INGREDIENT_MIN_TYPE,
				DoneDbHelper.KEY_INGREDIENT_MAX_TYPE};
		
		Cursor c2 = ourDatabase.query(DoneDbHelper.DATABASE_INGREDIENTTABLE, allIngredientsDetails, null, null, null, null, null);
		
		ArrayList<DoneInventory> orderDetailList = new ArrayList<DoneInventory>();
				
		for(c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()) {
			DoneInventory orderDetail = new DoneInventory();
			orderDetail.setIngredient(c2.getString(c2.getColumnIndexOrThrow(DoneDbHelper.KEY_INGREDIENTNAME)));
			orderDetail.setQuantity(c2.getString(c2.getColumnIndexOrThrow(DoneDbHelper.KEY_INGREDIENTQUANTITY)));
			orderDetail.setMin(c2.getString(c2.getColumnIndexOrThrow(DoneDbHelper.KEY_INGREDIENTMINVALUE)));
			orderDetail.setMax(c2.getString(c2.getColumnIndexOrThrow(DoneDbHelper.KEY_INGREDIENTMAXVALUE)));
			orderDetail.setTypeQty(c2.getString(c2.getColumnIndexOrThrow(DoneDbHelper.KEY_INGREDIENT_QUANTITY_TYPE)));
			orderDetail.setTypeMin(c2.getString(c2.getColumnIndexOrThrow(DoneDbHelper.KEY_INGREDIENT_MIN_TYPE)));
			orderDetail.setTypeMax(c2.getString(c2.getColumnIndexOrThrow(DoneDbHelper.KEY_INGREDIENT_MAX_TYPE)));
			orderDetailList.add(orderDetail);
		}
        return orderDetailList;
	}
	
	public long updateRestaurantName(String newName, String oldName) {
		System.out.println("updateRestaurantName");
		
		ContentValues cv = new ContentValues();
		cv.put(DoneDbHelper.KEY_RESTAURANTNAME, newName);
		
		return ourDatabase.update(DoneDbHelper.DATABASE_RESTAURANTTABLE, cv, DoneDbHelper.KEY_RESTAURANTNAME + " ='" + oldName + "'", null);
	}
	
	public long updateMenuItem(String newName, String oldName) {
		ContentValues cv = new ContentValues();
		
		cv.put(DoneDbHelper.KEY_MENUITEMNAME, newName);
		 		 
		return ourDatabase.update(DoneDbHelper.DATABASE_MENUITEMTABLE, 
				 cv, DoneDbHelper.KEY_MENUITEMNAME + " ='" + oldName + "'", null);
	}
	
	public long updateIngredient(String newName, String oldName, String quantity, 
			String min, String max, String qtyType, String minType, String maxType) {		
		ContentValues cv = new ContentValues();
		cv.put(DoneDbHelper.KEY_INGREDIENTNAME, newName);
		cv.put(DoneDbHelper.KEY_INGREDIENTQUANTITY, quantity);
		cv.put(DoneDbHelper.KEY_INGREDIENTMINVALUE, min);
		cv.put(DoneDbHelper.KEY_INGREDIENTMAXVALUE, max);
		cv.put(DoneDbHelper.KEY_INGREDIENT_QUANTITY_TYPE, qtyType);
		cv.put(DoneDbHelper.KEY_INGREDIENT_MIN_TYPE, minType);
		cv.put(DoneDbHelper.KEY_INGREDIENT_MAX_TYPE, maxType);
		
		return ourDatabase.update(DoneDbHelper.DATABASE_INGREDIENTTABLE, cv, DoneDbHelper.KEY_INGREDIENTNAME + " ='" + oldName + "'", null);
			
	}
	
	public long updateRecipe(int id, String menuName, String ingredientName, String newQuantity, String qtyType) {		
		ContentValues cv = new ContentValues();
		cv.put(DoneDbHelper.KEY_MENUITEM_INGREDIENT_ID, id);
		cv.put(DoneDbHelper.KEY_INGREDIENTNAME, ingredientName);
		cv.put(DoneDbHelper.KEY_INGREDIENTQUANTITY, newQuantity);
		cv.put(DoneDbHelper.KEY_MENUITEMNAME, menuName);
		cv.put(DoneDbHelper.KEY_INGREDIENT_TYPE, qtyType);
		
		return ourDatabase.update(DoneDbHelper.DATABASE_MENUITEMINGREDIENTTABLE, cv, DoneDbHelper.KEY_MENUITEM_INGREDIENT_ID + " =" + id, null);
			
	}
	
	public long deleteMenuItem(String name) {		 		 
		return ourDatabase.delete(DoneDbHelper.DATABASE_MENUITEMTABLE, 
				DoneDbHelper.KEY_MENUITEMNAME + " ='" + name + "'", null);
	}
	
	public long deleteIngredient(String name) {		 		 
		return ourDatabase.delete(DoneDbHelper.DATABASE_INGREDIENTTABLE, 
				DoneDbHelper.KEY_INGREDIENTNAME + " ='" + name + "'", null);
	}
	
	
	
	
		
	public int getMenuItemsCount(){
		Cursor c = ourDatabase.rawQuery("select " +  DoneDbHelper.KEY_MENUITEMNAME + " from " + DoneDbHelper.DATABASE_MENUITEMTABLE, null);
		int iID = c.getColumnIndex(DoneDbHelper.KEY_MENUITEMNAME);
		int count = 0;
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			count = c.getInt(iID);
		}
		return count;
	}
	
	
	
	public String getIngredients() {
		// TODO Auto-generated method stub		
		String[] ingredients = 
				new String[]{DoneDbHelper.KEY_INGREDIENTNAME, DoneDbHelper.KEY_INGREDIENTMAXVALUE, 
				DoneDbHelper.KEY_INGREDIENTMINVALUE};
		Cursor c = ourDatabase.query(DoneDbHelper.DATABASE_INGREDIENTTABLE, ingredients, 
				null, null, null, null, null);
		String result = "";
		
		int iName = c.getColumnIndex(DoneDbHelper.KEY_INGREDIENTNAME);
		int iMax = c.getColumnIndex(DoneDbHelper.KEY_INGREDIENTMAXVALUE);
		int iMin = c.getColumnIndex(DoneDbHelper.KEY_INGREDIENTMINVALUE);
		
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iName) + " " + c.getFloat(iMax) + 
					" " + c.getFloat(iMin) + "\n";
		}
		return result;
	}
	
	public String[] getName(){
		Cursor c = ourDatabase.rawQuery("select " + DoneDbHelper.KEY_MENUITEMNAME + ", " +  DoneDbHelper.KEY_INGREDIENTID + ", " + DoneDbHelper.KEY_INGREDIENTNAME + ", " + DoneDbHelper.KEY_INGREDIENTQUANTITY + " from " + DoneDbHelper.DATABASE_INGREDIENTTABLE, null);
		
		int iID = c.getColumnIndex(DoneDbHelper.KEY_INGREDIENTID);
		
		String[] array = new String[iID];
		int i=0;
		
		int iName = c.getColumnIndex(DoneDbHelper.KEY_MENUITEMNAME);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			array[i] = c.getString(iName);
			i++;
		}
		return array;
	}
		
	public ArrayList<DoneInventory> getIngredientQuantity(String menuItem, Context c) {
		// TODO Auto-generated method stub	
		
		ArrayList<DoneInventory> orderDetailList = new ArrayList<DoneInventory>();
		Dialog d = new Dialog(c);
		
		Cursor mCursor = ourDatabase.query(true, DoneDbHelper.DATABASE_INGREDIENTTABLE, new String[] {
				DoneDbHelper.KEY_INGREDIENTID,
				DoneDbHelper.KEY_INGREDIENTNAME,
				DoneDbHelper.KEY_INGREDIENTQUANTITY,
				DoneDbHelper.KEY_MENUITEMNAME,},
                DoneDbHelper.KEY_MENUITEMNAME + "=?", 
                new String[] {menuItem},
                null, null, null , null);
		
		
		
		if (mCursor.moveToFirst()) {
			do {
                DoneInventory orderDetail = new DoneInventory(); 
                orderDetail.setIngredient(mCursor.getString(mCursor.getColumnIndexOrThrow(DoneDbHelper.KEY_INGREDIENTNAME)));
                orderDetail.setQuantity(mCursor.getString(mCursor.getColumnIndexOrThrow(DoneDbHelper.KEY_INGREDIENTQUANTITY)));
        		d.setTitle(mCursor.getString(mCursor.getColumnIndexOrThrow(DoneDbHelper.KEY_INGREDIENTQUANTITY)));
        		d.show();
                orderDetailList.add(orderDetail);
            } while (mCursor.moveToNext());
		}
		
		if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return orderDetailList;
	}
		
	public long updateQuantity(double value, String ingredientName){
		ContentValues updatedValues = new ContentValues();
		
		 updatedValues.put(DoneDbHelper.KEY_INGREDIENTQUANTITY, value);
		 
		 System.out.println("inside update");
		 
		 return ourDatabase.update(DoneDbHelper.DATABASE_INGREDIENTTABLE, 
				 updatedValues, DoneDbHelper.KEY_INGREDIENTNAME + " ='" + ingredientName + "'", null);
	}
	
}
