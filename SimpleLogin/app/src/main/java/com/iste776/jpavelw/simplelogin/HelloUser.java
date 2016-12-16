package com.iste776.jpavelw.simplelogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HelloUser extends AppCompatActivity {

    private EditText usernameTxt;
    private EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_user);

        this.usernameTxt = (EditText) findViewById(R.id.username_txt);
        this.passwordTxt = (EditText) findViewById(R.id.password_txt);

        final Button loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(usernameTxt.getText().toString().equals("") || passwordTxt.getText().toString().equals("")){
                    Toast loginToast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorEmptyMessage), Toast.LENGTH_SHORT);
                    loginToast.show();
                    return;
                }

                changePassword(usernameTxt.getText().toString(), passwordTxt.getText().toString());
            }
        });
    }

    private void showResult(Boolean result, String msg){
        if(result) {
            this.usernameTxt.setText("");
            this.passwordTxt.setText("");
            this.usernameTxt.requestFocus();
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast loginToast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
            loginToast.show();
        }
    }

    private void changePassword(String username, String password){
        // Instantiate the RequestQueue.
        RequestQueue request = Volley.newRequestQueue(this);
        String url = "http://jpavelw.me:7761/simplelogin/update";
        final String body = String.format("{\"user\": {\"username\": \"%s\", \"password\": \"%s\"}}", username, password);

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(response.getInt("code") == 200 && response.getBoolean("message")){
                        showResult(true, getResources().getString(R.string.succUpdateMessgae));
                    } else {
                        showResult(false, getResources().getString(R.string.errorLoginMessage));
                    }
                } catch (JSONException e){
                    showResult(false, getResources().getString(R.string.errorWrongMessage));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showResult(false, getResources().getString(R.string.errorWrongMessage));
            }
        }) {
            @Override
            public byte[] getBody() {
                return body.getBytes();
            }
        };

        // Add the request to the RequestQueue.
        request.add(jsonRequest);
    }
}
