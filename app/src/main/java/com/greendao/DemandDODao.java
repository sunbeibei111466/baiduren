package com.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yl.baiduren.entity.greenentity.DemandDO;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DEMAND_DO".
*/
public class DemandDODao extends AbstractDao<DemandDO, Long> {

    public static final String TABLENAME = "DEMAND_DO";

    /**
     * Properties of entity DemandDO.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CategoryName = new Property(1, String.class, "categoryName", false, "categoryName");
        public final static Property CategoryId = new Property(2, int.class, "categoryId", false, "categoryId");
        public final static Property Name = new Property(3, String.class, "name", false, "name");
        public final static Property Value = new Property(4, int.class, "value", false, "value");
        public final static Property ValueStr = new Property(5, String.class, "valueStr", false, "valueStr");
        public final static Property Area = new Property(6, String.class, "area", false, "area");
        public final static Property Dizhi = new Property(7, String.class, "dizhi", false, "dizhi");
        public final static Property Remark = new Property(8, String.class, "remark", false, "remark");
    }


    public DemandDODao(DaoConfig config) {
        super(config);
    }
    
    public DemandDODao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DEMAND_DO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"categoryName\" TEXT," + // 1: categoryName
                "\"categoryId\" INTEGER NOT NULL ," + // 2: categoryId
                "\"name\" TEXT," + // 3: name
                "\"value\" INTEGER NOT NULL ," + // 4: value
                "\"valueStr\" TEXT," + // 5: valueStr
                "\"area\" TEXT," + // 6: area
                "\"dizhi\" TEXT," + // 7: dizhi
                "\"remark\" TEXT);"); // 8: remark
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DEMAND_DO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DemandDO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String categoryName = entity.getCategoryName();
        if (categoryName != null) {
            stmt.bindString(2, categoryName);
        }
        stmt.bindLong(3, entity.getCategoryId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
        stmt.bindLong(5, entity.getValue());
 
        String valueStr = entity.getValueStr();
        if (valueStr != null) {
            stmt.bindString(6, valueStr);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(7, area);
        }
 
        String dizhi = entity.getDizhi();
        if (dizhi != null) {
            stmt.bindString(8, dizhi);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(9, remark);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DemandDO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String categoryName = entity.getCategoryName();
        if (categoryName != null) {
            stmt.bindString(2, categoryName);
        }
        stmt.bindLong(3, entity.getCategoryId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
        stmt.bindLong(5, entity.getValue());
 
        String valueStr = entity.getValueStr();
        if (valueStr != null) {
            stmt.bindString(6, valueStr);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(7, area);
        }
 
        String dizhi = entity.getDizhi();
        if (dizhi != null) {
            stmt.bindString(8, dizhi);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(9, remark);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DemandDO readEntity(Cursor cursor, int offset) {
        DemandDO entity = new DemandDO( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // categoryName
            cursor.getInt(offset + 2), // categoryId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.getInt(offset + 4), // value
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // valueStr
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // area
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // dizhi
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // remark
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DemandDO entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCategoryName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCategoryId(cursor.getInt(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setValue(cursor.getInt(offset + 4));
        entity.setValueStr(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setArea(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDizhi(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setRemark(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DemandDO entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DemandDO entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DemandDO entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
