package com.example.book_management_system;

import java.io.Serializable;

public class News implements Serializable {
    public String title; // 标题
    public String content; //内容
    public String isbn;     //isbn
    public String publisher;
    public int book_surface;    //图片
}