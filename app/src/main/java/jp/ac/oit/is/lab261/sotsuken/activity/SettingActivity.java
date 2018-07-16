package jp.ac.oit.is.lab261.sotsuken.activity;

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

    EditText host,user,password,interval;
    Button commit,connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);//レイアウト適用

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle( getString(R.string.menu_setting) );

        final SettingImport setting = new SettingImport(getApplicationContext());

        host = (EditText)findViewById(R.id.host);
        user = (EditText)findViewById(R.id.user);
        password = (EditText)findViewById(R.id.password);
        interval = (EditText)findViewById(R.id.interval);
        commit = (Button)findViewById(R.id.commit);
        connect = (Button)findViewById(R.id.connect);

        host.setText( setting.getHost() );
        user.setText( setting.getUser() );
        password.setText( setting.getPassword() );
        interval.setText( String.valueOf(setting.getInterval()) );

        /* 保存ボタンイベント */
        commit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setting.setHost(host.getText().toString());
                setting.setUser(user.getText().toString());
                setting.setPassword(password.getText().toString());
                setting.setInterval( Integer.valueOf(interval.getText().toString()) );
                setting.setConnection(false);
                Toast.makeText(SettingActivity.this,  "保存しました",Toast.LENGTH_SHORT).show();
            }
        });

        /* 接続ボタンイベント */
        connect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                HttpUploader httpUploader = new HttpUploader(setting.getHost(),setting.getUser(),setting.getPassword());
                httpUploader.setTimeout(setting.getInterval()/2);
                if( true ){//接続チェック
                    Toast.makeText(SettingActivity.this,  "接続できました",Toast.LENGTH_SHORT).show();
                    setting.setConnection(true);
                }else{
                    Toast.makeText(SettingActivity.this,  "接続できませんでした",Toast.LENGTH_SHORT).show();
                    setting.setConnection(false);
                }
                setting.commit();
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
