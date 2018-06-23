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
import org.andcreator.andview.bean.RecyclerCardBen;

import java.util.List;

public class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.ViewHolder> {

    private Context mContext;
    private List<RecyclerCardBen> mListCard;

    public RecyclerCardAdapter(List<RecyclerCardBen> listCard){
        mListCard = listCard;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RecyclerCardBen cardBen = mListCard.get(position);
        holder.position.setText(cardBen.getPosition()+"");
        Glide.with(mContext).load(R.drawable.img).into(holder.background);
    }

    @Override
    public int getItemCount() {
        return mListCard.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView position;
        ImageView background;

        public ViewHolder(View itemView) {
            super(itemView);

            position = itemView.findViewById(R.id.position);
            background = itemView.findViewById(R.id.background);
        }
    }
}
