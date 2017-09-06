package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.topnews.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bean.NewsBean;
import utils.SharedPreferencesUtil;

/**
 * Created by 李英杰 on 2017/8/30.
 */

public class NewsAdapter extends BaseAdapter {

    private static final int ITEM_ONE=0;
    private static final int ITEM_TWO=1;
    private static final int TYPESUM=2;


    private Context context;
    private List<NewsBean> list;

    public NewsAdapter(Context context, List<NewsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return TYPESUM;
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2==0){
            return ITEM_ONE;
        }else {
            return ITEM_TWO;
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int itemViewType = getItemViewType(i);
        ViewHolder holder=null;
        ViewHolderTwo holderTwo=null;
        if (view==null){
            switch (itemViewType){
                case ITEM_ONE:
                    holder=new ViewHolder();
                    view= LayoutInflater.from(context).inflate(R.layout.item_view,null);
                    holder.textView=view.findViewById(R.id.tv_one_titile);
                    holder.imageView=view.findViewById(R.id.iv_one);
                    view.setTag(holder);
                    break;
                case ITEM_TWO:
                    holderTwo=new ViewHolderTwo();
                    view=LayoutInflater.from(context).inflate(R.layout.item_two_view,null);
                    holderTwo.textView_two=view.findViewById(R.id.tv_two_title);
                    holderTwo.tv_authorName_two=view.findViewById(R.id.tv_authorName);
                    holderTwo.tv_date_two=view.findViewById(R.id.tv_date);
                    view.setTag(holderTwo);
                    break;
            }
        }else {
            switch (itemViewType){
                case ITEM_ONE:
                    holder= (ViewHolder) view.getTag();
                    break;
                case ITEM_TWO:
                    holderTwo= (ViewHolderTwo) view.getTag();
                    break;
            }
        }
        final NewsBean newsBean= (NewsBean) getItem(i);
        switch (itemViewType){
            case ITEM_ONE:
                holder.textView.setText(newsBean.title);
                //ImageLoader.getInstance().displayImage(newsBean.thumbnail_pic_s,holder.imageView);
                String best = SharedPreferencesUtil.getPreferencesValue("state");
                if (best.equals("best")){
                    ImageLoader.getInstance().displayImage(newsBean.thumbnail_pic_s,holder.imageView);
                }else if (best.equals("terrible")){
                    final ViewHolder finalHolder = holder;
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ImageLoader.getInstance().displayImage(newsBean.thumbnail_pic_s, finalHolder.imageView);
                        }
                    });
                }


                break;
            case ITEM_TWO:
                holderTwo.textView_two.setText(newsBean.title);
                holderTwo.tv_authorName_two.setText(newsBean.author_name);
                holderTwo.tv_date_two.setText(newsBean.date);
                break;
        }

        return view;
    }
    static class ViewHolder {
        private TextView textView;
        private ImageView imageView;
    }
    static class  ViewHolderTwo{
        private TextView textView_two;
        private TextView tv_date_two;
        private TextView tv_authorName_two;
    }
}
