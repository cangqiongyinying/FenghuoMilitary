package phone1000.com.firemilitary.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.liang.OkHttpLibrary.IOKCallBack;
import com.liang.OkHttpLibrary.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import phone1000.com.firemilitary.Fragment.TuijianFrag.DuDianShiFragment;
import phone1000.com.firemilitary.Fragment.TuijianFrag.JunMiHuiFragment;
import phone1000.com.firemilitary.Fragment.TuijianFrag.ShouYeFragment;
import phone1000.com.firemilitary.Fragment.TuijianFrag.XinxikongFragment;
import phone1000.com.firemilitary.Fragment.TuijianFrag.YizhongguoFragmen;
import phone1000.com.firemilitary.Fragment.TuijianFrag.ZhangZiShiFragment;
import phone1000.com.firemilitary.Fragment.TuijianFrag.ZhuangbeiFragment;
import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.bean.ShouYeProductInfo;
import phone1000.com.firemilitary.easytagdragview.EasyTipDragView;
import phone1000.com.firemilitary.easytagdragview.bean.SimpleTitleTip;
import phone1000.com.firemilitary.easytagdragview.bean.Tip;
import phone1000.com.firemilitary.easytagdragview.widget.TipItemView;

public class TuijianFragment extends BaseFragment implements IOKCallBack{
    private TabLayout tablelayout;
    private ViewPager viewpager;
    private ImageButton imagebutton;
    private MyFragmentViewpagerAdapter myadapter;
    private Map<String,String> map=new HashMap<>();
    private Map<String,Fragment> mapFragment=new HashMap<>();
    private List<String> titlelist=new ArrayList<>();
    private List<Fragment> fragmentList=new ArrayList<>();
    private StringBuilder stringBuild;
    private int page = 1;
    private int maxid;
    private Gson gson;
    private List<ShouYeProductInfo.DataBean.AllChanListBean> chanListBeanList=new ArrayList<>();
    private List<ShouYeProductInfo.DataBean.AllChanListBean> shouyeListBeanList=new ArrayList<>();
    public static EasyTipDragView esaytipdragview;
    private LinearLayout linearlayout;

    public TuijianFragment() {
        // Required empty public constructor
    }
    public static TuijianFragment  newInstance(){
        TuijianFragment fragment = new TuijianFragment();
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
        View view=inflater.inflate(R.layout.fragment_tuijian, container, false);
        //初始化视图
        initView(view);
        //请求网络
        getHttp();
        return view;
    }

    private void getHttp() {
        stringBuild=new StringBuilder();
        stringBuild.append(ShouYeFragment.ADRESSURL).append(page).append("&");
        map.put("maxid=", String.valueOf(maxid));
        OkHttpUtils.newInstance().start(stringBuild.toString()).post(map).callback(this);
    }

    private void initView(View view) {
        esaytipdragview=(EasyTipDragView)view.findViewById(R.id.tuijianfragment_esaytipdragview);
        //设置我的频道的点击事件
        esaytipdragview.setSelectedListener(new TipItemView.OnSelectedListener() {
            @Override
            public void onTileSelected(Tip entity, int position, View view) {
                //得到拖动GridView的点击事件
                for (int i = 1; i <myadapter.getCount() ; i++) {
                    if (((SimpleTitleTip)entity).getTip().contentEquals(myadapter.getPageTitle(i))){
                        tablelayout.getTabAt(i);
                        viewpager.setCurrentItem(i);
                    }
                }
                if (esaytipdragview.isOpen()){
                    esaytipdragview.close();
                }
            }
        });
        //设置当我的频道改变时的tablelayout和ViewPager的条目的改变
        esaytipdragview.setOnCompleteCallback(new EasyTipDragView.OnCompleteCallback() {
            @Override
            public void onComplete(ArrayList<Tip> tips) {
                if (tips.size()==(myadapter.getCount()-1)){
                    //有变动就对位置进行排序
                }
                    fragmentList.clear();
                    titlelist.clear();
                    //先添加第一个的视图
                    titlelist.add("首页");
                    fragmentList.add(ShouYeFragment.newInstance());
                    for (int i = 0; i < tips.size(); i++) {
                        String tip = ((SimpleTitleTip) tips.get(i)).getTip();
                        if (mapFragment.containsKey(tip)) {
                            titlelist.add(tip);
                            fragmentList.add(mapFragment.get(tip));
                        }
                    }
                    myadapter.notifyDataSetChanged();
                    viewpager.setCurrentItem(0);
            }
        });

        esaytipdragview.setDataResultCallback(new EasyTipDragView.OnDataChangeResultCallback() {
            @Override
            public void onDataChangeResult(ArrayList<Tip> tips) {
                fragmentList.clear();
                titlelist.clear();
                //先添加第一个的视图
                titlelist.add("首页");
                fragmentList.add(ShouYeFragment.newInstance());
                for (int i = 0; i < tips.size(); i++) {
                    String tip = ((SimpleTitleTip) tips.get(i)).getTip();
                    if (mapFragment.containsKey(tip)) {
                        titlelist.add(tip);
                        fragmentList.add(mapFragment.get(tip));
                    }
                }
                myadapter.notifyDataSetChanged();
            }
        });

        linearlayout=(LinearLayout)view.findViewById(R.id.tuijiamfragment_linearlayout);
        tablelayout = (TabLayout) view.findViewById(R.id.tuijiamfragment_tablelayout);
        tablelayout.setSelectedTabIndicatorColor(Color.BLACK);
        tablelayout.setTabTextColors(Color.GRAY,Color.BLACK);
        tablelayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        viewpager=(ViewPager)view.findViewById(R.id.tuijianfragment_viewpager);
        viewpager.setCurrentItem(0);
        //放入标题
        titlelist.add("首页");
        titlelist.add("信息流");
        titlelist.add("装备控");
        titlelist.add("议中国");
        titlelist.add("读点史");
        titlelist.add("涨姿势");
        titlelist.add("军迷汇");
        //放入Fragment
        fragmentList.add(ShouYeFragment.newInstance());
        fragmentList.add(XinxikongFragment.newInstance());
        fragmentList.add(ZhuangbeiFragment.newInstance());
        fragmentList.add(YizhongguoFragmen.newInstance());
        fragmentList.add(DuDianShiFragment.newInstance());
        fragmentList.add(ZhangZiShiFragment.newInstance());
        fragmentList.add(JunMiHuiFragment.newInstance());
        //放入标题和Frament
        mapFragment.put("信息流",XinxikongFragment.newInstance());
        mapFragment.put("装备控",ZhuangbeiFragment.newInstance());
        mapFragment.put("议中国",YizhongguoFragmen.newInstance());
        mapFragment.put("读点史",DuDianShiFragment.newInstance());
        mapFragment.put("涨姿势",ZhangZiShiFragment.newInstance());
        mapFragment.put("军迷汇",JunMiHuiFragment.newInstance());

        myadapter=new MyFragmentViewpagerAdapter(getFragmentManager());
        viewpager.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();

        imagebutton=(ImageButton)view.findViewById(R.id.tuijianfragment_imagebutton);
        //关联Viewpager和TableLayout
        tablelayout.setupWithViewPager(viewpager);

    }

    @Override
    public void success(String result) {
        if (result!=null){
            gson=new Gson();
            ShouYeProductInfo shouYeProductInfo = gson.fromJson(result, ShouYeProductInfo.class);
            //得到标题栏的数据
            for (int i = 0; i < shouYeProductInfo.getData().getAll_chan_list().size(); i++) {
                if (shouYeProductInfo.getData().getAll_chan_list().get(i).getName().contentEquals("首页")){
                    shouyeListBeanList.add(shouYeProductInfo.getData().getAll_chan_list().get(i));
                }else {
                    chanListBeanList.add(shouYeProductInfo.getData().getAll_chan_list().get(i));
                }
            }
            //拖动的gridview添加数据
            List<Tip> tipList = addDragView(chanListBeanList);
            esaytipdragview.setDragData(tipList);
            //添加添加的数据的listview的数据的添加
            List<Tip> tipListadd = addGridView();
            esaytipdragview.setAddData(tipListadd);
            //设置右上角的点击事件
            imagebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    esaytipdragview.open();
                }
            });
        }
    }

    private List<Tip> addGridView() {
        List<Tip> result = new ArrayList<>();
        return result;
    }

    private List<Tip> addDragView(List<ShouYeProductInfo.DataBean.AllChanListBean> chanListBeanList) {
        List<Tip> tipList=new ArrayList<>();
        for (int i = 0; i < chanListBeanList.size(); i++) {
            SimpleTitleTip simpletitletip=new SimpleTitleTip();
            ShouYeProductInfo.DataBean.AllChanListBean allChanListBean = chanListBeanList.get(i);
            simpletitletip.setId(i);
            simpletitletip.setTip(allChanListBean.getName());
            simpletitletip.setImg(allChanListBean.getPic());
            tipList.add(simpletitletip);
        }
        return tipList;
    }


    class MyFragmentViewpagerAdapter extends FragmentStatePagerAdapter{
        public MyFragmentViewpagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titlelist.get(position);
        }
    }
}
