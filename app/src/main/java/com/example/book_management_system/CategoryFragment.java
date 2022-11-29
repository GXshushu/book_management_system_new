package com.example.book_management_system;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    RecyclerView mRecyclerView;
    public CategoryAdapter mMyAdapter ;
    List<News> mNewsList = new ArrayList<>();
    List<News> mTempList;
    String mCategory;
    public CategoryFragment() {
        // Required empty public constructor
    }
    public CategoryFragment(String category) {
        this.mCategory = category;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview_category);
        DividerItemDecoration mDivider = new
                DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mDivider);
        File file = new File("/data/user/0/com.example.book_management_system/files/data123");
        if(file.exists()){
            read();
        }
        else {
            // 构造一些数据
            for (int i = 0; i < 50; i++) {
                News news = new News();
                news.title = "标题" + i;
                news.Author = "作者" + i;
                switch (i%3){
                    case 0:
                        news.book_surface = R.drawable.a1;break;
                    case 1:
                        news.book_surface = R.drawable.a2;break;
                    case 2:
                        news.book_surface = R.drawable.a3;break;
                }

                mNewsList.add(news);
            }
        }

        mMyAdapter = new CategoryAdapter(mNewsList,getContext());
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        return view;
    }
    public void save(){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = getActivity().openFileOutput("data123", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(mNewsList);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(out != null)
                    out.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    //读取数据
    public void read(){
        FileInputStream in = null;
        try{
            in = getActivity().openFileInput("data123");
            ObjectInputStream ois = new ObjectInputStream(in);
            //Log.e("这里没问题","这里没问题");
            mNewsList = (List<News>) ois.readObject();
            mTempList = new ArrayList<>();

            for(int i = 0; i < mNewsList.size();i++){
                if(mNewsList.get(i).category==null){
                    continue;
                }
                if(mNewsList.get(i).category.equals(mCategory)){
                    mTempList.add(mNewsList.get(i));
                }
            }
            mNewsList = mTempList;
            if(mMyAdapter!=null) {
                ((CategoryAdapter) mMyAdapter).mNewsList = mTempList;
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            if(in != null){
                try{
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}