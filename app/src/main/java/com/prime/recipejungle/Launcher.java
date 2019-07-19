package com.prime.recipejungle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

<<<<<<< HEAD
import com.prime.recipejungle.activities.LoginActivity;
import com.prime.recipejungle.activities.RegisterActivity;
=======
import com.prime.recipejungle.activities.CreateActivity;
import com.prime.recipejungle.activities.HelloActivity;
>>>>>>> 32dd0034e69e4498a5858ad1612ebaab47267c4e
import com.prime.redef.app.App;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

<<<<<<< HEAD
        App.startActivity(this, LoginActivity.class, null);
=======
        App.startActivity(this, CreateActivity.class, null);
>>>>>>> 32dd0034e69e4498a5858ad1612ebaab47267c4e
        finish();
    }
}
