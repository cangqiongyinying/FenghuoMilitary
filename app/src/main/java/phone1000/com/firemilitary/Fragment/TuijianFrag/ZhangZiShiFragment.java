package phone1000.com.firemilitary.Fragment.TuijianFrag;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liang.OkHttpLibrary.IOKCallBack;
import com.liang.OkHttpLibrary.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import phone1000.com.firemilitary.Activity.TuijianInfo.TuijianInfoActivity;
import phone1000.com.firemilitary.Fragment.BaseFragment;
import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.adapter.CommonAdapter;
import phone1000.com.firemilitary.bean.XinxiKongProductInfo;

public class ZhangZiShiFragment extends BaseFragment implements IOKCallBack,PullToRefreshBase.OnRefreshListener2{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static final String ADRESSURL="http://if.fenghuo001.com/api1.3/topic.php?code=recdchantopic&id=9";
    private PullToRefreshListView putorefresh;
    private Map<String,String> map=new HashMap<>();
    private int page=1;
    private Gson gson;
    private int total_page;
    private CommonAdapter commonadapte;
    private List<XinxiKongProductInfo.DataBean.ListBean> xinxinkonglist=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                putorefresh.onRefreshComplete();
                commonadapte.notifyDataSetChanged();
            }
            if (msg.what==1){
                putorefresh.onRefreshComplete();
                Toast.makeText(getActivity(), "已经到底了", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public ZhangZiShiFragment() {
        // Required empty public constructor
    }

    public static ZhangZiShiFragment newInstance() {
        ZhangZiShiFragment fragment = new ZhangZiShiFragment();
        return fragment;
    }

    public static ZhangZiShiFragment newInstance(String param1, String param2) {
        ZhangZiShiFragment fragment = new ZhangZiShiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_zhang_zi_shi, container, false);
        putorefresh=(PullToRefreshListView)view.findViewById(R.id.zhangxishifragment_putorefreshlistview);
        putorefresh.setMode(PullToRefreshBase.Mode.BOTH);
        putorefresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TuijianInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tid", xinxinkonglist.get(position-1).getTid());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //设置进入界面的进度条
        addEmptyView();
        //请求网络
        getHttp();
        //设置putollrefresh的刷新事件
        putorefresh.setOnRefreshListener(this);
        return view;
    }

    private void getHttp() {
        map.put("&page=",String.valueOf(page));
        OkHttpUtils.newInstance().start(ADRESSURL).post(map).callback(this);
    }

    //设置进入界面的进度条
    private void addEmptyView() {
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_loading));
        progressBar.setIndeterminate(true);
        progressBar.setLayoutParams(layoutParams);
        ((ViewGroup) putorefresh.getParent()).addView(progressBar);
        putorefresh.setEmptyView(progressBar);
    }

    @Override
    public void success(String result) {
        if (result!=null) {
            gson = new Gson();
            XinxiKongProductInfo xinxiKongProductInfo = gson.fromJson(result, XinxiKongProductInfo.class);
            total_page = xinxiKongProductInfo.getData().getTotal_page();
            if (xinxiKongProductInfo != null) {
                for (int i = 0; i < xinxiKongProductInfo.getData().getList().size(); i++) {
                    xinxinkonglist.add(xinxiKongProductInfo.getData().getList().get(i));
                }
            }
        }
        //刷新适配器
        commonadapte=new CommonAdapter(xinxinkonglist,getActivity());
        putorefresh.setAdapter(commonadapte);
        commonadapte.notifyDataSetChanged();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                page = 1;
                map = new HashMap<>();
                map.put("&page=", String.valueOf(page));
                OkHttpUtils.newInstance().start(ADRESSURL).post(map).callback(new IOKCallBack() {
                    @Override
                    public void success(String result) {
                        gson = new Gson();
                        XinxiKongProductInfo xinxiKongProductInfo = gson.fromJson(result, XinxiKongProductInfo.class);
                        //清除数据源
                        xinxinkonglist.clear();
                        if (xinxiKongProductInfo!=null) {
                            for (int i = 0; i < xinxiKongProductInfo.getData().getList().size(); i++) {
                                xinxinkonglist.add(xinxiKongProductInfo.getData().getList().get(i));
                            }
                        }
                        handler.sendEmptyMessageDelayed(0, 2000);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        new Thread(new Runnable() {
            public StringBuffer stringBuffer;

            @Override
            public void run() {
                page++;
                if (page <= total_page) {
                    stringBuffer=new StringBuffer();
                    stringBuffer.append(ADRESSURL).append("&page=").append(page);
                    OkHttpUtils.newInstance().start(stringBuffer.toString()).callback(new IOKCallBack() {
                        @Override
                        public void success(String result) {
                            gson = new Gson();
                            XinxiKongProductInfo xinxiKongProductInfo = gson.fromJson(result, XinxiKongProductInfo.class);
                            if (xinxiKongProductInfo!=null) {
                                for (int i = 0; i < xinxiKongProductInfo.getData().getList().size(); i++) {
                                    xinxinkonglist.add(xinxiKongProductInfo.getData().getList().get(i));
                                }
                            }
                            handler.sendEmptyMessageDelayed(0, 2000);
                        }
                    });
                }else {
                    handler.sendEmptyMessage(1);
                }
            }
        }).start();
    }
}
