package org.andcreator.andview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.andcreator.andview.R;
import org.andcreator.andview.bean.RecyclerCommentBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerCommentAdapter extends RecyclerView.Adapter<RecyclerCommentAdapter.ViewHolder> {

    private Context mContext;
    private List<RecyclerCommentBean> mListComment;

    public RecyclerCommentAdapter(List<RecyclerCommentBean> listComment){
        mListComment = listComment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        RecyclerCommentAdapter.ViewHolder holder = new RecyclerCommentAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RecyclerCommentBean commentBean = mListComment.get(position);

        Glide.with(mContext).load(R.drawable.usericon).into(holder.icon);
        holder.title.setText(commentBean.getTitle());
        holder.text.setText(commentBean.getText());
    }

    @Override
    public int getItemCount() {
        return mListComment.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView icon;
        TextView title;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);

            icon = (CircleImageView) itemView.findViewById(R.id.notification_icon);
            title = (TextView) itemView.findViewById(R.id.notification_title);
            text = (TextView) itemView.findViewById(R.id.notification_text);
        }
    }
}
