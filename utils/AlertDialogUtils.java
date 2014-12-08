package com.wink.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

import com.wink.R;
import com.wink.custom.view.CustomAlertDialog;
import com.wink.webservices.HttpConstants;
import com.wink.webservices.responsebean.BaseResponse;

public class AlertDialogUtils {

	/**
	 * this method will produce an alert dialog showing an error message.
	 * 
	 * @param context
	 * @param msg
	 * @param view
	 *            to set focus on dialog close.
	 * @return
	 */
	public static AlertDialog getAlertDialog(Context context, String title, String msg,
			final View view, DialogInterface.OnDismissListener dismissListener,
			OnClickListener clickListener) {



		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(msg);
		if (dismissListener != null) {
			alertDialog.setOnDismissListener(dismissListener);
		}
		if (clickListener != null) {
			alertDialog.setButton(context.getString(android.R.string.ok),
					clickListener);
		} else {
			alertDialog.setButton(context.getString(android.R.string.ok),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							if (view != null) {
								view.requestFocus();
							}

						}
					});
		}
//
		alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
		return alertDialog;
	}

	public static AlertDialog createAlertDialog (Context context, String msg, String positiveButtonText , OnClickListener positiveClickListener, String negativeButtonText ,OnClickListener nagativeClickListener) {    	
		
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(context.getString(R.string.alert_title));
		alertDialog.setMessage(msg);
		if(positiveClickListener != null){
			alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, positiveButtonText, positiveClickListener);
		}
		
		if(nagativeClickListener != null){
			alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, negativeButtonText, nagativeClickListener);
		}
		return alertDialog;
    }
	
	
	public static AlertDialog getAlertDialog(Context context, String title, String msg,
			final View view) {
		return getAlertDialog(context, title, msg, view, null, null);
	}

	public static AlertDialog getAlertDialog(Context context, String title, String msg) {
		return getAlertDialog(context, title, msg, null, null, null);
	}

	public static AlertDialog getAlertDialog(Context context, String title, String msg,
			OnClickListener clickListener) {
		return getAlertDialog(context, title, msg, null, null, clickListener);
	}

	public static AlertDialog getAlertDialog(Context context, String msg) {
		return getAlertDialog(context,
				context.getString(R.string.alert_title),
				msg, null, null, null);
	}

	public static void showAlert(Context context, String message) {
//        AlertDialogUtils.getAlertDialog(context, message).show();
        CustomAlertDialog.getAlertDialog(context, message).show();
	}

	public static void alertFailureResponse(Context context, BaseResponse response) {

		if (response.status == null || response.status != HttpConstants.RESPONSE_CODE.RESPONSE_SUCCESS) {
//            AlertDialogUtils.getAlertDialog(context, context.getString(
//                    R.string.alert_title), response.message).show();
            CustomAlertDialog.getAlertDialog(context, context.getString(
                    R.string.alert_title), response.message).show();
		}
	}
}
