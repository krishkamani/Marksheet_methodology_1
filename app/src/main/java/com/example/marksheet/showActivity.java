package com.example.marksheet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class showActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        listView=findViewById(R.id.list_view);

        MyAdapter myAdapter=new MyAdapter(getApplicationContext());
        listView.setAdapter(myAdapter);
    }
}
