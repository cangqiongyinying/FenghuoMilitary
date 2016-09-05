package com.jiangxin.fenghuomilitary.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiangxin.fenghuomilitary.R;

/**
 * A simple {@link Fragment} subclass.
 * 推荐界面
 */
public class RecommendFragment extends BaseFragment {


    public RecommendFragment() {
        // Required empty public constructor
    }

    public static RecommendFragment newInstance(){
        return new RecommendFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

}
