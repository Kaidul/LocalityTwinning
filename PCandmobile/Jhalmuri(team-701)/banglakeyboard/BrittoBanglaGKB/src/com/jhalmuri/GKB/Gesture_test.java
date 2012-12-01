package com.jhalmuri.GKB;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

public class Gesture_test extends Activity {
	public FrameLayout fout;
	public FrameLayout m_fout;
	private LinearLayout lout3;
	private LinearLayout lout2;
	private ImageView giv2;

	// private ImageView save;
	private ImageView preview;
	private ImageView next;
	private ImageView ban_eng;
	private int be;
	private GestureOverlayView gov;
	public DisplayMetrics dm;
	public int p_c; // padding constant
	private GestureLibrary myLib, myLib_eng, myLib_smile;
	// private GestureLibrary myLib2;
	private int ges_name = 10;
	private int smile = 71;
	private ArrayList<Gesture> gesture;
	private LinearLayout l_lout2, m_l_lout;
	private final String alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJ";
	private int eng = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		dm = this.getResources().getDisplayMetrics();
		m_fout = new FrameLayout(this);

		android.view.ViewGroup.LayoutParams m_fout_params = new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT);
		m_fout.setLayoutParams(m_fout_params);
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
				if (be == 0) {
					if (smile == 71) {
						smile = 77;
					}
					if (smile > 71) {
						smile--;

						gesture = myLib_smile.getGestures(String.valueOf(smile));
						giv2.setImageBitmap(gesture.get(0).toBitmap(100, 100,
								5, Color.YELLOW));

					}
				} else if (be == 1) {
					// TODO Auto-generated method stub
					if (ges_name == 10) {
						ges_name = 71;
					}
					if (ges_name > 10) {
						ges_name--;
						// gesture =
						// myLib2.getGestures(String.valueOf(ges_name));
						// giv.setImageBitmap(gesture.get(0).toBitmap(100, 100,
						// 5,
						// Color.YELLOW));
						Boolean b = false;
						Set<String> Entries = myLib.getGestureEntries();
						Object ob[] = Entries.toArray();
						for (int i = 0; i < ob.length; i++) {
							if (ob[i].toString().equals(
									String.valueOf(ges_name))) {
								b = true;
							}
						}
						if (b) {
							gesture = myLib.getGestures(String
									.valueOf(ges_name));
							giv2.setImageBitmap(gesture.get(0).toBitmap(100,
									100, 5, Color.YELLOW));

						} else {
							giv2.setImageResource(R.drawable.no_gesture);
						}
					}
				} else if (be == 2) {
					if (eng == 0) {
						eng = alph.length();
					}
					if (eng > 0) {
						eng--;
						gesture = myLib_eng.getGestures(String.valueOf(alph
								.charAt(eng)));
						giv2.setImageBitmap(gesture.get(0).toBitmap(100, 100,
								5, Color.YELLOW));
					}
				}
			}
		});

		next = new ImageView(this);
		next.setImageResource(R.drawable.r_arro);
		next.setLayoutParams(paraimg);
		next.setPadding(p_c, p_c, p_c, p_c);
		next.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				if (be == 0) {
					if (smile == 76) {
						smile = 70;
					}
					if (smile < 76) {
						smile++;

						gesture = myLib_smile.getGestures(String.valueOf(smile));
						giv2.setImageBitmap(gesture.get(0).toBitmap(100, 100,
								5, Color.YELLOW));

					}
				}

				else if (be == 1) {
					if (ges_name == 70) {
						ges_name = 9;
					}
					if (ges_name < 70) {
						ges_name++;

						Boolean b = false;
						Set<String> Entries = myLib.getGestureEntries();
						Object ob[] = Entries.toArray();
						for (int i = 0; i < ob.length; i++) {
							if (ob[i].toString().equals(
									String.valueOf(ges_name))) {
								b = true;
							}
						}
						if (b) {
							gesture = myLib.getGestures(String
									.valueOf(ges_name));
							giv2.setImageBitmap(gesture.get(0).toBitmap(100,
									100, 5, Color.YELLOW));

						} else {
							giv2.setImageResource(R.drawable.no_gesture);
						}

					}
				} else if (be == 2) {
					if (eng == alph.length() - 1) {
						eng = -1;
					}
					if (eng < alph.length() - 1) {
						eng++;
						gesture = myLib_eng.getGestures(String.valueOf(alph
								.charAt(eng)));
						giv2.setImageBitmap(gesture.get(0).toBitmap(100, 100,
								5, Color.YELLOW));
					}
				}
			}
		});

		be = 1;
		ban_eng = new ImageView(this);
		ban_eng.setImageResource(R.drawable.ban);
		ban_eng.setPadding(p_c, p_c, p_c, p_c);
		ban_eng.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				if (be == 2) {
					ban_eng.setImageResource(R.drawable.smile);
					gesture = myLib_smile.getGestures(String.valueOf(71));
					giv2.setImageBitmap(gesture.get(0).toBitmap(100, 100, 5,
							Color.YELLOW));
					be = 0;
				} else if (be == 1) {
					ban_eng.setImageResource(R.drawable.eng);
					gesture = myLib_eng.getGestures(String.valueOf(alph
							.charAt(eng)));
					giv2.setImageBitmap(gesture.get(0).toBitmap(100, 100, 5,
							Color.YELLOW));
					be = 2;
				} else if (be == 0) {
					ban_eng.setImageResource(R.drawable.ban);
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
						giv2.setImageBitmap(gesture.get(0).toBitmap(100, 100,
								5, Color.YELLOW));

					} else {
						giv2.setImageResource(R.drawable.no_gesture);
					}
					be = 1;
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
		// lout2.addView(save);

		FrameLayout.LayoutParams fpara1 = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
						| Gravity.TOP);
		FrameLayout.LayoutParams fpara2 = new FrameLayout.LayoutParams(
				dm.widthPixels,
				(int) (200.0 * (float) (dm.densityDpi / 160.0)), Gravity.LEFT
						| Gravity.TOP);
		FrameLayout.LayoutParams fpara4 = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT
						| Gravity.BOTTOM);
		FrameLayout.LayoutParams fpara5 = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER
						| Gravity.TOP);
		LinearLayout.LayoutParams lpara = new LinearLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		FrameLayout.LayoutParams fpara6 = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT
						| Gravity.BOTTOM);

		gov = new GestureOverlayView(this);
		gov.setGestureStrokeType(1);
		gov.addOnGesturePerformedListener(new OnGesturePerformedListener() {

			public void onGesturePerformed(GestureOverlayView arg0, Gesture arg1) {
				if (be == 1) {
					ArrayList<Prediction> predictions = myLib.recognize(arg0
							.getGesture());
					if (predictions.size() > 0) {
						Prediction prediction = predictions.get(0);
						// We want at least some confidence in the result
						if (prediction.score > 2.0) {
							if (prediction.name.equals(String.valueOf(ges_name))) {
								Toast.makeText(getApplicationContext(),
										"Gesture Matched", Toast.LENGTH_SHORT)
										.show();

								if (ges_name == 70) {
									ges_name = 9;
								}
								if (ges_name < 70) {
									ges_name++;

									Boolean b = false;
									Set<String> Entries = myLib
											.getGestureEntries();
									Object ob[] = Entries.toArray();
									for (int i = 0; i < ob.length; i++) {
										if (ob[i].toString().equals(
												String.valueOf(ges_name))) {
											b = true;
										}
									}
									if (b) {
										gesture = myLib.getGestures(String
												.valueOf(ges_name));
										giv2.setImageBitmap(gesture.get(0)
												.toBitmap(100, 100, 5,
														Color.YELLOW));

									} else {
										giv2.setImageResource(R.drawable.no_gesture);
									}

								}

							}
						}
					}
				} else if (be == 2) {
					ArrayList<Prediction> predictions = myLib_eng
							.recognize(arg0.getGesture());
					if (predictions.size() > 0) {
						Prediction prediction = predictions.get(0);
						// We want at least some confidence in the result
						if (prediction.score > 2.0) {
							if (prediction.name.equals(String.valueOf(alph
									.charAt(eng)))) {
								Toast.makeText(getApplicationContext(),
										"Gesture Matched", Toast.LENGTH_SHORT)
										.show();

								if (eng == alph.length() - 1) {
									eng = -1;
								}
								if (eng < alph.length() - 1) {
									eng++;
									gesture = myLib_eng.getGestures(String
											.valueOf(alph.charAt(eng)));
									giv2.setImageBitmap(gesture
											.get(0)
											.toBitmap(100, 100, 5, Color.YELLOW));
								}

							}
						}
					}
				} else if (be == 0) {
					ArrayList<Prediction> predictions = myLib_smile.recognize(arg0
							.getGesture());
					if (predictions.size() > 0) {
						Prediction prediction = predictions.get(0);
						// We want at least some confidence in the result
						if (prediction.score > 2.0) {
							if (prediction.name.equals(String.valueOf(smile))) {
								Toast.makeText(getApplicationContext(),
										"Gesture Matched", Toast.LENGTH_SHORT)
										.show();
								if (smile == 76) {
									smile = 70;
								}
								if (smile < 76) {
									smile++;
									gesture = myLib_smile.getGestures(String.valueOf(smile));
									giv2.setImageBitmap(gesture.get(0).toBitmap(100, 100,
											5, Color.YELLOW));	
								}
							}
						}
					}
				}

			}
		});
		// giv = new ImageView(this);
		giv2 = new ImageView(this);
		// TextView defult=new TextView(this);
		// defult.setText("      Defult    ");
		TextView present = new TextView(this);
		present.setText("Draw this gesture");
		// l_lout1=new LinearLayout(this);
		// l_lout1.setOrientation(LinearLayout.VERTICAL);
		// l_lout1.setLayoutParams(new
		// LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
		// LinearLayout.LayoutParams.WRAP_CONTENT));
		// l_lout1.addView(giv);
		// l_lout1.addView(defult, lpara);
		// l_lout1.setPadding(30, 30, 30, 30);

		l_lout2 = new LinearLayout(this);
		l_lout2.setOrientation(LinearLayout.VERTICAL);
		l_lout2.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		l_lout2.addView(giv2);
		l_lout2.addView(present, lpara);
		l_lout2.setPadding(30, 30, 30, 30);

		m_l_lout = new LinearLayout(this);
		m_l_lout.setOrientation(LinearLayout.HORIZONTAL);
		m_l_lout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		// m_l_lout.addView(l_lout1);
		m_l_lout.addView(l_lout2);

		fout.addView(gov, fpara2);
		fout.addView(lout2, fpara1);
		m_fout.addView(fout, fpara4);
		m_fout.addView(m_l_lout, fpara5);
		m_fout.addView(ban_eng, fpara6);

		/*
		 * myLib2 = GestureLibraries.fromRawResource(this, R.raw.gestures); if
		 * (myLib2.load()) { // Toast.makeText(context, "loading...",
		 * Toast.LENGTH_SHORT).show(); }
		 */

		ges_name = 10;
		smile = 71;
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
				giv2.setImageBitmap(gesture.get(0).toBitmap(100, 100, 5,
						Color.YELLOW));

			} else {
				giv2.setImageResource(R.drawable.no_gesture);
			}
		} else {
			giv2.setImageResource(R.drawable.no_gesture);
		}

		myLib_eng = GestureLibraries.fromRawResource(this, R.raw.eng_gestures);
		myLib_eng.load();

		myLib_smile = GestureLibraries.fromRawResource(this,
				R.raw.smile_gestures);
		myLib_smile.load();

		// gesture = myLib2.getGestures(String.valueOf(ges_name));
		// giv.setImageBitmap(gesture.get(0).toBitmap(100, 100, 5,
		// Color.YELLOW));

		setContentView(m_fout);
	}

}
