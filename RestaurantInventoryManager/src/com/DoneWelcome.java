package com;
import com.example.restaurantinventorymanager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DoneWelcome extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
                
        Button launchbutton = (Button) findViewById(R.id.launchButton);
        launchbutton.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		Intent intent = new Intent(DoneWelcome.this, DoneInventoryDisplay.class);
        		startActivity(intent);
        		finish();
			}
        });
    }
}
