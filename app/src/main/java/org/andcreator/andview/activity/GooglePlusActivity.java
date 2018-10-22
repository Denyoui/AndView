package org.andcreator.andview.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import org.andcreator.andview.R;
import org.andcreator.andview.adapter.RecyclerCircleAdapter;
import org.andcreator.andview.bean.RecyclerCircleBean;
import org.andcreator.andview.bean.RecyclerImageBean;
import org.andcreator.andview.uilt.SetTheme;

import java.util.ArrayList;
import java.util.List;

public class GooglePlusActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerCircleAdapter adapter;
    private List<RecyclerCircleBean> mListImage;
    private List<RecyclerImageBean> imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_google_plus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = findViewById(R.id.circle_recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerCircleAdapter(listImage());
        recyclerView.setAdapter(adapter);
    }

    private List<RecyclerCircleBean> listImage() {
        mListImage = new ArrayList<>();
        imageUrl = new ArrayList<>();

        RecyclerImageBean h = new RecyclerImageBean(R.drawable.image_a);
        ff.add(h);
        RecyclerImageBean b = new RecyclerImageBean(R.drawable.image_c);
        gg.add(b);
        RecyclerImageBean c = new RecyclerImageBean(R.drawable.androidoreo);
        kk.add(c);
        RecyclerImageBean d = new RecyclerImageBean(R.drawable.image_a);
        hh.add(d);
        RecyclerImageBean e = new RecyclerImageBean(R.drawable.image_b);
        hh.add(e);
        RecyclerImageBean f = new RecyclerImageBean(R.drawable.image_c);
        hh.add(f);
        RecyclerImageBean g = new RecyclerImageBean(R.drawable.androidoreo);
        hh.add(g);

        for (int a = 0;a < 26;a++){

            if (a == 0){
                imageUrl = ff;
            }else if (a == 1){
                imageUrl = gg;
            }else if (a == 2){
                imageUrl = kk;
            }else if (a == 3){
                imageUrl = hh;
            }else if (a == 4){
                imageUrl = kk;
            }else if (a == 5){
                imageUrl = gg;
            }else if (a == 6){
                imageUrl = hh;
            }else if (a == 7){
                imageUrl = kk;
            }else if (a == 8){
                imageUrl = ff;
            }else if (a == 9){
                imageUrl = ff;
            }else {
                imageUrl = hh;
            }

            if (imageUrl.size()>1){ //如果是一个图集放到List

                RecyclerCircleBean imageManyBean = new RecyclerCircleBean(2,"8343034", "", "and", "", "上帝的恩典绝不改变他公义的律法，而是赐给我们力量去遵行。十字架的精义是永恒的慈爱，而十字架的根基则是永恒的公义", "1周前", imageUrl, "", 2, 3, 3, false);
                mListImage.add(imageManyBean);

            }else { //如果只1有一张图

                RecyclerCircleBean imageManyBean = new RecyclerCircleBean(1,"8343034", "", "and", "", "上帝的恩典绝不改变他公义的律法，而是赐给我们力量去遵行。十字架的精义是永恒的慈爱，而十字架的根基则是永恒的公义", "1周前", imageUrl, "", 2, 3, 3, false);
                mListImage.add(imageManyBean);

            }

        }

        return mListImage;
    }

    private List<RecyclerImageBean> ff = new ArrayList<>();
    private List<RecyclerImageBean> gg = new ArrayList<>();
    private List<RecyclerImageBean> kk = new ArrayList<>();
    private List<RecyclerImageBean> hh = new ArrayList<>();
}
