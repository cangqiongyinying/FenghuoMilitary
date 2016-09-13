package phone1000.com.firemilitary.Activity.MineLogin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import phone1000.com.firemilitary.R;

public class UserInfoActivity extends AppCompatActivity {
    private TextView backIcon;
    private TextView userNickName;
    private Button unLogin;
    private SharedPreferences mSp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        mSp=getSharedPreferences("users", Context.MODE_WORLD_WRITEABLE);
        backIcon= (TextView) findViewById(R.id.user_info_back);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userNickName= (TextView) findViewById(R.id.user_info_nickname);
        userNickName.setText(mSp.getString("nickname",""));
        unLogin= (Button) findViewById(R.id.user_info_unlogin);
        unLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=mSp.edit();
                editor.putBoolean("isLogin",false);
                editor.commit();
                finish();
            }
        });
    }
}
