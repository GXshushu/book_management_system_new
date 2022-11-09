package com.example.book_management_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Button btn_cancel=(Button) findViewById(R.id.buttoncancel);
        Intent intent = getIntent();
        TextView textView_title = findViewById(R.id.editTextTextPersonName);
        TextView textView_Anthor = findViewById(R.id.editTextTextPersonName2);
        TextView textView_ISBN = findViewById(R.id.editTextTextPersonName3);
        TextView textView_Publisher = findViewById(R.id.editTextTextPersonName4);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_OK,intent);
                intent.putExtra("return",0);
                finish();
            }
        });
        Button btn_ok=(Button) findViewById(R.id.buttonok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String author_back = textView_Anthor.getText().toString();
                intent.putExtra("Author_back",author_back);
                intent.putExtra("title_back",textView_title.getText().toString());
                intent.putExtra("ISBN_back",textView_ISBN.getText().toString());
                intent.putExtra("publisher_back",textView_Publisher.getText().toString());
                intent.putExtra("return",1);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}