package com.iste776.jpavelw.simplelogin;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameTxt = (EditText) findViewById(R.id.username_txt);
        final EditText passwordTxt = (EditText) findViewById(R.id.password_txt);

        final Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(usernameTxt.getText().toString().equals("") || passwordTxt.getText().toString().equals("")){
                    Toast loginToast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorEmptyMessage), Toast.LENGTH_SHORT);
                    loginToast.show();
                    return;
                }

                if(checkPassword(usernameTxt.getText().toString(), passwordTxt.getText().toString())) {
                    Intent helloUser = new Intent(Login.this, HelloUser.class);
                    startActivity(helloUser);
                } else {
                    Toast loginToast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorLoginMessage), Toast.LENGTH_SHORT);
                    loginToast.show();
                }
            }
        });
    }

    private boolean checkPassword(String username, String password){
        /*ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            InputStream inputStream = null;
            String result = "";
            try{
                //HttpClient
            } catch (Exception e){
                Log.e("ERROR-B", e.getLocalizedMessage());
            }
        }*/
        return false;
    }
}
