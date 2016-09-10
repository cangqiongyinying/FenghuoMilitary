package phone1000.com.firemilitary.Activity.ViedoInfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liang.OkHttpLibrary.IOKCallBack;
import com.liang.OkHttpLibrary.OkHttpUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.View.NoScrollListView;
import phone1000.com.firemilitary.adapter.VideoContentDiscussLvAdapter;
import phone1000.com.firemilitary.adapter.VideoContentLvAdapter;
import phone1000.com.firemilitary.bean.VideoContentInfo;

public class VideoContentActivity extends AppCompatActivity {

    public static final String URL = "http://if.fenghuo001.com/api1.3/topic_subscribe.php?code=details&tid=";
    //文章内容数据源
    private List<VideoContentInfo.DataBean.ContentBean> contentBeen = new ArrayList<>();
    //详情页相关栏ListView数据源
    private List<VideoContentInfo.DataBean.RelationsBean> relationsBeen = new ArrayList<>();
    //详情全部评论栏ListView数据源
    private List<VideoContentInfo.DataBean.ReplyListBean> replyListBeen = new ArrayList<>();
    private boolean netConnect;
    private TextView mArticleTitle;
    private TextView mArticleFrom;
    private TextView mArticleDateline;
    private ImageView mVideoPhoto;
    private TextView mArticleContent;
    private TextView mNickname;
    private TextView mReplys;
    private ProgressDialog progressDialog;
    private String tid;
    private String title;
    private String from;
    private String dateline;
    private String video_photo;
    private String nickname;
    private String replys;
    private String content;
    private String digcounts;
    private TextView mDigcounts;
    private String video_play;
    private RelativeLayout discussEmptyContent;
    private NoScrollListView discussListView;
    private NoScrollListView relationsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_content);
        //判断当前网络状态
        netConnect = OkHttpUtils.isNetworkConnected(this);
        if (!netConnect) {
            Toast.makeText(this, "当前网络未连接,请查看网络状态", Toast.LENGTH_SHORT).show();
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载中...");
        //接收传递过来的参数
        Intent intent = getIntent();
        tid = intent.getStringExtra("tid");
        //初始化视图
        initView();
        //初始化数据源
        initData();
        //初始化监听
        initListener();

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        /**
         * 点击videoImageView跳转到视频播放页面
         */
        mVideoPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoContentActivity.this, PlayVideoActivity.class);
                intent.putExtra("video_play", video_play);
                startActivity(intent);

            }
        });
    }

    /**
     * 加载数据效果
     */
    private void loadResult() {
        mArticleTitle.setText(title);//文章主标题
        mArticleFrom.setText("来源： " + from); //文章主标题
        mArticleDateline.setText(dateline);//文章发稿日期
        Picasso.with(this).load(video_photo).into(mVideoPhoto);//下载视频图片
        mNickname.setText("(" + "编辑：" + nickname + ")");//文章作者(编辑)
        mDigcounts.setText(digcounts);//点赞人数
        mReplys.setText("(" + replys + ")");//评论跟帖人数
        mArticleContent.setText(content);//文章内容

        //加载广告listView
        //初始化适配器,绑定适配器，并刷新适配器
        VideoContentLvAdapter contentLvAdapter = new VideoContentLvAdapter(this, relationsBeen);
        relationsListView.setAdapter(contentLvAdapter);
        contentLvAdapter.notifyDataSetChanged();


        //加载全部评论栏listView
        if (replyListBeen.size() != 0) {
            //初始化适配器,绑定适配器，并刷新适配器
            VideoContentDiscussLvAdapter discussLvAdapter = new VideoContentDiscussLvAdapter(this, replyListBeen);
            discussListView.setAdapter(discussLvAdapter);
            discussLvAdapter.notifyDataSetChanged();
        } else {
            discussListView.setVisibility(View.INVISIBLE);
            discussEmptyContent.setVisibility(View.VISIBLE);
        }

    }


    /**
     * 初始化数据源
     */
    private void initData() {

        progressDialog.show();

        //POST请求
//        HashMap<String, String> map = new HashMap<>();
//        map.put("tid=", tid);

        OkHttpUtils.newInstance()
                .start(URL + tid)
//                .post(map)
                .callback(new IOKCallBack() {

                    @Override
                    public void success(String result) {

                        progressDialog.dismiss();

                        //解析JSON数据
                        Gson gson = new Gson();
                        VideoContentInfo contentInfo = gson.fromJson(result, VideoContentInfo.class);

                        if (netConnect && result != null) {
                            //如果网络连接成功
                            title = contentInfo.getData().getTitle();//文章主标题
                            from = contentInfo.getData().getFrom();//文章主标题
                            dateline = contentInfo.getData().getDateline();//文章发稿日期
                            video_photo = contentInfo.getData().getVideo_photo();//视频图片
                            nickname = contentInfo.getData().getNickname();//文章作者
                            digcounts = contentInfo.getData().getDigcounts();//点赞人数digcount被顶次数
                            replys = contentInfo.getData().getReplys();//评论跟帖人数
                            video_play = contentInfo.getData().getVideo_play();//视频播放url_1
                            //文章内容数据
                            contentBeen.addAll(contentInfo.getData().getContent());
                            content = contentBeen.get(0).getContent();  //文章内容
                            //详情相关栏ListView数据源
                            relationsBeen.addAll(contentInfo.getData().getRelations());
                            //去除无效的数据
                            relationsBeen.remove(0);
                            relationsBeen.remove(4);//remove是一个个移除的  移出第一个后下标也跟着变了

                            //详情全部评论栏ListView数据源
                            replyListBeen.addAll(contentInfo.getData().getReply_list());
                            //加载数据效果
                            loadResult();

                        }

                    }
                });

    }

    /**
     * 初始化视图
     */
    private void initView() {
        mArticleTitle = (TextView) findViewById(R.id.video_content_title_tv);//文章主标题
        mArticleFrom = (TextView) findViewById(R.id.video_content_from_tv);  //文章来源
        mArticleDateline = (TextView) findViewById(R.id.video_content_dateline); //文章发稿日期
        mVideoPhoto = (ImageView) findViewById(R.id.video_content_video_iv);  //视频图片
        mArticleContent = (TextView) findViewById(R.id.video_content_content_tv);//文章内容
        mNickname = (TextView) findViewById(R.id.video_content_nickname);  //文章作者
        mDigcounts = (TextView) findViewById(R.id.video_content_digcounts);
//        relationsListView = (ListView) findViewById(R.id.video_content_relations_lv);  //相关文章(ListView)
        relationsListView = (NoScrollListView) findViewById(R.id.video_content_relations_lv);//相关文章(ListView)
        mReplys = (TextView) findViewById(R.id.video_content_discuss_num);  //评论跟帖人数
//        discussListView = (ListView) findViewById(R.id.video_content_discuss_lv);//全部评论(ListView)
        discussListView = (NoScrollListView) findViewById(R.id.video_content_discuss_lv);
        discussEmptyContent = (RelativeLayout) findViewById(R.id.discuss_lv_content_empty);//评论为空的时候显示此项
    }


    /**
     * 按返回键
     *
     * @param view
     */
    public void onBack(View view) {
        finish();
    }

    public void onAlertSize(View view) {
        Toast.makeText(VideoContentActivity.this, "改变字体大小", Toast.LENGTH_SHORT).show();
    }


}
