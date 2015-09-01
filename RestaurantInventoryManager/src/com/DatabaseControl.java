package com;

import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseControl {
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	public DatabaseControl(Context c) {
		ourContext = c;
	}
	
	public DatabaseControl open() throws SQLException{
		ourHelper = DbHelper.getInstance(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		ourHelper.close();
	}
	
	public long setRestaurant(String name) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(DbHelper.KEY_RESTAURANTNAME, name);
		return ourDatabase.insert(DbHelper.DATABASE_RESTAURANTTABLE, null, cv);
	}
	
	public long setMenuItem(String name) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(DbHelper.KEY_MENUITEMNAME, name);
		return ourDatabase.insert(DbHelper.DATABASE_MENUITEMTABLE, null, cv);
	}
	
	public long setIngredient(String ingredient, float min, float max, String menuItem) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(DbHelper.KEY_INGREDIENTNAME, ingredient);
		cv.put(DbHelper.KEY_INGREDIENTMINVALUE, min);
		cv.put(DbHelper.KEY_INGREDIENTMAXVALUE, max);
		cv.put(DbHelper.KEY_MENUITEMNAME, menuItem);
		return ourDatabase.insert(DbHelper.DATABASE_INGREDIENTTABLE, null, cv);
	}
	
	public String getRestaurantName() {
		// TODO Auto-generated method stub
		String[] restaurantName = new String[]{DbHelper.KEY_RESTAURANTNAME};
		Cursor c = ourDatabase.query(DbHelper.DATABASE_RESTAURANTTABLE, restaurantName, null, null, null, null, null);
		String result = "";
		
		int iName = c.getColumnIndex(DbHelper.KEY_RESTAURANTNAME);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iName) + "\n";
		}
		
		return result;
	}
	
	public String getMenuItems() {
		// TODO Auto-generated method stub
		String[] menuItems = new String[]{DbHelper.KEY_MENUITEMNAME};
		Cursor c = ourDatabase.query(DbHelper.DATABASE_MENUITEMTABLE, menuItems, null, null, null, null, null);
		String result = "";
		
		int iName = c.getColumnIndex(DbHelper.KEY_MENUITEMNAME);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iName) + "\n";
		}
		return result;
	}
	
	public String getIngredients() {
		// TODO Auto-generated method stub		
		String[] ingredients = 
				new String[]{DbHelper.KEY_INGREDIENTNAME, DbHelper.KEY_INGREDIENTMAXVALUE, 
				DbHelper.KEY_INGREDIENTMINVALUE};
		Cursor c = ourDatabase.query(DbHelper.DATABASE_INGREDIENTTABLE, ingredients, 
				null, null, null, null, null);
		String result = "";
		
		int iName = c.getColumnIndex(DbHelper.KEY_INGREDIENTNAME);
		int iMax = c.getColumnIndex(DbHelper.KEY_INGREDIENTMAXVALUE);
		int iMin = c.getColumnIndex(DbHelper.KEY_INGREDIENTMINVALUE);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iName) + " " + c.getFloat(iMax) + 
					" " + c.getFloat(iMin) + "\n";
		}
		return result;
	}
}
