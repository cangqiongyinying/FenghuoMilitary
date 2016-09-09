package com.jiangxin.fenghuomilitary.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiangxin.fenghuomilitary.Activity.MainActivity;
import com.jiangxin.fenghuomilitary.Activity.RingsInfo.RingsInfoActivity;
import com.jiangxin.fenghuomilitary.Bean.RingsBean;
import com.jiangxin.fenghuomilitary.R;
import com.liang.OkHttpLibrary.IOKCallBack;
import com.liang.OkHttpLibrary.OkHttpUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * 圈子界面
 */
public class RingsFragment extends BaseFragment implements IOKCallBack{

    public static final String HTTP_CONTEXT_URL="/api1.3/topic.php?code=quan_topic&qid=1059&page=";
    private int page=1;
    private LayoutInflater mInflater;
    private Context mContext;
    private PullToRefreshListView mRingsListView;
    private ListView listView;
    private View listHeadView;
    private ImageView headImageIcon;
    private ImageView headGuanZhuIcon;
    private TextView headUserNum;
    private TextView headRingsName;
    private TextView headHotComment;
    private TextView headCommentNum;
    private RingsBean ringsBean;
    private List<RingsBean.DataBean.ListBean.TopicListBean> topic_list =new ArrayList<>();
    private RingsListAdapter ringsListAdapter;


    public RingsFragment() {
        // Required empty public constructor
    }

    public static RingsFragment newInstance(){
        return new RingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater=inflater;
        View view=inflater.inflate(R.layout.fragment_rings, container, false);
        initView(view);
        httpLoad();
        return view;
    }

    private void httpLoad() {
        OkHttpUtils.newInstance().start(MainActivity.HTTP_HEAD+HTTP_CONTEXT_URL+page).callback(this);
    }

    private void initView(View view) {
        mRingsListView= (PullToRefreshListView) view.findViewById(R.id.rings_list_view);
        listHeadView=mInflater.inflate(R.layout.rings_fragment_header_view,null);
        listView=mRingsListView.getRefreshableView();
        addEmptyView();
        listView.addHeaderView(listHeadView);
        ringsListAdapter=new RingsListAdapter();
        mRingsListView.setAdapter(ringsListAdapter);
    }

    private void addEmptyView(){
        LinearLayout.LayoutParams layoutParams=
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity= Gravity.CENTER;
        ProgressBar progressBar =new ProgressBar(mContext);
        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_loading));
        progressBar.setIndeterminate(true);
        progressBar.setLayoutParams(layoutParams);
        ((ViewGroup)mRingsListView.getParent()).addView(progressBar);
        mRingsListView.setEmptyView(progressBar);
    }
    @Override
    public void success(String result) {
        Gson gson=new Gson();
        ringsBean=gson.fromJson(result,RingsBean.class);
        if (MainActivity.netConnect){
            topic_list.addAll(ringsBean.getData().getList().getTopic_list());
            updateHeadView();
            setDatas();
        }else {
            Toast.makeText(mContext,"网络连接异常，请检查网络！",Toast.LENGTH_SHORT).show();
        }
        ringsListAdapter.notifyDataSetChanged();
    }

    private void setDatas() {
        Picasso.with(mContext).load(ringsBean.getData().getList().getIcon()).into(headImageIcon);
        String commentNum="讨论："+ringsBean.getData().getList().getThread_num();
        headCommentNum.setText(commentNum);
        String userNum="人数："+ringsBean.getData().getList().getMember_num();
        headUserNum.setText(userNum);
        String ringsName="说明："+ringsBean.getData().getList().getDesc();
        headRingsName.setText(ringsName);
        String hotComment="热门讨论("+ringsBean.getData().getList().getThread_num()+")";
        headHotComment.setText(hotComment);
    }

    private void updateHeadView() {
        headImageIcon= (ImageView) listHeadView.findViewById(R.id.quanzi_head_icon);
        headGuanZhuIcon= (ImageView) listHeadView.findViewById(R.id.rings_guanzhu_icon);
        headCommentNum= (TextView) listHeadView.findViewById(R.id.quanzi_comment_num);
        headUserNum= (TextView) listHeadView.findViewById(R.id.quanzi_user_num);
        headRingsName= (TextView) listHeadView.findViewById(R.id.quanzi_head_name);
        headHotComment= (TextView) listHeadView.findViewById(R.id.hot_comment);
    }

    class RingsListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return topic_list==null?0:topic_list.size();
        }

        @Override
        public Object getItem(int i) {
            return topic_list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view==null){
                view=mInflater.inflate(R.layout.rings_list_item_view,viewGroup,false);
                viewHolder=new ViewHolder(view);
            }else {
                viewHolder= (ViewHolder) view.getTag();
            }
            final RingsBean.DataBean.ListBean.TopicListBean topicListBean=topic_list.get(i);
            Picasso.with(mContext).load(topicListBean.getFace()).into(viewHolder.user_icon);
            viewHolder.user_name.setText(topicListBean.getNickname());
            String user_lv="("+topicListBean.getRole_name()+")";
            viewHolder.user_lv.setText(user_lv);
            viewHolder.date_line.setText(topicListBean.getDateline());
            String message_from="来自"+topicListBean.getFrom();
            viewHolder.message_from.setText(message_from);
            viewHolder.replys_num.setText(topicListBean.getReplys());
            viewHolder.zan_num.setText(topicListBean.getDigcounts());
            viewHolder.message_title.setText(topicListBean.getTitle());
            if (topicListBean.getTitle().equals("")){
                viewHolder.message_title.setVisibility(View.GONE);
            }else {
                viewHolder.message_title.setVisibility(View.VISIBLE);
            }
            viewHolder.content.setText(topicListBean.getContent().get(0));
            GridImgListAdapter gridImgListAdapter=new GridImgListAdapter(topicListBean);
            if(topicListBean.getImage_list()==null){
                viewHolder.imgGrid.setVisibility(View.GONE);
            }else {
                viewHolder.imgGrid.setVisibility(View.VISIBLE);
            }
            viewHolder.imgGrid.setAdapter(gridImgListAdapter);
            viewHolder.imgGrid.setFocusable(false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, RingsInfoActivity.class);
                    intent.putExtra("tid",topicListBean.getTid());
                    startActivity(intent);
                }
            });
            return view;
        }

        class ViewHolder{
            public CircleImageView user_icon;
            public TextView user_name;
            public TextView user_lv;
            public TextView date_line;
            public TextView message_from;
            public TextView replys_num;
            public TextView zan_num;
            public TextView message_title;
            public TextView content;
            public GridView imgGrid;

            public ViewHolder(View view){
                view.setTag(this);
                user_icon= (CircleImageView) view.findViewById(R.id.user_face_icon);
                user_name= (TextView) view.findViewById(R.id.rings_user_name);
                user_lv= (TextView) view.findViewById(R.id.user_lv);
                date_line= (TextView) view.findViewById(R.id.rings_date_line);
                message_from= (TextView) view.findViewById(R.id.rings_message_from);
                replys_num= (TextView) view.findViewById(R.id.rings_item_replys_num);
                zan_num= (TextView) view.findViewById(R.id.rings_item_zan_num);
                message_title= (TextView) view.findViewById(R.id.rings_item_title);
                content= (TextView) view.findViewById(R.id.rings_item_content);
                imgGrid= (GridView) view.findViewById(R.id.rings_item_img_show);
            }
        }
    }
    class GridImgListAdapter extends BaseAdapter {
        private RingsBean.DataBean.ListBean.TopicListBean topicListBean;
        public GridImgListAdapter(RingsBean.DataBean.ListBean.TopicListBean topicListBean){
            this.topicListBean=topicListBean;
        }
        @Override
        public int getCount() {
            if (topicListBean.getImage_list()==null){
                return 0;
            }else if(topicListBean.getImage_list().size()<=3){
                return topicListBean.getImage_list().size();
            }else {
                return 3;
            }
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view==null){
                view=mInflater.inflate(R.layout.grid_view_item_view,viewGroup,false);
                viewHolder=new ViewHolder(view);
            }else {
                viewHolder= (ViewHolder) view.getTag();
            }
            viewHolder.imageView.setFocusable(false);
            Picasso.with(mContext).load(topicListBean.getImage_list().get(i).getImage_middle()).into(viewHolder.imageView);
            return view;
        }

        class ViewHolder{
            public ImageView imageView;
            public ViewHolder(View view){
                view.setTag(this);
                imageView= (ImageView) view.findViewById(R.id.grid_item_img);
            }
        }
    }
}
