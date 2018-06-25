package jp.ac.oit.is.lab261.sotsuken.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jp.ac.oit.is.lab261.sotsuken.R;
import jp.ac.oit.is.lab261.sotsuken.model.storage.SettingImport;

public class SettingActivity extends AppCompatActivity {

    EditText host,user,password;
    Button commit;

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
        commit = (Button)findViewById(R.id.commit);
        host.setText(setting.getHost());
        user.setText(setting.getUser());
        password.setText(setting.getPassword());

        commit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setting.setHost(host.getText().toString());
                setting.setUser(user.getText().toString());
                setting.setPassword(password.getText().toString());
                if( setting.commit() ){
                    Toast.makeText(SettingActivity.this,  "保存しました",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SettingActivity.this,  "保存できませんでした",Toast.LENGTH_SHORT).show();
                }
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
