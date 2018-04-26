package com.yl.baiduren.fragment.tradinghall;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.tradinghall.AssignmentActivity;
import com.yl.baiduren.adapter.tradinghall.ExpandableAdapter;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.base.ICallBack;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.InvestmentProjectBean;
import com.yl.baiduren.entity.ShareholderBean;
import com.yl.baiduren.entity.request_body.AssignmentEntity;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyExpandListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.investDOList;
import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.type;


/**
 * 债权转让 --投资情况
 */
public class Investment_situation_Fragment extends BaseFragment {

    private LinearLayout ll_investment_parent;
    //投资项目名称
    EditText etInvestmentProjects;
    //添加新项目  按钮
    ImageView ivAddNewProject;
    //股东姓名
    EditText etShareholder;
    //投资金额
    EditText etInvestmentMoney;
    //股份占比
    EditText etShareRatio;
    //完成  提交
    Button btComplete, but_invest;
    //parent   List
    MyExpandListView parentListView;

    ExpandableAdapter adapter;

    //项目item  数据源
    private List<InvestmentProjectBean> investmentProjectBeen = null;
    //股东item  数据源
    private List<ShareholderBean> shareholderBeen=null;

    private boolean isFirst = true;

    private final int COMPLETE = 1;

    @Override
    public int loadWindowLayout() {
        return R.layout.investment_situation_fragment;
    }

    @Override
    public void initViews(View rootView) {
        initView(rootView);
        initEditTextStyle();
        initListener();

        if (type != 0) {//表示进入详情
            if(type==1){
                //从债权大厅进入，不可编辑
                setEnabled();
            }
            layoutUpdata();//从我的债权进入，可以编辑
        }
        initAdapter();
    }

    private void setEnabled() {
        adapter.setVisibility(true);
        but_invest.setVisibility(View.GONE);
        ll_investment_parent.setVisibility(View.GONE);
    }

    private void layoutUpdata() {
        if (investDOList != null) {
            for (int i = 0; i < investDOList.size(); i++) {
                LUtils.e("-1--nvestDOList.size--",investDOList.size()+"");
                InvestmentProjectBean investmentProjectBean=new InvestmentProjectBean();
                investmentProjectBean.setInvestmentProject(investDOList.get(i).getInvestCompany());
                List<ShareholderBean> shareholderBeans=new ArrayList<>();
                for(int j=0;j<investDOList.get(i).getInvest().size();j++){
                    LUtils.e("-2--nvestDOList.size--",investDOList.get(i).getInvest().size()+"");
                    ShareholderBean shareholderBean=new ShareholderBean();
                    shareholderBean.setShareholderName(investDOList.get(i).getInvest().get(j).get("shareholders"));
                    shareholderBean.setInvestmentNumber(investDOList.get(i).getInvest().get(j).get("amount"));
                    shareholderBean.setShareRatio(investDOList.get(i).getInvest().get(j).get("proportion"));
                    shareholderBeans.add(shareholderBean);
                }
                investmentProjectBean.setShareholderBeen(shareholderBeans);
                investmentProjectBeen.add(investmentProjectBean);
            }
        }
    }

    private void initView(View view) {
        investmentProjectBeen = new ArrayList<>();
        etInvestmentProjects = view.findViewById(R.id.et_investment_projects);//投资项目
        ivAddNewProject = view.findViewById(R.id.iv_add_new_project);//添加项目
        etShareholder = view.findViewById(R.id.et_shareholder);//股东姓名
        etInvestmentMoney = view.findViewById(R.id.et_investment_money);//投资金额
        etShareRatio = view.findViewById(R.id.et_share_ratio);//股份占比
        btComplete = view.findViewById(R.id.bt_investment_complete);//保存按钮
        parentListView = view.findViewById(R.id.list_parent_view);//ExpandableListView
        but_invest = view.findViewById(R.id.but_invest);
        ll_investment_parent=view.findViewById(R.id.ll_investment_parent);
        adapter = new ExpandableAdapter(getActivity());
    }

    private void initEditTextStyle() {
        setHintTextSize("请输入项目名称", etInvestmentProjects);
        setHintTextSize("请输入项目的股东组成", etShareholder);
        setHintTextSize("请输入股东真实姓名", etInvestmentMoney);
        setHintTextSize("%", etShareRatio);
    }

    private void initListener() {
        btComplete.setOnClickListener(onClickListener);
        ivAddNewProject.setOnClickListener(onClickListener);
        but_invest.setOnClickListener(onClickListener);
    }


    /**
     * 点击完成
     */
    private void addShareholders() {
        //判断项目名称是否存在
        if (isCanAddProject()) {
            if (isCanAddShareholder()) {
                if (etInvestmentProjects.isEnabled()) {
                    createFirstInfo();
                    //按钮变成添加股东
                    int ADD_SHARE_HOLDER = 2;
                    updateBtnStyle(ADD_SHARE_HOLDER);
                    addNewShareholder();
                    updateLayoutState(false);

                    for (int i = 0; i < investmentProjectBeen.size(); i++) {
                        LUtils.e("--项目名---" + investmentProjectBeen.get(i).getInvestmentProject());
                        for (int j = 0; j < investmentProjectBeen.get(i).getShareholderBeen().size(); j++) {
                            LUtils.e("--股东名---" + investmentProjectBeen.get(i).getShareholderBeen().get(j).getShareholderName());
                            LUtils.e("--股东金额---" + investmentProjectBeen.get(i).getShareholderBeen().get(j).getInvestmentNumber());
                            LUtils.e("--股东占比---" + investmentProjectBeen.get(i).getShareholderBeen().get(j).getShareRatio());
                        }
                    }
                } else {
                    addNewShareholder();
                    updateLayoutState(false);

                    for (int i = 0; i < investmentProjectBeen.size(); i++) {
                        LUtils.e("--项目名---" + investmentProjectBeen.get(i).getInvestmentProject());
                        for (int j = 0; j < investmentProjectBeen.get(i).getShareholderBeen().size(); j++) {
                            LUtils.e("--股东名---" + investmentProjectBeen.get(i).getShareholderBeen().get(j).getShareholderName());
                            LUtils.e("--股东金额---" + investmentProjectBeen.get(i).getShareholderBeen().get(j).getInvestmentNumber());
                            LUtils.e("--股东占比---" + investmentProjectBeen.get(i).getShareholderBeen().get(j).getShareRatio());
                        }
                    }
                }
            } else {
                ToastUtil.showShort(getActivity(), "请补全股东信息");
            }
        } else {
            ToastUtil.showShort(getActivity(), "请填写项目名称");
        }
        updateAdapter();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_investment_complete:
                    addShareholders();
                    break;
                case R.id.iv_add_new_project:
                    clearEditview();
                    break;
                case R.id.but_invest://提交
                    getHttpData();
                    break;
            }
        }
    };

    public void getHttpData() {
        if (AssignmentActivity.claimsId != null) {
            List<AssignmentEntity.InvestDO> investDOList = new ArrayList<>();
            List<InvestmentProjectBean> investmentProjectBeans=adapter.getInvestmentProjectBeen();
            if (investmentProjectBeans!=null&&investmentProjectBeans.size() != 0) {
                for (int i = 0; i < investmentProjectBeans.size(); i++) {
                    AssignmentEntity.InvestDO investDO = new AssignmentEntity.InvestDO();
                    investDO.setInvestCompany(investmentProjectBeans.get(i).getInvestmentProject());
                    List<Map<String, String>> mapList = new ArrayList<>();
                    for (int j = 0; j < investmentProjectBeans.get(i).getShareholderBeen().size(); j++) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("shareholders", investmentProjectBeans.get(i).getShareholderBeen().get(j).getShareholderName());
                        map.put("amount", investmentProjectBeans.get(i).getShareholderBeen().get(j).getInvestmentNumber());
                        map.put("proportion", investmentProjectBeans.get(i).getShareholderBeen().get(j).getShareRatio());
                        mapList.add(map);
                        investDO.setInvest(mapList);
                    }

                    investDOList.add(investDO);
                }

            } else {
                ToastUtil.showShort(getActivity(), "请填写数据");
                return;
            }

            AssignmentEntity assignmentEntity = new AssignmentEntity(DataWarehouse.getBaseRequest(getActivity()));
            assignmentEntity.setSteps(3);
            assignmentEntity.setSaveOrUpdate(1);
            assignmentEntity.setClaimsId(AssignmentActivity.claimsId);
            assignmentEntity.setInvestDOS(investDOList);
            getHttp(assignmentEntity);
        } else {
            ToastUtil.showShort(getActivity(), "请先填写基本信息，在填写股分结构数据");
        }
    }

    public void getHttp(AssignmentEntity assignmentEntity) {
        String json = Util.toJson(assignmentEntity);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(getActivity()).getServer()
                .getSaveOrUpdate(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(getActivity()) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            LUtils.e("----data-----", data);
                            ToastUtil.showShort(getActivity(),"投资情况保存成功");
                            AssignmentActivity.claimsId = Long.valueOf(data);//债权Id
                        }
                    }
                });
    }

    /**
     * 清空四个输入框的内容
     */
    private void clearEditview() {
        updateBtnStyle(COMPLETE);
        etInvestmentProjects.setEnabled(true);
        etInvestmentProjects.setText("");
        updateLayoutState(true);
    }

    /**
     * 设置editText的提示文字大小
     *
     * @param hintStr
     * @param editText
     */
    private void setHintTextSize(String hintStr, EditText editText) {
        SpannableString ss = new SpannableString(hintStr);
        //设置字体颜色
        editText.setHintTextColor(getResources().getColor(R.color.light_bai));
        //设置字体大小（绝对值,单位：像素）
        //第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素
        ss.setSpan(new AbsoluteSizeSpan(13, true), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }


    /**
     * 创建首条信息
     */
    public void createFirstInfo() {
        shareholderBeen = new ArrayList<>();//股东item  数据源
        //创建项目信息
        InvestmentProjectBean investmentProjectBean = new InvestmentProjectBean();
        investmentProjectBean.setInvestmentProject(etInvestmentProjects.getText().toString());//投资项目名称
        //添加n个股东信息
        investmentProjectBean.setShareholderBeen(shareholderBeen);
        investmentProjectBeen.add(investmentProjectBean);
    }

    /**
     * 添加新股东
     */
    private void addNewShareholder() {
        //先保股东信息
        ShareholderBean shareholderBean = new ShareholderBean();
        shareholderBean.setShareholderName(etShareholder.getText().toString());
        shareholderBean.setInvestmentNumber(etInvestmentMoney.getText().toString());
        shareholderBean.setShareRatio(etShareRatio.getText().toString());
        //添加单个股东
        shareholderBeen.add(shareholderBean);
    }


    /**
     * 刷新adapter
     */
    private void updateAdapter() {
        if (adapter != null)
            adapter.notifyDataSetChanged();

        //展开所有子列表
//        for (int i = 0; i < parentListView.getCount(); i++) {
//            if (!parentListView.isGroupExpanded(i))
//                parentListView.expandGroup(i);
//        }
    }

    /**
     * 更改按钮样式
     */
    private void updateBtnStyle(int type) {
        if (type == COMPLETE) {//变为完成按钮
            btComplete.setBackgroundDrawable(getResources().getDrawable(R.drawable.debt_button_back));
            btComplete.setTextColor(getResources().getColor(R.color.blue));
            btComplete.setText("完成");
        } else {//变成添加股东按钮
            btComplete.setBackgroundDrawable(getResources().getDrawable(R.drawable.debt_button_back));
            btComplete.setTextColor(getResources().getColor(R.color.blue));
            btComplete.setText("添加股东");
        }
    }

    /**
     * 更新布局状态
     */
    private void updateLayoutState(boolean isEnabled) {
        etInvestmentProjects.setEnabled(isEnabled);//投资项目名称
        //清空股东信息
        etShareholder.setText("");
        etInvestmentMoney.setText("");
        etShareRatio.setText("");
    }


    /**
     * 是否可添加项目
     *
     * @return
     */
    private boolean isCanAddProject() {
        boolean is;
        //投资项目名称不为空时
        is = !etInvestmentProjects.getText().toString().equals("");
        return is;
    }

    /**
     * 是否可添加股东
     */
    private boolean isCanAddShareholder() {
        boolean is;
        //股东姓名  投资金额 股份占比
        is = !etShareholder.getText().toString().equals("") &&
                !etInvestmentMoney.getText().toString().equals("") &&
                !etShareRatio.getText().toString().equals("");
        return is;
    }

//    /**
//     * 初始化数据
//     */
//    private void initAdapter(){
//        adapter = new ExpandableAdapter(getActivity());
//        parentListView.setAdapter(adapter);
//        adapter.setICallBack(new ICallBack() {
//            @Override
//            public void updateData() {
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }

    /**
     * 初始化数据
     */
    private void initAdapter() {

        adapter.setInvestmentProjectBeen(investmentProjectBeen);
        parentListView.setAdapter(adapter);
        adapter.setICallBack(new ICallBack() {
            @Override
            public void updateData() {
                adapter.notifyDataSetChanged();
            }
        });
    }


}
