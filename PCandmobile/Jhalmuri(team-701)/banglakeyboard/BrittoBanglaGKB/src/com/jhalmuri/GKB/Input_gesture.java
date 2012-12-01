package com.jhalmuri.GKB;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

public class Input_gesture extends Activity {

	public FrameLayout fout;
	public LinearLayout m_fout;
	private LinearLayout lout3;
	private LinearLayout lout2;
	private ImageView giv, giv2;
	private HorizontalScrollView scv;
	private ImageView tut;
	private ImageView save;
	private ImageView preview;
	private ImageView next;
	private GestureOverlayView gov;
	public DisplayMetrics dm;
	public int p_c; // padding constant
	private GestureLibrary myLib;
	private GestureLibrary myLib2;
	private int ges_name = 10;
	private ArrayList<Gesture> gesture;
	private LinearLayout l_lout1, l_lout2, m_l_lout, l_lout3;
	private ArrayList<Prediction> predictions;
	private boolean b;
	public boolean isGestureSaved;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		isGestureSaved=false;

		dm = this.getResources().getDisplayMetrics();
		m_fout = new LinearLayout(this);
		m_fout.setOrientation(LinearLayout.VERTICAL);
		m_fout.setGravity(Gravity.CENTER | Gravity.BOTTOM);
		scv = new HorizontalScrollView(this);
		tut = new ImageView(this);
		tut.setImageResource(R.drawable.tut);
		// TextView tv=new TextView(this);
		// tv.setText("HHHHHHHHHHHHHHHhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhHHHHHHH");
		// tv.setSingleLine();
		// tv.setScrollContainer(true);
		// scv.addView(tut);
		//scv.addView(tut);

		
		// m_fout.setLayoutParams(m_fout_params);
		fout = new FrameLayout(this);
		fout.setBackgroundResource(R.drawable.bg);

		android.view.ViewGroup.LayoutParams paraimg = new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		p_c = (int) (3.0 * (float) (dm.densityDpi / 160.0));
		preview = new ImageView(this);
		preview.setImageResource(R.drawable.l_arro);
		preview.setLayoutParams(paraimg);
		preview.setPadding(p_c, p_c, p_c, p_c);
		preview.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (ges_name == 10) {
					ges_name = 71;
				}
				if (ges_name > 10) {
					ges_name--;
					gesture = myLib2.getGestures(String.valueOf(ges_name));
					giv.setImageBitmap(gesture.get(0).toBitmap(dynamic(100), dynamic(100),dynamic(5),
							Color.YELLOW));
					Boolean b = false;
					Set<String> Entries = myLib.getGestureEntries();
					Object ob[] = Entries.toArray();
					for (int i = 0; i < ob.length; i++) {
						if (ob[i].toString().equals(String.valueOf(ges_name))) {
							b = true;
						}
					}
					if (b) {
						gesture = myLib.getGestures(String.valueOf(ges_name));
						giv2.setImageBitmap(gesture.get(0).toBitmap(dynamic(100),dynamic(100),
								dynamic(5), Color.YELLOW));

					} else {
						giv2.setImageResource(R.drawable.no_gesture);
					}
				}
				gov.clear(true);
			}
		});

		next = new ImageView(this);
		next.setImageResource(R.drawable.r_arro);
		next.setLayoutParams(paraimg);
		next.setPadding(p_c, p_c, p_c, p_c);
		next.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (ges_name == 70) {
					ges_name = 9;
				}
				if (ges_name < 70) {
					ges_name++;

					gesture = myLib2.getGestures(String.valueOf(ges_name));
					giv.setImageBitmap(gesture.get(0).toBitmap(dynamic(100),dynamic(100),dynamic(5),
							Color.YELLOW));

					Boolean b = false;
					Set<String> Entries = myLib.getGestureEntries();
					Object ob[] = Entries.toArray();
					for (int i = 0; i < ob.length; i++) {
						if (ob[i].toString().equals(String.valueOf(ges_name))) {
							b = true;
						}
					}
					if (b) {
						gesture = myLib.getGestures(String.valueOf(ges_name));
						giv2.setImageBitmap(gesture.get(0).toBitmap(dynamic(100),dynamic(100),
								5, Color.YELLOW));

					} else {
						giv2.setImageResource(R.drawable.no_gesture);
					}

				}
				gov.clear(true);
			}
		});

		save = new ImageView(this);
		save.setImageResource(R.drawable.save);
		save.setLayoutParams(paraimg);
		save.setPadding(p_c, p_c, p_c, p_c);
		save.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				b = true;
				if (gov.getGesture() == null) {
					Toast.makeText(getApplicationContext(),
							"First draw a gesture", Toast.LENGTH_SHORT).show();
				} else if (gov.getGesture() != null) {
					myLib = GestureLibraries.fromFile("/sdcard/ban_gestures");
					myLib.load();
					predictions = myLib.recognize(gov.getGesture());
					if (predictions.size() > 0) {
						Prediction prediction = predictions.get(0);
						// We want at least some confidence in the result
						if (prediction.score > 2.0) {
							if (!prediction.name.equals(String
									.valueOf(ges_name))) {
								Toast.makeText(getApplicationContext(),
										"Sorry this gesture is already taken ",
										Toast.LENGTH_LONG).show();
								Toast.makeText(getApplicationContext(),
										"Gesture not saved", Toast.LENGTH_SHORT)
										.show();
								b = false;
							}
						}
					}

					if (b) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								Input_gesture.this);
						builder.setMessage("Are you sure to overwrite gesture?");
						builder.setCancelable(true);
						builder.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface arg0,
											int arg1) {

										// TODO Auto-generated method stub
										if (gov.getGesture() != null) {

											myLib = GestureLibraries
													.fromFile("/sdcard/ban_gestures");
											myLib.load();

											final GestureLibrary store = myLib;
											store.removeEntry(String
													.valueOf(ges_name));
											store.addGesture(
													String.valueOf(ges_name),
													gov.getGesture());
											store.save();
											Toast.makeText(
													getApplicationContext(),
													"Gesture Saved",
													Toast.LENGTH_SHORT).show();
											isGestureSaved=true;
											gesture = myLib.getGestures(String
													.valueOf(ges_name));
											giv2.setImageBitmap(gesture.get(0)
													.toBitmap(dynamic(100), dynamic(100), dynamic(5),
															Color.YELLOW));
											gov.clear(true);
										}
									}

								});

						builder.setNegativeButton("No",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface arg0,
											int arg1) {
										arg0.cancel();
										gov.clear(true);

									}
								});

						AlertDialog alert = builder.create();
						alert.show();
					}
				}
				
			}
		});

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lout3 = new LinearLayout(this);
		lout3.setLayoutParams(params);
		lout3.setOrientation(LinearLayout.HORIZONTAL);
		lout3.setGravity(Gravity.RIGHT);
		lout3.addView(preview);
		lout3.addView(next);

		lout2 = new LinearLayout(this);
		lout2.setOrientation(LinearLayout.VERTICAL);
		lout2.setGravity(Gravity.RIGHT);
		lout2.addView(lout3);
		lout2.addView(save);

		FrameLayout.LayoutParams fpara1 = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
						| Gravity.TOP);
		FrameLayout.LayoutParams fpara2 = new FrameLayout.LayoutParams(
				dm.widthPixels,
				(int) (200.0 * (float) (dm.densityDpi / 160.0)), Gravity.LEFT
						| Gravity.TOP);
		LinearLayout.LayoutParams lpara = new LinearLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);

		gov = new GestureOverlayView(this);
		gov.setGestureStrokeType(1);
		
		giv = new ImageView(this);
		giv2 = new ImageView(this);
		TextView defult = new TextView(this);
		defult.setText("      Default    ");
		TextView present = new TextView(this);
		present.setText("      Present   ");
		l_lout1 = new LinearLayout(this);
		l_lout1.setOrientation(LinearLayout.VERTICAL);
		l_lout1.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		l_lout1.addView(giv);
		l_lout1.addView(defult, lpara);
		l_lout1.setPadding(15, 0, 15, 0);

		l_lout2 = new LinearLayout(this);
		l_lout2.setOrientation(LinearLayout.VERTICAL);
		l_lout2.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		l_lout2.addView(giv2);
		l_lout2.addView(present, lpara);
		l_lout2.setPadding(15, 0, 15, 0);

		m_l_lout = new LinearLayout(this);
		m_l_lout.setOrientation(LinearLayout.HORIZONTAL);
		m_l_lout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		m_l_lout.addView(l_lout1);
		m_l_lout.addView(l_lout2);

		fout.addView(gov, fpara2);
		fout.addView(lout2, fpara1);
	//scv1.addView(m_l_lout);
		
		l_lout3 = new LinearLayout(this);
		l_lout3.setOrientation(LinearLayout.HORIZONTAL);
		l_lout3.addView(m_l_lout);
		l_lout3.addView(tut);
	   scv.addView(l_lout3);
		//scv1.addView(fout);
		m_fout.addView(scv);
		
		m_fout.addView(fout);

		myLib2 = GestureLibraries.fromRawResource(this, R.raw.ban_gestures);
		if (myLib2.load()) {
			// Toast.makeText(context, "loading...", Toast.LENGTH_SHORT).show();
		}

		ges_name = 10;
		myLib = GestureLibraries.fromFile("/sdcard/ban_gestures");
		if (myLib.load()) {
			Boolean b = false;
			Set<String> Entries = myLib.getGestureEntries();
			Object ob[] = Entries.toArray();
			for (int i = 0; i < ob.length; i++) {
				if (ob[i].toString().equals("10")) {
					b = true;
				}
			}
			if (b) {
				gesture = myLib.getGestures(String.valueOf(ges_name));
				giv2.setImageBitmap(gesture.get(0).toBitmap(dynamic(100),dynamic(100),dynamic(5),
						Color.YELLOW));

			} else {
				giv2.setImageResource(R.drawable.no_gesture);
			}
		} else {
			giv2.setImageResource(R.drawable.no_gesture);
		}

		gesture = myLib2.getGestures(String.valueOf(ges_name));
		giv.setImageBitmap(gesture.get(0).toBitmap(dynamic(100),dynamic(100),dynamic(5), Color.YELLOW));

		setContentView(m_fout);
	}
	
	int dynamic (int a)
	{
	 	

     return (int) ((float)a * (float) (dm.densityDpi / 160.0)) ;
	}
}


