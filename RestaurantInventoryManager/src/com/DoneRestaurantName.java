package com;

import com.example.restaurantinventorymanager.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class DoneRestaurantName extends Activity{
	EditText restaurantNameEditText;
	static String oldName = null;
	String newName = null;
		
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_restaurant_name);
        
        DoneDatabaseControl newEntry = new DoneDatabaseControl(DoneRestaurantName.this);
        try{
        	newEntry.open();
        	newEntry.setRestaurantName();
        	newEntry.close();
        }catch(Exception e){}
                        
        restaurantNameEditText = (EditText) findViewById(R.id.restaurantNameEditText);
        
        setExitTextWithCurrentRestaurantName();
        
        Button ok = (Button) findViewById(R.id.okButton);
        
        ok.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		buttonClick(v);
        	}
        });
	}
	
	public void setExitTextWithCurrentRestaurantName(){
		try {
			DoneDatabaseControl newEntry = new DoneDatabaseControl(DoneRestaurantName.this);
			newEntry.open();
			
			oldName = newEntry.getRestaurantName();
			restaurantNameEditText.setText(oldName);	
			newEntry.close();
		}catch(Exception e){
			
		}
	}
        
    @SuppressLint("NewApi") protected void buttonClick(View v){
    	AlertDialog.Builder notification  = new AlertDialog.Builder(this);
    	
		try {
			final DoneDatabaseControl newEntry = new DoneDatabaseControl(DoneRestaurantName.this);
			newName = restaurantNameEditText.getText().toString();
			
			if(newName != null && !newName.isEmpty()) {	 
				if(newName.equals(oldName)){
					finish();
				}else{
					newEntry.open();
		    		notification = DoneRestaurantName.changeAlertDialogBoxMessage(
		    				"Restaurant name will be changed from " + oldName + " to " + newName + "!", 
		    				DoneRestaurantName.this);
					notification.setNegativeButton("OK", 
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
						    		newEntry.updateRestaurantName(newName, oldName);
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
			}else{
				finish();
			}
		}catch (Exception e) {
			notification = DoneRestaurantName.changeAlertDialogBoxMessage("Sorry there was an Error!", 
					DoneRestaurantName.this);
			notification.setPositiveButton("OK", 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							
						}
		    		});
			notification.create().show();
		}finally{
		}
    }
    
    public static AlertDialog.Builder changeAlertDialogBoxMessage(String message, Context context){
    	AlertDialog.Builder notification  = new AlertDialog.Builder(context);
    	
    	notification.setMessage(message + "!");
		
		notification.setCancelable(true);
		
		return notification;
    }
}
