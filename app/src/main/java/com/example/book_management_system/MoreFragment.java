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
                inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});//????????????????????????50?????????
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//????????????????????????????????????????????????????????????????????????????????????????????????
                builder.setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                        .setNegativeButton("??????", null);//???????????????
                builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {//?????????????????????????????????????????????
                    public void onClick(DialogInterface dialog, int which) {
                        String sign = inputServer.getText().toString();//???????????????????????????????????????
                        sign = sign.toUpperCase();
                        mCategory.addCategory(sign);
                        save();
                    }
                });
                builder.show();//??????
            }
        });
        View linearLayout_del = view.findViewById(R.id.linearLayout3);
        linearLayout_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText inputServer = new EditText(getActivity());
                inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});//????????????????????????50?????????
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//????????????????????????????????????????????????????????????????????????????????????????????????
                builder.setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                        .setNegativeButton("??????", null);//???????????????
                builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {//?????????????????????????????????????????????
                    public void onClick(DialogInterface dialog, int which) {
                        String sign = inputServer.getText().toString();//???????????????????????????????????????
                        sign = sign.toUpperCase();
                        mCategory.delCategory(sign);
                        save();
                    }
                });
                builder.show();//??????
            }
        });
        return view;
    }
    //????????????
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
    //????????????
    public void read(){
        FileInputStream in = null;
        try{
            in = getActivity().openFileInput("category");
            ObjectInputStream ois = new ObjectInputStream(in);
            //Log.e("???????????????","???????????????");
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