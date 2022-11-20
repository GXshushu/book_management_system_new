package com.example.book_management_system;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
 * Use the {@link BookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookListFragment extends Fragment {




    RecyclerView mRecyclerView;
    BookListFragment.MyAdapter mMyAdapter ;
    List<News> mNewsList = new ArrayList<>();
    public BookListFragment() {
        // Required empty public constructor
    }


    public static BookListFragment newInstance() {
        BookListFragment fragment = new BookListFragment();
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
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        DividerItemDecoration mDivider = new
                DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
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
                news.publisher = "暨南大学出版社";
                news.isbn = "6666666666";
                mNewsList.add(news);
            }
        }

        mMyAdapter = new BookListFragment.MyAdapter(mNewsList,getActivity());
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {     //悬浮按钮监听事件
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.setClass(getActivity(), AddActivity.class);
                add_result.launch(intent);

            }
        });
        return view;
    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHoder> {
        private int position;
        private Context mContext;
        private List<News> mList;
        public MyAdapter(List<News> fruitList,Context mContext){this.mContext = mContext;this.mList=fruitList;}
        public int getContextMenuPosition() { return position; }
        public void setContextMenuPosition(int position) { this.position = position; }
        @NonNull
        int a = getContextMenuPosition();
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.item_list, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);

            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList.get(position);
            holder.mTitleTv.setText(news.title);
            holder.mTitleContent.setText(news.Author);
            holder.mBook.setImageResource(news.book_surface);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setContextMenuPosition(holder.getLayoutPosition());
                    return false;
                }
            });


        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
        class MyViewHoder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
            TextView mTitleTv;
            TextView mTitleContent;
            ImageView mBook;


            public MyViewHoder(@NonNull View itemView) {
                super(itemView);
                mTitleTv = itemView.findViewById(R.id.textView);
                mTitleContent = itemView.findViewById(R.id.textView2);
                mBook = itemView.findViewById(R.id.imageView);
                itemView.setOnCreateContextMenuListener(this);

            }
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                //注意传入的menuInfo为null
                News mSelectModelUser = mNewsList.get(getContextMenuPosition());
                Log.i("UserAdapter", "onCreateContextMenu: "+getContextMenuPosition());
                menu.setHeaderTitle(mSelectModelUser.title);
                (((MainActivity)getActivity()).getBookListFragment()).CreateMenu(menu);
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:     //edit
                Intent intent=new Intent();
                intent.putExtra("title",mNewsList.get(mMyAdapter.getContextMenuPosition()).title);
                intent.putExtra("Author",mNewsList.get(mMyAdapter.getContextMenuPosition()).Author);
                intent.putExtra("book_surface",mNewsList.get(mMyAdapter.getContextMenuPosition()).book_surface);
                intent.putExtra("isbn",mNewsList.get(mMyAdapter.getContextMenuPosition()).isbn);
                intent.putExtra("publisher",mNewsList.get(mMyAdapter.getContextMenuPosition()).publisher);
                intent.setClass(getActivity(), EditActivity.class);
                edit_result.launch(intent);
                //startActivity(intent);
                break;
            case 2:         //delete
                //mNewsList.remove(mMyAdapter.getContextMenuPosition());
                mNewsList.remove(mNewsList.get(mMyAdapter.getContextMenuPosition()));
                mMyAdapter.notifyItemRemoved(mMyAdapter.getContextMenuPosition());
                mMyAdapter.notifyItemRangeChanged(0, mMyAdapter.getItemCount());
                save();
                break;
            case 3:         //clear
                mNewsList.clear();

                mMyAdapter.notifyDataSetChanged();
                save();
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void CreateMenu(Menu menu)
    {
        int groupID = 0;
        int order = 0;
        int[] itemID = {1,2,3};

        for(int i=0;i<itemID.length;i++)
        {
            switch(itemID[i])
            {
                case 1:
                    menu.add(groupID, itemID[i], order, "编辑");
                    break;
                case 2:
                    menu.add(groupID, itemID[i], order, "删除");
                    break;
                case 3:
                    menu.add(groupID, itemID[i], order, "清空");
                default:
                    break;
            }
        }
    }
    public ActivityResultLauncher edit_result = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    String result_str = intent.getStringExtra("return_back");
                    if(result_str.equals("yes")) {
                        String title_str = intent.getStringExtra("title_back");
                        String author_str = intent.getStringExtra("Author_back");
                        String isbn_str = intent.getStringExtra("ISBN_back");
                        String publi_str = intent.getStringExtra("publisher_back");
                        mNewsList.get(mMyAdapter.getContextMenuPosition()).title = title_str;
                        mNewsList.get(mMyAdapter.getContextMenuPosition()).Author = author_str;
                        mNewsList.get(mMyAdapter.getContextMenuPosition()).isbn = isbn_str;
                        mNewsList.get(mMyAdapter.getContextMenuPosition()).publisher = publi_str;
                        mMyAdapter.notifyItemChanged(mMyAdapter.getContextMenuPosition());
                        save();
                    }
                }
            });

    public ActivityResultLauncher add_result = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    String result_str = intent.getStringExtra("return_back");
                    //if(result_int == 0)return;
                    if(result_str.equals("yes")) {
                        String title_str = intent.getStringExtra("title_back");
                        String author_str = intent.getStringExtra("Author_back");
                        String isbn_str = intent.getStringExtra("ISBN_back");
                        String publi_str = intent.getStringExtra("publisher_back");
                        News news = new News();
                        news.Author = author_str;
                        news.title = title_str;
                        news.isbn = isbn_str;
                        news.publisher = publi_str;
                        news.book_surface = R.drawable.a1;
                        mNewsList.add(news);
                        mMyAdapter.notifyItemRangeChanged(0, mMyAdapter.getItemCount());
                        save();
                    }
                }
            });
    //保存数据
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