package com.sciento.wumu.zhengjing.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sciento.wumu.zhengjing.R;
import com.sciento.wumu.zhengjing.WholeActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       firstLogin();

    }

    private void firstLogin() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isFirstRun){
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    /**
                     *要执行的操作
                     */
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,HealthActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 3000);//3秒后执行TimeTask的run方法

        } else {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    /**
                     *要执行的操作
                     */
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,WholeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 3000);//3秒后执行TimeTask的run方法


        }
    }
}
