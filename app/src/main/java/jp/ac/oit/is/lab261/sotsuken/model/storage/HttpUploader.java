package jp.ac.oit.is.lab261.sotsuken.model.storage;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUploader extends AsyncTask<String, Void, String> {
    private String host = null;//アプリケーションサーバ
    private String user = null;//学籍番号
    private String password = null;//学籍番号パスワード
    private String macaddress = null;//送信元macアドレス
    private String[] bssid = new String[3];
    private Integer[] level = new Integer[3];


    public HttpUploader(@NonNull String host,@NonNull String user,@NonNull String password){
        this.host = host;
        this.user = user;
        this.password = password;
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
            Log.d("aa","ここまで");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");//リクエストメソッド設定
            httpURLConnection.setInstanceFollowRedirects(false);//リダイレクト無効
            httpURLConnection.setDoOutput(true);// データを書き込む
            httpURLConnection.setReadTimeout(10000);//読み取り時間制限
            httpURLConnection.setConnectTimeout(5000);//接続時間制限
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

