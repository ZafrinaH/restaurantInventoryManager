package com;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DoneDbHelper extends SQLiteOpenHelper {
	private static DoneDbHelper instance = null;
	
	private static final String DATABASE_NAME = "restaurantDB25";
	private static final int DATABASE_VERSION = 1;
	
	public static final String DATABASE_RESTAURANTTABLE = "restaurant";
	public static final String KEY_RESTAURANTNAME = "restaurant_name";
	
	public static final String DATABASE_MENUITEMTABLE = "menuItem";
	public static final String KEY_MENUITEMID= "menu_item_id";
	public static final String KEY_MENUITEMNAME = "menu_item_name";
	
	public static final String DATABASE_INGREDIENTTABLE = "ingredient";
	public static final String KEY_INGREDIENTID = "ingredient_id";
	public static final String KEY_INGREDIENTNAME = "ingredient_name";
	public static final String KEY_INGREDIENTQUANTITY = "ingredient_quantity";
	public static final String KEY_INGREDIENT_QUANTITY_TYPE = "ingredient_quantity_type";
	public static final String KEY_INGREDIENTMAXVALUE = "max";
	public static final String KEY_INGREDIENT_MAX_TYPE = "ingredient_max_type";
	public static final String KEY_INGREDIENTMINVALUE = "min";
	public static final String KEY_INGREDIENT_MIN_TYPE = "ingredient_min_type";
	
	public static final String DATABASE_MENUITEMINGREDIENTTABLE = "menuitem_ingredient";
	public static final String KEY_MENUITEM_INGREDIENT_ID = "menuitem_ingredient_id";
	public static final String KEY_INGREDIENT_TYPE = "ingredient_type";
	
	private DoneDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		System.out.println("Inside DbHelper");
		//TODO Auto-generated constructor stub
	}
	
	public static DoneDbHelper getInstance(Context context) {
	      if(instance == null) {
	         instance = new DoneDbHelper(context);
	      }
	      return instance;
	   }
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL( "CREATE TABLE " + DoneDbHelper.DATABASE_RESTAURANTTABLE + " (" + 
					DoneDbHelper.KEY_RESTAURANTNAME + " TEXT PRIMARY KEY);"			
			);
			
			db.execSQL( "CREATE TABLE " + DoneDbHelper.DATABASE_MENUITEMTABLE + " (" + 
					DoneDbHelper.KEY_MENUITEMID + " INTEGER PRIMARY KEY, " + 
					DoneDbHelper.KEY_MENUITEMNAME + " TEXT NOT NULL, UNIQUE (" + 
					DoneDbHelper.KEY_MENUITEMNAME + "));"			
			);
			
			db.execSQL( "CREATE TABLE " + DoneDbHelper.DATABASE_INGREDIENTTABLE + " (" +
					DoneDbHelper.KEY_INGREDIENTID + " INTEGER PRIMARY KEY, " +
					DoneDbHelper.KEY_INGREDIENTNAME + " TEXT NOT NULL, " +  
					DoneDbHelper.KEY_INGREDIENTQUANTITY + " REAL NOT NULL, " +
					DoneDbHelper.KEY_INGREDIENTMAXVALUE + " REAL NOT NULL, " +
					DoneDbHelper.KEY_INGREDIENT_QUANTITY_TYPE + " TEXT NOT NULL, " +
					DoneDbHelper.KEY_INGREDIENT_MIN_TYPE + " TEXT NOT NULL, " +
					DoneDbHelper.KEY_INGREDIENT_MAX_TYPE + " TEXT NOT NULL, " +
					DoneDbHelper.KEY_INGREDIENTMINVALUE + " REAL NOT NULL, UNIQUE (" + 
					DoneDbHelper.KEY_INGREDIENTNAME + "));"		
			);
			
			db.execSQL( "CREATE TABLE " + DoneDbHelper.DATABASE_MENUITEMINGREDIENTTABLE + " (" + 
					DoneDbHelper.KEY_MENUITEM_INGREDIENT_ID + " INTEGER PRIMARY KEY, " + 
					DoneDbHelper.KEY_INGREDIENTQUANTITY + " REAL NOT NULL, " +
					DoneDbHelper.KEY_INGREDIENTNAME + " TEXT NOT NULL, " +
					DoneDbHelper.KEY_INGREDIENT_TYPE + " TEXT NOT NULL, " +
					DoneDbHelper.KEY_MENUITEMNAME + " TEXT NOT NULL, FOREIGN KEY (" +
					DoneDbHelper.KEY_INGREDIENTNAME + ") REFERENCES " +
					DoneDbHelper.DATABASE_INGREDIENTTABLE + "(" +
					DoneDbHelper.KEY_INGREDIENTNAME + "), FOREIGN KEY (" +
					DoneDbHelper.KEY_MENUITEMNAME + ") REFERENCES " +
					DoneDbHelper.DATABASE_MENUITEMTABLE + "(" +
					DoneDbHelper.KEY_MENUITEMNAME + "));"
			);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
			onCreate(db);
		}
	}
