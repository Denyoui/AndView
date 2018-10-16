package org.andcreator.andview.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.andcreator.andview.R;
import org.andcreator.andview.activity.AnimatorActivity;
import org.andcreator.andview.activity.BlurActivity;
import org.andcreator.andview.activity.ContributorActivity;
import org.andcreator.andview.adapter.RecyclerContributorAdapter;
import org.andcreator.andview.adapter.RecyclerMainLayoutAdapter;
import org.andcreator.andview.bean.RecyclerContributorBean;
import org.andcreator.andview.bean.RecyclerMainLayoutBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainContributorFragment extends Fragment {


    public MainContributorFragment() {
        // Required empty public constructor
    }


    public List<RecyclerContributorBean> data;
    private OnFragmentInteractionListener mListener;
    private NestedScrollView scrollView;
    private Boolean toolbarVisible = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_contributor, container, false);


        scrollView = view.findViewById(R.id.scroll_view);
        RecyclerView layoutRecycler = view.findViewById(R.id.layout_recycler);
        layoutRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutRecycler.setLayoutManager(layoutManager);
        RecyclerContributorAdapter adapter = new RecyclerContributorAdapter(getLayoutInflater(),loadData());
        layoutRecycler.setAdapter(adapter);

        //Android M API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    if (i3 < i1 && ((i1 - i3) > 15) && toolbarVisible) {// 向上
                        onHide();
                        toolbarVisible=false;
                    }  else if (i3 > i1 && (i3 - i1) > 15 && !toolbarVisible) {// 向下
                        onShow();
                        toolbarVisible=true;
                    }
                }
            });
        }

        return view;
    }


    private List<RecyclerContributorBean> loadData() {
        data = new ArrayList<>();
        data.add(new RecyclerContributorBean(R.drawable.and,"and","任何胆敢为爱付出灵魂的人，都拥有改变世界的力量。",0xfff66bb6,"http://andcreator.org/","https://github.com/hujincan","hujincan15369@Gmail.com"));
        data.add(new RecyclerContributorBean(R.drawable.lollipop,"Lollipop","你必须很努力，才能看上去毫不费力。",0xffff9100,"https://www.lollipoppp.com/","https://github.com/Mr-XiaoLiang","1982568737@qq.com"));
        data.add(new RecyclerContributorBean(R.drawable.night_farmer,"Night Farmer","我就是我，不一样的烟火！",0xff90a4ae,"https://nightfarmer.top","https://github.com/NightFarmer","nightfarmer@163.com"));
        return data;
    }

    //修改onButtonPressed方法的参数为int类型
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int scroll) {
        if (mListener != null) {
            mListener.onFragmentInteraction(scroll);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainLayoutFragment.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {}
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    //Android Studio 默认的回调接口
    //修改回调接口默认的url为int类型的参数
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int scroll);
    }

    //创建setOnFragmentInteractionListener方法，方便Activity实现接口
    public void setOnFragmentInteractionListener(OnFragmentInteractionListener listener){
        mListener = listener;
    }

    private void onHide() {
        mListener.onFragmentInteraction(0);
    }

    private void onShow() {
        mListener.onFragmentInteraction(1);
    }
}