package com.jolpai.tafsir.communication;

import android.util.Log;

import com.jolpai.tafsir.custom.exception.CustomException;
import com.jolpai.tafsir.custom.exception.ErrorCode;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelProvider {

	// create response model from response data
	public static Response getResponseModel( String data)
			throws CustomException {
		try {
			Log.e("RESPONSE DATA : ",data);
			Response response =new Response();
			JSONObject jObj = new JSONObject(data);
			response.setType(jObj.getString("type"));
			response.setStatus(jObj.getString("status"));
			if (response.getStatus().equalsIgnoreCase("00")) {
				response.setErrorMsg(jObj.getString("error_msg"));
			} else {
				response.setData( new JSONObject(jObj.getString("data")));
			}
			return response;

		} catch (JSONException e) {
			throw new CustomException(ErrorCode.JSON_EXCEPTION,"JSON_EXCEPTION");
		}

	}

}
