package jp.ac.oit.is.lab261.sotsuken;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import jp.ac.oit.is.lab261.sotsuken.activity.MainActivity;

public class Notifier {
    private Context context = null;

    private final Integer ID = 1;//通知ID

    private NotificationManager notificationManager = null;
    private Notification notification = null;
    private Intent notificationIntent = null;
    private PendingIntent contentIntent = null;

    public Notifier(@NonNull Context context){
        this.context = context;

        notificationIntent = new Intent(context,MainActivity.class);//アクティビティをインテントに追加

        contentIntent = PendingIntent.getActivity(
                this.context,//コンテキスト
                0,
                notificationIntent,//インテント
                PendingIntent.FLAG_CANCEL_CURRENT);//フラグ

        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);//マネージャー生成
        notification = new Notification.Builder(this.context)//通知を生成
                .setSmallIcon(R.drawable.ic_launcher_background)//通知のイラスト
                //.setContentInfo()通知のステータス
                .setContentTitle("位置管理システム")//通知のタイトル
                .setContentText("アプリの設定を開くにはタップ")//通知のテキスト
                .setDefaults(Notification.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)//通知タップでActivityが呼ばれる
                .setAutoCancel(false)//タップで通知削除しない
                .build();
        notification.flags = Notification.FLAG_NO_CLEAR;//通知のスライド削除を停止
        notificationManager.notify(ID,notification);//マネージャに通知を追加
    }

    public void cancel(){
        notificationManager.cancel(ID);
    }

    public void destroy(){
        cancel();
        System.exit(0);
    }

}
