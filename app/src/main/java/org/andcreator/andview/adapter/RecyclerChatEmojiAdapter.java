package org.andcreator.andview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.andcreator.andview.R;
import org.andcreator.andview.bean.RecyclerChatEmojiBean;

import java.util.List;

/**
 * Created by Andrew on 2018/4/11.
 */

public class RecyclerChatEmojiAdapter extends RecyclerView.Adapter<RecyclerChatEmojiAdapter.ViewHolder> {

    private Context mContext;
    private List<RecyclerChatEmojiBean> mChatEmoji;

    public RecyclerChatEmojiAdapter(List<RecyclerChatEmojiBean> chatEmoji){
        mChatEmoji = chatEmoji;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_emoji_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecyclerChatEmojiBean bean = mChatEmoji.get(position);
        holder.textView.setText(emojiString(bean.getUnicodeJoy()));
    }

    @Override
    public int getItemCount() {
        return mChatEmoji.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.emoji_text);
        }
    }

    private String emojiString(int unicodeJoy){
        String emojiString = getEmojiStringByUnicode(unicodeJoy);
        return emojiString;
    }

    private String getEmojiStringByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

}
