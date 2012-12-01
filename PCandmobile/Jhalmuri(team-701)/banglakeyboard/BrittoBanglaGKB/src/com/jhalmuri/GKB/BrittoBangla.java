package com.jhalmuri.GKB;

//import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.gesture.Prediction;
import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

public class BrittoBangla extends InputMethodService implements
		OnGestureListener, OnClickListener {

	private FrameLayout fout; // main UI layout
	private GestureLibrary myLib, myLib_e, myLib_smile;
	private DisplayMetrics dm;
	private Ui ui; // user interface layout object
	private int b = 0; // control shift key
	private int be = 0; // control ban_eng key
	private int smile = 0;
	private int sym = 0; // control symbol key
	private ArrayList<Prediction> predictions;

	// private final File mStoreFile = new
	// File(Environment.getExternalStorageDirectory(), "gesture");
	public final static String alph = "অআইঈউঋএঐতহঋডঈধঞওঔজ্ৎািীুূৃেোঁঊ‌ঁৌকখগগঘঙচছঝটঠঢণথদনপপফবভমযরলশষসসড়ঢ়য়ংঃঁ@১২৩৪৫৬৭৮৯০.:"
			+ "D)(;pৈ";
	public static String pasted = "";
	public static String past = "";
	public static String present = "";
	public static int c = 0;
	private int o = 0;
	public final static int code[] = { 1011, 1012, 1314, 1514, 1614, 1711, 18,
			1819, 10, 13, 17, 16, 15, 36, 1830, 20, 2019, 1628, 49, 47, 50,
			5051, 5052, 53, 54, 55, 56, 5650, 57, 161460, 59, 5748, 565014, 21,
			22, 23, 2311, 24, 25, 26, 27, 2911, 3114, 32, 31, 33, 34, 35, 37,
			38, 3811, 39, 29, 40, 41, 42, 2948, 43, 44, 45, 46, 4611, 1648,
			3148, 4248, 4849, 48, 5748, 58, 61, 62, 63, 64, 65, 66, 67, 68, 69,
			70, 71, 7171, 72, 73, 74, 7175, 76, 5614 };
	private final String num = "JABCDEFGHI";
	private String dp = "([0-9]*)";
	private String ap = "([a-z])";
	private String Cap = "([A-Z])";
	private Input_gesture ig;
	public static InputConnection ic;

	@Override
	public void onCreate() {

		super.onCreate();
	}

	@Override
	public View onCreateInputView() {

		myLib = GestureLibraries.fromFile("/sdcard/ban_gestures"); // load the
																	// gesture
																	// library
		if (myLib.load()) {
			Toast.makeText(this, "loading...", Toast.LENGTH_SHORT).show();

		}

		myLib_e = GestureLibraries.fromRawResource(this, R.raw.eng_gestures);
		myLib_e.load();

		myLib_smile = GestureLibraries.fromRawResource(this,
				R.raw.smile_gestures);
		myLib_smile.load();

		fout = new FrameLayout(this);
		fout.setBackgroundResource(R.drawable.bg);

		b = 0;
		be = 0;
		ui = new Ui(this);
		ui.gv.setGesture(new Gesture());
		ui.gv.setGestureColor(Color.CYAN);

		// Toast.makeText(this, String.valueOf(ui.dm.widthPixels),
		// Toast.LENGTH_SHORT).show();
		ig = new Input_gesture();

		dm = this.getResources().getDisplayMetrics();

		FrameLayout.LayoutParams fpara1 = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
						| Gravity.TOP);
		FrameLayout.LayoutParams fpara2 = new FrameLayout.LayoutParams(
				dm.widthPixels,
				(int) (200.0 * (float) (dm.densityDpi / 160.0)), Gravity.LEFT
						| Gravity.TOP);
		FrameLayout.LayoutParams fpara3 = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT
						| Gravity.TOP);
		FrameLayout.LayoutParams fpara4 = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT
						| Gravity.BOTTOM);
		FrameLayout.LayoutParams fpara5 = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
						| Gravity.BOTTOM);

		fout.addView(ui.gv, fpara2);
		fout.addView(ui.lout2, fpara1);
		fout.addView(ui.mslout1, fpara3);
		fout.addView(ui.mslout2, fpara3);
		fout.addView(ui.sml_lout, fpara3);
		fout.addView(ui.ban_eng_sym, fpara4);
		// fout.addView(ui.imgb9,fpara5);
		return fout;
	}

	public void onClick(View arg0) {
		// TODO onClick
		ic = getCurrentInputConnection();
		ui.uifunction_onClick(arg0, ic);

		if (arg0.getId() == 2) { // symbol key
			if (sym == 3) {
				ui.imgb2.setImageResource(R.drawable.sym_n);
				ui.sml_lout.setVisibility(View.GONE);
				smile = 0;
				sym = 0;
			} else if (sym == 2) {
				ui.imgb2.setImageResource(R.drawable.smile);
				ui.mslout2.setVisibility(View.GONE);
				ui.sml_lout.setVisibility(View.VISIBLE);
				smile = 1;
				sym = 3;
			} else if (sym == 1) {
				ui.imgb2.setImageResource(R.drawable.sym_n_on2);
				ui.mslout1.setVisibility(View.GONE);
				ui.mslout2.setVisibility(View.VISIBLE);
				sym = 2;
			} else if (sym == 0) {
				ui.imgb2.setImageResource(R.drawable.sym_n_on);
				ui.mslout1.setVisibility(View.VISIBLE);
				sym = 1;
			}
		} else if (arg0.getId() == 4) { // shift key
			if (b == 0) {
				ui.imgb4.setImageResource(R.drawable.shift_on1);
				b = 1;
			} else {
				ui.imgb4.setImageResource(R.drawable.shift_off1);
				b = 0;
			}
		} else if (arg0.getId() == 3) { // ban_eng key
			if (be == 0) {
				ui.imgb3.setImageResource(R.drawable.eng);
				ui.imgb4.setVisibility(View.VISIBLE);
				be = 1;

			} else {
				ui.imgb3.setImageResource(R.drawable.ban);
				ui.imgb4.setVisibility(View.GONE);
				be = 0;
			}
		} else if (arg0.getId() == 9) {
			List<InputMethodInfo> InputMethods = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
					.getEnabledInputMethodList();
			int i = 0;
			for (; i < InputMethods.size(); i++) {
				if (InputMethods.get(i).getServiceName()
						.equals("fahim.rana.protity.SoftKeyboard"))
					break;
			}
			String NewInputMethodName = InputMethods.get(i).getId();
			switchInputMethod(NewInputMethodName);

		}
	}

	public void onGesture(GestureOverlayView arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub

	}

	public void onGestureCancelled(GestureOverlayView arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub

	}

	public void onGestureEnded(GestureOverlayView arg0, MotionEvent arg1) {

		ic = getCurrentInputConnection();
		/*
		 * if(smile==1){ predictions = myLib_smile.recognize(arg0.getGesture());
		 * }
		 * 
		 * else
		 */if (be == 1) {
			predictions = myLib_e.recognize(arg0.getGesture());
		} else if (be == 0) {
			predictions = myLib.recognize(arg0.getGesture());
		}

		// We want at least one prediction
		if (predictions.size() > 0) {
			Prediction prediction = predictions.get(0);
			// We want at least some confidence in the result
			if (prediction.score > 2.0) {

				present = prediction.name;
				if (Pattern.matches(ap, present)) {
					if (b == 0) {// lower case
						ic.commitText(present, 1);
					} else if (b == 1) {// upper case
						ic.commitText(present.toUpperCase(), 1);
					}
				}

				if (Pattern.matches(Cap, present)) {
					for (int i = 0; i < num.length(); i++) {
						if (present.equals(String.valueOf(num.charAt(i)))) {
							ic.commitText(String.valueOf(i), 1);
						}
					}
				}

				if (Pattern.matches(dp, present)) {
					if (c == 2) {
						for (int i = 0; i < code.length; i++) {
							if (Integer.parseInt(pasted + past + present) == code[i]) {
								ic.deleteSurroundingText(1, 0);
								ic.commitText(String.valueOf(alph.charAt(i)), 1);
								ui.gv.setGesture(new Gesture());
								ui.gv.setGestureColor(Color.CYAN);
								c = 3;
							}
						}
					}

					if (c == 1) {
						for (int i = 0; i < code.length; i++) {
							if (Integer.parseInt(past + present) == code[i]) {
								ic.deleteSurroundingText(1, 0);
								ic.commitText(String.valueOf(alph.charAt(i)), 1);
								ui.gv.setGesture(new Gesture());
								ui.gv.setGestureColor(Color.CYAN);
								c = 2;
								if (Integer.parseInt(past + present) == 5650
										|| Integer.parseInt(past + present) == 4848
										|| Integer.parseInt(past + present) == 2948
										|| Integer.parseInt(past + present) == 1648
										|| Integer.parseInt(past + present) == 3148
										|| Integer.parseInt(past + present) == 4248
										|| Integer.parseInt(past + present) == 5748
										|| Integer.parseInt(past + present) == 4849
										|| Integer.parseInt(past + present) == 7171) {
									o = -1;
								}
							}
						}

					}

					if (o != -1) {
						for (int i = 0; i < code.length; i++) {
							if (Integer.parseInt(present) == code[i]) {
								ic.commitText(String.valueOf(alph.charAt(i)), 1);
								c = 1;
								if (Integer.parseInt(present) == 58) {// symbol
									ui.mslout1.setVisibility(View.VISIBLE);
									ui.imgb2.setImageResource(R.drawable.sym_n_on);
									sym = 1;
									ic.deleteSurroundingText(1, 0);
								}
							}
						}
					}

					pasted = past;

					past = present;
					o = 0;

				}
			}
		}

	}

	public void onGestureStarted(GestureOverlayView arg0, MotionEvent arg1) {
		if (!myLib.load()) {
			Toast.makeText(this, "Input or reset gestures", Toast.LENGTH_SHORT)
					.show();
		}
		if (ig.isGestureSaved) {
			if (myLib.load()) {
				Toast.makeText(this, "loading...", Toast.LENGTH_SHORT).show();

			}
			ig.isGestureSaved = false;
		}
		
			ui.imgb2.setImageResource(R.drawable.sym_n);
			ui.mslout1.setVisibility(View.GONE);
			ui.mslout2.setVisibility(View.GONE);
			ui.sml_lout.setVisibility(View.GONE);
			sym = 0;
		
		// new code
		ic = getCurrentInputConnection();
		if (ic.getTextBeforeCursor(1, 0).equals("")) {
			BrittoBangla.c = 0;
			BrittoBangla.past = "";
			BrittoBangla.pasted = "";
		} else {
			CharSequence crnt_txt = BrittoBangla.ic.getTextBeforeCursor(1, 0);
			int i = 0;
			for (; i < BrittoBangla.alph.length(); i++) {
				if (crnt_txt.charAt(0) == BrittoBangla.alph.charAt(i))
					break;
			}
			if (i != BrittoBangla.alph.length()) {
				int code = BrittoBangla.code[i];
				if (code > 99999 && code < 999999) {
					BrittoBangla.past = "";
					BrittoBangla.pasted = "";
					BrittoBangla.c = 0;
				} else if (code > 999 && code < 9999) {
					BrittoBangla.past = String.valueOf(code).substring(2, 4);
					BrittoBangla.pasted = String.valueOf(code).substring(0, 2);
					BrittoBangla.c = 2;
				} else if (code > 0 && code < 99) {
					BrittoBangla.past = String.valueOf(code);
					BrittoBangla.pasted = "";
					BrittoBangla.c = 1;
				}
			}// end

		}
		// new code end

	}

}
