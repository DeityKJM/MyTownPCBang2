package com.example.mytownpcbang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
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

public class MainActivity extends AppCompatActivity {


    pcAdapter PcbangAdapter;
    ArrayList<PcBang_info> Pcinfo_arr = new ArrayList<>();
    ListView pcbang_list;
    Switch fav_switch;
    TextView textView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ArrayList<PcBang_info> Pcbang_fav_arr = new ArrayList<>();

    void setLayout() {

        pcbang_list = (ListView) findViewById(R.id.main_fav_listview);
        fav_switch = (Switch) findViewById(R.id.fav_switch);
        textView = (TextView) findViewById(R.id.tmpview);

        PcbangAdapter = new pcAdapter(this, R.layout.pcbanglist, Pcbang_fav_arr);
        pcbang_list.setAdapter(PcbangAdapter);


        pref = getSharedPreferences("test", MODE_PRIVATE);
        editor = pref.edit();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayout();
        boolean tmp = pref.getBoolean("fav_switch", false); //즐겨찾기 on/off 체크
        if (tmp) {
            fav_switch.setChecked(true);
            pcbang_list.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        } else {
            fav_switch.setChecked(false);
            textView.setVisibility(View.VISIBLE);
            pcbang_list.setVisibility(View.GONE);
        }
        //샘플데이터 입력
        SetPcBangList();

        if (pref.getString("fav_list", null) == null) {
            Log.d("fav_data", "null");
        } else {
            String fav_arr = pref.getString("fav_list", null);
            if (fav_arr != null) {
                try {
                    JSONArray json_fav_list = new JSONArray(fav_arr);
                    for (int i = 0; i < json_fav_list.length(); i++) {
                        for (int c = 0; c < Pcinfo_arr.size(); c++) {

                            if (json_fav_list.getJSONObject(i).toString().equals(Pcinfo_arr.get(c).getPcBangName())) {
                                Pcbang_fav_arr.add(Pcinfo_arr.get(c));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            PcbangAdapter.notifyDataSetChanged();

        }


        pcbang_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //상세정보 액티비티
                Intent pcbang = new Intent(MainActivity.this, Pcbang_detail_Activity.class);
                pcbang.putExtra("pcbanginfo", Pcinfo_arr.get(position));//pc방 고유 코드
                startActivity(pcbang);


            }
        });

        fav_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Log.d("스위치", "on");
                    pcbang_list.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    editor.putBoolean("fav_switch", true);
                } else {
                    Log.d("스위치 ", "off");
                    textView.setVisibility(View.VISIBLE);
                    pcbang_list.setVisibility(View.GONE);
                    editor.putBoolean("fav_switch", false);
                }
                editor.commit();
            }
        });

    }

    //pc방리스트 보여주는 액티비티
    public void MainButton(View v) {
        switch (v.getId()) {
            case R.id.btn_myloc://내위치
                Intent intent1 = new Intent(MainActivity.this, Pcbang_List_Activity.class);
                intent1.putExtra("type", "1");
                startActivity(intent1);
                break;
            case R.id.btn_sub://지하철역
                Intent intent2 = new Intent(MainActivity.this, Pcbang_List_Activity.class);
                intent2.putExtra("type", "2");
                startActivity(intent2);
                break;
            case R.id.btn_event://이벤트
                Intent intent3 = new Intent(MainActivity.this, Pcbang_List_Activity.class);
                intent3.putExtra("type", "3");
                startActivity(intent3);
                break;
            case R.id.btn_fav://즐겨찾기
                Intent intent4 = new Intent(MainActivity.this, Pcbang_List_Activity.class);
                intent4.putExtra("type", "4");
                startActivity(intent4);
                break;
        }

    }


    public void SetPcBangList() { //샘플데이터

        HttpRequester httpRequester = new HttpRequester();
        httpRequester.request(Pcbang_uri.pcBang_allceo, httpCallback);

    }

    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            try {
                Pcinfo_arr.clear(); //서버 데이터 통신

                JSONArray root = new JSONArray(result);

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
                Toast.makeText(MainActivity.this, "데이터 에러", Toast.LENGTH_SHORT).show();
            }
            PcbangAdapter.notifyDataSetChanged();
        }
    };
}
