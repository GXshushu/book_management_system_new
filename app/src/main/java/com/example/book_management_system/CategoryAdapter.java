package com.example.book_management_system;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private int position;
    private Context mContext;
    public List<News> mNewsList;
    public CategoryAdapter.MyViewHolder myViewHolder;

    public CategoryAdapter(List<News> fruitList, Context mContext){this.mContext = mContext;this.mNewsList=fruitList; }
    public int getContextMenuPosition() { return position; }
    public void setContextMenuPosition(int position) { this.position = position; }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                mContext).inflate(R.layout.item_list, parent,
                false);
        myViewHolder = new CategoryAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        News news = mNewsList.get(position);
        holder.mTitleTv.setText(news.title);
        holder.mTitleContent.setText(news.Author);
        if(news.imagePath.equals("NULL")) {
            holder.mBook.setImageResource(news.book_surface);
        }
        else{
            Bitmap bitmap = getResource(news.imagePath);
            holder.mBook.setImageBitmap(bitmap);
        }
        if(myViewHolder != null) {
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setContextMenuPosition(myViewHolder.getLayoutPosition());
                    Log.v("tag", Integer.toString(myViewHolder.getLayoutPosition()));
                    Intent intent = new Intent();
                    intent.putExtra("title", mNewsList.get(getContextMenuPosition()).title);
                    intent.putExtra("Author", mNewsList.get(getContextMenuPosition()).Author);
                    intent.putExtra("book_surface", mNewsList.get(getContextMenuPosition()).book_surface);
                    intent.putExtra("isbn", mNewsList.get(getContextMenuPosition()).isbn);
                    intent.putExtra("publisher", mNewsList.get(getContextMenuPosition()).publisher);
                    intent.putExtra("image_path", mNewsList.get(getContextMenuPosition()).imagePath);
                    intent.putExtra("category", mNewsList.get(getContextMenuPosition()).category);
                    intent.setClass(mContext, DetailActivity.class);
                    ((MainActivity)mContext).detail_result.launch(intent);
                }
            });
        }
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                setContextMenuPosition(holder.getLayoutPosition());
//                return false;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTitleTv;
        TextView mTitleContent;
        ImageView mBook;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.textView);
            mTitleContent = itemView.findViewById(R.id.textView2);
            mBook = itemView.findViewById(R.id.imageView);

        }

    }
    public Bitmap getResource(String imageName) {
        Bitmap bitmap = null;
        try {
            FileInputStream localStream = mContext.openFileInput(imageName);
            bitmap = BitmapFactory.decodeStream(localStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
