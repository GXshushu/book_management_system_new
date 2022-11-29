package com.example.book_management_system;

import java.util.ArrayList;

public class Category {
    private ArrayList<String> category;

    Category(){
        category = new ArrayList<String>();
    }

    public ArrayList<String> getCategory() {
        return category;
    }
    public void addCategory(String str){
        category.add(str);
    }
    public void setCategory(String[] strList){
        for(int i =0;i<strList.length;i++){
            addCategory(strList[i]);
        }
    }
    public int getLength(){
        return category.size();
    }
}
