package com.jolpai.tafsir.custom.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jolpai.tafsir.R;
import com.jolpai.tafsir.custom.listener.OnDialogButtonClick;

import java.util.HashMap;
import java.util.Set;

public class MyDialog {
	 private Activity activity;
	 private String title="";
	 private int titleColor=Color.BLACK;
	 private String message="";
	 private int messageColor=Color.BLACK;
	 private Object icon=null;
	 private HashMap<Integer, Object> buttonMap =null;
	 private OnDialogButtonClick onDialogButtonClick;
	 
	 private MyDialog() {
	 }

	public void setOnDialogButtonClickListener(OnDialogButtonClick onDialogButtonClick) {
		this.onDialogButtonClick = onDialogButtonClick;
	}

	public MyDialog(Activity activity, String title, int titleColor,
					String message, int messageColor, Object icon,
					HashMap<Integer, Object> buttonMap) {
		super();
		
		this.activity = activity;
		
		setTitle(title);
		setTitleColor(titleColor);
		setMessage(message);
		setMessageColor(messageColor);
		setIcon(icon);
		setButtonMap(buttonMap);
	}
	
	public MyDialog(Activity activity, Object title,
					Object message, int messageColor, Object icon,
					HashMap<Integer, Object> buttonMap) {
		super();
		this.activity = activity;
		
		setTitle(title);
    	setMessage(message);
		setMessageColor(messageColor);
		setIcon(icon);
		setButtonMap(buttonMap);
	}
	
	public MyDialog(Activity activity, Object title,
					Object message, Object icon,
					HashMap<Integer, Object> buttonMap) {
		super();
		this.activity = activity;
		
		setTitle(title);
    	setMessage(message);
		setIcon(icon);
		setButtonMap(buttonMap);
	}
	
	public MyDialog(Activity activity,
					HashMap<Integer, Object> buttonMap) {
		super();
		this.activity = activity;
		setButtonMap(buttonMap);
	}

	public void setTitle(Object title) {	
		if(title instanceof String ){
			this.title=(String)title;
		} else if(title instanceof Integer ){
			this.title=(activity.getResources().getString((Integer)title));
		}else{
			this.title="";
		}
	}

	public void setTitleColor(int titleColor) {
		this.titleColor = titleColor;
	}
	
	
	
	
	
	public void setMessage(Object message) {
		if(message instanceof String ){
			this.message=(String)message;
		} else if(message instanceof Integer ){
			this.message=(activity.getResources().getString((Integer)message));
		}else{
			this.message="";
		}
	}
	
	
	
	
	
	public void setMessageColor(int messageColor) {
		this.messageColor = messageColor;
	}
	

	public void setIcon(Object icon) {
		this.icon=icon;
	}
	
	public void setButtonMap(HashMap<Integer, Object> buttonMap) {
		this.buttonMap = buttonMap;
	}

    /*public  void show(){
   
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		View view = View.inflate(activity, R.layout.my_dialog, null);
		builder.setView(view);
		final AlertDialog dialog= builder.create();
		TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
		tvTitle.setText(title);
		tvTitle.setTextColor(titleColor);
		
		TextView tvMsg = (TextView) view.findViewById(R.id.tv_dialog_message);
		tvMsg.setText(message);
		tvMsg.setTextColor(messageColor);
	
		
		ImageView imgIcon = (ImageView) view.findViewById(R.id.img_dialog_title);
		
		if(icon instanceof Bitmap ){
			imgIcon.setImageBitmap((Bitmap)icon);
		} else if(icon instanceof Drawable ){
			imgIcon.setImageDrawable((Drawable)icon);
		}else if(icon instanceof Integer ){
			imgIcon.setImageResource((Integer)icon);
		}
		
		LinearLayout buttonContainer= (LinearLayout) view.findViewById(R.id.btn_container);
		Button btnDemo= (Button) view.findViewById(R.id.btn);
		buttonContainer.removeAllViews();

		Set<Integer> keys= buttonMap.keySet();
		for (Integer id :keys ) {
		    Button btn = new Button(activity);
		    Object buttonCaption=buttonMap.get(id);
			btn.setId(id);
			
			if(buttonCaption instanceof Integer){
				btn.setText((Integer)buttonCaption);	
			}else if(buttonCaption instanceof String){
				btn.setText((String)buttonCaption+"");
			}
			
			btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
			btn.setTypeface(btnDemo.getTypeface());
			btn.setLayoutParams(btnDemo.getLayoutParams());
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					dialog.dismiss();
					if(onDialogButtonClick!=null){
						onDialogButtonClick.onDialogButtonClick(view);
					} 
				}
			});
			buttonContainer.addView(btn);         
		   }
		dialog.setCancelable(false);
		dialog.show();

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		Window window = dialog.getWindow();
		lp.copyFrom(window.getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		window.setAttributes(lp);

	}
    
    
    public  void showWebView(){
    	   
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		View view = View.inflate(activity, R.layout.my_web_dialog, null);
		builder.setView(view);
		final AlertDialog dialog= builder.create();
		TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
		tvTitle.setText(title);
		tvTitle.setTextColor(titleColor);
		
		
		
		 WebView wv = (WebView) view.findViewById(R.id.wv_webview);        

	     final String mimeType = "text/html";
	     final String encoding = "UTF-8";
	     wv.loadDataWithBaseURL("", message, mimeType, encoding, "");
	
		
		ImageView imgIcon = (ImageView) view.findViewById(R.id.img_dialog_title);
		
		if(icon instanceof Bitmap ){
			imgIcon.setImageBitmap((Bitmap)icon);
		} else if(icon instanceof Drawable ){
			imgIcon.setImageDrawable((Drawable)icon);
		}else if(icon instanceof Integer ){
			imgIcon.setImageResource((Integer)icon);
		}
		
		
		
		LinearLayout buttonContainer= (LinearLayout) view.findViewById(R.id.btn_container);
		Button btnDemo= (Button) view.findViewById(R.id.btn);
		buttonContainer.removeAllViews();
		
		Set<Integer> keys= buttonMap.keySet();
		for (Integer id :keys ) {
		    Button btn = new Button(activity);
		    Object buttonCaption=buttonMap.get(id);
			btn.setId(id);
			
			if(buttonCaption instanceof Integer){
				btn.setText((Integer)buttonCaption);	
			}else if(buttonCaption instanceof String){
				btn.setText((String)buttonCaption+"");
			}
			
			btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
			btn.setTypeface(btnDemo.getTypeface());
			btn.setLayoutParams(btnDemo.getLayoutParams());
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					dialog.dismiss();
					if(onDialogButtonClick!=null){
						onDialogButtonClick.onDialogButtonClick(view);
					} 
				}
			});
			buttonContainer.addView(btn);         
		   }
		dialog.setCancelable(false);
		dialog.show();
		
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		Window window = dialog.getWindow();
		lp.copyFrom(window.getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		window.setAttributes(lp);
	
	}*/
}
