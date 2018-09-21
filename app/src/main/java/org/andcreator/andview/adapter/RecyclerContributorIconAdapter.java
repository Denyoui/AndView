package org.andcreator.andview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.andcreator.andview.R;
import org.andcreator.andview.bean.RecyclerContributorIconBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerContributorIconAdapter extends RecyclerView.Adapter<RecyclerContributorIconAdapter.MyHolder>{

    private List<RecyclerContributorIconBean> data;
    //保存的成员变量的LayoutInflater
    private LayoutInflater inflater;
    private Context mContext;

    //构造器传入Context来创建LayoutInflater，也可以直接传入一个LayoutInflater
    public RecyclerContributorIconAdapter(LayoutInflater inflater, List<RecyclerContributorIconBean> data){
        this.data = data;
        this.inflater = inflater;
    }

    //这里代码变少了，直接使用MyHolder的静态构造方法
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        return MyHolder.create(inflater,parent);
    }

    //这里也变少了，使用Holder提供的绑定方法
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.onBind(data.get(position),mContext);
    }

    //这里没变
    @Override
    public int getItemCount() {
        return data.size();
    }

    //变化最大的地方就是这里了
    static class MyHolder extends RecyclerView.ViewHolder {

        //静态构造方法，传入环境参数，内部构造，封装起来，防止传入不合法的布局id
         static MyHolder create(LayoutInflater inflater,ViewGroup parent){
            return new MyHolder(inflater.inflate(R.layout.contributor_icon_item,parent,false));
         }

        //保存自己的View引用
        private CircleImageView icon;

        //构造器
        private MyHolder(View itemView) {
            super(itemView);
            //既然构造器已经传入了View，为什么不顺便把需要用到的View保存一份呢？
            icon = itemView.findViewById(R.id.icon);
        }

        //申明一个数据绑定方法，只要求外部传入bean，绑定方式由自己决定
        void onBind(RecyclerContributorIconBean bean, Context context){
            Glide.with(context).load(bean.getIcon()).into(icon);
        }
    }

}