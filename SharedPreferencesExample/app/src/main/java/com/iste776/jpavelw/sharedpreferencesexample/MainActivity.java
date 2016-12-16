package com.iste776.jpavelw.sharedpreferencesexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String MY_PREFERENCES = "MyPrefLogin";
    private final String USERNAME_KEY = "UsernameKey";
    private final String PASSWORD_KEY = "PasswordKey";

    private SharedPreferences sharedPreferences;
    private EditText txtUsername, txtPassword;
    private Button btnStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnStore = (Button) findViewById(R.id.btnStore);

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USERNAME_KEY, txtUsername.getText().toString());
                editor.putString(PASSWORD_KEY, txtPassword.getText().toString());
                editor.commit();
                Toast.makeText(getApplicationContext(), "Your data has been stored successfully!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
