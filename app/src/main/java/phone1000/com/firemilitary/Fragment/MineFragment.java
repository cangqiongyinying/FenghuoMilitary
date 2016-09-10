package phone1000.com.firemilitary.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import phone1000.com.firemilitary.Activity.MineLogin.LoginActivity;
import phone1000.com.firemilitary.Activity.MineLogin.MineCollectionActivity;
import phone1000.com.firemilitary.Activity.MineLogin.MineMessageActivity;
import phone1000.com.firemilitary.Activity.MineLogin.SetActivity;
import phone1000.com.firemilitary.R;


/**
 * A simple {@link Fragment} subclass.
 * 我的界面
 */
public class MineFragment extends BaseFragment implements View.OnClickListener{

    private Context mContext;
    private RelativeLayout mLogin;
    private LinearLayout afterLogin;
    private LinearLayout afterDiscuss;
    private LinearLayout afterAttention;
    private LinearLayout afterFans;
    private Button headquarters;
    private Button mineCollection;
    private Button mineMessage;
    private Button set;
    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance(){
        return new MineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        setOnClick();
        return view;
    }

    private void setOnClick() {
        mLogin.setOnClickListener(this);
        afterDiscuss.setOnClickListener(this);
        afterAttention.setOnClickListener(this);
        afterFans.setOnClickListener(this);
        headquarters.setOnClickListener(this);
        mineCollection.setOnClickListener(this);
        mineMessage.setOnClickListener(this);
        set.setOnClickListener(this);
    }

    private void initView(View view) {
        mLogin= (RelativeLayout) view.findViewById(R.id.user_message_layout);
        afterLogin= (LinearLayout) view.findViewById(R.id.after_login_user_info);
//        if (true){
//            afterLogin.setVisibility(View.VISIBLE);
//        }
        afterDiscuss= (LinearLayout) view.findViewById(R.id.after_login_discuss);
        afterAttention= (LinearLayout) view.findViewById(R.id.after_login_attention);
        afterFans= (LinearLayout) view.findViewById(R.id.after_login_fans);
        headquarters= (Button) view.findViewById(R.id.login_headquarters);
        mineCollection= (Button) view.findViewById(R.id.login_collection);
        mineMessage= (Button) view.findViewById(R.id.login_mine_message);
        set= (Button) view.findViewById(R.id.login_mine_set);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()){
            case R.id.user_message_layout:
                if (true){
                intent.setClass(mContext, LoginActivity.class);
                }else {
                }
                break;
            case R.id.after_login_discuss:
                break;
            case R.id.after_login_attention:
                break;
            case R.id.after_login_fans:
                break;
            case R.id.login_headquarters:
                if (true){
                    Toast.makeText(mContext,"您还没有登录，请先登录!",Toast.LENGTH_SHORT).show();
                    return;
                }
            case R.id.login_collection:
                intent.setClass(mContext, MineCollectionActivity.class);
                break;
            case R.id.login_mine_message:
                intent.setClass(mContext, MineMessageActivity.class);
                break;
            case R.id.login_mine_set:
                intent.setClass(mContext, SetActivity.class);
                break;
        }
        startActivity(intent);
    }
}
