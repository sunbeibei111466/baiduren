package com.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yl.baiduren.entity.greenentity.CategoryDo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CATEGORY_DO".
*/
public class CategoryDoDao extends AbstractDao<CategoryDo, Long> {

    public static final String TABLENAME = "CATEGORY_DO";

    /**
     * Properties of entity CategoryDo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Cg_Id = new Property(0, Long.class, "Cg_Id", true, "_id");
        public final static Property Id = new Property(1, String.class, "id", false, "id");
        public final static Property Name = new Property(2, String.class, "name", false, "name");
        public final static Property Image = new Property(3, String.class, "image", false, "image");
    }


    public CategoryDoDao(DaoConfig config) {
        super(config);
    }
    
    public CategoryDoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CATEGORY_DO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: Cg_Id
                "\"id\" TEXT," + // 1: id
                "\"name\" TEXT," + // 2: name
                "\"image\" TEXT);"); // 3: image
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CATEGORY_DO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CategoryDo entity) {
        stmt.clearBindings();
 
        Long Cg_Id = entity.getCg_Id();
        if (Cg_Id != null) {
            stmt.bindLong(1, Cg_Id);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(4, image);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CategoryDo entity) {
        stmt.clearBindings();
 
        Long Cg_Id = entity.getCg_Id();
        if (Cg_Id != null) {
            stmt.bindLong(1, Cg_Id);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(4, image);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CategoryDo readEntity(Cursor cursor, int offset) {
        CategoryDo entity = new CategoryDo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // Cg_Id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // image
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CategoryDo entity, int offset) {
        entity.setCg_Id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setImage(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CategoryDo entity, long rowId) {
        entity.setCg_Id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CategoryDo entity) {
        if(entity != null) {
            return entity.getCg_Id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CategoryDo entity) {
        return entity.getCg_Id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}