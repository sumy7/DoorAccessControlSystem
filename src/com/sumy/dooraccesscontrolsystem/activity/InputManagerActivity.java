package com.sumy.dooraccesscontrolsystem.activity;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.sumy.dooraccesscontrolsystem.R;
import com.sumy.dooraccesscontrolsystem.entity.Manager;
import com.sumy.dooraccesscontrolsystem.view.LockScreenView;
import com.sumy.dooraccesscontrolsystem.view.LockScreenView.OnDrawLockPinFinishedListener;

/**
 * 录入经理的手势
 * 
 * @author sumy
 * 
 */
public class InputManagerActivity extends BaseActivity {

    private static int STEP_INPUTNAME = 1;// 输入姓名阶段
    private static int STEP_INPUTPIN_FIRST = 2;// 第一次输入手势
    private static int STEP_INPUTPIN_SECOND = 3;// 第二次输入手势

    private LockScreenView lockScreenView;
    private EditText et_name;
    private Button btn_ok;

    private int input_step; // 当前的输入步骤

    // 临时存放信息
    private String username;
    private String firstpin;
    private String secondpin;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_input_manager;
    }

    @Override
    protected void initView() {
        lockScreenView = (LockScreenView) findViewById(R.id.lock_paint);
        et_name = (EditText) findViewById(R.id.editTextManagerName);
        btn_ok = (Button) findViewById(R.id.btn_lock_nopin);

        showView();

        input_step = STEP_INPUTNAME;

        btn_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (et_name.getText().toString().equals("")) {
                    showToast("请输入用户名");
                    return;
                }
                username = et_name.getText().toString();
                input_step = STEP_INPUTPIN_FIRST;
                hideView();
                showToast("请输入手势");
                // 隐藏自动隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        lockScreenView
                .setOnDrawLockPinFinishedListener(new OnDrawLockPinFinishedListener() {

                    @Override
                    public void onFinish(String lockPin) {
                        if (input_step == STEP_INPUTPIN_FIRST) {
                            firstpin = lockPin;
                            showToast("请再输入一遍手势");
                            input_step = STEP_INPUTPIN_SECOND;
                            lockScreenView.reDrawLockPin();
                        } else if (input_step == STEP_INPUTPIN_SECOND) {
                            secondpin = lockPin;
                            if (secondpin.equals(firstpin)) {
                                Manager manager = new Manager(doorSystem
                                        .getNextUserNumber() + "", username,
                                        firstpin);
                                doorSystem.getUserlist().add(manager);
                                showToast("手势录入成功");
                                finish();
                            } else {
                                showToast("两次手势不一致，请重新输入手势");
                                input_step = STEP_INPUTPIN_FIRST;
                                lockScreenView.reDrawLockPin();
                            }
                        }
                    }
                });
        showToast("请输入用户名点确定");
    }

    private void hideView() {
        lockScreenView.setVisibility(View.VISIBLE);
        et_name.setVisibility(View.INVISIBLE);
        btn_ok.setVisibility(View.INVISIBLE);
    }

    private void showView() {
        lockScreenView.setVisibility(View.INVISIBLE);
        et_name.setVisibility(View.VISIBLE);
        btn_ok.setVisibility(View.VISIBLE);
    }
}
