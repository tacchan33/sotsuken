package jp.ac.oit.is.lab261.sotsuken.network.model;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import jp.ac.oit.is.lab261.sotsuken.network.entity.Accesspoint;
import jp.ac.oit.is.lab261.sotsuken.network.entity.Device;

public class WifiScanner {
    private Context context;

    private WifiManager wifiManager;
    private Accesspoint[] accesspoint;
    private Device device;

    public WifiScanner(@NonNull Context context){
        this.context = context;
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }


    public void scanWifi(){
        List<ScanResult> scanResults = Collections.emptyList();
        try{
            /* WiFi機能の有効化 */
            if( !wifiManager.isWifiEnabled() ){
                wifiManager.setWifiEnabled(true);
            }
            /* WiFiスキャン */
            if( wifiManager.startScan() ){
                scanResults = wifiManager.getScanResults();
            }
        }catch (Exception e){
            // critical error
        }

    }

}
