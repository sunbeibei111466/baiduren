package com.yl.baiduren.activity.debtrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.ImageShowAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.request_body.Asset_Details_Body;
import com.yl.baiduren.entity.result.PhysicalBorrowing_Details_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyRecyclerView;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/*实物借贷详情*/
public class PhysicalBorrowingDetails extends BaseActivity {

    private ImageView iv_physical_details_back;
    private ImageView iv_physical_details_updata;
    private TextView physical_name;
    private TextView physical_unit;
    private TextView physical_num;
    private TextView physical_value;
    private TextView physical_jigou;
    private TextView physical_depreciation;
    private TextView physical_endtime;
    private TextView physical_year;
    private TextView physical_climed_num;
    private TextView physical_yet_amout;
    private MyRecyclerView recyclerView;
    private Long phy_detail;
    private PhysicalBorrowing_Details_Result physicalBorrowingDetailsResult = null;
    private Long userId;


    @Override
    public int loadWindowLayout() {
        return R.layout.activity_physical_borrowing_details;
    }

    @Override
    public void initViews() {
        phy_detail = getIntent().getLongExtra("phy_detail", 0);//事物借贷id
        userId = getIntent().getLongExtra("userId", 0);
        iv_physical_details_back = findViewById(R.id.iv_physical_details_back);
        iv_physical_details_back.setOnClickListener(listener);
        iv_physical_details_updata = findViewById(R.id.iv_physical_details_updata);//实物借贷编辑
        iv_physical_details_updata.setOnClickListener(listener);
        physical_name = findViewById(R.id.physical_name);//物品名称
        physical_unit = findViewById(R.id.physical_unit);//计量单位
        physical_num = findViewById(R.id.physical_num);//数量
        physical_value = findViewById(R.id.physical_value);//评估价值
        physical_jigou = findViewById(R.id.physical_jigou);//评估机构
        physical_depreciation = findViewById(R.id.physical_depreciation);//折旧率
        physical_endtime = findViewById(R.id.physical_endtime);//借用日期
        physical_year = findViewById(R.id.physical_year);//使用年限
        physical_climed_num = findViewById(R.id.physical_climed_num);//退换数量
        physical_yet_amout = findViewById(R.id.physical_yet_amout);//未还金额
        recyclerView = findViewById(R.id.rv_physical_details_imagelist);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        isShowUpdata();
    }

    /**
     * 判断是否显示编辑按钮
     */
    public void isShowUpdata() {
        List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequestList.size() != 0) {
            if (userId.equals(baseRequestList.get(0).getUid())) {
                iv_physical_details_updata.setVisibility(View.VISIBLE);
            } else {
                iv_physical_details_updata.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (phy_detail != 0) {
            requestWork();
        }
    }

    private void requestWork() {
        //设置基础参数
        Asset_Details_Body entity = new Asset_Details_Body();

        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        entity.setAccessToken(baseRequests.get(0).getAccessToken());
        entity.setPlatform(2);
        entity.setUuid(baseRequests.get(0).getUuid());
        entity.setUid(baseRequests.get(0).getUid());
        entity.setLoginUsername(baseRequests.get(0).getLoginUsername());
        entity.setId(phy_detail);
        String json = Util.toJson(entity);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(this).getServer()
                .getGoodLoanDetails(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<PhysicalBorrowing_Details_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<PhysicalBorrowing_Details_Result>(this) {
                    @Override
                    protected void onSuccees(String code, PhysicalBorrowing_Details_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(PhysicalBorrowingDetails.this, baseResponse);
                            physical_name.setText(data.getName());
                            physical_unit.setText(data.getUnit());
                            physical_num.setText(data.getNum() + "");
                            physical_value.setText(data.getValueStr() + "万");
                            physical_jigou.setText(data.getEvaluation());
                            physical_depreciation.setText(data.getDepreciation() * 100 + "%");
                            physical_endtime.setText(Util.LongParseString(data.getBorrowTime()));
                            physical_year.setText(data.getUseLife() + "");
                            physical_climed_num.setText(data.getReturnNum() + "");
                            physical_yet_amout.setText(data.getBalanceStr() + "万");

                            ImageShowAdapter adapter = new ImageShowAdapter(PhysicalBorrowingDetails.this);
                            if (!TextUtils.isEmpty(data.getImage())) {
                                String imgUrl = data.getImage();
                                String[] split = imgUrl.split(",");
                                adapter.setImageArry(split);
                                recyclerView.setAdapter(adapter);
                            } else {
                                adapter.getImageList().clear();
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                            physicalBorrowingDetailsResult = data;

                        }
                    }
                });

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == iv_physical_details_back) {
                finish();
            } else if (v == iv_physical_details_updata) {

                Intent intent = new Intent(PhysicalBorrowingDetails.this, PhysicalBorrowing.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("physicalBorrowingDetailsResult", physicalBorrowingDetailsResult);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }
    };
}
