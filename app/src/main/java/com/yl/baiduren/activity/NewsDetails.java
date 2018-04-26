package com.yl.baiduren.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;

public class NewsDetails extends BaseActivity {

    private String jumpUrl;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_news_details;
    }

    @Override
    public void initViews() {
        String name = getIntent().getStringExtra("name");
        ImageView iv_news = findViewById(R.id.iv_news);
        iv_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView news_name = findViewById(R.id.news_name);
        news_name.setText(name);
    }
}
