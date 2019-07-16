package com.lmy.lmydemo.update_scroll_vew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lmy.lmydemo.R;

public class UpdateSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewCustomLeftScreen customLeftScreen = new NewCustomLeftScreen(getApplicationContext(), this);
        setContentView(customLeftScreen);
    }
}
