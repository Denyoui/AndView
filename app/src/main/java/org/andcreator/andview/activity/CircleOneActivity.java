package org.andcreator.andview.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.andcreator.andview.R;
import org.andcreator.andview.adapter.RecyclerCommentAdapter;
import org.andcreator.andview.bean.RecyclerCommentBean;
import org.andcreator.andview.uilt.SetTheme;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CircleOneActivity extends AppCompatActivity {

    private RecyclerView comment;
    private List<RecyclerCommentBean> mListCommentBean;

    private ImageView addImage;
    private EditText commentInput;
    private ImageView sendContent;

    private CircleImageView icon;
    private TextView name;
    private TextView time;
    private ImageView more;
    private TextView text;

    private ImageView image;

    private ImageView plusOne;
    private TextView plusNum;
    private ImageView chat;
    private TextView chatNum;
    private ImageView share;
    private TextView shareNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_circle_one);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        comment = (RecyclerView) findViewById(R.id.recycler_comment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        comment.setLayoutManager(layoutManager);
        RecyclerCommentAdapter adapter = new RecyclerCommentAdapter(listComment());
        comment.setAdapter(adapter);

        addImage = findViewById(R.id.add_image);
        commentInput = findViewById(R.id.input_comment_content);
        sendContent = findViewById(R.id.send_content);
        icon = findViewById(R.id.icon);
        name = findViewById(R.id.name);
        time = findViewById(R.id.time);
        more = findViewById(R.id.more);
        text = findViewById(R.id.text);

        image = findViewById(R.id.image);

        plusOne = findViewById(R.id.plus_one);
        plusNum = findViewById(R.id.plus_num);
        chat = findViewById(R.id.chat);
        chatNum = findViewById(R.id.chat_num);
        share = findViewById(R.id.share);
        shareNum = findViewById(R.id.share_num);

        //测试
        Glide.with(this).load(R.drawable.usericon).into(icon);
        name.setText("and");
        time.setText("一周前");
        text.setText("我是没事的时候,在无聊的时候,想的时候,到一个地方,不相同的地方,到这个地方来,来的吧,可以瞧一瞧,不一样的地方,不相同的地方,很多，很多");
        plusNum.setText("0");
        chatNum.setText("0");
        shareNum.setText("0");
        Glide.with(this).load(R.drawable.usericon).into(image);
    }

    private List<RecyclerCommentBean> listComment() {
        mListCommentBean = new ArrayList<>();

        for (int i = 0;i <10;i++){
            RecyclerCommentBean commentBean = new RecyclerCommentBean("","Andrew Google","知道这是啥不？这是信仰！Google大法好！！");
            mListCommentBean.add(commentBean);
        }

        return mListCommentBean;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

}
