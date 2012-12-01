/*
 * Copyright (C) 2008-2009 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.jhalmuri.PKB;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;





/**
 * Example of writing an input method for a soft keyboard.  This code is
 * focused on simplicity over completeness, so it should in no way be considered
 * to be a complete soft keyboard implementation.  Its purpose is to provide
 * a basic example for how you would get started writing an input method, to
 * be fleshed out as appropriate.
 */
public class Pht_SoftKeyboard extends InputMethodService 
        implements KeyboardView.OnKeyboardActionListener {
    static final boolean DEBUG = false;
    
    /**
     * This boolean indicates the optional example code for performing
     * processing of hard keys in addition to regular text generation
     * from on-screen interaction.  It would be used for input methods that
     * perform language translations (such as converting text entered on 
     * a QWERTY keyboard to Chinese), but may not be used for input methods
     * that are primarily intended to be used for on-screen text entry.
     */
    static final boolean PROCESS_HARD_KEYS = true;
    
    private KeyboardView mInputView;
    private LinearLayout mCandidateAndConfigLayout;
    private Pht_CandidateView mCandidateView;
    private CompletionInfo[] mCompletions;
    private BanglaPreviewView mBanglaPreviewView;
    EditorInfo attributer = null;
    int mark=0;
   
    ArrayList<String> list = new ArrayList<String>();
	ArrayList<String> sptype = new ArrayList<String>();	

    private StringBuilder mComposing = new StringBuilder();

    StringBuilder cw = new StringBuilder();
 
    HashMap<String, String> srb;
    HashMap<String, String> kar;
    HashMap<String, String> bbr;
    HashMap<String, String> jbr;
    HashMap<String, String> other;
    
    String schar[]={"A","W","w","F","E","V","N","a","i","I","u","U","e","o","O","k","g","c","j","T","D","t","d","n","p","f","b","v","m","z","r","l","S","s","h","R","y","Y",":","q","Q","P","G","H","J","K","L","C","V","B","M","Z","x","X","^"};
    String dchar[]={"rZ","kt","hh","HH","SH","tT","hl","hr","hZ","hy","hm","hw","sp","sn","st","sT","hn","hN","sl","sr","sZ","sy","sm","sw","sf","Sl","Sr","SZ","Sy","Sm","Sw","Sn","St","sk","lg","lk","zZ","zy","ml","Sc","ll","lZ","ly","lm","lv","lw","lb","lp","lD","lT","bl","br","bZ","by","bb","bd","bj","fl","mr","mZ","my","mm","mv","mw","mb","mf","mp","mn","vl","vr","vZ","vy","nw","nn","fr","ps","pl","pr","pZ","py","pp","pn","pt","pT","ns","nZ","ny","nm","dr","dZ","dy","dm","dv","dw","nd","nt","nD","nT","NZ","Ny","Nm","Nw","Nn","dd","dg","tr","tZ","ty","tm","tw","tn","tt","TT","nj","ee","oo","OI","OU","kh","gh","Ng","ch","jh","NG","Th","Dh","th","dh","ph","bh","sh","Sh","Rh","ng","kk","kT","ktr","kw","km","ky","kZ","kr","kl","kx","ks","gN","dn","gn","gw","gm","gy","gZ","gr","gl","cc"};
    String tchar[]={"nTr","ngo","sth","stZ","sty","stw","skh","sTr","skr","skl","sph","ShT","Shk","shl","shr","shZ","shy","shm","shw","shn","sht","Shm","Shw","Shf","Shp","ShN","rrg","rrk","Sch","shc","lbh","ldh","bdh","phl","mvr","mbh","mph","mpr","mth","bhl","bhr","bhZ","bhy","ndh","ndr","ndw","phr","dbh","ddh","ddw","ndZ","ndy","nth","ntr","ntZ","nty","ntw","nTh","dhr","dhZ","dhy","dhm","dhw","dhn","dgh","thr","thZ","thy","thw","tmZ","tmy","tth","ttw","NGj","nch","kkh","kxw","kxN","kxm","kxy","kxZ","khy","khZ","khr","gdh","gny","gnZ","ghy","ghZ","ghr","Ngk","nk","nky","nkZ","Ngg","Ngm","cch"};
    String qchar[]={"hrri","sthZ","sthy","Shkr","Shph","Shpr","ShTh","ShTr","ShTZ","ShTy","rrkhy","rrkZ","rrky","rrkh","shch","mbhr","ndhr","ndhZ","ndhy","NGch","kkhw","kkhN","kkhm","kkhy","kkhZ","Ngky","NgkZ","Ngkx","Ngkh","Nggy","NggZ","Nggh","cchw"};
    String fchar[]={"ShThZ","ShThy","rrkhZ","Ngkkh","Ngghy","NgghZ","Ngghr"};
    
    private boolean mCompletionOn;
    private int mLastDisplayWidth;
    private boolean mCapsLock;
    private long mLastShiftTime;
    private long mMetaState;
    
    private Pht_ProtitiPhoneticKeyboard mSymbolsKeyboard;

    private Pht_ProtitiPhoneticKeyboard mQwertyKeyboard;
    
    private Pht_ProtitiPhoneticKeyboard mCurKeyboard;
    
    private String mWordSeparators;
    
    /**
     * Main initialization of the input method component.  Be sure to call
     * to super class.
     */
    @Override public void onCreate() {
        super.onCreate();
        sptype.add(" ");
        
        sptype.add("1");
        sptype.add("2");
        sptype.add("3");
        sptype.add("4");
        sptype.add("5");
        sptype.add("6");
        sptype.add("7");
        sptype.add("8");
        sptype.add("9");
        sptype.add("0");
        sptype.add("@");
        sptype.add("$");
        sptype.add("%");
        sptype.add("&");
        sptype.add("*");
        sptype.add("-");
        sptype.add("=");
        sptype.add("(");
        sptype.add(")");
        sptype.add("!");
        sptype.add("\"");
        sptype.add("'");
        sptype.add(";");
        sptype.add("/");
        sptype.add("?");
        
    
        
        
        
        
        //Sorborner mapping
        srb=new HashMap<String, String>();
        
        srb.put("o","অ");
        srb.put("a","আ");
        srb.put("A","আ");
        srb.put("i","ই");
        srb.put("I","ঈ");
        srb.put("ee","ঈ");
        srb.put("u","উ");
        srb.put("oo","উ");
        srb.put("U","ঊ");
        srb.put("rri","ঋ");
        srb.put("e","এ");
        srb.put("E","এ");
        srb.put("OI","ঐ");
        srb.put("O","ও");
        srb.put("OU","ঔ");
//Sorborner mapping     
        
//kar mapping
      //Sorborner mapping
        kar=new HashMap<String, String>();
        kar.put("a","া");
        kar.put("A","া");
        kar.put("i","ি");
        kar.put("I","ী");
        kar.put("ee","ী");
        kar.put("u","ু");
        kar.put("oo","ু");
        kar.put("U","ূ");
        kar.put("rri","ৃ");
        kar.put("e","ে");
        kar.put("E","ে");
        kar.put("OI","ৈ");
        kar.put("O","ো");
     //   kar.put("oi","ই");
        kar.put("OU","ৌ");
//kar mapping
        
//Sorborner mapping   

        
//Sorborner mapping   
     
      //Banjonborner mapping
        bbr=new HashMap<String, String>();
        bbr.put("F","ফ");
        bbr.put("SH", "ষ");
        bbr.put("V","ভ");
        bbr.put("tT", "ৎ");
        bbr.put("0", "০");
        bbr.put("1", "১");
        bbr.put("2", "২");
        bbr.put("3", "৩");
        bbr.put("4", "৪");
        bbr.put("5", "৫");
        bbr.put("6", "৬");
        bbr.put("7", "৭");
        bbr.put("8", "৮");
        bbr.put("9", "৯");
        bbr.put("k", "ক");
        bbr.put("hh", "্");
        bbr.put("HH", "্‌");
        bbr.put("kh", "খ");
        bbr.put("g", "গ");
        bbr.put("gh", "ঘ");
        bbr.put("Ng", "ঙ");
        bbr.put("c", "চ");
        bbr.put("ch", "ছ");
        bbr.put("j", "জ");
        bbr.put("jh", "ঝ");
        bbr.put("NG", "ঞ");
        bbr.put("T", "ট");
        bbr.put("Th", "ঠ");
        bbr.put("D", "ড");
        bbr.put("Dh", "ঢ");
        bbr.put("N", "ণ");
        bbr.put("t", "ত");
        bbr.put("th", "থ");
        bbr.put("d", "দ");
        bbr.put("dh", "ধ");
        bbr.put("n", "ন");
        bbr.put("p", "প");
        bbr.put("ph", "ফ");
        bbr.put("f", "ফ");
        bbr.put("b", "ব");
        bbr.put("bh", "ভ");
        bbr.put("v", "ভ");
        bbr.put("m", "ম");
        bbr.put("z", "য");
        bbr.put("r", "র");
        bbr.put("l", "ল");
        bbr.put("sh", "শ");
        bbr.put("S", "শ");
        bbr.put("Sh", "ষ");
        bbr.put("s", "স");
        bbr.put("h", "হ");
        bbr.put("R", "ড়");
        bbr.put("Rh", "ঢ়");
        bbr.put("y", "য়");
        bbr.put("Y", "য়");
        bbr.put("ng", "ং");
        bbr.put(":", "ঃ");
        bbr.put("q", "ক");
        bbr.put("Q", "ক");
        bbr.put("P", "প");
        bbr.put("G", "গ");
        bbr.put("H", "হ");
        bbr.put("J", "জ");
        bbr.put("K", "ক");
        bbr.put("L", "ল");
        bbr.put("C", "চ");
        bbr.put("B", "ব");
        bbr.put("M", "ম");
        bbr.put("Z", "্য");
        bbr.put("x", "এক্স");
        bbr.put("X", "এক্স");
        bbr.put("^","ঁ");
        bbr.put("w", "ও");
        bbr.put("W", "ও");
        //jbr.put("W","্‌ব");
        
//Banjonborner mapping


        //jukto mapping
        jbr=new HashMap<String, String>();
        
        
        jbr.put("kk","ক্ক");
        jbr.put("ngo","ঙ্গ");
        jbr.put("kT","ক্ট");
        jbr.put("kt","ক্ত");
        jbr.put("ktr","ক্ত্র");
        jbr.put("kw","ক্ব");
        jbr.put("km","ক্ম");
        jbr.put("ky","ক্য");
        jbr.put("kZ","ক্য");
        jbr.put("kr","ক্র");
        jbr.put("kl","ক্ল");
        jbr.put("kkh","ক্ষ");
        jbr.put("kx","ক্ষ");
        jbr.put("kkhw","ক্ষ্ব");
        jbr.put("kxw","ক্ষ্ব");
        jbr.put("kkhN","ক্ষ্ণ");
        jbr.put("kxN","ক্ষ্ণ");
        jbr.put("kkhm","ক্ষ্ম");
        jbr.put("kxm","ক্ষ্ম");
        jbr.put("kkhy","ক্ষ্য");
        jbr.put("kxy","ক্ষ্য");
        jbr.put("kkhZ","ক্ষ্য");
        jbr.put("kxZ","ক্ষ্য");
        jbr.put("ks","ক্স");
        jbr.put("khy","খ্য");
        jbr.put("khZ","খ্য");
        jbr.put("khr","খ্র");
        jbr.put("gN","গ্ণ");
        jbr.put("gdh","গ্ধ");
        jbr.put("gn","গ্ন");
        jbr.put("gny","গ্ন্য");
        jbr.put("gnZ","গ্ন্য");
        jbr.put("gw","গ্ব");
        jbr.put("gm","গ্ম");
        jbr.put("gy","গ্য");
        jbr.put("gZ","গ্য");
        jbr.put("gr","গ্র");
        jbr.put("gl","গ্ল");
        jbr.put("ghn","ঘ্ন");
        jbr.put("ghy","ঘ্য");
        jbr.put("ghZ","ঘ্য");
        jbr.put("ghr","ঘ্র");
        jbr.put("Ngk","ঙ্ক");
        jbr.put("nk","ঙ্ক");
        jbr.put("nky","ঙ্ক্য");
        jbr.put("Ngky","ঙ্ক্য");
        jbr.put("nkZ","ঙ্ক্য");
        jbr.put("NgkZ","ঙ্ক্য");
        jbr.put("Ngkkh","ঙ্ক্ষ");
        jbr.put("Ngkx","ঙ্ক্ষ");
        jbr.put("Ngkh","ঙ্খ");
        jbr.put("Ngg","ঙ্গ");
        jbr.put("Nggy","ঙ্গ্য");
        jbr.put("NggZ","ঙ্গ্য");
        jbr.put("Ngm","ঙ্ম");
        jbr.put("Nggh","ঙ্ঘ");
        jbr.put("Ngghy","ঙ্ঘ্য");
        jbr.put("NgghZ","ঙ্ঘ্য");
        jbr.put("Ngghr","ঙ্ঘ্র");
        jbr.put("cc","চ্চ");
        jbr.put("cch","চ্ছ");
        jbr.put("cchw","চ্ছ্ব");
        jbr.put("cchr","চ্ছ্র");
        jbr.put("cNG","চ্ঞ");
        jbr.put("cy","চ্য");
        jbr.put("cZ","চ্য");
        jbr.put("jj","জ্জ");
        jbr.put("jjw","জ্জ্ব");
        jbr.put("jjh","জ্ঝ");
        jbr.put("gg","জ্ঞ");
        jbr.put("jNG","জ্ঞ");
        jbr.put("jw","জ্ব");
        jbr.put("jy","জ্য");
        jbr.put("jZ","জ্য");
        jbr.put("jr","জ্র");
        jbr.put("nc","ঞ্চ");
        jbr.put("NGc","ঞ্চ");
        jbr.put("Tw","ট্ব");
       
        jbr.put("Tm","ট্ম");
        jbr.put("Ty","ট্য");
        jbr.put("TZ","ট্য");
        jbr.put("tr","ট্র");
        jbr.put("DD","ড্ড");
        jbr.put("Dy","ড্য");
        jbr.put("Dz","ড্য");
        jbr.put("Dr","ড্র");
        jbr.put("Dhy","ঢ্য");
        jbr.put("DhZ","ঢ্য");
        jbr.put("Dhr","ঢ্র");
        jbr.put("NT","ণ্ট");
        jbr.put("NTh","ণ্ঠ");
        jbr.put("ND","ণ্ড");
        jbr.put("NDy","ণ্ড্য");
        jbr.put("NDZ","ণ্ড্য");
        jbr.put("NDr","ণ্ড্র");
        jbr.put("NDh","ণ্ঢ");
        jbr.put("nch","ঞ্ছ");
jbr.put("NGch","ঞ্ছ");
jbr.put("nj","ঞ্জ");
jbr.put("NGj","ঞ্জ");
jbr.put("TT","ট্ট");
jbr.put("tt","ত্ত");
jbr.put("ttw","ত্ত্ব");
jbr.put("tth","ত্থ");
jbr.put("tn","ত্ন");
jbr.put("tw","ত্ব");
jbr.put("tm","ত্ম");

jbr.put("tmy","ত্ম্য");
jbr.put("tmZ","ত্ম্য");
jbr.put("ty","ত্য");
jbr.put("tZ","ত্য");
jbr.put("tr","ত্র");
jbr.put("thw","থ্ব");
jbr.put("thy","থ্য");
jbr.put("thZ","থ্য");
jbr.put("thr","থ্র");
jbr.put("dg","দ্গ");
jbr.put("dgh","দ্ঘ");
jbr.put("dd","দ্দ");
jbr.put("Nn","ণ্ন");
jbr.put("Nw","ণ্ব");
jbr.put("Nm","ণ্ম");
jbr.put("Ny","ণ্য");
jbr.put("NZ","ণ্য");
jbr.put("dhn","ধ্ন");
jbr.put("dhw","ধ্ব");
jbr.put("dhm","ধ্ম");
jbr.put("dhy","ধ্য");
jbr.put("dhZ","ধ্য");
jbr.put("dhr","ধ্র");
jbr.put("nT","ন্ট");
jbr.put("nTr","ন্ট্র");
jbr.put("nTh","ন্ঠ");
jbr.put("nD","ন্ড");
jbr.put("nt","ন্ত");
jbr.put("ntw","ন্ত্ব");
jbr.put("nty","ন্ত্য");
jbr.put("ntZ","ন্ত্য");
jbr.put("ntr","ন্ত্র");
jbr.put("nth","ন্থ");
jbr.put("nd","ন্দ");
jbr.put("ndy","ন্দ্য");
jbr.put("ndZ","ন্দ্য");
jbr.put("ddw","দ্দ্ব");
jbr.put("ddh","দ্ধ");
jbr.put("dw","দ্ব");
jbr.put("dv","দ্ভ");
jbr.put("dbh","দ্ভ");
jbr.put("dm","দ্ম");
jbr.put("dy","দ্য");
jbr.put("dZ","দ্য");
jbr.put("dr","দ্র");
jbr.put("nm","ন্ম");
jbr.put("ny","ন্য");
jbr.put("nZ","ন্য");
jbr.put("ns","ন্স");
jbr.put("pT","প্ট");
jbr.put("pt","প্ত");
jbr.put("pn","প্ন");
jbr.put("pp","প্প");
jbr.put("py","প্য");
jbr.put("pZ","প্য");
jbr.put("pr","প্র");
jbr.put("pl","প্ল");
jbr.put("ps","প্স");
jbr.put("fr","ফ্র");
jbr.put("phr","ফ্র");
jbr.put("ndw","ন্দ্ব");
jbr.put("ndr","ন্দ্র");
jbr.put("ndh","ন্ধ");
jbr.put("ndhy","ন্ধ্য");
jbr.put("ndhZ","ন্ধ্য");
jbr.put("ndhr","ন্ধ্র");
jbr.put("nn","ন্ন");
jbr.put("nw","ন্ব");
jbr.put("vy","ভ্য");
jbr.put("vZ","ভ্য");
jbr.put("bhy","ভ্য");
jbr.put("bhZ","ভ্য");
jbr.put("vr","ভ্র");
jbr.put("bhr","ভ্র");
jbr.put("vl","ভ্ল");
jbr.put("bhl","ভ্ল");
jbr.put("mth","ম্থ");
jbr.put("mn","ম্ন");
jbr.put("mp","ম্প");
jbr.put("mpr","ম্প্র");
jbr.put("mf","ম্ফ");
jbr.put("mph","ম্ফ");
jbr.put("mb","ম্ব");
jbr.put("mw","ম্ব");
jbr.put("mv","ম্ভ");
jbr.put("mbh","ম্ভ");
jbr.put("mvr","ম্ভ্র");
jbr.put("mbhr","ম্ভ্র");
jbr.put("mm","ম্ম");
jbr.put("my","ম্য");
jbr.put("mZ","ম্য");
jbr.put("mr","ম্র");
jbr.put("fl","ফ্ল");
jbr.put("phl","ফ্ল");
jbr.put("bj","ব্জ");
jbr.put("bd","ব্দ");
jbr.put("bdh","ব্ধ");
jbr.put("bb","ব্ব");
jbr.put("by","ব্য");
jbr.put("bZ","ব্য");
jbr.put("rZ","র্য্");
jbr.put("br","ব্র");
jbr.put("bl","ব্ল");
jbr.put("lT","ল্ট");
jbr.put("lD","ল্ড");
jbr.put("ldh","ল্ধ");
jbr.put("lp","ল্প");
jbr.put("lb","ল্ব");
jbr.put("lw","ল্ব");
jbr.put("lv","ল্ভ");
jbr.put("lbh","ল্ভ");
jbr.put("lm","ল্ম");
jbr.put("ly","ল্য");
jbr.put("lZ","ল্য");
jbr.put("ll","ল্ল");
jbr.put("shc","শ্চ");
jbr.put("Sc","শ্চ");
jbr.put("shch","শ্ছ");
jbr.put("Sch","শ্ছ");
jbr.put("ml","ম্ল");
jbr.put("zy","য্য");
jbr.put("zZ","য্য");
jbr.put("rrk","র্ক");
jbr.put("rrkh","র্খ");
jbr.put("rrg","র্গ");
jbr.put("rrky","র্ক্য");
jbr.put("rrkZ","র্ক্য");
jbr.put("rrkhy","র্খ্য");
jbr.put("rrkhZ","র্খ্য");
jbr.put("lk","ল্ক");
jbr.put("lg","ল্গ");
jbr.put("ShTy","ষ্ট্য");
jbr.put("ShTZ","ষ্ট্য");
jbr.put("ShTr","ষ্ট্র");
jbr.put("ShTh","ষ্ঠ");
jbr.put("ShThy","ষ্ঠ্য");
jbr.put("ShThZ","ষ্ঠ্য");
jbr.put("ShN","ষ্ণ");
jbr.put("Shp","ষ্প");
jbr.put("Shpr","ষ্প্র");
jbr.put("Shph","ষ্ফ");
jbr.put("Shf","ষ্ফ");
jbr.put("Shw","ষ্ব");
jbr.put("Shm","ষ্ম");
jbr.put("sk","স্ক");
jbr.put("sht","শ্ত");
jbr.put("St","শ্ত");
jbr.put("shn","শ্ন");
jbr.put("Sn","শ্ন");
jbr.put("shw","শ্ব");
jbr.put("Sw","শ্ব");
jbr.put("shm","শ্ম");
jbr.put("Sm","শ্ম");
jbr.put("shy","শ্য");
jbr.put("shZ","শ্য");
jbr.put("Sy","শ্য");
jbr.put("SZ","শ্য");
jbr.put("shr","শ্র");
jbr.put("Sr","শ্র");
jbr.put("shl","শ্ল");
jbr.put("Sl","শ্ল");
jbr.put("Shk","ষ্ক");
jbr.put("Shkr","ষ্ক্র");
jbr.put("ShT","ষ্ট");
jbr.put("sf","স্ফ");
jbr.put("sph","স্ফ");
jbr.put("sw","স্ব");
jbr.put("sm","স্ম");
jbr.put("sy","স্য");
jbr.put("sZ","স্য");
jbr.put("sr","স্র");
jbr.put("sl","স্ল");
jbr.put("skl","স্ক্ল");
jbr.put("hN","হ্ণ");
jbr.put("hn","হ্ন");
jbr.put("skr","স্ক্র");
jbr.put("sT","স্ট");
jbr.put("sTr","স্ট্র");
jbr.put("skh","স্খ");
jbr.put("st","স্ত");
jbr.put("stw","স্ত্ব");
jbr.put("sty","স্ত্য");
jbr.put("stZ","স্ত্য");
jbr.put("sth","স্থ");
jbr.put("sthy","স্থ্য");
jbr.put("sthZ","স্থ্য");
jbr.put("sn","স্ন");
jbr.put("sp","স্প");
jbr.put("hw","হ্ব");
jbr.put("hm","হ্ম");
jbr.put("hy","হ্য");
jbr.put("hZ","হ্য");
jbr.put("hr","হ্র");
jbr.put("hl","হ্ল");
jbr.put("hrri","হৃ");

        
      
        mWordSeparators = getResources().getString(R.string.word_separators);
    }
    
    /**
     * This is the point where you can do all of your UI initialization.  It
     * is called after creation and any configuration change.
     */
    @Override public void onInitializeInterface() {
        if (mQwertyKeyboard != null) {
            // Configuration changes can happen after the keyboard gets recreated,
            // so we need to be able to re-build the keyboards if the available
            // space has changed.
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) return;
            mLastDisplayWidth = displayWidth;
        }
        mQwertyKeyboard = new Pht_ProtitiPhoneticKeyboard(this, R.xml.pht_qwerty);
        mSymbolsKeyboard = new Pht_ProtitiPhoneticKeyboard(this, R.xml.pht_symbols);

    }
    
    /**
     * Called by the framework when your view for creating input needs to
     * be generated.  This will be called the first time your input method
     * is displayed, and every time it needs to be re-created such as due to
     * a configuration change.
     */
    @Override public View onCreateInputView() {
        mInputView = (KeyboardView) getLayoutInflater().inflate(
                R.layout.input, null);
        mInputView.setOnKeyboardActionListener(this);
        mInputView.setKeyboard(mQwertyKeyboard);
        return mInputView;
    }

    /**
     * Called by the framework when your view for showing candidates needs to
     * be generated, like {@link #onCreateInputView}.
     */
    @Override public View onCreateCandidatesView() {
    	mCandidateAndConfigLayout = new LinearLayout(this);
  	   // mCandidateAndConfigLayout = new LinearLayout(this);
  	    mCandidateAndConfigLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
  	    mCandidateAndConfigLayout.setOrientation(LinearLayout.VERTICAL);
  	      // CandidateView candidateView = new CandidateView(this);
  	   //("H","here");
  	    mBanglaPreviewView = new BanglaPreviewView(this);
   
  	    mBanglaPreviewView.setService(this);
  	
  	    
  	
  	        setCandidatesViewShown(true);
  	     //   updateCandidateText();
  	       mCandidateView = new Pht_CandidateView(this);
  	        mCandidateView.setService(this);
  	   	mCandidateAndConfigLayout.addView(mBanglaPreviewView);
  	       mCandidateAndConfigLayout.addView(mCandidateView);
  	        
  	  
          return mCandidateAndConfigLayout;

    }

    /**
     * This is the main point where we do our initialization of the input method
     * to begin operating on an application.  At this point we have been
     * bound to the client, and are now receiving all of the detailed information
     * about the target of our edits.
     */
    @Override public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        attributer=attribute;
        
        // Reset our state.  We want to do this even if restarting, because
        // the underlying state of the text editor could have changed in any way.
        mComposing.setLength(0);
        updateCandidates();
        
        if (!restarting) {
            // Clear shift states.
            mMetaState = 0;
        }
        

        mCompletionOn = false;
        mCompletions = null;
        
        // We are now going to initialize our state based on the type of
        // text being edited.
        switch (attribute.inputType&EditorInfo.TYPE_MASK_CLASS) {
            case EditorInfo.TYPE_CLASS_NUMBER:
            case EditorInfo.TYPE_CLASS_DATETIME:
                // Numbers and dates default to the symbols keyboard, with
                // no extra features.
                mCurKeyboard = mSymbolsKeyboard;
                break;
                
            case EditorInfo.TYPE_CLASS_PHONE:
                // Phones will also default to the symbols keyboard, though
                // often you will want to have a dedicated phone keyboard.
                mCurKeyboard = mSymbolsKeyboard;
                break;
                
            case EditorInfo.TYPE_CLASS_TEXT:
                // This is general text editing.  We will default to the
                // normal alphabetic keyboard, and assume that we should
                // be doing predictive text (showing candidates as the
                // user types).
                mCurKeyboard = mQwertyKeyboard;
   
                
                // We now look for a few special variations of text that will
                // modify our behavior.
                int variation = attribute.inputType &  EditorInfo.TYPE_MASK_VARIATION;
                if (variation == EditorInfo.TYPE_TEXT_VARIATION_PASSWORD ||
                        variation == EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    // Do not display predictions / what the user is typing
                    // when they are entering a password.

                }
                
                if (variation == EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS 
                        || variation == EditorInfo.TYPE_TEXT_VARIATION_URI
                        || variation == EditorInfo.TYPE_TEXT_VARIATION_FILTER) {
                    // Our predictions are not useful for e-mail addresses
                    // or URIs.

                }
                
                if ((attribute.inputType&EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE) != 0) {
                    // If this is an auto-complete text view, then our predictions
                    // will not be shown and instead we will allow the editor
                    // to supply their own.  We only show the editor's
                    // candidates when in fullscreen mode, otherwise relying
                    // own it displaying its own UI.
                	mCompletionOn = true;
                    mCompletionOn = isFullscreenMode();
                }
                
                // We also want to look at the current state of the editor
                // to decide whether our alphabetic keyboard should start out
                // shifted.
                updateShiftKeyState(attribute);
                break;
                
            default:
                // For all unknown input types, default to the alphabetic
                // keyboard with no special features.
                mCurKeyboard = mQwertyKeyboard;
                updateShiftKeyState(attribute);
        }
        
        // Update the label on the enter key, depending on what the application
        // says it will do.
        mCurKeyboard.setImeOptions(getResources(), attribute.imeOptions);
        mCurKeyboard.change(getResources(), 0);
    }

    /**
     * This is called when the user is done editing a field.  We can use
     * this to reset our state.
     */
    @Override public void onFinishInput() {
        super.onFinishInput();
        
        // Clear current composing text and candidates.
        mComposing.setLength(0);
        cw.setLength(0);
        updateCandidates();
        
        // We only hide the candidates window when finishing input on
        // a particular editor, to avoid popping the underlying application
        // up and down if the user is entering text into the bottom of
        // its window.
        setCandidatesViewShown(false);
        
        mCurKeyboard = mQwertyKeyboard;
        if (mInputView != null) {
            mInputView.closing();
        }
    }
    
    @Override public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        // Apply the selected keyboard to the input view.
        mInputView.setKeyboard(mCurKeyboard);
        mInputView.closing();
    }
    
    /**
     * Deal with the editor reporting movement of its cursor.
     */
    @Override public void onUpdateSelection(int oldSelStart, int oldSelEnd,
            int newSelStart, int newSelEnd,
            int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd,
                candidatesStart, candidatesEnd);
        
        // If the current selection in the text view changes, we should
        // clear whatever candidate text we have.
        if (mComposing.length() > 0 && (newSelStart != candidatesEnd
                || newSelEnd != candidatesEnd)) {
            mComposing.setLength(0);
            cw.setLength(0);
            updateCandidates();
            InputConnection ic = getCurrentInputConnection();
            if (ic != null) {
                ic.finishComposingText();
            }
        }
        
   
    }

    /**
     * This tells us about completions that the editor has determined based
     * on the current text in it.  We want to use this in fullscreen mode
     * to show the completions ourself, since the editor can not be seen
     * in that situation.
     */
    @Override public void onDisplayCompletions(CompletionInfo[] completions) {
        if (mCompletionOn) {
            mCompletions = completions;
            if (completions == null) {
                setSuggestions(null, false, false);
                return;
            }
            
            List<String> stringList = new ArrayList<String>();
            for (int i=0; i<(completions != null ? completions.length : 0); i++) {
                CompletionInfo ci = completions[i];
                if (ci != null) stringList.add(ci.getText().toString());
            }
            setSuggestions(stringList, true, true);
        }
    }
    
    /**
     * This translates incoming hard key events in to edit operations on an
     * InputConnection.  It is only needed when using the
     * PROCESS_HARD_KEYS option.
     */
    private boolean translateKeyDown(int keyCode, KeyEvent event) {
        mMetaState = MetaKeyKeyListener.handleKeyDown(mMetaState,
                keyCode, event);
        int c = event.getUnicodeChar(MetaKeyKeyListener.getMetaState(mMetaState));
        mMetaState = MetaKeyKeyListener.adjustMetaAfterKeypress(mMetaState);
        InputConnection ic = getCurrentInputConnection();
        if (c == 0 || ic == null) {
            return false;
        }
        
        boolean dead = false;

        if ((c & KeyCharacterMap.COMBINING_ACCENT) != 0) {
            dead = true;
            c = c & KeyCharacterMap.COMBINING_ACCENT_MASK;
        }
        
        if (mComposing.length() > 0) {
            char accent = mComposing.charAt(mComposing.length() -1 );
            int composed = KeyEvent.getDeadChar(accent, c);

            if (composed != 0) {
                c = composed;
                mComposing.setLength(mComposing.length()-1);
            }
        }
        
        onKey(c, null);
        
        return true;
    }
    
    /**
     * Use this to monitor key events being delivered to the application.
     * We get first crack at them, and can either resume them or let them
     * continue to the app.
     */
    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // The InputMethodService already takes care of the back
                // key for us, to dismiss the input method if it is shown.
                // However, our keyboard could be showing a pop-up window
                // that back should dismiss, so we first allow it to do that.
                if (event.getRepeatCount() == 0 && mInputView != null) {
                    if (mInputView.handleBack()) {
                        return true;
                    }
                }
                break;
                
            case KeyEvent.KEYCODE_DEL:
                // Special handling of the delete key: if we currently are
                // composing text for the user, we want to modify that instead
                // of let the application to the delete itself.
                if (mComposing.length() > 0) {
                    onKey(Keyboard.KEYCODE_DELETE, null);
                    return true;
                }
                break;
                
            case KeyEvent.KEYCODE_ENTER:
                // Let the underlying text editor always handle these.
                return false;
                
            default:
                // For all other keys, if we want to do transformations on
                // text being entered with a hard keyboard, we need to process
                // it and do the appropriate action.
                if (PROCESS_HARD_KEYS) {
                    if (keyCode == KeyEvent.KEYCODE_SPACE
                            && (event.getMetaState()&KeyEvent.META_ALT_ON) != 0) {
                        // A silly example: in our input method, Alt+Space
                        // is a shortcut for 'android' in lower case.
                        InputConnection ic = getCurrentInputConnection();
                        if (ic != null) {
                            // First, tell the editor that it is no longer in the
                            // shift state, since we are consuming this.
                            ic.clearMetaKeyStates(KeyEvent.META_ALT_ON);
                            keyDownUp(KeyEvent.KEYCODE_A);
                            keyDownUp(KeyEvent.KEYCODE_N);
                            keyDownUp(KeyEvent.KEYCODE_D);
                            keyDownUp(KeyEvent.KEYCODE_R);
                            keyDownUp(KeyEvent.KEYCODE_O);
                            keyDownUp(KeyEvent.KEYCODE_I);
                            keyDownUp(KeyEvent.KEYCODE_D);
                            // And we consume this event.
                            return true;
                        }
                    }
                    if (translateKeyDown(keyCode, event)) {
                        return true;
                    }
                }
        }
        
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Use this to monitor key events being delivered to the application.
     * We get first crack at them, and can either resume them or let them
     * continue to the app.
     */
    @Override public boolean onKeyUp(int keyCode, KeyEvent event) {
        // If we want to do transformations on text being entered with a hard
        // keyboard, we need to process the up events to update the meta key
        // state we are tracking.
        if (PROCESS_HARD_KEYS) {
  
                mMetaState = MetaKeyKeyListener.handleKeyUp(mMetaState,
                        keyCode, event);
            
        }
        
        return super.onKeyUp(keyCode, event);
    }

    /**
     * Helper function to commit any text being composed in to the editor.
     */
    private void commitTyped(InputConnection inputConnection) {
        if (mComposing.length() > 0)     		
            inputConnection.commitText(mComposing, 1);
            mComposing.setLength(0);
            cw.setLength(0);
            updateCandidates();
        
    }

    /**
     * Helper to update the shift state of our keyboard based on the initial
     * editor state.
     */
    private void updateShiftKeyState(EditorInfo attr) {
        if (attr != null 
                && mInputView != null && mQwertyKeyboard == mInputView.getKeyboard()) {
            int caps = 0;
            EditorInfo ei = getCurrentInputEditorInfo();
            if (ei != null && ei.inputType != EditorInfo.TYPE_NULL) {
                caps = getCurrentInputConnection().getCursorCapsMode(attr.inputType);
            }
            mInputView.setShifted(mCapsLock || caps != 0);
        }
    }
    
    /**
     * Helper to determine if a given character code is alphabetic.
     */
    private boolean isAlphabet(int code) {
        if (Character.isLetter(code)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Helper to send a key down / key up pair to the current editor.
     */
    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
    }
    
    /**
     * Helper to send a character to the editor as raw key events.
     */
    private void sendKey(int keyCode) {
        switch (keyCode) {
            case '\n':
                keyDownUp(KeyEvent.KEYCODE_ENTER);
                break;
           /* default:
                if (keyCode >= '0' && keyCode <= '9') {
                    keyDownUp(keyCode - '0' + KeyEvent.KEYCODE_0);
                } else {
                    getCurrentInputConnection().commitText(String.valueOf((char) keyCode), 1);
                }
                break;*/
        }
    }

    // Implementation of KeyboardViewListener

    public void onKey(int primaryCode, int[] keyCodes) {
    	
       if (sptype.contains(""+(char) primaryCode) || primaryCode==450|| primaryCode==10) {
            // Handle separator
        	
            if (mComposing.length() > 0) {

                commitTyped(getCurrentInputConnection());
                mComposing.setLength(0);
                cw.setLength(0);
                
            }
            if(primaryCode==450)
            	getCurrentInputConnection().commitText("।", 1);
            else if (primaryCode==10)
            	keyDownUp(KeyEvent.KEYCODE_ENTER);
            else if (primaryCode >= '0' && primaryCode <= '9')
            	{
            	if(mark==0)
            	{
            		//banglar kaj
            		getCurrentInputConnection().commitText(bbr.get(""+String.valueOf((char) primaryCode)), 1);
            	}
            	
            	else
            	getCurrentInputConnection().commitText(String.valueOf((char) primaryCode), 1);
            	}
            else
            	getCurrentInputConnection().commitText(String.valueOf((char) primaryCode), 1);
            updateShiftKeyState(getCurrentInputEditorInfo());
            updateCandidates();
        } 
        
        else if (primaryCode == -30) {
        	List<InputMethodInfo> InputMethods=((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).getEnabledInputMethodList();
			int i=0;
			for(;i<InputMethods.size();i++){
				if(InputMethods.get(i).getServiceName().equals("com.jhalmuri.PKB.SoftKeyboard"))
					break;
			}
			String NewInputMethodName=InputMethods.get(i).getId(); 
			switchInputMethod(NewInputMethodName);
        }
        
        else if (primaryCode == Keyboard.KEYCODE_DELETE) {
            handleBackspace();
        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            handleShift();
        } else if (primaryCode == Keyboard.KEYCODE_CANCEL) {
            handleClose();
            return;
        } else if (primaryCode == Pht_ProtitiPhoneticKeyboardView.KEYCODE_OPTIONS) {
            // Show a menu or somethin'
        } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE
                && mInputView != null) {
            Keyboard current = mInputView.getKeyboard();
            
            if (current == mSymbolsKeyboard) {
                current = mQwertyKeyboard;
            } else {
                current = mSymbolsKeyboard;
            }
            mInputView.setKeyboard(current);
            if (current == mSymbolsKeyboard) {
                current.setShifted(false);
            }
            mCurKeyboard=(Pht_ProtitiPhoneticKeyboard) current;
            if(mark==0)
        	{
        		mCurKeyboard.change(getResources(), 0);
        	}
        		else
        		{
        			mCurKeyboard.change(getResources(), 1);
        		}
        } 
        else if(primaryCode==500)
        {

        	
        	commitTyped(getCurrentInputConnection());
        	if(mark==0)
        	{
        		mark=1;
        		mCurKeyboard.change(getResources(), 1);
        	}
        		else
        		{
        			mark=0;
        			mCurKeyboard.change(getResources(), 0);
        		}
        	
        		}
        else {
        	
 	if(mark==0)
 	{
 
            handleCharacter(primaryCode, keyCodes);
            
 	}
            else
            {
            	  
 		handleCharacterEng(primaryCode, keyCodes);
            }
        }
    }

    public void onText(CharSequence text) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        ic.beginBatchEdit();
        if (mComposing.length() > 0) {
            commitTyped(ic);
        }
        ic.commitText(text, 0);
        ic.endBatchEdit();
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    /**
     * Update the list of available candidates from the current composing
     * text.  This will need to be filled in by however you are determining
     * candidates.
     */
    private void updateCandidates() {
//ami eikhane bangla preview update korsi
    	
    	String strbeforeCursor="";
        String strafterCursor ="";
        try{
        strbeforeCursor = getCurrentInputConnection().getTextBeforeCursor(1000000000, 0).toString();
        strafterCursor  = getCurrentInputConnection().getTextAfterCursor(1000000000, 0).toString();
        String str = strbeforeCursor +""+strafterCursor;
        if(mBanglaPreviewView != null && str!=null)
        	mBanglaPreviewView.update(str);
        }
        catch (Exception e) {
        	
			// TODO: handle exception
		}
        
    	
    	//ami eikhane bangla preview update korsi
list.clear();
            if (mComposing.length() > 0) {
               
                list.add(cw.toString());
                if(cw.toString().compareTo("a")==0||cw.toString().compareTo("A")==0)
                	list.add("া");
                if(cw.toString().compareTo("i")==0)
                	list.add("ি");
                if(cw.toString().compareTo("I")==0)
                	list.add("ী");
                if(cw.toString().compareTo("ee")==0)
                	list.add("ী");
                if(cw.toString().compareTo("u")==0)
                	list.add("ূ");
                if(cw.toString().compareTo("oo")==0)
                	list.add("ু");
                if(cw.toString().compareTo("U")==0)
                	list.add("ূ");
                if(cw.toString().compareTo("rri")==0)
                	list.add("ৃ");
                if(cw.toString().compareTo("e")==0)
                	list.add("ে");
                if(cw.toString().compareTo("E")==0)
                	list.add("ে");
                if(cw.toString().compareTo("OI")==0)
                	list.add("ৈ");
                if(cw.toString().compareTo("O")==0)
                	list.add("ো");
                if(cw.toString().compareTo("OU")==0)
                	list.add("ৌ");
                
                if(cw.toString().compareTo("ram")==0)
                {
                	list.add("র‍্যাম");
                }
                else if(cw.toString().compareTo("rab")==0)
                {
                	list.add("র‍্যাব");
                }
           if((""+cw.charAt(cw.length()-1)).compareTo("t")==0)
           {
        	   StringBuilder tmp = new StringBuilder(mComposing.toString());
        	   tmp.deleteCharAt(tmp.length()-1);
        	   tmp.append("ৎ");
        	   list.add(tmp.toString());

           }
                setSuggestions(list, true, true);
            }else {
                setSuggestions(null, false, false);
            }
       
    }
    
    public void setSuggestions(List<String> suggestions, boolean completions,
            boolean typedWordValid) {
        if (suggestions != null && suggestions.size() > 0) {
            setCandidatesViewShown(true);
        } else if (isExtractViewShown()) {
            setCandidatesViewShown(true);
        }
        if (mCandidateView != null) {
            mCandidateView.setSuggestions(suggestions, completions, typedWordValid);
        }
    }
    
    private void handleBackspace() {
    	
    //	String sp= getCurrentInputConnection().getTextBeforeCursor(1, 0).toString();
    	if (mComposing.length()==0)
    	{
    		getCurrentInputConnection().deleteSurroundingText(1, 0);

    	}
    	else
    	{  	
    		final int length = mComposing.length();
    		final int cwl= cw.length();
        
        if (length > 1) {
        	cw.delete(cwl-1,cwl);
        	fordelete();
        	
        } else {
            keyDownUp(KeyEvent.KEYCODE_DEL);
            cw.setLength(0);
            mComposing.setLength(0);
        }
    	}
        updateShiftKeyState(getCurrentInputEditorInfo());
        mBanglaPreviewView.update("");
        updateCandidates();
       
    	}

    private void handleShift() {
        if (mInputView == null) {
            return;
        }
        
        Keyboard currentKeyboard = mInputView.getKeyboard();
        if (mQwertyKeyboard == currentKeyboard) {
            // Alphabet keyboard
            checkToggleCapsLock();
            mInputView.setShifted(mCapsLock || !mInputView.isShifted());
        } 
    }
    
    private void handleCharacter(int primaryCode, int[] keyCodes) {
    	
        if (isInputViewShown()) {
            if (mInputView.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
            }
        }

        cw.append((char) primaryCode);
        
        mComposing.setLength(0);
		mComposing.append(phonetic(cw).toString());
		
		getCurrentInputConnection().setComposingText(mComposing, 1);
		updateShiftKeyState(getCurrentInputEditorInfo());
		updateCandidates();
		//majhe lekhi ;)
    }
    
    private void handleCharacterEng(int primaryCode, int[] keyCodes) {
        if (isInputViewShown()) {
            if (mInputView.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
            }
        }

            getCurrentInputConnection().commitText(
                    String.valueOf((char) primaryCode), 1);
            
    		updateShiftKeyState(getCurrentInputEditorInfo());
    		updateCandidates();
    }

    private void handleClose() {
        commitTyped(getCurrentInputConnection());
        requestHideSelf(0);
        mInputView.closing();
    }

    private void checkToggleCapsLock() {
        long now = System.currentTimeMillis();
        if (mLastShiftTime + 800 > now) {
            mCapsLock = !mCapsLock;
            mLastShiftTime = 0;
        } else {
            mLastShiftTime = now;
        }
    }
    
    private String getWordSeparators() {
        return mWordSeparators;
    }
    
    public boolean isWordSeparator(int code) {
        String separators = getWordSeparators();
        return separators.contains(String.valueOf((char)code));
    }

    public void pickDefaultCandidate() {
        pickSuggestionManually(0);
    }
    
    public void pickSuggestionManually(int index) {
    	//&& mCompletions != null
        if (index >= 0)// && !mCompletionOn) {
          //  CompletionInfo ci = list.get(index);
        { 
        	if(index==0)
        		getCurrentInputConnection().commitText(mComposing, 1);
        	else
        	getCurrentInputConnection().commitText(list.get(index), 1);
         //   getCurrentInputConnection().commitCompletion(null);
       //     getCurrentInputConnection().commitText(" ",1);
        //	getCurrentInputConnection().commitText(p.substring(0, 2), 1);
          //  if (mCandidateView != null) {
            //    mCandidateView.clear();
           // }
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
        cw.setLength(0);
        mComposing.setLength(0);
        updateCandidates();
        /*else if (mComposing.length() > 0) {
            // If we were generating candidate suggestions for the current
            // text, we would commit one of them here.  But for this sample,
            // we will just commit the current text.
            commitTyped(getCurrentInputConnection());
        }*/
    }
    
    public void swipeRight() {
        if (mCompletionOn) {
            pickDefaultCandidate();
        }
    }
    
    public void swipeLeft() {
        handleBackspace();
    }

    public void swipeDown() {
        handleClose();
    }

    public void swipeUp() {
    }
    
    public void onPress(int primaryCode) {
    }
    
    public void onRelease(int primaryCode) {
    }
    

    
    public StringBuilder phonetic(StringBuilder s)
    {
   
    	StringBuilder ps = new StringBuilder();
    	ps=s;
    	//5 char er gulan
    	for(int i=0;i<fchar.length;i++)
    	{
    	ps = chng(s.toString(),fchar[i],jbr.get(fchar[i]));
    	s=ps;
    	}
       	//5 char er gulan
    	
    	//4 char er gulan
    	for(int i=0;i<qchar.length;i++)
    	{
    	ps = chng(s.toString(),qchar[i],jbr.get(qchar[i]));
    	s=ps;
    	}
       	//4 char er gulan
    	
    	//3char er gulan
    	for(int i=0;i<tchar.length;i++)
    	{
    	ps = chng(s.toString(),tchar[i],jbr.get(tchar[i]));
    	s=ps;
    	}
    	
    	ps = chng2(s.toString(),"rri");
    	s=ps;
       	//3char er gulan
    	
    	//2char er gulan
    	for(int i=0;i<dchar.length;i++)
    	{
    		if(srb.containsKey(dchar[i]))
    		{
    	ps = chng2(s.toString(),dchar[i]);
    	s=ps;
    		}
    		else if(bbr.containsKey(dchar[i]))
    		{
    	ps = chng(s.toString(),dchar[i],bbr.get(dchar[i]));
    	s=ps;
    		}
    		else if(jbr.containsKey(dchar[i]))
    		{
    	ps = chng(s.toString(),dchar[i],jbr.get(dchar[i]));
    	s=ps;
    		}
    	
    
    	}
    	//2char er gulan
    	
    	//single char
    	for(int i=0;i<schar.length;i++)
    	{
    		
    		if(srb.containsKey(schar[i]))
    		{
    	ps = chng2(s.toString(),schar[i]);
    	s=ps;
    		}
    		else if(bbr.containsKey(schar[i]))
    		{
    	ps = chng(s.toString(),schar[i],bbr.get(schar[i]));
    	s=ps;
    		}   
    	}
    	//single char
    	
    	//o re replace krtesi
    	ps = chng(s.toString(),"o","");
    	
    	//
    	
    	return ps;
    	          
    }
    public StringBuilder chng(String txt,String ch,String nch)
    {
    	
        	StringBuilder sx = new StringBuilder();       	
        	sx.append(txt);
        	int ofe = sx.indexOf(ch,0);   
        	
      
    for(int ofs=0;ofs<txt.length() && ofe!=-1;ofs=ofe+1)
    {   	   	
      		  ofe = sx.indexOf(ch,ofs);   
      		  if(ofe == -1)
      			  break;
      		
     		  sx.replace(ofe, ofe+ch.length(), nch);    
    }
     return sx;   	
    }
    
   // public ketKey
    public String getKey(HashMap<String, String> map,String x)
    {
    	Set<String> references = getKeysByValue(map,x);
		references.toString();
		String key = "";
		 Iterator<String> it = references.iterator();
		    while (it.hasNext()) {
		      key = (String) it.next();
		    }
		return key;
    	
    }
    
    //
    //key from value  
	public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
		Set<T> keys = new HashSet<T>();
		for (Entry<T, E> entry : map.entrySet()) {
			if (entry.getValue().equals(value)) {
				keys.add(entry.getKey());
			}
		}
		return keys;
	}
    
    //key from value	
	
	
	
    public StringBuilder chng2(String txt,String ch)
    {
    	
        	StringBuilder sx = new StringBuilder();       	
        	sx.append(txt);

        	int ofe = sx.indexOf(ch,0);   
        	        	
    for(int ofs=0;ofs<txt.length() && ofe!=-1;ofs=ofe+1)
    {   	  	
      		  ofe = sx.indexOf(ch,ofs);   
      		  if(ofe == -1)
      			  break;
      		  else
      		  {
      				 if(ofe==0)
      					 sx.replace(ofe, ofe+ch.length(), srb.get(ch));	 
      				 else
      				 {
      					 if(ch.compareTo("o")==0 && (srb.containsValue(""+txt.charAt(ofe-1))||srb.containsKey(""+txt.charAt(ofe-1))||kar.containsKey(""+txt.charAt(ofe-1))||kar.containsValue(""+txt.charAt(ofe-1))) )
      					 {
      						 sx.replace(ofe, ofe+ch.length(), "ও");
      					 }
      				 else if(srb.containsKey(""+txt.charAt(ofe-1)) || srb.containsValue(""+txt.charAt(ofe-1)) || kar.containsKey(""+txt.charAt(ofe-1)) || kar.containsValue(""+txt.charAt(ofe-1)) ||(""+txt.charAt(ofe-1)).compareTo(" ") == 0 || (""+txt.charAt(ofe-1)).compareTo("o") == 0 )
      				 {
      					
      						 sx.replace(ofe, ofe+ch.length(), srb.get(ch));
      				 }
      				
      				 else
      				 {      					
      					if((""+txt.charAt(ofe)).compareTo("o") != 0)
      					{
      					sx.replace(ofe, ofe+ch.length(), kar.get(ch));
      					
      					}
      						
      				 }
      				 }
      		}  			
     
    }
     return sx;   	
    }
   
    
   
    
    public void fordelete()
    {
    	mComposing.setLength(0);
  		mComposing.append(phonetic(cw).toString());
  		
  		getCurrentInputConnection().setComposingText(mComposing, 1);
  		updateShiftKeyState(getCurrentInputEditorInfo());
  		updateCandidates();
    }
    

}

