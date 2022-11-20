package com.example.book_management_system;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BookListFragment bookListFragment = new BookListFragment();
    ArrayList<Fragment>  fragmentsArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentsArray.add(bookListFragment);
        fragmentsArray.add(new BlankFragment());
        //创建适配器，并传入fragment
        ViewPaperAdapter viewPaperAdapter = new ViewPaperAdapter(MainActivity.this,fragmentsArray);
        //添加适配器
        ViewPager2 viewPager = findViewById(R.id.viewpaper2);
        viewPager.setAdapter(viewPaperAdapter);
        viewPager.setCurrentItem(0);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //设置tab的文字
                switch (position){
                    case 0:
                        tab.setText("bookList");
                        break;
                    case 1:
                        tab.setText("blank");
                        break;
                }

            }
        }).attach();

        //ViewPager2提供的滑动监听
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }

        });
    }

    public BookListFragment getBookListFragment() {
        return bookListFragment;
    }

    public class ViewPaperAdapter extends FragmentStateAdapter {
        private ArrayList<Fragment> mfragments;
        public ViewPaperAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragments){
            super(fragmentActivity);
            this.mfragments=fragments;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mfragments.get(position);
        }

        @Override
        public int getItemCount() {
            return mfragments.size();
        }
    }
}
