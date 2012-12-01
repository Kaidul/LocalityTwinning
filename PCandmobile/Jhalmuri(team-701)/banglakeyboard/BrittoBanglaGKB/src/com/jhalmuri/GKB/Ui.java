package com.jhalmuri.GKB;

import java.io.IOException;
import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;

public class Ui {
	public GestureOverlayView gv;
	public LinearLayout ban_eng;
	public LinearLayout ban_eng_sym;
	public ImageView imgb2;
	public ImageView imgb3;
	public ImageView imgb4;
	public ImageView imgb5;
	public ImageView imgb6;
	public ImageView imgb7;
	public ImageView imgb8;
	public ImageView imgb9;
	public ImageView imgb10;
	public LinearLayout lout2;
	public LinearLayout lout3;
	public LinearLayout lout4;
	static final String symbol = "।.,@_?!;:'\"()/" +
			"*+-={}&[]<>$#~^";
	public LinearLayout slout[];
	public LinearLayout mslout1,mslout2;
	public HorizontalScrollView scr1,scr2;
	public TextView[] tva;	
	public static DisplayMetrics dm;
	public int p_c; //padding constant
	public ImageView sml_vw[];
	public String smile_sym[]={":)",":D",";)",":p",":(",":'(","","","","","","","",""};
	public LinearLayout sml_lout;
	
	Ui(Context context){
		gv= new GestureOverlayView(context);
		gv.setGestureStrokeType(1);
		gv.setEventsInterceptionEnabled(true);
		gv.addOnGestureListener((android.gesture.GestureOverlayView.OnGestureListener) context);
		gv.setFadeOffset(100);
		gv.setGestureColor(Color.CYAN);
		//gv.setBackgroundResource(R.drawable.bg);
	
		 
		dm = context.getResources().getDisplayMetrics();
		
		android.view.ViewGroup.LayoutParams paraimg=new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		
		p_c=(int)(4.0*(float)(dm.densityDpi/160.0));	
		
		
		
		imgb2=new ImageView(context);
		imgb2.setImageResource(R.drawable.sym_n);
		imgb2.setPadding(p_c, p_c, p_c, p_c);
		imgb2.setLayoutParams(paraimg);
		imgb2.setOnClickListener((OnClickListener) context);
		imgb2.setId(2);
		
		imgb3=new ImageView(context);
		imgb3.setImageResource(R.drawable.ban);
		imgb3.setPadding(p_c, p_c, p_c, p_c);
		imgb3.setLayoutParams(paraimg);
		imgb3.setOnClickListener((OnClickListener) context);
		imgb3.setId(3);
		
		imgb4=new ImageView(context);
		imgb4.setImageResource(R.drawable.shift_off1);
		imgb4.setPadding(p_c, p_c, p_c, p_c);
		imgb4.setOnClickListener((OnClickListener) context);
		imgb4.setVisibility(View.GONE);
		imgb4.setId(4);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		ban_eng = new LinearLayout(context);
		ban_eng.setLayoutParams(params);
		ban_eng.setGravity(Gravity.LEFT);
		ban_eng.addView(imgb3);
		ban_eng.addView(imgb4);
		
		ban_eng_sym = new LinearLayout(context);
		ban_eng_sym.setOrientation(LinearLayout.VERTICAL);
		ban_eng_sym.setGravity(Gravity.LEFT);
		ban_eng_sym.addView(imgb2);
		ban_eng_sym.addView(ban_eng);
		
		imgb5=new ImageView(context);
		imgb5.setImageResource(R.drawable.l_arro);
		imgb5.setLayoutParams(paraimg);
		imgb5.setPadding(p_c, p_c, p_c, p_c);
		imgb5.setOnClickListener((OnClickListener) context);
		imgb5.setId(5);
		
		imgb6=new ImageView(context);
		imgb6.setImageResource(R.drawable.r_arro);
		imgb6.setLayoutParams(paraimg);
		imgb6.setPadding(p_c, p_c, p_c, p_c);
		imgb6.setOnClickListener((OnClickListener) context);
		imgb6.setId(6);
		
		imgb7=new ImageView(context);
		imgb7.setImageResource(R.drawable.space2);
		imgb7.setLayoutParams(paraimg);
		imgb7.setPadding(p_c, p_c, p_c, p_c);
		imgb7.setOnClickListener((OnClickListener) context);
		imgb7.setId(7);
		
		imgb8=new ImageView(context);
		imgb8.setImageResource(R.drawable.del);
		imgb8.setLayoutParams(paraimg);
		imgb8.setPadding(p_c, p_c, p_c, p_c);
		imgb8.setOnClickListener((OnClickListener) context);
		imgb8.setId(8);
		
		imgb9=new ImageView(context);
		imgb9.setImageResource(R.drawable.keyboard);
		imgb9.setPadding(p_c, p_c, p_c, p_c);
		imgb9.setLayoutParams(paraimg);
		imgb9.setOnClickListener((OnClickListener) context);
		imgb9.setId(9);
		
		imgb10=new ImageView(context);
		imgb10.setImageResource(R.drawable.enter);
		imgb10.setPadding(p_c, p_c, p_c, p_c);
		imgb10.setLayoutParams(paraimg);
		imgb10.setOnClickListener((OnClickListener) context);
		imgb10.setId(10);
		
		lout3= new LinearLayout(context);
		lout3.setLayoutParams(params);
		lout3.setOrientation(LinearLayout.HORIZONTAL);
		lout3.setGravity(Gravity.RIGHT);
		lout3.addView(imgb5);
		lout3.addView(imgb6);
		
		lout4= new LinearLayout(context);
		lout4.setLayoutParams(params);
		lout4.setOrientation(LinearLayout.HORIZONTAL);
		lout4.setGravity(Gravity.RIGHT);
		lout4.addView(imgb10);
		lout4.addView(imgb8);
		
		
		lout2= new LinearLayout(context);
		lout2.setOrientation(LinearLayout.VERTICAL);
		lout2.setGravity(Gravity.RIGHT);
		lout2.addView(lout4);
		lout2.addView(imgb7);
		//lout2.addView(lout4);
		
		
		
		//symboll start.....................................................................
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
	            "fonts/Sola.ttf");
		slout=new LinearLayout[5];
		for(int i=0;i<5;i++){
			slout[i]=new LinearLayout(context);
		}
		mslout1=new LinearLayout(context);
		mslout1.setOrientation(LinearLayout.VERTICAL);
		mslout1.setBackgroundColor(Color.argb(90, 0, 0, 0));
		mslout2=new LinearLayout(context);
		mslout2.setOrientation(LinearLayout.VERTICAL);
		mslout2.setBackgroundColor(Color.argb(90, 0, 0, 0));
		tva =new TextView[symbol.length()];
		for(int i=0;i<symbol.length();i++){
	       tva[i]= new TextView(context);
	       tva[i].setTypeface(tf);
	       tva[i].setId(1000);
	       tva[i].setTag(String.valueOf(symbol.charAt(i)));
	       tva[i].setOnClickListener((OnClickListener) context);
	       
	       if(dm.widthPixels>=480){
	    	   tva[i].setTextSize(25);
	    	   tva[i].setPadding(15, 0, 15, 0);
	       }
	       else if(dm.widthPixels>=320&&dm.widthPixels<480){
	    	   tva[i].setTextSize(25);
	    	   tva[i].setPadding(10, 0, 10, 0);
	       }
	       
	       else if(dm.widthPixels==240){
	    	   tva[i].setTextSize(15);
	    	   tva[i].setPadding(7, 0, 7, 0);
	       }
	       tva[i].setTextColor(Color.CYAN);
	       tva[i].setText(String.valueOf(symbol.charAt(i)));
	       
	      }
	      int s=0;
	     for(int i=0;i<symbol.length();i++){
	    	  if(symbol.charAt(i)==';'){
	    		  s=1;
	    	  }else if(symbol.charAt(i)=='*'){
	    		  s=2;
	    	  }else if(symbol.charAt(i)=='['){
	    		  s=3;
	    	  }else if(symbol.charAt(i)=='^'){
	    		  s=4;
	    	  }
	    	  slout[s].addView(tva[i]);
	    	  
	      }
	      mslout1.addView(slout[0]);
	      mslout1.addView(slout[1]);
	      mslout1.addView(slout[2]);
	      mslout1.setVisibility(View.GONE);
	      mslout2.addView(slout[3]);
	      mslout2.addView(slout[4]);
	      mslout2.setVisibility(View.GONE);
	    //symbol end...................................................................................
	  	//smiley start.................................................................................
	  	
	  	sml_vw=new ImageView[smile_sym.length];
	  	for (int i=0;i<smile_sym.length;i++){
	  		sml_vw[i]=new ImageView(context);
	  		sml_vw[i].setId(561);
	  		sml_vw[i].setTag(smile_sym[i]);
	  		sml_vw[i].setOnClickListener((OnClickListener) context);
	  		sml_vw[i].setPadding(p_c, p_c, p_c, p_c);
	  	}
	  	sml_vw[0].setImageResource(R.drawable.s1);
	  	sml_vw[1].setImageResource(R.drawable.s2);
	  	sml_vw[2].setImageResource(R.drawable.s3);
	  	sml_vw[3].setImageResource(R.drawable.s4);
	  	sml_vw[4].setImageResource(R.drawable.s5);
	  	sml_vw[5].setImageResource(R.drawable.s6);
	  	
	  	sml_lout=new LinearLayout(context);
	  	sml_lout.setOrientation(LinearLayout.HORIZONTAL);
	  	sml_lout.setBackgroundColor(Color.argb(90, 0, 0, 0));
	  	for(int f=0;f<6;f++){
	  		sml_lout.addView(sml_vw[f]);
	  	}
	  	sml_lout.setVisibility(View.GONE);
	  	//smiley end...................................................................................
	  	
	}
	
	
	
	public void uifunction_onClick(View arg0, InputConnection ic){
		 if(arg0.getId()==5) {
			try{
	    		if(ic != null){
	    			String strbeforeCursor = ic.getTextBeforeCursor(1000000000, 0).toString();
	    			if(strbeforeCursor.length()>0)
	    				ic.setSelection(strbeforeCursor.length()-1, strbeforeCursor.length()-1);
	    			/*try {
						Write_thr_cls.out_strm.write(1);   //backspace
					} catch (IOException e) {
						
						e.printStackTrace();
					}*/
	    		}
	    	}catch (Exception e) {
				// TODO: handle exception
			}
	
		}
		else if(arg0.getId()==6){
			
			try{
	        	String strbeforeCursor = ic.getTextBeforeCursor(1000000000, 0).toString();
	        	ic.setSelection(strbeforeCursor.length()+1, strbeforeCursor.length()+1);
	        	/*if(Write_thr_cls.out_strm!=null){
	        	try {
					Write_thr_cls.out_strm.write(2);   //backspace
				} catch (IOException e) {
					
					e.printStackTrace();
				}
	        	}*/

	    	}catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		else if (arg0.getId() == 7) {
			ic.commitText(" ", 1);
		} 
		else if (arg0.getId()==8){
			//ic.deleteSurroundingText(1, 0);
			/*if(Write_thr_cls.out_strm!=null){
			try {
				Write_thr_cls.out_strm.write(8);   //backspace
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			}*/
			KeyEvent down_back=new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_DEL);
			//KeyEvent up_enter=new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_ENTER);
			ic.sendKeyEvent(down_back);
			//ic.sendKeyEvent(up_enter);
			
		}
		else if (arg0.getId() == 10) {
			KeyEvent down_enter=new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER);
			//KeyEvent up_enter=new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_ENTER);
			ic.sendKeyEvent(down_enter);
			//ic.sendKeyEvent(up_enter);
		} 
		
		else if(arg0.getId()==1000){
			ic.commitText(String.valueOf(arg0.getTag()), 1);
		}
		else if (arg0.getId()==561){
			ic.commitText(String.valueOf(arg0.getTag()), 1);
		}
			
	}	
	
	}