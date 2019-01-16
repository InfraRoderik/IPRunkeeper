package com.infraroderik.iprunkeeper.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


import static java.lang.Thread.sleep;

public class ApiHandler {
    RequestQueue queue;
    WeatherListener listener;
    String URLDIRECTIONS = "https://api.openweathermap.org/data/2.5/weather?";
    String API_KEY = "5c95a1db9e699ee7e677abd2fb9362cd";
    Context context;

    //Constructor
    public ApiHandler(Context context, WeatherListener listener) {
        this.listener = listener;
        queue = Volley.newRequestQueue(context);
        this.context = context;
    }

    public void getWeather() {
        LatLng point = new LatLng(51.5837165, 4.7952776);
        String url = URLDIRECTIONS + "lat=" + point.latitude + "&lon=" + point.longitude + "&units=metric&APPID=" + API_KEY;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onWeatherAvailable(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onWeatherError(error);
                    }
                }
        );

        this.queue.add(request);
    }
}