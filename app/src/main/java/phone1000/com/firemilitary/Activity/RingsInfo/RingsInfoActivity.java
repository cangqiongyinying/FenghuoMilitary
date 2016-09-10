package phone1000.com.firemilitary.Activity.RingsInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liang.OkHttpLibrary.IOKCallBack;
import com.liang.OkHttpLibrary.OkHttpUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import phone1000.com.firemilitary.MainActivity;
import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.View.NoScrollExpandableListView;
import phone1000.com.firemilitary.bean.RingsInfoBean;

public class RingsInfoActivity extends AppCompatActivity implements IOKCallBack{
    public static final String HTTP_CONTENT_URL="/api1.3/topic.php?code=detail&tid=";
    private String tid;
    private ListView infoListView;
    private View listHeadView;
    private View listFootView;
    private TextView backIcon;
    private ImageView moreCheck;
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
    private TextView zanNum;
    private TextView replyNum;
    private NoScrollExpandableListView footReplyView;
    private RingsInfoBean ringsInfoBean;
    private List<RingsInfoBean.DataBean.ContentBean> contentBeanList=new ArrayList<>();
    private List<String> imgList=new ArrayList<>();
    private RingsInfoImgAdapter ringsInfoImgAdapter;
    private List<RingsInfoBean.DataBean.ReplyListBean> replyListBeanList =new ArrayList<>();
    private ReplysListAdapter replysListAdapter;
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
        backIcon= (TextView) findViewById(R.id.rings_info_back);
        moreCheck= (ImageView) findViewById(R.id.more_check);
        inputComent= (EditText) findViewById(R.id.input_comment);
        zan= (ImageView) findViewById(R.id.rings_info_zan);
        share = (ImageView) findViewById(R.id.rings_info_share);
        addEmptyView();
        infoListView.addHeaderView(listHeadView);
        infoListView.addFooterView(listFootView);
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
            replyListBeanList=ringsInfoBean.getData().getReply_list();
            updateHeadView();
            updateFootView();
            loadDatas();
        }
        ringsInfoImgAdapter.notifyDataSetChanged();
        replysListAdapter.notifyDataSetChanged();
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
        zanNum= (TextView) listFootView.findViewById(R.id.rings_info_zan_num);
        replyNum= (TextView) listFootView.findViewById(R.id.rings_info_replys_num);
        footReplyView= (NoScrollExpandableListView) listFootView.findViewById(R.id.rings_info_comment_info);
        replysListAdapter=new ReplysListAdapter();
        footReplyView.setAdapter(replysListAdapter);
        for (int i = 0; i < replyListBeanList.size(); i++) {
            footReplyView.expandGroup(i);
        }
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
        String user_lv="("+ringsInfoBean.getData().getRole_name()+")";
        userLv.setText(user_lv);
        dateLine.setText(ringsInfoBean.getData().getDateline());
        infoTitle.setText(ringsInfoBean.getData().getTitle());
        zanNum.setText(ringsInfoBean.getData().getDigcounts());
        replyNum.setText(ringsInfoBean.getData().getReplys());
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

    class ReplysListAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return replyListBeanList==null?0:replyListBeanList.size();
        }

        @Override
        public int getChildrenCount(int i) {
            List<RingsInfoBean.DataBean.ReplyListBean.ParentsListBean> parentsListBeanList=replyListBeanList.get(i).getParents_list();
            return parentsListBeanList==null?0:parentsListBeanList.size();
        }

        @Override
        public Object getGroup(int i) {
            return replyListBeanList.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            List<RingsInfoBean.DataBean.ReplyListBean.ParentsListBean> parentsListBeanList=replyListBeanList.get(i).getParents_list();
            return parentsListBeanList.get(i);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            GroupViewHolder groupViewHolder;
            if (view==null){
                view=LayoutInflater.from(RingsInfoActivity.this).inflate(R.layout.rings_info_foot_list_item_view,viewGroup,false);
                groupViewHolder=new GroupViewHolder(view);
            }else {
                groupViewHolder= (GroupViewHolder) view.getTag();
            }
            RingsInfoBean.DataBean.ReplyListBean replyListBean=replyListBeanList.get(i);
            Picasso.with(RingsInfoActivity.this).load(replyListBean.getFace()).into(groupViewHolder.userIcon);
            groupViewHolder.userName.setText(replyListBean.getNickname());
            String userLv="("+replyListBean.getRole_name()+")";
            groupViewHolder.userLv.setText(userLv);
            groupViewHolder.dateLine.setText(replyListBean.getDateline());
            groupViewHolder.replyContent.setText(replyListBean.getContent().get(0));
            if (!replyListBean.getDigcounts().equals("0")){
                groupViewHolder.zan.setText(replyListBean.getDigcounts());
            }else {
                groupViewHolder.zan.setText("");
            }
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            ChildViewHolder childViewHolder;
            List<RingsInfoBean.DataBean.ReplyListBean.ParentsListBean> parentsListBeanList=replyListBeanList.get(i).getParents_list();
            RingsInfoBean.DataBean.ReplyListBean.ParentsListBean parentsListBean=parentsListBeanList.get(i1);
            if (view==null){
                view=LayoutInflater.from(RingsInfoActivity.this).inflate(R.layout.rings_info_foot_replys_list_child_item,viewGroup,false);
                childViewHolder=new ChildViewHolder(view);
            }else {
                childViewHolder= (ChildViewHolder) view.getTag();
            }
            String childUserName="";
            if (parentsListBean.getIs_reply().equals("0")){
                childUserName=parentsListBean.getNickname()+":";
            }else if (parentsListBean.getIs_reply().equals("1")){
                childUserName=parentsListBean.getNickname()+"回复"+parentsListBeanList.get(i).getNickname()+":";
            }
            childViewHolder.childUserName.setText(childUserName);
            childViewHolder.childReplyContent.setText(parentsListBean.getContent().get(0));
            childViewHolder.childDateLine.setText(parentsListBean.getDateline());
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }

        class GroupViewHolder{
            public CircleImageView userIcon;
            public TextView userName;
            public TextView userLv;
            public TextView dateLine;
            public TextView replyContent;
            public TextView louceng;
            public TextView zan;
            public TextView reply;

            public GroupViewHolder(View view){
                view.setTag(this);
                userIcon= (CircleImageView) view.findViewById(R.id.info_replys_user_face_icon);
                userName= (TextView) view.findViewById(R.id.rings_info_replys_user_name);
                userLv= (TextView) view.findViewById(R.id.rings_info_replys_user_lv);
                dateLine= (TextView) view.findViewById(R.id.rings_info_replys_date_line);
                replyContent= (TextView) view.findViewById(R.id.rings_info_foot_replys_content);
                louceng= (TextView) view.findViewById(R.id.rings_info_replys_louceng);
                zan= (TextView) view.findViewById(R.id.rings_info_foot_replys_zan);
                reply= (TextView) view.findViewById(R.id.rings_info_foot_replys_reply);
            }
        }

        class ChildViewHolder{
            public TextView childUserName;
            public TextView childReplyContent;
            public TextView childDateLine;

            public ChildViewHolder(View view){
                view.setTag(this);
                childUserName= (TextView) view.findViewById(R.id.child_replys_user_name);
                childReplyContent= (TextView) view.findViewById(R.id.child_replys_content);
                childDateLine= (TextView) view.findViewById(R.id.child_replys_date_line);
            }
        }
    }
}
