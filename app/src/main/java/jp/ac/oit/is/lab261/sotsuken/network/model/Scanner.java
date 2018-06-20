package jp.ac.oit.is.lab261.sotsuken.network.model;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

public class Scanner {
    private WifiManager wifiManager;

    public Scanner(@NonNull WifiManager wifiManager){
        this.wifiManager = wifiManager;
    }

    private void scanWifi(){
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
