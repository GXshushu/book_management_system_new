package com.example.book_management_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rg;
    private RadioButton mRbBookList;
    private RadioButton mRbMore;
    private Fragment mFragment;
    //AllBookFragment bookListFragment = new AllBookFragment();
    BookListFragment bookListFragment = new BookListFragment();
    //ArrayList<Fragment>  fragmentsArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();

        rg = findViewById(R.id.radioGroup);
        mRbBookList = findViewById(R.id.radioButton);
        mRbMore = findViewById(R.id.radioButton2);
        //RadioGroup切换监听
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        switchFragment(bookListFragment);
                        mRbBookList.setText("··Book List··");
                        mRbMore.setText("More");
                        break;
                    case R.id.radioButton2:
                        switchFragment(new BlankFragment());
                        mRbBookList.setText("Book List");
                        mRbMore.setText("··More··");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public BookListFragment getBookListFragment() {
        return bookListFragment;
    }
//    public AllBookFragment getAllBookListFragment(){
//        return bookListFragment;
//    }
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
    private void initFragment() {
        mFragment = bookListFragment;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, mFragment).commit();
    }
    private void switchFragment(Fragment fragment) {
        //判断当前显示的Fragment是不是切换的Fragment
        if (mFragment != fragment) {
            //判断切换的Fragment是否已经添加过
            if (!fragment.isAdded()) {
                //如果没有，则先把当前的Fragment隐藏，把切换的Fragment添加上
                getSupportFragmentManager().beginTransaction().hide(mFragment)
                        .add(R.id.frameLayout, fragment).commit();
            } else {
                //如果已经添加过，则先把当前的Fragment隐藏，把切换的Fragment显示出来
                getSupportFragmentManager().beginTransaction()
                        .hide(mFragment).show(fragment).commit();
            }
            mFragment = fragment;
        }
    }

}
