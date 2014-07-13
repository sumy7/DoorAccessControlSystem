package com.sumy.dooraccesscontrolsystem.activity;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.sumy.dooraccesscontrolsystem.R;
import com.sumy.dooraccesscontrolsystem.business.Environment;
import com.sumy.dooraccesscontrolsystem.entity.Admin;
import com.sumy.dooraccesscontrolsystem.entity.Manager;
import com.sumy.dooraccesscontrolsystem.entity.User;
import com.sumy.dooraccesscontrolsystem.validate.Validate;
import com.sumy.dooraccesscontrolsystem.view.LockScreenView;
import com.sumy.dooraccesscontrolsystem.view.LockScreenView.OnDrawLockPinFinishedListener;

/**
 * 经理手势验证界面
 * 
 * @author sumy
 * 
 */
public class ManagerCheckInActivity extends BaseActivity {

    private LockScreenView lockScreenView;
    private EditText et_name;
    private Button btn_ok;

    // 暂存用户名
    private String username;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_input_manager;
    }

    @Override
    protected void initView() {
        setResult(RESULT_CANCELED);
        lockScreenView = (LockScreenView) findViewById(R.id.lock_paint);
        et_name = (EditText) findViewById(R.id.editTextManagerName);
        btn_ok = (Button) findViewById(R.id.btn_lock_nopin);

        showView();

        btn_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (et_name.getText().toString().equals("")) {
                    showToast("请输入用户名");
                    return;
                }
                username = et_name.getText().toString();
                showToast("请输入 [" + username + "] 的手势");
                hideView();

                // 隐藏自动隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        lockScreenView
                .setOnDrawLockPinFinishedListener(new OnDrawLockPinFinishedListener() {

                    @Override
                    public void onFinish(final String lockPin) {
                        boolean checkresult = doorSystem.Check(new Validate() {

                            @Override
                            public boolean checkIn(List<User> users) {
                                for (User user : users) {
                                    if (user != null) {
                                        if (user instanceof Manager) {
                                            Manager thisuser = (Manager) user;
                                            if (thisuser.getName().equals(
                                                    username)
                                                    && thisuser.getFigure()
                                                            .equals(lockPin))
                                                return true;
                                        }
                                    }
                                }
                                return false;
                            }
                        });
                        if (checkresult) {
                            setResult(RESULT_OK);
                            showToast("验证成功");
                            finish();
                        } else {
                            setResult(RESULT_CANCELED);
                            showToast("验证失败");
                            finish();
                        }
                    }
                });
    }

    private void hideView() {
        lockScreenView.setVisibility(View.VISIBLE);
        et_name.setVisibility(View.GONE);
        btn_ok.setVisibility(View.GONE);
    }

    private void showView() {
        lockScreenView.setVisibility(View.GONE);
        et_name.setVisibility(View.VISIBLE);
        btn_ok.setVisibility(View.VISIBLE);
    }

}
