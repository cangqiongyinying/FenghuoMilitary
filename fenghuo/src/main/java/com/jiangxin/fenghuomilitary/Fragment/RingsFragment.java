package com.jiangxin.fenghuomilitary.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiangxin.fenghuomilitary.R;

/**
 * A simple {@link Fragment} subclass.
 * 圈子界面
 */
public class RingsFragment extends BaseFragment {


    public RingsFragment() {
        // Required empty public constructor
    }

    public static RingsFragment newInstance(){
        return new RingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rings, container, false);
    }

}
