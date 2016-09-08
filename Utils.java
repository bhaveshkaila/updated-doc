package com.golden11.app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.golden11.app.R;
import com.golden11.app.activity.LoginActivity;
import com.golden11.app.localStorage.Constants;
import com.golden11.app.localStorage.PreferencesHelper;
import com.golden11.app.model.AppConfiguration;
import com.golden11.app.model.CreateTeamPlayer;
import com.golden11.app.model.LangObject;
import com.golden11.app.model.MyTeam;
import com.golden11.app.model.ScoutingPlayerFilterTeamPositionList;
import com.golden11.app.model.ScoutingPlayerList;
import com.golden11.app.model.TeamDetail;
import com.golden11.app.model.Trainer;
import com.golden11.app.model.UndObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This the common class function that provide the static method
 *
 * @version 1.0.
 */
public class Utils {

    public static final int INFO = 1;
    public static final int DEBUG = 2;
    public static final int ERROR = 3;
    public static int[] team_formation = {4, 4, 2};
    public static boolean SHOW_SECOND_HEADER_VIEW = false;
    public static boolean SCOUTING_LIST_SHOW_SECOND_HEADER_VIEW = false;
    public static TeamDetail teamDetail;
    public static List<MyTeam> teamlist;
    public static Map<String, ScoutingPlayerList> mAllPlayer;
    public static List<ScoutingPlayerList> mScoutingPlayerLists;
    public static List<Trainer> mTrainerLists;
    public static List<ScoutingPlayerFilterTeamPositionList> mScoutingFilterLists;
    public static int mTotalTeamScore;
    //position id for forwarder or mid-fielder or defender or goalkeeper
    public static String SELECTED_POSITION_ID_FOR_TRANSFER = "selected_position_for _transfer";
    public static List<String> mFilteredTeamIdList;
    public static List<String> mFilteredPlayerIdList;
    public static AppConfiguration mConfiguration = new AppConfiguration();
    public static int lastSelectedPosition;
    private static List<CreateTeamPlayer> playerList;
    private static Context context;
    private static Dialog popupDialog;

    /**
     * Used to initialize preference of the application
     * This method should be called very first line of activity
     *
     * @param ctx : Context
     */
    public static void initializePreferences(Context ctx) {
        context = ctx;
    }

    /**
     * Used to check Internet connection available or not
     *
     * @return
     */
    public static boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            showLog(INFO, "***Available***");
            return true;
        }
        Log.e("Network Testing", "***Not Available***");
        return false;
    }

    public static void clearDataAndLogin(Context context) {
        PreferencesHelper preferencesHelper = new PreferencesHelper(context);
        preferencesHelper.putPrefBoolean(preferencesHelper.IS_LOGGED_IN, false);
        preferencesHelper.clear();

        //Screen redirect to home screen
        Intent mIntent = new Intent(context, LoginActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(mIntent);
        ((Activity) context).finish();
    }

    /**
     * This function is used to get values from und object
     *
     * @param object
     * @return
     */
    public static String undParse(Object object) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        try {
            UndObject und = null;
            if (!object.toString().equals("[]")) {
                und = (UndObject) gson.fromJson(object.toString(), UndObject.class);
                return und.getUnd().get(0).getValue();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This function is used to get values based on selected language
     *
     * @param object
     * @return
     */
    public static String langParse(Object object) {
        Gson gson = new Gson();
        try {
            LangObject und = null;
            if (!object.toString().equals("[]")) {
                und = gson.fromJson(object.toString(), LangObject.class);
                return und.getEn().get(0).getValue();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This function will encode base 64 from bitmap
     *
     * @param image
     * @return
     */
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    /**
     * method to check whether email address valid or not
     *
     * @param email pass email here
     * @return true in case email is valid else false
     */
    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;

        String EMAIL_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";
        //String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Check that phone number has min 7 and max 15 char
     *
     * @param phNumberStr number
     * @return
     */
    public static boolean isPhNumberValid(String phNumberStr) {

        if (phNumberStr.length() < 7 || phNumberStr.length() > 15) {
            return false;
        }
        return true;
    }

    /**
     * This will used to display logs
     *
     * @param log
     * @param message
     */
    public static void showLog(int log, String message) {

        switch (log) {
            case INFO:
                Log.i("LOG", message);
                break;

            case DEBUG:
                Log.d("LOG", message);
                break;

            case ERROR:
                Log.e("LOG", message);
                break;

        }
    }


    /**
     * Used to set language of the application
     *
     * @param act
     * @param lanCode
     */
    public static void setLanguage(Activity act, String lanCode) {
        Locale locale = new Locale(lanCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        act.getBaseContext()
                .getResources()
                .updateConfiguration(config,
                        act.getBaseContext().getResources().getDisplayMetrics());
        // onCreate(null);
    }


    /**
     * Used to show loader in application
     *
     * @param act
     */
    public static void showLoader(Activity act) {
        if (act != null && !act.isFinishing()) {

			/*if(popupDialog!=null && popupDialog.isShowing())
            {
				popupDialog.cancel();
			}
			*/
            LayoutInflater inflater = LayoutInflater.from(act);
            View contentView = inflater.inflate(R.layout.loading_layout, null);
            popupDialog = new Dialog(act, R.style.DialogLoadingAnimation);// R.style.DialogSlideAnim);//
            // R.style.fadeInFadeOut);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(popupDialog.getWindow().getAttributes());
            // lp.width = convertToDp(act,300);
            //lp.height = convertToDp(act,80);
            ProgressBar progressBar = (ProgressBar) contentView.findViewById(R.id.custom_progressbar);
            progressBar.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
            popupDialog.getWindow().setAttributes(lp);

            popupDialog.setCanceledOnTouchOutside(false);
            popupDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                }
            });

            popupDialog.getWindow();

            popupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            popupDialog.setContentView(contentView);

            popupDialog.show();
        }
    }

    /**
     * This function is used to convert to dp.
     *
     * @param context    pass context here
     * @param pixelValue size in pixel
     * @return actual pixel sixe
     */
    public static int convertToDp(Context context, int pixelValue) {
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixelValue * scale + 0.5f);
    }

    /**
     * Used to hide loader of the application
     *
     * @param act
     */
    public static void hideLoader(Activity act) {
        if (act != null && !act.isFinishing() && popupDialog != null)
            popupDialog.cancel();
    }

    /**
     * This method is used to set focus on view
     *
     * @param view pass view here
     */
    public static void requestFocus(Activity context, View view) {
        if (view.requestFocus()) {
            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * Genral message display for errors
     *
     * @param activity
     */
    public static void showAlert(final Activity activity, String msg, final OnClickAlertViewListner onClickAlertViewListner) {

        if (activity != null && !activity.isFinishing()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

            // Setting Dialog Title
            //alertDialog.setTitle("");

            // Setting Dialog Message
            alertDialog.setMessage(msg);

            // On pressing Settings button
            alertDialog.setPositiveButton(activity.getString(R.string.login_ok_action), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    onClickAlertViewListner.okClick();


                }
            });

            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dialog.cancel();
                }
            });
            /*// on pressing cancel button
            alertDialog.setNegativeButton(activity.getString(R.string.login_validation_ok_action), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    onClickAlertViewListner.cancelClick();
                }
            });*/

            // Showing Alert Message
            alertDialog.show();

        }
    }

    /**
     * Genral message display for errors
     *
     * @param activity
     */
    public static void showYesNoAlert(final Activity activity, String msg, final OnClickAlertViewListner onClickAlertViewListner) {

        if (activity != null && !activity.isFinishing()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

            // Setting Dialog Title
            //alertDialog.setTitle("");

            // Setting Dialog Message
            alertDialog.setMessage(msg);

            // On pressing Settings button
            alertDialog.setPositiveButton(activity.getString(R.string.btn_yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    onClickAlertViewListner.okClick();


                }
            });

            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dialog.cancel();
                }
            });
            // on pressing cancel button
            alertDialog.setNegativeButton(activity.getString(R.string.btn_no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }
    }

    /**
     * Genral message display for errors
     *
     * @param activity
     */
    public static void showAlert(final Activity activity, String msg, final OnClickAlertViewOkCancelListner onClickAlertViewOkCancelListner) {

        if (activity != null && !activity.isFinishing()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

            // Setting Dialog Title
            //alertDialog.setTitle("");

            // Setting Dialog Message
            alertDialog.setMessage(msg);

            // On pressing Settings button
            alertDialog.setPositiveButton(activity.getString(com.golden11.app.R.string.login_ok_action), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    onClickAlertViewOkCancelListner.okClick();
                }
            });

            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    onClickAlertViewOkCancelListner.cancelClick();
                }
            });

            alertDialog.setNegativeButton(activity.getString(com.golden11.app.R.string.login_cancel_action), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    onClickAlertViewOkCancelListner.cancelClick();
                }
            });
            /*// on pressing cancel button
            alertDialog.setNegativeButton(activity.getString(R.string.login_validation_ok_action), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    onClickAlertViewListner.cancelClick();
                }
            });*/

            // Showing Alert Message
            alertDialog.show();

        }
    }

    /**
     * Genral message display for errors
     *
     * @param activity
     */
    public static void showAlert(final Activity activity, String msg) {

        if (activity != null && !activity.isFinishing()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

            // Setting Dialog Title
            //alertDialog.setTitle("");

            // Setting Dialog Message
            alertDialog.setMessage(msg);

            // On pressing Settings button
            alertDialog.setPositiveButton(activity.getString(com.golden11.app.R.string.login_ok_action), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });

            /*// on pressing cancel button
            alertDialog.setNegativeButton(activity.getString(R.string.login_validation_ok_action), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    onClickAlertViewListner.cancelClick();
                }
            });*/

            // Showing Alert Message
            alertDialog.show();

        }
    }

    /**
     * Genral message display for errors
     *
     * @param activity
     * @param title title
     */
    public static void showAlert(final Activity activity,String title, String msg) {

        if (activity != null && !activity.isFinishing()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

            // Setting Dialog Title
            alertDialog.setTitle(title);

            // Setting Dialog Message
            alertDialog.setMessage(msg);

            // On pressing Settings button
            alertDialog.setPositiveButton(activity.getString(com.golden11.app.R.string.login_ok_action), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });

            /*// on pressing cancel button
            alertDialog.setNegativeButton(activity.getString(R.string.login_validation_ok_action), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    onClickAlertViewListner.cancelClick();
                }
            });*/

            // Showing Alert Message
            alertDialog.show();

        }
    }

    /**
     * Returns false if string is null or empty or blank.
     *
     * @param str pass string
     * @return true if string is blank
     */
    public static boolean isBlank(String str) {
        if (str == null || str.trim().equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }

    /**
     * This function is used to get the selected player list for create team
     *
     * @return player list
     */
    public static List<CreateTeamPlayer> getSelectedPlayerList() {
        return playerList;
    }

    /**
     * This function is used to set create team player list
     *
     * @param createTeamPlayers
     */
    public static void setSelectedPlayerList(List<CreateTeamPlayer> createTeamPlayers) {
        playerList = createTeamPlayers;
    }

    public static String formateDateFromString(String inputFormat, String outputFormat, String dateBefore) {

        Date parsed = null;
        String outputDate = "";

        if (android.text.format.DateFormat.is24HourFormat(context)) {
            // 24 hour format
            outputFormat = outputFormat.replace("hh", "HH").replace("a", "");
        }


        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            parsed = df_input.parse(dateBefore);
            outputDate = df_output.format(parsed).replace("am", "AM").replace("pm", "PM").replace("a.m.", "AM").replace("p.m.", "PM");

        } catch (ParseException e) {
            showLog(INFO, "ParseException - dateFormat");
        }

        return outputDate;

    }

    public static Map<String, ScoutingPlayerList> getAllPlayerList() {
        return mAllPlayer;
    }

    public static void setAllPlayerList(Map<String, ScoutingPlayerList> mPlayerList) {
        mAllPlayer = mPlayerList;
    }

    public static List<ScoutingPlayerFilterTeamPositionList> getScoutingFilterLists() {
        return mScoutingFilterLists;
    }

    public static void setScoutingFilterLists(List<ScoutingPlayerFilterTeamPositionList> mScoutingFilterLists) {
        Utils.mScoutingFilterLists = mScoutingFilterLists;
    }

    /**
     * This function is used to display correct positions GL,MD,FW,DF
     *
     * @param position_id
     * @param tvPlayerPosition
     */
    public static void setPlayerPosition(String position_id, CustomTextView tvPlayerPosition) {

        switch (Integer.parseInt(position_id)) {
            case Constants.POSITION_GOAL_KEEPER:
                //Goalkeeper
                tvPlayerPosition.setBackgroundResource(R.drawable.player_green_bg);
                break;
            case Constants.POSITION_DEFENDER:
                //Defender
                tvPlayerPosition.setBackgroundResource(R.drawable.player_yellow_bg);
                break;
            case Constants.POSITION_MID_FILDER:
                //Midfielder
                tvPlayerPosition.setBackgroundResource(R.drawable.player_red_bg);
                break;
            case Constants.POSITION_FORWARD:
                //Forward
                tvPlayerPosition.setBackgroundResource(R.drawable.player_orange_bg);
                break;
        }

    }

    /**
     * This function is used to set players properties in scoting player list or other screens
     * @param playerProperties selected properties
     * @param ivPlayerProperties imageview
     */
    public static void setPlayerProperties(String playerProperties, ImageView ivPlayerProperties) {
        if(playerProperties.equals(Constants.PROPERTIES_INJURED)){
            ivPlayerProperties.setImageResource(R.drawable.plus_sign);
        }else if(playerProperties.equals(Constants.PROPERTIES_SUSPENDED)){
            ivPlayerProperties.setImageResource(R.drawable.exclaim_sign);
        }else if(playerProperties.equals(Constants.PROPERTIES_UN_CERTAIN)){
            ivPlayerProperties.setImageResource(R.drawable.que_sign);
        }else if(playerProperties.equals(Constants.PROPERTIES_BACK_TO_BUSINESS)){
            ivPlayerProperties.setImageResource(R.drawable.reload_sign);
        }else if(playerProperties.equals(Constants.PROPERTIES_UP)){
            ivPlayerProperties.setImageResource(R.drawable.up_sign);
        }else if(playerProperties.equals(Constants.PROPERTIES_DOWN)){
            ivPlayerProperties.setImageResource(R.drawable.down_sign);
        }else if(playerProperties.equals(Constants.PROPERTIES_SAME_SCORE)){
            ivPlayerProperties.setImageResource(R.drawable.equal_sign);
        }else {
            ivPlayerProperties.setImageResource(R.drawable.reload_sign);
        }

    }


    public interface OnClickAlertViewListner {
        void okClick();

    }

    public interface OnClickAlertViewOkCancelListner {
        void okClick();

        void cancelClick();
    }

    public static String getQueryParam(String teamName,String week){

        if(week.equals("0")){
            week="";
        }
        //add query param in url
        String param="?";
        if(!Utils.isBlank(teamName) && !Utils.isBlank(week)){
            param=param+"team="+teamName+"&"+"week="+week;
        }else if(Utils.isBlank(teamName) && !Utils.isBlank(week)){
            param=param+"week="+week;
        }else if (!Utils.isBlank(teamName) && Utils.isBlank(week)){
            param=param+"team="+teamName;
        }else{
            param="";
        }

        return param;
    }
}
