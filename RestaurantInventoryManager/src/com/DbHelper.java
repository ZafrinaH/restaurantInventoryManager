package com;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	private static DbHelper instance = null;
	private SQLiteDatabase ourDatabase;
	
	private static final String DATABASE_NAME = "restaurantDB";
	private static final int DATABASE_VERSION = 1;
	
	public static final String KEY_RESTAURANTNAME = "restaurant_name";
	public static final String KEY_MENUITEMNAME = "menu_item_name";
	public static final String KEY_INGREDIENTNAME = "ingredient_name";
	public static final String KEY_INGREDIENTMAXVALUE = "max";
	public static final String KEY_INGREDIENTMINVALUE = "min";
	
	public static final String DATABASE_RESTAURANTTABLE = "restaurant";
	public static final String DATABASE_MENUITEMTABLE = "menuItem";
	public static final String DATABASE_INGREDIENTTABLE = "ingredient";
			
	private DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//TODO Auto-generated constructor stub
	}
	
	public static DbHelper getInstance(Context context) {
	      if(instance == null) {
	         instance = new DbHelper(context);
	      }
	      return instance;
	   }
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL( "CREATE TABLE " + DATABASE_RESTAURANTTABLE + " (" + 
					KEY_RESTAURANTNAME + " TEXT PRIMARY KEY);"			
			);
			
			db.execSQL( "CREATE TABLE " + DATABASE_MENUITEMTABLE + " (" + 
					KEY_MENUITEMNAME + " TEXT PRIMARY KEY);"			
			);
			
			db.execSQL( "CREATE TABLE " + DATABASE_INGREDIENTTABLE + " (" + 
					KEY_INGREDIENTNAME + " TEXT PRIMARY KEY, " +  
					KEY_INGREDIENTMAXVALUE + " REAL NOT NULL, " + 
					KEY_INGREDIENTMINVALUE + " REAL NOT NULL, " + 
					KEY_MENUITEMNAME + " TEXT NOT NULL, FOREIGN KEY" + 
					"(" + KEY_MENUITEMNAME + ") REFERENCES " + 
					DATABASE_MENUITEMTABLE + "(" + KEY_MENUITEMNAME + 
					"));"			
			);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
			onCreate(db);
		}
	}
