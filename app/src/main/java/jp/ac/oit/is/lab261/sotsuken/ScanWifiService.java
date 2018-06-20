package jp.ac.oit.is.lab261.sotsuken;


import android.app.Service;
import android.util.Log;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class ScanWifiService extends Service{

    /* 起動チェック用変数・メソッド */
    private static boolean gRunning = false;
    public static boolean isRunning(){
        return gRunning;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        gRunning = true;
        /* ログ出力 */
        Log.d("debug","ScanWifiService#onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;//サービスが落ちてもそのまま
    }

    @Override
    public void onDestroy() {
        gRunning = false;
        /* ログ出力 */
        Log.d("debug", "ScanWifiService#onDestroy()");

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
