package com.jiangxin.fenghuomilitary.Activity.RingsInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jiangxin.fenghuomilitary.Activity.MainActivity;
import com.jiangxin.fenghuomilitary.Bean.RingsInfoBean;
import com.jiangxin.fenghuomilitary.R;
import com.liang.OkHttpLibrary.IOKCallBack;
import com.liang.OkHttpLibrary.OkHttpUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RingsInfoActivity extends AppCompatActivity implements IOKCallBack{
    public static final String HTTP_CONTENT_URL="/api1.3/topic.php?code=detail&tid=";
    private String tid;
    private ListView infoListView;
    private View listHeadView;
    private View listFootView;
    private TextView backIcon;
    private TextView moreCheck;
    private EditText inputComent;
    private ImageView zan;
    private ImageView share;
    private CircleImageView userFace;
    private TextView userName;
    private TextView userLv;
    private TextView dateLine;
    private Button guanzhuBtn;
    private TextView infoTitle;
    private TextView infoContent;
    private RingsInfoBean ringsInfoBean;
    private List<RingsInfoBean.DataBean.ContentBean> contentBeanList=new ArrayList<>();
    private List<String> imgList=new ArrayList<>();
    private RingsInfoImgAdapter ringsInfoImgAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rings_info);
        getIntentDatas();
        initView();
        loadHttp();
    }


    private void initView() {
        infoListView= (ListView) findViewById(R.id.rings_info_img_list);
        listHeadView=LayoutInflater.from(this).inflate(R.layout.rings_info_head_view,null);
        listFootView=LayoutInflater.from(this).inflate(R.layout.rings_info_foot_view,null);
        addEmptyView();
        infoListView.addHeaderView(listHeadView);
        ringsInfoImgAdapter=new RingsInfoImgAdapter();
        infoListView.setAdapter(ringsInfoImgAdapter);
    }

    private void loadHttp() {
        OkHttpUtils.newInstance().start(MainActivity.HTTP_HEAD+HTTP_CONTENT_URL+tid).callback(this);
    }

    private void getIntentDatas() {
        Intent intent=this.getIntent();
        tid=intent.getStringExtra("tid");
    }

    private void addEmptyView(){
        LinearLayout.LayoutParams layoutParams=
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity= Gravity.CENTER;
        ProgressBar progressBar =new ProgressBar(this);
        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_loading));
        progressBar.setLayoutParams(layoutParams);
        ((ViewGroup)infoListView.getParent()).addView(progressBar);
        infoListView.setEmptyView(progressBar);
    }
    @Override
    public void success(String result) {
        Gson gson=new Gson();
        ringsInfoBean=gson.fromJson(result,RingsInfoBean.class);
        if (MainActivity.netConnect){
            contentBeanList=ringsInfoBean.getData().getContent();
            updateHeadView();
            updateFootView();
            loadDatas();
        }
        ringsInfoImgAdapter.notifyDataSetChanged();
    }

    private void updateHeadView() {
        userFace= (CircleImageView) listHeadView.findViewById(R.id.info_user_face_icon);
        userName= (TextView) listHeadView.findViewById(R.id.rings_info_user_name);
        userLv= (TextView) listHeadView.findViewById(R.id.rings_info_user_lv);
        dateLine= (TextView) listHeadView.findViewById(R.id.rings_info_date_line);
        infoTitle= (TextView) listHeadView.findViewById(R.id.rings_info_title);
        infoContent= (TextView) listHeadView.findViewById(R.id.rings_info_content);

    }

    private void updateFootView() {

    }
    private void loadDatas() {
        for (RingsInfoBean.DataBean.ContentBean i:contentBeanList){
            if (i.getType().equals("img")){
                imgList.add(i.getSrc());
            }else if (i.getType().equals("txt")){
                infoContent.setText(i.getContent());
            }
        }
        Picasso.with(this).load(ringsInfoBean.getData().getFace()).into(userFace);
        userName.setText(ringsInfoBean.getData().getNickname());
        userLv.setText(ringsInfoBean.getData().getRole_name());
        dateLine.setText(ringsInfoBean.getData().getDateline());
        infoTitle.setText(ringsInfoBean.getData().getTitle());
    }

    class RingsInfoImgAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return imgList==null?0:imgList.size();
        }

        @Override
        public Object getItem(int i) {
            return imgList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view==null){
                view= LayoutInflater.from(RingsInfoActivity.this).inflate(R.layout.rings_info_list_item,viewGroup,false);
                viewHolder=new ViewHolder(view);
            }else {
                viewHolder= (ViewHolder) view.getTag();
            }
            Picasso.with(RingsInfoActivity.this).load(imgList.get(i)).into(viewHolder.imageView);
            return view;
        }

        private class ViewHolder {
            public ImageView imageView;
            public ViewHolder(View view){
                view.setTag(this);
                imageView= (ImageView) view.findViewById(R.id.rings_info_list_img);
            }
        }
    }
}
