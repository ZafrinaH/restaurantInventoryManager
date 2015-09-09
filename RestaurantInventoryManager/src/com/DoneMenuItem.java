package com;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.restaurantinventorymanager.R;

public class DoneMenuItem extends Activity{
	Spinner spinner;
	String menuItem = null;
	RadioGroup radioGroup;
	EditText menuItemInserted;
	static String[] paths = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_menu_item);
        
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        
        Button okButton = (Button) findViewById(R.id.okButton);
        
        menuItemInserted = (EditText) findViewById(R.id.restaurantNameEditText);
                
        DoneDatabaseControl newEntry = new DoneDatabaseControl(DoneMenuItem.this);
		
        try{
			newEntry.open();
			paths = newEntry.getAllMenuItems();
			newEntry.close();
        }catch(Exception e){}
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DoneMenuItem.this, android.R.layout.simple_list_item_checked, paths); 
        
        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            	menuItem = spinner.getSelectedItem().toString();
            	menuItemInserted.setText(menuItem);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub                  
            }
        });
        
        okButton.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		buttonClick();
        	}
        });
	}
	
	@SuppressLint("NewApi") public void buttonClick(){
		final DoneDatabaseControl newEntry = new DoneDatabaseControl(DoneMenuItem.this);
		AlertDialog.Builder notification  = new AlertDialog.Builder(this);
		
		int selectedId = radioGroup.getCheckedRadioButtonId();
		
		RadioButton radioButton = (RadioButton) findViewById(selectedId);
		String radioButtonSelection = (String) radioButton.getText();
		final String newMenuItem = menuItemInserted.getText().toString();
		boolean wasItThere = false;
		
		for(int i=0; i<paths.length; i++){
			if(paths[i].equals(newMenuItem)){
				wasItThere = true;
				break;
			}
		}
		
		try{
			newEntry.open();
			if(newMenuItem != null && !newMenuItem.isEmpty()) {
				if(radioButtonSelection.equalsIgnoreCase("edit")){	
					if(menuItem==null){
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Sorry!\nThere are no menu items in the list as yet!\nPlease add a new menu item first!",
								DoneMenuItem.this);
						
						notification.setPositiveButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
									}
					    		});
						
						notification.create().show();
					}else if(wasItThere){
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"The new name " + newMenuItem + 
								" is the same as the existing name for that menu item!",
								DoneMenuItem.this);
						
						notification.setPositiveButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
									}
					    		});
						
						notification.create().show();
					}else{
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Menu item name will be changed from " + menuItem + 
								" to " + newMenuItem + "!", 
		    				DoneMenuItem.this);
					
						notification.setNegativeButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										newEntry.updateMenuItem(newMenuItem, menuItem);
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
				}else if(radioButtonSelection.equalsIgnoreCase("add")){
					if(wasItThere){
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Menu item " + newMenuItem + " already exists!",
								DoneMenuItem.this);
						
						notification.setPositiveButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
									}
					    		});
						
						notification.create().show();
					}else{
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Menu item " + newMenuItem + " will be added!",
								DoneMenuItem.this);
						
						notification.setNegativeButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										newEntry.setMenuItem(newMenuItem);
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
					if(menuItem==null){
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Sorry!\nThere are no menu items in the list as yet!\nPlease add a menu item first!",
								DoneMenuItem.this);
						
						notification.setPositiveButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
									}
					    		});
						
						notification.create().show();
					}
					else if(wasItThere){
						notification = DoneRestaurantName.changeAlertDialogBoxMessage(
								"Menu item " + menuItem + " will be deleted!",
								DoneMenuItem.this);
					
						notification.setNegativeButton("OK", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										newEntry.deleteMenuItem(newMenuItem);
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
								"Menu item " + newMenuItem + " is not in the list!",
								DoneMenuItem.this);
					
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
						DoneMenuItem.this);
				
				notification.setPositiveButton("OK", 
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								
							}
			    		});
				
				notification.create().show();
			}
		}catch(Exception e){
			
		}finally{
			
		}
	}
}
