package com;
import com.example.restaurantinventorymanager.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class MenuIngredient extends Activity{
	TextView addMore, textView1, textView2;
	EditText scr1, scr2, editText1, editText2;
	TableLayout tableLayout;
	TableRow tableRow;
	TableLayout.LayoutParams layoutParams;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_item_ingredient4);
        		
        addMore = (TextView) findViewById(R.id.textView2);
        textView1 = (TextView) findViewById(R.id.tablerowtextview1);
        textView2 = (TextView) findViewById(R.id.tablerowtextview2);
        
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        
        /*editText1.setMaxWidth(textView1.getWidth());
        editText2.setMaxWidth(textView2.getWidth());*/
        
        tableLayout = (TableLayout) findViewById(R.id.tableLayout1);
        
        layoutParams = new TableLayout.LayoutParams(
        		TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1);
        layoutParams.setMargins(1, 1, 1, 1);
        
        
        addMore.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		textViewClick(v);
			}
        });
    }

	@SuppressLint("NewApi") public void textViewClick(View v) {
		switch(v.getId()){
		case R.id.textView2:
			tableRow = new TableRow(this);
			
			scr1 = new EditText(this);
			scr2 = new EditText(this);
			
			scr1.setTextColor(Color.BLACK);
			scr1.setGravity(Gravity.CENTER);
			scr1.setBackgroundColor(Color.parseColor("#b0e0e6"));
			scr1.setFocusable(true);
			scr1.setEnabled(true);
			scr1.setClickable(true);
			scr1.setFocusableInTouchMode(true);
			scr1.setPadding(20, 20, 20, 20);
									
			scr2.setTextColor(Color.BLACK);
			scr2.setGravity(Gravity.CENTER);
			scr2.setBackgroundColor(Color.parseColor("#9EC9CF"));
			scr2.setFocusable(true);
			scr2.setEnabled(true);
			scr2.setClickable(true);
			scr2.setFocusableInTouchMode(true);
			scr2.setPadding(20, 20, 20, 20);
						
			tableRow.addView(scr1);
			tableRow.addView(scr2);
			tableLayout.addView(tableRow, layoutParams);
			break;
		default:
			break;
		}
	}
	
	
}
