package com;

import com.example.restaurantinventorymanager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NotDoneRecipeSettings extends Activity implements OnClickListener{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_settings);
        
        
        Button view_recipe = (Button) findViewById(R.id.view_recipe);
        Button add_recipe = (Button) findViewById(R.id.add_recipe);
        //Button edit_recipe = (Button) findViewById(R.id.edit_recipe);
        //Button delete_recipe = (Button) findViewById(R.id.delete_recipe);
        
        view_recipe.setOnClickListener(this);
        add_recipe.setOnClickListener(this);
        //edit_recipe.setOnClickListener(this);
        //delete_recipe.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch(v.getId()){
		case R.id.view_recipe:
			intent = new Intent(NotDoneRecipeSettings.this, DoneViewRecipe.class);
			startActivity(intent);
			finish();
			break;
		case R.id.add_recipe:
			intent = new Intent(NotDoneRecipeSettings.this, DoneAddRecipe.class);
			startActivity(intent);
			finish();
			break;
		}
	}
	
	
}
