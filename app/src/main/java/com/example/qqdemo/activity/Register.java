package com.example.qqdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qqdemo.R;
import com.example.qqdemo.bean.User;
import com.example.qqdemo.util.DatabaseHelper;

import java.util.ArrayList;

public class Register extends AppCompatActivity {
    private DatabaseHelper mSQlite;
    private EditText username;
    private EditText userpassword;
    private Button reday;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mSQlite = new DatabaseHelper(this);  // Initialize the DatabaseHelper here

        reday = findViewById(R.id.reday);
        back = findViewById(R.id.back);
        username = findViewById(R.id.username);
        userpassword = findViewById(R.id.userpassword);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        reday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString().trim();
                String password = userpassword.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "信息不完备，注册失败", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<User> data = mSQlite.getAllDATA();
                boolean userExists = false;

                for (User userdata : data) {
                    if (name.equals(userdata.getName())) {
                        userExists = true;
                        break;
                    }
                }

                if (!userExists) {
                    mSQlite.insert(name, password);
                    Intent intent = new Intent(Register.this, loginActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Register.this, "用户名已被注册", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
