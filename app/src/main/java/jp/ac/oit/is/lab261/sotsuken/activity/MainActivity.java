package jp.ac.oit.is.lab261.sotsuken.activity;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;

import jp.ac.oit.is.lab261.sotsuken.service.BackgroundService;
import jp.ac.oit.is.lab261.sotsuken.service.ControlService;
import jp.ac.oit.is.lab261.sotsuken.R;


public class MainActivity extends AppCompatActivity{

    CompoundButton toggleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);//レイアウト適用
        toggleService = (CompoundButton)findViewById(R.id.toggle_service);

        /* ControlServiceが動いてなければ、起動する */
        if( !ControlService.isRunning() ) {
            startService( new Intent(getApplicationContext(), ControlService.class) );
        }

        /* WifiScannerServiceが起動を判断してボタンを切り替える */
        if(BackgroundService.isRunning() ){//WifiScannerが有効時
            toggleService.setChecked(true);
        }else{//WifiScannerが無効時
            toggleService.setChecked(false);
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

        ControlPermission controlPermission = new ControlPermission();
        controlPermission.setPermission(this);


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
                if( !toggleService.isChecked() ) {
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

}