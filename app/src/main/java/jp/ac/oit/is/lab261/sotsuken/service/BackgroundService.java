package jp.ac.oit.is.lab261.sotsuken.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

import jp.ac.oit.is.lab261.sotsuken.model.network.WifiScanner;
import jp.ac.oit.is.lab261.sotsuken.model.storage.HttpUploader;
import jp.ac.oit.is.lab261.sotsuken.model.storage.SettingImport;

public class BackgroundService extends Service {

    private static boolean iRunning = false;//サービスの動作状態
    private static void setRunning(boolean state){iRunning=state;}
    public static boolean isRunning(){return iRunning;}

    private SettingImport setting;//設定項目
    private WifiManager wifiManager;//WiFi制御
    private WifiScanner wifiScanner ;//WiFiビーコンスキャン
    private HttpUploader httpUploader;//データHTTPアップロード

    private Timer timer = new Timer();//ループ用タイマー


    public void onCreate(){
        super.onCreate();
        setting = new SettingImport(getApplicationContext());
        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiScanner = new WifiScanner(getApplicationContext());

        setRunning(true);
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                /* WiFi機能の有効化 */
                if( !wifiManager.isWifiEnabled() ){
                    wifiManager.setWifiEnabled(true);
                }

                wifiScanner.WifiScan();//WiFiビーコンスキャン

                httpUploader = new HttpUploader(setting.getHost(),setting.getUser(),setting.getPassword(),setting.getInterval());
                for(int i=0;i<wifiScanner.RANK;i++){//データセット
                    httpUploader.setMACAddress(wifiScanner.getMACAddress());
                    httpUploader.setBSSID(i,wifiScanner.getBSSID(i));
                    httpUploader.setLEVEL(i,wifiScanner.getLEVEL(i));
                }
                httpUploader.execute(HttpUploader.UPLOAD);//通信
            }
        }, 0, setting.getInterval());
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        setRunning(false);
        super.onDestroy();
        if(timer != null){
            timer.cancel();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
