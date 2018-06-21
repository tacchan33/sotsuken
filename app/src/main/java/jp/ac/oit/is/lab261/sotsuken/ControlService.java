package jp.ac.oit.is.lab261.sotsuken;

import android.app.Service;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.util.Log;
import android.content.Intent;
import android.os.IBinder;

import jp.ac.oit.is.lab261.sotsuken.network.model.WifiScanner;


public class ControlService extends Service {

    /* 起動チェック用変数・メソッド */
    private static boolean iRunning = false;
    private static void setRunning(boolean state){iRunning = state;}
    public static boolean isRunning(){
        return iRunning;
    }

    /* WifiScannerの有効無効用 */
    private static boolean wifiScannerEnabled = false;
    public static void setWifiScannerEnabled(boolean state){wifiScannerEnabled = state;}
    public static boolean isWifiScannerEnabled(){return wifiScannerEnabled;}

    Handler handler;
    HandlerThread thread;

    WifiScanner wifiScanner;

    Notifier notifier;

    @Override
    public void onCreate() {
        super.onCreate();
        setRunning(true);

        thread = new HandlerThread("thread");
        thread.start();
        handler = new Handler(thread.getLooper());

        notifier = new Notifier(getApplicationContext());
        wifiScanner = new WifiScanner(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d("debug","run()");

        handler.post(new Runnable(){
            @Override
            public void run(){
                // アクティブな間だけ処理をする
                while ( isWifiScannerEnabled() ) {
                    try {
                        Log.d("debug","scan中");
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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


    /* スレッド処理 */


    private Runnable runTask = new Runnable() {
        @Override
        public void run() {
            // アクティブな間だけ処理をする
            while ( isWifiScannerEnabled() ) {
                try {
                    Log.d("debug","scan中");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // ハンドラーをはさまないとToastでエラーでる
                // UIスレッド内で処理をしないといけないらしい
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

            handler.post(new Runnable() {

                @Override
                public void run() {
                    Log.d("debug","スレッド終了");
                }
            });
        }
    };


}
