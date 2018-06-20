package jp.ac.oit.is.lab261.sotsuken;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class DeveloperActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer_activity);//レイアウト適用

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle( getString(R.string.menu_developer) );

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
