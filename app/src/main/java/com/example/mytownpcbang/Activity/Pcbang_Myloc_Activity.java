package com.example.mytownpcbang.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.mytownpcbang.Server.Pcbang_uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Jeongmin on 2018-01-20.
 */

public class Pcbang_Myloc_Activity extends Activity {

    TextView btn_text_back,text_title;

    pcAdapter PcbangAdapter;
    ArrayList<PcBang_info> Pcinfo_arr = new ArrayList<>();
    ListView pcbang_list;
    double topLon, bottomLon, leftLat, rightLat;
    JSONObject jsonObject;

    public void setting(){
        btn_text_back=(TextView)findViewById(R.id.btn_text_back);
        text_title=(TextView)findViewById(R.id.text_title);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_myloc_layout);
        //레이아웃 세팅
        setting();
        topLon = 37.588287;
        bottomLon = 37.586287;
        leftLat = 127.009234;
        rightLat = 127.009034;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("topLon", topLon);
            jsonObject.put("bottomLon", bottomLon);
            jsonObject.put("leftLat", leftLat);
            jsonObject.put("rightLat", rightLat);

        } catch (Exception e) {
            e.printStackTrace();
        }

        SetPcBangList();

        pcbang_list=(ListView)findViewById(R.id.loc_list);

        PcbangAdapter = new pcAdapter(this, R.layout.pcbanglist, Pcinfo_arr);


        pcbang_list.setAdapter(PcbangAdapter);


        pcbang_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //상세정보 액티비티
                Intent pcbang = new Intent(Pcbang_Myloc_Activity.this, Pcbang_detail_Activity.class);
                pcbang.putExtra("pcbanginfo", Pcinfo_arr.get(position));//pc방 고유 코드
                startActivity(pcbang);


            }
        });
        btn_text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    public synchronized void SetPcBangList() { //샘플데이터

        HttpRequest httpRequester = new HttpRequest();
        httpRequester.request(Pcbang_uri.pcBang_map_range, httpCallback);

    }


    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            try {
                Pcinfo_arr.clear(); //서버 데이터 통신
                JSONArray root = new JSONArray(result);//즐겨찾기 데이터값
                for (int i = 0; i < root.length(); i++) {
                    Pcinfo_arr.add(
                            new PcBang_info(root.getJSONObject(i).getString("pcBangName"),
                                    root.getJSONObject(i).getString("tel"),
                                    root.getJSONObject(i).getJSONObject("address").getString("postCode"),
                                    root.getJSONObject(i).getJSONObject("address").getString("roadAddress"),
                                    root.getJSONObject(i).getString("_id"),
                                    root.getJSONObject(i).getJSONObject("address").getString("detailAddress"),
                                    "4",
                                    root.getJSONObject(i).getJSONObject("location").getString("lat"),
                                    root.getJSONObject(i).getJSONObject("location").getString("lon")));
                }
            } catch (JSONException d) {
                d.printStackTrace();
            }
            catch (NullPointerException f){
                Toast.makeText(Pcbang_Myloc_Activity.this, "데이터 에러", Toast.LENGTH_SHORT).show();
            }
            PcbangAdapter.notifyDataSetChanged();
        }
    };

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
                String postData = jsonObject.toString();
                // PrintWriter pw ;
                BufferedWriter pw;
                BufferedReader in = null;

                //add~~~~~~~~~~~~~~
                try {
                    URL text = new URL(url);
                    HttpURLConnection http = (HttpURLConnection) text.openConnection();
                    http.setRequestProperty("Content-Type", "application/json");
                    http.setRequestProperty("Accept", "application/json");
                    http.setConnectTimeout(10000);
                    http.setReadTimeout(10000);
                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);


                    OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                    pw = new BufferedWriter(outStream);
                    pw.write(postData);
                    pw.flush();

                    Log.d("fav_data", " " + jsonObject.toString());

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
