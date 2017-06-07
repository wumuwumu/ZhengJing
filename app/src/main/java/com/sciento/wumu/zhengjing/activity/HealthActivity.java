package com.sciento.wumu.zhengjing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sciento.wumu.zhengjing.R;
import com.sciento.wumu.zhengjing.activity.RankActivity;
import com.sciento.wumu.zhengjing.view.MyRadioGroup;

public class HealthActivity extends AppCompatActivity implements MyRadioGroup.OnCheckedChangeListener
        ,RadioGroup.OnCheckedChangeListener
        ,View.OnClickListener{

    Button btnPush;

    RadioGroup rdoGrpQues1;
    RadioGroup rdoGrpQues2;
    RadioGroup rdoGrpQues3;
    RadioGroup rdoGrpQues4;
    RadioGroup rdoGrpQues5;
    RadioGroup rdoGrpQues6;
    RadioGroup rdoGrpQues7;
    MyRadioGroup rdoGrpQues8;
    RadioGroup rdoGrpQues9;
    MyRadioGroup rdoGrpQues10;


    private  boolean[] blQues = {false,false,false,false,false,false,false};
    private  boolean[] quesAsked = {false,false,false,false,false,false,false,false,false,false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        initEvent();
    }

    private void init() {
        btnPush = (Button)findViewById(R.id.btn_push);
        rdoGrpQues1 = (RadioGroup)findViewById(R.id.rdogrp_ques1);
        rdoGrpQues2 = (RadioGroup)findViewById(R.id.rdogrp_ques2);
        rdoGrpQues3 = (RadioGroup)findViewById(R.id.rdogrp_ques3);
        rdoGrpQues4 = (RadioGroup)findViewById(R.id.rdogrp_ques4);
        rdoGrpQues5 = (RadioGroup)findViewById(R.id.rdogrp_ques5);
        rdoGrpQues6 = (RadioGroup)findViewById(R.id.rdogrp_ques6);
        rdoGrpQues7 = (RadioGroup)findViewById(R.id.rdogrp_ques7);
        rdoGrpQues8 = (MyRadioGroup) findViewById(R.id.rdogrp_ques8);
        rdoGrpQues9 = (RadioGroup)findViewById(R.id.rdogrp_ques9);
        rdoGrpQues10 = (MyRadioGroup) findViewById(R.id.rdogrp_ques10);


    }

    private void initEvent() {
        btnPush.setOnClickListener(this);
        rdoGrpQues1.setOnCheckedChangeListener(this);
        rdoGrpQues2.setOnCheckedChangeListener(this);
        rdoGrpQues3.setOnCheckedChangeListener(this);
        rdoGrpQues4.setOnCheckedChangeListener(this);
        rdoGrpQues5.setOnCheckedChangeListener(this);
        rdoGrpQues6.setOnCheckedChangeListener(this);
        rdoGrpQues7.setOnCheckedChangeListener(this);
        rdoGrpQues8.setOnCheckedChangeListener(this);
        rdoGrpQues9.setOnCheckedChangeListener(this);
        rdoGrpQues10.setOnCheckedChangeListener(this);

    }

    /**
     *
     自定义按钮的监听函数
     */
    @Override
    public void onCheckedChanged(MyRadioGroup group, int checkedId) {
        switch (checkedId)
        {
            //ques8
            case R.id.ques8_1:
                quesAsked[7]=true;
                break;
            case R.id.ques8_2:
                quesAsked[7]=true;
                break;
            case R.id.ques8_3:
                quesAsked[7]=true;
                break;
            case R.id.ques8_4:
                quesAsked[7]=true;
            break;

            //ques10
            case R.id.ques10_1:
                quesAsked[9] = true;
                break;
            case R.id.ques10_2:
                quesAsked[9] = true;
                break;
            case R.id.ques10_3:
                quesAsked[9] = true;
                break;
            case R.id.ques10_4:
                quesAsked[9] = true;
                break;


        }


    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId)
        {
            //ques1
            case R.id.ques1_en:
                blQues[0] = true;
                quesAsked[0]=true;
                break;
            case R.id.ques1_dis:
                blQues[0]= false;
                quesAsked[0]=true;
                break;
            //ques2
            case R.id.ques2_en:
                blQues[1] = true;
                quesAsked[1]=true;
                break;
            case R.id.ques2_dis:
                blQues[1]= false;
                quesAsked[1]=true;
                break;
            //ques3
            case R.id.ques3_en:
                blQues[2]= true;
                quesAsked[2]=true;
                break;
            case R.id.ques3_dis:
                blQues[2]= false;
                quesAsked[2]=true;
                break;
            //ques4
            case R.id.ques4_en:
                blQues[3]= true;
                quesAsked[3]=true;
                break;
            case R.id.ques4_dis:
                blQues[3]= false;
                quesAsked[3]=true;
                break;
            //ques5
            case R.id.ques5_en:
                blQues[4]= true;
                quesAsked[4]=true;
                break;
            case R.id.ques5_dis:
                blQues[4]= false;
                quesAsked[4]=true;
                break;
            //ques6
            case R.id.ques6_en:
                blQues[5]= true;
                quesAsked[5]=true;
                break;
            case R.id.ques6_dis:
                blQues[5]= false;
                quesAsked[5]=true;
                break;
            //ques7
            case R.id.ques7_en:
                blQues[6]= true;
                quesAsked[6]=true;
                break;
            case R.id.ques7_dis:
                blQues[6]= false;
                quesAsked[6]=true;
                break;
            //ques9
            case R.id.ques9_dis:
                quesAsked[8] = true;
                break;
            case R.id.ques9_en:
                quesAsked[8] = true;
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_push:
                boolean isWriteAll = checkWriteAll();
                if(!isWriteAll)
                {
                    Toast.makeText(this,"表没有填写完整，请填写",Toast.LENGTH_SHORT).show();
                    break;
                }
                int i = 6;
                while (i>=0 && !blQues[i] )
                {
                    i--;
                }
                if(i<0)
                {
                    Toast.makeText(this,"表没有填写完整，请填写",Toast.LENGTH_SHORT).show();

                }
                else {
                    int rank = i + 1;
                    Intent intent = new Intent(this, RankActivity.class);
                    intent.putExtra("rank", rank);
                    startActivity(intent);
                    finish();
                }
                break;

        }

    }

    private boolean checkWriteAll() {
        for(int i  = 0;i<10;i++)
        {
            if(quesAsked[i]==false)
            {
                return  false;
            }
        }
        return true;
    }
}
