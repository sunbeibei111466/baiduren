package com.yl.baiduren.entity.greenentity;

import com.yl.baiduren.entity.greenentity.PayRecord;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.greendao.DaoSession;
import com.greendao.PayRecordDao;
import com.greendao.MoneyLoanDODao;

/**
 * 货币借贷
 */
@Entity
public class MoneyLoanDO {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "type")
    private String type;//币种
    @Property(nameInDb = "principalStr")
    private String principalStr;//本金
    @Property(nameInDb = "interest")
    private double interest;//利息
    @Property(nameInDb = "loanTime")
    private Long loanTime;//放款时间
    @Property(nameInDb = "endTime")
    private Long endTime;//'截止时间
    @ToMany(referencedJoinProperty = "pId")//一对多
    private List<PayRecord> payRecordList;
    @Property(nameInDb = "payMoneyStr")
    private String payMoneyStr;//已还合计 (元)
    @Property(nameInDb = "balanceStr")
    private String balanceStr;//剩余金额(元)
    @Property(nameInDb = "image")
    private String image;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1855824471)
    private transient MoneyLoanDODao myDao;
    @Generated(hash = 527978818)
    public MoneyLoanDO(Long id, String type, String principalStr, double interest,
            Long loanTime, Long endTime, String payMoneyStr, String balanceStr,
            String image) {
        this.id = id;
        this.type = type;
        this.principalStr = principalStr;
        this.interest = interest;
        this.loanTime = loanTime;
        this.endTime = endTime;
        this.payMoneyStr = payMoneyStr;
        this.balanceStr = balanceStr;
        this.image = image;
    }
    @Generated(hash = 774658683)
    public MoneyLoanDO() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getPrincipalStr() {
        return this.principalStr;
    }
    public void setPrincipalStr(String principalStr) {
        this.principalStr = principalStr;
    }
    public double getInterest() {
        return this.interest;
    }
    public void setInterest(double interest) {
        this.interest = interest;
    }
    public Long getLoanTime() {
        return this.loanTime;
    }
    public void setLoanTime(Long loanTime) {
        this.loanTime = loanTime;
    }
    public Long getEndTime() {
        return this.endTime;
    }
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
    public String getPayMoneyStr() {
        return this.payMoneyStr;
    }
    public void setPayMoneyStr(String payMoneyStr) {
        this.payMoneyStr = payMoneyStr;
    }
    public String getBalanceStr() {
        return this.balanceStr;
    }
    public void setBalanceStr(String balanceStr) {
        this.balanceStr = balanceStr;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1590706788)
    public List<PayRecord> getPayRecordList() {
        if (payRecordList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PayRecordDao targetDao = daoSession.getPayRecordDao();
            List<PayRecord> payRecordListNew = targetDao
                    ._queryMoneyLoanDO_PayRecordList(id);
            synchronized (this) {
                if (payRecordList == null) {
                    payRecordList = payRecordListNew;
                }
            }
        }
        return payRecordList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1197705061)
    public synchronized void resetPayRecordList() {
        payRecordList = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1176228733)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMoneyLoanDODao() : null;
    }

}
