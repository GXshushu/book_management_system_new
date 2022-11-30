package com.example.book_management_system;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class BookListFragment extends Fragment {
    AllBookFragment allBookFragment ;
    List<Fragment> fragmentList = new ArrayList<>();
    Category category;
    public BookListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        allBookFragment = new AllBookFragment();
        fragmentList.add(allBookFragment);
        read();
        if(category == null) {
            category = new Category();
            category.setCategory(new String[]{"ALL", "HISTORY", "COMPUTER"});
        }
        for(int i = 1;i < category.getLength();i++){
            fragmentList.add(new CategoryFragment(category.getCategory().get(i)));
        }
        //创建适配器，并传入fragment
        //添加适配器
        ViewPager2 viewPager = view.findViewById(R.id.viewpaper2);
        viewPager.setAdapter(new FragmentStateAdapter(this){

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        });
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //设置tab的文字
//                switch (position) {
//                    case 0:
//                        tab.setText("");
//                        break;
//                    case 1:
//                        tab.setText("blank");
//                        break;
//                }
                tab.setText(category.getCategory().get(position));

            }
        }).attach();
        // Inflate the layout for this fragment
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position != 0) {
                    ((CategoryFragment) fragmentList.get(position)).read();
                    ((CategoryFragment) fragmentList.get(position)).mMyAdapter.notifyDataSetChanged();
                }
                super.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }

        });
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    public AllBookFragment getAllBookFragment() {
        return allBookFragment;
    }
    public void read(){
        FileInputStream in = null;
        try{
            in = getActivity().openFileInput("category");
            ObjectInputStream ois = new ObjectInputStream(in);
            //Log.e("这里没问题","这里没问题");
            category =  (Category) ois.readObject();
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