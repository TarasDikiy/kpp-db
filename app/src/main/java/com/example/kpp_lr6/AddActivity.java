package com.example.kpp_lr6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kpp_lr6.dbhelper.DBHelper;

public class AddActivity extends AppCompatActivity {

    EditText et_name, et_period, et_productivity;
    Button btn_add;
    Spinner spn_season;

    DBHelper insertDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        et_name = findViewById(R.id.edit_et_wheatName);
        et_period = findViewById(R.id.edit_et_period);
        et_productivity = findViewById(R.id.edit_et_productivity);
        spn_season = findViewById(R.id.edit_spn_season);
        btn_add = findViewById(R.id.btn_change_save);

        insertDB = new DBHelper(this);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNew();
            }
        });
    }

    private void AddNew() {
        if (et_name.getText().toString().isEmpty() || et_period.getText().toString().isEmpty() || et_productivity.getText().toString().isEmpty()) {
            Toast.makeText(AddActivity.this, "Заповніть всі поля!", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase database = insertDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.WHEAT_NAME, et_name.getText().toString());
        contentValues.put(DBHelper.WHEAT_SEASON, spn_season.getSelectedItem().toString());
        contentValues.put(DBHelper.WHEAT_GROWING, Integer.parseInt(et_period.getText().toString()));
        contentValues.put(DBHelper.WHEAT_PRODUCTIVITY, Integer.parseInt(et_productivity.getText().toString()));

        try {
            database.insert(DBHelper.TABLE_WHEAT, null, contentValues);
            Log.d("DB", "Inserted new item");
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Log.e("DB", e.toString());
        }
    }
}