package jp.ac.oit.is.lab261.sotsuken.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import jp.ac.oit.is.lab261.sotsuken.service.ControlService;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            context.startService(new Intent(context.getApplicationContext(),ControlService.class));
        }
    }

}
