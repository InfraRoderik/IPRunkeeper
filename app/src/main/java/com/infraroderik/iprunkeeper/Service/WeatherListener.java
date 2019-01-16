package com.infraroderik.iprunkeeper.Service;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface WeatherListener {
    public void onWeatherAvailable(JSONObject object);

    public void onWeatherError(VolleyError error);
}

