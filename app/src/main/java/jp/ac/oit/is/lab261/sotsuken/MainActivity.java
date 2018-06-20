package jp.ac.oit.is.lab261.sotsuken;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);//レイアウト適用

        /* ログ出力 */
        Log.d("debug", "MainActivity.onCreate()");

        /* ControlServiceが動いてなければ、起動する */
        if( !ControlService.isRunning() ) {
            startService( new Intent(getApplicationContext(), ControlService.class) );
        }


        CompoundButton toggleService = (CompoundButton)findViewById(R.id.toggle_service);
        if( ScanWifiService.isRunning() ){
            toggleService.setChecked(true);
        }else{
            toggleService.setChecked(false);
        }
        toggleService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String msg = "";
                Intent intent = new Intent(getApplicationContext(),ScanWifiService.class);
                if(isChecked == true) {
                    startService(intent);
                    msg = "開始!";
                }else{
                    stopService(intent);
                    msg = "停止";
                }
                Toast.makeText(MainActivity.this,  "サービスを"+msg+"しました",Toast.LENGTH_SHORT).show();

            }
        });


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
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                return true;
            case R.id.developer:
                startActivity(new Intent(MainActivity.this,DeveloperActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
