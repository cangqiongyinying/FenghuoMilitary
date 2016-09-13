package phone1000.com.firemilitary.Activity.TuijianInfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liang.OkHttpLibrary.IOKCallBack;
import com.liang.OkHttpLibrary.OkHttpUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import phone1000.com.firemilitary.CustomerView.CustomerListview;
import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.adapter.TuijianInfoXianguanAdapter;
import phone1000.com.firemilitary.adapter.TujianInfoHuiFuAdpater;
import phone1000.com.firemilitary.bean.TuijianInfoActivityProductInfo;

public class TuijianInfoActivity extends AppCompatActivity implements IOKCallBack{
    public static final String ADRESSURL="http://if.fenghuo001.com/api1.3/topic_subscribe.php?code=details&tid=";
    private String tid;
    private Button back;
    private TextView title;
    private TextView dateline;
    private TextView from;
    private CustomerListview contentlistview;
    private Gson gson;
    private List<TuijianInfoActivityProductInfo.DataBean.ContentBean> contentlist=new ArrayList<>();
    private List<TuijianInfoActivityProductInfo.DataBean.RelationsBean> relativecontentlist=new ArrayList<>();
    private List<TuijianInfoActivityProductInfo.DataBean.ReplyListBean> replayscontentlist=new ArrayList<>();
    private List<String> imageList=new ArrayList<>();
    //对map集合进行升序排序
    private Map<Integer,String> mapImageList=new TreeMap<Integer,String>(new Comparator<Integer>() {
        @Override
        public int compare(Integer lhs, Integer rhs) {
            return lhs.compareTo(rhs);
        }
    });
    private TextView xiangguan;
    private View line2;
    private LinearLayout linear;
    private LinearLayout linear1;
    private LinearLayout linear2;
    private LinearLayout linear3;
    private RelativeLayout relative;
    private ProgressBar progressBar;
    private TuijianContentInfoAdapter tuijianContentInfoAdapter;
    private CustomerListview relativeContentListView;
    private CustomerListview replayscontentlistview;
    private ColorDrawable colorDrawable;
    private TuijianInfoXianguanAdapter tuijianInfoXianguanAdapter;
    private TujianInfoHuiFuAdpater tujianInfoHuiFuAdpater;
    private TextView replaycount;
    private TextView pinlunmention;
    private int pagernumber=0;
    private ImageView setting;
    private PopupWindow popupWindow;
    private RadioGroup popuwindowRadiogroup;
    private RadioButton popuwindowRadiobuttonsmall;
    private RadioButton popuwindowRadiobuttonmidel;
    private RadioButton popuwindowRadiobuttonbig;
    private RadioButton popuwindowRadiobuttonenough;
    private TextView frontcontentText;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                frontcontentText.setTextSize(R.dimen.text_size_small1);
            }
            if (msg.what==1){
                frontcontentText.setTextSize(R.dimen.text_size_medil);
            }
            if (msg.what==2){
                frontcontentText.setTextSize(R.dimen.text_size_big);
            }
            if (msg.what==3){
                frontcontentText.setTextSize(R.dimen.text_size_enoughbig);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuijian_info);
        Bundle extras = getIntent().getExtras();
        tid = extras.getString("tid");
        //初始化视图
        initView();
        //请求网络
        getHttp();
    }

    private void getHttp() {
        OkHttpUtils.newInstance().start(ADRESSURL+Integer.valueOf(tid)).callback(this);
    }

    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(TuijianInfoActivity.this);
        //点击返回事件
        back = (Button) findViewById(R.id.activity_tuijian_info_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuijianInfoActivity.this.finish();
            }
        });

        //设置新闻标题，时间，来源,评论信息等相关信息
        title=(TextView)findViewById(R.id.activity_tuijian_info_title);
        from=(TextView)findViewById(R.id.activity_tuijian_info_from);
        dateline=(TextView)findViewById(R.id.activity_tuijian_info_from_dateline);
        replaycount=(TextView)findViewById(R.id.activity_tuijian_info_from_num);
        pinlunmention=(TextView)findViewById(R.id.activity_tuijian_info_from_pinlunmention);

        //设置ProgressBar的相关属性
        progressBar=(ProgressBar)findViewById(R.id.activity_tuijian_info_progressbar);
        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_loading));






        //设置字体的大小，亮度，夜间模式
        setting=(ImageView)findViewById(R.id.activity_tuijian_info_setting);
        View view=layoutInflater.inflate(R.layout.tuijianinfoactivity_inflater_popuwindow,null);
        //设置字体大小
        popuwindowRadiobuttonsmall=(RadioButton)view.findViewById(R.id.tuijianinfo_popuwindow_radiobuttonsmall);
        popuwindowRadiobuttonmidel=(RadioButton)view.findViewById(R.id.tuijianinfo_popuwindow_radiobuttonmidel);
        popuwindowRadiobuttonbig=(RadioButton)view.findViewById(R.id.tuijianinfo_popuwindow_radiobuttonbig);
        popuwindowRadiobuttonenough=(RadioButton)view.findViewById(R.id.tuijianinfo_popuwindow_radiobuttonenoughbig);
        //找到设置显示字体的控件
        View inflate = layoutInflater.inflate(R.layout.tuijianinfoactivity_inflater1, null);
        frontcontentText = (TextView)inflate.findViewById(R.id.tuijianinfoactivity_inflater1_text);
        popuwindowRadiogroup=(RadioGroup)view.findViewById(R.id.tuijianinfo_popuwindow_radiogroup);
        popuwindowRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tuijianinfo_popuwindow_radiobuttonsmall:
                        handler.sendEmptyMessage(0);
                        break;
                    case R.id.tuijianinfo_popuwindow_radiobuttonmidel:
                        handler.sendEmptyMessage(1);
                        break;
                    case R.id.tuijianinfo_popuwindow_radiobuttonbig:
                        handler.sendEmptyMessage(2);
                        break;
                    case R.id.tuijianinfo_popuwindow_radiobuttonenoughbig:
                        handler.sendEmptyMessage(3);
                        break;
                }
            }
        });


        SeekBar seekBar = (SeekBar) view.findViewById(R.id.tuijianinfo_popuwindow_progress);
        //设置亮度的初始值
        try {
            int anInt = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            //系统亮度的最大值为255，故设置其其最大值为此亮度的最大值
            seekBar.setMax(255);
            seekBar.setProgress(anInt);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        //通过seekbar来改变屏幕亮度
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //设置系统的亮度为手动
                Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE,0);
                //改变系统的亮度
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //设置是模式为白天或是晚上
        Switch aSwitch = (Switch) view.findViewById(R.id.tuijianinfo_popuwindow_switch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    getApplication().setTheme(R.style.AppTheme1);
                }else {

                }
            }
        });
        //点击完成按钮使popuwindow消失
        Button wancheng=(Button)view.findViewById(R.id.tuijianinfo_popuwindow_btn);
        wancheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }else{
                    popupWindow.showAtLocation(findViewById(R.id.activity_tuijian_info_relative_parent), Gravity.BOTTOM, 0, 0);
                }
            }
        });






        //设置当前新闻数据相关的listview
        contentlistview=(CustomerListview)findViewById(R.id.activity_tuijian_info_from_contentlistview);
        colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        contentlistview.setSelector(colorDrawable);
        //设置内容的适配器
        tuijianContentInfoAdapter=new TuijianContentInfoAdapter(contentlist,imageList,TuijianInfoActivity.this);
        contentlistview.setAdapter(tuijianContentInfoAdapter);

        //设置相关新闻的ListView
        relativeContentListView=(CustomerListview)findViewById(R.id.activity_tuijian_info_from_xiangguanlistview);
        relativeContentListView.setSelector(colorDrawable);
        //设置相关新闻的适配器
        tuijianInfoXianguanAdapter = new TuijianInfoXianguanAdapter(relativecontentlist,TuijianInfoActivity.this);
        relativeContentListView.setAdapter(tuijianInfoXianguanAdapter);
        //listview的item的点击事件
        relativeContentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(TuijianInfoActivity.this,TuijianInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tid", relativecontentlist.get(position).getTid());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //设置回复条数的相关新闻
        replayscontentlistview=(CustomerListview)findViewById(R.id.activity_tuijian_info_from_pinlunlistview);
        relativeContentListView.setSelector(colorDrawable);
        //设置回复条数适配器
        tujianInfoHuiFuAdpater = new TujianInfoHuiFuAdpater(TuijianInfoActivity.this,replayscontentlist);
        replayscontentlistview.setAdapter(tujianInfoHuiFuAdpater);

        //先隐藏的相关控件
        xiangguan=(TextView)findViewById(R.id.activity_tuijian_xianggaun_text);
        line2=(View)findViewById(R.id.activity_tuijian_info_from_line2);
        xiangguan=(TextView)findViewById(R.id.activity_tuijian_xianggaun_text);
        linear=(LinearLayout)findViewById(R.id.activity_tuijian_info_from_ll);
        linear1=(LinearLayout)findViewById(R.id.activity_tuijian_info_from_ll1);
        linear2=(LinearLayout)findViewById(R.id.activity_tuijian_info_from_ll2);
        linear3=(LinearLayout)findViewById(R.id.activity_tuijian_info_from_ll3);
        relative=(RelativeLayout)findViewById(R.id.activity_tuijian_info_from_relative);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (popupWindow.isShowing()){
                popupWindow.dismiss();
            }else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void success(String result) {
        if (result!=null) {
            //请求网络成功后显示相关控件；
            xiangguan.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
            linear.setVisibility(View.VISIBLE);
            linear1.setVisibility(View.VISIBLE);
            linear2.setVisibility(View.VISIBLE);
            linear3.setVisibility(View.VISIBLE);
            relative.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            gson = new Gson();
            TuijianInfoActivityProductInfo tuijianInfoActivityProductInfo = gson.fromJson(result, TuijianInfoActivityProductInfo.class);
            //设置标题
            title.setText(tuijianInfoActivityProductInfo.getData().getTitle());
            //设置来源
            from.setText("来源:"+tuijianInfoActivityProductInfo.getData().getFrom());
            //设置时间
            dateline.setText(tuijianInfoActivityProductInfo.getData().getDateline());
            //设置回复数
            replaycount.setText("("+tuijianInfoActivityProductInfo.getData().getReplys()+")");
            //获取当前条目新闻内容数据
            for (int i = 0; i < tuijianInfoActivityProductInfo.getData().getContent().size(); i++) {
                contentlist.add(tuijianInfoActivityProductInfo.getData().getContent().get(i));
                if (tuijianInfoActivityProductInfo.getData().getContent().get(i).getType().contentEquals("img")){
                    mapImageList.put((pagernumber+1),tuijianInfoActivityProductInfo.getData().getContent().get(i).getSrc());
                    pagernumber++;
                }
            }
            Set<Integer> integers = mapImageList.keySet();
            Iterator<Integer> iterator = integers.iterator();
            while (iterator.hasNext()){
                Integer next = iterator.next();
                String s = mapImageList.get(next);
                imageList.add(s);
            }
            //获取相关新闻list
            for (int j = 0; j < tuijianInfoActivityProductInfo.getData().getRelations().size(); j++) {
                if ((!tuijianInfoActivityProductInfo.getData().getRelations().get(j).getTid().contentEquals("tid1"))
                        &&(!tuijianInfoActivityProductInfo.getData().getRelations().get(j).getTid().contentEquals("tid2")))
                {
                relativecontentlist.add( tuijianInfoActivityProductInfo.getData().getRelations().get(j));
                }
            }
            //获取评论相关新闻
            for (int m = 0; m < tuijianInfoActivityProductInfo.getData().getReply_list().size(); m++) {
                replayscontentlist.add(tuijianInfoActivityProductInfo.getData().getReply_list().get(m));
            }
            //当前条目新闻刷新适配器刷新
            tuijianContentInfoAdapter.notifyDataSetChanged();
            //相关新闻适配器刷新刷新
            tuijianInfoXianguanAdapter.notifyDataSetChanged();
            //设置回复条数的适配器刷新
            if (Integer.valueOf(tuijianInfoActivityProductInfo.getData().getReplys())==0){
                pinlunmention.setVisibility(View.VISIBLE);
            }else {
                tujianInfoHuiFuAdpater.notifyDataSetChanged();
            }
        }
    }
















    //设置显示内容的适配器
    class TuijianContentInfoAdapter extends BaseAdapter {
        private List<TuijianInfoActivityProductInfo.DataBean.ContentBean> contentlist = new ArrayList<>();
        private List<String> imageList = new ArrayList<>();
        private Context mContext;

        public TuijianContentInfoAdapter(List<TuijianInfoActivityProductInfo.DataBean.ContentBean> contentlist, List<String> imageList, Context mContext) {
            this.contentlist = contentlist;
            this.imageList = imageList;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return contentlist.size();
        }

        @Override
        public Object getItem(int position) {
            return contentlist.get(position);
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            if (contentlist.get(position).getType().contentEquals("txt")) {
                return 1;
            }
            if (contentlist.get(position).getType().contentEquals("img")) {
                return 2;
            }
            return super.getItemViewType(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            int itemViewType = getItemViewType(position);
            TuijianViewHolder1 tuijianViewHolder1 = null;
            TuijianViewHolder2 tuijianViewHolder2 = null;
            if (convertView == null) {
                switch (itemViewType) {
                    case 1:
                        convertView = layoutInflater.inflate(R.layout.tuijianinfoactivity_inflater1, null);
                        tuijianViewHolder1 = new TuijianViewHolder1(convertView);
                        convertView.setTag(tuijianViewHolder1);
                        break;
                    case 2:
                        convertView = layoutInflater.inflate(R.layout.tuijianinfoactivity_inflater2, null);
                        tuijianViewHolder2 = new TuijianViewHolder2(convertView);
                        convertView.setTag(tuijianViewHolder2);
                        break;
                }
            } else {
                switch (itemViewType) {
                    case 1:
                        tuijianViewHolder1 = (TuijianViewHolder1) convertView.getTag();
                        break;
                    case 2:
                        tuijianViewHolder2 = (TuijianViewHolder2) convertView.getTag();
                        break;
                }
            }
            final TuijianInfoActivityProductInfo.DataBean.ContentBean contentBean = contentlist.get(position);
            switch (itemViewType) {
                case 1:
                    tuijianViewHolder1.textView.setText(contentBean.getContent());
                    break;
                case 2:
                    if (contentBean.getSrc() != null) {
                        tuijianViewHolder2.textView.setVisibility(View.GONE);
                        ViewGroup.LayoutParams layoutParams = tuijianViewHolder2.imageView.getLayoutParams();
                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        layoutParams.height = (Integer.valueOf(contentBean.getHeight())) * 3;
                        tuijianViewHolder2.imageView.setLayoutParams(layoutParams);
                        tuijianViewHolder2.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        Picasso.with(mContext).load(contentBean.getSrc()).into(tuijianViewHolder2.imageView);
                        tuijianViewHolder2.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int position1=1;
                                //遍历Map集合进行排序
                                Set<Integer> integers = mapImageList.keySet();
                                Iterator<Integer> iterator = integers.iterator();
                                while (iterator.hasNext()){
                                    Integer next1 = iterator.next();
                                    String s1 = mapImageList.get(next1);
                                    if (s1.contentEquals(contentlist.get(position).getSrc())){
                                        position1=next1;
                                    }
                                }
                                Intent intent=new Intent(TuijianInfoActivity.this,WatchImageActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putStringArrayList("arraylist",(ArrayList<String>)imageList);
                                bundle.putInt("position",position1);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    } else {
                        tuijianViewHolder2.textView.setVisibility(View.VISIBLE);
                        tuijianViewHolder2.imageView.setImageResource(R.drawable.loading);
                    }
                    break;
            }
            return convertView;
        }

        class TuijianViewHolder1 {
            private TextView textView;

            TuijianViewHolder1(View view) {
                textView = (TextView) view.findViewById(R.id.tuijianinfoactivity_inflater1_text);
            }
        }

        class TuijianViewHolder2 {
            private TextView textView;
            private ImageView imageView;

            TuijianViewHolder2(View view) {
                textView = (TextView) view.findViewById(R.id.tuijianinfoactivity_inflater2_text);
                imageView = (ImageView) view.findViewById(R.id.tuijianinfoactivity_inflater2_imageview);
            }
        }
    }
}
