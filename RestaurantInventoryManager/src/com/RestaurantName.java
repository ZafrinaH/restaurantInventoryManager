package com;

import com.example.restaurantinventorymanager.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RestaurantName extends Activity{
	Button nextButton;
	EditText sqlName;
		
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_name2);
        
        nextButton = (Button) findViewById(R.id.button1);
        sqlName = (EditText) findViewById(R.id.editText1);
        
        nextButton.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		buttonClick(v);
        	}
        });
	}
        
    protected void buttonClick(View v){
    	boolean didItWork = true;
		
		try {
			String name = sqlName.getText().toString();
		
			DatabaseControl entry = new DatabaseControl(RestaurantName.this);
			entry.open();
			entry.setRestaurant(name);
			entry.close();
		}catch (Exception e) {
			didItWork = false;
			String error = e.toString();
			Dialog d = new Dialog(this);
			d.setTitle("Dang It!");
			
			TextView tv = new TextView(this);
			tv.setText(error);
			
			d.setContentView(tv);
			d.show();
		}finally {
			if(didItWork) {
				Dialog d = new Dialog(this);
				d.setTitle("Heck Yea!");
				
				TextView tv = new TextView(this);
				tv.setText("Success!");
				
				d.setContentView(tv);
				d.show();
			}
		}
		Intent intent = new Intent(v.getContext(), MenuItem.class);
		startActivity(intent);
    }
}
