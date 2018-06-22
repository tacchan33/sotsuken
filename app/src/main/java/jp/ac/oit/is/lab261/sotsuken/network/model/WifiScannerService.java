package jp.ac.oit.is.lab261.sotsuken.network.model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.ac.oit.is.lab261.sotsuken.MainActivity;
import jp.ac.oit.is.lab261.sotsuken.R;
import jp.ac.oit.is.lab261.sotsuken.network.entity.Accesspoint;
import jp.ac.oit.is.lab261.sotsuken.network.entity.Device;

public class WifiScannerService extends Service {

    private static boolean iRunning = false;
    private static void setRunning(boolean state){iRunning=state;}
    public static boolean isRunning(){return iRunning;}


    final String TAG = "WifiScannerService";
    final int INTERVAL = 1000;
    Timer timer = new Timer();

    List<ScanResult> scanResults = Collections.emptyList();

    private WifiManager wifiManager;
    private Accesspoint[] accesspoint;
    private Device device;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    NotificationManager notificationManager;
    Notification notification;


    public void onCreate(){
        super.onCreate();
        Log.d(TAG,"onCreate");
        this.wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        setRunning(true);




        notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);//マネージャー生成
        notification = new Notification.Builder(getApplicationContext())//通知を生成
                .setSmallIcon(R.drawable.ic_launcher_background)//通知のイラスト
                .setContentInfo("動作中")//通知のステータス
                .setContentTitle("位置管理システム")//通知のタイトル
                .setContentText("WifiScannerService")//通知のテキスト
                .setDefaults(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(false)//タップで通知削除しない
                .build();
        notificationManager.notify(2,notification);//マネージャに通知を追加

    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        Log.d(TAG,"onStartCommand");

        /* WiFi機能の有効化 */
        if( !wifiManager.isWifiEnabled() ){
            wifiManager.setWifiEnabled(true);
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG,"hahaha!");

                notification = new Notification.Builder(getApplicationContext())//通知を生成
                        .setSmallIcon(R.drawable.ic_launcher_background)//通知のイラスト
                        //.setContentInfo("動作中")//通知のステータス
                        .setContentTitle("位置管理システム")//通知のタイトル
                        .setContentText("アプリの設定を開くにはタップ")//通知のテキスト
                        .setDefaults(Notification.PRIORITY_DEFAULT)
                        .setAutoCancel(false)//タップで通知削除しない
                        .build();
                notificationManager.notify(2,notification);//マネージャに通知を追加

            }
        }, 0, INTERVAL);

        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        setRunning(false);
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        if(timer != null){
            timer.cancel();
        }
    }


}
