package com.example.chatting;

import static com.example.chatting.MainActivity.userID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class makechat extends AppCompatActivity {
    EditText et_idsearch;
    Button btn_search,btn_start,btn_cc;
    Boolean idcheck=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makechat);

        et_idsearch=(EditText)findViewById(R.id.et_idsearch) ;
        btn_search=(Button)findViewById(R.id.btn_search);
        btn_start=(Button)findViewById(R.id.btn_start);
        btn_cc=(Button)findViewById(R.id.btn_cc);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_idsearch.getText().toString(); //id저장

                Response.Listener<String> responseListener = new Response.Listener<String>() { //php로 데이터를 보내고 보낸 데이터를 사용하는 부분
                    @Override
                    public void onResponse(String response) {
                        try {

                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                            System.out.println("hongchul" + response);

                            JSONObject jsonObject = new JSONObject(response); //JSON으로 출력된 값을 불러오기
                            boolean success = jsonObject.getBoolean("success"); // 그값중 success가 참이면 실행

                            if (success) { // id 검색 성공한 경우
                                Toast.makeText(getApplicationContext(),"id가 검색됨",Toast.LENGTH_SHORT).show();
                                idcheck=true;
                            } else { // id 중복 없는 경우
                                Toast.makeText(getApplicationContext(),"id가 없음",Toast.LENGTH_SHORT).show();
                                idcheck=false;
                                return;
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                Idcheck ic = new Idcheck(userID, responseListener); //던져줄값 형식 맞춰서 만들어주기
                RequestQueue idqueue = Volley.newRequestQueue(makechat.this); //던져줄값 던질 큐
                idqueue.add(ic);// 큐로 던지기

            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user2 = et_idsearch.getText().toString();
                if (idcheck==true){

                    Response.Listener<String> responseListener = new Response.Listener<String>() { //php로 데이터를 보내고 보낸 데이터를 사용하는 부분
                        @Override
                        public void onResponse(String response) {
                            try {

                                // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                                System.out.println("hongchul" + response);

                                JSONObject jsonObject = new JSONObject(response); //JSON으로 출력된 값을 불러오기

                                Log.d("nana", "onResponse: ");

                                Response.Listener<String> responseListener = new Response.Listener<String>() { //php로 데이터를 보내고 보낸 데이터를 사용하는 부분
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                                            System.out.println("hongchul" + response);


                                        } catch (Exception e) {
                                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                };

                                CreateRoom cr = new CreateRoom("ref7", responseListener); //던져줄값 형식 맞춰서 만들어주기
                                RequestQueue idqueue = Volley.newRequestQueue(makechat.this); //던져줄값 던질 큐
                                idqueue.add(cr);// 큐로 던지기

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                                Log.d("nana", e.toString());
                            }
                        }
                    };

                    mchat ic = new mchat(userID,user2, responseListener); //던져줄값 형식 맞춰서 만들어주기
                    RequestQueue idqueue = Volley.newRequestQueue(makechat.this); //던져줄값 던질 큐
                    idqueue.add(ic);// 큐로 던지기

                    /*Intent it = new Intent(makechat.this,ChatActivity.class);
                    startActivity(it);

                    finish();*/
                }else{
                    return;
                }

            }
        });

    }
}
