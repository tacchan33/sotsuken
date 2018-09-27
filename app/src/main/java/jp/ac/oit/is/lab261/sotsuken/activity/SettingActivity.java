package jp.ac.oit.is.lab261.sotsuken.activity;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jp.ac.oit.is.lab261.sotsuken.R;
import jp.ac.oit.is.lab261.sotsuken.model.storage.HttpUploader;
import jp.ac.oit.is.lab261.sotsuken.model.storage.SettingImport;

public class SettingActivity extends AppCompatActivity {

    WifiManager wifiManager = null;

    EditText host,email,password,token,interval;
    Button commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);//レイアウト適用

        final SettingImport setting = new SettingImport(getApplicationContext());

        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle( getString(R.string.menu_setting) );

        host = (EditText)findViewById(R.id.host);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        token = (EditText)findViewById(R.id.token);
        interval = (EditText)findViewById(R.id.interval);
        commit = (Button)findViewById(R.id.commit);

        host.setText( setting.getHost() );
        email.setText( setting.getEmail() );
        password.setText( setting.getPassword() );
        token.setText( setting.getToken() );
        interval.setText( String.valueOf(setting.getInterval()) );

        /* 保存ボタンイベント */
        commit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setting.setHost(host.getText().toString());
                setting.setEmail(email.getText().toString());
                setting.setPassword(password.getText().toString());
                setting.setToken( token.getText().toString());
                setting.setInterval( Integer.valueOf(interval.getText().toString()) );

                HttpUploader httpUploader = new HttpUploader(setting.getHost(),setting.getEmail(),setting.getPassword(),setting.getToken(),setting.getInterval());
                httpUploader.execute(HttpUploader.TEST);//通信

                Toast.makeText(SettingActivity.this,  "保存しました",Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }


    /* --------------------------------------------------------- */
    /* --------------------オプションメニュー------------------- */
    /* --------------------------------------------------------- */
    @Override//選択メソッド
    public boolean onOptionsItemSelected(MenuItem item){
        finishAndRemoveTask();
        return true;
    }

}
