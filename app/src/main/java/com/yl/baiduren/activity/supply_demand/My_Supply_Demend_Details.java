package com.yl.baiduren.activity.supply_demand;

import android.content.Intent;
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
import com.yl.baiduren.entity.request_body.DeleteItem;
import com.yl.baiduren.entity.request_body.Supply_Demend_Details_Id;
import com.yl.baiduren.entity.result.Supply_Demend_Details_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.RetrofitService;
import com.yl.baiduren.utils.Constant;
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
 * 我的供需 供应端详情
 */
public class My_Supply_Demend_Details extends BaseActivity {

    private ImageView detail_back;
    private TextView supply_num;
    private TextView supply_name;
    private TextView supply_shuliang;
    private TextView supply_guzhi;
    private TextView supply_jigou;
    private TextView supply_area;
    private Button bt_my_supply_detail_delete;
    private Button my_supply_updata;
    private int current = 0;
    private ImageView left_image;
    private ImageView right_image;
    private ImageView detail_image;
    private long id;
    private String[] image;
    private Supply_Demend_Details_Result demendDetailsResult=null;
    private TextView supply_type;

    @Override
    public int loadWindowLayout() {
        return R.layout.my_supply_demend_details;
    }

    @Override
    public void initViews() {
        id = getIntent().getLongExtra("id", 0);
        RelativeLayout relative_image = findViewById(R.id.relative_image);
        detail_back = findViewById(R.id.detail_back);//关闭
        detail_back.setOnClickListener(listener);
        supply_num = findViewById(R.id.supply_num);//编号
        supply_type=findViewById(R.id.supply_type);//类型
        supply_name = findViewById(R.id.supply_name);//名称
        supply_shuliang = findViewById(R.id.supply_shuliang);//数量
        supply_guzhi = findViewById(R.id.supply_guzhi);//估值
        supply_jigou = findViewById(R.id.supply_jigou);//评估机构
        supply_area = findViewById(R.id.supply_area);//归属地
        bt_my_supply_detail_delete = findViewById(R.id.bt_my_supply_detail_delete);//删除
        bt_my_supply_detail_delete.setOnClickListener(listener);
        my_supply_updata = findViewById(R.id.my_supply_updata);//编辑
        my_supply_updata.setOnClickListener(listener);
        left_image = findViewById(R.id.left_image);//左箭头
        left_image.setOnClickListener(listener);
        right_image = findViewById(R.id.right_image);//右箭头
        right_image.setOnClickListener(listener);
        detail_image = findViewById(R.id.detail_image);




    }

    @Override
    protected void onResume() {
        super.onResume();
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
                    UserInfoUtils.setUuid(My_Supply_Demend_Details.this, baseResponse);
                    demendDetailsResult=data;
                    String imgurl = data.getImg();
                    image = imgurl.split(",");
                    LUtils.e(image.length+"数组测大小");
                    detail_image.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(My_Supply_Demend_Details.this).load(image[0]).
                            placeholder(R.drawable.banner).error(R.drawable.banner).into(detail_image);
                    left_image.setVisibility(View.GONE);
                    if (image.length==1){
                        right_image.setVisibility(View.GONE);
                    }
                    supply_num.setText(data.getCode());
                    supply_type.setText(data.getCategoryName());
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

        Glide.with(My_Supply_Demend_Details.this).load(image[current]).into(detail_image);
        LUtils.e(image[current]);

        if (current == image.length - 1) {//右箭头消失
            right_image.setVisibility(View.GONE);
            left_image.setVisibility(View.VISIBLE);
            return;
        } else if (current > 0) {
            left_image.setVisibility(View.VISIBLE);
        }
    }

    private void setLeftImage() {

        --current;
        Glide.with(My_Supply_Demend_Details.this).load(image[current]).into(detail_image);
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
            } else if (v == bt_my_supply_detail_delete) {//删除
                  delete(id);
            } else if (v == my_supply_updata) {//编辑
                if(demendDetailsResult!=null){
                    Intent intent=new Intent(My_Supply_Demend_Details.this,UploadSupply.class);
                    intent.putExtra("demendDetailsResult",demendDetailsResult);
                    startActivity(intent);
                }
            } else if (v == left_image) {//左箭头
                setLeftImage();
            } else if (v == right_image) {//右箭头
                setRghtImage();
            }
        }
    };

    private void delete(long id) {

        if (UserInfoUtils.IsLogin(this)) {
            DeleteItem deleteItem=new DeleteItem();
            deleteItem.setId(id);
            String json = Util.toJson(deleteItem);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitService retrofitService=RetrofitHelper.getInstance(this).getServer();
                retrofitService.deleteSupply(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                        .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                        .subscribe(new BaseObserver<String>(My_Supply_Demend_Details.this) {
                            @Override
                            protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                               if (code.equals("1")){
                                   ToastUtil.showShort(My_Supply_Demend_Details.this,"删除成功");
                                   new android.os.Handler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           finish();
                                       }
                                   },1000);
                               }
                            }
                        });
        }
    }
}