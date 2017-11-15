package com.example.administrator.eatwhat.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.example.administrator.eatwhat.MyApplication;
import com.example.administrator.eatwhat.R;
import com.example.administrator.eatwhat.model.EatWhatModel;
import com.example.administrator.eatwhat.view.Chip;
import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.common.PhoenixConstant;
import com.guoxiaoxing.phoenix.core.listener.OnPickerListener;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.robertlevonyan.views.chip.OnChipClickListener;
import com.robertlevonyan.views.chip.OnCloseClickListener;
import com.robertlevonyan.views.chip.OnIconClickListener;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class AddEatActivity extends AppCompatActivity implements View.OnClickListener {
    private AddEatActivity mContext = this;

    private TextView tvTitle;
    private ImageView imgAdd, imgMenu;

    private RelativeLayout rlHead;
    private Box<EatWhatModel> modelBox;
    private AutoFlowLayout llChip;

    private MyFlowAdapter flowAdapter;
    private LayoutInflater mLayoutInflater;
    private List<EatWhatModel> modelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_eat);
        rlHead = findViewById(R.id.act_rl_edit_head);
        BarUtils.setStatusBarColor(mContext, 0xffC9542C);
        BarUtils.addMarginTopEqualStatusBarHeight(rlHead);
        modelList = new ArrayList<>();
        if (Utils.getApp() instanceof MyApplication) {
            modelBox = ((MyApplication) Utils.getApp()).getBoxStore().boxFor(EatWhatModel.class);
            modelList.addAll(modelBox.getAll());
        }
        mLayoutInflater = getLayoutInflater();

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (modelBox == null) {
            if (Utils.getApp() instanceof MyApplication) {
                modelBox = ((MyApplication) Utils.getApp()).getBoxStore().boxFor(EatWhatModel.class);
                showList();
            }
        }
    }

    private void initView() {
        llChip = findViewById(R.id.act_chip_edit_list);
        tvTitle = findViewById(R.id.act_tv_edit_head_title);
        imgMenu = findViewById(R.id.act_img_edit_head_menu);
        imgAdd = findViewById(R.id.act_img_edit_add);

        imgMenu.setOnClickListener(this);
        imgAdd.setOnClickListener(this);

        flowAdapter = new MyFlowAdapter(modelList);

        llChip.setAdapter(flowAdapter);
    }

    private void showList() {
        if (flowAdapter != null) {
//            flowAdapter = null;
//            flowAdapter = new MyFlowAdapter(m);
//            llChip.setAdapter(flowAdapter);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_img_edit_head_menu:
                finish();
                break;
            case R.id.act_img_edit_add:
                ActivityUtils.startActivity(EditEatActivity.class);
                break;
            default:
                break;

        }
    }

    public class MyFlowAdapter extends FlowAdapter<EatWhatModel> {
        private List<EatWhatModel> mList;

        public MyFlowAdapter(List<EatWhatModel> datas) {
            super(datas);
            mList = new ArrayList<>();
            mList.addAll(datas);
        }

        public MyFlowAdapter(EatWhatModel[] datas) {
            super(datas);
        }

        @Override
        public View getView(final int i) {
            View view = mLayoutInflater.inflate(R.layout.item_chip, null);
            final Chip chip = view.findViewById(R.id.item_chip_value);

            chip.setChipText(mList.get(i).title);
            if (TextUtils.isEmpty(mList.get(i).img_url)) {
                chip.setChipIcon(Utils.getApp().getResources().getDrawable(R.drawable.icon_img_default));
            } else {
                chip.setChipIcon(mList.get(i).img_url);
            }
            chip.setOnIconClickListener(new OnIconClickListener() {
                @Override
                public void onIconClick(View v) {
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
                                                            EatWhatModel model = modelBox.get(modelBox.get(i).id);
                                                            model.setImg_url(pickList.get(0).getLocalPath());
                                                            modelBox.put(model);
                                                            chip.setChipIcon(pickList.get(0).getLocalPath());
                                                            showList();
                                                        }

                                                        @Override
                                                        public void onPickFailed(String errorMessage) {
                                                        }
                                                    }).start(mContext, PhoenixOption.TYPE_PICK_MEDIA);
                                            break;
                                        case 1:
                                            chip.setChipIcon(Utils.getApp().getResources().getDrawable(R.drawable.icon_img_default));
                                            EatWhatModel model = modelBox.get(modelBox.get(i).id);
                                            model.setImg_url("");
                                            modelBox.put(model);
                                            showList();
                                            break;
                                        default:
                                            break;
                                    }
                                    dialog.dismiss();
                                }
                            })
                            .show();

                }
            });

            chip.setOnChipClickListener(new OnChipClickListener() {
                @Override
                public void onChipClick(View v) {
                    final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(mContext);
                    builder
//                            .setTitle("编辑标题")
                            .setPlaceholder("编辑标题")
                            .addAction(R.drawable.icon_edit_cancel, "", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                }
                            })
                            .addAction(R.drawable.icon_edit_confirm, "", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    CharSequence title = builder.getEditText().getText();
                                    if (TextUtils.isEmpty(title)) {
                                        ToastUtils.showLong("不能为空哦~");
                                    } else {
                                        EatWhatModel model = mList.get(i);
                                        model.setTitle(title.toString());
                                        modelBox.put(model);
                                        dialog.dismiss();
                                        showList();
                                    }
                                }
                            }).show();

                }
            });

            chip.setOnCloseClickListener(new OnCloseClickListener() {
                @Override
                public void onCloseClick(View v) {
                    new QMUIDialog.MessageDialogBuilder(mContext)
//                            .setTitle("删除")
                            .setMessage("确定要删除吗？")
                            .addAction(R.drawable.icon_edit_cancel, "", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                }
                            })
                            .addAction(R.drawable.icon_edit_confirm, "", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    modelBox.remove(mList.get(i).id);
                                    dialog.dismiss();
                                    showList();
                                }
                            })
                            .show();

                }
            });

//                TextView tvTitle = view.findViewById(R.id.item_tv_chip_title);
//                ImageView imgIcon = view.findViewById(R.id.item_img_chip_icon);
//                ImageView imgMenu = view.findViewById(R.id.item_img_chip_menu);
//
//                tvTitle.setText(modelBox.getAll().get(i).title);
//                if (TextUtils.isEmpty(modelBox.getAll().get(i).img_url)) {
//                    imgIcon.setImageResource(R.drawable.icon_img_default);
//                } else {
//                    Glide.with(mContext).load(modelBox.getAll().get(i).img_url).into(imgIcon);
//                }
//                imgIcon.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ToastUtils.showLong("点击了icon" + modelBox.getAll().get(i).title);
//                    }
//                });
//                imgMenu.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ToastUtils.showLong("点击了删除" + modelBox.getAll().get(i).title);
//                    }
//                });
//                tvTitle.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ToastUtils.showLong("点击了文字" + modelBox.getAll().get(i).title);
//                    }
//                });

            return view;
        }

        public EatWhatModel getItem(int position) {
            return this.mList.get(position);
        }

        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }


        public void setmList(List<EatWhatModel> list) {
            this.mList.clear();
            this.mList.addAll(list);

        }
    }
}
