package com.yl.baiduren.view;

/*
  @author 王锋 on 2017/8/12.
 */


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yl.baiduren.R;


/**
 * @ explain:
 * @ author：xujun on 2016/6/27 11:21
 * @ email：gdutxiaoxu@163.com
 */
public class CollapseView_1 extends LinearLayout {

    private long duration = 350;
    private Context mContext;
    private TextView mTitleTextView;
    private RelativeLayout mContentRelativeLayout;
    private ImageView mArrowImageView;
    int parentWidthMeasureSpec;
    int parentHeightMeasureSpec;
    private boolean isOpen=false;

    public CollapseView_1(Context context) {
        this(context, null);
    }

    public CollapseView_1(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.collapse_layout_1, this);
        initView();

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        mTitleTextView = (TextView) findViewById(R.id.titleTextView);
        RelativeLayout mTitleRelativeLayout = (RelativeLayout) findViewById(R.id.titleRelativeLayout);
        mContentRelativeLayout = findViewById(R.id.contentRelativeLayout);
        mArrowImageView = (ImageView) findViewById(R.id.arrowImageView);
        mTitleRelativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateArrow();
            }
        });
        Drawable circleShape = createCircleShape(Color.BLACK);
        collapse(mContentRelativeLayout);
    }


    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTextView.setText(title);
        }
    }

    public void setContent(int resID) {
        View view = LayoutInflater.from(mContext).inflate(resID, null);
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        mContentRelativeLayout.addView(view);

    }

    public void setContent(View view) {
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        mContentRelativeLayout.addView(view);
    }


    public void rotateArrow() {
        int degree = 0;
        if (mArrowImageView.getTag() == null || mArrowImageView.getTag().equals(true)) {
            mArrowImageView.setTag(false);
            degree = -180;
            if(isOpen){
                collapse(mContentRelativeLayout);
                isOpen=false;
            }else {
                expand(mContentRelativeLayout);
                isOpen=true;
            }

        } else {
            degree = 0;
            mArrowImageView.setTag(true);
            if(isOpen){
                collapse(mContentRelativeLayout);
                isOpen=false;
            }else {
                expand(mContentRelativeLayout);
                isOpen=true;
            }
//            collapse(mContentRelativeLayout);
        }
        mArrowImageView.animate().setDuration(duration).rotation(degree);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        parentWidthMeasureSpec = widthMeasureSpec;
        parentHeightMeasureSpec = heightMeasureSpec;

    }


    public void expand() {
        isOpen=true;
        this.expand(mContentRelativeLayout);
    }

    public void collapse() {
        this.collapse(mContentRelativeLayout);
    }

    // 展开
    private void expand(final View view) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        view.measure(parentWidthMeasureSpec, parentHeightMeasureSpec);
        final int measuredWidth = view.getMeasuredWidth();
        final int measuredHeight = view.getMeasuredHeight();
        view.setVisibility(View.VISIBLE);


        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.getLayoutParams().height = measuredHeight;
                } else {
                    view.getLayoutParams().height = (int) (measuredHeight * interpolatedTime);
                }
                view.requestLayout();
            }


            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    // 折叠
    private void collapse(final View view) {
        final int measuredHeight = view.getMeasuredHeight();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = measuredHeight - (int) (measuredHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    /**
     * 获取指定颜色的圆角背景shape
     *
     * @return
     */
    public Drawable createCircleShape(int color) {
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        OvalShape ovalShape = new OvalShape();
        shapeDrawable.setShape(ovalShape);
        shapeDrawable.getPaint().setColor(color);


        return shapeDrawable;
    }


}


