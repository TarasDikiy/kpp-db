package com.example.kpp_lr6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kpp_lr6.dbhelper.DBHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_login, et_password;
    Button btn_login;
    TextView tv_register;

    DBHelper loginDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_login = findViewById(R.id.login_et_login);
        et_password = findViewById(R.id.login_et_password);
        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.tv_register);

        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);

        loginDB = new DBHelper(this);

        Intent values = getIntent();

        try {
            et_login.setText(values.getStringExtra("login"));
            et_password.setText(values.getStringExtra("pass"));
        } catch (Exception e) {
            Log.e("EXTRA'S", e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btn_login):
                Login();
                break;
            case (R.id.tv_register):
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void Login() {
        if (et_login.getText().toString().isEmpty() || et_password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заповніть всі поля!", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase database = loginDB.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_USER,
                null,
                DBHelper.USER_LOGIN + " = ? AND " + DBHelper.USER_PASSWORD + " = ?",
                new String[] {et_login.getText().toString(), et_password.getText().toString()},
                null,
                null,
                null);

        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(DBHelper.USER_ID);
            int loginIndex = cursor.getColumnIndex(DBHelper.USER_LOGIN);
            int passIndex = cursor.getColumnIndex(DBHelper.USER_PASSWORD);

            Log.d("LOGIN", "Login as " + cursor.getInt(idIndex) + " " + cursor.getString(loginIndex) + " " + cursor.getString(passIndex));

            cursor.close();

            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);

        } else {
            Log.d("LOGIN", "No login!");
            Toast.makeText(this, "Користувача з такими даними не існує!", Toast.LENGTH_SHORT).show();
            cursor.close();
        }
    }
}