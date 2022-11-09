package com.example.book_management_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity{
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        btn1=(Button) findViewById(R.id.btn1);
        Intent intent = getIntent();

        int book_surface_str = intent.getIntExtra("book_surface",0);
        TextView textView_title = findViewById(R.id.editTextTextTitle);
        String title_string = intent.getStringExtra("title");
        textView_title.setText(title_string);

        TextView Author = findViewById(R.id.editTextBookAuthor);
        String Author_str = intent.getStringExtra("Author");
        Author.setText(Author_str);

        TextView isbn = findViewById(R.id.editTextBookISBN);
        String isbn_str = intent.getStringExtra("isbn");
        isbn.setText(isbn_str);

        TextView Publisher = findViewById(R.id.editTextBookPublisher);
        String publi_str = intent.getStringExtra("publisher");
        Publisher.setText(publi_str);

        ImageView Image = findViewById(R.id.imageView2);
        Image.setImageResource(book_surface_str);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_OK,intent);
                intent.putExtra("return_back","no");
                finish();
            }
        });
        Button btn2=(Button) findViewById(R.id.button);
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String author_back = Author.getText().toString();
                intent.putExtra("return_back","yes");
                intent.putExtra("Author_back",author_back);
                intent.putExtra("ISBN_back",isbn.getText().toString());
                intent.putExtra("publisher_back",Publisher.getText().toString());
                intent.putExtra("title_back",textView_title.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
