package com.shi.androidstudy.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shi.androidstudy.tool.R;
import com.shi.androidstudy.widget.SmoothCheckBox.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liuling on 2017/11/28.
 * <p>
 * 该类为项目公用Dialog弹窗，包含单按钮、双按钮，使用规则和AlertDialog相似（如果只有一个按钮时，请实现setPositiveButton方法）
 */

public class IOSActionSheetDialog extends Dialog {

    private IOSActionSheetDialog(@NonNull Context context) {
        super(context);
    }

    private IOSActionSheetDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    private IOSActionSheetDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {

        private Context context;
        private String strTitle;
        private int cancelTextColor;
        private int titleTextColor;
        private boolean showTitle = false;
        private List<IOSActionSheetItem> IOSActionSheetItemList;
        private boolean flagCancelable = true;
        private boolean flagCanceledOnTouchOutside = true;
        Display mDisplay;
        IOSActionSheetDialog mDialog;


        public Builder(Context context) {
            this.context = context;
        }


        public Builder setTitle(String title) {
            this.strTitle = title;
            return this;
        }

        public Builder setTitleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }


        public Builder setCancelTextColor(int cancelTextColor) {
            this.cancelTextColor = cancelTextColor;
            return this;
        }


        public Builder setCancelable(boolean cancel) {
            flagCancelable = cancel;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean cancel) {
            flagCanceledOnTouchOutside = cancel;
            return this;
        }


        public Builder addSheetItem(IOSActionSheetItem IOSActionSheetItem) {
            if (IOSActionSheetItemList == null) {
                IOSActionSheetItemList = new ArrayList<>();
            }
            IOSActionSheetItemList.add(IOSActionSheetItem);
            return this;
        }

        public void show() {
            final IOSActionSheetDialog dialog = create();
            dialog.show();
        }

        /**
         * 创建dialog
         *
         * @return
         */
        private IOSActionSheetDialog create() {
            mDialog = new IOSActionSheetDialog(context, R.style.ActionSheetDialogStyle);
            // 获取Dialog布局
            View view = LayoutInflater.from(context).inflate(R.layout.view_ios_actionsheet, null);
            // 获取自定义Dialog布局中的控件
            ScrollView sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);
            LinearLayout lLayout_content = (LinearLayout) view.findViewById(R.id.lLayout_content);
            //初始化title
            if (strTitle != null) {
                TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
                txt_title.setVisibility(View.VISIBLE);
                txt_title.setText(strTitle);
                if (titleTextColor == 0) {
                    cancelTextColor = context.getResources().getColor(R.color.iosActionSheet_blue);
                }
                txt_title.setTextColor(titleTextColor);
            }
            //初始化取消
            TextView txtCancel = (TextView) view.findViewById(R.id.txt_cancel);
            txtCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
            if (cancelTextColor == 0) {
                cancelTextColor = context.getResources().getColor(R.color.iosActionSheet_blue);
            }
            txtCancel.setTextColor(cancelTextColor);
            // 定义Dialog布局和参数
            mDialog.setContentView(view);
            Window dialogWindow = mDialog.getWindow();
            dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.x = 0;
            lp.y = 0;
            dialogWindow.setAttributes(lp);
            //获取设备的宽度
            WindowManager windowManager = dialogWindow.getWindowManager();
            mDisplay = windowManager.getDefaultDisplay();
            // 设置Dialog最小宽度为屏幕宽度
            view.setMinimumWidth(mDisplay.getWidth());
            //初始化SheetItems
            setSheetItems(sLayout_content, lLayout_content);

            mDialog.setCancelable(flagCancelable);
            mDialog.setCanceledOnTouchOutside(flagCanceledOnTouchOutside);
            return mDialog;
        }

        /**
         * 设置条目布局
         */
        private void setSheetItems(ScrollView sLayout_content, LinearLayout lLayout_content) {
            if (IOSActionSheetItemList == null || IOSActionSheetItemList.size() <= 0) {
                return;
            }

            final int size = IOSActionSheetItemList.size();

            //高度控制，非最佳解决办法
            // 添加条目过多的时候控制高度
            if (size >= 7) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) sLayout_content
                        .getLayoutParams();
                params.height = mDisplay.getHeight() / 2;
                sLayout_content.setLayoutParams(params);
            }

            // 循环添加条目
            for (int i = 1; i <= size; i++) {
                final int index = i;
                IOSActionSheetItem IOSActionSheetItem = IOSActionSheetItemList.get(i - 1);
                String strItem = IOSActionSheetItem.name;
                IOSActionSheetItem.SheetItemColor color = IOSActionSheetItem.itemColor;
                IOSActionSheetItem.SheetItemColor checkColor = IOSActionSheetItem.checkColor;
                int resIconID = IOSActionSheetItem.itemIcon;
                View.OnClickListener listener = IOSActionSheetItem.itemClickListener;

                View itemView = LayoutInflater.from(context).inflate(R.layout.view_ios_actionsheet_item, null, false);
                ImageView itemIcon = (ImageView) itemView.findViewById(R.id.itemIcon);
                TextView itemText = (TextView) itemView.findViewById(R.id.itemText);
                IOSActionSheetItem.smoothCheckBox = (SmoothCheckBox) itemView.findViewById(R.id.itemCheck);

                if (color == null) {
                    color = com.shi.androidstudy.widget.dialog.IOSActionSheetItem.SheetItemColor.Blue;
                }
                if (checkColor == null) {
                    checkColor = com.shi.androidstudy.widget.dialog.IOSActionSheetItem.SheetItemColor.Blue;
                }
                //初始化条目TextView
                itemText.setText(strItem);
                itemText.setTextColor(Color.parseColor(color.getName()));
                //初始化icon
                if (resIconID != 0) {
                    itemIcon.setImageResource(resIconID);
                }
                //checkBox点击监听者
                if (IOSActionSheetItem.OnCheckedChangeListener != null) {
                    IOSActionSheetItem.smoothCheckBox.setCheckedColor(Color.parseColor(checkColor.getName()));
                    IOSActionSheetItem.smoothCheckBox.setVisibility(View.VISIBLE);
                    IOSActionSheetItem.smoothCheckBox.setOnCheckedChangeListener(IOSActionSheetItem.OnCheckedChangeListener);
                }
                // 点击事件
                if (listener != null)
                    itemView.setOnClickListener(listener);
                // 背景图片
                if (size == 1) {
                    if (showTitle) {
                        itemView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    } else {
                        itemView.setBackgroundResource(R.drawable.actionsheet_single_selector);
                    }
                } else {
                    if (showTitle) {
                        if (i >= 1 && i < size) {
                            itemView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                        } else {
                            itemView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                        }
                    } else {
                        if (i == 1) {
                            itemView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                        } else if (i < size) {
                            itemView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                        } else {
                            itemView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                        }
                    }
                }

                lLayout_content.addView(itemView);
            }
        }

    }
}
