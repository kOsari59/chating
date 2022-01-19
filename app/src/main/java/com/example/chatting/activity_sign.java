package com.example.chatting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class activity_sign extends AppCompatActivity {

    EditText et_id, et_pw, et_pwcheck;
    Button btn_idcheck, btn_pwcheck, btn_sub, btn_cancel;

    boolean passcheck = false; //비밀번호 확인
    boolean idcheck = false; //id가 겹치는게 있는지 확인

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        et_id = findViewById(R.id.et_userid);
        et_pw = findViewById(R.id.et_pass);
        et_pwcheck = findViewById(R.id.et_pwcheck);
        btn_idcheck = findViewById(R.id.btn_idcheck);
        btn_pwcheck = findViewById(R.id.btn_passcheck);
        btn_sub = findViewById(R.id.btn_submission);
        btn_cancel = findViewById(R.id.btn_cancel);


        btn_pwcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(et_pw.getText()).equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    passcheck=false;
                } else {
                    if (String.valueOf(et_pw.getText()).equals(String.valueOf(et_pwcheck.getText()))) { //비밀번호 확인이랑 비밀번호 텍스트가 같으면 가능

                        Toast.makeText(getApplicationContext(), "비밀번호가 같습니다.", Toast.LENGTH_SHORT).show();
                        passcheck = true;

                    } else {

                        Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                        passcheck = false;
                    }
                }
            }
        });

        btn_idcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_id.getText().toString(); //id저장
                Response.Listener<String> responseListener = new Response.Listener<String>() { //php로 데이터를 보내고 보낸 데이터를 사용하는 부분
                    @Override
                    public void onResponse(String response) {
                        try {
                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                            System.out.println("hongchul" + response);

                            JSONObject jsonObject = new JSONObject(response); //JSON으로 출력된 값을 불러오기
                            boolean success = jsonObject.getBoolean("success"); // 그값중 success가 참이면 실행
                            if (success) { // id 검색 성공한 경우
                                Toast.makeText(getApplicationContext(),"id가 겹칩니다.",Toast.LENGTH_SHORT).show();
                                idcheck=false;
                            } else { // id 중복 없는 경우
                                Toast.makeText(getApplicationContext(),"id 사용 가능",Toast.LENGTH_SHORT).show();
                                idcheck=true;
                                return;
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                Idcheck ic = new Idcheck(userID, responseListener); //던져줄값 형식 맞춰서 만들어주기
                RequestQueue idqueue = Volley.newRequestQueue(activity_sign.this); //던져줄값 던질 큐
                idqueue.add(ic);// 큐로 던지기

            }
        });

        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (idcheck == true && passcheck == true) {
                    String userID = et_id.getText().toString(); //id저장
                    String userPass = et_pw.getText().toString(); // pw 저장
                    Response.Listener<String> responseListener = new Response.Listener<String>() { //php로 데이터를 보내고 보낸 데이터를 사용하는 부분
                        @Override
                        public void onResponse(String response) {
                            try {
                                // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가

                                JSONObject jsonObject = new JSONObject(response); //JSON으로 출력된 값을 불러오기
                                boolean success = jsonObject.getBoolean("success"); // 그값중 success가 참이면 실행
                                if (success) { // 로그인에 성공한 경우

                                    Toast.makeText(getApplicationContext(), "계정 생성에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    finish(); //액티비티 닫기

                                } else { // 로그인에 실패한 경우
                                    Toast.makeText(getApplicationContext(), "계정 생성에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    Sign sign = new Sign(userID, userPass, responseListener); //던져줄값 형식 맞춰서 만들어주기
                    RequestQueue signqueue = Volley.newRequestQueue(activity_sign.this); //던져줄값 던질 큐
                    signqueue.add(sign);// 큐로 던지기
                }else{
                    Toast.makeText(getApplicationContext(), "ID와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}