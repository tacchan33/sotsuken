package jp.ac.oit.is.lab261.sotsuken.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class ControlPermission {

    private static final Integer REQUEST_CODE_PERMISSION = 2;

    public void setPermission(Context context){
        if( ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED ){
            if( ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,Manifest.permission.ACCESS_COARSE_LOCATION) ){
                Toast.makeText(context,"権限がOFFになっています",Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(
                        (Activity)context,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_PERMISSION);
            }

        }
    }
}
