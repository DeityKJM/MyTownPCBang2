package com.example.mytownpcbang;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import pl.polidea.view.ZoomView;

/**
 * Created by KimJeongMin on 2017-12-17.
 */

public class tmpxml {
String tmp ="타이틀바,앱바, 즐겨찾기를 위한 pc방고유코드(primary key),pc방리스트출력,클릭후 pc방 상세정보,전화예약 또는 좌석보기할건지(경로 축약)";
    String tmp1="pc방 상세 사진정보(스크롤가능한 뷰?,카드뷰?)";
    String tmp2="후기 평점";
    /**
     *   //container = (LinearLayout) findViewById(R.id.container);
     // container.addView(PCseatView(v,4,5,10));
     View v;
     v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);


     public ZoomView PCseatView(View v,int i,int j,int num){
     GridLayout grid = (GridLayout)v.findViewById(R.id.Grid);
     grid.setColumnCount(i);
     grid.setRowCount(j);
     for(int c=0; c<num; c++){

     }
     for(int i =0; i<imgs.size(); i++){
     int j= data[i];
     if(j==1){
     imgs.get(i).setVisibility(View.VISIBLE);
     }
     else {
     imgs.get(i).setVisibility(View.INVISIBLE);
     }
     }

    ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        zoomView.setMiniMapCaption("자리배치"); //미니 맵 내용
        zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정
        zoomView.zoomTo(2f,500f,300); //배율, 시작점 , 화면크기 측정후 입력받는 데이터 크기 비례해서 조절

        return zoomView;
}





     *
     */

}
