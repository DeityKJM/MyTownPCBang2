package com.example.mytownpcbang.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytownpcbang.R;

/**
 * Created by KimJeongMin on 2017-12-30.
 */

public class Pcbang_List_Activity extends Activity {

    TextView btn_close,App_Title,btn_change_event;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_list_activity);

        btn_close=(TextView)findViewById(R.id.btn_close);
        App_Title=(TextView)findViewById(R.id.App_Title);
        btn_change_event=(TextView)findViewById(R.id.btn_change_event);

        Intent i = getIntent();
        String type = i.getStringExtra("type");
        TypeDefine(type);





        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_change_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Pcbang_List_Activity.this, "미구현", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void TypeDefine(String type){
        switch (type){
            case "1":
                App_Title.setText("내 위치주변 PC방");
                btn_change_event.setText("내위치찾기");
                //위치 검색 반경 m정해서
                break;
            case "2":
                App_Title.setText("지하철 근처 PC방");
                btn_change_event.setText("지하철역검색");
                //지하철역 검색? 후 지하철역 검색결과/클릭후 반경 m
                break;
            case "3":
                App_Title.setText("이벤트 PC방");
                btn_change_event.setText("??");
                //이벤트 on 피시방 / 내위치 주변?
                break;
            case "4":
                App_Title.setText("즐겨찾기 PC방");
                btn_change_event.setText("즐겨찾기");
                //즐겨찾기가 눌린거
                break;
        }
    }




}
