package com.jiangxin.fenghuomilitary.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.jiangxin.fenghuomilitary.Fragment.MineFragment;
import com.jiangxin.fenghuomilitary.Fragment.RecommendFragment;
import com.jiangxin.fenghuomilitary.Fragment.RingsFragment;
import com.jiangxin.fenghuomilitary.Fragment.VideoFragment;
import com.jiangxin.fenghuomilitary.R;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mSupportFragmentManager;
    private  RecommendFragment recommendFragment;
    private  VideoFragment videoFragment;
    private  RingsFragment ringsFragment;
    private  MineFragment mineFragment;
    private Fragment mCurrentShowFragment;
    private RadioGroup pageChangeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        pageChangeBtn= (RadioGroup) findViewById(R.id.change_fragment_btnGroup);
        mSupportFragmentManager= getSupportFragmentManager();
        addFragment();
        pageChanged();
    }

    private void addFragment() {
        recommendFragment = RecommendFragment.newInstance();
        videoFragment = VideoFragment.newInstance();
        ringsFragment = RingsFragment.newInstance();
        mineFragment = MineFragment.newInstance();
        ctrlFragment(recommendFragment);
    }

    private void pageChanged(){
        pageChangeBtn.check(R.id.main_recommend_btn);
        pageChangeBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.main_recommend_btn :
                        ctrlFragment(recommendFragment);
                        break;
                    case R.id.main_video_btn:
                        ctrlFragment(videoFragment);
                        break;
                    case R.id.main_rings_btn:
                        ctrlFragment(ringsFragment);
                        break;
                    case R.id.main_mine_btn:
                        ctrlFragment(mineFragment);
                        break;
                }
            }
        });
    }

    private void ctrlFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        if (mCurrentShowFragment != null && mCurrentShowFragment.isAdded()) {
            fragmentTransaction.hide(mCurrentShowFragment);
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.main_show_fragment_layout,fragment);
        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.commit();

        mCurrentShowFragment = fragment;
    }
}
