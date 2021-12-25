package com.example.kpp_lr6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kpp_lr6.dbhelper.DBHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText et_login, et_password;
    Button btn_register;

    DBHelper registerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_login = findViewById(R.id.register_et_login);
        et_password = findViewById(R.id.register_et_password);
        btn_register = findViewById(R.id.btn_register);

        registerDB = new DBHelper(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    private void Register() {
        if (et_login.getText().toString().isEmpty() || et_password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заповніть всі поля!", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase database = registerDB.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_USER,
                null,
                DBHelper.USER_LOGIN + " = ?",
                new String[] {et_login.getText().toString()},
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            Toast.makeText(this, "Логін вже використовується", Toast.LENGTH_SHORT).show();
            et_login.setText(null);
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.USER_LOGIN, et_login.getText().toString());
            contentValues.put(DBHelper.USER_PASSWORD, et_password.getText().toString());

            database.insert(DBHelper.TABLE_USER, null, contentValues);
            cursor.close();

            Log.d("REGISTER", "Added new user " + et_login.getText().toString());

            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("login", et_login.getText().toString());
            intent.putExtra("pass", et_password.getText().toString());
            startActivity(intent);
        }
    }
}