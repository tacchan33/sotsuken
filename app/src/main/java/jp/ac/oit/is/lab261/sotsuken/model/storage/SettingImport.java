package jp.ac.oit.is.lab261.sotsuken.model.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static android.content.Context.MODE_PRIVATE;

public class SettingImport {
    private final String FILE = "setting";
    private final String HOST = "host";//データのキー
    private final String USER = "user";
    private final String PASSWORD = "password";
    private SharedPreferences sharedPreferences = null;

    private String host = "";
    private String user = "";
    private String password = "";

    public SettingImport(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        /* MODE_PRIVATE = 0 */
        context.getSharedPreferences(FILE,0);
    }

    public String getHost(){
        return sharedPreferences.getString(HOST,"(例)192.168.0.1");
    }

    public String getUser(){
        return sharedPreferences.getString(USER,"(例)username");
    }

    public String getPassword(){
        return sharedPreferences.getString(PASSWORD,"(例)password");
    }

    public boolean commit(){
        sharedPreferences.edit()
                .putString(HOST,host)
                .putString(USER,user)
                .putString(PASSWORD,password)
                .commit();
        return true;
    }

    public void setHost(String host){
        this.host = host;
    }

    public void setUser(String user){
        this.user = user;
    }

    public void setPassword(String password){
        this.password = password;
    }

}
