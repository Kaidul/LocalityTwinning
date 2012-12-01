package com.jhalmuri.GKB;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Builder_activity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button gesture_k_a = (Button) findViewById(R.id.button1);
		gesture_k_a.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				startActivity(new Intent(Builder_activity.this,
						Gesture_keyboard_activity.class));
						
			}
		});

		 
		
		/*Button w_less_key=(Button) findViewById(R.id.button4);
		w_less_key.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				startActivity(new Intent(Builder_activity.this,
						Wireless_Keyboard.class));
				
			}
		});*/
		
	}

}
