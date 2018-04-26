package com.yl.baiduren.utils;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.Button;


/**
 * Created by sunbeibei on 2017/6/1.
 */

public  class CountdownTimer_utils implements Runnable {


    private Drawable gray;
    private Drawable red;
    private Button textView ;
    private final Handler handler;
    private int T;

    public CountdownTimer_utils(int t,  Button textView, Drawable gray, Drawable red) {
         this.T=t;
        this.handler = new Handler();
        this.textView=textView;
        this.gray=gray;
        this.red=red;
    }

    @Override
    public void run() {

        //倒计时开始，循环
        while (T > 0) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    textView.setClickable(false);
                    textView.setText(T + " s重发");
                    textView.setBackground(gray);

                }
            });
            try {
                Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            T--;
        }

        //倒计时结束，也就是循环结束
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setClickable(true);
                textView.setText("重新发送");
                textView.setBackground(red);
            }
        });
        T=T;
        //最后再恢复倒计时时长
    }

}
