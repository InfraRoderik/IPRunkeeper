package com.infraroderik.iprunkeeper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;
import com.infraroderik.iprunkeeper.DataModel.Traject;
import com.infraroderik.iprunkeeper.Service.ApiHandler;
import com.infraroderik.iprunkeeper.Service.DataStorage;

import java.util.ArrayList;

import com.infraroderik.iprunkeeper.Service.NotificationService;
import com.infraroderik.iprunkeeper.Service.WeatherListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements WeatherListener {
    private Button newRouterButton;
    private ListView previousRoutes;
    private ArrayAdapter routeAdapter;
    private DataStorage storage;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView weather_text = (TextView)findViewById(R.id.weather_text);
        TextView temperature_text = (TextView)findViewById(R.id.temperature_text);
        weather_text.setText(R.string.weather_text);
        temperature_text.setText(R.string.temperature_text);
        ApiHandler apiHandler = new ApiHandler(this, this);
        apiHandler.getWeather();
        apiHandler = new ApiHandler(this, this);
        newRouterButton = findViewById(R.id.button2);
        newRouterButton.setText(R.string.new_route_button);
        previousRoutes = findViewById(R.id.list);

        storage = new DataStorage(getApplicationContext());
        ArrayList<Traject> trajects = new ArrayList<>();
        trajects = (ArrayList<Traject>) this.storage.retrieveAllTrajects();
        routeAdapter = new ArrayAdapter<Traject>(this, android.R.layout.simple_list_item_1, trajects);

        routeAdapter = new RouteAdapter(getApplicationContext(), trajects);

        previousRoutes.setAdapter(routeAdapter);

        ArrayList<Traject> finalTrajects = trajects;
        previousRoutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("FURBY_TAG", "" + position);

                Intent intent = new Intent(
                        getApplicationContext(),
                        DetailActivity.class
                );

                Traject traject = finalTrajects.get(position);
                intent.putExtra("FURBY_OBJECT", traject);

                startActivity(intent);

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            } else {
                checkLocationPermission();
            }
        }

        newRouterButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MapsActivity.class);
            startActivity(intent);
        });
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AppTheme);
                dialogBuilder
                        .setTitle(R.string.permission_title)
                        .setMessage(R.string.permission_message)
                        .setPositiveButton("Ok", (dialogInterface, i) -> {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onWeatherAvailable(JSONObject object) {
        try {

            TextView temperature = (TextView)findViewById(R.id.txtTemperature);
            TextView weather = (TextView)findViewById(R.id.txtWeather);
            temperature.setText(object.getJSONObject("main").get("temp").toString() + "°C");
            weather.setText(object.getJSONArray("weather").getJSONObject(0).get("main").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWeatherError(VolleyError error) {
    }
}
