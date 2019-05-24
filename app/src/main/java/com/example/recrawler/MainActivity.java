package com.example.recrawler;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    TextView textView;
    List<String> timetableStirng;
    ArrayList<String>newTable = new ArrayList<>();
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.timetable);
        btn = (Button)findViewById(R.id.btn);

        timetableStirng = getIntent().getStringArrayListExtra("timetable");

        Log.d("yoon","1111");
        for (int i = 0; i < timetableStirng.size(); i++) {
            String str = timetableStirng.get(i);
            Log.d("yoon", str);
            newTable.add(str);
        }
        Log.d("yoon","22222");
        load();
        Log.d("yoon","44444");

        btn.setOnClickListener(this);

    }

    private void load(){
        Log.d("yoon","33333");
        StringBuffer sb = new StringBuffer();
        for(String str: newTable){
            Log.d("yoon", str);
            sb.append(str);
        }
        textView.setText(sb.toString());
    }

    @Override
    public void onClick(View v) {
        load();
    }
}
