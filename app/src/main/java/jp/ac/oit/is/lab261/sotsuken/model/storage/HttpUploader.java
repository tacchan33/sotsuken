package jp.ac.oit.is.lab261.sotsuken.model.storage;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

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
public class HttpUploader extends AsyncTask<String, Integer, Integer> {

    public static final String TEST = "TEST";
    public static final String UPLOAD = "UPLOAD";


    /* 接続必要情報 */
    private String host = "";//アプリケーションサーバ
    private String user = "";//ユーザ名
    private String password = "";//パスワード
    private Integer timeout = 5000;

    public HttpUploader(@Nullable String host, @Nullable String user, @Nullable String password, @Nullable Integer interval){
        this.host = host;
        this.user = user;
        this.password = password;
        this.timeout = interval/2;
    }


    /* HTTPステータスコード */
    private static Integer httpCode = 0;
    private static void setHttpCode(Integer code){ httpCode = code; }
    public static Integer getHttpCode(){ return httpCode; }

    /* アップロード情報 */
    private String macaddress = null;//送信元macアドレス
    private String[] bssid = new String[3];//ビーコンAPのBSSID
    private Integer[] level = new Integer[3];//ビーコンAPの電波強度
    public void setMACAddress(String mac){ this.macaddress = mac; }
    public void setBSSID(Integer index,String bssid){ this.bssid[index] = bssid; }
    public void setLEVEL(Integer index,Integer level){ this.level[index] = level; }


        super.onPreExecute();
    HttpURLConnection httpURLConnection = null;//コネクション
    URL url;//URL


    // 非同期処理
    @Override
    protected Integer doInBackground(@Nullable String... params) {
        try {
            url = new URL(host);// URL設定
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(true);//リダイレクト
            httpURLConnection.setReadTimeout(10000);//読み取り時間制限
            httpURLConnection.setConnectTimeout(timeout);//接続時間制限
            httpURLConnection.setUseCaches(false);//キャッシュを許可する
            httpURLConnection.setChunkedStreamingMode(0);
            httpURLConnection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
            httpURLConnection.setRequestProperty("User-Agent", "Android");
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");

            if ( params[0].equals(HttpUploader.TEST) ) {//テスト接続
                Log.d("HttpUploader","TEST");
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();//接続
            } else if ( params[0].equals(HttpUploader.UPLOAD) ) {//データアップロード
                Log.d("HttpUploader","UPLOAD");
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);//リクエストのbody送信を許可する
                httpURLConnection.setDoInput(true);//レスポンスのbody送信を許可する
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

    protected void onProgressUpdate(Integer... progress){
        super.onProgressUpdate(progress);
    }

}

