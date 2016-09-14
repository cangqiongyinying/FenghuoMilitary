package phone1000.com.firemilitary.Fragment.TuijianFrag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liang.OkHttpLibrary.IOKCallBack;
import com.liang.OkHttpLibrary.OkHttpUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import phone1000.com.firemilitary.Activity.TuijianInfo.TuijianInfoActivity;
import phone1000.com.firemilitary.Activity.ViedoInfo.VideoContentActivity;
import phone1000.com.firemilitary.Fragment.BaseFragment;
import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.TimeFormatTool.TimeFormatTool;
import phone1000.com.firemilitary.bean.ShouYeProductInfo;

public class ShouYeFragment extends BaseFragment implements IOKCallBack, PullToRefreshBase.OnRefreshListener2 {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ConvenientBanner convirentbanner;
    private PullToRefreshListView putorefreshlistview;
    private int page = 1;
    private int maxid;
    public static final String ADRESSURL = "http://if.fenghuo001.com/api1.3/topic.php?code=recdtopicnew&page=";
    private Map<String, String> map = new HashMap<>();
    private List<ShouYeProductInfo.DataBean.AllDataBean.SlideBean> slideBeenlist = new ArrayList<>();
    private List<ShouYeProductInfo.DataBean.AllDataBean.RecdTopicBean> recdtopiclist = new ArrayList<>();
    private List<Map<String, String>> convientlist = new ArrayList<>();
    private Map<String, String> convienmaptlist;
    private Map<String, String> fragmentMap;
    public List<Map<String, String>> fragmentlist = new ArrayList<>();
    private StringBuffer stringbuffer;
    private Gson gson;
    private PutoBaseAdapter putobaseAdapter;
    private int totalpage;

    private Handler handler = new Handler() {
        public Toast toast;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                putorefreshlistview.onRefreshComplete();
                putobaseAdapter.notifyDataSetChanged();
            }
            if (msg.what == 1) {
                int obj = (int) msg.obj;
                putorefreshlistview.onRefreshComplete();
                putobaseAdapter.notifyDataSetChanged();
                toast = new Toast(getActivity());
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.shouyefragment_inflater_toast, null);
                TextView textView = (TextView) view.findViewById(R.id.shouyefragment_inflater_toast_text);
                toast.setGravity(Gravity.TOP, 0, 70);
                toast.setDuration(Toast.LENGTH_SHORT);
                textView.setText("本次共刷新了" + obj + "条数据");
                toast.setView(view);
                toast.show();
            }
            if (msg.what == 2) {
                putorefreshlistview.onRefreshComplete();
                toast = new Toast(getActivity());
                toast.setGravity(Gravity.BOTTOM, 0, 85);
                toast.setDuration(Toast.LENGTH_SHORT);
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.shouyefragment_inflater_toast, null);
                TextView textView = (TextView) view.findViewById(R.id.shouyefragment_inflater_toast_text);
                textView.setText("已经到底啦");
                toast.setView(view);
                toast.show();
            }
        }
    };

    public ShouYeFragment() {
        // Required empty public constructor
    }

    public static ShouYeFragment newInstance() {
        ShouYeFragment fragment = new ShouYeFragment();
        return fragment;
    }

    public static ShouYeFragment newInstance(String param1, String param2) {
        ShouYeFragment fragment = new ShouYeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.shou_ye_fragment, container, false);
        //产生随机数
        maxid = (int) (Math.random() * 40207) + 1;
        //初始化视图
        initView(view);
        //请求网络
        getHttp();
        return view;
    }

    private void getHttp() {
        stringbuffer = new StringBuffer();
        stringbuffer.append(ADRESSURL).append(page).append("&");
        map.put("maxid=", String.valueOf(maxid));
        OkHttpUtils.newInstance().start(stringbuffer.toString()).post(map).callback(this);
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
        ((ViewGroup) putorefreshlistview.getParent()).addView(progressBar);
        putorefreshlistview.setEmptyView(progressBar);
    }

    private void initView(View view) {
        putorefreshlistview = (PullToRefreshListView) view.findViewById(R.id.shouyefragment_putorefreshlistview);
        putorefreshlistview.setMode(PullToRefreshBase.Mode.BOTH);
        //设置进入界面的进度条
        addEmptyView();
        //设置putorefresh刷新
        putorefreshlistview.setOnRefreshListener(this);

        //设置putorefresh的头部
        AbsListView.LayoutParams absListView = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.shouyefragment_convientbanner, putorefreshlistview, false);
        header.setLayoutParams(absListView);
        ListView refreshableView = putorefreshlistview.getRefreshableView();
        refreshableView.addHeaderView(header);
        convirentbanner = (ConvenientBanner) header.findViewById(R.id.shouyefragment_inflater_convientbanner);
        //设置广告牌的点击事件
        convirentbanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), TuijianInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tid", slideBeenlist.get(position).getTid());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        convirentbanner.setCanLoop(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        convirentbanner.setCanLoop(true);
    }

    //上拉刷新监听
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                maxid = (int) ((Math.random() * 40207) + 1);
                int numpage = (int) ((Math.random() * 9) + 1);
                stringbuffer = new StringBuffer();
                stringbuffer.append(ADRESSURL).append(numpage).append("&");
                map.put("maxid=", String.valueOf(maxid));
                OkHttpUtils.newInstance().start(stringbuffer.toString()).post(map).callback(new IOKCallBack() {
                    @Override
                    public void success(String result) {
                        if (result != null) {
                            ShouYeProductInfo shouYeProductInfo = gson.fromJson(result, ShouYeProductInfo.class);
                            for (int j = 0; j < shouYeProductInfo.getData().getAll_data().getRecd_topic().size(); j++) {
                                recdtopiclist.add(shouYeProductInfo.getData().getAll_data().getRecd_topic().get(j));
                            }
                            int num = (int) (Math.random() * 28) + 1;
                            Message message = new Message();
                            message.what = 1;
                            message.obj = num;
                            handler.sendMessageDelayed(message, 2000);
                        }
                    }
                });
            }
        }).start();
    }

    //下拉加载更过监听
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                page++;
                if (page <= totalpage) {
                    stringbuffer = new StringBuffer();
                    stringbuffer.append(ADRESSURL).append(page).append("&");
                    map.put("maxid=", String.valueOf(maxid));
                    OkHttpUtils.newInstance().start(stringbuffer.toString()).post(map).callback(new IOKCallBack() {
                        @Override
                        public void success(String result) {
                            if (result != null) {
                                ShouYeProductInfo shouYeProductInfo = gson.fromJson(result, ShouYeProductInfo.class);
                                for (int j = 0; j < shouYeProductInfo.getData().getAll_data().getRecd_topic().size(); j++) {
                                    recdtopiclist.add(shouYeProductInfo.getData().getAll_data().getRecd_topic().get(j));
                                }
                                handler.sendEmptyMessageDelayed(0, 2000);
                            }
                        }
                    });
                } else {
                    handler.sendEmptyMessage(2);
                }
            }
        }).start();
    }

    @Override
    public void success(String result) {
        if (result!=null) {
            gson = new Gson();
            ShouYeProductInfo shouYeProductInfo = gson.fromJson(result, ShouYeProductInfo.class);
            totalpage = shouYeProductInfo.getData().getTotal_page();
            if (shouYeProductInfo != null) {
                slideBeenlist.addAll(shouYeProductInfo.getData().getAll_data().getSlide());
            }
            //添加其他Fragment的数据
            if (shouYeProductInfo != null) {
                for (int m = 0; m < shouYeProductInfo.getData().getAll_chan_list().size(); m++) {
                    fragmentMap = new HashMap<>();
                    fragmentMap.put(shouYeProductInfo.getData().getAll_chan_list().get(m).getName(), shouYeProductInfo.getData().getAll_chan_list().get(m).getUrl());
                    fragmentlist.add(fragmentMap);
                }
            }

            //添加广告的数据源
            for (int i = 0; i < slideBeenlist.size(); i++) {
                convienmaptlist = new HashMap<>();
                convienmaptlist.put(slideBeenlist.get(i).getTitle(), slideBeenlist.get(i).getImage_list());
                convientlist.add(convienmaptlist);
            }
            //设置首页的广告牌
            convirentbanner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new MyConvientBannerHolder();
                }
            }, convientlist).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL).
                    setPageIndicator(new int[]{R.drawable.convientbannerindictor, R.drawable.convientbannerindictorwhite}).startTurning(3000);
            //添加putorefreshlistview的数据源
            if (shouYeProductInfo != null) {
                for (int j = 0; j < shouYeProductInfo.getData().getAll_data().getRecd_topic().size(); j++) {
                    recdtopiclist.add(shouYeProductInfo.getData().getAll_data().getRecd_topic().get(j));
                }
            }
            putobaseAdapter = new PutoBaseAdapter();
            putorefreshlistview.setAdapter(putobaseAdapter);
            putobaseAdapter.notifyDataSetChanged();
        }
    }


    //设置广告牌的适配器
    class MyConvientBannerHolder implements Holder<Map<String, String>> {
        ConVientBannerViewHolder conVientBannerViewHolder=null;
        @Override
        public View createView(Context context) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view=null;
            if (view==null){
                view = layoutInflater.inflate(R.layout.shouyefragment_convientbanner_inflater, null);
                conVientBannerViewHolder=new ConVientBannerViewHolder(view);
                view.setTag(conVientBannerViewHolder);
            }else {
                conVientBannerViewHolder=(ConVientBannerViewHolder) view.getTag();
            }
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, Map<String, String> data) {
            Set<String> strings = data.keySet();
            Iterator<String> iterator = strings.iterator();
            String next = iterator.next();
            String s = data.get(next);
            Picasso.with(getActivity()).load(s).into(conVientBannerViewHolder.imageView);
            conVientBannerViewHolder.textView.setText(next);
        }
    }

    class ConVientBannerViewHolder{
        ImageView imageView;
        TextView textView;
        ConVientBannerViewHolder(View view){
            imageView = (ImageView) view.findViewById(R.id.shouyefragment_convientbanner_inflater_imageview);
            textView = (TextView) view.findViewById(R.id.shouyefragment_convientbanner_inflater_textview);
        }
    }


    class PutoBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return recdtopiclist.size();
        }

        @Override
        public Object getItem(int position) {
            return recdtopiclist.get(position);
        }

        @Override
        public int getItemViewType(int position) {
            int show_type = recdtopiclist.get(position).getShow_type();
            if (show_type == 1) {
                return 1;
            }
            if (show_type == 2) {
                return 2;
            }
            if (show_type == 4) {
                return 4;
            }
            return super.getItemViewType(position);
        }

        @Override
        public int getViewTypeCount() {
            return 5;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            int itemViewType = getItemViewType(position);
            ViewHolder1 viewHolder1 = null;
            ViewHolder2 viewHolder2 = null;
            ViewHolder4 viewHolder4 = null;
            if (convertView == null) {
                switch (itemViewType) {
                    case 1:
                        convertView = layoutInflater.inflate(R.layout.shouyefragment_inflater1_putolistview, null);
                        viewHolder1 = new ViewHolder1(convertView);
                        convertView.setTag(viewHolder1);
                        break;
                    case 2:
                        convertView = layoutInflater.inflate(R.layout.shouyefragment_inflater2_putolistview, null);
                        viewHolder2 = new ViewHolder2(convertView);
                        convertView.setTag(viewHolder2);
                        break;
                    case 4:
                        convertView = layoutInflater.inflate(R.layout.shouyefragment_inflater4_putolistview, null);
                        viewHolder4 = new ViewHolder4(convertView);
                        convertView.setTag(viewHolder4);
                        break;
                }
            } else {
                switch (itemViewType) {
                    case 1:
                        viewHolder1 = (ViewHolder1) convertView.getTag();
                        break;
                    case 2:
                        viewHolder2 = (ViewHolder2) convertView.getTag();
                        break;
                    case 4:
                        viewHolder4 = (ViewHolder4) convertView.getTag();
                        break;
                }
            }
            final ShouYeProductInfo.DataBean.AllDataBean.RecdTopicBean recdTopicBean = recdtopiclist.get(position);
            switch (itemViewType) {
                case 1:
                    viewHolder1.title.setText(recdTopicBean.getTitle());
                    if (recdTopicBean.getCategory().contentEquals("广告")) {
                        viewHolder1.type.setText("广告");
                        viewHolder1.head.setVisibility(View.GONE);
                        viewHolder1.source.setVisibility(View.GONE);
                        viewHolder1.reply.setVisibility(View.GONE);
                        viewHolder1.message.setVisibility(View.GONE);
                    } else {
                        viewHolder1.head.setVisibility(View.VISIBLE);
                        viewHolder1.source.setVisibility(View.VISIBLE);
                        viewHolder1.reply.setVisibility(View.VISIBLE);
                        viewHolder1.message.setVisibility(View.VISIBLE);
                        //得到服务器的时间
                        long time = TimeFormatTool.getTime(Integer.valueOf(recdTopicBean.getDateline()));
                        long days = (time / (1000 * 60 * 60 * 24));
                        long hours = (time / (1000 * 60 * 60)) - (days * 24);
                        long minutes = ((time - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60));
                        if (days > 0) {
                            viewHolder1.type.setText(days + "天前");
                        } else {
                            if (hours >= 1 && hours <= 24) {
                                viewHolder1.type.setText(hours + "小时前");
                            } else {
                                if (minutes >= 1) {
                                    viewHolder1.type.setText(minutes + "分钟前");
                                } else {
                                    viewHolder1.type.setText("刚刚");
                                }
                            }
                        }

                        viewHolder1.source.setText(recdTopicBean.getList_from());
                        viewHolder1.reply.setText(recdTopicBean.getReplys() + "");
                    }
                    Picasso.with(getActivity()).load(recdTopicBean.getImage_list()).into(viewHolder1.imageView);
                    //添加当前item的点击事件
                    if (!recdTopicBean.getCategory().contentEquals("广告")) {
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), TuijianInfoActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("tid", recdTopicBean.getTid());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                    break;
                case 2:
                    viewHolder2.title.setText(recdTopicBean.getTitle());
                    viewHolder2.type.setText("多图");
                    viewHolder2.source.setText(recdTopicBean.getList_from());
                    viewHolder2.reply.setText(recdTopicBean.getReplys() + "");
                    //设置RecycleView的布局管理器
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, true);
                    viewHolder2.recyclerView.setLayoutManager(gridLayoutManager);
                    MyRecycleViewAdapter myRecycleViewAdapter = new MyRecycleViewAdapter(recdTopicBean.getImage_arr(),recdTopicBean.getTid());
                    viewHolder2.recyclerView.setAdapter(myRecycleViewAdapter);
                    viewHolder2.recyclerView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), TuijianInfoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tid", recdTopicBean.getTid());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), TuijianInfoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tid", recdTopicBean.getTid());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    break;
                case 4:
                    viewHolder4.title.setText(recdTopicBean.getTitle());
                    viewHolder4.type.setText("视频");
                    viewHolder4.source.setText(recdTopicBean.getList_from());
                    viewHolder4.reply.setText(recdTopicBean.getReplys() + "");
                    Picasso.with(getActivity()).load(recdTopicBean.getImage_list()).into(viewHolder4.imageView);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), VideoContentActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tid", recdTopicBean.getTid());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    break;
            }
            return convertView;
        }
    }

    class ViewHolder1 {
        private TextView title;
        private TextView type;
        private TextView source;
        private TextView reply;
        private RoundedImageView imageView;
        private ImageView head;
        private ImageView message;

        ViewHolder1(View view) {
            title = (TextView) view.findViewById(R.id.shouyefragment_inflater1_title);
            type = (TextView) view.findViewById(R.id.shouyefragment_inflater1_type);
            source = (TextView) view.findViewById(R.id.shouyefragment_inflater1_source);
            reply = (TextView) view.findViewById(R.id.shouyefragment_inflater1_replycount);
            imageView = (RoundedImageView) view.findViewById(R.id.shouyefragment_inflater1_imageview);
            head = (ImageView) view.findViewById(R.id.shouyefragment_inflater1_head);
            message = (ImageView) view.findViewById(R.id.shouyefragment_inflater1_message);
        }
    }

    class ViewHolder2 {
        private TextView title;
        private TextView type;
        private TextView source;
        private TextView reply;
        private RecyclerView recyclerView;

        ViewHolder2(View view) {
            title = (TextView) view.findViewById(R.id.shouyefragment_inflater2_title);
            type = (TextView) view.findViewById(R.id.shouyefragment_inflater2_type);
            source = (TextView) view.findViewById(R.id.shouyefragment_inflater2_source);
            reply = (TextView) view.findViewById(R.id.shouyefragment_inflater2_replycount);
            recyclerView = (RecyclerView) view.findViewById(R.id.shouyefragment_inflater2_recycleview);
        }
    }

    class ViewHolder4 {
        private TextView title;
        private TextView type;
        private TextView source;
        private TextView reply;
        private RoundedImageView imageView;

        ViewHolder4(View view) {
            title = (TextView) view.findViewById(R.id.shouyefragment_inflater4_title);
            type = (TextView) view.findViewById(R.id.shouyefragment_inflater4_type);
            source = (TextView) view.findViewById(R.id.shouyefragment_inflater4_source);
            reply = (TextView) view.findViewById(R.id.shouyefragment_inflater4_replycount);
            imageView = (RoundedImageView) view.findViewById(R.id.shouyefragment_inflater4_imageview);
        }
    }


    class MyRecycleHolder extends RecyclerView.ViewHolder {
        public RoundedImageView imageview;

        public MyRecycleHolder(View itemView) {
            super(itemView);
            imageview = (RoundedImageView) itemView.findViewById(R.id.shouyefragment_inflate_recycle_roungimage);
        }
    }

    class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleHolder> {
        private List<String> list = new ArrayList<>();
        private String recdTopicBeanList;

        public MyRecycleViewAdapter(List<String> list, String recdTopicBeanList) {
            this.list = list;
            this.recdTopicBeanList = recdTopicBeanList;
        }

        @Override
        public MyRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.shouyefragment_inflater_recycleview, null);
            MyRecycleHolder myRecycleHolder = new MyRecycleHolder(view);
            return myRecycleHolder;
        }

        @Override
        public void onBindViewHolder(MyRecycleHolder holder, int position) {
            Picasso.with(getActivity()).load(list.get(position)).into(holder.imageview);
            holder.imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TuijianInfoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tid", recdTopicBeanList);
                            intent.putExtras(bundle);
                            startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}
