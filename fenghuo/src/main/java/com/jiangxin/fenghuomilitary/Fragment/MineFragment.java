package com.jiangxin.fenghuomilitary.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiangxin.fenghuomilitary.R;

/**
 * A simple {@link Fragment} subclass.
 * 我的界面
 */
public class MineFragment extends BaseFragment {


    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance(){
        return new MineFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

}
