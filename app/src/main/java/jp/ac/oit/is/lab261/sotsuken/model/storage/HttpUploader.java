package jp.ac.oit.is.lab261.sotsuken.model.storage;

import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import jp.ac.oit.is.lab261.sotsuken.model.network.WifiScanner;

/*
型1 … Activityからスレッド処理へ渡したい変数の型
 *          ※ Activityから呼び出すexecute()の引数の型
 *          ※ doInBackground()の引数の型
 *
 *   型2 … 進捗度合を表示する時に利用したい型
 *          ※ onProgressUpdate()の引数の型
 *
 *   型3 … バックグラウンド処理完了時に受け取る型
 *          ※ doInBackground()の戻り値の型
 *          ※ onPostExecute()の引数の型
 *
 *   ※ それぞれ不要な場合は、Voidを設定すれば良い
 */


public class HttpUploader extends AsyncTask<String, Void, Void> {

    public static final String TEST = "TEST";
    public static final String UPLOAD = "UPLOAD";

    public HttpUploader(@NonNull String host,@NonNull String user,@NonNull String password,@Nullable Integer timeout){
        setHost(host);
        setUser(user);
        setPassword(password);
        setTimeout(timeout);
    }


    /* 接続必要情報 */
    private String host = "";//アプリケーションサーバ
    private String user = "";//ユーザ名
    private String password = "";//パスワード
    private Integer timeout = 5000;
    public void setHost(@Nullable String host){ this.host = host; }
    public void setUser(@Nullable String user){ this.user = user; }
    public void setPassword(@Nullable String password){ this.password = password; }
    public void setTimeout(@NonNull Integer timeout){ this.timeout = timeout; }

    /* HTTPステータスコード */
    private Integer httpCode = 0;
    private void setHttpCode(Integer code){ this.httpCode = code; }
    public Integer getHttpCode(){ return this.httpCode; }

    /* アップロード情報 */
    private String macaddress = null;//送信元macアドレス
    private String[] bssid = new String[3];//ビーコンAPのBSSID
    private Integer[] level = new Integer[3];//ビーコンAPの電波強度
    public void setMACAddress(String mac){ this.macaddress = mac; }
    public void setBSSID(Integer index,String bssid){ this.bssid[index] = bssid; }
    public void setLEVEL(Integer index,Integer level){ this.level[index] = level; }


    // 非同期処理
    @Override
    protected Void doInBackground(@Nullable String... params) {
        HttpURLConnection httpURLConnection = null;//コネクション
        URL url;//URL

        try {
            url = new URL(host);// URL設定
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");//リクエストメソッド設定
            httpURLConnection.setInstanceFollowRedirects(false);//リダイレクト無効
            httpURLConnection.setDoOutput(true);// データを書き込む
            httpURLConnection.setReadTimeout(10000);//読み取り時間制限
            httpURLConnection.setConnectTimeout(timeout);//接続時間制限

            if ( params[0].equals(HttpUploader.TEST) ) {//テスト接続
                httpURLConnection.connect();//接続
            } else if ( params[0].equals(HttpUploader.UPLOAD) ) {//データアップロード
                httpURLConnection.connect();//接続
                OutputStream out = null;//出力
                String data =
                        "id="+user+"&"+
                                "password="+password+"&"+
                                "bssid1="+bssid[0]+"&"+
                                "level1="+level[0]+"&"+
                                "bssid2="+bssid[1]+"&"+
                                "level2="+level[1]+"&"+
                                "bssid3="+bssid[2]+"&"+
                                "level3="+level[2];
                // POSTデータ送信処理
                try{
                    out = httpURLConnection.getOutputStream();
                    out.write( data.getBytes("UTF-8") );
                    out.flush();
                }catch(IOException e) {
                    /* POST送信エラー */
                    e.printStackTrace();
                }finally{
                    /* パケット破棄 */
                    if (out != null) {
                        out.close();
                    }
                }
            }

            setHttpCode( httpURLConnection.getResponseCode() );

        }catch (IOException e){
            e.printStackTrace();
        }finally{
            /* コネクション破棄 */
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return null;
    }

}

