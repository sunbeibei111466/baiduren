package com.yl.baiduren.activity.debtrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Curry_Details_Adapter;
import com.yl.baiduren.adapter.ImageShowAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.request_body.Asset_Details_Body;
import com.yl.baiduren.entity.result.CurrencyLendingDetails_Result;
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

/*货币借贷*/
public class CurrencyLendingDetails extends BaseActivity {

    private Long curry_detail;
    private ImageView iv_currency_details_back;
    private ImageView iv_currency_details_updata;
    private TextView curry_type;
    private TextView curry_amout;
    private TextView curry_lixi;
    private TextView curry_date;
    private MyRecyclerView recyclerView;
    private TextView curry_over_date;
    private TextView curry_yihuan;
    private TextView curry_yue;
    private MyRecyclerView rv_currency_details_imagelist;
    private CurrencyLendingDetails_Result currencyLendingDetailsResult;
    private Long userId;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_currency_lending_details;
    }

    @Override
    public void initViews() {
        curry_detail = getIntent().getLongExtra("curry_detail", 0);//货币借贷Id
        userId = getIntent().getLongExtra("userId", 0);//判断是否隐藏编辑按钮
        iv_currency_details_back = findViewById(R.id.iv_currency_details_back);
        iv_currency_details_back.setOnClickListener(listener);
        iv_currency_details_updata= findViewById(R.id.iv_currency_details_updata);//货币借贷编辑
        iv_currency_details_updata.setOnClickListener(listener);
        curry_type = findViewById(R.id.curry_type);//币种
        curry_amout = findViewById(R.id.curry_amout);//本金
        curry_lixi = findViewById(R.id.curry_lixi);//利息
        curry_date = findViewById(R.id.curry_date);//放款日期
        recyclerView = findViewById(R.id.rv_currency_details_list); //是否结算
        LinearLayoutManager manager =new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        curry_over_date = findViewById(R.id.curry_over_date);//截止日期
        curry_yihuan = findViewById(R.id.curry_yihuan);//已还合计
        curry_yue = findViewById(R.id.curry_yue);//未还余额
        rv_currency_details_imagelist = findViewById(R.id.rv_currency_details_imagelist);
        rv_currency_details_imagelist.setLayoutManager(new GridLayoutManager(this, 4));
        isShowUpdata();
    }

    /**
     * 判断是否显示编辑按钮
     */
    public void isShowUpdata() {
        List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequestList.size() != 0) {
            if (userId.equals(baseRequestList.get(0).getUid()) ) {
                iv_currency_details_updata.setVisibility(View.VISIBLE);
            } else {
                iv_currency_details_updata.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (curry_detail!=0){
            requestWork();
        }
    }

    private void requestWork() {
        //设置基础参数
        Asset_Details_Body entity =new Asset_Details_Body();

        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        entity.setAccessToken(baseRequests.get(0).getAccessToken());
        entity.setPlatform(2);
        entity.setUuid(baseRequests.get(0).getUuid());
        entity.setUid(baseRequests.get(0).getUid());
        entity.setLoginUsername(baseRequests.get(0).getLoginUsername());
        entity.setId(curry_detail);
        String json = Util.toJson(entity);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(this).getServer()
                .getMoneyLoanDetails(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<CurrencyLendingDetails_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<CurrencyLendingDetails_Result>(this) {
                    @Override
                    protected void onSuccees(String code, CurrencyLendingDetails_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(CurrencyLendingDetails.this,baseResponse);
                            curry_type.setText(data.getType());
                            LUtils.e("------本金----"+data.getPrincipalStr());
                            curry_amout.setText(data.getPrincipalStr());
                            curry_lixi.setText(data.getInterest()*100+"%");
                            curry_date.setText(Util.LongParseString(data.getCreateTime()));
                            curry_over_date.setText(Util.LongParseString(data.getEndTime()));
                            curry_yihuan.setText(data.getPayMoneyStr()+"万");
                            curry_yue.setText(data.getBalanceStr()+"万");
                            boolean pay = data.isPay();
                            if (!pay){
                                recyclerView.setVisibility(View.GONE);
                            }else{
                                recyclerView.setVisibility(View.VISIBLE);
                                List<CurrencyLendingDetails_Result.PayRecordListBean> payRecordList = data.getPayRecordList();
                                Curry_Details_Adapter adapter2 =new Curry_Details_Adapter(CurrencyLendingDetails.this,payRecordList);
                                recyclerView.setAdapter(adapter2);
                            }


                            ImageShowAdapter adapter=new ImageShowAdapter(CurrencyLendingDetails.this);
                            if (!TextUtils.isEmpty(data.getImage())) {
                                String imgUrl = data.getImage();
                                String[] split = imgUrl.split(",");
                                adapter.setImageArry(split);
                                rv_currency_details_imagelist.setAdapter(adapter);
                            }else{
                                if(adapter.getImageList()!=null){
                                    adapter.getImageList().clear();
                                    adapter.notifyDataSetChanged();
                                }
                            }


                            currencyLendingDetailsResult=data;
                        }
                    }
                });
    }

    private View.OnClickListener listener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v==iv_currency_details_back){
                finish();
            }else if(v== iv_currency_details_updata){
                Intent intent = new Intent(CurrencyLendingDetails.this, CurrencyLending.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("currencyLendingDetailsResult", currencyLendingDetailsResult);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }
    };
}
