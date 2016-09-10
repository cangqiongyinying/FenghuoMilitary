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

import java.util.List;

import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.bean.VideoContentInfo;

/**
 * Created by Jerry 2016/9/7 11:47
 */
public class VideoContentLvAdapter extends BaseAdapter {

    private Context mContext;
    //详情页相关栏ListView数据源
    private List<VideoContentInfo.DataBean.RelationsBean> relationsBeen;
    private LayoutInflater mInflater;

    public VideoContentLvAdapter(Context mContext, List<VideoContentInfo.DataBean.RelationsBean> relationsBeen) {
        this.mContext = mContext;
        this.relationsBeen = relationsBeen;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return relationsBeen == null ? 0 : relationsBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return relationsBeen == null ? null : relationsBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        RelationsViewHolder viewHolder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.video_content_listview_item_view, parent, false);
            viewHolder = new RelationsViewHolder(view);
        } else {
            viewHolder = (RelationsViewHolder) view.getTag();
        }

        //数据映射
        VideoContentInfo.DataBean.RelationsBean list = relationsBeen.get(position);
        viewHolder.relationsTitle.setText(list.getTitle());
        viewHolder.relationsFrom.setText(" " + list.getFrom());
        //下载图片  使用Picasso类库加载网络图片，然后用Transformation方法转化,这是一款支持圆角，椭圆，圆形的RoundedImageView类库，可以生成ImageView和Drawable。
        String img_url = list.getImage();
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadius(10)
                .oval(false)
                .build();
//        Picasso.with(mContext).load(img_url).into(viewHolder.relationsImageView);
        Picasso.with(mContext).load(img_url).fit().transform(transformation).into(viewHolder.relationsImageView);

        return view;
    }


    /**
     * 初始化视图
     */
    class RelationsViewHolder {
        public ImageView relationsImageView;
        public TextView relationsTitle;
        private TextView relationsFrom;

        public RelationsViewHolder(View view) {
            view.setTag(this);
            relationsImageView = (ImageView) view.findViewById(R.id.video_lv_iv);
            relationsTitle = (TextView) view.findViewById(R.id.video_lv_tv_title);
            relationsFrom = (TextView) view.findViewById(R.id.video_lv_tv_list_from);
        }
    }
}
