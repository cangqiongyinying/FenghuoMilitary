package phone1000.com.firemilitary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.bean.VideoContentInfo;

/**
 * Created by Jerry 2016/9/7 16:25
 */
public class VideoContentDiscussLvAdapter extends BaseAdapter {

    private Context mContext;
    //详情全部评论栏ListView数据源
    private List<VideoContentInfo.DataBean.ReplyListBean> replyListBeen;
    private LayoutInflater mInflater;

    public VideoContentDiscussLvAdapter(Context mContext, List<VideoContentInfo.DataBean.ReplyListBean> replyListBeen) {
        this.mContext = mContext;
        this.replyListBeen = replyListBeen;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return replyListBeen == null ? 0 : replyListBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return replyListBeen == null ? null : replyListBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ReplyListViewHolder replyListViewHolder = null;
        if (view == null){
            view = mInflater.inflate(R.layout.video_content_discuss_listview_item,parent, false);
            replyListViewHolder = new ReplyListViewHolder(view);
        }else {
            replyListViewHolder = (ReplyListViewHolder) view.getTag();
        }
        //数据映射
        final VideoContentInfo.DataBean.ReplyListBean list = replyListBeen.get(position);
        replyListViewHolder.nickname.setText(list.getNickname());
        replyListViewHolder.role_name.setText("(" + list.getRole_name() + ")");
        replyListViewHolder.dateline.setText(list.getDateline());
        replyListViewHolder.content.setText(list.getContent().get(0));
        //下载图片
        String img_url = list.getFace();
        Picasso.with(mContext).load(img_url).into(replyListViewHolder.face);
        return view;
    }

    class ReplyListViewHolder{
        public CircleImageView face;
        public TextView nickname;
        public TextView role_name;
        public TextView dateline;
        public TextView content;

        public ReplyListViewHolder(View view) {
            view.setTag(this);
            face = (CircleImageView) view.findViewById(R.id.discuss_lv_civ);
            nickname = (TextView) view.findViewById(R.id.discuss_lv_nickname);
            role_name = (TextView) view.findViewById(R.id.discuss_lv_role_name);
            dateline = (TextView) view.findViewById(R.id.discuss_lv_dateline);
            content = (TextView) view.findViewById(R.id.discuss_lv_content);
        }

    }


}
