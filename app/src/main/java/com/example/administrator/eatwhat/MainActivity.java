package com.example.administrator.eatwhat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.example.administrator.eatwhat.model.EatWhatModel;

import java.util.Random;
import java.util.Timer;

import io.objectbox.Box;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvTitle, tvValue;
    private ImageView imgBg, imgMenu;
    private Button btnGo;

    private Box<EatWhatModel> modelBox;
    private long flag = 0;

    int counter = 0;
    static final int UPDATE_INTERVAL = 1000;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Utils.getApp() instanceof MyApplication) {
            modelBox = ((MyApplication) Utils.getApp()).getBoxStore().boxFor(EatWhatModel.class);
        }
        initView();
        initData();
    }

    private void initData() {
        if (modelBox == null || modelBox.count() == 0) {
            for (int i = 0; i < 10; i++) {
                modelBox.put(new EatWhatModel("name" + i, "value" + i, ""));
            }
        }
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

    private void go(){

    }

    /**
     * 获取随机
     *
     * @return
     */
    private long getRandom() {
        if (modelBox.count() == 0) {
            ToastUtils.showLong("没有数据哦，请添加~");
            return flag;
        } else {
            Random random = new Random();
            flag = random.nextInt((int) modelBox.count());
            if (flag == 0) {
                return 0;
            } else {
                return flag - 1;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_img_head_menu:
                break;
            case R.id.act_img_value:
                break;
            case R.id.act_btn_go:
                if ("GO".equals(btnGo.getText().toString())) {
                    btnGo.setText("STOP");
                } else {
                    btnGo.setText("GO");
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
