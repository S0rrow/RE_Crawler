package com.example.recrawler;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public List<String> timeTable;
    private Crawler crawler;
    Intent intent;
    EditText input_id;
    EditText input_pw;
    Button tryLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent = new Intent(this, MainActivity.class);
        input_id = findViewById(R.id.input_id);
        input_pw = findViewById(R.id.input_pw);
        tryLoginBtn = findViewById(R.id.tryLoginBtn);
        load();
    }

    private void load(){
        tryLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryCrawl();
            }
        });
    };

    private void tryCrawl(){
        String id = input_id.getText().toString().trim();
        String pw = input_pw.getText().toString().trim();
        String errorMsg;
        if(id.length()==4) {
            errorMsg = "ERR:id null; try again";
            tryLoginBtn.setText(errorMsg);
        }
        else if(pw.length()<6){
            errorMsg = "ERR:pw not long enough; try again";
            tryLoginBtn.setText(errorMsg);
        }
        else{
            crawler = new Crawler(id, pw);
            crawler.execute();
            while(!crawler.done()){
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            timeTable = crawler.getTimeTable();
            if(crawler.check()){
                errorMsg = "SUCCESS!";
                tryLoginBtn.setText(errorMsg);
                System.out.println("button click test");
                for(String temp: timeTable){
                    System.out.println(temp);
                }
                intent.putStringArrayListExtra("timetable", (ArrayList<String>) timeTable);
                finish();
                startActivity(intent);
            }
            else{
                if(crawler.error instanceof IOException) errorMsg = "ERR:id or pw wrong;try again";
                else errorMsg = "ERR:login fail;try again";
                tryLoginBtn.setText(errorMsg);
            }
        }
    }
}
