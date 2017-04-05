package com.zhangyan.style_handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhang_yan on 2017/3/31.
 */
public class HandlerTestActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressBar mprogressBar;
    private Button mBtn1;
    private Button mBtn2;
    private EditText mEditText;
    //1、创建Handler成员变量，并重写handlerMessage()方法
    private Handler handler = new Handler() {
        //必须在主线程处理
        @Override
        public void handleMessage(Message msg) {
            //4、在handlerMessage()中处理消息
            //把标识为1  拿过来判断
            if (msg.what == 1) {
                String re = msg.toString();
                if (re != null) {
                    mEditText.setText(re);
                    mprogressBar.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_handler);
        init();
    }

    private void init() {
        mprogressBar = (ProgressBar) findViewById(R.id.progressbar);
        mBtn1 = (Button) findViewById(R.id.submit);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) findViewById(R.id.submit2);
        mBtn2.setOnClickListener(this);
        mEditText = (EditText) findViewById(R.id.edit_text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                noHandler();
                break;
            case R.id.submit2:
                useHandler();
                break;
            default:
                break;
        }
    }


    public void useHandler() {
        mprogressBar.setVisibility(View.VISIBLE);
        //3、使用handler对象发送message
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://m.api.tarenwang.net/v2/gameservice";
                try {
                    final String result = requestToString(path);
                    //2、在子/主线程创建message对象
                    Message message = Message.obtain();
                    message.what = 1;//标识
                    message.obj = result;//把需要发送的信息传给message
                    //3、使用handler对象发送message
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 1、主线程显示ProgressBar
     * 2、子线程，联网请求，并得到相应数据
     * 3、主线程，显示数据
     */
    public void noHandler() {
        //1
        mprogressBar.setVisibility(View.VISIBLE);
        //2
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://m.api.tarenwang.net/v2/gameservice";
                try {
                    final String result = requestToString(path);
                    //主线程显示
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result != null) {
                                mEditText.setText(result);
                            } else {
                                Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
                            }
                            mprogressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String requestToString(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();
        InputStream is = connection.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        baos.close();
        is.close();
        String result = baos.toString();
        connection.disconnect();
        return result;
    }
}
