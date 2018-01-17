package com.hk.papers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;
import com.hk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main21Activity extends AppCompatActivity {

    @BindView(R.id.ivPlane)
    ImageView ivPlane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main21);
        ButterKnife.bind(this);

        KenBurnsView kbv = (KenBurnsView) findViewById(R.id.image);

        kbv.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {

            }
        });

        ivPlane.animate().y(0f).setDuration(5000).start();


    }

}
