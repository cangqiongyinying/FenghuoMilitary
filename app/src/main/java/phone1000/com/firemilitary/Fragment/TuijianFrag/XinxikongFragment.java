package phone1000.com.firemilitary.Fragment.TuijianFrag;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import phone1000.com.firemilitary.Fragment.BaseFragment;
import phone1000.com.firemilitary.Fragment.TuijianFragment;
import phone1000.com.firemilitary.InterfaceClass.FragmentUrl;
import phone1000.com.firemilitary.MainActivity;
import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.adapter.CommonAdapter;
import phone1000.com.firemilitary.bean.XinxiKongProductInfo;

public class XinxikongFragment extends BaseFragment implements IOKCallBack,PullToRefreshBase.OnRefreshListener2{

    public static final String ADRESSURL="http://if.fenghuo001.com/api1.3/topic.php?code=recdchantopic&id=2";
    // TODO: Rename and change types of parameters
    private Map<String,String> map=new HashMap<>();
    private PullToRefreshListView pulltorefresh;
    private List<XinxiKongProductInfo.DataBean.ListBean> xinxinkonglist=new ArrayList<>();
    private int page=1;
    private Gson gson;
    private StringBuffer stringBuffer;
    private int total_page;
    private CommonAdapter commonadapte;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                pulltorefresh.onRefreshComplete();
                commonadapte.notifyDataSetChanged();
            }
            if (msg.what==1){
                pulltorefresh.onRefreshComplete();
                Toast.makeText(getActivity(), "已经到底了", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public static XinxikongFragment newInstance() {
        XinxikongFragment fragment = new XinxikongFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_xinxikong, container, false);
//        ShouYeFragment.newInstance().getFragmentlist()
        pulltorefresh=(PullToRefreshListView)view.findViewById(R.id.xinxikongfragment_putorefreshlistview);
        pulltorefresh.setMode(PullToRefreshBase.Mode.BOTH);
        //设置进入界面的进度条
        addEmptyView();
        //请求网络
        getHttp();
        //设置putollrefresh的刷新事件
        pulltorefresh.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    page=1;
                    map=new HashMap<>();
                    map.put("&page=",String.valueOf(page));
                    OkHttpUtils.newInstance().start(ADRESSURL).post(map).callback(new IOKCallBack() {
                        @Override
                        public void success(String result) {
                            gson=new Gson();
                            XinxiKongProductInfo xinxiKongProductInfo = gson.fromJson(result, XinxiKongProductInfo.class);
                            //清除数据源
                            xinxinkonglist.clear();
                            if (xinxiKongProductInfo!=null) {
                                for (int i = 0; i < xinxiKongProductInfo.getData().getList().size(); i++) {
                                    xinxinkonglist.add(xinxiKongProductInfo.getData().getList().get(i));
                                }
                            }
                            handler.sendEmptyMessageDelayed(0,2000);
                        }
                    });
                }
            }).start();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (page<=total_page) {
                    page++;
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
    //设置进入界面的进度条
    private void addEmptyView() {
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_loading));
        progressBar.setIndeterminate(true);
        progressBar.setLayoutParams(layoutParams);
        ((ViewGroup) pulltorefresh.getParent()).addView(progressBar);
        pulltorefresh.setEmptyView(progressBar);
    }
    private void getHttp() {
        map.put("&page=",String.valueOf(page));
        OkHttpUtils.newInstance().start(ADRESSURL).post(map).callback(this);
    }

    @Override
    public void success(String result) {
       gson=new Gson();
        XinxiKongProductInfo xinxiKongProductInfo = gson.fromJson(result, XinxiKongProductInfo.class);
        total_page = xinxiKongProductInfo.getData().getTotal_page();
        if (xinxiKongProductInfo!=null) {
            for (int i = 0; i < xinxiKongProductInfo.getData().getList().size(); i++) {
                xinxinkonglist.add(xinxiKongProductInfo.getData().getList().get(i));
            }
        }
        //刷新适配器
        commonadapte=new CommonAdapter(xinxinkonglist,getActivity());
        pulltorefresh.setAdapter(commonadapte);
        commonadapte.notifyDataSetChanged();
    }
}
