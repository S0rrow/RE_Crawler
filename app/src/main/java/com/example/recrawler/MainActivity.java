package com.example.recrawler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    TextView textView;
    List<String> timetableString;
    ArrayList<String>newTable = new ArrayList<>();
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.timetable);
        btn = (Button)findViewById(R.id.btn);

        timetableString = getIntent().getStringArrayListExtra("timetable");

        //Log.d("yoon","1111");
        for (int i = 0; i < timetableString.size(); i++) {
            String str = timetableString.get(i);
            //Log.d("yoon", str);
            newTable.add(str);
        }
        //Log.d("yoon","22222");
        load();
        //Log.d("yoon","44444");

        btn.setOnClickListener(this);

    }

    private void load(){
        //Log.d("yoon","33333");
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < newTable.size(); i++){
            String str = newTable.get(i);
            //Log.d("yoon", str);
            sb.append(str);
            if(i%7==6) sb.append("\n");
        }
        textView.setText(sb.toString());
    }

    @Override
    public void onClick(View v) {
        load();
    }
}
