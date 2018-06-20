package jp.ac.oit.is.lab261.sotsuken.network.entity;

import android.support.annotation.NonNull;

public class Device {
    private String macAddress;//デバイスのMACアドレス

    public void setMacAddress(@NonNull String macAddress){
        this.macAddress = macAddress;
    }

    public String getMacAddress(){
        return this.macAddress;
    }
}
