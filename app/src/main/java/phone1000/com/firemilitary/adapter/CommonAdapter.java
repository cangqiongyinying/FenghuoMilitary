package phone1000.com.firemilitary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.TimeFormatTool.TimeFormatTool;
import phone1000.com.firemilitary.bean.XinxiKongProductInfo;

/**
 * Created by shiwei on 2016/9/8.
 */
public class CommonAdapter extends BaseAdapter {
    private List<XinxiKongProductInfo.DataBean.ListBean> xinxinkonglist;
    private Context mContext;

    public CommonAdapter(List<XinxiKongProductInfo.DataBean.ListBean> xinxinkonglist, Context mContext) {
        this.xinxinkonglist = xinxinkonglist;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return xinxinkonglist.size();
    }

    @Override
    public Object getItem(int position) {
        return xinxinkonglist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 viewHolder1=null;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.shouyefragment_inflater1_putolistview, null);
            viewHolder1 = new ViewHolder1(convertView);
            convertView.setTag(viewHolder1);
        }else {
            viewHolder1=(ViewHolder1)convertView.getTag();
        }
        XinxiKongProductInfo.DataBean.ListBean listBean = xinxinkonglist.get(position);
        viewHolder1.title.setText(listBean.getTitle());
        long time = TimeFormatTool.getTime(Integer.valueOf(listBean.getDateline()));
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
        viewHolder1.source.setText(listBean.getList_from());
        viewHolder1.reply.setText(listBean.getReplys() + "");
        Picasso.with(mContext).load(listBean.getImage_list()).into(viewHolder1.imageView);
        return convertView;
    }

    class ViewHolder1 {
        private TextView title;
        private TextView type;
        private TextView source;
        private TextView reply;
        private RoundedImageView imageView;

        ViewHolder1(View view) {
            title = (TextView) view.findViewById(R.id.shouyefragment_inflater1_title);
            type = (TextView) view.findViewById(R.id.shouyefragment_inflater1_type);
            source = (TextView) view.findViewById(R.id.shouyefragment_inflater1_source);
            reply = (TextView) view.findViewById(R.id.shouyefragment_inflater1_replycount);
            imageView = (RoundedImageView) view.findViewById(R.id.shouyefragment_inflater1_imageview);
        }
    }
}
