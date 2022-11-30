package com.example.book_management_system;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreFragment extends Fragment {
    private Category mCategory;


    public MoreFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
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
        View view =  inflater.inflate(R.layout.fragment_more, container, false);
        // Inflate the layout for this fragment
        read();
        if(mCategory == null){
            mCategory = new Category();
            mCategory.setCategory(new String[]{"ALL","HISTORY","COMPUTER"});
        }
        View linearLayout_add = view.findViewById(R.id.linearLayout2);
        linearLayout_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText inputServer = new EditText(getActivity());
                inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});//设置最多只能输入50个字符
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//构建对话框，一个对话框，上面有输入框了，然后就还要有取消和确认键
                builder.setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                        .setNegativeButton("取消", null);//设置取消键
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {//设置确认键，且对确认键进行监听
                    public void onClick(DialogInterface dialog, int which) {
                        String sign = inputServer.getText().toString();//点击确认后获取输入框的内容
                        sign = sign.toUpperCase();
                        mCategory.addCategory(sign);
                        save();
                    }
                });
                builder.show();//启动
            }
        });
        return view;
    }
    //保存数据
    public void save(){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = getActivity().openFileOutput("category", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(mCategory);
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
            in = getActivity().openFileInput("category");
            ObjectInputStream ois = new ObjectInputStream(in);
            //Log.e("这里没问题","这里没问题");
            mCategory =  (Category) ois.readObject();
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