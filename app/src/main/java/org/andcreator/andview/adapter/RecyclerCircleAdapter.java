package org.andcreator.andview.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import org.andcreator.andview.R;
import org.andcreator.andview.activity.CircleManyActivity;
import org.andcreator.andview.activity.CircleOneActivity;
import org.andcreator.andview.bean.RecyclerCircleBean;
import org.andcreator.andview.bean.RecyclerImageBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerCircleAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<RecyclerCircleBean> mListImage;
    private List<RecyclerImageBean> imageUrl;

    public static final int VIEW_TYPE_ONE = 1;
    public static final int VIEW_TYPE_TWO = 2;

    public RecyclerCircleAdapter(List<RecyclerCircleBean> listImage){
        mListImage = listImage;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType){
            case VIEW_TYPE_ONE:
                viewHolder = new ViewHolderOne(LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_one_item, parent, false));
                break;
            case VIEW_TYPE_TWO:
                viewHolder = new ViewHolderTwo(LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_many_item, parent, false));
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//        holder.setIsRecyclable(false);

        RecyclerCircleBean imageManyBean = mListImage.get(position);

        imageUrl = imageManyBean.getImageUrl();

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_ONE:
                ViewHolderOne holderOne = (ViewHolderOne) holder;
//                holderOne.participate.setText(imageManyBean.getParticipate());
                holderOne.time.setText(imageManyBean.getTime());
                Glide.with(mContext).load(R.drawable.usericon).into(holderOne.icon);

                holderOne.name.setText(imageManyBean.getUserName());
                holderOne.text.setText(imageManyBean.getUserMessage());
                holderOne.chatNum.setText(imageManyBean.getChatNum()+"");
                holderOne.plusNum.setText(imageManyBean.getPlusNum()+"");
                holderOne.shareNum.setText(imageManyBean.getShareNum()+"");

                RecyclerImageBean bean = imageUrl.get(0);
                Glide.with(mContext).load(bean.getUrl()).into(holderOne.image);

                holderOne.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.startActivity(new Intent(mContext, CircleOneActivity.class));
                    }
                });

                break;
            case VIEW_TYPE_TWO:
                ViewHolderTwo holderTwo = (ViewHolderTwo) holder;

                Glide.with(mContext).load(R.drawable.usericon).into(holderTwo.icon);
                holderTwo.name.setText(imageManyBean.getUserName());
                holderTwo.text.setText(imageManyBean.getUserMessage());
//                holderTwo.participate.setText(imageManyBean.getParticipate());
                holderTwo.time.setText(imageManyBean.getTime());
                holderTwo.chatNum.setText(imageManyBean.getChatNum()+"");
                holderTwo.plusNum.setText(imageManyBean.getPlusNum()+"");
                holderTwo.shareNum.setText(imageManyBean.getShareNum()+"");


                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                holderTwo.gallery.setLayoutManager(layoutManager);

                RecyclerImageAdapter recyclerInfoImageAdapter = new RecyclerImageAdapter(imageUrl);
                holderTwo.gallery.setAdapter(recyclerInfoImageAdapter);

                holderTwo.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.startActivity(new Intent(mContext, CircleManyActivity.class));
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mListImage.size();
    }

    @Override
    public int getItemViewType(int position) {
        RecyclerCircleBean imageManyBean = mListImage.get(position);
        return imageManyBean.getViewType();
    }

    class ViewHolderOne extends RecyclerView.ViewHolder{

        CircleImageView icon;
        TextView name;
        TextView participate;
        TextView time;
        TextView text;
        ImageView more;
        ImageView image;
        ImageView chat;
        ImageView plusOne;
        ImageView share;
        TextView chatNum;
        TextView plusNum;
        TextView shareNum;

        public ViewHolderOne(View itemView) {
            super(itemView);

            icon = (CircleImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            text = (TextView) itemView.findViewById(R.id.text);
            more = (ImageView) itemView.findViewById(R.id.more);
            image = (ImageView) itemView.findViewById(R.id.image);
            chat = (ImageView) itemView.findViewById(R.id.chat);
            plusOne = (ImageView) itemView.findViewById(R.id.plus_one);
            share = (ImageView) itemView.findViewById(R.id.share);
            chatNum = (TextView) itemView.findViewById(R.id.chat_num);
            plusNum = (TextView) itemView.findViewById(R.id.plus_num);
            shareNum = (TextView) itemView.findViewById(R.id.share_num);

        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder{

        CircleImageView icon;
        TextView name;
        TextView participate;
        TextView time;
        TextView text;
        ImageView more;
        RecyclerView gallery;
        ImageView chat;
        ImageView plusOne;
        ImageView share;
        TextView chatNum;
        TextView plusNum;
        TextView shareNum;

        public ViewHolderTwo(View itemView) {
            super(itemView);

            icon = (CircleImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            text = (TextView) itemView.findViewById(R.id.text);
            more = (ImageView) itemView.findViewById(R.id.more);
            gallery = (RecyclerView) itemView.findViewById(R.id.gallery);
            chat = (ImageView) itemView.findViewById(R.id.chat);
            plusOne = (ImageView) itemView.findViewById(R.id.plus_one);
            share = (ImageView) itemView.findViewById(R.id.share);
            chatNum = (TextView) itemView.findViewById(R.id.chat_num);
            plusNum = (TextView) itemView.findViewById(R.id.plus_num);
            shareNum = (TextView) itemView.findViewById(R.id.share_num);

        }
    }
}
