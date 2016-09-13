package phone1000.com.firemilitary.Activity.TuijianInfo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.squareup.picasso.Picasso;

import java.util.List;

import phone1000.com.firemilitary.CustomerView.TouchImageView;
import phone1000.com.firemilitary.R;

public class WatchImageActivity extends AppCompatActivity {

    private List<String> imagelist;
    private ConvenientBanner convientBanner;
    private ImageButton back;
    private TextView countnumber;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_image);
        Bundle extras = getIntent().getExtras();
        imagelist = extras.getStringArrayList("arraylist");
        position=extras.getInt("position");
        //初始化视图
         initView();
    }

    private void initView() {
        convientBanner=(ConvenientBanner)findViewById(R.id.watchimage_activity_convientbanner);
        back=(ImageButton)findViewById(R.id.watchimage_activity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        countnumber=(TextView)findViewById(R.id.watchimage_activity_countnumber);
        countnumber.setText(position+"/"+imagelist.size());

        convientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new MyWatchImageConvientbanner();
            }
        },imagelist).setcurrentitem((position-1));
        convientBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position1) {
                    countnumber.setText((position1+1)+"/"+imagelist.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class  MyWatchImageConvientbanner implements Holder<String>{
        private TouchImageView touchImageview;

        @Override
        public View createView(Context context) {
            touchImageview=new TouchImageView(WatchImageActivity.this);
            touchImageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return touchImageview;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Picasso.with(WatchImageActivity.this).load(data).into(touchImageview);
        }
    }
}
