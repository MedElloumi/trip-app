package com.mohamedelloumi.tripapp.utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Utils {
    /**
     * Fetch local JSON file from Asset folder and return it as a String
     * @param fileName The file name to be parsed
     * @return
     */
    public static String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = ApplicationContext.get().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Convert a String into a JSON Array
     * @param result The json data on a String format
     * @return
     */
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
