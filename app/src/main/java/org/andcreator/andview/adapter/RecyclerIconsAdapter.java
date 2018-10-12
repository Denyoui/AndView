package org.andcreator.andview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.andcreator.andview.R;
import org.andcreator.andview.bean.RecyclerIconsBean;

import java.util.List;

public class RecyclerIconsAdapter extends RecyclerView.Adapter<RecyclerIconsAdapter.ViewHolder> {

    private Context mContext;
    private List<RecyclerIconsBean> mListCard;

    public RecyclerIconsAdapter(List<RecyclerIconsBean> listCard){
        mListCard = listCard;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.icons_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RecyclerIconsBean iconsBean = mListCard.get(position);
        holder.name.setText(iconsBean.getName());
        Glide.with(mContext).load(R.drawable.img).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return mListCard.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            icon = itemView.findViewById(R.id.icon);
        }
    }

}
