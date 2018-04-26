package com.yl.baiduren.activity.supply_demand;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Collection_Supply_Body;
import com.yl.baiduren.entity.request_body.Supply_Demend_Details_Id;
import com.yl.baiduren.entity.result.Supply_Demend_Details_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.DialogUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/*
  Created by sunbeibei on 2018/1/22.
 */

/**
 * 供需大厅 供应端详情
 */
public class Supply_Demend_Details extends BaseActivity {

    private ImageView detail_back;
    private TextView supply_num;
    private TextView supply_name;
    private TextView supply_shuliang;
    private TextView supply_guzhi;
    private TextView supply_jigou;
    private TextView supply_area;
    private Button detail_collection;
    private Button btn_contact;


    private int current = 0;
    private ImageView left_image;
    private ImageView right_image;
    private ImageView detail_image;
    private long id;
    private String[] image;

    @Override
    public int loadWindowLayout() {
        return R.layout.supply_demend_details;
    }

    @Override
    public void initViews() {
        //为true时是从我的收藏供应端过来的
        boolean mYSupplyCollection = getIntent().getBooleanExtra("MySupplyCollection", false);
        id = getIntent().getLongExtra("id", 0);
        RelativeLayout relative_image = findViewById(R.id.relative_image);
        detail_back = findViewById(R.id.detail_back);//关闭
        detail_back.setOnClickListener(listener);
        supply_num = findViewById(R.id.supply_num);//编号
        supply_name = findViewById(R.id.supply_name);//名称
        supply_shuliang = findViewById(R.id.supply_shuliang);//数量
        supply_guzhi = findViewById(R.id.supply_guzhi);//估值
        supply_jigou = findViewById(R.id.supply_jigou);//评估机构
        supply_area = findViewById(R.id.supply_area);//归属地
        detail_collection = findViewById(R.id.detail_collection);//收藏
        detail_collection.setOnClickListener(listener);
        btn_contact = findViewById(R.id.btn_contact);//联系客服
        btn_contact.setOnClickListener(listener);
        left_image = findViewById(R.id.left_image);//左箭头
        left_image.setOnClickListener(listener);
        right_image = findViewById(R.id.right_image);//右箭头
        right_image.setOnClickListener(listener);
        detail_image = findViewById(R.id.detail_image);
        if(mYSupplyCollection){
            detail_collection.setVisibility(View.GONE);
            btn_contact.setVisibility(View.GONE);
        }
        if (id!=0){
            requestWork();
        }

    }


    private void requestWork() {
        Supply_Demend_Details_Id entity =new Supply_Demend_Details_Id(DataWarehouse.getBaseRequest(this));
        entity.setId(id);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);

        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<Supply_Demend_Details_Result> baseObserver = new BaseObserver<Supply_Demend_Details_Result>(this) {

            @Override
            protected void onSuccees(String code, Supply_Demend_Details_Result data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(Supply_Demend_Details.this, baseResponse);
                    if (data.getImg()==null){
                        left_image.setVisibility(View.GONE);
                        right_image.setVisibility(View.GONE);
                    }else if(data.getImg()!=null){
                        String imgurl = data.getImg();
                        image = imgurl.split(",");
                        LUtils.e(image.length+"数组测大小");
                        detail_image.setScaleType(ImageView.ScaleType.FIT_XY);
                        Glide.with(Supply_Demend_Details.this).load(image[0]).placeholder(R.drawable.banner).error(R.drawable.banner).into(detail_image);
                        left_image.setVisibility(View.GONE);
                        if (image.length==1){
                            right_image.setVisibility(View.GONE);
                        }
                    }

                    supply_num.setText(data.getCode());
                    supply_name.setText(data.getName());
                    supply_shuliang.setText(data.getNum()+"");
                    supply_guzhi.setText(data.getValueStr());
                    supply_jigou.setText(data.getEvaluation());
                    supply_area.setText(data.getAreaName());

                }

            }
        };
        baseObserver.setStop(true);
        RetrofitHelper.getInstance(this).getServer()
                .getSupplyDetail(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Supply_Demend_Details_Result>>bindToLifecycle()))
                .subscribe(baseObserver);

    }

    private void setRghtImage() {

        ++current;
        Glide.with(Supply_Demend_Details.this).load(image[current]).into(detail_image);
        LUtils.e(image[current]);

        if (current == image.length - 1) {//右箭头消失
            right_image.setVisibility(View.GONE);
            left_image.setVisibility(View.VISIBLE);
            return;
        }else if (current>0){
            left_image.setVisibility(View.VISIBLE);
        }
    }

    private void setLeftImage() {

        --current;
        Glide.with(Supply_Demend_Details.this).load(image[current]).into(detail_image);
        if (current == 0) {//左箭头消失
            left_image.setVisibility(View.GONE);
            right_image.setVisibility(View.VISIBLE);
            return;
        } else if (current < image.length) {
            right_image.setVisibility(View.VISIBLE);
        }


    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == detail_back) {
                finish();
            } else if (v == detail_collection) {//收藏
                collection_Suplly(id);
            } else if (v == btn_contact) {//联系客服
                DialogUtils.showPhone(Supply_Demend_Details.this);
            } else if (v == left_image) {//左箭头
                setLeftImage();
            } else if (v == right_image) {//右箭头
                setRghtImage();

            }
        }
    };

    private void collection_Suplly(Long supp_id) {//点击收藏
        Collection_Supply_Body entity = new Collection_Supply_Body(DataWarehouse.getBaseRequest(this));
        entity.setSupplyId(supp_id);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<String> baseObserver = new BaseObserver<String>(Supply_Demend_Details.this) {

            @Override
            protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(Supply_Demend_Details.this, baseResponse);
                    ToastUtil.showShort(Supply_Demend_Details.this,"收藏成功");
                }else {
                    ToastUtil.showShort(Supply_Demend_Details.this,"收藏失败");
                }
            }
        };
        baseObserver.setStop(true);
        RetrofitHelper.getInstance(Supply_Demend_Details.this).getServer()
                .collectSupply(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(baseObserver);

    }
}