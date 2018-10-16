package org.andcreator.andview.adapter;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.andcreator.andview.R;
import org.andcreator.andview.bean.RecyclerOpenAppsBean;

import java.util.List;


public class RecyclerOpenAppsAdapter extends RecyclerView.Adapter<RecyclerOpenAppsAdapter.ViewHolder> {


    private Context mContext;
    private List<RecyclerOpenAppsBean> mListApps;

    public RecyclerOpenAppsAdapter(List<RecyclerOpenAppsBean> listApps){
        mListApps = listApps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.open_apps_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final RecyclerOpenAppsBean bean = mListApps.get(i);

        if (bean.getWebsite().equals("")){
            viewHolder.website.setVisibility(View.GONE);
        }else if (viewHolder.website.getVisibility() == View.GONE){
            viewHolder.website.setVisibility(View.VISIBLE);
        }

        Glide.with(mContext).load(bean.getIcon()).into(viewHolder.icon);
        viewHolder.name.setText(bean.getName());
        viewHolder.introduction.setText(bean.getIntroduction());
        viewHolder.website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHttp(bean.getWebsite());
            }
        });

        viewHolder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAppStore(bean.getDownload());
            }
        });
    }

    //打开链接
    private void startHttp(String uri){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        mContext.startActivity(intent);
    }

    private void openAppStore(String packageName){
        try{
            Uri uri = Uri.parse("market://details?id="+packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }catch(Exception e){
            Toast.makeText(mContext, "没有可用的应用商店", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListApps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView name;
        TextView introduction;
        TextView website;
        TextView download;

        private ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            icon = itemView.findViewById(R.id.icon);
            introduction = itemView.findViewById(R.id.introduction);
            website = itemView.findViewById(R.id.website);
            download = itemView.findViewById(R.id.download);
        }
    }
}
