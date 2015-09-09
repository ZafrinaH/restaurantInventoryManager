package com;

import java.util.ArrayList;

import com.example.restaurantinventorymanager.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DoneInventoryDisplay extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_items5);
        
        Button salesButton = (Button) findViewById(R.id.salesButton);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        
        TableRow tableColumnTitlesRow = (TableRow) findViewById(R.id.tableColumnTitlesRow);
        
        salesButton.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		Intent intent = new Intent(DoneInventoryDisplay.this, NotDoneSalesToday.class);
        		startActivity(intent);
        	}
        });
        
        imageButton.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		Intent intent = new Intent(DoneInventoryDisplay.this, DoneSettings.class);
        		startActivity(intent);
        		finish();
        	}
        });
        
        DoneDatabaseControl newEntry = new DoneDatabaseControl(DoneInventoryDisplay.this);
        ArrayList<DoneInventory> orderDetailList = null;
        
        try{
			newEntry.open();
			orderDetailList = newEntry.getAllIngredientsDetails();
			newEntry.close();
        }catch(Exception e){}
        
        for(int i=0; i<orderDetailList.size(); i++){
        	TableRow tableRow = new TableRow(DoneInventoryDisplay.this);
        	TextView tv1 = new TextView(DoneInventoryDisplay.this);
        	TextView tv2 = new TextView(DoneInventoryDisplay.this);
        	TextView tv3 = new TextView(DoneInventoryDisplay.this);
        	
			tv1.setTextColor(Color.BLACK);
			tv1.setGravity(Gravity.CENTER);
			tv1.setBackgroundColor(Color.parseColor("#b0e0e6"));
			tv1.setText(orderDetailList.get(i).getIngredient());
			
			tv2.setTextColor(Color.BLACK);
			tv2.setGravity(Gravity.CENTER);
			tv2.setBackgroundColor(Color.parseColor("#b0e0e6"));
			tv2.setText(orderDetailList.get(i).getQuantity());
			
			tv3.setTextColor(Color.BLACK);
			tv3.setGravity(Gravity.CENTER);
			tv3.setBackgroundColor(Color.parseColor("#b0e0e6"));
			tv3.setText(orderDetailList.get(i).getTypeQty());
			
			tableRow.addView(tv1);
			tableRow.addView(tv2);
			tableRow.addView(tv3);
			tableLayout.addView(tableRow);
        }
        
        
	}
}
