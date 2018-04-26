package com.yl.baiduren.utils;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yl.baiduren.R;


public class CommomDialog extends Dialog implements View.OnClickListener{

    private AblumOpenListener ablumOpenListener;
    private CameraOpenListener cameraOpenListener;

    public CommomDialog(Context context,CameraOpenListener cameraOpenListener,AblumOpenListener ablumOpenListener) {
        super(context);
        this.ablumOpenListener = ablumOpenListener;
        this.cameraOpenListener = cameraOpenListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(false);
        initView();
    }
    private void initView(){
        TextView titleTxt = (TextView) findViewById(R.id.title);
        TextView camera = (TextView) findViewById(R.id.camera);
        camera.setOnClickListener(this);
        TextView ablum = (TextView) findViewById(R.id.ablum);
        ablum.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera:
                if(cameraOpenListener != null){
                    cameraOpenListener.onClick(this);
                }
                break;
            case R.id.ablum:
                if(ablumOpenListener != null){
                    ablumOpenListener.onClick(this);
                }
                break;
        }
    }

    public interface AblumOpenListener{
        void onClick(CommomDialog c);
    }
    public interface CameraOpenListener{
        void onClick(CommomDialog c);
    }
    
}
