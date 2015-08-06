package com.jolpai.tafsir.activity;


import com.jolpai.tafsir.R;
import com.jolpai.tafsir.custom.view.R2L;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivityArabicExample extends ActionBarActivity {



    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
      R2L customLayout = (R2L)this.findViewById(R.id.cl_R2L);

       Typeface tf = Typeface.createFromAsset(getAssets(),"font/TRADO.TTF");
       
       
    // String msg = "Hello My Name Is Tanim Reja I am Android Developer. Thank You Sir.. What Can I Do For You? Oh Thanks .. You Did Great Job."
    //   		+ "  My Name Is Tanim Reja I am Android Developer. Thank You Sir.. What Can I Do For You? Oh Thanks";
     String msg=" صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ  ";

       TextView t = new TextView(this);
       t.setText(msg);
       t.setTextSize(50);
      // customLayout.addView(t);

        String [] arrayWhitespace = msg.split("\\s+");
        String name="tt";
        int len=arrayWhitespace.length;
        for (int i=0; i<len; i++){
        	      	
        	//View roundView = new View(this); //View.inflate(MainActivityArabicExample.this, R.layout.custom_text_shape, null);
            View normalView=new View(this); //View.inflate(MainActivityArabicExample.this, R.layout.normal_textshspe, null);
            View emptyView= new View(this); //View.inflate(MainActivityArabicExample.this, R.layout.empty_text_view, null);
            TextView normalShapeTextView = new TextView(this); //(TextView)normalView.findViewById(R.id.txt1);
            TextView roundShapeTextView = new TextView(this);//(TextView)roundView.findViewById(R.id.txtShape);
            TextView emptyTextView = new TextView(this);//(TextView)emptyView.findViewById(R.id.emptyTextShape);
            
            normalShapeTextView.setLayoutDirection(4);
            
            int a= arrayWhitespace[i].indexOf(" ۚ");
            String mm="ۚ";
            String kk= "  ۚ ";
            String ss=arrayWhitespace[i];
        	if (ss.equals(mm)){
        		
        	System.out.println(ss);
        	 kk= "  ۚ ";
        	
    		customLayout.addView(emptyView);
    		emptyTextView.setText(kk);
    		emptyTextView.setText("-");
    		emptyTextView.setTextSize(45);
    		emptyTextView.setTextColor(Color.RED);
    		}
        	 boolean found=false;
        	for(int j=0;j<arrayWhitespace[i].length();j++) {
            	
            	if (arrayWhitespace[i].charAt(j) == 'ج'){  
            		//count++;
            		found=true;
            	//	arrayWhitespace[i]=arrayWhitespace[i].replace("*", "");
            	}
            	if(found ){
            		//customLayout.addView(roundView);
            		//roundShapeTextView.setText(arrayWhitespace[i]); 
            		break;
            	}
            }
        	 if (found==false){
        		 customLayout.addView(normalView);
             	String c="0";
             	if (i>0){
             	 c = ""+i;
             	 name=	arrayWhitespace[i].replace(c, "");
             	 arrayWhitespace[i]=name;
             	}        	
             	normalShapeTextView.setText(arrayWhitespace[i]);
             	normalShapeTextView.setTypeface(tf,Typeface.NORMAL);
             	normalShapeTextView.setTextSize(30);
             	normalShapeTextView.setTextColor(Color.RED);
             int viewWidth=	normalShapeTextView.getMeasuredWidth();
        	 }
        	
        	if(i!=arrayWhitespace.length-1)
        	{
        		//customLayout.addView(roundView);
        		//roundShapeTextView.setText(" ");
        		customLayout.addView(emptyView);
        		emptyTextView.setText(" ");
        		emptyTextView.setTextSize(20);
        		emptyTextView.setTextColor(Color.RED);
        	}
        }
    }


}
