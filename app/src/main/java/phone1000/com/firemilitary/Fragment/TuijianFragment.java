package phone1000.com.firemilitary.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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

public class TuijianFragment extends BaseFragment implements IOKCallBack{
    private TabLayout tablelayout;
    private ViewPager viewpager;
    private ImageButton imagebutton;
    private StringBuffer stringbuffer;
    private Context mContext;
    private int page=1;
    private int maxid=40208;
    private Map<String,String> map=new HashMap<>();
    private Gson gson;
    private List<String> titlelist=new ArrayList<>();
    private List<Fragment> fragmentList=new ArrayList<>();
    private MyFragmentViewpagerAdapter myadapter;
    private Map<String,String> fragmentMap;
    private Fragment tuijianreplacefragment;
    public TuijianFragment() {
        // Required empty public constructor
    }
    public static TuijianFragment  newInstance(){
        return new TuijianFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tuijian, container, false);
        //初始化视图
        initView(view);
        addFragments();
        //请求网络
        getHttp();
        return view;
    }

    private void getHttp() {
        stringbuffer=new StringBuffer();
        stringbuffer.append(ShouYeFragment.ADRESSURL).append(page).append("&");
        map.put("maxid=",String.valueOf(maxid));
        OkHttpUtils.newInstance().start(stringbuffer.toString()).post(map).callback(this);
    }

    private void addFragments(){
        titlelist.add("首页");
        titlelist.add("信息流");
        titlelist.add("装备控");
        titlelist.add("议中国");
        titlelist.add("读点史");
        titlelist.add("涨姿势");
        titlelist.add("军迷汇");

        fragmentList.add(ShouYeFragment.newInstance());
        fragmentList.add(XinxikongFragment.newInstance());
        fragmentList.add(ZhuangbeiFragment.newInstance());
        fragmentList.add(YizhongguoFragmen.newInstance());
        fragmentList.add(DuDianShiFragment.newInstance());
        fragmentList.add(ZhangZiShiFragment.newInstance());
        fragmentList.add(JunMiHuiFragment.newInstance());
        myadapter.notifyDataSetChanged();
    }
    private void initView(View view) {
        tablelayout = (TabLayout) view.findViewById(R.id.tuijiamfragment_tablelayout);
        tablelayout.setSelectedTabIndicatorColor(Color.BLACK);
        tablelayout.setTabTextColors(Color.GRAY,Color.BLACK);
        viewpager=(ViewPager)view.findViewById(R.id.tuijianfragment_viewpager);
        myadapter=new MyFragmentViewpagerAdapter(getFragmentManager());
        viewpager.setAdapter(myadapter);
        //关联Viewpager和TableLayout
        tablelayout.setupWithViewPager(viewpager);
        imagebutton=(ImageButton)view.findViewById(R.id.tuijianfragment_imagebutton);

        
//        imagebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//               if (activity.tuijian!=null){
//                   if (tuijianreplacefragment==null) {
//                       tuijianreplacefragment=TuiJianReplaceFragment.newInstance();
//                       fragmentTransaction.replace(R.id.mainactivity_framelayout,tuijianreplacefragment);
//                       fragmentTransaction.hide(activity.tuijian);
//                   }else {
//                       fragmentTransaction.replace(R.id.mainactivity_framelayout,tuijianreplacefragment);
//                       fragmentTransaction.hide(activity.tuijian);
//                   }
//                   fragmentTransaction.addToBackStack(null);
//                   fragmentTransaction.commit();
//               }
//            }
//        });
    }

    @Override
    public void success(String result) {
        gson=new Gson();
        ShouYeProductInfo shouYeProductInfo = gson.fromJson(result, ShouYeProductInfo.class);

    }

    class MyFragmentViewpagerAdapter extends FragmentPagerAdapter{
        public MyFragmentViewpagerAdapter(FragmentManager fm) {
            super(fm);
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
        public CharSequence getPageTitle(int position) {
            return titlelist.get(position);
        }
    }
}
