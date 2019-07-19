package com.prime.recipejungle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prime.recipejungle.activities.CreateActivity;
import com.prime.recipejungle.activities.HelloActivity;
import com.prime.redef.app.App;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.startActivity(this, CreateActivity.class, null);
        finish();
    }
}
