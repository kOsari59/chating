package com.example.chatting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class makechat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makechat);




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
    }
}