package phone1000.com.firemilitary.Activity.MineLogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.Utils.DataCleanUtils;


public class SetActivity extends AppCompatActivity {
    private RelativeLayout clearCache;
    private TextView cacheSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initView();
    }

    private void initView() {
        clearCache= (RelativeLayout) findViewById(R.id.clear_cache);
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataCleanUtils.clearAllCache(SetActivity.this);
                try {
                    cacheSize.setText(DataCleanUtils.getTotalCacheSize(SetActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        cacheSize= (TextView) findViewById(R.id.system_cache_show);
        try {
            cacheSize.setText(DataCleanUtils.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
