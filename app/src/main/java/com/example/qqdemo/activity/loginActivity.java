package com.example.qqdemo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qqdemo.R;
import com.example.qqdemo.bean.User;
import com.example.qqdemo.util.DatabaseHelper;


import java.util.ArrayList;

public class loginActivity extends AppCompatActivity {
    private DatabaseHelper mSQlite;
    private EditText username;
    private EditText userpassword;
    private Button login;
    private Button register;
    private static final String TAG = "loginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSQlite = new DatabaseHelper(this);  // Initialize the DatabaseHelper here

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        username = findViewById(R.id.username);
        userpassword = findViewById(R.id.userpassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString().trim();
                String password = userpassword.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                    Toast.makeText(loginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<User> data = mSQlite.getAllDATA();
                boolean userExists = false;
                boolean passwordMatches = false;


                for (User userdata : data) {
                    if (name.equals(userdata.getName())) {
                        userExists = true;
                        if (password.equals(userdata.getPassword())) {
                            passwordMatches = true;
                        }
                        break;
                    }
                }

                if (!userExists) {
                    Toast.makeText(loginActivity.this, "该用户名未注册", Toast.LENGTH_SHORT).show();
                } else if (userExists && passwordMatches) {
                    Toast.makeText(loginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                    intent.putExtra("username", name);
                    intent.putExtra("password", password);  // Pass username and password to the next activity
                    Log.d(TAG, "name==" + name + ",password==" + password);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(loginActivity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
