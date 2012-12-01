package com.jhalmuri.GKB;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Gesture_keyboard_activity extends Activity {

	private GestureLibrary myLib, myLib2;
	private Button input;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		myLib = GestureLibraries.fromFile("/sdcard/ban_gestures");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesture_func);
		Button reSet = (Button) findViewById(R.id.button1);
		reSet.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				AlertDialog.Builder builder= new AlertDialog.Builder(Gesture_keyboard_activity.this);
				builder.setMessage("Are you sure to reset gestures?");
				builder.setCancelable(true);
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface arg0, int arg1) {
						myLib2 = GestureLibraries.fromRawResource(
								getApplicationContext(), R.raw.ban_gestures);
						if (myLib2.load()) {
							// Toast.makeText(getApplicationContext(), "load1",
							// Toast.LENGTH_SHORT).show();
						}
						
						myLib = GestureLibraries.fromFile("/sdcard/ban_gestures");
						if (myLib.load()) {
							// Toast.makeText(getApplicationContext(), "load2",
							// Toast.LENGTH_SHORT).show();
						}
						final GestureLibrary store = myLib;
						for (int i = 10; i <= 70; i++) {
							store.removeEntry(String.valueOf(i));
						}
						for (int i = 10; i <= 70; i++) {
							store.addGesture(String.valueOf(i),
									myLib2.getGestures(String.valueOf(i)).get(0));
						}
						store.save();
						Toast.makeText(getApplicationContext(), "Gesture Reset",
								Toast.LENGTH_SHORT).show();
						input.setEnabled(true);
						
					}
				});
				
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.cancel();
						
					}
				});
				
				AlertDialog alert=builder.create();
				alert.show();

			}
		});

		 input = (Button) findViewById(R.id.button2);
		if(!myLib.load()){
			input.setEnabled(false);
		}
		input.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				startActivity(new Intent(Gesture_keyboard_activity.this,
						Input_gesture.class));

			}
		});
		
		Button test = (Button) findViewById(R.id.button3);
		test.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				startActivity(new Intent(Gesture_keyboard_activity.this,
						Gesture_test.class));
				
			}
		});
		
	}
}
