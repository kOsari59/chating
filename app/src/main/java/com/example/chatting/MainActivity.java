package com.example.chatting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText et_ID;
    EditText et_PASS;
    Button btlogin ,btsign;
    boolean bl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_ID = findViewById(R.id.et_ID);
        et_PASS = findViewById(R.id.et_PASS);
        btlogin = findViewById(R.id.btn_login);
        btsign = findViewById(R.id.btn_sign);


        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_ID.getText().toString(); //id저장
                String userPass = et_PASS.getText().toString(); // pw 저장
                Response.Listener<String> responseListener = new Response.Listener<String>() { //php로 데이터를 보내고 보낸 데이터를 사용하는 부분
                    @Override
                    public void onResponse(String response) {
                        try {
                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                            System.out.println("hongchul" + response);

                            JSONObject jsonObject = new JSONObject(response); //JSON으로 출력된 값을 불러오기
                            boolean success = jsonObject.getBoolean("success"); // 그값중 success가 참이면 실행
                            if (success) { // 로그인에 성공한 경우
                                String userID = jsonObject.getString("userID"); // 그 값중 userID 검색
                                String userPass = jsonObject.getString("userPassword"); // 그 값중 userPassword 검색


                                Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                bl = true;
                            } else { // 로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener); //던져줄값 형식 맞춰서 만들어주기
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this); //던져줄값 던질 큐
                queue.add(loginRequest);// 큐로 던지기
            }

        });

        btsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,activity_sign.class);
                startActivity(it);
            }
        });
    }
}