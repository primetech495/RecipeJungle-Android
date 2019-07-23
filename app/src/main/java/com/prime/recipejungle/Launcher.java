package com.prime.recipejungle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prime.recipejungle.activities.LoginActivity;
import com.prime.recipejungle.activities.MyRecipesActivity;
import com.prime.recipejungle.activities.RegisterActivity;
import com.prime.recipejungle.activities.CreateActivity;
import com.prime.redef.app.App;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.startActivity(this, LoginActivity.class, null);
        //App.startActivity(this, MyRecipesActivity.class, null);
        finish();
    }
}
