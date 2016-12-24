package com.example.mahtak.mapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mahtak.LifeCycleReporter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LifeCycleReporter reporter = new LifeCycleReporter();
        getApplication().registerActivityLifecycleCallbacks(reporter);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
