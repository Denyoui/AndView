package org.andcreator.andview.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.andcreator.andview.R;
import org.andcreator.andview.activity.ContributorActivity;
import org.andcreator.andview.bean.RecyclerContributorBean;
import org.andcreator.andview.bean.RecyclerContributorIconBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerContributorAdapter extends RecyclerView.Adapter<RecyclerContributorAdapter.MyHolder>{

    private List<RecyclerContributorBean> data;
    //保存的成员变量的LayoutInflater
    private LayoutInflater inflater;
    private Context mContext;

    //构造器传入Context来创建LayoutInflater，也可以直接传入一个LayoutInflater
    public RecyclerContributorAdapter(LayoutInflater inflater, List<RecyclerContributorBean> data){
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
        return new MyHolder(inflater.inflate(R.layout.contributor_item,parent,false));
    }

    //这里也变少了，使用Holder提供的绑定方法
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.onBind(data.get(position),mContext);
    }

    //这里没变
    @Override
    public int getItemCount() {
        return data.size();
    }

    //变化最大的地方就是这里了
    class MyHolder extends RecyclerView.ViewHolder {

        //保存自己的View引用
        private CardView card;
        private CircleImageView icon;
        private TextView name;
        private TextView motto;
        private ImageView blog;
        private ImageView github;
        private ImageView mail;
        private Button more;

        //构造器
        private MyHolder(View itemView) {
            super(itemView);
            //既然构造器已经传入了View，为什么不顺便把需要用到的View保存一份呢？
            card = itemView.findViewById(R.id.card);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            motto = itemView.findViewById(R.id.motto);
            blog = itemView.findViewById(R.id.blog);
            github = itemView.findViewById(R.id.github);
            mail = itemView.findViewById(R.id.mail);
            more = itemView.findViewById(R.id.more);
        }

        //申明一个数据绑定方法，只要求外部传入bean，绑定方式由自己决定
        void onBind(final RecyclerContributorBean bean, Context context){
            Glide.with(context).load(bean.getIcon()).into(icon);
            name.setText(bean.getName());
            motto.setText(bean.getMotto());

            card.setCardBackgroundColor(bean.getColor());

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, ContributorActivity.class);
                    intent.putExtra("icon",bean.getIcon());
                    intent.putExtra("name",bean.getName());
                    intent.putExtra("color",bean.getColor());
                    intent.putExtra("motto",bean.getMotto());
                    mContext.startActivity(intent);
                }
            });

            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, ContributorActivity.class);
                    intent.putExtra("icon",bean.getIcon());
                    intent.putExtra("name",bean.getName());
                    intent.putExtra("color",bean.getColor());
                    intent.putExtra("motto",bean.getMotto());
                    mContext.startActivity(intent);
                }
            });

            blog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startHttp(bean.getBlog());
                }
            });

            github.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startHttp(bean.getGithub());
                }
            });

            mail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendEmail(bean.getMail());
                }
            });
        }
    }

    private void sendEmail(String mail){
        // 必须明确使用mailto前缀来修饰邮件地址,如果使用
// intent.putExtra(Intent.EXTRA_EMAIL, email)，结果将匹配不到任何应用
        Uri uri = Uri.parse("mailto:"+mail);
        String[] email = {mail};
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
        intent.putExtra(Intent.EXTRA_SUBJECT, "致开发者"); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, ""); // 正文
        mContext.startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
    }


    //打开链接
    private void startHttp(String uri){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        mContext.startActivity(intent);
    }

}