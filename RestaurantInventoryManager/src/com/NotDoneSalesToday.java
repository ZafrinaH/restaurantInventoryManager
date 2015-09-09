package com;

import java.sql.SQLException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.restaurantinventorymanager.R;

public class NotDoneSalesToday extends Activity{
	TableLayout tableLayout;
	TextView textView1;
	TableLayout.LayoutParams layoutParams;
	static String[] paths = null;
	static boolean done = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_sales6);
        
        tableLayout = (TableLayout) findViewById(R.id.tableLayout1);
        textView1 = (TextView) findViewById(R.id.textView2);
        EditText editText1 = (EditText) findViewById(R.id.editText2);
        editText1.setId(0);
        
        layoutParams = new TableLayout.LayoutParams(
        		TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1);
        layoutParams.setMargins(1, 1, 1, 1);
        
        addMenuItemsTextView();
        
        Button ok = (Button) findViewById(R.id.button1);
        ok.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		buttonClick();
        	}
        });
	}

	public void addMenuItemsTextView(){
		DoneDatabaseControl newEntry = new DoneDatabaseControl(NotDoneSalesToday.this);
        try{
        	newEntry.open();
        	paths = newEntry.getAllMenuItems();
        	newEntry.close();
        }catch(Exception e){}
        
        System.out.println(paths.length);
        
        for(int i=0; i<paths.length; i++){
        	if(i==0){
        		textView1.setText(paths[0]);
        	}else{
        		TableRow tr = new TableRow(this);
        		TextView tv = new TextView(this);
        		EditText et = new EditText(this);
        		
        		tv.setText(paths[i]);
        		tv.setTextColor(Color.BLACK);
        		tv.setGravity(Gravity.CENTER);
        		tv.setBackgroundColor(Color.parseColor("#b0e0e6"));
        		tv.setPadding(20, 20, 20, 20);
        		
        		et.setId(i);
        		et.setTextColor(Color.BLACK);
        		et.setGravity(Gravity.CENTER);
        		et.setBackgroundColor(Color.parseColor("#9EC9CF"));
        		et.setFocusable(true);
        		et.setEnabled(true);
        		et.setClickable(true);
        		et.setFocusableInTouchMode(true);
        		et.setInputType(InputType.TYPE_CLASS_NUMBER);
        		et.setPadding(20, 20, 20, 20);
        		
        		tr.addView(tv);
        		tr.addView(et);
        		tableLayout.addView(tr, layoutParams);
        	}
        }
	}
	
	@SuppressLint("NewApi") public void buttonClick() {
		for(int i=0; i<paths.length; i++){
			EditText et = (EditText) findViewById(i);
			String quantityString = et.getText().toString();
			double quantity = 0.0;
			
			if(quantityString!=null && !quantityString.isEmpty()){
				done=true;
				quantity = Double.parseDouble(quantityString);
				DoneDatabaseControl newEntry = new DoneDatabaseControl(NotDoneSalesToday.this);
				try{
					newEntry.open();
					String[] a = newEntry.getName(); 
					System.out.println(a[i]);
					
					newEntry.open();
					System.out.println("here 2 " + paths[i]);
					ArrayList<DoneInventory> orderDetailList = newEntry.getIngredientQuantity(paths[i], this);
					
					for(int j=0; j<orderDetailList.size(); j++){
						String qty = orderDetailList.get(j).getQuantity();
						if(qty!=null && !qty.isEmpty()){
							double qty1 = 0.0;
							qty1 = Double.parseDouble(qty);
							qty1 -= quantity;
							newEntry.updateQuantity(qty1, orderDetailList.get(j).getIngredient());
						}
					}
					/*String iaq = ingredientsAndQuantity[i];
					String[] a1 = iaq.split(",");
					String ingredient = a1[0];
	        		String quantityString2 = a1[1];
	        		double quanity1 = 0.0;
	        		if(quantityString2!=null && !quantityString2.isEmpty()){
	    				quantity = Double.parseDouble(quantityString2);
	    				quanity1 = quanity1 - quantity;
	    				String[] b = newEntry.getName();
	    				System.out.println("here zf");
	    			}*/
				}catch(Exception e){}
			}else{
				done=false;
				Dialog d = new Dialog(this);
				d.setTitle("Sorry!");
				
				TextView tv = new TextView(this);
				tv.setText("Please enter a valid quantity value!");
				
				d.setContentView(tv);
				d.show();
			}
		}
	}
}
