package org.andcreator.andview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.andcreator.andview.R;
import org.andcreator.andview.bean.RecyclerImageBean;

import java.util.List;

import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static org.andcreator.andview.uilt.ImageUtil.drawableToBitmap;


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

        Glide.with(mContext).load(bean.getUrl()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                Bitmap bitmap = drawableToBitmap(resource);

                if (bitmap.getWidth() < bitmap.getHeight()){

//                    Glide.with(mContext).load(bean.getUrl()).override(700, 800).into(holder.image);

                    Glide.with(mContext).load(bean.getUrl()).apply(bitmapTransform(new FitCenter()).override(700, 800)).into(holder.image);

                }else if (bitmap.getWidth() > bitmap.getHeight()){

                    Glide.with(mContext).load(bean.getUrl()).apply(bitmapTransform(new FitCenter()).override(2000, 800)).into(holder.image);

//                    Glide.with(mContext).load(bean.getUrl()).override(2000, 800).into(holder.image);

                }else if (bitmap.getWidth() == bitmap.getHeight()){

                    Glide.with(mContext).load(bean.getUrl()).apply(bitmapTransform(new FitCenter()).override(800, 800)).into(holder.image);

//                    Glide.with(mContext).load(bean.getUrl()).override(800, 800).into(holder.image);

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
