package org.andcreator.andview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.andcreator.andview.R;
import org.andcreator.andview.bean.RecyclerContributorIconBean;
import org.andcreator.andview.bean.RecyclerMainLayoutBean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerMainLayoutAdapter extends RecyclerView.Adapter<RecyclerMainLayoutAdapter.MyHolder>{

    private List<RecyclerMainLayoutBean> data;
    private List<RecyclerContributorIconBean> contributorList;
    //保存的成员变量的LayoutInflater
    private LayoutInflater inflater;
    private Context mContext;

    //构造器传入Context来创建LayoutInflater，也可以直接传入一个LayoutInflater
    public RecyclerMainLayoutAdapter(LayoutInflater inflater, List<RecyclerMainLayoutBean> data){
        this.data = data;
        this.inflater = inflater;
    }

    //这里代码变少了，直接使用MyHolder的静态构造方法
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        return new MyHolder(inflater.inflate(R.layout.main_layout_item,parent,false));
    }

    //这里也变少了，使用Holder提供的绑定方法
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.onBind(data.get(position),mContext,inflater,contributorList);
    }

    //这里没变
    @Override
    public int getItemCount() {
        return data.size();
    }

    //变化最大的地方就是这里了
    class MyHolder extends RecyclerView.ViewHolder {

        //保存自己的View引用
        private CardView cardView;
        private TextView title;
        private TextView content;
        private RecyclerView contributor;

        //构造器
        private MyHolder(View itemView) {
            super(itemView);
            //既然构造器已经传入了View，为什么不顺便把需要用到的View保存一份呢？
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            contributor = itemView.findViewById(R.id.recycler_contributor);
            cardView = itemView.findViewById(R.id.card_a);
        }

        //申明一个数据绑定方法，只要求外部传入bean，绑定方式由自己决定
        void onBind(RecyclerMainLayoutBean bean,Context context,LayoutInflater layoutInflater,List<RecyclerContributorIconBean> contributorList){
            title.setText(bean.getTitle());
            content.setText(bean.getDescription());
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            contributor.setLayoutManager(layoutManager);
            RecyclerContributorIconAdapter adapter = new RecyclerContributorIconAdapter(layoutInflater,loadData(contributorList,bean));
            contributor.setAdapter(adapter);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null){
                        //传参
                        clickListener.onClick(getAdapterPosition());
                    }
                }
            });
        }

        private List<RecyclerContributorIconBean> loadData(List<RecyclerContributorIconBean> contributorList, RecyclerMainLayoutBean bean) {

            contributorList = new ArrayList<>();
            for (int i = 0;i < bean.getContributorList().length;i++){

                contributorList.add(new RecyclerContributorIconBean(bean.getContributorList()[i]));
            }

            return contributorList;
        }
    }

    //回调接口
    public interface OnItemClickListener{
        //这里的参数可以随意修改,我们以传入text为例
        void onClick(int position);
    }

    private OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    /**
     * 处理bitmap为高斯模糊图片
     * @param context 上下文
     * @param image   图片源
     * @param radius  模糊程度 0到25之间
     * @param scale   图片缩放比例, 该值越小越节省内存,模糊程度越敏感,0到1之间
     * @return 模糊的图片
     */
    public static Bitmap blurBitmap(Context context, Bitmap image, float radius, float scale) {

        Log.e("233333333",radius+"");
        // 计算图片缩小后的长宽
        int width = Math.round(image.getWidth() * scale);
        int height = Math.round(image.getHeight() * scale);
        // 将缩小后的图片做为预渲染的图片。
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        // 创建一张渲染后的输出图片。
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(radius);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);
        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }
}