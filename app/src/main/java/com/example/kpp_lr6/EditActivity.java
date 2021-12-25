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

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_name, et_period, et_productivity;
    Spinner spn_season;
    Button btn_save, btn_delete;

    String id;
    DBHelper editDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        et_name = findViewById(R.id.edit_et_wheatName);
        et_period = findViewById(R.id.edit_et_period);
        et_productivity = findViewById(R.id.edit_et_productivity);
        spn_season = findViewById(R.id.edit_spn_season);
        btn_save = findViewById(R.id.btn_change_save);
        btn_delete = findViewById(R.id.btn_deleta);

        btn_save.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        Intent intent = getIntent();
        et_name.setText(intent.getStringExtra("name"));
        et_period.setText(intent.getStringExtra("period"));
        et_productivity.setText(intent.getStringExtra("productivity"));

        if (intent.getStringExtra("season") == "Яра") {
            spn_season.setSelection(0);
        } else {
            spn_season.setSelection(1);
        }

        id = intent.getStringExtra("id");
        Log.d("ID", "id = " + id);

        editDB = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btn_change_save):
                Save();
                break;
            case (R.id.btn_deleta):
                Delete();
                break;
        }
    }

    private void Save() {
        if (et_name.getText().toString().isEmpty()){
            Toast.makeText(this, "Заповніть всі поля!", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase database = editDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.WHEAT_NAME, et_name.getText().toString());
        contentValues.put(DBHelper.WHEAT_GROWING, et_period.getText().toString());
        contentValues.put(DBHelper.WHEAT_PRODUCTIVITY, et_productivity.getText().toString());
        contentValues.put(DBHelper.WHEAT_SEASON, spn_season.getSelectedItem().toString());

        database.update(DBHelper.TABLE_WHEAT, contentValues, DBHelper.WHEAT_ID + " = " + id, null);

        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void Delete() {
        SQLiteDatabase database = editDB.getWritableDatabase();

        database.delete(DBHelper.TABLE_WHEAT, DBHelper.WHEAT_ID + " = " + id, null);

        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}