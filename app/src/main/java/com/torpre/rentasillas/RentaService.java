package com.torpre.rentasillas;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.torpre.rentasillas.control.Control;
import com.torpre.rentasillas.model.Orders;
import com.torpre.rentasillas.view.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RentaService extends Service implements Runnable {

    Thread thread;

    public RentaService() {
    }

    public void onCreate() {
        super.onCreate();
        thread = new Thread(this);
        thread.start();
        Log.i("chois", "onCreate Service");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!thread.isAlive()) {
            System.out.println("onStartComand, service is not alive");
            thread = new Thread(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("chois", "onDestroyservice");
        thread.destroy();
        startService(new Intent(this, RentaService.class));
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void run() {
        try {
            System.out.println("run thread service");
            Control con = new Control(getApplicationContext());
            int dayHours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);//TODO:usar variable
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            Date newDay = sdf.parse(sdf.format(new Date()));
            List<Orders> orders = con.recentOrderDate(newDay);
            System.out.println(orders.size());
            if (orders.size() > 0)
                prepareNotification(orders);
            Thread.sleep(60*1000);//TODO: establecer horas restantes
            while (true) {
                newDay = sdf.parse(sdf.format(new Date()));
                orders = con.recentOrderDate(newDay);
                if (orders.size() > 0)
                    prepareNotification(orders);
                Thread.sleep(60*1000);//TODO: establecer 24 horas
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareNotification(List<Orders> orders) {
        System.out.println("Prepare notification");
        //TODO: agregar el servivio en el boot de la aplicacion, y seguir probando
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Pedidos para hoy");
        //.setWhen(10000*10);
        StringBuilder sb =  new StringBuilder();
        for (Orders o: orders)
            sb.append(o.toString());
        builder.setContentText(sb.toString());
        Intent targetIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);
        nManager.notify(123456, builder.build());
    }

}
