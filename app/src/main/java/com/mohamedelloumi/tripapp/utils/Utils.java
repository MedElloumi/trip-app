package com.mohamedelloumi.tripapp.utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = ApplicationContext.get().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static JSONArray convertStringIntoJSONArray(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
