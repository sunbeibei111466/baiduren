package com.greendao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import com.yl.baiduren.entity.greenentity.PayRecord;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PAY_RECORD".
*/
public class PayRecordDao extends AbstractDao<PayRecord, Long> {

    public static final String TABLENAME = "PAY_RECORD";

    /**
     * Properties of entity PayRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PayMoneyStr = new Property(1, String.class, "payMoneyStr", false, "PAY_MONEY_STR");
        public final static Property PayTime = new Property(2, Long.class, "payTime", false, "PAY_TIME");
        public final static Property PId = new Property(3, Long.class, "pId", false, "P_ID");
    }

    private Query<PayRecord> moneyLoanDO_PayRecordListQuery;

    public PayRecordDao(DaoConfig config) {
        super(config);
    }
    
    public PayRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PAY_RECORD\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"PAY_MONEY_STR\" TEXT," + // 1: payMoneyStr
                "\"PAY_TIME\" INTEGER," + // 2: payTime
                "\"P_ID\" INTEGER);"); // 3: pId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PAY_RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PayRecord entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String payMoneyStr = entity.getPayMoneyStr();
        if (payMoneyStr != null) {
            stmt.bindString(2, payMoneyStr);
        }
 
        Long payTime = entity.getPayTime();
        if (payTime != null) {
            stmt.bindLong(3, payTime);
        }
 
        Long pId = entity.getPId();
        if (pId != null) {
            stmt.bindLong(4, pId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PayRecord entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String payMoneyStr = entity.getPayMoneyStr();
        if (payMoneyStr != null) {
            stmt.bindString(2, payMoneyStr);
        }
 
        Long payTime = entity.getPayTime();
        if (payTime != null) {
            stmt.bindLong(3, payTime);
        }
 
        Long pId = entity.getPId();
        if (pId != null) {
            stmt.bindLong(4, pId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public PayRecord readEntity(Cursor cursor, int offset) {
        PayRecord entity = new PayRecord( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // payMoneyStr
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // payTime
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // pId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PayRecord entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPayMoneyStr(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPayTime(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setPId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(PayRecord entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(PayRecord entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PayRecord entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "payRecordList" to-many relationship of MoneyLoanDO. */
    public List<PayRecord> _queryMoneyLoanDO_PayRecordList(Long pId) {
        synchronized (this) {
            if (moneyLoanDO_PayRecordListQuery == null) {
                QueryBuilder<PayRecord> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.PId.eq(null));
                moneyLoanDO_PayRecordListQuery = queryBuilder.build();
            }
        }
        Query<PayRecord> query = moneyLoanDO_PayRecordListQuery.forCurrentThread();
        query.setParameter(0, pId);
        return query.list();
    }

}
