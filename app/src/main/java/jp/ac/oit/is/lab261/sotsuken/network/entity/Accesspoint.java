package jp.ac.oit.is.lab261.sotsuken.network.entity;

import android.support.annotation.NonNull;

public class Accesspoint {
    private String bssid;//APのMACアドレス
    private Integer rssi;//APとの電波強度

    public void setBssid(@NonNull String bssid){
        this.bssid = bssid;
    }
    public String getBssid(){
        return this.bssid;
    }

    public void setRssi(@NonNull Integer rssi){
        this.rssi = rssi;
    }
    public Integer getRssi(){
        return this.rssi;
    }

}
