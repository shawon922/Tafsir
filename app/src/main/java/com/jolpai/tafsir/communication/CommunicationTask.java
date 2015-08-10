package com.jolpai.tafsir.communication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;

import com.jolpai.tafsir.custom.exception.CustomException;
import com.jolpai.tafsir.custom.listener.OnCompleteListener;

public class CommunicationTask extends AsyncTask<Void, Integer, Void> {
 

	private OnCompleteListener completeListener;
	private Context context;
	private Request request;
	private ProgressDialog dialog;
	
	Message message = Message.obtain();
	Bundle bundle = new Bundle();

	public CommunicationTask(Context context, Request request) {
		this.context = context;
		this.request = request;
		this.request.setContext(this.context);
		this.message = Message.obtain();
		this.bundle = new Bundle();
		this.dialog = new ProgressDialog(context);
	}
	
	
	public CommunicationTask(Context context, Request request, Object dilogTitle, Object dilogMessage) {
		this.context = context;
		this.request = request;
		this.request.setContext(this.context);
		this.message = Message.obtain();
		this.bundle = new Bundle();
		this.dialog = new ProgressDialog(context);
		if(dilogTitle instanceof Integer ){
			this.dialog.setTitle((Integer)dilogTitle);}
		else if(dilogTitle instanceof String ){
			this.dialog.setTitle((String)dilogTitle);}
		
		if(dilogMessage instanceof Integer ){
			this.dialog.setMessage(context.getResources().getString((Integer)dilogMessage));}
		else if(dilogMessage instanceof String ){
			this.dialog.setMessage((String)dilogMessage);}
	}

	private CommunicationTask(Context context) {}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(this.dialog!=null){
			this.dialog.show();
			this.dialog.setCancelable(false);
	        this.dialog.setCanceledOnTouchOutside(false);	
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		String url ="http://192.168.0.91/test_ci/webdataservice";
		try {
			bundle.putSerializable("DATA", request.send(url));
		}
		catch (CustomException se) {
			se.printStackTrace();
			bundle.putSerializable("ERROR", se.getStackTraceInfo());
		}
		return null;
	}

	private void dialogClose() {
		if (dialog!=null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		dialogClose();
		message.setData(bundle);
		if(completeListener!=null){
		completeListener.onComplete(message);
		}
	}
	public void setOnCompleteListener(OnCompleteListener completeListener) {
		this.completeListener = completeListener;
	}
}
