package com.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yl.baiduren.entity.greenentity.My_Delisting_Query;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MY__DELISTING__QUERY".
*/
public class My_Delisting_QueryDao extends AbstractDao<My_Delisting_Query, Long> {

    public static final String TABLENAME = "MY__DELISTING__QUERY";

    /**
     * Properties of entity My_Delisting_Query.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Query_delisting = new Property(1, String.class, "query_delisting", false, "query_delisting");
    }


    public My_Delisting_QueryDao(DaoConfig config) {
        super(config);
    }
    
    public My_Delisting_QueryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MY__DELISTING__QUERY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"query_delisting\" TEXT);"); // 1: query_delisting
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MY__DELISTING__QUERY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, My_Delisting_Query entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String query_delisting = entity.getQuery_delisting();
        if (query_delisting != null) {
            stmt.bindString(2, query_delisting);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, My_Delisting_Query entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String query_delisting = entity.getQuery_delisting();
        if (query_delisting != null) {
            stmt.bindString(2, query_delisting);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public My_Delisting_Query readEntity(Cursor cursor, int offset) {
        My_Delisting_Query entity = new My_Delisting_Query( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // query_delisting
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, My_Delisting_Query entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setQuery_delisting(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(My_Delisting_Query entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(My_Delisting_Query entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(My_Delisting_Query entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}