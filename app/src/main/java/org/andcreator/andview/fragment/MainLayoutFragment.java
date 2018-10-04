package org.andcreator.andview.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.andcreator.andview.R;
import org.andcreator.andview.activity.ChatActivity;
import org.andcreator.andview.activity.ColorPagerActivity;
import org.andcreator.andview.activity.FoldActivity;
import org.andcreator.andview.activity.GooglePlusActivity;
import org.andcreator.andview.activity.GradientActivity;
import org.andcreator.andview.activity.MaterialLoginActivity;
import org.andcreator.andview.activity.PixelLauncherActivity;
import org.andcreator.andview.activity.RecyclerActivity;
import org.andcreator.andview.activity.ScrollingActivity;
import org.andcreator.andview.activity.ViewPagerActivity;
import org.andcreator.andview.adapter.RecyclerMainLayoutAdapter;
import org.andcreator.andview.bean.RecyclerMainLayoutBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainLayoutFragment extends Fragment {

    public List<RecyclerMainLayoutBean> data;

    public MainLayoutFragment() {
        // Required empty public constructor
    }

    private OnFragmentInteractionListener mListener;
    private NestedScrollView scrollView;
    private Boolean toolbarVisible = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_layout, container, false);

        scrollView = view.findViewById(R.id.scroll_view);
        RecyclerView layoutRecycler = view.findViewById(R.id.layout_recycler);
        layoutRecycler.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutRecycler.setLayoutManager(layoutManager);
        RecyclerMainLayoutAdapter adapter = new RecyclerMainLayoutAdapter(getLayoutInflater(),loadData());
        layoutRecycler.setAdapter(adapter);

        adapter.setClickListener(new RecyclerMainLayoutAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(getActivity(),ChatActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(),ViewPagerActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(),RecyclerActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(),ScrollingActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(),ColorPagerActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(),GradientActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getActivity(),FoldActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(getActivity(),PixelLauncherActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(getActivity(),MaterialLoginActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(getActivity(),GooglePlusActivity.class));
                        break;
                        default:
                            break;
                }
            }
        });

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
//        layoutRecycler.setOnScrollListener(new HideScrollListener());

        return view;
    }

    private List<RecyclerMainLayoutBean> loadData() {
        data = new ArrayList<>();
        data.add(new RecyclerMainLayoutBean("对话界面","简单的模仿环聊对话界面",new int[]{R.drawable.and}));
        data.add(new RecyclerMainLayoutBean("ViewPager动画","实现ViewPager两边小中间大的动画效果",new int[]{R.drawable.and}));
        data.add(new RecyclerMainLayoutBean("RecyclerView堆叠滚动","实现卡片堆叠滚动的效果",new int[]{R.drawable.night_farmer}));
        data.add(new RecyclerMainLayoutBean("ScrollingLayout","一个属性即可实现炫酷的Material Design设计",new int[]{R.drawable.and}));
        data.add(new RecyclerMainLayoutBean("ViewPager滚动时改变颜色","监听ViewPager并实时改变背景颜色",new int[]{R.drawable.lollipop}));
        data.add(new RecyclerMainLayoutBean("系列按钮渐变","模仿桌面版Google Plus渐变按钮",new int[]{R.drawable.and}));
        data.add(new RecyclerMainLayoutBean("Pixel Launcher效果","模仿Pixel Launcher以及获取应用程序列表",new int[]{R.drawable.lollipop}));
        data.add(new RecyclerMainLayoutBean("Material 顶部布局折叠菜单","点击后下滑整个布局出现选项菜单",new int[]{R.drawable.and,R.drawable.lollipop}));
        data.add(new RecyclerMainLayoutBean("Google+的图片文字列表","模仿桌面版Google Plus图片文字Item",new int[]{R.drawable.and}));
        data.add(new RecyclerMainLayoutBean("Material Design 登录与注册","以屏幕中心的单个卡片为主题的登录界面",new int[]{R.drawable.and}));
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
        if (context instanceof OnFragmentInteractionListener) {
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


    //滑动监听
    class HideScrollListener extends RecyclerView.OnScrollListener{
        private static final int HIDE_HEIGHT=40;
        private int scrolledInstance=0;
        private boolean toolbarVisible=true;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if ((toolbarVisible&&dy>0)||!toolbarVisible&&dy<0){
                //recycler向上滚动时dy为正，向下滚动时dy为负数
                scrolledInstance+=dy;
            }
            if (scrolledInstance>HIDE_HEIGHT&&toolbarVisible){//当recycler向上滑动距离超过设置的默认值并且toolbar可见时，隐藏toolbar和fab
                onHide();
                scrolledInstance=0;
                toolbarVisible=false;
            }else if (scrolledInstance<-HIDE_HEIGHT&&!toolbarVisible){//当recycler向下滑动距离超过设置的默认值并且toolbar不可见时，显示toolbar和fab
                onShow();
                scrolledInstance=0;
                toolbarVisible=true;
            }
        }
    }

    private void onHide() {
        mListener.onFragmentInteraction(0);
    }

    private void onShow() {
        mListener.onFragmentInteraction(1);
    }

}
