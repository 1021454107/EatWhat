package com.example.administrator.eatwhat.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.example.administrator.eatwhat.MyApplication;
import com.example.administrator.eatwhat.R;
import com.example.administrator.eatwhat.model.EatWhatModel;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.common.PhoenixConstant;
import com.guoxiaoxing.phoenix.core.listener.OnPickerListener;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.List;

import io.objectbox.Box;

public class EditEatActivity extends AppCompatActivity implements View.OnClickListener {

    private EditEatActivity mContext = this;

    private RelativeLayout rlHead;
    private TextView tvTitle;
    private ImageView imgBack, imgMenu, imgBg;

    private EditText editText;
    private Box<EatWhatModel> modelBox;

    private String imgUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_eat);

        rlHead = findViewById(R.id.act_rl_add_head);
        BarUtils.setStatusBarColor(mContext, 0xffC9542C);
        BarUtils.addMarginTopEqualStatusBarHeight(rlHead);

        if (Utils.getApp() instanceof MyApplication) {
            modelBox = ((MyApplication) Utils.getApp()).getBoxStore().boxFor(EatWhatModel.class);
        }

        initView();
    }

    private void initView() {
        imgBack = findViewById(R.id.act_img_add_head_back);
        imgMenu = findViewById(R.id.act_img_add_head_menu);
        imgBg = findViewById(R.id.act_img_add_img);

        editText = findViewById(R.id.act_edt_add_title);
        imgBack.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        imgBg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_img_add_head_back:
                finish();
                break;
            case R.id.act_img_add_head_menu:
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    ToastUtils.showLong("蔡明不能为空哦~");
                    return;
                }

                modelBox.put(new EatWhatModel(editText.getText().toString(), "", imgUrl));
                finish();
                break;
            case R.id.act_img_add_img:
                final String[] items = new String[]{"选择图片", "删除图片"};
                new QMUIDialog.MenuDialogBuilder(mContext)
                        .addItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Phoenix.with()
                                                .theme(PhoenixOption.THEME_DEFAULT)// 主题
                                                .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                                                .maxPickNumber(1)// 最大选择数量
                                                .minPickNumber(1)// 最小选择数量
                                                .spanCount(4)// 每行显示个数
                                                .pickMode(PhoenixConstant.SINGLE)// 多选/单选
                                                .enablePreview(true)// 是否开启预览
                                                .enableCamera(true)// 是否开启拍照
                                                .enableAnimation(true)// 选择界面图片点击效果
                                                .enableCompress(true)// 是否开启压缩
                                                .thumbnailHeight(160)// 选择界面图片高度
                                                .thumbnailWidth(160)// 选择界面图片宽度
                                                .enableClickSound(false)//ƒ 是否开启点击声音
//                        .videoSecond(0)//显示多少秒以内的视频
                                                .onPickerListener(new OnPickerListener() {
                                                    @Override
                                                    public void onPickSuccess(List<MediaEntity> pickList) {
                                                        imgUrl = pickList.get(0).getLocalPath();
                                                        Glide.with(mContext).load(pickList.get(0).getLocalPath()).into(imgBg);
                                                    }

                                                    @Override
                                                    public void onPickFailed(String errorMessage) {
                                                    }
                                                }).start(mContext, PhoenixOption.TYPE_PICK_MEDIA);
                                        break;
                                    case 1:
                                        imgBg.setImageResource(R.drawable.icon_img_default);
                                        imgUrl = "";
                                        break;
                                    default:
                                        break;
                                }
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }
}
