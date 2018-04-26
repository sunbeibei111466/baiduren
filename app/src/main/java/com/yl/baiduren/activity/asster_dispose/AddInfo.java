package com.yl.baiduren.activity.asster_dispose;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.entity.greenentity.AssetsnDetailesInformation;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.ToastUtil;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.util.List;

/**
 * 添加详细
 */
public class AddInfo extends BaseActivity implements View.OnClickListener{

    private EditText et_miaoshu;
    private Button bu_baocun;
    private ImageView add_dispose_fish;


    @Override
    public int loadWindowLayout() {
        return R.layout.activity_add_info;
    }

    @Override
    public void initViews() {
        et_miaoshu=findViewById(R.id.et_miaoshu);
        bu_baocun=findViewById(R.id.bu_baocun);
        bu_baocun.setOnClickListener(this);
        add_dispose_fish=findViewById(R.id.add_dispose_fish);
        add_dispose_fish.setOnClickListener(this);
        List<AssetsnDetailesInformation> information=GreenDaoUtils.getInstance(AddInfo.this).getAssetsnDetailesInformationDao().loadAll();
        if(information.size()!=0){
            et_miaoshu.setText(information.get(0).getText());
        }
    }

    @Override
    public void onClick(View v) {
        if(v==bu_baocun){
            if(TextUtil.isEmpty(et_miaoshu.getText().toString().trim())){
                ToastUtil.showShort(this,"请输入详细描述");
                return;
            }
            String describe = et_miaoshu.getText().toString().trim();
            List<AssetsnDetailesInformation> information=GreenDaoUtils.getInstance(AddInfo.this).getAssetsnDetailesInformationDao().loadAll();
            if(information.size()!=0){
                GreenDaoUtils.getInstance(AddInfo.this).getAssetsnDetailesInformationDao().deleteAll();
                AssetsnDetailesInformation info = new AssetsnDetailesInformation();
                info.setText(describe);
                GreenDaoUtils.getInstance(AddInfo.this).getAssetsnDetailesInformationDao().insert(info);
            }else{
                AssetsnDetailesInformation info = new AssetsnDetailesInformation();
                info.setText(describe);
                GreenDaoUtils.getInstance(AddInfo.this).getAssetsnDetailesInformationDao().insert(info);
            }

            finish();
        }else if(v==add_dispose_fish){
            finish();
        }
    }

}
