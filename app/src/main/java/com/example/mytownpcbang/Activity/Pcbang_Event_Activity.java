package com.example.mytownpcbang.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
 * Created by Jeongmin on 2018-01-20.
 */

public class Pcbang_Event_Activity extends Activity {

    TextView btn_close, App_Title;

    pcAdapter PcbangAdapter;
    ArrayList<PcBang_info> Pcinfo_arr = new ArrayList<>();
    ListView pcbang_list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_event_layout);

        btn_close = (TextView) findViewById(R.id.btn_text_back);
        App_Title = (TextView) findViewById(R.id.text_title);
        pcbang_list = (ListView) findViewById(R.id.event_list);

        PcbangAdapter = new pcAdapter(this, R.layout.pcbanglist, Pcinfo_arr);
        pcbang_list.setAdapter(PcbangAdapter);

        HttpRequester httpRequester = new HttpRequester();
        httpRequester.request(Pcbang_uri.pcBang_allceo, httpCallback);


        pcbang_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //상세정보 액티비티
                Intent pcbang = new Intent(Pcbang_Event_Activity.this, Pcbang_detail_Activity.class);
                pcbang.putExtra("pcbanginfo", Pcinfo_arr.get(position).get_id());//pc방 고유 코드
                startActivity(pcbang);


            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            try {
                Double tmps;
                Pcinfo_arr.clear(); //서버 데이터 통신
                JSONArray root = new JSONArray(result);//즐겨찾기 데이터값
                Log.d("event_data", "call" + result);
                for (int i = 0; i < root.length(); i++) {
                    try {
                        tmps = root.getJSONObject(i).getDouble("ratingScore");
                    } catch (JSONException e) {
                        tmps = 0.0;
                        Log.d("event_data","rating error");
                    }

                    Pcinfo_arr.add(
                            new PcBang_info(root.getJSONObject(i).getString("pcBangName"),
                                    root.getJSONObject(i).getString("tel"),
                                    root.getJSONObject(i).getJSONObject("address").getString("postCode"),
                                    root.getJSONObject(i).getJSONObject("address").getString("roadAddress"),
                                    root.getJSONObject(i).getString("_id"),
                                    root.getJSONObject(i).getJSONObject("address").getString("detailAddress"),
                                    tmps,
                                    Double.parseDouble(root.getJSONObject(i).getJSONObject("location").getString("lat")),
                                    Double.parseDouble(root.getJSONObject(i).getJSONObject("location").getString("lon"))));

                }

                PcbangAdapter.notifyDataSetChanged();
                Log.d("event_data", "호출완료");


            } catch (JSONException d) {

                d.printStackTrace();
                Log.d("event_data","어디서 에러난거?");

            } catch (NullPointerException f) {
                Toast.makeText(Pcbang_Event_Activity.this, "데이터 에러", Toast.LENGTH_SHORT).show();
            }

        }
    };


}

