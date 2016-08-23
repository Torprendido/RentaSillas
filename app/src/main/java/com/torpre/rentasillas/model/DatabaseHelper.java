package com.torpre.rentasillas.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.torpre.rentasillas.R;

import java.sql.SQLException;

/**
 * Created by torpre on 8/08/16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "rentaSillas.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the Orders table
    private Dao<Orders, Integer> ordersDao = null;
    private RuntimeExceptionDao<Orders, Integer> ordersRuntimeDao = null;
    private Dao<Historical, Integer> historicalDao = null;
    private RuntimeExceptionDao<Historical, Integer> historicalRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Orders.class);
            TableUtils.createTable(connectionSource, Historical.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Orders.class, true);
            TableUtils.dropTable(connectionSource, Historical.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our Orders class. It will create it or just give the cached
     * value.
     */
    public Dao<Orders, Integer> getOrdersDao() throws SQLException {
        if (ordersDao == null) {
            ordersDao = getDao(Orders.class);
        }
        return ordersDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Orders class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<Orders, Integer> getOrdersRuntimeDao() {
        if (ordersRuntimeDao == null) {
            ordersRuntimeDao = getRuntimeExceptionDao(Orders.class);
        }
        return ordersRuntimeDao;
    }

    /**
     * Returns the Database Access Object (DAO) for our Historical class. It will create it or just give the cached
     * value.
     */
    public Dao<Historical, Integer> getHistoricalDao() throws SQLException {
        if (historicalDao == null) {
            historicalDao = getDao(Historical.class);
        }
        return historicalDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Historical class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<Historical, Integer> getHistoricalRuntimeDao() {
        if (historicalRuntimeDao == null) {
            historicalRuntimeDao = getRuntimeExceptionDao(Historical.class);
        }
        return historicalRuntimeDao;
    }


    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        ordersDao = null;
        ordersRuntimeDao = null;
    }

}

