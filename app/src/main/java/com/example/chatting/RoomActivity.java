package com.example.chatting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;

public class RoomActivity extends AppCompatActivity {
    Button btn_send;
    TextView tv_room;
    EditText et_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        btn_send=findViewById(R.id.btn_send);
        tv_room=findViewById(R.id.tv_room);
        et_msg=findViewById(R.id.et_msg);

        long now = System.currentTimeMillis();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message= String.valueOf(et_msg.getText());

                Response.Listener<String> responseListener = new Response.Listener<String>() { //php로 데이터를 보내고 보낸 데이터를 사용하는 부분
                    @Override
                    public void onResponse(String response) {
                        try {
                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                            System.out.println("hongchul" + response);

                            JSONObject jsonObject = new JSONObject(response); //JSON으로 출력된 값을 불러오기

                            JSONArray jsonArray = jsonObject.getJSONArray("result"); //JsonObject JsonArray로 변경
                            for(int i = 0 ; i<jsonArray.length(); i++){

                            // 로그인에 성공한 경우


                                String userID = jsonObject.getString("userID"); // 그 값중 userID 검색
                                String Msg = jsonObject.getString("Msg");
                                String Mtime = jsonObject.getString("Mtime");


                                tv_room.setText(tv_room.getText()+userID+Msg+Mtime);

                                Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                RoomLoad rl = new RoomLoad("123", message,  String.valueOf(new Date(now)), responseListener); //던져줄값 형식 맞춰서 만들어주기
                RequestQueue roomqueue = Volley.newRequestQueue(RoomActivity.this); //던져줄값 던질 큐
                roomqueue.add(rl);// 큐로 던지기
            }

        });
    }
}