package com.jolpai.tafsir.communication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


import com.jolpai.tafsir.R;
import com.jolpai.tafsir.custom.view.MyToast;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


// TODO: Auto-generated Javadoc

/**
 * The Class Utility.
 */
public class Utility {

	/**
	 * Create <strong>MD5</strong> digest of data and return.
	 * 
	 * @param data
	 *            The String data which will be used to create the
	 *            <strong>MD5</strong> digest
	 * @return The <strong>MD5</strong> digest
	 */
	public static String md5(String data) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(data.getBytes("UTF-8"));

			// converting byte array to Hexadecimal String
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}

			digest = sb.toString();

		} catch (UnsupportedEncodingException ex) {
			// Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE,
			// null, ex);
		} catch (NoSuchAlgorithmException ex) {
			// Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE,
			// null, ex);
		}
		return digest;
	}

	/**
	 * Read the device IMEI and return.
	 * 
	 * @param ctx
	 *            The application context
	 * @return The Device's IMEI or <strong>null</strong> if the device doesn't
	 *         have any IMEI
	 */
	public static String getIMEInumber(Context ctx) {
		String identifier = null;
		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm != null)
			identifier = tm.getDeviceId();
		if (identifier == null || identifier.length() == 0)
			identifier = Secure.getString(ctx.getContentResolver(),
					Secure.ANDROID_ID);

		return identifier;
	}

	/**
	 * Get Application version name.
	 * 
	 * @param ctx
	 *            The application context
	 * @return The application version name
	 */
	public static String getVersionName(Context ctx) {
		String versionName = "unknown";
		try {
			PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);
			versionName = pinfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * Set screen brightness .
	 * 
	 * @param context
	 *            The application context
	 * @param value
	 *            The brightness value. Min: 1, Max: 255
	 */
	public static void setScreenBrightness(Context context, float value) {
		float brightnessValue = value / 255;
		int brightnessMode = 1;
		try {
			brightnessMode = Settings.System.getInt(
					context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE);
		} catch (SettingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
			Settings.System.putInt(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE,
					Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
		}

		WindowManager.LayoutParams layoutParams = ((Activity) context)
				.getWindow().getAttributes();
		layoutParams.screenBrightness = brightnessValue; // set 50% brightness
		((Activity) context).getWindow().setAttributes(layoutParams);
	}

	/**
	 * Set the screen off timeout time .
	 * 
	 * @param ctx
	 *            The application context
	 * @param duraion
	 *            The time in millisecond
	 */
	public static void setScreenOffTimeoutTime(Context ctx, int duraion) {
		Settings.System.putInt(ctx.getContentResolver(),
				Settings.System.SCREEN_OFF_TIMEOUT, duraion);
	}

	/**
	 * Read device current screen off timeout time.
	 * 
	 * @param ctx
	 *            The application context
	 * @return The time in millisecond
	 */
	public static int getCurrentScreenTimeoutTime(Context ctx) {
		return Settings.System.getInt(ctx.getContentResolver(),
				Settings.System.SCREEN_OFF_TIMEOUT, -1);
	}

	/**
	 * Unlock device screen.
	 * 
	 * @param activity
	 *            The application context in Activity
	 */
	public static void unlockScreen(Activity activity) {
		Window window = activity.getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
	}

	/**
	 * Get date in millisecond from a formatted date
	 * <strong>(yyyy-MM-dd)</strong>.
	 * 
	 * @param dateStr
	 *            the date str
	 * @param dateFormat
	 *            the format
	 * @return The date in millisecond
	 * @throws ParseException
	 *             the parse exception
	 */
	public static long getMillisecondFromDate(String dateStr,
			SimpleDateFormat dateFormat) throws ParseException {
		if (dateStr == null)
			return 0;
		Log.e("getMillisecondFromDate", dateStr);

		Date date = dateFormat.parse(dateStr);
		long milliseconds = date.getTime();
		return milliseconds;
	}

	/**
	 * Get the formated date <strong>(yyyy-MM-dd)</strong> from millisecond.
	 * 
	 * @param millisecond
	 *            The date in millisecond
	 * @param dateFormat
	 *            the date format
	 * @return The Formated Date <strong>(yyyy-MM-dd)</strong>
	 */
	public static String getDateFromMillisecond(long millisecond,
			SimpleDateFormat dateFormat) {

		// Create a calendar object that will convert the date and time value in
		// milliseconds to date.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millisecond);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(calendar.MILLISECOND, 0);
		String dateStr = dateFormat.format(calendar.getTime());

		return dateStr;
	}
	
	public static String getDateTimeFromMillisecond(long millisecond,
			SimpleDateFormat dateFormat) {

		// Create a calendar object that will convert the date and time value in
		// milliseconds to date.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millisecond);
		String dateStr = dateFormat.format(calendar.getTime());
		return dateStr;
	}

	/**
	 * Gets the 12 hour time format.
	 * 
	 * @param time24
	 *            the time24
	 * @return the 12 hour time format
	 * @throws ParseException
	 *             the parse exception
	 */
	@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
	public static String get12HourTimeFormat(String time24)
			throws ParseException {
		SimpleDateFormat dataFormat24 = new SimpleDateFormat("hh:mm");
		Date date = dataFormat24.parse(time24);
		SimpleDateFormat dataFormat12 = new SimpleDateFormat("h:mm a");

		return dataFormat12.format(date).toLowerCase();
	}

	/**
	 * Send <strong>message</strong> to <strong>phoneNumber</strong> as SMS.
	 * 
	 * @param ctx
	 *            The application context
	 * @param phoneNumber
	 *            The phone number to which the message will be sent
	 * @param message
	 *            The message which will be sent.
	 */
	public static void sendSMS(final Context ctx, String phoneNumber,
			String message) {
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(ctx, 0, new Intent(
				SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(ctx, 0,
				new Intent(DELIVERED), 0);

		// ---when the SMS has been sent---
		ctx.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					//MyToast.show(ctx, "SMS sent");
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					//MyToast.show(ctx, "Generic failure");
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					//MyToast.show(ctx, "No service");
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					//MyToast.show(ctx, "Null PDU");
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					//MyToast.show(ctx, "Radio off");
					break;
				}
			}
		}, new IntentFilter(SENT));

		// ---when the SMS has been delivered---
		ctx.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
					case Activity.RESULT_OK:
					//	MyToast.show(ctx, "SMS delivered");
						break;
					case Activity.RESULT_CANCELED:
					//	MyToast.show(ctx, "SMS not delivered");
						break;
				}
			}
		}, new IntentFilter(DELIVERED));

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}



	/**
	 * Check if the device is connected with Internet.
	 *
	 * @param context The application context
	 * @return <strong>true</strong> if connected. <strong>false</strong> otherwise
	 */
	public static boolean isConnectedToInternet(Context context)
	{
		// Check intenet connectivity
		boolean connected = false;
		try
		{
			ConnectivityManager conMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

			connected = (   conMgr.getActiveNetworkInfo() != null &&
					conMgr.getActiveNetworkInfo().isAvailable() &&
					conMgr.getActiveNetworkInfo().isConnected()   );
		}catch (Exception e)
		{
			return false;
		}

		return connected;

	}

	/**
	 * Open the Internet settings activity of the device.
	 *
	 * @param context The application context
	 */
	public static void openInternetSettingsActivity(final Context context)
	{
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		/*alert.setIcon(R.drawable.yellow_warning);
		alert.setTitle(R.string.no_internet);
		alert.setMessage(R.string.connect_to_internet);
		alert.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				context.startActivity(new Intent(Settings.ACTION_SETTINGS));
			}
		});*/

		alert.show();
	}

	/**
	 * Turn On the device mobile data connection.
	 *
	 * @param context The application context
	 * @throws NoSuchMethodException the no such method exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	public static void turnOnDataConnection(Context context) throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		ConnectivityManager dataManager;
		dataManager  = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		Method dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
		dataMtd.setAccessible(true);
		dataMtd.invoke(dataManager, true);
	}

	/**
	 * Turn Off the device mobile data connection.
	 *
	 * @param context The application context
	 * @throws NoSuchMethodException the no such method exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	public static void turnOffDataConnection(Context context) throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		ConnectivityManager dataManager;
		dataManager  = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		Method dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
		dataMtd.setAccessible(true);
		dataMtd.invoke(dataManager, false);
	}
	 
}
