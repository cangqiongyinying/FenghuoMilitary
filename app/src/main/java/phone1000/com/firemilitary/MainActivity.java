package phone1000.com.firemilitary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.liang.OkHttpLibrary.OkHttpUtils;

import phone1000.com.firemilitary.Fragment.CircleFragment;
import phone1000.com.firemilitary.Fragment.MineFragment;
import phone1000.com.firemilitary.Fragment.PlayerFragment;
import phone1000.com.firemilitary.Fragment.TuijianFragment;

public class MainActivity extends AppCompatActivity {
    public static final String HTTP_HEAD="http://if.fenghuo001.com";
    public static boolean netConnect;
    private RadioGroup radiogroup;
    private FragmentManager supportFragmentManager;
    private Fragment mCurrentShowFragment;
    private  TuijianFragment recommendFragment;
    private  PlayerFragment videoFragment;
    private  CircleFragment ringsFragment;
    private  MineFragment mineFragment;
    private boolean isExit;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit=false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        netConnect= OkHttpUtils.isNetworkConnected(this);
        //初始化视图
        initView();
        addFragment();
    }

    private void addFragment() {
        recommendFragment = TuijianFragment.newInstance();
        videoFragment = PlayerFragment.newInstance();
        ringsFragment = CircleFragment.newInstance();
        mineFragment = MineFragment.newInstance();
        ctrlFragment(videoFragment);
    }
    private void initView() {
        supportFragmentManager = getSupportFragmentManager();
        radiogroup=(RadioGroup)findViewById(R.id.mainactivity_radiogroup);
        addFragment();
        pageChanged();

    }

    private void pageChanged() {
        radiogroup.check(R.id.mainacitvity_radiobtn1);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.mainacitvity_radiobtn:
                        ctrlFragment(recommendFragment);
                        break;
                    case R.id.mainacitvity_radiobtn1:
                        ctrlFragment(videoFragment);
                        break;
                    case R.id.mainacitvity_radiobtn2:
                        ctrlFragment(ringsFragment);
                        break;
                    case R.id.mainacitvity_radiobtn3:
                        ctrlFragment(mineFragment);
                        break;
                }
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void exit() {
        if (!isExit){
            isExit=true;
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(0,3000);
        }else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    private void ctrlFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if (mCurrentShowFragment != null && mCurrentShowFragment.isAdded()) {
            fragmentTransaction.hide(mCurrentShowFragment);
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.mainactivity_framelayout,fragment);
        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.commit();

        mCurrentShowFragment = fragment;
    }
}
