package com.asus.zenbodialogsample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class changePage extends AppCompatActivity {
    public changePage() {
        //Context nextPage
        //Context thisPage
        Intent intent = new Intent();
        //intent.setClass(ZenboDialogSample, nextPage.getClass());
        startActivity(intent);
    }
}
