package com;

import com.example.restaurantinventorymanager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class DoneSettings extends Activity implements OnClickListener{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        System.out.println("Inside Settings onCreate");
        
        Button change_restaurant_name = (Button) findViewById(R.id.change_restaurant_name);
        Button change_menuItem_name = (Button) findViewById(R.id.change_menuItem_name);
        Button change_ingredient_name = (Button) findViewById(R.id.change_ingredient_name);
        Button recipe = (Button) findViewById(R.id.recipe);
        Button add_todays_sales = (Button) findViewById(R.id.add_todays_sales);
        Button refill = (Button) findViewById(R.id.refill);
        change_restaurant_name.setOnClickListener(this);
        change_menuItem_name.setOnClickListener(this);
        change_ingredient_name.setOnClickListener(this);
        recipe.setOnClickListener(this);
        add_todays_sales.setOnClickListener(this);
        refill.setOnClickListener(this);
        
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        
        imageButton.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		Intent intent = new Intent(DoneSettings.this, DoneInventoryDisplay.class);
        		startActivity(intent);
        		finish();
        	}
        });
	}

	@Override
	public void onClick(View v) {
		System.out.println("Inside onClick method");
		Intent intent;
		switch(v.getId()){
		case R.id.change_restaurant_name:
			intent = new Intent(DoneSettings.this, DoneRestaurantName.class);
			startActivity(intent);
			break;
		case R.id.change_menuItem_name:
			intent = new Intent(DoneSettings.this, DoneMenuItem.class);
			startActivity(intent);
			break;
		case R.id.change_ingredient_name:
			intent = new Intent(DoneSettings.this, DoneMenuIngredient.class);
			startActivity(intent);
			break;
		case R.id.recipe:
			intent = new Intent(DoneSettings.this, NotDoneRecipeSettings.class);
			startActivity(intent);
			break;
		case R.id.add_todays_sales:
			intent = new Intent(DoneSettings.this, NotDoneSalesToday.class);
			startActivity(intent);
			break;
		case R.id.refill:
			intent = new Intent(DoneSettings.this, Refill.class);
			startActivity(intent);
			break;
		}
	}
}
