package jp.ac.oit.is.lab261.sotsuken;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.support.annotation.Nullable;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;


public class ControlService extends Service {

    /* 起動チェック用変数・メソッド */
    private static boolean gRunning = false;
    public static boolean isRunning(){
        return gRunning;
    }

    NotificationManager notificationManager = null;
    Notification notification = null;

    @Override
    public void onCreate() {
        super.onCreate();
        gRunning = true;
        /* Print debug Log */
        Log.d("debug", "BackgroundService#onCreate()");

        Intent notificationIntent = new Intent(this,MainActivity.class);//アクティビティをインテントに追加
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);//マネージャー生成
        notification = new Notification.Builder(this)//通知を生成
                .setSmallIcon(R.drawable.ic_launcher_background)//通知のイラスト
                //.setContentInfo("動作中")//通知のステータス
                .setContentTitle("位置管理システム")//通知のタイトル
                .setContentText("アプリの設定を開くにはタップ")//通知のテキスト
                .setDefaults(Notification.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)//通知タップでActivityが呼ばれる
                .setAutoCancel(false)//タップで通知削除しない
                .build();
        notification.flags = Notification.FLAG_NO_CLEAR;//通知のスライド削除を停止
        notificationManager.notify(1,notification);//マネージャに通知を追加

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        /* Print debug Log */
        Log.d("debug", "ControlService#onStartCommand()");

        return START_STICKY;//サービスが落ちても再起動する
    }

    @Override
    public void onDestroy() {
        gRunning = false;
        /* ログ出力 */
        Log.d("debug", "ControlService#onDestroy()");

        notificationManager.cancel(1);//通知削除

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