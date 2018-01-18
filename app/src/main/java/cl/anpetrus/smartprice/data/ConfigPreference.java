package cl.anpetrus.smartprice.data;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigPreference {
    private static final String GROUP_CONFIG_KEY = "cl.anpetrus.smartprice.data.GROUP_PHOTO_KEY";
    private static final String EXPLAIN_KEY = "cl.anpetrus.smartprice.data.PHOTO_KEY";
    private Context context;

    public ConfigPreference(Context context) {
        this.context = context;
    }

    public void showExplainSave(Boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GROUP_CONFIG_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(EXPLAIN_KEY, String.valueOf(!value));
        prefEditor.apply();
    }

    public boolean getShowExplain() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GROUP_CONFIG_KEY, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(EXPLAIN_KEY, null);
        return value == null || value.equals(Boolean.toString(true));
    }
}
