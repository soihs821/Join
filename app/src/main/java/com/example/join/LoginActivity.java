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

public class LoginActivity extends AppCompatActivity {

    private EditText edit_id, edit_pass;
    private Button btn_login, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        edit_id = findViewById( R.id.edit_id );
        edit_pass = findViewById( R.id.edit_pass );

        btn_register = findViewById( R.id.register_btn );
        btn_register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, RegisterActivity.class );
                startActivity( intent );
            }
        });


        btn_login = findViewById( R.id.login_btn );
        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = edit_id.getText().toString();
                String userPass = edit_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if(success) {//로그인 성공시

                                String userID = jsonObject.getString( "userID" );
                                String userPass = jsonObject.getString( "userPassword" );
                                String userName = jsonObject.getString( "userName" );
                                String userAge = jsonObject.getString( "userAge" );

                                Toast.makeText( getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent( LoginActivity.this, MainActivity.class );

                                intent.putExtra( "userID", userID );
                                intent.putExtra( "userPass", userPass );
                                intent.putExtra( "userName", userName );
                                intent.putExtra( "userAge", userAge );

                                startActivity( intent );

                            } else {//로그인 실패시
                                Toast.makeText( getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest( userID, userPass, responseListener );
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                queue.add( loginRequest );

            }
        });
    }
}