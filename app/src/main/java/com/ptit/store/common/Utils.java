package com.ptit.store.common;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.ptit.store.models.response.HeaderProfile;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by TuanAnhKid on 3/30/2018.
 */

public class Utils {
    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static void setSharePreferenceValues(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSharePreferenceValues(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, null);
    }

    public static void saveHeaderProfile(Context context, HeaderProfile headerProfile) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        if (headerProfile == null) {
            editor.putBoolean(Constants.PRE_HEADER_PROFILE_AVAILABLE, false);
            editor.putString(Constants.PRE_AVATAR_URL, null);
            editor.putString(Constants.PRE_FULL_NAME, null);
            editor.putString(Constants.PRE_EMAIL, null);
            editor.putString(Constants.PRE_PHONE, null);
        } else {
            editor.putBoolean(Constants.PRE_HEADER_PROFILE_AVAILABLE, true);
            editor.putString(Constants.PRE_AVATAR_URL, headerProfile.getAvatarUrl());
            editor.putString(Constants.PRE_FULL_NAME, headerProfile.getFullName());
            editor.putString(Constants.PRE_EMAIL, headerProfile.getEmail());
            editor.putString(Constants.PRE_PHONE, headerProfile.getPhone());

        }
        editor.apply();
    }

    public static HeaderProfile getHeaderProfile(Context context) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        HeaderProfile headerProfile=null;
        if (preferences.getBoolean(Constants.PRE_HEADER_PROFILE_AVAILABLE, false)) {
            headerProfile = new HeaderProfile();
            headerProfile.setAvatarUrl(preferences.getString(Constants.PRE_AVATAR_URL, null));
            headerProfile.setFullName(preferences.getString(Constants.PRE_FULL_NAME, null));
            headerProfile.setEmail(preferences.getString(Constants.PRE_EMAIL, null));
            headerProfile.setPhone(preferences.getString(Constants.PRE_PHONE,null));
        }
        return headerProfile;
    }

    public static String getTimeAndDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.HOUR) +
                ":" +
                calendar.get(Calendar.MINUTE) +
                "  " +
                calendar.get(Calendar.DAY_OF_MONTH) +
                "/" +
                calendar.get(Calendar.MONTH) +
                "/" +
                calendar.get(Calendar.YEAR);
    }

    public static void dialogShowDate(Activity activity, String title, DatePickerDialog.OnDateSetListener dateChangedListener) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(activity,
                dateChangedListener,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH) + 1,
                now.get(Calendar.DAY_OF_MONTH));
        dpd.setTitle(title);
        dpd.show();
    }
    public static long millisecondsFromDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date mDate = sdf.parse(date);
            return mDate.getTime();
        } catch (ParseException ignored) {
            Log.i("timeerro", "millisecondsFromDate: "+ignored.toString());
            return 0;
        }
    }
    public static String formatNumberMoney(double salary) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(salary);
    }
    public static String getDateFromMilliseconds(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static boolean checkNetwork(Context context) {
        boolean available = false;
        ConnectivityManager cn = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cn.getActiveNetworkInfo();
        if (info != null && info.isAvailable() && info.isConnected()) {
            available = true;
        }
        return available;
    }

    public static boolean isEmailValid(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static void rateApp(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

}
