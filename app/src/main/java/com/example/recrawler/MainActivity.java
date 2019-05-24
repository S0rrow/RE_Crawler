package com.example.recrawler;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    List<String> timetableStirng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.timetable);
        timetableStirng = getIntent().getStringArrayListExtra("timetable");

        System.out.println("main");
        System.out.println("main");
        System.out.println("main");
        for(String test:timetableStirng){
            System.out.print(test);
        }
    }

    private void load(){
        for(String str: timetableStirng){
            String cache = textView.getText().toString();
            textView.setText(cache+"str");
        }
    }
}
