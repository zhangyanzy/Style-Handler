package com.zhangyan.style_handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 使用Handler----postDelayed
 */

public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;

    private Button button;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Toast.makeText(getApplicationContext(), "" + 1, Toast.LENGTH_SHORT).show();

            return false;
        }
    }) {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "" + 2, Toast.LENGTH_SHORT).show();
            super.handleMessage(msg);
        }
    };

    private int image[] = {R.mipmap.image, R.mipmap.image1, R.mipmap.image2};

    //建立索引
    private int index;

    private MyRunnable myRunnable = new MyRunnable();


    class MyRunnable implements Runnable {

        @Override
        public void run() {
            index++;
            index = index % 3;
            imageView.setImageResource(image[index]);
            handler.postDelayed(myRunnable, 1000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);
        handler.postDelayed(myRunnable, 1000);
        initEvent();
    }

    private void initEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handler.removeCallbacks(myRunnable);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
