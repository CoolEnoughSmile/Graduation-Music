package com.ge.music.CESView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ge.music.R;


public class MainTopTabView extends RelativeLayout {

    private Listener listener;

    public MainTopTabView(Context context) {
        this(context, null);

    }

    public MainTopTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainTopTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_main_top_tab, this);

        findViewById(R.id.main_tab_bar_left).setOnClickListener(v -> {
            if (listener != null) {
                listener.onLeftClick(v);
            }
        });

        findViewById(R.id.main_tab_bar_right).setOnClickListener(v -> {
            if (listener != null) {
                listener.onRightClick(v);
            }
        });

        ((RadioGroup)findViewById(R.id.tab_group)).setOnCheckedChangeListener((group,checkedId) ->{
            if (listener == null){
                return;
            }
            switch (checkedId){
                case R.id.main_tab_bar_first_tab:
                    listener.onFirstTabSelected(findViewById(checkedId));
                    break;
                case R.id.main_tab_bar_second_tab:
                    listener.onSecondTabSelected(findViewById(checkedId));
                    break;
                case R.id.main_tab_bar_third_tab:
                    listener.onThirdTabSelected(findViewById(checkedId));
                    break;
                case R.id.main_tab_bar_fourth_tab:
                    listener.onFourthTabSelected(findViewById(checkedId));
                    break;
            }
        });

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onLeftClick(View view);

        void onRightClick(View view);

        void onFirstTabSelected(View view);

        void onSecondTabSelected(View view);

        void onThirdTabSelected(View view);

        void onFourthTabSelected(View view);
    }

}
