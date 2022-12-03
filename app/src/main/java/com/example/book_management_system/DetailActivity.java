package com.example.book_management_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();

        int book_surface_str = intent.getIntExtra("book_surface",0);
        TextView textView_title = findViewById(R.id.textViewDetailName);
        String title_string = intent.getStringExtra("title");
        textView_title.setText(title_string);

        TextView Author = findViewById(R.id.textViewDetailAuthor);
        String Author_str = intent.getStringExtra("Author");
        Author.setText(Author_str);

        TextView isbn = findViewById(R.id.textViewDetailISBN);
        String isbn_str = intent.getStringExtra("isbn");
        isbn.setText(isbn_str);

        TextView Publisher = findViewById(R.id.textViewDetailPublisher);
        String publi_str = intent.getStringExtra("publisher");
        Publisher.setText(publi_str);

        TextView Category = findViewById(R.id.textViewDetailCategory);
        String category_str = intent.getStringExtra("category");
        Category.setText(category_str);

        String image_path = intent.getStringExtra("image_path");

        ImageView Image = findViewById(R.id.imageView7);
        if(image_path.equals("NULL")) {
            Image.setImageResource(book_surface_str);
        }
        else{
            Bitmap bitmap = getResource(image_path);
            Image.setImageBitmap(bitmap);
        }

    }
    public Bitmap getResource(String imageName) {
        Bitmap bitmap = null;
        try {
            FileInputStream localStream = this.openFileInput(imageName);
            bitmap = BitmapFactory.decodeStream(localStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}