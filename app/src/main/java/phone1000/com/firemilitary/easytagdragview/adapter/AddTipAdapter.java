package phone1000.com.firemilitary.easytagdragview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.easytagdragview.bean.SimpleTitleTip;
import phone1000.com.firemilitary.easytagdragview.bean.Tip;

/**
 * Created by Administrator on 2016/5/27 0027.
 */
public class AddTipAdapter extends BaseAdapter{
    private Context mContext;
    private List<Tip> tips;
    public AddTipAdapter(Context mContext) {
        this.mContext=mContext;
    }

    @Override
    public int getCount() {
        if(tips ==null){
            return 0;
        }
        return tips.size();
    }

    @Override
    public Object getItem(int position) {
        return tips.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =View.inflate(parent.getContext(), R.layout.view_add_item,null);
        ((TextView)view.findViewById(R.id.tagview_title)).setText((((SimpleTitleTip)(tips.get(position))).getTip()));
        Picasso.with(mContext).load(((SimpleTitleTip)(tips.get(position))).getImg()).into((RoundedImageView)view.findViewById(R.id.tagview_addtitleimage));
        return view;
    }

    public List<Tip> getData() {
        return tips;
    }

    public void setData(List<Tip> iDragEntities) {
        this.tips = iDragEntities;
    }
    public void refreshData(){
        notifyDataSetChanged();
    }
}
