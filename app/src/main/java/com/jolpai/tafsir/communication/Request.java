
package com.jolpai.tafsir.communication;

import android.content.Context;
import android.util.Log;

import com.jolpai.tafsir.custom.exception.CustomException;
import com.jolpai.tafsir.custom.exception.ErrorCode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author User
 */
public class Request {
	private Context context;
    private String  type;
    private String mobileNumber;
    private String userCode;
    private String password;
    private String imei;
    private JSONObject data;


    private Request(){}
    public Request(String type){
    	this.type=type;
        this.data=new JSONObject();
    	this.userCode="abc";
    	this.password="123";
        this.mobileNumber="01712757945";
    }

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
		this.imei=Utility.getIMEInumber(this.context);
	}



	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public String toJson() throws JSONException {
    	Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy hh:mm:ss a");
		JSONObject jObj = new JSONObject();
		jObj.put("user_code", Utility.md5(userCode));
		jObj.put("password",  Utility.md5(password));
		jObj.put("imei", imei);
		jObj.put("type", type);
        jObj.put("mobile_number", mobileNumber);
		return jObj.toString();
    }


	public Response send(String url) throws CustomException {


		try {
			    String data = null;
			    InputStream inputStream = null;
				BufferedReader bufferedReader = null;

				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		    	Log.e("REQUEST : ",this.toJson());
				nameValuePair.add(new BasicNameValuePair("data", this.toJson()));
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
				HttpResponse httpResponse = httpClient.execute(httpPost);
			    HttpEntity resEntity = httpResponse.getEntity();
				if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				{
					inputStream = resEntity.getContent();
					bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"),8);

					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {sb.append(line);}
					inputStream.close();
					data = sb.toString();

					httpClient.getConnectionManager( ).shutdown( );
					return ModelProvider.getResponseModel(data);
				}
				else {
					throw new CustomException(ErrorCode.HTTP_STATUS,"STATUS CODE : "+httpResponse.getStatusLine().getStatusCode());
				}


		} catch (UnsupportedEncodingException e){
			throw new CustomException(ErrorCode.UNSUPPORTED_ENCODING_EXCEPTION,"UNSUPPORTED ENCODING EXCEPTION" ,e);
		} catch (ClientProtocolException e) {
			throw new CustomException(ErrorCode.CLIENT_PROTOCOL_EXCEPTION,"CLIENT PROTOCOL EXCEPTION ",e);
		}
		catch (HttpHostConnectException e) {
			throw new CustomException(ErrorCode.HTTP_HOST_CONNECT_EXCEPTION, "HTTP HOST CONNECT EXCEPTION", e);
		}
		catch (ConnectTimeoutException e) {
			throw new CustomException(ErrorCode.CONNECT_TIMEOUT_EXCEPTION,"CONNECT TIMEOUT EXCEPTION",e);
		}
		catch (SocketTimeoutException e) {
			throw new CustomException(ErrorCode.SOCKET_TIMEOUT_EXCEPTION ,"SOCKET TIMEOUT EXCEPTION",e);
		}
		catch (ConnectException e)
		{
			throw new CustomException(ErrorCode.CONNECT_EXCEPTION,"UNSUPPORTED ENCODING",e);
		}
		catch (IOException e) {
			throw new CustomException(ErrorCode.IO_EXCEPTION,"UNSUPPORTED_ENCODING",e);
		}
		catch (JSONException e) {
			throw new CustomException(ErrorCode.JSON_EXCEPTION,"JSON EXCEPTION",e);
		}
		

	}
    
 
    
}
