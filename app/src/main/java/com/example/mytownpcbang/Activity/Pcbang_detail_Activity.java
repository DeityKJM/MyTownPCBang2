package com.example.mytownpcbang.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.mytownpcbang.PcbangArray.pcinfo;
import com.example.mytownpcbang.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.polidea.view.ZoomView;

/**
 * Created by KimJeongMin on 2017-12-17.
 */

public class Pcbang_detail_Activity extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {
    TextView btn_text_back, text_title, btn_text_fav;
    LinearLayout Layout_pcbang_seat, Layout_pcbang_map, Layout_pcbang_review; //좌석/후기/위치
    TextView Pcbang_image, pcbang_info, btn_text_event, btn_text_pcspec, btn_text_loc, btn_text_review, btn_text_review_write;
    ListView review_listview;
    RatingBar pcbang_ratingbar;
    GoogleMap map;
    LinearLayout container;
    View v;
    GridLayout grid;
    int[] nums = {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0};
    pcinfo arr;

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

        btn_text_loc = (TextView) findViewById(R.id.btn_text_loc);
        Pcbang_image = (TextView) findViewById(R.id.Pcbang_image);
        pcbang_info = (TextView) findViewById(R.id.pcbang_info);
        btn_text_event= (TextView) findViewById(R.id.btn_text_event);
        btn_text_pcspec= (TextView) findViewById(R.id.btn_text_pcspec);
        btn_text_review_write= (TextView) findViewById(R.id.btn_text_review_write);
        btn_text_review_write.setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_detail_layout);
        SetLayout();//레이아웃세팅;
        arr = (pcinfo) getIntent().getSerializableExtra("pcbanginfo");
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        Log.d("외안디", "arr.getLatitude() " + arr.getPcBangName() + " arr.getLongitude() " + arr.getpcBangTel());

        Pcbang_image.setText(arr.getPcBangName());
        pcbang_info.setText(arr.getdetailAddress()+arr.getroadAddress());
        btn_text_pcspec.setText("사양\n"+arr.getpcBangTel());

        v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);
        grid = (GridLayout) v.findViewById(R.id.Grid);
        grid.setColumnCount(5);
        grid.setRowCount(5);
        container.addView(PCseatView(v, 4, 5, nums));


        btn_text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_text_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Pcbang_detail_Activity.this, "미구현", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onClick(View c){
        switch (v.getId()) {
            case R.id.btn_text_loc:
                Detail_layout_change(Integer.parseInt(Layout_pcbang_map.getTag().toString()));
                break;
            case R.id.btn_text_review:
                Layout_pcbang_review.setVisibility(View.VISIBLE);
                Layout_pcbang_map.setVisibility(View.GONE);
                Layout_pcbang_seat.setVisibility(View.GONE);

                //review_listview.setAdapter();
                break;
            case R.id.btn_text_review_write:
                Intent reviewIntent = new Intent(Pcbang_detail_Activity.this,Pcbang_review_Activity.class);
                reviewIntent.putExtra("pcbanginfo",arr);
                startActivity(reviewIntent);
                break;
        }
    }


    //위치보기
    private void Detail_layout_change(int num) {
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

        //   Log.d("외안디","arr.getLatitude() "+arr.getLatitude()+" arr.getLongitude() "+arr.getLongitude());
        if (map != null) {
            LatLng latLng = new LatLng(arr.getLatitude(), arr.getLongitude());
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


}