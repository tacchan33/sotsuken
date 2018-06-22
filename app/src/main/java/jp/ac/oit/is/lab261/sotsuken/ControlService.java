package jp.ac.oit.is.lab261.sotsuken;

import android.app.Service;
import android.support.annotation.Nullable;
import android.content.Intent;
import android.os.IBinder;

import jp.ac.oit.is.lab261.sotsuken.network.model.WifiScannerService;


public class ControlService extends Service {

    /* 起動チェック用変数・メソッド */
    private static boolean iRunning = false;
    private static void setRunning(boolean state){iRunning = state;}
    public static boolean isRunning(){
        return iRunning;
    }

    WifiScannerService wifiScannerService;
    Notifier notifier;

    @Override
    public void onCreate() {
        super.onCreate();
        notifier = new Notifier(getApplicationContext());

        setRunning(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return START_STICKY;//サービスが落ちても再起動する
    }

    @Override
    public void onDestroy() {
        setRunning(false);

        stopSelf();//Service終了
        super.onDestroy();//Service破棄

        startService(new Intent( getApplicationContext(), ControlService.class));//再起動
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
