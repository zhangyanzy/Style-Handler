package com.zhangyan.style_handler;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {
    private Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void testHandler(View v) {
        intent = new Intent();
        intent.setClass(getApplicationContext(),HandlerTestActivity.class);
        startActivity(intent);
    }

    public void handlerDemo(View v) {
        startActivity(new Intent(this, HandlerDemoActivity.class));
    }
//
//    public void testAsyncTask(View v) {
//        startActivity(new Intent(this, AsyncTaskTestActivity.class));
//    }
}
