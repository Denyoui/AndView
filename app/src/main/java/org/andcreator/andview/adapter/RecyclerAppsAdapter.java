package org.andcreator.andview.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import org.andcreator.andview.R;
import org.andcreator.andview.bean.RecyclerAppsBean;

import java.util.List;

public class RecyclerAppsAdapter extends RecyclerView.Adapter<RecyclerAppsAdapter.ViewHolder> {

    private Context mContext;
    private List<RecyclerAppsBean> mListApps;

    Activity activity;
    public RecyclerAppsAdapter(List<RecyclerAppsBean> listApps, Activity activity){
        mListApps = listApps;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.apps_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final RecyclerAppsBean bean = mListApps.get(i);
        Glide.with(mContext).load(bean.getIconId()).into(viewHolder.appsIcon);
        viewHolder.appsName.setText(bean.getAppName());

        viewHolder.appsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHttp(bean.getUri());
            }
        });
    }

    private void startHttp(String uri){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mListApps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView appsIcon;
        TextView appsName;

        private ViewHolder(View itemView) {
            super(itemView);

            appsIcon = itemView.findViewById(R.id.apps_icon);
            appsName = itemView.findViewById(R.id.apps_name);
        }
    }
}
