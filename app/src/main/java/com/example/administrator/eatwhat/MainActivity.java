package com.example.administrator.eatwhat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.example.administrator.eatwhat.activity.AddEatActivity;
import com.example.administrator.eatwhat.model.EatWhatModel;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.common.PhoenixConstant;
import com.guoxiaoxing.phoenix.core.listener.OnPickerListener;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import io.objectbox.Box;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainActivity mContext = this;

    private TextView tvTitle, tvValue;
    private ImageView imgBg, imgMenu;
    private ImageView btnGo;

    private RelativeLayout rlHead;
    private Box<EatWhatModel> modelBox;
    private long flag = 0;
    private int count = 0;

    private Timer timer = new Timer();
    private TimerTask timerTask;
    private boolean isStart = false;

    @SuppressLint("HandlerLeak")
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getRandom();
                    try {
                        if (flag != 0 && modelBox != null && modelBox.get(flag) != null) {
                            tvValue.setText(modelBox.get(flag).getTitle());
                            if (!TextUtils.isEmpty(modelBox.get(flag).getImg_url())) {
                                Glide.with(mContext).load(modelBox.get(flag).getImg_url()).into(imgBg);
                            } else {
                                imgBg.setImageResource(R.drawable.icon_img_default);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rlHead = findViewById(R.id.relativeLayout);
        BarUtils.setStatusBarColor(mContext, 0xffC9542C);
        BarUtils.addMarginTopEqualStatusBarHeight(rlHead);

        if (Utils.getApp() instanceof MyApplication) {
            modelBox = ((MyApplication) Utils.getApp()).getBoxStore().boxFor(EatWhatModel.class);
        }
        initView();
        initData();
    }

    private void initData() {
        if (modelBox == null || modelBox.count() == 0) {
            modelBox.put(new EatWhatModel("黄焖鸡米饭", "不要,没吃过"));
            modelBox.put(new EatWhatModel("韭菜煎蛋", "韭菜万岁,就他了!"));
            modelBox.put(new EatWhatModel("辣椒煎蛋", "换韭菜吧,韭菜的好吃"));
            modelBox.put(new EatWhatModel("番茄炒蛋", "别放糖"));
            modelBox.put(new EatWhatModel("炒饭", "别放芹菜"));
            modelBox.put(new EatWhatModel("双皮奶", "我要次饭"));
            modelBox.put(new EatWhatModel("披萨", "隔~"));
            modelBox.put(new EatWhatModel("金拱门", "RBQ,RBQ"));
            modelBox.put(new EatWhatModel("开封菜", "花生酱~~"));
            modelBox.put(new EatWhatModel("烧烤", "太干,太油,太贵"));
            modelBox.put(new EatWhatModel("绿茶", "我等到花儿都谢了"));
            modelBox.put(new EatWhatModel("热干面", "太热,太干,太面"));
            modelBox.put(new EatWhatModel("麻辣烫", "不卫生"));
            modelBox.put(new EatWhatModel("大碗小面", "蟑螂面"));
            modelBox.put(new EatWhatModel("包扎", "饿了,次饭次饭"));
            modelBox.put(new EatWhatModel("桂林米粉", "臭!"));
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

        btnGo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnGo.setPadding(20, 20, 20, 20);
                        break;
                    case MotionEvent.ACTION_UP:
                        btnGo.setPadding(0, 0, 0, 0);
                        break;
                }
                return false;
            }
        });
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
            return modelBox.getAll().get((int) flag).id;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_img_head_menu:
                ActivityUtils.startActivity(AddEatActivity.class);
                break;
            case R.id.act_img_value:
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
                                Glide.with(MainActivity.this).load(pickList.get(0).getLocalPath()).into(imgBg);
                                try {
                                    EatWhatModel model = modelBox.get(modelBox.get(flag).id);
                                    model.setImg_url(pickList.get(0).getLocalPath());
                                    modelBox.put(model);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }

                            @Override
                            public void onPickFailed(String errorMessage) {
                            }
                        }).start(MainActivity.this, PhoenixOption.TYPE_PICK_MEDIA);

                break;
            case R.id.act_btn_go:
                if (!isStart) {
                    if (count == 10) {
                        new QMUIDialog.CustomDialogBuilder(mContext)
                                .setLayout(R.layout.layout_dialog_chishibani)
                                .addAction("好吧", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        dialog.dismiss();
                                    }
                                })
                                .addAction(0, "再来最后一次嘛", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        isStart = true;
                                        count++;
                                        btnGo.setImageResource(R.drawable.icon_txt_stop);
                                        if (timer == null)
                                            timer = new Timer();
                                        if (timerTask == null) {
                                            timerTask = new TimerTask() {
                                                @Override
                                                public void run() {
                                                    mHanlder.sendEmptyMessage(1);//通知UI更新
                                                }
                                            };
                                        }
                                        timer.schedule(timerTask,
                                                0,//延迟5秒执行
                                                100);//周期为1秒
                                    }
                                }).show();
                    } else {
                        isStart = true;
                        count++;
                        btnGo.setImageResource(R.drawable.icon_txt_stop);
                        if (timer == null)
                            timer = new Timer();
                        if (timerTask == null) {
                            timerTask = new TimerTask() {
                                @Override
                                public void run() {
                                    mHanlder.sendEmptyMessage(1);//通知UI更新
                                }
                            };
                        }
                        timer.schedule(timerTask,
                                0,//延迟5秒执行
                                100);//周期为1秒
                    }
                } else {
                    isStart = false;
                    btnGo.setImageResource(R.drawable.icon_txt_resumu);
                    if (timer != null) {
                        timer.cancel();
                        timer.purge();
                        timer = null;
                    }

                    if (timerTask != null) {
                        timerTask.cancel();
                        timerTask = null;
                    }
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            timer.cancel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
