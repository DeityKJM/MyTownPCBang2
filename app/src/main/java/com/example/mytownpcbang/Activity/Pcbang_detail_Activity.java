package com.example.mytownpcbang.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytownpcbang.PcbangArray.PcBang_info;
import com.example.mytownpcbang.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.util.ArrayList;

import pl.polidea.view.ZoomView;

/**
 * Created by KimJeongMin on 2017-12-17.
 */

public class Pcbang_detail_Activity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    TextView btn_text_back, text_title, btn_text_fav;
    LinearLayout Layout_pcbang_seat, Layout_pcbang_map, Layout_pcbang_review; //좌석/후기/위치
    TextView Pcbang_image, pcbang_info, btn_text_event, btn_text_pcspec, btn_text_loc, btn_text_review, btn_text_review_write;
    ListView review_listview;
    RatingBar pcbang_ratingbar;
    GoogleMap map;
    LinearLayout container;
    View v;
    GridLayout grid;
    //

    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;


    int[] nums = {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0};
    PcBang_info arr;
    ArrayList<String> fav_list = new ArrayList<>();
//

    public void SetLayout() {
        container = (LinearLayout) findViewById(R.id.container);
        btn_text_back = (TextView) findViewById(R.id.btn_text_back);
        text_title = (TextView) findViewById(R.id.text_title);
        btn_text_fav = (TextView) findViewById(R.id.btn_text_fav);
        Layout_pcbang_seat = (LinearLayout) findViewById(R.id.lay_pcbang_Seat);
        Layout_pcbang_map = (LinearLayout) findViewById(R.id.lay_pcbang_map);
        Layout_pcbang_review = (LinearLayout) findViewById(R.id.lay_pcbang_review);
        review_listview = (ListView) findViewById(R.id.review_listview);
        btn_text_review = (TextView) findViewById(R.id.btn_text_review);
        btn_text_loc = (TextView) findViewById(R.id.btn_text_loc);
        Pcbang_image = (TextView) findViewById(R.id.Pcbang_image);
        pcbang_info = (TextView) findViewById(R.id.pcbang_info);
        pcbang_ratingbar = (RatingBar) findViewById(R.id.pcbang_rating);
        btn_text_event = (TextView) findViewById(R.id.btn_text_event);
        btn_text_pcspec = (TextView) findViewById(R.id.btn_text_pcspec);
        btn_text_review_write = (TextView) findViewById(R.id.btn_text_review_write);
        btn_text_review_write.setOnClickListener(this);
        btn_text_loc.setOnClickListener(this);
        btn_text_review.setOnClickListener(this);
        btn_text_back.setOnClickListener(this);
        btn_text_fav.setOnClickListener(this);
        mPref = getSharedPreferences("test", MODE_PRIVATE);
        mEditor = mPref.edit();


        //intent 데이터 받기
        arr = (PcBang_info) getIntent().getSerializableExtra("pcbanginfo");

        //pc방 정보 세팅
        Pcbang_image.setText(arr.getPcBangName());
        pcbang_info.setText(arr.getdetailAddress() + arr.getroadAddress());
        btn_text_pcspec.setText("사양\n" + arr.getpcBangTel());
        pcbang_ratingbar.setNumStars(5);
        pcbang_ratingbar.setStepSize(0.5f);
        pcbang_ratingbar.setRating(Float.parseFloat(arr.getStarnum()));

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_detail_layout);
        SetLayout();//레이아웃세팅;

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        //map세팅


        //인플레이트 레이아웃
        v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);
        grid = (GridLayout) v.findViewById(R.id.Grid);
        grid.setColumnCount(5); //가로세로 설정
        grid.setRowCount(5);
        container.addView(PCseatView(v, 4, 5, nums));


       String json_fav_list= mPref.getString("fav_list",null); //즐겨찾기 목록 불러오기
       if(json_fav_list!=null){
           try{
               JSONArray fav_array = new JSONArray(json_fav_list);
               for(int i =0; i<fav_array.length(); i++){
                   fav_list.add(fav_array.getJSONObject(i).toString());
                   if(fav_array.getJSONObject(i).toString().equals(arr.getPcBangName())){ //즐겨찾기 목록에 지금 현 pc방이름과 일치하는 데이터 있는지 확인
                       btn_text_fav.setTag("on"); //태그 on
                       btn_text_fav.setBackgroundResource(android.R.drawable.btn_star_big_on);
                   }
               }
           }
           catch (Exception e){
               e.printStackTrace();
           }
       }


    }

    @Override
    public void onClick(View c) {
        switch (c.getId()) {
            case R.id.btn_text_loc:
                Log.d("touch", "test ok");
                Detail_layout_change(Integer.parseInt(Layout_pcbang_map.getTag().toString()));
                break;
            case R.id.btn_text_review:
                Layout_pcbang_review.setVisibility(View.VISIBLE);
                Layout_pcbang_map.setVisibility(View.GONE);
                Layout_pcbang_seat.setVisibility(View.GONE);

                //review_listview.setAdapter();
                break;
            case R.id.btn_text_review_write:
                Intent reviewIntent = new Intent(Pcbang_detail_Activity.this, Pcbang_review_Activity.class);
                reviewIntent.putExtra("pcbanginfo2", arr);
                startActivity(reviewIntent);
                Layout_pcbang_review.setVisibility(View.GONE);
                Layout_pcbang_map.setVisibility(View.GONE);
                Layout_pcbang_seat.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_text_back:
                finish();
                break;
            case R.id.btn_text_fav:
                Shared_Fav();
                break;
        }
    }

    //위치보기
    private void Detail_layout_change(int num) {
        Log.d("touch", "test ok");
        if (num == 0) {
            //경도 위도 사이즈범위 정해서 보내주기
            Layout_pcbang_map.setTag(1);
            Layout_pcbang_map.setVisibility(View.VISIBLE);
            Layout_pcbang_seat.setVisibility(View.GONE);
            Layout_pcbang_review.setVisibility(View.GONE);
            btn_text_loc.setText("좌석보기");
        } else {
            Layout_pcbang_map.setTag(0);
            Layout_pcbang_map.setVisibility(View.GONE);
            Layout_pcbang_seat.setVisibility(View.VISIBLE);
            Layout_pcbang_review.setVisibility(View.GONE);
            btn_text_loc.setText("위치보기");
        }
    }

    public ZoomView PCseatView(View v, int i, int j, int[] num) {
        GridLayout.LayoutParams layoutParam = new GridLayout.LayoutParams();
        layoutParam.columnSpec = GridLayout.spec(0, i);
        layoutParam.rowSpec = GridLayout.spec(0, j);
        layoutParam.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParam.height = GridLayout.LayoutParams.WRAP_CONTENT;

        int count = 0;
        for (int c = 0; c < i; c++) {
            for (int d = 0; d < j; d++) {
                Button tmp = new Button(Pcbang_detail_Activity.this);
                if (nums[count] == 0) {
                    tmp.setBackgroundColor(Color.BLUE);
                } else {
                    tmp.setBackgroundColor(Color.RED);
                }
                grid.addView(tmp);
                count++;
            }
        }
        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setBackgroundColor(Color.BLACK);
        zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(3f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        zoomView.setMiniMapCaption("자리배치"); //미니 맵 내용
        zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정
        zoomView.zoomTo(2f, 500f, 300); //배율, 시작점 , 화면크기 측정후 입력받는 데이터 크기 비례해서 조절

        return zoomView;
    }

    public void onBackPressed() {
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (map != null) {
            LatLng latLng = new LatLng(Float.parseFloat(arr.getLatitude()), Float.parseFloat(arr.getLongitude()));
            CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(16f).build();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(position));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
            markerOptions.position(latLng);
            markerOptions.title(arr.getPcBangName());
            markerOptions.snippet("TEL : " + arr.getpcBangTel());

            map.addMarker(markerOptions);


        }
    }

    public void Shared_Fav() {
        if (btn_text_fav.getTag().toString().equals("on")) {//즐겨찾기 되어잇을시
            final AlertDialog.Builder fav_Alert = new AlertDialog.Builder(Pcbang_detail_Activity.this,MODE_APPEND);
            fav_Alert.setTitle("즐겨찾기");
            fav_Alert.setMessage("즐겨찾기를 해제하시겠습니까?");
            fav_Alert.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            fav_Alert.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    for(int c=0; c<fav_list.size(); c++){
                        if(fav_list.get(c).equals(arr.getPcBangName())){
                            fav_list.remove(c);
                        }
                    }
                    JSONArray tmp = new JSONArray();
                    for(int d=0; d<fav_list.size(); d++){
                        tmp.put(fav_list.get(d));
                    }
                    mEditor.putString("fav_list",tmp.toString());
                    mEditor.commit();
                    btn_text_fav.setBackgroundResource(android.R.drawable.btn_star_big_off);
                }
            });

        } else {//즐겨찾기 x
            if(fav_list.size()>2){
                //크기초과
                Toast.makeText(this, "즐겨찾기는 최대 3개까지 가능합니다.", Toast.LENGTH_SHORT).show();
            }
            else {
                fav_list.add(arr.getPcBangName());
                JSONArray tmp = new JSONArray();
                for(int d=0; d<fav_list.size(); d++){
                    tmp.put(fav_list.get(d));
                }
                mEditor.putString("fav_list",tmp.toString());
                mEditor.commit();
                btn_text_fav.setBackgroundResource(android.R.drawable.btn_star_big_on);
                btn_text_fav.setTag("on");
            }

        }
    }


}