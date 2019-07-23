package com.prime.recipejungle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prime.recipejungle.activities.LoginActivity;
import com.prime.recipejungle.activities.RegisterActivity;
import com.prime.recipejungle.activities.CreateActivity;
import com.prime.recipejungle.activities.UpdateActivity;
import com.prime.redef.app.App;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.startActivity(this, UpdateActivity.class, null);
        //App.startActivity(this, CreateActivity.class, null);
        finish();
    }
}
