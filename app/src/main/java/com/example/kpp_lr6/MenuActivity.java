package com.example.kpp_lr6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_add, btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_add = findViewById(R.id.menu_btn_add);
        btn_search = findViewById(R.id.menu_btn_search);

        btn_search.setOnClickListener(this);
        btn_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.menu_btn_add):
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
            case (R.id.menu_btn_search):
                Intent intent1 = new Intent(this, SearchActivity.class);
                startActivity(intent1);
                break;
        }
    }
}