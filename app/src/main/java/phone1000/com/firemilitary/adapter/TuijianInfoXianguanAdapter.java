package phone1000.com.firemilitary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.bean.TuijianInfoActivityProductInfo;

/**
 * Created by shiwei on 2016/9/10.
 */
public class TuijianInfoXianguanAdapter extends BaseAdapter {
    private List<TuijianInfoActivityProductInfo.DataBean.RelationsBean> relativecontentlist=new ArrayList<>();
    private Context mContext;

    public TuijianInfoXianguanAdapter(List<TuijianInfoActivityProductInfo.DataBean.RelationsBean> relativecontentlist, Context mContext) {
        this.relativecontentlist = relativecontentlist;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return relativecontentlist.size();
    }

    @Override
    public Object getItem(int position) {
        return relativecontentlist.get(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (!relativecontentlist.get(position).getTid().contentEquals("广告")){
            return 2;
        }
        return super.getItemViewType(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        TuijianRelativeViewHolder tuijianRelativeViewHolder=null;
        if (convertView==null){
            switch (itemViewType){
                case 2:
                    convertView= layoutInflater.inflate(R.layout.tujianinfo_relative_inflater,null);
                    tuijianRelativeViewHolder=new TuijianRelativeViewHolder(convertView);
                    convertView.setTag(tuijianRelativeViewHolder);
                    break;
            }

        }else {
            switch (itemViewType){
                case 2:
                    tuijianRelativeViewHolder= (TuijianRelativeViewHolder) convertView.getTag();
                    break;
            }
        }
        switch (itemViewType){
            case 2:
                TuijianInfoActivityProductInfo.DataBean.RelationsBean relationsBean = relativecontentlist.get(position);
                tuijianRelativeViewHolder.texttitle.setText(relationsBean.getTitle());
                tuijianRelativeViewHolder.from.setText(relationsBean.getFrom());
                Picasso.with(mContext).load(relationsBean.getImage()).into(tuijianRelativeViewHolder.imageView);
                break;
        }
        return convertView;
    }
    class TuijianRelativeViewHolder{
        private TextView texttitle;
        private TextView from;
        private ImageView imageView;
        TuijianRelativeViewHolder(View view){
            texttitle=(TextView)view.findViewById(R.id.tuijianinfo_relative_title);
            from=(TextView)view.findViewById(R.id.tuijianinfo_relative_text);
            imageView=(ImageView)view.findViewById(R.id.tuijianinfo_relative_image);
        }
    }
}
