package com.infraroderik.iprunkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.infraroderik.iprunkeeper.DataModel.Traject;

public class Main3Activity extends AppCompatActivity {
    private Traject traject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        traject = (Traject)getIntent().getExtras().get("TRAJECT");
        //Toast.makeText(this, traject.toString(), Toast.LENGTH_LONG).show();
    }
}
