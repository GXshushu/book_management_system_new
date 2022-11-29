package com.example.book_management_system;

import java.io.Serializable;

public class News implements Serializable {
    public String title; // 标题
    public String Author; //内容
    public String isbn;     //isbn
    public String publisher;
    public String category;
    public int book_surface;    //图片
    public String imagePath;    //图片文件名
}
