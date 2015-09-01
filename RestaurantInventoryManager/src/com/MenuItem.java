package com;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantinventorymanager.R;

public class MenuItem extends Activity{
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_item3);
        
        TextView tv = (TextView) findViewById(R.id.editText1);
        DatabaseControl db = new DatabaseControl(this);
        
        try {
			db.open();
			String data = db.getRestaurantName();
			db.close();
			
			tv.setText(data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        Button nextButton = (Button) findViewById(R.id.button1);
        nextButton.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MenuIngredient.class);
				startActivity(intent);
			}
        });
	}
}
