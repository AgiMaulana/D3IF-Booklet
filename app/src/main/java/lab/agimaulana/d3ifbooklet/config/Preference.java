package lab.agimaulana.d3ifbooklet.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 5/29/16.
 */
public class Preference {
    private static final String SETTING_SEARCH = "setting-search";

    public static boolean isFirstRun(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("isFirstRun", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("first-run", false);
    }

    public static boolean markHasFirstRun(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("isFirstRun", Context.MODE_PRIVATE).edit();
        editor.putBoolean("first-run", true);
        return editor.commit();
    }

    public static boolean setSearchSetting(Context context, String bookletType, boolean value){
        SharedPreferences.Editor editor = context.getSharedPreferences(SETTING_SEARCH, Context.MODE_PRIVATE).edit();
        editor.putBoolean(bookletType, value);
        return editor.commit();
    }

    public static boolean getSearchSetting(Context context, String bookletType){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SETTING_SEARCH, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(bookletType, true);
    }
}
