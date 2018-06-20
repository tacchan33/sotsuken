package jp.ac.oit.is.lab261.sotsuken;

import android.content.BroadcastReceiver;
import android.util.Log;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            context.startService(new Intent(context,ControlService.class));
            /* ログ出力 */
            Log.d("debug","BootReceiver#onReceive()");
        }
    }

}
