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

import com.example.mytownpcbang.PcbangArray.pcAdapter;
import com.example.mytownpcbang.PcbangArray.pcinfo;
import com.example.mytownpcbang.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    pcAdapter PcbangAdapter;
    ArrayList<pcinfo> Pcinfo_arr = new ArrayList<>();
    ListView pcbang_list;
    Switch fav_switch;
    TextView textView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    void setLayout() {

        pcbang_list = (ListView) findViewById(R.id.main_fav_listview);
        fav_switch = (Switch) findViewById(R.id.fav_switch);
        textView = (TextView) findViewById(R.id.tmpview);

        PcbangAdapter = new pcAdapter(this, R.layout.pcbanglist, Pcinfo_arr);
        pcbang_list.setAdapter(PcbangAdapter);


        pref = getSharedPreferences("PcBangtest", MODE_PRIVATE);
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

        pcbang_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //상세정보 액티비티
                Intent pcbang = new Intent(MainActivity.this, Pcbang_detail_Activity.class);
                pcbang.putExtra("Pcbang_code",Pcinfo_arr.get(position));//pc방 고유 코드
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
        Pcinfo_arr.clear(); //서버 데이터 통신
        //샘플데이터    pcinfo(String pcBangName, int pcBangTel, int postCode, String roadAddress, String detailAddress, int ipFirst, int ipSecond, int ipThird)
        Pcinfo_arr.add(new pcinfo("3POP\nPC방 ", 0101155555, 0, "서울시 동작구", "123", 0, 0, 0, 4.5f));
        Pcinfo_arr.add(new pcinfo("TRON\nPC방", 0101155555, 0, "대전시 몰랑구", "456", 0, 0, 0,5f));
        Pcinfo_arr.add(new pcinfo("XRP\nPC방", 0101155555, 0, "대구시 동태구", "789", 0, 0, 0,3.5f));
        Pcinfo_arr.add(new pcinfo("BTC\nPC방", 0101115555, 0, "부산시 벗엇구", "147", 0, 0, 0,2.5f));
        Pcinfo_arr.add(new pcinfo("UPBIT\nPC방", 0101115555, 0, "찍고시 트론가좌", "258", 0, 0, 0,1.5f));
        Pcinfo_arr.add(new pcinfo("GAZEA\nPC방", 0101115555, 0, "아하시 야시시", "369", 0, 0, 0,0.5f));
        PcbangAdapter.notifyDataSetChanged();
    }


}
