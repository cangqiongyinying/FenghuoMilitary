package phone1000.com.firemilitary.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liang.OkHttpLibrary.IOKCallBack;
import com.liang.OkHttpLibrary.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import phone1000.com.firemilitary.Activity.ViedoInfo.VideoContentActivity;
import phone1000.com.firemilitary.MainActivity;
import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.adapter.VideoAdapter;
import phone1000.com.firemilitary.bean.VideoInfo;

public class PlayerFragment extends BaseFragment {

    //    public static final String URL = "http://if.fenghuo001.com/api1.3/topic.php?code=videotopic&page=1";
    public static final String URL = "http://if.fenghuo001.com/api1.3/topic.php?code=videotopic";
    private List<VideoInfo.DataBean.ListBean> lvData = new ArrayList<>();
    private int page = 1;
    private Context mContext;
    private VideoAdapter videoAdapter;
    private ProgressDialog progressDialog;
    private PullToRefreshListView mPullToRefreshListView;
    private ListView mListView;


    public PlayerFragment() {
        // Required empty public constructor
    }
    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_player, container, false);
        // Inflate the layout for this fragment
        //初始化视图
        initView(view);
        //初始化数据源
        initData();
        //初始化适配器，并绑定适配器
        initAdapter();
        //设置监听
        initListener();
        return view;
    }

    /**
     * 设置监听事件
     */
    private void initListener() {
        /**
         * 设置下拉刷新回到第一页，上拉刷新加载下一页数据
         */
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            /**
             * 下拉刷新,刷新第一页
             * @param refreshView
             */
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (!MainActivity.netConnect) {
                    Toast.makeText(mContext, "当前网络未连接,请查看网络状态", Toast.LENGTH_SHORT).show();
                }
                page = 1;
                initData();
                //加载数据完成之后，通知刷新控件结束刷新动作
                mPullToRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshListView.onRefreshComplete();
                    }
                }, 1000);
            }

            /**
             * 上拉刷新,请求下一页数据
             * @param refreshView
             */
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //判断当前网络状态
                if (!MainActivity.netConnect) {
                    Toast.makeText(mContext, "当前网络未连接,请查看网络状态", Toast.LENGTH_SHORT).show();
                }
                if (page <= 6) {
                    page++;
                    initData();
                    //加载数据完成之后，通知刷新控件结束刷新动作
                    mPullToRefreshListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullToRefreshListView.onRefreshComplete();
                        }
                    }, 1000);
                } else {
                    Toast.makeText(mContext, "没有更多数据了...", Toast.LENGTH_SHORT).show();
                    //加载数据完成之后，通知刷新控件结束刷新动作
                    mPullToRefreshListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullToRefreshListView.onRefreshComplete();
                        }
                    }, 1000);
                }

            }
        });

        /**
         * 点击跳转到详情页面
         */
        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, VideoContentActivity.class);
                Intent intent = new Intent(mContext, VideoContentActivity.class);
                //传递参数
                String key = lvData.get(position - 1).getTid();
                intent.putExtra("tid", key);
                startActivity(intent);
            }
        });


    }

    /**
     * 定义适配器，并绑定数据源
     */
    private void initAdapter() {
        videoAdapter = new VideoAdapter(mContext, lvData);
        //绑定适配器
        mPullToRefreshListView.setAdapter(videoAdapter);
    }

    /**
     * 初始化数据源
     */
    private void initData() {

        progressDialog.show();

        String id = String.valueOf(page);
        //POST请求
        HashMap<String, String> map = new HashMap<>();
        map.put("page", id);

        OkHttpUtils.newInstance()
                .start(URL)
                .post(map)
                .callback(new IOKCallBack() {
                    @Override
                    public void success(String result) {

                        progressDialog.dismiss();

                        //解析JSON数据
                        Gson gson = new Gson();
                        VideoInfo videoInfo = gson.fromJson(result, VideoInfo.class);
                        if (MainActivity.netConnect && result != null) {
                            //如果网络连接成功
                            lvData.addAll(videoInfo.getData().getList());
                        }
                        //刷新适配器
                        videoAdapter.notifyDataSetChanged();
                    }
                });

    }

    /**
     * 初始化视图
     */
    private void initView(View view) {
//        mListView = (ListView) findViewById(R.id.video_listView);
        mPullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.video_listView);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
//        mListView = mPullToRefreshListView.getRefreshableView();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("正在加载中...");
    }



}
