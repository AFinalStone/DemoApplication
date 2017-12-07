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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shi.androidstudy.tool.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liuling on 2017/11/28.
 * <p>
 * 该类为项目公用Dialog弹窗，包含单按钮、双按钮，使用规则和AlertDialog相似（如果只有一个按钮时，请实现setPositiveButton方法）
 */

public class IOSActionSheetDialog extends Dialog {

    public IOSActionSheetDialog(@NonNull Context context) {
        super(context);
    }

    public IOSActionSheetDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected IOSActionSheetDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {

        private Context context;
        private String strTitle;
        private boolean showTitle = false;
        private List<SheetItem> sheetItemList;
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

        public Builder setCancelable(boolean cancel) {
            flagCancelable = cancel;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean cancel) {
            flagCanceledOnTouchOutside = cancel;
            return this;
        }

        /**
         * @param strItem  条目名称
         * @param color    条目字体颜色，设置null则默认蓝色
         * @param listener
         * @return
         */
        public Builder addSheetItem(String strItem, SheetItemColor color,
                                    OnSheetItemClickListener listener) {
            if (sheetItemList == null) {
                sheetItemList = new ArrayList<SheetItem>();
            }
            sheetItemList.add(new SheetItem(strItem, color, listener));
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
            LinearLayout lLayout_content = (LinearLayout) view
                    .findViewById(R.id.lLayout_content);
//            TextView txt_title = (TextView) widget.findViewById(R.id.txt_title);
            if (strTitle != null) {
                TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
                txt_title.setVisibility(View.VISIBLE);
                txt_title.setText(strTitle);
            }
            view.findViewById(R.id.txt_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
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
            setSheetItems(sLayout_content, lLayout_content);

            mDialog.setCancelable(flagCancelable);
            mDialog.setCanceledOnTouchOutside(flagCanceledOnTouchOutside);
            return mDialog;
        }

        /**
         * 设置条目布局
         */
        private void setSheetItems(ScrollView sLayout_content, LinearLayout lLayout_content) {
            if (sheetItemList == null || sheetItemList.size() <= 0) {
                return;
            }

            int size = sheetItemList.size();

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
                SheetItem sheetItem = sheetItemList.get(i - 1);
                String strItem = sheetItem.name;
                SheetItemColor color = sheetItem.color;
                final OnSheetItemClickListener listener = sheetItem.itemClickListener;

                TextView textView = new TextView(context);
                textView.setText(strItem);
                textView.setTextSize(18);
                textView.setGravity(Gravity.CENTER);

                // 背景图片
                if (size == 1) {
                    if (showTitle) {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_single_selector);
                    }
                } else {
                    if (showTitle) {
                        if (i >= 1 && i < size) {
                            textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                        } else {
                            textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                        }
                    } else {
                        if (i == 1) {
                            textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                        } else if (i < size) {
                            textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                        } else {
                            textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                        }
                    }
                }

                // 字体颜色
                if (color == null) {
                    textView.setTextColor(Color.parseColor(SheetItemColor.Blue
                            .getName()));
                } else {
                    textView.setTextColor(Color.parseColor(color.getName()));
                }

                // 高度
                float scale = context.getResources().getDisplayMetrics().density;
                int height = (int) (45 * scale + 0.5f);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, height));

                // 点击事件
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(index);
                        mDialog.dismiss();
                    }
                });

                lLayout_content.addView(textView);
            }
        }

        public class SheetItem {
            String name;
            OnSheetItemClickListener itemClickListener;
            SheetItemColor color;

            public SheetItem(String name, SheetItemColor color,
                             OnSheetItemClickListener itemClickListener) {
                this.name = name;
                this.color = color;
                this.itemClickListener = itemClickListener;
            }
        }

        public interface OnSheetItemClickListener {
            void onClick(int which);
        }

        public enum SheetItemColor {
            Blue("#037BFF"), Red("#FD4A2E");

            private String name;

            private SheetItemColor(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

    }
}
