package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwie.topnews.R;

import java.util.List;

import bean.TypeBean;

/**
 * Created by 李英杰 on 2017/9/4.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<TypeBean> list;
    private OnItemClickListenner onItemClickListenner;

    public RecyclerViewAdapter(Context context,List<TypeBean> list){
        this.context=context;
        this.list=list;
        }

    /**
     * 用于创建ViewHolder，和View绑定
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.recycle_view,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListenner.onItemClickListenner((Integer)view.getTag(),view );
            }
        });

        return myViewHolder;
    }

    /**
     * 用于处理逻辑，绘制UI数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
     holder.tv_recycleItem.setText(list.get(position).type);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_recycleItem;
        private CheckBox cb_cycleCb;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_recycleItem=itemView.findViewById(R.id.tv_recycleItem);
            cb_cycleCb=itemView.findViewById(R.id.cb_cycleCb);
        }
    }

    /**
     * 供外部调用
     * @param onItemClickListenner
     */
    public void setOnItemClickListenner(OnItemClickListenner onItemClickListenner){
        this.onItemClickListenner=onItemClickListenner;
    }

    /**
     * 定义接口
     */
    public interface OnItemClickListenner{
        void onItemClickListenner(int position,View view);
    }
}