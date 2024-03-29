package com.example.mvvm_base.crash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvm_base.R;

import static com.example.mvvm_base.crash.CrashExceptioner.closeApplication;
import static com.example.mvvm_base.crash.CrashExceptioner.getAllErrorDetailsFromIntent;
import static com.example.mvvm_base.crash.CrashExceptioner.getRestartActivityClassFromIntent;
import static com.example.mvvm_base.crash.CrashExceptioner.isShowErrorDetails;
import static com.example.mvvm_base.crash.CrashExceptioner.isShowErrorDetailsFromIntent;
import static com.example.mvvm_base.crash.CrashExceptioner.restartApplicationWithIntent;

/**
 * desc: DefaultErrorActivity
 * date:
 * author:
 */
public final class DefaultErrorActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ac_default_error);
        Button restartButton = (Button) findViewById(R.id.btn_restart);

        final Class<? extends Activity> restartActivityClass = getRestartActivityClassFromIntent(getIntent());

        if (restartActivityClass != null) {
            restartButton.setText(getString(R.string.ac_error_restart_app));
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DefaultErrorActivity.this, restartActivityClass);
                    restartApplicationWithIntent(DefaultErrorActivity.this, intent);
                }
            });
        } else {
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeApplication(DefaultErrorActivity.this);
                }
            });
        }

        Button moreInfoButton = (Button) findViewById(R.id.btn_more_info);
        if (!isShowErrorDetails()) {//用户设置不显示错误信息
            moreInfoButton.setVisibility(View.GONE);
        }
        if (isShowErrorDetailsFromIntent(getIntent())) {

            moreInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //We retrieve all the error data and show it

                    AlertDialog dialog = new AlertDialog.Builder(DefaultErrorActivity.this)
                            .setTitle(R.string.ac_error_error_details_title)
                            .setMessage(getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent()))
                            .setPositiveButton(R.string.ac_error_error_details_close, null)
                            .setNeutralButton(R.string.ac_error_error_details_copy,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            copyErrorToClipboard();
                                            Toast.makeText(DefaultErrorActivity.this, R.string.ac_error_error_details_copied, Toast.LENGTH_SHORT).show();
                                        }
                                    })
                            .show();
                    TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                    textView.setTextSize(14);
                }
            });
        } else {
            moreInfoButton.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NewApi")
    private void copyErrorToClipboard() {
        String errorInformation =
                getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent());

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(errorInformation);
        } else {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Error information", errorInformation);
            clipboard.setPrimaryClip(clip);
        }
    }
}
