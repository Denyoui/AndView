package org.andcreator.andview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.andcreator.andview.R;
import org.andcreator.andview.bean.RecyclerImageBean;

import java.util.List;


public class RecyclerImageAdapter extends RecyclerView.Adapter<RecyclerImageAdapter.ViewHolder> {

    private Context mContext;
    private List<RecyclerImageBean> mListImage;

    public RecyclerImageAdapter(List<RecyclerImageBean> listImage){
        mListImage = listImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,parent,false);
        RecyclerImageAdapter.ViewHolder holder = new RecyclerImageAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final RecyclerImageBean bean = mListImage.get(position);

        Glide.with(mContext)
                .load(bean.getUrl())
                .asBitmap()//强制Glide返回一个Bitmap对象
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
//                        width = bitmap.getWidth();
//                        height = bitmap.getHeight();

                        if (bitmap.getWidth() < bitmap.getHeight()){

                            Glide.with(mContext).load(bean.getUrl()).override(700, 800).into(holder.image);

                        }else if (bitmap.getWidth() > bitmap.getHeight()){

                            Glide.with(mContext).load(bean.getUrl()).override(2000, 800).into(holder.image);

                        }else if (bitmap.getWidth() == bitmap.getHeight()){

                            Glide.with(mContext).load(bean.getUrl()).override(800, 800).into(holder.image);

                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mListImage.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
