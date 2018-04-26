package com.yl.baiduren.activity.pay_for;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yl.baiduren.MainActivity;
import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;


public class PaySuccess extends BaseActivity implements View.OnClickListener {

    private TextView mTv_Title_pay;
    private Button back_home;
    private TextView timer;
    private int count = 5;
    private int indexPager;
    private CountDownTimer countDownTimer;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_pay_success;
    }

    @Override
    public void initViews() {

        back_home = findViewById(R.id.btn_back_home);
        back_home.setOnClickListener(this);
        timer = (TextView) findViewById(R.id.timer);
        indexPager = getIntent().getIntExtra("cz", 0);

        countDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count--;
                if (indexPager == 1) {
                    timer.setText(count + " 秒后开始自动跳回到钱包");
                  /*  back_home.setText(R.string.back_wallet);*/
                } else {
                    timer.setText(count + " 秒后开始自动跳回到首页");
                    back_home.setText("返回首页");
                }

                if (count == 0) {
                    back();
                }
            }

            @Override
            public void onFinish() {
            }

        }.start();

    }

    /**
     * 返回
     */
    private void back() {
        Intent intent = new Intent();
        if (indexPager == 1) {

        } else {
            intent = new Intent(PaySuccess.this, MainActivity.class);
            intent.setClass(PaySuccess.this, MainActivity.class);
            intent.putExtra("currMenu", 0);
        }
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        if (v == back_home) {
            countDownTimer.cancel();
            back();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer = null;
    }
}
