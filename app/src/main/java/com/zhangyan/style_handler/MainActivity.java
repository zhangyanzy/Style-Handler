package com.zhangyan.style_handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 使用Handler----sendMessage
 */

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //textView.setText("" + msg.arg1 + "--" + msg.arg2);
            textView.setText("" + msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text_view);
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
//                    Message message = new Message();
//                    message.arg1 = 88;
//                    message.arg2 = 100;

                    //******************************************//
                    Message message = handler.obtainMessage();
                    Person person = new Person();
                    person.name = "zhangyan";
                    person.age = 23;
                    message.obj = person;
                    message.sendToTarget();
//                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    class Person {
        public String name;
        public int age;

        @Override
        public String toString() {
            return "name:  " + name + "---" + "age:  " + age;
        }
    }
}
