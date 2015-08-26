import com.example.restaurantinventorymanager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class Welcome extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome1);
        
        Button launchbutton = (Button) findViewById(R.id.launchButton);
        launchbutton.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), RestaurantName.class);
				startActivity(intent);
			}
        });
    }
}
