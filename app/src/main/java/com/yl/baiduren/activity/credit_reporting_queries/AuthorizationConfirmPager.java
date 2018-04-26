package com.yl.baiduren.activity.credit_reporting_queries;

import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.text.Html;
import android.widget.TextView;


import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Break_Debt_Activity;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Authorization_Confrim_Isallow;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.ServiceUrl;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 授权确认页面
 */
public class AuthorizationConfirmPager extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private TextView user_instructions;
    private TextView user_too;
    private ImageView zhengxin_finish;
    private EditText input_content;
    private Button btn;
    private Long id;
    private String isallow;
    private int check = 1;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_authorization_confirm_pager;
    }

    @Override
    public void initViews() {
        id = getIntent().getLongExtra("id", 0);
        String main_name = getIntent().getStringExtra("main_name");
        zhengxin_finish = findViewById(R.id.zhengxin_finish);
        zhengxin_finish.setOnClickListener(this);
        RadioGroup group = findViewById(R.id.rg_auth);
        group.setOnCheckedChangeListener(this);
        CheckBox checkbox = findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(checkedChangeListener);
        input_content = findViewById(R.id.input_content);
        btn = findViewById(R.id.btn_query);//提交
        btn.setOnClickListener(this);
        TextView tv_auth_text = findViewById(R.id.tv_auth_text);
        user_instructions = findViewById(R.id.user_instructions_one);
        user_instructions.setOnClickListener(this);
        user_too=findViewById(R.id.user_too);
        user_too=findViewById(R.id.user_too);
        String str1 = "本信息主题明白查询申请人:";
        String str2 = "<font color='#2595ff' size='26' >" + main_name + "</font>";
        String str3 = "对本企业信用信息的查询目的，以及信息被查询后的相关风险。鉴于以上原由，本信息主体决定:";
        String text = str1 + str2 + str3;
        tv_auth_text.setText(Html.fromHtml(text));
    }

    @Override
    public void onClick(View v) {
        if (v == zhengxin_finish) {
            finish();
        } else if (v == btn) {//提交
            if (check != 1) {
                ToastUtil.showShort(this, "请同意征信协议");
                return;
            }
            if (id != 0) {
                requestWork();
            }
        } else if (v == user_instructions) {
                Intent intent=new Intent(this, Break_Debt_Activity.class);
                intent.putExtra("type",8);
                intent.putExtra("url", ServiceUrl.H5_HCREDITTALK);
                startActivity(intent);
        }else if(v==user_too){
            Intent intent=new Intent(this, Break_Debt_Activity.class);
            intent.putExtra("type",9);
            intent.putExtra("url", ServiceUrl.H5_HEMPOWERTALK);
            startActivity(intent);
        }
    }

    private void requestWork() {
        String content = input_content.getText().toString().trim();
        if (UserInfoUtils.IsLogin(this)) {
            Authorization_Confrim_Isallow entity = new Authorization_Confrim_Isallow(DataWarehouse.getBaseRequest(this));
            entity.setId(id);
            entity.setChoice(isallow);
            entity.setReplyReason(content);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver<String> baseObserver = new BaseObserver<String>(this) {

                @Override
                protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(AuthorizationConfirmPager.this, baseResponse);
                        ToastUtil.showShort(AuthorizationConfirmPager.this, "信息提交成功");
                        finish();

                    }

                }
            };
            baseObserver.setStop(true);
            RetrofitHelper.getInstance(this).getServer()
                    .reply_Authorize(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(baseObserver);

        } else {
            finish();
        }

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.allow) {
            isallow = "1";
        } else if (checkedId == R.id.no_allow) {
            isallow = "2";
        }

    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                check = 1;
            } else {
                check = 2;
            }

        }
    };
}
