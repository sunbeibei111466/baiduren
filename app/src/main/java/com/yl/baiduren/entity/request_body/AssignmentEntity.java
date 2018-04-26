package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by Android_apple on 2018/1/9.
 */

public class AssignmentEntity extends BaseRequest {

    private int steps;//步骤
    private Long claimsId;//债权ID 用于
    private int saveOrUpdate;//1新增 2修改

    private BaseClaimsVo baseClaimsVo;//基础信息
    private List<SharesDO> sharesDOS;//	第二页：股份
    private List<InvestDO>	investDOS;//第三页：投资
    private List<AssetsDO>	assetsDOS; //第四页：资产
    private List<LiabilitiesDO> liabilitiesDOS;//	第五页：负债
    private List<ManageDO>	manageDOS;//第六页：经营

    public static class BaseClaimsVo{
        private String enterpriseName;//企业名称
        private String enterpriseNumber;	//企业证号
        private String area;//企业所属地
        private String areaStr;
        private String lawyerName;//企业法人
        private String idCard;//身份证号
        private String mobile;//电话
        private String address;//详细地址
        private String industryType;//行业
        private String upIndustryType;//上游行业
        private String downIndustryType;//下游行业
        private String profile	;//简介
        private String imgUrl;//图片地址
        private String addressCode;//所属地区
        private String addressCodeStr;//


        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public String getEnterpriseNumber() {
            return enterpriseNumber;
        }

        public void setEnterpriseNumber(String enterpriseNumber) {
            this.enterpriseNumber = enterpriseNumber;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getLawyerName() {
            return lawyerName;
        }

        public void setLawyerName(String lawyerName) {
            this.lawyerName = lawyerName;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIndustryType() {
            return industryType;
        }

        public void setIndustryType(String industryType) {
            this.industryType = industryType;
        }

        public String getUpIndustryType() {
            return upIndustryType;
        }

        public void setUpIndustryType(String upIndustryType) {
            this.upIndustryType = upIndustryType;
        }

        public String getDownIndustryType() {
            return downIndustryType;
        }

        public void setDownIndustryType(String downIndustryType) {
            this.downIndustryType = downIndustryType;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getAddressCode() {
            return addressCode;
        }

        public void setAddressCode(String addressCode) {
            this.addressCode = addressCode;
        }

        public String getAreaStr() {
            return areaStr;
        }

        public void setAreaStr(String areaStr) {
            this.areaStr = areaStr;
        }

        public String getAddressCodeStr() {
            return addressCodeStr;
        }

        public void setAddressCodeStr(String addressCodeStr) {
            this.addressCodeStr = addressCodeStr;
        }
    }

    public static class SharesDO{//股份
        private Long id;
        private String shareholders;
        private String proportion;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getShareholders() {
            return shareholders;
        }

        public void setShareholders(String shareholders) {
            this.shareholders = shareholders;
        }

        public String getProportion() {
            return proportion;
        }

        public void setProportion(String proportion) {
            this.proportion = proportion;
        }
    }

    public static class InvestDO{//投资

        private Long id;
        private String investCompany;//投资项目
        /**
         shareholders    投资股东
         proportion      投资占比
         amount          投资金额
         */
        private List<Map<String,String>> invest;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getInvestCompany() {
            return investCompany;
        }

        public void setInvestCompany(String investCompany) {
            this.investCompany = investCompany;
        }

        public List<Map<String, String>> getInvest() {
            return invest;
        }

        public void setInvest(List<Map<String, String>> invest) {
            this.invest = invest;
        }
    }

    public static class AssetsDO{//资产
        private Long id;
        /**
         * 产业名称
         */
        private String industryName;
        /**
         * type        资产类型   （1.固定资产  2.技术资产）
         * assetsName  资产名称
         * amount      资产价值
         */
        private List<Map<String, String>> assets;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getIndustryName() {
            return industryName;
        }

        public void setIndustryName(String industryName) {
            this.industryName = industryName;
        }

        public List<Map<String, String>> getAssets() {
            return assets;
        }

        public void setAssets(List<Map<String, String>> assets) {
            this.assets = assets;
        }
    }

    public static class LiabilitiesDO{//负债

        private Long id;
        private String name;//债权方名称
        private String idNumber;//证件号
        private String mobile;//手机号
        private String address;//地址
        private Long amount;//欠债金额
        private Long debtTime;//欠债时间
        private String debtTimeString;
        private String area;//所属地
        private String areaStr;//所属地


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getAreaStr() {
            return areaStr;
        }

        public void setAreaStr(String areaStr) {
            this.areaStr = areaStr;
        }

        public String getDebtTimeString() {
            return debtTimeString;
        }

        public void setDebtTimeString(String debtTimeString) {
            this.debtTimeString = debtTimeString;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public Long getDebtTime() {
            return debtTime;
        }

        public void setDebtTime(Long debtTime) {
            this.debtTime = debtTime;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }
    }

    public static class ManageDO{//经营
        private Long id;
        private String industry;
        private String isProfit;
        private Long amount;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getIsProfit() {
            return isProfit;
        }

        public void setIsProfit(String isProfit) {
            this.isProfit = isProfit;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }
    }








    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }



    public List<SharesDO> getSharesDOS() {
        return sharesDOS;
    }

    public void setSharesDOS(List<SharesDO> sharesDOS) {
        this.sharesDOS = sharesDOS;
    }

    public List<InvestDO> getInvestDOS() {
        return investDOS;
    }

    public void setInvestDOS(List<InvestDO> investDOS) {
        this.investDOS = investDOS;
    }

    public List<AssetsDO> getAssetsDOS() {
        return assetsDOS;
    }

    public void setAssetsDOS(List<AssetsDO> assetsDOS) {
        this.assetsDOS = assetsDOS;
    }

    public List<LiabilitiesDO> getLiabilitiesDOS() {
        return liabilitiesDOS;
    }

    public void setLiabilitiesDOS(List<LiabilitiesDO> liabilitiesDOS) {
        this.liabilitiesDOS = liabilitiesDOS;
    }

    public List<ManageDO> getManageDOS() {
        return manageDOS;
    }

    public void setManageDOS(List<ManageDO> manageDOS) {
        this.manageDOS = manageDOS;
    }

    public int getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(int saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public Long getClaimsId() {
        return claimsId;
    }

    public void setClaimsId(Long claimsId) {
        this.claimsId = claimsId;
    }

    public BaseClaimsVo getBaseClaimsVo() {
        return baseClaimsVo;
    }

    public void setBaseClaimsVo(BaseClaimsVo baseClaimsVo) {
        this.baseClaimsVo = baseClaimsVo;
    }

    public AssignmentEntity(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
