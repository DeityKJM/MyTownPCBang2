package com.example.mytownpcbang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.example.mytownpcbang.Server.Pcbang_uri;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    pcAdapter PcbangAdapter;
    ArrayList<PcBang_info> Pcinfo_arr = new ArrayList<>();
    ListView pcbang_list;
    Switch fav_switch;
    TextView textView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    double topLon, bottomLon, leftLat, rightLat;


    void setLayout() {

        pcbang_list = (ListView) findViewById(R.id.main_fav_listview);
        fav_switch = (Switch) findViewById(R.id.fav_switch);
        textView = (TextView) findViewById(R.id.tmpview);

        PcbangAdapter = new pcAdapter(this, R.layout.pcbanglist, Pcinfo_arr);
        pcbang_list.setAdapter(PcbangAdapter);


        pref = getSharedPreferences("test2", MODE_PRIVATE);
        editor = pref.edit();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayout();
        topLon = 37.588287;
        bottomLon = 37.586287;
        leftLat = 127.009234;
        rightLat = 127.009034;
        boolean tmp = pref.getBoolean("fav_switch", false); //즐겨찾기 on/off 체크
        Callfav_list();

        if (tmp) {
            fav_switch.setChecked(true);
            pcbang_list.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        } else {
            fav_switch.setChecked(false);
            textView.setVisibility(View.VISIBLE);
            pcbang_list.setVisibility(View.GONE);
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
                    pcbang_list.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    editor.putBoolean("fav_switch", true);
                } else {
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
                Intent intent1 = new Intent(MainActivity.this, Pcbang_Myloc_Activity.class);
                startActivity(intent1);
                break;
            case R.id.btn_sub://지하철역
                Intent intent2 = new Intent(MainActivity.this, Pcbang_Subway_Activity.class);
                startActivity(intent2);
                break;
            case R.id.btn_event://이벤트
                Intent intent3 = new Intent(MainActivity.this, Pcbang_Event_Activity.class);
                startActivity(intent3);
                break;
            case R.id.btn_fav://즐겨찾기
                Intent intent4 = new Intent(MainActivity.this, Pcbang_Favorite_Activity.class);
                startActivity(intent4);
                break;
        }

    }


    public synchronized void SetPcBangList(String data) { //샘플데이터

        HttpRequest httpRequester = new HttpRequest();
        httpRequester.request(Pcbang_uri.pcBang_search_id, httpCallback);

    }

    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            try {
                JSONArray root = new JSONArray(result);
                Log.d("fav_data", "call" + result);
                Pcinfo_arr.add(
                        new PcBang_info(root.getJSONObject(0).getString("pcBangName"),
                                root.getJSONObject(0).getString("tel"),
                                root.getJSONObject(0).getJSONObject("address").getString("postCode"),
                                root.getJSONObject(0).getJSONObject("address").getString("roadAddress"),
                                root.getJSONObject(0).getString("_id"),
                                root.getJSONObject(0).getJSONObject("address").getString("detailAddress"),
                                "4",
                                root.getJSONObject(0).getJSONObject("location").getString("lat"),
                                root.getJSONObject(0).getJSONObject("location").getString("lon")));


                Log.d("fav_data", "호출완료");

            } catch (JSONException d) {

                d.printStackTrace();

            } catch (NullPointerException f) {
                Toast.makeText(MainActivity.this, "데이터 에러", Toast.LENGTH_SHORT).show();
            }
            PcbangAdapter.notifyDataSetChanged();
        }
    };

    public void onResume() {
        super.onResume();
        Callfav_list();
    }

    public void Callfav_list() {
        Pcinfo_arr.clear();
        String fav_arr = pref.getString("fav_list", null);
        if (fav_arr == null) {
        } else {

            try {
                JSONArray json_fav_list = new JSONArray(fav_arr);


                for (int i = 0; i < json_fav_list.length(); i++) {
                    SetPcBangList(json_fav_list.optString(i));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            Log.d("fav_data", "shared  : " + Pcinfo_arr.size());
        }
    }


    public class HttpRequest {

        HttpTask http;

        public void request(String url, HttpCallback callback) {
            http = new HttpRequest.HttpTask(url, callback);
            http.execute();
        }

        public void cancel() {
            if (http != null)
                http.cancel(true);
        }

        private class HttpTask extends AsyncTask<Void, Void, String> {
            String url;
            HttpCallback callback;

            public HttpTask(String url, HttpCallback callback) {
                this.url = url;
                this.callback = callback;
            }

            @Override
            protected String doInBackground(Void... nothing) {
                String response = "";
                String postData = "";
                PrintWriter pw = null;
                BufferedReader in = null;

                //add~~~~~~~~~~~~~~
                try {
                    URL text = new URL(url);
                    HttpURLConnection http = (HttpURLConnection) text.openConnection();
                    http.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
                    http.setConnectTimeout(10000);
                    http.setReadTimeout(10000);
                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("topLon").append("=").append(topLon).append("&");                 // php 변수에 값 대입
                    buffer.append("bottomLon").append("=").append(bottomLon).append("&");   // php 변수 앞에 '$' 붙이지 않는다
                    buffer.append("leftLat").append("=").append(leftLat).append("&");           // 변수 구분은 '&' 사용
                    buffer.append("rightLat").append("=").append(rightLat);

                    OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "EUC-KR");
                    PrintWriter writer = new PrintWriter(outStream);
                    writer.write(buffer.toString());
                    writer.flush();

                    in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
                    StringBuffer sb = new StringBuffer();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    response = sb.toString();


                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

                return response;
            }

            @Override
            protected void onPostExecute(String result) {
                //add~~~~~~~~~~~
                this.callback.onResult(result);

            }
        }

    }


}

