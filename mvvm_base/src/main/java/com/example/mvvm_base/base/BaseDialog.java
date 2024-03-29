package com.example.mvvm_base.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mvvm_base.R;
import com.example.mvvm_base.util.ToolUtil;

/**
 * desc: BaseDialog
 * date:2021-4-25 11:04
 * author:bWang
 */
public class BaseDialog extends Dialog {
    private Context ctx;
    private View rootView;
    private SparseArray<View> views;

    /**
     * 0 :  默认的弹出框优先级
     * 1 ： 系统公告
     * 2 ： 版本更新的引导
     * 3 ： 账号冻结
     */
    public int level = 0;//弹出框的默认优先级

    //禁止外部点击取消
//    @Override
//    public void setCanceledOnTouchOutside(boolean cancel) {
//        super.setCanceledOnTouchOutside(false);
//    }

    //禁用外部返回键
    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        return true;
    }

    public BaseDialog(Context context, int layoutId) {
        super(context, R.style.Dialog);
        if (null == views) {
            views = new SparseArray<>();
        }
        initView(context, layoutId);
    }

    public void initView(final Context ctx, int layoutId) {
        this.ctx = ctx;
        rootView = LayoutInflater.from(ctx).inflate(layoutId, null);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(true);
        this.setContentView(rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (ctx instanceof Activity) {
//                    ToolUtil.hide((Activity) ctx);
//                }
            }
        });
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //弹框消失的回调
                myDialogDismiss();
            }
        });
    }

    /**
     * 设置内容的点击事件
     *
     * @param clickListener
     */
    public BaseDialog setContentViewListener(View.OnClickListener clickListener) {
        rootView.setOnClickListener(clickListener);
        return this;
    }

    /**
     * 设置Dialog中的文本
     *
     * @autor timi
     * create at 2017/5/5 14:34
     */
    public BaseDialog setTextViewContent(int resId, Object content) {
        if (!(rootView.findViewById(resId) instanceof TextView)) {
            return this;
        }
        TextView tv = (TextView) rootView.findViewById(resId);
        //存储控件
        views.put(resId, tv);
        //设置内容

        tv.setText(ToolUtil.null2String(content));

        return this;
    }

    /**
     * 设置按钮的文本及点击事件
     *
     * @autor timi
     * create at 2017/5/5 14:37
     */
    public BaseDialog setButtonListener(int resId, String content, final DialogClickListener listener) {
        if (!(rootView.findViewById(resId) instanceof Button)) {
            return this;
        }
        Button bt = (Button) rootView.findViewById(resId);
        if (null != bt) {
            //设置内容
            if (null != content && !TextUtils.isEmpty(content)) {
                bt.setText(content);
            }
            if (null != listener) {
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.dialogClick(BaseDialog.this);
                    }
                });
            }
            //存储控件
            views.put(resId, bt);
        }
        return this;
    }

    /**
     * 设置TextView文本及点击事件
     *
     * @autor timi
     * create at 2017/5/5 14:37
     */
    public BaseDialog setTextViewListener(int resId, final DialogClickListener listener) {
        if (!(rootView.findViewById(resId) instanceof TextView)) {
            return this;
        }
        TextView bt = (TextView) rootView.findViewById(resId);
        if (null != bt) {
            //存储控件
            views.put(resId, bt);
            if (null != listener) {
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.dialogClick(BaseDialog.this);
                    }
                });
            }
        }
        return this;
    }

    /**
     * 设置图片及点击事件
     *
     * @autor timi
     * create at 2017/5/5 14:37
     */
    public BaseDialog setImageViewListener(int resId, final DialogClickListener listener) {
        if (!(rootView.findViewById(resId) instanceof ImageView)) {
            return this;
        }
        ImageView iv = (ImageView) rootView.findViewById(resId);
        if (null != iv) {
            //存储控件
            views.put(resId, iv);
            if (null != listener) {
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.dialogClick(BaseDialog.this);
                    }
                });
            }
        }
        return this;
    }

    /**
     * 设置图片及点击事件
     *
     * @autor timi
     * create at 2017/5/5 14:37
     */
    public BaseDialog setImageViewListener(int resId, Bitmap bp, final DialogClickListener listener) {
        if (!(rootView.findViewById(resId) instanceof ImageView)) {
            return this;
        }
        ImageView iv = (ImageView) rootView.findViewById(resId);
        if (null != iv) {
            //存储控件
            views.put(resId, iv);
            if (null != listener) {
                iv.setImageBitmap(bp);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.dialogClick(BaseDialog.this);
                    }
                });
            }
        }
        return this;
    }

    /**
     * 设置View的点击事件
     *
     * @autor timi
     * create at 2017/5/5 14:37
     */
    public BaseDialog setViewListener(int resId, final DialogClickListener listener) {
        View viewById = rootView.findViewById(resId);
        if (null != viewById) {
            //存储控件
            views.put(resId, viewById);
            if (null != listener) {
                viewById.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.dialogClick(BaseDialog.this);
                    }
                });
            }
        }
        return this;
    }

    /**
     * 设置布局的点击事件
     *
     * @autor timi
     * create at 2017/5/5 14:37
     */
    public BaseDialog setLinearlayoutListener(int resId, final DialogClickListener listener) {
        LinearLayout viewById = (LinearLayout) rootView.findViewById(resId);
        if (null != viewById) {
            if (null != listener) {
                viewById.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.dialogClick(BaseDialog.this);
                    }
                });
            }
        }
        return this;
    }

    /**
     * 设置相对布局的点击事件
     *
     * @autor timi
     * create at 2017/5/5 14:37
     */
    public BaseDialog setRelativeLayoutListener(int resId, final DialogClickListener listener) {
        RelativeLayout viewById = (RelativeLayout) rootView.findViewById(resId);
        if (null != viewById) {
            if (null != listener) {
                viewById.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.dialogClick(BaseDialog.this);
                    }
                });
            }
        }
        return this;
    }

    /**
     * 设置Dialog的Edittext
     *
     * @param resId
     * @return
     */
    public BaseDialog setEditTextContent(int resId, String content) {
        View viewById = rootView.findViewById(resId);
        if (null != viewById && viewById instanceof EditText) {
            views.put(resId, viewById);
            ((EditText) viewById).setText(content);
        }
        return this;
    }

    /**
     * 设置Dialog的Edittext
     *
     * @param resId
     * @return
     */
    public BaseDialog setEditTextHint(int resId, String content) {
        View viewById = rootView.findViewById(resId);
        if (null != viewById && viewById instanceof EditText) {
            views.put(resId, viewById);
            ((EditText) viewById).setHint(content);
        }
        return this;
    }

    /**
     * 返回Dialog的Edittext
     *
     * @param resId
     * @return
     */
    public EditText getEditText(int resId) {
        View viewById = views.get(resId);
        if (null != viewById && viewById instanceof TextView) {
            return (EditText) viewById;
        } else {
            viewById = rootView.findViewById(resId);
            if (null != viewById && viewById instanceof TextView) {
                return (EditText) viewById;
            }
        }
        return null;
    }

    public RadioButton getRadioButton(int resId) {
        View viewById = views.get(resId);
        if (null != viewById && viewById instanceof RadioButton) {
            return (RadioButton) viewById;
        } else {
            viewById = rootView.findViewById(resId);
            if (null != viewById && viewById instanceof RadioButton) {
                return (RadioButton) viewById;
            }
        }
        return null;
    }

    /**
     * 返回Dialog的TextView
     *
     * @param resId
     * @return
     */
    public TextView getTextView(int resId) {
        View viewById = views.get(resId);
        if (null != viewById && viewById instanceof TextView) {
            return (TextView) viewById;
        } else {
            viewById = rootView.findViewById(resId);
            if (null != viewById && viewById instanceof TextView) {
                return (TextView) viewById;
            }
        }
        return null;
    }

    /**
     * 返回Dialog的View
     *
     * @param resId
     * @return
     */
    public View getView(int resId) {
        View viewById = views.get(resId);
        if (null != viewById && viewById instanceof View) {
            return viewById;
        } else {
            viewById = rootView.findViewById(resId);
            if (null != viewById && viewById instanceof View) {
                return viewById;
            }
        }
        return null;
    }

    public BaseDialog addViewToWindow(View view, LinearLayout.LayoutParams params) {
        getWindow().addContentView(view, params);
        return this;
    }

    /**
     * 设置是否能够点击外部按钮取消弹出框
     *
     * @autor timi
     * create at 2017/5/5 14:41
     */
    public BaseDialog setCancelByOutside(boolean isCancel) {
        this.setCanceledOnTouchOutside(isCancel);
        return this;
    }

    /**
     * 设置Dialog的优先级
     *
     * @autor timi
     * create at 2017/5/5 15:17
     */
    public BaseDialog setLevel(int level) {
        this.level = level;
        return this;
    }

    /**
     * 设置Dialog的动画 不设置默认是渐变
     *
     * @param stryleId
     * @return
     */
    public BaseDialog setAnimation(int stryleId) {
        getWindow().setWindowAnimations(stryleId);
        return this;
    }

    /**
     * 设置弹出框 不能够被返回键取消掉
     *
     * @autor timi
     * create at 2017/5/5 14:44
     */
    public BaseDialog setCantCancelByBackPress() {
        //弹出加载框的时候不能被取消掉
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        return this;
    }

    private DialogDismissListener disListener = null;

    /**
     * 设置dialog消失的监听器
     *
     * @autor timi
     * create at 2017/5/8 9:24
     */
    public BaseDialog setMyDialogDismissListener(DialogDismissListener disListener) {
        this.disListener = disListener;
        return this;
    }

    /**
     * 供外部调用 即MyDialogUtils调用 MyDialog消失的回调
     *
     * @autor timi
     * create at 2017/5/8 9:27
     */
    public BaseDialog myDialogDismiss() {
        if (null != disListener) {
            this.disListener.dialogDismiss(this);
        }
        return this;
    }

    /**
     * Dialog点击的接口
     *
     * @autor timi
     * create at 2017/5/5 15:15
     */
    public interface DialogClickListener {
        void dialogClick(BaseDialog dialog);
    }


    /**
     * MyDialog 消失的接口
     *
     * @autor timi
     * create at 2017/5/8 9:25
     */
    public interface DialogDismissListener {
        void dialogDismiss(BaseDialog dialog);
    }

}
