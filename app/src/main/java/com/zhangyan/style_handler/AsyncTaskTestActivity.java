package com.zhangyan.style_handler;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class AsyncTaskTestActivity extends AppCompatActivity {

    private Button mLoad;
    private ProgressDialog dialog;
    private File apkFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_test);
        mLoad = (Button) findViewById(R.id.btn_load);
        initEvent();
    }

    private void initEvent() {
        mLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动异步任务处理
                //创建任务对象
                new AsyncTask<Void, Void, Void>() {
                    //1.主线程，显示提示视图
                    @Override
                    protected void onPreExecute() {
                        dialog = new ProgressDialog(AsyncTaskTestActivity.this);
                        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        dialog.show();

                        apkFile = new File(getExternalFilesDir(null),"update.apk");
                    }

                    //2.分线程，联网请求
                    @Override
                    protected Void doInBackground(Void... params) {

                        //TODO

                        return null;
                    }
                    //3.主线程更新界面
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        dialog.dismiss();
                    }
                    //主线程更新进度

                    @Override
                    protected void onProgressUpdate(Void... values) {
                        dialog.incrementSecondaryProgressBy(values[0]);
                    }
                }.execute();
            }
        });
    }
}
