package jp.ac.oit.is.lab261.sotsuken.model.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static android.content.Context.MODE_PRIVATE;

public class SettingImport {
    private final String FILE = "setting";

    private final String HOST = "host";//データのキー
    private final String EMAIL = "email";
    private final String PASSWORD = "password";
    private final String TOKEN = "token";
    private  final String INTERVAL = "interval";

    private SharedPreferences sharedPreferences;

    public SettingImport(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        /* MODE_PRIVATE = 0 */
        context.getSharedPreferences(FILE,0);
    }

    public void setHost(String host){
        sharedPreferences.edit().putString(HOST,host).commit();
    }

    public void setEmail(String email){
        sharedPreferences.edit().putString(EMAIL,email).commit();
    }

    public void setPassword(String password){
        sharedPreferences.edit().putString(PASSWORD,password).commit();
    }

    public void setToken(Integer token) {
        sharedPreferences.edit().putInt(TOKEN,token).commit();
    }

    public void setInterval(Integer interval){
        /* 1000で1秒 */
        sharedPreferences.edit().putInt(INTERVAL,interval).commit();
    }

    public String getHost(){
        return sharedPreferences.getString(HOST,"(例)http://192.168.0.1/index.php");
    }

    public String getEmail(){
        return sharedPreferences.getString(EMAIL,"(例)sotsuken@oit.ac.jp");
    }

    public String getPassword(){
        return sharedPreferences.getString(PASSWORD,"(例)password");
    }

    public Integer getToken(){
        return sharedPreferences.getInt(TOKEN,0);
    }

    public Integer getInterval(){
        return sharedPreferences.getInt(INTERVAL,60000);
    }


}
