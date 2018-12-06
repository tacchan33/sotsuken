package jp.ac.oit.is.lab261.sotsuken.model.network;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import android.util.Log;

import java.util.List;

public class WifiScanner {

    private WifiManager wifiManager;

    public static final Integer RANK = 3;
    private ScanResult sortResult[] = new ScanResult[RANK];



    public WifiScanner(Context context){
        wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
    }


    public void WifiScan(){
        /* initialize */
        for(int i=0;i<RANK;i++){
            sortResult[i] = null;
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
        sort(scanResults);
    }

    private void sort(List<ScanResult> scanResult){
        for(ScanResult result : scanResult){
            if(sortResult[0] == null || sortResult[0].level < result.level){
                sortResult[0] = result;
            }else if(sortResult[1] == null || sortResult[1].level < result.level){
                sortResult[1] = result;
            }else if(sortResult[2] == null || sortResult[2].level < result.level){
                sortResult[2] = result;
            }
        }
    }

    public String getBSSID(Integer index){
        if( sortResult[index] == null){
            return "";
        }else {
            return sortResult[index].BSSID;
        }
    }

    public String getESSID(Integer index){
        if( sortResult[index] == null){
            return "";
        }else {
            return sortResult[index].SSID;
        }
    }

    public Integer getLEVEL(Integer index){
        if( sortResult[index] == null){
            return -128;
        }else {
            return sortResult[index].level;
        }
    }

}
