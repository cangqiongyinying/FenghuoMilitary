package phone1000.com.firemilitary.Activity.MineLogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import phone1000.com.firemilitary.R;


public class MineCollectionActivity extends AppCompatActivity {
    private TextView backIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_collection);
        backIcon= (TextView) findViewById(R.id.collection_back);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
