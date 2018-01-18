package com.example.mytownpcbang.PcbangArray;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mytownpcbang.R;

import java.util.ArrayList;

/**
 * Created by KimJeongMin on 2017-12-15.
 */

public class pcAdapter extends BaseAdapter {
    private ArrayList<PcBang_info> arr = new ArrayList<>();
    private Context mContext;
    private int layout;
    private LayoutInflater inflater;


    public pcAdapter(Context mContext, int layout, ArrayList<PcBang_info> arr) {
        this.mContext = mContext;
        this.layout = layout;
        this.arr = arr;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public PcBang_info getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(layout, null);
        }
        final TextView pcbangName = (TextView) view.findViewById(R.id.Pcbang_image);
        final TextView pcbanginfo = (TextView) view.findViewById(R.id.pcbang_info);
        final RatingBar star = (RatingBar) view.findViewById(R.id.pcbang_rating);



        final String starnum = getItem(position).getStarnum();
        star.setNumStars(5);
        star.setStepSize(0.5f);
        star.setRating(Float.parseFloat(starnum));



        pcbangName.setText(getItem(position).getPcBangName());
        pcbanginfo.setText("\n주소 : " + getItem(position).getroadAddress() + "\n" + "번호 " + getItem(position).getpcBangTel() + "" + "" + "");
        return view;
    }
}
