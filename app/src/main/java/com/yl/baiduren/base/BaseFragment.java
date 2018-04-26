package com.yl.baiduren.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.yl.baiduren.R;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseFragment extends RxFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ImageView imageView;//没有数据时显示该布局

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout relatyou = new RelativeLayout(getContext());
        imageView=new ImageView(getContext());
        imageView.setVisibility(View.GONE);
        imageView.setImageResource(R.mipmap.no_content);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
        relatyou.setLayoutParams(layoutParams);
        //加载一个布局
        View rootView = inflater.inflate(this.loadWindowLayout(), container, false);
        relatyou.addView(rootView);
        relatyou.addView(imageView,layoutParams);
        initViews(relatyou);
        return relatyou;
    }

    //该抽象方法用于为Activit设置xml
    public abstract int loadWindowLayout();
    public abstract void initViews(View rootView);


    /**
     * 线程调度
     */
    public  <T> ObservableTransformer<T, T> compose(final LifecycleTransformer<T> lifecycle) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(lifecycle);
            }
        };
    }

    /***
     * 请求数据为空时，显示该图片
     */
    public void dataNullShowImageView(){
        imageView.setVisibility(View.VISIBLE);
    }

    /**
     * 请求数据不为空时，隐藏该图片
     */
    public void dataNotNullHideImageView(){
        imageView.setVisibility(View.GONE);
    }

}
