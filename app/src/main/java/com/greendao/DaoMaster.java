package com.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * Master of DAO (schema version 37): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 37;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(Database db, boolean ifNotExists) {
        BaseRequestDao.createTable(db, ifNotExists);
        AssetDODao.createTable(db, ifNotExists);
        AssetsnDetailesInformationDao.createTable(db, ifNotExists);
        CategoryDoDao.createTable(db, ifNotExists);
        DebtPersonDao.createTable(db, ifNotExists);
        DemandDODao.createTable(db, ifNotExists);
        GoodLoanDODao.createTable(db, ifNotExists);
        Hall_QuerryDao.createTable(db, ifNotExists);
        LoginSuccessDao.createTable(db, ifNotExists);
        MoneyLoanDODao.createTable(db, ifNotExists);
        MortgageDODao.createTable(db, ifNotExists);
        My_Delisting_QueryDao.createTable(db, ifNotExists);
        PayRecordDao.createTable(db, ifNotExists);
        PropertyLoanDODao.createTable(db, ifNotExists);
        SponsorDODao.createTable(db, ifNotExists);
        Supply_Serch_DaoDao.createTable(db, ifNotExists);
        Sussful_Exple_QueryDao.createTable(db, ifNotExists);
        LoginPasswordDao.createTable(db, ifNotExists);
        MyPagerDao.createTable(db, ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Database db, boolean ifExists) {
        BaseRequestDao.dropTable(db, ifExists);
        AssetDODao.dropTable(db, ifExists);
        AssetsnDetailesInformationDao.dropTable(db, ifExists);
        CategoryDoDao.dropTable(db, ifExists);
        DebtPersonDao.dropTable(db, ifExists);
        DemandDODao.dropTable(db, ifExists);
        GoodLoanDODao.dropTable(db, ifExists);
        Hall_QuerryDao.dropTable(db, ifExists);
        LoginSuccessDao.dropTable(db, ifExists);
        MoneyLoanDODao.dropTable(db, ifExists);
        MortgageDODao.dropTable(db, ifExists);
        My_Delisting_QueryDao.dropTable(db, ifExists);
        PayRecordDao.dropTable(db, ifExists);
        PropertyLoanDODao.dropTable(db, ifExists);
        SponsorDODao.dropTable(db, ifExists);
        Supply_Serch_DaoDao.dropTable(db, ifExists);
        Sussful_Exple_QueryDao.dropTable(db, ifExists);
        LoginPasswordDao.dropTable(db, ifExists);
        MyPagerDao.dropTable(db, ifExists);
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     * Convenience method using a {@link DevOpenHelper}.
     */
    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(BaseRequestDao.class);
        registerDaoClass(AssetDODao.class);
        registerDaoClass(AssetsnDetailesInformationDao.class);
        registerDaoClass(CategoryDoDao.class);
        registerDaoClass(DebtPersonDao.class);
        registerDaoClass(DemandDODao.class);
        registerDaoClass(GoodLoanDODao.class);
        registerDaoClass(Hall_QuerryDao.class);
        registerDaoClass(LoginSuccessDao.class);
        registerDaoClass(MoneyLoanDODao.class);
        registerDaoClass(MortgageDODao.class);
        registerDaoClass(My_Delisting_QueryDao.class);
        registerDaoClass(PayRecordDao.class);
        registerDaoClass(PropertyLoanDODao.class);
        registerDaoClass(SponsorDODao.class);
        registerDaoClass(Supply_Serch_DaoDao.class);
        registerDaoClass(Sussful_Exple_QueryDao.class);
        registerDaoClass(LoginPasswordDao.class);
        registerDaoClass(MyPagerDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /**
     * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
     */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

}
