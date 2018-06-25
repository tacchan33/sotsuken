package jp.ac.oit.is.lab261.sotsuken.model.network;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import android.util.Log;

import java.util.List;

public class WifiScanner {

    private WifiManager wifiManager = null;

    private static final Integer RANK = 3;
    private ScanResult sort[] = new ScanResult[RANK];

    public WifiScanner(){
    }

    public void onDestroy(){

    }


    public void WifiScan(Context context){
        wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResults = wifiManager.getScanResults();

        /* initialize */
        for(int i=0;i<RANK;i++){
            sort[i] = null;
        }

        sort(scanResults);

        display(sort);
    }

    private void display(ScanResult scanResult[]){
        for(int i=0;i<RANK;i++){
            Log.d("a","SSID:"+scanResult[i].SSID);
            Log.d("a","BSSID:"+scanResult[i].BSSID);
            Log.d("a","LEVEL:"+scanResult[i].level);
            Log.d("a","+++++++++++++++++++++++++++++++++");
        }
    }

    private void sort(List<ScanResult> scanResult){
        for(ScanResult result : scanResult){
            if(sort[0] == null || sort[0].level < result.level){
                sort[0] = result;
            }else if(sort[1] == null || sort[1].level < result.level){
                sort[1] = result;
            }else if(sort[2] == null || sort[2].level < result.level){
                sort[2] = result;
            }
        }
    }

}
