package com.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yl.baiduren.entity.greenentity.SponsorDO;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SPONSOR_DO".
*/
public class SponsorDODao extends AbstractDao<SponsorDO, Long> {

    public static final String TABLENAME = "SPONSOR_DO";

    /**
     * Properties of entity SponsorDO.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "name");
        public final static Property Area = new Property(2, String.class, "area", false, "area");
        public final static Property Dizhi = new Property(3, String.class, "dizhi", false, "dizhi");
        public final static Property Address = new Property(4, String.class, "address", false, "address");
        public final static Property IdCode = new Property(5, String.class, "idCode", false, "idCode");
        public final static Property PhoneNumber = new Property(6, String.class, "phoneNumber", false, "phoneNumber");
        public final static Property Image = new Property(7, String.class, "image", false, "image");
    }


    public SponsorDODao(DaoConfig config) {
        super(config);
    }
    
    public SponsorDODao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SPONSOR_DO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"name\" TEXT," + // 1: name
                "\"area\" TEXT," + // 2: area
                "\"dizhi\" TEXT," + // 3: dizhi
                "\"address\" TEXT," + // 4: address
                "\"idCode\" TEXT," + // 5: idCode
                "\"phoneNumber\" TEXT," + // 6: phoneNumber
                "\"image\" TEXT);"); // 7: image
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SPONSOR_DO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SponsorDO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(3, area);
        }
 
        String dizhi = entity.getDizhi();
        if (dizhi != null) {
            stmt.bindString(4, dizhi);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
 
        String idCode = entity.getIdCode();
        if (idCode != null) {
            stmt.bindString(6, idCode);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(7, phoneNumber);
        }
 
        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(8, image);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SponsorDO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(3, area);
        }
 
        String dizhi = entity.getDizhi();
        if (dizhi != null) {
            stmt.bindString(4, dizhi);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
 
        String idCode = entity.getIdCode();
        if (idCode != null) {
            stmt.bindString(6, idCode);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(7, phoneNumber);
        }
 
        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(8, image);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SponsorDO readEntity(Cursor cursor, int offset) {
        SponsorDO entity = new SponsorDO( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // area
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // dizhi
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // address
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // idCode
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // phoneNumber
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // image
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SponsorDO entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setArea(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDizhi(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAddress(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIdCode(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPhoneNumber(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setImage(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SponsorDO entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SponsorDO entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SponsorDO entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}