package org.andcreator.andview.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.andcreator.andview.R;
import org.andcreator.andview.adapter.RecyclerChatEmojiAdapter;
import org.andcreator.andview.bean.RecyclerChatEmojiBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatEmojiListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatEmojiListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerEmoji;
    private RecyclerChatEmojiAdapter adapter;
    private List<RecyclerChatEmojiBean> mChatEmoji;


    public ChatEmojiListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatEmojiListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatEmojiListFragment newInstance(String param1, String param2) {
        ChatEmojiListFragment fragment = new ChatEmojiListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_emoji_list, container, false);

        recyclerEmoji = (RecyclerView) view.findViewById(R.id.chat_emoji_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),8);
        recyclerEmoji.setLayoutManager(layoutManager);
        adapter = new RecyclerChatEmojiAdapter(listEmoji());
        recyclerEmoji.setAdapter(adapter);
        return view;
    }

    private List<RecyclerChatEmojiBean> listEmoji() {
        mChatEmoji = new ArrayList<>();
        for (int i = 0x1F601; i<0x1F64A;i++){

            mChatEmoji.add(new RecyclerChatEmojiBean(i));
        }
        for (int i = 0x1F443; i<0x1F4AA;i++){

            mChatEmoji.add(new RecyclerChatEmojiBean(i));
        }
        for (int i = 0x1F347; i<0x1F394;i++){

            mChatEmoji.add(new RecyclerChatEmojiBean(i));
        }
//        for (int i = 0x1F680; i<0x1F6C0;i++){
//
//            mChatEmoji.add(new RecyclerChatEmojiBean(i));
//        }
        return mChatEmoji;
    }

}
