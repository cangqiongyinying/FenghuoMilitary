package phone1000.com.firemilitary.Activity.MineLogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.dao.DBUtils;
import phone1000.com.firemilitary.dao.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText accountEt;
    private EditText nicknameEt;
    private EditText passwordEt;
    private EditText repeatPasswordEt;
    private Button commitBtn;
    private TextView backIcon;
    private User user;
    private List<User> userList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user = new User();
        loadDbDatas();
        initView();
    }

    private void initView() {
        accountEt= (EditText) findViewById(R.id.register_account);
        nicknameEt= (EditText) findViewById(R.id.register_nickname);
        passwordEt= (EditText) findViewById(R.id.register_password);
        repeatPasswordEt= (EditText) findViewById(R.id.register_repeat_password);
        commitBtn= (Button) findViewById(R.id.register_commit);
        backIcon= (TextView) findViewById(R.id.register_back);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void loadDbDatas() {
        List<User> users=DBUtils.getDao(this).loadAll();
        userList.addAll(users);
    }

    private void registerUser() {
        String account=accountEt.getText().toString();
        String nickname=nicknameEt.getText().toString();
        String password=passwordEt.getText().toString();
        String repeatPassword=repeatPasswordEt.getText().toString();
        for(User us:userList){
            if (account.equals(us.getAccount())){
                Toast.makeText(this,"此账号已被注册,请重新输入",Toast.LENGTH_SHORT).show();
                accountEt.setText("");
                nicknameEt.setText("");
                passwordEt.setText("");
                repeatPasswordEt.setText("");
                return;
            }
        }
        if (account.equals("")||nickname.equals("")||password.equals("")||repeatPassword.equals("")){
            Toast.makeText(this,"请输入完整信息",Toast.LENGTH_SHORT).show();
            return;
        }else if (!password.equals(repeatPassword)) {
            Toast.makeText(this,"两次输入密码不同,请重新输入",Toast.LENGTH_SHORT).show();
            passwordEt.setText("");
            repeatPasswordEt.setText("");
            return;
        }else {
            user.setAccount(account);
            user.setNickname(nickname);
            user.setPassword(password);
            DBUtils.getDao(this).insert(user);
            Toast.makeText(this,"注册成功，跳转登陆界面",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
