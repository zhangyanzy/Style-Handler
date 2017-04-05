package com.zhangyan.style_handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 1、手动增加，减少
 * 2、自动增加，减少
 * 3、限制数字最大值和最小值（1,20）
 * 4、限制button可操作性
 */

public class HandlerDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int WHAT_ADD = 1;
    private static final int WHAT_REDUCE = 2;

    private TextView mTextView;
    private Button mAdd;
    private Button mReduce;
    private Button mStop;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //得到当前的数值
            int number = Integer.parseInt(mTextView.getText().toString());
            switch (msg.what) {
                case WHAT_ADD:
                    number++;
                    mTextView.setText(number + "");
                    //发送增加的延迟消息
                    if (number < 20) {
                        handler.sendEmptyMessageDelayed(WHAT_ADD, 1000);
                    } else {
                        Toast.makeText(getApplicationContext(), "MAX", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case WHAT_REDUCE:
                    number--;
                    mTextView.setText(number + "");
                    //发送减少
                    if (number > 1) {
                        handler.sendEmptyMessageDelayed(WHAT_REDUCE, 1000);
                    } else {
                        Toast.makeText(getApplicationContext(), "MIN", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_demo);
        init();
    }

    private void init() {
        mTextView = (TextView) findViewById(R.id.text_view_demo);
        mAdd = (Button) findViewById(R.id.btn_add_demo);
        mReduce = (Button) findViewById(R.id.btn_reduce_demo);
        mStop = (Button) findViewById(R.id.btn_stop_demo);
        mAdd.setOnClickListener(this);
        mReduce.setOnClickListener(this);
        mStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_demo:
                add();
                break;
            case R.id.btn_reduce_demo:
                reduce();
                break;
            case R.id.btn_stop_demo:
                stop();
                break;
            default:
                break;
        }
    }

    private void add() {
        //限制button的可操作性
        mAdd.setEnabled(false);
        mReduce.setEnabled(true);
        mStop.setEnabled(true);
        //移除为处理的减少消息
        handler.removeMessages(WHAT_REDUCE);
        //发空的消息（增加）
        handler.sendEmptyMessage(WHAT_ADD);

    }

    private void reduce() {
        //限制button的可操作性
        mAdd.setEnabled(true);
        mReduce.setEnabled(false);
        mStop.setEnabled(true);

        handler.removeMessages(WHAT_ADD);
        //发空的消息（减少）
        handler.sendEmptyMessage(WHAT_REDUCE);
    }

    private void stop() {
        mAdd.setEnabled(true);
        mReduce.setEnabled(true);
        mStop.setEnabled(false);
        //停止增加或者减少（移除为处理的增加或者减少的方法）
        handler.removeMessages(WHAT_ADD);
        handler.removeMessages(WHAT_REDUCE);
    }


}
