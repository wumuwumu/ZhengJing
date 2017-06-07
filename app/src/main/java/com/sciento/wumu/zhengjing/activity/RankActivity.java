package com.sciento.wumu.zhengjing.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sciento.wumu.zhengjing.R;
import com.sciento.wumu.zhengjing.WholeActivity;

import java.util.Timer;
import java.util.TimerTask;

public class RankActivity extends AppCompatActivity {

    Button btnStartCure ;

    int rank;
    TextView tvRankShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        initValue();
        init();
        initEvent();

    }

    private void initValue() {
        Bundle bundle = getIntent().getExtras();
        rank = bundle.getInt("rank");
    }

    private void init() {
        tvRankShow = (TextView)findViewById(R.id.tv_rankshow);
        btnStartCure = (Button)findViewById(R.id.btn_startcure);
        tvRankShow.setText(rank+"级");

    }

    private void initEvent() {
        btnStartCure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(RankActivity.this, WholeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
            }
        });


    }
}
