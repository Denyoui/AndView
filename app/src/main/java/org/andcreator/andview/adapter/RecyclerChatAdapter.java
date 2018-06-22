package org.andcreator.andview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.andcreator.andview.R;
import org.andcreator.andview.bean.RecyclerChatBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andrew on 2018/4/7.
 */

public class RecyclerChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<RecyclerChatBean> mListChat;
    public static final int VIEW_TYPE_ONE = 1;
    public static final int VIEW_TYPE_TWO = 2;

    public RecyclerChatAdapter(List<RecyclerChatBean> listChat){
        mListChat = listChat;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType){
            case VIEW_TYPE_ONE:
                viewHolder = new ViewHolderLeft(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_module_left, parent, false));
                break;
            case VIEW_TYPE_TWO:
                viewHolder = new ViewHolderRight(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_module_right, parent, false));
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RecyclerChatBean recyclerChatBean = mListChat.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_ONE:

                ViewHolderLeft viewHolderLeft = (ViewHolderLeft) holder;

                Glide.with(mContext).load(recyclerChatBean.getIcon()).into(viewHolderLeft.icon);
                viewHolderLeft.textView.setText(recyclerChatBean.getText());

                break;
            case VIEW_TYPE_TWO:

                ViewHolderRight viewHolderRight = (ViewHolderRight) holder;
                viewHolderRight.textView.setTextSize(recyclerChatBean.getTextSize());
                viewHolderRight.textView.setText(recyclerChatBean.getText());

                break;
        }
    }

    @Override
    public int getItemCount() {
        return mListChat.size();
    }

    @Override
    public int getItemViewType(int position) {
        RecyclerChatBean recyclerChatBean = mListChat.get(position);
        return recyclerChatBean.getViewType();
    }

    class ViewHolderLeft extends RecyclerView.ViewHolder{

        CircleImageView icon;
        TextView textView;
        public ViewHolderLeft(View itemView) {
            super(itemView);

            icon = (CircleImageView) itemView.findViewById(R.id.icon);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
    class ViewHolderRight extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolderRight(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
