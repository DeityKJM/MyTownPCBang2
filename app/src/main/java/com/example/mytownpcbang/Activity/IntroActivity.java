package com.example.mytownpcbang.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.mytownpcbang.R;

/**
 * Created by KimJeongMin on 2017-12-17.
 */

public class IntroActivity extends Activity {
    Animation move;
    TextView imag;
    Handler handler = new Handler();

    Runnable r = new Runnable() {
        @Override
        public void run() {

            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        imag = (TextView) findViewById(R.id.textView);
        move = AnimationUtils.loadAnimation(
                IntroActivity.this, R.anim.introanim);
        imag.startAnimation(move);
        move.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(r, 500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(r);
    }
}


/*
* move = AnimationUtils.loadAnimation(MainActivity.this, R.anim.move);
                imag.startAnimation(move);
* */
