package com.example.administrator.eatwhat;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.eatwhat.model.EatWhatModel;
import com.example.administrator.eatwhat.viewmodel.EatWhatViewModel;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String UID_KEY = "uid";
    private EatWhatViewModel viewModel;

    private TextView tvTitle, tvValue;
    private ImageView imgBg, imgMenu;
    private Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        viewModel = ViewModelProviders.of(this).get(EatWhatViewModel.class);
        viewModel.init(UID_KEY);

        viewModel.getEatWhatModel().observe(this, new Observer<EatWhatModel>() {
            @Override
            public void onChanged(@Nullable EatWhatModel eatWhatModel) {

            }
        });
    }

    private void initView() {
        tvTitle = findViewById(R.id.act_tv_head_title);
        tvValue = findViewById(R.id.act_tv_value);
        imgBg = findViewById(R.id.act_img_value);
        imgMenu = findViewById(R.id.act_img_head_menu);
        btnGo = findViewById(R.id.act_btn_go);
        imgMenu.setOnClickListener(this);
        imgBg.setOnClickListener(this);
        btnGo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_img_head_menu:
                break;
            case R.id.act_img_value:
                break;
            case R.id.act_btn_go:
                break;
            default:
                break;
        }
    }
}
