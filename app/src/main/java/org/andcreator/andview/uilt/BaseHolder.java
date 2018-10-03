package org.andcreator.andview.uilt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by liuj on 2016/12/5.
 * 基础的ViewHolder
 */

public class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected int id;
    protected LItemTouchHelper helper;
    protected Context context;
    protected boolean canSwipe = false;

    public BaseHolder(View itemView) {
        super(itemView);
        setContext(itemView.getContext());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setHelper(LItemTouchHelper helper) {
        this.helper = helper;
    }

    public boolean canSwipe(){
        return canSwipe;
    }

    @Override
    public void onClick(View view) {
        if(helper!=null)
            helper.onItemViewClick(this,view);
    }
}
