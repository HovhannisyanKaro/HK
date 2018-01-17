package com.hk.tickle_single_choise_personages_heads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hk.R;

public class Main7Activity extends AppCompatActivity {

    private LinearLayout llHeads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        llHeads = (LinearLayout) findViewById(R.id.ll_heads);
        llHeads.setWeightSum(3f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < 3; i++) {
            RelativeLayout ll = new RelativeLayout(this);
            CheckBox cb = new CheckBox(this);
            cb.setText("i " + i);
            cb.setButtonDrawable(0);
            cb.setGravity(Gravity.RIGHT);
            cb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cb.setBackgroundResource(R.drawable.selector_head);
            params.weight = 1f;
            params.gravity = Gravity.CENTER;
            ll.setLayoutParams(params);
            ll.addView(cb);
            llHeads.addView(ll);
        }
    }
}
