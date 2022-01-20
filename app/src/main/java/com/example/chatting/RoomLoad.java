package com.example.chatting;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RoomLoad extends StringRequest { // 로그인 부분에서 던질 부분 만들기

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://1.241.64.177:5678/Send.php";
    private Map<String, String> map;


    public RoomLoad(String userID, String Msg, String Mtime,Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();

        map.put("userID",userID);
        map.put("Msg",Msg);
        map.put("Mtime", Mtime);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
