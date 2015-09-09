package com;

import java.util.ArrayList;

import com.example.restaurantinventorymanager.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class DoneMenuIngredient extends Activity {	
	RadioGroup radioGroup;
	EditText ingredientEditText, quantityEditText, minEditText, maxEditText, typeEditText;
	TextView quantityTypeTextView, minTypeTextView, maxTypeTextView;
	Spinner spinner, spinner2, spinner3, spinner4;
	String ingredientName = null;
	static ArrayList<DoneInventory> orderDetailList = null;
	static String[] paths = null;
	static String[] types = {"kg", "g", "teaspoons", "l", "ml", "tablespoons", "cups", "pieces"};
	
	static ArrayAdapter<String> adapter2;
	
	int spinnerPosition=0;
    int spinnerPosition2=0;
    int spinnerPosition3=0;
    
    static int bigI = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ngredient);
        
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        
        Button okButton = (Button) findViewById(R.id.okButton);
        
        ingredientEditText = (EditText) findViewById(R.id.ingredientEditText);
        quantityEditText = (EditText) findViewById(R.id.quantityEditText);
        minEditText = (EditText) findViewById(R.id.minEditText);
        maxEditText = (EditText) findViewById(R.id.maxEditText);
        
        quantityTypeTextView = (TextView) findViewById(R.id.quantityType);
        minTypeTextView = (TextView) findViewById(R.id.minType);
        maxTypeTextView = (TextView) findViewById(R.id.maxType);
        
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
                
        DoneDatabaseControl newEntry = new DoneDatabaseControl(DoneMenuIngredient.this);
		
		try{
			newEntry.open();
			orderDetailList = newEntry.getAllIngredientsDetails();
			paths = newEntry.getIngredientNames();
			newEntry.close();
        }catch(Exception e){}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(DoneMenuIngredient.this, android.R.layout.simple_list_item_checked, paths); 
        
        spinner = (Spinner) findViewById(R.id.spinner1);
        
        adapter2 = new ArrayAdapter<String>(DoneMenuIngredient.this, android.R.layout.simple_list_item_checked, types);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            	for(int i=0; i<orderDetailList.size(); i++){
            		ingredientName = spinner.getSelectedItem().toString();
        	    	ingredientEditText.setText(ingredientName);
        	    	
    	    		if(paths[i].equals(ingredientName)){
    	    			quantityEditText.setText(orderDetailList.get(i).getQuantity());
    	    			minEditText.setText(orderDetailList.get(i).getMin());
    	    			maxEditText.setText(orderDetailList.get(i).getMax());
    	    			quantityTypeTextView.setText(orderDetailList.get(i).getTypeQty());
    	    			minTypeTextView.setText(orderDetailList.get(i).getTypeMin());
    	    			maxTypeTextView.setText(orderDetailList.get(i).getTypeMax());
    	    			break;
    	    		}
    			}
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub                  
            }
        });
        
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter2);
        spinner4.setAdapter(adapter2);
        
                        
        okButton.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		buttonClick();
        	}
        });
	}
		
	@SuppressLint("NewApi") public void buttonClick(){
		final DoneDatabaseControl newEntry = new DoneDatabaseControl(DoneMenuIngredient.this);
		
		AlertDialog.Builder notification  = new AlertDialog.Builder(this);
		
		int selectedId = radioGroup.getCheckedRadioButtonId();
		
		RadioButton radioButton = (RadioButton) findViewById(selectedId);
		String radioButtonSelection = (String) radioButton.getText();
		final String newIngredient = ingredientEditText.getText().toString();
		final String newIngredientQuantity = quantityEditText.getText().toString();
		final String newIngredientMinQuantity = minEditText.getText().toString();
		final String newIngredientMaxQuantity = maxEditText.getText().toString();
		final String newQtyType = spinner2.getSelectedItem().toString();
		final String newMinType = spinner3.getSelectedItem().toString();
		final String newMaxType = spinner4.getSelectedItem().toString();
		
		boolean wasNotChanged = false;
				
		try{
			newEntry.open();
			
			if((newIngredient != null && !newIngredient.isEmpty())
					&& (newIngredientQuantity != null && !newIngredientQuantity.isEmpty()) 
					&& (newIngredientMinQuantity != null && !newIngredientMinQuantity.isEmpty())
					&& (newIngredientMaxQuantity != null && !newIngredientMaxQuantity.isEmpty())
					&& (newQtyType != null && !newQtyType.isEmpty())
					&& (newMinType != null && !newMinType.isEmpty())
					&& (newMaxType != null && !newMaxType.isEmpty())) {
				
				for(int i=0; i<paths.length; i++){
					if(paths[i].equals(newIngredient) 
							&& (newIngredientQuantity.equals(orderDetailList.get(i).getQuantity())) 
							&& (newIngredientMinQuantity.equals(orderDetailList.get(i).getMin()))
							&& (newIngredientMaxQuantity.equals(orderDetailList.get(i).getMax()))
							&& (newQtyType.equals(orderDetailList.get(i).getTypeQty()))
							&& (newMinType.equals(orderDetailList.get(i).getTypeMin()))
							&& (newMaxType.equals(orderDetailList.get(i).getTypeMax()))
							&& (quantityTypeTextView.getText().toString().equals(orderDetailList.get(i).getTypeQty()))
							&& (minTypeTextView.getText().toString().equals(orderDetailList.get(i).getTypeMin()))
							&& (maxTypeTextView.getText().toString().equals(orderDetailList.get(i).getTypeMax()))){
							wasNotChanged = true;
								break;
					}
				}
				
				if(radioButtonSelection.equalsIgnoreCase("none")){
					notification = DoneRestaurantName.changeAlertDialogBoxMessage(
							"Do you want to go back to settings?!",
							DoneMenuIngredient.this);
					
					notification.setNegativeButton("OK", 
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									finish();
								}
				    		});
					
					notification.setPositiveButton("CANCEL", 
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									
								}
				    		});
					
					notification.create().show();
				}else if(radioButtonSelection.equalsIgnoreCase("add")){
					if(wasNotChanged){
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Ingredient " + newIngredient + " already exists!",
								DoneMenuIngredient.this);
						
						notification.setPositiveButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
									}
					    		});
						
						notification.create().show();
					}else{
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Ingredient " + newIngredient + " will be added!",
								DoneMenuIngredient.this);
						
						notification.setNegativeButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {										
										newEntry.setIngredient(newIngredient, newIngredientQuantity, 
												newIngredientMinQuantity, newIngredientMaxQuantity,
												newQtyType, newMinType, newMaxType);
										newEntry.close();
										finish();
									}
					    		});
						
						notification.setPositiveButton("Cancel", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
									}
					    		});
						notification.create().show();
						
					}
				}else if(radioButtonSelection.equalsIgnoreCase("edit")){	
					if(ingredientName==null){
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Sorry!\nThere are no ingredients in the list as yet!\nPlease add a new ingredient first!",
								DoneMenuIngredient.this);
						
						notification.setPositiveButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
									}
					    		});
						
						notification.create().show();
					}else if(wasNotChanged){
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Nothing was changed!",
								DoneMenuIngredient.this);
						
						notification.setPositiveButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
									}
					    		});
						
						notification.create().show();
					}else{
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"The changes you made will be applied!", 
								DoneMenuIngredient.this);
					
						notification.setNegativeButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										newEntry.updateIngredient(newIngredient, ingredientName, 
												newIngredientQuantity, newIngredientMinQuantity, 
												newIngredientMaxQuantity,
												newQtyType, newMinType, newMaxType);
										newEntry.close();
										finish();
									}
				    			});
						
						notification.setPositiveButton("Cancel", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
									}
				    			});
						
						notification.create().show();
					}
				}else if(radioButtonSelection.equalsIgnoreCase("delete")){
					if(ingredientName==null){
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Sorry!\nThere are no ingredients in the list as yet!\nPlease add a new ingredient first!",
								DoneMenuIngredient.this);
						
						notification.setPositiveButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
									}
					    		});
						
						notification.create().show();
					}else if(wasNotChanged){
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Menu item " + ingredientName + " will be deleted!",
								DoneMenuIngredient.this);
					
						notification.setNegativeButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										newEntry.deleteIngredient(newIngredient);
										newEntry.close();
										finish();
									}
				    			});
					
						notification.setPositiveButton("Cancel", 
								new DialogInterface.OnClickListener() { 
									public void onClick(DialogInterface dialog, int which) {
								
									}
								});
						
						notification.create().show();
					}else{
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Menu item " + newIngredient + " is not in the list!",
								DoneMenuIngredient.this);
					
						notification.setNegativeButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
									}
				    			});

						notification.create().show();
					}
				}
			}else{
				notification = DoneRestaurantName.changeAlertDialogBoxMessage(
						"Sorry you have not entered anything in the text box!", 
						DoneMenuIngredient.this);
				
				notification.setPositiveButton("OK", 
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								
							}
			    		});
				
				notification.create().show();
			}
		}catch(Exception e){
			
		}
	}
}