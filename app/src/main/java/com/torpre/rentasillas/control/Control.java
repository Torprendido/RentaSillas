package com.torpre.rentasillas.control;

import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.torpre.rentasillas.model.DatabaseHelper;
import com.torpre.rentasillas.model.Historical;
import com.torpre.rentasillas.model.Orders;

public class Control {

    private DatabaseHelper dbh;

    public Control(Context context) {
        dbh = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public void insert(Orders order) throws Exception {
        try {
            Dao<Orders, Integer> dao = dbh.getOrdersDao();
            dao.create(order);
        } catch (SQLException ex) {
            throw traduceSQLException(ex.getCause().getCause().toString());
        }
    }

    public void insert(Historical historical) throws Exception {
        try {
            Dao<Historical, Integer> dao = dbh.getHistoricalDao();
            dao.create(historical);
        } catch (SQLException ex) {
            throw traduceSQLException(ex.getCause().getCause().toString());
        }
    }

    public Exception traduceSQLException(String cause) {
        if (cause.contains("NOT NULL constraint failed: orders.address"))
            return new Exception("Debes ingresar dirección y/o persona");
        else if(cause.contains("NOT NULL constraint failed: orders.date"))
            return new Exception("Debes ingresar una fecha");
        else if(cause.contains("NOT NULL constraint failed: orders.status"))
            return new Exception("Debes ingresar el estado del pedido -> Order.STATUS");
        else if(cause.contains("NOT NULL constraint failed: historical.historicalTag"))
            return new Exception("Debes ingresar la tiqueta del Historial");
        else
            return new Exception(cause);
    }

    public String[] ordersArray(List<Orders> orders) {
        try {
            Dao<Orders, Integer> dao = dbh.getOrdersDao();
            String[] array = new String[orders.size()];
            for (int i = 0; i < orders.size(); i ++)
                array[i] = orders.get(i).toString();
            return array;
        } catch (SQLException e) {
            Log.w(e.getMessage(), dbh.getClass().toString());
            return null;
        }
    }

    public void deleteOrder(Orders order) throws Exception {
        try {
            Dao<Orders, Integer> dao = dbh.getOrdersDao();
            dao.delete(order);
        } catch (SQLException e) {
            throw new Exception("Imposible borrar el pedido");
        }
    }

    public void changeOrderStatus(Orders order, byte newStatus) throws  Exception {
        try {
            Dao<Orders, Integer> dao = dbh.getOrdersDao();
            order.setStatus(newStatus);
            dao.update(order);
        } catch (SQLException e) {
            throw new Exception("Imposible cambiar el estado del pedido");
        }
    }

    public List<Orders> orders(byte orderStatus) {
        try {
            Dao<Orders, Integer> dao = dbh.getOrdersDao();
            return dao.queryForEq("status", orderStatus);
        } catch (SQLException e) {
            Log.w(e.getMessage(), dbh.getClass().toString());
            return null;
        }
    }

    public static String convertTOString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yyyy");
        return sdf.format(date);
    }

    public void update(Orders order) throws Exception {
        try {
            Dao<Orders, Integer> dao = dbh.getOrdersDao();
            dao.update(order);
        } catch (SQLException ex) {
            throw traduceSQLException(ex.getCause().getCause().toString());
        }
    }
}
