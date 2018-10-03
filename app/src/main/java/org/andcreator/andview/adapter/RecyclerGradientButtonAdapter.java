package org.andcreator.andview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.andcreator.andview.R;
import org.andcreator.andview.bean.RecyclerGradientButtonBean;
import org.andcreator.andview.uilt.Gradient;
import org.andcreator.andview.view.Rounded;

import java.util.List;

public class RecyclerGradientButtonAdapter extends RecyclerView.Adapter<RecyclerGradientButtonAdapter.ViewHolder> {

    private Context mContext;
    private List<RecyclerGradientButtonBean> mListApps;
    private int mStartColor;
    private int mEndColor;

    public RecyclerGradientButtonAdapter(List<RecyclerGradientButtonBean> listApps,int startColor,int endColor){
        mListApps = listApps;
        mStartColor = startColor;
        mEndColor = endColor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gradient_button_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RecyclerGradientButtonBean bean = mListApps.get(i);
        viewHolder.groupName.setText(bean.getGroupName());
        viewHolder.rounded.setColor(
                Gradient.getGradient(mStartColor,mEndColor,mListApps.size()+1,i+1), Gradient.getGradient(mStartColor,mEndColor,mListApps.size()+1,i+2));
    }

    @Override
    public int getItemCount() {
        return mListApps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView groupName;
        Rounded rounded;

        private ViewHolder(View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.group_name);
            rounded = itemView.findViewById(R.id.group);
        }
    }
}
