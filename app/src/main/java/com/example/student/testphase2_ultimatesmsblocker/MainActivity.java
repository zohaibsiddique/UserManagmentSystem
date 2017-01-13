package com.example.student.testphase2_ultimatesmsblocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(this);

        final EditText usernameEditTxt = (EditText) findViewById(R.id.username);
        final EditText passwordEditTxt = (EditText) findViewById(R.id.password);

        loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db.authenticateLogin(usernameEditTxt.getText().toString().trim(), passwordEditTxt.getText().toString().trim())) {
                    Utility.shortToast(MainActivity.this, "Successful login");
                    Utility.intent(MainActivity.this, ViewUser.class);
                } else {
                    Utility.shortToast(MainActivity.this, "Invalid login");
                }
            }
        });
    }
}
