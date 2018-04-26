package com.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yl.baiduren.entity.greenentity.Hall_Querry;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HALL__QUERRY".
*/
public class Hall_QuerryDao extends AbstractDao<Hall_Querry, Long> {

    public static final String TABLENAME = "HALL__QUERRY";

    /**
     * Properties of entity Hall_Querry.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property QuerryText = new Property(1, String.class, "querryText", false, "querryText");
    }


    public Hall_QuerryDao(DaoConfig config) {
        super(config);
    }
    
    public Hall_QuerryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HALL__QUERRY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"querryText\" TEXT);"); // 1: querryText
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HALL__QUERRY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Hall_Querry entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String querryText = entity.getQuerryText();
        if (querryText != null) {
            stmt.bindString(2, querryText);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Hall_Querry entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String querryText = entity.getQuerryText();
        if (querryText != null) {
            stmt.bindString(2, querryText);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Hall_Querry readEntity(Cursor cursor, int offset) {
        Hall_Querry entity = new Hall_Querry( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // querryText
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Hall_Querry entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setQuerryText(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Hall_Querry entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Hall_Querry entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Hall_Querry entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}