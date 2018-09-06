package jp.ac.oit.is.lab261.sotsuken.activity;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;

import java.net.HttpURLConnection;

import jp.ac.oit.is.lab261.sotsuken.model.storage.HttpUploader;
import jp.ac.oit.is.lab261.sotsuken.service.BackgroundService;
import jp.ac.oit.is.lab261.sotsuken.service.ControlService;
import jp.ac.oit.is.lab261.sotsuken.R;


public class MainActivity extends AppCompatActivity{

    CompoundButton toggleService;

    /* --------------------------------------------------------- */
    /* ------------------Activityライフサイクル----------------- */
    /* --------------------------------------------------------- */
    @Override/* アクティビティが初めて作られた時 */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);//レイアウト適用

        ControlPermission controlPermission = new ControlPermission();
        controlPermission.setPermission(this);

        toggleService = (CompoundButton)findViewById(R.id.toggle_service);

        /* ControlServiceが動いてなければ、起動する */
        if( !ControlService.isRunning() ) {
            startService( new Intent(getApplicationContext(), ControlService.class) );
        }

        toggleService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(getApplicationContext(),BackgroundService.class);
                if(isChecked == true) {
                    startService(intent);
                }else{
                    stopService(intent);
                }
            }
        });
    }

    @Override/* アクティビティ開始された時 */
    protected void onStart(){
        super.onStart();
    }

    @Override/* アクティビティ表示された時とonPause()終了直後 */
    protected void onResume(){
        super.onResume();
        reload();
    }

    @Override/* 別アクティビティが表示されるとき */
    protected  void onPause(){
        super.onPause();
    }

    @Override/* アクティビティ非表示された時 */
    protected void onStop(){
        super.onStop();
    }

    @Override/* onStop()終了直後からonStart() */
    protected void onRestart(){
        super.onRestart();
    }

    @Override/* アクティビティがメモリから解放される時 */
    protected void onDestroy(){
        super.onDestroy();
    }

    /* --------------------------------------------------------- */
    /* --------------------オプションメニュー------------------- */
    /* --------------------------------------------------------- */
    @Override//生成メソッド
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override//選択メソッド
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.setting:
                if( !BackgroundService.isRunning() ) {
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                }else{
                    Toast.makeText(MainActivity.this,  "サービスを停止して下さい",Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.developer:
                startActivity(new Intent(MainActivity.this,DeveloperActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    //再描画
    private void reload(){
        /* WifiScannerServiceが起動を判断してボタンを切り替える */
        if(BackgroundService.isRunning() ){//WifiScannerが有効時
            toggleService.setChecked(true);
        }else{//WifiScannerが無効時
            toggleService.setChecked(false);
        }
        /* アプリケーションサーバ接続確認でボタンを有効無効切り替える */
        Toast.makeText(MainActivity.this,  "HTTP/"+HttpUploader.getHttpCode(),Toast.LENGTH_SHORT).show();
        if( HttpURLConnection.HTTP_OK <= HttpUploader.getHttpCode() && HttpUploader.getHttpCode() < HttpURLConnection.HTTP_MOVED_PERM ){
            toggleService.setEnabled(true);
        }else{
            toggleService.setEnabled(false);
            if( BackgroundService.isRunning() ) {
                Intent intent = new Intent(getApplicationContext(), BackgroundService.class);
                stopService(intent);
            }
            Toast.makeText(MainActivity.this,  "アプリケーションサーバ設定に誤りがあります",Toast.LENGTH_SHORT).show();
        }

    }

}