package com.yl.baiduren.activity.asster_dispose;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Supply_Demend_Details_Id;
import com.yl.baiduren.entity.result.Supply_Demend_Details_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.DialogUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Asstes_DisposeHall_Detials extends BaseActivity {

    private ImageView left_image;
    private ImageView right_image;
    private ImageView dispose_asset_detials_back;
    private ImageView detail_image_detials;
    private String[] image = new String[]{};
    private int current = 0;
    private Long id;
    private TextView tv_hall_asstes_detials;
    private TextView tv_hall_asstes_detials_gz;
    private TextView tv_hall_asstes_detials_pgjg;
    private TextView tv_hall_asstes_detials_gsd;
    private TextView tv_asstes_d_text_hall;
    private TextView tv_hall_asstes_detials_sl,tv_xingy_baogao;
    private Button bt_lxkf;
    private boolean isuyCredit=false;//是否购买报告

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_asstes__dispose_hall__detials;
    }

    @Override
    public void initViews() {
        id = getIntent().getLongExtra("id", 0);
        tv_hall_asstes_detials = findViewById(R.id.tv_hall_asstes_detials);//名称
        tv_hall_asstes_detials_gz = findViewById(R.id.tv_hall_asstes_detials_gz);//估值
        tv_hall_asstes_detials_pgjg = findViewById(R.id.tv_hall_asstes_detials_pgjg);//评估机构
        tv_hall_asstes_detials_gsd = findViewById(R.id.tv_hall_asstes_detials_gsd);//归属地
        tv_hall_asstes_detials_sl = findViewById(R.id.tv_hall_asstes_detials_sl); //数量
        tv_asstes_d_text_hall = findViewById(R.id.tv_asstes_d_text_hall);//详细信息
        dispose_asset_detials_back = findViewById(R.id.dispose_asset_detials_back);
        dispose_asset_detials_back.setOnClickListener(listener);
        detail_image_detials = findViewById(R.id.detail_image_detials);
        bt_lxkf = findViewById(R.id.bt_lxkf);//联系我们
        bt_lxkf.setOnClickListener(listener);
        tv_xingy_baogao=findViewById(R.id.tv_xingy_baogao);
        left_image = findViewById(R.id.left_image);//左箭头
        left_image.setOnClickListener(listener);
        right_image = findViewById(R.id.right_image);//右箭头
        right_image.setOnClickListener(listener);
        left_image.setVisibility(View.GONE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (id != 0) {
            requestWork();
        }
    }

    private void requestWork() {
        Supply_Demend_Details_Id entity = new Supply_Demend_Details_Id(DataWarehouse.getBaseRequest(this));
        entity.setId(id);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);

        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<Supply_Demend_Details_Result> baseObserver = new BaseObserver<Supply_Demend_Details_Result>(this) {

            @Override
            protected void onSuccees(String code, Supply_Demend_Details_Result data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(Asstes_DisposeHall_Detials.this, baseResponse);
                    if (data.getImg() == null) {
                        left_image.setVisibility(View.GONE);
                        right_image.setVisibility(View.GONE);
                    }
                    if (data.getImg() != null) {
                        image = data.getImg().split(",");
                        LUtils.e(image.length + "数组测大小");
                        Glide.with(Asstes_DisposeHall_Detials.this).load(image[0]).
                                placeholder(R.drawable.banner).error(R.drawable.banner).into(detail_image_detials);
                        left_image.setVisibility(View.GONE);
                        if (image.length == 1) {
                            right_image.setVisibility(View.GONE);
                        }
                    }


                    tv_hall_asstes_detials.setText(data.getName());
                    tv_hall_asstes_detials_gz.setText(data.getValueStr());
                    tv_hall_asstes_detials_pgjg.setText(data.getEvaluation());
                    tv_hall_asstes_detials_gsd.setText(data.getAreaName());
                    tv_asstes_d_text_hall.setText(data.getDescribe());
                    tv_hall_asstes_detials_sl.setText(data.getNum() + "");


                    isuyCredit=data.getBuyCredit();//是否购买报告
                    if(isuyCredit){
                        tv_xingy_baogao.setText("信用报告(已购买)");
                    }else{
                        tv_xingy_baogao.setText("信用报告(未购买)");
                    }
                }
            }
        };
        baseObserver.setStop(true);
        RetrofitHelper.getInstance(this).getServer()
                .getAsstesSupplyDetail(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Supply_Demend_Details_Result>>bindToLifecycle()))
                .subscribe(baseObserver);

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == dispose_asset_detials_back) {
                finish();
            } else if (v == left_image) {//左箭头
                setLeftImage();
            } else if (v == right_image) {//右箭头
                setRghtImage();
            } else if (v == bt_lxkf) {
                DialogUtils.showPhone(Asstes_DisposeHall_Detials.this);
            }
        }
    };

    private void setRghtImage() {
        ++current;
        Glide.with(Asstes_DisposeHall_Detials.this).load(image[current]).into(detail_image_detials);
        LUtils.e(image[current]);

        if (current == image.length - 1) {//右箭头消失
            right_image.setVisibility(View.GONE);
            left_image.setVisibility(View.VISIBLE);
        } else if (current > 0) {
            left_image.setVisibility(View.VISIBLE);
        }
    }

    private void setLeftImage() {

        --current;
        Glide.with(Asstes_DisposeHall_Detials.this).load(image[current]).into(detail_image_detials);
        if (current == 0) {//左箭头消失
            left_image.setVisibility(View.GONE);
            right_image.setVisibility(View.VISIBLE);
        } else if (current < image.length) {
            right_image.setVisibility(View.VISIBLE);
        }

    }
}
