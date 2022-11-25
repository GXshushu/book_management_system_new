package com.example.book_management_system;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class AddActivity extends AppCompatActivity {
    Button btn_cancel;
    ImageButton imageButton;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        context = this.getApplicationContext();
        btn_cancel=(Button) findViewById(R.id.buttoncancel);
        Intent intent = getIntent();
        TextView textView_title = findViewById(R.id.editTextTextPersonName);
        TextView textView_Anthor = findViewById(R.id.editTextTextPersonName2);
        TextView textView_ISBN = findViewById(R.id.editTextTextPersonName3);
        TextView textView_Publisher = findViewById(R.id.editTextTextPersonName4);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_OK,intent);
                intent.putExtra("return_back","no");
                finish();
            }
        });
        Button btn_ok=(Button) findViewById(R.id.buttonok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                intent.putExtra("return_back","yes");
                String author_back = textView_Anthor.getText().toString();
                intent.putExtra("Author_back",author_back);
                intent.putExtra("title_back",textView_title.getText().toString());
                intent.putExtra("ISBN_back",textView_ISBN.getText().toString());
                intent.putExtra("publisher_back",textView_Publisher.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK);
                gallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                chooseimage_result.launch(gallery);
            }
        });
    }
    @Override
    public void onBackPressed() {
        //TODO something
        btn_cancel.callOnClick();
    }
    public ActivityResultLauncher chooseimage_result = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    Glide.with(context).load(intent.getData()).into(imageButton);
                    Drawable image = imageButton.getDrawable();

                    onResume();
                }
            });
}