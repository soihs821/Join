package com.example.join;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {

    private EditText edit_id, edit_pass, edit_name, edit_age;
    private Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // find ID
        edit_id = findViewById(R.id.edit_id);
        edit_pass = findViewById(R.id.edit_pass);
        edit_name = findViewById(R.id.edit_name);
        edit_age = findViewById(R.id.edit_age);

        // execute when click join button
        register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = edit_id.getText().toString();
                String userPass = edit_id.getText().toString();
                String userName = edit_name.getText().toString();
                int userAge = Integer.parseInt(edit_age.getText().toString());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            //success register
                            if (success) {

                                Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            // fail register
                            else {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // request to server using volley
                RegisterRequest registerRequest = new RegisterRequest(userID, userPass, userName, userAge, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
