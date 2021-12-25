package com.example.kpp_lr6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kpp_lr6.dbhelper.DBHelper;
import com.example.kpp_lr6.dbhelper.Wheat;
import com.example.kpp_lr6.dbhelper.WheatAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ArrayList<Wheat> wheats = new ArrayList<Wheat>();

    RecyclerView rv_list;

    DBHelper searchDB;

    EditText et_name, et_period_min, et_period_max, et_productivity_min, et_productivity_max;
    Spinner spn_season;
    Button btn_search;

    WheatAdapter.OnWheatClickListener wheatClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        et_name = findViewById(R.id.search_et_name);
        et_period_min = findViewById(R.id.search_et_min_period);
        et_period_max = findViewById(R.id.search_et_max_period);
        et_productivity_min = findViewById(R.id.search_et_min_prod);
        et_productivity_max = findViewById(R.id.search_et_max_prod);
        spn_season = findViewById(R.id.search_spn_season);
        btn_search = findViewById(R.id.search_btn_search);

        searchDB = new DBHelper(this);

        rv_list = findViewById(R.id.rv_list);

        wheatClickListener = new WheatAdapter.OnWheatClickListener() {
            @Override
            public void onWheatClick(Wheat wheat, int position) {
                Log.d("RV", wheat.getName());
                Intent intent = new Intent(SearchActivity.this, EditActivity.class);
                intent.putExtra("id", wheat.getId());
                intent.putExtra("name", wheat.getName());
                intent.putExtra("season", wheat.getSeason());
                intent.putExtra("period", wheat.getPeriod());
                intent.putExtra("productivity", wheat.getProductivity());
                Log.d("ITEM", "id = " + wheat.getId() + " name = " + wheat.getName());
                startActivity(intent);
            }
        };

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });
    }

    private void Search() {
        wheats.clear();

        if (et_name.getText().toString().isEmpty()){
            SearchAll();
            return;
        }

        int  perMin = Integer.parseInt(et_period_min.getText().toString());
        int  perMax = Integer.parseInt(et_period_max.getText().toString());
        int  prodMin = Integer.parseInt(et_productivity_min.getText().toString());
        int  prodMax = Integer.parseInt(et_productivity_min.getText().toString());

        SQLiteDatabase database = searchDB.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_WHEAT,
                                null,
                                DBHelper.WHEAT_NAME + " = ? AND " + DBHelper.WHEAT_SEASON + " = ?",
                             new String[] {et_name.getText().toString(), spn_season.getSelectedItem().toString()},
                                null,
                                 null,
                                null,
                                  null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.WHEAT_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.WHEAT_NAME);
            int seasonIndex = cursor.getColumnIndex(DBHelper.WHEAT_SEASON);
            int periodIndex = cursor.getColumnIndex(DBHelper.WHEAT_GROWING);
            int productivityIndex = cursor.getColumnIndex(DBHelper.WHEAT_PRODUCTIVITY);

            do {
                if (cursor.getInt(periodIndex) > perMin || cursor.getInt(periodIndex) < perMax || cursor.getInt(productivityIndex) > prodMin || cursor.getInt(productivityIndex) < prodMax){
                    wheats.add(new Wheat(cursor.getString(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(seasonIndex),
                            cursor.getString(periodIndex),
                            cursor.getString(productivityIndex)));
                }
            } while (cursor.moveToNext());
        } else {
            Log.d("DB", "Empty table");
        }
        cursor.close();
        DisplayList();
    }

    private void DisplayList() {
        WheatAdapter wheatAdapter = new WheatAdapter(this, wheats, wheatClickListener);
        rv_list.setAdapter(wheatAdapter);
    }

    private void SearchAll() {
        SQLiteDatabase database = searchDB.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_WHEAT, null, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.WHEAT_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.WHEAT_NAME);
            int seasonIndex = cursor.getColumnIndex(DBHelper.WHEAT_SEASON);
            int periodIndex = cursor.getColumnIndex(DBHelper.WHEAT_GROWING);
            int productivityIndex = cursor.getColumnIndex(DBHelper.WHEAT_PRODUCTIVITY);

            do {
                wheats.add(new Wheat(cursor.getString(idIndex),
                                    cursor.getString(nameIndex),
                                    cursor.getString(seasonIndex),
                                    cursor.getString(periodIndex),
                                    cursor.getString(productivityIndex)));

            } while (cursor.moveToNext());
        } else {
            Log.d("DB", "Empty table");
        }
        cursor.close();
        DisplayList();
    }
}