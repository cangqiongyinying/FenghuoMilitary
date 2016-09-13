package phone1000.com.firemilitary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import phone1000.com.firemilitary.CustomerView.CustomerListview;
import phone1000.com.firemilitary.R;
import phone1000.com.firemilitary.bean.TuijianInfoActivityProductInfo;

/**
 * Created by shiwei on 2016/9/10.
 */
public class TujianInfoHuiFuAdpater extends BaseAdapter {
    private Context mContext;
    private List<TuijianInfoActivityProductInfo.DataBean.ReplyListBean> replayscontentlist=new ArrayList<>();

    public TujianInfoHuiFuAdpater(Context mContext, List<TuijianInfoActivityProductInfo.DataBean.ReplyListBean> replayscontentlist) {
        this.mContext = mContext;
        this.replayscontentlist = replayscontentlist;
    }

    @Override
    public int getCount() {
        return replayscontentlist==null?0:replayscontentlist.size();
    }

    @Override
    public Object getItem(int position) {
        return replayscontentlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        TuijianInfoHuiFuViewHolder tuijianInfoHuiFuViewHolder=null;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.tuijianinfo_huifu_inflater,null);
            tuijianInfoHuiFuViewHolder=new TuijianInfoHuiFuViewHolder(convertView);
            convertView.setTag(tuijianInfoHuiFuViewHolder);
        }else {
            tuijianInfoHuiFuViewHolder=(TuijianInfoHuiFuViewHolder)convertView.getTag();
        }
        TuijianInfoActivityProductInfo.DataBean.ReplyListBean replyListBean = replayscontentlist.get(position);
        if (replyListBean!=null) {
            Picasso.with(mContext).load(replyListBean.getFace()).into(tuijianInfoHuiFuViewHolder.header);
            tuijianInfoHuiFuViewHolder.nickname.setText(replyListBean.getNickname());
            tuijianInfoHuiFuViewHolder.junxian.setText("("+replyListBean.getRole_name()+")");
            tuijianInfoHuiFuViewHolder.datetime.setText(replyListBean.getDateline());
            // TODO: 2016/9/10 listview
            List<String> contentlist = replyListBean.getContent();
            ContentReplaysAdapter contentReplaysAdapter=new ContentReplaysAdapter(contentlist,mContext);
            tuijianInfoHuiFuViewHolder.contentlistview.setAdapter(contentReplaysAdapter);
            tuijianInfoHuiFuViewHolder.contentlistview.setSelector(new ColorDrawable(Color.TRANSPARENT));
            contentReplaysAdapter.notifyDataSetChanged();

            tuijianInfoHuiFuViewHolder.huifutext.setText("回复");
            tuijianInfoHuiFuViewHolder.dianzan.setImageResource(R.drawable.toolbar_btn_like_small);
            tuijianInfoHuiFuViewHolder.huifuimage.setImageResource(R.drawable.toolbar_btn_coments_small);
            tuijianInfoHuiFuViewHolder.jubao.setImageResource(R.drawable.article_btn_group_report);
        }
        return convertView;
    }
    class TuijianInfoHuiFuViewHolder{
        private RoundedImageView header;
        private TextView nickname;
        private TextView junxian;
        private TextView datetime;
        private CustomerListview contentlistview;
        private ImageButton jubao;
        private ImageButton dianzan;
        private TextView huifutext;
        private ImageButton huifuimage;
        TuijianInfoHuiFuViewHolder(View view){
            header=(RoundedImageView)view.findViewById(R.id.tuijianinfo_huifu_header);
            nickname=(TextView)view.findViewById(R.id.tuijianinfo_huifu_nickname);
            junxian=(TextView)view.findViewById(R.id.tuijianinfo_huifu_junxian);
            datetime=(TextView)view.findViewById(R.id.tuijianinfo_huifu_dateline);
            contentlistview=(CustomerListview)view.findViewById(R.id.tuijianinfo_huifu_content);
            huifutext=(TextView)view.findViewById(R.id.tuijianinfo_huifu_texthuifu);
            jubao=(ImageButton) view.findViewById(R.id.tuijianinfo_huifu_jubao);
            dianzan=(ImageButton) view.findViewById(R.id.tuijianinfo_huifu_dianzan);
            huifuimage=(ImageButton) view.findViewById(R.id.tuijianinfo_huifu_imagehuifu);

        }
    }

    class ContentReplaysAdapter extends BaseAdapter{
        private List<String> contentList=new ArrayList<>();
        private Context mContext;

        public ContentReplaysAdapter(List<String> contentList, Context mContext) {
            this.contentList = contentList;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return contentList.size();
        }

        @Override
        public Object getItem(int position) {
            return contentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView convertView1 = new TextView(mContext);
            convertView1.setTextSize(14);
            convertView1.setTextColor(Color.BLACK);
            convertView=convertView1;
            convertView1.setText(contentList.get(position));
            return convertView;
        }
    }
}
