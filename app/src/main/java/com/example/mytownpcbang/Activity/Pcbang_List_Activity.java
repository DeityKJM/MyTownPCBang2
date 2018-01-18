package com.example.mytownpcbang.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytownpcbang.PcbangArray.PcBang_info;
import com.example.mytownpcbang.PcbangArray.pcAdapter;
import com.example.mytownpcbang.R;
import com.example.mytownpcbang.Server.HttpCallback;
import com.example.mytownpcbang.Server.HttpRequester;
import com.example.mytownpcbang.Server.Pcbang_uri;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by KimJeongMin on 2017-12-30.
 */

public class Pcbang_List_Activity extends Activity {

    TextView btn_close,App_Title,btn_change_event;

    pcAdapter PcbangAdapter;
    ArrayList<PcBang_info> Pcinfo_arr = new ArrayList<>();
    ListView pcbang_list;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_list_activity);

        btn_close=(TextView)findViewById(R.id.btn_close);
        App_Title=(TextView)findViewById(R.id.App_Title);
        btn_change_event=(TextView)findViewById(R.id.btn_change_event);



        pcbang_list=(ListView)findViewById(R.id.loc_list);

        PcbangAdapter = new pcAdapter(this, R.layout.pcbanglist, Pcinfo_arr);

        Intent i = getIntent();
        String type = i.getStringExtra("type");
        TypeDefine(type);


        pcbang_list.setAdapter(PcbangAdapter);


        pcbang_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //상세정보 액티비티
                Intent pcbang = new Intent(Pcbang_List_Activity.this, Pcbang_detail_Activity.class);
                pcbang.putExtra("pcbanginfo", Pcinfo_arr.get(position));//pc방 고유 코드
                startActivity(pcbang);


            }
        });
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
                HttpRequester httpRequester = new HttpRequester();
                httpRequester.request(Pcbang_uri.pcBang_allceo, httpCallback);

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
    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            try {
                Pcinfo_arr.clear(); //서버 데이터 통신

                JSONArray root = new JSONArray(result);
//즐겨찾기 데이터값
                for (int i = 0; i < root.length(); i++) {
                    Pcinfo_arr.add(
                            new PcBang_info(root.getJSONObject(0).getString("pcBangName"),
                                    root.getJSONObject(0).getString("tel"),
                                    root.getJSONObject(i).getJSONObject("address").getString("postCode"),
                                    root.getJSONObject(1).getJSONObject("address").getString("roadAddress"),
                                    root.getJSONObject(2).getJSONObject("address").getString("detailAddress"),
                                    "4",
                                    root.getJSONObject(i).getJSONObject("location").getString("lat"),
                                    root.getJSONObject(i).getJSONObject("location").getString("lon")));

                }


            } catch (JSONException d) {

                d.printStackTrace();

            }
            catch (NullPointerException f){
                Toast.makeText(Pcbang_List_Activity.this, "데이터 에러", Toast.LENGTH_SHORT).show();
            }
            PcbangAdapter.notifyDataSetChanged();
        }
    };



}
