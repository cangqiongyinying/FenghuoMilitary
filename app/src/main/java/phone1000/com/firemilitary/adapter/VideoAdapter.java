package phone1000.com.firemilitary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.List;

import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.bean.VideoInfo;

/**
 * Created by Jerry 2016/9/5 14:56
 */
public class VideoAdapter extends BaseAdapter {

    private Context mContext;
    private List<VideoInfo.DataBean.ListBean> lvData;
    private LayoutInflater mInflater;

    public VideoAdapter(Context mContext, List<VideoInfo.DataBean.ListBean> lvData) {
        this.mContext = mContext;
        this.lvData = lvData;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return lvData == null ? 0 : lvData.size();
    }

    @Override
    public Object getItem(int position) {
        return lvData == null ? null : lvData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.listview_item_view, parent, false);
            viewHolder = new ViewHolder(view);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //数据映射
        VideoInfo.DataBean.ListBean listBean = lvData.get(position);
        viewHolder.title.setText(listBean.getTitle());
//        viewHolder.dateline.setText(listBean.getDateline());
        viewHolder.list_from.setText(" " + listBean.getList_from());
        viewHolder.replys.setText(" " + listBean.getReplys());
        //下载图片  使用Picasso类库加载网络图片，然后用Transformation方法转化,这是一款支持圆角，椭圆，圆形的RoundedImageView类库，可以生成ImageView和Drawable。
        String img_url = listBean.getImage_list();
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadius(10)
                .oval(false)
                .build();
//        Picasso.with(mContext).load(img_url).into(viewHolder.image_list);
        Picasso.with(mContext).load(img_url).fit().transform(transformation).into(viewHolder.image_list);

        /**
         * 将毫秒数转化为时间
         */
        String dateline = listBean.getDateline();
        Long time = Long.parseLong(dateline);
        double datetime = (double) time * 1000;
//        Date date = new Date();
        //将毫秒数转化为时间
//        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = format.format(datetime);
        viewHolder.dateline.setText(format1);

        return view;
    }


    class ViewHolder {
        public ImageView image_list;
        public TextView title;
        public TextView dateline;
        public TextView list_from;
        public TextView replys;

        public ViewHolder(View view) {
            view.setTag(this);
            image_list = (ImageView) view.findViewById(R.id.video_iv);
            title = (TextView) view.findViewById(R.id.video_tv_title);
            dateline = (TextView) view.findViewById(R.id.video_tv_dateline);
            list_from = (TextView) view.findViewById(R.id.video_tv_list_from);
            replys = (TextView) view.findViewById(R.id.video_tv_replys);
        }
    }
}
