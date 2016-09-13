package phone1000.com.firemilitary.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import phone1000.com.firemilitary.Activity.MineLogin.LoginActivity;
import phone1000.com.firemilitary.Activity.MineLogin.MineCollectionActivity;
import phone1000.com.firemilitary.Activity.MineLogin.MineMessageActivity;
import phone1000.com.firemilitary.Activity.MineLogin.SetActivity;
import phone1000.com.firemilitary.Activity.MineLogin.UserInfoActivity;
import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.dao.DBUtils;
import phone1000.com.firemilitary.dao.User;


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
    private ImageView userImg;
    private TextView beforeLoginMsg;
    private TextView userName;
    private TextView userAbstract;
    private SharedPreferences mSp;
    private boolean isLogin;
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
        mSp=mContext.getSharedPreferences("users",Context.MODE_WORLD_WRITEABLE);
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

    @Override
    public void onResume() {
        super.onResume();
        showUserMessage();
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
        userImg= (ImageView) view.findViewById(R.id.user_img);
        beforeLoginMsg= (TextView) view.findViewById(R.id.before_login_message);
        userName= (TextView) view.findViewById(R.id.after_login_user_name);
        userAbstract= (TextView) view.findViewById(R.id.after_login_user_abstract);
        afterDiscuss= (LinearLayout) view.findViewById(R.id.after_login_discuss);
        afterAttention= (LinearLayout) view.findViewById(R.id.after_login_attention);
        afterFans= (LinearLayout) view.findViewById(R.id.after_login_fans);
        headquarters= (Button) view.findViewById(R.id.login_headquarters);
        mineCollection= (Button) view.findViewById(R.id.login_collection);
        mineMessage= (Button) view.findViewById(R.id.login_mine_message);
        set= (Button) view.findViewById(R.id.login_mine_set);
    }

    private void showUserMessage() {
        isLogin=mSp.getBoolean("isLogin",false);
        if (isLogin){
            userImg.setImageResource(R.drawable.avatar);
            afterLogin.setVisibility(View.VISIBLE);
            beforeLoginMsg.setVisibility(View.GONE);
            userName.setVisibility(View.VISIBLE);
            for (User us: DBUtils.getDao(mContext).loadAll()){
                if (mSp.getString("account","").equals(us.getAccount())){
                    SharedPreferences.Editor editor=mSp.edit();
                    editor.putString("nickname",us.getNickname());
                    editor.commit();
                    userName.setText(us.getNickname());
                }
            }
            userAbstract.setVisibility(View.VISIBLE);
            userAbstract.setText("暂无简介");
        }else {
            userImg.setImageResource(R.drawable.user_img98);
            afterLogin.setVisibility(View.GONE);
            beforeLoginMsg.setVisibility(View.VISIBLE);
            userName.setVisibility(View.GONE);
            userAbstract.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()){
            case R.id.user_message_layout:
                if (!isLogin){
                    intent.setClass(mContext, LoginActivity.class);
                }else {
                    intent.setClass(mContext, UserInfoActivity.class);
                }
                break;
            case R.id.after_login_discuss:
                return;
            case R.id.after_login_attention:
                return;
            case R.id.after_login_fans:
                return;
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
