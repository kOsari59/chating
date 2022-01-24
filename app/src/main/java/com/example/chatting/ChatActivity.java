package com.example.chatting;


import static com.example.chatting.MainActivity.userID;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    static ArrayList<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.label);

        list = new ArrayList<String>();
        // 검색에 사용할 데이터을 미리 저장한다.
        readChat();


        // 리스트에 연동될 아답터를 생성한다.

    }

    public void readChat() {

        Response.Listener<String> responseListener = new Response.Listener<String>() { //php로 데이터를 보내고 보낸 데이터를 사용하는 부분
            @Override
            public void onResponse(String response) {
                try {
                    // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                    JSONObject jsonObject = new JSONObject(response); //JSON으로 출력된 값을 불러오기
                    addList(jsonObject);
                    adapter = new SearchAdapter(list, ChatActivity.this);
                    // 리스트뷰에 아답터를 연결한다.
                    listView.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        Chat chat = new Chat(userID, responseListener); //던져줄값 형식 맞춰서 만들어주기
        RequestQueue signqueue = Volley.newRequestQueue(ChatActivity.this); //던져줄값 던질 큐
        signqueue.add(chat);// 큐로 던지기

    }

    public void addList(JSONObject a){
        try {
            JSONObject ab = a;
            JSONArray jsonArray = ab.getJSONArray("result"); //JsonObject JsonArray로 변경
            for (int i = 0; i < jsonArray.length(); i++) {

                // 로그인에 성공한 경우
                ab = jsonArray.getJSONObject(i);

                String User1 = ab.getString("User1"); // 그 값중 userID 검색
                String User2 = ab.getString("User2");
                String ID = ab.getString("ID");

                list.add(User1+User2+ID);
                Log.d("TAG", list.get(i));

            }

        }catch (Exception e){
            Log.d("TAG", e.toString());
        }
    }

}
