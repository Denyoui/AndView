package org.andcreator.andview.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.andcreator.andview.R;
import org.andcreator.andview.adapter.RecyclerCardAdapter;
import org.andcreator.andview.bean.RecyclerCardBen;
import org.andcreator.andview.uilt.SetTheme;
import org.andcreator.andview.uilt.SmartLayoutManager;
import org.andcreator.andview.uilt.SmartSnapHelper;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private List<RecyclerCardBen> mListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        setContentView(R.layout.activity_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView cardRecycler = findViewById(R.id.card_recycler);
        SmartLayoutManager smartLayoutManager = new SmartLayoutManager();
        cardRecycler.setLayoutManager(smartLayoutManager);
        new SmartSnapHelper().attachToRecyclerView(cardRecycler);;
        RecyclerCardAdapter adapter = new RecyclerCardAdapter(listCard());
        cardRecycler.setAdapter(adapter);

    }

    private List<RecyclerCardBen> listCard() {
        mListBean = new ArrayList<>();

        for (int i = 0; i< 20; i++){
            mListBean.add(new RecyclerCardBen(i));
        }

        return mListBean;
    }

}
