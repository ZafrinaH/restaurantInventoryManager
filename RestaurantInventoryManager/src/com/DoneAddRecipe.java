package com;

import java.util.ArrayList;

import com.example.restaurantinventorymanager.R;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DoneAddRecipe extends Activity{
	Button okButton;
	TextView addMore;
	EditText quantityEditText;
	int count2 = 0;
	TableLayout tableLayout;
	TableLayout.LayoutParams layoutParams;
	Spinner menuItemsSpinner, ingredientSpinner, typeSpinner;
	String menuItem;
	String[] types = {"kg", "g", "teaspoons", "l", "ml", "tablespoons", "cups", "pieces"};
	static String[] ingredients = null;
	String[] ingredientsAdded = null;
	String[] typesAdded = null;
	static ArrayList<Recipe> orderDetailList = null, orderDetailList2 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe);
        
        okButton = (Button) findViewById(R.id.okButton);
        
        addMore = (TextView) findViewById(R.id.addMoreWordTextView);
        
        quantityEditText = (EditText) findViewById(R.id.quantityEditText);
        
        quantityEditText.setId(0);
        
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        
        layoutParams = new TableLayout.LayoutParams(
        		TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1);
        layoutParams.setMargins(1, 1, 1, 1);
        
        addMore.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		count2++;
        		onClick2();
        	}
        });
        
        okButton.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		onClick3();
        	}
        });
        
        DoneDatabaseControl newEntry = new DoneDatabaseControl(DoneAddRecipe.this);
        
        String[] menuItems = null;
        
        
        try{
			newEntry.open();
			menuItems = newEntry.getAllMenuItems();
			ingredients = newEntry.getIngredientNames();
			ingredientsAdded = new String[ingredients.length];
			typesAdded = new String[ingredients.length];
			orderDetailList = newEntry.getRecipe();
			newEntry.close();
        }catch(Exception e){}
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DoneAddRecipe.this, android.R.layout.simple_list_item_checked, menuItems); 
        
        menuItemsSpinner = (Spinner) findViewById(R.id.menuItemsSpinner);
        menuItemsSpinner.setAdapter(adapter);
        menuItemsSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            	menuItem = menuItemsSpinner.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub                  
            }
        });
        
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(DoneAddRecipe.this, android.R.layout.simple_list_item_checked, ingredients);
        
        ingredientSpinner = (Spinner) findViewById(R.id.ingredientSpinner);
        ingredientSpinner.setAdapter(adapter2);
        ingredientSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            	ingredientsAdded[0] = ingredientSpinner.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub                  
            }
        });
        
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(DoneAddRecipe.this, android.R.layout.simple_list_item_checked, types);
        
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        typeSpinner.setAdapter(adapter3);
        typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            	typesAdded[0] = typeSpinner.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub    
            }
        });
	}
		
	public void onClick2(){
		if(count2>=ingredients.length){
			Dialog d = new Dialog(DoneAddRecipe.this);
			d.setTitle("Sorry!");
		
			TextView tv = new TextView(DoneAddRecipe.this);
			tv.setText("That's all the ingredients we've got!\nAdd new "
					+ "ingredients first to add more here");
		
			d.setContentView(tv);
			d.show();
		}else{
			TableRow tableRow = new TableRow(this);
		
			EditText newQuantityEditText = new EditText(this);
		
			newQuantityEditText.setId(count2);
			newQuantityEditText.setTextColor(Color.BLACK);
			newQuantityEditText.setWidth(quantityEditText.getWidth());
			newQuantityEditText.setHeight(quantityEditText.getHeight());
			newQuantityEditText.setInputType(quantityEditText.getInputType());
			newQuantityEditText.setPadding(10, 10, 10, 10);
			newQuantityEditText.setBackgroundColor((Color.parseColor("#b0e0e6")));
			newQuantityEditText.setGravity(Gravity.CENTER);
			newQuantityEditText.setTextColor(Color.BLACK);
			newQuantityEditText.setFocusable(true);
			newQuantityEditText.setEnabled(true);
			newQuantityEditText.setClickable(true);
			newQuantityEditText.setFocusableInTouchMode(true);
			/*newQuantityEditText.setLayoutParams(new RelativerLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                	LayoutParams.WRAP_CONTENT, 0.25f));*/
		
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(DoneAddRecipe.this, android.R.layout.simple_list_item_checked, ingredients);
        
			final Spinner spinnerIngredients = new Spinner(this);
			spinnerIngredients.setAdapter(adapter);
			spinnerIngredients.setOnItemSelectedListener(new OnItemSelectedListener(){
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					ingredientsAdded[count2] = spinnerIngredients.getSelectedItem().toString();
				}
				public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub                  
				}
			});
        
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(DoneAddRecipe.this, android.R.layout.simple_list_item_checked, types);
        
			final Spinner spinnerTypes = new Spinner(this);
			spinnerTypes.setAdapter(adapter2);
			spinnerTypes.setOnItemSelectedListener(new OnItemSelectedListener(){
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					typesAdded[count2] = spinnerTypes.getSelectedItem().toString();
				}
				public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub                  
				}
			});
        
			tableRow.addView(spinnerIngredients);
			tableRow.addView(newQuantityEditText);
			tableRow.addView(spinnerTypes);
		
			tableLayout.addView(tableRow, layoutParams);
        
			System.out.println("Count2 = " + count2);
		}
	}
	
	@SuppressLint("NewApi") public void onClick3(){
		boolean alreadyadded = false;
		boolean done = false;
		final DoneDatabaseControl newEntry = new DoneDatabaseControl(DoneAddRecipe.this);
		AlertDialog.Builder notification  = new AlertDialog.Builder(this);
		
		for(int j=0; j<orderDetailList.size(); j++){
			if(orderDetailList.get(j).getMenuItem().equals(menuItem)){
				alreadyadded = true;
				break;
			}
		}
				
		try{
			for(int i=0; i<=count2; i++){
				newEntry.open();
				EditText quantityText = (EditText) findViewById(i);
			
				String ingredientQuantity = quantityText.getText().toString();
			
				System.out.println(ingredientQuantity);
			
				if((ingredientQuantity != null && !ingredientQuantity.isEmpty())){
					final String quantity = ingredientQuantity;
					final String ingredient = ingredientsAdded[i];
					final String type = typesAdded[i];
					
					if(alreadyadded){
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Recipe to this menu item has already been added!",
								DoneAddRecipe.this);
				
						notification.setNegativeButton("OK", 
								new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {										
								finish();
									}
			    				});
					
						notification.create().show();
					}else{
						System.out.println("count = " + i);
						newEntry.setRecipe(ingredient, menuItem, quantity, type);
						done = true;
						newEntry.close();
					}
				}else{
					notification = DoneRestaurantName.changeAlertDialogBoxMessage(
							"Sorry you have not entered anything in the quantity text box!", 
							DoneAddRecipe.this);
		
					notification.setPositiveButton("OK", 
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									
								}
	    					});
		
					notification.create().show();
				}
			}
			
			if(done){
				notification = DoneRestaurantName.changeAlertDialogBoxMessage(
						"Recipe will be added!",
						DoneAddRecipe.this);
				
				notification.setNegativeButton("OK", 
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
				});
				
				notification.create().show();
			}
		}catch(Exception e){}
	}
}