package jp.ac.oit.is.lab261.sotsuken.model.storage;

import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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
public class HttpUploader extends AsyncTask<String, Void, String> {

    private String host = null;//アプリケーションサーバ
    private String user = null;//学籍番号
    private String password = null;//学籍番号パスワード
    private Integer timeout = 5000;
    private String macaddress = null;//送信元macアドレス
    private String[] bssid = new String[3];
    private Integer[] level = new Integer[3];

    public HttpUploader(@NonNull String host,@NonNull String user,@NonNull String password){
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public void setTimeout(@NonNull Integer timeout){
        this.timeout = timeout;
    }

    /* コネクション確認 */
    public boolean availableConnection(){
        boolean result = false;

        HttpURLConnection httpURLConnection = null;//コネクション
        URL url = null;//URL
        try{
            url = new URL(host);// URL設定
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");//リクエストメソッド設定
            httpURLConnection.setInstanceFollowRedirects(false);//リダイレクト無効
            httpURLConnection.setDoOutput(true);// データを書き込む
            httpURLConnection.setReadTimeout(10000);//読み取り時間制限
            httpURLConnection.setConnectTimeout(timeout);//接続時間制限
            Log.d("HttpUploader","ここ");
            httpURLConnection.connect();//接続
            result = true;
        }catch(IOException e) {
            e.printStackTrace();
        }finally{
            /* コネクション破棄 */
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return result;
    }


    // 非同期処理
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection httpURLConnection = null;//コネクション
        URL url = null;//URL
        OutputStream out = null;//出力
        String result = null;//HTTPレスポンスコード
        String data =
                "id="+user+"&"+
                "password="+password+"&"+
                "bssid1="+bssid[0]+"&"+
                "level1="+level[0]+"&"+
                "bssid2="+bssid[1]+"&"+
                "level2="+level[1]+"&"+
                "bssid3="+bssid[2]+"&"+
                "level3="+level[2];


        try{
            url = new URL(host);// URL設定
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");//リクエストメソッド設定
            httpURLConnection.setInstanceFollowRedirects(false);//リダイレクト無効
            httpURLConnection.setDoOutput(true);// データを書き込む
            httpURLConnection.setReadTimeout(10000);//読み取り時間制限
            httpURLConnection.setConnectTimeout(timeout);//接続時間制限
            httpURLConnection.connect();//接続

            // POSTデータ送信処理
            try{
                out = httpURLConnection.getOutputStream();
                out.write( data.getBytes("UTF-8") );
                out.flush();
            }catch(IOException e) {
                /* POST送信エラー */
                e.printStackTrace();
                result = "HTTP_NG";
            }finally{
                /* パケット破棄 */
                if (out != null) {
                    out.close();
                }
            }

            /* HTTPレスポンスコード */
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = "HTTP_OK";
            } else{
                result = "HTTP_NG";
            }

        }catch(IOException e) {
            e.printStackTrace();
        }finally{
            /* コネクション破棄 */
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return result;
    }

    // 非同期処理が終了後、結果をメインスレッドに返す
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    public void setMACAddress(String mac){
        this.macaddress = mac;
    }
    public void setBSSID(Integer index,String bssid){
        this.bssid[index] = bssid;
    }
    public void setLEVEL(Integer index,Integer level){
        this.level[index] = level;
    }

}

