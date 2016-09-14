package phone1000.com.firemilitary.Activity.MineLogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import phone1000.com.firemilitary.MainActivity;
import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.Utils.DBUtils;
import phone1000.com.firemilitary.dao.User;


public class LoginActivity extends AppCompatActivity {

    private EditText accountEt;
    private EditText passwordEt;
    private TextView register;
    private TextView backIcon;
    private Button loginCommit;
    private boolean loginSuccess;
    private SharedPreferences mSp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        mSp=getSharedPreferences("users",Context.MODE_WORLD_WRITEABLE);
        initView();
    }

    private void initView() {
        accountEt= (EditText) findViewById(R.id.login_user_name);
        passwordEt= (EditText) findViewById(R.id.login_user_pass);
        register= (TextView) findViewById(R.id.login_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        backIcon= (TextView) findViewById(R.id.login_back);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loginCommit= (Button) findViewById(R.id.login_btn);
        loginCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        String account=accountEt.getText().toString();
        String password=passwordEt.getText().toString();
        for (User us: DBUtils.getDao(this).loadAll()){
            if (account.equals(us.getAccount())&&password.equals(us.getPassword())){
                Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
                loginSuccess=true;
                SharedPreferences.Editor editor=mSp.edit();
                editor.putBoolean("isLogin",loginSuccess);
                editor.putString("account",account);
                editor.putString("password",password);
                editor.commit();
                passwordEt.setText("");
                break;
            }else {
                loginSuccess=false;
            }
        }
        if (!loginSuccess){
            Toast.makeText(this,"账号或密码错误，请检查",Toast.LENGTH_SHORT).show();
        }
    }

}
