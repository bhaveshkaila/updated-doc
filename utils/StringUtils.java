package com.wink.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.wink.fragment.ResetPasswordFragment;

import android.util.Log;

/**
 * String utility class to perform common string operation.
 * 
 * @author bhavesh.kaila
 * 
 */
public class StringUtils {
	
	private static String TAG = StringUtils.class.getSimpleName();
	/**
	 * Returns false if string is null or empty or blank.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		if (str == null || str.trim().equalsIgnoreCase(""))
			return false;
		return true;
	}

	/**
	 * Returns false if string is null or empty or blank.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (str == null || str.trim().equalsIgnoreCase(""))
			return true;
		return false;
	}

	public static String getMD5HashString(String chk) {

		MessageDigest digest;
		String codedValue = "";
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.update(chk.getBytes());
			byte messageDigest[] = digest.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				byte b = messageDigest[i];
				String hex = Integer.toHexString((int) 0x00FF & b);
				if (hex.length() == 1) {
					sb.append("0");
				}
				sb.append(hex);
			}
			codedValue = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			Log.d(TAG, e.toString());
			e.printStackTrace();
		}

		return codedValue;
	}


	public static String formatDecimal(String string) {
		String finalFormatedValus = "";
		NumberFormat nf = NumberFormat.getNumberInstance();
		String pattern = ((DecimalFormat) nf).toPattern();
		NumberFormat newFormat = new DecimalFormat(pattern);

		if (string == null || string.length() == 0)
			finalFormatedValus = "-";
		else
			finalFormatedValus = newFormat.format(Double.valueOf(string));

		return finalFormatedValus;
	}

}
