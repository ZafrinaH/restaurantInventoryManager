package com;

import java.util.ArrayList;

import com.example.restaurantinventorymanager.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DoneViewRecipe extends Activity{
	TableLayout tableLayout;
	
	static String[] paths = null;
	static ArrayList<Recipe> orderDetailList = null;
	
	Spinner spinner;
	TextView ingredientTextView, quanitityTextView, typeTextView;
	TextView ingredientsColumnTextView, quantityColumnTextView, typeColumnTextView;
	TableRow tableRow1;
	int count = 0;
	String menuItem;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recipe);
        
        Button okButton = (Button) findViewById(R.id.okButton);
        
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        
        ingredientsColumnTextView = (TextView) findViewById(R.id.ingredientsColumnTextView);
        quantityColumnTextView = (TextView) findViewById(R.id.quantityColumnTextView);
        typeColumnTextView = (TextView) findViewById(R.id.typeColumnTextView);
                
        ingredientTextView = (TextView) findViewById(R.id.ingredientTextView);
		quanitityTextView = (TextView) findViewById(R.id.quantityTextView);
		typeTextView = (TextView) findViewById(R.id.typeTextView);
		
		ingredientTextView.setVisibility(View.INVISIBLE);
		quanitityTextView.setVisibility(View.INVISIBLE);
		typeTextView.setVisibility(View.INVISIBLE);
        
        DoneDatabaseControl newEntry = new DoneDatabaseControl(DoneViewRecipe.this);
        
        try{
			newEntry.open();
			paths = newEntry.getAllMenuItems();
			orderDetailList = newEntry.getRecipe();
			newEntry.close();
        }catch(Exception e){}
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DoneViewRecipe.this, android.R.layout.simple_list_item_checked, paths);
        
        spinner = (Spinner) findViewById(R.id.menuItemsSpinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				menuItem = spinner.getSelectedItem().toString();
				for(int i=0; i<orderDetailList.size(); i++){
					if(orderDetailList.get(i).getMenuItem().equals(menuItem)){
						count++;
					}
				}
				loadRecipe();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        okButton.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		finish();
        	}
        });
	}
	
	public void loadRecipe(){
		if(orderDetailList.size()==0){
			AlertDialog.Builder notification  = new AlertDialog.Builder(DoneViewRecipe.this);
			notification = DoneRestaurantName.changeAlertDialogBoxMessage(
					"Sorry you have not entered any recipe as yet!", 
					DoneViewRecipe.this);
		
			notification.setPositiveButton("OK", 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
	    			});
		
			notification.create().show();
		}else{
			tableLayout.removeAllViews();
			for(int i=0; i<orderDetailList.size(); i++){
				if(orderDetailList.get(i).getMenuItem().equals(spinner.getSelectedItem().toString())){
					String ingredient = orderDetailList.get(i).getIngredient();
					String quantity = orderDetailList.get(i).getQuantity();
					String type = orderDetailList.get(i).getType();
					
					
							TableRow tablerow = new TableRow(DoneViewRecipe.this);
							TextView tv1 = new TextView(DoneViewRecipe.this);
							TextView tv2 = new TextView(DoneViewRecipe.this);
							TextView tv3 = new TextView(DoneViewRecipe.this);
							
							tv1.setWidth(ingredientsColumnTextView.getWidth());
							tv1.setHeight(ingredientsColumnTextView.getHeight());
							
							tv2.setWidth(quantityColumnTextView.getWidth());
							tv2.setHeight(quantityColumnTextView.getHeight());
							
							tv3.setWidth(typeColumnTextView.getWidth());
							tv3.setHeight(typeColumnTextView.getHeight());
							
							tv1.setTextColor(Color.BLACK);
							tv1.setGravity(Gravity.CENTER);
							tv1.setBackgroundColor(Color.parseColor("#b0e0e6"));
							tv1.setText(ingredient);
							
							tv2.setTextColor(Color.BLACK);
							tv2.setGravity(Gravity.CENTER);
							tv2.setBackgroundColor(Color.parseColor("#b0e0e6"));
							tv2.setText(quantity);
							
							tv3.setTextColor(Color.BLACK);
							tv3.setGravity(Gravity.CENTER);
							tv3.setBackgroundColor(Color.parseColor("#b0e0e6"));
							tv3.setText(type);
							
							tablerow.addView(tv1);
							tablerow.addView(tv2);
							tablerow.addView(tv3);
							tableLayout.addView(tablerow);
						
					
					/*tableRow.addView(ingredientTextView);
					tableRow.addView(quanitityTextView);
					tableRow.addView(typeTextView);
					tableLayout.addView(tableRow);*/	
				}
			}
		}
	}
}