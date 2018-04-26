package com.yl.baiduren.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yl.baiduren.R;


/**
 * @author 王锋 on 2017/11/10.
 */

public class DialogUtils {

    private static AlertDialog dialog = null;
    private static PopupWindow popupWindow;


    /*
    *
    * 弹出对话框通知用户更新程序
    *
    * 弹出对话框的步骤：
    *  1.创建alertDialog的builder.
    *  2.要给builder设置属性, 对话框的内容,样式,按钮
    *  3.通过builder 创建一个对话框
    *  4.对话框show()出来
    */
    public static void showDialogVersion(Context context, boolean canceled, String message, final OnButtonEventListener1 buttonEventListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(canceled);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.updata_vresion, null);
        TextView textView= (TextView) layout.findViewById(R.id.tv_content);
        textView.setText(message);
        //  点击现在就去
        TextView btCommit =  layout.findViewById(R.id.btnConfirm);
        btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (buttonEventListener != null) {
                    buttonEventListener.onConfirm();
                }
            }
        });

        TextView btCancel =  layout.findViewById(R.id.btnCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (buttonEventListener != null) {
                    buttonEventListener.onCancel(dialog);
                }
            }
        });

        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth - 60), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(layout);
    }

    public static void showDialogCertification(Context context, boolean canceled, String content, final String enterpriseText, final String individualText, final OnCertificationListener certificationListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(canceled);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loan_dialog_certification, null);
        TextView contentTv = layout.findViewById(R.id.content);
        contentTv.setText(content);
        // 企业
        TextView enterprise = layout.findViewById(R.id.enterprise_certification);
        enterprise.setText(enterpriseText);
        enterprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (certificationListener != null) {
                    certificationListener.onEnterprise();
                }
            }
        });

        TextView individualTv = layout.findViewById(R.id.individual);
        individualTv.setText(individualText);
        individualTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (certificationListener != null) {
                    certificationListener.onIndividualTv();
                }
            }
        });
        TextView cancelBt = layout.findViewById(R.id.cancel);
        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth - 60), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(layout);
    }


    public static void showDialogPrompt(Context context, boolean canceled, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(canceled);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loan_dialogpromptlayout, null);
        TextView textView = layout.findViewById(R.id.content);
        textView.setText(message);
        //  点击现在就去
        Button btCommit = (Button) layout.findViewById(R.id.btnConfirm);
        btCommit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth - 60), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(layout);
    }

    /**
     * 显示默认的请求进度条
     *
     * @param context
     */
    public static Dialog ShowDefaultProDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loan_pro_dialog_layout, null);
        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth / 3), screenWidth / 3);
        dialog.getWindow().setContentView(layout);
        return dialog;
    }

    /**
     * 显示网络请求dialog进度条
     *
     * @param context
     */
    public static synchronized void showDrawDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.nerwork_request_dialog_layout, null);
        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth / 3), screenWidth / 3);
        dialog.getWindow().setContentView(layout);
    }

    /**
     * 关闭网络请求dialog进度条
     */
    public static synchronized void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }


    public static void showPopup(final Activity activity) {
        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.nerwork_request_dialog_layout, null);
        Util.backgroundAlpha(activity, 0.6f);
        int screenWidth = ScreenUtil.getScreenWidth(activity);
        popupWindow = new PopupWindow(popupView, screenWidth / 3, screenWidth / 3);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //添加弹出、弹入的动画
//        popupWindow.setAnimationStyle(R.style.window_style);
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Util.backgroundAlpha(activity, 1f);
            }
        });
    }

    public static void closePopup() {

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }


    public static void showDialogZsr(Context context, boolean canceled, String message, final OnButtonEventListener buttonEventListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(canceled);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loan_dialogs_layout, null);
        TextView textView = layout.findViewById(R.id.content);
        textView.setText(message);
        //  点击现在就去
        Button btCommit = (Button) layout.findViewById(R.id.btnConfirm);
        btCommit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (buttonEventListener != null) {
                    buttonEventListener.onConfirm();
                }
            }
        });

        Button btCancel = (Button) layout.findViewById(R.id.btnCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (buttonEventListener != null) {
                    buttonEventListener.onCancel();
                }
            }
        });

        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth - 60), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(layout);
    }


    public static void showDialogZsr2(Context context, String commit, boolean canceled, String message, final OnButtonEventListener buttonEventListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(canceled);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loan_dialogs_layout, null);
        TextView textView = layout.findViewById(R.id.content);
        textView.setText(message);
        //  点击现在就去
        Button btCommit = (Button) layout.findViewById(R.id.btnConfirm);
        btCommit.setText(commit);
        btCommit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (buttonEventListener != null) {
                    buttonEventListener.onConfirm();
                }
            }
        });

        Button btCancel = (Button) layout.findViewById(R.id.btnCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (buttonEventListener != null) {
                    buttonEventListener.onCancel();
                }
            }
        });

        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth - 60), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(layout);
    }


    public static void showDialog(Context context, boolean canceled, String message, final OnButtonEventListener2 buttonEventListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(canceled);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loan_dialogs_layout, null);
        TextView textView = layout.findViewById(R.id.content);
        textView.setText(message);
        //  点击现在就去
        Button btCommit = (Button) layout.findViewById(R.id.btnConfirm);
        btCommit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (buttonEventListener != null) {
                    buttonEventListener.onConfirm();
                }
            }
        });

        Button btCancel = (Button) layout.findViewById(R.id.btnCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth - 60), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(layout);
    }


    public static void showPhone(final Activity activity) {
        DialogUtils.showDialogZsr2(activity, "呼叫", false, "010-65361782", new DialogUtils.OnButtonEventListener() {
            @Override
            public void onConfirm() {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "010-65361782"));
                activity.startActivity(intent);
            }

            @Override
            public void onCancel() {
            }
        });
    }


    public interface OnButtonEventListener1 {

        void onConfirm();

        void onCancel(AlertDialog dialog);
    }

    public interface OnButtonEventListener {

        void onConfirm();

        void onCancel();
    }

    public interface OnButtonEventListener2 {

        void onConfirm();
    }

    public interface OnCertificationListener {
        void onEnterprise();

        void onIndividualTv();
    }
}
